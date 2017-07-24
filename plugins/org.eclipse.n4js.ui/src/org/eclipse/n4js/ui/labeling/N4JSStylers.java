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
	 * Color used for styling origin of inherited members. Used by {@link N4JSStylers#INHERITED_MEMBERS_STYLER} and
	 * defined in same class to ensure static initialization.
	 */
	public final static String INHERITED_MEMBER_COLOR_NAME = "org.eclipse.n4js.ui.labeling.INHERITED_MEMBER_COLOR";
	/**
	 * Color used for styling origin of consumed members. Used by {@link N4JSStylers#INHERITED_MEMBERS_STYLER} and
	 * defined in same class to ensure static initialization.
	 */
	public final static String CONSUMED_MEMBER_COLOR_NAME = "org.eclipse.n4js.ui.labeling.CONSUMED_MEMBERS_STYLER";
	/**
	 * Color used for styling origin of polyfilled members. Used by {@link N4JSStylers#INHERITED_MEMBERS_STYLER} and
	 * defined in same class to ensure static initialization.
	 */
	public final static String POLYFILLED_MEMBER_COLOR_NAME = "org.eclipse.n4js.ui.labeling.POLYFILLED_MEMBERS_STYLER";

	static {
		ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
		colorRegistry.put(INHERITED_MEMBER_COLOR_NAME, new RGB(0, 0, 200));
		colorRegistry.put(CONSUMED_MEMBER_COLOR_NAME, new RGB(0, 150, 0));
		colorRegistry.put(POLYFILLED_MEMBER_COLOR_NAME, new RGB(150, 0, 0));
	}

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

}
