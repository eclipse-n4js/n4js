/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 by Bart Kiers (original author) and Alexandre Vitorelli (contributor -> ported to CSharp)
 * Copyright (c) 2017 by Ivan Kochurkin (Positive Technologies):
    added ECMAScript 6 support, cleared and transformed to the universal grammar.
 * Copyright (c) 2018 by Juan Alvarez (contributor -> ported to Go)
 * Copyright (c) 2019 by Andrii Artiushok (contributor -> added TypeScript support)
 * Copyright (c) 2022 by NumberFour AG (contributor -> overall restructuring and d.ts support)
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


program
    : statementList? EOF
    ;



// Statement

statement
    : block
    | importStatement
    | decoratorList
    | exportStatement
    | declareStatement
    | ifStatement
    | iterationStatement
    | continueStatement
    | breakStatement
    | returnStatement
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
    | namespaceDeclaration
    | interfaceDeclaration
    | typeAliasDeclaration
    | functionDeclaration
    | classDeclaration
    | enumDeclaration
    | variableStatement
    ;

block
    : '{' statementList? '}'
    ;

statementList
    : statement+
    ;



// Types References

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
	primaryTypeExpression arrayTypeExpressionSuffix*;

arrayTypeExpressionSuffix
	: '[' ']'
	| '[' typeRef ']' // index access type
	;

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
        typeParameters?
        parameterBlock
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
    thisTypeRef
	| parameterizedTypeRef
	| objectLiteralTypeRef
;

parameterizedTypeRef
    : typeName typeArguments?
    ;

typeName
    : typeReferenceName ('.' typeReferenceName)*
    ;

typeArguments
    : '<' (typeArgumentList ','?)? '>'
    ;

typeArgumentList
    : typeArgument (',' typeArgument)*
    ;

typeArgument
    : Infer? typeRef
    ;

objectLiteralTypeRef:
    '{' interfaceBody? '}'
    ;

thisTypeRef: This;

queryTypeRef:
	'typeof' propertyAccessExpressionInTypeRef
;

importTypeRef:
    Import '(' StringLiteral ')' ('.' parameterizedTypeRef)?
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

typeAliasDeclaration
    : TypeAlias identifierName typeParameters? '=' typeRef SemiColon?
    ;

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


// Module

moduleDeclaration
    : Module moduleName block
    ;

moduleName
    : StringLiteral
    | Identifier
    ;



// Namespace

namespaceDeclaration
    : Namespace namespaceName block
    ;

namespaceName
    : typeReferenceName ('.' typeReferenceName)*
    ;

// Decorators

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



// Interface

interfaceDeclaration
    : Interface identifierName typeParameters? interfaceExtendsClause? '{' interfaceBody? '}' SemiColon?
    ;

interfaceExtendsClause
    : Extends classOrInterfaceTypeList
    ;

classOrInterfaceTypeList
    : parameterizedTypeRef (',' parameterizedTypeRef)*
    ;

interfaceBody
    : interfaceMemberList (SemiColon | ',')?
    ;

interfaceMemberList
    : interfaceMember (({lineTerminatorAhead();} | SemiColon | ',') interfaceMember)*
    ;

interfaceMember
    : constructSignature
    | callSignature
    | indexSignature
    | getAccessor
    | setAccessor
    | methodSignature
    | propertySignature
    ;

constructSignature
    : 'new' typeParameters? parameterBlock colonSepTypeRef?
    ;

callSignature
    : typeParameters? parameterBlock (':' (typePredicateWithOperatorTypeRef | typeRef))?
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

propertySignature
    : ReadOnly? propertyName '?'? colonSepTypeRef?
    ;



// Enum

enumDeclaration
    : Const? Enum identifierOrKeyWord '{' enumBody? '}' SemiColon?
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



// Function Declaration

functionDeclaration
    : Function '*'? identifierName callSignature block? SemiColon?
    ;



// Class Declaration

classDeclaration
    : Abstract? Class identifierOrKeyWord typeParameters? classHeritage classBody SemiColon?
    ;

classHeritage
    : classExtendsClause? classImplementsClause?
    ;

classExtendsClause
    : Extends parameterizedTypeRef
    ;

classImplementsClause
    : Implements classOrInterfaceTypeList
    ;

classBody
    :  '{' classMemberList? SemiColon? '}'
    ;

classMemberList
    : classMember (({lineTerminatorAhead();} | SemiColon) classMember)*
    ;

classMember
    : constructorDeclaration
    | indexSignature
    | decoratorList? propertyMemberDeclaration
    ;

constructorDeclaration
    : accessibilityModifier? Constructor parameterBlock block?
    ;

propertyMemberDeclaration
    : abstractDeclaration                                                                  
    | propertyMember
    ;

abstractDeclaration
    : Abstract (Identifier callSignature | variableStatement) eos
    ;

propertyMember
    : propertyMemberBase
    (
          getAccessor
        | setAccessor
        | propertyOrMethod
    )
    ;

propertyMemberBase
    : Async? accessibilityModifier? Static?
    ;

propertyOrMethod
    : ReadOnly? propertyName '?'? (
          (colonSepTypeRef? initializer?)
        | (callSignature block?)
    )
    ;

initializer
    : '=' singleExpression
    ;


parameterBlock:
    '(' (This colonSepTypeRef? ',')?  parameterListTrailingComma? ')'
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





bindingPattern
    : arrayLiteral
    | objectLiteral
    ;



// Array Literal

arrayLiteral
    : '[' elementList? ']'
    ;

elementList
    : arrayElement (','+ arrayElement)* ','?
    ;

arrayElement
    : Ellipsis? bindingElement
    ;

bindingElement
    : bindingPattern
    | Identifier
    ;



// Object Literal

objectLiteral
    : '{' (propertyAssignment (',' propertyAssignment)* ','?)? '}'
    ;

propertyAssignment
    : propertyName (':' identifierOrKeyWord | bindingPattern)? ('=' singleExpression)?  # PropertyExpressionAssignment
    | getAccessor                                                 # PropertyGetter
    | setAccessor                                                 # PropertySetter
    | generatorMethod                                             # MethodProperty
    | restParameter                                               # RestParameterInObject
    ;

propertyName
    : StringLiteral
    | numericLiteral
    | '[' (identifierName '.' identifierName // usually Symbol.iterator
          | StringLiteral
          )
      ']'
    | identifierName
    ;

getAccessor
    : getter '(' ')' colonSepTypeRef? block?
    ;

setAccessor
    : setter '(' (Identifier | bindingPattern) colonSepTypeRef? ')' block?
    ;

generatorMethod
    : '*'?  Identifier parameterBlock block
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




// Import Statement

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



// Export Statement

exportStatement
    : Export exportStatementTail
    ;

exportStatementTail
    : As Namespace identifierName eos                   #ExportAsNamespace
    | '=' namespaceName eos                             #ExportEquals
    | Import identifierName '=' namespaceName eos       #ExportImport
    | Default? declareStatement                         #ExportDeclareStatement
    | Default? exportFromBlock eos                      #ExportElement
    ;

exportFromBlock
    :
    ( Multiply (As identifierName)?
    | identifierName
    | multipleImportElements
    ) (From StringLiteral)?
    ;


// Variable Statement

variableStatement
    : accessibilityModifier? ReadOnly? varModifier (bindingPatternBlock | variableDeclarationList) SemiColon?
    ;

varModifier
    : Var
    | Let
    | Const
    ;

bindingPatternBlock
    : bindingPattern colonSepTypeRef? initializer?
    ;

variableDeclarationList
    : variableDeclaration (',' variableDeclaration)*
    ;

variableDeclaration
    : identifierName colonSepTypeRef? ('=' typeParameters? singleExpression)? // ECMAScript 6: Array & Object Matching
    ;



// Switch Statement

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



// Try Statement

tryStatement
    : Try block (catchProduction finallyProduction? | finallyProduction)
    ;

catchProduction
    : Catch '(' Identifier ')' block
    ;

finallyProduction
    : Finally block
    ;




// Other Statements

emptyStatement
    : SemiColon
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


continueStatement
    : Continue ({this.notLineTerminator()}? Identifier)? eos
    ;


breakStatement
    : Break ({this.notLineTerminator()}? Identifier)? eos
    ;


returnStatement
    : Return ({this.notLineTerminator()}? expressionSequence)? eos
    ;


withStatement
    : With '(' expressionSequence ')' statement
    ;


labelledStatement
    : Identifier ':' statement
    ;


throwStatement
    : Throw {this.notLineTerminator()}? expressionSequence eos
    ;


debuggerStatement
    : Debugger eos
    ;


expressionStatement
    : {this.notOpenBraceAndNotFunction()}? expressionSequence eos
    ;


expressionSequence
    : singleExpression (',' singleExpression)*
    ;



// Expressions

singleExpression
    :
      This                                                                   # ThisExpression
    | Super                                                                  # SuperExpression
    | literal                                                                # LiteralExpression
    | arrayLiteral                                                           # ArrayLiteralExpression
    | objectLiteral                                                          # ObjectLiteralExpression
    | templateStringLiteral                                                  # TemplateStringExpression

    | Yield ({this.notLineTerminator()}? expressionSequence)?                # YieldExpression
    | Await singleExpression                                                 # AwaitExpression

// respect precedence by order of sub-rules
    | singleExpression assignmentOperator singleExpression                   # AssignmentExpression
    | singleExpression '?' singleExpression ':' singleExpression             # TernaryExpression
    | singleExpression '?''?' singleExpression                               # coalesceExpression
    | singleExpression ('||' | '&&') singleExpression                        # LogicalExpression
    | singleExpression ('&' | '^' | '|' ) singleExpression                   # BinaryExpression
    | singleExpression ('==' | '!=' | '===' | '!==') singleExpression        # EqualityExpression
    | singleExpression relationalOperator singleExpression                   # RelationalExpression
    | singleExpression ('<<' | '>''>' | '>''>''>') singleExpression          # BitShiftExpression
    | singleExpression ('+' | '-') singleExpression                          # AdditiveExpression
    | singleExpression ('*' | '/' | '%') singleExpression                    # MultiplicativeExpression
    | unaryOperator singleExpression                                         # UnaryExpression
    | singleExpression As typeRef                                            # CastAsExpression
    | singleExpression {this.notLineTerminator()}? ('++' | '--')             # PostfixExpression
    | singleExpression '?''.'? '[' expressionSequence ']'                    # IndexedAccessExpression
    | singleExpression '?''.'? identifierName typeArguments? arguments       # CallExpression
    | singleExpression ('.' | '?''.') identifierName                         # PropertyAccessExpression
    | newExpression                                                          # NewExpressionL

    | functionExpression                                                     # FunctionExpressionL
    | arrowFunctionExpression                                                # ArrowFunctionExpressionL
    | classExpression                                                        # ClassExpressionL

  // TODO:
  //  | iteratorBlock                                                          # IteratorsExpression
  //  | generatorBlock                                                         # GeneratorsExpression

    | identifierName                                                         # IdentifierExpression
    | '(' expressionSequence ')'                                             # ParenthesizedExpression
    ;





functionExpression
    : Function '*'? Identifier? parameterBlock colonSepTypeRef? block
    ;

arrowFunctionExpression
    : Async? parameterBlock colonSepTypeRef? '=>' arrowFunctionBody
    ;

arrowFunctionBody
    : singleExpression
    | block
    ;

classExpression
    : Class Identifier? classBody
    ;

assignmentOperator
    : '=' | '*=' | '/=' | '%=' | '+='
    | '-' '=' /* must be split into two literals since jsx attribute names may end with a dash as in attr-="value" */ 
    | '<<=' | '>' '>' '>'? '=' | '&=' | '^=' | '|='
    ;

relationalOperator
    : Instanceof | In | '<' | '>' | '<=' | '>='
    ;

unaryOperator
    : Delete | Void | Typeof | '++' | '--' | '+' | '-' | '~' | '!'
    ;

newExpression
    : New (
          Dot Target
        | singleExpression typeArguments? arguments?
    )
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
    | Await
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
    | Target
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