import * as glob from "glob"
import * as fs from "fs";

export const PACKAGE_JSON = "package.json";

export const usage = `\
USAGE: n4jsd-gen [<options>] <inputPath> ...`;

export interface Options {
	inputPathGlobs: string[],
	/** The actual input paths as resolved from the 'inputPathGlobs'. */
	inputPaths: string[],
	outputPath?: string,
	verbose: boolean,
	force: boolean,
	error?: string
}

const optionsThatRequireAnArgument = [
	"--output"
];

export function parseCommandLineOptions(args: string[]): Options {
	const result = {
		inputPathGlobs: [],
		inputPaths: [],
		verbose: false,
		force: false
	} as Options;
	if (args.length === 0) {
		result.error = "no input path given";
		return result;
	}
	let i = 0;
	while (i < args.length) {
		const currArg = args[i++];
		if (currArg.startsWith("--")) {
			let nextArg = undefined;
			if (optionsThatRequireAnArgument.indexOf(currArg) >= 0) {
				nextArg = i < args.length && !args[i].startsWith("--") ? args[i++] : undefined;
				if (nextArg === undefined) {
					result.error = "argument missing for option " + currArg;
					return result;
				}
			}
			switch (currArg) {
				case "--output":
					result.outputPath = nextArg;
					break;
				case "--verbose":
					result.verbose = !result.verbose;
					break;
				case "--force":
					result.force = !result.force;
					break;
				default:
					result.error = "unknown option: " + currArg;
					return result;
			}
		} else {
			result.inputPathGlobs.push(currArg);
		}
	}
	resolveInputPathStr(result);
	return result;
}

function resolveInputPathStr(opts: Options) {
	const paths = [] as string[];
	for (let pathGlob of opts.inputPathGlobs) {
		if (!pathGlob.startsWith("/")) {
			pathGlob = __dirname + "/" + pathGlob;
		}
		const currPaths = glob.sync(pathGlob, {});
		if (currPaths.length === 0) {
			opts.error = "input path does not exist: " + pathGlob;
			continue;
		}
		paths.push(...currPaths);
	}
	const errors = [] as string[];
	for (const path of paths) {
		if (!fs.existsSync(path)) {
			errors.push("input path does not exist: " + path);
			continue;
		}
		const stats = fs.statSync(path);
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



export class Issue {
	kind: 'warning' | 'error';
	message: string;

	constructor(msg: string, kind: 'warning' | 'error' = 'error') {
		this.kind = kind;
		this.message = msg;
	}
}
export function error(msg: string) {
	return new Issue(msg, 'error');
}
export function warning(msg: string) {
	return new Issue(msg, 'warning');
}



export function testFlagsOR(value: number, ...flags: number[]) {
	for (const flag of flags) {
		if (testFlag(value, flag)) {
			return true;
		}
	}
	return false;
}

export function testFlagsAND(value: number, ...flags: number[]) {
	for (const flag of flags) {
		if (!testFlag(value, flag)) {
			return false;
		}
	}
	return true;
}

export function testFlag(value: number, flag: number) {
	return (value & flag) === flag;
}
