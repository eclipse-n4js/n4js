import { CodeBuffer } from "./CodeBuffer";
import * as utils from "./utils";

export enum DTSMode {
	NONE,
	MODULE,
	LEGACY
}

export abstract class NamedElement {
	name: string;
}

export abstract class ExportableElement extends NamedElement {
	exported: boolean;
}

export class Parameter extends NamedElement {
	typeStr: string = 'any+';
}

export class Signature {
	parameters: Parameter[] = [];
	returnTypeStr: string = 'any+';
}

export class Member extends NamedElement {
	kind: 'ctor' | 'field' | 'getter' | 'setter' | 'method';
	typeStr?: string;
	signatures?: Signature[];
}

export class Variable extends ExportableElement {
	keyword: 'var' | 'let' | 'const';
	typeStr: string = 'any+';
}

export class Function extends ExportableElement {
	signatures: Signature[] = [];
}

export class Type extends ExportableElement {
	kind: 'class' | 'interface' | 'enum' | 'alias' | 'unknown' = 'unknown';
	members: Member[] = [];
}

export class Script {
	mode: DTSMode = DTSMode.NONE;
	topLevelElements: ExportableElement[] = [];
	issues: utils.Issue[] = [];
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
		script.topLevelElements.forEach((elem, idx) => {
			if (idx > 0) {
				buff.pushln();
				buff.pushln();
			}
			this.emitExportableElement(elem);
		});
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
		buff.pushln(type.kind, ' ', type.name, ' {');
		buff.indent();
		for (const m of type.members) {
			this.emitMember(m);
			buff.pushln();
		}
		buff.undent();
		buff.push('}');
	}

	emitMember(member: Member) {
		const buff = this.buff;
		switch(member.kind) {
			case 'field':
				buff.push(member.name, ': ', member.typeStr, ';');
				break;
			case 'method':
				buff.push(member.name);
				this.emitSignature(member.signatures[0]);
				buff.push(';');
		}
	}

	emitSignature(sig: Signature) {
		const buff = this.buff;
		buff.push('(');
		let needSep = false;
		for (const param of sig.parameters) {
			if (needSep) {
				buff.push(', ');
			}
			this.emitParameter(param);
			needSep = true;
		}
		buff.push(')');
		buff.push(': ', sig.returnTypeStr);
	}

	emitParameter(param: Parameter) {
		const buff = this.buff;
		buff.push(param.name, ': ', param.typeStr);
	}
}
