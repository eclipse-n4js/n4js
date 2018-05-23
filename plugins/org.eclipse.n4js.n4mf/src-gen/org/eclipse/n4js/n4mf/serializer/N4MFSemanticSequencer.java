/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4mf.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.n4js.n4mf.BootstrapModule;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.ModuleFilter;
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.n4mf.SourceContainerDescription;
import org.eclipse.n4js.n4mf.VersionConstraint;
import org.eclipse.n4js.n4mf.services.N4MFGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;

@SuppressWarnings("all")
public class N4MFSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private N4MFGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == N4mfPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case N4mfPackage.BOOTSTRAP_MODULE:
				sequence_BootstrapModule(context, (BootstrapModule) semanticObject); 
				return; 
			case N4mfPackage.DECLARED_VERSION:
				sequence_DeclaredVersion(context, (DeclaredVersion) semanticObject); 
				return; 
			case N4mfPackage.MODULE_FILTER:
				sequence_ModuleFilter(context, (ModuleFilter) semanticObject); 
				return; 
			case N4mfPackage.MODULE_FILTER_SPECIFIER:
				sequence_ModuleFilterSpecifier(context, (ModuleFilterSpecifier) semanticObject); 
				return; 
			case N4mfPackage.PROJECT_DEPENDENCY:
				sequence_ProjectDependency_ProjectIdWithOptionalVendor(context, (ProjectDependency) semanticObject); 
				return; 
			case N4mfPackage.PROJECT_DESCRIPTION:
				sequence_ProjectDescription(context, (ProjectDescription) semanticObject); 
				return; 
			case N4mfPackage.PROJECT_REFERENCE:
				sequence_ProjectIdWithOptionalVendor(context, (ProjectReference) semanticObject); 
				return; 
			case N4mfPackage.SOURCE_CONTAINER_DESCRIPTION:
				sequence_SourceContainerDescription(context, (SourceContainerDescription) semanticObject); 
				return; 
			case N4mfPackage.VERSION_CONSTRAINT:
				sequence_VersionConstraint(context, (VersionConstraint) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     BootstrapModule returns BootstrapModule
	 *
	 * Constraint:
	 *     (moduleSpecifierWithWildcard=STRING sourcePath=STRING?)
	 */
	protected void sequence_BootstrapModule(ISerializationContext context, BootstrapModule semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     DeclaredVersion returns DeclaredVersion
	 *
	 * Constraint:
	 *     (major=INT (minor=INT micro=INT?)? qualifier=N4mfIdentifier?)
	 */
	protected void sequence_DeclaredVersion(ISerializationContext context, DeclaredVersion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ModuleFilterSpecifier returns ModuleFilterSpecifier
	 *
	 * Constraint:
	 *     (moduleSpecifierWithWildcard=STRING sourcePath=STRING?)
	 */
	protected void sequence_ModuleFilterSpecifier(ISerializationContext context, ModuleFilterSpecifier semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ModuleFilter returns ModuleFilter
	 *
	 * Constraint:
	 *     (moduleFilterType=ModuleFilterType moduleSpecifiers+=ModuleFilterSpecifier moduleSpecifiers+=ModuleFilterSpecifier*)
	 */
	protected void sequence_ModuleFilter(ISerializationContext context, ModuleFilter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ProjectDependency returns ProjectDependency
	 *
	 * Constraint:
	 *     (declaredVendorId=N4mfIdentifier? projectId=N4mfIdentifier versionConstraint=VersionConstraint? declaredScope=ProjectDependencyScope?)
	 */
	protected void sequence_ProjectDependency_ProjectIdWithOptionalVendor(ISerializationContext context, ProjectDependency semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ProjectDescription returns ProjectDescription
	 *
	 * Constraint:
	 *     (
	 *         (
	 *             projectId=N4mfIdentifier | 
	 *             projectType=ProjectType | 
	 *             projectVersion=DeclaredVersion | 
	 *             vendorId=N4mfIdentifier | 
	 *             vendorName=STRING | 
	 *             mainModule=STRING | 
	 *             extendedRuntimeEnvironment=ProjectReference | 
	 *             implementationId=N4mfIdentifier | 
	 *             execModule=BootstrapModule | 
	 *             outputPathRaw=STRING | 
	 *             sourceContainers+=SourceContainerDescription | 
	 *             moduleFilters+=ModuleFilter | 
	 *             moduleLoader=ModuleLoader
	 *         )? 
	 *         (libraryPathsRaw+=STRING libraryPathsRaw+=STRING*)? 
	 *         (resourcePathsRaw+=STRING resourcePathsRaw+=STRING*)? 
	 *         (testedProjects+=ProjectDependency testedProjects+=ProjectDependency*)? 
	 *         (initModules+=BootstrapModule initModules+=BootstrapModule*)? 
	 *         (implementedProjects+=ProjectReference implementedProjects+=ProjectReference*)? 
	 *         (projectDependencies+=ProjectDependency projectDependencies+=ProjectDependency*)? 
	 *         (requiredRuntimeLibraries+=ProjectReference requiredRuntimeLibraries+=ProjectReference*)? 
	 *         (providedRuntimeLibraries+=ProjectReference providedRuntimeLibraries+=ProjectReference*)?
	 *     )+
	 */
	protected void sequence_ProjectDescription(ISerializationContext context, ProjectDescription semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ProjectReference returns ProjectReference
	 *
	 * Constraint:
	 *     (declaredVendorId=N4mfIdentifier? projectId=N4mfIdentifier)
	 */
	protected void sequence_ProjectIdWithOptionalVendor(ISerializationContext context, ProjectReference semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     SourceContainerDescription returns SourceContainerDescription
	 *
	 * Constraint:
	 *     (sourceContainerType=SourceContainerType pathsRaw+=STRING pathsRaw+=STRING*)
	 */
	protected void sequence_SourceContainerDescription(ISerializationContext context, SourceContainerDescription semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     VersionConstraint returns VersionConstraint
	 *
	 * Constraint:
	 *     ((exclLowerBound?='('? lowerVersion=DeclaredVersion (upperVersion=DeclaredVersion exclUpperBound?=')'?)?) | lowerVersion=DeclaredVersion)
	 */
	protected void sequence_VersionConstraint(ISerializationContext context, VersionConstraint semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
