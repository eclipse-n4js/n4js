/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 by Bart Kiers (original author) and Alexandre Vitorelli (contributor -> ported to CSharp)
 * Copyright (c) 2017 by Ivan Kochurkin (Positive Technologies):
    added ECMAScript 6 support, cleared and transformed to the universal grammar.
 * Copyright (c) 2018 by Juan Alvarez (contributor -> ported to Go)
 * Copyright (c) 2019 by Andrii Artiushok (contributor -> added TypeScript support)
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
parser grammar TypeScriptParser;

options {
    tokenVocab=TypeScriptLexer;
    superClass=TypeScriptParserBase;
}


// SupportSyntax

initializer
    : '=' singleExpression
    ;

bindingPattern
    : arrayLiteral
    | objectLiteral
    ;

// TypeScript SPart
// A.1 Types

typeParameters
    : '<' typeParameterList? '>'
    ;

typeParameterList
    : typeParameter (',' typeParameter)*
    ;

typeParameter
    : identifierName constraint? ('=' defaultType)?
    ;

constraint
    : 'extends' typeRef
    ;

defaultType
    : typeRef
    ;

colonSepTypeRef:
	':' typeRef
;

typeRef: conditionalTypeRef;

conditionalTypeRef:
	unionTypeExpression ('extends' unionTypeExpression '?' conditionalTypeRef ':' conditionalTypeRef)?;

unionTypeExpression:
	'|'? intersectionTypeExpression ('|' intersectionTypeExpression)*;

intersectionTypeExpression:
	'&'? operatorTypeRef ('&' operatorTypeRef)*;

operatorTypeRef:
	typeOperator? arrayTypeExpression
	;

typeOperator: Keyof | Unique | ReadOnly;

arrayTypeExpression:
	  ('?' '[' ']' ('[' ']')*)
	| ('(' '?' ')' '[' ']' ('[' ']')*)
	| primaryTypeExpression (
		  ('[' ']')
		| ('[' typeRef ']')
	)*;

primaryTypeExpression:
	( literalType
	| arrowFunctionTypeExpression
	| tupleTypeExpression
	| queryTypeRef
    | importTypeRef
	| inferTypeRef
	| typeRefWithModifiers
	| '(' typeRef ')'
	);



literalType
    : BooleanLiteral
    | StringLiteral
    | numericLiteral
    ;

arrowFunctionTypeExpression:
	(
		('abstract'? 'new')?
		('<' typeVariable (',' typeVariable)* ','? '>')?
		'(' anonymousFormalParameterListWithDeclaredThisType ')'
		'=>'
	) (typePredicateWithOperatorTypeRef | unionTypeExpression);

tupleTypeExpression:
	'['
	(
		']'
	|	tupleTypeArgument (',' tupleTypeArgument)* ','? ']'
	);

tupleTypeArgument
    : '...'? Infer? (Identifier ':')? typeRef '?'?
    ;

typeVariable:
	Identifier (Extends typeRef)?;

typeRefWithModifiers:
	parameterizedTypeRef
	| objectLiteralTypeRef
	| thisTypeRef
;

parameterizedTypeRef:
	typeReference
;

typeReference
    : typeName typeGeneric?
    ;

typeName
    : typeReferenceName ('.' typeReferenceName)*
    ;

typeGeneric
    : '<' typeArgumentList ','? '>'
    ;

typeArgumentList
    : typeArgument (',' typeArgument)*
    ;

typeArgument
    : Infer? typeRef
    ;

typeArguments
    : '<' typeArgumentList? '>'
    ;

objectLiteralTypeRef:
    '{' typeBody? '}'
    ;

thisTypeRef: This;

queryTypeRef:
	'typeof' propertyAccessExpressionInTypeRef
;

importTypeRef:
    Import '(' StringLiteral ')' ('.' typeReference)?
;

anonymousFormalParameterListWithDeclaredThisType :
	(('this' colonSepTypeRef | anonymousFormalParameter) (',' anonymousFormalParameter)* ','?)?
;

anonymousFormalParameter:
	'...'? ((bindingIdentifier '?'? colonSepTypeRef) | typeRef)
	defaultFormalParameter?
;

defaultFormalParameter:
	'=' Identifier
;

typePredicateWithOperatorTypeRef:
	Asserts? (This | bindingIdentifier) Is unionTypeExpression
;

bindingIdentifier: identifierName;


propertyAccessExpressionInTypeRef:
	typeReferenceName ('.' typeReferenceName)* // e.g. Symbol.iterator or osConstants.priority (in node/constants.d.ts)
;

inferTypeRef:
	Infer typeReferenceName
;


propertySignatur
    : ReadOnly? propertyName '?'? colonSepTypeRef? ('=>' typeRef)?
    ;

constructSignature
    : 'new' typeParameters? parameterBlock colonSepTypeRef?
    ;

indexSignature
    : ReadOnly? ('+'? 'readonly' | '-' 'readonly')? '[' indexSignatureElement ']' ('+'? '?' | '-' '?')? colonSepTypeRef
    ;

indexSignatureElement
    : identifierName ':' (Number|String)
    | Identifier In typeRef
    ;


methodSignature
    : propertyName '?'? callSignature
    ;

typeAliasDeclaration
    : TypeAlias identifierName typeParameters? '=' typeRef SemiColon?
    ;

// A.5 Interface

interfaceDeclaration
    : Interface identifierName typeParameters? interfaceExtendsClause? '{' typeBody? '}' SemiColon?
    ;

interfaceExtendsClause
    : Extends classOrInterfaceTypeList
    ;

classOrInterfaceTypeList
    : typeReference (',' typeReference)*
    ;

// A.7 Enum

enumDeclaration
    : Const? Enum Identifier '{' enumBody? '}' SemiColon?
    ;

enumBody
    : enumMemberList ','?
    ;

enumMemberList
    : enumMember (',' enumMember)*
    ;

enumMember
    : propertyName ('=' singleExpression)?
    ;

// A.8 Namespaces

namespaceDeclaration
    : Namespace namespaceName block
    ;

namespaceName
    : typeReferenceName ('.' typeReferenceName)*
    ;


// Module

moduleDeclaration
    : Module moduleName block
    ;

moduleName
    : StringLiteral
    | Identifier
    ;

// Ext.2 Additions to 1.8: Decorators

decoratorList
    : decorator+ ;

decorator
    : '@' (decoratorMemberExpression | decoratorCallExpression)
    ;

decoratorMemberExpression
    : Identifier
    | decoratorMemberExpression '.' identifierName
    | '(' singleExpression ')'
    ;

decoratorCallExpression
    : decoratorMemberExpression arguments;

// ECMAPart
program
    : statementList? EOF
    ;

statement
    : block
    | importStatement
    | decoratorList
    | exportStatement
    | declareStatement
    | arrowFunctionDeclaration
    | ifStatement
    | iterationStatement
    | continueStatement
    | breakStatement
    | returnStatement
    | yieldStatement
    | withStatement
    | labelledStatement
    | switchStatement
    | throwStatement
    | tryStatement
    | debuggerStatement
//    | expressionStatement
    | emptyStatement
    ;

declareStatement
    : Declare? declarationStatement
    ;

declarationStatement
    : moduleDeclaration
    | namespaceDeclaration //ADDED
    | interfaceDeclaration //ADDED
    | typeAliasDeclaration //ADDED
    | functionDeclaration
    | generatorFunctionDeclaration
    | classDeclaration
    | enumDeclaration      //ADDED
    | variableStatement
    ;

block
    : '{' statementList? '}'
    ;

statementList
    : statement+
    ;

importStatement
    : Import
    ( importFromBlock
    | importAliasDeclaration
    | StringLiteral
    )
    eos
    ;

importFromBlock
    : TypeAlias?
    ( Multiply As identifierName 
    | identifierName
    | multipleImportElements
    ) From StringLiteral
    ;

multipleImportElements
    : (identifierName ',')? '{' importedElement (',' importedElement)* ','? '}'
    ;

importedElement
    : identifierName (As identifierName)?
    ;

importAliasDeclaration
    : Identifier '=' 
    ( namespaceName
    | Require '(' StringLiteral ')'
    )
    ;

exportStatement
    : Export
    (
          ( As Namespace identifierName eos)
        | '=' namespaceName eos
        | Import identifierName '=' namespaceName eos
        | ( Default?
            ( declareStatement
            | exportFromBlock eos
            | identifierName eos
            )
          )
    )
    ;

exportFromBlock
    :
    ( Multiply (As identifierName)?
    | identifierName
    | multipleImportElements
    ) (From StringLiteral)?
    ;

variableStatement
    : bindingPattern colonSepTypeRef? initializer SemiColon?
    | accessibilityModifier? varModifier? ReadOnly? variableDeclarationList SemiColon?
    ;

variableDeclarationList
    : variableDeclaration (',' variableDeclaration)*
    ;

variableDeclaration
    : ( identifierName| arrayLiteral | objectLiteral) colonSepTypeRef? singleExpression? ('=' typeParameters? singleExpression)? // ECMAScript 6: Array & Object Matching
    ;

emptyStatement
    : SemiColon
    ;

expressionStatement
    : {this.notOpenBraceAndNotFunction()}? expressionSequence SemiColon?
    ;

ifStatement
    : If '(' expressionSequence ')' statement (Else statement)?
    ;


iterationStatement
    : Do statement While '(' expressionSequence ')' eos                                                         # DoStatement
    | While '(' expressionSequence ')' statement                                                                # WhileStatement
    | For '(' expressionSequence? SemiColon expressionSequence? SemiColon expressionSequence? ')' statement     # ForStatement
    | For '(' varModifier variableDeclarationList SemiColon expressionSequence? SemiColon expressionSequence? ')'
          statement                                                                                             # ForVarStatement
    | For '(' singleExpression (In | Identifier{this.p("of")}?) expressionSequence ')' statement                # ForInStatement
    | For '(' varModifier variableDeclaration (In | Identifier{this.p("of")}?) expressionSequence ')' statement # ForVarInStatement
    ;

varModifier
    : Var
    | Let
    | Const
    ;

continueStatement
    : Continue ({this.notLineTerminator()}? Identifier)? eos
    ;

breakStatement
    : Break ({this.notLineTerminator()}? Identifier)? eos
    ;

returnStatement
    : Return ({this.notLineTerminator()}? expressionSequence)? eos
    ;

yieldStatement
    : Yield ({this.notLineTerminator()}? expressionSequence)? eos
    ;

withStatement
    : With '(' expressionSequence ')' statement
    ;

switchStatement
    : Switch '(' expressionSequence ')' caseBlock
    ;

caseBlock
    : '{' caseClauses? (defaultClause caseClauses?)? '}'
    ;

caseClauses
    : caseClause+
    ;

caseClause
    : Case expressionSequence ':' statementList?
    ;

defaultClause
    : Default ':' statementList?
    ;

labelledStatement
    : Identifier ':' statement
    ;

throwStatement
    : Throw {this.notLineTerminator()}? expressionSequence eos
    ;

tryStatement
    : Try block (catchProduction finallyProduction? | finallyProduction)
    ;

catchProduction
    : Catch '(' Identifier ')' block
    ;

finallyProduction
    : Finally block
    ;

debuggerStatement
    : Debugger eos
    ;

functionDeclaration
    : Function identifierName callSignature block? SemiColon?
    ;

//Ovveride ECMA
classDeclaration
    : Abstract? Class identifierOrKeyWord typeParameters? classHeritage classTail SemiColon?
    ;

classHeritage
    : classExtendsClause? implementsClause?
    ;



classTail
    :  '{' classElementList? SemiColon? '}'
    ;

classElementList
    : classElement (({lineTerminatorAhead();} | SemiColon) classElement)*
    ;

classExtendsClause
    : Extends typeReference
    ;

implementsClause
    : Implements classOrInterfaceTypeList
    ;

// Classes modified
classElement
    : constructorDeclaration
    | decoratorList? propertyMemberDeclaration
    | indexSignature
    ;

constructorDeclaration
    : accessibilityModifier? Constructor parameterBlock block?
    ;


propertyMemberDeclaration
    : abstractDeclaration                                                                  
    | propertyMemberBase (
          propertyName '?'? (
              (colonSepTypeRef? initializer?)
            | callSignature block?
        )
        | (getAccessor | setAccessor)                                               
    )
    ;

abstractDeclaration
    : Abstract (Identifier callSignature | variableStatement) eos
    ;

propertyMemberBase
    : Async? accessibilityModifier? Static? ReadOnly?
    ;

generatorMethod
    : '*'?  Identifier parameterBlock block
    ;

generatorFunctionDeclaration
    : Function '*' Identifier? parameterBlock block
    ;

generatorBlock
    : '{' generatorDefinition (',' generatorDefinition)* ','? '}'
    ;

generatorDefinition
    : '*' iteratorDefinition
    ;

iteratorBlock
    : '{' iteratorDefinition (',' iteratorDefinition)* ','? '}'
    ;

iteratorDefinition
    : '[' singleExpression ']' parameterBlock block
    ;




callSignature
    : typeParameters? parameterBlock (':' (typePredicateWithOperatorTypeRef | typeRef))?
    ;

parameterBlock:
    '(' parameterListTrailingComma? ')'
    ;

parameterListTrailingComma
    : parameterList ','?
    ;

parameterList
    : restParameter
    | parameter (',' parameter)* (',' restParameter)?
    ;

restParameter
    : '...' identifierOrPattern colonSepTypeRef?
    ;

parameter
    : requiredParameter
    | optionalParameter
    ;

requiredParameter
    : decoratorList? accessibilityModifier? identifierOrPattern colonSepTypeRef?
    ;

optionalParameter
    : decoratorList? accessibilityModifier? identifierOrPattern
    (   '?' colonSepTypeRef?
        | colonSepTypeRef? initializer
    )
    ;

accessibilityModifier
    : Public
    | Private
    | Protected
    ;

identifierOrPattern
    : identifierName
    | bindingPattern
    ;





arrayLiteral
    : ('[' elementList? ']')
    ;

elementList
    : arrayElement (','+ arrayElement)*
    ;

arrayElement                      // ECMAScript 6: Spread Operator
    : Ellipsis? (singleExpression | Identifier) ','?
    ;



typeBody
    : typeMemberList (SemiColon | ',')?
    ;

typeMemberList
    : typeMember (({lineTerminatorAhead();} | SemiColon | ',') typeMember)*
    ;

typeMember
    : propertySignatur
    | callSignature
    | constructSignature
    | indexSignature
    | methodSignature ('=>' typeRef)?
    ;



objectLiteral
    : '{' (propertyAssignment (',' propertyAssignment)* ','?)? '}'
    ;

// MODIFIED
propertyAssignment
    : propertyName (':' |'=') singleExpression                # PropertyExpressionAssignment
    | '[' singleExpression ']' ':' singleExpression           # ComputedPropertyExpressionAssignment
    | getAccessor                                             # PropertyGetter
    | setAccessor                                             # PropertySetter
    | generatorMethod                                         # MethodProperty
    | identifierOrKeyWord                                     # PropertyShorthand
    | restParameter                                           # RestParameterInObject
    ;

getAccessor
    : getter '(' ')' colonSepTypeRef? block?
    ;

setAccessor
    : setter '(' ( Identifier | bindingPattern) colonSepTypeRef? ')' block?
    ;

propertyName
    : identifierName
    | StringLiteral
    | numericLiteral
    | '[' (identifierName '.' identifierName // usually Symbol.iterator
          | StringLiteral
          )
      ']'
    ;

arguments
    : '(' (argumentList ','?)? ')'
    ;

argumentList
    : argument (',' argument)*
    ;

argument                      // ECMAScript 6: Spread Operator
    : Ellipsis? (singleExpression | Identifier)
    ;

expressionSequence
    : singleExpression (',' singleExpression)*
    ;

functionExpressionDeclaration
    : Function Identifier? parameterBlock colonSepTypeRef? block
    ;

singleExpression
    : functionExpressionDeclaration                                          # FunctionExpression
    | arrowFunctionDeclaration                                               # ArrowFunctionExpression   // ECMAScript 6
    | Class Identifier? classTail                                            # ClassExpression
    | singleExpression '[' expressionSequence ']'                            # MemberIndexExpression
    | singleExpression '.' identifierName typeGeneric?                       # MemberDotExpression
    | New singleExpression typeArguments? arguments?                         # NewExpression
    | singleExpression arguments                                             # ArgumentsExpression
    | singleExpression {this.notLineTerminator()}? '++'                      # PostIncrementExpression
    | singleExpression {this.notLineTerminator()}? '--'                      # PostDecreaseExpression
    | Delete singleExpression                                                # DeleteExpression
    | Void singleExpression                                                  # VoidExpression
    | Typeof singleExpression                                                # TypeofExpression
    | '++' singleExpression                                                  # PreIncrementExpression
    | '--' singleExpression                                                  # PreDecreaseExpression
    | '+' singleExpression                                                   # UnaryPlusExpression
    | '-' singleExpression                                                   # UnaryMinusExpression
    | '~' singleExpression                                                   # BitNotExpression
    | '!' singleExpression                                                   # NotExpression
    | singleExpression ('*' | '/' | '%') singleExpression                    # MultiplicativeExpression
    | singleExpression ('+' | '-') singleExpression                          # AdditiveExpression
    | singleExpression ('<<' | '>''>' | '>''>''>') singleExpression          # BitShiftExpression
    | singleExpression ('<' | '>' | '<=' | '>=') singleExpression            # RelationalExpression
    | singleExpression Instanceof singleExpression                           # InstanceofExpression
    | singleExpression In singleExpression                                   # InExpression
    | singleExpression ('==' | '!=' | '===' | '!==') singleExpression        # EqualityExpression
    | singleExpression ('&' | '^' | '|' ) singleExpression                   # BinaryExpression
    | singleExpression ('&&' | '||' ) singleExpression                       # LogicalExpression
    | singleExpression '?' singleExpression ':' singleExpression             # TernaryExpression
    | singleExpression '=' singleExpression                                  # AssignmentExpression
    | singleExpression assignmentOperator singleExpression                   # AssignmentOperatorExpression
    | singleExpression templateStringLiteral                                 # TemplateStringExpression  // ECMAScript 6
    | iteratorBlock                                                          # IteratorsExpression // ECMAScript 6
    | generatorBlock                                                         # GeneratorsExpression // ECMAScript 6
    | generatorFunctionDeclaration                                           # GeneratorsFunctionExpression // ECMAScript 6
    | yieldStatement                                                         # YieldExpression // ECMAScript 6
    | This                                                                   # ThisExpression
    | identifierName                                                         # IdentifierExpression
    | Super                                                                  # SuperExpression
    | literal                                                                # LiteralExpression
    | arrayLiteral                                                           # ArrayLiteralExpression
    | objectLiteral                                                          # ObjectLiteralExpression
    | '(' expressionSequence ')'                                             # ParenthesizedExpression
    | typeArguments expressionSequence?                                      # GenericTypes
    | singleExpression As typeRef                                              # CastAsExpression
    ;


arrowFunctionDeclaration
    : Async? arrowFunctionParameters colonSepTypeRef? '=>' arrowFunctionBody
    ;

arrowFunctionParameters
    : Identifier
    | parameterBlock
    ;

arrowFunctionBody
    : singleExpression
    | block
    ;

assignmentOperator
    : '*='
    | '/='
    | '%='
    | '+='
    | '-='
    | '<<='
    | '>>='
    | '>>>='
    | '&='
    | '^='
    | '|='
    ;

literal
    : NullLiteral
    | BooleanLiteral
    | StringLiteral
    | templateStringLiteral
    | RegularExpressionLiteral
    | numericLiteral
    ;

templateStringLiteral
    : BackTick templateStringAtom* BackTick
    ;
templateStringAtom
    : TemplateStringAtom
    | TemplateStringStartExpression singleExpression CloseBrace
    ;
//*/
numericLiteral
    : '-'? DecimalLiteral
    | HexIntegerLiteral
    | OctalIntegerLiteral
    | OctalIntegerLiteral2
    | BinaryIntegerLiteral
    ;

identifierOrKeyWord
    : Identifier
    | Require
    | TypeAlias
    ;

identifierName
    : reservedWord
    | Identifier
    ;

reservedWord
    : keyword
    | BooleanLiteral
    ;


typeReferenceName
    : keywordAllowedInTypeReferences
    | BooleanLiteral
    | Identifier
    ;

keyword:
    keywordAllowedInTypeReferences
    | ReadOnly // cannot reference types named 'readonly'
    | Typeof
    ;

keywordAllowedInTypeReferences:
    Abstract
    | Any
    | As
    | Asserts
    | Async
    | Break
    | Boolean
    | Case
    | Catch
    | Class
    | Const
    | Constructor
    | Continue
    | Debugger
    | Declare
    | Default
    | Delete
    | Do
    | Else
    | Enum
    | Export
    | Extends
    | Finally
    | For
    | From
    | Function
    | Get
    | If
    | Implements
    | Import
    | In
    | Infer
    | Instanceof
    | Interface
    | Is
    | Let
    | Module
    | Namespace
    | Never
    | New
    | NullLiteral
    | Number
    | Package
    | Private
    | Protected
    | Public
    // | ReadOnly // cannot reference types named 'readonly'
    | Require
    | Return
    | Set
    | Static
    | String
    | Super
    | Switch
    | Symbol
    | This
    | Throw
    | Try
    | TypeAlias
    // | Typeof // cannot reference types named 'typeof'
    | UndefinedLiteral
    | Unknown
    | Unique
    | Var
    | Void
    | While
    | With
    | Yield
    ;

getter
    : Get propertyName
    ;

setter
    : Set propertyName
    ;

eos
    : SemiColon
    | EOF
    | {this.lineTerminatorAhead()}?
    | {this.closeBrace()}?
    ;