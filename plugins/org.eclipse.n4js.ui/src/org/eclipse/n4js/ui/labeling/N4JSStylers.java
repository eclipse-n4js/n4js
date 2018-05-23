/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.labeling;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.n4js.ui.labeling.helper.StyledTextCalculationHelper;
import org.eclipse.swt.graphics.RGB;

/**
 * Defines constant stylers used for extended colored labels.
 */
public class N4JSStylers {

	/**
	 * Color used for styling type refs. Used by {@link N4JSStylers#TYPEREF_STYLER} and defined in same class to ensure
	 * static initialization.
	 */
	public final static String TYPEREF_COLOR_NAME = "org.eclipse.n4js.ui.labeling.TYPEREF_COLOR";
	/**
	 * Color used for styling origin of inherited members. Used by {@link N4JSStylers#INHERITED_MEMBERS_STYLER} and
	 * defined in same class to ensure static initialization.
	 */
	public final static String INHERITED_MEMBER_COLOR_NAME = "org.eclipse.n4js.ui.labeling.INHERITED_MEMBER_COLOR";
	/**
	 * Color used for styling origin of consumed members. Used by {@link N4JSStylers#INHERITED_MEMBERS_STYLER} and
	 * defined in same class to ensure static initialization.
	 */
	public final static String CONSUMED_MEMBER_COLOR_NAME = "org.eclipse.n4js.ui.labeling.CONSUMED_MEMBERS_COLOR";
	/**
	 * Color used for styling origin of polyfilled members. Used by {@link N4JSStylers#INHERITED_MEMBERS_STYLER} and
	 * defined in same class to ensure static initialization.
	 */
	public final static String POLYFILLED_MEMBER_COLOR_NAME = "org.eclipse.n4js.ui.labeling.POLYFILLED_MEMBERS_COLOR";
	/**
	 * Color used for styling constructor.
	 */
	public final static String CONSTRUCTOR_COLOR_NAME = "org.eclipse.n4js.ui.labeling.CONSTRUCTOR_COLOR";
	/**
	 * Color used for styling fields.
	 */
	public final static String FIELD_OR_VAR_COLOR_NAME = "org.eclipse.n4js.ui.labeling.FIELD_COLOR";

	static {
		ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
		colorRegistry.put(TYPEREF_COLOR_NAME, new RGB(140, 140, 140)); // color we use in editor as well
		colorRegistry.put(INHERITED_MEMBER_COLOR_NAME, new RGB(180, 180, 255));
		colorRegistry.put(CONSUMED_MEMBER_COLOR_NAME, new RGB(255, 180, 180));
		colorRegistry.put(POLYFILLED_MEMBER_COLOR_NAME, new RGB(255, 255, 180));
		colorRegistry.put(CONSTRUCTOR_COLOR_NAME, new RGB(0, 0, 200));
		colorRegistry.put(FIELD_OR_VAR_COLOR_NAME, new RGB(148, 82, 0)); // mocha
	}

	/**
	 * Styler used to style origin of inherited members. Used by
	 * {@link StyledTextCalculationHelper#dispatchGetStyledText(Object)} for EObjectWithContext.
	 */
	public final static Styler TYPEREF_STYLER = StyledString.createColorRegistryStyler(
			TYPEREF_COLOR_NAME, null);
	/**
	 * Styler used to style origin of inherited members. Used by
	 * {@link StyledTextCalculationHelper#dispatchGetStyledText(Object)} for EObjectWithContext.
	 */
	public final static Styler INHERITED_MEMBERS_STYLER = StyledString.createColorRegistryStyler(
			INHERITED_MEMBER_COLOR_NAME, null);
	/**
	 * Styler used to style origin of consumed members. Used by
	 * {@link StyledTextCalculationHelper#dispatchGetStyledText(Object)} for EObjectWithContext.
	 */
	public final static Styler CONSUMED_MEMBERS_STYLER = StyledString.createColorRegistryStyler(
			CONSUMED_MEMBER_COLOR_NAME, null);
	/**
	 * Styler used to style origin of polyfilled members. Used by
	 * {@link StyledTextCalculationHelper#dispatchGetStyledText(Object)} for EObjectWithContext.
	 */
	public final static Styler POLYFILLED_MEMBERS_STYLER = StyledString.createColorRegistryStyler(
			POLYFILLED_MEMBER_COLOR_NAME, null);
	/**
	 * Styler used to style constructor method. Used by
	 * {@link StyledTextCalculationHelper#dispatchGetStyledText(Object)} for EObjectWithContext.
	 */
	public final static Styler CONSTRUCTOR_STYLER = StyledString.createColorRegistryStyler(
			CONSTRUCTOR_COLOR_NAME, null);
	/**
	 * Styler used to style fields. Used by {@link StyledTextCalculationHelper#dispatchGetStyledText(Object)} for
	 * EObjectWithContext.
	 */
	public final static Styler FIELD_OR_VAR_STYLER = StyledString.createColorRegistryStyler(
			FIELD_OR_VAR_COLOR_NAME, null);

}
