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

export type IgnorePredicate = (filePath: string, elementName?: string, memberName?: string, signatureIndex?: number) => boolean;

export class Converter {
	private readonly projectPath?: string;
	private readonly ignorePredicate?: IgnorePredicate;
	/** True iff option '--runtime-libs' was given on the command line. */
	private readonly runtimeLibs: boolean;

	private readonly program: ts.Program;
	private readonly checker: ts.TypeChecker;

	private exportAssignment: ts.ExportAssignment;
	private readonly convertedTypes: Map<ts.Symbol, model.Type> = new Map<ts.Symbol, model.Type>();
	private readonly suppressedTypes: Set<ts.Type> = new Set<ts.Type>();
	private readonly issues: utils.Issue[] = [];

	constructor(sourceDtsFilePaths: string[], projectPath?: string, ignorePredicate?: IgnorePredicate, runtimeLibs = false) {
		if (projectPath !== undefined && !path_lib.isAbsolute(projectPath)) {
			throw "projectPath must be absolute";
		}

		this.projectPath = projectPath;
		this.ignorePredicate = ignorePredicate;
		this.runtimeLibs = runtimeLibs;

		// prepare compilation options
		const opts: ts.CompilerOptions = {
			allowJs: true
		};
		if (runtimeLibs) {
			opts.noLib = true;
			opts.types = []; // make sure we do not compile type definitions requested in a tsconfig.json the compiler might find!
		}

		// compile .d.ts files
		const program = ts.createProgram(sourceDtsFilePaths, opts);
		this.program = program;
		this.checker = program.getTypeChecker();
	}

	getDiagnostics(): string[] {
		const diagnostics = [] as ts.Diagnostic[];
		diagnostics.push(...this.program.getSyntacticDiagnostics());
		diagnostics.push(...this.program.getDeclarationDiagnostics());
		diagnostics.push(...this.program.getSemanticDiagnostics());
		const result = [] as string[];
		for (const diag of diagnostics) {
			// note: this is how to retrieve the path of the file containing the issue:
			//const fileName = diag?.file?.fileName;
			const msg = diag?.messageText;
			if (typeof msg === 'string') {
				result.push(msg);
			} else if (typeof msg === 'object' && msg.messageText) {
				result.push(msg.messageText); // only use the head of the DiagnosticMessageChain
			}
		}
		return result;
	}

	convertScript(sourceFilePath: string): model.Script {
		// clean up
		this.exportAssignment = undefined;
		this.convertedTypes.clear();
		this.suppressedTypes.clear();
		this.issues.length = 0;

		const sourceFile = this.program.getSourceFile(sourceFilePath);
		const dtsMode = utils_ts.getDTSMode(sourceFile);

		if (dtsMode == model.DTSMode.LEGACY) {
			this.exportAssignment = utils_ts.getExportEquals(sourceFile);
		}

		const result = new model.Script();
		result.tsFileName = path_lib.basename(sourceFilePath);
		result.tsFilePath = sourceFilePath;
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
		this.createErrorForUnsupportedNode(node, "import");
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
			return this.isIgnoredTopLevelElement(node) ? [] : inArrayIfDefined(this.convertFunction(node));
		} else if (ts.isEnumDeclaration(node)) {
			return this.isIgnoredTopLevelElement(node) ? [] : inArrayIfDefined(this.convertEnum(node));
		} else if (ts.isInterfaceDeclaration(node)) {
			return this.isIgnoredTopLevelElement(node) ? [] : inArrayIfDefined(this.convertInterface(node));
		} else if (ts.isClassDeclaration(node)) {
			return this.isIgnoredTopLevelElement(node) ? [] : inArrayIfDefined(this.convertClass(node));
		} else if (ts.isTypeAliasDeclaration(node)) {
			return this.isIgnoredTopLevelElement(node) ? [] : inArrayIfDefined(this.convertTypeAlias(node));
		} else if (node.kind === ts.SyntaxKind.FirstStatement) {
			const children = utils_ts.getAllChildNodes(node);
			if (children.length === 2
				&& (children[0].kind === ts.SyntaxKind.ExportKeyword
					|| children[0].kind === ts.SyntaxKind.DeclareKeyword)
				&& children[1].kind === ts.SyntaxKind.VariableDeclarationList) {
				// something like "export var someStr: string, someNum: number;"
				const result = this.convertVariableDeclList(children[1] as ts.VariableDeclarationList);
				if (result[0] && !result[0].jsdoc) {
					result[0].jsdoc = utils_ts.getJSDocForNode(node);
				}
				return result;
			}
		} else if (ts.isModuleDeclaration(node)) {
			if (this.isIgnoredTopLevelElement(node)) {
				return [];
			}
			if (!this.exportAssignment) {
				// we are *not* in legacy mode
				return this.convertModuleDeclaration(node);
			}
			const exportSymbol = this.checker.getSymbolAtLocation(this.exportAssignment.expression);

			if (utils.testFlagsOR(exportSymbol.flags,
				ts.SymbolFlags.ValueModule,
				ts.SymbolFlags.NamespaceModule)) {

				const sym = this.checker.getSymbolAtLocation(node.name);
				if (sym === exportSymbol) {
					const result = [];
					node.body.forEachChild(child => {
						result.push(...this.convertExportableElement(child));
					});
					return result;
				}
			}
		}
		this.createErrorForUnsupportedNode(node);
		return [];
	}

	private convertModuleDeclaration(node: ts.ModuleDeclaration): model.ExportableElement[] {
		// TODO implement support for namespaces
		return [];
	}

	private convertVariableDeclList(node: ts.VariableDeclarationList): model.Variable[] {
		const keyword = utils_ts.getVarDeclKeyword(node);
		const result = [] as model.Variable[];
		for (const varDecl of node.declarations) {
			if (this.isIgnoredTopLevelElement(varDecl)) {
				continue;
			}
			result.push(this.convertVariable(varDecl, keyword));
		}
		return result;
	}

	private convertVariable(node: ts.VariableDeclaration, keyword: model.VariableKeyword): model.Variable {
		const result = new model.Variable();
		result.name = utils_ts.getLocalNameOfExportableElement(node, this.checker, this.exportAssignment);
		result.jsdoc = utils_ts.getJSDocForNode(node);
		result.keyword = keyword;
		result.type = this.convertTypeReference(node.type);
		result.exported = utils_ts.isExported(node);
		result.exportedAsDefault = utils_ts.isExportedAsDefault(node, this.checker, this.exportAssignment);
		return result;
	}

	private convertFunction(node: ts.FunctionDeclaration): model.Function {
		const sym = this.checker.getSymbolAtLocation(node.name);
		const sourceFile = node.getSourceFile();
		let firstDeclFromCurrentFile = sym.declarations?.find(decl => decl?.getSourceFile() === sourceFile);
		if (firstDeclFromCurrentFile !== node) {
			// we were called for the AST node of a signature other than this function's first signature
			// --> ignore this call, because we have handled all signatures in one go when we were
			// called for the AST node of the first signature
			return undefined;
		}

		const result = new model.Function();
		result.name = utils_ts.getLocalNameOfExportableElement(node, this.checker, this.exportAssignment);
		result.jsdoc = utils_ts.getJSDocForNode(node);
		const funSigs = this.convertCallSignatures(node.getSourceFile(), sym);
		result.signatures = funSigs;
		result.exported = utils_ts.isExported(node);
		result.exportedAsDefault = utils_ts.isExportedAsDefault(node, this.checker, this.exportAssignment);
		return result;
	}

	private convertEnum(node: ts.EnumDeclaration): model.Type {
		let result = new model.Type();
		result.name = utils_ts.getLocalNameOfExportableElement(node, this.checker, this.exportAssignment);
		result.jsdoc = utils_ts.getJSDocForNode(node);
		result.kind = model.TypeKind.ENUM;
		result.exported = utils_ts.isExported(node);
		result.exportedAsDefault = utils_ts.isExportedAsDefault(node, this.checker, this.exportAssignment);

		const flags = ts.getCombinedModifierFlags(node);
		const isConst = utils.testFlag(flags, ts.ModifierFlags.Const);
		if (isConst) {
			const valueTypes = utils_ts.getValueTypesOfEnum(node, this.checker);
			const singleValueType = valueTypes.size === 1 ? valueTypes.values().next().value : undefined;
			if (valueTypes.size === 0) {
				// in TypeScript, const enums are number-based by default:
				result.primitiveBased = model.PrimitiveBasedKind.NUMBER_BASED;
			} else if (singleValueType == 'string') {
				result.primitiveBased = model.PrimitiveBasedKind.STRING_BASED;
			} else if (singleValueType == 'number') {
				result.primitiveBased = model.PrimitiveBasedKind.NUMBER_BASED;
			} else {
				// use @StringBased enum in error case
				result.primitiveBased = model.PrimitiveBasedKind.STRING_BASED;
				this.createErrorForNode("unsupported value types in const enum: " + Array.from(valueTypes).join(", "), node);
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
			result.jsdoc = utils_ts.getJSDocForNode(lit);
			if (isConst) {
				result.value = this.checker.getConstantValue(lit);
			}
			results.push(result);
		}
		return results;
	}

	private convertInterface(node: ts.InterfaceDeclaration): model.Type {
		const [result, isNew] = this.getOrCreateN4jsType(node);
		if (result.kind === undefined) { // classes must not be changed to interfaces!
			result.kind = model.TypeKind.INTERFACE;
		}
		result.defSiteStructural = true;
		this.convertHeritageClauses(node, result);
		result.members.push(...this.convertMembers(node));
		result.exported = utils_ts.isExported(node);
		result.exportedAsDefault = utils_ts.isExportedAsDefault(node, this.checker, this.exportAssignment);
		return isNew ? result : undefined;
	}

	private convertClass(node: ts.ClassDeclaration): model.Type {
		const [result, isNew] = this.getOrCreateN4jsType(node);
		result.kind = model.TypeKind.CLASS; // interfaces will be changed to classes!
		result.defSiteStructural = true;
		this.convertHeritageClauses(node, result);
		result.members.push(...this.convertMembers(node));
		result.exported = utils_ts.isExported(node);
		result.exportedAsDefault = utils_ts.isExportedAsDefault(node, this.checker, this.exportAssignment);
		return isNew ? result : undefined;
	}

	/**
	 * Creates a new model.Type or returns an already existing type (if one exists). The latter occurs only
	 * in case of declaration merging.
	 *
	 * Since this method is invoked (only) from methods #convertInterface() and #convertClass(), an already
	 * existing type returned by this method might be an interface or a class.
	 */
	private getOrCreateN4jsType(node: ts.InterfaceDeclaration | ts.ClassDeclaration): [model.Type, boolean] {
		const sym = this.checker.getSymbolAtLocation(node.name);
		// 1) actually get/create the type
		let result = this.convertedTypes.get(sym);
		let isNew = false;
		if (!result) {
			result = new model.Type();
			result.name = utils_ts.getLocalNameOfExportableElement(node, this.checker, this.exportAssignment);
			this.convertedTypes.set(sym, result);
			isNew = true;
		}
		// 2) perform some updates/initializations that apply to all types
		// 2.a) type parameters
		// (in case of declaration merging, TypeScript enforces each declaration to
		// have the identical type paramters (even names must be equal), so it is ok to do
		// the following just once based on the first declaration)
		if (isNew) {
			result.typeParams.push(...this.convertTypeParameters(node));
		}
		// 2.b) JSDoc
		// (we do not merge JSDoc across declarations; instead, we use the JSDoc of the first
		// declaration that actually has a JSDoc)
		if (!result.jsdoc) {
			result.jsdoc = utils_ts.getJSDocForNode(node);
		}
		return [result, isNew];
	}

	private convertTypeParameters(node: ts.ClassDeclaration | ts.InterfaceDeclaration | ts.TypeAliasDeclaration | ts.SignatureDeclaration | ts.ConstructSignatureDeclaration | ts.IndexSignatureDeclaration | ts.FunctionTypeNode): model.TypeParameter[] {
		const result = [] as model.TypeParameter[];
		for (const typeParam of node.typeParameters ?? []) {
			const n4jsTypeParam = this.convertTypeParameter(typeParam);
			result.push(n4jsTypeParam);
		}
		return result;
	}

	private convertTypeParameter(node: ts.TypeParameterDeclaration): model.TypeParameter {
		const result = new model.TypeParameter();
		result.name = node.name.text;
		if (node.default) {
			result.defaultArgument = this.convertTypeReference(node.default);
		}
		return result;
	}

	private convertHeritageClauses(node: ts.InterfaceDeclaration | ts.ClassDeclaration, type: model.Type) {
		for (const clause of node.heritageClauses ?? []) {
			const n4jsTypeRefs = [];
			for (const expr of clause.types) {
				const n4jsTypeRef = this.convertExpressionWithTypeArguments(expr);
				if (n4jsTypeRef) {
					n4jsTypeRefs.push(n4jsTypeRef);
				} else {
					this.createErrorForNode("unable to convert type reference in heritage clause", expr);
				}
			}
			if (n4jsTypeRefs.length > 0) {
				switch (clause.token) {
					case ts.SyntaxKind.ExtendsKeyword:
						type.extends.push(...n4jsTypeRefs);
						break;
					case ts.SyntaxKind.ImplementsKeyword:
						type.implements.push(...n4jsTypeRefs);
						break;
					default:
						this.createErrorForNode("unsupported keyword in heritage clause: " + clause.token, clause);
						break;
				}
			}
		}
	}

	private convertMembers(node: ts.InterfaceDeclaration | ts.ClassDeclaration): model.Member[] {
		const result = [] as model.Member[];
		const sourceFile = node.getSourceFile();
		const sym = this.checker.getSymbolAtLocation(node.name);
		for (const m of node.members) {
			const n4jsMember = this.convertMember(sourceFile, m, utils_ts.isStatic(m), sym);
			if (n4jsMember !== undefined) {
				result.push(n4jsMember);
			}
		}
		return result;
	}

	private convertMembersOfObjectType(node: ts.TypeLiteralNode): model.Member[] {
		const result = [] as model.Member[];
		const sourceFile = node.getSourceFile();
		for (const m of node.members) {
			const n4jsMember = this.convertMember(sourceFile, m, false, undefined);
			if (n4jsMember !== undefined) {
				result.push(n4jsMember);
			}
		}
		return result;
	}

	private convertMember(sourceFile: ts.SourceFile, node: ts.ClassElement | ts.TypeElement, isStatic: boolean, symContainingClassifier?: ts.Symbol): model.Member | undefined {
		const symMember = (node.name
			? this.checker.getSymbolAtLocation(node.name)
			: this.checker.getSymbolAtLocation(node)) ?? (node as any).symbol as ts.Symbol;

		let firstDeclFromCurrentFile = symMember.declarations?.find(decl => decl?.getSourceFile() === sourceFile);
		if (firstDeclFromCurrentFile !== node) {
			// we were called for the AST node of a signature other than this member's first signature
			// --> ignore this call, because we have handled all signatures in one go when we were
			// called for the AST node of the first signature
			return undefined;
		}

		const result = new model.Member();
		result.jsdoc = utils_ts.getJSDocForNode(node);
		result.accessibility = utils_ts.getAccessibility(node);
		result.isStatic = isStatic;

		if (ts.isTypeParameterDeclaration(node)) {
			// type parameters of the containing classifier appear as members, but they are handled elsewhere
			// -> so ignore them here:
			return undefined;
		}

		if (ts.isConstructorDeclaration(node)) {
			if (!symContainingClassifier) {
				return undefined; // constructor declarations only supported in classifiers
			}
			result.kind = model.MemberKind.CTOR;
			result.signatures = this.convertConstructSignatures(sourceFile, symContainingClassifier);
			return result;
		} else if (ts.isConstructSignatureDeclaration(node)) {
			result.kind = model.MemberKind.CTOR;
			result.signatures = this.convertSignatureDeclarationsInAST(symMember.declarations);
			return result;
		} else if (ts.isCallSignatureDeclaration(node)) {
			result.kind = model.MemberKind.CALLABLE_CTOR;
			result.signatures = this.convertSignatureDeclarationsInAST(symMember.declarations);
			return result;
		} else if (ts.isIndexSignatureDeclaration(node)) {
			result.kind = model.MemberKind.INDEX_SIGNATURE;
			result.signatures = [ this.convertIndexSignatureDeclarationInAST(node) ];
			return result;
		}

		result.name = symMember.getName();
		if (this.isIgnoredMember(sourceFile, symContainingClassifier, symMember)) {
			return undefined;
		}

		const isReadonly = utils_ts.isReadonly(node);
		if ((!isReadonly && ts.isPropertyDeclaration(node))
				|| (!isReadonly && ts.isPropertySignature(node))) {
			result.kind = model.MemberKind.FIELD;
			result.isOptional = !!node.questionToken;
			result.type = this.convertTypeReferenceOfTypedSymbol(symMember);
		} else if (ts.isGetAccessorDeclaration(node)
				|| (isReadonly && ts.isPropertyDeclaration(node))
				|| (isReadonly && ts.isPropertySignature(node))) {
			result.kind = model.MemberKind.GETTER;
			result.type = this.convertTypeReferenceOfTypedSymbol(symMember);
		} else if (ts.isSetAccessorDeclaration(node)) {
			result.kind = model.MemberKind.SETTER;
			result.type = this.convertTypeReferenceOfTypedDeclaration(node.parameters[0]);
		} else if (ts.isMethodDeclaration(node)
				|| ts.isMethodSignature(node)) {
			const sigs = this.convertCallSignatures(sourceFile, symMember, symContainingClassifier);
			result.kind = model.MemberKind.METHOD;
			result.isOptional = !!node.questionToken;
			result.signatures = sigs;
		} else {
			this.createErrorForUnsupportedNode(node, "member");
			return undefined;
		}
		return result;
	}

	private convertConstructSignatures(sourceFile: ts.SourceFile, somethingWithCtors: ts.Symbol): model.Signature[] {
		const type = this.checker.getTypeOfSymbolAtLocation(somethingWithCtors, somethingWithCtors.valueDeclaration!);
		const constructSigs = type.getConstructSignatures();
		return this.convertSignatures(sourceFile, somethingWithCtors, [...constructSigs]);
	}

	private convertCallSignatures(sourceFile: ts.SourceFile, somethingWithSignatures: ts.Symbol, symContainingClassifier?: ts.Symbol): model.Signature[] {
		const type = this.checker.getTypeOfSymbolAtLocation(somethingWithSignatures, somethingWithSignatures.valueDeclaration!);
		const callSigs = type.getCallSignatures();
		return this.convertSignatures(sourceFile, somethingWithSignatures, [...callSigs], symContainingClassifier);
	}

	private convertSignatures(sourceFile: ts.SourceFile, somethingWithSignatures: ts.Symbol, signatures: ts.Signature[], symContainingClassifier?: ts.Symbol,
		allowIgnore: boolean = true): model.Signature[] {

		const results = [] as model.Signature[];
		let sigIndex = -1;
		let didIgnoreSignatures = false;
		for (const sig of signatures) {
			if (sig.declaration?.getSourceFile() !== sourceFile) {
				continue; // ignore declarations from other source files (and do not increment sigIndex for them!)
			}
			++sigIndex;
			if (allowIgnore && symContainingClassifier) {
				if (this.isIgnoredSignature(sourceFile, symContainingClassifier, somethingWithSignatures, sigIndex)) {
					didIgnoreSignatures = true;
					continue;
				}
			}
			const result = new model.Signature();
			if (sig.typeParameters) {
				const sigDecl = sig.declaration;
				if (sigDecl && !ts.isJSDocSignature(sigDecl)) { // note: there is no "ts.is...()" method for ts.SignatureDeclaration!
					result.typeParams.push(...this.convertTypeParameters(sigDecl));
				}
			}
			result.parameters = sig.getParameters().map(param => this.convertParameter(param));
			result.returnType = this.convertTypeReferenceOfTypedDeclaration(sig.declaration);
			results.push(result);
		}
		if (allowIgnore && didIgnoreSignatures && results.length === 0) {
			// ignoring one or more signatures led to all signatures being ignored, which is illegal
			// --> try again with ignore functionality being disabled:
			return this.convertSignatures(sourceFile, somethingWithSignatures, signatures, symContainingClassifier, false);
		}
		return results;
	}

	private convertSignatureDeclarationsInAST(decls: ts.Declaration[]): model.Signature[] {
		const results = [] as model.Signature[];
		for (const decl of decls) {
			const isConstructSigDecl = ts.isConstructSignatureDeclaration(decl);
			if (isConstructSigDecl
				|| ts.isCallSignatureDeclaration(decl)
				|| ts.isIndexSignatureDeclaration(decl)
				|| ts.isFunctionTypeNode(decl)) {
				const result = new model.Signature();
				if (decl.typeParameters) {
					// is parameterization for this kind of member supported on N4JS side?
					const typeParamsSupported = !ts.isCallSignatureDeclaration(decl);
					if (typeParamsSupported) {
						// yes
						result.typeParams.push(...this.convertTypeParameters(decl));
					} else {
						// no, type parameters are not supported on N4JS side for this member,
						// so in the signature of this member we have to suppress all references to these type parameters:
						this.suppressTypes([...decl.typeParameters]);
					}
				}
				for (const param of decl.parameters) {
					const paramSym = this.checker.getSymbolAtLocation(param.name);
					if (paramSym) {
						result.parameters.push(this.convertParameter(paramSym));
					}
				}
				if (!isConstructSigDecl && decl.type) {
					result.returnType = this.convertTypeReference(decl.type);
				}
				results.push(result);
			}
		}
		return results;
	}

	private convertIndexSignatureDeclarationInAST(decl: ts.IndexSignatureDeclaration): model.Signature {
		const result = new model.Signature();
		const param = decl.parameters[0];
		if (param) {
			const paramSym = this.checker.getSymbolAtLocation(param.name);
			if (paramSym) {
				result.parameters.push(this.convertParameter(paramSym));
			}
		}
		result.returnType = this.convertTypeReference(decl.type);
		return result;
	}

	private convertParameter(param: ts.Symbol): model.Parameter {
		const result = new model.Parameter();
		result.name = param.getName();
		result.type = this.convertTypeReferenceOfTypedSymbol(param);
		if (param.declarations && param.declarations[0] && param.declarations[0].kind == ts.SyntaxKind.Parameter) {
			let paramDecl = param.declarations[0] as ts.ParameterDeclaration;
			result.isOptional = this.checker.isOptionalParameter(paramDecl);
			result.isVariadic = !!paramDecl.dotDotDotToken;
		}
		if (result.isVariadic
			&& result.type?.kind === model.TypeRefKind.NAMED
			&& result.type?.targetTypeName === "Array"
			&& result.type?.targetTypeArgs?.length > 0) {
			// "(...args: string[]):void" must be turned into
			// "(...args: string):void" on N4JS side:
			result.type = result.type.targetTypeArgs[0];
		}
		return result;
	}

	private convertTypeAlias(node: ts.TypeAliasDeclaration): model.Type {
		const result = new model.Type();
		result.kind = model.TypeKind.TYPE_ALIAS;
		result.name = utils_ts.getLocalNameOfExportableElement(node, this.checker, this.exportAssignment);
		result.jsdoc = utils_ts.getJSDocForNode(node);
		result.typeParams.push(...this.convertTypeParameters(node));
		result.exported = utils_ts.isExported(node);
		result.exportedAsDefault = utils_ts.isExportedAsDefault(node, this.checker, this.exportAssignment);
		result.aliasedType = this.convertTypeReference(node.type);
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
	private convertExpressionWithTypeArguments(node: ts.ExpressionWithTypeArguments): model.TypeRef {
		const type = this.checker.getTypeAtLocation(node.expression);
		if (this.suppressedTypes.has(type)) {
			return model.createAnyPlus();
		}
		const result = new model.TypeRef();
		result.kind = model.TypeRefKind.NAMED;
		result.targetTypeName = node.expression.getText().trim();
		if (node.typeArguments) {
			for (const typeArg of node.typeArguments) {
				result.targetTypeArgs.push(this.convertTypeReference(typeArg) ?? model.createAnyPlus());
			}
		}
		return result;
	}
	private convertTypeReference(node?: ts.TypeNode): model.TypeRef {
		if (!node) {
			return undefined;
		}
		const kind = node.kind;
		const sourceStr = node.getText().trim();
		const result = new model.TypeRef();

		// search typescript.js for "function isTypeNodeKind(kind)" to see a list of possible kind values:
		if (kind === ts.SyntaxKind.AnyKeyword
			|| kind === ts.SyntaxKind.BooleanKeyword
			|| kind === ts.SyntaxKind.NumberKeyword
			|| kind === ts.SyntaxKind.StringKeyword
			|| kind === ts.SyntaxKind.SymbolKeyword
			|| kind === ts.SyntaxKind.VoidKeyword
			|| kind === ts.SyntaxKind.UndefinedKeyword) {
			// type keyword supported by N4JS
			result.kind = model.TypeRefKind.NAMED;
			result.targetTypeName = sourceStr;
		} else if (kind === ts.SyntaxKind.ObjectKeyword) {
			// type keyword NOT supported by N4JS, but it can be converted
			result.kind = model.TypeRefKind.NAMED;
			result.targetTypeName = "Object";
		} else if (kind === ts.SyntaxKind.NullKeyword
			|| kind === ts.SyntaxKind.NeverKeyword
			|| kind === ts.SyntaxKind.UnknownKeyword
			|| kind === ts.SyntaxKind.BigIntKeyword) {
			// type keyword NOT supported by N4JS -> replace by "any+"
			return model.createAnyPlus();
		} else if (ts.isTypeReferenceNode(node)) {
			// reference to another type (except for those types that are represented as keyword, see above)
			const type = this.checker.getTypeAtLocation(node.typeName);
			if (this.suppressedTypes.has(type)) {
				return model.createAnyPlus();
			}
			result.kind = model.TypeRefKind.NAMED;
			result.targetTypeName = node.typeName.getText().trim();
			if (node.typeArguments) {
				for (const typeArg of node.typeArguments) {
					result.targetTypeArgs.push(this.convertTypeReference(typeArg) ?? model.createAnyPlus());
				}
			}
		} else if (ts.isLiteralTypeNode(node)) {
			const literal = node.literal;
			if (literal.kind === ts.SyntaxKind.NullKeyword) {
				// not supported on N4JS side
				return model.createAnyPlus();
			} else if (literal.kind === ts.SyntaxKind.FalseKeyword) {
				result.kind = model.TypeRefKind.LITERAL;
			} else if (literal.kind === ts.SyntaxKind.TrueKeyword) {
				result.kind = model.TypeRefKind.LITERAL;
			} else if (ts.isLiteralExpression(literal)) {
				result.kind = model.TypeRefKind.LITERAL;
			} else if (ts.isPrefixUnaryExpression(literal)) {
				result.kind = model.TypeRefKind.LITERAL;
			} else {
				this.createErrorForUnsupportedNode(literal, "literal in LiteralTypeNode");
				return model.createAnyPlus();
			}
		} else if (ts.isFunctionTypeNode(node)) {
			result.kind = model.TypeRefKind.FUNCTION;
			result.signature = this.convertSignatureDeclarationsInAST([node])[0];
			const param0 = result.signature?.parameters?.[0];
			if (param0 && param0.name === "this") {
				result.signature.parameters.splice(0, 1);
				const annThis = new model.Annotation("@This");
				annThis.args.push(param0.type ?? model.createAnyPlus());
				result.annotations.push(annThis);
			}
		} else if (ts.isArrayTypeNode(node)) {
			result.kind = model.TypeRefKind.NAMED;
			result.targetTypeName = "Array";
			let elemType = node.elementType;
			if (ts.isParenthesizedTypeNode(elemType)) {
				elemType = elemType.type;
			}
			result.targetTypeArgs.push(this.convertTypeReference(elemType) ?? model.createAnyPlus());
		} else if (ts.isTypeLiteralNode(node)) {
			// object type syntax, e.g. "let x: { prop: string };"
			result.kind = model.TypeRefKind.OBJECT;
			result.members.push(...this.convertMembersOfObjectType(node));
		} else if (ts.isThisTypeNode(node)) {
			result.kind = model.TypeRefKind.THIS;
		} else if (ts.isUnionTypeNode(node)
			|| ts.isIntersectionTypeNode(node)) {
			const isUnion = ts.isUnionTypeNode(node);
			const n4jsMemberTypeRefs = [];
			for (const memberNode of node.types) {
				if (isUnion) {
					let memberKind = ts.isLiteralTypeNode(memberNode) ? memberNode.literal.kind : memberNode.kind;
					if (memberKind === ts.SyntaxKind.NullKeyword || memberKind === ts.SyntaxKind.UndefinedKeyword) {
						continue; // avoid 'null', 'undefined' in unions
					}
				}
				const n4jsMemberTypeRef = this.convertTypeReference(memberNode);
				if (n4jsMemberTypeRef) {
					n4jsMemberTypeRefs.push(n4jsMemberTypeRef);
				}
			}
			if (n4jsMemberTypeRefs.length === 0) {
				return model.createUndefined();
			} else if (n4jsMemberTypeRefs.length === 1) {
				return n4jsMemberTypeRefs[0];
			}
			result.kind = isUnion ? model.TypeRefKind.UNION : model.TypeRefKind.INTERSECTION;
			result.composedTypeRefs.push(...n4jsMemberTypeRefs);
		} else if (ts.isTupleTypeNode(node)) {
			result.kind = model.TypeRefKind.TUPLE;
			result.targetTypeName = "Array";
			for (let elemTypeNode of node.elements) {
				if (ts.isNamedTupleMember(elemTypeNode)) {
					elemTypeNode = elemTypeNode.type;
				}
				const n4jsElemTypeRef = this.convertTypeReference(elemTypeNode);
				result.targetTypeArgs.push(n4jsElemTypeRef ?? model.createAnyPlus());
			}
		// } else if (ts.isTypeQueryNode(node)) { // unsupported (for now)
		} else if (ts.isParenthesizedTypeNode(node)) {
			result.kind = model.TypeRefKind.PARENTHESES;
			result.parenthesizedTypeRef = this.convertTypeReference(node.type);
		} else if (ts.isTypeOperatorNode(node)) {
			let op: model.TypeRefOperator = undefined;
			switch(node.operator) {
				case ts.SyntaxKind.KeyOfKeyword:
					op = model.TypeRefOperator.KEYOF;
					break;
				case ts.SyntaxKind.UniqueKeyword:
					op = model.TypeRefOperator.UNIQUE;
					break;
				case ts.SyntaxKind.ReadonlyKeyword:
					op = model.TypeRefOperator.READONLY;
					break;
				default:
					this.createErrorForNode("unsupported type operator: " + node.operator, node);
			}
			const resultNested = this.convertTypeReference(node.type);
			if (resultNested && op) {
				resultNested.tsOperators.push(op);
			}
			return resultNested;
		} else if (ts.isTypePredicateNode(node)) {
			// e.g. "this is Cls"
			result.kind = model.TypeRefKind.TYPE_PREDICATE;
			this.createWarningForNode("type predicate will be replaced by boolean", node);
		} else if (ts.isTypeQueryNode(node)) {
			// e.g. "typeof x" (with x being a variable, etc.)
			result.kind = model.TypeRefKind.TYPE_QUERY;
			this.createWarningForNode("type query will be replaced by any+", node);
		} else if (ts.isIndexedAccessTypeNode(node)) {
			// e.g. "SomeType['someProperty']"
			result.kind = model.TypeRefKind.INDEXED_ACCESS_TYPE;
			this.createWarningForNode("indexed access type will be replaced by any+", node);
		} else if (ts.isMappedTypeNode(node)) {
			// e.g. { [P in K]: T[P]; }
			result.kind = model.TypeRefKind.MAPPED_TYPE;
			this.createWarningForNode("mapped type will be replaced by Object+", node);
		} else {
			this.createErrorForUnsupportedNode(node, "TypeNode (in #convertTypeReference())")
			return model.createAnyPlus();
		}

		result.tsSourceString = sourceStr;
		return result;
	}

	private isIgnoredTopLevelElement(elemDeclNode: ts.NamedDeclaration) {
		if (!this.ignorePredicate) {
			return false;
		}
		const filePath = utils_ts.getFilePath(elemDeclNode);
		const elementName = utils_ts.getLocalNameOfExportableElement(elemDeclNode as ts.NamedDeclaration, this.checker);
		return this.ignorePredicate(filePath, elementName);
	}

	private isIgnoredMember(sourceFile: ts.SourceFile, classifierSym: ts.Symbol | undefined, memberSym: ts.Symbol) {
		if (!this.ignorePredicate) {
			return false;
		}
		return this.ignorePredicate(sourceFile.fileName, classifierSym?.name, memberSym.name);
	}

	private isIgnoredSignature(sourceFile: ts.SourceFile, classifierSym: ts.Symbol, memberSym: ts.Symbol, signatureIndex: number) {
		if (!this.ignorePredicate) {
			return false;
		}
		return this.ignorePredicate(sourceFile.fileName, classifierSym.name, memberSym.name, signatureIndex);
	}

	private suppressTypes(nodes: ts.NamedDeclaration[]): ts.Type[] {
		const addedTypes = [];
		for (const node of nodes) {
			const type = this.checker.getTypeAtLocation(node.name);
			if (type) {
				this.suppressedTypes.add(type);
				addedTypes.push(type);
			}
		}
		return addedTypes;
	}

	private createWarningForNode(msg: string, node: ts.Node) {
		this.createIssueForNode(utils.IssueKind.WARNING, msg, node);
	}

	private createErrorForUnsupportedNode(node: ts.Node, superKind: string = "node") {
		const nodeKindStr = ts.SyntaxKind[node.kind];
		this.createErrorForNode("unsupported kind of " + superKind + ": " + nodeKindStr, node);
	}

	private createErrorForNode(msg: string, node: ts.Node) {
		this.createIssueForNode(utils.IssueKind.ERROR, msg, node);
	}

	private createIssueForNode(kind: utils.IssueKind, msg: string, node: ts.Node) {
		const contextStr = utils_ts.getContextForNode(node, this.checker);
		const offendingCode = utils_ts.getSourceCodeForNode(node);
		const error = utils.issue(kind, msg + (contextStr ? " (in " + contextStr + ")" : "") + "\n" + offendingCode);
		this.issues.push(error);
	}
}

function inArrayIfDefined<T>(value: T): T[] {
	return value !== undefined && value !== null ? [ value ] : [];
}
