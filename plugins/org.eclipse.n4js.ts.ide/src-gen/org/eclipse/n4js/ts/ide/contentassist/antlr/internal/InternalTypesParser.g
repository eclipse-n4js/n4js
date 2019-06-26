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
parser grammar InternalTypesParser;

options {
	tokenVocab=InternalTypesLexer;
	superClass=AbstractInternalContentAssistParser;
	backtrack=true;
}

@header {
package org.eclipse.n4js.ts.ide.contentassist.antlr.internal;
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
import org.eclipse.n4js.ts.services.TypesGrammarAccess;

}
@members {
	private TypesGrammarAccess grammarAccess;
	private final Map<String, String> tokenNameToValue = new HashMap<String, String>();
	
	{
		tokenNameToValue.put("Ampersand", "'&'");
		tokenNameToValue.put("LeftParenthesis", "'('");
		tokenNameToValue.put("RightParenthesis", "')'");
		tokenNameToValue.put("PlusSign", "'+'");
		tokenNameToValue.put("Comma", "','");
		tokenNameToValue.put("FullStop", "'.'");
		tokenNameToValue.put("Solidus", "'/'");
		tokenNameToValue.put("Colon", "':'");
		tokenNameToValue.put("Semicolon", "';'");
		tokenNameToValue.put("LessThanSign", "'<'");
		tokenNameToValue.put("EqualsSign", "'='");
		tokenNameToValue.put("GreaterThanSign", "'>'");
		tokenNameToValue.put("QuestionMark", "'?'");
		tokenNameToValue.put("CommercialAt", "'@'");
		tokenNameToValue.put("LeftSquareBracket", "'['");
		tokenNameToValue.put("RightSquareBracket", "']'");
		tokenNameToValue.put("LeftCurlyBracket", "'{'");
		tokenNameToValue.put("RightCurlyBracket", "'}'");
		tokenNameToValue.put("Tilde", "'~'");
		tokenNameToValue.put("EqualsSignGreaterThanSign", "'=>'");
		tokenNameToValue.put("As", "'as'");
		tokenNameToValue.put("Do", "'do'");
		tokenNameToValue.put("If", "'if'");
		tokenNameToValue.put("In", "'in'");
		tokenNameToValue.put("Of", "'of'");
		tokenNameToValue.put("FullStopFullStopFullStop", "'...'");
		tokenNameToValue.put("Any", "'any'");
		tokenNameToValue.put("For", "'for'");
		tokenNameToValue.put("Get", "'get'");
		tokenNameToValue.put("Let", "'let'");
		tokenNameToValue.put("New", "'new'");
		tokenNameToValue.put("Out", "'out'");
		tokenNameToValue.put("Set", "'set'");
		tokenNameToValue.put("Try", "'try'");
		tokenNameToValue.put("Var", "'var'");
		tokenNameToValue.put("This", "'This'");
		tokenNameToValue.put("Case", "'case'");
		tokenNameToValue.put("Else", "'else'");
		tokenNameToValue.put("Enum", "'enum'");
		tokenNameToValue.put("From", "'from'");
		tokenNameToValue.put("Null", "'null'");
		tokenNameToValue.put("This_1", "'this'");
		tokenNameToValue.put("True", "'true'");
		tokenNameToValue.put("Type", "'type'");
		tokenNameToValue.put("Void", "'void'");
		tokenNameToValue.put("With", "'with'");
		tokenNameToValue.put("Async", "'async'");
		tokenNameToValue.put("Await", "'await'");
		tokenNameToValue.put("Break", "'break'");
		tokenNameToValue.put("Catch", "'catch'");
		tokenNameToValue.put("Class", "'class'");
		tokenNameToValue.put("Const", "'const'");
		tokenNameToValue.put("False", "'false'");
		tokenNameToValue.put("Final", "'final'");
		tokenNameToValue.put("Super", "'super'");
		tokenNameToValue.put("Throw", "'throw'");
		tokenNameToValue.put("Union", "'union'");
		tokenNameToValue.put("While", "'while'");
		tokenNameToValue.put("Yield", "'yield'");
		tokenNameToValue.put("Delete", "'delete'");
		tokenNameToValue.put("Export", "'export'");
		tokenNameToValue.put("Import", "'import'");
		tokenNameToValue.put("Object", "'object'");
		tokenNameToValue.put("Public", "'public'");
		tokenNameToValue.put("Return", "'return'");
		tokenNameToValue.put("Static", "'static'");
		tokenNameToValue.put("Switch", "'switch'");
		tokenNameToValue.put("Target", "'target'");
		tokenNameToValue.put("Typeof", "'typeof'");
		tokenNameToValue.put("Default", "'default'");
		tokenNameToValue.put("Extends", "'extends'");
		tokenNameToValue.put("Finally", "'finally'");
		tokenNameToValue.put("Indexed", "'indexed'");
		tokenNameToValue.put("Private", "'private'");
		tokenNameToValue.put("Project", "'project'");
		tokenNameToValue.put("Abstract", "'abstract'");
		tokenNameToValue.put("Continue", "'continue'");
		tokenNameToValue.put("Debugger", "'debugger'");
		tokenNameToValue.put("External", "'external'");
		tokenNameToValue.put("Function", "'function'");
		tokenNameToValue.put("Promisify", "'Promisify'");
		tokenNameToValue.put("Interface", "'interface'");
		tokenNameToValue.put("Primitive", "'primitive'");
		tokenNameToValue.put("Protected", "'protected'");
		tokenNameToValue.put("Undefined", "'undefined'");
		tokenNameToValue.put("Implements", "'implements'");
		tokenNameToValue.put("Instanceof", "'instanceof'");
		tokenNameToValue.put("Constructor", "'constructor'");
		tokenNameToValue.put("VirtualBase", "'virtualBase'");
		tokenNameToValue.put("Intersection", "'intersection'");
		tokenNameToValue.put("AutoboxedType", "'autoboxedType'");
		tokenNameToValue.put("PublicInternal", "'publicInternal'");
		tokenNameToValue.put("ProtectedInternal", "'protectedInternal'");
		tokenNameToValue.put("ProvidedByRuntime", "'providedByRuntime'");
		tokenNameToValue.put("AssignmnentCompatible", "'assignmnentCompatible'");
	}

	public void setGrammarAccess(TypesGrammarAccess grammarAccess) {
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

// Entry rule entryRuleTypeDefs
entryRuleTypeDefs
:
{ before(grammarAccess.getTypeDefsRule()); }
	 ruleTypeDefs
{ after(grammarAccess.getTypeDefsRule()); } 
	 EOF 
;

// Rule TypeDefs
ruleTypeDefs 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeDefsAccess().getTypesAssignment()); }
		(rule__TypeDefs__TypesAssignment)*
		{ after(grammarAccess.getTypeDefsAccess().getTypesAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTAnnotation
entryRuleTAnnotation
:
{ before(grammarAccess.getTAnnotationRule()); }
	 ruleTAnnotation
{ after(grammarAccess.getTAnnotationRule()); } 
	 EOF 
;

// Rule TAnnotation
ruleTAnnotation 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTAnnotationAccess().getGroup()); }
		(rule__TAnnotation__Group__0)
		{ after(grammarAccess.getTAnnotationAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTAnnotationArgument
entryRuleTAnnotationArgument
:
{ before(grammarAccess.getTAnnotationArgumentRule()); }
	 ruleTAnnotationArgument
{ after(grammarAccess.getTAnnotationArgumentRule()); } 
	 EOF 
;

// Rule TAnnotationArgument
ruleTAnnotationArgument 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTAnnotationArgumentAccess().getAlternatives()); }
		(rule__TAnnotationArgument__Alternatives)
		{ after(grammarAccess.getTAnnotationArgumentAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTAnnotationStringArgument
entryRuleTAnnotationStringArgument
:
{ before(grammarAccess.getTAnnotationStringArgumentRule()); }
	 ruleTAnnotationStringArgument
{ after(grammarAccess.getTAnnotationStringArgumentRule()); } 
	 EOF 
;

// Rule TAnnotationStringArgument
ruleTAnnotationStringArgument 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTAnnotationStringArgumentAccess().getValueAssignment()); }
		(rule__TAnnotationStringArgument__ValueAssignment)
		{ after(grammarAccess.getTAnnotationStringArgumentAccess().getValueAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTAnnotationTypeRefArgument
entryRuleTAnnotationTypeRefArgument
:
{ before(grammarAccess.getTAnnotationTypeRefArgumentRule()); }
	 ruleTAnnotationTypeRefArgument
{ after(grammarAccess.getTAnnotationTypeRefArgumentRule()); } 
	 EOF 
;

// Rule TAnnotationTypeRefArgument
ruleTAnnotationTypeRefArgument 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTAnnotationTypeRefArgumentAccess().getTypeRefAssignment()); }
		(rule__TAnnotationTypeRefArgument__TypeRefAssignment)
		{ after(grammarAccess.getTAnnotationTypeRefArgumentAccess().getTypeRefAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleType
entryRuleType
:
{ before(grammarAccess.getTypeRule()); }
	 ruleType
{ after(grammarAccess.getTypeRule()); } 
	 EOF 
;

// Rule Type
ruleType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeAccess().getAlternatives()); }
		(rule__Type__Alternatives)
		{ after(grammarAccess.getTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypeRef
entryRuleTypeRef
:
{ before(grammarAccess.getTypeRefRule()); }
	 ruleTypeRef
{ after(grammarAccess.getTypeRefRule()); } 
	 EOF 
;

// Rule TypeRef
ruleTypeRef 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeRefAccess().getGroup()); }
		(rule__TypeRef__Group__0)
		{ after(grammarAccess.getTypeRefAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRulePrimitiveType
entryRulePrimitiveType
:
{ before(grammarAccess.getPrimitiveTypeRule()); }
	 rulePrimitiveType
{ after(grammarAccess.getPrimitiveTypeRule()); } 
	 EOF 
;

// Rule PrimitiveType
rulePrimitiveType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getPrimitiveTypeAccess().getGroup()); }
		(rule__PrimitiveType__Group__0)
		{ after(grammarAccess.getPrimitiveTypeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypeReferenceName
entryRuleTypeReferenceName
:
{ before(grammarAccess.getTypeReferenceNameRule()); }
	 ruleTypeReferenceName
{ after(grammarAccess.getTypeReferenceNameRule()); } 
	 EOF 
;

// Rule TypeReferenceName
ruleTypeReferenceName 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeReferenceNameAccess().getAlternatives()); }
		(rule__TypeReferenceName__Alternatives)
		{ after(grammarAccess.getTypeReferenceNameAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleAnyType
entryRuleAnyType
:
{ before(grammarAccess.getAnyTypeRule()); }
	 ruleAnyType
{ after(grammarAccess.getAnyTypeRule()); } 
	 EOF 
;

// Rule AnyType
ruleAnyType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getAnyTypeAccess().getGroup()); }
		(rule__AnyType__Group__0)
		{ after(grammarAccess.getAnyTypeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleVoidType
entryRuleVoidType
:
{ before(grammarAccess.getVoidTypeRule()); }
	 ruleVoidType
{ after(grammarAccess.getVoidTypeRule()); } 
	 EOF 
;

// Rule VoidType
ruleVoidType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getVoidTypeAccess().getGroup()); }
		(rule__VoidType__Group__0)
		{ after(grammarAccess.getVoidTypeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleUndefinedType
entryRuleUndefinedType
:
{ before(grammarAccess.getUndefinedTypeRule()); }
	 ruleUndefinedType
{ after(grammarAccess.getUndefinedTypeRule()); } 
	 EOF 
;

// Rule UndefinedType
ruleUndefinedType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getUndefinedTypeAccess().getGroup()); }
		(rule__UndefinedType__Group__0)
		{ after(grammarAccess.getUndefinedTypeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleNullType
entryRuleNullType
:
{ before(grammarAccess.getNullTypeRule()); }
	 ruleNullType
{ after(grammarAccess.getNullTypeRule()); } 
	 EOF 
;

// Rule NullType
ruleNullType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getNullTypeAccess().getGroup()); }
		(rule__NullType__Group__0)
		{ after(grammarAccess.getNullTypeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypesIdentifier
entryRuleTypesIdentifier
:
{ before(grammarAccess.getTypesIdentifierRule()); }
	 ruleTypesIdentifier
{ after(grammarAccess.getTypesIdentifierRule()); } 
	 EOF 
;

// Rule TypesIdentifier
ruleTypesIdentifier 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypesIdentifierAccess().getAlternatives()); }
		(rule__TypesIdentifier__Alternatives)
		{ after(grammarAccess.getTypesIdentifierAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleBindingTypesIdentifier
entryRuleBindingTypesIdentifier
:
{ before(grammarAccess.getBindingTypesIdentifierRule()); }
	 ruleBindingTypesIdentifier
{ after(grammarAccess.getBindingTypesIdentifierRule()); } 
	 EOF 
;

// Rule BindingTypesIdentifier
ruleBindingTypesIdentifier 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getBindingTypesIdentifierAccess().getAlternatives()); }
		(rule__BindingTypesIdentifier__Alternatives)
		{ after(grammarAccess.getBindingTypesIdentifierAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleVoidOrBindingIdentifier
entryRuleVoidOrBindingIdentifier
:
{ before(grammarAccess.getVoidOrBindingIdentifierRule()); }
	 ruleVoidOrBindingIdentifier
{ after(grammarAccess.getVoidOrBindingIdentifierRule()); } 
	 EOF 
;

// Rule VoidOrBindingIdentifier
ruleVoidOrBindingIdentifier 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getVoidOrBindingIdentifierAccess().getAlternatives()); }
		(rule__VoidOrBindingIdentifier__Alternatives)
		{ after(grammarAccess.getVoidOrBindingIdentifierAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypesSpecificKeywords
entryRuleTypesSpecificKeywords
:
{ before(grammarAccess.getTypesSpecificKeywordsRule()); }
	 ruleTypesSpecificKeywords
{ after(grammarAccess.getTypesSpecificKeywordsRule()); } 
	 EOF 
;

// Rule TypesSpecificKeywords
ruleTypesSpecificKeywords 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypesSpecificKeywordsAccess().getAlternatives()); }
		(rule__TypesSpecificKeywords__Alternatives)
		{ after(grammarAccess.getTypesSpecificKeywordsAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypesComputedPropertyName
entryRuleTypesComputedPropertyName
:
{ before(grammarAccess.getTypesComputedPropertyNameRule()); }
	 ruleTypesComputedPropertyName
{ after(grammarAccess.getTypesComputedPropertyNameRule()); } 
	 EOF 
;

// Rule TypesComputedPropertyName
ruleTypesComputedPropertyName 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypesComputedPropertyNameAccess().getGroup()); }
		(rule__TypesComputedPropertyName__Group__0)
		{ after(grammarAccess.getTypesComputedPropertyNameAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypesSymbolLiteralComputedName
entryRuleTypesSymbolLiteralComputedName
:
{ before(grammarAccess.getTypesSymbolLiteralComputedNameRule()); }
	 ruleTypesSymbolLiteralComputedName
{ after(grammarAccess.getTypesSymbolLiteralComputedNameRule()); } 
	 EOF 
;

// Rule TypesSymbolLiteralComputedName
ruleTypesSymbolLiteralComputedName 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getGroup()); }
		(rule__TypesSymbolLiteralComputedName__Group__0)
		{ after(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypesStringLiteralComputedName
entryRuleTypesStringLiteralComputedName
:
{ before(grammarAccess.getTypesStringLiteralComputedNameRule()); }
	 ruleTypesStringLiteralComputedName
{ after(grammarAccess.getTypesStringLiteralComputedNameRule()); } 
	 EOF 
;

// Rule TypesStringLiteralComputedName
ruleTypesStringLiteralComputedName 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypesStringLiteralComputedNameAccess().getSTRINGTerminalRuleCall()); }
		RULE_STRING
		{ after(grammarAccess.getTypesStringLiteralComputedNameAccess().getSTRINGTerminalRuleCall()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTObjectPrototype
entryRuleTObjectPrototype
:
{ before(grammarAccess.getTObjectPrototypeRule()); }
	 ruleTObjectPrototype
{ after(grammarAccess.getTObjectPrototypeRule()); } 
	 EOF 
;

// Rule TObjectPrototype
ruleTObjectPrototype 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTObjectPrototypeAccess().getGroup()); }
		(rule__TObjectPrototype__Group__0)
		{ after(grammarAccess.getTObjectPrototypeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleVirtualBaseType
entryRuleVirtualBaseType
:
{ before(grammarAccess.getVirtualBaseTypeRule()); }
	 ruleVirtualBaseType
{ after(grammarAccess.getVirtualBaseTypeRule()); } 
	 EOF 
;

// Rule VirtualBaseType
ruleVirtualBaseType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getVirtualBaseTypeAccess().getGroup()); }
		(rule__VirtualBaseType__Group__0)
		{ after(grammarAccess.getVirtualBaseTypeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTClass
entryRuleTClass
:
{ before(grammarAccess.getTClassRule()); }
	 ruleTClass
{ after(grammarAccess.getTClassRule()); } 
	 EOF 
;

// Rule TClass
ruleTClass 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTClassAccess().getGroup()); }
		(rule__TClass__Group__0)
		{ after(grammarAccess.getTClassAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTInterface
entryRuleTInterface
:
{ before(grammarAccess.getTInterfaceRule()); }
	 ruleTInterface
{ after(grammarAccess.getTInterfaceRule()); } 
	 EOF 
;

// Rule TInterface
ruleTInterface 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTInterfaceAccess().getGroup()); }
		(rule__TInterface__Group__0)
		{ after(grammarAccess.getTInterfaceAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypeVariable
entryRuleTypeVariable
:
{ before(grammarAccess.getTypeVariableRule()); }
	 ruleTypeVariable
{ after(grammarAccess.getTypeVariableRule()); } 
	 EOF 
;

// Rule TypeVariable
ruleTypeVariable 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeVariableAccess().getGroup()); }
		(rule__TypeVariable__Group__0)
		{ after(grammarAccess.getTypeVariableAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule TClassOrInterfaceHeader
ruleTClassOrInterfaceHeader 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getGroup()); }
		(rule__TClassOrInterfaceHeader__Group__0)
		{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleCallableCtor
entryRuleCallableCtor
:
{ before(grammarAccess.getCallableCtorRule()); }
	 ruleCallableCtor
{ after(grammarAccess.getCallableCtorRule()); } 
	 EOF 
;

// Rule CallableCtor
ruleCallableCtor 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getCallableCtorAccess().getGroup()); }
		(rule__CallableCtor__Group__0)
		{ after(grammarAccess.getCallableCtorAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule TFormalParameters
ruleTFormalParameters 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTFormalParametersAccess().getGroup()); }
		(rule__TFormalParameters__Group__0)
		{ after(grammarAccess.getTFormalParametersAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTMember
entryRuleTMember
:
{ before(grammarAccess.getTMemberRule()); }
	 ruleTMember
{ after(grammarAccess.getTMemberRule()); } 
	 EOF 
;

// Rule TMember
ruleTMember 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTMemberAccess().getAlternatives()); }
		(rule__TMember__Alternatives)
		{ after(grammarAccess.getTMemberAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTMethod
entryRuleTMethod
:
{ before(grammarAccess.getTMethodRule()); }
	 ruleTMethod
{ after(grammarAccess.getTMethodRule()); } 
	 EOF 
;

// Rule TMethod
ruleTMethod 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTMethodAccess().getGroup()); }
		(rule__TMethod__Group__0)
		{ after(grammarAccess.getTMethodAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTField
entryRuleTField
:
{ before(grammarAccess.getTFieldRule()); }
	 ruleTField
{ after(grammarAccess.getTFieldRule()); } 
	 EOF 
;

// Rule TField
ruleTField 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTFieldAccess().getGroup()); }
		(rule__TField__Group__0)
		{ after(grammarAccess.getTFieldAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTGetter
entryRuleTGetter
:
{ before(grammarAccess.getTGetterRule()); }
	 ruleTGetter
{ after(grammarAccess.getTGetterRule()); } 
	 EOF 
;

// Rule TGetter
ruleTGetter 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTGetterAccess().getGroup()); }
		(rule__TGetter__Group__0)
		{ after(grammarAccess.getTGetterAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTSetter
entryRuleTSetter
:
{ before(grammarAccess.getTSetterRule()); }
	 ruleTSetter
{ after(grammarAccess.getTSetterRule()); } 
	 EOF 
;

// Rule TSetter
ruleTSetter 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTSetterAccess().getGroup()); }
		(rule__TSetter__Group__0)
		{ after(grammarAccess.getTSetterAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTFunction
entryRuleTFunction
:
{ before(grammarAccess.getTFunctionRule()); }
	 ruleTFunction
{ after(grammarAccess.getTFunctionRule()); } 
	 EOF 
;

// Rule TFunction
ruleTFunction 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTFunctionAccess().getGroup()); }
		(rule__TFunction__Group__0)
		{ after(grammarAccess.getTFunctionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTEnum
entryRuleTEnum
:
{ before(grammarAccess.getTEnumRule()); }
	 ruleTEnum
{ after(grammarAccess.getTEnumRule()); } 
	 EOF 
;

// Rule TEnum
ruleTEnum 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTEnumAccess().getGroup()); }
		(rule__TEnum__Group__0)
		{ after(grammarAccess.getTEnumAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTEnumLiteral
entryRuleTEnumLiteral
:
{ before(grammarAccess.getTEnumLiteralRule()); }
	 ruleTEnumLiteral
{ after(grammarAccess.getTEnumLiteralRule()); } 
	 EOF 
;

// Rule TEnumLiteral
ruleTEnumLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTEnumLiteralAccess().getNameAssignment()); }
		(rule__TEnumLiteral__NameAssignment)
		{ after(grammarAccess.getTEnumLiteralAccess().getNameAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleArrayTypeExpression
entryRuleArrayTypeExpression
:
{ before(grammarAccess.getArrayTypeExpressionRule()); }
	 ruleArrayTypeExpression
{ after(grammarAccess.getArrayTypeExpressionRule()); } 
	 EOF 
;

// Rule ArrayTypeExpression
ruleArrayTypeExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getArrayTypeExpressionAccess().getAlternatives()); }
		(rule__ArrayTypeExpression__Alternatives)
		{ after(grammarAccess.getArrayTypeExpressionAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRulePrimaryTypeExpression
entryRulePrimaryTypeExpression
:
{ before(grammarAccess.getPrimaryTypeExpressionRule()); }
	 rulePrimaryTypeExpression
{ after(grammarAccess.getPrimaryTypeExpressionRule()); } 
	 EOF 
;

// Rule PrimaryTypeExpression
rulePrimaryTypeExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getPrimaryTypeExpressionAccess().getAlternatives()); }
		(rule__PrimaryTypeExpression__Alternatives)
		{ after(grammarAccess.getPrimaryTypeExpressionAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypeRefWithModifiers
entryRuleTypeRefWithModifiers
:
{ before(grammarAccess.getTypeRefWithModifiersRule()); }
	 ruleTypeRefWithModifiers
{ after(grammarAccess.getTypeRefWithModifiersRule()); } 
	 EOF 
;

// Rule TypeRefWithModifiers
ruleTypeRefWithModifiers 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeRefWithModifiersAccess().getGroup()); }
		(rule__TypeRefWithModifiers__Group__0)
		{ after(grammarAccess.getTypeRefWithModifiersAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypeRefWithoutModifiers
entryRuleTypeRefWithoutModifiers
:
{ before(grammarAccess.getTypeRefWithoutModifiersRule()); }
	 ruleTypeRefWithoutModifiers
{ after(grammarAccess.getTypeRefWithoutModifiersRule()); } 
	 EOF 
;

// Rule TypeRefWithoutModifiers
ruleTypeRefWithoutModifiers 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeRefWithoutModifiersAccess().getAlternatives()); }
		(rule__TypeRefWithoutModifiers__Alternatives)
		{ after(grammarAccess.getTypeRefWithoutModifiersAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypeRefFunctionTypeExpression
entryRuleTypeRefFunctionTypeExpression
:
{ before(grammarAccess.getTypeRefFunctionTypeExpressionRule()); }
	 ruleTypeRefFunctionTypeExpression
{ after(grammarAccess.getTypeRefFunctionTypeExpressionRule()); } 
	 EOF 
;

// Rule TypeRefFunctionTypeExpression
ruleTypeRefFunctionTypeExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getAlternatives()); }
		(rule__TypeRefFunctionTypeExpression__Alternatives)
		{ after(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypeArgInTypeTypeRef
entryRuleTypeArgInTypeTypeRef
:
{ before(grammarAccess.getTypeArgInTypeTypeRefRule()); }
	 ruleTypeArgInTypeTypeRef
{ after(grammarAccess.getTypeArgInTypeTypeRefRule()); } 
	 EOF 
;

// Rule TypeArgInTypeTypeRef
ruleTypeArgInTypeTypeRef 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeArgInTypeTypeRefAccess().getAlternatives()); }
		(rule__TypeArgInTypeTypeRef__Alternatives)
		{ after(grammarAccess.getTypeArgInTypeTypeRefAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleThisTypeRef
entryRuleThisTypeRef
:
{ before(grammarAccess.getThisTypeRefRule()); }
	 ruleThisTypeRef
{ after(grammarAccess.getThisTypeRefRule()); } 
	 EOF 
;

// Rule ThisTypeRef
ruleThisTypeRef 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getThisTypeRefAccess().getAlternatives()); }
		(rule__ThisTypeRef__Alternatives)
		{ after(grammarAccess.getThisTypeRefAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleThisTypeRefNominal
entryRuleThisTypeRefNominal
:
{ before(grammarAccess.getThisTypeRefNominalRule()); }
	 ruleThisTypeRefNominal
{ after(grammarAccess.getThisTypeRefNominalRule()); } 
	 EOF 
;

// Rule ThisTypeRefNominal
ruleThisTypeRefNominal 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getThisTypeRefNominalAccess().getGroup()); }
		(rule__ThisTypeRefNominal__Group__0)
		{ after(grammarAccess.getThisTypeRefNominalAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleThisTypeRefStructural
entryRuleThisTypeRefStructural
:
{ before(grammarAccess.getThisTypeRefStructuralRule()); }
	 ruleThisTypeRefStructural
{ after(grammarAccess.getThisTypeRefStructuralRule()); } 
	 EOF 
;

// Rule ThisTypeRefStructural
ruleThisTypeRefStructural 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getThisTypeRefStructuralAccess().getGroup()); }
		(rule__ThisTypeRefStructural__Group__0)
		{ after(grammarAccess.getThisTypeRefStructuralAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleFunctionTypeExpressionOLD
entryRuleFunctionTypeExpressionOLD
:
{ before(grammarAccess.getFunctionTypeExpressionOLDRule()); }
	 ruleFunctionTypeExpressionOLD
{ after(grammarAccess.getFunctionTypeExpressionOLDRule()); } 
	 EOF 
;

// Rule FunctionTypeExpressionOLD
ruleFunctionTypeExpressionOLD 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getGroup()); }
		(rule__FunctionTypeExpressionOLD__Group__0)
		{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleArrowFunctionTypeExpression
entryRuleArrowFunctionTypeExpression
:
{ before(grammarAccess.getArrowFunctionTypeExpressionRule()); }
	 ruleArrowFunctionTypeExpression
{ after(grammarAccess.getArrowFunctionTypeExpressionRule()); } 
	 EOF 
;

// Rule ArrowFunctionTypeExpression
ruleArrowFunctionTypeExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getArrowFunctionTypeExpressionAccess().getGroup()); }
		(rule__ArrowFunctionTypeExpression__Group__0)
		{ after(grammarAccess.getArrowFunctionTypeExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule TAnonymousFormalParameterList
ruleTAnonymousFormalParameterList 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTAnonymousFormalParameterListAccess().getGroup()); }
		(rule__TAnonymousFormalParameterList__Group__0)?
		{ after(grammarAccess.getTAnonymousFormalParameterListAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTAnonymousFormalParameter
entryRuleTAnonymousFormalParameter
:
{ before(grammarAccess.getTAnonymousFormalParameterRule()); }
	 ruleTAnonymousFormalParameter
{ after(grammarAccess.getTAnonymousFormalParameterRule()); } 
	 EOF 
;

// Rule TAnonymousFormalParameter
ruleTAnonymousFormalParameter 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTAnonymousFormalParameterAccess().getGroup()); }
		(rule__TAnonymousFormalParameter__Group__0)
		{ after(grammarAccess.getTAnonymousFormalParameterAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTFormalParameter
entryRuleTFormalParameter
:
{ before(grammarAccess.getTFormalParameterRule()); }
	 ruleTFormalParameter
{ after(grammarAccess.getTFormalParameterRule()); } 
	 EOF 
;

// Rule TFormalParameter
ruleTFormalParameter 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTFormalParameterAccess().getGroup()); }
		(rule__TFormalParameter__Group__0)
		{ after(grammarAccess.getTFormalParameterAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule DefaultFormalParameter
ruleDefaultFormalParameter 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getDefaultFormalParameterAccess().getGroup()); }
		(rule__DefaultFormalParameter__Group__0)?
		{ after(grammarAccess.getDefaultFormalParameterAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleUnionTypeExpressionOLD
entryRuleUnionTypeExpressionOLD
:
{ before(grammarAccess.getUnionTypeExpressionOLDRule()); }
	 ruleUnionTypeExpressionOLD
{ after(grammarAccess.getUnionTypeExpressionOLDRule()); } 
	 EOF 
;

// Rule UnionTypeExpressionOLD
ruleUnionTypeExpressionOLD 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getUnionTypeExpressionOLDAccess().getGroup()); }
		(rule__UnionTypeExpressionOLD__Group__0)
		{ after(grammarAccess.getUnionTypeExpressionOLDAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleIntersectionTypeExpressionOLD
entryRuleIntersectionTypeExpressionOLD
:
{ before(grammarAccess.getIntersectionTypeExpressionOLDRule()); }
	 ruleIntersectionTypeExpressionOLD
{ after(grammarAccess.getIntersectionTypeExpressionOLDRule()); } 
	 EOF 
;

// Rule IntersectionTypeExpressionOLD
ruleIntersectionTypeExpressionOLD 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getIntersectionTypeExpressionOLDAccess().getGroup()); }
		(rule__IntersectionTypeExpressionOLD__Group__0)
		{ after(grammarAccess.getIntersectionTypeExpressionOLDAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleParameterizedTypeRef
entryRuleParameterizedTypeRef
:
{ before(grammarAccess.getParameterizedTypeRefRule()); }
	 ruleParameterizedTypeRef
{ after(grammarAccess.getParameterizedTypeRefRule()); } 
	 EOF 
;

// Rule ParameterizedTypeRef
ruleParameterizedTypeRef 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getParameterizedTypeRefAccess().getAlternatives()); }
		(rule__ParameterizedTypeRef__Alternatives)
		{ after(grammarAccess.getParameterizedTypeRefAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleParameterizedTypeRefNominal
entryRuleParameterizedTypeRefNominal
:
{ before(grammarAccess.getParameterizedTypeRefNominalRule()); }
	 ruleParameterizedTypeRefNominal
{ after(grammarAccess.getParameterizedTypeRefNominalRule()); } 
	 EOF 
;

// Rule ParameterizedTypeRefNominal
ruleParameterizedTypeRefNominal 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getParameterizedTypeRefNominalAccess().getGroup()); }
		(rule__ParameterizedTypeRefNominal__Group__0)
		{ after(grammarAccess.getParameterizedTypeRefNominalAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleParameterizedTypeRefStructural
entryRuleParameterizedTypeRefStructural
:
{ before(grammarAccess.getParameterizedTypeRefStructuralRule()); }
	 ruleParameterizedTypeRefStructural
{ after(grammarAccess.getParameterizedTypeRefStructuralRule()); } 
	 EOF 
;

// Rule ParameterizedTypeRefStructural
ruleParameterizedTypeRefStructural 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup()); }
		(rule__ParameterizedTypeRefStructural__Group__0)
		{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleIterableTypeExpression
entryRuleIterableTypeExpression
:
{ before(grammarAccess.getIterableTypeExpressionRule()); }
	 ruleIterableTypeExpression
{ after(grammarAccess.getIterableTypeExpressionRule()); } 
	 EOF 
;

// Rule IterableTypeExpression
ruleIterableTypeExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getIterableTypeExpressionAccess().getGroup()); }
		(rule__IterableTypeExpression__Group__0)
		{ after(grammarAccess.getIterableTypeExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEmptyIterableTypeExpressionTail
entryRuleEmptyIterableTypeExpressionTail
:
{ before(grammarAccess.getEmptyIterableTypeExpressionTailRule()); }
	 ruleEmptyIterableTypeExpressionTail
{ after(grammarAccess.getEmptyIterableTypeExpressionTailRule()); } 
	 EOF 
;

// Rule EmptyIterableTypeExpressionTail
ruleEmptyIterableTypeExpressionTail 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEmptyIterableTypeExpressionTailAccess().getGroup()); }
		(rule__EmptyIterableTypeExpressionTail__Group__0)
		{ after(grammarAccess.getEmptyIterableTypeExpressionTailAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule VersionRequest
ruleVersionRequest 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getVersionRequestAccess().getRequestedVersionAssignment()); }
		(rule__VersionRequest__RequestedVersionAssignment)
		{ after(grammarAccess.getVersionRequestAccess().getRequestedVersionAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule TypeReference
ruleTypeReference 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeReferenceAccess().getDeclaredTypeAssignment()); }
		(rule__TypeReference__DeclaredTypeAssignment)
		{ after(grammarAccess.getTypeReferenceAccess().getDeclaredTypeAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule TypeArguments
ruleTypeArguments 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeArgumentsAccess().getGroup()); }
		(rule__TypeArguments__Group__0)
		{ after(grammarAccess.getTypeArgumentsAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule TStructMemberList
ruleTStructMemberList 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTStructMemberListAccess().getGroup()); }
		(rule__TStructMemberList__Group__0)
		{ after(grammarAccess.getTStructMemberListAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTStructMember
entryRuleTStructMember
:
{ before(grammarAccess.getTStructMemberRule()); }
	 ruleTStructMember
{ after(grammarAccess.getTStructMemberRule()); } 
	 EOF 
;

// Rule TStructMember
ruleTStructMember 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTStructMemberAccess().getAlternatives()); }
		(rule__TStructMember__Alternatives)
		{ after(grammarAccess.getTStructMemberAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTStructMethod
entryRuleTStructMethod
:
{ before(grammarAccess.getTStructMethodRule()); }
	 ruleTStructMethod
{ after(grammarAccess.getTStructMethodRule()); } 
	 EOF 
;

// Rule TStructMethod
ruleTStructMethod 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTStructMethodAccess().getGroup()); }
		(rule__TStructMethod__Group__0)
		{ after(grammarAccess.getTStructMethodAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule TypeVariables
ruleTypeVariables 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeVariablesAccess().getGroup()); }
		(rule__TypeVariables__Group__0)
		{ after(grammarAccess.getTypeVariablesAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule ColonSepDeclaredTypeRef
ruleColonSepDeclaredTypeRef 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getColonSepDeclaredTypeRefAccess().getGroup()); }
		(rule__ColonSepDeclaredTypeRef__Group__0)
		{ after(grammarAccess.getColonSepDeclaredTypeRefAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule ColonSepTypeRef
ruleColonSepTypeRef 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getColonSepTypeRefAccess().getGroup()); }
		(rule__ColonSepTypeRef__Group__0)
		{ after(grammarAccess.getColonSepTypeRefAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule ColonSepReturnTypeRef
ruleColonSepReturnTypeRef 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getColonSepReturnTypeRefAccess().getGroup()); }
		(rule__ColonSepReturnTypeRef__Group__0)
		{ after(grammarAccess.getColonSepReturnTypeRefAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTStructField
entryRuleTStructField
:
{ before(grammarAccess.getTStructFieldRule()); }
	 ruleTStructField
{ after(grammarAccess.getTStructFieldRule()); } 
	 EOF 
;

// Rule TStructField
ruleTStructField 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTStructFieldAccess().getGroup()); }
		(rule__TStructField__Group__0)
		{ after(grammarAccess.getTStructFieldAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTStructGetter
entryRuleTStructGetter
:
{ before(grammarAccess.getTStructGetterRule()); }
	 ruleTStructGetter
{ after(grammarAccess.getTStructGetterRule()); } 
	 EOF 
;

// Rule TStructGetter
ruleTStructGetter 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTStructGetterAccess().getGroup()); }
		(rule__TStructGetter__Group__0)
		{ after(grammarAccess.getTStructGetterAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTStructSetter
entryRuleTStructSetter
:
{ before(grammarAccess.getTStructSetterRule()); }
	 ruleTStructSetter
{ after(grammarAccess.getTStructSetterRule()); } 
	 EOF 
;

// Rule TStructSetter
ruleTStructSetter 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTStructSetterAccess().getGroup()); }
		(rule__TStructSetter__Group__0)
		{ after(grammarAccess.getTStructSetterAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypingStrategyUseSiteOperator
entryRuleTypingStrategyUseSiteOperator
:
{ before(grammarAccess.getTypingStrategyUseSiteOperatorRule()); }
	 ruleTypingStrategyUseSiteOperator
{ after(grammarAccess.getTypingStrategyUseSiteOperatorRule()); } 
	 EOF 
;

// Rule TypingStrategyUseSiteOperator
ruleTypingStrategyUseSiteOperator 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getGroup()); }
		(rule__TypingStrategyUseSiteOperator__Group__0)
		{ after(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypingStrategyDefSiteOperator
entryRuleTypingStrategyDefSiteOperator
:
{ before(grammarAccess.getTypingStrategyDefSiteOperatorRule()); }
	 ruleTypingStrategyDefSiteOperator
{ after(grammarAccess.getTypingStrategyDefSiteOperatorRule()); } 
	 EOF 
;

// Rule TypingStrategyDefSiteOperator
ruleTypingStrategyDefSiteOperator 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypingStrategyDefSiteOperatorAccess().getTildeKeyword()); }
		Tilde
		{ after(grammarAccess.getTypingStrategyDefSiteOperatorAccess().getTildeKeyword()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypeTypeRef
entryRuleTypeTypeRef
:
{ before(grammarAccess.getTypeTypeRefRule()); }
	 ruleTypeTypeRef
{ after(grammarAccess.getTypeTypeRefRule()); } 
	 EOF 
;

// Rule TypeTypeRef
ruleTypeTypeRef 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeTypeRefAccess().getGroup()); }
		(rule__TypeTypeRef__Group__0)
		{ after(grammarAccess.getTypeTypeRefAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypeArgument
entryRuleTypeArgument
:
{ before(grammarAccess.getTypeArgumentRule()); }
	 ruleTypeArgument
{ after(grammarAccess.getTypeArgumentRule()); } 
	 EOF 
;

// Rule TypeArgument
ruleTypeArgument 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeArgumentAccess().getAlternatives()); }
		(rule__TypeArgument__Alternatives)
		{ after(grammarAccess.getTypeArgumentAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleWildcard
entryRuleWildcard
:
{ before(grammarAccess.getWildcardRule()); }
	 ruleWildcard
{ after(grammarAccess.getWildcardRule()); } 
	 EOF 
;

// Rule Wildcard
ruleWildcard 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getWildcardAccess().getAlternatives()); }
		(rule__Wildcard__Alternatives)
		{ after(grammarAccess.getWildcardAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleWildcardOldNotation
entryRuleWildcardOldNotation
:
{ before(grammarAccess.getWildcardOldNotationRule()); }
	 ruleWildcardOldNotation
{ after(grammarAccess.getWildcardOldNotationRule()); } 
	 EOF 
;

// Rule WildcardOldNotation
ruleWildcardOldNotation 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getWildcardOldNotationAccess().getGroup()); }
		(rule__WildcardOldNotation__Group__0)
		{ after(grammarAccess.getWildcardOldNotationAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleWildcardOldNotationWithoutBound
entryRuleWildcardOldNotationWithoutBound
:
{ before(grammarAccess.getWildcardOldNotationWithoutBoundRule()); }
	 ruleWildcardOldNotationWithoutBound
{ after(grammarAccess.getWildcardOldNotationWithoutBoundRule()); } 
	 EOF 
;

// Rule WildcardOldNotationWithoutBound
ruleWildcardOldNotationWithoutBound 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getWildcardOldNotationWithoutBoundAccess().getGroup()); }
		(rule__WildcardOldNotationWithoutBound__Group__0)
		{ after(grammarAccess.getWildcardOldNotationWithoutBoundAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleWildcardNewNotation
entryRuleWildcardNewNotation
:
{ before(grammarAccess.getWildcardNewNotationRule()); }
	 ruleWildcardNewNotation
{ after(grammarAccess.getWildcardNewNotationRule()); } 
	 EOF 
;

// Rule WildcardNewNotation
ruleWildcardNewNotation 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getWildcardNewNotationAccess().getAlternatives()); }
		(rule__WildcardNewNotation__Alternatives)
		{ after(grammarAccess.getWildcardNewNotationAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entrySuperTypeVariable
entrySuperTypeVariable
:
{ before(grammarAccess.getTypeExpressionsTypeVariableRule()); }
	 superTypeVariable
{ after(grammarAccess.getTypeExpressionsTypeVariableRule()); } 
	 EOF 
;

// Rule TypeVariable
superTypeVariable 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getGroup()); }
		(superTypeVariable__Group__0)
		{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleBindingIdentifier
entryRuleBindingIdentifier
:
{ before(grammarAccess.getBindingIdentifierRule()); }
	 ruleBindingIdentifier
{ after(grammarAccess.getBindingIdentifierRule()); } 
	 EOF 
;

// Rule BindingIdentifier
ruleBindingIdentifier 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getBindingIdentifierAccess().getAlternatives(), 0
		); }
		(rule__BindingIdentifier__Alternatives)
		{ after(grammarAccess.getBindingIdentifierAccess().getAlternatives(), 0
		); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleIdentifierName
entryRuleIdentifierName
:
{ before(grammarAccess.getIdentifierNameRule()); }
	 ruleIdentifierName
{ after(grammarAccess.getIdentifierNameRule()); } 
	 EOF 
;

// Rule IdentifierName
ruleIdentifierName 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getIdentifierNameAccess().getAlternatives()); }
		(rule__IdentifierName__Alternatives)
		{ after(grammarAccess.getIdentifierNameAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleReservedWord
entryRuleReservedWord
:
{ before(grammarAccess.getReservedWordRule()); }
	 ruleReservedWord
{ after(grammarAccess.getReservedWordRule()); } 
	 EOF 
;

// Rule ReservedWord
ruleReservedWord 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getReservedWordAccess().getAlternatives()); }
		(rule__ReservedWord__Alternatives)
		{ after(grammarAccess.getReservedWordAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleN4Keyword
entryRuleN4Keyword
:
{ before(grammarAccess.getN4KeywordRule()); }
	 ruleN4Keyword
{ after(grammarAccess.getN4KeywordRule()); } 
	 EOF 
;

// Rule N4Keyword
ruleN4Keyword 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getN4KeywordAccess().getAlternatives()); }
		(rule__N4Keyword__Alternatives)
		{ after(grammarAccess.getN4KeywordAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule TypeAccessModifier
ruleTypeAccessModifier
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeAccessModifierAccess().getAlternatives()); }
		(rule__TypeAccessModifier__Alternatives)
		{ after(grammarAccess.getTypeAccessModifierAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule MemberAccessModifier
ruleMemberAccessModifier
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getMemberAccessModifierAccess().getAlternatives()); }
		(rule__MemberAccessModifier__Alternatives)
		{ after(grammarAccess.getMemberAccessModifierAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotationArgument__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTAnnotationArgumentAccess().getTAnnotationStringArgumentParserRuleCall_0()); }
		ruleTAnnotationStringArgument
		{ after(grammarAccess.getTAnnotationArgumentAccess().getTAnnotationStringArgumentParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getTAnnotationArgumentAccess().getTAnnotationTypeRefArgumentParserRuleCall_1()); }
		ruleTAnnotationTypeRefArgument
		{ after(grammarAccess.getTAnnotationArgumentAccess().getTAnnotationTypeRefArgumentParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Type__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeAccess().getTObjectPrototypeParserRuleCall_0()); }
		ruleTObjectPrototype
		{ after(grammarAccess.getTypeAccess().getTObjectPrototypeParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getTClassParserRuleCall_1()); }
		ruleTClass
		{ after(grammarAccess.getTypeAccess().getTClassParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getTInterfaceParserRuleCall_2()); }
		ruleTInterface
		{ after(grammarAccess.getTypeAccess().getTInterfaceParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getTEnumParserRuleCall_3()); }
		ruleTEnum
		{ after(grammarAccess.getTypeAccess().getTEnumParserRuleCall_3()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getAnyTypeParserRuleCall_4()); }
		ruleAnyType
		{ after(grammarAccess.getTypeAccess().getAnyTypeParserRuleCall_4()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getVoidTypeParserRuleCall_5()); }
		ruleVoidType
		{ after(grammarAccess.getTypeAccess().getVoidTypeParserRuleCall_5()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getUndefinedTypeParserRuleCall_6()); }
		ruleUndefinedType
		{ after(grammarAccess.getTypeAccess().getUndefinedTypeParserRuleCall_6()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getNullTypeParserRuleCall_7()); }
		ruleNullType
		{ after(grammarAccess.getTypeAccess().getNullTypeParserRuleCall_7()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getPrimitiveTypeParserRuleCall_8()); }
		rulePrimitiveType
		{ after(grammarAccess.getTypeAccess().getPrimitiveTypeParserRuleCall_8()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getTFunctionParserRuleCall_9()); }
		ruleTFunction
		{ after(grammarAccess.getTypeAccess().getTFunctionParserRuleCall_9()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getTypeVariableParserRuleCall_10()); }
		ruleTypeVariable
		{ after(grammarAccess.getTypeAccess().getTypeVariableParserRuleCall_10()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getVirtualBaseTypeParserRuleCall_11()); }
		ruleVirtualBaseType
		{ after(grammarAccess.getTypeAccess().getVirtualBaseTypeParserRuleCall_11()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeReferenceName__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeReferenceNameAccess().getVoidKeyword_0()); }
		Void
		{ after(grammarAccess.getTypeReferenceNameAccess().getVoidKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypeReferenceNameAccess().getAnyKeyword_1()); }
		Any
		{ after(grammarAccess.getTypeReferenceNameAccess().getAnyKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getTypeReferenceNameAccess().getUndefinedKeyword_2()); }
		Undefined
		{ after(grammarAccess.getTypeReferenceNameAccess().getUndefinedKeyword_2()); }
	)
	|
	(
		{ before(grammarAccess.getTypeReferenceNameAccess().getNullKeyword_3()); }
		Null
		{ after(grammarAccess.getTypeReferenceNameAccess().getNullKeyword_3()); }
	)
	|
	(
		{ before(grammarAccess.getTypeReferenceNameAccess().getIndexedKeyword_4()); }
		Indexed
		{ after(grammarAccess.getTypeReferenceNameAccess().getIndexedKeyword_4()); }
	)
	|
	(
		{ before(grammarAccess.getTypeReferenceNameAccess().getGroup_5()); }
		(rule__TypeReferenceName__Group_5__0)
		{ after(grammarAccess.getTypeReferenceNameAccess().getGroup_5()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypesIdentifier__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypesIdentifierAccess().getTypesSpecificKeywordsParserRuleCall_0()); }
		ruleTypesSpecificKeywords
		{ after(grammarAccess.getTypesIdentifierAccess().getTypesSpecificKeywordsParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypesIdentifierAccess().getIdentifierNameParserRuleCall_1()); }
		ruleIdentifierName
		{ after(grammarAccess.getTypesIdentifierAccess().getIdentifierNameParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__BindingTypesIdentifier__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getBindingTypesIdentifierAccess().getTypesSpecificKeywordsParserRuleCall_0()); }
		ruleTypesSpecificKeywords
		{ after(grammarAccess.getBindingTypesIdentifierAccess().getTypesSpecificKeywordsParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getBindingTypesIdentifierAccess().getBindingIdentifierParserRuleCall_1()); }
		ruleBindingIdentifier
		{ after(grammarAccess.getBindingTypesIdentifierAccess().getBindingIdentifierParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VoidOrBindingIdentifier__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVoidOrBindingIdentifierAccess().getVoidKeyword_0()); }
		Void
		{ after(grammarAccess.getVoidOrBindingIdentifierAccess().getVoidKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getVoidOrBindingIdentifierAccess().getBindingTypesIdentifierParserRuleCall_1()); }
		ruleBindingTypesIdentifier
		{ after(grammarAccess.getVoidOrBindingIdentifierAccess().getBindingTypesIdentifierParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypesSpecificKeywords__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypesSpecificKeywordsAccess().getAnyKeyword_0()); }
		Any
		{ after(grammarAccess.getTypesSpecificKeywordsAccess().getAnyKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypesSpecificKeywordsAccess().getUndefinedKeyword_1()); }
		Undefined
		{ after(grammarAccess.getTypesSpecificKeywordsAccess().getUndefinedKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getTypesSpecificKeywordsAccess().getObjectKeyword_2()); }
		Object
		{ after(grammarAccess.getTypesSpecificKeywordsAccess().getObjectKeyword_2()); }
	)
	|
	(
		{ before(grammarAccess.getTypesSpecificKeywordsAccess().getVirtualBaseKeyword_3()); }
		VirtualBase
		{ after(grammarAccess.getTypesSpecificKeywordsAccess().getVirtualBaseKeyword_3()); }
	)
	|
	(
		{ before(grammarAccess.getTypesSpecificKeywordsAccess().getPrimitiveKeyword_4()); }
		Primitive
		{ after(grammarAccess.getTypesSpecificKeywordsAccess().getPrimitiveKeyword_4()); }
	)
	|
	(
		{ before(grammarAccess.getTypesSpecificKeywordsAccess().getAutoboxedTypeKeyword_5()); }
		AutoboxedType
		{ after(grammarAccess.getTypesSpecificKeywordsAccess().getAutoboxedTypeKeyword_5()); }
	)
	|
	(
		{ before(grammarAccess.getTypesSpecificKeywordsAccess().getAssignmnentCompatibleKeyword_6()); }
		AssignmnentCompatible
		{ after(grammarAccess.getTypesSpecificKeywordsAccess().getAssignmnentCompatibleKeyword_6()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypesComputedPropertyName__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypesComputedPropertyNameAccess().getTypesSymbolLiteralComputedNameParserRuleCall_1_0()); }
		ruleTypesSymbolLiteralComputedName
		{ after(grammarAccess.getTypesComputedPropertyNameAccess().getTypesSymbolLiteralComputedNameParserRuleCall_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypesComputedPropertyNameAccess().getTypesStringLiteralComputedNameParserRuleCall_1_1()); }
		ruleTypesStringLiteralComputedName
		{ after(grammarAccess.getTypesComputedPropertyNameAccess().getTypesStringLiteralComputedNameParserRuleCall_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMember__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTMemberAccess().getTGetterParserRuleCall_0()); }
		(ruleTGetter)
		{ after(grammarAccess.getTMemberAccess().getTGetterParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getTMemberAccess().getTSetterParserRuleCall_1()); }
		(ruleTSetter)
		{ after(grammarAccess.getTMemberAccess().getTSetterParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getTMemberAccess().getTMethodParserRuleCall_2()); }
		(ruleTMethod)
		{ after(grammarAccess.getTMemberAccess().getTMethodParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getTMemberAccess().getTFieldParserRuleCall_3()); }
		ruleTField
		{ after(grammarAccess.getTMemberAccess().getTFieldParserRuleCall_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Alternatives_0_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTMethodAccess().getDeclaredAbstractAssignment_0_0_1_0()); }
		(rule__TMethod__DeclaredAbstractAssignment_0_0_1_0)
		{ after(grammarAccess.getTMethodAccess().getDeclaredAbstractAssignment_0_0_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getTMethodAccess().getDeclaredStaticAssignment_0_0_1_1()); }
		(rule__TMethod__DeclaredStaticAssignment_0_0_1_1)
		{ after(grammarAccess.getTMethodAccess().getDeclaredStaticAssignment_0_0_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Alternatives_0_0_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTMethodAccess().getNameAssignment_0_0_3_0()); }
		(rule__TMethod__NameAssignment_0_0_3_0)
		{ after(grammarAccess.getTMethodAccess().getNameAssignment_0_0_3_0()); }
	)
	|
	(
		{ before(grammarAccess.getTMethodAccess().getNameAssignment_0_0_3_1()); }
		(rule__TMethod__NameAssignment_0_0_3_1)
		{ after(grammarAccess.getTMethodAccess().getNameAssignment_0_0_3_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFieldAccess().getDeclaredStaticAssignment_1_0()); }
		(rule__TField__DeclaredStaticAssignment_1_0)
		{ after(grammarAccess.getTFieldAccess().getDeclaredStaticAssignment_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getTFieldAccess().getConstAssignment_1_1()); }
		(rule__TField__ConstAssignment_1_1)
		{ after(grammarAccess.getTFieldAccess().getConstAssignment_1_1()); }
	)
	|
	(
		{ before(grammarAccess.getTFieldAccess().getDeclaredFinalAssignment_1_2()); }
		(rule__TField__DeclaredFinalAssignment_1_2)
		{ after(grammarAccess.getTFieldAccess().getDeclaredFinalAssignment_1_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__Alternatives_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFieldAccess().getNameAssignment_2_0()); }
		(rule__TField__NameAssignment_2_0)
		{ after(grammarAccess.getTFieldAccess().getNameAssignment_2_0()); }
	)
	|
	(
		{ before(grammarAccess.getTFieldAccess().getNameAssignment_2_1()); }
		(rule__TField__NameAssignment_2_1)
		{ after(grammarAccess.getTFieldAccess().getNameAssignment_2_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Alternatives_0_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTGetterAccess().getDeclaredAbstractAssignment_0_0_1_0()); }
		(rule__TGetter__DeclaredAbstractAssignment_0_0_1_0)
		{ after(grammarAccess.getTGetterAccess().getDeclaredAbstractAssignment_0_0_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getTGetterAccess().getDeclaredStaticAssignment_0_0_1_1()); }
		(rule__TGetter__DeclaredStaticAssignment_0_0_1_1)
		{ after(grammarAccess.getTGetterAccess().getDeclaredStaticAssignment_0_0_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Alternatives_0_0_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTGetterAccess().getNameAssignment_0_0_3_0()); }
		(rule__TGetter__NameAssignment_0_0_3_0)
		{ after(grammarAccess.getTGetterAccess().getNameAssignment_0_0_3_0()); }
	)
	|
	(
		{ before(grammarAccess.getTGetterAccess().getNameAssignment_0_0_3_1()); }
		(rule__TGetter__NameAssignment_0_0_3_1)
		{ after(grammarAccess.getTGetterAccess().getNameAssignment_0_0_3_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Alternatives_0_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTSetterAccess().getDeclaredAbstractAssignment_0_0_1_0()); }
		(rule__TSetter__DeclaredAbstractAssignment_0_0_1_0)
		{ after(grammarAccess.getTSetterAccess().getDeclaredAbstractAssignment_0_0_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getTSetterAccess().getDeclaredStaticAssignment_0_0_1_1()); }
		(rule__TSetter__DeclaredStaticAssignment_0_0_1_1)
		{ after(grammarAccess.getTSetterAccess().getDeclaredStaticAssignment_0_0_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Alternatives_0_0_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTSetterAccess().getNameAssignment_0_0_3_0()); }
		(rule__TSetter__NameAssignment_0_0_3_0)
		{ after(grammarAccess.getTSetterAccess().getNameAssignment_0_0_3_0()); }
	)
	|
	(
		{ before(grammarAccess.getTSetterAccess().getNameAssignment_0_0_3_1()); }
		(rule__TSetter__NameAssignment_0_0_3_1)
		{ after(grammarAccess.getTSetterAccess().getNameAssignment_0_0_3_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getArrayTypeExpressionAccess().getGroup_0()); }
		(rule__ArrayTypeExpression__Group_0__0)
		{ after(grammarAccess.getArrayTypeExpressionAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getArrayTypeExpressionAccess().getGroup_1()); }
		(rule__ArrayTypeExpression__Group_1__0)
		{ after(grammarAccess.getArrayTypeExpressionAccess().getGroup_1()); }
	)
	|
	(
		{ before(grammarAccess.getArrayTypeExpressionAccess().getGroup_2()); }
		(rule__ArrayTypeExpression__Group_2__0)
		{ after(grammarAccess.getArrayTypeExpressionAccess().getGroup_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimaryTypeExpression__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPrimaryTypeExpressionAccess().getArrowFunctionTypeExpressionParserRuleCall_0()); }
		(ruleArrowFunctionTypeExpression)
		{ after(grammarAccess.getPrimaryTypeExpressionAccess().getArrowFunctionTypeExpressionParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getPrimaryTypeExpressionAccess().getIterableTypeExpressionParserRuleCall_1()); }
		ruleIterableTypeExpression
		{ after(grammarAccess.getPrimaryTypeExpressionAccess().getIterableTypeExpressionParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getPrimaryTypeExpressionAccess().getTypeRefWithModifiersParserRuleCall_2()); }
		ruleTypeRefWithModifiers
		{ after(grammarAccess.getPrimaryTypeExpressionAccess().getTypeRefWithModifiersParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getPrimaryTypeExpressionAccess().getGroup_3()); }
		(rule__PrimaryTypeExpression__Group_3__0)
		{ after(grammarAccess.getPrimaryTypeExpressionAccess().getGroup_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRefWithoutModifiers__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeRefWithoutModifiersAccess().getGroup_0()); }
		(rule__TypeRefWithoutModifiers__Group_0__0)
		{ after(grammarAccess.getTypeRefWithoutModifiersAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypeRefWithoutModifiersAccess().getTypeTypeRefParserRuleCall_1()); }
		ruleTypeTypeRef
		{ after(grammarAccess.getTypeRefWithoutModifiersAccess().getTypeTypeRefParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getTypeRefWithoutModifiersAccess().getFunctionTypeExpressionOLDParserRuleCall_2()); }
		ruleFunctionTypeExpressionOLD
		{ after(grammarAccess.getTypeRefWithoutModifiersAccess().getFunctionTypeExpressionOLDParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getTypeRefWithoutModifiersAccess().getUnionTypeExpressionOLDParserRuleCall_3()); }
		ruleUnionTypeExpressionOLD
		{ after(grammarAccess.getTypeRefWithoutModifiersAccess().getUnionTypeExpressionOLDParserRuleCall_3()); }
	)
	|
	(
		{ before(grammarAccess.getTypeRefWithoutModifiersAccess().getIntersectionTypeExpressionOLDParserRuleCall_4()); }
		ruleIntersectionTypeExpressionOLD
		{ after(grammarAccess.getTypeRefWithoutModifiersAccess().getIntersectionTypeExpressionOLDParserRuleCall_4()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRefWithoutModifiers__Alternatives_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeRefWithoutModifiersAccess().getParameterizedTypeRefParserRuleCall_0_0_0()); }
		ruleParameterizedTypeRef
		{ after(grammarAccess.getTypeRefWithoutModifiersAccess().getParameterizedTypeRefParserRuleCall_0_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypeRefWithoutModifiersAccess().getThisTypeRefParserRuleCall_0_0_1()); }
		ruleThisTypeRef
		{ after(grammarAccess.getTypeRefWithoutModifiersAccess().getThisTypeRefParserRuleCall_0_0_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRefFunctionTypeExpression__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getParameterizedTypeRefParserRuleCall_0()); }
		ruleParameterizedTypeRef
		{ after(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getParameterizedTypeRefParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getIterableTypeExpressionParserRuleCall_1()); }
		ruleIterableTypeExpression
		{ after(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getIterableTypeExpressionParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getTypeTypeRefParserRuleCall_2()); }
		ruleTypeTypeRef
		{ after(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getTypeTypeRefParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getUnionTypeExpressionOLDParserRuleCall_3()); }
		ruleUnionTypeExpressionOLD
		{ after(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getUnionTypeExpressionOLDParserRuleCall_3()); }
	)
	|
	(
		{ before(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getIntersectionTypeExpressionOLDParserRuleCall_4()); }
		ruleIntersectionTypeExpressionOLD
		{ after(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getIntersectionTypeExpressionOLDParserRuleCall_4()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArgInTypeTypeRef__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeArgInTypeTypeRefAccess().getParameterizedTypeRefNominalParserRuleCall_0()); }
		ruleParameterizedTypeRefNominal
		{ after(grammarAccess.getTypeArgInTypeTypeRefAccess().getParameterizedTypeRefNominalParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypeArgInTypeTypeRefAccess().getThisTypeRefNominalParserRuleCall_1()); }
		ruleThisTypeRefNominal
		{ after(grammarAccess.getTypeArgInTypeTypeRefAccess().getThisTypeRefNominalParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getTypeArgInTypeTypeRefAccess().getWildcardOldNotationParserRuleCall_2()); }
		(ruleWildcardOldNotation)
		{ after(grammarAccess.getTypeArgInTypeTypeRefAccess().getWildcardOldNotationParserRuleCall_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ThisTypeRef__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThisTypeRefAccess().getThisTypeRefNominalParserRuleCall_0()); }
		ruleThisTypeRefNominal
		{ after(grammarAccess.getThisTypeRefAccess().getThisTypeRefNominalParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getThisTypeRefAccess().getThisTypeRefStructuralParserRuleCall_1()); }
		ruleThisTypeRefStructural
		{ after(grammarAccess.getThisTypeRefAccess().getThisTypeRefStructuralParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameter__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTAnonymousFormalParameterAccess().getGroup_1_0()); }
		(rule__TAnonymousFormalParameter__Group_1_0__0)
		{ after(grammarAccess.getTAnonymousFormalParameterAccess().getGroup_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getTAnonymousFormalParameterAccess().getTypeRefAssignment_1_1()); }
		(rule__TAnonymousFormalParameter__TypeRefAssignment_1_1)
		{ after(grammarAccess.getTAnonymousFormalParameterAccess().getTypeRefAssignment_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRef__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getParameterizedTypeRefAccess().getParameterizedTypeRefNominalParserRuleCall_0()); }
		ruleParameterizedTypeRefNominal
		{ after(grammarAccess.getParameterizedTypeRefAccess().getParameterizedTypeRefNominalParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getParameterizedTypeRefAccess().getParameterizedTypeRefStructuralParserRuleCall_1()); }
		ruleParameterizedTypeRefStructural
		{ after(grammarAccess.getParameterizedTypeRefAccess().getParameterizedTypeRefStructuralParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefNominal__Alternatives_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getParameterizedTypeRefNominalAccess().getTypeReferenceParserRuleCall_0_0()); }
		ruleTypeReference
		{ after(grammarAccess.getParameterizedTypeRefNominalAccess().getTypeReferenceParserRuleCall_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getParameterizedTypeRefNominalAccess().getGroup_0_1()); }
		(rule__ParameterizedTypeRefNominal__Group_0_1__0)
		{ after(grammarAccess.getParameterizedTypeRefNominalAccess().getGroup_0_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Alternatives_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup_0_0()); }
		(rule__ParameterizedTypeRefStructural__Group_0_0__0)
		{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup_0_1()); }
		(rule__ParameterizedTypeRefStructural__Group_0_1__0)
		{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup_0_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsAssignment_1_0()); }
		(rule__IterableTypeExpression__TypeArgsAssignment_1_0)
		{ after(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsAssignment_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getIterableTypeExpressionAccess().getGroup_1_1()); }
		(rule__IterableTypeExpression__Group_1_1__0)
		{ after(grammarAccess.getIterableTypeExpressionAccess().getGroup_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMemberList__Alternatives_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTStructMemberListAccess().getSemicolonKeyword_1_1_0()); }
		Semicolon
		{ after(grammarAccess.getTStructMemberListAccess().getSemicolonKeyword_1_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getTStructMemberListAccess().getCommaKeyword_1_1_1()); }
		Comma
		{ after(grammarAccess.getTStructMemberListAccess().getCommaKeyword_1_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMember__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTStructMemberAccess().getTStructGetterParserRuleCall_0()); }
		(ruleTStructGetter)
		{ after(grammarAccess.getTStructMemberAccess().getTStructGetterParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getTStructMemberAccess().getTStructSetterParserRuleCall_1()); }
		(ruleTStructSetter)
		{ after(grammarAccess.getTStructMemberAccess().getTStructSetterParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getTStructMemberAccess().getTStructMethodParserRuleCall_2()); }
		(ruleTStructMethod)
		{ after(grammarAccess.getTStructMemberAccess().getTStructMethodParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getTStructMemberAccess().getTStructFieldParserRuleCall_3()); }
		ruleTStructField
		{ after(grammarAccess.getTStructMemberAccess().getTStructFieldParserRuleCall_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypingStrategyUseSiteOperator__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getTildeKeyword_1_0()); }
		Tilde
		{ after(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getTildeKeyword_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getSTRUCTMODSUFFIXTerminalRuleCall_1_1()); }
		RULE_STRUCTMODSUFFIX
		{ after(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getSTRUCTMODSUFFIXTerminalRuleCall_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeTypeRef__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeTypeRefAccess().getTypeKeyword_1_0()); }
		Type
		{ after(grammarAccess.getTypeTypeRefAccess().getTypeKeyword_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypeTypeRefAccess().getConstructorRefAssignment_1_1()); }
		(rule__TypeTypeRef__ConstructorRefAssignment_1_1)
		{ after(grammarAccess.getTypeTypeRefAccess().getConstructorRefAssignment_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArgument__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeArgumentAccess().getWildcardParserRuleCall_0()); }
		ruleWildcard
		{ after(grammarAccess.getTypeArgumentAccess().getWildcardParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypeArgumentAccess().getTypeRefParserRuleCall_1()); }
		ruleTypeRef
		{ after(grammarAccess.getTypeArgumentAccess().getTypeRefParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Wildcard__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWildcardAccess().getWildcardOldNotationParserRuleCall_0()); }
		(ruleWildcardOldNotation)
		{ after(grammarAccess.getWildcardAccess().getWildcardOldNotationParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getWildcardAccess().getWildcardNewNotationParserRuleCall_1()); }
		ruleWildcardNewNotation
		{ after(grammarAccess.getWildcardAccess().getWildcardNewNotationParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWildcardOldNotationAccess().getGroup_1_0()); }
		(rule__WildcardOldNotation__Group_1_0__0)
		{ after(grammarAccess.getWildcardOldNotationAccess().getGroup_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getWildcardOldNotationAccess().getGroup_1_1()); }
		(rule__WildcardOldNotation__Group_1_1__0)
		{ after(grammarAccess.getWildcardOldNotationAccess().getGroup_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardNewNotation__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWildcardNewNotationAccess().getGroup_0()); }
		(rule__WildcardNewNotation__Group_0__0)
		{ after(grammarAccess.getWildcardNewNotationAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getWildcardNewNotationAccess().getGroup_1()); }
		(rule__WildcardNewNotation__Group_1__0)
		{ after(grammarAccess.getWildcardNewNotationAccess().getGroup_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

superTypeVariable__Alternatives_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredCovariantAssignment_0_0()); }
		(superTypeVariable__DeclaredCovariantAssignment_0_0)
		{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredCovariantAssignment_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredContravariantAssignment_0_1()); }
		(superTypeVariable__DeclaredContravariantAssignment_0_1)
		{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredContravariantAssignment_0_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__BindingIdentifier__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getBindingIdentifierAccess().getIDENTIFIERTerminalRuleCall_0()); }
		RULE_IDENTIFIER
		{ after(grammarAccess.getBindingIdentifierAccess().getIDENTIFIERTerminalRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getBindingIdentifierAccess().getYieldKeyword_1_0()); }
		Yield
		{ after(grammarAccess.getBindingIdentifierAccess().getYieldKeyword_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getBindingIdentifierAccess().getN4KeywordParserRuleCall_2()); }
		ruleN4Keyword
		{ after(grammarAccess.getBindingIdentifierAccess().getN4KeywordParserRuleCall_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__IdentifierName__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIdentifierNameAccess().getIDENTIFIERTerminalRuleCall_0()); }
		RULE_IDENTIFIER
		{ after(grammarAccess.getIdentifierNameAccess().getIDENTIFIERTerminalRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getIdentifierNameAccess().getReservedWordParserRuleCall_1()); }
		ruleReservedWord
		{ after(grammarAccess.getIdentifierNameAccess().getReservedWordParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getIdentifierNameAccess().getN4KeywordParserRuleCall_2()); }
		ruleN4Keyword
		{ after(grammarAccess.getIdentifierNameAccess().getN4KeywordParserRuleCall_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ReservedWord__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getReservedWordAccess().getBreakKeyword_0()); }
		Break
		{ after(grammarAccess.getReservedWordAccess().getBreakKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getCaseKeyword_1()); }
		Case
		{ after(grammarAccess.getReservedWordAccess().getCaseKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getCatchKeyword_2()); }
		Catch
		{ after(grammarAccess.getReservedWordAccess().getCatchKeyword_2()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getClassKeyword_3()); }
		Class
		{ after(grammarAccess.getReservedWordAccess().getClassKeyword_3()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getConstKeyword_4()); }
		Const
		{ after(grammarAccess.getReservedWordAccess().getConstKeyword_4()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getContinueKeyword_5()); }
		Continue
		{ after(grammarAccess.getReservedWordAccess().getContinueKeyword_5()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getDebuggerKeyword_6()); }
		Debugger
		{ after(grammarAccess.getReservedWordAccess().getDebuggerKeyword_6()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getDefaultKeyword_7()); }
		Default
		{ after(grammarAccess.getReservedWordAccess().getDefaultKeyword_7()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getDeleteKeyword_8()); }
		Delete
		{ after(grammarAccess.getReservedWordAccess().getDeleteKeyword_8()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getDoKeyword_9()); }
		Do
		{ after(grammarAccess.getReservedWordAccess().getDoKeyword_9()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getElseKeyword_10()); }
		Else
		{ after(grammarAccess.getReservedWordAccess().getElseKeyword_10()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getExportKeyword_11()); }
		Export
		{ after(grammarAccess.getReservedWordAccess().getExportKeyword_11()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getExtendsKeyword_12()); }
		Extends
		{ after(grammarAccess.getReservedWordAccess().getExtendsKeyword_12()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getFinallyKeyword_13()); }
		Finally
		{ after(grammarAccess.getReservedWordAccess().getFinallyKeyword_13()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getForKeyword_14()); }
		For
		{ after(grammarAccess.getReservedWordAccess().getForKeyword_14()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getFunctionKeyword_15()); }
		Function
		{ after(grammarAccess.getReservedWordAccess().getFunctionKeyword_15()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getIfKeyword_16()); }
		If
		{ after(grammarAccess.getReservedWordAccess().getIfKeyword_16()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getImportKeyword_17()); }
		Import
		{ after(grammarAccess.getReservedWordAccess().getImportKeyword_17()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getInKeyword_18()); }
		In
		{ after(grammarAccess.getReservedWordAccess().getInKeyword_18()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getInstanceofKeyword_19()); }
		Instanceof
		{ after(grammarAccess.getReservedWordAccess().getInstanceofKeyword_19()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getNewKeyword_20()); }
		New
		{ after(grammarAccess.getReservedWordAccess().getNewKeyword_20()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getReturnKeyword_21()); }
		Return
		{ after(grammarAccess.getReservedWordAccess().getReturnKeyword_21()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getSuperKeyword_22()); }
		Super
		{ after(grammarAccess.getReservedWordAccess().getSuperKeyword_22()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getSwitchKeyword_23()); }
		Switch
		{ after(grammarAccess.getReservedWordAccess().getSwitchKeyword_23()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getThisKeyword_24()); }
		This_1
		{ after(grammarAccess.getReservedWordAccess().getThisKeyword_24()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getThrowKeyword_25()); }
		Throw
		{ after(grammarAccess.getReservedWordAccess().getThrowKeyword_25()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getTryKeyword_26()); }
		Try
		{ after(grammarAccess.getReservedWordAccess().getTryKeyword_26()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getTypeofKeyword_27()); }
		Typeof
		{ after(grammarAccess.getReservedWordAccess().getTypeofKeyword_27()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getVarKeyword_28()); }
		Var
		{ after(grammarAccess.getReservedWordAccess().getVarKeyword_28()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getVoidKeyword_29()); }
		Void
		{ after(grammarAccess.getReservedWordAccess().getVoidKeyword_29()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getWhileKeyword_30()); }
		While
		{ after(grammarAccess.getReservedWordAccess().getWhileKeyword_30()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getWithKeyword_31()); }
		With
		{ after(grammarAccess.getReservedWordAccess().getWithKeyword_31()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getYieldKeyword_32()); }
		Yield
		{ after(grammarAccess.getReservedWordAccess().getYieldKeyword_32()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getNullKeyword_33()); }
		Null
		{ after(grammarAccess.getReservedWordAccess().getNullKeyword_33()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getTrueKeyword_34()); }
		True
		{ after(grammarAccess.getReservedWordAccess().getTrueKeyword_34()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getFalseKeyword_35()); }
		False
		{ after(grammarAccess.getReservedWordAccess().getFalseKeyword_35()); }
	)
	|
	(
		{ before(grammarAccess.getReservedWordAccess().getEnumKeyword_36()); }
		Enum
		{ after(grammarAccess.getReservedWordAccess().getEnumKeyword_36()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__N4Keyword__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getN4KeywordAccess().getGetKeyword_0()); }
		Get
		{ after(grammarAccess.getN4KeywordAccess().getGetKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getSetKeyword_1()); }
		Set
		{ after(grammarAccess.getN4KeywordAccess().getSetKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getLetKeyword_2()); }
		Let
		{ after(grammarAccess.getN4KeywordAccess().getLetKeyword_2()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getProjectKeyword_3()); }
		Project
		{ after(grammarAccess.getN4KeywordAccess().getProjectKeyword_3()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getExternalKeyword_4()); }
		External
		{ after(grammarAccess.getN4KeywordAccess().getExternalKeyword_4()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getAbstractKeyword_5()); }
		Abstract
		{ after(grammarAccess.getN4KeywordAccess().getAbstractKeyword_5()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getStaticKeyword_6()); }
		Static
		{ after(grammarAccess.getN4KeywordAccess().getStaticKeyword_6()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getAsKeyword_7()); }
		As
		{ after(grammarAccess.getN4KeywordAccess().getAsKeyword_7()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getFromKeyword_8()); }
		From
		{ after(grammarAccess.getN4KeywordAccess().getFromKeyword_8()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getConstructorKeyword_9()); }
		Constructor
		{ after(grammarAccess.getN4KeywordAccess().getConstructorKeyword_9()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getOfKeyword_10()); }
		Of
		{ after(grammarAccess.getN4KeywordAccess().getOfKeyword_10()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getTargetKeyword_11()); }
		Target
		{ after(grammarAccess.getN4KeywordAccess().getTargetKeyword_11()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getTypeKeyword_12()); }
		Type
		{ after(grammarAccess.getN4KeywordAccess().getTypeKeyword_12()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getUnionKeyword_13()); }
		Union
		{ after(grammarAccess.getN4KeywordAccess().getUnionKeyword_13()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getIntersectionKeyword_14()); }
		Intersection
		{ after(grammarAccess.getN4KeywordAccess().getIntersectionKeyword_14()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getThisKeyword_15()); }
		This
		{ after(grammarAccess.getN4KeywordAccess().getThisKeyword_15()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getPromisifyKeyword_16()); }
		Promisify
		{ after(grammarAccess.getN4KeywordAccess().getPromisifyKeyword_16()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getAwaitKeyword_17()); }
		Await
		{ after(grammarAccess.getN4KeywordAccess().getAwaitKeyword_17()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getAsyncKeyword_18()); }
		Async
		{ after(grammarAccess.getN4KeywordAccess().getAsyncKeyword_18()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getImplementsKeyword_19()); }
		Implements
		{ after(grammarAccess.getN4KeywordAccess().getImplementsKeyword_19()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getInterfaceKeyword_20()); }
		Interface
		{ after(grammarAccess.getN4KeywordAccess().getInterfaceKeyword_20()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getPrivateKeyword_21()); }
		Private
		{ after(grammarAccess.getN4KeywordAccess().getPrivateKeyword_21()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getProtectedKeyword_22()); }
		Protected
		{ after(grammarAccess.getN4KeywordAccess().getProtectedKeyword_22()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getPublicKeyword_23()); }
		Public
		{ after(grammarAccess.getN4KeywordAccess().getPublicKeyword_23()); }
	)
	|
	(
		{ before(grammarAccess.getN4KeywordAccess().getOutKeyword_24()); }
		Out
		{ after(grammarAccess.getN4KeywordAccess().getOutKeyword_24()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeAccessModifier__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeAccessModifierAccess().getProjectEnumLiteralDeclaration_0()); }
		(Project)
		{ after(grammarAccess.getTypeAccessModifierAccess().getProjectEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccessModifierAccess().getPublicInternalEnumLiteralDeclaration_1()); }
		(PublicInternal)
		{ after(grammarAccess.getTypeAccessModifierAccess().getPublicInternalEnumLiteralDeclaration_1()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccessModifierAccess().getPublicEnumLiteralDeclaration_2()); }
		(Public)
		{ after(grammarAccess.getTypeAccessModifierAccess().getPublicEnumLiteralDeclaration_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__MemberAccessModifier__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getMemberAccessModifierAccess().getPrivateEnumLiteralDeclaration_0()); }
		(Private)
		{ after(grammarAccess.getMemberAccessModifierAccess().getPrivateEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getMemberAccessModifierAccess().getProjectEnumLiteralDeclaration_1()); }
		(Project)
		{ after(grammarAccess.getMemberAccessModifierAccess().getProjectEnumLiteralDeclaration_1()); }
	)
	|
	(
		{ before(grammarAccess.getMemberAccessModifierAccess().getProtectedInternalEnumLiteralDeclaration_2()); }
		(ProtectedInternal)
		{ after(grammarAccess.getMemberAccessModifierAccess().getProtectedInternalEnumLiteralDeclaration_2()); }
	)
	|
	(
		{ before(grammarAccess.getMemberAccessModifierAccess().getProtectedEnumLiteralDeclaration_3()); }
		(Protected)
		{ after(grammarAccess.getMemberAccessModifierAccess().getProtectedEnumLiteralDeclaration_3()); }
	)
	|
	(
		{ before(grammarAccess.getMemberAccessModifierAccess().getPublicInternalEnumLiteralDeclaration_4()); }
		(PublicInternal)
		{ after(grammarAccess.getMemberAccessModifierAccess().getPublicInternalEnumLiteralDeclaration_4()); }
	)
	|
	(
		{ before(grammarAccess.getMemberAccessModifierAccess().getPublicEnumLiteralDeclaration_5()); }
		(Public)
		{ after(grammarAccess.getMemberAccessModifierAccess().getPublicEnumLiteralDeclaration_5()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnnotation__Group__0__Impl
	rule__TAnnotation__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnnotationAccess().getGroup_0()); }
	(rule__TAnnotation__Group_0__0)
	{ after(grammarAccess.getTAnnotationAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnnotation__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnnotationAccess().getGroup_1()); }
	(rule__TAnnotation__Group_1__0)?
	{ after(grammarAccess.getTAnnotationAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TAnnotation__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnnotation__Group_0__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnnotationAccess().getGroup_0_0()); }
	(rule__TAnnotation__Group_0_0__0)
	{ after(grammarAccess.getTAnnotationAccess().getGroup_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TAnnotation__Group_0_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnnotation__Group_0_0__0__Impl
	rule__TAnnotation__Group_0_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_0_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnnotationAccess().getCommercialAtKeyword_0_0_0()); }
	CommercialAt
	{ after(grammarAccess.getTAnnotationAccess().getCommercialAtKeyword_0_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_0_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnnotation__Group_0_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_0_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnnotationAccess().getNameAssignment_0_0_1()); }
	(rule__TAnnotation__NameAssignment_0_0_1)
	{ after(grammarAccess.getTAnnotationAccess().getNameAssignment_0_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TAnnotation__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnnotation__Group_1__0__Impl
	rule__TAnnotation__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnnotationAccess().getLeftParenthesisKeyword_1_0()); }
	(LeftParenthesis)
	{ after(grammarAccess.getTAnnotationAccess().getLeftParenthesisKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnnotation__Group_1__1__Impl
	rule__TAnnotation__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnnotationAccess().getGroup_1_1()); }
	(rule__TAnnotation__Group_1_1__0)?
	{ after(grammarAccess.getTAnnotationAccess().getGroup_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnnotation__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnnotationAccess().getRightParenthesisKeyword_1_2()); }
	RightParenthesis
	{ after(grammarAccess.getTAnnotationAccess().getRightParenthesisKeyword_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TAnnotation__Group_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnnotation__Group_1_1__0__Impl
	rule__TAnnotation__Group_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnnotationAccess().getArgsAssignment_1_1_0()); }
	(rule__TAnnotation__ArgsAssignment_1_1_0)
	{ after(grammarAccess.getTAnnotationAccess().getArgsAssignment_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnnotation__Group_1_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnnotationAccess().getGroup_1_1_1()); }
	(rule__TAnnotation__Group_1_1_1__0)*
	{ after(grammarAccess.getTAnnotationAccess().getGroup_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TAnnotation__Group_1_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnnotation__Group_1_1_1__0__Impl
	rule__TAnnotation__Group_1_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_1_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnnotationAccess().getCommaKeyword_1_1_1_0()); }
	Comma
	{ after(grammarAccess.getTAnnotationAccess().getCommaKeyword_1_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_1_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnnotation__Group_1_1_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__Group_1_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnnotationAccess().getArgsAssignment_1_1_1_1()); }
	(rule__TAnnotation__ArgsAssignment_1_1_1_1)
	{ after(grammarAccess.getTAnnotationAccess().getArgsAssignment_1_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeRef__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeRef__Group__0__Impl
	rule__TypeRef__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeRefAccess().getTypeRefWithoutModifiersParserRuleCall_0()); }
	ruleTypeRefWithoutModifiers
	{ after(grammarAccess.getTypeRefAccess().getTypeRefWithoutModifiersParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeRef__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeRefAccess().getFollowedByQuestionMarkAssignment_1()); }
	(rule__TypeRef__FollowedByQuestionMarkAssignment_1)?
	{ after(grammarAccess.getTypeRefAccess().getFollowedByQuestionMarkAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__PrimitiveType__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__0__Impl
	rule__PrimitiveType__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getPrimitiveKeyword_0()); }
	Primitive
	{ after(grammarAccess.getPrimitiveTypeAccess().getPrimitiveKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__1__Impl
	rule__PrimitiveType__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getNameAssignment_1()); }
	(rule__PrimitiveType__NameAssignment_1)
	{ after(grammarAccess.getPrimitiveTypeAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__2__Impl
	rule__PrimitiveType__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getGroup_2()); }
	(rule__PrimitiveType__Group_2__0)?
	{ after(grammarAccess.getPrimitiveTypeAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__3__Impl
	rule__PrimitiveType__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getGroup_3()); }
	(rule__PrimitiveType__Group_3__0)?
	{ after(grammarAccess.getPrimitiveTypeAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__4__Impl
	rule__PrimitiveType__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getLeftCurlyBracketKeyword_4()); }
	LeftCurlyBracket
	{ after(grammarAccess.getPrimitiveTypeAccess().getLeftCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__5__Impl
	rule__PrimitiveType__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getGroup_5()); }
	(rule__PrimitiveType__Group_5__0)?
	{ after(grammarAccess.getPrimitiveTypeAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__6__Impl
	rule__PrimitiveType__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getGroup_6()); }
	(rule__PrimitiveType__Group_6__0)?
	{ after(grammarAccess.getPrimitiveTypeAccess().getGroup_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__7__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getRightCurlyBracketKeyword_7()); }
	RightCurlyBracket
	{ after(grammarAccess.getPrimitiveTypeAccess().getRightCurlyBracketKeyword_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__PrimitiveType__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group_2__0__Impl
	rule__PrimitiveType__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getLessThanSignKeyword_2_0()); }
	LessThanSign
	{ after(grammarAccess.getPrimitiveTypeAccess().getLessThanSignKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group_2__1__Impl
	rule__PrimitiveType__Group_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getTypeVarsAssignment_2_1()); }
	(rule__PrimitiveType__TypeVarsAssignment_2_1)
	{ after(grammarAccess.getPrimitiveTypeAccess().getTypeVarsAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group_2__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getGreaterThanSignKeyword_2_2()); }
	GreaterThanSign
	{ after(grammarAccess.getPrimitiveTypeAccess().getGreaterThanSignKeyword_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__PrimitiveType__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group_3__0__Impl
	rule__PrimitiveType__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getIndexedKeyword_3_0()); }
	Indexed
	{ after(grammarAccess.getPrimitiveTypeAccess().getIndexedKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getDeclaredElementTypeAssignment_3_1()); }
	(rule__PrimitiveType__DeclaredElementTypeAssignment_3_1)
	{ after(grammarAccess.getPrimitiveTypeAccess().getDeclaredElementTypeAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__PrimitiveType__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group_5__0__Impl
	rule__PrimitiveType__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeKeyword_5_0()); }
	AutoboxedType
	{ after(grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group_5__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeAssignment_5_1()); }
	(rule__PrimitiveType__AutoboxedTypeAssignment_5_1)
	{ after(grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeAssignment_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__PrimitiveType__Group_6__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group_6__0__Impl
	rule__PrimitiveType__Group_6__1
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_6__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getAssignmnentCompatibleKeyword_6_0()); }
	AssignmnentCompatible
	{ after(grammarAccess.getPrimitiveTypeAccess().getAssignmnentCompatibleKeyword_6_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_6__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group_6__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group_6__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getAssignmentCompatibleAssignment_6_1()); }
	(rule__PrimitiveType__AssignmentCompatibleAssignment_6_1)
	{ after(grammarAccess.getPrimitiveTypeAccess().getAssignmentCompatibleAssignment_6_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeReferenceName__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeReferenceName__Group_5__0__Impl
	rule__TypeReferenceName__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeReferenceName__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeReferenceNameAccess().getIDENTIFIERTerminalRuleCall_5_0()); }
	RULE_IDENTIFIER
	{ after(grammarAccess.getTypeReferenceNameAccess().getIDENTIFIERTerminalRuleCall_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeReferenceName__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeReferenceName__Group_5__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeReferenceName__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeReferenceNameAccess().getGroup_5_1()); }
	(rule__TypeReferenceName__Group_5_1__0)*
	{ after(grammarAccess.getTypeReferenceNameAccess().getGroup_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeReferenceName__Group_5_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeReferenceName__Group_5_1__0__Impl
	rule__TypeReferenceName__Group_5_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeReferenceName__Group_5_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeReferenceNameAccess().getSolidusKeyword_5_1_0()); }
	Solidus
	{ after(grammarAccess.getTypeReferenceNameAccess().getSolidusKeyword_5_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeReferenceName__Group_5_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeReferenceName__Group_5_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeReferenceName__Group_5_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeReferenceNameAccess().getIDENTIFIERTerminalRuleCall_5_1_1()); }
	RULE_IDENTIFIER
	{ after(grammarAccess.getTypeReferenceNameAccess().getIDENTIFIERTerminalRuleCall_5_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__AnyType__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__AnyType__Group__0__Impl
	rule__AnyType__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__AnyType__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAnyTypeAccess().getAnyTypeAction_0()); }
	()
	{ after(grammarAccess.getAnyTypeAccess().getAnyTypeAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__AnyType__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__AnyType__Group__1__Impl
	rule__AnyType__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__AnyType__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAnyTypeAccess().getNameAssignment_1()); }
	(rule__AnyType__NameAssignment_1)
	{ after(grammarAccess.getAnyTypeAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__AnyType__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__AnyType__Group__2__Impl
	rule__AnyType__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__AnyType__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAnyTypeAccess().getLeftCurlyBracketKeyword_2()); }
	LeftCurlyBracket
	{ after(grammarAccess.getAnyTypeAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__AnyType__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__AnyType__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__AnyType__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAnyTypeAccess().getRightCurlyBracketKeyword_3()); }
	RightCurlyBracket
	{ after(grammarAccess.getAnyTypeAccess().getRightCurlyBracketKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VoidType__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VoidType__Group__0__Impl
	rule__VoidType__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VoidType__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVoidTypeAccess().getVoidTypeAction_0()); }
	()
	{ after(grammarAccess.getVoidTypeAccess().getVoidTypeAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VoidType__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VoidType__Group__1__Impl
	rule__VoidType__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__VoidType__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVoidTypeAccess().getNameAssignment_1()); }
	(rule__VoidType__NameAssignment_1)
	{ after(grammarAccess.getVoidTypeAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VoidType__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VoidType__Group__2__Impl
	rule__VoidType__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__VoidType__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVoidTypeAccess().getLeftCurlyBracketKeyword_2()); }
	LeftCurlyBracket
	{ after(grammarAccess.getVoidTypeAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VoidType__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VoidType__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VoidType__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVoidTypeAccess().getRightCurlyBracketKeyword_3()); }
	RightCurlyBracket
	{ after(grammarAccess.getVoidTypeAccess().getRightCurlyBracketKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__UndefinedType__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__UndefinedType__Group__0__Impl
	rule__UndefinedType__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__UndefinedType__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getUndefinedTypeAccess().getUndefinedTypeAction_0()); }
	()
	{ after(grammarAccess.getUndefinedTypeAccess().getUndefinedTypeAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__UndefinedType__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__UndefinedType__Group__1__Impl
	rule__UndefinedType__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__UndefinedType__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getUndefinedTypeAccess().getNameAssignment_1()); }
	(rule__UndefinedType__NameAssignment_1)
	{ after(grammarAccess.getUndefinedTypeAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__UndefinedType__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__UndefinedType__Group__2__Impl
	rule__UndefinedType__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__UndefinedType__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getUndefinedTypeAccess().getLeftCurlyBracketKeyword_2()); }
	LeftCurlyBracket
	{ after(grammarAccess.getUndefinedTypeAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__UndefinedType__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__UndefinedType__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__UndefinedType__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getUndefinedTypeAccess().getRightCurlyBracketKeyword_3()); }
	RightCurlyBracket
	{ after(grammarAccess.getUndefinedTypeAccess().getRightCurlyBracketKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__NullType__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__NullType__Group__0__Impl
	rule__NullType__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__NullType__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getNullTypeAccess().getNullTypeAction_0()); }
	()
	{ after(grammarAccess.getNullTypeAccess().getNullTypeAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__NullType__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__NullType__Group__1__Impl
	rule__NullType__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__NullType__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getNullTypeAccess().getNameAssignment_1()); }
	(rule__NullType__NameAssignment_1)
	{ after(grammarAccess.getNullTypeAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__NullType__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__NullType__Group__2__Impl
	rule__NullType__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__NullType__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getNullTypeAccess().getLeftCurlyBracketKeyword_2()); }
	LeftCurlyBracket
	{ after(grammarAccess.getNullTypeAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__NullType__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__NullType__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__NullType__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getNullTypeAccess().getRightCurlyBracketKeyword_3()); }
	RightCurlyBracket
	{ after(grammarAccess.getNullTypeAccess().getRightCurlyBracketKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypesComputedPropertyName__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypesComputedPropertyName__Group__0__Impl
	rule__TypesComputedPropertyName__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypesComputedPropertyName__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypesComputedPropertyNameAccess().getLeftSquareBracketKeyword_0()); }
	LeftSquareBracket
	{ after(grammarAccess.getTypesComputedPropertyNameAccess().getLeftSquareBracketKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypesComputedPropertyName__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypesComputedPropertyName__Group__1__Impl
	rule__TypesComputedPropertyName__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TypesComputedPropertyName__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypesComputedPropertyNameAccess().getAlternatives_1()); }
	(rule__TypesComputedPropertyName__Alternatives_1)
	{ after(grammarAccess.getTypesComputedPropertyNameAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypesComputedPropertyName__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypesComputedPropertyName__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypesComputedPropertyName__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypesComputedPropertyNameAccess().getRightSquareBracketKeyword_2()); }
	RightSquareBracket
	{ after(grammarAccess.getTypesComputedPropertyNameAccess().getRightSquareBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypesSymbolLiteralComputedName__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypesSymbolLiteralComputedName__Group__0__Impl
	rule__TypesSymbolLiteralComputedName__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypesSymbolLiteralComputedName__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getTypesIdentifierParserRuleCall_0()); }
	ruleTypesIdentifier
	{ after(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getTypesIdentifierParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypesSymbolLiteralComputedName__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypesSymbolLiteralComputedName__Group__1__Impl
	rule__TypesSymbolLiteralComputedName__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TypesSymbolLiteralComputedName__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getFullStopKeyword_1()); }
	FullStop
	{ after(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getFullStopKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypesSymbolLiteralComputedName__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypesSymbolLiteralComputedName__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypesSymbolLiteralComputedName__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getTypesIdentifierParserRuleCall_2()); }
	ruleTypesIdentifier
	{ after(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getTypesIdentifierParserRuleCall_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TObjectPrototype__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group__0__Impl
	rule__TObjectPrototype__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getDeclaredTypeAccessModifierAssignment_0()); }
	(rule__TObjectPrototype__DeclaredTypeAccessModifierAssignment_0)
	{ after(grammarAccess.getTObjectPrototypeAccess().getDeclaredTypeAccessModifierAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group__1__Impl
	rule__TObjectPrototype__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getDeclaredProvidedByRuntimeAssignment_1()); }
	(rule__TObjectPrototype__DeclaredProvidedByRuntimeAssignment_1)?
	{ after(grammarAccess.getTObjectPrototypeAccess().getDeclaredProvidedByRuntimeAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group__2__Impl
	rule__TObjectPrototype__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getDeclaredFinalAssignment_2()); }
	(rule__TObjectPrototype__DeclaredFinalAssignment_2)?
	{ after(grammarAccess.getTObjectPrototypeAccess().getDeclaredFinalAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group__3__Impl
	rule__TObjectPrototype__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getObjectKeyword_3()); }
	Object
	{ after(grammarAccess.getTObjectPrototypeAccess().getObjectKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group__4__Impl
	rule__TObjectPrototype__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getNameAssignment_4()); }
	(rule__TObjectPrototype__NameAssignment_4)
	{ after(grammarAccess.getTObjectPrototypeAccess().getNameAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group__5__Impl
	rule__TObjectPrototype__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getTypeVariablesParserRuleCall_5()); }
	(ruleTypeVariables)?
	{ after(grammarAccess.getTObjectPrototypeAccess().getTypeVariablesParserRuleCall_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group__6__Impl
	rule__TObjectPrototype__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getGroup_6()); }
	(rule__TObjectPrototype__Group_6__0)?
	{ after(grammarAccess.getTObjectPrototypeAccess().getGroup_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group__7__Impl
	rule__TObjectPrototype__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getGroup_7()); }
	(rule__TObjectPrototype__Group_7__0)?
	{ after(grammarAccess.getTObjectPrototypeAccess().getGroup_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group__8__Impl
	rule__TObjectPrototype__Group__9
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getAnnotationsAssignment_8()); }
	(rule__TObjectPrototype__AnnotationsAssignment_8)*
	{ after(grammarAccess.getTObjectPrototypeAccess().getAnnotationsAssignment_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__9
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group__9__Impl
	rule__TObjectPrototype__Group__10
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__9__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getLeftCurlyBracketKeyword_9()); }
	LeftCurlyBracket
	{ after(grammarAccess.getTObjectPrototypeAccess().getLeftCurlyBracketKeyword_9()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__10
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group__10__Impl
	rule__TObjectPrototype__Group__11
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__10__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersAssignment_10()); }
	(rule__TObjectPrototype__OwnedMembersAssignment_10)*
	{ after(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersAssignment_10()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__11
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group__11__Impl
	rule__TObjectPrototype__Group__12
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__11__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getGroup_11()); }
	(rule__TObjectPrototype__Group_11__0)?
	{ after(grammarAccess.getTObjectPrototypeAccess().getGroup_11()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__12
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group__12__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group__12__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getRightCurlyBracketKeyword_12()); }
	RightCurlyBracket
	{ after(grammarAccess.getTObjectPrototypeAccess().getRightCurlyBracketKeyword_12()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TObjectPrototype__Group_6__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group_6__0__Impl
	rule__TObjectPrototype__Group_6__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group_6__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getExtendsKeyword_6_0()); }
	Extends
	{ after(grammarAccess.getTObjectPrototypeAccess().getExtendsKeyword_6_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group_6__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group_6__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group_6__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getSuperTypeAssignment_6_1()); }
	(rule__TObjectPrototype__SuperTypeAssignment_6_1)
	{ after(grammarAccess.getTObjectPrototypeAccess().getSuperTypeAssignment_6_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TObjectPrototype__Group_7__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group_7__0__Impl
	rule__TObjectPrototype__Group_7__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group_7__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getIndexedKeyword_7_0()); }
	Indexed
	{ after(grammarAccess.getTObjectPrototypeAccess().getIndexedKeyword_7_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group_7__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group_7__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group_7__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getDeclaredElementTypeAssignment_7_1()); }
	(rule__TObjectPrototype__DeclaredElementTypeAssignment_7_1)
	{ after(grammarAccess.getTObjectPrototypeAccess().getDeclaredElementTypeAssignment_7_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TObjectPrototype__Group_11__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group_11__0__Impl
	rule__TObjectPrototype__Group_11__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group_11__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getCallableCtorAssignment_11_0()); }
	(rule__TObjectPrototype__CallableCtorAssignment_11_0)
	{ after(grammarAccess.getTObjectPrototypeAccess().getCallableCtorAssignment_11_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group_11__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TObjectPrototype__Group_11__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__Group_11__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersAssignment_11_1()); }
	(rule__TObjectPrototype__OwnedMembersAssignment_11_1)*
	{ after(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersAssignment_11_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VirtualBaseType__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VirtualBaseType__Group__0__Impl
	rule__VirtualBaseType__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVirtualBaseTypeAccess().getVirtualBaseTypeAction_0()); }
	()
	{ after(grammarAccess.getVirtualBaseTypeAccess().getVirtualBaseTypeAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VirtualBaseType__Group__1__Impl
	rule__VirtualBaseType__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVirtualBaseTypeAccess().getVirtualBaseKeyword_1()); }
	VirtualBase
	{ after(grammarAccess.getVirtualBaseTypeAccess().getVirtualBaseKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VirtualBaseType__Group__2__Impl
	rule__VirtualBaseType__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVirtualBaseTypeAccess().getNameAssignment_2()); }
	(rule__VirtualBaseType__NameAssignment_2)
	{ after(grammarAccess.getVirtualBaseTypeAccess().getNameAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VirtualBaseType__Group__3__Impl
	rule__VirtualBaseType__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVirtualBaseTypeAccess().getGroup_3()); }
	(rule__VirtualBaseType__Group_3__0)?
	{ after(grammarAccess.getVirtualBaseTypeAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VirtualBaseType__Group__4__Impl
	rule__VirtualBaseType__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVirtualBaseTypeAccess().getLeftCurlyBracketKeyword_4()); }
	LeftCurlyBracket
	{ after(grammarAccess.getVirtualBaseTypeAccess().getLeftCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VirtualBaseType__Group__5__Impl
	rule__VirtualBaseType__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVirtualBaseTypeAccess().getOwnedMembersAssignment_5()); }
	(rule__VirtualBaseType__OwnedMembersAssignment_5)*
	{ after(grammarAccess.getVirtualBaseTypeAccess().getOwnedMembersAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VirtualBaseType__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVirtualBaseTypeAccess().getRightCurlyBracketKeyword_6()); }
	RightCurlyBracket
	{ after(grammarAccess.getVirtualBaseTypeAccess().getRightCurlyBracketKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VirtualBaseType__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VirtualBaseType__Group_3__0__Impl
	rule__VirtualBaseType__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVirtualBaseTypeAccess().getIndexedKeyword_3_0()); }
	Indexed
	{ after(grammarAccess.getVirtualBaseTypeAccess().getIndexedKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VirtualBaseType__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVirtualBaseTypeAccess().getDeclaredElementTypeAssignment_3_1()); }
	(rule__VirtualBaseType__DeclaredElementTypeAssignment_3_1)
	{ after(grammarAccess.getVirtualBaseTypeAccess().getDeclaredElementTypeAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TClass__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group__0__Impl
	rule__TClass__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getDeclaredTypeAccessModifierAssignment_0()); }
	(rule__TClass__DeclaredTypeAccessModifierAssignment_0)
	{ after(grammarAccess.getTClassAccess().getDeclaredTypeAccessModifierAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group__1__Impl
	rule__TClass__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getDeclaredProvidedByRuntimeAssignment_1()); }
	(rule__TClass__DeclaredProvidedByRuntimeAssignment_1)?
	{ after(grammarAccess.getTClassAccess().getDeclaredProvidedByRuntimeAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group__2__Impl
	rule__TClass__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getDeclaredAbstractAssignment_2()); }
	(rule__TClass__DeclaredAbstractAssignment_2)?
	{ after(grammarAccess.getTClassAccess().getDeclaredAbstractAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group__3__Impl
	rule__TClass__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getDeclaredFinalAssignment_3()); }
	(rule__TClass__DeclaredFinalAssignment_3)?
	{ after(grammarAccess.getTClassAccess().getDeclaredFinalAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group__4__Impl
	rule__TClass__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getClassKeyword_4()); }
	Class
	{ after(grammarAccess.getTClassAccess().getClassKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group__5__Impl
	rule__TClass__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getTClassOrInterfaceHeaderParserRuleCall_5()); }
	ruleTClassOrInterfaceHeader
	{ after(grammarAccess.getTClassAccess().getTClassOrInterfaceHeaderParserRuleCall_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group__6__Impl
	rule__TClass__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getGroup_6()); }
	(rule__TClass__Group_6__0)?
	{ after(grammarAccess.getTClassAccess().getGroup_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group__7__Impl
	rule__TClass__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getGroup_7()); }
	(rule__TClass__Group_7__0)?
	{ after(grammarAccess.getTClassAccess().getGroup_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group__8__Impl
	rule__TClass__Group__9
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getAnnotationsAssignment_8()); }
	(rule__TClass__AnnotationsAssignment_8)*
	{ after(grammarAccess.getTClassAccess().getAnnotationsAssignment_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__9
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group__9__Impl
	rule__TClass__Group__10
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__9__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getLeftCurlyBracketKeyword_9()); }
	LeftCurlyBracket
	{ after(grammarAccess.getTClassAccess().getLeftCurlyBracketKeyword_9()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__10
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group__10__Impl
	rule__TClass__Group__11
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__10__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getOwnedMembersAssignment_10()); }
	(rule__TClass__OwnedMembersAssignment_10)*
	{ after(grammarAccess.getTClassAccess().getOwnedMembersAssignment_10()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__11
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group__11__Impl
	rule__TClass__Group__12
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__11__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getGroup_11()); }
	(rule__TClass__Group_11__0)?
	{ after(grammarAccess.getTClassAccess().getGroup_11()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__12
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group__12__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group__12__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getRightCurlyBracketKeyword_12()); }
	RightCurlyBracket
	{ after(grammarAccess.getTClassAccess().getRightCurlyBracketKeyword_12()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TClass__Group_6__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group_6__0__Impl
	rule__TClass__Group_6__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_6__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getExtendsKeyword_6_0()); }
	Extends
	{ after(grammarAccess.getTClassAccess().getExtendsKeyword_6_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_6__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group_6__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_6__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getSuperClassRefAssignment_6_1()); }
	(rule__TClass__SuperClassRefAssignment_6_1)
	{ after(grammarAccess.getTClassAccess().getSuperClassRefAssignment_6_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TClass__Group_7__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group_7__0__Impl
	rule__TClass__Group_7__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_7__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getImplementsKeyword_7_0()); }
	Implements
	{ after(grammarAccess.getTClassAccess().getImplementsKeyword_7_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_7__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group_7__1__Impl
	rule__TClass__Group_7__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_7__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getImplementedInterfaceRefsAssignment_7_1()); }
	(rule__TClass__ImplementedInterfaceRefsAssignment_7_1)
	{ after(grammarAccess.getTClassAccess().getImplementedInterfaceRefsAssignment_7_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_7__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group_7__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_7__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getGroup_7_2()); }
	(rule__TClass__Group_7_2__0)*
	{ after(grammarAccess.getTClassAccess().getGroup_7_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TClass__Group_7_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group_7_2__0__Impl
	rule__TClass__Group_7_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_7_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getCommaKeyword_7_2_0()); }
	Comma
	{ after(grammarAccess.getTClassAccess().getCommaKeyword_7_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_7_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group_7_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_7_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getImplementedInterfaceRefsAssignment_7_2_1()); }
	(rule__TClass__ImplementedInterfaceRefsAssignment_7_2_1)
	{ after(grammarAccess.getTClassAccess().getImplementedInterfaceRefsAssignment_7_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TClass__Group_11__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group_11__0__Impl
	rule__TClass__Group_11__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_11__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getCallableCtorAssignment_11_0()); }
	(rule__TClass__CallableCtorAssignment_11_0)
	{ after(grammarAccess.getTClassAccess().getCallableCtorAssignment_11_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_11__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClass__Group_11__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__Group_11__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassAccess().getOwnedMembersAssignment_11_1()); }
	(rule__TClass__OwnedMembersAssignment_11_1)*
	{ after(grammarAccess.getTClassAccess().getOwnedMembersAssignment_11_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TInterface__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group__0__Impl
	rule__TInterface__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getDeclaredTypeAccessModifierAssignment_0()); }
	(rule__TInterface__DeclaredTypeAccessModifierAssignment_0)
	{ after(grammarAccess.getTInterfaceAccess().getDeclaredTypeAccessModifierAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group__1__Impl
	rule__TInterface__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getDeclaredProvidedByRuntimeAssignment_1()); }
	(rule__TInterface__DeclaredProvidedByRuntimeAssignment_1)?
	{ after(grammarAccess.getTInterfaceAccess().getDeclaredProvidedByRuntimeAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group__2__Impl
	rule__TInterface__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getInterfaceKeyword_2()); }
	Interface
	{ after(grammarAccess.getTInterfaceAccess().getInterfaceKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group__3__Impl
	rule__TInterface__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getTClassOrInterfaceHeaderParserRuleCall_3()); }
	ruleTClassOrInterfaceHeader
	{ after(grammarAccess.getTInterfaceAccess().getTClassOrInterfaceHeaderParserRuleCall_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group__4__Impl
	rule__TInterface__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getGroup_4()); }
	(rule__TInterface__Group_4__0)?
	{ after(grammarAccess.getTInterfaceAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group__5__Impl
	rule__TInterface__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getAnnotationsAssignment_5()); }
	(rule__TInterface__AnnotationsAssignment_5)*
	{ after(grammarAccess.getTInterfaceAccess().getAnnotationsAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group__6__Impl
	rule__TInterface__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getLeftCurlyBracketKeyword_6()); }
	LeftCurlyBracket
	{ after(grammarAccess.getTInterfaceAccess().getLeftCurlyBracketKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group__7__Impl
	rule__TInterface__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getOwnedMembersAssignment_7()); }
	(rule__TInterface__OwnedMembersAssignment_7)*
	{ after(grammarAccess.getTInterfaceAccess().getOwnedMembersAssignment_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group__8__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getRightCurlyBracketKeyword_8()); }
	RightCurlyBracket
	{ after(grammarAccess.getTInterfaceAccess().getRightCurlyBracketKeyword_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TInterface__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group_4__0__Impl
	rule__TInterface__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getExtendsKeyword_4_0()); }
	Extends
	{ after(grammarAccess.getTInterfaceAccess().getExtendsKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group_4__1__Impl
	rule__TInterface__Group_4__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsAssignment_4_1()); }
	(rule__TInterface__SuperInterfaceRefsAssignment_4_1)
	{ after(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsAssignment_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group_4__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group_4__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group_4__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getGroup_4_2()); }
	(rule__TInterface__Group_4_2__0)*
	{ after(grammarAccess.getTInterfaceAccess().getGroup_4_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TInterface__Group_4_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group_4_2__0__Impl
	rule__TInterface__Group_4_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group_4_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getCommaKeyword_4_2_0()); }
	Comma
	{ after(grammarAccess.getTInterfaceAccess().getCommaKeyword_4_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group_4_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TInterface__Group_4_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__Group_4_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsAssignment_4_2_1()); }
	(rule__TInterface__SuperInterfaceRefsAssignment_4_2_1)
	{ after(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsAssignment_4_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeVariable__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeVariable__Group__0__Impl
	rule__TypeVariable__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariable__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeVariableAccess().getNameAssignment_0()); }
	(rule__TypeVariable__NameAssignment_0)
	{ after(grammarAccess.getTypeVariableAccess().getNameAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariable__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeVariable__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariable__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeVariableAccess().getGroup_1()); }
	(rule__TypeVariable__Group_1__0)?
	{ after(grammarAccess.getTypeVariableAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeVariable__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeVariable__Group_1__0__Impl
	rule__TypeVariable__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariable__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeVariableAccess().getExtendsKeyword_1_0()); }
	Extends
	{ after(grammarAccess.getTypeVariableAccess().getExtendsKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariable__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeVariable__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariable__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeVariableAccess().getDeclaredUpperBoundAssignment_1_1()); }
	(rule__TypeVariable__DeclaredUpperBoundAssignment_1_1)
	{ after(grammarAccess.getTypeVariableAccess().getDeclaredUpperBoundAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TClassOrInterfaceHeader__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClassOrInterfaceHeader__Group__0__Impl
	rule__TClassOrInterfaceHeader__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypingStrategyAssignment_0()); }
	(rule__TClassOrInterfaceHeader__TypingStrategyAssignment_0)?
	{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypingStrategyAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClassOrInterfaceHeader__Group__1__Impl
	rule__TClassOrInterfaceHeader__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getNameAssignment_1()); }
	(rule__TClassOrInterfaceHeader__NameAssignment_1)
	{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClassOrInterfaceHeader__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getGroup_2()); }
	(rule__TClassOrInterfaceHeader__Group_2__0)?
	{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TClassOrInterfaceHeader__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClassOrInterfaceHeader__Group_2__0__Impl
	rule__TClassOrInterfaceHeader__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getLessThanSignKeyword_2_0()); }
	LessThanSign
	{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getLessThanSignKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClassOrInterfaceHeader__Group_2__1__Impl
	rule__TClassOrInterfaceHeader__Group_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsAssignment_2_1()); }
	(rule__TClassOrInterfaceHeader__TypeVarsAssignment_2_1)
	{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClassOrInterfaceHeader__Group_2__2__Impl
	rule__TClassOrInterfaceHeader__Group_2__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getGroup_2_2()); }
	(rule__TClassOrInterfaceHeader__Group_2_2__0)*
	{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getGroup_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group_2__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClassOrInterfaceHeader__Group_2__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group_2__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getGreaterThanSignKeyword_2_3()); }
	GreaterThanSign
	{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getGreaterThanSignKeyword_2_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TClassOrInterfaceHeader__Group_2_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClassOrInterfaceHeader__Group_2_2__0__Impl
	rule__TClassOrInterfaceHeader__Group_2_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group_2_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getCommaKeyword_2_2_0()); }
	Comma
	{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getCommaKeyword_2_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group_2_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TClassOrInterfaceHeader__Group_2_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__Group_2_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsAssignment_2_2_1()); }
	(rule__TClassOrInterfaceHeader__TypeVarsAssignment_2_2_1)
	{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsAssignment_2_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CallableCtor__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CallableCtor__Group__0__Impl
	rule__CallableCtor__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__CallableCtor__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCallableCtorAccess().getTMethodAction_0()); }
	()
	{ after(grammarAccess.getCallableCtorAccess().getTMethodAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CallableCtor__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CallableCtor__Group__1__Impl
	rule__CallableCtor__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__CallableCtor__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCallableCtorAccess().getTFormalParametersParserRuleCall_1()); }
	ruleTFormalParameters
	{ after(grammarAccess.getCallableCtorAccess().getTFormalParametersParserRuleCall_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CallableCtor__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CallableCtor__Group__2__Impl
	rule__CallableCtor__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__CallableCtor__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCallableCtorAccess().getColonSepReturnTypeRefParserRuleCall_2()); }
	(ruleColonSepReturnTypeRef)?
	{ after(grammarAccess.getCallableCtorAccess().getColonSepReturnTypeRefParserRuleCall_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CallableCtor__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CallableCtor__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CallableCtor__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCallableCtorAccess().getSemicolonKeyword_3()); }
	(Semicolon)?
	{ after(grammarAccess.getCallableCtorAccess().getSemicolonKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TFormalParameters__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFormalParameters__Group__0__Impl
	rule__TFormalParameters__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameters__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFormalParametersAccess().getLeftParenthesisKeyword_0()); }
	LeftParenthesis
	{ after(grammarAccess.getTFormalParametersAccess().getLeftParenthesisKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameters__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFormalParameters__Group__1__Impl
	rule__TFormalParameters__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameters__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFormalParametersAccess().getGroup_1()); }
	(rule__TFormalParameters__Group_1__0)?
	{ after(grammarAccess.getTFormalParametersAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameters__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFormalParameters__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameters__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFormalParametersAccess().getRightParenthesisKeyword_2()); }
	RightParenthesis
	{ after(grammarAccess.getTFormalParametersAccess().getRightParenthesisKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TFormalParameters__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFormalParameters__Group_1__0__Impl
	rule__TFormalParameters__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameters__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFormalParametersAccess().getFparsAssignment_1_0()); }
	(rule__TFormalParameters__FparsAssignment_1_0)
	{ after(grammarAccess.getTFormalParametersAccess().getFparsAssignment_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameters__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFormalParameters__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameters__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFormalParametersAccess().getGroup_1_1()); }
	(rule__TFormalParameters__Group_1_1__0)*
	{ after(grammarAccess.getTFormalParametersAccess().getGroup_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TFormalParameters__Group_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFormalParameters__Group_1_1__0__Impl
	rule__TFormalParameters__Group_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameters__Group_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFormalParametersAccess().getCommaKeyword_1_1_0()); }
	Comma
	{ after(grammarAccess.getTFormalParametersAccess().getCommaKeyword_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameters__Group_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFormalParameters__Group_1_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameters__Group_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFormalParametersAccess().getFparsAssignment_1_1_1()); }
	(rule__TFormalParameters__FparsAssignment_1_1_1)
	{ after(grammarAccess.getTFormalParametersAccess().getFparsAssignment_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TMethod__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TMethod__Group__0__Impl
	rule__TMethod__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTMethodAccess().getGroup_0()); }
	(rule__TMethod__Group_0__0)
	{ after(grammarAccess.getTMethodAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TMethod__Group__1__Impl
	rule__TMethod__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTMethodAccess().getColonSepReturnTypeRefParserRuleCall_1()); }
	ruleColonSepReturnTypeRef
	{ after(grammarAccess.getTMethodAccess().getColonSepReturnTypeRefParserRuleCall_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TMethod__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTMethodAccess().getSemicolonKeyword_2()); }
	(Semicolon)?
	{ after(grammarAccess.getTMethodAccess().getSemicolonKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TMethod__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TMethod__Group_0__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTMethodAccess().getGroup_0_0()); }
	(rule__TMethod__Group_0_0__0)
	{ after(grammarAccess.getTMethodAccess().getGroup_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TMethod__Group_0_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TMethod__Group_0_0__0__Impl
	rule__TMethod__Group_0_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group_0_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTMethodAccess().getDeclaredMemberAccessModifierAssignment_0_0_0()); }
	(rule__TMethod__DeclaredMemberAccessModifierAssignment_0_0_0)
	{ after(grammarAccess.getTMethodAccess().getDeclaredMemberAccessModifierAssignment_0_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group_0_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TMethod__Group_0_0__1__Impl
	rule__TMethod__Group_0_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group_0_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTMethodAccess().getAlternatives_0_0_1()); }
	(rule__TMethod__Alternatives_0_0_1)?
	{ after(grammarAccess.getTMethodAccess().getAlternatives_0_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group_0_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TMethod__Group_0_0__2__Impl
	rule__TMethod__Group_0_0__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group_0_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTMethodAccess().getTypeVariablesParserRuleCall_0_0_2()); }
	(ruleTypeVariables)?
	{ after(grammarAccess.getTMethodAccess().getTypeVariablesParserRuleCall_0_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group_0_0__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TMethod__Group_0_0__3__Impl
	rule__TMethod__Group_0_0__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group_0_0__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTMethodAccess().getAlternatives_0_0_3()); }
	(rule__TMethod__Alternatives_0_0_3)
	{ after(grammarAccess.getTMethodAccess().getAlternatives_0_0_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group_0_0__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TMethod__Group_0_0__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__Group_0_0__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTMethodAccess().getTFormalParametersParserRuleCall_0_0_4()); }
	(ruleTFormalParameters)
	{ after(grammarAccess.getTMethodAccess().getTFormalParametersParserRuleCall_0_0_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TField__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TField__Group__0__Impl
	rule__TField__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFieldAccess().getDeclaredMemberAccessModifierAssignment_0()); }
	(rule__TField__DeclaredMemberAccessModifierAssignment_0)
	{ after(grammarAccess.getTFieldAccess().getDeclaredMemberAccessModifierAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TField__Group__1__Impl
	rule__TField__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFieldAccess().getAlternatives_1()); }
	(rule__TField__Alternatives_1)?
	{ after(grammarAccess.getTFieldAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TField__Group__2__Impl
	rule__TField__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFieldAccess().getAlternatives_2()); }
	(rule__TField__Alternatives_2)
	{ after(grammarAccess.getTFieldAccess().getAlternatives_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TField__Group__3__Impl
	rule__TField__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFieldAccess().getOptionalAssignment_3()); }
	(rule__TField__OptionalAssignment_3)?
	{ after(grammarAccess.getTFieldAccess().getOptionalAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TField__Group__4__Impl
	rule__TField__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFieldAccess().getColonSepTypeRefParserRuleCall_4()); }
	ruleColonSepTypeRef
	{ after(grammarAccess.getTFieldAccess().getColonSepTypeRefParserRuleCall_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TField__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFieldAccess().getSemicolonKeyword_5()); }
	(Semicolon)?
	{ after(grammarAccess.getTFieldAccess().getSemicolonKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TGetter__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TGetter__Group__0__Impl
	rule__TGetter__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTGetterAccess().getGroup_0()); }
	(rule__TGetter__Group_0__0)
	{ after(grammarAccess.getTGetterAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TGetter__Group__1__Impl
	rule__TGetter__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTGetterAccess().getOptionalAssignment_1()); }
	(rule__TGetter__OptionalAssignment_1)?
	{ after(grammarAccess.getTGetterAccess().getOptionalAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TGetter__Group__2__Impl
	rule__TGetter__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTGetterAccess().getLeftParenthesisKeyword_2()); }
	LeftParenthesis
	{ after(grammarAccess.getTGetterAccess().getLeftParenthesisKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TGetter__Group__3__Impl
	rule__TGetter__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTGetterAccess().getRightParenthesisKeyword_3()); }
	RightParenthesis
	{ after(grammarAccess.getTGetterAccess().getRightParenthesisKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TGetter__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTGetterAccess().getColonSepDeclaredTypeRefParserRuleCall_4()); }
	ruleColonSepDeclaredTypeRef
	{ after(grammarAccess.getTGetterAccess().getColonSepDeclaredTypeRefParserRuleCall_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TGetter__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TGetter__Group_0__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTGetterAccess().getGroup_0_0()); }
	(rule__TGetter__Group_0_0__0)
	{ after(grammarAccess.getTGetterAccess().getGroup_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TGetter__Group_0_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TGetter__Group_0_0__0__Impl
	rule__TGetter__Group_0_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group_0_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTGetterAccess().getDeclaredMemberAccessModifierAssignment_0_0_0()); }
	(rule__TGetter__DeclaredMemberAccessModifierAssignment_0_0_0)
	{ after(grammarAccess.getTGetterAccess().getDeclaredMemberAccessModifierAssignment_0_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group_0_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TGetter__Group_0_0__1__Impl
	rule__TGetter__Group_0_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group_0_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTGetterAccess().getAlternatives_0_0_1()); }
	(rule__TGetter__Alternatives_0_0_1)?
	{ after(grammarAccess.getTGetterAccess().getAlternatives_0_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group_0_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TGetter__Group_0_0__2__Impl
	rule__TGetter__Group_0_0__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group_0_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTGetterAccess().getGetKeyword_0_0_2()); }
	Get
	{ after(grammarAccess.getTGetterAccess().getGetKeyword_0_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group_0_0__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TGetter__Group_0_0__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__Group_0_0__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTGetterAccess().getAlternatives_0_0_3()); }
	(rule__TGetter__Alternatives_0_0_3)
	{ after(grammarAccess.getTGetterAccess().getAlternatives_0_0_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TSetter__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TSetter__Group__0__Impl
	rule__TSetter__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTSetterAccess().getGroup_0()); }
	(rule__TSetter__Group_0__0)
	{ after(grammarAccess.getTSetterAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TSetter__Group__1__Impl
	rule__TSetter__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTSetterAccess().getOptionalAssignment_1()); }
	(rule__TSetter__OptionalAssignment_1)?
	{ after(grammarAccess.getTSetterAccess().getOptionalAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TSetter__Group__2__Impl
	rule__TSetter__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTSetterAccess().getLeftParenthesisKeyword_2()); }
	LeftParenthesis
	{ after(grammarAccess.getTSetterAccess().getLeftParenthesisKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TSetter__Group__3__Impl
	rule__TSetter__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTSetterAccess().getFparAssignment_3()); }
	(rule__TSetter__FparAssignment_3)
	{ after(grammarAccess.getTSetterAccess().getFparAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TSetter__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTSetterAccess().getRightParenthesisKeyword_4()); }
	RightParenthesis
	{ after(grammarAccess.getTSetterAccess().getRightParenthesisKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TSetter__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TSetter__Group_0__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTSetterAccess().getGroup_0_0()); }
	(rule__TSetter__Group_0_0__0)
	{ after(grammarAccess.getTSetterAccess().getGroup_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TSetter__Group_0_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TSetter__Group_0_0__0__Impl
	rule__TSetter__Group_0_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group_0_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTSetterAccess().getDeclaredMemberAccessModifierAssignment_0_0_0()); }
	(rule__TSetter__DeclaredMemberAccessModifierAssignment_0_0_0)
	{ after(grammarAccess.getTSetterAccess().getDeclaredMemberAccessModifierAssignment_0_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group_0_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TSetter__Group_0_0__1__Impl
	rule__TSetter__Group_0_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group_0_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTSetterAccess().getAlternatives_0_0_1()); }
	(rule__TSetter__Alternatives_0_0_1)?
	{ after(grammarAccess.getTSetterAccess().getAlternatives_0_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group_0_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TSetter__Group_0_0__2__Impl
	rule__TSetter__Group_0_0__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group_0_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTSetterAccess().getSetKeyword_0_0_2()); }
	Set
	{ after(grammarAccess.getTSetterAccess().getSetKeyword_0_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group_0_0__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TSetter__Group_0_0__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__Group_0_0__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTSetterAccess().getAlternatives_0_0_3()); }
	(rule__TSetter__Alternatives_0_0_3)
	{ after(grammarAccess.getTSetterAccess().getAlternatives_0_0_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TFunction__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFunction__Group__0__Impl
	rule__TFunction__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFunctionAccess().getDeclaredTypeAccessModifierAssignment_0()); }
	(rule__TFunction__DeclaredTypeAccessModifierAssignment_0)
	{ after(grammarAccess.getTFunctionAccess().getDeclaredTypeAccessModifierAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFunction__Group__1__Impl
	rule__TFunction__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFunctionAccess().getDeclaredProvidedByRuntimeAssignment_1()); }
	(rule__TFunction__DeclaredProvidedByRuntimeAssignment_1)?
	{ after(grammarAccess.getTFunctionAccess().getDeclaredProvidedByRuntimeAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFunction__Group__2__Impl
	rule__TFunction__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFunctionAccess().getFunctionKeyword_2()); }
	Function
	{ after(grammarAccess.getTFunctionAccess().getFunctionKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFunction__Group__3__Impl
	rule__TFunction__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFunctionAccess().getTypeVariablesParserRuleCall_3()); }
	(ruleTypeVariables)?
	{ after(grammarAccess.getTFunctionAccess().getTypeVariablesParserRuleCall_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFunction__Group__4__Impl
	rule__TFunction__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFunctionAccess().getNameAssignment_4()); }
	(rule__TFunction__NameAssignment_4)
	{ after(grammarAccess.getTFunctionAccess().getNameAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFunction__Group__5__Impl
	rule__TFunction__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFunctionAccess().getTFormalParametersParserRuleCall_5()); }
	ruleTFormalParameters
	{ after(grammarAccess.getTFunctionAccess().getTFormalParametersParserRuleCall_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFunction__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFunctionAccess().getColonSepReturnTypeRefParserRuleCall_6()); }
	ruleColonSepReturnTypeRef
	{ after(grammarAccess.getTFunctionAccess().getColonSepReturnTypeRefParserRuleCall_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TEnum__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TEnum__Group__0__Impl
	rule__TEnum__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTEnumAccess().getDeclaredTypeAccessModifierAssignment_0()); }
	(rule__TEnum__DeclaredTypeAccessModifierAssignment_0)
	{ after(grammarAccess.getTEnumAccess().getDeclaredTypeAccessModifierAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TEnum__Group__1__Impl
	rule__TEnum__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTEnumAccess().getDeclaredProvidedByRuntimeAssignment_1()); }
	(rule__TEnum__DeclaredProvidedByRuntimeAssignment_1)?
	{ after(grammarAccess.getTEnumAccess().getDeclaredProvidedByRuntimeAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TEnum__Group__2__Impl
	rule__TEnum__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTEnumAccess().getEnumKeyword_2()); }
	Enum
	{ after(grammarAccess.getTEnumAccess().getEnumKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TEnum__Group__3__Impl
	rule__TEnum__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTEnumAccess().getNameAssignment_3()); }
	(rule__TEnum__NameAssignment_3)
	{ after(grammarAccess.getTEnumAccess().getNameAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TEnum__Group__4__Impl
	rule__TEnum__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTEnumAccess().getLeftCurlyBracketKeyword_4()); }
	LeftCurlyBracket
	{ after(grammarAccess.getTEnumAccess().getLeftCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TEnum__Group__5__Impl
	rule__TEnum__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTEnumAccess().getLiteralsAssignment_5()); }
	(rule__TEnum__LiteralsAssignment_5)
	{ after(grammarAccess.getTEnumAccess().getLiteralsAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TEnum__Group__6__Impl
	rule__TEnum__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTEnumAccess().getGroup_6()); }
	(rule__TEnum__Group_6__0)*
	{ after(grammarAccess.getTEnumAccess().getGroup_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TEnum__Group__7__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTEnumAccess().getRightCurlyBracketKeyword_7()); }
	RightCurlyBracket
	{ after(grammarAccess.getTEnumAccess().getRightCurlyBracketKeyword_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TEnum__Group_6__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TEnum__Group_6__0__Impl
	rule__TEnum__Group_6__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group_6__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTEnumAccess().getCommaKeyword_6_0()); }
	Comma
	{ after(grammarAccess.getTEnumAccess().getCommaKeyword_6_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group_6__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TEnum__Group_6__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__Group_6__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTEnumAccess().getLiteralsAssignment_6_1()); }
	(rule__TEnum__LiteralsAssignment_6_1)
	{ after(grammarAccess.getTEnumAccess().getLiteralsAssignment_6_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrayTypeExpression__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_0__0__Impl
	rule__ArrayTypeExpression__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefAction_0_0()); }
	()
	{ after(grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefAction_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_0__1__Impl
	rule__ArrayTypeExpression__Group_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getTypeArgsAssignment_0_1()); }
	(rule__ArrayTypeExpression__TypeArgsAssignment_0_1)
	{ after(grammarAccess.getArrayTypeExpressionAccess().getTypeArgsAssignment_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_0__2__Impl
	rule__ArrayTypeExpression__Group_0__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionAssignment_0_2()); }
	(rule__ArrayTypeExpression__ArrayTypeExpressionAssignment_0_2)
	{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionAssignment_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_0__3__Impl
	rule__ArrayTypeExpression__Group_0__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_0_3()); }
	RightSquareBracket
	{ after(grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_0_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_0__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getGroup_0_4()); }
	(rule__ArrayTypeExpression__Group_0_4__0)*
	{ after(grammarAccess.getArrayTypeExpressionAccess().getGroup_0_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrayTypeExpression__Group_0_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_0_4__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getGroup_0_4_0()); }
	(rule__ArrayTypeExpression__Group_0_4_0__0)
	{ after(grammarAccess.getArrayTypeExpressionAccess().getGroup_0_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrayTypeExpression__Group_0_4_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_0_4_0__0__Impl
	rule__ArrayTypeExpression__Group_0_4_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0_4_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_0_4_0_0()); }
	()
	{ after(grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_0_4_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0_4_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_0_4_0__1__Impl
	rule__ArrayTypeExpression__Group_0_4_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0_4_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionAssignment_0_4_0_1()); }
	(rule__ArrayTypeExpression__ArrayTypeExpressionAssignment_0_4_0_1)
	{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionAssignment_0_4_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0_4_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_0_4_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_0_4_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_0_4_0_2()); }
	RightSquareBracket
	{ after(grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_0_4_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrayTypeExpression__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_1__0__Impl
	rule__ArrayTypeExpression__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefAction_1_0()); }
	()
	{ after(grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefAction_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_1__1__Impl
	rule__ArrayTypeExpression__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getLeftParenthesisKeyword_1_1()); }
	LeftParenthesis
	{ after(grammarAccess.getArrayTypeExpressionAccess().getLeftParenthesisKeyword_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_1__2__Impl
	rule__ArrayTypeExpression__Group_1__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getTypeArgsAssignment_1_2()); }
	(rule__ArrayTypeExpression__TypeArgsAssignment_1_2)
	{ after(grammarAccess.getArrayTypeExpressionAccess().getTypeArgsAssignment_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_1__3__Impl
	rule__ArrayTypeExpression__Group_1__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getRightParenthesisKeyword_1_3()); }
	RightParenthesis
	{ after(grammarAccess.getArrayTypeExpressionAccess().getRightParenthesisKeyword_1_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_1__4__Impl
	rule__ArrayTypeExpression__Group_1__5
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionAssignment_1_4()); }
	(rule__ArrayTypeExpression__ArrayTypeExpressionAssignment_1_4)
	{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionAssignment_1_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_1__5__Impl
	rule__ArrayTypeExpression__Group_1__6
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_1_5()); }
	RightSquareBracket
	{ after(grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_1_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_1__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getGroup_1_6()); }
	(rule__ArrayTypeExpression__Group_1_6__0)*
	{ after(grammarAccess.getArrayTypeExpressionAccess().getGroup_1_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrayTypeExpression__Group_1_6__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_1_6__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1_6__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getGroup_1_6_0()); }
	(rule__ArrayTypeExpression__Group_1_6_0__0)
	{ after(grammarAccess.getArrayTypeExpressionAccess().getGroup_1_6_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrayTypeExpression__Group_1_6_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_1_6_0__0__Impl
	rule__ArrayTypeExpression__Group_1_6_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1_6_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_6_0_0()); }
	()
	{ after(grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_6_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1_6_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_1_6_0__1__Impl
	rule__ArrayTypeExpression__Group_1_6_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1_6_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionAssignment_1_6_0_1()); }
	(rule__ArrayTypeExpression__ArrayTypeExpressionAssignment_1_6_0_1)
	{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionAssignment_1_6_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1_6_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_1_6_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_1_6_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_1_6_0_2()); }
	RightSquareBracket
	{ after(grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_1_6_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrayTypeExpression__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_2__0__Impl
	rule__ArrayTypeExpression__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getPrimaryTypeExpressionParserRuleCall_2_0()); }
	rulePrimaryTypeExpression
	{ after(grammarAccess.getArrayTypeExpressionAccess().getPrimaryTypeExpressionParserRuleCall_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getGroup_2_1()); }
	(rule__ArrayTypeExpression__Group_2_1__0)*
	{ after(grammarAccess.getArrayTypeExpressionAccess().getGroup_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrayTypeExpression__Group_2_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_2_1__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_2_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getGroup_2_1_0()); }
	(rule__ArrayTypeExpression__Group_2_1_0__0)
	{ after(grammarAccess.getArrayTypeExpressionAccess().getGroup_2_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrayTypeExpression__Group_2_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_2_1_0__0__Impl
	rule__ArrayTypeExpression__Group_2_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_2_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_2_1_0_0()); }
	()
	{ after(grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_2_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_2_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_2_1_0__1__Impl
	rule__ArrayTypeExpression__Group_2_1_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_2_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionAssignment_2_1_0_1()); }
	(rule__ArrayTypeExpression__ArrayTypeExpressionAssignment_2_1_0_1)
	{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionAssignment_2_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_2_1_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayTypeExpression__Group_2_1_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__Group_2_1_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_2_1_0_2()); }
	RightSquareBracket
	{ after(grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_2_1_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__PrimaryTypeExpression__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimaryTypeExpression__Group_3__0__Impl
	rule__PrimaryTypeExpression__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimaryTypeExpression__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_3_0()); }
	LeftParenthesis
	{ after(grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimaryTypeExpression__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimaryTypeExpression__Group_3__1__Impl
	rule__PrimaryTypeExpression__Group_3__2
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimaryTypeExpression__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimaryTypeExpressionAccess().getTypeRefParserRuleCall_3_1()); }
	ruleTypeRef
	{ after(grammarAccess.getPrimaryTypeExpressionAccess().getTypeRefParserRuleCall_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimaryTypeExpression__Group_3__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimaryTypeExpression__Group_3__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimaryTypeExpression__Group_3__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimaryTypeExpressionAccess().getRightParenthesisKeyword_3_2()); }
	RightParenthesis
	{ after(grammarAccess.getPrimaryTypeExpressionAccess().getRightParenthesisKeyword_3_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeRefWithModifiers__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeRefWithModifiers__Group__0__Impl
	rule__TypeRefWithModifiers__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRefWithModifiers__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeRefWithModifiersAccess().getTypeRefWithoutModifiersParserRuleCall_0()); }
	ruleTypeRefWithoutModifiers
	{ after(grammarAccess.getTypeRefWithModifiersAccess().getTypeRefWithoutModifiersParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRefWithModifiers__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeRefWithModifiers__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRefWithModifiers__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeRefWithModifiersAccess().getFollowedByQuestionMarkAssignment_1()); }
	(rule__TypeRefWithModifiers__FollowedByQuestionMarkAssignment_1)?
	{ after(grammarAccess.getTypeRefWithModifiersAccess().getFollowedByQuestionMarkAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeRefWithoutModifiers__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeRefWithoutModifiers__Group_0__0__Impl
	rule__TypeRefWithoutModifiers__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRefWithoutModifiers__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeRefWithoutModifiersAccess().getAlternatives_0_0()); }
	(rule__TypeRefWithoutModifiers__Alternatives_0_0)
	{ after(grammarAccess.getTypeRefWithoutModifiersAccess().getAlternatives_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRefWithoutModifiers__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeRefWithoutModifiers__Group_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRefWithoutModifiers__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeRefWithoutModifiersAccess().getDynamicAssignment_0_1()); }
	(rule__TypeRefWithoutModifiers__DynamicAssignment_0_1)?
	{ after(grammarAccess.getTypeRefWithoutModifiersAccess().getDynamicAssignment_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ThisTypeRefNominal__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ThisTypeRefNominal__Group__0__Impl
	rule__ThisTypeRefNominal__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ThisTypeRefNominal__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThisTypeRefNominalAccess().getThisTypeRefNominalAction_0()); }
	()
	{ after(grammarAccess.getThisTypeRefNominalAccess().getThisTypeRefNominalAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ThisTypeRefNominal__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ThisTypeRefNominal__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ThisTypeRefNominal__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThisTypeRefNominalAccess().getThisKeyword_1()); }
	This_1
	{ after(grammarAccess.getThisTypeRefNominalAccess().getThisKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ThisTypeRefStructural__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ThisTypeRefStructural__Group__0__Impl
	rule__ThisTypeRefStructural__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ThisTypeRefStructural__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThisTypeRefStructuralAccess().getDefinedTypingStrategyAssignment_0()); }
	(rule__ThisTypeRefStructural__DefinedTypingStrategyAssignment_0)
	{ after(grammarAccess.getThisTypeRefStructuralAccess().getDefinedTypingStrategyAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ThisTypeRefStructural__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ThisTypeRefStructural__Group__1__Impl
	rule__ThisTypeRefStructural__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ThisTypeRefStructural__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThisTypeRefStructuralAccess().getThisKeyword_1()); }
	This_1
	{ after(grammarAccess.getThisTypeRefStructuralAccess().getThisKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ThisTypeRefStructural__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ThisTypeRefStructural__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ThisTypeRefStructural__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThisTypeRefStructuralAccess().getGroup_2()); }
	(rule__ThisTypeRefStructural__Group_2__0)?
	{ after(grammarAccess.getThisTypeRefStructuralAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ThisTypeRefStructural__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ThisTypeRefStructural__Group_2__0__Impl
	rule__ThisTypeRefStructural__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ThisTypeRefStructural__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThisTypeRefStructuralAccess().getWithKeyword_2_0()); }
	With
	{ after(grammarAccess.getThisTypeRefStructuralAccess().getWithKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ThisTypeRefStructural__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ThisTypeRefStructural__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ThisTypeRefStructural__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThisTypeRefStructuralAccess().getTStructMemberListParserRuleCall_2_1()); }
	ruleTStructMemberList
	{ after(grammarAccess.getThisTypeRefStructuralAccess().getTStructMemberListParserRuleCall_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FunctionTypeExpressionOLD__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group__0__Impl
	rule__FunctionTypeExpressionOLD__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getFunctionTypeExpressionAction_0()); }
	()
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getFunctionTypeExpressionAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group__1__Impl
	rule__FunctionTypeExpressionOLD__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_1()); }
	LeftCurlyBracket
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group__2__Impl
	rule__FunctionTypeExpressionOLD__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getGroup_2()); }
	(rule__FunctionTypeExpressionOLD__Group_2__0)?
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group__3__Impl
	rule__FunctionTypeExpressionOLD__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getFunctionKeyword_3()); }
	Function
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getFunctionKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group__4__Impl
	rule__FunctionTypeExpressionOLD__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getGroup_4()); }
	(rule__FunctionTypeExpressionOLD__Group_4__0)?
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group__5__Impl
	rule__FunctionTypeExpressionOLD__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftParenthesisKeyword_5()); }
	LeftParenthesis
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftParenthesisKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group__6__Impl
	rule__FunctionTypeExpressionOLD__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getTAnonymousFormalParameterListParserRuleCall_6()); }
	ruleTAnonymousFormalParameterList
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getTAnonymousFormalParameterListParserRuleCall_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group__7__Impl
	rule__FunctionTypeExpressionOLD__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getRightParenthesisKeyword_7()); }
	RightParenthesis
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getRightParenthesisKeyword_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group__8__Impl
	rule__FunctionTypeExpressionOLD__Group__9
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getColonSepReturnTypeRefParserRuleCall_8()); }
	(ruleColonSepReturnTypeRef)?
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getColonSepReturnTypeRefParserRuleCall_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__9
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group__9__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group__9__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_9()); }
	RightCurlyBracket
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_9()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FunctionTypeExpressionOLD__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group_2__0__Impl
	rule__FunctionTypeExpressionOLD__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getCommercialAtKeyword_2_0()); }
	CommercialAt
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getCommercialAtKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group_2__1__Impl
	rule__FunctionTypeExpressionOLD__Group_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getThisKeyword_2_1()); }
	This
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getThisKeyword_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group_2__2__Impl
	rule__FunctionTypeExpressionOLD__Group_2__3
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftParenthesisKeyword_2_2()); }
	LeftParenthesis
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftParenthesisKeyword_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_2__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group_2__3__Impl
	rule__FunctionTypeExpressionOLD__Group_2__4
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_2__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getDeclaredThisTypeAssignment_2_3()); }
	(rule__FunctionTypeExpressionOLD__DeclaredThisTypeAssignment_2_3)
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getDeclaredThisTypeAssignment_2_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_2__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group_2__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_2__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getRightParenthesisKeyword_2_4()); }
	RightParenthesis
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getRightParenthesisKeyword_2_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FunctionTypeExpressionOLD__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group_4__0__Impl
	rule__FunctionTypeExpressionOLD__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getLessThanSignKeyword_4_0()); }
	LessThanSign
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getLessThanSignKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group_4__1__Impl
	rule__FunctionTypeExpressionOLD__Group_4__2
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsAssignment_4_1()); }
	(rule__FunctionTypeExpressionOLD__OwnedTypeVarsAssignment_4_1)
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsAssignment_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_4__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group_4__2__Impl
	rule__FunctionTypeExpressionOLD__Group_4__3
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_4__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getGroup_4_2()); }
	(rule__FunctionTypeExpressionOLD__Group_4_2__0)*
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getGroup_4_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_4__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group_4__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_4__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getGreaterThanSignKeyword_4_3()); }
	GreaterThanSign
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getGreaterThanSignKeyword_4_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FunctionTypeExpressionOLD__Group_4_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group_4_2__0__Impl
	rule__FunctionTypeExpressionOLD__Group_4_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_4_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getCommaKeyword_4_2_0()); }
	Comma
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getCommaKeyword_4_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_4_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionTypeExpressionOLD__Group_4_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__Group_4_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsAssignment_4_2_1()); }
	(rule__FunctionTypeExpressionOLD__OwnedTypeVarsAssignment_4_2_1)
	{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsAssignment_4_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrowFunctionTypeExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrowFunctionTypeExpression__Group__0__Impl
	rule__ArrowFunctionTypeExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrowFunctionTypeExpressionAccess().getGroup_0()); }
	(rule__ArrowFunctionTypeExpression__Group_0__0)
	{ after(grammarAccess.getArrowFunctionTypeExpressionAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrowFunctionTypeExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrowFunctionTypeExpressionAccess().getReturnTypeRefAssignment_1()); }
	(rule__ArrowFunctionTypeExpression__ReturnTypeRefAssignment_1)
	{ after(grammarAccess.getArrowFunctionTypeExpressionAccess().getReturnTypeRefAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrowFunctionTypeExpression__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrowFunctionTypeExpression__Group_0__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrowFunctionTypeExpressionAccess().getGroup_0_0()); }
	(rule__ArrowFunctionTypeExpression__Group_0_0__0)
	{ after(grammarAccess.getArrowFunctionTypeExpressionAccess().getGroup_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrowFunctionTypeExpression__Group_0_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrowFunctionTypeExpression__Group_0_0__0__Impl
	rule__ArrowFunctionTypeExpression__Group_0_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__Group_0_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrowFunctionTypeExpressionAccess().getFunctionTypeExpressionAction_0_0_0()); }
	()
	{ after(grammarAccess.getArrowFunctionTypeExpressionAccess().getFunctionTypeExpressionAction_0_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__Group_0_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrowFunctionTypeExpression__Group_0_0__1__Impl
	rule__ArrowFunctionTypeExpression__Group_0_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__Group_0_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrowFunctionTypeExpressionAccess().getLeftParenthesisKeyword_0_0_1()); }
	LeftParenthesis
	{ after(grammarAccess.getArrowFunctionTypeExpressionAccess().getLeftParenthesisKeyword_0_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__Group_0_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrowFunctionTypeExpression__Group_0_0__2__Impl
	rule__ArrowFunctionTypeExpression__Group_0_0__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__Group_0_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrowFunctionTypeExpressionAccess().getTAnonymousFormalParameterListParserRuleCall_0_0_2()); }
	ruleTAnonymousFormalParameterList
	{ after(grammarAccess.getArrowFunctionTypeExpressionAccess().getTAnonymousFormalParameterListParserRuleCall_0_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__Group_0_0__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrowFunctionTypeExpression__Group_0_0__3__Impl
	rule__ArrowFunctionTypeExpression__Group_0_0__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__Group_0_0__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrowFunctionTypeExpressionAccess().getRightParenthesisKeyword_0_0_3()); }
	RightParenthesis
	{ after(grammarAccess.getArrowFunctionTypeExpressionAccess().getRightParenthesisKeyword_0_0_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__Group_0_0__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrowFunctionTypeExpression__Group_0_0__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__Group_0_0__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrowFunctionTypeExpressionAccess().getEqualsSignGreaterThanSignKeyword_0_0_4()); }
	EqualsSignGreaterThanSign
	{ after(grammarAccess.getArrowFunctionTypeExpressionAccess().getEqualsSignGreaterThanSignKeyword_0_0_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TAnonymousFormalParameterList__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnonymousFormalParameterList__Group__0__Impl
	rule__TAnonymousFormalParameterList__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameterList__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsAssignment_0()); }
	(rule__TAnonymousFormalParameterList__FparsAssignment_0)
	{ after(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameterList__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnonymousFormalParameterList__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameterList__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnonymousFormalParameterListAccess().getGroup_1()); }
	(rule__TAnonymousFormalParameterList__Group_1__0)*
	{ after(grammarAccess.getTAnonymousFormalParameterListAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TAnonymousFormalParameterList__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnonymousFormalParameterList__Group_1__0__Impl
	rule__TAnonymousFormalParameterList__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameterList__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnonymousFormalParameterListAccess().getCommaKeyword_1_0()); }
	Comma
	{ after(grammarAccess.getTAnonymousFormalParameterListAccess().getCommaKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameterList__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnonymousFormalParameterList__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameterList__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsAssignment_1_1()); }
	(rule__TAnonymousFormalParameterList__FparsAssignment_1_1)
	{ after(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TAnonymousFormalParameter__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnonymousFormalParameter__Group__0__Impl
	rule__TAnonymousFormalParameter__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameter__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnonymousFormalParameterAccess().getVariadicAssignment_0()); }
	(rule__TAnonymousFormalParameter__VariadicAssignment_0)?
	{ after(grammarAccess.getTAnonymousFormalParameterAccess().getVariadicAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameter__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnonymousFormalParameter__Group__1__Impl
	rule__TAnonymousFormalParameter__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameter__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnonymousFormalParameterAccess().getAlternatives_1()); }
	(rule__TAnonymousFormalParameter__Alternatives_1)
	{ after(grammarAccess.getTAnonymousFormalParameterAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameter__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnonymousFormalParameter__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameter__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnonymousFormalParameterAccess().getDefaultFormalParameterParserRuleCall_2()); }
	ruleDefaultFormalParameter
	{ after(grammarAccess.getTAnonymousFormalParameterAccess().getDefaultFormalParameterParserRuleCall_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TAnonymousFormalParameter__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnonymousFormalParameter__Group_1_0__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameter__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnonymousFormalParameterAccess().getGroup_1_0_0()); }
	(rule__TAnonymousFormalParameter__Group_1_0_0__0)
	{ after(grammarAccess.getTAnonymousFormalParameterAccess().getGroup_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TAnonymousFormalParameter__Group_1_0_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnonymousFormalParameter__Group_1_0_0__0__Impl
	rule__TAnonymousFormalParameter__Group_1_0_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameter__Group_1_0_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnonymousFormalParameterAccess().getNameAssignment_1_0_0_0()); }
	(rule__TAnonymousFormalParameter__NameAssignment_1_0_0_0)
	{ after(grammarAccess.getTAnonymousFormalParameterAccess().getNameAssignment_1_0_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameter__Group_1_0_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAnonymousFormalParameter__Group_1_0_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameter__Group_1_0_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAnonymousFormalParameterAccess().getColonSepTypeRefParserRuleCall_1_0_0_1()); }
	(ruleColonSepTypeRef)
	{ after(grammarAccess.getTAnonymousFormalParameterAccess().getColonSepTypeRefParserRuleCall_1_0_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TFormalParameter__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFormalParameter__Group__0__Impl
	rule__TFormalParameter__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameter__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFormalParameterAccess().getVariadicAssignment_0()); }
	(rule__TFormalParameter__VariadicAssignment_0)?
	{ after(grammarAccess.getTFormalParameterAccess().getVariadicAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameter__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFormalParameter__Group__1__Impl
	rule__TFormalParameter__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameter__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFormalParameterAccess().getNameAssignment_1()); }
	(rule__TFormalParameter__NameAssignment_1)
	{ after(grammarAccess.getTFormalParameterAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameter__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFormalParameter__Group__2__Impl
	rule__TFormalParameter__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameter__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFormalParameterAccess().getColonSepTypeRefParserRuleCall_2()); }
	ruleColonSepTypeRef
	{ after(grammarAccess.getTFormalParameterAccess().getColonSepTypeRefParserRuleCall_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameter__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TFormalParameter__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameter__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTFormalParameterAccess().getDefaultFormalParameterParserRuleCall_3()); }
	ruleDefaultFormalParameter
	{ after(grammarAccess.getTFormalParameterAccess().getDefaultFormalParameterParserRuleCall_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__DefaultFormalParameter__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DefaultFormalParameter__Group__0__Impl
	rule__DefaultFormalParameter__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__DefaultFormalParameter__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDefaultFormalParameterAccess().getHasInitializerAssignmentAssignment_0()); }
	(rule__DefaultFormalParameter__HasInitializerAssignmentAssignment_0)
	{ after(grammarAccess.getDefaultFormalParameterAccess().getHasInitializerAssignmentAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DefaultFormalParameter__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DefaultFormalParameter__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__DefaultFormalParameter__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDefaultFormalParameterAccess().getAstInitializerAssignment_1()); }
	(rule__DefaultFormalParameter__AstInitializerAssignment_1)?
	{ after(grammarAccess.getDefaultFormalParameterAccess().getAstInitializerAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__UnionTypeExpressionOLD__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__UnionTypeExpressionOLD__Group__0__Impl
	rule__UnionTypeExpressionOLD__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getUnionTypeExpressionOLDAccess().getUnionTypeExpressionAction_0()); }
	()
	{ after(grammarAccess.getUnionTypeExpressionOLDAccess().getUnionTypeExpressionAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__UnionTypeExpressionOLD__Group__1__Impl
	rule__UnionTypeExpressionOLD__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getUnionTypeExpressionOLDAccess().getUnionKeyword_1()); }
	Union
	{ after(grammarAccess.getUnionTypeExpressionOLDAccess().getUnionKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__UnionTypeExpressionOLD__Group__2__Impl
	rule__UnionTypeExpressionOLD__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getUnionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_2()); }
	LeftCurlyBracket
	{ after(grammarAccess.getUnionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__UnionTypeExpressionOLD__Group__3__Impl
	rule__UnionTypeExpressionOLD__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsAssignment_3()); }
	(rule__UnionTypeExpressionOLD__TypeRefsAssignment_3)
	{ after(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__UnionTypeExpressionOLD__Group__4__Impl
	rule__UnionTypeExpressionOLD__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getUnionTypeExpressionOLDAccess().getGroup_4()); }
	(rule__UnionTypeExpressionOLD__Group_4__0)*
	{ after(grammarAccess.getUnionTypeExpressionOLDAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__UnionTypeExpressionOLD__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getUnionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_5()); }
	RightCurlyBracket
	{ after(grammarAccess.getUnionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__UnionTypeExpressionOLD__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__UnionTypeExpressionOLD__Group_4__0__Impl
	rule__UnionTypeExpressionOLD__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getUnionTypeExpressionOLDAccess().getCommaKeyword_4_0()); }
	Comma
	{ after(grammarAccess.getUnionTypeExpressionOLDAccess().getCommaKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__UnionTypeExpressionOLD__Group_4__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsAssignment_4_1()); }
	(rule__UnionTypeExpressionOLD__TypeRefsAssignment_4_1)
	{ after(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsAssignment_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__IntersectionTypeExpressionOLD__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IntersectionTypeExpressionOLD__Group__0__Impl
	rule__IntersectionTypeExpressionOLD__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntersectionTypeExpressionOLDAccess().getIntersectionTypeExpressionAction_0()); }
	()
	{ after(grammarAccess.getIntersectionTypeExpressionOLDAccess().getIntersectionTypeExpressionAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IntersectionTypeExpressionOLD__Group__1__Impl
	rule__IntersectionTypeExpressionOLD__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntersectionTypeExpressionOLDAccess().getIntersectionKeyword_1()); }
	Intersection
	{ after(grammarAccess.getIntersectionTypeExpressionOLDAccess().getIntersectionKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IntersectionTypeExpressionOLD__Group__2__Impl
	rule__IntersectionTypeExpressionOLD__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntersectionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_2()); }
	LeftCurlyBracket
	{ after(grammarAccess.getIntersectionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IntersectionTypeExpressionOLD__Group__3__Impl
	rule__IntersectionTypeExpressionOLD__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsAssignment_3()); }
	(rule__IntersectionTypeExpressionOLD__TypeRefsAssignment_3)
	{ after(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IntersectionTypeExpressionOLD__Group__4__Impl
	rule__IntersectionTypeExpressionOLD__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntersectionTypeExpressionOLDAccess().getGroup_4()); }
	(rule__IntersectionTypeExpressionOLD__Group_4__0)*
	{ after(grammarAccess.getIntersectionTypeExpressionOLDAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IntersectionTypeExpressionOLD__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntersectionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_5()); }
	RightCurlyBracket
	{ after(grammarAccess.getIntersectionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__IntersectionTypeExpressionOLD__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IntersectionTypeExpressionOLD__Group_4__0__Impl
	rule__IntersectionTypeExpressionOLD__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntersectionTypeExpressionOLDAccess().getCommaKeyword_4_0()); }
	Comma
	{ after(grammarAccess.getIntersectionTypeExpressionOLDAccess().getCommaKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IntersectionTypeExpressionOLD__Group_4__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsAssignment_4_1()); }
	(rule__IntersectionTypeExpressionOLD__TypeRefsAssignment_4_1)
	{ after(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsAssignment_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ParameterizedTypeRefNominal__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefNominal__Group__0__Impl
	rule__ParameterizedTypeRefNominal__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefNominal__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefNominalAccess().getAlternatives_0()); }
	(rule__ParameterizedTypeRefNominal__Alternatives_0)
	{ after(grammarAccess.getParameterizedTypeRefNominalAccess().getAlternatives_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefNominal__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefNominal__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefNominal__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefNominalAccess().getTypeArgumentsParserRuleCall_1()); }
	(ruleTypeArguments)?
	{ after(grammarAccess.getParameterizedTypeRefNominalAccess().getTypeArgumentsParserRuleCall_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ParameterizedTypeRefNominal__Group_0_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefNominal__Group_0_1__0__Impl
	rule__ParameterizedTypeRefNominal__Group_0_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefNominal__Group_0_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefNominalAccess().getVersionedParameterizedTypeRefAction_0_1_0()); }
	()
	{ after(grammarAccess.getParameterizedTypeRefNominalAccess().getVersionedParameterizedTypeRefAction_0_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefNominal__Group_0_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefNominal__Group_0_1__1__Impl
	rule__ParameterizedTypeRefNominal__Group_0_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefNominal__Group_0_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefNominalAccess().getTypeReferenceParserRuleCall_0_1_1()); }
	ruleTypeReference
	{ after(grammarAccess.getParameterizedTypeRefNominalAccess().getTypeReferenceParserRuleCall_0_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefNominal__Group_0_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefNominal__Group_0_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefNominal__Group_0_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefNominalAccess().getVersionRequestParserRuleCall_0_1_2()); }
	ruleVersionRequest
	{ after(grammarAccess.getParameterizedTypeRefNominalAccess().getVersionRequestParserRuleCall_0_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ParameterizedTypeRefStructural__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefStructural__Group__0__Impl
	rule__ParameterizedTypeRefStructural__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getAlternatives_0()); }
	(rule__ParameterizedTypeRefStructural__Alternatives_0)
	{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getAlternatives_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefStructural__Group__1__Impl
	rule__ParameterizedTypeRefStructural__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeArgumentsParserRuleCall_1()); }
	(ruleTypeArguments)?
	{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeArgumentsParserRuleCall_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefStructural__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup_2()); }
	(rule__ParameterizedTypeRefStructural__Group_2__0)?
	{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ParameterizedTypeRefStructural__Group_0_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefStructural__Group_0_0__0__Impl
	rule__ParameterizedTypeRefStructural__Group_0_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group_0_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyAssignment_0_0_0()); }
	(rule__ParameterizedTypeRefStructural__DefinedTypingStrategyAssignment_0_0_0)
	{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyAssignment_0_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group_0_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefStructural__Group_0_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group_0_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeReferenceParserRuleCall_0_0_1()); }
	ruleTypeReference
	{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeReferenceParserRuleCall_0_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ParameterizedTypeRefStructural__Group_0_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefStructural__Group_0_1__0__Impl
	rule__ParameterizedTypeRefStructural__Group_0_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group_0_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getVersionedParameterizedTypeRefStructuralAction_0_1_0()); }
	()
	{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getVersionedParameterizedTypeRefStructuralAction_0_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group_0_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefStructural__Group_0_1__1__Impl
	rule__ParameterizedTypeRefStructural__Group_0_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group_0_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyAssignment_0_1_1()); }
	(rule__ParameterizedTypeRefStructural__DefinedTypingStrategyAssignment_0_1_1)
	{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyAssignment_0_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group_0_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefStructural__Group_0_1__2__Impl
	rule__ParameterizedTypeRefStructural__Group_0_1__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group_0_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeReferenceParserRuleCall_0_1_2()); }
	ruleTypeReference
	{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeReferenceParserRuleCall_0_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group_0_1__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefStructural__Group_0_1__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group_0_1__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getVersionRequestParserRuleCall_0_1_3()); }
	ruleVersionRequest
	{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getVersionRequestParserRuleCall_0_1_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ParameterizedTypeRefStructural__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefStructural__Group_2__0__Impl
	rule__ParameterizedTypeRefStructural__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getWithKeyword_2_0()); }
	With
	{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getWithKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ParameterizedTypeRefStructural__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getTStructMemberListParserRuleCall_2_1()); }
	ruleTStructMemberList
	{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getTStructMemberListParserRuleCall_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__IterableTypeExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IterableTypeExpression__Group__0__Impl
	rule__IterableTypeExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIterableTypeExpressionAccess().getIterableTypeExpressionAssignment_0()); }
	(rule__IterableTypeExpression__IterableTypeExpressionAssignment_0)
	{ after(grammarAccess.getIterableTypeExpressionAccess().getIterableTypeExpressionAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IterableTypeExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIterableTypeExpressionAccess().getAlternatives_1()); }
	(rule__IterableTypeExpression__Alternatives_1)
	{ after(grammarAccess.getIterableTypeExpressionAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__IterableTypeExpression__Group_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IterableTypeExpression__Group_1_1__0__Impl
	rule__IterableTypeExpression__Group_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__Group_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsAssignment_1_1_0()); }
	(rule__IterableTypeExpression__TypeArgsAssignment_1_1_0)
	{ after(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsAssignment_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__Group_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IterableTypeExpression__Group_1_1__1__Impl
	rule__IterableTypeExpression__Group_1_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__Group_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIterableTypeExpressionAccess().getGroup_1_1_1()); }
	(rule__IterableTypeExpression__Group_1_1_1__0)*
	{ after(grammarAccess.getIterableTypeExpressionAccess().getGroup_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__Group_1_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IterableTypeExpression__Group_1_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__Group_1_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIterableTypeExpressionAccess().getRightSquareBracketKeyword_1_1_2()); }
	RightSquareBracket
	{ after(grammarAccess.getIterableTypeExpressionAccess().getRightSquareBracketKeyword_1_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__IterableTypeExpression__Group_1_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IterableTypeExpression__Group_1_1_1__0__Impl
	rule__IterableTypeExpression__Group_1_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__Group_1_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIterableTypeExpressionAccess().getCommaKeyword_1_1_1_0()); }
	Comma
	{ after(grammarAccess.getIterableTypeExpressionAccess().getCommaKeyword_1_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__Group_1_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__IterableTypeExpression__Group_1_1_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__Group_1_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsAssignment_1_1_1_1()); }
	(rule__IterableTypeExpression__TypeArgsAssignment_1_1_1_1)
	{ after(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsAssignment_1_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__EmptyIterableTypeExpressionTail__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EmptyIterableTypeExpressionTail__Group__0__Impl
	rule__EmptyIterableTypeExpressionTail__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__EmptyIterableTypeExpressionTail__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEmptyIterableTypeExpressionTailAccess().getWildcardAction_0()); }
	()
	{ after(grammarAccess.getEmptyIterableTypeExpressionTailAccess().getWildcardAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EmptyIterableTypeExpressionTail__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EmptyIterableTypeExpressionTail__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__EmptyIterableTypeExpressionTail__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEmptyIterableTypeExpressionTailAccess().getRightSquareBracketKeyword_1()); }
	RightSquareBracket
	{ after(grammarAccess.getEmptyIterableTypeExpressionTailAccess().getRightSquareBracketKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeArguments__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeArguments__Group__0__Impl
	rule__TypeArguments__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArguments__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeArgumentsAccess().getLessThanSignKeyword_0()); }
	LessThanSign
	{ after(grammarAccess.getTypeArgumentsAccess().getLessThanSignKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArguments__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeArguments__Group__1__Impl
	rule__TypeArguments__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArguments__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeArgumentsAccess().getTypeArgsAssignment_1()); }
	(rule__TypeArguments__TypeArgsAssignment_1)
	{ after(grammarAccess.getTypeArgumentsAccess().getTypeArgsAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArguments__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeArguments__Group__2__Impl
	rule__TypeArguments__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArguments__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeArgumentsAccess().getGroup_2()); }
	(rule__TypeArguments__Group_2__0)*
	{ after(grammarAccess.getTypeArgumentsAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArguments__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeArguments__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArguments__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeArgumentsAccess().getGreaterThanSignKeyword_3()); }
	GreaterThanSign
	{ after(grammarAccess.getTypeArgumentsAccess().getGreaterThanSignKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeArguments__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeArguments__Group_2__0__Impl
	rule__TypeArguments__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArguments__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeArgumentsAccess().getCommaKeyword_2_0()); }
	Comma
	{ after(grammarAccess.getTypeArgumentsAccess().getCommaKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArguments__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeArguments__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArguments__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeArgumentsAccess().getTypeArgsAssignment_2_1()); }
	(rule__TypeArguments__TypeArgsAssignment_2_1)
	{ after(grammarAccess.getTypeArgumentsAccess().getTypeArgsAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TStructMemberList__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMemberList__Group__0__Impl
	rule__TStructMemberList__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMemberList__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMemberListAccess().getLeftCurlyBracketKeyword_0()); }
	LeftCurlyBracket
	{ after(grammarAccess.getTStructMemberListAccess().getLeftCurlyBracketKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMemberList__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMemberList__Group__1__Impl
	rule__TStructMemberList__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMemberList__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMemberListAccess().getGroup_1()); }
	(rule__TStructMemberList__Group_1__0)*
	{ after(grammarAccess.getTStructMemberListAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMemberList__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMemberList__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMemberList__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMemberListAccess().getRightCurlyBracketKeyword_2()); }
	RightCurlyBracket
	{ after(grammarAccess.getTStructMemberListAccess().getRightCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TStructMemberList__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMemberList__Group_1__0__Impl
	rule__TStructMemberList__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMemberList__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMemberListAccess().getAstStructuralMembersAssignment_1_0()); }
	(rule__TStructMemberList__AstStructuralMembersAssignment_1_0)
	{ after(grammarAccess.getTStructMemberListAccess().getAstStructuralMembersAssignment_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMemberList__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMemberList__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMemberList__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMemberListAccess().getAlternatives_1_1()); }
	(rule__TStructMemberList__Alternatives_1_1)?
	{ after(grammarAccess.getTStructMemberListAccess().getAlternatives_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TStructMethod__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMethod__Group__0__Impl
	rule__TStructMethod__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMethodAccess().getGroup_0()); }
	(rule__TStructMethod__Group_0__0)
	{ after(grammarAccess.getTStructMethodAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMethod__Group__1__Impl
	rule__TStructMethod__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMethodAccess().getTAnonymousFormalParameterListParserRuleCall_1()); }
	ruleTAnonymousFormalParameterList
	{ after(grammarAccess.getTStructMethodAccess().getTAnonymousFormalParameterListParserRuleCall_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMethod__Group__2__Impl
	rule__TStructMethod__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMethodAccess().getRightParenthesisKeyword_2()); }
	RightParenthesis
	{ after(grammarAccess.getTStructMethodAccess().getRightParenthesisKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMethod__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMethodAccess().getColonSepReturnTypeRefParserRuleCall_3()); }
	(ruleColonSepReturnTypeRef)?
	{ after(grammarAccess.getTStructMethodAccess().getColonSepReturnTypeRefParserRuleCall_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TStructMethod__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMethod__Group_0__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMethodAccess().getGroup_0_0()); }
	(rule__TStructMethod__Group_0_0__0)
	{ after(grammarAccess.getTStructMethodAccess().getGroup_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TStructMethod__Group_0_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMethod__Group_0_0__0__Impl
	rule__TStructMethod__Group_0_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group_0_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMethodAccess().getTStructMethodAction_0_0_0()); }
	()
	{ after(grammarAccess.getTStructMethodAccess().getTStructMethodAction_0_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group_0_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMethod__Group_0_0__1__Impl
	rule__TStructMethod__Group_0_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group_0_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMethodAccess().getTypeVariablesParserRuleCall_0_0_1()); }
	(ruleTypeVariables)?
	{ after(grammarAccess.getTStructMethodAccess().getTypeVariablesParserRuleCall_0_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group_0_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMethod__Group_0_0__2__Impl
	rule__TStructMethod__Group_0_0__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group_0_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMethodAccess().getNameAssignment_0_0_2()); }
	(rule__TStructMethod__NameAssignment_0_0_2)
	{ after(grammarAccess.getTStructMethodAccess().getNameAssignment_0_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group_0_0__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructMethod__Group_0_0__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__Group_0_0__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructMethodAccess().getLeftParenthesisKeyword_0_0_3()); }
	LeftParenthesis
	{ after(grammarAccess.getTStructMethodAccess().getLeftParenthesisKeyword_0_0_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeVariables__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeVariables__Group__0__Impl
	rule__TypeVariables__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariables__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeVariablesAccess().getLessThanSignKeyword_0()); }
	LessThanSign
	{ after(grammarAccess.getTypeVariablesAccess().getLessThanSignKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariables__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeVariables__Group__1__Impl
	rule__TypeVariables__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariables__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeVariablesAccess().getTypeVarsAssignment_1()); }
	(rule__TypeVariables__TypeVarsAssignment_1)
	{ after(grammarAccess.getTypeVariablesAccess().getTypeVarsAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariables__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeVariables__Group__2__Impl
	rule__TypeVariables__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariables__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeVariablesAccess().getGroup_2()); }
	(rule__TypeVariables__Group_2__0)*
	{ after(grammarAccess.getTypeVariablesAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariables__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeVariables__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariables__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeVariablesAccess().getGreaterThanSignKeyword_3()); }
	GreaterThanSign
	{ after(grammarAccess.getTypeVariablesAccess().getGreaterThanSignKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeVariables__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeVariables__Group_2__0__Impl
	rule__TypeVariables__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariables__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeVariablesAccess().getCommaKeyword_2_0()); }
	Comma
	{ after(grammarAccess.getTypeVariablesAccess().getCommaKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariables__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeVariables__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariables__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeVariablesAccess().getTypeVarsAssignment_2_1()); }
	(rule__TypeVariables__TypeVarsAssignment_2_1)
	{ after(grammarAccess.getTypeVariablesAccess().getTypeVarsAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ColonSepDeclaredTypeRef__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ColonSepDeclaredTypeRef__Group__0__Impl
	rule__ColonSepDeclaredTypeRef__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ColonSepDeclaredTypeRef__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getColonSepDeclaredTypeRefAccess().getColonKeyword_0()); }
	Colon
	{ after(grammarAccess.getColonSepDeclaredTypeRefAccess().getColonKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ColonSepDeclaredTypeRef__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ColonSepDeclaredTypeRef__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ColonSepDeclaredTypeRef__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getColonSepDeclaredTypeRefAccess().getDeclaredTypeRefAssignment_1()); }
	(rule__ColonSepDeclaredTypeRef__DeclaredTypeRefAssignment_1)
	{ after(grammarAccess.getColonSepDeclaredTypeRefAccess().getDeclaredTypeRefAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ColonSepTypeRef__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ColonSepTypeRef__Group__0__Impl
	rule__ColonSepTypeRef__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ColonSepTypeRef__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getColonSepTypeRefAccess().getColonKeyword_0()); }
	Colon
	{ after(grammarAccess.getColonSepTypeRefAccess().getColonKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ColonSepTypeRef__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ColonSepTypeRef__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ColonSepTypeRef__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getColonSepTypeRefAccess().getTypeRefAssignment_1()); }
	(rule__ColonSepTypeRef__TypeRefAssignment_1)
	{ after(grammarAccess.getColonSepTypeRefAccess().getTypeRefAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ColonSepReturnTypeRef__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ColonSepReturnTypeRef__Group__0__Impl
	rule__ColonSepReturnTypeRef__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ColonSepReturnTypeRef__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getColonSepReturnTypeRefAccess().getColonKeyword_0()); }
	Colon
	{ after(grammarAccess.getColonSepReturnTypeRefAccess().getColonKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ColonSepReturnTypeRef__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ColonSepReturnTypeRef__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ColonSepReturnTypeRef__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getColonSepReturnTypeRefAccess().getReturnTypeRefAssignment_1()); }
	(rule__ColonSepReturnTypeRef__ReturnTypeRefAssignment_1)
	{ after(grammarAccess.getColonSepReturnTypeRefAccess().getReturnTypeRefAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TStructField__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructField__Group__0__Impl
	rule__TStructField__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructField__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructFieldAccess().getNameAssignment_0()); }
	(rule__TStructField__NameAssignment_0)
	{ after(grammarAccess.getTStructFieldAccess().getNameAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructField__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructField__Group__1__Impl
	rule__TStructField__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructField__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructFieldAccess().getOptionalAssignment_1()); }
	(rule__TStructField__OptionalAssignment_1)?
	{ after(grammarAccess.getTStructFieldAccess().getOptionalAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructField__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructField__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructField__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructFieldAccess().getColonSepTypeRefParserRuleCall_2()); }
	(ruleColonSepTypeRef)?
	{ after(grammarAccess.getTStructFieldAccess().getColonSepTypeRefParserRuleCall_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TStructGetter__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructGetter__Group__0__Impl
	rule__TStructGetter__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructGetterAccess().getGroup_0()); }
	(rule__TStructGetter__Group_0__0)
	{ after(grammarAccess.getTStructGetterAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructGetter__Group__1__Impl
	rule__TStructGetter__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructGetterAccess().getOptionalAssignment_1()); }
	(rule__TStructGetter__OptionalAssignment_1)?
	{ after(grammarAccess.getTStructGetterAccess().getOptionalAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructGetter__Group__2__Impl
	rule__TStructGetter__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructGetterAccess().getLeftParenthesisKeyword_2()); }
	LeftParenthesis
	{ after(grammarAccess.getTStructGetterAccess().getLeftParenthesisKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructGetter__Group__3__Impl
	rule__TStructGetter__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructGetterAccess().getRightParenthesisKeyword_3()); }
	RightParenthesis
	{ after(grammarAccess.getTStructGetterAccess().getRightParenthesisKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructGetter__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructGetterAccess().getColonSepDeclaredTypeRefParserRuleCall_4()); }
	(ruleColonSepDeclaredTypeRef)?
	{ after(grammarAccess.getTStructGetterAccess().getColonSepDeclaredTypeRefParserRuleCall_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TStructGetter__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructGetter__Group_0__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructGetterAccess().getGroup_0_0()); }
	(rule__TStructGetter__Group_0_0__0)
	{ after(grammarAccess.getTStructGetterAccess().getGroup_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TStructGetter__Group_0_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructGetter__Group_0_0__0__Impl
	rule__TStructGetter__Group_0_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group_0_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructGetterAccess().getTStructGetterAction_0_0_0()); }
	()
	{ after(grammarAccess.getTStructGetterAccess().getTStructGetterAction_0_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group_0_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructGetter__Group_0_0__1__Impl
	rule__TStructGetter__Group_0_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group_0_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructGetterAccess().getGetKeyword_0_0_1()); }
	Get
	{ after(grammarAccess.getTStructGetterAccess().getGetKeyword_0_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group_0_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructGetter__Group_0_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__Group_0_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructGetterAccess().getNameAssignment_0_0_2()); }
	(rule__TStructGetter__NameAssignment_0_0_2)
	{ after(grammarAccess.getTStructGetterAccess().getNameAssignment_0_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TStructSetter__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructSetter__Group__0__Impl
	rule__TStructSetter__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructSetterAccess().getGroup_0()); }
	(rule__TStructSetter__Group_0__0)
	{ after(grammarAccess.getTStructSetterAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructSetter__Group__1__Impl
	rule__TStructSetter__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructSetterAccess().getOptionalAssignment_1()); }
	(rule__TStructSetter__OptionalAssignment_1)?
	{ after(grammarAccess.getTStructSetterAccess().getOptionalAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructSetter__Group__2__Impl
	rule__TStructSetter__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructSetterAccess().getLeftParenthesisKeyword_2()); }
	LeftParenthesis
	{ after(grammarAccess.getTStructSetterAccess().getLeftParenthesisKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructSetter__Group__3__Impl
	rule__TStructSetter__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructSetterAccess().getFparAssignment_3()); }
	(rule__TStructSetter__FparAssignment_3)
	{ after(grammarAccess.getTStructSetterAccess().getFparAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructSetter__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructSetterAccess().getRightParenthesisKeyword_4()); }
	RightParenthesis
	{ after(grammarAccess.getTStructSetterAccess().getRightParenthesisKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TStructSetter__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructSetter__Group_0__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructSetterAccess().getGroup_0_0()); }
	(rule__TStructSetter__Group_0_0__0)
	{ after(grammarAccess.getTStructSetterAccess().getGroup_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TStructSetter__Group_0_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructSetter__Group_0_0__0__Impl
	rule__TStructSetter__Group_0_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group_0_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructSetterAccess().getTStructSetterAction_0_0_0()); }
	()
	{ after(grammarAccess.getTStructSetterAccess().getTStructSetterAction_0_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group_0_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructSetter__Group_0_0__1__Impl
	rule__TStructSetter__Group_0_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group_0_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructSetterAccess().getSetKeyword_0_0_1()); }
	Set
	{ after(grammarAccess.getTStructSetterAccess().getSetKeyword_0_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group_0_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TStructSetter__Group_0_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__Group_0_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTStructSetterAccess().getNameAssignment_0_0_2()); }
	(rule__TStructSetter__NameAssignment_0_0_2)
	{ after(grammarAccess.getTStructSetterAccess().getNameAssignment_0_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypingStrategyUseSiteOperator__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypingStrategyUseSiteOperator__Group__0__Impl
	rule__TypingStrategyUseSiteOperator__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypingStrategyUseSiteOperator__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getTildeKeyword_0()); }
	Tilde
	{ after(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getTildeKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypingStrategyUseSiteOperator__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypingStrategyUseSiteOperator__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypingStrategyUseSiteOperator__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getAlternatives_1()); }
	(rule__TypingStrategyUseSiteOperator__Alternatives_1)?
	{ after(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeTypeRef__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeTypeRef__Group__0__Impl
	rule__TypeTypeRef__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeTypeRef__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeTypeRefAccess().getTypeTypeRefAction_0()); }
	()
	{ after(grammarAccess.getTypeTypeRefAccess().getTypeTypeRefAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeTypeRef__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeTypeRef__Group__1__Impl
	rule__TypeTypeRef__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeTypeRef__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeTypeRefAccess().getAlternatives_1()); }
	(rule__TypeTypeRef__Alternatives_1)
	{ after(grammarAccess.getTypeTypeRefAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeTypeRef__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeTypeRef__Group__2__Impl
	rule__TypeTypeRef__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeTypeRef__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeTypeRefAccess().getLeftCurlyBracketKeyword_2()); }
	LeftCurlyBracket
	{ after(grammarAccess.getTypeTypeRefAccess().getLeftCurlyBracketKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeTypeRef__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeTypeRef__Group__3__Impl
	rule__TypeTypeRef__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeTypeRef__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeTypeRefAccess().getTypeArgAssignment_3()); }
	(rule__TypeTypeRef__TypeArgAssignment_3)
	{ after(grammarAccess.getTypeTypeRefAccess().getTypeArgAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeTypeRef__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeTypeRef__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeTypeRef__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeTypeRefAccess().getRightCurlyBracketKeyword_4()); }
	RightCurlyBracket
	{ after(grammarAccess.getTypeTypeRefAccess().getRightCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__WildcardOldNotation__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardOldNotation__Group__0__Impl
	rule__WildcardOldNotation__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardOldNotationAccess().getGroup_0()); }
	(rule__WildcardOldNotation__Group_0__0)
	{ after(grammarAccess.getWildcardOldNotationAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardOldNotation__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardOldNotationAccess().getAlternatives_1()); }
	(rule__WildcardOldNotation__Alternatives_1)?
	{ after(grammarAccess.getWildcardOldNotationAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__WildcardOldNotation__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardOldNotation__Group_0__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardOldNotationAccess().getGroup_0_0()); }
	(rule__WildcardOldNotation__Group_0_0__0)
	{ after(grammarAccess.getWildcardOldNotationAccess().getGroup_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__WildcardOldNotation__Group_0_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardOldNotation__Group_0_0__0__Impl
	rule__WildcardOldNotation__Group_0_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Group_0_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardOldNotationAccess().getWildcardAction_0_0_0()); }
	()
	{ after(grammarAccess.getWildcardOldNotationAccess().getWildcardAction_0_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Group_0_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardOldNotation__Group_0_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Group_0_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardOldNotationAccess().getQuestionMarkKeyword_0_0_1()); }
	QuestionMark
	{ after(grammarAccess.getWildcardOldNotationAccess().getQuestionMarkKeyword_0_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__WildcardOldNotation__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardOldNotation__Group_1_0__0__Impl
	rule__WildcardOldNotation__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardOldNotationAccess().getExtendsKeyword_1_0_0()); }
	Extends
	{ after(grammarAccess.getWildcardOldNotationAccess().getExtendsKeyword_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardOldNotation__Group_1_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardOldNotationAccess().getDeclaredUpperBoundAssignment_1_0_1()); }
	(rule__WildcardOldNotation__DeclaredUpperBoundAssignment_1_0_1)
	{ after(grammarAccess.getWildcardOldNotationAccess().getDeclaredUpperBoundAssignment_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__WildcardOldNotation__Group_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardOldNotation__Group_1_1__0__Impl
	rule__WildcardOldNotation__Group_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Group_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardOldNotationAccess().getSuperKeyword_1_1_0()); }
	Super
	{ after(grammarAccess.getWildcardOldNotationAccess().getSuperKeyword_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Group_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardOldNotation__Group_1_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__Group_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardOldNotationAccess().getDeclaredLowerBoundAssignment_1_1_1()); }
	(rule__WildcardOldNotation__DeclaredLowerBoundAssignment_1_1_1)
	{ after(grammarAccess.getWildcardOldNotationAccess().getDeclaredLowerBoundAssignment_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__WildcardOldNotationWithoutBound__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardOldNotationWithoutBound__Group__0__Impl
	rule__WildcardOldNotationWithoutBound__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotationWithoutBound__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardOldNotationWithoutBoundAccess().getWildcardAction_0()); }
	()
	{ after(grammarAccess.getWildcardOldNotationWithoutBoundAccess().getWildcardAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotationWithoutBound__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardOldNotationWithoutBound__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotationWithoutBound__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardOldNotationWithoutBoundAccess().getQuestionMarkKeyword_1()); }
	QuestionMark
	{ after(grammarAccess.getWildcardOldNotationWithoutBoundAccess().getQuestionMarkKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__WildcardNewNotation__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardNewNotation__Group_0__0__Impl
	rule__WildcardNewNotation__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardNewNotation__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationAssignment_0_0()); }
	(rule__WildcardNewNotation__UsingInOutNotationAssignment_0_0)
	{ after(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationAssignment_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardNewNotation__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardNewNotation__Group_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardNewNotation__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardNewNotationAccess().getDeclaredUpperBoundAssignment_0_1()); }
	(rule__WildcardNewNotation__DeclaredUpperBoundAssignment_0_1)
	{ after(grammarAccess.getWildcardNewNotationAccess().getDeclaredUpperBoundAssignment_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__WildcardNewNotation__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardNewNotation__Group_1__0__Impl
	rule__WildcardNewNotation__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardNewNotation__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationAssignment_1_0()); }
	(rule__WildcardNewNotation__UsingInOutNotationAssignment_1_0)
	{ after(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationAssignment_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardNewNotation__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WildcardNewNotation__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardNewNotation__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardNewNotationAccess().getDeclaredLowerBoundAssignment_1_1()); }
	(rule__WildcardNewNotation__DeclaredLowerBoundAssignment_1_1)
	{ after(grammarAccess.getWildcardNewNotationAccess().getDeclaredLowerBoundAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


superTypeVariable__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	superTypeVariable__Group__0__Impl
	superTypeVariable__Group__1
;
finally {
	restoreStackSize(stackSize);
}

superTypeVariable__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getAlternatives_0()); }
	(superTypeVariable__Alternatives_0)?
	{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getAlternatives_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

superTypeVariable__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	superTypeVariable__Group__1__Impl
	superTypeVariable__Group__2
;
finally {
	restoreStackSize(stackSize);
}

superTypeVariable__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getNameAssignment_1()); }
	(superTypeVariable__NameAssignment_1)
	{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

superTypeVariable__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	superTypeVariable__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

superTypeVariable__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getGroup_2()); }
	(superTypeVariable__Group_2__0)?
	{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


superTypeVariable__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	superTypeVariable__Group_2__0__Impl
	superTypeVariable__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

superTypeVariable__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getExtendsKeyword_2_0()); }
	Extends
	{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getExtendsKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

superTypeVariable__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	superTypeVariable__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

superTypeVariable__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredUpperBoundAssignment_2_1()); }
	(superTypeVariable__DeclaredUpperBoundAssignment_2_1)
	{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredUpperBoundAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeDefs__TypesAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeDefsAccess().getTypesTypeParserRuleCall_0()); }
		ruleType
		{ after(grammarAccess.getTypeDefsAccess().getTypesTypeParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__NameAssignment_0_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTAnnotationAccess().getNameIDENTIFIERTerminalRuleCall_0_0_1_0()); }
		RULE_IDENTIFIER
		{ after(grammarAccess.getTAnnotationAccess().getNameIDENTIFIERTerminalRuleCall_0_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__ArgsAssignment_1_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTAnnotationAccess().getArgsTAnnotationArgumentParserRuleCall_1_1_0_0()); }
		ruleTAnnotationArgument
		{ after(grammarAccess.getTAnnotationAccess().getArgsTAnnotationArgumentParserRuleCall_1_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotation__ArgsAssignment_1_1_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTAnnotationAccess().getArgsTAnnotationArgumentParserRuleCall_1_1_1_1_0()); }
		ruleTAnnotationArgument
		{ after(grammarAccess.getTAnnotationAccess().getArgsTAnnotationArgumentParserRuleCall_1_1_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotationStringArgument__ValueAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTAnnotationStringArgumentAccess().getValueSTRINGTerminalRuleCall_0()); }
		RULE_STRING
		{ after(grammarAccess.getTAnnotationStringArgumentAccess().getValueSTRINGTerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnnotationTypeRefArgument__TypeRefAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTAnnotationTypeRefArgumentAccess().getTypeRefTypeRefParserRuleCall_0()); }
		ruleTypeRef
		{ after(grammarAccess.getTAnnotationTypeRefArgumentAccess().getTypeRefTypeRefParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__FollowedByQuestionMarkAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeRefAccess().getFollowedByQuestionMarkQuestionMarkKeyword_1_0()); }
		(
			{ before(grammarAccess.getTypeRefAccess().getFollowedByQuestionMarkQuestionMarkKeyword_1_0()); }
			QuestionMark
			{ after(grammarAccess.getTypeRefAccess().getFollowedByQuestionMarkQuestionMarkKeyword_1_0()); }
		)
		{ after(grammarAccess.getTypeRefAccess().getFollowedByQuestionMarkQuestionMarkKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPrimitiveTypeAccess().getNameVoidOrBindingIdentifierParserRuleCall_1_0()); }
		ruleVoidOrBindingIdentifier
		{ after(grammarAccess.getPrimitiveTypeAccess().getNameVoidOrBindingIdentifierParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__TypeVarsAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPrimitiveTypeAccess().getTypeVarsTypeVariableParserRuleCall_2_1_0()); }
		ruleTypeVariable
		{ after(grammarAccess.getPrimitiveTypeAccess().getTypeVarsTypeVariableParserRuleCall_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__DeclaredElementTypeAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPrimitiveTypeAccess().getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0()); }
		ruleParameterizedTypeRefNominal
		{ after(grammarAccess.getPrimitiveTypeAccess().getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__AutoboxedTypeAssignment_5_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeTClassifierCrossReference_5_1_0()); }
		(
			{ before(grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeTClassifierTypeReferenceNameParserRuleCall_5_1_0_1()); }
			ruleTypeReferenceName
			{ after(grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeTClassifierTypeReferenceNameParserRuleCall_5_1_0_1()); }
		)
		{ after(grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeTClassifierCrossReference_5_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__AssignmentCompatibleAssignment_6_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPrimitiveTypeAccess().getAssignmentCompatiblePrimitiveTypeCrossReference_6_1_0()); }
		(
			{ before(grammarAccess.getPrimitiveTypeAccess().getAssignmentCompatiblePrimitiveTypeTypeReferenceNameParserRuleCall_6_1_0_1()); }
			ruleTypeReferenceName
			{ after(grammarAccess.getPrimitiveTypeAccess().getAssignmentCompatiblePrimitiveTypeTypeReferenceNameParserRuleCall_6_1_0_1()); }
		)
		{ after(grammarAccess.getPrimitiveTypeAccess().getAssignmentCompatiblePrimitiveTypeCrossReference_6_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__AnyType__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAnyTypeAccess().getNameAnyKeyword_1_0()); }
		(
			{ before(grammarAccess.getAnyTypeAccess().getNameAnyKeyword_1_0()); }
			Any
			{ after(grammarAccess.getAnyTypeAccess().getNameAnyKeyword_1_0()); }
		)
		{ after(grammarAccess.getAnyTypeAccess().getNameAnyKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VoidType__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVoidTypeAccess().getNameVoidKeyword_1_0()); }
		(
			{ before(grammarAccess.getVoidTypeAccess().getNameVoidKeyword_1_0()); }
			Void
			{ after(grammarAccess.getVoidTypeAccess().getNameVoidKeyword_1_0()); }
		)
		{ after(grammarAccess.getVoidTypeAccess().getNameVoidKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__UndefinedType__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getUndefinedTypeAccess().getNameUndefinedKeyword_1_0()); }
		(
			{ before(grammarAccess.getUndefinedTypeAccess().getNameUndefinedKeyword_1_0()); }
			Undefined
			{ after(grammarAccess.getUndefinedTypeAccess().getNameUndefinedKeyword_1_0()); }
		)
		{ after(grammarAccess.getUndefinedTypeAccess().getNameUndefinedKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__NullType__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getNullTypeAccess().getNameNullKeyword_1_0()); }
		(
			{ before(grammarAccess.getNullTypeAccess().getNameNullKeyword_1_0()); }
			Null
			{ after(grammarAccess.getNullTypeAccess().getNameNullKeyword_1_0()); }
		)
		{ after(grammarAccess.getNullTypeAccess().getNameNullKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__DeclaredTypeAccessModifierAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTObjectPrototypeAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0()); }
		ruleTypeAccessModifier
		{ after(grammarAccess.getTObjectPrototypeAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__DeclaredProvidedByRuntimeAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTObjectPrototypeAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
		(
			{ before(grammarAccess.getTObjectPrototypeAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
			ProvidedByRuntime
			{ after(grammarAccess.getTObjectPrototypeAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
		)
		{ after(grammarAccess.getTObjectPrototypeAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__DeclaredFinalAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTObjectPrototypeAccess().getDeclaredFinalFinalKeyword_2_0()); }
		(
			{ before(grammarAccess.getTObjectPrototypeAccess().getDeclaredFinalFinalKeyword_2_0()); }
			Final
			{ after(grammarAccess.getTObjectPrototypeAccess().getDeclaredFinalFinalKeyword_2_0()); }
		)
		{ after(grammarAccess.getTObjectPrototypeAccess().getDeclaredFinalFinalKeyword_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__NameAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTObjectPrototypeAccess().getNameBindingTypesIdentifierParserRuleCall_4_0()); }
		ruleBindingTypesIdentifier
		{ after(grammarAccess.getTObjectPrototypeAccess().getNameBindingTypesIdentifierParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__SuperTypeAssignment_6_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTObjectPrototypeAccess().getSuperTypeParameterizedTypeRefNominalParserRuleCall_6_1_0()); }
		ruleParameterizedTypeRefNominal
		{ after(grammarAccess.getTObjectPrototypeAccess().getSuperTypeParameterizedTypeRefNominalParserRuleCall_6_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__DeclaredElementTypeAssignment_7_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTObjectPrototypeAccess().getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_7_1_0()); }
		ruleParameterizedTypeRefNominal
		{ after(grammarAccess.getTObjectPrototypeAccess().getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_7_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__AnnotationsAssignment_8
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTObjectPrototypeAccess().getAnnotationsTAnnotationParserRuleCall_8_0()); }
		ruleTAnnotation
		{ after(grammarAccess.getTObjectPrototypeAccess().getAnnotationsTAnnotationParserRuleCall_8_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__OwnedMembersAssignment_10
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersTMemberParserRuleCall_10_0()); }
		ruleTMember
		{ after(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersTMemberParserRuleCall_10_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__CallableCtorAssignment_11_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTObjectPrototypeAccess().getCallableCtorCallableCtorParserRuleCall_11_0_0()); }
		ruleCallableCtor
		{ after(grammarAccess.getTObjectPrototypeAccess().getCallableCtorCallableCtorParserRuleCall_11_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TObjectPrototype__OwnedMembersAssignment_11_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersTMemberParserRuleCall_11_1_0()); }
		ruleTMember
		{ after(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersTMemberParserRuleCall_11_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__NameAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVirtualBaseTypeAccess().getNameBindingTypesIdentifierParserRuleCall_2_0()); }
		ruleBindingTypesIdentifier
		{ after(grammarAccess.getVirtualBaseTypeAccess().getNameBindingTypesIdentifierParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__DeclaredElementTypeAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVirtualBaseTypeAccess().getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0()); }
		ruleParameterizedTypeRefNominal
		{ after(grammarAccess.getVirtualBaseTypeAccess().getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VirtualBaseType__OwnedMembersAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVirtualBaseTypeAccess().getOwnedMembersTMemberParserRuleCall_5_0()); }
		ruleTMember
		{ after(grammarAccess.getVirtualBaseTypeAccess().getOwnedMembersTMemberParserRuleCall_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__DeclaredTypeAccessModifierAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0()); }
		ruleTypeAccessModifier
		{ after(grammarAccess.getTClassAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__DeclaredProvidedByRuntimeAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
		(
			{ before(grammarAccess.getTClassAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
			ProvidedByRuntime
			{ after(grammarAccess.getTClassAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
		)
		{ after(grammarAccess.getTClassAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__DeclaredAbstractAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassAccess().getDeclaredAbstractAbstractKeyword_2_0()); }
		(
			{ before(grammarAccess.getTClassAccess().getDeclaredAbstractAbstractKeyword_2_0()); }
			Abstract
			{ after(grammarAccess.getTClassAccess().getDeclaredAbstractAbstractKeyword_2_0()); }
		)
		{ after(grammarAccess.getTClassAccess().getDeclaredAbstractAbstractKeyword_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__DeclaredFinalAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassAccess().getDeclaredFinalFinalKeyword_3_0()); }
		(
			{ before(grammarAccess.getTClassAccess().getDeclaredFinalFinalKeyword_3_0()); }
			Final
			{ after(grammarAccess.getTClassAccess().getDeclaredFinalFinalKeyword_3_0()); }
		)
		{ after(grammarAccess.getTClassAccess().getDeclaredFinalFinalKeyword_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__SuperClassRefAssignment_6_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassAccess().getSuperClassRefParameterizedTypeRefNominalParserRuleCall_6_1_0()); }
		ruleParameterizedTypeRefNominal
		{ after(grammarAccess.getTClassAccess().getSuperClassRefParameterizedTypeRefNominalParserRuleCall_6_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__ImplementedInterfaceRefsAssignment_7_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassAccess().getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_1_0()); }
		ruleParameterizedTypeRefNominal
		{ after(grammarAccess.getTClassAccess().getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__ImplementedInterfaceRefsAssignment_7_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassAccess().getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_2_1_0()); }
		ruleParameterizedTypeRefNominal
		{ after(grammarAccess.getTClassAccess().getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__AnnotationsAssignment_8
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassAccess().getAnnotationsTAnnotationParserRuleCall_8_0()); }
		ruleTAnnotation
		{ after(grammarAccess.getTClassAccess().getAnnotationsTAnnotationParserRuleCall_8_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__OwnedMembersAssignment_10
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassAccess().getOwnedMembersTMemberParserRuleCall_10_0()); }
		ruleTMember
		{ after(grammarAccess.getTClassAccess().getOwnedMembersTMemberParserRuleCall_10_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__CallableCtorAssignment_11_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassAccess().getCallableCtorCallableCtorParserRuleCall_11_0_0()); }
		ruleCallableCtor
		{ after(grammarAccess.getTClassAccess().getCallableCtorCallableCtorParserRuleCall_11_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClass__OwnedMembersAssignment_11_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassAccess().getOwnedMembersTMemberParserRuleCall_11_1_0()); }
		ruleTMember
		{ after(grammarAccess.getTClassAccess().getOwnedMembersTMemberParserRuleCall_11_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__DeclaredTypeAccessModifierAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTInterfaceAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0()); }
		ruleTypeAccessModifier
		{ after(grammarAccess.getTInterfaceAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__DeclaredProvidedByRuntimeAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTInterfaceAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
		(
			{ before(grammarAccess.getTInterfaceAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
			ProvidedByRuntime
			{ after(grammarAccess.getTInterfaceAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
		)
		{ after(grammarAccess.getTInterfaceAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__SuperInterfaceRefsAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_1_0()); }
		ruleParameterizedTypeRefNominal
		{ after(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__SuperInterfaceRefsAssignment_4_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_2_1_0()); }
		ruleParameterizedTypeRefNominal
		{ after(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__AnnotationsAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTInterfaceAccess().getAnnotationsTAnnotationParserRuleCall_5_0()); }
		ruleTAnnotation
		{ after(grammarAccess.getTInterfaceAccess().getAnnotationsTAnnotationParserRuleCall_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TInterface__OwnedMembersAssignment_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTInterfaceAccess().getOwnedMembersTMemberParserRuleCall_7_0()); }
		ruleTMember
		{ after(grammarAccess.getTInterfaceAccess().getOwnedMembersTMemberParserRuleCall_7_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariable__NameAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeVariableAccess().getNameIDENTIFIERTerminalRuleCall_0_0()); }
		RULE_IDENTIFIER
		{ after(grammarAccess.getTypeVariableAccess().getNameIDENTIFIERTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariable__DeclaredUpperBoundAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeVariableAccess().getDeclaredUpperBoundTypeRefParserRuleCall_1_1_0()); }
		ruleTypeRef
		{ after(grammarAccess.getTypeVariableAccess().getDeclaredUpperBoundTypeRefParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__TypingStrategyAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0()); }
		ruleTypingStrategyDefSiteOperator
		{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getNameBindingTypesIdentifierParserRuleCall_1_0()); }
		ruleBindingTypesIdentifier
		{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getNameBindingTypesIdentifierParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__TypeVarsAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsTypeVariableParserRuleCall_2_1_0()); }
		superTypeVariable
		{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsTypeVariableParserRuleCall_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TClassOrInterfaceHeader__TypeVarsAssignment_2_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsTypeVariableParserRuleCall_2_2_1_0()); }
		superTypeVariable
		{ after(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsTypeVariableParserRuleCall_2_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameters__FparsAssignment_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFormalParametersAccess().getFparsTFormalParameterParserRuleCall_1_0_0()); }
		ruleTFormalParameter
		{ after(grammarAccess.getTFormalParametersAccess().getFparsTFormalParameterParserRuleCall_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameters__FparsAssignment_1_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFormalParametersAccess().getFparsTFormalParameterParserRuleCall_1_1_1_0()); }
		ruleTFormalParameter
		{ after(grammarAccess.getTFormalParametersAccess().getFparsTFormalParameterParserRuleCall_1_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__DeclaredMemberAccessModifierAssignment_0_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTMethodAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0()); }
		ruleMemberAccessModifier
		{ after(grammarAccess.getTMethodAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__DeclaredAbstractAssignment_0_0_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTMethodAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0()); }
		(
			{ before(grammarAccess.getTMethodAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0()); }
			Abstract
			{ after(grammarAccess.getTMethodAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0()); }
		)
		{ after(grammarAccess.getTMethodAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__DeclaredStaticAssignment_0_0_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTMethodAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0()); }
		(
			{ before(grammarAccess.getTMethodAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0()); }
			Static
			{ after(grammarAccess.getTMethodAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0()); }
		)
		{ after(grammarAccess.getTMethodAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__NameAssignment_0_0_3_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTMethodAccess().getNameTypesIdentifierParserRuleCall_0_0_3_0_0()); }
		ruleTypesIdentifier
		{ after(grammarAccess.getTMethodAccess().getNameTypesIdentifierParserRuleCall_0_0_3_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TMethod__NameAssignment_0_0_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTMethodAccess().getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0()); }
		ruleTypesComputedPropertyName
		{ after(grammarAccess.getTMethodAccess().getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__DeclaredMemberAccessModifierAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFieldAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0()); }
		ruleMemberAccessModifier
		{ after(grammarAccess.getTFieldAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__DeclaredStaticAssignment_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFieldAccess().getDeclaredStaticStaticKeyword_1_0_0()); }
		(
			{ before(grammarAccess.getTFieldAccess().getDeclaredStaticStaticKeyword_1_0_0()); }
			Static
			{ after(grammarAccess.getTFieldAccess().getDeclaredStaticStaticKeyword_1_0_0()); }
		)
		{ after(grammarAccess.getTFieldAccess().getDeclaredStaticStaticKeyword_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__ConstAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFieldAccess().getConstConstKeyword_1_1_0()); }
		(
			{ before(grammarAccess.getTFieldAccess().getConstConstKeyword_1_1_0()); }
			Const
			{ after(grammarAccess.getTFieldAccess().getConstConstKeyword_1_1_0()); }
		)
		{ after(grammarAccess.getTFieldAccess().getConstConstKeyword_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__DeclaredFinalAssignment_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFieldAccess().getDeclaredFinalFinalKeyword_1_2_0()); }
		(
			{ before(grammarAccess.getTFieldAccess().getDeclaredFinalFinalKeyword_1_2_0()); }
			Final
			{ after(grammarAccess.getTFieldAccess().getDeclaredFinalFinalKeyword_1_2_0()); }
		)
		{ after(grammarAccess.getTFieldAccess().getDeclaredFinalFinalKeyword_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__NameAssignment_2_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFieldAccess().getNameTypesIdentifierParserRuleCall_2_0_0()); }
		ruleTypesIdentifier
		{ after(grammarAccess.getTFieldAccess().getNameTypesIdentifierParserRuleCall_2_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__NameAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFieldAccess().getNameTypesComputedPropertyNameParserRuleCall_2_1_0()); }
		ruleTypesComputedPropertyName
		{ after(grammarAccess.getTFieldAccess().getNameTypesComputedPropertyNameParserRuleCall_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TField__OptionalAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFieldAccess().getOptionalQuestionMarkKeyword_3_0()); }
		(
			{ before(grammarAccess.getTFieldAccess().getOptionalQuestionMarkKeyword_3_0()); }
			QuestionMark
			{ after(grammarAccess.getTFieldAccess().getOptionalQuestionMarkKeyword_3_0()); }
		)
		{ after(grammarAccess.getTFieldAccess().getOptionalQuestionMarkKeyword_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__DeclaredMemberAccessModifierAssignment_0_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTGetterAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0()); }
		ruleMemberAccessModifier
		{ after(grammarAccess.getTGetterAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__DeclaredAbstractAssignment_0_0_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTGetterAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0()); }
		(
			{ before(grammarAccess.getTGetterAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0()); }
			Abstract
			{ after(grammarAccess.getTGetterAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0()); }
		)
		{ after(grammarAccess.getTGetterAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__DeclaredStaticAssignment_0_0_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTGetterAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0()); }
		(
			{ before(grammarAccess.getTGetterAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0()); }
			Static
			{ after(grammarAccess.getTGetterAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0()); }
		)
		{ after(grammarAccess.getTGetterAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__NameAssignment_0_0_3_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTGetterAccess().getNameTypesIdentifierParserRuleCall_0_0_3_0_0()); }
		ruleTypesIdentifier
		{ after(grammarAccess.getTGetterAccess().getNameTypesIdentifierParserRuleCall_0_0_3_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__NameAssignment_0_0_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTGetterAccess().getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0()); }
		ruleTypesComputedPropertyName
		{ after(grammarAccess.getTGetterAccess().getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TGetter__OptionalAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTGetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
		(
			{ before(grammarAccess.getTGetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
			QuestionMark
			{ after(grammarAccess.getTGetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
		)
		{ after(grammarAccess.getTGetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__DeclaredMemberAccessModifierAssignment_0_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTSetterAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0()); }
		ruleMemberAccessModifier
		{ after(grammarAccess.getTSetterAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__DeclaredAbstractAssignment_0_0_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTSetterAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0()); }
		(
			{ before(grammarAccess.getTSetterAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0()); }
			Abstract
			{ after(grammarAccess.getTSetterAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0()); }
		)
		{ after(grammarAccess.getTSetterAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__DeclaredStaticAssignment_0_0_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTSetterAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0()); }
		(
			{ before(grammarAccess.getTSetterAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0()); }
			Static
			{ after(grammarAccess.getTSetterAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0()); }
		)
		{ after(grammarAccess.getTSetterAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__NameAssignment_0_0_3_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTSetterAccess().getNameTypesIdentifierParserRuleCall_0_0_3_0_0()); }
		ruleTypesIdentifier
		{ after(grammarAccess.getTSetterAccess().getNameTypesIdentifierParserRuleCall_0_0_3_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__NameAssignment_0_0_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTSetterAccess().getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0()); }
		ruleTypesComputedPropertyName
		{ after(grammarAccess.getTSetterAccess().getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__OptionalAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTSetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
		(
			{ before(grammarAccess.getTSetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
			QuestionMark
			{ after(grammarAccess.getTSetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
		)
		{ after(grammarAccess.getTSetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TSetter__FparAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTSetterAccess().getFparTFormalParameterParserRuleCall_3_0()); }
		ruleTFormalParameter
		{ after(grammarAccess.getTSetterAccess().getFparTFormalParameterParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__DeclaredTypeAccessModifierAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFunctionAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0()); }
		ruleTypeAccessModifier
		{ after(grammarAccess.getTFunctionAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__DeclaredProvidedByRuntimeAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFunctionAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
		(
			{ before(grammarAccess.getTFunctionAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
			ProvidedByRuntime
			{ after(grammarAccess.getTFunctionAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
		)
		{ after(grammarAccess.getTFunctionAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFunction__NameAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFunctionAccess().getNameBindingTypesIdentifierParserRuleCall_4_0()); }
		ruleBindingTypesIdentifier
		{ after(grammarAccess.getTFunctionAccess().getNameBindingTypesIdentifierParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__DeclaredTypeAccessModifierAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTEnumAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0()); }
		ruleTypeAccessModifier
		{ after(grammarAccess.getTEnumAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__DeclaredProvidedByRuntimeAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTEnumAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
		(
			{ before(grammarAccess.getTEnumAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
			ProvidedByRuntime
			{ after(grammarAccess.getTEnumAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
		)
		{ after(grammarAccess.getTEnumAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__NameAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTEnumAccess().getNameBindingTypesIdentifierParserRuleCall_3_0()); }
		ruleBindingTypesIdentifier
		{ after(grammarAccess.getTEnumAccess().getNameBindingTypesIdentifierParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__LiteralsAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTEnumAccess().getLiteralsTEnumLiteralParserRuleCall_5_0()); }
		ruleTEnumLiteral
		{ after(grammarAccess.getTEnumAccess().getLiteralsTEnumLiteralParserRuleCall_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnum__LiteralsAssignment_6_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTEnumAccess().getLiteralsTEnumLiteralParserRuleCall_6_1_0()); }
		ruleTEnumLiteral
		{ after(grammarAccess.getTEnumAccess().getLiteralsTEnumLiteralParserRuleCall_6_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TEnumLiteral__NameAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTEnumLiteralAccess().getNameIDENTIFIERTerminalRuleCall_0()); }
		RULE_IDENTIFIER
		{ after(grammarAccess.getTEnumLiteralAccess().getNameIDENTIFIERTerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__TypeArgsAssignment_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getArrayTypeExpressionAccess().getTypeArgsWildcardOldNotationWithoutBoundParserRuleCall_0_1_0()); }
		ruleWildcardOldNotationWithoutBound
		{ after(grammarAccess.getArrayTypeExpressionAccess().getTypeArgsWildcardOldNotationWithoutBoundParserRuleCall_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__ArrayTypeExpressionAssignment_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_0_2_0()); }
		(
			{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_0_2_0()); }
			LeftSquareBracket
			{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_0_2_0()); }
		)
		{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_0_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__ArrayTypeExpressionAssignment_0_4_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_0_4_0_1_0()); }
		(
			{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_0_4_0_1_0()); }
			LeftSquareBracket
			{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_0_4_0_1_0()); }
		)
		{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_0_4_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__TypeArgsAssignment_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getArrayTypeExpressionAccess().getTypeArgsWildcardParserRuleCall_1_2_0()); }
		ruleWildcard
		{ after(grammarAccess.getArrayTypeExpressionAccess().getTypeArgsWildcardParserRuleCall_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__ArrayTypeExpressionAssignment_1_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_1_4_0()); }
		(
			{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_1_4_0()); }
			LeftSquareBracket
			{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_1_4_0()); }
		)
		{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_1_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__ArrayTypeExpressionAssignment_1_6_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_1_6_0_1_0()); }
		(
			{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_1_6_0_1_0()); }
			LeftSquareBracket
			{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_1_6_0_1_0()); }
		)
		{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_1_6_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayTypeExpression__ArrayTypeExpressionAssignment_2_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_2_1_0_1_0()); }
		(
			{ before(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_2_1_0_1_0()); }
			LeftSquareBracket
			{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_2_1_0_1_0()); }
		)
		{ after(grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_2_1_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRefWithModifiers__FollowedByQuestionMarkAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeRefWithModifiersAccess().getFollowedByQuestionMarkQuestionMarkKeyword_1_0()); }
		(
			{ before(grammarAccess.getTypeRefWithModifiersAccess().getFollowedByQuestionMarkQuestionMarkKeyword_1_0()); }
			QuestionMark
			{ after(grammarAccess.getTypeRefWithModifiersAccess().getFollowedByQuestionMarkQuestionMarkKeyword_1_0()); }
		)
		{ after(grammarAccess.getTypeRefWithModifiersAccess().getFollowedByQuestionMarkQuestionMarkKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRefWithoutModifiers__DynamicAssignment_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeRefWithoutModifiersAccess().getDynamicPlusSignKeyword_0_1_0()); }
		(
			{ before(grammarAccess.getTypeRefWithoutModifiersAccess().getDynamicPlusSignKeyword_0_1_0()); }
			PlusSign
			{ after(grammarAccess.getTypeRefWithoutModifiersAccess().getDynamicPlusSignKeyword_0_1_0()); }
		)
		{ after(grammarAccess.getTypeRefWithoutModifiersAccess().getDynamicPlusSignKeyword_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ThisTypeRefStructural__DefinedTypingStrategyAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThisTypeRefStructuralAccess().getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0()); }
		ruleTypingStrategyUseSiteOperator
		{ after(grammarAccess.getThisTypeRefStructuralAccess().getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__DeclaredThisTypeAssignment_2_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getDeclaredThisTypeTypeRefFunctionTypeExpressionParserRuleCall_2_3_0()); }
		ruleTypeRefFunctionTypeExpression
		{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getDeclaredThisTypeTypeRefFunctionTypeExpressionParserRuleCall_2_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__OwnedTypeVarsAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsTypeVariableParserRuleCall_4_1_0()); }
		ruleTypeVariable
		{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsTypeVariableParserRuleCall_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionTypeExpressionOLD__OwnedTypeVarsAssignment_4_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsTypeVariableParserRuleCall_4_2_1_0()); }
		ruleTypeVariable
		{ after(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsTypeVariableParserRuleCall_4_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrowFunctionTypeExpression__ReturnTypeRefAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getArrowFunctionTypeExpressionAccess().getReturnTypeRefPrimaryTypeExpressionParserRuleCall_1_0()); }
		rulePrimaryTypeExpression
		{ after(grammarAccess.getArrowFunctionTypeExpressionAccess().getReturnTypeRefPrimaryTypeExpressionParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameterList__FparsAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsTAnonymousFormalParameterParserRuleCall_0_0()); }
		ruleTAnonymousFormalParameter
		{ after(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsTAnonymousFormalParameterParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameterList__FparsAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsTAnonymousFormalParameterParserRuleCall_1_1_0()); }
		ruleTAnonymousFormalParameter
		{ after(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsTAnonymousFormalParameterParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameter__VariadicAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTAnonymousFormalParameterAccess().getVariadicFullStopFullStopFullStopKeyword_0_0()); }
		(
			{ before(grammarAccess.getTAnonymousFormalParameterAccess().getVariadicFullStopFullStopFullStopKeyword_0_0()); }
			FullStopFullStopFullStop
			{ after(grammarAccess.getTAnonymousFormalParameterAccess().getVariadicFullStopFullStopFullStopKeyword_0_0()); }
		)
		{ after(grammarAccess.getTAnonymousFormalParameterAccess().getVariadicFullStopFullStopFullStopKeyword_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameter__NameAssignment_1_0_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTAnonymousFormalParameterAccess().getNameBindingIdentifierParserRuleCall_1_0_0_0_0()); }
		ruleBindingIdentifier
		{ after(grammarAccess.getTAnonymousFormalParameterAccess().getNameBindingIdentifierParserRuleCall_1_0_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAnonymousFormalParameter__TypeRefAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTAnonymousFormalParameterAccess().getTypeRefTypeRefParserRuleCall_1_1_0()); }
		ruleTypeRef
		{ after(grammarAccess.getTAnonymousFormalParameterAccess().getTypeRefTypeRefParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameter__VariadicAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFormalParameterAccess().getVariadicFullStopFullStopFullStopKeyword_0_0()); }
		(
			{ before(grammarAccess.getTFormalParameterAccess().getVariadicFullStopFullStopFullStopKeyword_0_0()); }
			FullStopFullStopFullStop
			{ after(grammarAccess.getTFormalParameterAccess().getVariadicFullStopFullStopFullStopKeyword_0_0()); }
		)
		{ after(grammarAccess.getTFormalParameterAccess().getVariadicFullStopFullStopFullStopKeyword_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TFormalParameter__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTFormalParameterAccess().getNameBindingIdentifierParserRuleCall_1_0()); }
		ruleBindingIdentifier
		{ after(grammarAccess.getTFormalParameterAccess().getNameBindingIdentifierParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__DefaultFormalParameter__HasInitializerAssignmentAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getDefaultFormalParameterAccess().getHasInitializerAssignmentEqualsSignKeyword_0_0()); }
		(
			{ before(grammarAccess.getDefaultFormalParameterAccess().getHasInitializerAssignmentEqualsSignKeyword_0_0()); }
			EqualsSign
			{ after(grammarAccess.getDefaultFormalParameterAccess().getHasInitializerAssignmentEqualsSignKeyword_0_0()); }
		)
		{ after(grammarAccess.getDefaultFormalParameterAccess().getHasInitializerAssignmentEqualsSignKeyword_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__DefaultFormalParameter__AstInitializerAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getDefaultFormalParameterAccess().getAstInitializerTypeReferenceNameParserRuleCall_1_0()); }
		ruleTypeReferenceName
		{ after(grammarAccess.getDefaultFormalParameterAccess().getAstInitializerTypeReferenceNameParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__TypeRefsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsTypeRefParserRuleCall_3_0()); }
		ruleTypeRef
		{ after(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsTypeRefParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__UnionTypeExpressionOLD__TypeRefsAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsTypeRefParserRuleCall_4_1_0()); }
		ruleTypeRef
		{ after(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsTypeRefParserRuleCall_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__TypeRefsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsTypeRefParserRuleCall_3_0()); }
		ruleTypeRef
		{ after(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsTypeRefParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__IntersectionTypeExpressionOLD__TypeRefsAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsTypeRefParserRuleCall_4_1_0()); }
		ruleTypeRef
		{ after(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsTypeRefParserRuleCall_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__DefinedTypingStrategyAssignment_0_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0_0_0()); }
		ruleTypingStrategyUseSiteOperator
		{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ParameterizedTypeRefStructural__DefinedTypingStrategyAssignment_0_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_1_1_0()); }
		ruleTypingStrategyUseSiteOperator
		{ after(grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__IterableTypeExpressionAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIterableTypeExpressionAccess().getIterableTypeExpressionLeftSquareBracketKeyword_0_0()); }
		(
			{ before(grammarAccess.getIterableTypeExpressionAccess().getIterableTypeExpressionLeftSquareBracketKeyword_0_0()); }
			LeftSquareBracket
			{ after(grammarAccess.getIterableTypeExpressionAccess().getIterableTypeExpressionLeftSquareBracketKeyword_0_0()); }
		)
		{ after(grammarAccess.getIterableTypeExpressionAccess().getIterableTypeExpressionLeftSquareBracketKeyword_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__TypeArgsAssignment_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsEmptyIterableTypeExpressionTailParserRuleCall_1_0_0()); }
		ruleEmptyIterableTypeExpressionTail
		{ after(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsEmptyIterableTypeExpressionTailParserRuleCall_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__TypeArgsAssignment_1_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsTypeArgumentParserRuleCall_1_1_0_0()); }
		ruleTypeArgument
		{ after(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsTypeArgumentParserRuleCall_1_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__IterableTypeExpression__TypeArgsAssignment_1_1_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsTypeArgumentParserRuleCall_1_1_1_1_0()); }
		ruleTypeArgument
		{ after(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsTypeArgumentParserRuleCall_1_1_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRequest__RequestedVersionAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionRequestAccess().getRequestedVersionVERSIONTerminalRuleCall_0()); }
		RULE_VERSION
		{ after(grammarAccess.getVersionRequestAccess().getRequestedVersionVERSIONTerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeReference__DeclaredTypeAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeReferenceAccess().getDeclaredTypeTypeCrossReference_0()); }
		(
			{ before(grammarAccess.getTypeReferenceAccess().getDeclaredTypeTypeTypeReferenceNameParserRuleCall_0_1()); }
			ruleTypeReferenceName
			{ after(grammarAccess.getTypeReferenceAccess().getDeclaredTypeTypeTypeReferenceNameParserRuleCall_0_1()); }
		)
		{ after(grammarAccess.getTypeReferenceAccess().getDeclaredTypeTypeCrossReference_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArguments__TypeArgsAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeArgumentsAccess().getTypeArgsTypeArgumentParserRuleCall_1_0()); }
		ruleTypeArgument
		{ after(grammarAccess.getTypeArgumentsAccess().getTypeArgsTypeArgumentParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeArguments__TypeArgsAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeArgumentsAccess().getTypeArgsTypeArgumentParserRuleCall_2_1_0()); }
		ruleTypeArgument
		{ after(grammarAccess.getTypeArgumentsAccess().getTypeArgsTypeArgumentParserRuleCall_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMemberList__AstStructuralMembersAssignment_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTStructMemberListAccess().getAstStructuralMembersTStructMemberParserRuleCall_1_0_0()); }
		ruleTStructMember
		{ after(grammarAccess.getTStructMemberListAccess().getAstStructuralMembersTStructMemberParserRuleCall_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructMethod__NameAssignment_0_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTStructMethodAccess().getNameIdentifierNameParserRuleCall_0_0_2_0()); }
		ruleIdentifierName
		{ after(grammarAccess.getTStructMethodAccess().getNameIdentifierNameParserRuleCall_0_0_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariables__TypeVarsAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeVariablesAccess().getTypeVarsTypeVariableParserRuleCall_1_0()); }
		ruleTypeVariable
		{ after(grammarAccess.getTypeVariablesAccess().getTypeVarsTypeVariableParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeVariables__TypeVarsAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeVariablesAccess().getTypeVarsTypeVariableParserRuleCall_2_1_0()); }
		ruleTypeVariable
		{ after(grammarAccess.getTypeVariablesAccess().getTypeVarsTypeVariableParserRuleCall_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ColonSepDeclaredTypeRef__DeclaredTypeRefAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getColonSepDeclaredTypeRefAccess().getDeclaredTypeRefTypeRefParserRuleCall_1_0()); }
		ruleTypeRef
		{ after(grammarAccess.getColonSepDeclaredTypeRefAccess().getDeclaredTypeRefTypeRefParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ColonSepTypeRef__TypeRefAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getColonSepTypeRefAccess().getTypeRefTypeRefParserRuleCall_1_0()); }
		ruleTypeRef
		{ after(grammarAccess.getColonSepTypeRefAccess().getTypeRefTypeRefParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ColonSepReturnTypeRef__ReturnTypeRefAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getColonSepReturnTypeRefAccess().getReturnTypeRefTypeRefParserRuleCall_1_0()); }
		ruleTypeRef
		{ after(grammarAccess.getColonSepReturnTypeRefAccess().getReturnTypeRefTypeRefParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructField__NameAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTStructFieldAccess().getNameIdentifierNameParserRuleCall_0_0()); }
		ruleIdentifierName
		{ after(grammarAccess.getTStructFieldAccess().getNameIdentifierNameParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructField__OptionalAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTStructFieldAccess().getOptionalQuestionMarkKeyword_1_0()); }
		(
			{ before(grammarAccess.getTStructFieldAccess().getOptionalQuestionMarkKeyword_1_0()); }
			QuestionMark
			{ after(grammarAccess.getTStructFieldAccess().getOptionalQuestionMarkKeyword_1_0()); }
		)
		{ after(grammarAccess.getTStructFieldAccess().getOptionalQuestionMarkKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__NameAssignment_0_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTStructGetterAccess().getNameIdentifierNameParserRuleCall_0_0_2_0()); }
		ruleIdentifierName
		{ after(grammarAccess.getTStructGetterAccess().getNameIdentifierNameParserRuleCall_0_0_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructGetter__OptionalAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTStructGetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
		(
			{ before(grammarAccess.getTStructGetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
			QuestionMark
			{ after(grammarAccess.getTStructGetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
		)
		{ after(grammarAccess.getTStructGetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__NameAssignment_0_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTStructSetterAccess().getNameIdentifierNameParserRuleCall_0_0_2_0()); }
		ruleIdentifierName
		{ after(grammarAccess.getTStructSetterAccess().getNameIdentifierNameParserRuleCall_0_0_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__OptionalAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTStructSetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
		(
			{ before(grammarAccess.getTStructSetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
			QuestionMark
			{ after(grammarAccess.getTStructSetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
		)
		{ after(grammarAccess.getTStructSetterAccess().getOptionalQuestionMarkKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TStructSetter__FparAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTStructSetterAccess().getFparTAnonymousFormalParameterParserRuleCall_3_0()); }
		ruleTAnonymousFormalParameter
		{ after(grammarAccess.getTStructSetterAccess().getFparTAnonymousFormalParameterParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeTypeRef__ConstructorRefAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeTypeRefAccess().getConstructorRefConstructorKeyword_1_1_0()); }
		(
			{ before(grammarAccess.getTypeTypeRefAccess().getConstructorRefConstructorKeyword_1_1_0()); }
			Constructor
			{ after(grammarAccess.getTypeTypeRefAccess().getConstructorRefConstructorKeyword_1_1_0()); }
		)
		{ after(grammarAccess.getTypeTypeRefAccess().getConstructorRefConstructorKeyword_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeTypeRef__TypeArgAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeTypeRefAccess().getTypeArgTypeArgInTypeTypeRefParserRuleCall_3_0()); }
		ruleTypeArgInTypeTypeRef
		{ after(grammarAccess.getTypeTypeRefAccess().getTypeArgTypeArgInTypeTypeRefParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__DeclaredUpperBoundAssignment_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWildcardOldNotationAccess().getDeclaredUpperBoundTypeRefParserRuleCall_1_0_1_0()); }
		ruleTypeRef
		{ after(grammarAccess.getWildcardOldNotationAccess().getDeclaredUpperBoundTypeRefParserRuleCall_1_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardOldNotation__DeclaredLowerBoundAssignment_1_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWildcardOldNotationAccess().getDeclaredLowerBoundTypeRefParserRuleCall_1_1_1_0()); }
		ruleTypeRef
		{ after(grammarAccess.getWildcardOldNotationAccess().getDeclaredLowerBoundTypeRefParserRuleCall_1_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardNewNotation__UsingInOutNotationAssignment_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationOutKeyword_0_0_0()); }
		(
			{ before(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationOutKeyword_0_0_0()); }
			Out
			{ after(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationOutKeyword_0_0_0()); }
		)
		{ after(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationOutKeyword_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardNewNotation__DeclaredUpperBoundAssignment_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWildcardNewNotationAccess().getDeclaredUpperBoundTypeRefParserRuleCall_0_1_0()); }
		ruleTypeRef
		{ after(grammarAccess.getWildcardNewNotationAccess().getDeclaredUpperBoundTypeRefParserRuleCall_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardNewNotation__UsingInOutNotationAssignment_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationInKeyword_1_0_0()); }
		(
			{ before(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationInKeyword_1_0_0()); }
			In
			{ after(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationInKeyword_1_0_0()); }
		)
		{ after(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationInKeyword_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WildcardNewNotation__DeclaredLowerBoundAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWildcardNewNotationAccess().getDeclaredLowerBoundTypeRefParserRuleCall_1_1_0()); }
		ruleTypeRef
		{ after(grammarAccess.getWildcardNewNotationAccess().getDeclaredLowerBoundTypeRefParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

superTypeVariable__DeclaredCovariantAssignment_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredCovariantOutKeyword_0_0_0()); }
		(
			{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredCovariantOutKeyword_0_0_0()); }
			Out
			{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredCovariantOutKeyword_0_0_0()); }
		)
		{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredCovariantOutKeyword_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

superTypeVariable__DeclaredContravariantAssignment_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredContravariantInKeyword_0_1_0()); }
		(
			{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredContravariantInKeyword_0_1_0()); }
			In
			{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredContravariantInKeyword_0_1_0()); }
		)
		{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredContravariantInKeyword_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

superTypeVariable__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getNameIDENTIFIERTerminalRuleCall_1_0()); }
		RULE_IDENTIFIER
		{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getNameIDENTIFIERTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

superTypeVariable__DeclaredUpperBoundAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredUpperBoundTypeRefParserRuleCall_2_1_0()); }
		ruleTypeRef
		{ after(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredUpperBoundTypeRefParserRuleCall_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}
