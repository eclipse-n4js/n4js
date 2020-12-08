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
import * as path_lib from "path";
import * as model from "./model";
import * as utils from "./utils";
import * as utils_ts from "./utils_ts";

export class Converter {
	readonly program: ts.Program;
	readonly checker: ts.TypeChecker;
	readonly projectPath?: string;

	legacyExportedNamespace: ts.Symbol;
	readonly issues: utils.Issue[] = [];

	constructor(program: ts.Program, projectPath?: string) {
		if (projectPath !== undefined && !path_lib.isAbsolute(projectPath)) {
			throw "projectPath must be absolute";
		}

		this.program = program;
		this.checker = program.getTypeChecker();
		this.projectPath = projectPath;
	}

	convertScript(sourceFilePath: string): model.Script {
		// clean up
		this.legacyExportedNamespace = undefined;
		this.issues.length = 0;

		const sourceFile = this.program.getSourceFile(sourceFilePath);
		const dtsMode = utils_ts.getDTSMode(sourceFile);

		if (dtsMode == model.DTSMode.LEGACY) {
			const legacyExport = utils_ts.getExportEquals(sourceFile);
			const sym = this.checker.getSymbolAtLocation(legacyExport.expression);
			if (utils.testFlagsOR(sym.flags, ts.SymbolFlags.ValueModule, ts.SymbolFlags.NamespaceModule)) {
				this.legacyExportedNamespace = sym;
			}
		}

		const result = new model.Script();
		result.mode = dtsMode;
		sourceFile.forEachChild(node => {
			if (ts.isImportDeclaration(node)) {
				const imports = this.convertImport(node);
				result.imports.push(...imports);
			} else {
				const elems = this.convertExportableElement(node);
				result.topLevelElements.push(...elems);
			}
		});
		result.issues.push(...this.issues);

		return result;
	}

	private convertImport(node: ts.ImportDeclaration): model.Import[] {
		const moduleSpecifier = this.convertModuleSpecifier(node);
		if (moduleSpecifier === undefined) {
			return [];
		}
		const importClause = node.importClause;
		if (importClause === undefined) {
			// bare import -> ignore it
			return [];
		} else if (importClause.name) {
			const sym = this.checker.getSymbolAtLocation(importClause.name);
			const result = new model.DefaultImport();
			result.moduleSpecifier = moduleSpecifier;
			result.localName = sym.getName();
			return [ result ];
		} else if (importClause.namedBindings) {
			const bindings = importClause.namedBindings;
			if (ts.isNamespaceImport(bindings)) {
				const sym = this.checker.getSymbolAtLocation(bindings.name);
				const result = new model.NamespaceImport();
				result.moduleSpecifier = moduleSpecifier;
				result.namespaceName = sym.getName();
				return [ result ];
			} else if (ts.isNamedImports(bindings)) {
				const results = [];
				for (const importSpecifier of bindings.elements) {
					const symImportedElement = this.checker.getSymbolAtLocation(importSpecifier.name);
					const symAlias = importSpecifier.propertyName !== undefined ? this.checker.getSymbolAtLocation(importSpecifier.propertyName) : undefined;
					const result = new model.NamedImport();
					result.moduleSpecifier = moduleSpecifier;
					result.importedElementName = symImportedElement.getName();
					if (symAlias) {
						result.aliasName = symAlias.getName();
					}
					results.push(result);
				}
				return results;
			}
		}
		this.createIssueForUnsupportedNode(node, "import");
		return [];
	}

	private convertExportableElement(node: ts.Node): model.ExportableElement[] {
		if (node.kind === ts.SyntaxKind.EndOfFileToken) {
			return []; // ignore
		}
		if (ts.isImportDeclaration(node)) {
			return []; // imports are handled in #convertScript(), ignore them here
		} else if (ts.isExportAssignment(node) && node.isExportEquals) {
			return []; // "export equals" was handled in #convertScript() as part of legacy support, ignore it here
		} else if (ts.isExportAssignment(node) && !node.isExportEquals) { // default exports
			return []; // TODO!!!
		} else if (ts.isVariableDeclarationList(node)) {
			return this.convertVariableDeclList(node); // TODO can a VariableDeclarationList even appear here?
		} else if (ts.isFunctionDeclaration(node)) {
			return [ this.convertFunction(node) ];
		} else if (ts.isEnumDeclaration(node)) {
			return [ this.convertEnum(node) ];
		} else if (ts.isInterfaceDeclaration(node)) {
			return [ this.convertInterface(node) ];
		} else if (ts.isClassDeclaration(node)) {
			return [ this.convertClass(node) ];
		} else if (ts.isTypeAliasDeclaration(node)) {
			const elem = this.convertTypeAlias(node);
			return elem !== undefined ? [ elem ] : [];
		} else if (node.kind === ts.SyntaxKind.FirstStatement) {
			const children = utils_ts.getAllChildNodes(node);
			if (children.length === 2
				&& (children[0].kind === ts.SyntaxKind.ExportKeyword
					|| children[0].kind === ts.SyntaxKind.DeclareKeyword)
				&& children[1].kind === ts.SyntaxKind.VariableDeclarationList) {
				// something like "export var someStr: string, someNum: number;"
				return this.convertVariableDeclList(children[1] as ts.VariableDeclarationList);
			}
		} else if (ts.isModuleDeclaration(node)) {
			const sym = this.checker.getSymbolAtLocation(node.name);
			if (sym === this.legacyExportedNamespace) {
				const result = [];
				node.body.forEachChild(child => {
					result.push(...this.convertExportableElement(child));
				});
				return result;
			}
		}
		this.createIssueForUnsupportedNode(node);
		return [];
	}

	private convertVariableDeclList(node: ts.VariableDeclarationList): model.Variable[] {
		const keyword = utils_ts.getVarDeclKeyword(node);
		const result = [] as model.Variable[];
		for (const varDecl of node.declarations) {
			result.push(this.convertVariable(varDecl, keyword));
		}
		return result;
	}

	private convertVariable(node: ts.VariableDeclaration, keyword: 'var' | 'let' | 'const'): model.Variable {
		const sym = this.checker.getSymbolAtLocation(node.name);
		const varName = sym.getName();

		const result = new model.Variable();
		result.name = varName;
		result.keyword = keyword;
		result.type = this.convertTypeReference(node.type);
		result.exported = utils_ts.isExported(node);
		return result;
	}

	private convertFunction(node: ts.FunctionDeclaration): model.Function {
		const sym = this.checker.getSymbolAtLocation(node.name);
		const funName = sym.getName();
		const funSigs = this.convertCallSignatures(sym);

		const result = new model.Function();
		result.name = funName;
		result.signatures = funSigs;
		result.exported = utils_ts.isExported(node);
		return result;
	}

	private convertEnum(node: ts.EnumDeclaration): model.Type {
		const sym = this.checker.getSymbolAtLocation(node.name);
		let result = new model.Type();
		result.name = sym.getName();
		result.kind = 'enum';
		result.exported = utils_ts.isExported(node);

		const flags = ts.getCombinedModifierFlags(node);
		const isConst = utils.testFlag(flags, ts.ModifierFlags.Const);
		if (isConst) {
			const valueTypes = utils_ts.getValueTypesOfEnum(node, this.checker);
			const singleValueType = valueTypes.size === 1 ? valueTypes.values().next().value : undefined;
			if (valueTypes.size === 0) {
				// in TypeScript, const enums are number-based by default:
				result.primitiveBased = 'number';
			} else if (singleValueType == 'string' || singleValueType == 'number') {
				result.primitiveBased = singleValueType;
			} else {
				result.primitiveBased = 'string'; // use @StringBased enum in error case
				this.createIssueForNode("unsupported value types in const enum: " + Array.from(valueTypes).join(", "), node);
			}
		}

		result.literals.push(...this.convertEnumLiterals(node, isConst));

		return result;
	}

	private convertEnumLiterals(node: ts.EnumDeclaration, isConst: boolean): model.EnumLiteral[] {
		const results = [] as model.EnumLiteral[];
		for (const lit of node.members) {
			const litSym = this.checker.getSymbolAtLocation(lit.name);
			const result = new model.EnumLiteral();
			result.name = litSym.getName();
			if (isConst) {
				result.value = this.checker.getConstantValue(lit);
			}
			results.push(result);
		}
		return results;
	}

	private convertInterface(node: ts.InterfaceDeclaration): model.Type {
		const sym = this.checker.getSymbolAtLocation(node.name);
		let result = new model.Type();
		result.name = sym.getName();
		result.kind = 'interface';
		result.defSiteStructural = true;
		result.members.push(...this.convertMembers(node));
		result.exported = utils_ts.isExported(node);
		return result;
	}

	private convertClass(node: ts.ClassDeclaration): model.Type {
		const sym = this.checker.getSymbolAtLocation(node.name);
		let result = new model.Type();
		result.name = sym.getName();
		result.kind = 'class';
		result.defSiteStructural = true;
		result.members.push(...this.convertMembers(node));
		result.exported = utils_ts.isExported(node);
		return result;
	}

	private convertMembers(node: ts.NamedDeclaration): model.Member[] {
		const sym = this.checker.getSymbolAtLocation(node.name);
		const result = [] as model.Member[];
		sym.members?.forEach((symMember, name) => {
			const member = this.convertMember(symMember, sym);
			if (member !== undefined) {
				result.push(member);
			}
		});
		return result;
	}

	private convertMember(symMember: ts.Symbol, symOwner: ts.Symbol): model.Member | undefined {
		// we need an AST node; in case of overloading there will be several declarations (one per signature)
		// but because relevant properties (kind, accessibility, etc.) will be the same in all cases we can
		// simply use the first one as representative:
		const representativeNode = symMember.declarations[0] as ts.NamedDeclaration;

		const result = new model.Member();
		result.accessibility = utils_ts.getAccessibility(representativeNode);

		if (ts.isConstructorDeclaration(representativeNode)) {
			result.kind = 'ctor';
			result.signatures = this.convertConstructSignatures(symOwner);
			return result;
		}

		result.name = symMember.getName();

		if (ts.isPropertyDeclaration(representativeNode)) {
			result.kind = 'field';
			result.type = this.convertTypeReferenceOfTypedSymbol(symMember);
			return result;
		} else if (ts.isGetAccessorDeclaration(representativeNode)) {
			result.kind = 'getter';
			result.type = this.convertTypeReferenceOfTypedSymbol(symMember);
			return result;
		} else if (ts.isSetAccessorDeclaration(representativeNode)) {
			result.kind = 'setter';
			result.type = this.convertTypeReferenceOfTypedDeclaration(representativeNode.parameters[0]);
			return result;
		} else if (ts.isMethodDeclaration(representativeNode)
				|| ts.isMethodSignature(representativeNode)) {
			const sigs = this.convertCallSignatures(symMember);
			result.kind = 'method';
			result.signatures = sigs;
			return result;
		}
		this.createIssueForUnsupportedNode(representativeNode, "member");
		return undefined;
	}

	private convertConstructSignatures(somethingWithCtors: ts.Symbol): model.Signature[] {
		const type = this.checker.getTypeOfSymbolAtLocation(somethingWithCtors, somethingWithCtors.valueDeclaration!);
		return this.convertSignatures([...type.getConstructSignatures()]);
	}

	private convertCallSignatures(somethingWithSignatures: ts.Symbol): model.Signature[] {
		const type = this.checker.getTypeOfSymbolAtLocation(somethingWithSignatures, somethingWithSignatures.valueDeclaration!);
		return this.convertSignatures([...type.getCallSignatures()]);
	}

	private convertSignatures(signatures: ts.Signature[]): model.Signature[] {
		const results = [] as model.Signature[];
		for (const sig of signatures) {
			const result = new model.Signature();
			result.parameters = sig.getParameters().map(param => this.convertParameter(param));
			result.returnType = this.convertTypeReferenceOfTypedDeclaration(sig.declaration);
			results.push(result);
		}
		return results;
	}

	private convertParameter(param: ts.Symbol): model.Parameter {
		const result = new model.Parameter();
		result.name = param.getName();
		result.type = this.convertTypeReferenceOfTypedSymbol(param);
		return result;
	}

	private convertTypeAlias(node: ts.TypeAliasDeclaration): model.Type | undefined {
		if (!ts.isUnionTypeNode(node.type)
			|| node.type.types.length === 0
			|| !node.type.types.every(ts.isLiteralTypeNode)) {
			// not the special case covered by this method
			this.createIssueForNode("type alias not supported (except the special case of an aliased union of literal types)", node);
			return undefined;
		}
		const literalValues = [] as (string | number)[];
		for (const elemTypeNode of node.type.types) {
			const elemType = this.checker.getTypeFromTypeNode(elemTypeNode);
			if (elemType.isLiteral()) {
				if (elemType.isNumberLiteral()) {
					literalValues.push(elemType.value);
					continue;
				} else if (elemType.isStringLiteral()) {
					literalValues.push(elemType.value);
					continue;
				}
			}
			this.createIssueForNode("unsupported type in aliased union of literal types: " + this.checker.typeToString(elemType), node);
			return undefined;
		}
		const isAllString = literalValues.every(v => typeof v == 'string');
		const isAllNumber = literalValues.every(v => typeof v == 'number');
		if (!isAllString && !isAllNumber) {
			this.createIssueForNode("a combination of strings and numbers in an aliased union of literal types is not allowed", node);
			return undefined;
		}

		const sym = this.checker.getSymbolAtLocation(node.name);
		const result = new model.Type();
		result.name = sym.getName();
		result.kind = 'enum';
		result.exported = utils_ts.isExported(node);
		result.primitiveBased = isAllString ? 'string' : 'number';
		result.literals.push(...utils_ts.createEnumLiteralsFromValues(literalValues));
		return result;
	}

	private convertModuleSpecifier(importDecl: ts.ImportDeclaration): string {
		const moduleSpec = importDecl.moduleSpecifier;
		if (!ts.isStringLiteral(moduleSpec)) {
			return undefined; // this is a syntax error in TS
		}
		let moduleSpecStr = moduleSpec.text;
		if (moduleSpecStr.startsWith(".")) {
			if (this.projectPath !== undefined) {
				const srcFilePath = path_lib.dirname(importDecl.getSourceFile().fileName);
				const srcFilePathAbs = path_lib.isAbsolute(srcFilePath) ? srcFilePath : path_lib.resolve(srcFilePath);
				const trgtFilePath = path_lib.resolve(srcFilePathAbs, moduleSpecStr).normalize();
				moduleSpecStr = path_lib.relative(this.projectPath, trgtFilePath);
			} else {
				this.issues.push(utils.error("cannot resolve relative module specifier in single-file mode: " + moduleSpecStr));
			}
		}
		return moduleSpecStr;
	}

	private convertTypeReferenceOfTypedSymbol(sym: ts.Symbol): model.TypeRef {
		// note: the following API might be of use when proper handling of type references is being implemented:
		// const symType = this.checker.getTypeOfSymbolAtLocation(sym, sym.valueDeclaration!);
		// const typeAsStr = this.checker.typeToString(symType);
		return this.convertTypeReferenceOfTypedDeclaration(sym?.valueDeclaration);
	}
	private convertTypeReferenceOfTypedDeclaration(decl: ts.Declaration): model.TypeRef {
		const typeRef = (decl as any)?.type; // no common interface available
		if (typeRef && ts.isTypeNode(typeRef)) {
			return this.convertTypeReference(typeRef);
		}
		return undefined;
	}
	private convertTypeReference(node?: ts.TypeNode): model.TypeRef {
		if (node) {
			const result = new model.TypeRef();
			result.tsSourceString = node.getText().trim();
			return result;
		}
		return undefined;
	}

	private createIssueForUnsupportedNode(node: ts.Node, superKind: string = "node") {
		const nodeKindStr = ts.SyntaxKind[node.kind];
		this.createIssueForNode("unsupported kind of " + superKind + ": " + nodeKindStr, node);
	}

	private createIssueForNode(msg: string, node: ts.Node) {
		const offendingCode = utils_ts.getSourceCodeForNode(node);
		const error = utils.error(msg + "\n" + offendingCode);
		this.issues.push(error);
	}
}
