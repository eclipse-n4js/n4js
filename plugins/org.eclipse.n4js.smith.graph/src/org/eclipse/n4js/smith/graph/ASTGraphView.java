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
package org.eclipse.n4js.smith.graph;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.n4js.smith.graph.graph.Graph;
import org.eclipse.n4js.smith.graph.graph.Graph.GraphProvider;
import org.eclipse.n4js.smith.graph.graph.GraphList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

/**
 * A view showing a graph of the AST and type model (i.e. TModule).
 */
public class ASTGraphView extends ViewPart {

	private GraphList graphList;
	private GraphProvider graphProvider;

	private XtextEditor activeEditor = null;

	private boolean paused = true;

	private final DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS");

	/**
	 * Create new instance.
	 */
	public ASTGraphView() {
		Activator.getInstance().setViewInstance(this);
	}

	private void updateActiveEditor() {
		final IEditorPart eddy = getSite().getPage().getActiveEditor();
		final XtextEditor xeddy = eddy instanceof XtextEditor ? (XtextEditor) eddy : null;
		if (activeEditor != xeddy) {
			activeEditor = xeddy;
			onActiveEditorChanged();
		}
	}

	/**
	 * Invoked whenever the active editor in the workbench changes.
	 */
	protected void onActiveEditorChanged() {
		// refreshInfo();
	}

	@Override
	public void createPartControl(Composite parent) {
		// add listener to track the active editor
		this.getSite().getPage().addPartListener(new IPartListener() {
			@Override
			public void partActivated(IWorkbenchPart part) {
				updateActiveEditor();
			}

			@Override
			public void partDeactivated(IWorkbenchPart part) {
				updateActiveEditor();
			}

			@Override
			public void partOpened(IWorkbenchPart part) {
				updateActiveEditor();
			}

			@Override
			public void partClosed(IWorkbenchPart part) {
				updateActiveEditor();
			}

			@Override
			public void partBroughtToTop(IWorkbenchPart part) {
				// do nothing
			}
		});

		graphList = new GraphList(parent, SWT.NONE);
		graphProvider = new EMFGraphProvider();

		createAction(
				"Snapshot", IAction.AS_PUSH_BUTTON,
				"Take a manual snapshot.",
				Activator.getInstance().ICON_SNAPSHOT,
				this::onTakeSnapshot);
		createAction(
				"Pause", IAction.AS_CHECK_BOX,
				"Suspend accepting snapshots that were triggered programmatically.",
				Activator.getInstance().ICON_PAUSE,
				this::onPause).setChecked(paused);
		createAction(
				"Delete", IAction.AS_PUSH_BUTTON,
				"Delete selected snapshots from history.",
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE),
				this::onDelete);
	}

	private Action createAction(String label, int style, String tooltip, ImageDescriptor image,
			Consumer<Action> onRun) {
		final Action result = new Action(label, style) {
			@Override
			public void run() {
				onRun.accept(this);
			}
		};
		result.setText(label);
		result.setToolTipText(tooltip);
		result.setImageDescriptor(image);
		getViewSite().getActionBars().getToolBarManager().add(result);
		return result;
	}

	/**
	 * Static version of method {@link #showGraph(String, Object)}. It is safe to call this method at all times (i.e.
	 * even if the view is not opened or disposed, from UI or non-UI thread, etc.).
	 *
	 * @param label
	 *            the label for the new graph.
	 * @param root
	 *            the root object to create the graph from; must be a {@link ResourceSet}, {@link Resource}, or
	 *            {@link EObject}.
	 */
	public static final void show(String label, Object root) {
		final ASTGraphView view = findInstance();
		if (view != null && !view.paused)
			view.showGraph(label, root);
	}

	/**
	 * Create a new graph for the given resource set (using an {@link EMFGraphProvider}), add it to the history and show
	 * it in the view.
	 * <p>
	 * Need not be invoked from the UI thread.
	 *
	 * @param label
	 *            the label for the new graph.
	 * @param root
	 *            the root object to create the graph from; must be a {@link ResourceSet}, {@link Resource}, or
	 *            {@link EObject}.
	 */
	public void showGraph(String label, Object root) {
		if (!(root instanceof ResourceSet || root instanceof Resource || root instanceof EObject))
			throw new IllegalArgumentException("root must be a ResourceSet, Resource, or EObject");
		if (graphList.isDisposed())
			return;
		final Graph graph = new Graph();
		graph.build(graphProvider, root);
		showGraph(label, graph);
	}

	/**
	 * Add the given graph to the history and show it in the view.
	 * <p>
	 * Need not be invoked from the UI thread.
	 */
	public void showGraph(String label, Graph graph) {
		if (graphList.isDisposed())
			return;
		final String prefix = getTimeStamp() + ": ";
		if (Display.getCurrent() != null) {
			// already on UI thread
			graphList.addGraph(prefix + label, graph, true);
		} else {
			// not on UI thread
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					graphList.addGraph(prefix + label, graph, true);
				}
			});
		}
	}

	/**
	 * Return time stamp to prepend to a graph's label in the list of graphs.
	 */
	protected String getTimeStamp() {
		return dateFormat.format(new Date());
	}

	@Override
	public void setFocus() {
		graphList.setFocus();
	}

	/**
	 * User clicked button 'take snapshot'.
	 */
	protected void onTakeSnapshot(@SuppressWarnings("unused") Action action) {
		if (activeEditor != null) {
			activeEditor.getDocument().readOnly(new IUnitOfWork<Boolean, XtextResource>() {
				@Override
				public Boolean exec(XtextResource state) throws Exception {
					showGraph("manual", state.getResourceSet());
					return null;
				}
			});
		}
	}

	/**
	 * User clicked button 'pause'.
	 */
	protected void onPause(Action action) {
		this.paused = action.isChecked();
	}

	/**
	 * User clicked button 'delete'.
	 */
	protected void onDelete(@SuppressWarnings("unused") Action action) {
		graphList.removeSelectedGraphs(true);
	}

	private static final ASTGraphView findInstance() {
		return Activator.getInstance().getViewInstance();

		// the following code won't work, because #getActiveWorkbenchWindow() returns 'null' if called form non-UI
		// (that's why we cache the instance in the Activator)

		// thread
		// try {
		// return (TestView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ID);
		// }
		// catch(Exception e) {
		// return null;
		// }
	}
}
