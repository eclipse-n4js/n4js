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
package org.eclipse.n4js.ui.labeling.helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.xtext.ui.label.AbstractLabelProvider;

/**
 * This image descriptor accepts multiple decorators on a single quadrant. In contrast, {@link DecorationOverlayIcon}
 * accepts at most one decorator per quadrant (ie, up to 4 quadrants total).
 * <p>
 * In order to have the outline view running in the background, or just to minimize memory consumption, all image
 * descriptors should be designed with the image registry in mind. For that, an image descriptor should serve as key for
 * the image map managed by {@link AbstractLabelProvider} (of which {@link N4JSLabelProvider} is a subclass).
 * <p>
 * Following the recommendation in {@link ImageDescriptor} an image descriptor shouldn't hold references to images, thus
 * contributing towards both goals of having a background-outline and lower memory-consumption.
 * <p>
 * This class is not thread-reentrant. It is however safe for use outside the Eclipse-UI thread.
 */
public final class N4JSDecoratorRow extends CompositeImageDescriptor {

	private final ImageDescriptor main;
	private final int quadrant;
	private final ImageDescriptor[] decos;
	private ImageDataProvider imageDataProvider;

	/**
	 * Usage:
	 * <ul>
	 * <li>Never access directly, use {@link #getMainData()} instead.</li>
	 * <li>lazily-initialized, derived data, not considered for equals()/hashCode()</li>
	 * </ul>
	 */
	private ImageData mainData = null;

	/**
	 * Usage:
	 * <ul>
	 * <li>Never access directly, use {@link #getDecosDataProvider()} instead.</li>
	 * <li>lazily-initialized, derived data, not considered for equals()/hashCode()</li>
	 * </ul>
	 */
	private CachedImageDataProvider[] decosDataProvider = null;

	@SuppressWarnings("javadoc")
	public N4JSDecoratorRow(ImageDescriptor main, int quadrant, List<ImageDescriptor> decos) {
		Objects.requireNonNull(main);
		if ((quadrant < IDecoration.TOP_LEFT) || (quadrant > IDecoration.BOTTOM_RIGHT)) {
			throw new IllegalArgumentException();
		}
		Objects.requireNonNull(decos);
		this.main = main;
		this.quadrant = quadrant;
		this.decos = decos.toArray(new ImageDescriptor[decos.size()]);
	}

	@SuppressWarnings("javadoc")
	public N4JSDecoratorRow(ImageDescriptor main, int quadrant, ImageDescriptor deco) {
		this(main, quadrant, Collections.singletonList(deco));
	}

	/**
	 * Are the decorators aligned vertically on top?
	 */
	private boolean isDecoTop() {
		switch (quadrant) {
		case IDecoration.TOP_LEFT:
		case IDecoration.TOP_RIGHT:
			return true;

		default:
			return false;
		}
	}

	/**
	 * Do the decorators start on the left border?
	 */
	private boolean isDecoLeft() {
		switch (quadrant) {
		case IDecoration.TOP_LEFT:
		case IDecoration.BOTTOM_LEFT:
			return true;

		default:
			return false;
		}
	}

	private int halfDecoHeight() {
		return getDecosHeight() / 2;
	}

	private int halfDecoWidth() {
		return getDecosWidth() / 2;
	}

	/**
	 * Distance by which the main image is displaced (from the top border) to partially make room for decorators.
	 * <p>
	 * In contrast, {@link #marginFromTop()} applies to the decorators.
	 */
	private int shiftFromTop() {
		return isDecoTop() ? halfDecoHeight() : 0;
	}

	/**
	 * Distance by which the main image is displaced (from the left border) to partially make room for decorators.
	 * <p>
	 * In contrast, {@link #marginFromLeft()} applies to the decorators.
	 */
	private int shiftFromLeft() {
		return isDecoLeft() ? halfDecoWidth() : 0;
	}

	/**
	 * This method initializes a field on demand, given that each invocation of the delegate allocates anew.
	 */
	private ImageData getMainData() {
		if (null == mainData) {
			mainData = main.getImageData(100);
		}
		return mainData;
	}

	/**
	 * Return ImageDataProvider.
	 */
	private ImageDataProvider getImageDataProvider() {
		if (main == null)
			return null;

		if (imageDataProvider == null) {
			imageDataProvider = createCachedImageDataProvider(main);
		}
		return imageDataProvider;
	}

	/**
	 * This method initializes a field on demand, given that each invocation of the delegate allocates anew.
	 */
	private CachedImageDataProvider[] getDecosDataProvider() {
		if (null == decosDataProvider) {
			decosDataProvider = new CachedImageDataProvider[decos.length];
			for (int i = 0; i < decosDataProvider.length; i++) {
				decosDataProvider[i] = createCachedImageDataProvider(decos[i]);
			}
		}
		return decosDataProvider;
	}

	/**
	 * @return Summation over the width of each decorator.
	 */
	private int getDecosWidth() {
		int result = 0;
		for (CachedImageDataProvider dd : getDecosDataProvider()) {
			result += dd.getWidth();
		}
		return result;
	}

	/**
	 * @return The maximum height over all decorators.
	 */
	private int getDecosHeight() {
		int result = 0;
		for (CachedImageDataProvider dd : getDecosDataProvider()) {
			result = Math.max(result, dd.getHeight());
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(decos);
		result = prime * result + ((main == null) ? 0 : main.hashCode());
		result = prime * result + quadrant;
		return result;
	}

	/**
	 * This override and that for {@link #hashCode} are essential so that the image registry (accessed from
	 * {@code AbstractLabelProvider}) may recognize two different instances of this class as describing the same image.
	 * Otherwise a new image is allocated each time.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof N4JSDecoratorRow)) {
			return false;
		}
		N4JSDecoratorRow other = (N4JSDecoratorRow) obj;
		if (!Arrays.equals(decos, other.decos)) {
			return false;
		}
		if (!main.equals(other.main)) {
			return false;
		}
		if (quadrant != other.quadrant) {
			return false;
		}
		return true;
	}

	/**
	 * Bounding box for the image that would result after shifting the main image to make room for decorators, ie
	 * including both top and left margins if any.
	 * <p>
	 * Such image only materializes as an intermediate step during {@link #drawCompositeImage(int, int)}
	 */
	private Rectangle getSizeOfShiftedMain() {
		int widthAfterShift = shiftFromLeft() + getMainData().width;
		int heightAfterShift = shiftFromTop() + getMainData().height;
		return new Rectangle(0, 0, widthAfterShift, heightAfterShift);
	}

	/**
	 * Bounding box for the image that would result after shifting the image that includes all decorators, accounting
	 * for the left-most position (given by {@link #quadrant}) for such decorators-image.
	 * <p>
	 * The images in question only materialize as intermediate steps during {@link #drawCompositeImage(int, int)}
	 */
	private Rectangle getSizeOfShiftedDecos() {
		int widthAfterShift = marginFromLeft() + getDecosWidth();
		int heightAfterShift = marginFromTop() + getDecosHeight();
		return new Rectangle(0, 0, widthAfterShift, heightAfterShift);
	}

	/**
	 * The distance from the left border at which the drawing of the decorator row starts, influenced by
	 * {@link #quadrant}.
	 * <p>
	 * In contrast, {@link #shiftFromLeft()} applies to the main image.
	 */
	private int marginFromLeft() {
		return isDecoLeft() ? 0 : halfOf(getMainData().width);
	}

	/**
	 * The distance from the top border at which the drawing of the decorator row starts, influenced by
	 * {@link #quadrant}.
	 * <p>
	 * In contrast, {@link #shiftFromTop()} applies to the main image.
	 */
	private int marginFromTop() {
		return isDecoTop() ? 0 : halfOf(getMainData().height);
	}

	private static int halfOf(int amount) {
		return amount / 2;
	}

	/**
	 * A bounding box for the resulting image (ie, after decorators are applied).
	 * <p>
	 * The result of this method is passed to {@link #drawCompositeImage} as part of allocating image data for the
	 * composite as a whole, during an invocation of {@link CompositeImageDescriptor#getImageData()}.
	 */
	@Override
	protected Point getSize() {
		Rectangle boundingBox = getSizeOfShiftedMain().union(getSizeOfShiftedDecos());
		return new Point(boundingBox.width, boundingBox.height);
	}

	@Override
	protected void drawCompositeImage(int ignoredWidth, int ignoredHeight) {
		// base image, possibly shifted to accommodate decorators
		drawImage(getImageDataProvider(), shiftFromLeft(), shiftFromTop());
		// row of decorators
		int x = marginFromLeft();
		final int y = marginFromTop();
		for (CachedImageDataProvider decoDataProvider : getDecosDataProvider()) {
			drawImage(decoDataProvider, x, y);
			x += decoDataProvider.getWidth();
		}
	}

}
