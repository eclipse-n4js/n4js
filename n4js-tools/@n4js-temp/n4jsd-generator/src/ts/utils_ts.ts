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

import * as ts from "typescript";
import * as model from "./model";
import * as utils from "./utils";

// utilities related to TypeScript

export function getAllChildNodes(node: ts.Node): ts.Node[] {
	// for some reason, node.getChildren() does not do the trick in all cases, it seems
	const result = [] as ts.Node[];
	node.forEachChild(child => {
		//(ts.SyntaxKind[child.kind]);
		result.push(child);
	});
	return result;
}

export function traverse(sourceFile: ts.SourceFile, fn: (node: ts.Node, indent: number)=>boolean): void {
	const doTraverse = (node: ts.Node, indent: number) => {
		const traverseChildren = fn(node, indent);
		if (traverseChildren) {
			ts.forEachChild(node, (n)=>doTraverse(n, indent + 1));
		}
	}
	doTraverse(sourceFile, 0);
}

export function isExported(node: ts.Declaration): boolean {
	const flags = ts.getCombinedModifierFlags(node);
	return utils.testFlag(flags, ts.ModifierFlags.Export)
		|| (!!node.parent && node.parent.kind === ts.SyntaxKind.SourceFile);
}

export function isExportedAsDefault(node: ts.NamedDeclaration, checker: ts.TypeChecker, exportAssignment?: ts.ExportAssignment): boolean {
	if (!isExported(node)) {
		return false;
	}
	const flags = ts.getCombinedModifierFlags(node);
	if (utils.testFlag(flags, ts.ModifierFlags.ExportDefault)) {
		return true;
	}
	if (exportAssignment) {
		// legacy, e.g.: export = myFunction;
		const exportSymbol = checker.getSymbolAtLocation(exportAssignment.expression);
		const exportSymbol2 = checker.getSymbolAtLocation(node.name);

		if (exportSymbol === exportSymbol2) {
			return true;
		}
	}

	return false;
}

export function getLocalNameOfExportableElement(node: ts.NamedDeclaration, checker: ts.TypeChecker, exportAssignment?: ts.ExportAssignment): string {
	if (isExportedAsDefault(node, checker, exportAssignment)) {
		// could not find a public API for obtaining the local symbol:
		const localSym = (node as any).localSymbol as ts.Symbol;
		if (localSym) {
			return localSym.getName();
		}
	}
	// this seems to be the ordinary approach for obtaining names,
	// but would return "default" for default-exported elements
	const sym = checker.getSymbolAtLocation(node.name);
	return sym.getName();
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

export function getVarDeclKeyword(node: ts.VariableDeclarationList): model.VariableKeyword {
	const flags = ts.getCombinedNodeFlags(node);
	if (utils.testFlag(flags, ts.NodeFlags.Let)) {
		return model.VariableKeyword.LET;
	} else if (utils.testFlag(flags, ts.NodeFlags.Const)) {
		return model.VariableKeyword.CONST;
	}
	return model.VariableKeyword.VAR;
}

export function getTypeKind(decl: ts.Node): model.TypeKind {
	if (decl) {
		if (ts.isClassDeclaration(decl)) {
			return model.TypeKind.CLASS;
		} else if (ts.isInterfaceDeclaration(decl)) {
			return model.TypeKind.INTERFACE;
		} else if (ts.isEnumDeclaration(decl)) {
			return model.TypeKind.ENUM;
		} else if (ts.isTypeAliasDeclaration(decl)) {
			return model.TypeKind.ALIAS;
		}
	}
	return undefined;
}

export function getValueTypesOfEnum(enumDecl: ts.EnumDeclaration, checker: ts.TypeChecker): Set<string> {
	const types = new Set<string>();
	for (const lit of enumDecl.members) {
		const initExpr = lit.initializer;
		if (initExpr !== undefined) {
			const type = checker.getTypeAtLocation(initExpr);
			if (type !== undefined) {
				const baseType = checker.getBaseTypeOfLiteralType(type);
				if (baseType !== undefined) {
					types.add(checker.typeToString(baseType));
				} else {
					types.add(checker.typeToString(type));
				}
			}
		}
	}
	return types;
}

export function createEnumLiteralsFromValues(values: (string | number)[]): model.EnumLiteral[] {
	const litNames = new Set<string>();
	const results = [] as model.EnumLiteral[];
	for (const value of values) {
		let name: string;
		// 1) choose a valid identifier as name
		if (typeof value == 'string') {
			if (value.length === 0) {
				name = "_";
			} else {
				name = value;
				if (name.charAt(0) >= '0' && name.charAt(0) <= '9') {
					name = "_" + name;
				}
				for (let i = 0; i < name.length; i++) {
					const ch = name.charAt(i).toUpperCase();
					const isValid = ch == '_' || (ch >= 'A' && ch <= 'Z') || (i > 0 && ch >= '0' && ch <= '9');
					if (!isValid) {
						name = name.substring(0, i) + (ch == " " ? "_" : "X") + name.substring(i + 1, name.length);
					}
				}
			}
		} else {
			name = "L" + value;
		}
		// 2) make unique
		if (litNames.has(name)) {
			let i = 2;
			while (litNames.has(name + i)) {
				i++;
			}
			name = name + i;
		}
		litNames.add(name);
		// 3) create enum literal
		const result = new model.EnumLiteral();
		result.name = name;
		result.value = value;
		results.push(result);
	}
	return results;
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
