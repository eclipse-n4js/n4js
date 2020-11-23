


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
