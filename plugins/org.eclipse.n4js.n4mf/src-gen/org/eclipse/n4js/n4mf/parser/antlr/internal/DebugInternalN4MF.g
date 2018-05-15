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
	(
		'ExtendedRuntimeEnvironment'
		':'
		ruleProjectReference
	)?
	    |
	(
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
	)?
	    |
	(
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
	)?
	    |
	(
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
	)?
	    |
	(
		'ImplementationId'
		':'
		ruleN4mfIdentifier
	)?
	    |
	(
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
	)?
	    |
	(
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
	)?
	    |
	(
		'ExecModule'
		':'
		ruleBootstrapModule
	)?
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
	(
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
	)?
	    |
	(
		'ModuleLoader'
		':'
		ruleModuleLoader
	)?
	)*
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
	ruleProjectIdWithOptionalVendor
;

// Rule RequiredRuntimeLibraryDependency
ruleRequiredRuntimeLibraryDependency:
	ruleProjectIdWithOptionalVendor
;

// Rule TestedProject
ruleTestedProject:
	ruleProjectIdWithOptionalVendor
;

// Rule ProjectReference
ruleProjectReference:
	ruleProjectIdWithOptionalVendor
;

// Rule ProjectDependency
ruleProjectDependency:
	ruleProjectIdWithOptionalVendor
	ruleVersionConstraint
	?
	ruleProjectDependencyScope
	?
;

// Rule ProjectIdWithOptionalVendor
ruleProjectIdWithOptionalVendor:
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
		    |
		'validation'
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
