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

(function (global) {
    "use strict";

    var ArraySlice = Array.prototype.slice,
        noop = function() {};

    function defineN4TypeGetter(instance, factoryFn) {
        var n4type;
        Object.defineProperty(instance, "n4type", {
            configurable: true, // for hot reloading/patching
            get: function() {
                return n4type || (n4type = factoryFn());
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
            const extendsFn = ifc.$extends;
            if(extendsFn) {
                $initFieldsFromInterfaces(target, extendsFn(), spec, mixinExclusion);
            }
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
    function $makeEnum(enumeration, members, n4typeFn) {
        var length, index, member, name, value, values, literal;

        Object.setPrototypeOf(enumeration, global.N4Enum);
        enumeration.prototype = Object.create(global.N4Enum.prototype, {});

        if (n4typeFn) {
            defineN4TypeGetter(enumeration, n4typeFn.bind(null, noop));
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
    global.$initFieldsFromInterfaces = $initFieldsFromInterfaces;
    global.$makeEnum = $makeEnum;

    global.$sliceToArrayForDestruct = $sliceToArrayForDestruct;
    global.$spawn = $spawn;
    global.$n4promisifyFunction = $n4promisifyFunction;
    global.$n4promisifyMethod = $n4promisifyMethod;

})(typeof global === "object" ? global : self);
