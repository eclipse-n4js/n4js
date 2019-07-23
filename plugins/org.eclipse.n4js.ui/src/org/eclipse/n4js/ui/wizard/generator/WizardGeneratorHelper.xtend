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
package org.eclipse.n4js.ui.wizard.generator

import com.google.common.base.Optional
import com.google.common.io.Files
import com.google.inject.Inject
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.ArrayList
import java.util.Collection
import java.util.List
import java.util.Map
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.jface.text.BadLocationException
import org.eclipse.n4js.json.model.utils.JSONModelUtils
import org.eclipse.n4js.packagejson.model.edit.IJSONDocumentModification
import org.eclipse.n4js.packagejson.model.edit.PackageJsonModificationProvider
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.ui.changes.ChangeManager
import org.eclipse.n4js.ui.changes.IAtomicChange
import org.eclipse.n4js.ui.organize.imports.Interaction
import org.eclipse.n4js.ui.organize.imports.OrganizeImportsService
import org.eclipse.n4js.ui.wizard.model.AccessModifier
import org.eclipse.n4js.ui.wizard.model.ClassifierReference
import org.eclipse.n4js.ui.wizard.workspace.WorkspaceWizardModel
import org.eclipse.n4js.utils.Log
import org.eclipse.ui.part.FileEditorInput
import org.eclipse.xtext.resource.SaveOptions
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.model.IXtextDocument
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider
import org.eclipse.xtext.util.concurrent.IUnitOfWork

/**
 * This class contains commonly used methods when writing wizard generators.
 */
@Log
class WizardGeneratorHelper {
	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ChangeManager changeManager;

	@Inject
	private XtextDocumentProvider docProvider;

	@Inject
	private N4JSImportRequirementResolver requirementResolver;

	public static val LINEBREAK = "\n";

	/**
	 * Return the last character of a given file.
	 */
	public def String lastCharacterInFile(IFile file) {
		try {
			val contents = readFileAsString(file)
			return contents.charAt(contents.length-1).toString;
		} catch (Exception exc) {
			return "";
		}
	}

	/**
	 * Returns the given string with a trailing line break.
	 *
	 * If the string is empty no line break is added.
	 */
	public def String addLineBreak(String str) {
		if (str.empty) {
			str
		} else {
			str + WizardGeneratorHelper.LINEBREAK;
		}
	}

	/**
	 * Returns the given string with a trailing space.
	 *
	 * If the string is empty an empty string is returned.
	 * */
	public def String addSpace(String str) {
		if (str.empty) {
			str
		} else {
			str + " ";
		}
	}

	/**
	 * Returns the export statement if the modifier
	 * requires it or an empty string if not.
	 */
	public def String exportStatement(AccessModifier modifier) {
		if (modifier == AccessModifier.PROJECT || modifier == AccessModifier.PUBLIC) {
			"export"
		} else {
			""
		}
	}

	/**
	 * Returns the content of the file as a string.
	 */
	public def String readFileAsString(IFile file) throws IOException, CoreException, UnsupportedEncodingException {
		Files.toString(file.location.toFile, Charset.defaultCharset);
	}

	/**
	 * Inserts the corresponding import statements for the given model into an existing file.
	 *
	 * <p>The method tries to find the files import region and append the import statements to it</p>
	 */
	public def void insertImportStatements(XtextResource moduleResource, List<ImportRequirement> importRequirements ) {
		val importReplacement = requirementResolver.getImportStatementChanges(moduleResource, importRequirements);
		moduleResource.applyChanges(#[importReplacement]);
	}


	/**
	 * Returns true if the given path exists in the workspace.
	 *
	 * Note that the path must contain a project segment and at least one additional segment.
	 */
	public def boolean exists(IPath path) {
		if (null === path) {
			return false;
		}
		val member = ResourcesPlugin.workspace.root.findMember(path)
		if (null === member) {
			return false;
		}
		member.exists
	}

	/**
	 * Load and return the {@link XtextResource} at the given URI
	 */
	public def XtextResource getResource(URI moduleURI) {
		val resourceSet = n4jsCore.createResourceSet(Optional.fromNullable(null));
		val moduleResource = resourceSet.getResource(moduleURI, true);
		if (moduleResource instanceof XtextResource ) {
			return moduleResource
		}
		null
	}
	
	/**
	 * Retrieve the XtextDocument for the given resource and apply the changes
	 *
	 * @param resource The XtextResource to modify
	 * @param changes The changes to apply
	 * */
	public def boolean applyChanges(XtextResource resource,Collection<? extends IAtomicChange> changes){
		val IPath resourcePath = new Path(resource.getURI.toString).makeRelativeTo(new Path("platform:/resource/"));
		val IFile resourceFile = ResourcesPlugin.workspace.root.getFile(resourcePath);
		if (resourceFile.exists) {
			try {
				val FileEditorInput fileInput = new FileEditorInput(resourceFile);
				docProvider.connect(fileInput);
				val IXtextDocument document = docProvider.getDocument(fileInput) as IXtextDocument;
				docProvider.aboutToChange(fileInput);

				document.modify(
					new IUnitOfWork.Void<XtextResource>() {
						public override void process(XtextResource state) throws Exception {
							try {
								// Sort changes by descending offset to avoid conflicts
								changeManager.applyAllInSameDocument(changes.sortBy[offset].reverse(), document);
							} catch (BadLocationException e) {
								return;
							}
						}
					});

				docProvider.saveDocument(null, fileInput, document, true);
				docProvider.changed(fileInput);
				docProvider.disconnect(fileInput);

			} catch (Exception all) {
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

	/**
	 * Applies the given list of {@link IJSONDocumentModification}s to the given JSON resource.
	 * 
	 * Runs the JSON formatter on the whole file after applying the modification.
	 * 
	 * @param resource 
	 * 			The XtextResource to modify.
	 * @param changes 
	 * 			The JSON document modifications to apply.
	 * */
	public def boolean applyJSONModifications(XtextResource resource, Collection<? extends IJSONDocumentModification> modifications){
		val IPath resourcePath = new Path(resource.getURI.toString).makeRelativeTo(new Path("platform:/resource/"));
		val IFile resourceFile = ResourcesPlugin.workspace.root.getFile(resourcePath);
		val jsonDocument = JSONModelUtils.getDocument(resource);
		
		if (resourceFile.exists) {
			try {
				// apply all given modifications
				for (modification : modifications) {
					// TODO implement proper support for ISemanticModification based on an XtextDocument and use those instead.
					// For this to work, partial serialization and replacement needs to be enabled for the JSON language.
					modification.apply(jsonDocument);
				}
				
				// save updated resource and run formatter
				resource.save(SaveOptions.newBuilder.format.options.toOptionsMap);
			} catch (Exception all) {
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

	/**
	 * Run organize import on the given file and save it.
	 *
	 * This method works in the background without opening the graphical editor.
	 */
	public def void organizeImports(IFile file, IProgressMonitor mon) throws CoreException {
		OrganizeImportsService.organizeImportsInFile(file, Interaction.takeFirst, mon);
	}

	/**
	 * Return the project name of the containing project of the given uri.
	 */
	public def IN4JSProject projectOfUri(URI uri) {
		val projectOptional = n4jsCore.findProject(uri);
		if ( projectOptional.present ) {
			return projectOptional.get();
		}
		return null;
	}

	/**
	 * Returns the real or bound name of the classifier reference.
	 *
	 * Always prioritizes alias name over real name.
	 *
	 * @param reference The classifier reference
	 * @param aliasBindings The alias bindings, may be null
	 */
	public def String realOrAliasName(ClassifierReference reference, Map<URI,String> aliasBindings) {
		if (aliasBindings !== null && aliasBindings.containsKey(reference.uri)) {
			return aliasBindings.get(reference.uri);
		}
		return reference.classifierName;
	}

	/**
	 * Return the package.json modifications which need to be applied in order to allow referencing of 
	 * the given projects/runtime libraries.
	 *
	 * @param packageJson 
	 * 			The package.json resource
	 * @param model 
	 * 			The workspace wizard model
	 * @param referencedProjects 
	 * 			A list of the projects to be referenced
	 * @param moduleURI 
	 * 			The platform uri of the target module
	 *
	 * @returns A list of {@link IAtomicChange}s for the manifest resource.
	 */
	public def List<IJSONDocumentModification> projectDescriptionModifications(Resource packageJson, WorkspaceWizardModel model, List<IN4JSProject> referencedProjects, URI moduleURI) {
		val modifications = new ArrayList<IJSONDocumentModification>();
		
		// remove the containing project from the dependencies
		val referencedProjectsSet = referencedProjects.filter[ !it.projectName.rawName.equals(model.project.lastSegment) ].toSet;

		// add project dependency changes (includes added runtime libraries)
		modifications.add(PackageJsonModificationProvider.insertProjectDependencies(
			referencedProjectsSet.map[projectName.rawName].toList));
				
		// add required runtime library changes
		modifications.add(PackageJsonModificationProvider.insertRequiredRuntimeLibraries(referencedProjectsSet.filter [
			projectType == ProjectType.RUNTIME_LIBRARY
		].map[projectName.rawName].toList));
	
		return modifications;
	}
}
