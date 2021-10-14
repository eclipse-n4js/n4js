export default {
	preamble: `@@Global @@ProvidedByRuntime`,
	files: {
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

					// FIXME well-known symbols moved here from file 'es2015.symbol.wellknown.d.ts'
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
					// FIXME well-known symbols moved here from file 'es2018.asynciterable.d.ts'
					public const asyncIterator: symbol;
					// FIXME well-known symbols moved here from file 'es2020.symbol.wellknown.d.ts'
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
			`,
			ignore: [
				// temporarily ignored, because we need our own definition:
				"Symbol", "Promise",
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
				// other oddities:
				"PropertyDescriptor#get": undefined,
				"PropertyDescriptor#set": undefined
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
				"RegExp#constructor": undefined
			}
		},
		"es2015.collection.d.ts": {
			suffix: `
				export external public type ReadonlySet<T> = Set<T>;
				export external public type ReadonlyMap<K,V> = Map<K,V>;
			`,
			ignore: [
				// read-only types:
				"ReadonlySet", "ReadonlyMap"
			]
		},
		"es2015.symbol.d.ts": {
			ignore: [
				"Symbol", "SymbolConstructor" // Symbol was moved to es5.n4jsd (see above)
			]
		},
		"es2015.symbol.wellknown.d.ts": {
			ignore: [
				"Symbol", "SymbolConstructor", // Symbol was moved to es5.n4jsd (see above)
				"Promise", "PromiseConstructor" // FIXME this would work fine, except for different number of type parameters!!!
			],
			patchMembers: {
				"String#replace": { addAnnotations: [ "@Override" ] },
				// the signatures of the following members use computed property names inside ~Object with {}
				"String#match": undefined,
				"String#search": undefined,
				"String#split": undefined
			}
		},
		"es2015.iterable.d.ts": {
			ignore: [
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
				"Map#constructor": undefined,
				"WeakMap#constructor": undefined,
				"Set#constructor": undefined,
				"WeakSet#constructor": undefined,
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
			}
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
		"es2015.proxy.d.ts": {},
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
		"es2017.typedarrays.d.ts": {},
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
				"Promise", "PromiseConstructor" // FIXME this would work fine, except for different number of type parameters!!!
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
			]
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
				"Promise", "PromiseConstructor" // FIXME this would work fine, except for different number of type parameters!!!
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
	}
};
