/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */

import * as fs_lib from "fs";
import * as glob_lib from "glob"


export const USAGE = `\
USAGE: dts2n4jsd [<options>] <input> ...

Each argument <input> must be a path or glob pattern denoting either one or more individual '.d.ts' files or
one or more folders. In the former case, the files are converted individually and the resulting N4JSD files
are placed right next to the original '.d.ts' files. In the latter case, each folder is treated as a project
and a new N4JSD project will be created.

OPTIONS

--force, -f
    Write output to disk even in case of errors, e.g. unsupported features.

--output, -o <folder>
    When converting folders, create the generated projects in the given output folder. Ignored when converting
    individual '.d.ts' files.

--verbose, -v
    Print more information while running.

EXAMPLES

dts2n4jsd node_modules/express/**/*.d.ts
    Generate an N4JSD file for each '.d.ts' file below folder "node_modules/express" and place them next to
    their corresponding '.d.ts' source file.

dts2n4jsd -o ../n4jsd-gen node_modules/*
    Generate an N4JS type definition project for each npm package in folder "node_modules" and place those
    projects in folder "../n4jsd-gen".
`;


export interface Options {
	inputPathsOrGlobs: string[],
	/** The actual input paths as resolved from the 'inputPathsOrGlobs'. */
	inputPaths: string[],
	outputPath?: string,
	verbose: boolean,
	force: boolean,
	/** A fatal error encountered during command line option parsing. */
	error?: string
}

class Option {
	names: string[];
	requiresArgument: boolean;
	applyToOptions: (opts: Options, arg: string)=>void;

	constructor(names: string[], requiresArgument: boolean, applyToOptions: (opts: Options, arg: string)=>void) {
		this.names = names;
		this.requiresArgument = requiresArgument;
		this.applyToOptions = applyToOptions;
	}
}

const OPT_FORCE   = new Option([ "--force", "-f" ],   false, (opts) => opts.force = true);
const OPT_OUTPUT  = new Option([ "--output", "-o" ],  true,  (opts, arg) => opts.outputPath = arg);
const OPT_VERBOSE = new Option([ "--verbose", "-v" ], false, (opts) => opts.verbose = true);

const allOptions = [ OPT_FORCE, OPT_OUTPUT, OPT_VERBOSE  ];

export function parseCommandLineOptions(args: string[]): Options {
	const result = {
		inputPathsOrGlobs: [],
		inputPaths: [],
		verbose: false,
		force: false
	} as Options;
	let i = 0;
	while (i < args.length) {
		const currArg = args[i++];
		if (currArg.startsWith("-")) {
			const currOpt = findOption(currArg);
			if (currOpt === undefined) {
				result.error = "unknown option: " + currArg;
				return result;
			}
			let nextArg = undefined;
			if (currOpt.requiresArgument) {
				nextArg = i < args.length && !args[i].startsWith("-") ? args[i++] : undefined;
				if (nextArg === undefined) {
					result.error = "argument missing for option " + currArg;
					return result;
				}
			}
			currOpt.applyToOptions(result, nextArg);
		} else {
			result.inputPathsOrGlobs.push(currArg);
		}
	}
	resolveInputPathsOrGlobs(result);
	return result;
}

function resolveInputPathsOrGlobs(opts: Options): void {
	if (opts.inputPathsOrGlobs.length === 0) {
		opts.error = "no input path or glob pattern given";
		return;
	}
	const paths = [] as string[];
	for (let pathGlob of opts.inputPathsOrGlobs) {
		if (!pathGlob.startsWith("/")) {
			pathGlob = __dirname + "/" + pathGlob;
		}
		const currPaths = glob_lib.sync(pathGlob, {});
		if (currPaths.length === 0) {
			opts.error = "input path does not exist: " + pathGlob;
			continue;
		}
		paths.push(...currPaths);
	}
	const errors = [] as string[];
	for (const path of paths) {
		if (!fs_lib.existsSync(path)) {
			errors.push("input path does not exist: " + path);
			continue;
		}
		const stats = fs_lib.statSync(path);
		const valid = (stats.isFile() && path.endsWith(".d.ts")) || stats.isDirectory();
		if (!valid) {
			errors.push("neither a folder nor a '.d.ts' file: " + path);
		}
	}
	if (errors.length > 0) {
		opts.error = "invalid input path(s):\n    " + errors.join("\n    ");
		return;
	}
	opts.inputPaths.push(...paths);
}

function findOption(optionStr: string): Option | undefined {
	optionStr = optionStr.trim();
	for (const opt of allOptions) {
		for (const name of opt.names) {
			if (optionStr == name) {
				return opt;
			}
		}
	}
	return undefined;
}
