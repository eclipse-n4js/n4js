/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta
 *	in bundle org.eclipse.xtext
 *	available under the terms of the Eclipse Public License 2.0
 *  Copyright (c) 2009 itemis AG (http://www.itemis.eu) and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.resource;

import java.util.Set;

import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.util.Arrays;

import com.google.common.collect.Sets;

/**
 * Like {@link DefaultResourceDescriptionDelta}, but ignores user data key {@link UserdataMapper#USERDATA_KEY_AST_MD5}
 * when checking for changes with method
 * {@link org.eclipse.xtext.resource.IResourceDescription.Delta#haveEObjectDescriptionsChanged()
 * #haveEObjectDescriptionsChanged()}.
 */
public class N4JSResourceDescriptionDelta extends DefaultResourceDescriptionDelta {

	private static final Set<String> IGNORED_USERDATA_KEYS = Sets.newHashSet(UserdataMapper.USERDATA_KEY_AST_MD5);

	/**
	 * Creates an instance.
	 */
	public N4JSResourceDescriptionDelta(IResourceDescription oldDescription, IResourceDescription newDescription) {
		super(oldDescription, newDescription);
	}

	/**
	 * Tells if given user data key is to be ignored when checking for changes with method
	 * {@link org.eclipse.xtext.resource.IResourceDescription.Delta#haveEObjectDescriptionsChanged()
	 * #haveEObjectDescriptionsChanged()}.
	 */
	protected boolean isIgnoredUserDataKey(String key) {
		return IGNORED_USERDATA_KEYS.contains(key);
	}

	// copied from super class to add support for ignoring some user data keys during comparison
	@Override
	protected boolean equals(IEObjectDescription oldObj, IEObjectDescription newObj) {
		if (oldObj == newObj)
			return true;
		if (oldObj.getEClass() != newObj.getEClass())
			return false;
		if (oldObj.getName() != null && !oldObj.getName().equals(newObj.getName()))
			return false;
		if (!oldObj.getEObjectURI().equals(newObj.getEObjectURI()))
			return false;
		String[] oldKeys = oldObj.getUserDataKeys();
		String[] newKeys = newObj.getUserDataKeys();
		if (oldKeys.length != newKeys.length)
			return false;
		for (String key : oldKeys) {
			// ------------------------------------------------ START of changes w.r.t. super class
			if (isIgnoredUserDataKey(key)) {
				continue;
			}
			// ------------------------------------------------ END of changes w.r.t. super class
			if (!Arrays.contains(newKeys, key))
				return false;
			String oldValue = oldObj.getUserData(key);
			String newValue = newObj.getUserData(key);
			if (oldValue == null) {
				if (newValue != null)
					return false;
			} else if (!oldValue.equals(newValue)) {
				return false;
			}
		}
		return true;
	}
}
