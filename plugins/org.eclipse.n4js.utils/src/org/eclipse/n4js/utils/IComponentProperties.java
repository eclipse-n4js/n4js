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
package org.eclipse.n4js.utils;

/**
 */
public interface IComponentProperties<T extends ComponentDescriptor> {

	/**
	 * The short key that identifies this property. Note this isn't the key to use to resolve the value in the
	 * preference store as this key is dependent on the actual compiler. That key can be calculated by calling
	 * getKey(String) where String is the output name (= compiler id). This short key will then be the last part of the
	 * compiler dependent key.
	 */
	public String getKey();

	/**
	 * The string to label the input field or check box in the compiler configuration preference page
	 */
	public String getLabel();

	/**
	 * Type of the property. Depending on the type, the preference page (see {@code CompilePreferencesDetailsPart}) will
	 * create different field editors. Internally, only Boolean and String are distinguished, all other types are
	 * handled similar to String.
	 */
	public Class<?> getType();

	/**
	 * True, if the configuration access interface should provide access to this property. Design rational:
	 * configuration properties like 'Mark generated files as derived' are only sensible to be checked in UI but not
	 * inside the compiler
	 */
	public boolean isVisibleForClient();

	/**
	 * Returns true if the property can be edited in the preference page. This is used in
	 * {@code CompilePreferencesDetailsPart} in the ui plugin.
	 */
	public boolean isVisibleInPreferencePage();

	/**
	 * @param outputName
	 *            the output name (in context of the output configuration) that also identifiers the compiler in our
	 *            case
	 * @return the compiler dependent property key that can be used to resolve the value of this property for the
	 *         compiler identified by the output name in the preference store
	 */
	public String getKey(String outputName);

	/**
	 * This method defines how to set the value of this property in the given CompilerDescriptor object. E.g. to set the
	 * value for OUTPUT_OVERRIDE compilerDescriptor.getOutputConfiguration().setOverrideExistingResources(value) have to
	 * be called.
	 *
	 * @param compilerDescriptor
	 *            the value object (mostly used as transfer object) to hold the configuration of an compiler
	 * @param outputName
	 *            the output name (in context of the output configuration) that also identifiers the compiler in our
	 *            case
	 * @param value
	 *            the value to set for the property
	 */
	public void setValueInCompilerDescriptor(T compilerDescriptor, String outputName,
			Object value);

	/**
	 * This method defines how to get the value of this property in the given CompilerDescriptor object. E.g. to get the
	 * value for OUTPUT_OVERRIDE compilerDescriptor.getOutputConfiguration().getOverrideExistingResources() have to be
	 * called.
	 *
	 * @param compilerDescriptor
	 *            the value object (mostly used as transfer object) to hold the configuration of an compiler
	 * @param outputName
	 *            the output name (in context of the output configuration) that also identifiers the compiler in our
	 *            case
	 * @return the value fetched for the property
	 */
	public Object getValueInCompilerDescriptor(T compilerDescriptor, String outputName);

}
