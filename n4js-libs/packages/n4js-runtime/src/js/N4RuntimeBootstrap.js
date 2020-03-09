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

var symHasInstance = Symbol.hasInstance,
    ArraySlice = Array.prototype.slice,
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

/** Call context is prototype object. */
function mixinDefaultMethod(method) {
    var name = method && method.name;
    if (name && !(name in this) && !method.isStatic && method.jsFunction) {
        this[name] = method.jsFunction.value;
    }
}
/** Call context is prototype object. */
function mixinDefaultMethods(iface) {
    var n4type = iface && iface.n4type;
    if (n4type) {
        n4type.ownedMembers.forEach(mixinDefaultMethod, this);
        n4type.consumedMembers.forEach(mixinDefaultMethod, this);
    }
}

/**
 * Setup a constructor function to work as a class.
 *
 * @param ctor - The constructor function
 * @param superCtor - The constructor function of the super class
 * @param implementedInterfaces - Array of directly implemented interfaces, excluding built-in types and
 *                                interfaces defined in definition files without annotation @N4JS.
 * @param instanceMethods - An object holding the methods for the class instance and mixed in methods
 * @param staticMethods - An object holding the descriptors for the class static methods
 * @param n4typeFn - Optional factory function to create the meta type (currently mandatory though, will be optional with GH-574).
 */
function $makeClass_orig(ctor, superCtor, implementedInterfaces, instanceMethods, staticMethods, n4typeFn) {
    if (typeof superCtor === "function") {
        Object.setPrototypeOf(ctor, superCtor);
    }
    Object.defineProperties(ctor, staticMethods);

    var proto = Object.create(superCtor.prototype, instanceMethods);
    implementedInterfaces.forEach(mixinDefaultMethods, proto);
    Object.defineProperty(proto, "constructor", {
        value: ctor
    });

    if (n4typeFn) {
        defineN4TypeGetter(ctor, n4typeFn.bind(null, proto, ctor));
    }

    ctor.prototype = proto;
}
/**
 * Setup a constructor function to work as a class.
 *
 * @param ctor - The constructor function
 * @param superCtor - The constructor function of the super class
 * @param implementedInterfaces - Array of directly implemented interfaces, excluding built-in types and
 *                                interfaces defined in definition files without annotation @N4JS.
 * @param instanceMethods - An object holding the methods for the class instance and mixed in methods
 * @param staticMethods - An object holding the descriptors for the class static methods
 * @param n4typeValues - Optional factory function to create the meta type (currently mandatory though, will be optional with GH-574).
 */
function $makeClass(ctor, superCtor, implementedInterfaces, instanceMethods, staticMethods, reflectionString) {
    if (typeof superCtor === "function") {
        Object.setPrototypeOf(ctor, superCtor);
    }
    Object.defineProperties(ctor, staticMethods);

    var proto = Object.create(superCtor.prototype, instanceMethods);
    implementedInterfaces.forEach(mixinDefaultMethods, proto);
    Object.defineProperty(proto, "constructor", {
        value: ctor
    });

    if (reflectionString) {
        defineN4TypeGetter(ctor, makeReflectionsForClass.bind(null, proto, ctor, reflectionString));
    }

    ctor.prototype = proto;
}


/** Used for ES6 class version of N4JS */
//function $getReflectionForClass(n4elem, moduleName, n4ElemName) {
//    const $sym = Symbol.for('org.eclipse.n4js/reflectionInfo');
//    if (n4elem.hasOwnProperty($sym)) {
//        return n4elem[$sym];
//    }
//    // FIXME: path of require is wrong
//    const reflectValues = require(moduleName + '.reflect')?.[n4ElemName];
//    if (!reflectValues) {
//        return null;
//    }
//    const instanceProto = n4elem.prototype;
//    return createN4Class(instanceProto, staticProto, ...reflectValues);
//}

function makeReflectionsForClass(instanceProto, staticProto, reflectionString) {
	const reflectionValues = JSON.parse(reflectionString);
	const superclass = staticProto.__proto__.n4type;
	const n4Class = new N4Class();
    setN4TypeProperties(n4Class, ...reflectionValues);
    setN4ClassifierProperties(n4Class, superclass, instanceProto, staticProto, ...reflectionValues);
    return n4Class;
}

function makeReflectionsForInterface(instanceProto, staticProto, reflectionString) {
	const reflectionValues = JSON.parse(reflectionString);
	const n4Interface = new N4Interface();
    setN4TypeProperties(n4Interface, ...reflectionValues);
    setN4ClassifierProperties(n4Interface, undefined, instanceProto, staticProto, ...reflectionValues);
    return n4Interface;
}

function makeReflectionsForEnum(reflectionString) {
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
	if (memberStrings) {
        for (const memberString of memberStrings) {
			const memberInfo = parseMemberString(memberString);
			const member = createMember(instanceProto, staticProto, memberInfo, annotations);
			const members = (memberInfo.isConsumed) ? consumedMembers : ownedMembers;
            members.push(member);
        }		
	}

    return {ownedMembers, consumedMembers};
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




/**
 * Setup a interface. Methods and field initializers are already merged into the interface object.
 *
 * @param tinterface - The interface object.
 * @param extendedInterfacesFn - Optional function returning an array of interfaces extended by 'tinterface'.
 *                               May be 'undefined' in case no interfaces are extended.
 * @param n4typeFn - Optional factory function to create the meta type (currently mandatory though, will be optional with GH-574).
 */
function $makeInterface(tinterface, extendedInterfacesFn, reflectionString) {
    tinterface.$extends = extendedInterfacesFn || (()=>[]);

    if (reflectionString) {
        defineN4TypeGetter(tinterface, makeReflectionsForInterface.bind(null, tinterface.$methods, tinterface, reflectionString));
    }

    Object.defineProperty(tinterface, symHasInstance, {
        /**
         * Check whether a value is instance of a class implementing an interface.
         *
         * @param instance - The instance which type is to be checked whether it implements the interface
         * @return boolean
         */
        value: function(instance) {
            if (!instance || !instance.constructor || !instance.constructor.n4type || !instance.constructor.n4type.allImplementedInterfaces) {
                return false;
            }
            const implementedInterface = tinterface.n4type.fqn;
            return instance.constructor.n4type.allImplementedInterfaces.indexOf(implementedInterface) !== -1;
        }
    });
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
        $initFieldsFromInterfaces(target, ifc.$extends(), spec, mixinExclusion);
    }
}

/**
 * Create an enumeration type with the given FQN and members.
 *
 * @param enumeration - the enumeration constructor function
 * @param members - An array of <String, String> tuples containing
 *                  information about the enum members
 * @param n4typeFn - Optional factory function to create the meta type (currently mandatory though, will be optional with GH-574).
 * @return The constructed enumeration type
 */
function $makeEnum(enumeration, members, reflectionString) {
    var length, index, member, name, value, values, literal;

    Object.setPrototypeOf(enumeration, N4Enum);
    enumeration.prototype = Object.create(N4Enum.prototype, {});

    if (reflectionString) {
        defineN4TypeGetter(enumeration, makeReflectionsForEnum.bind(null, reflectionString));
    }

    Object.defineProperty(enumeration.prototype, "constructor", {
        value: enumeration
    });

    length = members.length;
    values = new Array(length);
    for (index = 0; index < length; ++index) {
        member = members[index];
        name = member[0];
        value = member[1];

        literal = new enumeration(name, value);
        Object.defineProperty(enumeration, literal.name, {
            enumerable: true,
            value: literal
        });
        values[index] = literal;
    }

    Object.defineProperty(enumeration, 'literals', {
        value: values
    });
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

//expose in global scope
_globalThis.$makeClass = $makeClass;
_globalThis.$makeInterface = $makeInterface;
_globalThis.$initFieldsFromInterfaces = $initFieldsFromInterfaces;
_globalThis.$makeEnum = $makeEnum;

_globalThis.$sliceToArrayForDestruct = $sliceToArrayForDestruct;
_globalThis.$spawn = $spawn;
_globalThis.$n4promisifyFunction = $n4promisifyFunction;
_globalThis.$n4promisifyMethod = $n4promisifyMethod;
