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
import static com.google.common.collect.Iterables.toArray;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static java.util.regex.Pattern.compile;
import static org.eclipse.n4js.projectDescription.ProjectType.LIBRARY;
import static org.eclipse.n4js.projectDescription.ProjectType.RUNTIME_ENVIRONMENT;
import static org.eclipse.n4js.projectDescription.ProjectType.RUNTIME_LIBRARY;
import static org.eclipse.n4js.projectDescription.ProjectType.TEST;
import static org.eclipse.n4js.ui.navigator.N4JSProjectExplorerProblemsDecorator.ERROR;
import static org.eclipse.n4js.ui.navigator.N4JSProjectExplorerProblemsDecorator.NO_ADORNMENT;
import static org.eclipse.n4js.ui.navigator.N4JSProjectExplorerProblemsDecorator.WARNING;
import static org.eclipse.n4js.ui.workingsets.WorkingSet.OTHERS_WORKING_SET_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.names.EclipseProjectName;
import org.eclipse.n4js.tests.util.EclipseUIUtils;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.navigator.N4JSProjectExplorerProblemsDecorator;
import org.eclipse.n4js.ui.navigator.internal.SelectWorkingSetDropDownAction;
import org.eclipse.n4js.ui.navigator.internal.ShowHiddenWorkingSetsDropDownAction;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.workingsets.ManualAssociationAwareWorkingSetManager;
import org.eclipse.n4js.ui.workingsets.ManualAssociationAwareWorkingSetManager.ManualAssociationWorkingSet;
import org.eclipse.n4js.ui.workingsets.ProjectNameFilterAwareWorkingSetManager;
import org.eclipse.n4js.ui.workingsets.ProjectNameFilterAwareWorkingSetManager.ProjectNameFilterWorkingSet;
import org.eclipse.n4js.ui.workingsets.ProjectTypeAwareWorkingSetManager;
import org.eclipse.n4js.ui.workingsets.ProjectTypeAwareWorkingSetManager.ProjectTypeWorkingSet;
import org.eclipse.n4js.ui.workingsets.WorkingSet;
import org.eclipse.n4js.ui.workingsets.WorkingSetDiffBuilder;
import org.eclipse.n4js.ui.workingsets.WorkingSetManager;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBrokerImpl;
import org.eclipse.n4js.ui.workingsets.internal.HideWorkingSetAction;
import org.eclipse.n4js.ui.workingsets.internal.N4JSProjectInWorkingSetDropAdapterAssistant;
import org.eclipse.n4js.utils.Diff;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.INavigatorDnDService;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.xtext.ui.testing.util.JavaProjectSetupUtil;
import org.eclipse.xtext.util.StringInputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

/**
 * Class for testing the functionality of the N4JS working set support.
 */
public class GHOLD_101_WorkingSetsTest_PluginUITest extends AbstractPluginUITest {

	@Inject
	private WorkingSetManagerBrokerImpl broker;

	private ProjectExplorer projectExplorer;

	private CommonViewer commonViewer;

	/**
	 * Asserts that the {@link IWorkbench workbench} is running.
	 */
	@BeforeClass
	public static void assertWorkbenchIsRunning() {
		assertTrue("Expected running workbench.", PlatformUI.isWorkbenchRunning());
	}

	/***/
	@Before
	public void setUp2() throws Exception {
		waitForIdleState();
		projectExplorer = (ProjectExplorer) EclipseUIUtils.showView(ProjectExplorer.VIEW_ID);
		UIUtils.waitForUiThread();
		assertNotNull("Cannot show Project Explorer.", projectExplorer);
		commonViewer = projectExplorer.getCommonViewer();
		assertFalse("Expected projects as top level elements in navigator.", broker.isWorkingSetTopLevel());
		assertNull(
				"Select working set drop down contribution was visible when projects are configured as top level elements.",
				getWorkingSetDropDownContribution());

	}

	/***/
	@After
	public void tearDown2() throws Exception {
		super.tearDown(); // called after this method again but necessary here already

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
	public void testWorkingSetIsTheSingleRootInNavaigator() {
		activateWorkingSetManager(ManualAssociationAwareWorkingSetManager.class);

		final TreeItem[] treeItems = commonViewer.getTree().getItems();
		assertTrue("Expected exactly one item in the Project Explorer. Input was: " + Arrays.toString(treeItems),
				treeItems.length == 1);

		final Object data = treeItems[0].getData();
		assertTrue("Expected " + WorkingSet.class + " input in navigator. Was " + data,
				data instanceof WorkingSet);

		final WorkingSet workingSet = (WorkingSet) treeItems[0].getData();
		assertTrue("Expected working set with ID: " + OTHERS_WORKING_SET_ID + ". Was " + workingSet.getId(),
				OTHERS_WORKING_SET_ID.equals(workingSet.getId()));

	}

	/***/
	@Test
	public void testMarkerSupportForWorkingSets() throws CoreException, IOException {
		final IProject project = createN4JSProject("P1", LIBRARY);
		createAndRegisterDummyN4JSRuntime(project);

		createTestFile(project.getFolder("src"), "A", "class a { }");

		final IFile moduleFile = project.getFolder("src").getFile("A.n4js");
		assertTrue("File is not accessible under src/A.n4js",
				moduleFile.isAccessible());

		// line 1: Class names should start with upper case letter.
		assertMarkers("Expected exactly one validation issue.", project, 1);

		activateWorkingSetManager(ManualAssociationAwareWorkingSetManager.class);

		final TreeItem[] treeItems = commonViewer.getTree().getItems();
		assertTrue("Expected exactly one item in the Project Explorer. Input was: " + Arrays.toString(treeItems),
				treeItems.length == 1);

		final Object data = treeItems[0].getData();
		assertTrue("Expected " + WorkingSet.class + " input in navigator. Was " + data,
				data instanceof WorkingSet);

		final WorkingSet workingSet = (WorkingSet) treeItems[0].getData();
		assertTrue("Expected working set with ID: " + OTHERS_WORKING_SET_ID + ". Was " + workingSet.getId(),
				OTHERS_WORKING_SET_ID.equals(workingSet.getId()));

		final N4JSProjectExplorerProblemsDecorator decorator = new N4JSProjectExplorerProblemsDecorator();
		int adornmentFlag = decorator.computeAdornmentFlags(workingSet);
		assertEquals("Adornment mismatch.", WARNING, adornmentFlag);

		try (final InputStream is = new StringInputStream("someBrokenContent")) {
			moduleFile.setContents(is, IResource.FORCE, null);
		}
		waitForAutoBuild();

		// line 1: Couldn't resolve reference to IdentifiableElement 'someBrokenContent'
		assertMarkers("Expected exactly one validation issue.", project, 1);

		adornmentFlag = decorator.computeAdornmentFlags(workingSet);
		assertEquals("Adornment mismatch.", ERROR, adornmentFlag);

		try (final InputStream is = new StringInputStream("class A { }")) {
			moduleFile.setContents(is, IResource.FORCE, null);
		}
		waitForAutoBuild();

		assertMarkers("Expected exactly zero validation issues.", project, 0);

		adornmentFlag = decorator.computeAdornmentFlags(workingSet);
		assertEquals("Adornment mismatch.", NO_ADORNMENT, adornmentFlag);

	}

	/***/
	@Test
	public void testProjectTypeWorkingSetGrouping() throws CoreException {

		final Multimap<ProjectType, String> typeNamesMapping = HashMultimap.create();
		typeNamesMapping.putAll(LIBRARY, newArrayList("L1", "L2", "L3"));
		typeNamesMapping.putAll(TEST, newArrayList("T1", "T2"));
		typeNamesMapping.putAll(RUNTIME_ENVIRONMENT, newArrayList("RE1", "RE2", "RE3", "RE4"));
		typeNamesMapping.putAll(RUNTIME_LIBRARY, newArrayList("RL1"));

		for (final Entry<ProjectType, Collection<String>> entry : typeNamesMapping.asMap().entrySet()) {
			for (final String projectName : entry.getValue()) {
				createN4JSProject(projectName, entry.getKey());
			}
		}

		final Collection<String> othersProjectNames = newArrayList("O1", "O2");
		for (final String projectName : othersProjectNames) {
			createJSProject(projectName);
		}

		activateWorkingSetManager(ProjectTypeAwareWorkingSetManager.class);
		commonViewer.expandToLevel(2);
		waitForIdleState();

		final TreeItem[] treeItems = commonViewer.getTree().getItems();
		final int expectedItemCount = ProjectType.values().length + 1;
		assertTrue("Expected exactly " + expectedItemCount + " items in the Project Explorer. Input was: "
				+ Arrays.toString(treeItems),
				treeItems.length == expectedItemCount);

		final List<ProjectTypeWorkingSet> workingSets = from(asList(treeItems)).transform(item -> item.getData())
				.filter(ProjectTypeWorkingSet.class)
				.toList();

		assertEquals("Mismatching number of working sets.", expectedItemCount, workingSets.size());

		for (final TreeItem treeItem : treeItems) {
			final ProjectType type = ((ProjectTypeWorkingSet) treeItem.getData()).getType();
			final Collection<String> expectedProjectNames;
			if (null == type) {
				expectedProjectNames = othersProjectNames;
			} else {
				expectedProjectNames = typeNamesMapping.get(type);
			}
			assertEquals("Child item count mismatch: " + treeItem, expectedProjectNames.size(),
					treeItem.getItemCount());
			for (final TreeItem child : treeItem.getItems()) {
				final String childText = child.getText();
				assertTrue("Unexpected tree item label: " + childText + ". Expected any of: "
						+ Iterables.toString(expectedProjectNames),
						expectedProjectNames.contains(childText));
			}
		}

	}

	/***/
	@Test
	public void testProjectNameFilterWorkingSetGrouping() throws CoreException {

		final Multimap<String, String> filterNamesMapping = LinkedHashMultimap.create();
		filterNamesMapping.putAll(OTHERS_WORKING_SET_ID, newArrayList(
				"org.eclipse.n4js.mangelhaft",
				"org.eclipse.n4js.mangelhaft.assert",
				"org.eclipse.n4js.mangelhaft.assert.test",
				"org.eclipse.n4js.mangelhaft.types",
				"eu.numberfour.mangelhaft.reporter.console",
				"eu.numberfour.mangelhaft.reporter.html",
				"org.eclipse.n4js.mangelhaft.reporter.ide",
				"org.eclipse.n4js.mangelhaft.reporter.ide.test",
				"eu.numberfour.mangelhaft.reporter.xunit",
				"eu.numberfour.mangelhaft.runner.html",
				"org.eclipse.n4js.mangelhaft.runner.ide",
				"eu.numberfour.mangelhaft.runner.node",
				"org.eclipse.n4js.mangelhaft.test"));

		filterNamesMapping.putAll(".*-runtime-.*", newArrayList(
				"n4js-runtime-es2015",
				"n4js-runtime-esnext",
				"n4js-runtime-fetch",
				"n4js-runtime-html5",
				"n4js-runtime-n4",
				"n4js-runtime-n4-tests",
				"n4js-runtime-node",
				"n4js-runtime-node-tests",
				"n4js-runtime-v8"));

		filterNamesMapping.putAll(".*lib.*", newArrayList(
				"org.eclipse.lib.format",
				"org.eclipse.lib.format.api",
				"org.eclipse.lib.format.api-tests",
				"org.eclipse.lib.i18n.api",
				"org.eclipse.lib.i18n.api-tests",
				"org.eclipse.lib.jtl",
				"org.eclipse.lib.jtl.api",
				"org.eclipse.lib.jtl.api-tests",
				"org.eclipse.lib.jtl.tests",
				"org.eclipse.lib.model.base",
				"org.eclipse.lib.model.base.api",
				"org.eclipse.lib.model.base.api-tests",
				"org.eclipse.lib.model.base.tests",
				"org.eclipse.lib.model.common",
				"org.eclipse.lib.model.common.api",
				"org.eclipse.lib.model.common.api-tests",
				"org.eclipse.lib.model.common.tests",
				"org.eclipse.lib.model.core",
				"org.eclipse.lib.model.core.api",
				"org.eclipse.lib.model.core.api-tests",
				"org.eclipse.lib.model.core.zoo.berlin",
				"org.eclipse.lib.model.gen",
				"org.eclipse.lib.model.gen.api",
				"org.eclipse.lib.model.gen.api-tests",
				"org.eclipse.lib.notificationCenter.api",
				"org.eclipse.lib.notificationCenter.api-tests",
				"org.eclipse.lib.npm-dependencies",
				"org.eclipse.lib.transaction",
				"org.eclipse.lib.transaction.api",
				"org.eclipse.lib.transaction.api-tests",
				"org.eclipse.lib.util"));

		filterNamesMapping.putAll(".*lib.*api.*", newArrayList(
				"org.eclipse.lib.format.api",
				"org.eclipse.lib.format.api-tests",
				"org.eclipse.lib.i18n.api",
				"org.eclipse.lib.i18n.api-tests",
				"org.eclipse.lib.jtl.api",
				"org.eclipse.lib.jtl.api-tests",
				"org.eclipse.lib.model.base.api",
				"org.eclipse.lib.model.base.api-tests",
				"org.eclipse.lib.model.common.api",
				"org.eclipse.lib.model.common.api-tests",
				"org.eclipse.lib.model.core.api",
				"org.eclipse.lib.model.core.api-tests",
				"org.eclipse.lib.model.gen.api",
				"org.eclipse.lib.model.gen.api-tests",
				"org.eclipse.lib.notificationCenter.api",
				"org.eclipse.lib.notificationCenter.api-tests",
				"org.eclipse.lib.transaction.api",
				"org.eclipse.lib.transaction.api-tests"));

		filterNamesMapping.putAll(".*lib.*api.*test.*", newArrayList(
				"org.eclipse.lib.format.api-tests",
				"org.eclipse.lib.i18n.api-tests",
				"org.eclipse.lib.jtl.api-tests",
				"org.eclipse.lib.model.base.api-tests",
				"org.eclipse.lib.model.common.api-tests",
				"org.eclipse.lib.model.core.api-tests",
				"org.eclipse.lib.model.gen.api-tests",
				"org.eclipse.lib.notificationCenter.api-tests",
				"org.eclipse.lib.transaction.api-tests"));

		final IWorkspaceDescription workspaceDescription = ResourcesPlugin.getWorkspace().getDescription();
		final boolean autoBuild = workspaceDescription.isAutoBuilding();
		try {
			// No need for the build at all.
			workspaceDescription.setAutoBuilding(false);
			for (final String projectName : filterNamesMapping.values()) {
				JavaProjectSetupUtil.createSimpleProject(projectName);
			}
		} finally {
			workspaceDescription.setAutoBuilding(autoBuild);
		}

		activateWorkingSetManager(ProjectNameFilterAwareWorkingSetManager.class);
		final WorkingSetManager manager = broker.getActiveManager();
		final WorkingSetDiffBuilder builder = new WorkingSetDiffBuilder(manager);
		final List<WorkingSet> workingSets = newArrayList();

		final WorkingSet other = new ProjectNameFilterWorkingSet(compile(OTHERS_WORKING_SET_ID), OTHERS_WORKING_SET_ID,
				manager);
		builder.add(other);
		workingSets.add(other);

		for (final String workingSetId : filterNamesMapping.keySet()) {
			final WorkingSet workingSet = new ProjectNameFilterWorkingSet(compile(workingSetId), workingSetId, manager);
			builder.add(workingSet);
			workingSets.add(workingSet);
		}

		final Diff<WorkingSet> diff = builder.build(toArray(workingSets, WorkingSet.class),
				toArray(workingSets, WorkingSet.class));

		manager.updateState(diff);
		broker.refreshNavigator();
		commonViewer.refresh();
		waitForIdleState();

		commonViewer.expandToLevel(2);
		waitForIdleState();

		final TreeItem[] treeItems = commonViewer.getTree().getItems();
		final int expectedItemCount = filterNamesMapping.keySet().size();
		assertTrue("Expected exactly " + expectedItemCount + " items in the Project Explorer. Input was: "
				+ Arrays.toString(treeItems),
				treeItems.length == expectedItemCount);

		final List<ProjectNameFilterWorkingSet> workingSetsFromTree = from(asList(treeItems))
				.transform(item -> item.getData())
				.filter(ProjectNameFilterWorkingSet.class)
				.toList();

		assertEquals("Mismatching number of working sets.", expectedItemCount, workingSetsFromTree.size());

		for (final TreeItem treeItem : treeItems) {
			final Pattern filter = ((ProjectNameFilterWorkingSet) treeItem.getData()).getFilter();
			final Collection<String> expectedProjectNames = filterNamesMapping.get(filter.pattern());
			assertEquals("Child item count mismatch: " + treeItem, expectedProjectNames.size(),
					treeItem.getItemCount());
			for (final TreeItem child : treeItem.getItems()) {
				final String childText = child.getText();
				assertTrue("Unexpected tree item label: " + childText + ". Expected any of: "
						+ Iterables.toString(expectedProjectNames),
						expectedProjectNames.contains(childText));
			}
		}

	}

	/***/
	@Test
	public void testHideShowWorkingSets() {
		activateWorkingSetManager(ProjectTypeAwareWorkingSetManager.class);
		TreeItem[] treeItems = commonViewer.getTree().getItems();
		final int expectedItemCount = ProjectType.values().length + 1;
		assertTrue("Expected exactly " + expectedItemCount + " items in the Project Explorer. Input was: "
				+ Arrays.toString(treeItems),
				treeItems.length == expectedItemCount);

		List<ProjectTypeWorkingSet> workingSets = from(asList(treeItems)).transform(item -> item.getData())
				.filter(ProjectTypeWorkingSet.class)
				.toList();

		assertEquals("Mismatching number of working sets.", expectedItemCount, workingSets.size());

		List<ProjectTypeWorkingSet> workingSetsToHide = workingSets;
		final HideWorkingSetAction hideAction = new HideWorkingSetAction();

		commonViewer.setSelection(new StructuredSelection(workingSets.toArray()));
		waitForIdleState();

		treeItems = commonViewer.getTree().getItems();
		final List<ProjectTypeWorkingSet> selectedWorkingSets = from(asList(treeItems))
				.transform(item -> item.getData())
				.filter(ProjectTypeWorkingSet.class)
				.toList();

		assertEquals(workingSetsToHide, selectedWorkingSets);
		hideAction.selectionChanged(commonViewer.getStructuredSelection());
		waitForIdleState();

		assertFalse("Expected disabled action.", hideAction.isEnabled());

		workingSetsToHide = newArrayList(workingSets.subList(0, 3));

		commonViewer.setSelection(new StructuredSelection(workingSetsToHide.toArray()));
		hideAction.selectionChanged(commonViewer.getStructuredSelection());
		waitForIdleState();

		assertTrue("Expected enabled action.", hideAction.isEnabled());

		hideAction.run();
		waitForIdleState();
		commonViewer.refresh();

		treeItems = commonViewer.getTree().getItems();
		workingSets = from(asList(treeItems)).transform(item -> item.getData())
				.filter(ProjectTypeWorkingSet.class)
				.toList();

		assertEquals("Mismatching number of working sets.", expectedItemCount - workingSetsToHide.size(),
				workingSets.size());

		for (final WorkingSet workingSet : workingSetsToHide) {
			assertTrue("Working set must not be visible in the navigator: " + workingSet,
					!workingSets.contains(workingSet));
		}

		IContributionItem showHiddenWorkingSetsItem = from(
				Arrays.asList(projectExplorer.getViewSite().getActionBars().getToolBarManager().getItems()))
						.firstMatch(i -> ShowHiddenWorkingSetsDropDownAction.class.getName().equals(i.getId()))
						.orNull();

		assertNotNull("Expected visible toolbar item, since there are hidden working sets.", showHiddenWorkingSetsItem);
		assertTrue("Expected a type of " + ActionContributionItem.class,
				showHiddenWorkingSetsItem instanceof ActionContributionItem);

		final IAction action = ((ActionContributionItem) showHiddenWorkingSetsItem).getAction();
		assertTrue("Expected a type of " + ShowHiddenWorkingSetsDropDownAction.class,
				action instanceof ShowHiddenWorkingSetsDropDownAction);
		final ShowHiddenWorkingSetsDropDownAction showHiddenWorkingSetsAction = (ShowHiddenWorkingSetsDropDownAction) action;

		Menu menu = showHiddenWorkingSetsAction.getMenu(commonViewer.getControl());

		assertTrue(
				"Expected " + workingSetsToHide.size()
						+ " menu item plus a separator plus an item for showing all hidden elements.",
				workingSetsToHide.size() + 2 == menu.getItemCount());

		menu.getItem(0).notifyListeners(SWT.Selection, null);
		waitForIdleState();
		workingSetsToHide.remove(0);
		waitForIdleState();
		commonViewer.refresh();

		treeItems = commonViewer.getTree().getItems();
		workingSets = from(asList(treeItems)).transform(item -> item.getData())
				.filter(ProjectTypeWorkingSet.class)
				.toList();

		assertEquals("Mismatching number of working sets.", expectedItemCount - workingSetsToHide.size(),
				workingSets.size());

		menu = showHiddenWorkingSetsAction.getMenu(commonViewer.getControl());
		assertTrue(
				"Expected " + workingSetsToHide.size()
						+ " menu item plus a separator plus an item for showing all hidden elements.",
				workingSetsToHide.size() + 2 == menu.getItemCount());

		menu.getItem(menu.getItemCount() - 1).notifyListeners(SWT.Selection, null);
		waitForIdleState();
		commonViewer.refresh();

		treeItems = commonViewer.getTree().getItems();
		assertTrue("Expected exactly " + expectedItemCount + " items in the Project Explorer. Input was: "
				+ Arrays.toString(treeItems),
				treeItems.length == expectedItemCount);

		showHiddenWorkingSetsItem = from(
				Arrays.asList(projectExplorer.getViewSite().getActionBars().getToolBarManager().getItems()))
						.firstMatch(i -> ShowHiddenWorkingSetsDropDownAction.class.getName().equals(i.getId()))
						.orNull();

		assertNull("Expected not visible toolbar item, since all working sets are visible.", showHiddenWorkingSetsItem);

		treeItems = commonViewer.getTree().getItems();
		assertTrue("Expected exactly " + expectedItemCount + " items in the Project Explorer. Input was: "
				+ Arrays.toString(treeItems),
				treeItems.length == expectedItemCount);

		workingSets = from(asList(treeItems)).transform(item -> item.getData())
				.filter(ProjectTypeWorkingSet.class)
				.toList();

		workingSetsToHide = newArrayList(workingSets.subList(0, 3));

		commonViewer.setSelection(new StructuredSelection(workingSetsToHide.toArray()));
		hideAction.selectionChanged(commonViewer.getStructuredSelection());
		waitForIdleState();

		assertTrue("Expected enabled action.", hideAction.isEnabled());

		hideAction.run();
		waitForIdleState();

		showHiddenWorkingSetsItem = from(
				Arrays.asList(projectExplorer.getViewSite().getActionBars().getToolBarManager().getItems()))
						.firstMatch(i -> ShowHiddenWorkingSetsDropDownAction.class.getName().equals(i.getId()))
						.orNull();

		// This state will be reseted in tear down phase.
		assertNotNull("Expected visible toolbar item, since there are hidden working sets.", showHiddenWorkingSetsItem);

	}

	/***/
	@Test
	public void testDndSupport() throws CoreException {

		final Collection<String> projectNames = newArrayList("A", "B", "C", "D", "E");
		final IWorkspaceDescription workspaceDescription = ResourcesPlugin.getWorkspace().getDescription();
		final boolean autoBuild = workspaceDescription.isAutoBuilding();
		try {
			// No need for the build at all.
			workspaceDescription.setAutoBuilding(false);
			for (final String projectName : projectNames) {
				JavaProjectSetupUtil.createSimpleProject(projectName);
				assertTrue(
						"Project " + projectName + " is not accessible.",
						ProjectTestsUtils.getProjectByName(new EclipseProjectName(projectName)).isAccessible());
			}
		} finally {
			workspaceDescription.setAutoBuilding(autoBuild);
		}

		activateWorkingSetManager(ManualAssociationAwareWorkingSetManager.class);
		WorkingSetManager manager = broker.getActiveManager();
		WorkingSetDiffBuilder builder = new WorkingSetDiffBuilder(manager);

		List<WorkingSet> workingSets = newArrayList();
		workingSets.add(new ManualAssociationWorkingSet(newArrayList(), "WS1", manager));
		workingSets.add(new ManualAssociationWorkingSet(newArrayList(), "WS2", manager));
		workingSets.add(new ManualAssociationWorkingSet(newArrayList(), "WS3", manager));

		for (WorkingSet workingSet : workingSets) {
			builder.add(workingSet);
		}

		workingSets.add(0, manager.getWorkingSets()[0]);
		Diff<WorkingSet> diff = builder.build(
				Iterables.toArray(workingSets, WorkingSet.class),
				Iterables.toArray(workingSets, WorkingSet.class));

		manager.updateState(diff);
		waitForIdleState();

		broker.refreshNavigator();
		commonViewer.refresh();
		waitForIdleState();

		commonViewer.expandToLevel(2);
		waitForIdleState();

		TreeItem[] treeItems = commonViewer.getTree().getItems();
		final int expectedItemCount = workingSets.size();
		assertTrue("Expected exactly " + expectedItemCount + " items in the Project Explorer. Input was: "
				+ Arrays.toString(treeItems),
				treeItems.length == expectedItemCount);

		for (TreeItem item : treeItems) {
			Object data = item.getData();
			assertTrue("Expected instance of working set. Was: " + data, data instanceof WorkingSet);
			WorkingSet workingSet = (WorkingSet) data;
			if (WorkingSet.OTHERS_WORKING_SET_ID.equals(workingSet.getId())) {
				assertEquals(
						"Expected " + projectNames.size() + " elements. Got: " + item.getItemCount(),
						projectNames.size(), item.getItemCount());
			} else {
				assertEquals(
						"Expected 0 elements. Got: " + item.getItemCount(),
						0, item.getItemCount());
			}
		}

		StructuredSelection selection = new StructuredSelection(ProjectTestsUtils.getProjectsByName(
				new EclipseProjectName("A"), new EclipseProjectName("B"), new EclipseProjectName("C")));
		commonViewer.setSelection(selection);
		assertEquals(3, commonViewer.getTree().getSelection().length);

		INavigatorDnDService dnDService = projectExplorer.getNavigatorContentService().getDnDService();
		CommonDropAdapterAssistant[] dropAdapterAssistants = dnDService
				.findCommonDropAdapterAssistants(manager.getWorkingSets()[1], commonViewer.getStructuredSelection());
		assertTrue(!Arrays2.isEmpty(dropAdapterAssistants));

		N4JSProjectInWorkingSetDropAdapterAssistant[] n4DropAdapterAssistants = Arrays2.filter(dropAdapterAssistants,
				N4JSProjectInWorkingSetDropAdapterAssistant.class);

		assertTrue(!Arrays2.isEmpty(n4DropAdapterAssistants));

		N4JSProjectInWorkingSetDropAdapterAssistant assistant = n4DropAdapterAssistants[0];
		CommonDropAdapter adapter = assistant.getCommonDropAdapter();

		LocalSelectionTransfer.getTransfer().setSelection(commonViewer.getStructuredSelection());
		assistant.handleDrop(adapter, null, manager.getWorkingSets()[1]);
		waitForIdleState();

		broker.refreshNavigator();
		commonViewer.refresh();
		waitForIdleState();

		commonViewer.expandToLevel(2);
		waitForIdleState();

		treeItems = commonViewer.getTree().getItems();

		for (TreeItem item : treeItems) {
			Object data = item.getData();
			assertTrue("Expected instance of working set. Was: " + data, data instanceof WorkingSet);
			WorkingSet workingSet = (WorkingSet) data;
			if (WorkingSet.OTHERS_WORKING_SET_ID.equals(workingSet.getId())) {
				assertEquals(
						"Expected " + (projectNames.size() - 3) + " elements. Got: " + item.getItemCount(),
						projectNames.size() - 3, item.getItemCount());
			} else if ("WS1".equals(workingSet.getId())) {
				assertEquals(
						"Expected 3 elements. Got: " + item.getItemCount(),
						3, item.getItemCount());
			} else {
				assertEquals(
						"Expected 0 elements. Got: " + item.getItemCount(),
						0, item.getItemCount());
			}
		}

	}

	private void activateWorkingSetManager(final Class<? extends WorkingSetManager> clazz) {
		final WorkingSetManager manager = from(broker.getWorkingSetManagers())
				.firstMatch(m -> m.getId().equals(clazz.getName()))
				.orNull();
		checkNotNull(manager, "Working set manager does not exist with ID: " + clazz);
		broker.setActiveManager(manager);
		broker.setWorkingSetTopLevel(true);
		commonViewer.refresh();
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

}
