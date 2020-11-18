import * as ts from "typescript";
import * as model from "./model";
import * as utils from "./utils";
import * as utils_ts from "./utils_ts";

export function convertScript(program: ts.Program, sourceFilePath: string): model.Script {
	return new Converter(program).convertScript(sourceFilePath);
}

class Converter {
	readonly program: ts.Program;
	readonly checker: ts.TypeChecker;

	readonly issues: utils.Issue[] = [];

	constructor(program: ts.Program) {
		this.program = program;
		this.checker = program.getTypeChecker();
	}

	convertScript(sourceFilePath: string): model.Script {
		this.issues.length = 0;

		const result = new model.Script();
		const sourceFile = this.program.getSourceFile(sourceFilePath);
		result.mode = utils_ts.getDTSMode(sourceFile);
		sourceFile.forEachChild(node => {
			const elem = this.convertNode(node);
			if (elem !== undefined) {
				result.topLevelElements.push(...elem);
			}
		});
		result.issues.push(...this.issues);

		return result;
	}

	convertNode(node: ts.Node): model.ExportableElement[] {
		if (node.kind === ts.SyntaxKind.EndOfFileToken) {
			return undefined; // ignore
		}
		const typeKind = utils_ts.getTypeKind(node);
		if (ts.isImportDeclaration(node)) {
			return undefined; // TODO!!!
		} else if (ts.isExportAssignment(node) && !node.isExportEquals) { // default exports
			return undefined; // TODO!!!
		} else if (ts.isVariableDeclarationList(node)) {
			return this.convertVariableDeclList(node); // TODO can a VariableDeclarationList even appear here?
		} else if (ts.isFunctionDeclaration(node)) {
			return [ this.convertFunction(node) ];
		} else if (typeKind && typeKind !== 'unknown') {
			return [ this.convertType(node) ];
		} else if (node.kind === ts.SyntaxKind.FirstStatement) {
			const children = utils_ts.getAllChildNodes(node);
			if (children.length === 2
				&& (children[0].kind === ts.SyntaxKind.ExportKeyword
					|| children[0].kind === ts.SyntaxKind.DeclareKeyword)
				&& children[1].kind === ts.SyntaxKind.VariableDeclarationList) {
				// something like "export var someStr: string, someNum: number;"
				return this.convertVariableDeclList(children[1] as ts.VariableDeclarationList);
			}
		}
		// create issue for unsupported node
		const nodeKindStr = ts.SyntaxKind[node.kind];
		const offendingCode = utils_ts.getSourceCodeForNode(node, "    |");
		const error = utils.error("unsupported construct " + nodeKindStr + ":\n" + offendingCode);
		this.issues.push(error);
		return undefined;
	}

	convertVariableDeclList(node: ts.VariableDeclarationList): model.Variable[] {
		const keyword = utils_ts.getVarDeclKeyword(node);
		const result = [] as model.Variable[];
		for (const varDecl of node.declarations) {
			result.push(this.convertVariable(varDecl, keyword));
		}
		return result;
	}

	convertVariable(node: ts.VariableDeclaration, keyword: 'var' | 'let' | 'const'): model.Variable {
		const sym = this.checker.getSymbolAtLocation(node.name);
		const varName = sym.getName();
		const varType = this.checker.getTypeOfSymbolAtLocation(sym, sym.valueDeclaration!);

		const result = new model.Variable();
		result.name = varName;
		result.keyword = keyword;
		result.typeStr = this.checker.typeToString(varType);
		result.exported = utils_ts.isExported(node);
		return result;
	}

	convertFunction(node: ts.FunctionDeclaration): model.Function {
		const sym = this.checker.getSymbolAtLocation(node.name);
		const funName = sym.getName();
		const funSigs = this.convertCallSignatures(sym);

		const result = new model.Function();
		result.name = funName;
		result.signatures = funSigs;
		result.exported = utils_ts.isExported(node);
		return result;
	}

	convertType(node: ts.Node): model.Type {
		const name = (node as ts.NamedDeclaration).name;
		const sym = this.checker.getSymbolAtLocation(name);

		let result = new model.Type();
		result.name = sym.getName();
		result.kind = utils_ts.getTypeKind(sym.declarations[0]);
		result.exported = utils_ts.isExported(node);

		sym.members?.forEach((member, name) => {
			result.members.push(this.convertMember(member));
		});

		return result;
	}

	convertMember(member: ts.Symbol): model.Member {
		const result = new model.Member();

		const memberName = member.getName();
		if (memberName == '__constructor') {
			result.kind = 'ctor';
			return result;
		}
		result.name = memberName;

		const sigs = this.convertCallSignatures(member);
		if (sigs.length > 0) {
			result.kind = 'method';
			result.signatures = sigs;
			return result;
		}

		result.kind = 'field';
		const memberType = this.checker.getTypeOfSymbolAtLocation(member, member.valueDeclaration!);
		result.typeStr = this.checker.typeToString(memberType);
		return result;
	}

	convertCallSignatures(somethingWithSignatures: ts.Symbol): model.Signature[] {
		const type = this.checker.getTypeOfSymbolAtLocation(somethingWithSignatures, somethingWithSignatures.valueDeclaration!);
		const sigs = [] as model.Signature[];
		for (const callSig of type.getCallSignatures()) {
			const sig = new model.Signature();
			sig.parameters = callSig.getParameters().map(param => this.convertParameter(param));
			sig.returnTypeStr = this.checker.typeToString(callSig.getReturnType());
			sigs.push(sig);
		}
		return sigs;
	}

	convertParameter(param: ts.Symbol): model.Parameter {
		const paramType = this.checker.getTypeOfSymbolAtLocation(param, param.valueDeclaration!);
		const result = new model.Parameter();
		result.name = param.getName();
		result.typeStr = this.checker.typeToString(paramType);
		return result;
	}
}
