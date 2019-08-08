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
package org.eclipse.n4js.ui.building;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.names.EclipseProjectName;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ui.N4JSClusteringBuilderConfiguration;
import org.eclipse.n4js.ui.building.BuilderStateLogger.BuilderState;
import org.eclipse.n4js.ui.building.instructions.IBuildParticipantInstruction;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.builder.IXtextBuilderParticipant.BuildType;
import org.eclipse.xtext.builder.clustering.ClusteringBuilderState;
import org.eclipse.xtext.builder.clustering.CurrentDescriptions;
import org.eclipse.xtext.builder.debug.IBuildLogger;
import org.eclipse.xtext.builder.impl.BuildData;
import org.eclipse.xtext.builder.impl.RegistryBuilderParticipant;
import org.eclipse.xtext.builder.impl.RegistryBuilderParticipant.DeferredBuilderParticipant;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

/**
 * Produces the compiled js files immediately after the validation in order save CPU cycles, e.g. the file is already
 * loaded and linked.
 *
 * <p>
 * N4JSGenerateImmediatelyBuilderState resp. its super class ClusteringBuilderState is the back bone of the Xtext build.
 * It performs creating / up- dating the Xtext Index as well as delegating to all registered builder participants. The
 * main adaption here is to set our class N4JSStatefulBuilderParticipant as adapter to the resource set.
 *
 * <p>
 * This part describes how the incremental builder works as of 26.04.2016. This also introduces a brief description how
 * the incremental builder was changes in the scope of GHOLD-134.
 *
 * <p>
 * Assume the following three projects <b>PA</b>, <b>PB</b> and <b>PC</b> where <b>PB</b> depends on <b>PA</b> and
 * <b>PC</b> depends on <b>PB</b>, hence <b>PC</b> transitively depends on <b>PA</b>. Each project has one single module
 * with one single public exported class, <b>PA</b> has {@code A}, <b>PB</b> has {@code B} and <b>PC</b> has {@code C}.
 * Class {@code A} is extended by {@code B} and class {@code C} extends {@code B}, hence {@code C} has an implicit
 * {@code A} super type.
 *
 * <p>
 * The classes have the below implementations:
 *
 * <pre>
 * export public class A { public foo(): void { } }
 *
 * export public class B extends A { }
 *
 * export public class C extends B { public bar(): { this.foo(); } }
 * </pre>
 *
 * Also let assume our workspace contains no other projects and any external libraries are available. Furthermore, the
 * workspace contains neither validation warnings nor errors. Our intention is to get rid of the public {@code foo}
 * method from class {@code A} and we expect a validation error in class project <b>PC</b> at class {@code C}. After
 * getting rid of the method {@code foo} in class {@code A} and saving the editor content the incremental builder kicks
 * in and we arrive in the {@link ClusteringBuilderState}.
 *
 * First iteration with project <b>PA</b> (as of 27.04.2016):
 * <p>
 * <ol>
 * <li>{@link ToBeBuilt} contains one URI that has to be updated. The URI of the module with class {@code A}.</li>
 * <li>After calculating all other available resource URIs we will have a set of all workspace URIs but the module that
 * contains class {@code A}.</li>
 * <li>All deltas is initially empty at this point and the build queue contains module A and the manifest of project
 * <b>PA</b>.</li>
 * <li>While iterating through the build queue we recognize that class {@code A} in module A has changed, hence the
 * corresponding serialized {@link TModule} state differs between the old and the new state, hence we put module A into
 * the changed deltas.</li>
 * <li>After processing all elements in the current build queue, we have to queue all affected resources as well via the
 * {@link #queueAffectedResources(Set, IResourceDescriptions, CurrentDescriptions, Collection, Collection, BuildData, IProgressMonitor)
 * queueAffectedResources} method.</li>
 * <li>This method will consider module B for class {@code B} as an affected one (since {@code B} imports the
 * {@link QualifiedName FQN} of class {@code A} into {@code B} and the is a direct dependency between the container
 * projects) <b>AND</b> will wrap the {@link ResourceDescriptionsData} into a custom resource description delta that
 * hides the obsolete serialized {@link TModule} information.</li>
 * <li>The clustering builder after re-validating and re-generating module A will return with a set of deltas containing
 * module A and the manifest of project <b>PA</b>.</li>
 * </ol>
 *
 * <p>
 * Due to the changed deltas and the {@link IProjectDescription#getDynamicReferences() dynamic} project references we
 * will arrive in the clustering builder state again with the project <b>PB</b>. The {@link ToBeBuilt} instance will be
 * empty but from the previous cycle the module for class {@code B} has been queued. Since in the previous cycle we have
 * "invalidated" the serialized {@link TModule} information for module B we will consider class {@code B} as a changed
 * one and based on the above described workflow we will rebuild module B and queue module C.
 *
 * <p>
 * This class gets injected by {@link N4JSClusteringBuilderConfiguration}.
 */
@SuppressWarnings("restriction")
public class N4JSGenerateImmediatelyBuilderState extends N4ClusteringBuilderState {

	@Inject
	private RegistryBuilderParticipant builderParticipant;

	@Inject
	@BuilderState
	private IBuildLogger builderStateLogger;

	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private void injectExternalLibraryWorkspace(ISharedStateContributionRegistry contributionRegistry) {
		try {
			// we are in the context of shared xtext injector
			this.externalLibraryWorkspace = contributionRegistry
					.getSingleContributedInstance(ExternalLibraryWorkspace.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Initializes an adapter which is attached to the builder's resource set. This adapter will be used later on to
	 * process each delta after the corresponding resource was validated.
	 *
	 * @param buildData
	 *            the data that should be considered for the update
	 * @param newData
	 *            the new resource descriptions as they are to be persisted (the new index after the build). Initially
	 *            contains the old resource descriptions.
	 * @param monitor
	 *            The progress monitor
	 * @return A list of deltas describing all changes made by the build.
	 */
	@Override
	protected Collection<Delta> doUpdate(BuildData buildData, ResourceDescriptionsData newData,
			IProgressMonitor monitor) {

		builderStateLogger.log("N4JSGenerateImmediatelyBuilderState.doUpdate() >>>");
		monitor.subTask("Building " + buildData.getProjectName());
		logBuildData(buildData, " of before #doUpdate");

		try (Measurement m = N4JSDataCollectors.dcBuild.getMeasurement("build " + Instant.now());) {
			try {
				IBuildParticipantInstruction instruction = IBuildParticipantInstruction.NOOP;

				IProject project = findProject(buildData);
				if (project != null) {
					BuildType buildType = N4JSBuildTypeTracker.getBuildType(project);
					instruction = findJSBuilderParticipant().prepareBuild(project, buildType);
				}

				// removed after the build automatically;
				// the resource set is discarded afterwards, anyway
				buildData.getResourceSet().eAdapters().add(instruction);
			} catch (CoreException e) {
				handleCoreException(e);
			}
			Collection<Delta> modifiedDeltas = super.doUpdate(buildData, newData, monitor);
			logBuildData(buildData, " of after #doUpdate");
			builderStateLogger.log("Modified deltas: " + modifiedDeltas);
			builderStateLogger.log("N4JSGenerateImmediatelyBuilderState.doUpdate() <<<");
			return modifiedDeltas;
		}
	}

	private void logBuildData(BuildData buildData, String... tags) {
		// This log call sometimes yields a ConcurrentModificationException (see GHOLD-296)
		// We disable it as a temporary fix only until GHOLD-296 is resolved.
		// TODO Uncomment the following code when GHOLD-296 is resolved and remove the SuppressWarnings annotation.

		// UPDATE as of Nov 2017 (mor):
		// commented logging back in after (hopefully) fixing the ConcurrentModificationException
		// (but keeping these comments for reference, for now; if this does not cause problems over the next few weeks,
		// this comment and the previous comments in this method can be removed)

		String tag = Arrays2.isEmpty(tags) ? "" : Joiner.on(" - ").join(tags);
		String header = "---------------------- Build data" + tag + " --------------------------------------";
		builderStateLogger.log(header);
		builderStateLogger.log("Project name: " + buildData.getProjectName());
		builderStateLogger.log("To be deleted: " + ensureNotNull(buildData.getToBeDeleted()));
		builderStateLogger.log("To be updated: " + ensureNotNull(buildData.getToBeUpdated()));
		builderStateLogger.log("URI queue: " + buildData.getURIQueue());
		builderStateLogger.log("All remaining URIs: " + buildData.getAllRemainingURIs());
		builderStateLogger.log(Strings.repeat("-", header.length()) + "\n");
	}

	@Override
	protected void updateMarkers(Delta delta, ResourceSet resourceSet, IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 2);
		URI uri = delta.getUri();
		DataCollector dc = uri != null && N4JSGlobals.PACKAGE_JSON.equals(uri.lastSegment())
				? N4JSDataCollectors.dcValidationsPackageJson
				: N4JSDataCollectors.dcValidations;
		try (Measurement m = dc.getMeasurement("validation");) {
			super.updateMarkers(delta, resourceSet, monitor);
		}

		if (resourceSet != null) { // resourceSet is null during clean build

			IBuildParticipantInstruction instruction = (IBuildParticipantInstruction) EcoreUtil.getAdapter(
					resourceSet.eAdapters(), IBuildParticipantInstruction.class);
			if (instruction == null) {
				throw new IllegalStateException();
			}
			try (Measurement m = N4JSDataCollectors.dcTranspilation.getMeasurement("transpilation");) {
				instruction.process(delta, resourceSet, subMonitor.split(1));

			} catch (CoreException e) {
				handleCoreException(e);
			}
		}
	}

	@Override
	protected void clearResourceSet(final ResourceSet resourceSet) {
		N4JSResourceSetCleanerUtils.clearResourceSet(resourceSet);
	}

	private N4JSBuilderParticipant findJSBuilderParticipant() {
		ImmutableList<IXtextBuilderParticipant> all = builderParticipant.getParticipants();
		for (IXtextBuilderParticipant candidate : all) {
			if (candidate instanceof DeferredBuilderParticipant) {
				DeferredBuilderParticipant dbp = (DeferredBuilderParticipant) candidate;
				if (isParticipating(dbp)) {
					IXtextBuilderParticipant delegate = dbp.getDelegate();
					if (delegate instanceof N4JSBuilderParticipant) {
						return (N4JSBuilderParticipant) delegate;
					}
				}
			}
			// N4JSBuilderParticipant is never directly used, it's always delegated to via an DeferredBuilderParticipant
		}
		throw new IllegalStateException();
	}

	/**
	 * Check if given build participant is supporting given file type
	 */
	private boolean isParticipating(DeferredBuilderParticipant dbp) {
		// TODO IDE-2493 multilanguage support
		// @Inject FileExtensionProvider
		for (String ext : N4JSGlobals.ALL_N4_FILE_EXTENSIONS) {
			if (dbp.isParticipating(ext)) {
				return true;
			}
		}
		return false;
	}

	private void handleCoreException(CoreException e) {
		N4JSActivator.getInstance().getLog()
				.log(new Status(IStatus.ERROR, N4JSActivator.ORG_ECLIPSE_N4JS_N4JS, e.getMessage(), e));
	}

	/** logic of {@link IN4JSCore#findAllProjects()} with filtering by name */
	private IProject findProject(BuildData buildData) {
		String eclipseProjectName = buildData.getProjectName();
		if (Strings.isNullOrEmpty(eclipseProjectName)) {
			return null;
		}
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject project = root.getProject(eclipseProjectName); // creates a project instance if not existing

		if (null == project || !project.isAccessible()) {
			N4JSProjectName n4jsProjectName = new EclipseProjectName(eclipseProjectName).toN4JSProjectName();

			final IProject externalProject = externalLibraryWorkspace.getProject(n4jsProjectName);
			if (null != externalProject && externalProject.exists()) {
				project = externalProject;
			}
		}

		return project;
	}

}
