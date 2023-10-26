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
package org.eclipse.n4js.xpect.config;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.xpect.setup.XpectSetupComponent;

/**
 * Configuration of switches and values affecting the performance of the test-run. This component may have a name
 * (String) on Construction In the Content block you can define variables by stating {@link VarDef} components.
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

	List<VarDef> vars = new ArrayList<>();

	/**   */
	public String name;
	long contentAssistTimeout = 2000L;
	String content_assist_kind = "n4js";

	/** Constructor */
	public Config() {
		name = "Default_Ui_Root_Config";
	}

	/** Constructor */
	public Config(String name) {
		this.name = name;
	}

	/**   */
	public void add(VarDef v) {
		vars.add(v);
	}

	/**   */
	public VarDef getVar(String sname) {
		return findFirst(vars, var -> Objects.equals(var.name, sname));
	}

	/**
	 * Timeout in milliseconds for the content-assist computation. Defaults to 2000ms.
	 *
	 * @param timeoutInMsec
	 *            timeout value in ms
	 */
	public void setContent_assist_timeout(long timeoutInMsec) {
		contentAssistTimeout = timeoutInMsec;
	}

	/** Returns the timeout */
	public long getContent_assist_timeout() {
		return contentAssistTimeout;
	}

	/**
	 * Optionally specify the content assist kind. Cycling in content-assist (multiple ctrl+space hits.) changes kinds,
	 * to test them we can specify the kind in the test.
	 *
	 * Default kind is 'n4js'.
	 *
	 * @param kind
	 *            key String identifying the default content assist kind.
	 */
	public void setContent_assist_kind(String kind) {
		content_assist_kind = kind;
	}

	/**   */
	public String getContent_assist_kind() {
		return content_assist_kind;
	}

	@Override
	public String toString() {
		return "Config(" + name + ")[" + vars + "]";
	}

}
