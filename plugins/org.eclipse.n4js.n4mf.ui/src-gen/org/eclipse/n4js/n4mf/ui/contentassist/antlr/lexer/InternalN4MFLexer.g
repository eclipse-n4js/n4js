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
lexer grammar InternalN4MFLexer;

@header {
package org.eclipse.n4js.n4mf.ui.contentassist.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
}

ExtendedRuntimeEnvironment : 'ExtendedRuntimeEnvironment';

ProvidedRuntimeLibraries : 'ProvidedRuntimeLibraries';

RequiredRuntimeLibraries : 'RequiredRuntimeLibraries';

ImplementedProjects : 'ImplementedProjects';

ProjectDependencies : 'ProjectDependencies';

RuntimeEnvironment : 'runtimeEnvironment';

ImplementationId : 'ImplementationId';

ProjectVersion : 'ProjectVersion';

TestedProjects : 'TestedProjects';

RuntimeLibrary : 'runtimeLibrary';

ModuleFilters : 'ModuleFilters';

ModuleLoader : 'ModuleLoader';

NoModuleWrap : 'noModuleWrap';

Node_builtin : 'node_builtin';

InitModules : 'InitModules';

ProjectType : 'ProjectType';

Application : 'application';

ExecModule : 'ExecModule';

MainModule : 'MainModule';

VendorName : 'VendorName';

NoValidate : 'noValidate';

Libraries : 'Libraries';

ProjectId : 'ProjectId';

Resources : 'Resources';

Processor : 'processor';

VendorId : 'VendorId';

Commonjs : 'commonjs';

External : 'external';

Sources : 'Sources';

Compile : 'compile';

Content : 'content';

Library : 'library';

Output : 'Output';

Source : 'source';

KW_System : 'system';

N4js : 'n4js';

Test : 'test';

User : 'user';

API : 'API';

In : 'in';

LeftParenthesis : '(';

RightParenthesis : ')';

Comma : ',';

HyphenMinus : '-';

FullStop : '.';

Colon : ':';

LeftSquareBracket : '[';

RightSquareBracket : ']';

LeftCurlyBracket : '{';

RightCurlyBracket : '}';

// Rules duplicated to allow inter-rule references

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'-'|'.'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;
