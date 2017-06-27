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
import org.eclipse.n4js.n4mf.ExecModule;
import org.eclipse.n4js.n4mf.ExtendedRuntimeEnvironment;
import org.eclipse.n4js.n4mf.ImplementedProjects;
import org.eclipse.n4js.n4mf.InitModules;
import org.eclipse.n4js.n4mf.ModuleFilter;
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectDependencies;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.n4mf.ProvidedRuntimeLibraries;
import org.eclipse.n4js.n4mf.ProvidedRuntimeLibraryDependency;
import org.eclipse.n4js.n4mf.RequiredRuntimeLibraries;
import org.eclipse.n4js.n4mf.RequiredRuntimeLibraryDependency;
import org.eclipse.n4js.n4mf.SimpleProjectDescription;
import org.eclipse.n4js.n4mf.SourceFragment;
import org.eclipse.n4js.n4mf.TestedProject;
import org.eclipse.n4js.n4mf.TestedProjects;
import org.eclipse.n4js.n4mf.VersionConstraint;
import org.eclipse.n4js.n4mf.services.N4MFGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

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
			case N4mfPackage.EXEC_MODULE:
				sequence_ExecModule(context, (ExecModule) semanticObject); 
				return; 
			case N4mfPackage.EXTENDED_RUNTIME_ENVIRONMENT:
				sequence_ExtendedRuntimeEnvironment(context, (ExtendedRuntimeEnvironment) semanticObject); 
				return; 
			case N4mfPackage.IMPLEMENTED_PROJECTS:
				sequence_ImplementedProjects(context, (ImplementedProjects) semanticObject); 
				return; 
			case N4mfPackage.INIT_MODULES:
				sequence_InitModules(context, (InitModules) semanticObject); 
				return; 
			case N4mfPackage.MODULE_FILTER:
				sequence_ModuleFilter(context, (ModuleFilter) semanticObject); 
				return; 
			case N4mfPackage.MODULE_FILTER_SPECIFIER:
				sequence_ModuleFilterSpecifier(context, (ModuleFilterSpecifier) semanticObject); 
				return; 
			case N4mfPackage.PROJECT_DEPENDENCIES:
				sequence_ProjectDependencies(context, (ProjectDependencies) semanticObject); 
				return; 
			case N4mfPackage.PROJECT_DEPENDENCY:
				sequence_ProjectDependency(context, (ProjectDependency) semanticObject); 
				return; 
			case N4mfPackage.PROJECT_DESCRIPTION:
				sequence_ProjectDescription(context, (ProjectDescription) semanticObject); 
				return; 
			case N4mfPackage.PROJECT_REFERENCE:
				sequence_ProjectReference(context, (ProjectReference) semanticObject); 
				return; 
			case N4mfPackage.PROVIDED_RUNTIME_LIBRARIES:
				sequence_ProvidedRuntimeLibraries(context, (ProvidedRuntimeLibraries) semanticObject); 
				return; 
			case N4mfPackage.PROVIDED_RUNTIME_LIBRARY_DEPENDENCY:
				sequence_ProvidedRuntimeLibraryDependency(context, (ProvidedRuntimeLibraryDependency) semanticObject); 
				return; 
			case N4mfPackage.REQUIRED_RUNTIME_LIBRARIES:
				sequence_RequiredRuntimeLibraries(context, (RequiredRuntimeLibraries) semanticObject); 
				return; 
			case N4mfPackage.REQUIRED_RUNTIME_LIBRARY_DEPENDENCY:
				sequence_RequiredRuntimeLibraryDependency(context, (RequiredRuntimeLibraryDependency) semanticObject); 
				return; 
			case N4mfPackage.SIMPLE_PROJECT_DESCRIPTION:
				sequence_SimpleProjectDescription(context, (SimpleProjectDescription) semanticObject); 
				return; 
			case N4mfPackage.SOURCE_FRAGMENT:
				sequence_SourceFragment(context, (SourceFragment) semanticObject); 
				return; 
			case N4mfPackage.TESTED_PROJECT:
				sequence_TestedProject(context, (TestedProject) semanticObject); 
				return; 
			case N4mfPackage.TESTED_PROJECTS:
				sequence_TestedProjects(context, (TestedProjects) semanticObject); 
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
	 *     ExecModule returns ExecModule
	 *
	 * Constraint:
	 *     execModule=BootstrapModule
	 */
	protected void sequence_ExecModule(ISerializationContext context, ExecModule semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, N4mfPackage.Literals.EXEC_MODULE__EXEC_MODULE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, N4mfPackage.Literals.EXEC_MODULE__EXEC_MODULE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getExecModuleAccess().getExecModuleBootstrapModuleParserRuleCall_3_0(), semanticObject.getExecModule());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ExtendedRuntimeEnvironment returns ExtendedRuntimeEnvironment
	 *
	 * Constraint:
	 *     extendedRuntimeEnvironment=ProjectReference
	 */
	protected void sequence_ExtendedRuntimeEnvironment(ISerializationContext context, ExtendedRuntimeEnvironment semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, N4mfPackage.Literals.EXTENDED_RUNTIME_ENVIRONMENT__EXTENDED_RUNTIME_ENVIRONMENT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, N4mfPackage.Literals.EXTENDED_RUNTIME_ENVIRONMENT__EXTENDED_RUNTIME_ENVIRONMENT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_3_0(), semanticObject.getExtendedRuntimeEnvironment());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ImplementedProjects returns ImplementedProjects
	 *
	 * Constraint:
	 *     (implementedProjects+=ProjectReference implementedProjects+=ProjectReference*)?
	 */
	protected void sequence_ImplementedProjects(ISerializationContext context, ImplementedProjects semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     InitModules returns InitModules
	 *
	 * Constraint:
	 *     (initModules+=BootstrapModule initModules+=BootstrapModule*)?
	 */
	protected void sequence_InitModules(ISerializationContext context, InitModules semanticObject) {
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
	 *     ProjectDependencies returns ProjectDependencies
	 *
	 * Constraint:
	 *     (projectDependencies+=ProjectDependency projectDependencies+=ProjectDependency*)?
	 */
	protected void sequence_ProjectDependencies(ISerializationContext context, ProjectDependencies semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ProjectDependency returns ProjectDependency
	 *
	 * Constraint:
	 *     (project=SimpleProjectDescription versionConstraint=VersionConstraint? declaredScope=ProjectDependencyScope?)
	 */
	protected void sequence_ProjectDependency(ISerializationContext context, ProjectDependency semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ProjectDescription returns ProjectDescription
	 *
	 * Constraint:
	 *     (
	 *         projectId=N4mfIdentifier | 
	 *         projectType=ProjectType | 
	 *         projectVersion=DeclaredVersion | 
	 *         declaredVendorId=N4mfIdentifier | 
	 *         vendorName=STRING | 
	 *         mainModule=STRING | 
	 *         extendedRuntimeEnvironment=ExtendedRuntimeEnvironment | 
	 *         providedRuntimeLibraries=ProvidedRuntimeLibraries | 
	 *         requiredRuntimeLibraries=RequiredRuntimeLibraries | 
	 *         projectDependencies=ProjectDependencies | 
	 *         implementationId=N4mfIdentifier | 
	 *         implementedProjects=ImplementedProjects | 
	 *         initModules=InitModules | 
	 *         execModule=ExecModule | 
	 *         outputPath=STRING | 
	 *         (libraryPaths+=STRING libraryPaths+=STRING*) | 
	 *         (resourcePaths+=STRING resourcePaths+=STRING*) | 
	 *         sourceFragment+=SourceFragment | 
	 *         moduleFilters+=ModuleFilter | 
	 *         testedProjects=TestedProjects | 
	 *         moduleLoader=ModuleLoader
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
	 *     project=SimpleProjectDescription
	 */
	protected void sequence_ProjectReference(ISerializationContext context, ProjectReference semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, N4mfPackage.Literals.PROJECT_REFERENCE__PROJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, N4mfPackage.Literals.PROJECT_REFERENCE__PROJECT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getProjectReferenceAccess().getProjectSimpleProjectDescriptionParserRuleCall_0(), semanticObject.getProject());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ProvidedRuntimeLibraries returns ProvidedRuntimeLibraries
	 *
	 * Constraint:
	 *     (providedRuntimeLibraries+=ProvidedRuntimeLibraryDependency providedRuntimeLibraries+=ProvidedRuntimeLibraryDependency*)?
	 */
	protected void sequence_ProvidedRuntimeLibraries(ISerializationContext context, ProvidedRuntimeLibraries semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ProvidedRuntimeLibraryDependency returns ProvidedRuntimeLibraryDependency
	 *
	 * Constraint:
	 *     project=SimpleProjectDescription
	 */
	protected void sequence_ProvidedRuntimeLibraryDependency(ISerializationContext context, ProvidedRuntimeLibraryDependency semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, N4mfPackage.Literals.PROJECT_REFERENCE__PROJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, N4mfPackage.Literals.PROJECT_REFERENCE__PROJECT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0(), semanticObject.getProject());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     RequiredRuntimeLibraries returns RequiredRuntimeLibraries
	 *
	 * Constraint:
	 *     (requiredRuntimeLibraries+=RequiredRuntimeLibraryDependency requiredRuntimeLibraries+=RequiredRuntimeLibraryDependency*)?
	 */
	protected void sequence_RequiredRuntimeLibraries(ISerializationContext context, RequiredRuntimeLibraries semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     RequiredRuntimeLibraryDependency returns RequiredRuntimeLibraryDependency
	 *
	 * Constraint:
	 *     project=SimpleProjectDescription
	 */
	protected void sequence_RequiredRuntimeLibraryDependency(ISerializationContext context, RequiredRuntimeLibraryDependency semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, N4mfPackage.Literals.PROJECT_REFERENCE__PROJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, N4mfPackage.Literals.PROJECT_REFERENCE__PROJECT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0(), semanticObject.getProject());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     SimpleProjectDescription returns SimpleProjectDescription
	 *
	 * Constraint:
	 *     (declaredVendorId=N4mfIdentifier? projectId=N4mfIdentifier)
	 */
	protected void sequence_SimpleProjectDescription(ISerializationContext context, SimpleProjectDescription semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     SourceFragment returns SourceFragment
	 *
	 * Constraint:
	 *     (sourceFragmentType=SourceFragmentType paths+=STRING paths+=STRING*)
	 */
	protected void sequence_SourceFragment(ISerializationContext context, SourceFragment semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TestedProject returns TestedProject
	 *
	 * Constraint:
	 *     project=SimpleProjectDescription
	 */
	protected void sequence_TestedProject(ISerializationContext context, TestedProject semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, N4mfPackage.Literals.PROJECT_REFERENCE__PROJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, N4mfPackage.Literals.PROJECT_REFERENCE__PROJECT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getTestedProjectAccess().getProjectSimpleProjectDescriptionParserRuleCall_0(), semanticObject.getProject());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     TestedProjects returns TestedProjects
	 *
	 * Constraint:
	 *     (testedProjects+=TestedProject testedProjects+=TestedProject*)?
	 */
	protected void sequence_TestedProjects(ISerializationContext context, TestedProjects semanticObject) {
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
