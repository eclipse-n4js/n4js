/*
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
function Foo() {}
Foo.n4type = { // fake
    fqn: "runtime.n4.util.moduleloader.modules.TestModule.Foo"
};

var bar = {
    bar: 5
}

module.exports = {
    Foo: Foo,
    bar: bar
};
