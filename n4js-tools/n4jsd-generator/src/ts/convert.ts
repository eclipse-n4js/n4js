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

export type IgnorePredicate = (filePath: string, elementName: string) => boolean;

export class Converter {
	private readonly projectPath?: string;
	private readonly ignorePredicate?: IgnorePredicate;
	/** True iff option '--runtime-libs' was given on the command line. */
	private readonly runtimeLibs: boolean;

	private readonly program: ts.Program;
	private readonly checker: ts.TypeChecker;

	private exportAssignment: ts.ExportAssignment;
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
		this.issues.length = 0;

		const sourceFile = this.program.getSourceFile(sourceFilePath);
		const dtsMode = utils_ts.getDTSMode(sourceFile);

		if (dtsMode == model.DTSMode.LEGACY) {
			this.exportAssignment = utils_ts.getExportEquals(sourceFile);
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
			return this.isIgnored(node) ? [] : [ this.convertFunction(node) ];
		} else if (ts.isEnumDeclaration(node)) {
			return this.isIgnored(node) ? [] : [ this.convertEnum(node) ];
		} else if (ts.isInterfaceDeclaration(node)) {
			return this.isIgnored(node) ? [] : [ this.convertInterface(node) ];
		} else if (ts.isClassDeclaration(node)) {
			return this.isIgnored(node) ? [] : [ this.convertClass(node) ];
		} else if (ts.isTypeAliasDeclaration(node)) {
			return this.isIgnored(node) ? [] : [ this.convertTypeAlias(node) ];
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
			if (!this.exportAssignment) {
				return []; // FIXME!!!!! do not merge this to master
			}
			if (this.isIgnored(node)) {
				return [];
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

	private convertVariableDeclList(node: ts.VariableDeclarationList): model.Variable[] {
		const keyword = utils_ts.getVarDeclKeyword(node);
		const result = [] as model.Variable[];
		for (const varDecl of node.declarations) {
			if (!this.isIgnored(varDecl)) {
				result.push(this.convertVariable(varDecl, keyword));
			}
		}
		return result;
	}

	private convertVariable(node: ts.VariableDeclaration, keyword: model.VariableKeyword): model.Variable {
		const result = new model.Variable();
		result.name = utils_ts.getLocalNameOfExportableElement(node, this.checker, this.exportAssignment);
		result.keyword = keyword;
		result.type = this.convertTypeReference(node.type);
		result.exported = utils_ts.isExported(node);
		result.exportedAsDefault = utils_ts.isExportedAsDefault(node, this.checker, this.exportAssignment);
		return result;
	}

	private convertFunction(node: ts.FunctionDeclaration): model.Function {
		const sym = this.checker.getSymbolAtLocation(node.name);
		const funSigs = this.convertCallSignatures(sym);
		
		const result = new model.Function();
		result.name = utils_ts.getLocalNameOfExportableElement(node, this.checker, this.exportAssignment);
		const expOfModule = this.checker.getExportSymbolOfSymbol(sym);
		
		result.signatures = funSigs;
		result.exported = utils_ts.isExported(node);
		result.exportedAsDefault = utils_ts.isExportedAsDefault(node, this.checker, this.exportAssignment);
		return result;
	}

	private convertEnum(node: ts.EnumDeclaration): model.Type {
		let result = new model.Type();
		result.name = utils_ts.getLocalNameOfExportableElement(node, this.checker, this.exportAssignment);
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
			if (isConst) {
				result.value = this.checker.getConstantValue(lit);
			}
			results.push(result);
		}
		return results;
	}

	private convertInterface(node: ts.InterfaceDeclaration): model.Type {
		const result = new model.Type();
		result.name = utils_ts.getLocalNameOfExportableElement(node, this.checker, this.exportAssignment);
		result.kind = model.TypeKind.INTERFACE;
		result.defSiteStructural = true;
		result.typeParams.push(...this.convertTypeParameters(node));
		result.members.push(...this.convertMembers(node));
		result.exported = utils_ts.isExported(node);
		result.exportedAsDefault = utils_ts.isExportedAsDefault(node, this.checker, this.exportAssignment);
		return result;
	}

	private convertClass(node: ts.ClassDeclaration): model.Type {
		const result = new model.Type();
		result.name = utils_ts.getLocalNameOfExportableElement(node, this.checker, this.exportAssignment);
		result.kind = model.TypeKind.CLASS;
		result.defSiteStructural = true;
		result.typeParams.push(...this.convertTypeParameters(node));
		result.members.push(...this.convertMembers(node));
		result.exported = utils_ts.isExported(node);
		result.exportedAsDefault = utils_ts.isExportedAsDefault(node, this.checker, this.exportAssignment);
		return result;
	}

	private convertTypeParameters(node: ts.NamedDeclaration): string[] {
		const sym = this.checker.getSymbolAtLocation(node.name);
		const result = [];
		sym.members?.forEach((symMember, name) => {
			const representativeNode = symMember.declarations[0] as ts.NamedDeclaration;
			if (ts.isTypeParameterDeclaration(representativeNode)) {
				result.push(symMember.name);
			}
		});
		return result;
	}

	private convertMembers(node: ts.NamedDeclaration): model.Member[] {
		const result = [] as model.Member[];
		const sym = this.checker.getSymbolAtLocation(node.name);
		// instance members
		sym.members?.forEach((symMember, name) => {
			const n4jsMember = this.convertMember(symMember, false, sym);
			if (n4jsMember !== undefined) {
				result.push(n4jsMember);
			}
		});
		// static members
		if (ts.isClassLike(node)) {
			for (const m of node.members) {
				if (!utils_ts.isStatic(m)) {
					continue;
				}
				const n4jsMember = this.convertMemberDeclInAST(m, true, sym);
				if (n4jsMember !== undefined) {
					result.push(n4jsMember);
				}
			}
		}
		return result;
	}

	private convertMembersOfObjectType(node: ts.TypeLiteralNode): model.Member[] {
		const result = [] as model.Member[];
		for (const m of node.members) {
			const n4jsMember = this.convertMemberDeclInAST(m, false, undefined);
			if (n4jsMember !== undefined) {
				result.push(n4jsMember);
			}
		}
		return result;
	}

	private convertMemberDeclInAST(member: ts.ClassElement | ts.TypeElement, isStatic: boolean, symOwner?: ts.Symbol): model.Member | undefined {
		const symMember = this.checker.getSymbolAtLocation(member.name); // FIXME what happens for members that do not have a name???
		return this.convertMember(symMember, isStatic, symOwner);
	}

	private convertMember(symMember: ts.Symbol, isStatic: boolean, symOwner?: ts.Symbol): model.Member | undefined {
		// we need an AST node; in case of overloading there will be several declarations (one per signature)
		// but because relevant properties (kind, accessibility, etc.) will be the same in all cases we can
		// simply use the first one as representative:
		const representativeNode = symMember.declarations[0] as ts.SignatureDeclaration;

		const result = new model.Member();
		result.accessibility = utils_ts.getAccessibility(representativeNode);
		result.isStatic = isStatic;

		if (ts.isTypeParameterDeclaration(representativeNode)) {
			// type parameters of the containing classifier appear as members, but they are handled elsewhere
			// -> so ignore them here:
			return undefined;
		}

		// type parameters of 'symMember'
		for (const typeParam of representativeNode.typeParameters ?? []) {
			const typeParamName = typeParam.name.text;
			if (typeParamName) {
				result.typeParams.push(typeParamName);
			}
		}

		if (ts.isConstructorDeclaration(representativeNode)) {
			if (!symOwner) {
				return undefined; // constructor declarations not supported if owner not given
			}
			result.kind = model.MemberKind.CTOR;
			result.signatures = this.convertConstructSignatures(symOwner);
			return result;
		} else if (ts.isConstructSignatureDeclaration(representativeNode)) {
			result.kind = model.MemberKind.CTOR;
			result.signatures = this.convertSignatureDeclarationsInAST(symMember.declarations);
			return result;
		} else if (ts.isCallSignatureDeclaration(representativeNode)) {
			result.kind = model.MemberKind.CALLABLE_CTOR;
			result.signatures = this.convertSignatureDeclarationsInAST(symMember.declarations);
			return result;
		} else if (ts.isIndexSignatureDeclaration(representativeNode)) {
			result.kind = model.MemberKind.INDEX_SIGNATURE;
			result.signatures = [ this.convertIndexSignatureDeclarationInAST(representativeNode) ];
			return result;
		}

		result.name = symMember.getName();

		const isReadonly = utils_ts.isReadonly(representativeNode);
		if ((!isReadonly && ts.isPropertyDeclaration(representativeNode))
				|| (!isReadonly && ts.isPropertySignature(representativeNode))) {
			result.kind = model.MemberKind.FIELD;
			result.type = this.convertTypeReferenceOfTypedSymbol(symMember);
		} else if (ts.isGetAccessorDeclaration(representativeNode)
				|| (isReadonly && ts.isPropertyDeclaration(representativeNode))
				|| (isReadonly && ts.isPropertySignature(representativeNode))) {
			result.kind = model.MemberKind.GETTER;
			result.type = this.convertTypeReferenceOfTypedSymbol(symMember);
		} else if (ts.isSetAccessorDeclaration(representativeNode)) {
			result.kind = model.MemberKind.SETTER;
			result.type = this.convertTypeReferenceOfTypedDeclaration(representativeNode.parameters[0]);
		} else if (ts.isMethodDeclaration(representativeNode)
				|| ts.isMethodSignature(representativeNode)) {
			const sigs = this.convertCallSignatures(symMember);
			result.kind = model.MemberKind.METHOD;
			result.signatures = sigs;
		} else {
			this.createErrorForUnsupportedNode(representativeNode, "member");
			return undefined;
		}
		return result;
	}

	private convertConstructSignatures(somethingWithCtors: ts.Symbol): model.Signature[] {
		const type = this.checker.getTypeOfSymbolAtLocation(somethingWithCtors, somethingWithCtors.valueDeclaration!);
		const constructSigs = type.getConstructSignatures();
		return this.convertSignatures([...constructSigs]);
	}

	private convertCallSignatures(somethingWithSignatures: ts.Symbol): model.Signature[] {
		const type = this.checker.getTypeOfSymbolAtLocation(somethingWithSignatures, somethingWithSignatures.valueDeclaration!);
		const callSigs = type.getCallSignatures();
		return this.convertSignatures([...callSigs]);
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

	private convertSignatureDeclarationsInAST(decls: ts.Declaration[]): model.Signature[] {
		const results = [] as model.Signature[];
		for (const decl of decls) {
			const isConstructSigDecl = ts.isConstructSignatureDeclaration(decl);
			if (isConstructSigDecl
				|| ts.isCallSignatureDeclaration(decl)
				|| ts.isIndexSignatureDeclaration(decl)
				|| ts.isFunctionTypeNode(decl)) {
				const result = new model.Signature();
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
		result.name = utils_ts.getLocalNameOfExportableElement(node, this.checker, this.exportAssignment);
		result.kind = model.TypeKind.TYPE_ALIAS;
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
			// reference to another type (except those represented as keyword, see above)
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
		} else if (ts.isUnionTypeNode(node)) {
			result.kind = model.TypeRefKind.UNION;
			for (const memberNode of node.types) {
				const memberTypeRef = this.convertTypeReference(memberNode);
				if (memberTypeRef) {
					result.composedTypeRefs.push(memberTypeRef);
				}
			}
		} else if (ts.isIntersectionTypeNode(node)) {
			result.kind = model.TypeRefKind.INTERSECTION;
			for (const memberNode of node.types) {
				const memberTypeRef = this.convertTypeReference(memberNode);
				if (memberTypeRef) {
					result.composedTypeRefs.push(memberTypeRef);
				}
			}
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
			result.kind = model.TypeRefKind.PREDICATE;
			this.createWarningForNode("type predicate will be replaced by any+", node);
		} else if (ts.isMappedTypeNode(node)) {
			// e.g. { [P in K]: T[P]; }
			result.kind = model.TypeRefKind.MAPPED_TYPE;
			this.createWarningForNode("mapped type will be replaced by Object+", node);
		// } else if (ts.isTupleTypeNode(node)) {
		} else {
			this.createErrorForUnsupportedNode(node, "TypeNode (in #convertTypeReference())")
			return model.createAnyPlus();
		}

		result.tsSourceString = sourceStr;
		return result;
	}

	private isIgnored(node: ts.NamedDeclaration) {
		if (!this.ignorePredicate) {
			return false;
		}
		const filePath = utils_ts.getFilePath(node);
		const elementName = utils_ts.getLocalNameOfExportableElement(node as ts.NamedDeclaration, this.checker);
		return this.ignorePredicate?.(filePath, elementName);
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
		const offendingCode = utils_ts.getSourceCodeForNode(node);
		const error = utils.issue(kind, msg + "\n" + offendingCode);
		this.issues.push(error);
	}
}
