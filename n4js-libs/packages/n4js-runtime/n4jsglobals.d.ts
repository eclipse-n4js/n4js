/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */


// This file contains definitions of N4JS built-in types in order to make them available
// to .d.ts files exported by the N4JS DtsTranspiler. Each exported .d.ts file will include
// a bare import of 'n4js-runtime' which will load this file.


// Merged from primitives_js.n4ts

declare type pathSelector<T> = string;
declare type i18nKey = string;
declare type typeName<T> = string;


// Merged from n4js-runtime-es2015

/**
 * Use this interface type to denote common ES2015 work-around cases
 * used to make Iterators compatible with for..of loops.
 *
 * Dirty, but properly represents the ES2015 work-around.
 */
declare type IteratorExt<T> = Iterator<T> & Iterable<T>;


// Merged from builtin_n4.n4jsd

/**
 * Work-around:
 * Temporary class to define the return type of methods String#match() and RegExp#exec(), because
 * .n4ts files do not properly support "with"-clauses of structural types (see IDEBUG-273).
 */
declare class TEMP_ReturnTypeOfMatchExec extends Array<string> {
    public index: number;
    public input: string;
    public groups?: any;
}


/**
 * Base class for all N4 reflective elements.
 */
declare abstract class N4Element extends N4Object {

    /**
     * Disallow instantiation of this class and its subclasses by user code.
     */
    constructor()

    /**
     * Annotations attached to this element.
     */
    public get annotations(): Array<N4Annotation>

    /**
     * Returns true if an annotation with the given name is attached to the element.
     */
    public hasAnnotation(name: string): boolean

    /**
     * Returns any annotation with the given name attached to the element, or null if no such annotation is found.
     */
    public anyAnnotation(name: string): N4Annotation

    /**
     * Returns all annotations with the given name attached to the element, or an empty array if no such annotations are found.
     */
    public allAnnotations(name: string): Array<N4Annotation>
    /**
     * The origin string formed as <ID-VERSION>, where ID is containing project artifact id, and VERSION is its version
     */
    public get origin(): string
}

/**
 * Base class for all reflective classes with a name.
 */
declare abstract class N4NamedElement extends N4Element {
    /**
     * The simple name of a named element.
     */
    public get name(): string
}

/**
 * Base class for all reflective classes describing a type (declaration).
 */
declare abstract class N4Type extends N4NamedElement {
    /**
     * The FQN of the type.
     */
    public get fqn(): string

    /**
     * Given an instance of <code>N4Object</code> or a value of type <code>type{N4Object}</code>
     * (e.g. the constructor of an N4JS class), this method returns its <code>N4Type</code> meta
     * object. For any other argument <code>undefined</code> is returned.
     */
    public static of(n4object: any): N4Type

    /**
     * Returns true if this N4Class describes an N4-class declaration.
     */
    public get isClass(): boolean

    /**
     * Returns true if this N4Class describes an N4-interface declaration.
     */
    public get isInterface(): boolean

    /**
     * Returns true if this N4Class describes an N4-enumeration declaration.
     */
    public get isEnum(): boolean
}

/**
 * Base class for meta types of classes and interfaces.
 */
declare abstract class N4Classifier extends N4Type {

    /**
     * The N4Class of the super type, may be null if super type is a not an N4Class.
     */
    public get n4superType(): N4Class

    /**
     * Array of the FQN of all (transitively) implemented interfaces, i.e. interfaces directly implemented by this class, its super
     * class or interfaces extended by directly implemented interfaces.
     */
    public get allImplementedInterfaces(): Array<string>

    /**
     * Array of all owned members, that is members defined in the class directly.
     * This field is private as it is an internal detail, members are accessed via methods defined in this class.
     */
    private ownedMembers: Array<N4Member>;

    /**
     * Array of all members consumed from implemented interfaces, i.e. fields and accessors/methods with a body defined
     * in an implemented interface iff(!) the receiving classifier does not own or inherit a corresponding member (i.e.
     * same name and access).
     * This field is private as it is an internal detail, members are accessed via methods defined in this class.
     */
    private consumedMembers: Array<N4Member>;

    /**
     * Returns all members defined by this class directly, consumed, and inherited. The boolean flags control which members are returned.
     *
     * @param consumed if set, consumed members are returned as well (false by default)
     * @param inherited if set, inherited members are returned as well (false by default)
     * @param _static if set, static members are returned as well (false by default).
     * @return array of members, may be empty but never null
     */
    public members(consumed?: boolean, inherited?: boolean, _static?: boolean): Array<N4Member>

    /**
     * Returns all members defined in this classifier (or inherited) with an annotation
     * of given name attached to it. The boolean flags control which methods are returned.
     *
     * @param name name of annotation to be used as filter
     * @param consumed if set, consumed members are returned as well (false by default)
     * @param inherited if set, inherited members are returned as well (false by default)
     * @param _static if set, static members are returned as well (false by default).
     * @return array of members, may be empty but never null
     */
    public membersWithAnnotation(name: string, consumed?: boolean, inherited?: boolean, _static?: boolean): Array<N4Member>

    /**
     * Returns all data fields defined by this class directly, consumed, and inherited. The boolean flags control which data fields are returned.
     *
     * @param consumed if set, consumed data fields are returned as well (false by default)
     * @param inherited if set, inherited data fields are returned as well (false by default)
     * @param _static if set, static data fields are returned as well (false by default).
     * @return array of data fields, may be empty but never null
     */
    public dataFields(consumed?: boolean, inherited?: boolean, _static?: boolean): Array<N4DataField>

    /**
     * Returns all data fields defined in this classifier (or inherited) with an annotation
     * of given name attached to it. The boolean flags control which data fields are returned.
     *
     * @param name name of annotation to be used as filter
     * @param consumed if set, consumed data fields are returned as well (false by default)
     * @param inherited if set, inherited data fields are returned as well (false by default)
     * @param _static if set, static data fields are returned as well (false by default).
     * @return array of data fields, may be empty but never null
     */
    public dataFieldsWithAnnotation(name: string, consumed?: boolean, inherited?: boolean, _static?: boolean): Array<N4DataField>

    /**
     * Returns all methods defined by this class directly, consumed, and inherited. The boolean flags control which methods are returned.
     *
     * @param consumed if set, consumed methods are returned as well (false by default)
     * @param inherited if set, inherited methods are returned as well (false by default)
     * @param _static if set, static methods are returned as well (false by default).
     * @return array of methods, may be empty but never null
     */
    public methods(consumed?: boolean, inherited?: boolean, _static?: boolean): Array<N4Method>

    /**
     * Returns all methods defined in this classifier (or inherited) with an annotation
     * of given name attached to it. The boolean flags control which methods are returned.
     *
     * @param name name of annotation to be used as filter
     * @param consumed if set, consumed methods are returned as well (false by default)
     * @param inherited if set, inherited methods are returned as well (false by default)
     * @param _static if set, static methods are returned as well (false by default).
     * @return array of methods, may be empty but never null
     */
    public methodsWithAnnotation(name: string, consumed?: boolean, inherited?: boolean, _static?: boolean): Array<N4Method>
}

/**
 * Meta information of an n4 class.
 */
declare class N4Class extends N4Classifier {

    /**
     * Returns true if this N4Class describes an N4-class declaration.
     */
    /* @Override */
    public get isClass(): boolean
}

/**
 * Meta information of an n4 interface.
 */
declare class N4Interface extends N4Classifier {
    /**
     * Returns true if this N4Class describes an N4-interface declaration.
     */
    /* @Override */
    public get isInterface(): boolean
}

/**
 * Description of a member, that is a method or field.
 */
declare abstract class N4Member extends N4NamedElement {
    public get owner(): N4Classifier
    public get isStatic(): boolean
}

/**
 * Description of a method.
 */
declare class N4Method extends N4Member {
    public get jsFunction(): Function
}

/**
 * Description of a simple data field.
 */
declare class N4DataField extends N4Member {
}

/**
 * Description of an accessor, that is a getter or setter.
 */
declare class N4Accessor extends N4Member {
    /**
     * Flag indicating whether accessor is a getter or setter, internal detail.
     */
    private getter: boolean;

    /**
     * Returns true if accessor is a getter.
     */
    public isGetter(): boolean

    /**
     * Returns true if accessor is a setter.
     */
    public isSetter(): boolean
}

/**
 * Description of an N4Enum
 */
declare class N4EnumType extends N4Type {
    /**
     * Returns true if this N4Classifier describes an N4-enumeration declaration.
     */
    /* @Override */
    public get isEnum(): boolean
}

/**
 * Base class for all enumerations, literals are assumed to be static constant fields of concrete subclasses with the type of these subclasses.
 */
declare class N4Enum {

    /**
     * Disallow instantiation of this type by user code.
     */
    private constructor(name: string, value?: string)

    /**
     * Returns the name of a concrete literal
     */
    public get name(): string

    /**
     * Returns the value of a concrete literal. If no value is
     * explicitly set, it is similar to the name.
     */
    public get value(): string

    /**
     * Returns a string representation of a concrete literal, it returns
     * the same result as value()
     */
    public toString(): string

    /**
     * Returns the meta class object of this enum literal for reflection.
     * The very same meta class object can be retrieved from the enumeration type directly.
     */
    public static get n4type(): N4EnumType

    //IDE-785 this as return type in static

    /**
     * Returns array of concrete enum literals
     */
    public static get literals(): Array<N4Enum>

    /**
     * Returns concrete enum literal that matches provided name,
     * if no match found returns undefined.
     */
    public static findLiteralByName(name: string): N4Enum

    /**
     * Returns concrete enum literal that matches provided value,
     * if no match found returns undefined.
     */
    public static findLiteralByValue (value: string): N4Enum
}

/**
 * Annotation with value.
 */
declare class N4Annotation extends N4Object {

    /**
     * Disallow instantiation of this class by user code.
     */
    private constructor()

    public get name(): string
    public get details(): Array<string>
    public get target(): N4Element
}

/**
 * The base class for all instances of n4 classes.
 */
declare class N4Object extends Object {

    /**
     * Default constructor for N4JS classes.
     */
    // This constructor is only defined here to avoid having the one from Object (which has a parameter) be inherited
    // by all N4JS classes that do not define a custom constructor.
    public constructor()

    /**
     * Returns the meta class object of this class for reflection.
     * The very same meta class object can be retrieved from an instance by calling
     * <code>instance.constructor.n4type</code> or, preferred, via
     * <code>N4Type.of(instance)</code>
     */
    public static get n4type(): N4Class
}


/**
 * Base class for @NumberBased enumerations.
 */
declare class N4NumberBasedEnum {

    /**
     * Disallow instantiation of this type by user code.
     */
    private constructor()

    /**
     * Returns a string representation of a concrete literal, it returns
     * the same result as value()
     */
    public toString(): string

    /**
     * Returns array of concrete enum literals
     */
    public static get literals(): Array<N4NumberBasedEnum>
}

/**
 * Base class for @StringBased enumerations.
 */
declare class N4StringBasedEnum {

    /**
     * Disallow instantiation of this type by user code.
     */
    private constructor()

    /**
     * Returns a string representation of a concrete literal, it returns
     * the same result as value()
     */
    public toString(): string

    /**
     * Returns array of concrete enum literals
     */
    public static get literals(): Array<N4StringBasedEnum>
}


declare class Array2<T1,T2> extends Array<T1|T2> implements Iterable2<T1,T2> {}
declare class Array3<T1,T2,T3> extends Array2<T1,T2|T3> implements Iterable3<T1,T2,T3> {}
declare class Array4<T1,T2,T3,T4> extends Array3<T1,T2,T3|T4> implements Iterable4<T1,T2,T3,T4> {}
declare class Array5<T1,T2,T3,T4,T5> extends Array4<T1,T2,T3,T4|T5> implements Iterable5<T1,T2,T3,T4,T5> {}
declare class Array6<T1,T2,T3,T4,T5,T6> extends Array5<T1,T2,T3,T4,T5|T6> implements Iterable6<T1,T2,T3,T4,T5,T6> {}
declare class Array7<T1,T2,T3,T4,T5,T6,T7> extends Array6<T1,T2,T3,T4,T5,T6|T7> implements Iterable7<T1,T2,T3,T4,T5,T6,T7> {}
declare class Array8<T1,T2,T3,T4,T5,T6,T7,T8> extends Array7<T1,T2,T3,T4,T5,T6,T7|T8> implements Iterable8<T1,T2,T3,T4,T5,T6,T7,T8> {}
declare class Array9<T1,T2,T3,T4,T5,T6,T7,T8,T9> extends Array8<T1,T2,T3,T4,T5,T6,T7,T8|T9> implements Iterable9<T1,T2,T3,T4,T5,T6,T7,T8,T9> {}



declare interface Iterable2<T1, T2> extends Iterable<T1|T2> {}
declare interface Iterable3<T1, T2, T3> extends Iterable2<T1,T2|T3> {}
declare interface Iterable4<T1, T2, T3, T4> extends Iterable3<T1,T2, T3|T4> {}
declare interface Iterable5<T1, T2, T3, T4, T5> extends Iterable4<T1,T2,T3,T4|T5> {}
declare interface Iterable6<T1, T2, T3, T4, T5, T6> extends Iterable5<T1,T2,T3,T4,T5|T6> {}
declare interface Iterable7<T1, T2, T3, T4, T5, T6, T7> extends Iterable6<T1,T2,T3,T4,T5,T6|T7> {}
declare interface Iterable8<T1, T2, T3, T4, T5, T6, T7, T8> extends Iterable7<T1,T2,T3,T4,T5,T6,T7|T8> {}
declare interface Iterable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends Iterable8<T1,T2,T3,T4,T5,T6,T7,T8|T9> {}


type FunctionPropertyNames<T> = { 
    [K in keyof T]: T[K] extends Function ? K : never 
}[keyof T];

type IfEquals<X, Y, A, B> =
    (<T>() => T extends X ? 1 : 2) extends
    (<T>() => T extends Y ? 1 : 2) ? A : B;

type WritableKeysOf<T> = {
    [P in keyof T]: IfEquals<{ [Q in P]: T[P] }, { -readonly [Q in P]: T[P] }, P, never>
}[keyof T];
type WritablePart<T> = Pick<T, WritableKeysOf<T>>;

/**
 * This type mimics the N4JS structural typing modifier ~~.
 * Note that it cannot remove non-public elements due to limited expressiveness of TypeScript.
 */
declare type StructuralFields<T> = Omit<T, FunctionPropertyNames<T>>;
/**
 * This type mimics the N4JS structural typing modifier ~w~.
 * Note that it cannot remove non-public elements due to limited expressiveness of TypeScript.
 */
declare type StructuralWriteOnly<T> = WritablePart<StructuralFields<T>>;
/**
 * This type mimics the N4JS structural typing modifier ~r~.
 * Note that it cannot remove single setters and non-public elements due to limited expressiveness of TypeScript.
 */
declare type StructuralReadOnly<T> = Readonly<StructuralFields<T>>
/**
 * This type mimics the N4JS structural typing modifier ~i~.
 * Note that it cannot remove non-public elements due to limited expressiveness of TypeScript.
 */
declare type StructuralInititializers<T> = Readonly<StructuralWriteOnly<T>>

