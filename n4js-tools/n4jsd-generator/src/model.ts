import { CodeBuffer } from "./CodeBuffer";
import * as utils from "./utils";

export enum DTSMode {
	NONE,
	MODULE,
	LEGACY
}

export enum Accessibility {
	PUBLIC,
	PROTECTED,
	PRIVATE
}

export class Script {
	mode: DTSMode = DTSMode.NONE;
	imports: Import[] = [];
	topLevelElements: ExportableElement[] = [];
	issues: utils.Issue[] = [];
}

export abstract class Import {
	moduleSpecifier: string;
}

export class NamespaceImport extends Import {
	namespaceName: string;
}

export class NamedImport extends Import {
	importedElementName: string;
	aliasName?: string;
}

export abstract class NamedElement {
	name: string;
}

export abstract class ExportableElement extends NamedElement {
	exported: boolean;
}

export class Variable extends ExportableElement {
	keyword: 'var' | 'let' | 'const';
	typeStr: string;
}

export class Function extends ExportableElement {
	signatures: Signature[] = [];
}

export class Type extends ExportableElement {
	kind: 'class' | 'interface' | 'enum' | 'alias';
	defSiteStructural?: boolean;
	members: Member[] = [];
}

export class Member extends NamedElement {
	kind: 'ctor' | 'field' | 'getter' | 'setter' | 'method';
	accessibility: Accessibility;
	typeStr?: string;
	signatures?: Signature[];
}

export class Signature {
	parameters: Parameter[] = [];
	/** Will be undefined iff this signature belongs to a constructor. */
	returnTypeStr?: string;
}

export class Parameter extends NamedElement {
	typeStr: string = 'any+';
}


export function scriptToString(script: Script): string {
    const emitter = new Emitter();
    emitter.emitScript(script);
    return emitter.getCode();
}

class Emitter {

	private buff: CodeBuffer = new CodeBuffer();

	getCode(): string {
		return this.buff.getCode();
	}

	emitScript(script: Script) {
		const buff = this.buff;
		if (script.imports.length > 0) {
			script.imports.forEach((elem, idx) => {
				if (idx > 0) {
					buff.pushln();
				}
				this.emitImport(elem);
			});
			buff.pushln();
			buff.pushln();
		}
		script.topLevelElements.forEach((elem, idx) => {
			if (idx > 0) {
				buff.pushln();
				buff.pushln();
			}
			this.emitExportableElement(elem);
		});
	}

	emitImport(elem: Import) {
		const buff = this.buff;
		buff.push("import ");
		if (elem instanceof NamespaceImport) {
			buff.push("* as ", elem.namespaceName, " ");
		} else if (elem instanceof NamedImport) {
			buff.push("{ ", elem.importedElementName);
			if (elem.aliasName !== undefined) {
				buff.push(" as ", elem.aliasName);
			}
			buff.push(" } ");
		} else {
			throw "unsupported sub-class of Import";
		}
		buff.push("from \"", elem.moduleSpecifier, "\";");
	}

	emitExportableElement(elem: ExportableElement) {
		if (elem instanceof Variable) {
			this.emitVariable(elem);
		} else if (elem instanceof Function) {
			this.emitFunction(elem);
		} else if (elem instanceof Type) {
			this.emitType(elem);
		} else {
			throw "unsupported sub-type of ExportableElement";
		}
	}

	emitVariable(variable: Variable) {
		const buff = this.buff;
		if (variable.exported) {
			buff.push("export ");
		}
		buff.push(variable.keyword);
		buff.push(" ");
		buff.push(variable.name);
		buff.push(": ", variable.typeStr);
		buff.push(";");
	}

	emitFunction(fun: Function) {
		const buff = this.buff;
		if (fun.exported) {
			buff.push("export ");
		}
		buff.push("external ");
		buff.push("function ");
		buff.push(fun.name);
		this.emitSignature(fun.signatures[0]);
		buff.push(";");
	}

	emitType(type: Type) {
		const buff = this.buff;
		if (type.exported) {
			buff.push("export ");
		}
		buff.push("external ");
		buff.push(type.kind, " ");
		if (type.defSiteStructural) {
			buff.push("~");
		}
		buff.pushln(type.name, " {");
		buff.indent();
		for (const m of type.members) {
			this.emitMember(m);
			buff.pushln();
		}
		buff.undent();
		buff.push("}");
	}

	emitMember(member: Member) {
		const buff = this.buff;
		if (member.accessibility !== undefined) {
			this.emitAccessibility(member);
			buff.push(" ");
		}
		switch(member.kind) {
			case 'ctor':
				buff.push("constructor");
				this.emitSignature(member.signatures[0]);
				buff.push(";");
				break;
			case 'field':
				buff.push(member.name, ": ", member.typeStr, ";");
				break;
			case 'getter':
				buff.push("get ", member.name, "(): ", member.typeStr, ";");
				break;
			case 'setter':
				buff.push("set ", member.name, "(", "value: ", member.typeStr, ");");
				break;
			case 'method':
				buff.push(member.name);
				this.emitSignature(member.signatures[0]);
				buff.push(";");
				break;
			default:
				throw "unknown kind of member: " + member.kind;
		}
		if (member.signatures !== undefined && member.signatures.length > 1) {
			buff.push(" // further signatures were omitted");
		}
	}

	emitSignature(sig: Signature) {
		const buff = this.buff;
		buff.push("(");
		let needSep = false;
		for (const param of sig.parameters) {
			if (needSep) {
				buff.push(", ");
			}
			this.emitParameter(param);
			needSep = true;
		}
		buff.push(")");
		if (sig.returnTypeStr !== undefined) {
			buff.push(": ", sig.returnTypeStr);
		}
	}

	emitParameter(param: Parameter) {
		const buff = this.buff;
		buff.push(param.name, ": ", param.typeStr);
	}

	emitAccessibility(member: Member) {
		const buff = this.buff;
		if (member.accessibility === undefined) {
			// ignore
		} else if (member.accessibility == Accessibility.PUBLIC) {
			buff.push("public");
		} else if (member.accessibility == Accessibility.PROTECTED) {
			buff.push("protected");
		} else if (member.accessibility == Accessibility.PRIVATE) {
			buff.push("private");
		} else {
			throw "unkown member accessibility: " + Accessibility[member.accessibility];
		}
	}
}
