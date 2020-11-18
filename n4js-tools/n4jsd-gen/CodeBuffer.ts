
export class CodeBuffer {
	private buff: string[] = [];
	private indentLevel: number = 0;
	private requiresIndent: boolean = true;

	indent() {
		this.indentLevel++;
	}
	undent() {
		if(this.indentLevel > 0) {
			this.indentLevel--;
		}
	}

	lineBreak() {
		this.buff.push('\n');
		this.requiresIndent = true;
	}

	pushIndent() {
		for (let i=0; i< this.indentLevel; i++) {
			this.buff.push('\t');
		}
		this.requiresIndent = false;
	}

	push(...strs: string[]) {
		if (this.requiresIndent) {
			this.pushIndent();
		}
		this.buff.push(...strs);
	}

	pushln(...strs: string[]) {
		this.push(...strs);
		this.lineBreak();
	}

	getCode(): string {
		return this.buff.join('');
	}
}
