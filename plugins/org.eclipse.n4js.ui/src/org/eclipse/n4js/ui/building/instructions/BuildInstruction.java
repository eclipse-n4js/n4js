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
package org.eclipse.n4js.ui.building.instructions;

import static com.google.common.collect.Sets.newLinkedHashSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.generator.GeneratorException;
import org.eclipse.n4js.generator.ICompositeGenerator;
import org.eclipse.n4js.ui.generator.GeneratorMarkerSupport;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.ui.generator.IDerivedResourceMarkers;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;
import org.eclipse.xtext.util.Pair;

import com.google.inject.Injector;

/**
 * A {@link IBuildParticipantInstruction instruction} for a build. This is used to support clustering in the generator
 * which is not part of the Xtext framework.
 */
@SuppressWarnings("restriction")
public class BuildInstruction extends AbstractBuildParticipantInstruction {
	private static final Logger logger = Logger.getLogger(BuildInstruction.class);

	private final EclipseResourceFileSystemAccess2 access;
	private final Map<OutputConfiguration, Iterable<IMarker>> generatorMarkers;
	private final IStorage2UriMapper storage2UriMapper;
	private final Injector injector;
	private final Set<IFile> derivedResources = newLinkedHashSet();
	private final ICompositeGenerator compositeGenerator;

	/**
	 * Create a build instruction for the given project.
	 */
	public BuildInstruction(IProject project,
			Map<String, OutputConfiguration> outputConfigurations,
			IDerivedResourceMarkers derivedResourceMarkers,
			EclipseResourceFileSystemAccess2 access,
			Map<OutputConfiguration, Iterable<IMarker>> generatorMarkers,
			IStorage2UriMapper storage2UriMapper, ICompositeGenerator compositeGenerator,
			Injector injector) {
		super(project, outputConfigurations, derivedResourceMarkers);
		this.access = access;
		this.generatorMarkers = generatorMarkers;
		this.storage2UriMapper = storage2UriMapper;
		this.compositeGenerator = compositeGenerator;
		this.injector = injector;
	}

	@Override
	public void finish(List<Delta> deltas, IProgressMonitor progressMonitor) throws CoreException {
		for (Delta delta : deltas) {
			if (delta.getNew() == null) {
				String uri = delta.getUri().toString();
				recordDerivedResources(uri);
				deleteObsoleteResources(uri, progressMonitor);
			}
		}
		deleteEmptyDirectories(progressMonitor);
	}

	@Override
	public void process(Delta delta, ResourceSet resourceSet, IProgressMonitor progressMonitor) throws CoreException {
		access.setMonitor(progressMonitor);
		final String uri = delta.getUri().toString();
		recordDerivedResources(uri);
		access.setPostProcessor(new EclipseResourceFileSystemAccess2.IFileCallback() {

			@Override
			public boolean beforeFileDeletion(IFile file) {
				derivedResources.remove(file);
				needRebuild();
				return true;
			}

			@Override
			public void afterFileUpdate(IFile file) {
				handleFileAccess(file);
			}

			@Override
			public void afterFileCreation(IFile file) {
				handleFileAccess(file);
			}

			protected void handleFileAccess(IFile file) {
				try {
					derivedResources.remove(file);
					derivedResourceMarkers.installMarker(file, uri);
					needRebuild();
				} catch (CoreException e) {
					throw new RuntimeException(e);
				}
			}

		});
		if (delta.getNew() != null) {
			try {
				handleChangedContents(delta, project, resourceSet);
			} catch (OperationCanceledException e) {
				throw e;
			} catch (Exception e) {
				logger.error("Error during compilation of '" + delta.getUri() + "'.", e);
			}
		}
		access.flushSourceTraces();
		deleteObsoleteResources(uri, progressMonitor);
	}

	private void deleteEmptyDirectories(IProgressMonitor progressMonitor) throws CoreException {
		for (OutputConfiguration config : outputConfigurations.values()) {
			// skip output-configurations that emit files to the project root
			if (".".equals(config.getOutputDirectory())) {
				continue;
			}
			IFolder folder = project.getFolder(config.getOutputDirectory());
			if (null != folder && folder.exists()) {
				deleteEmptyDirectories(folder, progressMonitor);
				folder.getParent().refreshLocal(IResource.DEPTH_INFINITE, progressMonitor);
			}
		}
	}

	private void deleteObsoleteResources(final String uri, IProgressMonitor progressMonitor) throws CoreException {
		SubMonitor deleteMonitor = SubMonitor.convert(progressMonitor, derivedResources.size());
		for (IFile iFile : newLinkedHashSet(derivedResources)) {
			IMarker marker = derivedResourceMarkers.findDerivedResourceMarker(iFile, uri);
			if (marker != null)
				marker.delete();
			if (derivedResourceMarkers.findDerivedResourceMarkers(iFile).length == 0) {
				for (String outputName : outputConfigurations.keySet()) {
					access.deleteFile(iFile, outputName, deleteMonitor);
				}
				needRebuild();
			}
		}
	}

	private void recordDerivedResources(final String uri) {
		for (OutputConfiguration config : outputConfigurations.values()) {
			if (config.isCleanUpDerivedResources()) {
				Iterable<IMarker> markers = generatorMarkers.get(config);
				if (null != markers) {
					for (IMarker marker : markers) {
						String source = derivedResourceMarkers.getSource(marker);
						if (source != null && source.equals(uri)) {
							derivedResources.add((IFile) marker.getResource());
						}
					}
				}
			}
		}
	}

	private void deleteEmptyDirectories(IFolder folder, IProgressMonitor progressMonitor) throws CoreException {
		for (IResource member : folder.members()) {
			if (member instanceof IFolder) {
				deleteEmptyDirectories((IFolder) member, progressMonitor);
			}
		}
		if (folder.members().length == 0) {
			folder.delete(true, progressMonitor);
		}
	}

	private void handleChangedContents(Delta delta, IProject aProject, ResourceSet resourceSet) throws CoreException {
		// TODO: we will run out of memory here if the number of deltas is large enough
		Resource resource = resourceSet.getResource(delta.getUri(), true);
		if (shouldGenerate(resource, aProject)) {
			try {
				compositeGenerator.doGenerate(resource, access);
			} catch (RuntimeException e) {
				if (e instanceof GeneratorException) {
					N4JSActivator
							.getInstance()
							.getLog()
							.log(new Status(IStatus.ERROR, N4JSActivator.getInstance().getBundle().getSymbolicName(), e
									.getMessage(), e.getCause()));
				}
				if (e.getCause() instanceof CoreException) {
					throw (CoreException) e.getCause();
				}
				throw e;
			}
		}
	}

	private boolean shouldGenerate(Resource resource, IProject aProject) {
		try {
			Iterable<Pair<IStorage, IProject>> storages = storage2UriMapper.getStorages(resource.getURI());
			for (Pair<IStorage, IProject> pair : storages) {
				if (pair.getFirst() instanceof IFile && pair.getSecond().equals(aProject)) {
					IFile file = (IFile) pair.getFirst();
					int findMaxProblemSeverity = file.findMaxProblemSeverity(null, true, IResource.DEPTH_INFINITE);
					// If the generator itself placed an error marker on the resource, we have to ignore that error.
					// Easiest way here is to remove that error marker-type and look for other severe errors once more:
					if (findMaxProblemSeverity == IMarker.SEVERITY_ERROR) {
						// clean
						GeneratorMarkerSupport generatorMarkerSupport = injector
								.getInstance(GeneratorMarkerSupport.class);
						generatorMarkerSupport.deleteMarker(resource);
						// and recompute:
						findMaxProblemSeverity = file.findMaxProblemSeverity(null, true, IResource.DEPTH_INFINITE);
					}
					// the final decision to build:
					return findMaxProblemSeverity != IMarker.SEVERITY_ERROR;
				}
			}
			return false;
		} catch (CoreException exc) {
			throw new WrappedException(exc);
		}
	}
}
