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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.xtext.ide.server.build.ILoadResultInfoAdapter;

/**
 *
 */
public class LoadResultInfoAdapter implements ILoadResultInfoAdapter {

	public static LoadResultInfoAdapter install(Resource resource) {
		LoadResultInfoAdapter adapter = (LoadResultInfoAdapter) ILoadResultInfoAdapter.get(resource);
		if (adapter != null) {
			return adapter;
		}
		adapter = new LoadResultInfoAdapter();
		resource.eAdapters().add(adapter);
		return adapter;
	}

	final List<URI> newUris = new ArrayList<>();

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

	@Override
	public List<URI> getNewUris() {
		return newUris;
	}

}
