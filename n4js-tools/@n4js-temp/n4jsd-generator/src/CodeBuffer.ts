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
