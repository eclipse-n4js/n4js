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
package org.eclipse.n4js.ui;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.ts.ui.navigation.URIBasedStorage;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.n4js.ui.external.EclipseExternalLibraryWorkspace;
import org.eclipse.n4js.ui.labeling.N4JSDescriptionLabelProvider;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.XtextEditorErrorTickUpdater;
import org.eclipse.xtext.ui.editor.XtextReadonlyEditorInput;
import org.eclipse.xtext.ui.editor.XtextSourceViewer;
import org.eclipse.xtext.ui.editor.reconciler.XtextReconciler;

import com.google.inject.Inject;

/**
 * The n4js editor usually shows files of platform URIs.
 * <p>
 * In case of external library files, file-uris must be shown. To enable support for navigation in the project explorer,
 * a conversion from file to platform uri is done.
 */
public class N4JSEditor extends XtextEditor implements IShowInSource, IShowInTargetList {

	private static final Logger LOG = Logger.getLogger(N4JSEditor.class);

	private N4JSEditorErrorTickUpdater errorTickUpdater = null;

	private final AtomicInteger reconcilingCounter = new AtomicInteger();

	@Inject
	private IImageHelper imageHelper;

	@Inject
	private EclipseExternalLibraryWorkspace extWS;

	@Inject
	private N4JSDescriptionLabelProvider labelProvider;

	/* package */ void setErrorTickUpdater(N4JSEditorErrorTickUpdater errorTickUpdater) {
		this.errorTickUpdater = errorTickUpdater;
	}

	@Override
	public boolean isEditable() {
		IEditorInput input = getEditorInput();
		if (input instanceof FileEditorInput) {
			FileEditorInput fei = (FileEditorInput) input;
			IFile inputFile = fei.getFile();
			if (inputFile.toString().contains(N4JSGlobals.NODE_MODULES)) {
				FileURI fileUri = new FileURI(inputFile.getFullPath().toFile());
				FileURI project = extWS.findProjectWith(fileUri);
				boolean editorShowsExternalFile = project != null;
				if (editorShowsExternalFile) {
					return false;
				}
			}
		}
		return super.isEditable();
	}

	@Override
	public Image getDefaultImage() {
		Image defaultImage = getImageN4JSVariantOrGiven(super.getDefaultImage());
		return defaultImage;
	}

	/** This will show the icon variant according to the file extension: n4js, n4jsx, n4jsd, js, jsx */
	protected Image getImageN4JSVariantOrGiven(Image titleImage) {
		IEditorInput input = getEditorInput();
		URI uri = null;
		if (input instanceof XtextReadonlyEditorInput) {
			XtextReadonlyEditorInput xrei = (XtextReadonlyEditorInput) input;
			try {
				IStorage storage = xrei.getStorage();
				if (storage instanceof URIBasedStorage) {
					URIBasedStorage ubs = (URIBasedStorage) storage;
					uri = ubs.getURI();
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		if (input instanceof FileEditorInput) {
			FileEditorInput fei = (FileEditorInput) input;
			uri = URIUtils.convert(fei.getFile());

		}
		if (uri != null) {
			Image image = labelProvider.getImageForURI(uri);
			if (image != null) {
				titleImage = image;
			}
		}
		return titleImage;
	}

	/**
	 * Tells if this editor is currently {@link XtextReconciler reconciling} its document.
	 */
	public boolean isReconciling() {
		return reconcilingCounter.get() > 0;
	}

	/**
	 * Sets if this editor is currently {@link XtextReconciler reconciling} its document. While this is set to
	 * <code>true</code>, the editor's title image will be overlaid by a {@link ImageRef#TINY_CLOCK tiny clock symbol}.
	 */
	public void setReconciling(final boolean reconciling) {
		if (reconciling) {
			final int oldCounter = reconcilingCounter.getAndIncrement();
			if (oldCounter == 0) {
				// we just transitioned from "not reconciling" to "reconciling"
				refreshTitleImage();
			}
		} else {
			final int newCounter = reconcilingCounter.decrementAndGet();
			if (newCounter == 0) {
				// we just transitioned from "reconciling" to "not reconciling"
				refreshTitleImage();
			}
		}
	}

	/**
	 * Notify the {@link XtextEditorErrorTickUpdater} to refresh the title image.
	 */
	protected void refreshTitleImage() {
		final N4JSEditorErrorTickUpdater etu = errorTickUpdater;
		if (etu != null) {
			etu.updateEditorImage(this);
		}
	}

	/**
	 * This method is expected to add all applicable overlays for the title image to the given image descriptor. If no
	 * overlays are to be added, then the given image descriptor should be returned (this method should never return
	 * <code>null</code>).
	 * <p>
	 * This method should never add overlays to indicate errors/warnings, as this is taken care of by the default
	 * implementation of {@link XtextEditorErrorTickUpdater}.
	 */
	public ImageDescriptor applyTitleImageOverlays(ImageDescriptor titleImageDesc) {
		if (isReconciling()) {
			final Image image = imageHelper.getImage(titleImageDesc);
			titleImageDesc = new DecorationOverlayIcon(image, ImageRef.TINY_CLOCK.asImageDescriptor().get(),
					IDecoration.TOP_RIGHT);
		}
		return titleImageDesc;
	}

	/**
	 * Returns the {@link ISourceViewer}, which will most likely be an {@link XtextSourceViewer}. Same as
	 * {@link AbstractTextEditor#getSourceViewer() getSourceViewer()} in super class, but provided here to increase
	 * visibility.
	 */
	public final ISourceViewer getSourceViewer2() {
		return getSourceViewer();
	}

	/**
	 * Make publicly available.
	 */
	@Override
	public void initializeViewerColors(ISourceViewer viewer) {
		super.initializeViewerColors(viewer);
	}

	@Override
	protected void handlePreferenceStoreChanged(PropertyChangeEvent event) {
		// this event is not supposed to occur here anymore due to some internal changes in Xtext
		// Unfortunately the colors won't be invalidated due to that in some rare occasions.
		// That was fixed by introducing the InvalidatingHighlightingHelper
		boolean tokenStyleChanged = event.getProperty().contains(".syntaxColorer.tokenStyles");
		if (tokenStyleChanged) {
			LOG.error("Unexpected event", new Exception());
			return;
		}
		super.handlePreferenceStoreChanged(event);
	}

	@Override
	protected void editorContextMenuAboutToShow(final IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);

		final IContributionItem[] items = menu.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i] instanceof IMenuManager) {
				final IMenuManager subMenu = (IMenuManager) items[i];
				final IContributionItem testShowIn = subMenu.find(ContributionItemFactory.VIEWS_SHOW_IN.getId());
				if (null != testShowIn) {
					menu.remove(subMenu);
				}
			}
		}
	}

	/**
	 * Provides input so that the Project Explorer can locate the editor's input in its tree.
	 */
	@Override
	public ShowInContext getShowInContext() {
		IEditorInput editorInput = getEditorInput();
		if (editorInput instanceof FileEditorInput) {
			FileEditorInput fei = (FileEditorInput) getEditorInput();
			return new ShowInContext(fei.getFile(), null);
		} else if (editorInput instanceof XtextReadonlyEditorInput) {
			XtextReadonlyEditorInput readOnlyEditorInput = (XtextReadonlyEditorInput) editorInput;
			IStorage storage;
			try {
				storage = readOnlyEditorInput.getStorage();
				return new ShowInContext(storage.getFullPath(), null);
			} catch (CoreException e) {
				// Do nothing
			}
		}
		return new ShowInContext(null, null);
	}

	/**
	 * List Project Explorer as target in Navigator -> Show In.
	 */
	@Override
	public String[] getShowInTargetIds() {
		return new String[] { IPageLayout.ID_PROJECT_EXPLORER };
	}
}
