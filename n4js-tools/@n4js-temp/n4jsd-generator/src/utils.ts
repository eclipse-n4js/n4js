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


export const PACKAGE_JSON = "package.json";


export class Issue {
	kind: 'warning' | 'error';
	message: string;

	constructor(msg: string, kind: 'warning' | 'error' = 'error') {
		this.kind = kind;
		this.message = msg;
	}
}
export function error(msg: string) {
	return new Issue(msg, 'error');
}
export function warning(msg: string) {
	return new Issue(msg, 'warning');
}


export function testFlagsOR(value: number, ...flags: number[]) {
	for (const flag of flags) {
		if (testFlag(value, flag)) {
			return true;
		}
	}
	return false;
}

export function testFlagsAND(value: number, ...flags: number[]) {
	for (const flag of flags) {
		if (!testFlag(value, flag)) {
			return false;
		}
	}
	return true;
}

export function testFlag(value: number, flag: number) {
	return (value & flag) === flag;
}
