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
package org.eclipse.n4js.npmexporter.ui;

import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.core.runtime.IStatus.ERROR;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.compressors.CompressorException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

import com.google.common.base.Joiner;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.eclipse.n4js.npmexporter.NpmExporter;
import org.eclipse.n4js.npmexporter.NpmExporter.MergeResult;
import org.eclipse.n4js.npmexporter.validation.Diagnostician;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;

/**
 * Wizard for exporting N4JS Projects to npm.
 */
@SuppressWarnings({ "restriction", "javadoc" })
public class NpmExportWizard extends Wizard implements IExportWizard {

	/** global switch to enable experimental tool - runner. */
	public boolean enableToolRunner = false;

	@Inject
	private IN4JSEclipseCore n4JSCore;

	@Inject
	IResourceSetProvider resourceSetProvider;

	@Inject
	NpmExporter npmExporter;

	private ExportSelectionPage exportPage;
	private PackageJsonComparePage comparePage;
	private NpmToolRunnerPage toolRunnerPage;

	/** mapping between eclipse-ui projects and n4js projects */
	private BiMap<IProject, IN4JSEclipseProject> iP2in4jsP;

	/** triggers page-change based updates */
	private IPageChangedListener pageListener;

	/** tracks the necessity to show the merge-page */
	private List<IN4JSProject> requiresMerge;

	/** */
	public NpmExportWizard() {

		IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings section = workbenchSettings.getSection("NpmExportWizard");//$NON-NLS-1$
		if (section == null) {
			section = workbenchSettings.addNewSection("NpmExportWizard");//$NON-NLS-1$
		}
		setDialogSettings(section);
	}

	/** Check if the tool - runner feature is active. */
	private boolean runTools() {
		return enableToolRunner;
	}

	@Override
	public boolean performFinish() {

		String destination = exportPage.getDestinationValue();
		List<IProject> toExport = exportPage.getChosenProjects();
		boolean shouldPackAsTarball = exportPage.getShouldPackAsTarball();

		File folder = new File(destination);

		// remap all IProjects
		List<? extends IN4JSProject> toExportIN4JSProjects = mapToIN4JSProjects(toExport);

		if (runTools() && toolRunnerPage.isToolrunRequested()) {
			// bring to front.
			((WizardDialog) getContainer()).showPage(toolRunnerPage);
		}

		try {

			npmExporter.export(toExportIN4JSProjects, folder);

			if (shouldPackAsTarball) {
				npmExporter.tarAndZip(toExportIN4JSProjects, folder);
			}

			boolean runIt = runTools() && toolRunnerPage.queryRunTool();
			if (runIt) {
				final List<String> toolCommand = toolRunnerPage.getCommand();

				getContainer().run(true, true, new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						try {

							List<String> cmds = newArrayList();
							// cmds.add("echo"); // prepend with echo for debug TODO remove
							// cmds.addAll(toolCommand);
							cmds.add("bash");
							cmds.add("-login");
							cmds.add("-c");
							// cmds.addAll(toolCommand);
							cmds.add(Joiner.on(" ").join(toolCommand));

							System.out.println("Comman will be: " + Joiner.on(" :: ").join(cmds));

							for (IN4JSProject p : toExportIN4JSProjects) {

								String info = "Processing " + p.toString() + "\n";
								System.out.println(info);
								toolRunnerPage.appendText(info);

								File dir = npmExporter.exportDestination(p, folder);

								ProcessBuilder pb = new ProcessBuilder();
								pb.directory(dir);
								pb.command(cmds);
								pb.redirectErrorStream(true);
								Process proc = pb.start();

								// handle each of proc's streams in a separate thread
								ExecutorService handlerThreadPool = Executors.newFixedThreadPool(3);

								// handlerThreadPool.submit(new Runnable() {
								// @Override
								// public void run() {
								// // we want to write to the stdin of the process
								// BufferedWriter stdin = new BufferedWriter(
								// new OutputStreamWriter(proc.getOutputStream()));
								//
								// // read from our own stdin so we can write it to proc's stdin
								// BufferedReader myStdin =
								// new BufferedReader(new InputStreamReader(System.in));
								// String line = null;
								// try {
								// do {
								// line = myStdin.readLine();
								// stdin.write(String.format("%s%n", line));
								// stdin.flush();
								// } while(! "exit".equalsIgnoreCase(line));
								// } catch(IOException e) {
								// e.printStackTrace();
								// }
								// }
								// });

								handlerThreadPool.submit(new Runnable() {
									@Override
									public void run() {
										// we want to read the stdout of the process
										BufferedReader stdout = new BufferedReader(
												new InputStreamReader(proc.getInputStream()));
										String line;
										try {
											while (null != (line = stdout.readLine())) {
												System.err.printf("[stderr] %s%n", line);
												toolRunnerPage.appendConsoleOut(line);
											}
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								});

								handlerThreadPool.submit(new Runnable() {
									@Override
									public void run() {
										// we want to read the stderr of the process
										BufferedReader stderr = new BufferedReader(
												new InputStreamReader(proc.getErrorStream()));
										String line;
										try {
											while (null != (line = stderr.readLine())) {
												System.err.printf("[stderr] %s%n", line);
												toolRunnerPage.appendConsoleErr(line);
											}
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								});

								// wait for the process to terminate
								int exitCode = proc.waitFor();
								System.out.printf("Process terminated with exit code %d%n", exitCode);
								handlerThreadPool.shutdown();

							}

							// done with all projects.
							// wait for close.

							toolRunnerPage.queryCloseDialog();

						} catch (Exception e) {
							throw new InvocationTargetException(e);
						}
					}
				});
			}

		} catch (IOException | ArchiveException | CompressorException e) {

			e.printStackTrace();

			Status s = new Status(ERROR, NpmExporterActivator.PLUGIN_ID, "Error occured during export.", e);
			N4JSActivator.getInstance().getLog().log(s);

			return false;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// successfully done, then store relevant history:
		exportPage.finish();
		if (runTools()) {
			toolRunnerPage.finish();
		}

		return true;
	}

	/**
	 * @param toExport
	 * @return
	 */
	private List<? extends IN4JSProject> mapToIN4JSProjects(List<IProject> toExport) {
		List<? extends IN4JSProject> toExportIN4JSProjects = toExport.stream().map(p -> iP2in4jsP.get(p))
				.collect(Collectors.toList());
		return toExportIN4JSProjects;
	}

	@Override
	public void init(IWorkbench targetWorkbench, IStructuredSelection currentSelection) {

		// this.selection = currentSelection;

		List<?> selectedResources = IDE.computeSelectedResources(currentSelection);
		List<IProject> workspaceProjects = Arrays.asList(ResourcesPlugin.getWorkspace().getRoot().getProjects());

		// Find all selected projects
		Set<IProject> projects = selectedResources.stream()
				.filter(m -> m instanceof IResource)
				.map(m -> ((IResource) m).getProject())
				.filter(p -> p.isOpen()) // only open projects
				.collect(Collectors.toSet());
		// make the behavior predictable by ordering:
		TreeSet<IProject> sortedProjects = Sets
				.<IProject> newTreeSet((a, b) -> a.getName()
						.compareToIgnoreCase(b.getName()));
		sortedProjects.addAll(projects);

		// 0) turn into IN4JSProject and give and process further.
		// a) find out which projects fulfill the npm-"exportable"-contract
		// b) give back a list to the user what to export,
		// c) disable things not fullfilling the contract.
		// d) take choosing from the list and pass to exporter in non-ui package.

		// 0)
		List<IN4JSEclipseProject> rawN4jsProjects = Lists.newArrayList();
		iP2in4jsP = HashBiMap.create();
		for (IProject iProject : workspaceProjects) {
			IN4JSEclipseProject mappedIn4jsProject = map2In4js(iProject);
			if (mappedIn4jsProject != null) {
				rawN4jsProjects.add(mappedIn4jsProject);

				iP2in4jsP.put(iProject, mappedIn4jsProject);
			}
		}

		// filter out Non-N4JS-projects from initial selection.
		sortedProjects.retainAll(iP2in4jsP.keySet());

		// filter out all non-N4JS-projects from the workspace projects.
		ArrayList<IProject> filteredWorkspaceProjects = new ArrayList<>(workspaceProjects);
		filteredWorkspaceProjects.retainAll(iP2in4jsP.keySet());

		setWindowTitle("N4JS to npm Export");
		setNeedsProgressMonitor(true);

		Map<IProject, Boolean> selectedProjects = new HashMap<>();
		// Add all workspace projects to list, default selection value is false
		filteredWorkspaceProjects.forEach(project -> selectedProjects.put(project, false));
		// Override selection value for all initially selected projects
		sortedProjects.forEach(project -> selectedProjects.put(project, true));

		// exportPage = new ExportSelectionPage("Export Page", rawN4jsProjects, labelProvider);
		exportPage = new ExportSelectionPage("Export Page", selectedProjects);
		if (runTools())
			toolRunnerPage = new NpmToolRunnerPage("npm Execution Page");
		comparePage = new PackageJsonComparePage("Compare package.json Page");

		pageListener = new IPageChangedListener() {

			@Override
			public void pageChanged(PageChangedEvent event) {
				if (event.getSelectedPage() == comparePage) {
					udpatePackagJasonComparison();
				}
			}
		};
	}

	@Override
	public void setContainer(IWizardContainer wizardContainer) {

		IWizardContainer oldContainer = this.getContainer();
		if (oldContainer instanceof WizardDialog) {
			((WizardDialog) oldContainer).removePageChangedListener(pageListener);
		}

		if (wizardContainer instanceof WizardDialog) {
			WizardDialog wizDialog = (WizardDialog) wizardContainer;
			wizDialog.addPageChangedListener(pageListener);
		}

		super.setContainer(wizardContainer);
	}

	/**
	 * @param iProject
	 *            project from selection
	 * @return
	 */
	private IN4JSEclipseProject map2In4js(IProject iProject) {
		URI projectURI = URI.createPlatformResourceURI(iProject.getName(), true);
		IN4JSEclipseProject project = n4JSCore.findProject(projectURI).orNull();

		// Additionally check for existence to only obtain visible N4JS workspace projects.
		if (project.exists()) {
			return project;
		} else {
			return null;
		}
	}

	@Override
	public void addPages() {
		addPage(exportPage);
		addPage(comparePage);
		if (runTools()) {
			addPage(toolRunnerPage);
		}
	}

	/**
	 * @param checked
	 *            list of selected projects to export
	 * @throws InterruptedException
	 * @throws InvocationTargetException
	 */
	public void updateProjectsToExportSelection(Object[] checked)
			throws InvocationTargetException, InterruptedException {

		getContainer().run(true, true, new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					List<IProject> checkedProjects = newArrayList();
					for (Object o : checked)
						checkedProjects.add((IProject) o);
					List<? extends IN4JSProject> checkedIN4JSProjects = mapToIN4JSProjects(checkedProjects);

					// validate:
					final Diagnostician diag = new Diagnostician();
					List<IN4JSProject> _requiresMerge1 = newArrayList();
					for (IN4JSProject p : checkedIN4JSProjects) {
						diag.setActiveProject(p);
						npmExporter.validate(p, diag);
						if (npmExporter.requiresJsonMerge(p)) {
							_requiresMerge1.add(p);
						}
					}
					setRequireJsonMerge(_requiresMerge1);

					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {
							if (diag.isIssueFree()) {
								exportPage.setErrorMessage(null);
								exportPage.setErrorText("");
							} else {
								exportPage.setPageComplete(false);

								exportPage.setErrorMessage(
										"Validation errors for projects: "
												+ Joiner.on(", ").join(diag.projectsWithEntries().stream()
														.map(p -> p.getProjectId()).iterator()));
								String text = diag.asErrorText();

								exportPage.setErrorText(text);
							}

						}
					});

				} catch (Exception e) {
					throw new InvocationTargetException(e);
				}
			}
		});
	}

	/**
	 * @param requiresMerge
	 *            if some needs to be displayed
	 */
	protected void setRequireJsonMerge(List<IN4JSProject> requiresMerge) {
		this.requiresMerge = requiresMerge;
	}

	protected boolean hasRequiredJsonMerge() {
		return requiresMerge != null && !requiresMerge.isEmpty();
	}

	/**
	 *
	 */
	protected void udpatePackagJasonComparison() {
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						System.out.println("called to synch the package.jason-compareview");
						// prepare merge
						final List<MergeResult> mergeResults = new ArrayList<>();
						for (IN4JSProject p : requiresMerge) {
							mergeResults.add(npmExporter.readExistingAndMerge(p));
						}

						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								comparePage.updateOn(mergeResults);
							}
						});

					} catch (Exception e) {
						throw new InvocationTargetException(e);
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean needsProgressMonitor() {
		return true;
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {

		if (page == exportPage) {
			if (hasRequiredJsonMerge())
				return comparePage;
			else if (runTools())
				return toolRunnerPage;
		}
		if (page == comparePage) {
			if (runTools())
				return toolRunnerPage;
		}

		return super.getNextPage(page);
	}

	@Override
	public IWizardPage getPreviousPage(IWizardPage page) {

		if (page == comparePage) {
			return exportPage;
		}
		if (page == toolRunnerPage) {
			if (hasRequiredJsonMerge())
				return comparePage;
			else
				return exportPage;
		}

		return super.getPreviousPage(page);
	}

	@Override
	public boolean canFinish() {
		boolean ret = super.canFinish();
		if (!ret)
			return false;
		// all OK
		if (hasRequiredJsonMerge() && exportPage == getContainer().getCurrentPage()) {
			// but we need to show the merge.
			return false;
		}
		return true;
	}

}
