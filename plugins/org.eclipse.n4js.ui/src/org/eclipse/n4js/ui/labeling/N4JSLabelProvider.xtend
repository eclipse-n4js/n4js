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
package org.eclipse.n4js.ui.labeling

import com.google.inject.Inject
import org.eclipse.n4js.ui.labeling.helper.ImageCalculationHelper
import org.eclipse.n4js.ui.labeling.helper.LabelCalculationHelper
import org.eclipse.n4js.ui.labeling.helper.StyledTextCalculationHelper
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.ui.ISharedImages
import org.eclipse.ui.PlatformUI
import org.eclipse.xtext.ui.label.AbstractLabelProvider
import org.eclipse.n4js.ui.outline.N4JSOutlineTreeProvider
import org.eclipse.jface.viewers.StyledString
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.xtext.util.CancelIndicator

/**
 * This provider is bound as default label provider and is used e.g. for the outline view.
 * As labels in content assist and hyperlinks may look different (as different use case),
 * there are {@link N4JSContentAssistLabelProvider} and {@link N4JSHyperlinkLabelProvider}.
 * <br/><br/>
 * Everytime \@Inject ILabelProvider is used this label provider is injected.
 * <br/><br/>
 * see http://www.eclipse.org/Xtext/documentation.html#labelProvider
 * <br/><br/>
 * Main use case is the use in {@link N4JSOutlineTreeProvider}.
 * <br/><br/>
 * This class just delegates the calculation of labels and images. Please not that the
 * {@link StyledTextCalculationHelper} and {@link LabelCalculationHelper} creates in
 * combination the label, e.g. for fields, the {@link LabelCalculationHelper} writes the name
 * of the field and {@link StyledTextCalculationHelper} then just appends the styled string
 * with the type information. {@link ImageCalculationHelper} is used to calculate the icons
 * next to the labels.
 * <br/><br/>
 * Although this class delegates tasks the dependent classes sometimes requires access to
 * methods in the super class of this class. Therefore the dependent classes get this label
 * provider set and this class overrides some of its super class methods to raise visibility
 * from protected to public.
 */
class N4JSLabelProvider extends AbstractLabelProvider {
	@Inject
	private LabelCalculationHelper labelCalculationHelper

	private ImageCalculationHelper imageCalculationHelper
	private StyledTextCalculationHelper styledTextCalculationHelper

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate)
	}

	@Inject
	def setImageCalculationHelper(ImageCalculationHelper imageCalculationHelper) {
		this.imageCalculationHelper = imageCalculationHelper
		this.imageCalculationHelper.setLabelProvider(this)
	}

	@Inject
	def setStyledTextCalculationHelper(StyledTextCalculationHelper styledTextCalculationHelper) {
		this.styledTextCalculationHelper = styledTextCalculationHelper
		this.styledTextCalculationHelper.setLabelProvider(this)
	}

	public CancelIndicator cancelIndicator = CancelIndicator.NullImpl;

	/**
	 * A "real" {@link CancelIndicator} (ie, the argument) is only available during an invocation of the caller of this method.
	 * That caller is expected to follow this protocol:
	 * <ol>
	 * <li>establish the indicator on entry, by invoking this method.</li>
	 * <li>performing operations (which may access the cancel indicator put into effect above).</li>
	 * <li>removing the cancel indicator on exit (in a finally block).</li>
	 * </ol>
	 *
	 * @see #removeCancelIndicator
	 */
	def void establishCancelIndicator(CancelIndicator indicator) {
		this.cancelIndicator = (if (null !== indicator) indicator else CancelIndicator.NullImpl)
	}

	def void removeCancelIndicator() {
		cancelIndicator = CancelIndicator.NullImpl;
	}

	/* doGetText -> manually dispatch label text via dispatchDoGetText */
	override String doGetText(Object element) {
		labelCalculationHelper.dispatchDoGetText(element)
	}

	/* manually dispatch image object via dispatchDoGetImage */
	override ImageDescriptor doGetImage(Object element) {
		imageCalculationHelper.dispatchDoGetImage(element)
	}

	/* manually dispatch styled text via dispatchGetStyledText */
	override StyledString getStyledText(Object element) {
		return styledTextCalculationHelper.dispatchGetStyledText(element)
	}

	// delegates to super.getStyledText, have to use getSuperStyledText
	// as name as otherwise the caller would end in a endless loop
	def getSuperStyledText(Object element) {
		super.getStyledText(element)
	}

	public def ImageDescriptor asImageDescriptor(String name) {
		super.convertToImageDescriptor(name)
	}

	// If you see this default image in the outline view this is a hint
	// that there is something wrong with the N4JSOutlineTreeProvider
	public static def ImageDescriptor getDefaultImageDescriptor() {
		return PlatformUI.workbench.sharedImages.getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT)
	}
}
