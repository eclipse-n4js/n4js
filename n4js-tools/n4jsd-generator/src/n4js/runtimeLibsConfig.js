export default {
	preamble: `@@Global @@ProvidedByRuntime`,
	patchFiles: {
		"es5.d.ts": {
			prefix: ``,
			suffix: `
				export external public type ReadonlyArray<T> = Array<T>;

				/**
				 * See [ECMA6] Section 19.4.
				 *
				 * A symbol is a unique and immutable data type and may be used as an identifier for object properties. The symbol
				 * object is an implicit object wrapper for the symbol primitive data type.
				 *
				 * TODO in ES6 this is defined to be a property of the global object and a constructor function
				 */
				export external public class Symbol extends Object {

					/**
					 * Creates a new, primitive symbol.
					 *
					 * @param description A description of the symbol which can be used for debugging but not to access the symbol itself.
					 */
					(description: string = undefined): symbol

					/**
					 * This "well-known" symbol may be used to define the default iterator of an object.
					 *
					 * @see ES6, 19.4.2.4
					 */
					public const iterator: symbol

					// well-known symbols moved here from file 'es2015.symbol.wellknown.d.ts'
					public const hasInstance: symbol;
					public const isConcatSpreadable: symbol;
					public const match: symbol;
					public const replace: symbol;
					public const search: symbol;
					public const species: symbol;
					public const split: symbol;
					public const toPrimitive: symbol;
					public const toStringTag: symbol;
					public const unscopables: symbol;
					// well-known symbols moved here from file 'es2018.asynciterable.d.ts'
					public const asyncIterator: symbol;
					// well-known symbols moved here from file 'es2020.symbol.wellknown.d.ts'
					public const matchAll: symbol;

					/**
					 * Returns the shared symbol with the given key from the Javascript engine's shared symbol registry.
					 * Creates a new symbol if not found.
					 *
					 * @see ES6, 19.4.2.1
					 */
					public static for(key: string): symbol

					/**
					 * Returns the key for the given shared symbol if found in the Javascript engine's shared symbol registry.
					 * Otherwise 'undefined' is returned.
					 *
					 * @see ES6, 19.4.2.5
					 */
					public static keyFor(sym: symbol): string
				}

				/**
				 * An iterator over a collection, defining a standard way to produce a sequence of values (either finite
				 * or infinite).
				 * <p>
				 * Note that in ECMAScript 6 this is not a built-in type, but a protocol. This protocol can be implemented
				 * by any object respecting some conventions.
				 */
				export external public interface ~Iterator<out T> {
					/**
					 * A zero arguments function that returns an object with two properties:
					 * <ul>
					 * <li>done (boolean)<br>
					 *     Has the value true if the iterator is past the end of the iterated sequence. In this case value
					 *     optionally specifies the return value of the iterator. The return values are explained here.
					 *     Has the value false if the iterator was able to produce the next value in the sequence. This is
					 *     equivalent of not specifying the done property altogether.
					 * <li>value<br>
					 *     any JavaScript value returned by the iterator. Can be omitted when done is true.
					 * </ul>
					 */
					public abstract next(): IteratorEntry<T>
				}

				/**
				 * Same as interface <code>Iterator</code>, but for asynchronous iteration.
				 */
				export external public interface ~AsyncIterator<out T> {
					/**
					 * A zero arguments function that returns a promise of an object with two properties:
					 * <ul>
					 * <li>done (boolean)<br>
					 *     Has the value true if the iterator is past the end of the iterated sequence. In this case value
					 *     optionally specifies the return value of the iterator. The return values are explained here.
					 *     Has the value false if the iterator was able to produce the next value in the sequence. This is
					 *     equivalent of not specifying the done property altogether.
					 * <li>value<br>
					 *     any JavaScript value returned by the iterator. Can be omitted when done is true.
					 * </ul>
					 */
					public abstract next(): Promise<IteratorEntry<T>, ?>
				}

				/**
				 * The value returned by an Iterator's method next().
				 */
				export external public interface ~IteratorEntry<out T> {
					/**
					 * Has the value true if the iterator is past the end of the iterated sequence. In this case value
					 * optionally specifies the return value of the iterator. The return values are explained here.
					 * Has the value false if the iterator was able to produce the next value in the sequence. This is
					 * equivalent of not specifying the done property altogether.
					 */
					public done: boolean;
				//	public get done(): boolean
					/**
					 * Any JavaScript value returned by the iterator. Can be omitted when done is true.
					 */
					// TODO dirty hack: to use covariant T as type of field 'value', we make the field final; but usually fields
					// in interfaces cannot be final - only works because no validation in n4ts!
					@Final public value?: T;
					// this would be the alternative, but then we would lose the optionality of 'value':
				//	public get value(): T
				}

				/**
				 * An object that can be iterated over. Whenever an object needs to be iterated (such as at the beginning
				 * of a for..of loop), its <code>[Symbol.iterator]</code> method is called with no arguments, and the
				 * returned iterator is used to obtain the values to be iterated.
				 * <p>
				 * Note that in ECMAScript 6 this is not a built-in type, but a protocol. This protocol can be implemented
				 * by any object respecting some conventions.
				 * <p>
				 * For asynchronous iteration use interface <code>AsyncIterable</code> instead.
				 */
				export external public interface ~Iterable<out T> {
					/**
					 * Returns an object conforming to the iteration protocol.
					 */
					public abstract [Symbol.iterator](): Iterator<T>
				}

				/**
				 * Same as interface <code>Iterable</code>, but for asynchronous iteration.
				 */
				export external public interface ~AsyncIterable<out T> {
					/**
					 * Returns an object conforming to the asynchronous iteration protocol.
					 */
					public abstract [Symbol.asyncIterator](): AsyncIterator<T>
				}


				/**
				 * An iterator over a collection, defining a standard way to produce a sequence of values (either finite
				 * or infinite).
				 * <p>
				 * Note that in ECMAScript 6 this is not a built-in type, but a protocol. This protocol can be implemented
				 * by any object respecting some conventions.
				 */
				export external public interface ~Generator<out TYield, out TReturn, in TNext> extends Iterable<TYield>, Iterator<TYield> {

					/**
					 * A one (optional) argument function. The (optional) argument <code>value</code> is passed to the
					 * current yield expression in the generator function. The return is an <code>IteratorEntry</code>
					 * with two properties:
					 * <ul>
					 * <li>done (boolean)<br>
					 *     Has the value true if the iterator is past the end of the iterated sequence. In this case value
					 *     optionally specifies the return value of the iterator. The return values are explained here.
					 *     Has the value false if the iterator was able to produce the next value in the sequence. This is
					 *     equivalent of not specifying the done property altogether.
					 * <li>value<br>
					 *     any JavaScript value returned by the iterator. Can be omitted when done is true.
					 * </ul>
					 */
					@Override public abstract next(value: TNext = undefined): IteratorEntry<TYield>

					/**
					 * A zero arguments function that returns the <code>this</code> object, conforming to the iterator
					 * interface.
					 * <p>
					 * Note that the iterator can only be iterated once.
					 */
					@Override public abstract [Symbol.iterator](): Generator<TYield, TReturn, TNext>

					/**
					 * A one argument function. The argument <code>exception</code> is thrown at the current yield
					 * expression in the generator function. The return is an <code>IteratorEntry</code> with two
					 * properties:
					 * <ul>
					 * <li>done (boolean)<br>
					 *     Has the value true if the iterator is past the end of the iterated sequence. In this case value
					 *     optionally specifies the return value of the iterator. The return values are explained here.
					 *     Has the value false if the iterator was able to produce the next value in the sequence. This is
					 *     equivalent of not specifying the done property altogether.
					 * <li>value<br>
					 *     any JavaScript value returned by the iterator. Can be omitted when done is true.
					 * </ul>
					 */
					public abstract throw(exception: any): IteratorEntry<TYield>;

				    /**
					 * A one (optional) argument function. The (optional) argument <code>value</code> is passed to the
					 * current yield expression in the generator function. The return is an <code>IteratorEntry</code> with
					 * two properties:
					 * <ul>
					 * <li>done (boolean)<br>
					 *     Has the value true if the iterator is past the end of the iterated sequence. In this case value
					 *     optionally specifies the return value of the iterator. The return values are explained here.
					 *     Has the value false if the iterator was able to produce the next value in the sequence. This is
					 *     equivalent of not specifying the done property altogether.
					 * <li>value<br>
					 *     any JavaScript value returned by the iterator. Can be omitted when done is true.
					 * </ul>
					 */
					public abstract return(value: TNext = undefined): IteratorEntry<TReturn>;
				}

				/**
				 * An asynchronous generator object. See interface <code>Generator</code> for details.
				 */
				export external public interface ~AsyncGenerator<out TYield, out TReturn, in TNext> extends AsyncIterable<TYield>, AsyncIterator<TYield> {

					/**
					 * A one (optional) argument function. The (optional) argument <code>value</code> is passed to the
					 * current yield expression in the generator function. Return a promise of an <code>IteratorEntry</code>
					 * with two properties:
					 * <ul>
					 * <li>done (boolean)<br>
					 *     Has the value true if the iterator is past the end of the iterated sequence. In this case value
					 *     optionally specifies the return value of the iterator. The return values are explained here.
					 *     Has the value false if the iterator was able to produce the next value in the sequence. This is
					 *     equivalent of not specifying the done property altogether.
					 * <li>value<br>
					 *     any JavaScript value returned by the iterator. Can be omitted when done is true.
					 * </ul>
					 */
					@Override public abstract next(value: TNext = undefined): Promise<IteratorEntry<TYield>, ?>

					/**
					 * A zero arguments function that returns the <code>this</code> object, conforming to the asynchronous
					 * iterator interface.
					 * <p>
					 * Note that the iterator can only be iterated once.
					 */
					@Override public abstract [Symbol.asyncIterator](): AsyncGenerator<TYield, TReturn, TNext>

					/**
					 * A one argument function. The argument <code>exception</code> is thrown at the current yield
					 * expression in the generator function. Returns a promise of an <code>IteratorEntry</code> with two
					 * properties:
					 * <ul>
					 * <li>done (boolean)<br>
					 *     Has the value true if the iterator is past the end of the iterated sequence. In this case value
					 *     optionally specifies the return value of the iterator. The return values are explained here.
					 *     Has the value false if the iterator was able to produce the next value in the sequence. This is
					 *     equivalent of not specifying the done property altogether.
					 * <li>value<br>
					 *     any JavaScript value returned by the iterator. Can be omitted when done is true.
					 * </ul>
					 */
					public abstract throw(exception: any): Promise<IteratorEntry<TYield>, any>;

				    /**
					 * A one (optional) argument function. The (optional) argument <code>value</code> is passed to the
					 * current yield expression in the generator function. Returns a promise of an <code>IteratorEntry</code> with
					 * two properties:
					 * <ul>
					 * <li>done (boolean)<br>
					 *     Has the value true if the iterator is past the end of the iterated sequence. In this case value
					 *     optionally specifies the return value of the iterator. The return values are explained here.
					 *     Has the value false if the iterator was able to produce the next value in the sequence. This is
					 *     equivalent of not specifying the done property altogether.
					 * <li>value<br>
					 *     any JavaScript value returned by the iterator. Can be omitted when done is true.
					 * </ul>
					 */
					public abstract return(value: TNext = undefined): Promise<IteratorEntry<TReturn>, ?>;
				}

				/**
				 * A promise represents a proxy for a value that is usually not yet known
				 * when the promise is created.
				 * <p>
				 * A promise can exist in either of two states: "pending" and "settled".
				 * If the result value of a promise is not yet determined, the promise is
				 * "pending". Once the value has been determined, the promise becomes
				 * "settled".
				 * <p>
				 * A promise can either be fulfilled or rejected. A fulfilled promise
				 * indicates a successful completion of whatever asynchronous process it
				 * encapsulates. A rejected promise indicates erroneous or abnormal completion
				 * of the process.
				 */
				export external public class Promise<out S, out F> extends Object {

					public constructor(
						executor: {function(
							resolveFn:{function(arg: S = undefined):void},
							rejectFn: {function(arg: F = undefined):void}
						): void}
					)

					public <Snew, Fnew> then(
						onFulfilled: {function(arg:S = undefined): union{Snew, Promise<Snew, Fnew>}?},
						onRejected: {function(arg:F = undefined): union{Fnew, Promise<Snew, Fnew>}?} = undefined
					): Promise<Snew, Fnew>

					public <Snew, Fnew> catch(
						onRejected: {function(arg:F = undefined): union{Fnew, Promise<Snew, Fnew>}?}
					): Promise<Snew, Fnew>

					public static <S, F> reject(f: F): Promise<S, F>
					public static <S, F> resolve(
						s: union{
							Promise<S, F>,
							~Object with { then(...args: any): Promise<S, F>; }, // i.e. thenable
							S
						}
					): Promise<S, F>

					public static <T> all(iterable: Iterable<union{Promise<T,?>, T}>): Promise<Array<T>, any>
					public static <T> race(iterable: Iterable<union{Promise<T,?>, T}>): Promise<T, any>
				}

				export external public type PromiseConstructor = constructor{Promise};


				export external public interface ~ArrayBufferView {
					/**
					 * The ArrayBuffer instance referenced by the array.
					 */
					get buffer(): ArrayBufferLike;

					/**
					 * The length in bytes of the array.
					 */
					get byteLength(): number;

					/**
					 * The offset in bytes of the array.
					 */
					get byteOffset(): number;
				}
			`,
			ignore: [
				// temporarily ignored, because we need our own definition:
				"Symbol", "Promise",
				// unfortunately we have to replace ArrayBufferView by our own definition (see suffix above), because TypeScript is
				// using writable fields in ArrayBufferView but the types expected to be subtypes (e.g. Uint8Array) only provide
				// readonly fields / getters, meaning they would not actually be subtypes when applying strict subtype checking:
				"ArrayBufferView",
				// stuff defined in primitive_js.n4ts and global.n4jsd:
				"NaN", "Infinity", "eval", "parseInt", "parseFloat", "isNaN", "isFinite", "decodeURI", "decodeURIComponent",
				"encodeURI", "encodeURIComponent", "escape", "unescape",
				// read-only types:
				"ReadonlyArray", "TemplateStringsArray",
				// type constructors:
				"Partial", "Required", "Readonly", "Pick", "Record", "Exclude", "Extract", "Omit", "NonNullable",
				"Parameters", "ConstructorParameters", "ReturnType", "InstanceType", "Uppercase", "Lowercase",
				"Capitalize", "Uncapitalize", "Awaited",
				// decorators:
				"ClassDecorator", "PropertyDecorator", "MethodDecorator", "ParameterDecorator",
				// odd stuff:
				"ThisParameterType", "OmitThisParameter", "CallableFunction", "NewableFunction", "PromiseConstructorLike"
			],
			polyfills: [],
			changeToClass: [
				"RegExpMatchArray",
				"RegExpExecArray"
			],
			ctorInstanceTypes: [
				"Symbol", "SymbolConstructor",
				"Promise", "PromiseConstructor"
			],
			patchMembers: {
				// required due to odd return types on TypeScript side
				"Function#bind": { replaceBy: "public bind(thisArg: any, ...args: any): {function(... args: any): any}" }, // return type in .d.ts is 'any' instead of a function
				"Object#valueOf": { replaceBy: "public valueOf(): any" }, // return type in .d.ts is 'Object' causing errors when overridden to return a primitve type
				"Object#getPrototypeOf": { replaceBy: "public static getPrototypeOf(o: any): Object;" }, // return type in .d.ts is 'any' instead of 'Object'
				// required due to stricter type checking in N4JS (type of searchElement must be 'any' instead of 'T' on N4JS side)
				"Array#indexOf": { replaceBy: "public indexOf(searchElement: any, fromIndex: number = ): number;" },
				"Array#lastIndexOf": { replaceBy: "public lastIndexOf(searchElement: any, fromIndex: number = ): number;" },
				// required due to different signature
				"Array#map": { replaceBy: "public <U, ThisT extends Object> map(callback: {function(value: T, index: number, traversedObject: Array<T>) : U}, thisObject: ThisT = undefined): Array<U>;" },
				// required due to overloading:
				"Object#()": { replaceBy: "(...args: any): Object;" },
				"Object#create": { replaceBy: "public static create(proto: Object, props: Object = undefined): Object;" },
				"Object#freeze": { replaceBy: "public static <T extends Object> freeze(obj: T): T;" },
				"String#localeCompare": { replaceBy: "public localeCompare(that: string, locales: string = undefined, options: any+ = undefined): number;" },
				"String#replace": { replaceBy: "public replace(searchValue: union{RegExp, string}, replaceValue: union{string, Function}): string;" },
				"Array#constructor": { replaceBy: "public constructor(first: union{number, T} = undefined, ...items: T);  // TODO: signature is type unsafe, allows to create arrays with elements of wrong type, new Array<string>(5, 'x', 'y'), but cannot write constructor(items : union {number, T... items} = undefined)" },
				"Array#concat": { replaceBy: "public concat(...items: union {T, Array<? extends T>}): Array<T>;" },
				"Array#splice": { replaceBy: "public splice(start: number = undefined, deleteCount: number = undefined, ...items: T): Array<T>;" },
				"Array#every": { replaceBy: "public <ThisT extends Object> every(callback: {function(value: T, index: number, traversedObject: Array<T>)}, thisObject: ThisT = undefined): boolean;" },
				"Array#filter": { replaceBy: "public <ThisT extends Object> filter(callback: {function(value: T, index: number, traversedObject: Array<T>) : boolean}, thisObject: ThisT = undefined): Array<T>;" },
				"Array#reduce": { replaceBy: "public <MemoT> reduce(callback: {function(previousValue: MemoT, currentValue: T, index: number, traversedObject: Array<T>) : MemoT}, initialValue: MemoT = undefined): MemoT;" },
				"Array#reduceRight": { replaceBy: "public <MemoT> reduceRight(callback: {function(previousValue: MemoT, currentValue: T, index: number, traversedObject: Array<T>) : MemoT}, initialValue: MemoT = undefined): MemoT;" },
				"Date#constructor": { replaceBy: "public constructor(numberOrStringOrYear: union{string, number} = undefined, month: number = undefined, date: number = undefined, hours: number = undefined, minutes: number = undefined, seconds: number = undefined, ms: number = undefined);" },
				"RegExp#constructor": { replaceBy: "public constructor(pattern: string = undefined, flags: string = undefined);" },
				"JSON#stringify": { replaceBy: "public static stringify(value: any, replacer: union{Array<?>, {function(key: string, value: any) : any} } = undefined, space: union{number , string} = undefined): string;" },
				// required because optional methods used in .d.ts and not supported in N4JS
				"PropertyDescriptor#get": { replaceBy: "get?: ()=>any;" },
				"PropertyDescriptor#set": { replaceBy: "set?: (value: any)=>void;" },
				// missing @Override annotations for methods #valueOf(), #toString(), #toLocaleString()
				"Function#toString": { addAnnotations: [ "@Override" ] },
				"String#valueOf": { addAnnotations: [ "@Override" ] },
				"String#toString": { addAnnotations: [ "@Override" ] },
				"Boolean#valueOf": { addAnnotations: [ "@Override" ] },
				"Number#valueOf": { addAnnotations: [ "@Override" ] },
				"Number#toString": { addAnnotations: [ "@Override" ] },
				"Number#toLocaleString": { addAnnotations: [ "@Override" ] },
				"Date#valueOf": { addAnnotations: [ "@Override" ] },
				"Date#toString": { addAnnotations: [ "@Override" ] },
				"Date#toLocaleString": { addAnnotations: [ "@Override" ] },
				"Array#toString": { addAnnotations: [ "@Override" ] },
				"Array#toLocaleString": { addAnnotations: [ "@Override" ] },
				"Int8Array#valueOf": { addAnnotations: [ "@Override" ] },
				"Int8Array#toString": { addAnnotations: [ "@Override" ] },
				"Int8Array#toLocaleString": { addAnnotations: [ "@Override" ] },
				"Uint8Array#valueOf": { addAnnotations: [ "@Override" ] },
				"Uint8Array#toString": { addAnnotations: [ "@Override" ] },
				"Uint8Array#toLocaleString": { addAnnotations: [ "@Override" ] },
				"Uint8ClampedArray#valueOf": { addAnnotations: [ "@Override" ] },
				"Uint8ClampedArray#toString": { addAnnotations: [ "@Override" ] },
				"Uint8ClampedArray#toLocaleString": { addAnnotations: [ "@Override" ] },
				"Int16Array#valueOf": { addAnnotations: [ "@Override" ] },
				"Int16Array#toString": { addAnnotations: [ "@Override" ] },
				"Int16Array#toLocaleString": { addAnnotations: [ "@Override" ] },
				"Uint16Array#valueOf": { addAnnotations: [ "@Override" ] },
				"Uint16Array#toString": { addAnnotations: [ "@Override" ] },
				"Uint16Array#toLocaleString": { addAnnotations: [ "@Override" ] },
				"Int32Array#valueOf": { addAnnotations: [ "@Override" ] },
				"Int32Array#toString": { addAnnotations: [ "@Override" ] },
				"Int32Array#toLocaleString": { addAnnotations: [ "@Override" ] },
				"Uint32Array#valueOf": { addAnnotations: [ "@Override" ] },
				"Uint32Array#toString": { addAnnotations: [ "@Override" ] },
				"Uint32Array#toLocaleString": { addAnnotations: [ "@Override" ] },
				"Float32Array#valueOf": { addAnnotations: [ "@Override" ] },
				"Float32Array#toString": { addAnnotations: [ "@Override" ] },
				"Float32Array#toLocaleString": { addAnnotations: [ "@Override" ] },
				"Float64Array#valueOf": { addAnnotations: [ "@Override" ] },
				"Float64Array#toString": { addAnnotations: [ "@Override" ] }
			},
			appendCode: {
				"Object": `
					/**
					 * The built-in ES5 property "__proto__", here modeled as a read-only property (i.e. no setter provided), because
					 * changing an object's prototype after creation is strongly discouraged in todays Javascript engines.
					 */
					public get __proto__(): Object;
					/**
					 * The initial value of Object.prototype is the standard built-in Object prototype object (15.2.4).
					 *
					 * Remark: Modeled as getter to emulate const and to allow for overriding.
					 * @see ES5, 15.2.3.1
					 */
					public static get prototype(): Object;
				`,
				"String": `
					/**
					 * Returns a new Iterator object that iterates over the code points of a String value, returning each code point as
					 * a String value.
					 */
					public [Symbol.iterator](): Iterator<string>
				`,
				"Array": `
					public [Symbol.iterator](): Iterator<T>;
					/**
					 * The initial value of Array.prototype is the Array prototype object (15.4.4).
					 *
					 * @see ES5 15.4.3.1
					 */
					@Override public static get prototype(): Array<? extends any>
				`,
				"Function": `
					/**
					 * The Function prototype object is itself a Function object (its [[Class]] is "Function") that, when invoked, accepts any arguments and returns undefined.
					 */
					@Override public static get prototype(): Function;
					/**
					 * The value of the prototype property is used to initialize the [[Prototype]] internal property of a newly created object before the Function object is invoked as a constructor for that newly created object. This property has the attribute { [[Writable]]: true, [[Enumerable]]: false, [[Configurable]]: false }.
					 * NOTE Function objects created using Function.prototype.bind do not have a prototype property.
					 *
					 * @see ES5 15.3.5.2
					 */
					public get prototype(): Object;
				`
			}
		},
		"es2015.core.d.ts": {
			ignore: [
				// read-only types:
				"ReadonlyArray"
			],
			patchMembers: {
				"Object#keys": { addAnnotations: [ "@Override" ] },
				"Date#constructor": undefined,
				"RegExp#constructor": undefined,
				// required due to overloading:
				"Array#find": { replaceBy: "public find(predicate: (T, int=, T[]=) => boolean, thisArg: Object=): T;" },
				"Array#from": { replaceBy: "public static <I> from(source: Iterable<I>|ArrayLike<I>|Iterator<I>|string, mapFn: (I, int) => any=, thisArg: Object=): I[];" }
			}
		},
		"es2015.collection.d.ts": {
			ignore: [
				// read-only types:
				"ReadonlySet", "ReadonlyMap"
			],
			patchMembers: {
				// TypeScript provides constructors accepting Iterables via overloading in file "es2015.iterable.d.ts" (which we do not support):
				"Map#constructor": { replaceBy: "public constructor(entries: Iterable<Iterable2<K,V>> = );" },
				"WeakMap#constructor": { replaceBy: "public constructor(entries: Iterable<Iterable2<K,V>> = );" },
				"Set#constructor": { replaceBy: "public constructor(values: Iterable<T> = );" },
				"WeakSet#constructor": { replaceBy: "public constructor(values: Iterable<T> = );" },
			},
			suffix: `
				export external public type ReadonlySet<T> = Set<T>;
				export external public type ReadonlyMap<K,V> = Map<K,V>;
			`
		},
		"es2015.symbol.d.ts": {
			ignore: [
				"Symbol", "SymbolConstructor" // Symbol was moved to es5.n4jsd (see above)
			]
		},
		"es2015.symbol.wellknown.d.ts": {
			ignore: [
				"Symbol", "SymbolConstructor", // Symbol was moved to es5.n4jsd (see above)
				"Promise", "PromiseConstructor" // this would work fine, except for different number of type parameters
			],
			patchMembers: {
				// the signatures of the following members use computed property names inside ~Object with {}
				"String#replace": undefined,
				"String#match": undefined,
				"String#search": undefined,
				"String#split": undefined
			}
		},
		"es2015.iterable.d.ts": {
			ignore: [
				"IterableIterator", // replaced by modified declaration in suffix below (see below for details)
				"Iterator", // Iterator was moved to es5.n4jsd (see above)
				"Iterable", // Iterable was moved to es5.n4jsd (see above)
				"Promise", "PromiseConstructor", // Promise was moved to es5.n4jsd (see above)
				// read-only types:
				"ReadonlyArray", "ReadonlySet", "ReadonlyMap"
			],
			patchMembers: {
				"Symbol#iterator": undefined, // was moved to es5.n4jsd (see above)
				"Array#[Symbol.iterator]": undefined, // was moved to es5.n4jsd (see above)
				"Array#from": undefined,
				"String#[Symbol.iterator]": undefined, // was moved to es5.n4jsd (see above)
				"Map#constructor": undefined, // the signature added here via overloading was moved to "es2015.collection.d.ts" (see above)
				"WeakMap#constructor": undefined, // the signature added here via overloading was moved to "es2015.collection.d.ts" (see above)
				"Set#constructor": undefined, // the signature added here via overloading was moved to "es2015.collection.d.ts" (see above)
				"WeakSet#constructor": undefined, // the signature added here via overloading was moved to "es2015.collection.d.ts" (see above)
				"Uint8ClampedArray#constructor": undefined,
				"Uint8ClampedArray#from": undefined,
				"Int8Array#constructor": undefined,
				"Int8Array#from": undefined,
				"Uint8Array#constructor": undefined,
				"Uint8Array#from": undefined,
				"Uint8Clampe#constructor": undefined,
				"Uint8Clampe#from": undefined,
				"Int16Array#constructor": undefined,
				"Int16Array#from": undefined,
				"Uint16Array#constructor": undefined,
				"Uint16Array#from": undefined,
				"Int32Array#constructor": undefined,
				"Int32Array#from": undefined,
				"Uint32Array#constructor": undefined,
				"Uint32Array#from": undefined,
				"Float32Array#constructor": undefined,
				"Float32Array#from": undefined,
				"Float64Array#constructor": undefined,
				"Float64Array#from": undefined
			},
			// we need to provide our own modified variant of IterableIterator, because
			// 1) we must add "out" modifier to type parameter,
			// 2) extend interface "Iterable" explicitly to avoid error message "All N4Objects must explicitly extend/implement definition site structural type Iterable<?>.",
			// 3) due to 2), @Override annotation must be added.
			suffix: `
				export external public interface ~IterableIterator<out T> extends Iterator<T>, Iterable<T> {
					@Override
					[Symbol.iterator](): IterableIterator<T>;
				}
			`
		},
		"es2015.generator.d.ts": {
			ignore: [
				"Generator" // Generator was moved to es5.n4jsd (see above)
			],
			patchMembers: {
				"GeneratorFunction#()": undefined,           // callable ctor not supported in interface
				"GeneratorFunctionConstructor#()": undefined // callable ctor not supported in interface
			}
		},
		"es2015.promise.d.ts": {
			ignore: [
				"Promise", "PromiseConstructor" // Promise was moved to es5.n4jsd (see above)
			]
		},
		"es2015.proxy.d.ts": {
			ignore: [
				// need to replace ProxyHandler entirely, because of
				// 1) heavy use of optional methods (not supported in N4JS), and
				// 2) use of unfortunate type 'any' instead of 'Object' for parameters "thisArg" and "receiver"
				"ProxyHandler"
			],
			suffix: `
				export external public interface ~ProxyHandler<T> {
					apply?: (target: T, thisArg: Object, argArray: Array<any>) => any;
					construct?: (target: T, argArray: Array<any>, newTarget: Function) => Object;
					defineProperty?: (target: T, p: string | symbol, attributes: PropertyDescriptor) => boolean;
					deleteProperty?: (target: T, p: string | symbol) => boolean;
					get?: (target: T, p: string | symbol, receiver: Object) => any;
					getOwnPropertyDescriptor?: (target: T, p: string | symbol) => PropertyDescriptor;
					getPrototypeOf?: (target: T) => Object;
					has?: (target: T, p: string | symbol) => boolean;
					isExtensible?: (target: T) => boolean;
					ownKeys?: (target: T) => ArrayLike<string | symbol>;
					preventExtensions?: (target: T) => boolean;
					set?: (target: T, p: string | symbol, value: any, receiver: Object) => boolean;
					setPrototypeOf?: (target: T, v: Object) => boolean;
				}
			`
		},
		"es2015.reflect.d.ts": {},
		"es2016.array.include.d.ts": {
			ignore: [
				// read-only types:
				"ReadonlyArray"
			]
		},
		"es2017.string.d.ts": {},
		"es2017.object.d.ts": {
			patchMembers: {
				// required due to overloading:
				"Object#values": { replaceBy: "public static values(obj: Object): any[];" },
				"Object#entries": { replaceBy: "public static entries(obj: Object): Iterable2<string, any>[];" }
			}
		},
		"es2017.sharedmemory.d.ts": {},
		"es2017.typedarrays.d.ts": {
			patchMembers: {
				// cannot add overload signatures via polyfill:
				"Int8Array#constructor": undefined,
				"Uint8Array#constructor": undefined,
				"Uint8ClampedArray#constructor": undefined,
				"Int16Array#constructor": undefined,
				"Uint16Array#constructor": undefined,
				"Int32Array#constructor": undefined,
				"Uint32Array#constructor": undefined,
				"Float32Array#constructor": undefined,
				"Float64Array#constructor": undefined
			}
		},
		"es2017.intl.d.ts": {},
		"es2018.asynciterable.d.ts": {
			ignore: [
				"AsyncIterator", // AsyncIterator was moved to es5.n4jsd (see above)
				"AsyncIterable" // AsyncIterable was moved to es5.n4jsd (see above)
			],
			patchMembers: {
				"Symbol#asyncIterator": undefined // was moved to es5.n4jsd (see above)
			}
		},
		"es2018.asyncgenerator.d.ts": {
			ignore: [
				"AsyncGenerator" // AsyncGenerator was moved to es5.n4jsd (see above)
			],
			patchMembers: {
				"AsyncGeneratorFunction#()": undefined,           // callable ctor not supported in interface
				"AsyncGeneratorFunctionConstructor#()": undefined // callable ctor not supported in interface
			}
		},
		"es2018.promise.d.ts": {
			ignore: [
				"Promise", "PromiseConstructor" // this would work fine, except for different number of type parameters
			],
			suffix: `
				@Polyfill
				export external public class Promise<out S, out F> extends Promise<S,F> {
					public finally(onfinally: (()=>void) = ): Promise<S,F>;
				}
			`
		},
		"es2018.regexp.d.ts": {
			polyfills: [
				"RegExpMatchArray",
				"RegExpExecArray"
			],
			changeToClass: [
				"RegExpMatchArray",
				"RegExpExecArray"
			],
		},
		"es2018.intl.d.ts": {},
		"es2019.string.d.ts": {},
		"es2019.object.d.ts": {},
		"es2019.array.d.ts": {
			ignore: [
				// read-only types:
				"ReadonlyArray"
			]
		},
		"es2019.symbol.d.ts": {},
		"es2020.bigint.d.ts": {
			patchMembers: {
				"BigInt#toString": { addAnnotations: [ "@Override" ] },
				"BigInt#toLocaleString": { addAnnotations: [ "@Override" ] },
				"BigInt#valueOf": { addAnnotations: [ "@Override" ] },
				"BigInt64Array#toString": { addAnnotations: [ "@Override" ] },
				"BigInt64Array#toLocaleString": { addAnnotations: [ "@Override" ] },
				"BigInt64Array#valueOf": { addAnnotations: [ "@Override" ] },
				"BigUint64Array#toString": { addAnnotations: [ "@Override" ] },
				"BigUint64Array#toLocaleString": { addAnnotations: [ "@Override" ] },
				"BigUint64Array#valueOf": { addAnnotations: [ "@Override" ] }
			}
		},
		"es2020.string.d.ts": {},
		"es2020.promise.d.ts": {
			ignore: [
				"Promise", "PromiseConstructor" // this would work fine, except for different number of type parameters
			],
			suffix: `
				@Polyfill
				export external public class Promise<out S, out F> extends Promise<S,F> {
					public static allSettled(...args: any+): any+; // overloading not supported
				}
			`
		},
		"es2020.sharedmemory.d.ts": {
			patchMembers: {
				// these are cases of adding overloads to existing methods:
				"Atomics#add": undefined,
				"Atomics#and": undefined,
				"Atomics#compareExchange": undefined,
				"Atomics#exchange": undefined,
				"Atomics#load": undefined,
				"Atomics#or": undefined,
				"Atomics#store": undefined,
				"Atomics#sub": undefined,
				"Atomics#wait": undefined,
				"Atomics#notify": undefined,
				"Atomics#xor": undefined
			}
		},
		"es2020.symbol.wellknown.d.ts": {
			patchMembers: {
				"Symbol#matchAll": undefined // was moved to es5.n4jsd (see above)
			}
		},
		"es2020.intl.d.ts": {},
		/*
		"dom.generated.d.ts": {
			ignore: [
				"*#prototype",
				"*#addEventListener#signature0",
				"*#removeEventListener#signature0"
			],
			patchMembers: {
				// @Override annotations:
				// misc. methods ====================================================================================
				"AudioBufferSourceNode#start": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#a": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#b": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#c": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#d": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#e": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#f": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m11": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m12": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m13": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m14": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m21": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m22": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m23": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m24": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m31": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m32": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m33": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m34": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m41": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m42": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m43": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#m44": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#fromFloat32Array": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#fromFloat64Array": { addAnnotations: [ "@Override" ] },
				"DOMMatrix#fromMatrix": { addAnnotations: [ "@Override" ] },
				"DOMPoint#w": { addAnnotations: [ "@Override" ] },
				"DOMPoint#x": { addAnnotations: [ "@Override" ] },
				"DOMPoint#y": { addAnnotations: [ "@Override" ] },
				"DOMPoint#z": { addAnnotations: [ "@Override" ] },
				"DOMPoint#fromPoint": { addAnnotations: [ "@Override" ] },
				"DOMRect#height": { addAnnotations: [ "@Override" ] },
				"DOMRect#width": { addAnnotations: [ "@Override" ] },
				"DOMRect#x": { addAnnotations: [ "@Override" ] },
				"DOMRect#y": { addAnnotations: [ "@Override" ] },
				"DOMRect#fromRect": { addAnnotations: [ "@Override" ] },
				"Document#getElementById": { addAnnotations: [ "@Override" ] },
				"DocumentFragment#getElementById": { addAnnotations: [ "@Override" ] },
				"HTMLOptionsCollection#length": { addAnnotations: [ "@Override" ] },
				"HTMLSelectElement#remove": { addAnnotations: [ "@Override" ] },
				"PerformanceNavigationTiming#toJSON": { addAnnotations: [ "@Override" ] },
				"PerformanceResourceTiming#toJSON": { addAnnotations: [ "@Override" ] },
				"Text#assignedSlot": { addAnnotations: [ "@Override" ] },
				"TextDecoderStream#readable": { addAnnotations: [ "@Override" ] },
				"TextDecoderStream#writable": { addAnnotations: [ "@Override" ] },
				"TextEncoderStream#readable": { addAnnotations: [ "@Override" ] },
				"TextEncoderStream#writable": { addAnnotations: [ "@Override" ] },
				"WindowEventMap#gamepadconnected": { addAnnotations: [ "@Override" ] },
				"WindowEventMap#gamepaddisconnected": { addAnnotations: [ "@Override" ] },
				// method ownerDocument =============================================================================
				"Attr#ownerDocument": { addAnnotations: [ "@Override" ] },
				"CharacterData#ownerDocument": { addAnnotations: [ "@Override" ] },
				"Document#ownerDocument": { addAnnotations: [ "@Override" ] },
				"DocumentFragment#ownerDocument": { addAnnotations: [ "@Override" ] },
				"DocumentType#ownerDocument": { addAnnotations: [ "@Override" ] },
				"Element#ownerDocument": { addAnnotations: [ "@Override" ] },
				"ProcessingInstruction#ownerDocument": { addAnnotations: [ "@Override" ] },
				// method toString ==================================================================================
				"DOMMatrixReadOnly#toString": { addAnnotations: [ "@Override" ] },
				"DOMTokenList#toString": { addAnnotations: [ "@Override" ] },
				"Location#toString": { addAnnotations: [ "@Override" ] },
				"MediaList#toString": { addAnnotations: [ "@Override" ] },
				"Range#toString": { addAnnotations: [ "@Override" ] },
				"Selection#toString": { addAnnotations: [ "@Override" ] },
				"URL#toString": { addAnnotations: [ "@Override" ] },
				"URLSearchParams#toString": { addAnnotations: [ "@Override" ] },
				// method addEventListener ==========================================================================
				"AbortSignal#addEventListener": { addAnnotations: [ "@Override" ] },
				"Animation#addEventListener": { addAnnotations: [ "@Override" ] },
				"AudioBufferSourceNode#addEventListener": { addAnnotations: [ "@Override" ] },
				"AudioContext#addEventListener": { addAnnotations: [ "@Override" ] },
				"AudioScheduledSourceNode#addEventListener": { addAnnotations: [ "@Override" ] },
				"AudioWorkletNode#addEventListener": { addAnnotations: [ "@Override" ] },
				"BaseAudioContext#addEventListener": { addAnnotations: [ "@Override" ] },
				"BroadcastChannel#addEventListener": { addAnnotations: [ "@Override" ] },
				"CSSAnimation#addEventListener": { addAnnotations: [ "@Override" ] },
				"CSSTransition#addEventListener": { addAnnotations: [ "@Override" ] },
				"ConstantSourceNode#addEventListener": { addAnnotations: [ "@Override" ] },
				"Document#addEventListener": { addAnnotations: [ "@Override" ] },
				"Element#addEventListener": { addAnnotations: [ "@Override" ] },
				"EventSource#addEventListener": { addAnnotations: [ "@Override" ] },
				"FileReader#addEventListener": { addAnnotations: [ "@Override" ] },
				"FontFaceSet#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLAnchorElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLAreaElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLAudioElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLBRElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLBaseElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLBodyElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLButtonElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLCanvasElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDListElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDataElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDataListElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDetailsElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDirectoryElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDivElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDocument#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLEmbedElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLFieldSetElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLFontElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLFormElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLFrameElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLFrameSetElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLHRElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLHeadElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLHeadingElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLHtmlElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLIFrameElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLImageElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLInputElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLLIElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLLabelElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLLegendElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLLinkElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLMapElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLMarqueeElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLMediaElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLMenuElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLMetaElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLMeterElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLModElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLOListElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLObjectElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLOptGroupElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLOptionElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLOutputElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLParagraphElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLParamElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLPictureElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLPreElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLProgressElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLQuoteElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLScriptElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLSelectElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLSlotElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLSourceElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLSpanElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLStyleElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTableCaptionElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTableCellElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTableColElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTableElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTableRowElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTableSectionElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTemplateElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTextAreaElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTimeElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTitleElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTrackElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLUListElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLUnknownElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLVideoElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"IDBDatabase#addEventListener": { addAnnotations: [ "@Override" ] },
				"IDBOpenDBRequest#addEventListener": { addAnnotations: [ "@Override" ] },
				"IDBRequest#addEventListener": { addAnnotations: [ "@Override" ] },
				"IDBTransaction#addEventListener": { addAnnotations: [ "@Override" ] },
				"MathMLElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"MediaDevices#addEventListener": { addAnnotations: [ "@Override" ] },
				"MediaKeySession#addEventListener": { addAnnotations: [ "@Override" ] },
				"MediaQueryList#addEventListener": { addAnnotations: [ "@Override" ] },
				"MediaRecorder#addEventListener": { addAnnotations: [ "@Override" ] },
				"MediaSource#addEventListener": { addAnnotations: [ "@Override" ] },
				"MediaStream#addEventListener": { addAnnotations: [ "@Override" ] },
				"MediaStreamTrack#addEventListener": { addAnnotations: [ "@Override" ] },
				"MessagePort#addEventListener": { addAnnotations: [ "@Override" ] },
				"Notification#addEventListener": { addAnnotations: [ "@Override" ] },
				"OfflineAudioContext#addEventListener": { addAnnotations: [ "@Override" ] },
				"OscillatorNode#addEventListener": { addAnnotations: [ "@Override" ] },
				"PaymentRequest#addEventListener": { addAnnotations: [ "@Override" ] },
				"Performance#addEventListener": { addAnnotations: [ "@Override" ] },
				"PermissionStatus#addEventListener": { addAnnotations: [ "@Override" ] },
				"PictureInPictureWindow#addEventListener": { addAnnotations: [ "@Override" ] },
				"RTCDTMFSender#addEventListener": { addAnnotations: [ "@Override" ] },
				"RTCDataChannel#addEventListener": { addAnnotations: [ "@Override" ] },
				"RTCDtlsTransport#addEventListener": { addAnnotations: [ "@Override" ] },
				"RTCPeerConnection#addEventListener": { addAnnotations: [ "@Override" ] },
				"RemotePlayback#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGAElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGAnimateElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGAnimateMotionElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGAnimateTransformElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGAnimationElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGCircleElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGClipPathElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGComponentTransferFunctionElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGDefsElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGDescElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGEllipseElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEBlendElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEColorMatrixElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEComponentTransferElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFECompositeElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEConvolveMatrixElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEDiffuseLightingElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEDisplacementMapElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEDistantLightElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEDropShadowElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEFloodElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEFuncAElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEFuncBElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEFuncGElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEFuncRElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEGaussianBlurElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEImageElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEMergeElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEMergeNodeElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEMorphologyElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEOffsetElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEPointLightElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFESpecularLightingElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFESpotLightElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFETileElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFETurbulenceElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFilterElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGForeignObjectElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGGElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGGeometryElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGGradientElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGGraphicsElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGImageElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGLineElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGLinearGradientElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGMPathElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGMarkerElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGMaskElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGMetadataElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGPathElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGPatternElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGPolygonElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGPolylineElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGRadialGradientElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGRectElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGSVGElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGScriptElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGSetElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGStopElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGStyleElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGSwitchElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGSymbolElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGTSpanElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGTextContentElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGTextElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGTextPathElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGTextPositioningElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGTitleElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGUseElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"SVGViewElement#addEventListener": { addAnnotations: [ "@Override" ] },
				"ScreenOrientation#addEventListener": { addAnnotations: [ "@Override" ] },
				"ScriptProcessorNode#addEventListener": { addAnnotations: [ "@Override" ] },
				"ServiceWorker#addEventListener": { addAnnotations: [ "@Override" ] },
				"ServiceWorkerContainer#addEventListener": { addAnnotations: [ "@Override" ] },
				"ServiceWorkerRegistration#addEventListener": { addAnnotations: [ "@Override" ] },
				"SharedWorker#addEventListener": { addAnnotations: [ "@Override" ] },
				"SourceBuffer#addEventListener": { addAnnotations: [ "@Override" ] },
				"SourceBufferList#addEventListener": { addAnnotations: [ "@Override" ] },
				"SpeechSynthesis#addEventListener": { addAnnotations: [ "@Override" ] },
				"SpeechSynthesisUtterance#addEventListener": { addAnnotations: [ "@Override" ] },
				"TextTrack#addEventListener": { addAnnotations: [ "@Override" ] },
				"TextTrackCue#addEventListener": { addAnnotations: [ "@Override" ] },
				"TextTrackList#addEventListener": { addAnnotations: [ "@Override" ] },
				"VTTCue#addEventListener": { addAnnotations: [ "@Override" ] },
				"VisualViewport#addEventListener": { addAnnotations: [ "@Override" ] },
				"WebSocket#addEventListener": { addAnnotations: [ "@Override" ] },
				"Window#addEventListener": { addAnnotations: [ "@Override" ] },
				"Worker#addEventListener": { addAnnotations: [ "@Override" ] },
				"XMLDocument#addEventListener": { addAnnotations: [ "@Override" ] },
				"XMLHttpRequest#addEventListener": { addAnnotations: [ "@Override" ] },
				"XMLHttpRequestEventTarget#addEventListener": { addAnnotations: [ "@Override" ] },
				"XMLHttpRequestUpload#addEventListener": { addAnnotations: [ "@Override" ] },
				// method removeEventListener =======================================================================
				"AbortSignal#removeEventListener": { addAnnotations: [ "@Override" ] },
				"Animation#removeEventListener": { addAnnotations: [ "@Override" ] },
				"AudioBufferSourceNode#removeEventListener": { addAnnotations: [ "@Override" ] },
				"AudioContext#removeEventListener": { addAnnotations: [ "@Override" ] },
				"AudioScheduledSourceNode#removeEventListener": { addAnnotations: [ "@Override" ] },
				"AudioWorkletNode#removeEventListener": { addAnnotations: [ "@Override" ] },
				"BaseAudioContext#removeEventListener": { addAnnotations: [ "@Override" ] },
				"BroadcastChannel#removeEventListener": { addAnnotations: [ "@Override" ] },
				"CSSAnimation#removeEventListener": { addAnnotations: [ "@Override" ] },
				"CSSTransition#removeEventListener": { addAnnotations: [ "@Override" ] },
				"ConstantSourceNode#removeEventListener": { addAnnotations: [ "@Override" ] },
				"Document#removeEventListener": { addAnnotations: [ "@Override" ] },
				"Element#removeEventListener": { addAnnotations: [ "@Override" ] },
				"EventSource#removeEventListener": { addAnnotations: [ "@Override" ] },
				"FileReader#removeEventListener": { addAnnotations: [ "@Override" ] },
				"FontFaceSet#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLAnchorElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLAreaElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLAudioElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLBRElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLBaseElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLBodyElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLButtonElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLCanvasElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDListElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDataElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDataListElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDetailsElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDirectoryElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDivElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLDocument#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLEmbedElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLFieldSetElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLFontElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLFormElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLFrameElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLFrameSetElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLHRElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLHeadElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLHeadingElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLHtmlElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLIFrameElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLImageElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLInputElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLLIElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLLabelElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLLegendElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLLinkElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLMapElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLMarqueeElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLMediaElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLMenuElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLMetaElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLMeterElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLModElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLOListElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLObjectElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLOptGroupElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLOptionElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLOutputElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLParagraphElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLParamElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLPictureElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLPreElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLProgressElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLQuoteElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLScriptElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLSelectElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLSlotElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLSourceElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLSpanElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLStyleElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTableCaptionElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTableCellElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTableColElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTableElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTableRowElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTableSectionElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTemplateElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTextAreaElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTimeElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTitleElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLTrackElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLUListElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLUnknownElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"HTMLVideoElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"IDBDatabase#removeEventListener": { addAnnotations: [ "@Override" ] },
				"IDBOpenDBRequest#removeEventListener": { addAnnotations: [ "@Override" ] },
				"IDBRequest#removeEventListener": { addAnnotations: [ "@Override" ] },
				"IDBTransaction#removeEventListener": { addAnnotations: [ "@Override" ] },
				"MathMLElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"MediaDevices#removeEventListener": { addAnnotations: [ "@Override" ] },
				"MediaKeySession#removeEventListener": { addAnnotations: [ "@Override" ] },
				"MediaQueryList#removeEventListener": { addAnnotations: [ "@Override" ] },
				"MediaRecorder#removeEventListener": { addAnnotations: [ "@Override" ] },
				"MediaSource#removeEventListener": { addAnnotations: [ "@Override" ] },
				"MediaStream#removeEventListener": { addAnnotations: [ "@Override" ] },
				"MediaStreamTrack#removeEventListener": { addAnnotations: [ "@Override" ] },
				"MessagePort#removeEventListener": { addAnnotations: [ "@Override" ] },
				"Notification#removeEventListener": { addAnnotations: [ "@Override" ] },
				"OfflineAudioContext#removeEventListener": { addAnnotations: [ "@Override" ] },
				"OscillatorNode#removeEventListener": { addAnnotations: [ "@Override" ] },
				"PaymentRequest#removeEventListener": { addAnnotations: [ "@Override" ] },
				"Performance#removeEventListener": { addAnnotations: [ "@Override" ] },
				"PermissionStatus#removeEventListener": { addAnnotations: [ "@Override" ] },
				"PictureInPictureWindow#removeEventListener": { addAnnotations: [ "@Override" ] },
				"RTCDTMFSender#removeEventListener": { addAnnotations: [ "@Override" ] },
				"RTCDataChannel#removeEventListener": { addAnnotations: [ "@Override" ] },
				"RTCDtlsTransport#removeEventListener": { addAnnotations: [ "@Override" ] },
				"RTCPeerConnection#removeEventListener": { addAnnotations: [ "@Override" ] },
				"RemotePlayback#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGAElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGAnimateElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGAnimateMotionElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGAnimateTransformElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGAnimationElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGCircleElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGClipPathElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGComponentTransferFunctionElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGDefsElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGDescElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGEllipseElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEBlendElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEColorMatrixElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEComponentTransferElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFECompositeElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEConvolveMatrixElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEDiffuseLightingElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEDisplacementMapElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEDistantLightElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEDropShadowElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEFloodElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEFuncAElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEFuncBElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEFuncGElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEFuncRElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEGaussianBlurElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEImageElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEMergeElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEMergeNodeElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEMorphologyElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEOffsetElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFEPointLightElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFESpecularLightingElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFESpotLightElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFETileElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFETurbulenceElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGFilterElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGForeignObjectElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGGElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGGeometryElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGGradientElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGGraphicsElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGImageElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGLineElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGLinearGradientElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGMPathElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGMarkerElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGMaskElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGMetadataElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGPathElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGPatternElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGPolygonElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGPolylineElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGRadialGradientElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGRectElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGSVGElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGScriptElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGSetElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGStopElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGStyleElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGSwitchElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGSymbolElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGTSpanElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGTextContentElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGTextElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGTextPathElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGTextPositioningElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGTitleElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGUseElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SVGViewElement#removeEventListener": { addAnnotations: [ "@Override" ] },
				"ScreenOrientation#removeEventListener": { addAnnotations: [ "@Override" ] },
				"ScriptProcessorNode#removeEventListener": { addAnnotations: [ "@Override" ] },
				"ServiceWorker#removeEventListener": { addAnnotations: [ "@Override" ] },
				"ServiceWorkerContainer#removeEventListener": { addAnnotations: [ "@Override" ] },
				"ServiceWorkerRegistration#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SharedWorker#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SourceBuffer#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SourceBufferList#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SpeechSynthesis#removeEventListener": { addAnnotations: [ "@Override" ] },
				"SpeechSynthesisUtterance#removeEventListener": { addAnnotations: [ "@Override" ] },
				"TextTrack#removeEventListener": { addAnnotations: [ "@Override" ] },
				"TextTrackCue#removeEventListener": { addAnnotations: [ "@Override" ] },
				"TextTrackList#removeEventListener": { addAnnotations: [ "@Override" ] },
				"VTTCue#removeEventListener": { addAnnotations: [ "@Override" ] },
				"VisualViewport#removeEventListener": { addAnnotations: [ "@Override" ] },
				"WebSocket#removeEventListener": { addAnnotations: [ "@Override" ] },
				"Window#removeEventListener": { addAnnotations: [ "@Override" ] },
				"Worker#removeEventListener": { addAnnotations: [ "@Override" ] },
				"XMLDocument#removeEventListener": { addAnnotations: [ "@Override" ] },
				"XMLHttpRequest#removeEventListener": { addAnnotations: [ "@Override" ] },
				"XMLHttpRequestEventTarget#removeEventListener": { addAnnotations: [ "@Override" ] },
				"XMLHttpRequestUpload#removeEventListener": { addAnnotations: [ "@Override" ] },
			}
		},
		"dom.iterable.d.ts": {},
		"dom.iterable.generated.d.ts": {}
		*/
	},
	addFiles: {
		"es2020.globalThis.n4jsd": `
			@@Global @@ProvidedByRuntime
			export external public const globalThis: Object+;

			// Before, this was defined in "globalThis.n4jsd" of "n4js-runtime-esnext" as follows:
			//     @@Global @@ProvidedByRuntime
			//     export external public const globalThis: Object+;
			// and in "globalThis.n4jsd" of "n4js-runtime-html5" it was commented out as follows:
			//     //export external public const globalThis: Window+;
		`
	}
};
