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
/*eslint-disable no-proto, new-cap */
/*global $makeClass */

(function (global) {
    "use strict";

    /**
     * Similar to {@link $makeClass} without n4type meta class.
     *
     * @param ctor - The constructor function
     * @param superCtor - The constructor function of the super class
     * @param instanceMethods - An object holding the methods for the class instance and mixed in methods
     * @param staticMethods - An object holding the descriptors for the class static methods
     */
    function $makeN4BuiltInClass(ctor, superCtor, instanceMethods, staticMethods) {
        if (typeof superCtor === "function") {
            Object.setPrototypeOf(ctor, superCtor);
        }

        if (superCtor === Error) {
            instanceMethods.stack = { get: function() { return this.$__n4err.stack; } };
            instanceMethods.message = { get: function() { return this.$__n4err.message; } };
            instanceMethods.name = { get: function() { return ctor.n4type.name; } };
        }

        Object.defineProperties(ctor, staticMethods);

        var proto = Object.create(superCtor.prototype, instanceMethods);
        Object.defineProperty(proto, "constructor", { value: ctor });

        ctor.prototype = proto;
    }

    /**
     * Create meta information for N4BuiltInClasses i.e.
     * - bind n4Type property of ctor to instance of (meta) N4Class
     * - bind ownedMembers (meta) N4Class to that instance
     *
     * @param ctor - The constructor function of N4BuiltInClass that will be enriched with meta info
     * @param n4type - The instance of N4Class describing meta info of given N4BuiltInClass (specified by provided ctor)
     */
    function $createMetaInfo(ctor, metaClass) {
        //bind n4Type property metaclass instance
        Object.defineProperty(ctor, 'n4type', {
            value: metaClass,
            enumerable: false
        });

        //bind meta class instance members to that instance (so member->owner)
        //(owner->member relation is set when creating instance of the meta class)
        metaClass.ownedMembers.forEach(function (m) {
            Object.defineProperty(m, 'owner', {
                value: metaClass,
                enumerable: false
            });
        });

        //bind owned N4Methods (of the meta class) to Functions (their constructors) and viceVersa
        metaClass.ownedMembers.forEach(function (m) {
            if (m instanceof N4Method) {
                Object.defineProperty(m, 'jsFunction', {
                    value: m.constructor,
                    enumerable: false
                });
                m.constructor.n4member = m;
            }
        });
    }

    function defineTargetProp(annotation) {
        Object.defineProperty(annotation, "target", { value: this, enumerable: false });
    }
    function setTargetOfAnnotations(target) {
        if (target.annotations) {
            target.annotations.forEach(defineTargetProp, target);
        }
    }

    // ===== define functions used later as ctors of N4BuiltInClasses

    var N4Object = function N4Object(spec) {
        Object.prototype.constructor.call(this, spec);
        // defined in types model, added by $createMetaInfo
        /* public static N4Class get n4type() { return null; } */
    };

    var N4Element = function N4Element(spec) {
        N4Object.prototype.constructor.call(this, spec);
        this.origin = spec.origin;
        this.annotations = spec.annotations || [];
        setTargetOfAnnotations(this);
    };

    var N4NamedElement = function N4NamedElement(spec) {
        N4Element.prototype.constructor.call(this, spec);
        this.name = spec.name;
    };

    var N4Type = function N4Type(spec) {
        N4NamedElement.prototype.constructor.call(this, spec);
        this.fqn = spec.fqn;
    };

    var N4Classifier = function N4Classifier(spec) {
        N4Type.prototype.constructor.call(this, spec);
        this.n4superType = spec.n4superType;
        this.allImplementedInterfaces = spec.allImplementedInterfaces;
        this.annotations = spec.annotations;
        this.ownedMembers = spec.ownedMembers || [];
        this.consumedMembers = spec.consumedMembers || []; //we know it is empty array

        this.ownedMembers.forEach(function(m) {
            Object.defineProperty(m, 'owner', { value: this, enumerable: false });
            setTargetOfAnnotations(m);
        }, this);
    };

    var N4Class = function N4Class(spec) {
        N4Classifier.prototype.constructor.call(this, spec);
    };

    var N4Interface = function N4Interface(spec) {
        N4Classifier.prototype.constructor.call(this, spec);
    };

    var N4Member = function N4Member(spec) {
        N4NamedElement.prototype.constructor.call(this, spec);
        this.owner = spec.owner;
        this.isStatic = spec.isStatic || false;
    };

    var N4Method = function N4Method(spec) {
        N4Member.prototype.constructor.call(this, spec);
        this.jsFunction = spec.jsFunction;
    };

    var N4DataField = function N4DataField(spec) {
        N4Member.prototype.constructor.call(this, spec);
    };

    var N4Accessor = function N4Accessor(spec) {
        N4Member.prototype.constructor.call(this, spec);
        this.getter = spec.getter;
    };

    var N4EnumType = function N4EnumType(spec) {
        N4Type.prototype.constructor.call(this, spec);
    };

    var N4Enum = function N4Enum(spec) {
        Object.prototype.constructor.call(this, spec);
    };

    var N4StringBasedEnumType = function N4StringBasedEnumType(spec) {
        N4Type.prototype.constructor.call(this, spec);
    };

    var N4StringBasedEnum = function N4StringBasedEnum(spec) {
        Object.prototype.constructor.call(this, spec);
    };

    var N4Annotation = function N4Annotation(spec) {
        N4Object.prototype.constructor.call(this, spec);
        this.name = spec.name;
        this.details = spec.details;
        this.target = spec.target;
    };

    var N4Provider = function N4Provider(spec) {
        Object.prototype.constructor.call(this, spec);
    };

    var N4ApiNotImplementedError = function N4ApiNotImplementedError(spec) {
        this.$__n4err = new Error(spec);
    };

    // N4IDL-related built-in types
    var MigrationController = function MigrationController(spec) {
        Object.prototype.constructor.call(this, spec);
    }

    var MigrationContext = function MigrationContext(spec) {
        Object.prototype.constructor.call(this, spec);
    }

    // ===== make N4BuiltInClasses (transform ctor functions to work as Classes)

    $makeN4BuiltInClass(N4Object, Object, {}, {});

    $makeN4BuiltInClass(N4Element, N4Object, {
        hasAnnotation: {
            value: function
            hasAnnotation(name) {
                return this.annotations.some(function (a) {
                    return a.name === name;
                });
            }
        },
        anyAnnotation: {
            value: function
            anyAnnotation(name) {
                for (var i = this.annotations.length - 1; i >= 0; i--) {
                    var a = this.annotations[i];
                    if (a.name === name) {
                        return a;
                    }
                }
                return null;
            }
        },
        allAnnotations: {
            value: function
            allAnnotations(name) {
                return this.annotations.filter(function (a) {
                    return a.name === name;
                });
            }
        }
    }, {});

    $makeN4BuiltInClass(N4NamedElement, N4Element, {}, {});

    $makeN4BuiltInClass(N4Type, N4NamedElement, {
        isClass: {
            get: function
            getIsClass() {
                return false;
            }
        },
       isInterface: {
            get: function
            getIsInterface() {
                return false;
            }
        },
        isEnum: {
            get: function
            getIsEnum() {
                return false;
            }
        }
    }, {
        of: {
            value: function of(n4object) {
                return n4object ? n4object.n4type || n4object.constructor.n4type : undefined;
            }
        }
    });

    $makeN4BuiltInClass(N4Classifier, N4Type, {
        members: {
            value: function members(consumed, inherited, _static) {
                var arr = this.ownedMembers.slice();
                if (consumed) {
                    arr = arr.concat(this.consumedMembers);
                }
                if (inherited) {
                    if (this.n4superType instanceof N4Classifier) {
                        var tmp = this.n4superType.members(consumed, inherited, _static);
                        arr = arr.concat(tmp);
                    }
                }
                if (!_static) {
                    arr = arr.filter(function (m) {
                        return !m.isStatic;
                    });
                }
                return arr;
            }
        },
        membersWithAnnotation: {
            value: function membersWithAnnotation(name, consumed, inherited, _static) {
                return this.members(consumed, inherited, _static).filter(function (m) {
                    return m.hasAnnotation(name);
                });
            }
        },
        dataFields: {
            value: function dataFields(consumed, inherited, _static) {
                return this.members(consumed, inherited, _static).filter(function (m) {
                    return m instanceof N4DataField;
                });
            }
        },
        dataFieldsWithAnnotation: {
            value: function dataFieldsWithAnnotation(name, consumed, inherited, _static) {
                return this.membersWithAnnotation(name, consumed, inherited, _static).filter(function (m) {
                    return m instanceof N4DataField;
                });
            }
        },
        methods: {
            value: function methods(consumed, inherited, _static) {
                return this.members(consumed, inherited, _static).filter(function (m) {
                    return m instanceof N4Method;
                });
            }
        },
        methodsWithAnnotation: {
            value: function
            methodsWithAnnotation(name, consumed, inherited, _static) {
                return this.membersWithAnnotation(name, consumed, inherited, _static).filter(function (m) {
                    return m instanceof N4Method;
                });
            }
        }
    }, {});

    $makeN4BuiltInClass(N4Class, N4Classifier, {
        isClass: {
            get: function
            getIsClass() {
                return true;
            }
        }
    }, {});

    $makeN4BuiltInClass(N4Interface, N4Classifier, {
        isInterface: {
            get: function
            getIsInterface() {
                return true;
            }
        }
    }, {});

    $makeN4BuiltInClass(N4Member, N4NamedElement, {}, {});

    $makeN4BuiltInClass(N4Method, N4Member, {}, {});

    $makeN4BuiltInClass(N4DataField, N4Member, {}, {});

    $makeN4BuiltInClass(N4Accessor, N4Member, {
        isGetter: {
            value: function
            isGetter() {
                return this.getter;
            }
        },
        isSetter: {
            value: function
            isSetter() {
                return !this.getter;
            }
        }
    }, {});

    $makeN4BuiltInClass(N4EnumType, N4Type, {
        isEnum: {
            get: function
            getIsEnum() {
                return true;
            }
        }
    }, {});

    $makeN4BuiltInClass(N4Enum, Object, {
        toString: {
            value: function toString() {
                return this.value;
            }
        }
    }, {
        findLiteralByName: {
            value: function findLiteralByName(name) {
                for (var i = this.literals.length - 1; i >= 0; i--) {
                    if (this.literals[i].name === name) {
                        return this.literals[i];
                    }
                }
                return undefined;
            }
        },
        findLiteralByValue: {
            value: function findLiteralByValue(value) {
                for (var i = this.literals.length - 1; i >= 0; i--) {
                    if (this.literals[i].value === value) {
                        return this.literals[i];
                    }
                }
                return undefined;
            }
        }
    });

    $makeN4BuiltInClass(N4StringBasedEnumType, N4Type, {
        isEnum: {
            get: function
            getIsEnum() {
                return true;
            }
        }
    }, {});

    $makeN4BuiltInClass(N4StringBasedEnum, Object, {
        toString: {
            value: function toString() {
                return this.value;
            }
        }
    }, {
        findLiteralByValue: {
            value: function findLiteralByValue(value) {
                for (var i = this.literals.length - 1; i >= 0; i--) {
                    if (this.literals[i] === value) {
                        return this.literals[i];
                    }
                }
                return undefined;
            }
        }
    });

    $makeN4BuiltInClass(N4Annotation, N4Object, {}, {});

    $makeN4BuiltInClass(N4ApiNotImplementedError, Error, {}, {});

    // ===== add meta information for N4BuiltInClasses

    $createMetaInfo(N4Object,
        new N4Class({
            name: 'N4Object',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4Object',
            n4superType: N4Object.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [],
            consumedMemebers: []
        }));

    $createMetaInfo(N4Element,
        new N4Class({
            name: 'N4Element',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4Element',
            n4superType: N4Object.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Accessor({
                name: 'annotations',
                getter: true
            }), new N4Method({
                name: 'hasAnnotation',
                jsFunction: N4Element['hasAnnotation']
            }), new N4Method({
                name: 'anyAnnotation',
                jsFunction: N4Element['anyAnnotation']
            }), new N4Method({
                name: 'allAnnotations',
                jsFunction: N4Element['allAnnotations']
            }), new N4Accessor({
                name: 'origin',
                getter: true
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4NamedElement,
        new N4Class({
            name: 'N4NamedElement',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4NamedElement',
            n4superType: N4Element.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Accessor({
                name: 'name',
                getter: true
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4Type,
        new N4Class({
            name: 'N4Type',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4Type',
            n4superType: N4NamedElement.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Accessor({
                name: 'fqn',
                getter: true
            }), new N4Method({
                name: 'of',
                jsFunction: N4Type['of'],
                isStatic: true
            }), new N4Accessor({
                name: 'isClass',
                getter: true
            }), new N4Accessor({
                name: 'isInterface',
                getter: true
            }), new N4Accessor({
                name: 'isEnum',
                getter: true
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4Classifier,
        new N4Class({
            name: 'N4Classifier',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4Classifier',
            n4superType: N4Type.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Accessor({
                name: 'n4superType',
                getter: true
            }), new N4Accessor({
                name: 'allImplementedInterfaces',
                getter: true
            }), new N4DataField({
                name: 'ownedMembers'
            }), new N4DataField({
                name: 'consumedMembers'
            }), new N4Method({
                name: 'constructor'
            }), new N4Method({
                name: 'members',
                jsFunction: N4Classifier['members']
            }), new N4Method({
                name: 'membersWithAnnotation',
                jsFunction: N4Classifier['membersWithAnnotation']
            }), new N4Method({
                name: 'dataFields',
                jsFunction: N4Classifier['dataFields']
            }), new N4Method({
                name: 'dataFieldsWithAnnotation',
                jsFunction: N4Classifier['dataFieldsWithAnnotation']
            }), new N4Method({
                name: 'methods',
                jsFunction: N4Classifier['methods']
            }), new N4Method({
                name: 'methodsWithAnnotation',
                jsFunction: N4Classifier['methodsWithAnnotation']
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4Class,
        new N4Class({
            name: 'N4Class',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4Class',
            n4superType: N4Classifier.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Accessor({
                name: 'isClass',
                getter: true
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4Interface,
        new N4Class({
            name: 'N4Interface',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4Interface',
            n4superType: N4Classifier.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Accessor({
                name: 'isInterface',
                getter: true
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4Member,
        new N4Class({
            name: 'N4Member',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4Member',
            n4superType: N4NamedElement.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Accessor({
                name: 'owner',
                getter: true
            }), new N4Accessor({
                name: 'isStatic',
                getter: true
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4Method,
        new N4Class({
            name: 'N4Method',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4Method',
            n4superType: N4Member.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Accessor({
                name: 'jsFunction',
                getter: true
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4DataField,
        new N4Class({
            name: 'N4DataField',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4DataField',
            n4superType: N4Member.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [],
            consumedMemebers: []
        }));

    $createMetaInfo(N4Accessor,
        new N4Class({
            name: 'N4Accessor',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4Accessor',
            n4superType: N4Member.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4DataField({
                name: 'getter'
            }), new N4Method({
                name: 'isGetter',
                jsFunction: N4Accessor['isGetter']
            }), new N4Method({
                name: 'isSetter',
                jsFunction: N4Accessor['isSetter']
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4EnumType,
        new N4Class({
            name: 'N4EnumType',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4EnumType',
            n4superType: N4Type.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Accessor({
                name: 'isEnum',
                getter: true
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4Enum,
        new N4Class({
            name: 'N4Enum',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4Enum',
            n4superType: undefined,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Method({
                name: 'toString',
                jsFunction: N4Enum['toString']
            }), new N4Method({
                name: 'valueByName',
                jsFunction: N4Enum['valueByName'],
                isStatic: true
            }), new N4Accessor({
                name: 'name',
                getter: true
            }), new N4Accessor({
                name: 'value',
                getter: true
            }), new N4Accessor({
                name: 'values',
                getter: true,
                isStatic: true
            }), new N4Accessor({
                name: 'n4type',
                getter: true
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4StringBasedEnumType,
        new N4Class({
            name: 'N4StringBasedEnumType',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4StringBasedEnumType',
            n4superType: N4Type.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Accessor({
                name: 'isEnum',
                getter: true
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4StringBasedEnum,
        new N4Class({
            name: 'N4StringBasedEnum',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4StringBasedEnum',
            n4superType: undefined,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Method({
                name: 'toString',
                jsFunction: N4StringBasedEnum['toString']
            }), new N4Method({
                name: 'valueByName',
                jsFunction: N4StringBasedEnum['valueByName'],
                isStatic: true
            }), new N4Accessor({
                name: 'name',
                getter: true
            }), new N4Accessor({
                name: 'value',
                getter: true
            }), new N4Accessor({
                name: 'values',
                getter: true,
                isStatic: true
            }), new N4Accessor({
                name: 'n4type',
                getter: true
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4Annotation,
        new N4Class({
            name: 'N4Annotation',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4Annotation',
            n4superType: N4Object.n4type,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Accessor({
                name: 'name',
                getter: true
            }), new N4Accessor({
                name: 'details',
                getter: true
            }), new N4Accessor({
                name: 'target',
                getter: true
            })],
            consumedMemebers: []
        }));

    $createMetaInfo(N4Provider,
        new N4Interface({
            name: 'N4Provider',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/N4Provider',
            n4superType: undefined,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [new N4Method({ name: 'get', jsFunction: N4Provider['get'], annotations: [] })],
            consumedMembers: []
        }));

    $createMetaInfo(N4ApiNotImplementedError,
        new N4Class({
            name: 'N4ApiNotImplementedError',
            origin: 'N4BuiltInClasses',
            fqn: 'N4BuiltInClasses/N4ApiNotImplementedError',
            n4superType: undefined,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [],
            consumedMemebers: []
        }));

    // N4IDL-related interfaces
    $createMetaInfo(MigrationController,
        new N4Interface({
            name: 'MigrationController',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/MigrationController',
            n4superType: undefined,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [
                new N4Method({
                    name: 'migrate',
                    isStatic: false,
                    annotations: []
                }),
                new N4Method({
                    name: 'migrateWith',
                    isStatic: false,
                    annotations: []
                }),
                new N4Accessor({
                    name: 'context',
                    getter: true,
                    isStatic: false,
                    annotations: []
                })
            ],
            consumedMembers: []
        }));
    
    $createMetaInfo(MigrationContext,
        new N4Interface({
            name: 'MigrationContext',
            origin: 'n4js-runtime',
            fqn: 'N4BuiltInClasses/MigrationContext',
            n4superType: undefined,
            allImplementedInterfaces: [],
            annotations: [],
            ownedMembers: [
                new N4Method({
                    name: 'getTrace',
                    isStatic: false,
                    annotations: []
                }),
                new N4Method({
                    name: 'isModified',
                    isStatic: false,
                    annotations: []
                }),
                new N4Method({
                    name: 'setUserData',
                    isStatic: false,
                    annotations: []
                }),
                new N4Method({
                    name: 'getUserData',
                    isStatic: false,
                    annotations: []
                })
            ],
            consumedMembers: []
        }));

    //====== prevent modifications at runtime runtime
    Object.freeze(N4Object);
    Object.freeze(N4Element);
    Object.freeze(N4NamedElement);
    Object.freeze(N4Type);
    Object.freeze(N4Classifier);
    Object.freeze(N4Class);
    Object.freeze(N4Interface);
    Object.freeze(N4Member);
    Object.freeze(N4Method);
    Object.freeze(N4DataField);
    Object.freeze(N4Accessor);
    Object.freeze(N4EnumType);
    Object.freeze(N4Enum);
    Object.freeze(N4StringBasedEnumType);
    Object.freeze(N4StringBasedEnum);
    Object.freeze(N4Provider);
    Object.freeze(N4ApiNotImplementedError);
    Object.freeze(MigrationController);
    Object.freeze(MigrationContext);

    //====== make globally available

    global.N4Object = N4Object;
    global.N4Element = N4Element;
    global.N4NamedElement = N4NamedElement;
    global.N4Type = N4Type;
    global.N4Classifier = N4Classifier;
    global.N4Class = N4Class;
    global.N4Interface = N4Interface;
    global.N4Member = N4Member;
    global.N4Method = N4Method;
    global.N4DataField = N4DataField;
    global.N4Accessor = N4Accessor;
    global.N4EnumType = N4EnumType;
    global.N4Enum = N4Enum;
    global.N4StringBasedEnumType = N4StringBasedEnumType;
    global.N4StringBasedEnum = N4StringBasedEnum;
    global.N4Annotation = N4Annotation;
    global.N4Provider = N4Provider;
    global.N4ApiNotImplementedError = N4ApiNotImplementedError;
    global.MigrationController = MigrationController;
    global.MigrationContext = MigrationContext;

})(typeof global === "object" ? global : self);
