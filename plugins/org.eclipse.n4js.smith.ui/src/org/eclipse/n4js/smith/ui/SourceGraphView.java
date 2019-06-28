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
package org.eclipse.n4js.smith.ui;

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
import org.eclipse.n4js.smith.ui.editoroverlay.EditorOverlay;
import org.eclipse.n4js.smith.ui.graph.ASTGraph;
import org.eclipse.n4js.smith.ui.graph.CFGraph;
import org.eclipse.n4js.smith.ui.graph.Graph;
import org.eclipse.n4js.smith.ui.graph.GraphList;
import org.eclipse.n4js.smith.ui.graph.GraphProvider;
import org.eclipse.n4js.smith.ui.graph.GraphType;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

/**
 * A view showing a graph of the AST and type model (i.e. TModule).
 */
public class SourceGraphView extends ViewPart {

	private GraphList graphList;
	private ASTGraphProvider astGraphProvider;
	private CFGraphProvider cfGraphProvider;

	private XtextEditor activeEditor = null;

	private boolean paused = true;

	private final DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS");

	private final EditorOverlay editorOverlay = new EditorOverlay();

	/**
	 * Create new instance.
	 */
	public SourceGraphView() {
		Activator.getInstance().setViewInstance(this);
	}

	private void updateActiveEditor() {
		final IWorkbenchPartSite site = getSite();
		final IWorkbenchPage page = site != null ? site.getPage() : null;
		final IEditorPart eddy = page != null ? page.getActiveEditor() : null;
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

		graphList = new GraphList(parent, SWT.NONE, editorOverlay);
		astGraphProvider = new ASTGraphProvider();
		cfGraphProvider = new CFGraphProvider();

		createAction(
				"AST Snapshot", IAction.AS_PUSH_BUTTON,
				"Take an AST snapshot.",
				Activator.getInstance().ICON_GRAPH_AST,
				this::onTakeASTSnapshot);
		createAction(
				"CFG Snapshot", IAction.AS_PUSH_BUTTON,
				"Take a CFG snapshot.",
				Activator.getInstance().ICON_GRAPH_CF,
				this::onTakeCFGSnapshot);
		// done in GH-235
		// createAction(
		// "DFG Snapshot", IAction.AS_PUSH_BUTTON,
		// "Take a DFG snapshot.",
		// Activator.getInstance().ICON_GRAPH_DF,
		// this::onTakeCFGSnapshot);
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
	 * Static version of method {@link #showGraph(String, Graph, GraphType, GraphProvider, Object)}. It is safe to call
	 * this method at all times (i.e. even if the view is not opened or disposed, from UI or non-UI thread, etc.).
	 *
	 * @param label
	 *            the label for the new graph.
	 * @param root
	 *            the root object to create the graph from; must be a {@link ResourceSet}, {@link Resource}, or
	 *            {@link EObject}.
	 */
	public static final void show(String label, Object root) {
		final SourceGraphView view = findInstance();
		if (view != null && !view.paused)
			view.showASTGraph(label, root);
	}

	/**
	 *
	 */
	public void showASTGraph(String label, Object root) {
		if (!(root instanceof ResourceSet || root instanceof Resource || root instanceof EObject))
			throw new IllegalArgumentException("root must be a ResourceSet, Resource, or EObject");
		if (graphList.isDisposed())
			return;
		ASTGraph graph = new ASTGraph();
		GraphType graphType = GraphType.AST;
		graph.build(astGraphProvider, root);
		showGraph(label, graph, graphType);
	}

	/**
	 * Create a new graph for the given resource set (using an {@link ASTGraphProvider}), add it to the history and show
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
	public <GP extends GraphProvider<?, ?>> void showGraph(String label, Graph<GP> graph, GraphType graphType,
			GP graphProvider, Object root) {

		if (!(root instanceof ResourceSet || root instanceof Resource || root instanceof EObject))
			throw new IllegalArgumentException("root must be a ResourceSet, Resource, or EObject");
		if (graphList.isDisposed())
			return;
		ResourceType resourceType = null;
		if (root instanceof ResourceSet) {
			ResourceSet rs = (ResourceSet) root;
			if (rs.getResources().isEmpty()) {
				return;
			}
			resourceType = ResourceType.getResourceType(rs.getResources().get(0));
		}
		if (root instanceof Resource) {
			Resource rs = (Resource) root;
			resourceType = ResourceType.getResourceType(rs);
		}
		if (root instanceof EObject) {
			EObject eo = (EObject) root;
			resourceType = ResourceType.getResourceType(eo);
		}
		if (resourceType != null) {
			boolean supportedCFG = false;
			supportedCFG |= resourceType == ResourceType.JS;
			supportedCFG |= resourceType == ResourceType.JSX;
			supportedCFG |= resourceType == ResourceType.N4JS;
			supportedCFG |= resourceType == ResourceType.N4JSX;
			if (supportedCFG) {
				graph.build(graphProvider, root);
				showGraph(label, graph, graphType);
			}
		}
	}

	/**
	 * Add the given graph to the history and show it in the view.
	 * <p>
	 * Need not be invoked from the UI thread.
	 */
	public void showGraph(String label, Graph<?> graph, GraphType graphType) {
		if (graphList.isDisposed())
			return;

		final String prefix = getTimeStamp() + ": ";
		if (Display.getCurrent() != null) {
			// already on UI thread
			graphList.addGraph(prefix + label, graph, graphType, true);
		} else {
			// not on UI thread
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					graphList.addGraph(prefix + label, graph, graphType, true);
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
	protected void onTakeASTSnapshot(@SuppressWarnings("unused") Action action) {
		if (activeEditor != null) {
			activeEditor.getDocument().readOnly(new IUnitOfWork<Boolean, XtextResource>() {
				@Override
				public Boolean exec(XtextResource state) throws Exception {
					showGraph("manual", new ASTGraph(), GraphType.AST, astGraphProvider, state.getResourceSet());
					return null;
				}
			});
		}
	}

	/**
	 * User clicked button 'take snapshot'.
	 */
	protected void onTakeCFGSnapshot(@SuppressWarnings("unused") Action action) {
		if (activeEditor != null) {
			activeEditor.getDocument().readOnly(new IUnitOfWork<Boolean, XtextResource>() {
				@Override
				public Boolean exec(XtextResource state) throws Exception {
					showGraph("manual", new CFGraph(), GraphType.CFG, cfGraphProvider, state.getResourceSet());
					return null;
				}
			});
		}
	}

	/**
	 * User clicked button 'take snapshot'.
	 */
	protected void onTakeDFGSnapshot(@SuppressWarnings("unused") Action action) {
		if (activeEditor != null) {
			activeEditor.getDocument().readOnly(new IUnitOfWork<Boolean, XtextResource>() {
				@Override
				public Boolean exec(XtextResource state) throws Exception {
					showGraph("manual", new ASTGraph(), GraphType.DFG, astGraphProvider, state.getResourceSet());
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

	private static final SourceGraphView findInstance() {
		return Activator.getInstance().getViewInstance();
	}
}
