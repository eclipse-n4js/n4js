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
grammar DebugInternalN4MF;

// Rule ProjectDescription
ruleProjectDescription:
	('ProjectId'
	':'
	ruleN4mfIdentifier
	    |
	'ProjectType'
	':'
	ruleProjectType
	    |
	'ProjectVersion'
	':'
	ruleDeclaredVersion
	    |
	'VendorId'
	':'
	ruleN4mfIdentifier
	    |
	(
		'VendorName'
		':'
		RULE_STRING
	)?
	    |
	(
		'MainModule'
		':'
		RULE_STRING
	)?
	    |
	ruleExtendedRuntimeEnvironment
	?
	    |
	ruleProvidedRuntimeLibraries
	?
	    |
	ruleRequiredRuntimeLibraries
	?
	    |
	ruleProjectDependencies
	?
	    |
	(
		'ImplementationId'
		':'
		ruleN4mfIdentifier
	)?
	    |
	ruleImplementedProjects
	?
	    |
	ruleInitModules
	?
	    |
	ruleExecModule
	?
	    |
	(
		'Output'
		':'
		RULE_STRING
	)?
	    |
	(
		'Libraries'
		'{'
		RULE_STRING
		(
			','
			RULE_STRING
		)*
		'}'
	)?
	    |
	(
		'Resources'
		'{'
		RULE_STRING
		(
			','
			RULE_STRING
		)*
		'}'
	)?
	    |
	(
		'Sources'
		'{'
		ruleSourceFragment
		+
		'}'
	)?
	    |
	(
		'ModuleFilters'
		'{'
		ruleModuleFilter
		+
		'}'
	)?
	    |
	ruleTestedProjects
	?
	    |
	(
		'ModuleLoader'
		':'
		ruleModuleLoader
	)?
	)*
;

// Rule ExecModule
ruleExecModule:
	'ExecModule'
	':'
	ruleBootstrapModule
;

// Rule TestedProjects
ruleTestedProjects:
	'TestedProjects'
	'{'
	(
		ruleTestedProject
		(
			','
			ruleTestedProject
		)*
	)?
	'}'
;

// Rule InitModules
ruleInitModules:
	'InitModules'
	'{'
	(
		ruleBootstrapModule
		(
			','
			ruleBootstrapModule
		)*
	)?
	'}'
;

// Rule ImplementedProjects
ruleImplementedProjects:
	'ImplementedProjects'
	'{'
	(
		ruleProjectReference
		(
			','
			ruleProjectReference
		)*
	)?
	'}'
;

// Rule ProjectDependencies
ruleProjectDependencies:
	'ProjectDependencies'
	'{'
	(
		ruleProjectDependency
		(
			','
			ruleProjectDependency
		)*
	)?
	'}'
;

// Rule ProvidedRuntimeLibraries
ruleProvidedRuntimeLibraries:
	'ProvidedRuntimeLibraries'
	'{'
	(
		ruleProvidedRuntimeLibraryDependency
		(
			','
			ruleProvidedRuntimeLibraryDependency
		)*
	)?
	'}'
;

// Rule RequiredRuntimeLibraries
ruleRequiredRuntimeLibraries:
	'RequiredRuntimeLibraries'
	'{'
	(
		ruleRequiredRuntimeLibraryDependency
		(
			','
			ruleRequiredRuntimeLibraryDependency
		)*
	)?
	'}'
;

// Rule ExtendedRuntimeEnvironment
ruleExtendedRuntimeEnvironment:
	'ExtendedRuntimeEnvironment'
	':'
	ruleProjectReference
;

// Rule DeclaredVersion
ruleDeclaredVersion:
	RULE_INT
	(
		'.'
		RULE_INT
		(
			'.'
			RULE_INT
		)?
	)?
	(
		'-'
		ruleN4mfIdentifier
	)?
;

// Rule SourceFragment
ruleSourceFragment:
	ruleSourceFragmentType
	'{'
	RULE_STRING
	(
		','
		RULE_STRING
	)*
	'}'
;

// Rule ModuleFilter
ruleModuleFilter:
	ruleModuleFilterType
	'{'
	ruleModuleFilterSpecifier
	(
		','
		ruleModuleFilterSpecifier
	)*
	'}'
;

// Rule BootstrapModule
ruleBootstrapModule:
	RULE_STRING
	(
		'in'
		RULE_STRING
	)?
;

// Rule ModuleFilterSpecifier
ruleModuleFilterSpecifier:
	RULE_STRING
	(
		'in'
		RULE_STRING
	)?
;

// Rule ProvidedRuntimeLibraryDependency
ruleProvidedRuntimeLibraryDependency:
	ruleSimpleProjectDescription
;

// Rule RequiredRuntimeLibraryDependency
ruleRequiredRuntimeLibraryDependency:
	ruleSimpleProjectDescription
;

// Rule TestedProject
ruleTestedProject:
	ruleSimpleProjectDescription
;

// Rule ProjectReference
ruleProjectReference:
	ruleSimpleProjectDescription
;

// Rule ProjectDependency
ruleProjectDependency:
	ruleSimpleProjectDescription
	ruleVersionConstraint
	?
	ruleProjectDependencyScope
	?
;

// Rule SimpleProjectDescription
ruleSimpleProjectDescription:
	(
		ruleN4mfIdentifier
		':'
	)?
	ruleN4mfIdentifier
;

// Rule VersionConstraint
ruleVersionConstraint:
	(
		(
			'('
			    |
			'['
		)
		ruleDeclaredVersion
		(
			(
				','
				ruleDeclaredVersion
				(
					')'
					    |
					']'
				)
			)?
			    |
			')'
		)
		    |
		ruleDeclaredVersion
	)
;

// Rule N4mfIdentifier
ruleN4mfIdentifier:
	(
		RULE_ID
		    |
		'ProjectId'
		    |
		'ProjectType'
		    |
		'ProjectVersion'
		    |
		'VendorId'
		    |
		'VendorName'
		    |
		'Output'
		    |
		'Libraries'
		    |
		'Resources'
		    |
		'Sources'
		    |
		'ModuleFilters'
		    |
		'ProjectDependencies'
		'system'
		    |
		'API'
		    |
		'user'
		    |
		'application'
		    |
		'processor'
		'source'
		    |
		'content'
		    |
		'test'
	)
;

// Rule ProjectType
ruleProjectType:
	(
		'application'
		    |
		'processor'
		    |
		'library'
		    |
		'API'
		    |
		'runtimeEnvironment'
		    |
		'runtimeLibrary'
		    |
		'test'
	)
;

// Rule SourceFragmentType
ruleSourceFragmentType:
	(
		'source'
		    |
		'external'
		    |
		'test'
	)
;

// Rule ModuleFilterType
ruleModuleFilterType:
	(
		'noValidate'
		    |
		'noModuleWrap'
	)
;

// Rule ProjectDependencyScope
ruleProjectDependencyScope:
	(
		'compile'
		    |
		'test'
	)
;

// Rule ModuleLoader
ruleModuleLoader:
	(
		'n4js'
		    |
		'commonjs'
		    |
		'node_builtin'
	)
;

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'-'|'.'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/' {skip();};

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')? {skip();};

RULE_WS : (' '|'\t'|'\r'|'\n')+ {skip();};

RULE_ANY_OTHER : .;
