import { RSA_NO_PADDING } from "constants";
import * as ts from "typescript";
import * as model from "./model";
import * as utils from "./utils";

// utilities related to TypeScript

export function getAllChildNodes(node: ts.Node): ts.Node[] {
	// for some reason, node.getChildren() does not do the trick in all cases, it seems
	const result = [] as ts.Node[];
	node.forEachChild(child => {
		//console.log(ts.SyntaxKind[child.kind]);
		result.push(child);
	});
	return result;
}

export function traverse(sourceFile: ts.SourceFile, fn: (node: ts.Node, indent: number)=>boolean) {
	const doTraverse = (node: ts.Node, indent: number) => {
		const traverseChildren = fn(node, indent);
		if (traverseChildren) {
			ts.forEachChild(node, (n)=>doTraverse(n, indent + 1));
		}
	}
	doTraverse(sourceFile, 0);
}

export function isExported(node: ts.Node): boolean {
	const flags = ts.getCombinedModifierFlags(node as ts.Declaration);
	return utils.testFlag(flags, ts.ModifierFlags.Export)
		|| (!!node.parent && node.parent.kind === ts.SyntaxKind.SourceFile);
}

export function getAccessibility(node: ts.Declaration): model.Accessibility {
	const flags = ts.getCombinedModifierFlags(node);
	if (utils.testFlag(flags, ts.ModifierFlags.Public)) {
		return model.Accessibility.PUBLIC;
	} else if (utils.testFlag(flags, ts.ModifierFlags.Protected)) {
		return model.Accessibility.PROTECTED;
	} else if (utils.testFlag(flags, ts.ModifierFlags.Private)) {
		return model.Accessibility.PRIVATE;
	}
	return undefined;
}

export function getDTSMode(file: ts.SourceFile): model.DTSMode {
	if (!file.isDeclarationFile) {
		return model.DTSMode.NONE;
	}
	const containsExportEquals = getExportEquals(file) !== undefined;
	return containsExportEquals ? model.DTSMode.LEGACY : model.DTSMode.MODULE;
}

export function getExportEquals(file: ts.SourceFile): ts.ExportAssignment {
	if (file.isDeclarationFile) {
		for (const child of getAllChildNodes(file)) {
			if (ts.isExportAssignment(child) && child.isExportEquals) {
				return child;
			}
		}
	}
	return undefined;
}

export function getVarDeclKeyword(node: ts.VariableDeclarationList): 'var' | 'let' | 'const' {
	const flags = ts.getCombinedNodeFlags(node);
	if (utils.testFlag(flags, ts.NodeFlags.Let)) {
		return 'let';
	} else if (utils.testFlag(flags, ts.NodeFlags.Const)) {
		return 'const';
	}
	return 'var';
}

export function getTypeKind(decl: ts.Node) {
	if (decl) {
		if (ts.isClassDeclaration(decl)) {
			return 'class';
		} else if (ts.isInterfaceDeclaration(decl)) {
			return 'interface';
		} else if (ts.isEnumDeclaration(decl)) {
			return 'enum';
		} else if (ts.isTypeAliasDeclaration(decl)) {
			return 'alias';
		}
	}
	return undefined;
}

export function getSourceCodeForNode(node: ts.Node, indentStr: string = "  |"): string {
	const sourceFile = node.getSourceFile();
	let offendingCode = sourceFile.text.substring(node.pos, node.end);
	offendingCode = offendingCode.trim();
	if (offendingCode.length > 256) {
		offendingCode = offendingCode.slice(0, 256) + " [...]";
	}
	offendingCode = offendingCode.replace(/\r\n/gi, "\n");
	offendingCode = indentStr + offendingCode.replace(/\n/gi, "\n" + indentStr);
	return offendingCode;
}
