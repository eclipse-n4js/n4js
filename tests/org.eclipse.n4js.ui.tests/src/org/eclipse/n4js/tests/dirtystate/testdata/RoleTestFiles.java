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
package org.eclipse.n4js.tests.dirtystate.testdata;

public class RoleTestFiles {

	public static String roleA() {
		return """
				export interface ARole {

					public myMethodA() {}
				}
				""";
	}

	public static String roleAChanged() {
		return """
				export interface ARole {

					public myMethodA2() {}
				}
				""";
	}

	public static String moduleFolder() {
		return "roles";
	}

	public static String roleB() {
		return """
				import { ARole } from "%s/ARole"
				export interface BRole extends ARole {

					public myMethodB() {
						this.myMethodA()
					}
				}
				""".formatted(moduleFolder());
	}

	public static String roleBChanged() {
		return """
				import { ARole } from "%s/ARole"
				export interface BRole extends ARole {

					public myMethodB2() {
						this.myMethodA()
					}
				}
				""".formatted(moduleFolder());
	}

	public static String roleBChanged2() {
		return """
				import { ARole } from "%s/ARole"
				export interface BRole /*extends ARole*/ {

					public myMethodA() {

					}
				}
				""".formatted(moduleFolder());
	}

	public static String roleBChanged3() {
		return """
				import { ARole } from "%s/ARole"
				export interface BRole /*extends ARole*/ {

					public myMethodA() {

					}

					public myMethodB() {

					}
				}
				""".formatted(moduleFolder());
	}

	public static String roleC() {
		return """
				import { BRole } from "%s/BRole"
				export interface CRole extends BRole {
					name : String;

					myMethodC() {
						this.myMethodA()
						this.myMethodB()
					}
				}
				""".formatted(moduleFolder());
	}

	public static String classD() {
		return """
				export class D {

					public myMethodB() {
					}
				}
				""";
	}

	public static String classDChanged() {
		return """
				export class D {

					public myMethodB2() {
					}
				}
				""";
	}

	public static String classE() {
		return """
				import { D } from "%s/D"
				import { BRole } from "%s/BRole"
				export class E extends D implements BRole {

					public myMethodE() {
						this.myMethodB()
					}
				}
				""".formatted(moduleFolder(), moduleFolder());
	}

	public static String classEChanged() {
		return """
				import { D } from "%s/D"
				import { BRole } from "%s/BRole"
				export class E extends D /*extends BRole*/ {

					public myMethodE() {
						this.myMethodB()
					}
				}
				""".formatted(moduleFolder(), moduleFolder());
	}
}
