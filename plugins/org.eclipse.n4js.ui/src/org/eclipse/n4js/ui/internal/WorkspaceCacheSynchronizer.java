/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.internal;

import static com.google.common.collect.FluentIterable.from;
import static org.eclipse.ui.PlatformUI.getWorkbench;
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.external.ExternalLibraryBuilder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.validation.CheckMode;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Synchronizer between the state of the Eclipse workspace (e.g. resources added/removed/changed) and the
 * {@link EclipseBasedN4JSWorkspace} cache of available {@link ProjectDescription}s.
 *
 * Registers itself as resource listener on instantiation.
 */
public class WorkspaceCacheSynchronizer implements IResourceChangeListener {

	private static final Logger LOGGER = Logger.getLogger(WorkspaceCacheSynchronizer.class);

	private static final String PLATFORM_RESOURCE_SCHEME = "platform:/resource";

	@Inject
	private ResourceUIValidatorExtension validatorExtension;

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private Provider<ExternalLibraryBuilder> builderProvider;

	@Inject
	private OwnResourceValidatorAwareValidatingEditorCallback editorCallback;

	/** Workspace instance to keep in sync with the Eclipse workspace. */
	private EclipseBasedN4JSWorkspace eclipseBasedWorkspace;

	/**
	 * Instantiates a new workspace cache synchronizer and registers its as resource listener.
	 */
	public WorkspaceCacheSynchronizer() {
		register();
	}

	/**
	 * Sets the {@link EclipseBasedN4JSWorkspace} that needs to be kept in sync with the Eclipse workspace.
	 */
	public void setWorkspace(EclipseBasedN4JSWorkspace workspace) {
		this.eclipseBasedWorkspace = workspace;
	}

	private void register() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this,
				IResourceChangeEvent.PRE_DELETE |
						IResourceChangeEvent.POST_CHANGE |
						IResourceChangeEvent.PRE_CLOSE);
	}

	/**
	 * Unregisters this synchronizer as listener in the current Eclipse workspace
	 *
	 * This will disable the synchronization.
	 */
	public void unregister() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getDelta() != null) {
			IResourceDelta eventDelta = event.getDelta();
			try {
				eventDelta.accept(new IResourceDeltaVisitor() {
					@Override
					public boolean visit(IResourceDelta delta) throws CoreException {
						final IResource resource = delta.getResource();
						if (resource == null) {
							return true;
						}
						final IPath path = resource.getFullPath();
						final int pathLength = path.segmentCount();

						if (pathLength <= 2) {
							handleResourceDelta(delta);
						}

						// only traverse the children of project-deltas (e.g. project description files)
						return pathLength == 0 || resource instanceof IProject;
					}
				});
			} catch (CoreException e) {
				LOGGER.error("Failed to process IResourceDelta", e);
			}
		}
	}

	private void handleResourceDelta(IResourceDelta delta) {
		if (delta.getKind() == IResourceDelta.ADDED || delta.getKind() == IResourceDelta.REMOVED) {
			if (IN4JSProject.PACKAGE_JSON.equals(delta.getFullPath().lastSegment())) {
				clearProjectCache();
				return;
			}
			if (delta.getResource() instanceof IProject) {
				clearProjectCache();
				return;
			}
			if (delta.getResource() instanceof IFolder) {
				if (isSourceContainerModification(delta)) {
					tryValidateProjectDescriptionFile(delta);
					tryValidateProjectDescriptionInEditor(delta);
					clearProjectCache(delta);
					return;
				}
			}
			return;
		} else if (delta.getKind() == IResourceDelta.CHANGED && delta.getResource() instanceof IProject) {
			if ((delta.getFlags() & IResourceDelta.OPEN) != 0) {
				clearProjectCache();
				return;
			}
			return;
		}
		if (packageJSONFileHasBeenChanged(delta)) {
			clearProjectCache(delta);
			return;
		}
		return;
	}

	private void tryValidateProjectDescriptionFile(final IResourceDelta delta) {
		final String fullPath = delta.getFullPath().toString();
		final URI folderUri = URI.createPlatformResourceURI(fullPath, true);
		final IN4JSProject project = n4jsCore.findProject(folderUri).orNull();
		if (null != project && project.exists()) {
			final URI projectDescriptionLocation = project.getProjectDescriptionLocation().orNull();
			if (null != projectDescriptionLocation) {
				final IFile packageJSON = delta.getResource().getProject().getFile(N4JSGlobals.PACKAGE_JSON);
				final ResourceSet resourceSet = n4jsCore.createResourceSet(Optional.of(project));
				final Resource resource = resourceSet.getResource(projectDescriptionLocation, true);
				final Job job = Job.create("Update validation markers for " + resource.getURI(), monitor -> {
					ISchedulingRule rule = builderProvider.get().getRule();
					try {
						Job.getJobManager().beginRule(rule, monitor);
						validatorExtension.updateValidationMarkers(packageJSON, resource, CheckMode.ALL,
								new NullProgressMonitor());
					} finally {
						Job.getJobManager().endRule(rule);
					}
					return Status.OK_STATUS;
				});
				job.setPriority(Job.INTERACTIVE);
				job.schedule();
			}
		}
	}

	private void tryValidateProjectDescriptionInEditor(final IResourceDelta delta) {
		if (isWorkbenchRunning()) {
			Runnable validateInEditorRunnable = () -> {
				final IWorkbenchWindow window = getWorkbench().getActiveWorkbenchWindow();
				if (null != window) {
					final IWorkbenchPage page = window.getActivePage();
					for (final IEditorReference editorRef : page.getEditorReferences()) {
						if (isEditorForProjectDescriptionResource(editorRef, delta.getResource())) {
							final IWorkbenchPart part = editorRef.getPart(true);
							if (part instanceof XtextEditor) {
								editorCallback.afterSave((XtextEditor) part);
								return;
							}
						}
					}
				}
			};

			Display.getDefault().asyncExec(validateInEditorRunnable);
		}
	}

	private boolean isEditorForProjectDescriptionResource(IEditorReference editorRef, IResource resource) {
		final IFile packageJSON = resource.getProject().getFile(N4JSGlobals.PACKAGE_JSON);
		if (!packageJSON.exists()) {
			return false;
		}
		try {
			final IEditorInput input = editorRef.getEditorInput();
			if (input instanceof IFileEditorInput) {
				return ((IFileEditorInput) input).getFile().equals(packageJSON);
			}
		} catch (final PartInitException e) {
			LOGGER.warn("Error while trying to get editor input for editor reference: " + editorRef, e);
		}
		return false;
	}

	private boolean isSourceContainerModification(final IResourceDelta delta) {
		final String fullPath = delta.getFullPath().toString();
		final URI folderUri = URI.createPlatformResourceURI(fullPath, true);
		final IN4JSProject project = n4jsCore.findProject(folderUri).orNull();
		if (null != project && project.exists()) {
			return from(project.getSourceContainers())
					.transform(container -> container.getLocation())
					.filter(uri -> uri.isPlatformResource())
					.transform(uri -> uri.toString())
					.transform(uri -> uri.replaceFirst(PLATFORM_RESOURCE_SCHEME, ""))
					.firstMatch(uri -> uri.equals(fullPath))
					.isPresent();
		}
		return false;
	}

	private boolean packageJSONFileHasBeenChanged(IResourceDelta delta) {
		return delta.getKind() == IResourceDelta.CHANGED
				&& delta.getResource().getType() == IResource.FILE
				&& IN4JSProject.PACKAGE_JSON.equalsIgnoreCase(delta.getFullPath().lastSegment());
	}

	private void clearProjectCache() {
		LOGGER.info("Clearing all cached project descriptions.");
		eclipseBasedWorkspace.discardEntries();
	}

	private void clearProjectCache(IResourceDelta delta) {
		IProject project = delta.getResource().getProject();
		LOGGER.info("Clearing cache for " + project.getProject().getName() + ".");

		final URI projectUri = URI.createPlatformResourceURI(project.getFullPath().toString(), true);
		eclipseBasedWorkspace.discardEntry(projectUri);
	}
}
