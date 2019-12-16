/*
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */

import 'n4js-runtime'

export class MyExternalClass extends N4Object {
	constructor(start, spec, end) {
		super(start, spec, end);
		const $specObj = spec || {};
		this.stuff = $specObj.stuff;
	}
	static get n4type() {
		const $sym = Symbol.for('org.eclipse.n4js/reflectionInfo');
		if (this.hasOwnProperty($sym)) {
			return this[$sym];
		}
		const instanceProto = this.prototype, staticProto = this;
		return this[$sym] = new N4Class({
			name: 'MyExternalClass',
			origin: 'ES6_Classes',
			fqn: 'tests/Temp/MyExternalClass',
			n4superType: N4Object.n4type,
			allImplementedInterfaces: [],
			ownedMembers: [
				new N4DataField({
					name: 'stuff',
					isStatic: false,
					annotations: []
				}),
				new N4Method({
					name: 'constructor',
					isStatic: false,
					jsFunction: instanceProto['constructor'],
					annotations: []
				})
			],
			consumedMembers: [],
			annotations: []
		});
	}
}
