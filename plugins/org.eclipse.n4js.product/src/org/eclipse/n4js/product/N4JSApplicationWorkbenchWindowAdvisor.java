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
package org.eclipse.n4js.product;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.eclipse.ui.PlatformUI.getWorkbench;
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.ide.application.IDEWorkbenchWindowAdvisor;
import org.eclipse.ui.internal.registry.EditorRegistry;
import org.eclipse.ui.internal.registry.FileEditorMapping;
import org.eclipse.ui.internal.util.PrefUtil;

/**
 * Class for configuring the workbench window in the N4JS IDE application.
 */
@SuppressWarnings("restriction")
public class N4JSApplicationWorkbenchWindowAdvisor extends IDEWorkbenchWindowAdvisor {

	private static final Logger LOGGER = Logger.getLogger(N4JSApplicationWorkbenchWindowAdvisor.class);

	private static final Collection<String> VIEW_CATEGORY_BLACKLIST = unmodifiableList(asList(
			"Java",
			"Ant",
			"Remote Systems",
			"Plug-in Development",
			"XML",
			"Java Browsing",
			"Test Views",
			"Debug"));

	private static final Collection<String> VIEW_BLACKLIST = unmodifiableList(asList(
			"org.eclipse.gef.ui.palette_view",
			"org.eclipse.wst.common.snippets.internal.ui.SnippetsView"));

	/**
	 * Default text editor ID that has to be used if a given file extension does not have a corresponding default
	 * editor.
	 */
	private static final String DEFAULT_TEXT_EDITOR_ID = "org.eclipse.ui.DefaultTextEditor";

	/** Context ID of the N4JS-IDE. This is used to override default key bindings. */
	public static final String N4_CONTEXT_ID = "org.eclipse.n4js.ui.context";

	/**
	 * Constructor for creating a workbench window advisor instance.
	 *
	 * @param advisor
	 *            the workbench advisor.
	 * @param configurer
	 *            the window configurer instance.
	 */
	public N4JSApplicationWorkbenchWindowAdvisor(final N4JSApplicationWorkbenchAdvisor advisor,
			final IWorkbenchWindowConfigurer configurer) {
		super(advisor, configurer);
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(final IActionBarConfigurer configurer) {
		return new N4JSApplicationActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen() {
		super.preWindowOpen();
		final IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(1024, 768));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setShowProgressIndicator(true);
		configurer.setShowPerspectiveBar(true);
		initN4Context();
		updateDefaultEditorMappingIfAbsent();
		reviewDisabledCategoriesFromAppModel();

	}

	/**
	 * The 'Show View' dialog behavior slightly changed with E4. Even if the views are properly removed via activities
	 * (by the unique view IDs) the content provider creates the root category for the unavailable views as well since
	 * it is using the {@link MApplication}.
	 */
	private void reviewDisabledCategoriesFromAppModel() {
		final IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		final MApplication service = workbenchWindow.getService(MApplication.class);
		for (Iterator<MPartDescriptor> itr = service.getDescriptors().iterator(); itr.hasNext(); /**/) {
			final MPartDescriptor descriptor = itr.next();
			if (isView(descriptor) && isDisabledView(descriptor)) {
				itr.remove();
			}
		}
	}

	/**
	 * The view part represents either a disabled view or a disabled category.
	 *
	 * @param descriptor
	 *            the descriptor for the view part.
	 * @return {@code true} if disabled view or belongs to a disabled category. Otherwise {@code false}.
	 */
	private boolean isDisabledView(final MPartDescriptor descriptor) {
		return VIEW_BLACKLIST.contains(descriptor.getElementId())
				|| VIEW_CATEGORY_BLACKLIST.contains(descriptor.getCategory());
	}

	/**
	 * Determines if the part is a view or and editor
	 *
	 * @param descriptor
	 *            the view part descriptor.
	 *
	 * @return {@code true} if part is tagged as view otherwise {@code false}.
	 */
	private boolean isView(MPartDescriptor descriptor) {
		return descriptor.getTags().contains("View"); //$NON-NLS-1$
	}

	private void initN4Context() {
		if (isWorkbenchRunning()) {
			final IWorkbench workbench = getWorkbench();
			workbench.getDisplay().asyncExec(() -> {
				final IContextService service = workbench.getService(IContextService.class);
				service.activateContext(N4_CONTEXT_ID);
			});
		}
	}

	private void updateDefaultEditorMappingIfAbsent() {
		final EditorRegistry registry = (EditorRegistry) WorkbenchPlugin.getDefault().getEditorRegistry();
		for (final IFileEditorMapping editorMapping : registry.getFileEditorMappings()) {
			final IEditorDescriptor defaultEditor = editorMapping.getDefaultEditor();
			if (null == defaultEditor) {

				final String extension = editorMapping.getExtension();
				LOGGER.info("No default editor is associated with files with extension: '." + extension + "'.");
				final IEditorDescriptor defaultTextEditor = registry.findEditor(DEFAULT_TEXT_EDITOR_ID);
				if (null != defaultTextEditor) {
					((FileEditorMapping) editorMapping).setDefaultEditor(defaultTextEditor);
					String editorName = defaultTextEditor.getLabel();
					if (null == editorName) {
						editorName = defaultTextEditor.getId();
					}
					if (null != editorName) {
						LOGGER.info("Associated files with extension " + extension + " with '" + editorName + "'.");
					}
				}
			}
		}
		registry.saveAssociations();
		PrefUtil.savePrefs();
	}

}
