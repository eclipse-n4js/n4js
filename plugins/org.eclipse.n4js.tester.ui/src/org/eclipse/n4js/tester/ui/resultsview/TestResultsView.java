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
package org.eclipse.n4js.tester.ui.resultsview;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.primitives.Ints.asList;
import static org.eclipse.n4js.tester.domain.TestStatus.ERROR;
import static org.eclipse.n4js.tester.domain.TestStatus.FAILED;
import static org.eclipse.n4js.tester.domain.TestStatus.PASSED;
import static org.eclipse.n4js.tester.domain.TestStatus.SKIPPED;
import static org.eclipse.n4js.tester.domain.TestStatus.SKIPPED_FIXME;
import static org.eclipse.n4js.tester.domain.TestStatus.SKIPPED_IGNORE;
import static org.eclipse.n4js.tester.domain.TestStatus.SKIPPED_NOT_IMPLEMENTED;
import static org.eclipse.n4js.tester.domain.TestStatus.SKIPPED_PRECONDITION;
import static org.eclipse.n4js.ui.utils.UIUtils.getShell;
import static java.lang.System.lineSeparator;
import static java.util.Arrays.copyOfRange;
import static org.eclipse.core.runtime.IPath.SEPARATOR;
import static org.eclipse.jface.dialogs.MessageDialog.openError;
import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.widgets.Display.getDefault;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import org.eclipse.n4js.tester.TestConfiguration;
import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.TesterFrontEnd;
import org.eclipse.n4js.tester.domain.ID;
import org.eclipse.n4js.tester.domain.TestCase;
import org.eclipse.n4js.tester.domain.TestElement;
import org.eclipse.n4js.tester.domain.TestResult;
import org.eclipse.n4js.tester.domain.TestStatus;
import org.eclipse.n4js.tester.domain.TestSuite;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.events.SessionEndedEvent;
import org.eclipse.n4js.tester.events.SessionFailedEvent;
import org.eclipse.n4js.tester.events.SessionFinishedEvent;
import org.eclipse.n4js.tester.events.SessionStartedEvent;
import org.eclipse.n4js.tester.events.TestEndedEvent;
import org.eclipse.n4js.tester.events.TestEvent;
import org.eclipse.n4js.tester.events.TestStartedEvent;
import org.eclipse.n4js.tester.ui.TesterFrontEndUI;
import org.eclipse.n4js.tester.ui.TesterUiActivator;
import org.eclipse.n4js.ui.editor.EditorContentExtractor;
import org.eclipse.n4js.ui.editor.StyledTextDescriptor;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.ui.viewer.TreeViewerBuilder;

/**
 * An Eclipse {@link IViewPart view} showing test results. This UI is independent of the particular tester being used
 * (node.js, Chrome, etc.).
 */
public class TestResultsView extends ViewPart {

	/**
	 * ID of this view as defined in the plugin.xml
	 */
	public static final String ID = "org.eclipse.n4js.tester.ui.TestResultsView";

	@Inject
	private TesterEventBus testerEventBus;

	@Inject
	private IURIEditorOpener uriOpener;

	@Inject
	private EditorContentExtractor editorContentExtractor;

	@Inject
	private TesterFrontEndUI testerFrontEndUI;

	@Inject
	private TesterFrontEnd testerFrontEnd;

	@Inject
	private IN4JSEclipseCore core;

	private final List<TestSession> registeredSessions = new ArrayList<>();

	/**
	 * Root of the tree of {@link ResultNode}s that is currently being presented in the UI. Must be one of the roots
	 * registered in {@link #registeredSessions}.
	 */
	private ResultNode currentRoot;
	private ResultNode focusNode;

	private ToolBar toolBar;
	private TestProgressBar progressBar;
	private TreeViewer testTreeViewer;
	private Text stackTrace;

	private Action actionLock;
	private Action actionRelaunch;
	private Action actionRelaunchFailed;
	private Action actionStop;
	private Action actionShowHistory;
	private Action actionClearTerminated;
	private Action doubleClickAction;
	private Action singleClickAction;

	private class CodeSnippetToolTip extends ToolTip {

		private StyledTextDescriptor lastDescriptor;

		private CodeSnippetToolTip(final Control control) {
			super(control);
		}

		@Override
		protected Composite createToolTipContentArea(final Event event, final Composite parent) {
			final Object layoutData = parent.getLayoutData();
			final StyledText text = lastDescriptor.toStyledText(parent, NONE);
			if (layoutData instanceof GridData) {
				((GridData) layoutData).heightHint = text.getBounds().height;
				((GridData) layoutData).widthHint = text.getBounds().width;
			}
			return parent;
		}

		@Override
		protected boolean shouldCreateToolTip(final Event e) {
			this.lastDescriptor = null;
			if (e.widget instanceof Tree) {
				final Tree tree = (Tree) e.widget;
				final TreeItem item = tree.getItem(new Point(e.x, e.y));

				if (null != item && item.getData() instanceof ResultNode) {
					final ResultNode node = (ResultNode) item.getData();
					if (node.getElement() instanceof TestCase) {
						final URI uri = ((TestCase) node.getElement()).getURI();
						if (null != uri) {
							final StyledTextDescriptor descriptor = getDescriptor(uri);
							if (null != descriptor) {
								this.lastDescriptor = descriptor;
							}
						}
					}
				}
			}

			return null != this.lastDescriptor;
		}

		private StyledTextDescriptor getDescriptor(final URI uri) {
			return editorContentExtractor.getDescriptorForSemanticElement(uri).orNull();
		}

	}

	private static class TestTreeViewerContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object input) {
			if (input instanceof ResultNode)
				return ((ResultNode) input).getChildren();
			return new Object[0];
		}

		@Override
		public boolean hasChildren(Object parent) {
			return ((ResultNode) parent).hasChildren();
		}

		@Override
		public Object[] getChildren(Object parent) {
			return ((ResultNode) parent).getChildren();
		}

		@Override
		public Object getParent(Object child) {
			return ((ResultNode) child).getParent();
		}

		@Override
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
			// ignore
		}

		@Override
		public void dispose() {
			// ignore
		}
	}

	private class TestTreeViewerLabelProvider extends LabelProvider
			implements ITableLabelProvider, ITableColorProvider {

		@Override
		public String getColumnText(Object element, int columnIndex) {
			final ResultNode node = (ResultNode) element;
			switch (columnIndex) {
			case 0:
				final TestElement tmElement = node.getElement();
				if (tmElement instanceof TestSuite)
					return ((TestSuite) tmElement).getName();
				if (tmElement instanceof TestCase)
					return ((TestCase) tmElement).getName();
				return null;
			case 1:
				if (node.isLeaf()) {
					// show actual status
					final TestStatus stat = ((ResultNode) element).getStatus();
					if (stat != null)
						return stat.toString();
					if (node.isRunning())
						return "running ...";
					return null;
				} else {
					// show aggregated status of children
					return node.getChildrenStatus().toString(true, 0, SWT.LEFT);
				}
			case 2:
				final long runTime = node.getElapsedTime();
				if (runTime > 0) {
					return "   " + String.format("%.3f", runTime / 1000f) + " s";
				}
				if (node.getElement() instanceof TestCase) {
					final TestCase testCase = (TestCase) node.getElement();
					if (null != testCase.getResult()) {
						return "< 0.001 s";
					}
				}
				return null;
			default:
				return null;
			}
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			switch (columnIndex) {
			case 0:
				final ResultNode node = (ResultNode) element;
				if (node.isLeaf()) {
					if (node.isRunning())
						return TesterUiActivator.getImage(TesterUiActivator.ICON_TEST_RUNNING);
					final TestStatus status = node.getStatus();
					if (status == SKIPPED)
						return TesterUiActivator.getImage(TesterUiActivator.ICON_TEST_SKIPPED);
					if (status == PASSED)
						return TesterUiActivator.getImage(TesterUiActivator.ICON_TEST_PASSED);
					if (status == FAILED)
						return TesterUiActivator.getImage(TesterUiActivator.ICON_TEST_FAILED);
					if (status == ERROR)
						return TesterUiActivator.getImage(TesterUiActivator.ICON_TEST_ERROR);
					if (status == SKIPPED_NOT_IMPLEMENTED || status == SKIPPED_PRECONDITION) {
						return TesterUiActivator.getImage(TesterUiActivator.ICON_TEST_SKIPPED_NOT_IMPLEMENTED_YET);
					}
					return TesterUiActivator.getImage(TesterUiActivator.ICON_TEST);
				} else {
					if (node.isRunning())
						return TesterUiActivator.getImage(TesterUiActivator.ICON_SUITE_RUNNING);
					final TestStatus status = node.getChildrenStatus().getAggregatedStatus();
					if (status == SKIPPED || status == SKIPPED_NOT_IMPLEMENTED || status == SKIPPED_PRECONDITION) {
						return TesterUiActivator.getImage(TesterUiActivator.ICON_SUITE_SKIPPED);
					}
					if (status == PASSED)
						return TesterUiActivator.getImage(TesterUiActivator.ICON_SUITE_PASSED);
					if (status == FAILED)
						return TesterUiActivator.getImage(TesterUiActivator.ICON_SUITE_FAILED);
					if (status == ERROR)
						return TesterUiActivator.getImage(TesterUiActivator.ICON_SUITE_ERROR);
					return TesterUiActivator.getImage(TesterUiActivator.ICON_SUITE);
				}
			default:
				return null;
			}
		}

		@Override
		public Color getForeground(Object element, int columnIndex) {
			return null;
		}

		@Override
		public Color getBackground(Object element, int columnIndex) {
			if (columnIndex == 1) {
				final ResultNode node = (ResultNode) element;
				if (node.isLeaf()) {
					// show actual status
					return progressBar.getColorForStatus(node.getStatus());
				} else {
					// show aggregated status of children
					return progressBar.getColorForStatus(node.getChildrenStatus().getAggregatedStatus());
				}
			}
			return null;
		}
	}

	/**
	 * Returns the instance of this view in the active workbench page. Will open the view if not open already. Returns
	 * <code>null</code> on error (e.g. not invoked from UI thread).
	 *
	 * @param activate
	 *            if true, the view will be brought to the front.
	 */
	public static TestResultsView getInstance(boolean activate) {
		try {
			final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			final TestResultsView view = (TestResultsView) page.showView(ID);
			if (activate)
				page.activate(view);
			return view;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		testerEventBus.register(this);
	}

	@Override
	public void dispose() {
		testerEventBus.unregister(this);
		super.dispose();
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(final Composite parent) {

		GridLayout gl;
		GridData gd;

		gl = new GridLayout(2, false);
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		gl.marginTop = 4;
		parent.setLayout(gl);

		toolBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
		gd = new GridData();
		gd.grabExcessHorizontalSpace = false;
		gd.horizontalAlignment = SWT.CENTER;
		toolBar.setLayoutData(gd);

		progressBar = new TestProgressBar(parent, SWT.BORDER);
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		progressBar.setLayoutData(gd);

		final SashForm sash = new SashForm(parent, SWT.NONE);
		gd = new GridData();
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = SWT.FILL;
		sash.setLayoutData(gd);

		testTreeViewer = new TreeViewerBuilder(newArrayList("Test", "Status", "Duration"),
				new TestTreeViewerContentProvider())
						.setLinesVisible(false).setLabelProvider(new TestTreeViewerLabelProvider())
						.setColumnWeights(asList(5, 2, 1)).build(sash);
		installToolTipSupport(testTreeViewer.getTree());

		testTreeViewer.setInput(getViewSite());

		stackTrace = new Text(sash, SWT.MULTI | SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL);

		sash.addControlListener(new ControlListener() {
			@Override
			public void controlResized(ControlEvent e) {
				final Point size = parent.getSize();
				final int orientation = size.x > size.y ? SWT.HORIZONTAL : SWT.VERTICAL;
				if (orientation != sash.getOrientation()) {
					sash.setOrientation(orientation);
				}
			}

			@Override
			public void controlMoved(ControlEvent e) {
				// ignore
			}
		});

		createActions();
		hookContextMenu();
		hookDoubleClickAction();
		hookSingleClickAction();
		contributeToActionBars();

		refreshActions();
	}

	private ToolTip installToolTipSupport(final Control control) {
		return new CodeSnippetToolTip(control);
	}

	private void hookDoubleClickAction() {
		testTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void hookSingleClickAction() {
		testTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				singleClickAction.run();
			}
		});
	}

	private void hookContextMenu() {
		final MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				TestResultsView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(testTreeViewer.getControl());
		testTreeViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, testTreeViewer);
	}

	private void contributeToActionBars() {
		final IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
		// a custom tool bar works slightly differently:
		final ToolBarManager m = new ToolBarManager(toolBar);
		fillCustomToolBar(m);
		m.update(true);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(actionRelaunch);
		manager.add(actionRelaunchFailed);
		manager.add(actionStop);
		manager.add(new Separator());
		manager.add(actionLock);
		manager.add(new Separator());
		// Other plug-ins can contribute their actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(actionRelaunch);
		manager.add(actionRelaunchFailed);
		manager.add(actionStop);
		manager.add(new Separator());
		manager.add(actionLock);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actionShowHistory);
		manager.add(new Separator());
		manager.add(actionLock);
		manager.add(new Separator());
		manager.add(actionRelaunch);
		manager.add(actionRelaunchFailed);
		manager.add(actionStop);
	}

	private void fillCustomToolBar(IToolBarManager manager) {
		manager.add(actionShowHistory);
	}

	/**
	 * Create all actions.
	 */
	protected void createActions() {
		actionLock = createAction(
				"Scroll Lock", IAction.AS_CHECK_BOX,
				"Do not jump to test case when its status is updated.",
				TesterUiActivator.getImageDescriptor(TesterUiActivator.ICON_LOCK),
				null); // nothing to be done when toggled (will read state when necessary)
		actionRelaunch = createAction(
				"Relaunch", IAction.AS_PUSH_BUTTON,
				"Relaunch entire test session.",
				TesterUiActivator.getImageDescriptor(TesterUiActivator.ICON_RELAUNCH),
				this::performRelaunch);
		actionRelaunchFailed = createAction(
				"Relaunch Failed", IAction.AS_PUSH_BUTTON,
				"Relaunch failed tests from last test session.",
				TesterUiActivator.getImageDescriptor(TesterUiActivator.ICON_RELAUNCH_FAILED),
				this::performRelaunchFailed);
		actionStop = createAction(
				"Stop", IAction.AS_PUSH_BUTTON,
				/* "Stop currently running test session.", */
				"Stopping the running test session is currently not supported.",
				TesterUiActivator.getImageDescriptor(TesterUiActivator.ICON_STOP),
				this::performStop);
		// Disable stop action for now since testing framework doesn't support stopping yet.
		// TODO re-enable stop action (also see to-do in method #refreshActions(), below!)
		actionStop.setEnabled(false);

		actionShowHistory = new ShowHistoryAction(this);
		actionClearTerminated = createAction(
				"Clear Terminated", IAction.AS_PUSH_BUTTON,
				"Clear terminated sessions from history.",
				null,
				this::performClearTerminated);

		doubleClickAction = createAction(null, IAction.AS_PUSH_BUTTON, null, null, this::onDoubleClick);

		singleClickAction = createAction(null, IAction.AS_PUSH_BUTTON, null, null, this::onSingleClick);

	}

	/**
	 * Creates a single action.
	 */
	protected Action createAction(String text, int style, String tooltip, ImageDescriptor imageDescriptor,
			Runnable runnable) {
		final Action a = new Action(text, style) {
			@Override
			public void run() {
				if (runnable != null)
					runnable.run();
			}
		};
		a.setToolTipText(tooltip);
		a.setImageDescriptor(imageDescriptor);
		return a;
	}

	/**
	 * Allow {@link ShowHistoryAction} to access our action for clearing terminated sessions from the outside.
	 */
	/* package */Action getActionClearTerminated() {
		return actionClearTerminated;
	}

	/**
	 * Refreshes the enabled state of all of this view's actions.
	 */
	protected void refreshActions() {
		actionLock.setEnabled(true);
		actionRelaunch.setEnabled(null != currentRoot && !currentRoot.isRunning());
		actionRelaunchFailed.setEnabled(false);
		// actionStop.setEnabled(currentRoot != null && currentRoot.isRunning());
		// TODO
		actionShowHistory.setEnabled(true);
		actionClearTerminated.setEnabled(containsTerminated());
	}

	/**
	 * Invoked when user performs {@link #actionRelaunch}.
	 */
	protected void performRelaunch() {
		if (null != currentRoot) {
			final TestSession session = from(registeredSessions).firstMatch(s -> s.root == currentRoot).orNull();
			if (null != session) {
				final TestConfiguration configurationToReRun = session.configuration;
				registeredSessions.remove(session);
				try {
					final TestConfiguration newConfiguration = testerFrontEnd.createConfiguration(configurationToReRun);
					testerFrontEndUI.runInUI(newConfiguration);
				} catch (Exception e) {
					String message = "Test class not found in the workspace.";
					if (!Strings.isNullOrEmpty(e.getMessage())) {
						message += " Reason: " + e.getMessage();
					}
					MessageDialog.openError(getShell(), "Cannot open editor", message);
				}
			}
		}
	}

	/**
	 * Invoked when user performs {@link #performRelaunchFailed()}.
	 */
	protected void performRelaunchFailed() {
		// TODO
	}

	/**
	 * Invoked when user performs {@link #actionStop}.
	 */
	protected void performStop() {
		// TODO
	}

	/**
	 * Invoked when user performs {@link #actionClearTerminated}.
	 */
	protected void performClearTerminated() {
		clearTerminated();
	}

	/**
	 * Invoked when user double-clicks a result node in the UI.
	 */
	protected void onDoubleClick() {
		final ISelection selection = testTreeViewer.getSelection();
		final ResultNode resultNode = (ResultNode) ((IStructuredSelection) selection).getFirstElement();

		if (resultNode == null) {
			return;
		}

		TestElement testElement = resultNode.getElement();
		if (testElement instanceof TestCase) {

			final URI testCaseURI = ((TestCase) testElement).getURI();
			if (testCaseURI == null) {
				return;
			}

			final IN4JSEclipseProject project = core.findProject(testCaseURI).orNull();
			if (null != project && project.exists()) {
				final URI moduleLocation = testCaseURI.trimFragment();
				final String[] projectRelativeSegments = moduleLocation.deresolve(project.getLocation()).segments();
				final String path = Joiner.on(SEPARATOR)
						.join(copyOfRange(projectRelativeSegments, 1, projectRelativeSegments.length));
				final IFile module = project.getProject().getFile(path);
				if (null != module && module.isAccessible()) {
					uriOpener.open(testCaseURI, true);
				} else {
					openError(getShell(), "Cannot open editor", "Test class not found in selected project.");
				}
			} else {
				openError(getShell(), "Cannot open editor", "The container project not found in the workspace.");
			}

		}
	}

	/**
	 * Invoked when user double-clicks a result node in the UI.
	 *
	 * On invocation clears stack trace text are. If selection is a test case with stack trace, trace is shown in the
	 * trace area.
	 */
	protected void onSingleClick() {
		stackTrace.setText("");

		final ISelection selection = testTreeViewer.getSelection();
		if (selection.isEmpty()) {
			return;
		}

		if (selection instanceof IStructuredSelection) {
			final Object element = ((IStructuredSelection) selection).getFirstElement();

			if (element instanceof ResultNode) {
				final ResultNode resultNode = (ResultNode) element;
				final TestElement testElement = resultNode.getElement();

				if (testElement instanceof TestCase) {
					final TestCase testCase = (TestCase) testElement;
					final TestResult result = testCase.getResult();

					if (result != null) {
						if (result.getTrace() != null && !result.getTrace().isEmpty()) {
							final List<String> trace = newArrayList(result.getTrace());
							final String firstLine = trace.get(0);
							if ("Error".equals(firstLine) && !isNullOrEmpty(result.getMessage())) {
								trace.set(0, result.getMessage());
							}
							final StringBuilder sb = new StringBuilder();
							trace.forEach(line -> sb.append(line).append(lineSeparator()));
							stackTrace.setText(sb.toString());
							stackTrace.setSelection(0);
						} else if ((SKIPPED_IGNORE.equals(result.getTestStatus())
								|| SKIPPED_FIXME.equals(result.getTestStatus()))
								&& !isNullOrEmpty(result.getMessage())) {
							stackTrace.setText(result.getMessage());
							stackTrace.setSelection(0);
						}
					}

				}
			}
		}
	}

	/**
	 * Invoked when user toggles the scroll lock (via {@link #actionLock}).
	 */
	protected void onScrollLockToggled() {
		if (actionLock.isChecked()) {
			// scroll lock was turned ON
			setFocusNode(null, false);
		} else {
			// scroll lock was turned OFF
			if (currentRoot != null) {
				final List<ResultNode> runningNodes = currentRoot.stream().filter(node -> node.isRunning())
						.collect(Collectors.toList());
				if (!runningNodes.isEmpty()) {
					final ResultNode lastRunningNode = runningNodes.get(runningNodes.size() - 1);
					setFocusNode(lastRunningNode, true);
				}
			}
		}
	}

	/**
	 * Whenever a new test event from 'mangelhaft' on the Javascript side is being received by the HTTP server, this
	 * method will be invoked.
	 * <p>
	 * <b>This is the only method in this class that may be invoked from a non-UI thread.</b>
	 */
	@Subscribe
	@AllowConcurrentEvents
	public void onTestEvent(TestEvent ev) {
		Display.getDefault().asyncExec(() -> {
			notifyTestEvent(ev);
		});
	}

	/**
	 * Update UI according to given {@link TestEvent}.
	 */
	public void notifyTestEvent(TestEvent event) {
		if (event instanceof SessionStartedEvent) {
			// ignore
		} else if (event instanceof TestStartedEvent) {
			notifyTestCaseStarted(new ID(((TestStartedEvent) event).getTestId()));
		} else if (event instanceof TestEndedEvent) {
			final TestEndedEvent teev = (TestEndedEvent) event;
			notifyTestCaseEnded(new ID(teev.getTestId()), teev.getResult());
		} else if (event instanceof SessionEndedEvent) {
			// event received from client side
			notifySessionEnded(new ID(event.getSessionId()));
		} else if (event instanceof SessionFinishedEvent) {
			// server completed test session SUCCESS
			// ignore
		} else if (event instanceof SessionFailedEvent) {
			// server completed test session FAILURE
			// ignore
		} else {
			// ignore all other events (e.g. TestPingedEvent)
		}
	}

	/**
	 * Update UI according to a test tree argument as the viewer input.
	 */
	protected void notifyTestTreeCreated(final TestConfiguration configuration, TestTree testTree) {
		getDefault().asyncExec(() -> {
			// was this test tree already added with method #addTestTree()?
			final boolean alreadyAdded = findRootNode(testTree.getSessionId()) != null;
			if (alreadyAdded) {
				// yes -> just show it
				setShownTestTree(testTree.getSessionId());
			} else {
				// no -> add & show it
				addTestTree(configuration, testTree, true);
			}
		});
	}

	/**
	 * Update UI according to a {@link TestStartedEvent}.
	 */
	protected void notifyTestCaseStarted(ID testCaseId) {
		final ResultNode node = findNode(testCaseId);
		if (node != null) {
			node.startRunning();
			if (isShown(node)) {
				updateNode(node);
				setFocusNode(node, true);
			}
			refreshActions();
		}
	}

	/**
	 * Update UI according to a {@link TestEndedEvent}.
	 */
	protected void notifyTestCaseEnded(ID testCaseId, TestResult testResult) {
		final ResultNode node = findNode(testCaseId);
		if (node != null) {
			node.stopRunning();
			node.updateResult(testResult);
			if (isShown(node)) {
				updateNode(node);
				updateProgressBar();
				setFocusNode(node, true);
			}
			refreshActions();
		}
	}

	/**
	 * Update UI according to a {@link SessionEndedEvent}.
	 */
	protected void notifySessionEnded(ID sessionId) {
		final ResultNode rootNode = findNode(sessionId);
		if (rootNode != null) {
			rootNode.stopRunning();
			if (isShown(rootNode)) {
				setFocusNode(null, false);
			}
			refreshActions();
		}
	}

	/**
	 * Get all test trees registered in this view (of which one might be currently shown in the UI, the others are
	 * accessible via the history).
	 */
	public List<TestTree> getTestTrees() {
		return registeredSessions.stream().map(session -> session.root).map(currRoot -> currRoot.getTestTree())
				.collect(Collectors.toList());
	}

	/**
	 * Add a test tree to this view and optionally show it in the UI. If <code>show</code> is false, the tree will only
	 * be added to the history but the contents of the main UI won't change.
	 */
	public void addTestTree(final TestConfiguration configuration, TestTree testTree, boolean show) {
		// some sanity checks
		if (testTree == null)
			throw new IllegalArgumentException("testTree may not be null");
		final String sessionId = testTree.getSessionId() != null ? testTree.getSessionId().getValue() : null;
		if (sessionId == null || sessionId.trim().length() == 0)
			throw new IllegalArgumentException("testTree must have a non-null, non-empty sessionId");
		if (findRootNode(testTree.getSessionId()) != null)
			throw new IllegalArgumentException("view already contains a test tree with the same sessionId");
		// create internal representation
		final ResultNode newRoot = createNodes(testTree);
		newRoot.startRunning(); // marks the entire session / test tree as running
		// add it & update UI
		registeredSessions.add(new TestSession(configuration, newRoot));
		refreshActions();
		if (show) {
			setShownTestTree(testTree.getSessionId());
		}
	}

	/**
	 * Completely clear this view, including the entire history. In most cases, {@link #clearTerminated()} should be
	 * used instead.
	 */
	public void clear() {
		registeredSessions.clear();
		setShownTestTree(null);
	}

	/**
	 * Clear all terminated test sessions from this view's history.
	 */
	public void clearTerminated() {
		final Iterator<ResultNode> i = from(registeredSessions).transform(session -> session.root).iterator();
		while (i.hasNext())
			if (!i.next().isRunning())
				i.remove();
		// if the currently shown root was among the purged ones, clear the UI
		if (currentRoot != null && !registeredSessions.contains(currentRoot))
			setShownTestTree(null);
		// if nothing is shown, show the newest session
		if (currentRoot == null && !registeredSessions.isEmpty())
			setShownTestTree(registeredSessions.get(registeredSessions.size() - 1).root.getId());
	}

	/**
	 * Checks if this view contains at least one terminated test tree.
	 */
	protected boolean containsTerminated() {
		return registeredSessions.stream().anyMatch(currSession -> !currSession.root.isRunning());
	}

	/**
	 * Returns the test tree that is currently being presented in the UI.
	 */
	public TestTree getShownTestTree() {
		return currentRoot != null ? currentRoot.getTestTree() : null;
	}

	/**
	 * Sets test tree to be presented in the UI or <code>null</code> to clear the UI. The test tree must have been added
	 * to the view before using {@link #addTestTree(TestConfiguration, TestTree, boolean)}.
	 */
	public void setShownTestTree(ID sessionId) {
		final ResultNode rootToShow = sessionId != null ? findRootNode(sessionId) : null;
		setRoot(rootToShow);
	}

	/**
	 * Checks if the given node belongs to the test tree that is currently being presented in the UI.
	 */
	protected boolean isShown(ResultNode node) {
		return currentRoot != null && currentRoot == node.getRoot();
	}

	/**
	 * Sets root of the test tree to be presented in the UI or <code>null</code> to clear the UI.
	 * <p>
	 * Same as {@link #setShownTestTree(ID)}, but uses the internal {@link ResultNode} object to identify the test tree.
	 */
	protected void setRoot(ResultNode root) {
		if (root != null && !from(registeredSessions).firstMatch(s -> s.root == root).isPresent()) {
			throw new IllegalArgumentException("root is not registered in resultRegistry");
		}
		setFocusNode(null, false);
		this.currentRoot = root;
		progressBar.setExpectedTotal(root != null ? root.countTestCases() : 0);
		progressBar.setCounter(root != null ? root.getChildrenStatus() : null);
		testTreeViewer.setInput(root);
		refreshActions();
	}

	/**
	 * Find the internal {@link ResultNode} for the test model element with the given {@link ID}. Will only search
	 * roots.
	 */
	protected ResultNode findRootNode(ID id) {
		for (ResultNode currRoot : from(registeredSessions).transform(session -> session.root))
			if (id.equals(currRoot.getId()))
				return currRoot;
		return null;
	}

	/**
	 * Find the internal {@link ResultNode} for the test model element with the given {@link ID}. This method will
	 * search all test trees registered in this view (i.e. the one currently shown in the UI and all other test trees in
	 * the history).
	 */
	protected ResultNode findNode(ID id) {
		for (int idx = registeredSessions.size() - 1; idx >= 0; idx--) {
			final ResultNode currRoot = registeredSessions.get(idx).root;
			final ResultNode matchingNode = currRoot.findById(id);
			if (matchingNode != null)
				return matchingNode;
		}
		return null;
	}

	/**
	 * Expand given node. Will also expand all ancestors of 'node'.
	 */
	protected void expand(ResultNode node) {
		testTreeViewer.expandToLevel(node, 0);
	}

	/**
	 * Collapse given node.
	 */
	protected void collapse(ResultNode node) {
		testTreeViewer.collapseToLevel(node, 1);
	}

	/**
	 * Update the progress bar.
	 */
	protected void updateProgressBar() {
		progressBar.redraw();
	}

	/**
	 * Update the given node. Will do nothing if given node does not belong to the test tree currently shown in the UI.
	 */
	protected void updateNode(ResultNode node) {
		while (node != null) {
			testTreeViewer.update(node, null);
			node = node.getParent();
		}
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		testTreeViewer.getControl().setFocus();
	}

	/**
	 * Set the focus node or unset the focus node if <code>newNode</code> is <code>null</code>.
	 * <p>
	 * The focus node is like a cursor that tracks the currently running test case until a test session is completed.
	 * Ancestors will be expanded and collapsed in order to always show the current focus node. However, the focus node
	 * is unrelated to selection, so this method won't change the current selection in the UI. If {@link #actionLock
	 * scroll lock} is active, then this method will ignore argument <code>reveal</code> and will never reveal the new
	 * focus node.
	 */
	protected void setFocusNode(ResultNode newNode, boolean reveal) {
		if (focusNode == newNode)
			return;

		// collapse all ancestors of old focus node (if any) that no longer have to be expanded in order to show the
		// *new* focus node
		if (focusNode != null) {
			ResultNode n = focusNode;
			while (n != null) {
				if (n.isContainer()) {
					if (newNode == null || !n.isAncestorOf(newNode)) {
						// do not collapse if it contains pending tests
						// (to better support random order of incoming results)
						if (!constainsTestCasesWithoutResult(n)) {
							collapse(n);
						}
					}
				}
				n = n.getParent();
			}
		}

		focusNode = newNode;

		// expand all ancestors of new focus node (if any)
		if (focusNode != null) {
			expand(focusNode);

			if (reveal && !actionLock.isChecked())
				testTreeViewer.reveal(focusNode);
		}
	}

	private boolean constainsTestCasesWithoutResult(ResultNode node) {
		return node.stream().anyMatch(currNode -> {
			final TestCase _case = currNode.getTestCase();
			return _case != null && _case.getResult() == null;
		});
	}

	/**
	 * Creates a tree of {@link ResultNode}s representing the given test tree.
	 */
	protected ResultNode createNodes(TestTree testTree) {
		final ResultNode newRoot = new ResultNode(null, testTree);
		for (TestSuite suite : testTree.getSuites()) {
			final ResultNode suiteNode = new ResultNode(newRoot, suite);
			for (TestCase _case : suite.getTestCases()) {
				@SuppressWarnings("unused")
				final ResultNode caseNode = new ResultNode(suiteNode, _case);
			}
		}
		return newRoot;
	}

	/**
	 * Simple POJO for associating a test configuration with a result node, that wraps a test tree.
	 */
	private static final class TestSession {

		final TestConfiguration configuration;
		final ResultNode root;

		private TestSession(final TestConfiguration configuration, final ResultNode resultNode) {
			this.configuration = configuration;
			root = resultNode;
		}

	}
}
