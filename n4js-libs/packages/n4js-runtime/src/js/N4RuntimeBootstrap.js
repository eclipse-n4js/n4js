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

const SYMBOL_IDENTIFIER_PREFIX = "#";

var ArraySlice = Array.prototype.slice,
    noop = function() {};

function defineN4TypeGetter(instance, factoryFn) {
    var n4type;
    Object.defineProperty(instance, "n4type", {
        configurable: true, // for hot reloading/patching
        get: function() {
            if (!n4type) {
                n4type = factoryFn();
            }
            return n4type;
        }
    });
}


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
    for(const ifc of interfaces) {
        const defs = ifc.$fieldDefaults || {};
        for(const fieldName of Object.getOwnPropertyNames(defs)) {
            if(target.hasOwnProperty(fieldName) || mixinExclusion.hasOwnProperty(fieldName)) {
                continue;
            }
            let value = undefined;
            if(spec) {
                value = spec[fieldName];
            }
            if(value === undefined) {
                value = defs[fieldName];
                if (typeof value === "function") {
                    value = value.call(target);
                }
            }
            target[fieldName] = value;
        }
        const extendsFn = ifc.$extends;
        if(extendsFn) {
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

/**
 * Executes the given generator step by step chaining up promises.
 *
 * @param gen - generator object to be sequentially executed.
 */
function $spawn(gen) {
    function exec(step, v) {
        var res;
        try {
            res = this[step](v);
        } catch (exc) {
            return Promise.reject(exc);
        }
        if (res.done) {
            return Promise.resolve(res.value);
        } else {
            return Promise.resolve(res.value).then(next, throwr);
        }
    }

    var next = exec.bind(gen, "next"),
        throwr = exec.bind(gen, "throw");
    return next();
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



function makeReflectionsForClass(staticProto, reflectionString) {
    const instanceProto = staticProto.prototype;
    const reflectionValues = JSON.parse(reflectionString);
    const superclass = staticProto.__proto__.n4type;
    const n4Class = new N4Class();
    setN4TypeProperties(n4Class, ...reflectionValues);
    setN4ClassifierProperties(n4Class, superclass, instanceProto, staticProto, ...reflectionValues);
    return n4Class;
}

function makeReflectionsForInterface(staticProto, reflectionString) {
    const instanceProto = staticProto.$members;
    const reflectionValues = JSON.parse(reflectionString);
    const n4Interface = new N4Interface();
    setN4TypeProperties(n4Interface, ...reflectionValues);
    // for interfaces we retrieve all reflection information from the JSON reflection string
    // hence we pass empty object literals instead of instanceProto and staticProto.
    // might change with ES6 classes
    setN4ClassifierProperties(n4Interface, undefined, {}, {}, ...reflectionValues);
    return n4Interface;
}

function makeReflectionsForEnum(staticProto, reflectionString) {
    const reflectionValues = JSON.parse(reflectionString);
    const n4enumType = new N4EnumType();
    setN4TypeProperties(n4enumType, ...reflectionValues);
    return n4enumType;
}

function setN4TypeProperties(n4Type, name, modulePath, origin, members, memberAnnotations, allImplementedInterfaces, annotations) {
    n4Type.name = name;
    n4Type.origin = origin;
    n4Type.setAnnotations(createAnnotations(annotations));
    n4Type.fqn = modulePath + '/' + name;
}

function setN4ClassifierProperties(n4Classifier, superclass, instanceProto, staticProto, name, modulePath, origin, members, memberAnnotations, allImplementedInterfaces, annotations) {
    const splitMembers = createMembers(instanceProto, staticProto, members, memberAnnotations);
    n4Classifier.n4superType = superclass;
    n4Classifier.setOwnedMembers(splitMembers.ownedMembers);
    n4Classifier.consumedMembers = splitMembers.consumedMembers;
    n4Classifier.allImplementedInterfaces = allImplementedInterfaces || [];
}

function createMembers(instanceProto, staticProto, memberStrings, memberAnnotations) {
    const annotations = createMemberAnnotations(memberAnnotations);
    const ownedMembers = [];
    const consumedMembers = [];
// TODO GH-1693
//  const detectedMemberStrings = detectMembers(instanceProto, staticProto);
const detectedMemberStrings = [];
    const detectedMemberStringsReduced = [];
    for (const memberString of detectedMemberStrings) {
        const memberAlreadyAsConsumedGiven = memberStrings && memberStrings.includes(toConsumedMemberString(memberString));
        if (!memberAlreadyAsConsumedGiven) {
            detectedMemberStringsReduced.push(memberString);
        }
    }
    const memberStringsPlusDetected = memberStrings? detectedMemberStringsReduced.concat(memberStrings) : detectedMemberStringsReduced;
    for (const memberString of memberStringsPlusDetected) {
        const memberInfo = parseMemberString(memberString);
        const member = createMember(instanceProto, staticProto, memberInfo, annotations);
        const members = (memberInfo.isConsumed) ? consumedMembers : ownedMembers;
        members.push(member);
    }

    return {ownedMembers, consumedMembers};
}

function toConsumedMemberString(memberString) {
    return memberString[0] + ':' + memberString.substring(2);
}

function detectMembers(instanceProto, staticProto) {
    const memberStrings = [];
    const memberNamesInstance = Object.getOwnPropertyNames(instanceProto);
    for (const memberName of memberNamesInstance) {
        const memberString = detectMember(instanceProto, memberName, false);
        if (memberString) {
            memberStrings.push(memberString);
        }
    }
    const memberNamesStatic = Object.getOwnPropertyNames(staticProto);
    for (const memberName of memberNamesStatic) {
        const memberString = detectMember(staticProto, memberName, true)
        if (memberString) {
            memberStrings.push(memberString);
        }
    }
    return memberStrings;
}

function detectMember(object, memberName, isStatic) {
    if (!isStatic && ['constructor'].includes(memberName)) {
        return null;
    }
    if (isStatic && ['length', 'name', 'prototype', 'n4type', '$methods', '$extends'].includes(memberName)) {
        return null;
    }
    const propDescriptor = Object.getOwnPropertyDescriptor(object, memberName);

    const isFunction = propDescriptor.hasOwnProperty('value') && typeof propDescriptor.value == 'function';
    if (isFunction) {
        return createMemberString('m', isStatic, memberName);
    }
    const isField = propDescriptor.hasOwnProperty('writable');
    if (isField) {
        return createMemberString('f', isStatic, memberName);
    }
    const isSetter = propDescriptor.hasOwnProperty('set') && typeof propDescriptor.set == 'function';
    if (isSetter) {
        return createMemberString('s', isStatic, memberName);
    }
    const isGetter = propDescriptor.hasOwnProperty('get') && typeof propDescriptor.get == 'function';
    if (isGetter) {
        return createMemberString('g', isStatic, memberName);
    }

    throw "Unknown member type detected";
}

function createMemberString(kind, isStatic, memberName) {
    if (isStatic) {
        kind = kind.toUpperCase();
    }
    return kind + "." + memberName;
}

function createMemberAnnotations(memberAnnotations) {
    const annotations = {};
    if (memberAnnotations) {
        for (const memberName of Object.keys(memberAnnotations)) {
            const annotationArray = [];
            annotations[memberName] = annotationArray;

            for (const memberAnnotation of memberAnnotations[memberName]) {
                const annotation = createAnnotation(memberAnnotation);
                if (annotation) {
                    annotationArray.push(annotation);
                }
            }
        }
    }
    return annotations;
}

function createAnnotations(annotations) {
    const annotationArray = [];
    if (annotations) {
        for (const annotationValues of annotations) {
            const annotation = createAnnotation(annotationValues);
            if (annotation) {
                annotationArray.push(annotation);
            }
        }
    }
    return annotationArray;
}

function createAnnotation(annotationValues) {
    const annotation = new N4Annotation();
    if (typeof annotationValues === "string") {
        annotation.name = annotationValues;
        annotation.details = [];
    } else {
        const [name, details] = annotationValues;
        annotation.name = name;
        annotation.details = details || [];
    }
    return annotation;
}

function parseMemberString(memberString) {
    if (!/^[mMfFgGsS][\.:]/.test(memberString)) {
        return null;
    }
    const idxKind = 0;
    const idxConsumed = idxKind + 1;
    const idxNameStart = idxConsumed + 1;
    const kind = memberString[idxKind].toLowerCase();	
    const isUpperCase = (memberString[idxKind] == memberString[idxKind].toUpperCase());

    let isConsumed = undefined;
    switch (memberString[idxConsumed].toLowerCase()) {
        case '.': isConsumed = false; break;
        case ':': isConsumed = true; break;
        default: isConsumed = undefined;
    }
    
    const name = memberString.substring(idxNameStart);
    const jsFunctionRef = (name.startsWith(SYMBOL_IDENTIFIER_PREFIX)) ? Symbol[name.substring(1)] : name;

    return {memberString, name, kind, isStatic: isUpperCase, isConsumed, jsFunctionRef};
}

function createMember(instanceProto, staticProto, memberInfo, annotations) {
    var member = null;
    switch (memberInfo.kind) {
        case 'f':
            member = new N4DataField();
            break;
        case 'm': 
            member = new N4Method();
            member.jsFunction = memberInfo.isStatic ? staticProto[memberInfo.jsFunctionRef] : instanceProto[memberInfo.jsFunctionRef];
            break;
        case 'g': 
            member = new N4Accessor();
            member.getter = true;
            break;
        case 's':
            member = new N4Accessor();
            member.getter = false;
            break;
        default:
            return null;
    }
    
    member.name = memberInfo.name;
    member.isStatic = memberInfo.isStatic;
    member.setAnnotations(annotations[memberInfo.memberString] || []);
    return member;
}




//expose in global scope
_globalThis.$getReflectionForClass = $getReflectionForClass;
_globalThis.$getReflectionForInterface = $getReflectionForInterface;
_globalThis.$getReflectionForEnum = $getReflectionForEnum;
_globalThis.$defineFields = $defineFields;
_globalThis.$initFieldsFromInterfaces = $initFieldsFromInterfaces;
_globalThis.$sliceToArrayForDestruct = $sliceToArrayForDestruct;
_globalThis.$spawn = $spawn;
_globalThis.$n4promisifyFunction = $n4promisifyFunction;
_globalThis.$n4promisifyMethod = $n4promisifyMethod;
