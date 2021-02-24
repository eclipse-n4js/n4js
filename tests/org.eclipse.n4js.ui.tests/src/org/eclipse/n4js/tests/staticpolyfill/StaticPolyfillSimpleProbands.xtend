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
package org.eclipse.n4js.tests.staticpolyfill;

import java.util.regex.Pattern

/**
 * Provides constants holding n4js code
 */
public class StaticPolyfillSimpleProbands {

	public static val validContent_Filled = '''
					@@StaticPolyfillAware

					export public class P {
						public fromParent() : void {}
					}

					export public class K extends P { // to be filled.

						public greeting : string;

						public constructor(s : string) {
							super()
							this.greeting = "Hi there.";
						}
						public greet() : void {
							console.log( this.greeting );
						}
					}
				''';


	public static val validContent_Filling = '''
					@@StaticPolyfillModule
					@StaticPolyfill
					export public class K extends K  // filling
					{
						public f ;

						@Override
						public constructor (  ) {
							super (  )
					 		this . greeting = "Hi there " + "x" + "!" ;
							this . f = "foo" ;
						}

						@ Override
						public greet ( ) : void {
							console . log ( "Enhanced: " + this . greeting ) ;
						}

						public addedGreetResult ( ) : string { return this . greeting ; }
						public unique ( ) : void { super.fromParent()  }
					}

				''';
	/** search pattern can be found in {@link #validContent_Filling} but not in {@link #validContentFilled} */
	public static val pattern_1_Unique = Pattern.compile("unique");


	public static val validExtendedContent_filling = '''
					@@StaticPolyfillModule
					@StaticPolyfill
					export public class K extends K  // filling
					{
						public f ;

						@Override
						public constructor (  ) {
							super (  )
					 		this . greeting = "Hi there " + "x" + "!" ;
							this . f = "foo" ;
						}

						@ Override
						public greet ( ) : void {
							console . log ( "Enhanced: " + this . greeting ) ;
						}

						public addedGreetResult ( ) : string { return this . greeting ; }
						public unique ( ) : void { super.fromParent()  }

						public goodRecognisableNewlyAddedMethodHere() : void { console.log("A"); }
					}
		''';
	/** search pattern can be found in {@link #validExtendedContent_filling} */
	public static val pattern_2_ExtendedContent = Pattern.compile("goodRecognisableNewlyAddedMethodHere");

	public static val validModifiedExtendedContent_filling = '''
					@@StaticPolyfillModule
					@StaticPolyfill
					export public class K extends K  // filling
					{
						public f ;

						@Override
						public constructor (  ) {
							super (  )
					 		this . greeting = "Hi there " + "x" + "!" ;
							this . f = "foo" ;
						}

					  	/**  "ModifiedExtendedFillingShouldBeHere" <-- no structural change to TModule */
						@ Override
						public greet ( ) : void {
							console . log ( "Enhanced: " + this . greeting ) ;
						}

						public addedGreetResult ( ) : string { return this . greeting ; }
						public unique ( ) : void { super.fromParent()  }

						public goodRecognisableNewlyAddedMethodHere() : void { console.log("A"); }
					}
		''';
	/** search pattern can be found in {@link #validModifiedExtendedContent_filling} */
	public static val pattern_3_ModifiedExtendedContent = Pattern.compile("ModifiedExtendedFillingShouldBeHere");


	public static val invalidContent_filling = '''
					@@StaticPolyfillModule
					@StaticPolyfill
					export public class K extends K  // filling
					{
						public f ;

						@Override
						public constructor ( f : number ) { 					// <-- this introduces an error on purpose !!!
							super (  )
					 		this . greeting = "Hi there " + "x" + "!" ;
							this . f = "foo" ;
						}

						@ Override
						public greet ( ) : void {
							console . log ( "Enhanced: " + this . greeting ) ;
						}

						public addedGreetResult ( ) : string { return this . greeting ; }
						public unique ( ) : void { super.fromParent()  }
					}
		'''



}
