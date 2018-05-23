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
package org.eclipse.n4js.tests.staticpolyfill

import java.util.regex.Pattern
import org.junit.Assert

public class StaticPolyfill_inheriting_from_filled_type__Probands {

	public static val validContent_Filled = '''
			@@StaticPolyfillAware

			export public class P {
				public fromParent() : void {}
			}

			export public class K extends P { // to be filled.

				public string greeting;

				public constructor(s : string) {
					super()
					this.greeting = "Hi there.";
				}
				public greet() : void {
					console.log( this.greeting );
				}
			}
			''';
	// ********************************************************************************
	// IDEBUG 656 - Specstyle-ctor not correctly recognized over inheritance
	// ********************************************************************************
	/** PART of IDEBUG-656 */
	public static val specStyle_Filled = '''
			@@StaticPolyfillAware
			export public class A extends Asuper {
			    @Final a : number;

			    constructor(@Spec spec : ~i~this) {
			    		super(spec)
			    		this.a = -5;
			    }
			}

			export public class Asuper {
			    @Final public asuper : number;

			    constructor(@Spec spec : ~i~this) {
			    }
			}
			'''
	/** PART of IDEBUG-656 */
	public static val specStyle_Filling = '''
			@@StaticPolyfillModule

			@StaticPolyfill
			export public class A extends A {

			    @Final b : number;

			    @Override
				constructor(@Spec spec : ~i~this) {
			        super(spec);

			        // XPECT noerrors --> "The final field a is read-only." at "a"
			        this.a = 5;    // <---- this will fail because A.a is read-only

			        // XPECT noerrors --> "The final field a is read-only." at "b"
			        this.b = 5;
			    }
			}
			'''
	// ********************************************************************************
	// IDEBUG 657 - Passed in Arguments not bound correctly
	// ********************************************************************************
	public static val idebug657_filling ='''
		@@StaticPolyfillModule
		import { PolyB } from "A2"
		@StaticPolyfill
		export public class Poly extends Poly {

		    public test() : boolean {
		        return this.value == 0;
		    }

		    public action(other : Poly) : void {
		        // filled in missing XPECT noerrors --> "Couldn't resolve reference to IdentifiableElement 'test'."
		        other.test();
		        this.test();
		    }

		    public action2(other : PolyB) : void {
		        // inherited filled in missing XPECT noerrors --> "Couldn't resolve reference to IdentifiableElement 'test'."
		        other.test();

		        var pb : PolyB;
		        pb.plusB();
		        pb.test();
		    }

		}

		@StaticPolyfill
		export public class Buddy extends Buddy {

			public foo(other : Poly) : void {
		        // filled in missing XPECT noerrors --> "Couldn't resolve reference to IdentifiableElement 'test'."
				other.test();
			}

			public bar(other : PolyB) : void {
		        // inherited filled in missing XPECT noerrors --> "Couldn't resolve reference to IdentifiableElement 'test'."
				other.test();
			}

		}
	'''
	public static val idebug657_next_to_filling ='''
		import { Poly } from "A"

		export public class PolyB extends Poly {

		//	@Override
		//    public test() : boolean {
		//        return this.value == 0;
		//    }

		    @Override
		    public action(other : Poly) : void {
		        // filled in missing XPECT noerrors --> "Couldn't resolve reference to IdentifiableElement 'test'."
		        other.test();

		        // filled in missing XPECT noerrors --> "Couldn't resolve reference to IdentifiableElement 'test'."
		        this.test();

		    }

			@Override
		    public action2(other : PolyB) : void {
		        // inherited filled in missing XPECT noerrors --> "Couldn't resolve reference to IdentifiableElement 'test'."
		        other.test();

		        // inherited filled in missing XPECT noerrors --> "Couldn't resolve reference to IdentifiableElement 'action'."
		        other.action( this );

		        // inherited filled in missing XPECT noerrors --> "Couldn't resolve reference to IdentifiableElement 'action2'."
		        other.action2( this );

		    }

		    public plusB() : void {}

		}
	'''

	public static val idebug657_filled ='''
		@@StaticPolyfillAware
		export public class Poly {
		   public value : number = 0;
		}

		export public class Buddy {
		   public name : string = "nix";
		}
	'''


	// ********************************************************************************
	// IDEBUG 662 - Validation of Type Arguments broken
	// ********************************************************************************
	public static val idebug662_filled ='''
		@@StaticPolyfillAware
		import { Abstract } from "Abstract"

		export public class Poly<T extends Abstract> {
		    public unit : T;
		}
	'''
	public static val idebug662_next_to_filled ='''
		export public abstract class Abstract {}
		export public class Concrete extends Abstract {}
	'''
	public static val idebug662_filling ='''
		@@StaticPolyfillModule
		import { Abstract } from "Abstract"
		@StaticPolyfill

		export public class Poly<T extends Abstract> extends Poly<T> {

		    protected verify(other : Poly<T>) : void {}
		    protected verifyUnit(other : T) : void {}

		    public convert(other : Poly<T>) : Poly<T> {
		        var u = other.unit;

		        // XPECT noerrors -->
		        this.verify(other);//Error: Poly<T> is not a subtype of Poly<T>.

		        // XPECT noerrors -->
		        this.verifyUnit(other.unit);//Error: T is not a subtype of T.

		        // XPECT noerrors -->
		        u = this.unit;//Error: T is not a subtype of T.

		        // XPECT noerrors -->
		        return this;//Error: this[Poly] is not a subtype of Poly<T>.
		    }

		}
	'''


	// ********************************************************************************
	// IDEBUG 681 - missing initializer for static fields in static polyfills
	// ********************************************************************************
	public static val idebug681_filled ='''
		@@StaticPolyfillAware
		export public class Poly {
		}

		export public class DataMap <T,T> {
			public constructor(p : Array<Array<T>>) {
				console.log("P1=",p[0][0]);
				console.log("P2=",p[0][1]);
			}
		}
	'''
	public static val idebug681_filling ='''
		@@StaticPolyfillModule


		@StaticPolyfill
		export public class Poly extends Poly{

		    private static ENUM_MAP = new DataMap<string, string>([
		            ['a', 'b']
		        ]);
		    const mapping = new DataMap<string, string>([
		            ['C', 'D']
		        ]);

		    const NUMBER = 5;
		    const STRING = "5";
		    public static OTHER : string = "";
		}
	'''

	/** search pattern can be found in compiled code exactly once (short-hand-version) */
	public static val pattern_681_ENUM_MAP = Pattern.compile("Poly\\.ENUM_MAP");

	/** search pattern can be found in compiled code exactly once.*/
	public static val pattern_681_compiled_STRING_init = Pattern.compile("Poly\\.STRING\\s*=\\s*\"5\";")

	/**
	 * Platform independent regular expression for line breaks. Considers
	 * Windows and UNIX specific line breaks due to the platform dependent Xtend template building.
	 */
	private static val LINE_BREAK_REGEXP = "(\\r\\n|\\r|\\n)";

	/** search pattern can be found in compiled code exactly once. */
	public static val pattern_681_compiled_ENUM_Init = Pattern.compile("Poly\\.ENUM_MAP\\s*=\\s*new\\s*DataMap\\(\\["+ LINE_BREAK_REGEXP + "\\s*\\["+ LINE_BREAK_REGEXP + "\\s*'a'")



	def static void main(String[] args) {
		// Testing of regex
		 print("do ")
		 Assert.assertTrue(pattern_681_ENUM_MAP.matcher("Poly.ENUM_MAP").find )
		 Assert.assertTrue(pattern_681_ENUM_MAP.matcher(" xxx Poly.ENUM_MAP xx").find )
		 Assert.assertFalse(pattern_681_ENUM_MAP.matcher(" xxx Poly ENUM_MAP xx").find )

		 val String testCode = '''
				return metaClass;
				        }
				        );

				Poly.ENUM_MAP =  new DataMap([
				            ['a', 'b']
				        ]);
				Poly.mapping =  new DataMap([
				            ['C', 'D']
				        ]);
				Poly.NUMBER =  5;
				Poly.STRING =  "5";
				Poly.OTHER =  "";

				            $n4Export('Poly', Poly);
			'''
		Assert.assertTrue(pattern_681_ENUM_MAP.matcher(testCode).find)
		Assert.assertTrue(StaticPolyfill_inheriting_from_filled_type__Probands.pattern_681_compiled_ENUM_Init.matcher(testCode).find)
		Assert.assertTrue(StaticPolyfill_inheriting_from_filled_type__Probands.pattern_681_compiled_STRING_init.matcher(testCode).find)
		println(" done")
	}


}
