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
package org.eclipse.n4js.ui.typesearch;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.base.Suppliers.memoize;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Iterables.tryFind;
import static com.google.common.collect.Iterators.filter;
import static org.eclipse.core.runtime.IProgressMonitor.UNKNOWN;
import static org.eclipse.core.runtime.Status.OK_STATUS;
import static org.eclipse.n4js.ui.typesearch.TypeSearchKind.ALL_TYPES;
import static org.eclipse.swt.SWT.BOLD;

import java.util.Comparator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.utils.ui.JDTUtils;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Ordering;
import com.google.inject.Inject;

/**
 * Dialog for searching for N4JS types just like in JDT.
 */
public class OpenTypeSelectionDialog extends FilteredItemsSelectionDialog {

	private static final String DIALOG_SETTINGS_ID = OpenTypeSelectionDialog.class.getSimpleName();

	@Inject
	private IN4JSEclipseCore core;

	@Inject
	private N4JSLabelProvider labelProvider;

	@Inject
	private IURIEditorOpener uriEditorOpener;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	private final Supplier<IResourceDescriptions> indexSupplier = Suppliers
			.memoize(new Supplier<IResourceDescriptions>() {
				@Override
				public IResourceDescriptions get() {
					final ResourceSet resourceSet = core.createResourceSet(Optional.absent());
					return core.getXtextIndex(resourceSet);
				}
			});

	/**
	 * {@link TypeSearchKind Search kind} to restrict the available N4JS types in the search dialog.
	 */
	private TypeSearchKind searchKind = ALL_TYPES;

	private ItemsFilter currentFilter;

	/**
	 * Primary constructor for creating a N4JS type selection dialog.<br>
	 * By default multiple selection is allowed in the dialog.
	 */
	public OpenTypeSelectionDialog() {
		this(true);
	}

	/**
	 * Constructor for creating a N4JS type selection dialog. <br>
	 * The additional parameter allows to limit multiple selection functionality.
	 *
	 * @param multiSelection
	 *            False to disable multiple selection
	 */
	protected OpenTypeSelectionDialog(boolean multiSelection) {
		super(Display.getCurrent().getActiveShell(), multiSelection);
		setTitle("Open N4JS types");
	}

	/**
	 * Opens the window and sets the {@link TypeSearchKind search kind} for the dialog.
	 *
	 * @param kind
	 *            the search kind to restrict the search for specific N4JS types.
	 * @return the return code after the window is closed.
	 */
	public int open(final TypeSearchKind kind) {
		this.searchKind = kind;
		indexSupplier.get(); // Initializes the index.
		return super.open();
	}

	@Override
	protected void configureShell(final Shell shell) {
		super.configureShell(shell);
		final TypesListLabelProvider listLabelProvider = new TypesListLabelProvider();
		setListLabelProvider(listLabelProvider);
		setListSelectionLabelDecorator(listLabelProvider);
		setSelectionHistory(new TypesSelectionHistory());
		setDetailsLabelProvider(new TypesDetailsLabelProvider());
	}

	@Override
	protected void okPressed() {
		filter(getSelectedItems().iterator(), IEObjectDescription.class).forEachRemaining(desc -> {
			uriEditorOpener.open(desc.getEObjectURI(), true);
		});
		super.okPressed();
	}

	@Override
	protected Control createExtendedContentArea(final Composite parent) {
		return null; // intentionally ignored
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = N4JSActivator.getInstance().getDialogSettings().getSection(DIALOG_SETTINGS_ID);
		if (null == settings) {
			settings = N4JSActivator.getInstance().getDialogSettings().addNewSection(DIALOG_SETTINGS_ID);
		}
		return settings;
	}

	@Override
	protected IStatus validateItem(final Object item) {
		return OK_STATUS;
	}

	@Override
	protected ItemsFilter createFilter() {
		setCurrentFilter(new TypesFilter());
		return getCurrentFilter();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	protected Comparator getItemsComparator() {
		return Ordering.natural().nullsLast().from(new Comparator() {

			@Override
			public int compare(final Object o1, final Object o2) {
				if (o1 instanceof IEObjectDescription && o2 instanceof IEObjectDescription) {
					final IEObjectDescription d1 = (IEObjectDescription) o1;
					final IEObjectDescription d2 = (IEObjectDescription) o2;
					final QualifiedName fqn1 = d1.getQualifiedName();
					final QualifiedName fqn2 = d2.getQualifiedName();
					if (null != fqn1 && null != fqn2) {
						return nullToEmpty(fqn1.getLastSegment()).compareToIgnoreCase(
								nullToEmpty(fqn2.getLastSegment()));
					}
				}
				return Objects.hashCode(o1) - Objects.hashCode(o2);
			}
		});
	}

	@Override
	protected void fillContentProvider(final AbstractContentProvider contentProvider, final ItemsFilter itemsFilter,
			final IProgressMonitor monitor) throws CoreException {

		monitor.beginTask("Searching for N4JS types...", UNKNOWN);

		final Iterable<IEObjectDescription> types = filter(indexSupplier.get().getExportedObjects(),
				desc -> searchKind.matches(desc.getEClass()));

		monitor.beginTask("Searching for N4JS types...", size(types));
		types.forEach(desc -> {
			contentProvider.add(desc, itemsFilter);
			monitor.worked(1);
		});

		monitor.done();
	}

	@Override
	public String getElementName(final Object item) {
		// Intentionally returns with the simple name of the type.
		// Duplicates are calculated with this mechanism.
		return labelProvider.getText(item);
	}

	/**
	 * @return The currently set ItemsFilter
	 */
	public ItemsFilter getCurrentFilter() {
		return currentFilter;
	}

	/**
	 * Set a custom ItemsFilter
	 *
	 * @param currentFilter
	 *            The new ItemsFilter
	 */
	public void setCurrentFilter(ItemsFilter currentFilter) {
		this.currentFilter = currentFilter;
	}

	/**
	 * Filter implementation for N4JS types.
	 */
	private final class TypesFilter extends ItemsFilter {

		@Override
		public boolean matchItem(final Object item) {
			if (item instanceof IEObjectDescription) {
				final QualifiedName fqn = ((IEObjectDescription) item).getQualifiedName();
				if (null != fqn) {
					return matches(fqn.getLastSegment());
				}
			}
			return false;
		}

		@Override
		public boolean isConsistentItem(final Object item) {
			return true;
		}
	}

	/**
	 * History for the N4JS type search.
	 */
	private final class TypesSelectionHistory extends SelectionHistory {

		private static final String MEMENTO_URI_KEY = "uri";

		@Override
		protected void storeItemToMemento(final Object item, final IMemento memento) {
			if (item instanceof IEObjectDescription) {
				final URI uri = ((IEObjectDescription) item).getEObjectURI();
				memento.putString(MEMENTO_URI_KEY, uri.toString());
			}
		}

		@Override
		protected Object restoreItemFromMemento(final IMemento memento) {
			final String uri = memento.getString(MEMENTO_URI_KEY);
			if (!isNullOrEmpty(uri)) {
				final Optional<IEObjectDescription> result = tryFind(indexSupplier.get().getExportedObjects(),
						desc -> uri.equals(String.valueOf(desc.getEObjectURI())));
				if (result.isPresent()) {
					return searchKind.matches(result.get().getEClass()) ? result.get() : null;
				}
			}
			return null;
		}

	}

	/**
	 * Label provider for showing the details of the N4JS type search. This label provider tries to append the container
	 * project name to the FQN of a N4JS type.
	 */
	private final class TypesDetailsLabelProvider extends LabelProvider {

		@Override
		public String getText(final Object element) {
			if (element instanceof IEObjectDescription) {
				final IEObjectDescription description = (IEObjectDescription) element;
				final StringBuilder sb = new StringBuilder(getFqn(description));
				if (description instanceof EObject && null != ((EObject) description).eContainer()) {
					final EObject container = ((EObject) description).eContainer();
					if (container instanceof IResourceDescription) {
						final URI uri = ((IResourceDescription) container).getURI();
						final IN4JSEclipseProject project = core.findProject(uri).orNull();
						if (null != project && project.exists()) {
							sb.append(" [");
							sb.append(project.getProjectName());
							sb.append("]");
							if (project.isExternal()) {
								final IProject resourceProject = project.getProject();
								sb.append(" External library: ");
								sb.append(resourceProject.getLocation().toFile().getAbsolutePath());
							}
						}
					}
				}
				return sb.toString();
			} else {
				return "";
			}
		}

		@Override
		public Image getImage(final Object element) {
			return labelProvider.getImage(element);
		}

		private String getFqn(final IEObjectDescription description) {
			final String fqn = qualifiedNameConverter.toString(description.getQualifiedName());
			final String name = nullToEmpty(description.getQualifiedName().getLastSegment());
			final int lastIndexOf = fqn.lastIndexOf(name);
			return 0 < lastIndexOf ? fqn.substring(0, lastIndexOf - 1) : fqn;
		}
	}

	/**
	 * Enhanced label provider for decorating and highlighting the text in the list viewer.
	 */
	private final class TypesListLabelProvider extends LabelProvider implements ILabelDecorator, IStyledLabelProvider {

		private static final String NAME_SEPARATOR = " - ";

		private final Styler boldStyler;
		private final Styler qualifierStyler;
		private final TypesDetailsLabelProvider delegate;
		private final Supplier<Font> boldFontSupplier = memoize(new Supplier<Font>() {

			@Override
			public Font get() {
				final Font font = getDialogArea().getFont();
				final FontData[] data = font.getFontData();
				for (int i = 0; i < data.length; i++) {
					data[i].setStyle(BOLD);
				}
				return new Font(font.getDevice(), data);
			}
		});

		private TypesListLabelProvider() {
			delegate = new TypesDetailsLabelProvider();
			qualifierStyler = new Styler() {

				@Override
				public void applyStyles(final TextStyle textStyle) {
					StyledString.QUALIFIER_STYLER.applyStyles(textStyle);
				}
			};
			boldStyler = new Styler() {

				@Override
				public void applyStyles(final TextStyle textStyle) {
					textStyle.font = boldFontSupplier.get();
				}
			};
		}

		@Override
		public void dispose() {
			super.dispose();
			if (null != boldFontSupplier) {
				final Font font = boldFontSupplier.get();
				if (null != font && !font.isDisposed()) {
					font.dispose();
				}
			}
		}

		@Override
		public Image getImage(final Object element) {
			return labelProvider.getImage(element);
		}

		@Override
		public String getText(final Object element) {
			if (element instanceof IEObjectDescription) {
				final String text = labelProvider.getText(element);
				if (isDuplicateElement(element)) {
					final String fqn = delegate.getText(element);
					return new StringBuilder(text).append(NAME_SEPARATOR).append(fqn).toString();
				}
				return text;
			}
			return null;
		}

		@Override
		public Image decorateImage(final Image image, final Object element) {
			return image;
		}

		@Override
		public String decorateText(final String text, final Object element) {
			return text;
		}

		@Override
		public StyledString getStyledText(final Object element) {
			if (element instanceof IEObjectDescription) {
				final String text = getText(element);
				final StyledString string = new StyledString(text);

				final int[] matchingRegion = getMatchingRegions(text);
				if (null != matchingRegion) {
					for (int i = 0; i < matchingRegion.length; i = i + 2) {
						string.setStyle(matchingRegion[i], matchingRegion[i + 1], boldStyler);
					}
				}

				final int indexOf = text.indexOf(NAME_SEPARATOR);
				if (-1 < indexOf) {
					string.setStyle(indexOf, text.length() - indexOf, qualifierStyler);
				}
				return string;
			}
			return new StyledString();
		}

		private int[] getMatchingRegions(final String text) {
			return JDTUtils.getMatchingRegions(
					getCurrentFilter().getPattern(),
					text,
					getCurrentFilter().getMatchRule());
		}
	}

}
