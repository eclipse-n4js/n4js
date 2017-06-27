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
package org.eclipse.n4js.ui.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.ui.editor.preferences.fields.AbstractMasterDetailsFieldEditor;
import org.eclipse.xtext.util.Triple;

import org.eclipse.n4js.utils.IComponentProperties;

/**
 */
public abstract class ComponentDetailsFieldEditor extends AbstractMasterDetailsFieldEditor {

	ComponentPreferencesDetailsPart detailsPart;

	ComponentDetailsFieldEditor(String name, String labelText, Composite composite,
			IPreferenceStore preferenceStore, List<?> list) {
		super(name, labelText, composite, preferenceStore, list);
	}

	/**
	 * Returns component properties. Value cannot specified in constructor, as it is already needed while processing the
	 * super constructor call.
	 */
	protected abstract IComponentProperties<?>[] getComponentProperties();

	@Override
	protected ComponentPreferencesDetailsPart createDetailsPart() {
		detailsPart = new ComponentPreferencesDetailsPart(getPreferenceStore(), getComponentProperties());
		return detailsPart;
	}

	@Override
	protected String label(Object object) {
		if (object instanceof Triple<?, ?, ?>)
			return (String) ((Triple<?, ?, ?>) object).getSecond();
		return object.toString();
	}

	@Override
	protected String identifier(Object object) {
		if (object instanceof Triple<?, ?, ?>)
			return (String) ((Triple<?, ?, ?>) object).getFirst();
		return object.toString();
	}

	/**
	 * @return the preference stores associated with the contained field editors (they store their values in separate
	 *         preference stores)
	 */
	public List<IPreferenceStore> getPreferenceStores() {
		if (detailsPart != null) {
			return detailsPart.getPreferenceStores();
		}
		return new ArrayList<>();
	}

}
