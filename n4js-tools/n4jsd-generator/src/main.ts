import * as ts from "typescript";
import * as fs_lib from "fs";
import * as path_lib from "path";
import * as glob_lib from "glob"

import * as model from "./model";
import { Converter } from "./convert";
import * as utils from "./utils";


runN4jsdGen(process.argv.slice(2)); // strip the first two args (path of node binary and main script)

function runN4jsdGen(args: string[]) {
	const opts = utils.parseCommandLineOptions(args);
	if (opts.error !== undefined) {
		exitWithError(opts.error, true);
	}
	for (const inputPath of opts.inputPaths) {
		if(opts.verbose) {
			console.log();
			console.log("Input path: " + inputPath);
			processFileOrFolder(inputPath, opts);
		}
	}
}

/**
 * Convert one or more '.d.ts' files to n4jsd-files.
 *
 * @param inputPath must point to a folder or a single '.d.ts' file.
 */
function processFileOrFolder(inputPath: string, opts: utils.Options) {
	if (!fs_lib.existsSync(inputPath)) {
		logError("does not exist: " + inputPath);
		return;
	}

	let sourceProjectPath = undefined;
	const sourceDtsFilePaths = [] as string[];
	const stats = fs_lib.statSync(inputPath);
	if (stats.isFile() && inputPath.endsWith(".d.ts")) {
		sourceDtsFilePaths.push(inputPath);
	} else if (stats.isDirectory()) {
		sourceProjectPath = path_lib.isAbsolute(inputPath) ? inputPath : path_lib.resolve(inputPath);
		const globStr = path_lib.join(inputPath, "**", "*.d.ts");
		sourceDtsFilePaths.push(...glob_lib.sync(globStr, {}));
		if (sourceDtsFilePaths.length === 0) {
			logError("no '.d.ts' files found in folder: " + inputPath);
			return;
		}
	} else {
		logError("neither a '.d.ts' file nor a folder: " + inputPath);
		return;
	}

	const program = ts.createProgram(sourceDtsFilePaths, { allowJs: true });
	const converter = new Converter(program, sourceProjectPath);
	const scripts = new Map<string,model.Script>();
	let hasIssues = false;
	for (const srcDtsPath of sourceDtsFilePaths) {
		const script = converter.convertScript(srcDtsPath);
		scripts.set(srcDtsPath, script);
		hasIssues = hasIssues || script.issues.length > 0;
	}

	if (hasIssues) {
		for (const srcDtsPath of sourceDtsFilePaths) {
			const script = scripts.get(srcDtsPath);
			if (script.issues.length > 0) {
				console.log(srcDtsPath + ":");
				logIssues(script.issues);
			}
		}
		if (!opts.force) {
			return;
		}
	}

	// create target project (if necessary)
	let targetProjectPath = undefined;
	if (sourceProjectPath !== undefined) {
		targetProjectPath = createTargetProject(sourceProjectPath, opts);
		if (targetProjectPath === undefined) {
			logError("failed to create target project");
			return; // skip creating n4jsd-files for this project (even if opts.force is true)
		}
	}

	// create target n4jsd-files
	for (const srcDtsPath of sourceDtsFilePaths) {
		const script = scripts.get(srcDtsPath);
		const n4jsdStr = model.scriptToString(script);
		const trgtN4jsdPath = chooseTargetN4jsdPath(srcDtsPath, sourceProjectPath, targetProjectPath);
		if (opts.verbose) {
			console.log("Writing n4jsd-file: " + trgtN4jsdPath);
		}
		fs_lib.mkdirSync(path_lib.dirname(trgtN4jsdPath), { recursive: true });
		fs_lib.writeFileSync(trgtN4jsdPath, n4jsdStr + "\n");
	}
}

// TODO error handling!
function createTargetProject(srcPath: string, opts: utils.Options): string {
	const srcName = path_lib.basename(srcPath);
	const trgtName = "@n4jsd/" + srcName;
	const trgtPath = opts.outputPath !== undefined
		? path_lib.join(opts.outputPath, trgtName)
		: path_lib.dirname(srcPath);
	fs_lib.mkdirSync(trgtPath, { recursive: true });

	const srcPackageJsonPath = path_lib.join(srcPath, utils.PACKAGE_JSON);
	const trgtPackageJson: any = {
		name: trgtName,
		repository: {
			type: "git",
			url: "https://github.com/NumberFour/n4jsd"
		},
		description: "N4JS type definitions for " + srcName,
		keywords: [ "n4js" ],
		n4js: {
			projectType: "definition",
			definesPackage: srcName,
			// mainModule: "index", // TODO
			sources: {
				source: [
					"."
				]
			},
			requiredRuntimeLibraries: [
				// TODO
			]
		}
		// dependencies will be added below from original package.json
	};
	if (fs_lib.existsSync(srcPackageJsonPath)) {
		const srcPackageJsonStr = fs_lib.readFileSync(srcPackageJsonPath).toString();
		const srcPackageJson = JSON.parse(srcPackageJsonStr);
		if (srcPackageJson) {
			if (srcPackageJson.dependencies) {
				trgtPackageJson.dependencies = srcPackageJson.dependencies;
			}
			if (srcPackageJson.private) {
				trgtPackageJson.private = true;
			}
		}
	}
	const trgtPackageJsonPath = path_lib.join(trgtPath, utils.PACKAGE_JSON);
	fs_lib.writeFileSync(trgtPackageJsonPath, JSON.stringify(trgtPackageJson, undefined, "\t"));

	return trgtPath;
}

// TODO error handling!
function chooseTargetN4jsdPath(sourceDtsFilePath: string, sourceProjectPath: string, targetProjectPath: string): string {
	let srcPathWithoutExt = sourceDtsFilePath.endsWith(".d.ts")
		? sourceDtsFilePath.slice(0, sourceDtsFilePath.length - ".d.ts".length)
		: sourceDtsFilePath;
	let trgtN4jsdPath = srcPathWithoutExt + ".n4jsd";
	if (sourceProjectPath !== undefined && targetProjectPath !== undefined) {
		const projectRelativePath = path_lib.relative(sourceProjectPath, trgtN4jsdPath);
		trgtN4jsdPath = path_lib.resolve(targetProjectPath, projectRelativePath);
	}
	return trgtN4jsdPath;
}

function logIssues(issues: utils.Issue[]) {
	for (const issue of issues) {
		console.log(issue.kind.toUpperCase() + ": " + issue.message);
	}
}

function logError(msg: string) {
	console.log("ERROR: " + msg);
}

function exitWithError(msg: string, showUsage?: boolean): never {
	logError(msg);
	if (showUsage) {
		console.log();
		console.log(utils.usage);
	}
	process.exit(1);
}
