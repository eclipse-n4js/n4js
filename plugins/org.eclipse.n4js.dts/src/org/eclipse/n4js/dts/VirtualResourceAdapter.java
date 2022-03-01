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
public class VirtualResourceAdapter implements Adapter {

	public static boolean isInstalled(Resource resource) {
		return get(resource) != null;
	}

	public static VirtualResourceAdapter get(Resource resource) {
		for (Adapter adapter : resource.eAdapters()) {
			if (adapter instanceof VirtualResourceAdapter) {
				return (VirtualResourceAdapter) adapter;
			}
		}
		return null;
	}

	public static VirtualResourceAdapter install(Resource resource, DtsTokenStream tokenStream,
			ModuleDeclarationContext ctx) {

		VirtualResourceAdapter adapter = get(resource);
		if (adapter != null) {
			return adapter;
		}
		adapter = new VirtualResourceAdapter(tokenStream, ctx);
		resource.eAdapters().add(adapter);
		return adapter;
	}

	public static VirtualResourceAdapter remove(Resource resource) {
		VirtualResourceAdapter adapter = get(resource);
		if (adapter != null) {
			resource.eAdapters().remove(adapter);
			return adapter;
		}
		return adapter;
	}

	final DtsTokenStream tokenStream;
	final ModuleDeclarationContext ctx;

	public VirtualResourceAdapter(DtsTokenStream tokenStream, ModuleDeclarationContext ctx) {
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
