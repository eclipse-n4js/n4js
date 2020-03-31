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
		super();
		const $specObj = spec || {};
		this.stuff = $specObj.stuff;
	}
	static get n4type() {
		return $getReflectionForClass(MyExternalClass, '["MyExternalClass","AT_874_ExternalClasses","ES6_Classes_NonPrototype",["f.stuff","m.constructor"]]');
	}
}
