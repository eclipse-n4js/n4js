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
parser grammar InternalN4MFParser;

options {
	tokenVocab=InternalN4MFLexer;
	superClass=AbstractInternalContentAssistParser;
}

@header {
package org.eclipse.n4js.n4mf.ui.contentassist.antlr.internal;
import java.util.Map;
import java.util.HashMap;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.eclipse.n4js.n4mf.services.N4MFGrammarAccess;

}
@members {
	private N4MFGrammarAccess grammarAccess;
	private final Map<String, String> tokenNameToValue = new HashMap<String, String>();
	
	{
		tokenNameToValue.put("LeftParenthesis", "'('");
		tokenNameToValue.put("RightParenthesis", "')'");
		tokenNameToValue.put("Comma", "','");
		tokenNameToValue.put("HyphenMinus", "'-'");
		tokenNameToValue.put("FullStop", "'.'");
		tokenNameToValue.put("Colon", "':'");
		tokenNameToValue.put("LeftSquareBracket", "'['");
		tokenNameToValue.put("RightSquareBracket", "']'");
		tokenNameToValue.put("LeftCurlyBracket", "'{'");
		tokenNameToValue.put("RightCurlyBracket", "'}'");
		tokenNameToValue.put("In", "'in'");
		tokenNameToValue.put("API", "'API'");
		tokenNameToValue.put("N4js", "'n4js'");
		tokenNameToValue.put("Test", "'test'");
		tokenNameToValue.put("User", "'user'");
		tokenNameToValue.put("Output", "'Output'");
		tokenNameToValue.put("Source", "'source'");
		tokenNameToValue.put("KW_System", "'system'");
		tokenNameToValue.put("Sources", "'Sources'");
		tokenNameToValue.put("Compile", "'compile'");
		tokenNameToValue.put("Content", "'content'");
		tokenNameToValue.put("Library", "'library'");
		tokenNameToValue.put("VendorId", "'VendorId'");
		tokenNameToValue.put("Commonjs", "'commonjs'");
		tokenNameToValue.put("External", "'external'");
		tokenNameToValue.put("Libraries", "'Libraries'");
		tokenNameToValue.put("ProjectId", "'ProjectId'");
		tokenNameToValue.put("Resources", "'Resources'");
		tokenNameToValue.put("Processor", "'processor'");
		tokenNameToValue.put("ExecModule", "'ExecModule'");
		tokenNameToValue.put("MainModule", "'MainModule'");
		tokenNameToValue.put("VendorName", "'VendorName'");
		tokenNameToValue.put("NoValidate", "'noValidate'");
		tokenNameToValue.put("Validation", "'validation'");
		tokenNameToValue.put("InitModules", "'InitModules'");
		tokenNameToValue.put("ProjectType", "'ProjectType'");
		tokenNameToValue.put("Application", "'application'");
		tokenNameToValue.put("ModuleLoader", "'ModuleLoader'");
		tokenNameToValue.put("NoModuleWrap", "'noModuleWrap'");
		tokenNameToValue.put("Node_builtin", "'node_builtin'");
		tokenNameToValue.put("ModuleFilters", "'ModuleFilters'");
		tokenNameToValue.put("ProjectVersion", "'ProjectVersion'");
		tokenNameToValue.put("TestedProjects", "'TestedProjects'");
		tokenNameToValue.put("RuntimeLibrary", "'runtimeLibrary'");
		tokenNameToValue.put("ImplementationId", "'ImplementationId'");
		tokenNameToValue.put("RuntimeEnvironment", "'runtimeEnvironment'");
		tokenNameToValue.put("ImplementedProjects", "'ImplementedProjects'");
		tokenNameToValue.put("ProjectDependencies", "'ProjectDependencies'");
		tokenNameToValue.put("ProvidedRuntimeLibraries", "'ProvidedRuntimeLibraries'");
		tokenNameToValue.put("RequiredRuntimeLibraries", "'RequiredRuntimeLibraries'");
		tokenNameToValue.put("ExtendedRuntimeEnvironment", "'ExtendedRuntimeEnvironment'");
	}

	public void setGrammarAccess(N4MFGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}

	@Override
	protected Grammar getGrammar() {
		return grammarAccess.getGrammar();
	}

	@Override
	protected String getValueForTokenName(String tokenName) {
		String result = tokenNameToValue.get(tokenName);
		if (result == null)
			result = tokenName;
		return result;
	}
}

// Entry rule entryRuleProjectDescription
entryRuleProjectDescription
:
{ before(grammarAccess.getProjectDescriptionRule()); }
	 ruleProjectDescription
{ after(grammarAccess.getProjectDescriptionRule()); } 
	 EOF 
;

// Rule ProjectDescription
ruleProjectDescription 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup()); }
		(rule__ProjectDescription__UnorderedGroup)
		{ after(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleExecModule
entryRuleExecModule
:
{ before(grammarAccess.getExecModuleRule()); }
	 ruleExecModule
{ after(grammarAccess.getExecModuleRule()); } 
	 EOF 
;

// Rule ExecModule
ruleExecModule 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getExecModuleAccess().getGroup()); }
		(rule__ExecModule__Group__0)
		{ after(grammarAccess.getExecModuleAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTestedProjects
entryRuleTestedProjects
:
{ before(grammarAccess.getTestedProjectsRule()); }
	 ruleTestedProjects
{ after(grammarAccess.getTestedProjectsRule()); } 
	 EOF 
;

// Rule TestedProjects
ruleTestedProjects 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTestedProjectsAccess().getGroup()); }
		(rule__TestedProjects__Group__0)
		{ after(grammarAccess.getTestedProjectsAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleInitModules
entryRuleInitModules
:
{ before(grammarAccess.getInitModulesRule()); }
	 ruleInitModules
{ after(grammarAccess.getInitModulesRule()); } 
	 EOF 
;

// Rule InitModules
ruleInitModules 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getInitModulesAccess().getGroup()); }
		(rule__InitModules__Group__0)
		{ after(grammarAccess.getInitModulesAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleImplementedProjects
entryRuleImplementedProjects
:
{ before(grammarAccess.getImplementedProjectsRule()); }
	 ruleImplementedProjects
{ after(grammarAccess.getImplementedProjectsRule()); } 
	 EOF 
;

// Rule ImplementedProjects
ruleImplementedProjects 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getImplementedProjectsAccess().getGroup()); }
		(rule__ImplementedProjects__Group__0)
		{ after(grammarAccess.getImplementedProjectsAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleProjectDependencies
entryRuleProjectDependencies
:
{ before(grammarAccess.getProjectDependenciesRule()); }
	 ruleProjectDependencies
{ after(grammarAccess.getProjectDependenciesRule()); } 
	 EOF 
;

// Rule ProjectDependencies
ruleProjectDependencies 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getProjectDependenciesAccess().getGroup()); }
		(rule__ProjectDependencies__Group__0)
		{ after(grammarAccess.getProjectDependenciesAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleProvidedRuntimeLibraries
entryRuleProvidedRuntimeLibraries
:
{ before(grammarAccess.getProvidedRuntimeLibrariesRule()); }
	 ruleProvidedRuntimeLibraries
{ after(grammarAccess.getProvidedRuntimeLibrariesRule()); } 
	 EOF 
;

// Rule ProvidedRuntimeLibraries
ruleProvidedRuntimeLibraries 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup()); }
		(rule__ProvidedRuntimeLibraries__Group__0)
		{ after(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleRequiredRuntimeLibraries
entryRuleRequiredRuntimeLibraries
:
{ before(grammarAccess.getRequiredRuntimeLibrariesRule()); }
	 ruleRequiredRuntimeLibraries
{ after(grammarAccess.getRequiredRuntimeLibrariesRule()); } 
	 EOF 
;

// Rule RequiredRuntimeLibraries
ruleRequiredRuntimeLibraries 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup()); }
		(rule__RequiredRuntimeLibraries__Group__0)
		{ after(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleExtendedRuntimeEnvironment
entryRuleExtendedRuntimeEnvironment
:
{ before(grammarAccess.getExtendedRuntimeEnvironmentRule()); }
	 ruleExtendedRuntimeEnvironment
{ after(grammarAccess.getExtendedRuntimeEnvironmentRule()); } 
	 EOF 
;

// Rule ExtendedRuntimeEnvironment
ruleExtendedRuntimeEnvironment 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getExtendedRuntimeEnvironmentAccess().getGroup()); }
		(rule__ExtendedRuntimeEnvironment__Group__0)
		{ after(grammarAccess.getExtendedRuntimeEnvironmentAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleDeclaredVersion
entryRuleDeclaredVersion
:
{ before(grammarAccess.getDeclaredVersionRule()); }
	 ruleDeclaredVersion
{ after(grammarAccess.getDeclaredVersionRule()); } 
	 EOF 
;

// Rule DeclaredVersion
ruleDeclaredVersion 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getDeclaredVersionAccess().getGroup()); }
		(rule__DeclaredVersion__Group__0)
		{ after(grammarAccess.getDeclaredVersionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSourceFragment
entryRuleSourceFragment
:
{ before(grammarAccess.getSourceFragmentRule()); }
	 ruleSourceFragment
{ after(grammarAccess.getSourceFragmentRule()); } 
	 EOF 
;

// Rule SourceFragment
ruleSourceFragment 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSourceFragmentAccess().getGroup()); }
		(rule__SourceFragment__Group__0)
		{ after(grammarAccess.getSourceFragmentAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleModuleFilter
entryRuleModuleFilter
:
{ before(grammarAccess.getModuleFilterRule()); }
	 ruleModuleFilter
{ after(grammarAccess.getModuleFilterRule()); } 
	 EOF 
;

// Rule ModuleFilter
ruleModuleFilter 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getModuleFilterAccess().getGroup()); }
		(rule__ModuleFilter__Group__0)
		{ after(grammarAccess.getModuleFilterAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleBootstrapModule
entryRuleBootstrapModule
:
{ before(grammarAccess.getBootstrapModuleRule()); }
	 ruleBootstrapModule
{ after(grammarAccess.getBootstrapModuleRule()); } 
	 EOF 
;

// Rule BootstrapModule
ruleBootstrapModule 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getBootstrapModuleAccess().getGroup()); }
		(rule__BootstrapModule__Group__0)
		{ after(grammarAccess.getBootstrapModuleAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleModuleFilterSpecifier
entryRuleModuleFilterSpecifier
:
{ before(grammarAccess.getModuleFilterSpecifierRule()); }
	 ruleModuleFilterSpecifier
{ after(grammarAccess.getModuleFilterSpecifierRule()); } 
	 EOF 
;

// Rule ModuleFilterSpecifier
ruleModuleFilterSpecifier 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getModuleFilterSpecifierAccess().getGroup()); }
		(rule__ModuleFilterSpecifier__Group__0)
		{ after(grammarAccess.getModuleFilterSpecifierAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleProvidedRuntimeLibraryDependency
entryRuleProvidedRuntimeLibraryDependency
:
{ before(grammarAccess.getProvidedRuntimeLibraryDependencyRule()); }
	 ruleProvidedRuntimeLibraryDependency
{ after(grammarAccess.getProvidedRuntimeLibraryDependencyRule()); } 
	 EOF 
;

// Rule ProvidedRuntimeLibraryDependency
ruleProvidedRuntimeLibraryDependency 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectAssignment()); }
		(rule__ProvidedRuntimeLibraryDependency__ProjectAssignment)
		{ after(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleRequiredRuntimeLibraryDependency
entryRuleRequiredRuntimeLibraryDependency
:
{ before(grammarAccess.getRequiredRuntimeLibraryDependencyRule()); }
	 ruleRequiredRuntimeLibraryDependency
{ after(grammarAccess.getRequiredRuntimeLibraryDependencyRule()); } 
	 EOF 
;

// Rule RequiredRuntimeLibraryDependency
ruleRequiredRuntimeLibraryDependency 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectAssignment()); }
		(rule__RequiredRuntimeLibraryDependency__ProjectAssignment)
		{ after(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTestedProject
entryRuleTestedProject
:
{ before(grammarAccess.getTestedProjectRule()); }
	 ruleTestedProject
{ after(grammarAccess.getTestedProjectRule()); } 
	 EOF 
;

// Rule TestedProject
ruleTestedProject 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTestedProjectAccess().getProjectAssignment()); }
		(rule__TestedProject__ProjectAssignment)
		{ after(grammarAccess.getTestedProjectAccess().getProjectAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleProjectReference
entryRuleProjectReference
:
{ before(grammarAccess.getProjectReferenceRule()); }
	 ruleProjectReference
{ after(grammarAccess.getProjectReferenceRule()); } 
	 EOF 
;

// Rule ProjectReference
ruleProjectReference 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getProjectReferenceAccess().getProjectAssignment()); }
		(rule__ProjectReference__ProjectAssignment)
		{ after(grammarAccess.getProjectReferenceAccess().getProjectAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleProjectDependency
entryRuleProjectDependency
:
{ before(grammarAccess.getProjectDependencyRule()); }
	 ruleProjectDependency
{ after(grammarAccess.getProjectDependencyRule()); } 
	 EOF 
;

// Rule ProjectDependency
ruleProjectDependency 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getProjectDependencyAccess().getGroup()); }
		(rule__ProjectDependency__Group__0)
		{ after(grammarAccess.getProjectDependencyAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSimpleProjectDescription
entryRuleSimpleProjectDescription
:
{ before(grammarAccess.getSimpleProjectDescriptionRule()); }
	 ruleSimpleProjectDescription
{ after(grammarAccess.getSimpleProjectDescriptionRule()); } 
	 EOF 
;

// Rule SimpleProjectDescription
ruleSimpleProjectDescription 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSimpleProjectDescriptionAccess().getGroup()); }
		(rule__SimpleProjectDescription__Group__0)
		{ after(grammarAccess.getSimpleProjectDescriptionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleVersionConstraint
entryRuleVersionConstraint
:
{ before(grammarAccess.getVersionConstraintRule()); }
	 ruleVersionConstraint
{ after(grammarAccess.getVersionConstraintRule()); } 
	 EOF 
;

// Rule VersionConstraint
ruleVersionConstraint 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getVersionConstraintAccess().getAlternatives()); }
		(rule__VersionConstraint__Alternatives)
		{ after(grammarAccess.getVersionConstraintAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleN4mfIdentifier
entryRuleN4mfIdentifier
:
{ before(grammarAccess.getN4mfIdentifierRule()); }
	 ruleN4mfIdentifier
{ after(grammarAccess.getN4mfIdentifierRule()); } 
	 EOF 
;

// Rule N4mfIdentifier
ruleN4mfIdentifier 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getAlternatives()); }
		(rule__N4mfIdentifier__Alternatives)
		{ after(grammarAccess.getN4mfIdentifierAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule ProjectType
ruleProjectType
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectTypeAccess().getAlternatives()); }
		(rule__ProjectType__Alternatives)
		{ after(grammarAccess.getProjectTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule SourceFragmentType
ruleSourceFragmentType
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSourceFragmentTypeAccess().getAlternatives()); }
		(rule__SourceFragmentType__Alternatives)
		{ after(grammarAccess.getSourceFragmentTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule ModuleFilterType
ruleModuleFilterType
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getModuleFilterTypeAccess().getAlternatives()); }
		(rule__ModuleFilterType__Alternatives)
		{ after(grammarAccess.getModuleFilterTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule ProjectDependencyScope
ruleProjectDependencyScope
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDependencyScopeAccess().getAlternatives()); }
		(rule__ProjectDependencyScope__Alternatives)
		{ after(grammarAccess.getProjectDependencyScopeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule ModuleLoader
ruleModuleLoader
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getModuleLoaderAccess().getAlternatives()); }
		(rule__ModuleLoader__Alternatives)
		{ after(grammarAccess.getModuleLoaderAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionConstraintAccess().getGroup_0()); }
		(rule__VersionConstraint__Group_0__0)
		{ after(grammarAccess.getVersionConstraintAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_1()); }
		(rule__VersionConstraint__LowerVersionAssignment_1)
		{ after(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Alternatives_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionConstraintAccess().getExclLowerBoundAssignment_0_0_0()); }
		(rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0)
		{ after(grammarAccess.getVersionConstraintAccess().getExclLowerBoundAssignment_0_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getVersionConstraintAccess().getLeftSquareBracketKeyword_0_0_1()); }
		LeftSquareBracket
		{ after(grammarAccess.getVersionConstraintAccess().getLeftSquareBracketKeyword_0_0_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Alternatives_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionConstraintAccess().getGroup_0_2_0()); }
		(rule__VersionConstraint__Group_0_2_0__0)?
		{ after(grammarAccess.getVersionConstraintAccess().getGroup_0_2_0()); }
	)
	|
	(
		{ before(grammarAccess.getVersionConstraintAccess().getRightParenthesisKeyword_0_2_1()); }
		RightParenthesis
		{ after(grammarAccess.getVersionConstraintAccess().getRightParenthesisKeyword_0_2_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Alternatives_0_2_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionConstraintAccess().getExclUpperBoundAssignment_0_2_0_2_0()); }
		(rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0)
		{ after(grammarAccess.getVersionConstraintAccess().getExclUpperBoundAssignment_0_2_0_2_0()); }
	)
	|
	(
		{ before(grammarAccess.getVersionConstraintAccess().getRightSquareBracketKeyword_0_2_0_2_1()); }
		RightSquareBracket
		{ after(grammarAccess.getVersionConstraintAccess().getRightSquareBracketKeyword_0_2_0_2_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__N4mfIdentifier__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getIDTerminalRuleCall_0()); }
		RULE_ID
		{ after(grammarAccess.getN4mfIdentifierAccess().getIDTerminalRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getProjectIdKeyword_1()); }
		ProjectId
		{ after(grammarAccess.getN4mfIdentifierAccess().getProjectIdKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getProjectTypeKeyword_2()); }
		ProjectType
		{ after(grammarAccess.getN4mfIdentifierAccess().getProjectTypeKeyword_2()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getProjectVersionKeyword_3()); }
		ProjectVersion
		{ after(grammarAccess.getN4mfIdentifierAccess().getProjectVersionKeyword_3()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getVendorIdKeyword_4()); }
		VendorId
		{ after(grammarAccess.getN4mfIdentifierAccess().getVendorIdKeyword_4()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getVendorNameKeyword_5()); }
		VendorName
		{ after(grammarAccess.getN4mfIdentifierAccess().getVendorNameKeyword_5()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getOutputKeyword_6()); }
		Output
		{ after(grammarAccess.getN4mfIdentifierAccess().getOutputKeyword_6()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getLibrariesKeyword_7()); }
		Libraries
		{ after(grammarAccess.getN4mfIdentifierAccess().getLibrariesKeyword_7()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getResourcesKeyword_8()); }
		Resources
		{ after(grammarAccess.getN4mfIdentifierAccess().getResourcesKeyword_8()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getSourcesKeyword_9()); }
		Sources
		{ after(grammarAccess.getN4mfIdentifierAccess().getSourcesKeyword_9()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getModuleFiltersKeyword_10()); }
		ModuleFilters
		{ after(grammarAccess.getN4mfIdentifierAccess().getModuleFiltersKeyword_10()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getGroup_11()); }
		(rule__N4mfIdentifier__Group_11__0)
		{ after(grammarAccess.getN4mfIdentifierAccess().getGroup_11()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getAPIKeyword_12()); }
		API
		{ after(grammarAccess.getN4mfIdentifierAccess().getAPIKeyword_12()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getUserKeyword_13()); }
		User
		{ after(grammarAccess.getN4mfIdentifierAccess().getUserKeyword_13()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getApplicationKeyword_14()); }
		Application
		{ after(grammarAccess.getN4mfIdentifierAccess().getApplicationKeyword_14()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getGroup_15()); }
		(rule__N4mfIdentifier__Group_15__0)
		{ after(grammarAccess.getN4mfIdentifierAccess().getGroup_15()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getContentKeyword_16()); }
		Content
		{ after(grammarAccess.getN4mfIdentifierAccess().getContentKeyword_16()); }
	)
	|
	(
		{ before(grammarAccess.getN4mfIdentifierAccess().getTestKeyword_17()); }
		Test
		{ after(grammarAccess.getN4mfIdentifierAccess().getTestKeyword_17()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectTypeAccess().getAPPLICATIONEnumLiteralDeclaration_0()); }
		(Application)
		{ after(grammarAccess.getProjectTypeAccess().getAPPLICATIONEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getProjectTypeAccess().getPROCESSOREnumLiteralDeclaration_1()); }
		(Processor)
		{ after(grammarAccess.getProjectTypeAccess().getPROCESSOREnumLiteralDeclaration_1()); }
	)
	|
	(
		{ before(grammarAccess.getProjectTypeAccess().getLIBRARYEnumLiteralDeclaration_2()); }
		(Library)
		{ after(grammarAccess.getProjectTypeAccess().getLIBRARYEnumLiteralDeclaration_2()); }
	)
	|
	(
		{ before(grammarAccess.getProjectTypeAccess().getAPIEnumLiteralDeclaration_3()); }
		(API)
		{ after(grammarAccess.getProjectTypeAccess().getAPIEnumLiteralDeclaration_3()); }
	)
	|
	(
		{ before(grammarAccess.getProjectTypeAccess().getRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4()); }
		(RuntimeEnvironment)
		{ after(grammarAccess.getProjectTypeAccess().getRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4()); }
	)
	|
	(
		{ before(grammarAccess.getProjectTypeAccess().getRUNTIME_LIBRARYEnumLiteralDeclaration_5()); }
		(RuntimeLibrary)
		{ after(grammarAccess.getProjectTypeAccess().getRUNTIME_LIBRARYEnumLiteralDeclaration_5()); }
	)
	|
	(
		{ before(grammarAccess.getProjectTypeAccess().getTESTEnumLiteralDeclaration_6()); }
		(Test)
		{ after(grammarAccess.getProjectTypeAccess().getTESTEnumLiteralDeclaration_6()); }
	)
	|
	(
		{ before(grammarAccess.getProjectTypeAccess().getVALIDATIONEnumLiteralDeclaration_7()); }
		(Validation)
		{ after(grammarAccess.getProjectTypeAccess().getVALIDATIONEnumLiteralDeclaration_7()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragmentType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSourceFragmentTypeAccess().getSOURCEEnumLiteralDeclaration_0()); }
		(Source)
		{ after(grammarAccess.getSourceFragmentTypeAccess().getSOURCEEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getSourceFragmentTypeAccess().getEXTERNALEnumLiteralDeclaration_1()); }
		(External)
		{ after(grammarAccess.getSourceFragmentTypeAccess().getEXTERNALEnumLiteralDeclaration_1()); }
	)
	|
	(
		{ before(grammarAccess.getSourceFragmentTypeAccess().getTESTEnumLiteralDeclaration_2()); }
		(Test)
		{ after(grammarAccess.getSourceFragmentTypeAccess().getTESTEnumLiteralDeclaration_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilterType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getModuleFilterTypeAccess().getNO_VALIDATEEnumLiteralDeclaration_0()); }
		(NoValidate)
		{ after(grammarAccess.getModuleFilterTypeAccess().getNO_VALIDATEEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getModuleFilterTypeAccess().getNO_MODULE_WRAPPINGEnumLiteralDeclaration_1()); }
		(NoModuleWrap)
		{ after(grammarAccess.getModuleFilterTypeAccess().getNO_MODULE_WRAPPINGEnumLiteralDeclaration_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencyScope__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDependencyScopeAccess().getCOMPILEEnumLiteralDeclaration_0()); }
		(Compile)
		{ after(grammarAccess.getProjectDependencyScopeAccess().getCOMPILEEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getProjectDependencyScopeAccess().getTESTEnumLiteralDeclaration_1()); }
		(Test)
		{ after(grammarAccess.getProjectDependencyScopeAccess().getTESTEnumLiteralDeclaration_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleLoader__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getModuleLoaderAccess().getN4JSEnumLiteralDeclaration_0()); }
		(N4js)
		{ after(grammarAccess.getModuleLoaderAccess().getN4JSEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getModuleLoaderAccess().getCOMMONJSEnumLiteralDeclaration_1()); }
		(Commonjs)
		{ after(grammarAccess.getModuleLoaderAccess().getCOMMONJSEnumLiteralDeclaration_1()); }
	)
	|
	(
		{ before(grammarAccess.getModuleLoaderAccess().getNODE_BUILTINEnumLiteralDeclaration_2()); }
		(Node_builtin)
		{ after(grammarAccess.getModuleLoaderAccess().getNODE_BUILTINEnumLiteralDeclaration_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_0__0__Impl
	rule__ProjectDescription__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getProjectIdKeyword_0_0()); }
	ProjectId
	{ after(grammarAccess.getProjectDescriptionAccess().getProjectIdKeyword_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_0__1__Impl
	rule__ProjectDescription__Group_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_0_1()); }
	Colon
	{ after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getProjectIdAssignment_0_2()); }
	(rule__ProjectDescription__ProjectIdAssignment_0_2)
	{ after(grammarAccess.getProjectDescriptionAccess().getProjectIdAssignment_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_1__0__Impl
	rule__ProjectDescription__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getProjectTypeKeyword_1_0()); }
	ProjectType
	{ after(grammarAccess.getProjectDescriptionAccess().getProjectTypeKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_1__1__Impl
	rule__ProjectDescription__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_1_1()); }
	Colon
	{ after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getProjectTypeAssignment_1_2()); }
	(rule__ProjectDescription__ProjectTypeAssignment_1_2)
	{ after(grammarAccess.getProjectDescriptionAccess().getProjectTypeAssignment_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_2__0__Impl
	rule__ProjectDescription__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getProjectVersionKeyword_2_0()); }
	ProjectVersion
	{ after(grammarAccess.getProjectDescriptionAccess().getProjectVersionKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_2__1__Impl
	rule__ProjectDescription__Group_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_2_1()); }
	Colon
	{ after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_2__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getProjectVersionAssignment_2_2()); }
	(rule__ProjectDescription__ProjectVersionAssignment_2_2)
	{ after(grammarAccess.getProjectDescriptionAccess().getProjectVersionAssignment_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_3__0__Impl
	rule__ProjectDescription__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getVendorIdKeyword_3_0()); }
	VendorId
	{ after(grammarAccess.getProjectDescriptionAccess().getVendorIdKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_3__1__Impl
	rule__ProjectDescription__Group_3__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_3_1()); }
	Colon
	{ after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_3__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_3__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_3__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getDeclaredVendorIdAssignment_3_2()); }
	(rule__ProjectDescription__DeclaredVendorIdAssignment_3_2)
	{ after(grammarAccess.getProjectDescriptionAccess().getDeclaredVendorIdAssignment_3_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_4__0__Impl
	rule__ProjectDescription__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getVendorNameKeyword_4_0()); }
	VendorName
	{ after(grammarAccess.getProjectDescriptionAccess().getVendorNameKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_4__1__Impl
	rule__ProjectDescription__Group_4__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_4_1()); }
	Colon
	{ after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_4__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_4__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_4__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getVendorNameAssignment_4_2()); }
	(rule__ProjectDescription__VendorNameAssignment_4_2)
	{ after(grammarAccess.getProjectDescriptionAccess().getVendorNameAssignment_4_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_5__0__Impl
	rule__ProjectDescription__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getMainModuleKeyword_5_0()); }
	MainModule
	{ after(grammarAccess.getProjectDescriptionAccess().getMainModuleKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_5__1__Impl
	rule__ProjectDescription__Group_5__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_5_1()); }
	Colon
	{ after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_5__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_5__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_5__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getMainModuleAssignment_5_2()); }
	(rule__ProjectDescription__MainModuleAssignment_5_2)
	{ after(grammarAccess.getProjectDescriptionAccess().getMainModuleAssignment_5_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_10__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_10__0__Impl
	rule__ProjectDescription__Group_10__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_10__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getImplementationIdKeyword_10_0()); }
	ImplementationId
	{ after(grammarAccess.getProjectDescriptionAccess().getImplementationIdKeyword_10_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_10__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_10__1__Impl
	rule__ProjectDescription__Group_10__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_10__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_10_1()); }
	Colon
	{ after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_10_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_10__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_10__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_10__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getImplementationIdAssignment_10_2()); }
	(rule__ProjectDescription__ImplementationIdAssignment_10_2)
	{ after(grammarAccess.getProjectDescriptionAccess().getImplementationIdAssignment_10_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_14__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_14__0__Impl
	rule__ProjectDescription__Group_14__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_14__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getOutputKeyword_14_0()); }
	Output
	{ after(grammarAccess.getProjectDescriptionAccess().getOutputKeyword_14_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_14__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_14__1__Impl
	rule__ProjectDescription__Group_14__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_14__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_14_1()); }
	Colon
	{ after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_14_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_14__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_14__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_14__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getOutputPathRawAssignment_14_2()); }
	(rule__ProjectDescription__OutputPathRawAssignment_14_2)
	{ after(grammarAccess.getProjectDescriptionAccess().getOutputPathRawAssignment_14_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_15__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_15__0__Impl
	rule__ProjectDescription__Group_15__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_15__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getLibrariesKeyword_15_0()); }
	Libraries
	{ after(grammarAccess.getProjectDescriptionAccess().getLibrariesKeyword_15_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_15__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_15__1__Impl
	rule__ProjectDescription__Group_15__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_15__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_15_1()); }
	LeftCurlyBracket
	{ after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_15_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_15__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_15__2__Impl
	rule__ProjectDescription__Group_15__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_15__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawAssignment_15_2()); }
	(rule__ProjectDescription__LibraryPathsRawAssignment_15_2)
	{ after(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawAssignment_15_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_15__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_15__3__Impl
	rule__ProjectDescription__Group_15__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_15__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getGroup_15_3()); }
	(rule__ProjectDescription__Group_15_3__0)*
	{ after(grammarAccess.getProjectDescriptionAccess().getGroup_15_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_15__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_15__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_15__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_15_4()); }
	RightCurlyBracket
	{ after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_15_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_15_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_15_3__0__Impl
	rule__ProjectDescription__Group_15_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_15_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_15_3_0()); }
	Comma
	{ after(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_15_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_15_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_15_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_15_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawAssignment_15_3_1()); }
	(rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1)
	{ after(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawAssignment_15_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_16__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_16__0__Impl
	rule__ProjectDescription__Group_16__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_16__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getResourcesKeyword_16_0()); }
	Resources
	{ after(grammarAccess.getProjectDescriptionAccess().getResourcesKeyword_16_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_16__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_16__1__Impl
	rule__ProjectDescription__Group_16__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_16__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_16_1()); }
	LeftCurlyBracket
	{ after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_16_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_16__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_16__2__Impl
	rule__ProjectDescription__Group_16__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_16__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawAssignment_16_2()); }
	(rule__ProjectDescription__ResourcePathsRawAssignment_16_2)
	{ after(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawAssignment_16_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_16__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_16__3__Impl
	rule__ProjectDescription__Group_16__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_16__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getGroup_16_3()); }
	(rule__ProjectDescription__Group_16_3__0)*
	{ after(grammarAccess.getProjectDescriptionAccess().getGroup_16_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_16__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_16__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_16__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_16_4()); }
	RightCurlyBracket
	{ after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_16_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_16_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_16_3__0__Impl
	rule__ProjectDescription__Group_16_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_16_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_16_3_0()); }
	Comma
	{ after(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_16_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_16_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_16_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_16_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawAssignment_16_3_1()); }
	(rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1)
	{ after(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawAssignment_16_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_17__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_17__0__Impl
	rule__ProjectDescription__Group_17__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_17__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getSourcesKeyword_17_0()); }
	Sources
	{ after(grammarAccess.getProjectDescriptionAccess().getSourcesKeyword_17_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_17__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_17__1__Impl
	rule__ProjectDescription__Group_17__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_17__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_17_1()); }
	LeftCurlyBracket
	{ after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_17_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_17__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_17__2__Impl
	rule__ProjectDescription__Group_17__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_17__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); }
		(rule__ProjectDescription__SourceFragmentAssignment_17_2)
		{ after(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); }
	)
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); }
		(rule__ProjectDescription__SourceFragmentAssignment_17_2)*
		{ after(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_17__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_17__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_17__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_17_3()); }
	RightCurlyBracket
	{ after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_17_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_18__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_18__0__Impl
	rule__ProjectDescription__Group_18__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_18__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getModuleFiltersKeyword_18_0()); }
	ModuleFilters
	{ after(grammarAccess.getProjectDescriptionAccess().getModuleFiltersKeyword_18_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_18__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_18__1__Impl
	rule__ProjectDescription__Group_18__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_18__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_18_1()); }
	LeftCurlyBracket
	{ after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_18_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_18__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_18__2__Impl
	rule__ProjectDescription__Group_18__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_18__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); }
		(rule__ProjectDescription__ModuleFiltersAssignment_18_2)
		{ after(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); }
	)
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); }
		(rule__ProjectDescription__ModuleFiltersAssignment_18_2)*
		{ after(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_18__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_18__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_18__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_18_3()); }
	RightCurlyBracket
	{ after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_18_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__Group_20__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_20__0__Impl
	rule__ProjectDescription__Group_20__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_20__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getModuleLoaderKeyword_20_0()); }
	ModuleLoader
	{ after(grammarAccess.getProjectDescriptionAccess().getModuleLoaderKeyword_20_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_20__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_20__1__Impl
	rule__ProjectDescription__Group_20__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_20__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_20_1()); }
	Colon
	{ after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_20_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_20__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__Group_20__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__Group_20__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDescriptionAccess().getModuleLoaderAssignment_20_2()); }
	(rule__ProjectDescription__ModuleLoaderAssignment_20_2)
	{ after(grammarAccess.getProjectDescriptionAccess().getModuleLoaderAssignment_20_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ExecModule__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExecModule__Group__0__Impl
	rule__ExecModule__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ExecModule__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExecModuleAccess().getExecModuleAction_0()); }
	()
	{ after(grammarAccess.getExecModuleAccess().getExecModuleAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExecModule__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExecModule__Group__1__Impl
	rule__ExecModule__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ExecModule__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExecModuleAccess().getExecModuleKeyword_1()); }
	ExecModule
	{ after(grammarAccess.getExecModuleAccess().getExecModuleKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExecModule__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExecModule__Group__2__Impl
	rule__ExecModule__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ExecModule__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExecModuleAccess().getColonKeyword_2()); }
	Colon
	{ after(grammarAccess.getExecModuleAccess().getColonKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExecModule__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExecModule__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ExecModule__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExecModuleAccess().getExecModuleAssignment_3()); }
	(rule__ExecModule__ExecModuleAssignment_3)
	{ after(grammarAccess.getExecModuleAccess().getExecModuleAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TestedProjects__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TestedProjects__Group__0__Impl
	rule__TestedProjects__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTestedProjectsAccess().getTestedProjectsAction_0()); }
	()
	{ after(grammarAccess.getTestedProjectsAccess().getTestedProjectsAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TestedProjects__Group__1__Impl
	rule__TestedProjects__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTestedProjectsAccess().getTestedProjectsKeyword_1()); }
	TestedProjects
	{ after(grammarAccess.getTestedProjectsAccess().getTestedProjectsKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TestedProjects__Group__2__Impl
	rule__TestedProjects__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTestedProjectsAccess().getLeftCurlyBracketKeyword_2()); }
	LeftCurlyBracket
	{ after(grammarAccess.getTestedProjectsAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TestedProjects__Group__3__Impl
	rule__TestedProjects__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTestedProjectsAccess().getGroup_3()); }
	(rule__TestedProjects__Group_3__0)?
	{ after(grammarAccess.getTestedProjectsAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TestedProjects__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTestedProjectsAccess().getRightCurlyBracketKeyword_4()); }
	RightCurlyBracket
	{ after(grammarAccess.getTestedProjectsAccess().getRightCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TestedProjects__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TestedProjects__Group_3__0__Impl
	rule__TestedProjects__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTestedProjectsAccess().getTestedProjectsAssignment_3_0()); }
	(rule__TestedProjects__TestedProjectsAssignment_3_0)
	{ after(grammarAccess.getTestedProjectsAccess().getTestedProjectsAssignment_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TestedProjects__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTestedProjectsAccess().getGroup_3_1()); }
	(rule__TestedProjects__Group_3_1__0)*
	{ after(grammarAccess.getTestedProjectsAccess().getGroup_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TestedProjects__Group_3_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TestedProjects__Group_3_1__0__Impl
	rule__TestedProjects__Group_3_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group_3_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTestedProjectsAccess().getCommaKeyword_3_1_0()); }
	Comma
	{ after(grammarAccess.getTestedProjectsAccess().getCommaKeyword_3_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group_3_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TestedProjects__Group_3_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__Group_3_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTestedProjectsAccess().getTestedProjectsAssignment_3_1_1()); }
	(rule__TestedProjects__TestedProjectsAssignment_3_1_1)
	{ after(grammarAccess.getTestedProjectsAccess().getTestedProjectsAssignment_3_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__InitModules__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InitModules__Group__0__Impl
	rule__InitModules__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInitModulesAccess().getInitModulesAction_0()); }
	()
	{ after(grammarAccess.getInitModulesAccess().getInitModulesAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InitModules__Group__1__Impl
	rule__InitModules__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInitModulesAccess().getInitModulesKeyword_1()); }
	InitModules
	{ after(grammarAccess.getInitModulesAccess().getInitModulesKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InitModules__Group__2__Impl
	rule__InitModules__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInitModulesAccess().getLeftCurlyBracketKeyword_2()); }
	LeftCurlyBracket
	{ after(grammarAccess.getInitModulesAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InitModules__Group__3__Impl
	rule__InitModules__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInitModulesAccess().getGroup_3()); }
	(rule__InitModules__Group_3__0)?
	{ after(grammarAccess.getInitModulesAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InitModules__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInitModulesAccess().getRightCurlyBracketKeyword_4()); }
	RightCurlyBracket
	{ after(grammarAccess.getInitModulesAccess().getRightCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__InitModules__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InitModules__Group_3__0__Impl
	rule__InitModules__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInitModulesAccess().getInitModulesAssignment_3_0()); }
	(rule__InitModules__InitModulesAssignment_3_0)
	{ after(grammarAccess.getInitModulesAccess().getInitModulesAssignment_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InitModules__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInitModulesAccess().getGroup_3_1()); }
	(rule__InitModules__Group_3_1__0)*
	{ after(grammarAccess.getInitModulesAccess().getGroup_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__InitModules__Group_3_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InitModules__Group_3_1__0__Impl
	rule__InitModules__Group_3_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group_3_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInitModulesAccess().getCommaKeyword_3_1_0()); }
	Comma
	{ after(grammarAccess.getInitModulesAccess().getCommaKeyword_3_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group_3_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InitModules__Group_3_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__Group_3_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInitModulesAccess().getInitModulesAssignment_3_1_1()); }
	(rule__InitModules__InitModulesAssignment_3_1_1)
	{ after(grammarAccess.getInitModulesAccess().getInitModulesAssignment_3_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ImplementedProjects__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ImplementedProjects__Group__0__Impl
	rule__ImplementedProjects__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAction_0()); }
	()
	{ after(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ImplementedProjects__Group__1__Impl
	rule__ImplementedProjects__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsKeyword_1()); }
	ImplementedProjects
	{ after(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ImplementedProjects__Group__2__Impl
	rule__ImplementedProjects__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getImplementedProjectsAccess().getLeftCurlyBracketKeyword_2()); }
	LeftCurlyBracket
	{ after(grammarAccess.getImplementedProjectsAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ImplementedProjects__Group__3__Impl
	rule__ImplementedProjects__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getImplementedProjectsAccess().getGroup_3()); }
	(rule__ImplementedProjects__Group_3__0)?
	{ after(grammarAccess.getImplementedProjectsAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ImplementedProjects__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getImplementedProjectsAccess().getRightCurlyBracketKeyword_4()); }
	RightCurlyBracket
	{ after(grammarAccess.getImplementedProjectsAccess().getRightCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ImplementedProjects__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ImplementedProjects__Group_3__0__Impl
	rule__ImplementedProjects__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAssignment_3_0()); }
	(rule__ImplementedProjects__ImplementedProjectsAssignment_3_0)
	{ after(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAssignment_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ImplementedProjects__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getImplementedProjectsAccess().getGroup_3_1()); }
	(rule__ImplementedProjects__Group_3_1__0)*
	{ after(grammarAccess.getImplementedProjectsAccess().getGroup_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ImplementedProjects__Group_3_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ImplementedProjects__Group_3_1__0__Impl
	rule__ImplementedProjects__Group_3_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group_3_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getImplementedProjectsAccess().getCommaKeyword_3_1_0()); }
	Comma
	{ after(grammarAccess.getImplementedProjectsAccess().getCommaKeyword_3_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group_3_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ImplementedProjects__Group_3_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__Group_3_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAssignment_3_1_1()); }
	(rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1)
	{ after(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAssignment_3_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDependencies__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDependencies__Group__0__Impl
	rule__ProjectDependencies__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAction_0()); }
	()
	{ after(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDependencies__Group__1__Impl
	rule__ProjectDependencies__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesKeyword_1()); }
	ProjectDependencies
	{ after(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDependencies__Group__2__Impl
	rule__ProjectDependencies__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDependenciesAccess().getLeftCurlyBracketKeyword_2()); }
	LeftCurlyBracket
	{ after(grammarAccess.getProjectDependenciesAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDependencies__Group__3__Impl
	rule__ProjectDependencies__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDependenciesAccess().getGroup_3()); }
	(rule__ProjectDependencies__Group_3__0)?
	{ after(grammarAccess.getProjectDependenciesAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDependencies__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDependenciesAccess().getRightCurlyBracketKeyword_4()); }
	RightCurlyBracket
	{ after(grammarAccess.getProjectDependenciesAccess().getRightCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDependencies__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDependencies__Group_3__0__Impl
	rule__ProjectDependencies__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAssignment_3_0()); }
	(rule__ProjectDependencies__ProjectDependenciesAssignment_3_0)
	{ after(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAssignment_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDependencies__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDependenciesAccess().getGroup_3_1()); }
	(rule__ProjectDependencies__Group_3_1__0)*
	{ after(grammarAccess.getProjectDependenciesAccess().getGroup_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDependencies__Group_3_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDependencies__Group_3_1__0__Impl
	rule__ProjectDependencies__Group_3_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group_3_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDependenciesAccess().getCommaKeyword_3_1_0()); }
	Comma
	{ after(grammarAccess.getProjectDependenciesAccess().getCommaKeyword_3_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group_3_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDependencies__Group_3_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__Group_3_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAssignment_3_1_1()); }
	(rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1)
	{ after(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAssignment_3_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProvidedRuntimeLibraries__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedRuntimeLibraries__Group__0__Impl
	rule__ProvidedRuntimeLibraries__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAction_0()); }
	()
	{ after(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedRuntimeLibraries__Group__1__Impl
	rule__ProvidedRuntimeLibraries__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesKeyword_1()); }
	ProvidedRuntimeLibraries
	{ after(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedRuntimeLibraries__Group__2__Impl
	rule__ProvidedRuntimeLibraries__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedRuntimeLibrariesAccess().getLeftCurlyBracketKeyword_2()); }
	LeftCurlyBracket
	{ after(grammarAccess.getProvidedRuntimeLibrariesAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedRuntimeLibraries__Group__3__Impl
	rule__ProvidedRuntimeLibraries__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup_3()); }
	(rule__ProvidedRuntimeLibraries__Group_3__0)?
	{ after(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedRuntimeLibraries__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedRuntimeLibrariesAccess().getRightCurlyBracketKeyword_4()); }
	RightCurlyBracket
	{ after(grammarAccess.getProvidedRuntimeLibrariesAccess().getRightCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProvidedRuntimeLibraries__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedRuntimeLibraries__Group_3__0__Impl
	rule__ProvidedRuntimeLibraries__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAssignment_3_0()); }
	(rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0)
	{ after(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAssignment_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedRuntimeLibraries__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup_3_1()); }
	(rule__ProvidedRuntimeLibraries__Group_3_1__0)*
	{ after(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProvidedRuntimeLibraries__Group_3_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedRuntimeLibraries__Group_3_1__0__Impl
	rule__ProvidedRuntimeLibraries__Group_3_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group_3_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedRuntimeLibrariesAccess().getCommaKeyword_3_1_0()); }
	Comma
	{ after(grammarAccess.getProvidedRuntimeLibrariesAccess().getCommaKeyword_3_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group_3_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedRuntimeLibraries__Group_3_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__Group_3_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAssignment_3_1_1()); }
	(rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1)
	{ after(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAssignment_3_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RequiredRuntimeLibraries__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredRuntimeLibraries__Group__0__Impl
	rule__RequiredRuntimeLibraries__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAction_0()); }
	()
	{ after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredRuntimeLibraries__Group__1__Impl
	rule__RequiredRuntimeLibraries__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesKeyword_1()); }
	RequiredRuntimeLibraries
	{ after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredRuntimeLibraries__Group__2__Impl
	rule__RequiredRuntimeLibraries__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredRuntimeLibrariesAccess().getLeftCurlyBracketKeyword_2()); }
	LeftCurlyBracket
	{ after(grammarAccess.getRequiredRuntimeLibrariesAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredRuntimeLibraries__Group__3__Impl
	rule__RequiredRuntimeLibraries__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup_3()); }
	(rule__RequiredRuntimeLibraries__Group_3__0)?
	{ after(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredRuntimeLibraries__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRightCurlyBracketKeyword_4()); }
	RightCurlyBracket
	{ after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRightCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RequiredRuntimeLibraries__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredRuntimeLibraries__Group_3__0__Impl
	rule__RequiredRuntimeLibraries__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAssignment_3_0()); }
	(rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0)
	{ after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAssignment_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredRuntimeLibraries__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup_3_1()); }
	(rule__RequiredRuntimeLibraries__Group_3_1__0)*
	{ after(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RequiredRuntimeLibraries__Group_3_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredRuntimeLibraries__Group_3_1__0__Impl
	rule__RequiredRuntimeLibraries__Group_3_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group_3_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredRuntimeLibrariesAccess().getCommaKeyword_3_1_0()); }
	Comma
	{ after(grammarAccess.getRequiredRuntimeLibrariesAccess().getCommaKeyword_3_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group_3_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredRuntimeLibraries__Group_3_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__Group_3_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAssignment_3_1_1()); }
	(rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1)
	{ after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAssignment_3_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ExtendedRuntimeEnvironment__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExtendedRuntimeEnvironment__Group__0__Impl
	rule__ExtendedRuntimeEnvironment__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ExtendedRuntimeEnvironment__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentAction_0()); }
	()
	{ after(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExtendedRuntimeEnvironment__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExtendedRuntimeEnvironment__Group__1__Impl
	rule__ExtendedRuntimeEnvironment__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ExtendedRuntimeEnvironment__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentKeyword_1()); }
	ExtendedRuntimeEnvironment
	{ after(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExtendedRuntimeEnvironment__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExtendedRuntimeEnvironment__Group__2__Impl
	rule__ExtendedRuntimeEnvironment__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ExtendedRuntimeEnvironment__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExtendedRuntimeEnvironmentAccess().getColonKeyword_2()); }
	Colon
	{ after(grammarAccess.getExtendedRuntimeEnvironmentAccess().getColonKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExtendedRuntimeEnvironment__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExtendedRuntimeEnvironment__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ExtendedRuntimeEnvironment__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentAssignment_3()); }
	(rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3)
	{ after(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__DeclaredVersion__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DeclaredVersion__Group__0__Impl
	rule__DeclaredVersion__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDeclaredVersionAccess().getMajorAssignment_0()); }
	(rule__DeclaredVersion__MajorAssignment_0)
	{ after(grammarAccess.getDeclaredVersionAccess().getMajorAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DeclaredVersion__Group__1__Impl
	rule__DeclaredVersion__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDeclaredVersionAccess().getGroup_1()); }
	(rule__DeclaredVersion__Group_1__0)?
	{ after(grammarAccess.getDeclaredVersionAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DeclaredVersion__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDeclaredVersionAccess().getGroup_2()); }
	(rule__DeclaredVersion__Group_2__0)?
	{ after(grammarAccess.getDeclaredVersionAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__DeclaredVersion__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DeclaredVersion__Group_1__0__Impl
	rule__DeclaredVersion__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDeclaredVersionAccess().getFullStopKeyword_1_0()); }
	FullStop
	{ after(grammarAccess.getDeclaredVersionAccess().getFullStopKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DeclaredVersion__Group_1__1__Impl
	rule__DeclaredVersion__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDeclaredVersionAccess().getMinorAssignment_1_1()); }
	(rule__DeclaredVersion__MinorAssignment_1_1)
	{ after(grammarAccess.getDeclaredVersionAccess().getMinorAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DeclaredVersion__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDeclaredVersionAccess().getGroup_1_2()); }
	(rule__DeclaredVersion__Group_1_2__0)?
	{ after(grammarAccess.getDeclaredVersionAccess().getGroup_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__DeclaredVersion__Group_1_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DeclaredVersion__Group_1_2__0__Impl
	rule__DeclaredVersion__Group_1_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group_1_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDeclaredVersionAccess().getFullStopKeyword_1_2_0()); }
	FullStop
	{ after(grammarAccess.getDeclaredVersionAccess().getFullStopKeyword_1_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group_1_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DeclaredVersion__Group_1_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group_1_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDeclaredVersionAccess().getMicroAssignment_1_2_1()); }
	(rule__DeclaredVersion__MicroAssignment_1_2_1)
	{ after(grammarAccess.getDeclaredVersionAccess().getMicroAssignment_1_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__DeclaredVersion__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DeclaredVersion__Group_2__0__Impl
	rule__DeclaredVersion__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDeclaredVersionAccess().getHyphenMinusKeyword_2_0()); }
	HyphenMinus
	{ after(grammarAccess.getDeclaredVersionAccess().getHyphenMinusKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DeclaredVersion__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDeclaredVersionAccess().getQualifierAssignment_2_1()); }
	(rule__DeclaredVersion__QualifierAssignment_2_1)
	{ after(grammarAccess.getDeclaredVersionAccess().getQualifierAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SourceFragment__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SourceFragment__Group__0__Impl
	rule__SourceFragment__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeAssignment_0()); }
	(rule__SourceFragment__SourceFragmentTypeAssignment_0)
	{ after(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SourceFragment__Group__1__Impl
	rule__SourceFragment__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSourceFragmentAccess().getLeftCurlyBracketKeyword_1()); }
	LeftCurlyBracket
	{ after(grammarAccess.getSourceFragmentAccess().getLeftCurlyBracketKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SourceFragment__Group__2__Impl
	rule__SourceFragment__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSourceFragmentAccess().getPathsRawAssignment_2()); }
	(rule__SourceFragment__PathsRawAssignment_2)
	{ after(grammarAccess.getSourceFragmentAccess().getPathsRawAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SourceFragment__Group__3__Impl
	rule__SourceFragment__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSourceFragmentAccess().getGroup_3()); }
	(rule__SourceFragment__Group_3__0)*
	{ after(grammarAccess.getSourceFragmentAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SourceFragment__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSourceFragmentAccess().getRightCurlyBracketKeyword_4()); }
	RightCurlyBracket
	{ after(grammarAccess.getSourceFragmentAccess().getRightCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SourceFragment__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SourceFragment__Group_3__0__Impl
	rule__SourceFragment__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSourceFragmentAccess().getCommaKeyword_3_0()); }
	Comma
	{ after(grammarAccess.getSourceFragmentAccess().getCommaKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SourceFragment__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSourceFragmentAccess().getPathsRawAssignment_3_1()); }
	(rule__SourceFragment__PathsRawAssignment_3_1)
	{ after(grammarAccess.getSourceFragmentAccess().getPathsRawAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ModuleFilter__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ModuleFilter__Group__0__Impl
	rule__ModuleFilter__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuleFilterAccess().getModuleFilterTypeAssignment_0()); }
	(rule__ModuleFilter__ModuleFilterTypeAssignment_0)
	{ after(grammarAccess.getModuleFilterAccess().getModuleFilterTypeAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ModuleFilter__Group__1__Impl
	rule__ModuleFilter__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuleFilterAccess().getLeftCurlyBracketKeyword_1()); }
	LeftCurlyBracket
	{ after(grammarAccess.getModuleFilterAccess().getLeftCurlyBracketKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ModuleFilter__Group__2__Impl
	rule__ModuleFilter__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_2()); }
	(rule__ModuleFilter__ModuleSpecifiersAssignment_2)
	{ after(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ModuleFilter__Group__3__Impl
	rule__ModuleFilter__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuleFilterAccess().getGroup_3()); }
	(rule__ModuleFilter__Group_3__0)*
	{ after(grammarAccess.getModuleFilterAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ModuleFilter__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuleFilterAccess().getRightCurlyBracketKeyword_4()); }
	RightCurlyBracket
	{ after(grammarAccess.getModuleFilterAccess().getRightCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ModuleFilter__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ModuleFilter__Group_3__0__Impl
	rule__ModuleFilter__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuleFilterAccess().getCommaKeyword_3_0()); }
	Comma
	{ after(grammarAccess.getModuleFilterAccess().getCommaKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ModuleFilter__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_3_1()); }
	(rule__ModuleFilter__ModuleSpecifiersAssignment_3_1)
	{ after(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__BootstrapModule__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__BootstrapModule__Group__0__Impl
	rule__BootstrapModule__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__BootstrapModule__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardAssignment_0()); }
	(rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0)
	{ after(grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__BootstrapModule__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__BootstrapModule__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__BootstrapModule__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getBootstrapModuleAccess().getGroup_1()); }
	(rule__BootstrapModule__Group_1__0)?
	{ after(grammarAccess.getBootstrapModuleAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__BootstrapModule__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__BootstrapModule__Group_1__0__Impl
	rule__BootstrapModule__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__BootstrapModule__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getBootstrapModuleAccess().getInKeyword_1_0()); }
	In
	{ after(grammarAccess.getBootstrapModuleAccess().getInKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__BootstrapModule__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__BootstrapModule__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__BootstrapModule__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getBootstrapModuleAccess().getSourcePathAssignment_1_1()); }
	(rule__BootstrapModule__SourcePathAssignment_1_1)
	{ after(grammarAccess.getBootstrapModuleAccess().getSourcePathAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ModuleFilterSpecifier__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ModuleFilterSpecifier__Group__0__Impl
	rule__ModuleFilterSpecifier__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilterSpecifier__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardAssignment_0()); }
	(rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0)
	{ after(grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilterSpecifier__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ModuleFilterSpecifier__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilterSpecifier__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuleFilterSpecifierAccess().getGroup_1()); }
	(rule__ModuleFilterSpecifier__Group_1__0)?
	{ after(grammarAccess.getModuleFilterSpecifierAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ModuleFilterSpecifier__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ModuleFilterSpecifier__Group_1__0__Impl
	rule__ModuleFilterSpecifier__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilterSpecifier__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuleFilterSpecifierAccess().getInKeyword_1_0()); }
	In
	{ after(grammarAccess.getModuleFilterSpecifierAccess().getInKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilterSpecifier__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ModuleFilterSpecifier__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilterSpecifier__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuleFilterSpecifierAccess().getSourcePathAssignment_1_1()); }
	(rule__ModuleFilterSpecifier__SourcePathAssignment_1_1)
	{ after(grammarAccess.getModuleFilterSpecifierAccess().getSourcePathAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDependency__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDependency__Group__0__Impl
	rule__ProjectDependency__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependency__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDependencyAccess().getProjectAssignment_0()); }
	(rule__ProjectDependency__ProjectAssignment_0)
	{ after(grammarAccess.getProjectDependencyAccess().getProjectAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependency__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDependency__Group__1__Impl
	rule__ProjectDependency__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependency__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDependencyAccess().getVersionConstraintAssignment_1()); }
	(rule__ProjectDependency__VersionConstraintAssignment_1)?
	{ after(grammarAccess.getProjectDependencyAccess().getVersionConstraintAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependency__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDependency__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependency__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProjectDependencyAccess().getDeclaredScopeAssignment_2()); }
	(rule__ProjectDependency__DeclaredScopeAssignment_2)?
	{ after(grammarAccess.getProjectDependencyAccess().getDeclaredScopeAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SimpleProjectDescription__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SimpleProjectDescription__Group__0__Impl
	rule__SimpleProjectDescription__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleProjectDescription__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSimpleProjectDescriptionAccess().getGroup_0()); }
	(rule__SimpleProjectDescription__Group_0__0)?
	{ after(grammarAccess.getSimpleProjectDescriptionAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleProjectDescription__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SimpleProjectDescription__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleProjectDescription__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSimpleProjectDescriptionAccess().getProjectIdAssignment_1()); }
	(rule__SimpleProjectDescription__ProjectIdAssignment_1)
	{ after(grammarAccess.getSimpleProjectDescriptionAccess().getProjectIdAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SimpleProjectDescription__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SimpleProjectDescription__Group_0__0__Impl
	rule__SimpleProjectDescription__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleProjectDescription__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSimpleProjectDescriptionAccess().getDeclaredVendorIdAssignment_0_0()); }
	(rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0)
	{ after(grammarAccess.getSimpleProjectDescriptionAccess().getDeclaredVendorIdAssignment_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleProjectDescription__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SimpleProjectDescription__Group_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleProjectDescription__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSimpleProjectDescriptionAccess().getColonKeyword_0_1()); }
	Colon
	{ after(grammarAccess.getSimpleProjectDescriptionAccess().getColonKeyword_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VersionConstraint__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionConstraint__Group_0__0__Impl
	rule__VersionConstraint__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionConstraintAccess().getAlternatives_0_0()); }
	(rule__VersionConstraint__Alternatives_0_0)
	{ after(grammarAccess.getVersionConstraintAccess().getAlternatives_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionConstraint__Group_0__1__Impl
	rule__VersionConstraint__Group_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_0_1()); }
	(rule__VersionConstraint__LowerVersionAssignment_0_1)
	{ after(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Group_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionConstraint__Group_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Group_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2()); }
	(rule__VersionConstraint__Alternatives_0_2)
	{ after(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VersionConstraint__Group_0_2_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionConstraint__Group_0_2_0__0__Impl
	rule__VersionConstraint__Group_0_2_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Group_0_2_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionConstraintAccess().getCommaKeyword_0_2_0_0()); }
	Comma
	{ after(grammarAccess.getVersionConstraintAccess().getCommaKeyword_0_2_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Group_0_2_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionConstraint__Group_0_2_0__1__Impl
	rule__VersionConstraint__Group_0_2_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Group_0_2_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionConstraintAccess().getUpperVersionAssignment_0_2_0_1()); }
	(rule__VersionConstraint__UpperVersionAssignment_0_2_0_1)
	{ after(grammarAccess.getVersionConstraintAccess().getUpperVersionAssignment_0_2_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Group_0_2_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionConstraint__Group_0_2_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__Group_0_2_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2_0_2()); }
	(rule__VersionConstraint__Alternatives_0_2_0_2)
	{ after(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__N4mfIdentifier__Group_11__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__N4mfIdentifier__Group_11__0__Impl
	rule__N4mfIdentifier__Group_11__1
;
finally {
	restoreStackSize(stackSize);
}

rule__N4mfIdentifier__Group_11__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getN4mfIdentifierAccess().getProjectDependenciesKeyword_11_0()); }
	ProjectDependencies
	{ after(grammarAccess.getN4mfIdentifierAccess().getProjectDependenciesKeyword_11_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__N4mfIdentifier__Group_11__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__N4mfIdentifier__Group_11__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__N4mfIdentifier__Group_11__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getN4mfIdentifierAccess().getSystemKeyword_11_1()); }
	KW_System
	{ after(grammarAccess.getN4mfIdentifierAccess().getSystemKeyword_11_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__N4mfIdentifier__Group_15__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__N4mfIdentifier__Group_15__0__Impl
	rule__N4mfIdentifier__Group_15__1
;
finally {
	restoreStackSize(stackSize);
}

rule__N4mfIdentifier__Group_15__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getN4mfIdentifierAccess().getProcessorKeyword_15_0()); }
	Processor
	{ after(grammarAccess.getN4mfIdentifierAccess().getProcessorKeyword_15_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__N4mfIdentifier__Group_15__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__N4mfIdentifier__Group_15__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__N4mfIdentifier__Group_15__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getN4mfIdentifierAccess().getSourceKeyword_15_1()); }
	Source
	{ after(grammarAccess.getN4mfIdentifierAccess().getSourceKeyword_15_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__UnorderedGroup
	@init {
		int stackSize = keepStackSize();
		getUnorderedGroupHelper().enter(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
	}
:
	rule__ProjectDescription__UnorderedGroup__0
	{getUnorderedGroupHelper().canLeave(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup())}?
;
finally {
	getUnorderedGroupHelper().leave(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__Impl
	@init {
		int stackSize = keepStackSize();
		boolean selected = false;
	}
:
		(
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getGroup_0()); }
					(rule__ProjectDescription__Group_0__0)
					{ after(grammarAccess.getProjectDescriptionAccess().getGroup_0()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getGroup_1()); }
					(rule__ProjectDescription__Group_1__0)
					{ after(grammarAccess.getProjectDescriptionAccess().getGroup_1()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getGroup_2()); }
					(rule__ProjectDescription__Group_2__0)
					{ after(grammarAccess.getProjectDescriptionAccess().getGroup_2()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getGroup_3()); }
					(rule__ProjectDescription__Group_3__0)
					{ after(grammarAccess.getProjectDescriptionAccess().getGroup_3()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getGroup_4()); }
					(rule__ProjectDescription__Group_4__0)
					{ after(grammarAccess.getProjectDescriptionAccess().getGroup_4()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getGroup_5()); }
					(rule__ProjectDescription__Group_5__0)
					{ after(grammarAccess.getProjectDescriptionAccess().getGroup_5()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentAssignment_6()); }
					(rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6)
					{ after(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentAssignment_6()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_7()); }
					(rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7)
					{ after(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_7()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_8()); }
					(rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8)
					{ after(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_8()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_9()); }
					(rule__ProjectDescription__ProjectDependenciesAssignment_9)
					{ after(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_9()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getGroup_10()); }
					(rule__ProjectDescription__Group_10__0)
					{ after(grammarAccess.getProjectDescriptionAccess().getGroup_10()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_11()); }
					(rule__ProjectDescription__ImplementedProjectsAssignment_11)
					{ after(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_11()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_12()); }
					(rule__ProjectDescription__InitModulesAssignment_12)
					{ after(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_12()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getExecModuleAssignment_13()); }
					(rule__ProjectDescription__ExecModuleAssignment_13)
					{ after(grammarAccess.getProjectDescriptionAccess().getExecModuleAssignment_13()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getGroup_14()); }
					(rule__ProjectDescription__Group_14__0)
					{ after(grammarAccess.getProjectDescriptionAccess().getGroup_14()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getGroup_15()); }
					(rule__ProjectDescription__Group_15__0)
					{ after(grammarAccess.getProjectDescriptionAccess().getGroup_15()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getGroup_16()); }
					(rule__ProjectDescription__Group_16__0)
					{ after(grammarAccess.getProjectDescriptionAccess().getGroup_16()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getGroup_17()); }
					(rule__ProjectDescription__Group_17__0)
					{ after(grammarAccess.getProjectDescriptionAccess().getGroup_17()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getGroup_18()); }
					(rule__ProjectDescription__Group_18__0)
					{ after(grammarAccess.getProjectDescriptionAccess().getGroup_18()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_19()); }
					(rule__ProjectDescription__TestedProjectsAssignment_19)
					{ after(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_19()); }
				)
			)
		)|
		( 
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20);
				}
				{
					selected = true;
				}
				(
					{ before(grammarAccess.getProjectDescriptionAccess().getGroup_20()); }
					(rule__ProjectDescription__Group_20__0)
					{ after(grammarAccess.getProjectDescriptionAccess().getGroup_20()); }
				)
			)
		)
		)
;
finally {
	if (selected)
		getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__1?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__2?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__3?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__4?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__5?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__6?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__7?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__8?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__9?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__9
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__10?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__10
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__11?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__11
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__12?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__12
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__13?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__13
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__14?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__14
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__15?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__15
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__16?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__16
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__17?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__17
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__18?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__18
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__19?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__19
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
	rule__ProjectDescription__UnorderedGroup__20?
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__UnorderedGroup__20
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProjectDescription__UnorderedGroup__Impl
;
finally {
	restoreStackSize(stackSize);
}


rule__ProjectDescription__ProjectIdAssignment_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getProjectIdN4mfIdentifierParserRuleCall_0_2_0()); }
		ruleN4mfIdentifier
		{ after(grammarAccess.getProjectDescriptionAccess().getProjectIdN4mfIdentifierParserRuleCall_0_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__ProjectTypeAssignment_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getProjectTypeProjectTypeEnumRuleCall_1_2_0()); }
		ruleProjectType
		{ after(grammarAccess.getProjectDescriptionAccess().getProjectTypeProjectTypeEnumRuleCall_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__ProjectVersionAssignment_2_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getProjectVersionDeclaredVersionParserRuleCall_2_2_0()); }
		ruleDeclaredVersion
		{ after(grammarAccess.getProjectDescriptionAccess().getProjectVersionDeclaredVersionParserRuleCall_2_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__DeclaredVendorIdAssignment_3_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_3_2_0()); }
		ruleN4mfIdentifier
		{ after(grammarAccess.getProjectDescriptionAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_3_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__VendorNameAssignment_4_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getVendorNameSTRINGTerminalRuleCall_4_2_0()); }
		RULE_STRING
		{ after(grammarAccess.getProjectDescriptionAccess().getVendorNameSTRINGTerminalRuleCall_4_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__MainModuleAssignment_5_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getMainModuleSTRINGTerminalRuleCall_5_2_0()); }
		RULE_STRING
		{ after(grammarAccess.getProjectDescriptionAccess().getMainModuleSTRINGTerminalRuleCall_5_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentExtendedRuntimeEnvironmentParserRuleCall_6_0()); }
		ruleExtendedRuntimeEnvironment
		{ after(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentExtendedRuntimeEnvironmentParserRuleCall_6_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibrariesParserRuleCall_7_0()); }
		ruleProvidedRuntimeLibraries
		{ after(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibrariesParserRuleCall_7_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibrariesParserRuleCall_8_0()); }
		ruleRequiredRuntimeLibraries
		{ after(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibrariesParserRuleCall_8_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__ProjectDependenciesAssignment_9
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesProjectDependenciesParserRuleCall_9_0()); }
		ruleProjectDependencies
		{ after(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesProjectDependenciesParserRuleCall_9_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__ImplementationIdAssignment_10_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getImplementationIdN4mfIdentifierParserRuleCall_10_2_0()); }
		ruleN4mfIdentifier
		{ after(grammarAccess.getProjectDescriptionAccess().getImplementationIdN4mfIdentifierParserRuleCall_10_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__ImplementedProjectsAssignment_11
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsImplementedProjectsParserRuleCall_11_0()); }
		ruleImplementedProjects
		{ after(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsImplementedProjectsParserRuleCall_11_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__InitModulesAssignment_12
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getInitModulesInitModulesParserRuleCall_12_0()); }
		ruleInitModules
		{ after(grammarAccess.getProjectDescriptionAccess().getInitModulesInitModulesParserRuleCall_12_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__ExecModuleAssignment_13
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getExecModuleExecModuleParserRuleCall_13_0()); }
		ruleExecModule
		{ after(grammarAccess.getProjectDescriptionAccess().getExecModuleExecModuleParserRuleCall_13_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__OutputPathRawAssignment_14_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getOutputPathRawSTRINGTerminalRuleCall_14_2_0()); }
		RULE_STRING
		{ after(grammarAccess.getProjectDescriptionAccess().getOutputPathRawSTRINGTerminalRuleCall_14_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__LibraryPathsRawAssignment_15_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawSTRINGTerminalRuleCall_15_2_0()); }
		RULE_STRING
		{ after(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawSTRINGTerminalRuleCall_15_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawSTRINGTerminalRuleCall_15_3_1_0()); }
		RULE_STRING
		{ after(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawSTRINGTerminalRuleCall_15_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__ResourcePathsRawAssignment_16_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawSTRINGTerminalRuleCall_16_2_0()); }
		RULE_STRING
		{ after(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawSTRINGTerminalRuleCall_16_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawSTRINGTerminalRuleCall_16_3_1_0()); }
		RULE_STRING
		{ after(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawSTRINGTerminalRuleCall_16_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__SourceFragmentAssignment_17_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getSourceFragmentSourceFragmentParserRuleCall_17_2_0()); }
		ruleSourceFragment
		{ after(grammarAccess.getProjectDescriptionAccess().getSourceFragmentSourceFragmentParserRuleCall_17_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__ModuleFiltersAssignment_18_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getModuleFiltersModuleFilterParserRuleCall_18_2_0()); }
		ruleModuleFilter
		{ after(grammarAccess.getProjectDescriptionAccess().getModuleFiltersModuleFilterParserRuleCall_18_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__TestedProjectsAssignment_19
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getTestedProjectsTestedProjectsParserRuleCall_19_0()); }
		ruleTestedProjects
		{ after(grammarAccess.getProjectDescriptionAccess().getTestedProjectsTestedProjectsParserRuleCall_19_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDescription__ModuleLoaderAssignment_20_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDescriptionAccess().getModuleLoaderModuleLoaderEnumRuleCall_20_2_0()); }
		ruleModuleLoader
		{ after(grammarAccess.getProjectDescriptionAccess().getModuleLoaderModuleLoaderEnumRuleCall_20_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExecModule__ExecModuleAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExecModuleAccess().getExecModuleBootstrapModuleParserRuleCall_3_0()); }
		ruleBootstrapModule
		{ after(grammarAccess.getExecModuleAccess().getExecModuleBootstrapModuleParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__TestedProjectsAssignment_3_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTestedProjectsAccess().getTestedProjectsTestedProjectParserRuleCall_3_0_0()); }
		ruleTestedProject
		{ after(grammarAccess.getTestedProjectsAccess().getTestedProjectsTestedProjectParserRuleCall_3_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProjects__TestedProjectsAssignment_3_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTestedProjectsAccess().getTestedProjectsTestedProjectParserRuleCall_3_1_1_0()); }
		ruleTestedProject
		{ after(grammarAccess.getTestedProjectsAccess().getTestedProjectsTestedProjectParserRuleCall_3_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__InitModulesAssignment_3_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInitModulesAccess().getInitModulesBootstrapModuleParserRuleCall_3_0_0()); }
		ruleBootstrapModule
		{ after(grammarAccess.getInitModulesAccess().getInitModulesBootstrapModuleParserRuleCall_3_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InitModules__InitModulesAssignment_3_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInitModulesAccess().getInitModulesBootstrapModuleParserRuleCall_3_1_1_0()); }
		ruleBootstrapModule
		{ after(grammarAccess.getInitModulesAccess().getInitModulesBootstrapModuleParserRuleCall_3_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__ImplementedProjectsAssignment_3_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsProjectReferenceParserRuleCall_3_0_0()); }
		ruleProjectReference
		{ after(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsProjectReferenceParserRuleCall_3_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsProjectReferenceParserRuleCall_3_1_1_0()); }
		ruleProjectReference
		{ after(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsProjectReferenceParserRuleCall_3_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__ProjectDependenciesAssignment_3_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesProjectDependencyParserRuleCall_3_0_0()); }
		ruleProjectDependency
		{ after(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesProjectDependencyParserRuleCall_3_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesProjectDependencyParserRuleCall_3_1_1_0()); }
		ruleProjectDependency
		{ after(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesProjectDependencyParserRuleCall_3_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_0_0()); }
		ruleProvidedRuntimeLibraryDependency
		{ after(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_1_1_0()); }
		ruleProvidedRuntimeLibraryDependency
		{ after(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_0_0()); }
		ruleRequiredRuntimeLibraryDependency
		{ after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_1_1_0()); }
		ruleRequiredRuntimeLibraryDependency
		{ after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_3_0()); }
		ruleProjectReference
		{ after(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__MajorAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getDeclaredVersionAccess().getMajorINTTerminalRuleCall_0_0()); }
		RULE_INT
		{ after(grammarAccess.getDeclaredVersionAccess().getMajorINTTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__MinorAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getDeclaredVersionAccess().getMinorINTTerminalRuleCall_1_1_0()); }
		RULE_INT
		{ after(grammarAccess.getDeclaredVersionAccess().getMinorINTTerminalRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__MicroAssignment_1_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getDeclaredVersionAccess().getMicroINTTerminalRuleCall_1_2_1_0()); }
		RULE_INT
		{ after(grammarAccess.getDeclaredVersionAccess().getMicroINTTerminalRuleCall_1_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__DeclaredVersion__QualifierAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getDeclaredVersionAccess().getQualifierN4mfIdentifierParserRuleCall_2_1_0()); }
		ruleN4mfIdentifier
		{ after(grammarAccess.getDeclaredVersionAccess().getQualifierN4mfIdentifierParserRuleCall_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__SourceFragmentTypeAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeSourceFragmentTypeEnumRuleCall_0_0()); }
		ruleSourceFragmentType
		{ after(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeSourceFragmentTypeEnumRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__PathsRawAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSourceFragmentAccess().getPathsRawSTRINGTerminalRuleCall_2_0()); }
		RULE_STRING
		{ after(grammarAccess.getSourceFragmentAccess().getPathsRawSTRINGTerminalRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SourceFragment__PathsRawAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSourceFragmentAccess().getPathsRawSTRINGTerminalRuleCall_3_1_0()); }
		RULE_STRING
		{ after(grammarAccess.getSourceFragmentAccess().getPathsRawSTRINGTerminalRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__ModuleFilterTypeAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getModuleFilterAccess().getModuleFilterTypeModuleFilterTypeEnumRuleCall_0_0()); }
		ruleModuleFilterType
		{ after(grammarAccess.getModuleFilterAccess().getModuleFilterTypeModuleFilterTypeEnumRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__ModuleSpecifiersAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getModuleFilterAccess().getModuleSpecifiersModuleFilterSpecifierParserRuleCall_2_0()); }
		ruleModuleFilterSpecifier
		{ after(grammarAccess.getModuleFilterAccess().getModuleSpecifiersModuleFilterSpecifierParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilter__ModuleSpecifiersAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getModuleFilterAccess().getModuleSpecifiersModuleFilterSpecifierParserRuleCall_3_1_0()); }
		ruleModuleFilterSpecifier
		{ after(grammarAccess.getModuleFilterAccess().getModuleSpecifiersModuleFilterSpecifierParserRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0()); }
		RULE_STRING
		{ after(grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__BootstrapModule__SourcePathAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getBootstrapModuleAccess().getSourcePathSTRINGTerminalRuleCall_1_1_0()); }
		RULE_STRING
		{ after(grammarAccess.getBootstrapModuleAccess().getSourcePathSTRINGTerminalRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0()); }
		RULE_STRING
		{ after(grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ModuleFilterSpecifier__SourcePathAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getModuleFilterSpecifierAccess().getSourcePathSTRINGTerminalRuleCall_1_1_0()); }
		RULE_STRING
		{ after(grammarAccess.getModuleFilterSpecifierAccess().getSourcePathSTRINGTerminalRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedRuntimeLibraryDependency__ProjectAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); }
		ruleSimpleProjectDescription
		{ after(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredRuntimeLibraryDependency__ProjectAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); }
		ruleSimpleProjectDescription
		{ after(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TestedProject__ProjectAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTestedProjectAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); }
		ruleSimpleProjectDescription
		{ after(grammarAccess.getTestedProjectAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectReference__ProjectAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectReferenceAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); }
		ruleSimpleProjectDescription
		{ after(grammarAccess.getProjectReferenceAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependency__ProjectAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0_0()); }
		ruleSimpleProjectDescription
		{ after(grammarAccess.getProjectDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependency__VersionConstraintAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDependencyAccess().getVersionConstraintVersionConstraintParserRuleCall_1_0()); }
		ruleVersionConstraint
		{ after(grammarAccess.getProjectDependencyAccess().getVersionConstraintVersionConstraintParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProjectDependency__DeclaredScopeAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProjectDependencyAccess().getDeclaredScopeProjectDependencyScopeEnumRuleCall_2_0()); }
		ruleProjectDependencyScope
		{ after(grammarAccess.getProjectDependencyAccess().getDeclaredScopeProjectDependencyScopeEnumRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSimpleProjectDescriptionAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_0_0_0()); }
		ruleN4mfIdentifier
		{ after(grammarAccess.getSimpleProjectDescriptionAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleProjectDescription__ProjectIdAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSimpleProjectDescriptionAccess().getProjectIdN4mfIdentifierParserRuleCall_1_0()); }
		ruleN4mfIdentifier
		{ after(grammarAccess.getSimpleProjectDescriptionAccess().getProjectIdN4mfIdentifierParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionConstraintAccess().getExclLowerBoundLeftParenthesisKeyword_0_0_0_0()); }
		(
			{ before(grammarAccess.getVersionConstraintAccess().getExclLowerBoundLeftParenthesisKeyword_0_0_0_0()); }
			LeftParenthesis
			{ after(grammarAccess.getVersionConstraintAccess().getExclLowerBoundLeftParenthesisKeyword_0_0_0_0()); }
		)
		{ after(grammarAccess.getVersionConstraintAccess().getExclLowerBoundLeftParenthesisKeyword_0_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__LowerVersionAssignment_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionConstraintAccess().getLowerVersionDeclaredVersionParserRuleCall_0_1_0()); }
		ruleDeclaredVersion
		{ after(grammarAccess.getVersionConstraintAccess().getLowerVersionDeclaredVersionParserRuleCall_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__UpperVersionAssignment_0_2_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionConstraintAccess().getUpperVersionDeclaredVersionParserRuleCall_0_2_0_1_0()); }
		ruleDeclaredVersion
		{ after(grammarAccess.getVersionConstraintAccess().getUpperVersionDeclaredVersionParserRuleCall_0_2_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionConstraintAccess().getExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0()); }
		(
			{ before(grammarAccess.getVersionConstraintAccess().getExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0()); }
			RightParenthesis
			{ after(grammarAccess.getVersionConstraintAccess().getExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0()); }
		)
		{ after(grammarAccess.getVersionConstraintAccess().getExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionConstraint__LowerVersionAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionConstraintAccess().getLowerVersionDeclaredVersionParserRuleCall_1_0()); }
		ruleDeclaredVersion
		{ after(grammarAccess.getVersionConstraintAccess().getLowerVersionDeclaredVersionParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}
