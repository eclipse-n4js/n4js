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
package org.eclipse.n4js.xpect.ui.runner;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.xpect.ui.N4IDEXpectUIPlugin;
import org.eclipse.xpect.Environment;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.XpectJavaModel;
import org.eclipse.xpect.XpectRequiredEnvironment;
import org.eclipse.xpect.setup.XpectSetupFactory;
import org.eclipse.xpect.state.Creates;
import org.eclipse.xpect.util.IXtInjectorProvider;
import org.eclipse.xpect.xtext.lib.setup.FileSetupContext;
import org.eclipse.xpect.xtext.lib.setup.InjectorSetup;
import org.eclipse.xpect.xtext.lib.setup.ThisFile;
import org.eclipse.xpect.xtext.lib.setup.ThisProject;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xpect.xtext.lib.setup.XtextStandaloneSetup;
import org.eclipse.xpect.xtext.lib.setup.XtextTestObjectSetup;
import org.eclipse.xpect.xtext.lib.setup.XtextValidatingSetup;
import org.eclipse.xpect.xtext.lib.setup.XtextWorkspaceSetup;
import org.eclipse.xpect.xtext.lib.setup.workspace.WorkspaceDefaultsSetup;
import org.eclipse.xpect.xtext.lib.util.XtextOffsetAdapter;
import org.eclipse.xpect.xtext.lib.util.XtextTargetSyntaxSupport;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Setup for running xpect in the product. Should be used instead of {@link XtextStandaloneSetup},
 * {@link XtextWorkspaceSetup} and {@link WorkspaceDefaultsSetup}.
 */
@XpectSetupFactory
@XpectImport({ XtextTargetSyntaxSupport.class, XtextTestObjectSetup.class,
		InjectorSetup.class, XtextValidatingSetup.class, XtextOffsetAdapter.class })
@XpectRequiredEnvironment(Environment.WORKBENCH)
public class N4IDEXpectFileSetup {

	/**
	 * see ENCODE_PLATFORM_RESOURCE_URIS in {@link org.eclipse.emf.common.util.URI}
	 */
	private static final boolean ENCODE_PLATFORM_RESOURCE_URIS = System
			.getProperty("org.eclipse.emf.common.util.URI.encodePlatformResourceURIs") != null
			&&
			!"false".equalsIgnoreCase(System
					.getProperty("org.eclipse.emf.common.util.URI.encodePlatformResourceURIs"));

	@Inject
	private IResourceSetProvider resourceSetProvider;

	private final FileSetupContext ctx;

	/**
	 * Creates setup with provided context. Adds members to provided injector.
	 */
	public N4IDEXpectFileSetup(FileSetupContext ctx, Injector injector) {
		this.ctx = ctx;
		injector.injectMembers(this);
	}

	/**
	 * Dummy factory for for {@link ThisFile}. Running xpect in product should never ask for its instances. On purpose
	 * throws {@link RuntimeException} to detect wrong xpect setup
	 */
	@Creates(ThisFile.class)
	public IFile createThisFile() {
		foo("createThisFile");
		return null;
	}

	/**
	 * Dummy factory for for {@link ThisProject}. Running xpect in product should never ask for its instances. On
	 * purpose throws {@link RuntimeException} to detect wrong xpect setup
	 */
	@Creates(ThisProject.class)
	public IProject createThisProject() {
		foo("createThisProject");
		return null;
	}

	/**
	 * Dummy factory for for {@link IWorkspace}. Running xpect in product should never ask for its instances. On purpose
	 * throws {@link RuntimeException} to detect wrong xpect setup
	 */
	@Creates
	public IWorkspace createWorkspace() {
		foo("createWorkspace");
		return null;
	}

	/**
	 * Convenience method.
	 *
	 * @throws RuntimeException
	 *             is throws with message containing provided parameter. Also logs error with default plugin logger.
	 */
	private void foo(String methodName) throws RuntimeException {
		RuntimeException re = new RuntimeException("unexpected call to " + methodName);
		N4IDEXpectUIPlugin.logError(getClass().getSimpleName() + " does not impleemnt " + methodName
				+ ", check xpect setups if it should", re);
		throw re;
	}

	/**
	 * Creates {@link N4JSResource} in new {@link ResourceSet}. Created resource has uri of processed xt file and its
	 * context. During creation resource factory is obtained dynamically to preserve bindings created by XPECT (see
	 * {@link org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup#configure})
	 *
	 */
	@Creates(ThisResource.class)
	public XtextResource createThisResource() throws IOException, CoreException {

		Entry<IFile, IProject> file2project = findTestResources();
		IFile xpectFile = file2project.getKey();
		IProject userProject = file2project.getValue();

		ResourceSet resourceSet = resourceSetProvider.get(userProject);
		URI xpectFilePlatformURI = URI.createPlatformResourceURI(xpectFile.getFullPath().toString(),
				ENCODE_PLATFORM_RESOURCE_URIS);

		Injector injector = IXtInjectorProvider.INSTANCE.getInjector(ctx.get(XpectJavaModel.class),
				xpectFilePlatformURI);
		Resource resource = injector.getInstance(IResourceFactory.class).createResource(xpectFilePlatformURI);

		resourceSet.getResources().add(resource);
		InputStream input = xpectFile.getContents();

		try {
			resource.load(input, null);
		} finally {
			if (input != null)
				input.close();
		}
		return (XtextResource) resource;
	}

	/**
	 * Finds {@link IFile} and containing {@link IProject} for xpect file that is currently processed.
	 *
	 * Looks through all available projects and finds all files that match (by {@link URI}) to currently processed xpect
	 * file. If only one file in only one project is found, returns that mapping, throws error in other cases
	 *
	 * @return {@link java.util.Map.Entry} map entry of file and containing project for a given
	 *         {@link org.eclipse.xpect.XpectFile}
	 */
	private Entry<IFile, IProject> findTestResources() throws RuntimeException {
		Map<IFile, IProject> files2projects = new HashMap<>();

		IProject[] worksapceProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

		String xtFileLocation = ctx.getXpectFileURI().toString();// file://C:/Users/Administrator/runtime-New_configuration/yyyyyyyyyyyyyyyyyy/src/TestFile_001.n4js.xt

		for (IProject iProject : worksapceProjects) {
			String projLoc = iProject.getLocationURI().toString();
			if (xtFileLocation.startsWith(projLoc)) {
				String lkp = xtFileLocation.substring(projLoc.length());// file:/C:/Users/Administrator/runtime-New_configuration/yyyyyyyyyyyyyyyyyy
				IFile lkpFile = (IFile) iProject.findMember(lkp);
				if (lkpFile != null) {
					files2projects.put(lkpFile, iProject);
				}
			}
		}

		RuntimeException re = null;
		switch (files2projects.size()) {
		case 0:
			re = new RuntimeException("cannot find any file and project for processed xpect file");
			N4IDEXpectUIPlugin.logError("no projects with files mathching " + xtFileLocation + " found", re);
			throw re;
		case 1:
			return files2projects.entrySet().iterator().next();

		default:
			re = new RuntimeException("cannot find single file and project for processed xpect file");
			StringBuilder sb = new StringBuilder("multiple projects matching " + xtFileLocation + " found");
			files2projects.forEach((file, project) -> {
				sb.append("\n file : " + file.getRawLocation().toString() + ", project :: "
						+ project.getRawLocation().toString());
			});
			N4IDEXpectUIPlugin.logError(sb.toString(), re);
			throw re;
		}
	}

}
