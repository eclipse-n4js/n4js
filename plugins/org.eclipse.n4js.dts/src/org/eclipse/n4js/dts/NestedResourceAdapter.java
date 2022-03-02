/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.dts.TypeScriptParser.ModuleDeclarationContext;

/**
 *
 */
public class NestedResourceAdapter implements Adapter {

	public static boolean isInstalled(Resource resource) {
		return get(resource) != null;
	}

	public static NestedResourceAdapter get(Resource resource) {
		for (Adapter adapter : resource.eAdapters()) {
			if (adapter instanceof NestedResourceAdapter) {
				return (NestedResourceAdapter) adapter;
			}
		}
		return null;
	}

	public static NestedResourceAdapter install(Resource resource, NestedResourceAdapter nra) {
		NestedResourceAdapter adapter = get(resource);
		if (adapter == nra) {
			return adapter;
		}
		resource.eAdapters().remove(adapter);
		resource.eAdapters().add(nra);
		return nra;
	}

	public static NestedResourceAdapter remove(Resource resource) {
		NestedResourceAdapter adapter = get(resource);
		if (adapter != null) {
			resource.eAdapters().remove(adapter);
			return adapter;
		}
		return adapter;
	}

	final DtsTokenStream tokenStream;
	final ModuleDeclarationContext ctx;

	public NestedResourceAdapter(DtsTokenStream tokenStream, ModuleDeclarationContext ctx) {
		this.tokenStream = tokenStream;
		this.ctx = ctx;
	}

	@Override
	public void notifyChanged(Notification notification) {
		// TODO Auto-generated method stub

	}

	@Override
	public Notifier getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTarget(Notifier newTarget) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAdapterForType(Object type) {
		// TODO Auto-generated method stub
		return false;
	}

	/**  */
	public ModuleDeclarationContext getModuleDeclarationContext() {
		return ctx;
	}

	/** 	 */
	public DtsTokenStream getTokenStream() {
		return tokenStream;
	}

}
