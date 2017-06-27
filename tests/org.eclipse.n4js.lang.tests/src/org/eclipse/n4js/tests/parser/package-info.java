/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
/**
 * Provides tests for N4JS parser. There are different kind of tests:
 * <dl>
 * <dt>ECMAScript Tests</dt>
 * <dd>These tests start with <code>ES_</code>, followed by the chapter number of the ECMAScript specification [ECM11].
 * Tests with the suffix "EsprimaTest" stem from the <a href="http://esprima.org/">Esprima project</a> (tests see
 * <a href="https://github.com/ariya/esprima/blob/master/test/test.js">tests.js<a>), tests with the suffix "EcmaTest"
 * stem from the <a href="http://test262.ecmascript.org/">ECMAScript test suite</a>.</dd>
 * <dt>N4JS Tests</dt>
 * <dd>These tests start with <code>N4_</code>, followed by the chapter number of the N4JS specification [N4JS]. These
 * tests focus on N4 specific enhancements of the ECMAScript language.</dd>
 * </dl>
 * Besides these tests, there exists a project containing all tests from the
 * <a href="http://test262.ecmascript.org/">ECMAScript test suite</a>.
 *
 *
 * <h4>Bibliography</h4>
 * <dl>
 * <dt>[ECM11]</dt>
 * <dd>ECMAScript Language Specification. International Standard ECMA-262, 5.1 Edition, ISO/IEC, Geneva, Switzerland,
 * June 2011. http://www.ecma-international.org/publications/standards/Ecma-262.htm</dd>
 * <dt>[N4JS]</dt>
 * <dd>Pilgrim, Jens von ; Köster, Matthias: N4JS Specification / NumberFour AG. Berlin, August 2013. Specification.
 * https://github.com/NumberFour/specs/</dd>
 * </dl>
 */
package org.eclipse.n4js.tests.parser;
