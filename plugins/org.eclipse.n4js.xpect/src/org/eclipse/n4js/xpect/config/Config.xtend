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
package org.eclipse.n4js.xpect.config

import java.util.List
import org.eclipse.xpect.setup.XpectSetupComponent

/**
 * Configuration of switches and values affecting the performance of the test-run.
 * This component may have a name (String) on Construction
 * In the Content block you can define variables by stating  {@link VarDef}  components.
 *
 * <pre>
 * Config {
 *   content_assist_timeout = 1000
 *   VarDef "objectProposals" {
 *     MemberList  "Object" "methods" "public" {}
 *   }
 *   VarDef "keywords" {
 *     StringList {
 *       s="++" s="--" s="abstract" s="break" s="class"
 *     }
 *   }
 * }
 * </pre>
 */
@XpectSetupComponent
public class Config {

	List<VarDef> vars = newArrayList;

	public String name;
	long contentAssistTimeout = 2000L;
	String content_assist_kind = "n4js"

	public new() {
		name = "Default_Ui_Root_Config"
	}

	public new(String name ) {
		this.name = name
	}

	public def add(VarDef v) { vars.add(v) }

	public def VarDef getVar(String sname) {
		return vars.findFirst[ name == sname ]
	}

	/**
	 * Timeout in milliseconds for the content-assist computation. Defaults to 2000ms.
	 * @param timeoutInMsec  timeout value in ms
	 */
	public def void setContent_assist_timeout(long timeoutInMsec) {
		contentAssistTimeout = timeoutInMsec
	}
	public def long getContent_assist_timeout() { return contentAssistTimeout }

	/**
	 * Optionally specify the content assist kind.
	 * Cycling in content-assist (multiple ctrl+space hits.) changes kinds, to test them we can
	 * specify the kind in the test.
	 *
	 * Default kind is 'n4js'.
	 *
	 * @param kind key String identifying the default content assist kind.
	 */
	public def void setContent_assist_kind(String kind) {
		content_assist_kind = kind;
	}
	public def String getContent_assist_kind() { content_assist_kind }



	public override toString() {
		"Config("+name+")["+vars+"]"
	}

}
