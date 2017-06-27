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

MyExternalClass = function MyExternalClass(start, spec, end) {
    if (spec) {
        this.stuff = spec.stuff;
    } else {
        this.stuff = undefined;
    }
}

$makeClass(MyExternalClass, Object, [], {}, {},
    function(instanceProto, staticProto) {
        var metaClass = new N4Class({
            name: 'MyExternalClass',
            origin: 'org.eclipse.n4js.transpiler.es5.tests-0.0.1',
            fqn: 'classes.AT_874_InheritConstructor.AT_874_ExternalClasses.MyExternalClass',
            n4superType: N4Object.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4DataField({
                name: 'stuff',
                annotations: []
            }), new N4Method({
                name: 'constructor',
                jsFunction: instanceProto['constructor'],
                annotations: []
            }), ],
            consumedMembers: []
        });
        metaClass.annotations.forEach(function(a) {
            Object.defineProperty(a, 'target', {
                value: metaClass,
                enumerable: false
            });
        });
        if (typeof metaClass.ownedMembers !== 'undefined') {
            metaClass.ownedMembers.forEach(function(m) {
                Object.defineProperty(m, 'owner', {
                    value: metaClass,
                    enumerable: false
                });
                if (typeof m.annotations !== 'undefined') {
                    m.annotations.forEach(function(a) {
                        Object.defineProperty(a, 'target', {
                            value: m,
                            enumerable: false
                        });
                    })
                }
            });
        }
        return metaClass;
    }
);

module.exports = {
	'MyExternalClass': MyExternalClass};
        
