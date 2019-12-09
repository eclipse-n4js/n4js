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

/*
 * See ImplicitVersionImport.n4idl for the actual implementation of this test.
 * This is just a runner file that includes the N4IDL implementation.
 */

const versionedModule = require("./imports/ImplicitVersionImport")

versionedModule.f(null);
