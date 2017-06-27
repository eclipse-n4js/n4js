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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.xtext.ui.editor.preferences.fields.AbstractDetailsPart;

import org.eclipse.n4js.utils.IComponentProperties;

/**
 */
public class ComponentPreferencesDetailsPart extends AbstractDetailsPart {

	/**
	 * Fields to be created in {@link #createFieldEditors()}
	 */
	protected final List<FieldEditor> fields = new ArrayList<>();
	private final IComponentProperties<?>[] componentPropertiesValues;

	ComponentPreferencesDetailsPart(IPreferenceStore masterPreferenceStore,
			IComponentProperties<?>[] componentPropertiesValues) {
		super(masterPreferenceStore);
		this.componentPropertiesValues = componentPropertiesValues;
	}

	/**
	 * @return the preference stores associated with the field editors (they store their values in separate preference
	 *         stores)
	 */
	public List<IPreferenceStore> getPreferenceStores() {
		List<IPreferenceStore> stores = new ArrayList<>();
		for (FieldEditor field : fields) {
			stores.add(field.getPreferenceStore());
		}
		return stores;
	}

	@Override
	protected void createFieldEditors() {

		for (IComponentProperties<?> prop : componentPropertiesValues) {
			if (prop.isVisibleInPreferencePage()) {
				FieldEditor field = null;
				if (prop.getType() == Boolean.class) {
					field = new BooleanFieldEditor(prop.getKey(),
							prop.getLabel(), getFieldEditorParent());
				} else if (prop.getType() == File.class) {
					field = new FileFieldEditor(prop.getKey(), prop.getLabel(), getFieldEditorParent());
				}
				else {
					field = new StringFieldEditor(prop.getKey(),
							prop.getLabel(), getFieldEditorParent());
				}
				fields.add(field);
				addField(field);
			}
		}
	}

}
