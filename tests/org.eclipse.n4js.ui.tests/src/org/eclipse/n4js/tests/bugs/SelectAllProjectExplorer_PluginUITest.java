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
package org.eclipse.n4js.tests.bugs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;
import static org.eclipse.n4js.projectDescription.ProjectType.LIBRARY;
import static org.eclipse.n4js.projectDescription.ProjectType.TEST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.tests.util.EclipseUIUtils;
import org.eclipse.n4js.ui.navigator.internal.SelectWorkingSetDropDownAction;
import org.eclipse.n4js.ui.navigator.internal.ShowHiddenWorkingSetsDropDownAction;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.workingsets.ProjectTypeAwareWorkingSetManager;
import org.eclipse.n4js.ui.workingsets.WorkingSet;
import org.eclipse.n4js.ui.workingsets.WorkingSetManager;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBrokerImpl;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

/**
 * Class for testing the project navigator behavior regarding the selectAll shortcut, context menu contributions and
 * mixed element selections.
 */
public class SelectAllProjectExplorer_PluginUITest extends AbstractPluginUITest {

	private static final List<String> LIBRARY_PROJECTS = Arrays.asList("L1", "ClosedL2");
	private static final List<String> TEST_PROJECTS = Arrays.asList("T1", "ClosedT2");

	private static final Iterable<String> ALL_PROJECT_NAMES = Iterables
			.concat(LIBRARY_PROJECTS, TEST_PROJECTS);

	private static final int NUMBER_OF_PROJECTS = LIBRARY_PROJECTS.size() + TEST_PROJECTS.size();

	/** ClosedL2 and ClosedT2 */
	private static final int NUMBER_OF_CLOSED_PROJECTS = 2;

	/** Default number of resources for a fresh project (src, src-gen, ...) */
	private static final int RESOURCES_PER_PROJECT = 3;

	@Inject
	private WorkingSetManagerBrokerImpl broker;

	@Inject
	private IN4JSEclipseCore eclipseN4jsCore;

	private ProjectExplorer projectExplorer;
	private CommonViewer commonViewer;

	/**
	 * Asserts that the {@link IWorkbench workbench} is running.
	 */
	@BeforeClass
	public static void assertWorkbenchIsRunning() {
		assertTrue("Expected running workbench.", PlatformUI.isWorkbenchRunning());
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		waitForIdleState();
		projectExplorer = (ProjectExplorer) EclipseUIUtils.showView(ProjectExplorer.VIEW_ID);
		UIUtils.waitForUiThread();
		assertNotNull("Cannot show Project Explorer.", projectExplorer);
		commonViewer = projectExplorer.getCommonViewer();
		assertFalse("Expected projects as top level elements in navigator.", broker.isWorkingSetTopLevel());
		assertNull(
				"Select working set drop down contribution was visible when projects are configured as top level elements.",
				getWorkingSetDropDownContribution());

		final Multimap<ProjectType, String> typeNamesMapping = HashMultimap.create();

		typeNamesMapping.putAll(LIBRARY, LIBRARY_PROJECTS);
		typeNamesMapping.putAll(TEST, TEST_PROJECTS);
		for (final Entry<ProjectType, Collection<String>> entry : typeNamesMapping.asMap().entrySet()) {
			for (final String projectName : entry.getValue()) {
				createN4JSProject(projectName, entry.getKey());
			}
		}

		// Actually close "Closed*" projects
		closeProject("ClosedL2");
		closeProject("ClosedT2");

		// Wait for workbench to reflect project changes
		waitForIdleState();
		commonViewer.refresh();

		// Disable auto-building, as there is no real code to build involved
		ResourcesPlugin.getWorkspace().getDescription().setAutoBuilding(false);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		broker.resetState();
		commonViewer.refresh();
		waitForIdleState();

		final TreeItem[] treeItems = commonViewer.getTree().getItems();
		assertTrue("Expected empty Project Explorer. Input was: " + Arrays.toString(treeItems),
				Arrays2.isEmpty(treeItems));
		assertFalse("Expected projects as top level elements in navigator.", broker.isWorkingSetTopLevel());
		assertNull(
				"Select working set drop down contribution was visible when projects are configured as top level elements.",
				getWorkingSetDropDownContribution());

		IContributionItem showHiddenWorkingSetsItem = from(
				Arrays.asList(projectExplorer.getViewSite().getActionBars().getToolBarManager().getItems()))
						.firstMatch(i -> ShowHiddenWorkingSetsDropDownAction.class.getName().equals(i.getId()))
						.orNull();

		assertNull(
				"Show hidden working set drop down contribution was visible when projects are configured as top level elements.",
				showHiddenWorkingSetsItem);
	}

	/***/
	@Test
	public void testOnlyProjectsSelection() {
		// Set projects as top level
		broker.setWorkingSetTopLevel(false);
		waitForIdleState();

		commonViewer.expandToLevel(1);
		waitForIdleState();

		// Set open-projects-only selection
		setSelection(projectsWithName("L1", "T1"));

		assertContextMenuNoActionDuplicates();

		assertContextMenuContains(
				/* Project action group */ "Close Projects", "Close Unrelated Project",
				/* New action group */ "New",
				/* Edit actions */ "Delete", "Copy", "Paste",
				/* Refactor action group */ "Move...", "Rename...",
				/* Import Export action group */ "Import...", "Export...",
				/* Refresh action */ "Refresh");
		assertContextMenuDoesNotContain("Open Project");

		// Set one-open-one-closed projects-only selection
		setSelection(projectsWithName("L1", "ClosedL2"));

		assertContextMenuContains("Open Project");
	}

	/***/
	@Test
	public void testSingleProjectSelection() {
		// Set projects as top level
		broker.setWorkingSetTopLevel(false);
		waitForIdleState();

		commonViewer.expandToLevel(1);
		waitForIdleState();

		// Set open-projects-only selection
		setSelection(projectsWithName("L1"));

		assertContextMenuNoActionDuplicates();

		assertContextMenuContains(
				/* Project action group */ "Close Project", "Close Unrelated Project",
				/* New action group */ "New",
				/* Edit actions */ "Delete", "Copy", "Paste",
				/* Refactor action group */ "Move...", "Rename...",
				/* Import Export action group */ "Import...", "Export...",
				/* Refresh action */ "Refresh");
		assertContextMenuDoesNotContain("Open Project");
	}

	/***/
	@Test
	public void testMixedNavigatorSelection() throws CoreException {
		// Set projects as top level
		broker.setWorkingSetTopLevel(false);
		waitForIdleState();

		commonViewer.expandToLevel(2);
		waitForIdleState();

		// Set mixed projects-and-resources-selection
		setSelection(projectsWithName("L1", "T1"), childrenOfProject("T1"));

		assertContextMenuNoActionDuplicates();

		assertContextMenuContains("Close Project");
		assertContextMenuDoesNotContain("Open Project");

		// Add closed project to selection
		setSelection(projectsWithName("L1", "T1", "ClosedT2"), childrenOfProject("T1"));

		assertContextMenuContains("Close Project", "Open Project");
	}

	/***/
	@Test
	public void testResourcesOnlySelection() throws CoreException {
		// Set projects as top level
		broker.setWorkingSetTopLevel(false);
		waitForIdleState();

		commonViewer.expandToLevel(2);
		waitForIdleState();

		// Set resources-only-selection
		setSelection(childrenOfProject("T1"));

		assertContextMenuNoActionDuplicates();

		assertContextMenuDoesNotContain(/* Project action group */ "Close Project", "Open Project");
		assertContextMenuContains(
				/* New action group */ "New",
				/* Edit actions */ "Delete", "Copy", "Paste",
				/* Refactor action group */ "Move...", "Rename...",
				/* Import Export action group */ "Import...", "Export...",
				/* Refresh action */ "Refresh");
	}

	/***/
	@Test
	public void testSingleResourceSelection() {
		// Set projects as top level
		broker.setWorkingSetTopLevel(false);
		waitForIdleState();

		commonViewer.expandToLevel(2);
		waitForIdleState();

		IResource srcFolder = projectWithName("T1").findMember("src");
		// Set selection to first resource of project T1
		setSelection(new Object[] { srcFolder });

		assertContextMenuNoActionDuplicates();

		assertContextMenuDoesNotContain(/* Project action group */ "Close Project", "Open Project",
				"Close Unrelated projects");
		assertContextMenuContains(
				/* New action group */ "New",
				/* Edit actions */ "Delete", "Copy", "Paste",
				/* Refactor action group */ "Move...", "Rename...",
				/* Import Export action group */ "Import...", "Export...",
				/* Refresh action */ "Refresh");
	}

	/***/
	@Test
	public void testSelectAllCommand() throws ExecutionException {
		// Set projects as top level
		broker.setWorkingSetTopLevel(false);
		waitForIdleState();

		commonViewer.expandToLevel(2);
		waitForIdleState();

		// Make sure project explorer is in focus
		projectExplorer.setFocus();
		// Select all
		executeSelectAllCommand();
		waitForIdleState();

		// Test navigator selection to be complete
		StructuredSelection navigatorSelection = (StructuredSelection) commonViewer.getSelection();
		assertSelectionContainsAccessibleWorkspace(Arrays.asList(navigatorSelection.toArray()));

		assertContextMenuNoActionDuplicates();

		// Test context menu behavior
		assertContextMenuContains("Open Project", "Close Project");

		// If all is selected, there can't be unrelated projects
		assertContextMenuDoesNotContain("Close Unrelated Projects");
	}

	/***/
	@Ignore("random")
	@Test
	public void testWorkingSetAndProject() {
		// Set projects as top level
		broker.setWorkingSetTopLevel(true);
		waitForIdleState();

		activateWorkingSetManager(ProjectTypeAwareWorkingSetManager.class);
		waitForIdleState();

		Runnable actionWhileWaitingForUI = () -> {
			commonViewer.refresh();
			commonViewer.expandToLevel(3);
			waitForIdleState();
		};

		TreeItem libraryItem = waitForNavigatorItem("Library", WorkingSet.class, actionWhileWaitingForUI);

		setSelection(new Object[] { libraryItem }, projectsWithName("L1"));

		// Test context menu behavior
		assertContextMenuContains("Close Project");
	}

	/***/
	@Test
	public void testWorkingSetSelection() {
		// Set working sets to be top level representation
		broker.setWorkingSetTopLevel(true);
		waitForIdleState();

		activateWorkingSetManager(ProjectTypeAwareWorkingSetManager.class);
		waitForIdleState();

		commonViewer.expandToLevel(3);
		waitForIdleState();

		setSelection(projectsWithName("L1"));
		waitForIdleState();

		assertContextMenuNoActionDuplicates();

		assertContextMenuContains("Close Project");
		assertContextMenuDoesNotContain("Open Project");
	}

	/**
	 * Tests that the Top Level Elements
	 */
	@Test
	public void testTopLevelElementsEntryNoDuplicates() {
		IActionBars actionBars = projectExplorer.getViewSite().getActionBars();
		IMenuManager menuManager = actionBars.getMenuManager();

		int topLevelElementsEntriesFound = 0;

		for (IContributionItem item : menuManager.getItems()) {
			if (item instanceof MenuManager) {
				String escapedMenuText = LegacyActionTools.removeMnemonics(((MenuManager) item).getMenuText());
				if (escapedMenuText.equals("Top Level Elements")) {
					topLevelElementsEntriesFound++;
				}
			}
		}

		assertEquals("There was more than one 'Top Level Elements' entry in the navigator action bar.",
				topLevelElementsEntriesFound, 1);
	}

	/**
	 * Asserts that the given list of elements represents a complete selection of all accessible elements in the
	 * workspace.
	 */
	private void assertSelectionContainsAccessibleWorkspace(List<Object> selectedElements) {

		List<String> selectedProjectNames = selectedElements.stream()
				.filter(i -> i instanceof IProject)
				.map(p -> ((IProject) p).getName())
				.collect(Collectors.toList());

		ALL_PROJECT_NAMES.forEach(name -> {
			if (!selectedProjectNames.contains(name)) {
				fail("Project with name '" + name + "' wasn't selected by SELECT_ALL command.");
			}
		});

		final int OPEN_PROJECTS = NUMBER_OF_PROJECTS - NUMBER_OF_CLOSED_PROJECTS;
		final int NUMBER_OF_ACCESSIBLE_RESOURCES = NUMBER_OF_CLOSED_PROJECTS + OPEN_PROJECTS
				+ OPEN_PROJECTS * RESOURCES_PER_PROJECT;

		assertEquals("Number of selected elements should be number of accessible resources in workspace",
				NUMBER_OF_ACCESSIBLE_RESOURCES,
				selectedElements.size());
	}

	/**
	 * Asserts the context menu to contain the given action contributions (matches context menu item text)
	 */
	private void assertContextMenuContains(String... actions) {
		List<String> contextMenuActionContributions = getContextMenuContributions();
		for (String actionText : actions) {
			if (!contextMenuActionContributions.contains(actionText)) {
				fail("Context menu didn't contain action with text '" + actionText + "': "
						+ contextMenuActionContributions.toString());
			}
		}
	}

	/**
	 * Asserts that the context menu for the current navigator selection does not contain any duplicates.
	 *
	 * That is, two menu items that represent an action of the same class.
	 */
	private void assertContextMenuNoActionDuplicates() {
		MenuManager menu = new MenuManager();
		projectExplorer.getNavigatorActionService().fillContextMenu(menu);
		List<ActionContributionItem> actionContributions = Arrays.asList(menu.getItems()).stream()
				.filter(i -> i instanceof ActionContributionItem)
				.map(i -> ((ActionContributionItem) i))
				.collect(Collectors.toList());

		Map<String, ActionContributionItem> contributionNameMap = new HashMap<>();

		for (ActionContributionItem item : actionContributions) {
			ActionContributionItem mapItem = contributionNameMap.putIfAbsent(item.getAction().getText(), item);
			if (mapItem != null) {
				IAction mapAction = mapItem.getAction();
				IAction otherAction = item.getAction();

				// Double check if action is of the same type
				if (mapAction.getClass().equals(otherAction.getClass())) {
					fail("Action '" + mapAction.getClass().getSimpleName()
							+ "' is contributed twice to the context menu: "
							+ mapAction.toString() + " " + otherAction.toString());
				}
			}
		}
	}

	/**
	 * Asserts the context menu to not contain the given action contributions (matches context menu item text)
	 */
	private void assertContextMenuDoesNotContain(String... actions) {
		List<String> contextMenuActionContributions = getContextMenuContributions();
		for (String actionText : actions) {
			if (contextMenuActionContributions.contains(actionText)) {
				fail("Context menu contains unwanted action with text '" + actionText + "': "
						+ contextMenuActionContributions.toString());
			}
		}
	}

	/**
	 * Returns the menu contribution titles of the project navigator context menu.
	 *
	 * This only includes {@link ActionContributionItem}s and {@link MenuManager}s.
	 */
	private List<String> getContextMenuContributions() {
		MenuManager menu = new MenuManager();
		projectExplorer.getNavigatorActionService().fillContextMenu(menu);
		return Arrays.asList(menu.getItems()).stream()
				.map(i -> {
					if (i instanceof ActionContributionItem) {
						// use action name
						return ((ActionContributionItem) i).getAction().getText();
					} else if (i instanceof MenuManager) {
						// use sub-menu title
						return ((MenuManager) i).getMenuText();
					} else {
						// null for other types of contributions
						return null;
					}
				})
				.filter(t -> null != t)
				// remove mnemonics (e.g. Close &Project -> Close Project))
				.map(text -> LegacyActionTools.removeMnemonics(text))
				.collect(Collectors.toList());
	}

	private IProject[] projectsWithName(String... projectNames) {
		return Arrays.asList(projectNames).stream()
				.map(name -> projectWithName(name))
				.toArray(IProject[]::new);
	}

	private IProject projectWithName(String projectName) {
		IN4JSEclipseProject n4jsProject = eclipseN4jsCore.findProject(URI.createPlatformResourceURI(projectName, true))
				.orNull();
		if (null == n4jsProject) {
			throw new IllegalArgumentException("Could not find project with name '" + projectName + "'");
		}
		return n4jsProject.getProject();
	}

	private TreeItem waitForNavigatorItem(String name, Class<?> type, Runnable actionWhileWaiting) {
		return UIUtils.waitForValueFromUI(
				() -> {
					actionWhileWaiting.run();
					return getNavigatorItem(name, type);
				},
				() -> "tree item of type " + type.getSimpleName() + " with name '" + name + "'");
	}

	private Optional<TreeItem> getNavigatorItem(String name, Class<?> type) {
		Optional<TreeItem> item = Arrays.asList(commonViewer.getTree().getItems()).stream()
				.filter(i -> i.getText().equals(name) && type.isInstance(i.getData()))
				.findAny();
		return item;
	}

	/**
	 * Tries to close the project with the given name.
	 */
	private void closeProject(String projectName) {
		IN4JSEclipseProject n4jsProject = eclipseN4jsCore.findProject(URI.createPlatformResourceURI(projectName, true))
				.orNull();
		if (null == n4jsProject) {
			throw new IllegalArgumentException("Could not find project with name '" + projectName + "'");
		}
		try {
			n4jsProject.getProject().close(new NullProgressMonitor());
		} catch (CoreException e) {
			throw new IllegalArgumentException(
					"Could not close project with name '" + projectName + "': " + e.getMessage());
		}
	}

	/**
	 * Executes the select all command in the currently open workbench.
	 */
	private void executeSelectAllCommand() throws ExecutionException {
		Command selectAllCommand = PlatformUI.getWorkbench()
				.getService(CommandManager.class).getCommand(IWorkbenchCommandConstants.EDIT_SELECT_ALL);
		selectAllCommand.getHandler().execute(new ExecutionEvent());
	}

	private void setSelection(Object[]... items) {
		ArrayList<Object> mergedItems = new ArrayList<>();
		for (Object[] item : items) {
			mergedItems.addAll(Arrays.asList(item));
		}
		commonViewer.setSelection(new StructuredSelection(mergedItems));
		waitForIdleState();
	}

	private Object[] childrenOfProject(String projectName) throws CoreException {
		return projectWithName(projectName).members();
	}

	private void activateWorkingSetManager(final Class<? extends WorkingSetManager> clazz) {
		final WorkingSetManager manager = from(broker.getWorkingSetManagers())
				.firstMatch(m -> m.getId().equals(clazz.getName()))
				.orNull();
		checkNotNull(manager, "Working set manager does not exist with ID: " + clazz);
		broker.setActiveManager(manager);
		broker.setWorkingSetTopLevel(true);
		waitForIdleState();
		final IContributionItem dropDownContribution = getWorkingSetDropDownContribution();
		assertNotNull(
				"Select working set drop down contribution was null when working sets are configured as top level elements.",
				dropDownContribution);
	}

	/**
	 * Returns with the 'Select Working Set' action contribution if visible on the toolbar of the Project Explorer.
	 * Otherwise returns with {@code null}.
	 */
	private IContributionItem getWorkingSetDropDownContribution() {
		return from(asList(projectExplorer.getViewSite().getActionBars().getToolBarManager().getItems()))
				.firstMatch(item -> SelectWorkingSetDropDownAction.class.getName().equals(item.getId())).orNull();
	}

	@Override
	protected IProject createN4JSProject(String projectName, ProjectType type) throws CoreException {
		final IProject project = createJSProject(projectName, "src", "src-gen",
				b -> b.withType(type));
		configureProjectWithXtext(project);
		// Don't waitForBuild here as there is no code to build
		return project;
	}

}
