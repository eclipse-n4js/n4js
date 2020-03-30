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
/*eslint-disable new-cap, no-proto, strict */

import _globalThis from "./_globalThis";
import {makeReflectionsForClass, makeReflectionsForInterface, makeReflectionsForEnum} from "./ReflectionBuilder"
import {$interfaceFieldInits, $interfaceDefaultMembers, $interfaceExtends} from "./ReflectionBuilder"

var ArraySlice = Array.prototype.slice;


/**
 * Returns reflection information.
 * If it is not existing yet, it will be created and attached to the prototype using a symbol.
 */
function $getReflectionForClass(staticProto, reflectionString) {
    const $sym = Symbol.for('org.eclipse.n4js/reflectionInfo');
    if (!staticProto.hasOwnProperty($sym)) {
	    staticProto[$sym] = makeReflectionsForClass(staticProto, reflectionString);
    }
    return staticProto[$sym];
}

/**
 * Returns reflection information.
 * If it is not existing yet, it will be created and attached to the prototype using a symbol.
 */
function $getReflectionForInterface(staticProto, reflectionString) {
    const $sym = Symbol.for('org.eclipse.n4js/reflectionInfo');
    if (!staticProto.hasOwnProperty($sym)) {
	    staticProto[$sym] = makeReflectionsForInterface(staticProto, reflectionString);
    }
    return staticProto[$sym];
}

/**
 * Returns reflection information.
 * If it is not existing yet, it will be created and attached to the prototype using a symbol.
 */
function $getReflectionForEnum(staticProto, reflectionString) {
    const $sym = Symbol.for('org.eclipse.n4js/reflectionInfo');
    if (!staticProto.hasOwnProperty($sym)) {
	    staticProto[$sym] = makeReflectionsForEnum(staticProto, reflectionString);
    }
    return staticProto[$sym];
}


/**
 * Define one or more static or instance data fields of a class. This is only intended for the
 * rare special cases in which explicit property definitions are required for a data field, due
 * to accessor(s) being overridden by the data field.
 *
 * @param target - The target object. Either the constructor of a class (for static fields)
 *                 or an instance of a class (for instance fields).
 * @param names - One or more field names.
 */
function $defineFields(target, ...names) {
    for(const name of names) {
        Object.defineProperty(target, name, {
            writable: true,
            enumerable: true,
            configurable: true
        });
    }
}

/**
 * Initialize the fields declared by the given interfaces in the target object 'target'.
 * Takes care of defaults defined in the interfaces and values provided via the optional
 * 'spec' object. Will never override properties that already exist in the target object
 * or that exist in the 'mixinExclusion' object.
 *
 * @param target - The object to be initialized. Usually a newly created instance of
 *                 some class implementing the given interfaces.
 * @param interfaces - Array of zero or more interfaces.
 * @param spec - If invoked from a @Spec-constructor, this is the spec-object; otherwise 'undefined'.
 * @param mixinExclusion - An object with properties that must not be overridden in the target object.
 */
function $initFieldsFromInterfaces(target, interfaces, spec, mixinExclusion) {
    for (const ifc of interfaces) {
        const defs = $interfaceFieldInits(ifc) || {};
        for (const fieldName of Object.getOwnPropertyNames(defs)) {
            if (target.hasOwnProperty(fieldName) || mixinExclusion.hasOwnProperty(fieldName)) {
                continue;
            }
            let value = undefined;
            if (spec) {
                value = spec[fieldName];
            }
            if (value === undefined) {
                value = defs[fieldName];
                if (typeof value === "function") {
                    value = value.call(target);
                }
            }
            target[fieldName] = value;
        }
        const extendsFn = $interfaceExtends(ifc);
        if (extendsFn) {
            $initFieldsFromInterfaces(target, extendsFn(), spec, mixinExclusion);
        }
    }
}

/**
 * Helper function used when transpiling destructuring patterns. Return an array of the first 'max'
 * elements from iterable 'arr'. If the Iterable does not provide enough elements, a shorter array
 * is returned. So length of returned array is between 0 and 'max'.
 *
 * @param arr - the Iterable that is to be turned into an array.
 * @param max - maximum number of elements returned.
 */
function $sliceToArrayForDestruct(arr, max) {
    if (Array.isArray(arr)) {
        return arr;
    } else if (Symbol.iterator in Object(arr)) {
        var ret = [],
            it = arr[Symbol.iterator](),
            entry;
        while (!(entry = it.next()).done) {
            ret.push(entry.value);
            if (ret.length >= max) {
                break;
            }
        }
        return ret;
    } else {
        throw new TypeError("Invalid attempt to destructure non-iterable instance");
    }
}

function $n4promisifyFunction(cbBasedFn, args, multiSuccessValues, noErrorValue) {
    return new Promise(function(resolve, reject) {
        args.push(function(result0, result1) {
            if (noErrorValue) {
                resolve(multiSuccessValues ? ArraySlice.call(arguments, 0) : result0);
            } else {
                if (result0) {
                    reject(result0);
                } else {
                    resolve(multiSuccessValues ? ArraySlice.call(arguments, 1) : result1);
                }
            }
        });
        cbBasedFn.apply(null, args);
    });
}
function $n4promisifyMethod(receiver, methodName, args, multiSuccessValues, noErrorValue) {
    return new Promise(function(resolve, reject) {
        args.push(function(result0, result1) {
            if (noErrorValue) {
                resolve(multiSuccessValues ? ArraySlice.call(arguments, 0) : result0);
            } else {
                if (result0) {
                    reject(result0);
                } else {
                    resolve(multiSuccessValues ? ArraySlice.call(arguments, 1) : result1);
                }
            }
        });
        receiver[methodName].apply(receiver, args);
    });
}




//expose in global scope
_globalThis.$getReflectionForClass = $getReflectionForClass;
_globalThis.$getReflectionForInterface = $getReflectionForInterface;
_globalThis.$getReflectionForEnum = $getReflectionForEnum;
_globalThis.$defineFields = $defineFields;
_globalThis.$initFieldsFromInterfaces = $initFieldsFromInterfaces;
_globalThis.$sliceToArrayForDestruct = $sliceToArrayForDestruct;
_globalThis.$n4promisifyFunction = $n4promisifyFunction;
_globalThis.$n4promisifyMethod = $n4promisifyMethod;
