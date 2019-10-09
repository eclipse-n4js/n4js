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
grammar DebugInternalN4JS;

// Rule Script
ruleScript:
	(
		ruleScriptAnnotation
		    |
		ruleScriptElement
	)*
;

// Rule ScriptElement
ruleScriptElement:
	(
		(
			('@'
			(
				'This'
				    |
				'target'
				    |
				RULE_IDENTIFIER
			)
			)=>
			ruleAnnotatedScriptElement
		)
		    |
		(
			(ruleN4Modifier
			*
			'class'
			ruleTypingStrategyDefSiteOperator
			?
			ruleBindingIdentifier
			?
			ruleVersionDeclaration?
			)=>
			ruleN4ClassDeclaration
		)
		    |
		(
			(ruleN4Modifier
			*
			'interface'
			ruleTypingStrategyDefSiteOperator
			?
			ruleBindingIdentifier
			?
			ruleVersionDeclaration?
			)=>
			ruleN4InterfaceDeclaration
		)
		    |
		(
			(ruleN4Modifier
			*
			'enum'
			ruleBindingIdentifier
			?
			ruleVersionDeclaration?
			)=>
			ruleN4EnumDeclaration
		)
		    |
		(
			(ruleImportDeclaration)=>
			ruleImportDeclaration
		)
		    |
		ruleExportDeclaration
		    |
		ruleRootStatement
	)
;

// Rule AnnotatedScriptElement
ruleAnnotatedScriptElement:
	(
		('@'
		(
			'This'
			    |
			'target'
			    |
			RULE_IDENTIFIER
		)
		)=>
		ruleAnnotationList
	)
	(
		ruleExportDeclarationImpl
		    |
		ruleImportDeclarationImpl
		    |
		(
			(ruleN4Modifier
			*
			ruleAsyncNoTrailingLineBreak
			'function'
			)=>
			ruleN4Modifier
			*
			ruleAsyncNoTrailingLineBreak
			(
				('function')=>
				ruleFunctionImpl
			)
		)
		    |
		(
			ruleN4Modifier
			*
			'class'
			ruleTypingStrategyDefSiteOperator
			?
			ruleBindingIdentifier
			ruleVersionDeclaration?
			ruleTypeVariables?
			ruleClassExtendsImplements?
			    |
			ruleN4Modifier
			*
			'interface'
			ruleTypingStrategyDefSiteOperator
			?
			ruleBindingIdentifier
			ruleVersionDeclaration?
			ruleTypeVariables?
			ruleInterfaceExtendsList?
		)
		ruleMembers
		    |
		ruleN4Modifier
		*
		'enum'
		ruleBindingIdentifier
		ruleVersionDeclaration?
		'{'
		ruleN4EnumLiteral
		(
			','
			ruleN4EnumLiteral
		)*
		'}'
	)
;

// Rule ExportDeclaration
ruleExportDeclaration:
	ruleExportDeclarationImpl
;

// Rule ExportDeclarationImpl
ruleExportDeclarationImpl:
	'export'
	(
		'*'
		ruleExportFromClause
		ruleSemi
		    |
		ruleExportClause
		(
			('from')=>
			ruleExportFromClause
		)?
		ruleSemi
		    |
		ruleExportableElement
		    |
		'default'
		(
			(
				('@' | 'private' | 'project' | 'protected' | 'public' | 'external' | 'abstract' | 'static' | 'const' | 'class' | 'interface' | 'enum' | 'async' | 'function' | 'var' | 'let')=>
				ruleExportableElement
			)
			    |
			norm1_AssignmentExpression
			ruleSemi
		)
	)
;

// Rule ExportFromClause
ruleExportFromClause:
	'from'
	ruleModuleSpecifier
;

// Rule ExportClause
ruleExportClause:
	'{'
	(
		ruleExportSpecifier
		(
			','
			ruleExportSpecifier
		)*
		','?
	)?
	'}'
;

// Rule ExportSpecifier
ruleExportSpecifier:
	ruleIdentifierRef
	(
		'as'
		ruleIdentifierName
	)?
;

// Rule ExportableElement
ruleExportableElement:
	(
		(
			('@'
			(
				'This'
				    |
				'target'
				    |
				RULE_IDENTIFIER
			)
			)=>
			ruleAnnotatedExportableElement
		)
		    |
		(
			(ruleN4Modifier
			*
			'class'
			ruleTypingStrategyDefSiteOperator
			?
			ruleBindingIdentifier
			?
			ruleVersionDeclaration?
			)=>
			ruleN4ClassDeclaration
		)
		    |
		(
			(ruleN4Modifier
			*
			'interface'
			ruleTypingStrategyDefSiteOperator
			?
			ruleBindingIdentifier
			?
			ruleVersionDeclaration?
			)=>
			ruleN4InterfaceDeclaration
		)
		    |
		(
			(ruleN4Modifier
			*
			'enum'
			ruleBindingIdentifier
			?
			ruleVersionDeclaration?
			)=>
			ruleN4EnumDeclaration
		)
		    |
		(
			(ruleN4Modifier
			*
			ruleAsyncNoTrailingLineBreak
			'function'
			)=>
			ruleFunctionDeclaration
		)
		    |
		ruleExportedVariableStatement
	)
;

// Rule AnnotatedExportableElement
ruleAnnotatedExportableElement:
	(
		('@'
		(
			'This'
			    |
			'target'
			    |
			RULE_IDENTIFIER
		)
		)=>
		ruleAnnotationList
	)
	(
		ruleN4Modifier
		*
		ruleAsyncNoTrailingLineBreak
		ruleFunctionImpl
		    |
		ruleN4Modifier
		*
		ruleVariableStatementKeyword
		ruleExportedVariableDeclarationOrBinding
		(
			','
			ruleExportedVariableDeclarationOrBinding
		)*
		ruleSemi
		    |
		(
			ruleN4Modifier
			*
			'class'
			ruleTypingStrategyDefSiteOperator
			?
			ruleBindingIdentifier
			ruleTypeVariables?
			ruleClassExtendsImplements?
			    |
			ruleN4Modifier
			*
			'interface'
			ruleTypingStrategyDefSiteOperator
			?
			ruleBindingIdentifier
			ruleTypeVariables?
			ruleInterfaceExtendsList?
		)
		ruleMembers
		    |
		ruleN4Modifier
		*
		'enum'
		ruleBindingIdentifier
		'{'
		ruleN4EnumLiteral
		(
			','
			ruleN4EnumLiteral
		)*
		'}'
	)
;

// Rule ImportDeclaration
ruleImportDeclaration:
	ruleImportDeclarationImpl
;

// Rule ImportDeclarationImpl
ruleImportDeclarationImpl:
	'import'
	(
		ruleImportClause
		'from'
	)?
	ruleModuleSpecifier
	ruleSemi
;

// Rule ImportClause
ruleImportClause:
	(
		ruleDefaultImportSpecifier
		(
			','
			ruleImportSpecifiersExceptDefault
		)?
		    |
		ruleImportSpecifiersExceptDefault
	)
;

// Rule ImportSpecifiersExceptDefault
ruleImportSpecifiersExceptDefault:
	(
		ruleNamespaceImportSpecifier
		    |
		'{'
		(
			ruleNamedImportSpecifier
			(
				','
				ruleNamedImportSpecifier
			)*
			','?
		)?
		'}'
	)
;

// Rule NamedImportSpecifier
ruleNamedImportSpecifier:
	(
		ruleBindingIdentifier
		    |
		ruleIdentifierName
		'as'
		ruleBindingIdentifier
	)
;

// Rule DefaultImportSpecifier
ruleDefaultImportSpecifier:
	ruleBindingIdentifier
;

// Rule NamespaceImportSpecifier
ruleNamespaceImportSpecifier:
	'*'
	'as'
	ruleBindingIdentifier
	'+'
	?
;

// Rule ModuleSpecifier
ruleModuleSpecifier:
	RULE_STRING
;

// Rule FunctionDeclaration
ruleFunctionDeclaration:
	(
		(ruleN4Modifier
		*
		ruleAsyncNoTrailingLineBreak
		'function'
		)=>
		ruleN4Modifier
		*
		ruleAsyncNoTrailingLineBreak
		(
			('function')=>
			ruleFunctionImpl
		)
	)
	(
		(ruleSemi)=>
		ruleSemi
	)?
;

// Rule FunctionDeclaration
norm1_FunctionDeclaration:
	(
		(ruleN4Modifier
		*
		ruleAsyncNoTrailingLineBreak
		'function'
		)=>
		ruleN4Modifier
		*
		ruleAsyncNoTrailingLineBreak
		(
			('function')=>
			norm3_FunctionImpl
		)
	)
	(
		(ruleSemi)=>
		ruleSemi
	)?
;

// Rule AsyncNoTrailingLineBreak
ruleAsyncNoTrailingLineBreak:
	(
		'async'
		ruleNoLineTerminator
	)?
;

// Rule FunctionImpl
ruleFunctionImpl:
	'function'
	(
		'*'
		norm2_FunctionHeader
		norm1_FunctionBody
		    |
		ruleFunctionHeader
		ruleFunctionBody
	)
;

// Rule FunctionImpl
norm3_FunctionImpl:
	'function'
	(
		'*'
		norm3_FunctionHeader
		norm1_FunctionBody
		    |
		norm1_FunctionHeader
		ruleFunctionBody
	)
;

// Rule FunctionImpl
norm6_FunctionImpl:
	'function'
	(
		'*'
		norm3_FunctionHeader
		norm3_FunctionBody
		    |
		ruleFunctionHeader
		norm2_FunctionBody
	)
;

// Rule FunctionHeader
ruleFunctionHeader:
	ruleTypeVariables?
	ruleBindingIdentifier
	?
	ruleVersionDeclaration?
	ruleStrictFormalParameters
	(
		(':')=>
		ruleColonSepReturnTypeRef
	)?
;

// Rule FunctionHeader
norm1_FunctionHeader:
	ruleTypeVariables?
	norm1_BindingIdentifier
	?
	ruleVersionDeclaration?
	ruleStrictFormalParameters
	(
		(':')=>
		ruleColonSepReturnTypeRef
	)?
;

// Rule FunctionHeader
norm2_FunctionHeader:
	ruleTypeVariables?
	ruleBindingIdentifier
	?
	ruleVersionDeclaration?
	norm1_StrictFormalParameters
	(
		(':')=>
		ruleColonSepReturnTypeRef
	)?
;

// Rule FunctionHeader
norm3_FunctionHeader:
	ruleTypeVariables?
	norm1_BindingIdentifier
	?
	ruleVersionDeclaration?
	norm1_StrictFormalParameters
	(
		(':')=>
		ruleColonSepReturnTypeRef
	)?
;

// Rule FunctionBody
ruleFunctionBody:
	(
		('{'
		)=>
		ruleBlock
	)?
;

// Rule FunctionBody
norm1_FunctionBody:
	(
		('{'
		)=>
		norm1_Block
	)?
;

// Rule FunctionBody
norm2_FunctionBody:
	(
		('{'
		)=>
		ruleBlock
	)
;

// Rule FunctionBody
norm3_FunctionBody:
	(
		('{'
		)=>
		norm1_Block
	)
;

// Rule AnnotatedFunctionDeclaration
ruleAnnotatedFunctionDeclaration:
	(
		('@'
		(
			'This'
			    |
			'target'
			    |
			RULE_IDENTIFIER
		)
		)=>
		ruleAnnotationList
	)
	ruleN4Modifier
	*
	ruleAsyncNoTrailingLineBreak
	ruleFunctionImpl
;

// Rule AnnotatedFunctionDeclaration
norm1_AnnotatedFunctionDeclaration:
	(
		('@'
		(
			'This'
			    |
			'target'
			    |
			RULE_IDENTIFIER
		)
		)=>
		ruleAnnotationList
	)
	ruleN4Modifier
	*
	ruleAsyncNoTrailingLineBreak
	norm3_FunctionImpl
;

// Rule FunctionExpression
ruleFunctionExpression:
	norm6_FunctionImpl
;

// Rule AsyncFunctionExpression
ruleAsyncFunctionExpression:
	(
		('async'
		ruleNoLineTerminator
		'function'
		)=>
		'async'
		ruleNoLineTerminator
		'function'
	)
	ruleFunctionHeader
	norm2_FunctionBody
;

// Rule ArrowExpression
ruleArrowExpression:
	(
		((
			ruleStrictFormalParameters
			ruleColonSepReturnTypeRef?
			    |
			(
				('async'
				ruleNoLineTerminator
				'('
				)=>
				'async'
				ruleNoLineTerminator
				(
					('(')=>
					ruleStrictFormalParameters
				)
			)
			ruleColonSepReturnTypeRef?
			    |
			ruleBindingIdentifierAsFormalParameter
		)
		'=>'
		)=>
		(
			ruleStrictFormalParameters
			ruleColonSepReturnTypeRef?
			    |
			(
				('async'
				ruleNoLineTerminator
				'('
				)=>
				'async'
				ruleNoLineTerminator
				(
					('(')=>
					ruleStrictFormalParameters
				)
			)
			ruleColonSepReturnTypeRef?
			    |
			ruleBindingIdentifierAsFormalParameter
		)
		'=>'
	)
	(
		(
			('{')=>
			'{'
		)
		ruleBlockMinusBraces
		'}'
		    |
		ruleExpressionDisguisedAsBlock
	)
;

// Rule ArrowExpression
norm1_ArrowExpression:
	(
		((
			ruleStrictFormalParameters
			ruleColonSepReturnTypeRef?
			    |
			(
				('async'
				ruleNoLineTerminator
				'('
				)=>
				'async'
				ruleNoLineTerminator
				(
					('(')=>
					ruleStrictFormalParameters
				)
			)
			ruleColonSepReturnTypeRef?
			    |
			ruleBindingIdentifierAsFormalParameter
		)
		'=>'
		)=>
		(
			ruleStrictFormalParameters
			ruleColonSepReturnTypeRef?
			    |
			(
				('async'
				ruleNoLineTerminator
				'('
				)=>
				'async'
				ruleNoLineTerminator
				(
					('(')=>
					ruleStrictFormalParameters
				)
			)
			ruleColonSepReturnTypeRef?
			    |
			ruleBindingIdentifierAsFormalParameter
		)
		'=>'
	)
	(
		(
			('{')=>
			'{'
		)
		ruleBlockMinusBraces
		'}'
		    |
		norm1_ExpressionDisguisedAsBlock
	)
;

// Rule ArrowExpression
norm2_ArrowExpression:
	(
		((
			norm1_StrictFormalParameters
			ruleColonSepReturnTypeRef?
			    |
			(
				('async'
				ruleNoLineTerminator
				'('
				)=>
				'async'
				ruleNoLineTerminator
				(
					('(')=>
					norm1_StrictFormalParameters
				)
			)
			ruleColonSepReturnTypeRef?
			    |
			norm1_BindingIdentifierAsFormalParameter
		)
		'=>'
		)=>
		(
			norm1_StrictFormalParameters
			ruleColonSepReturnTypeRef?
			    |
			(
				('async'
				ruleNoLineTerminator
				'('
				)=>
				'async'
				ruleNoLineTerminator
				(
					('(')=>
					norm1_StrictFormalParameters
				)
			)
			ruleColonSepReturnTypeRef?
			    |
			norm1_BindingIdentifierAsFormalParameter
		)
		'=>'
	)
	(
		(
			('{')=>
			'{'
		)
		norm1_BlockMinusBraces
		'}'
		    |
		ruleExpressionDisguisedAsBlock
	)
;

// Rule ArrowExpression
norm3_ArrowExpression:
	(
		((
			norm1_StrictFormalParameters
			ruleColonSepReturnTypeRef?
			    |
			(
				('async'
				ruleNoLineTerminator
				'('
				)=>
				'async'
				ruleNoLineTerminator
				(
					('(')=>
					norm1_StrictFormalParameters
				)
			)
			ruleColonSepReturnTypeRef?
			    |
			norm1_BindingIdentifierAsFormalParameter
		)
		'=>'
		)=>
		(
			norm1_StrictFormalParameters
			ruleColonSepReturnTypeRef?
			    |
			(
				('async'
				ruleNoLineTerminator
				'('
				)=>
				'async'
				ruleNoLineTerminator
				(
					('(')=>
					norm1_StrictFormalParameters
				)
			)
			ruleColonSepReturnTypeRef?
			    |
			norm1_BindingIdentifierAsFormalParameter
		)
		'=>'
	)
	(
		(
			('{')=>
			'{'
		)
		norm1_BlockMinusBraces
		'}'
		    |
		norm1_ExpressionDisguisedAsBlock
	)
;

// Rule StrictFormalParameters
ruleStrictFormalParameters:
	'('
	(
		ruleFormalParameter
		(
			','
			ruleFormalParameter
		)*
	)?
	')'
;

// Rule StrictFormalParameters
norm1_StrictFormalParameters:
	'('
	(
		norm1_FormalParameter
		(
			','
			norm1_FormalParameter
		)*
	)?
	')'
;

// Rule BindingIdentifierAsFormalParameter
ruleBindingIdentifierAsFormalParameter:
	ruleBindingIdentifier
;

// Rule BindingIdentifierAsFormalParameter
norm1_BindingIdentifierAsFormalParameter:
	norm1_BindingIdentifier
;

// Rule BlockMinusBraces
ruleBlockMinusBraces:
	ruleStatement
	*
;

// Rule BlockMinusBraces
norm1_BlockMinusBraces:
	norm1_Statement
	*
;

// Rule ExpressionDisguisedAsBlock
ruleExpressionDisguisedAsBlock:
	ruleAssignmentExpressionStatement
;

// Rule ExpressionDisguisedAsBlock
norm1_ExpressionDisguisedAsBlock:
	norm1_AssignmentExpressionStatement
;

// Rule AssignmentExpressionStatement
ruleAssignmentExpressionStatement:
	ruleAssignmentExpression
;

// Rule AssignmentExpressionStatement
norm1_AssignmentExpressionStatement:
	norm1_AssignmentExpression
;

// Rule AnnotatedExpression
ruleAnnotatedExpression:
	ruleExpressionAnnotationList
	(
		'class'
		ruleBindingIdentifier
		?
		ruleClassExtendsImplements?
		ruleMembers
		    |
		ruleAsyncNoTrailingLineBreak
		norm6_FunctionImpl
	)
;

// Rule AnnotatedExpression
norm1_AnnotatedExpression:
	ruleExpressionAnnotationList
	(
		'class'
		norm1_BindingIdentifier
		?
		norm1_ClassExtendsImplements?
		norm1_Members
		    |
		ruleAsyncNoTrailingLineBreak
		norm6_FunctionImpl
	)
;

// Rule TypeVariable
ruleTypeVariable:
	(
		'out'
		    |
		'in'
	)?
	ruleIdentifierOrThis
	(
		'extends'
		ruleTypeRef
	)?
;

// Rule FormalParameter
ruleFormalParameter:
	ruleBindingElementFragment
;

// Rule FormalParameter
norm1_FormalParameter:
	norm1_BindingElementFragment
;

// Rule BindingElementFragment
ruleBindingElementFragment:
	(
		(
			(ruleBindingPattern
			)=>
			ruleBindingPattern
		)
		    |
		ruleAnnotation
		*
		ruleBogusTypeRefFragment?
		'...'
		?
		ruleBindingIdentifier
		ruleColonSepDeclaredTypeRef?
	)
	(
		'='
		norm1_AssignmentExpression
		?
	)?
;

// Rule BindingElementFragment
norm1_BindingElementFragment:
	(
		(
			(norm1_BindingPattern
			)=>
			norm1_BindingPattern
		)
		    |
		ruleAnnotation
		*
		ruleBogusTypeRefFragment?
		'...'
		?
		norm1_BindingIdentifier
		ruleColonSepDeclaredTypeRef?
	)
	(
		'='
		norm3_AssignmentExpression
		?
	)?
;

// Rule BogusTypeRefFragment
ruleBogusTypeRefFragment:
	ruleTypeRefWithModifiers
;

// Rule Block
ruleBlock:
	(
		('{'
		)=>
		'{'
	)
	ruleStatement
	*
	'}'
;

// Rule Block
norm1_Block:
	(
		('{'
		)=>
		'{'
	)
	norm1_Statement
	*
	'}'
;

// Rule RootStatement
ruleRootStatement:
	(
		(
			('{'
			)=>
			ruleBlock
		)
		    |
		(
			(ruleN4Modifier
			*
			ruleAsyncNoTrailingLineBreak
			'function'
			)=>
			ruleFunctionDeclaration
		)
		    |
		(
			(ruleVariableStatementKeyword
			)=>
			norm1_VariableStatement
		)
		    |
		ruleEmptyStatement
		    |
		(
			(ruleBindingIdentifier
			':'
			)=>
			ruleLabelledStatement
		)
		    |
		ruleExpressionStatement
		    |
		ruleIfStatement
		    |
		ruleIterationStatement
		    |
		ruleContinueStatement
		    |
		ruleBreakStatement
		    |
		ruleReturnStatement
		    |
		ruleWithStatement
		    |
		ruleSwitchStatement
		    |
		ruleThrowStatement
		    |
		ruleTryStatement
		    |
		ruleDebuggerStatement
	)
;

// Rule RootStatement
norm1_RootStatement:
	(
		(
			('{'
			)=>
			norm1_Block
		)
		    |
		(
			(ruleN4Modifier
			*
			ruleAsyncNoTrailingLineBreak
			'function'
			)=>
			norm1_FunctionDeclaration
		)
		    |
		(
			(ruleVariableStatementKeyword
			)=>
			norm3_VariableStatement
		)
		    |
		ruleEmptyStatement
		    |
		(
			(norm1_BindingIdentifier
			':'
			)=>
			norm1_LabelledStatement
		)
		    |
		norm1_ExpressionStatement
		    |
		norm1_IfStatement
		    |
		norm1_IterationStatement
		    |
		norm1_ContinueStatement
		    |
		norm1_BreakStatement
		    |
		norm1_ReturnStatement
		    |
		norm1_WithStatement
		    |
		norm1_SwitchStatement
		    |
		norm1_ThrowStatement
		    |
		norm1_TryStatement
		    |
		ruleDebuggerStatement
	)
;

// Rule Statement
ruleStatement:
	(
		(
			('@'
			(
				'This'
				    |
				'target'
				    |
				RULE_IDENTIFIER
			)
			)=>
			ruleAnnotatedFunctionDeclaration
		)
		    |
		ruleRootStatement
	)
;

// Rule Statement
norm1_Statement:
	(
		(
			('@'
			(
				'This'
				    |
				'target'
				    |
				RULE_IDENTIFIER
			)
			)=>
			norm1_AnnotatedFunctionDeclaration
		)
		    |
		norm1_RootStatement
	)
;

// Rule VariableStatement
norm1_VariableStatement:
	(
		(ruleVariableStatementKeyword
		)=>
		ruleVariableStatementKeyword
	)
	norm1_VariableDeclarationOrBinding
	(
		','
		norm1_VariableDeclarationOrBinding
	)*
	ruleSemi
;

// Rule VariableStatement
norm3_VariableStatement:
	(
		(ruleVariableStatementKeyword
		)=>
		ruleVariableStatementKeyword
	)
	norm3_VariableDeclarationOrBinding
	(
		','
		norm3_VariableDeclarationOrBinding
	)*
	ruleSemi
;

// Rule ExportedVariableStatement
ruleExportedVariableStatement:
	ruleN4Modifier
	*
	ruleVariableStatementKeyword
	ruleExportedVariableDeclarationOrBinding
	(
		','
		ruleExportedVariableDeclarationOrBinding
	)*
	ruleSemi
;

// Rule VariableDeclarationOrBinding
ruleVariableDeclarationOrBinding:
	(
		(
			(ruleBindingPattern
			)=>
			ruleVariableBinding
		)
		    |
		norm4_VariableDeclaration
	)
;

// Rule VariableDeclarationOrBinding
norm1_VariableDeclarationOrBinding:
	(
		(
			(ruleBindingPattern
			)=>
			norm1_VariableBinding
		)
		    |
		norm5_VariableDeclaration
	)
;

// Rule VariableDeclarationOrBinding
norm2_VariableDeclarationOrBinding:
	(
		(
			(norm1_BindingPattern
			)=>
			norm2_VariableBinding
		)
		    |
		norm6_VariableDeclaration
	)
;

// Rule VariableDeclarationOrBinding
norm3_VariableDeclarationOrBinding:
	(
		(
			(norm1_BindingPattern
			)=>
			norm3_VariableBinding
		)
		    |
		norm7_VariableDeclaration
	)
;

// Rule VariableDeclarationOrBinding
norm4_VariableDeclarationOrBinding:
	(
		(
			(ruleBindingPattern
			)=>
			norm4_VariableBinding
		)
		    |
		norm4_VariableDeclaration
	)
;

// Rule VariableDeclarationOrBinding
norm6_VariableDeclarationOrBinding:
	(
		(
			(norm1_BindingPattern
			)=>
			norm6_VariableBinding
		)
		    |
		norm6_VariableDeclaration
	)
;

// Rule VariableBinding
ruleVariableBinding:
	(
		(ruleBindingPattern
		)=>
		ruleBindingPattern
	)
	'='
	ruleAssignmentExpression
;

// Rule VariableBinding
norm1_VariableBinding:
	(
		(ruleBindingPattern
		)=>
		ruleBindingPattern
	)
	'='
	norm1_AssignmentExpression
;

// Rule VariableBinding
norm2_VariableBinding:
	(
		(norm1_BindingPattern
		)=>
		norm1_BindingPattern
	)
	'='
	norm2_AssignmentExpression
;

// Rule VariableBinding
norm3_VariableBinding:
	(
		(norm1_BindingPattern
		)=>
		norm1_BindingPattern
	)
	'='
	norm3_AssignmentExpression
;

// Rule VariableBinding
norm4_VariableBinding:
	(
		(ruleBindingPattern
		)=>
		ruleBindingPattern
	)
	(
		'='
		ruleAssignmentExpression
	)?
;

// Rule VariableBinding
norm5_VariableBinding:
	(
		(ruleBindingPattern
		)=>
		ruleBindingPattern
	)
	(
		'='
		norm1_AssignmentExpression
	)?
;

// Rule VariableBinding
norm6_VariableBinding:
	(
		(norm1_BindingPattern
		)=>
		norm1_BindingPattern
	)
	(
		'='
		norm2_AssignmentExpression
	)?
;

// Rule VariableBinding
norm7_VariableBinding:
	(
		(norm1_BindingPattern
		)=>
		norm1_BindingPattern
	)
	(
		'='
		norm3_AssignmentExpression
	)?
;

// Rule VariableDeclaration
norm1_VariableDeclaration:
	norm1_VariableDeclarationImpl
;

// Rule VariableDeclaration
norm3_VariableDeclaration:
	norm3_VariableDeclarationImpl
;

// Rule VariableDeclaration
norm4_VariableDeclaration:
	norm4_VariableDeclarationImpl
;

// Rule VariableDeclaration
norm5_VariableDeclaration:
	norm5_VariableDeclarationImpl
;

// Rule VariableDeclaration
norm6_VariableDeclaration:
	norm6_VariableDeclarationImpl
;

// Rule VariableDeclaration
norm7_VariableDeclaration:
	norm7_VariableDeclarationImpl
;

// Rule VariableDeclarationImpl
ruleVariableDeclarationImpl:
	ruleAnnotation
	*
	(
		(ruleBindingIdentifier
		)=>
		ruleBindingIdentifier
	)
	(
		'='
		ruleAssignmentExpression
	)?
;

// Rule VariableDeclarationImpl
norm1_VariableDeclarationImpl:
	ruleAnnotation
	*
	(
		(ruleBindingIdentifier
		)=>
		ruleBindingIdentifier
	)
	(
		'='
		norm1_AssignmentExpression
	)?
;

// Rule VariableDeclarationImpl
norm2_VariableDeclarationImpl:
	ruleAnnotation
	*
	(
		(norm1_BindingIdentifier
		)=>
		norm1_BindingIdentifier
	)
	(
		'='
		norm2_AssignmentExpression
	)?
;

// Rule VariableDeclarationImpl
norm3_VariableDeclarationImpl:
	ruleAnnotation
	*
	(
		(norm1_BindingIdentifier
		)=>
		norm1_BindingIdentifier
	)
	(
		'='
		norm3_AssignmentExpression
	)?
;

// Rule VariableDeclarationImpl
norm4_VariableDeclarationImpl:
	ruleAnnotation
	*
	(
		(ruleBindingIdentifier
		ruleColonSepDeclaredTypeRef?
		)=>
		ruleBindingIdentifier
		ruleColonSepDeclaredTypeRef?
	)
	(
		'='
		ruleAssignmentExpression
	)?
;

// Rule VariableDeclarationImpl
norm5_VariableDeclarationImpl:
	ruleAnnotation
	*
	(
		(ruleBindingIdentifier
		ruleColonSepDeclaredTypeRef?
		)=>
		ruleBindingIdentifier
		ruleColonSepDeclaredTypeRef?
	)
	(
		'='
		norm1_AssignmentExpression
	)?
;

// Rule VariableDeclarationImpl
norm6_VariableDeclarationImpl:
	ruleAnnotation
	*
	(
		(norm1_BindingIdentifier
		ruleColonSepDeclaredTypeRef?
		)=>
		norm1_BindingIdentifier
		ruleColonSepDeclaredTypeRef?
	)
	(
		'='
		norm2_AssignmentExpression
	)?
;

// Rule VariableDeclarationImpl
norm7_VariableDeclarationImpl:
	ruleAnnotation
	*
	(
		(norm1_BindingIdentifier
		ruleColonSepDeclaredTypeRef?
		)=>
		norm1_BindingIdentifier
		ruleColonSepDeclaredTypeRef?
	)
	(
		'='
		norm3_AssignmentExpression
	)?
;

// Rule ExportedVariableDeclarationOrBinding
ruleExportedVariableDeclarationOrBinding:
	(
		(
			(ruleBindingPattern
			)=>
			ruleExportedVariableBinding
		)
		    |
		ruleExportedVariableDeclaration
	)
;

// Rule ExportedVariableDeclarationOrBinding
norm1_ExportedVariableDeclarationOrBinding:
	(
		(
			(norm1_BindingPattern
			)=>
			norm1_ExportedVariableBinding
		)
		    |
		norm1_ExportedVariableDeclaration
	)
;

// Rule ExportedVariableBinding
ruleExportedVariableBinding:
	(
		(ruleBindingPattern
		)=>
		ruleBindingPattern
	)
	'='
	norm1_AssignmentExpression
;

// Rule ExportedVariableBinding
norm1_ExportedVariableBinding:
	(
		(norm1_BindingPattern
		)=>
		norm1_BindingPattern
	)
	'='
	norm3_AssignmentExpression
;

// Rule ExportedVariableDeclaration
ruleExportedVariableDeclaration:
	norm5_VariableDeclarationImpl
;

// Rule ExportedVariableDeclaration
norm1_ExportedVariableDeclaration:
	norm7_VariableDeclarationImpl
;

// Rule EmptyStatement
ruleEmptyStatement:
	';'
;

// Rule ExpressionStatement
ruleExpressionStatement:
	norm1_Expression
	ruleSemi
;

// Rule ExpressionStatement
norm1_ExpressionStatement:
	norm3_Expression
	ruleSemi
;

// Rule IfStatement
ruleIfStatement:
	'if'
	'('
	norm1_Expression
	')'
	ruleStatement
	(
		(
			('else')=>
			'else'
		)
		ruleStatement
	)?
;

// Rule IfStatement
norm1_IfStatement:
	'if'
	'('
	norm3_Expression
	')'
	norm1_Statement
	(
		(
			('else')=>
			'else'
		)
		norm1_Statement
	)?
;

// Rule IterationStatement
ruleIterationStatement:
	(
		ruleDoStatement
		    |
		ruleWhileStatement
		    |
		ruleForStatement
	)
;

// Rule IterationStatement
norm1_IterationStatement:
	(
		norm1_DoStatement
		    |
		norm1_WhileStatement
		    |
		norm1_ForStatement
	)
;

// Rule DoStatement
ruleDoStatement:
	'do'
	ruleStatement
	'while'
	'('
	norm1_Expression
	')'
	(
		(ruleSemi)=>
		ruleSemi
	)?
;

// Rule DoStatement
norm1_DoStatement:
	'do'
	norm1_Statement
	'while'
	'('
	norm3_Expression
	')'
	(
		(ruleSemi)=>
		ruleSemi
	)?
;

// Rule WhileStatement
ruleWhileStatement:
	'while'
	'('
	norm1_Expression
	')'
	ruleStatement
;

// Rule WhileStatement
norm1_WhileStatement:
	'while'
	'('
	norm3_Expression
	')'
	norm1_Statement
;

// Rule ForStatement
ruleForStatement:
	'for'
	'('
	(
		(
			(ruleLetIdentifierRef
			'in'
			norm1_Expression
			')'
			)=>
			ruleLetIdentifierRef
			'in'
			norm1_Expression
			')'
		)
		    |
		(
			(
				('var' | 'const' | 'let')=>
				ruleVariableStatementKeyword
			)
			(
				(
					(ruleBindingIdentifierAsVariableDeclaration
					(
						'in'
						    |
						'of'
					)
					(
						'await'
						    |
						'@'
						    |
						'('
						    |
						'async'
						    |
						'yield'
						    |
						'get'
						    |
						'set'
						    |
						'let'
						    |
						'project'
						    |
						'external'
						    |
						'abstract'
						    |
						'static'
						    |
						'as'
						    |
						'from'
						    |
						'constructor'
						    |
						'of'
						    |
						'target'
						    |
						'type'
						    |
						'union'
						    |
						'intersection'
						    |
						'This'
						    |
						'Promisify'
						    |
						'implements'
						    |
						'interface'
						    |
						'private'
						    |
						'protected'
						    |
						'public'
						    |
						'out'
						    |
						'new'
						    |
						'this'
						    |
						'super'
						    |
						'<'
						    |
						'import'
						    |
						'true'
						    |
						'false'
						    |
						'null'
						    |
						'/'
						    |
						'/='
						    |
						'['
						    |
						'{'
						    |
						'function'
						    |
						'class'
						    |
						'delete'
						    |
						'void'
						    |
						'typeof'
						    |
						'++'
						    |
						'--'
						    |
						'+'
						    |
						'-'
						    |
						'~'
						    |
						'!'
						    |
						RULE_IDENTIFIER
						    |
						RULE_DOUBLE
						    |
						RULE_INT
						    |
						RULE_BINARY_INT
						    |
						RULE_OCTAL_INT
						    |
						RULE_LEGACY_OCTAL_INT
						    |
						RULE_HEX_INT
						    |
						RULE_SCIENTIFIC_INT
						    |
						RULE_STRING
						    |
						RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
						    |
						RULE_TEMPLATE_HEAD
					)?
					)=>
					ruleBindingIdentifierAsVariableDeclaration
					(
						'in'
						    |
						'of'
					)
					(
						('await' | '@' | '(' | 'async' | 'yield' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | 'new' | 'this' | 'super' | '<' | 'import' | 'true' | 'false' | 'null' | '/' | '/=' | '[' | '{' | 'function' | 'class' | 'delete' | 'void' | 'typeof' | '++' | '--' | '+' | '-' | '~' | '!' | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
						norm1_AssignmentExpression
					)?
				)
				    |
				norm4_VariableDeclarationOrBinding
				(
					(
						','
						ruleVariableDeclarationOrBinding
					)*
					';'
					norm1_Expression
					?
					';'
					norm1_Expression
					?
					    |
					'in'
					norm1_Expression
					?
					    |
					'of'
					norm1_AssignmentExpression
					?
				)
			)
			    |
			ruleExpression
			(
				';'
				norm1_Expression
				?
				';'
				norm1_Expression
				?
				    |
				'in'
				norm1_Expression
				?
				    |
				'of'
				norm1_AssignmentExpression
				?
			)
			    |
			';'
			norm1_Expression
			?
			';'
			norm1_Expression
			?
		)
		')'
	)
	ruleStatement
;

// Rule ForStatement
norm1_ForStatement:
	'for'
	'('
	(
		(
			(ruleLetIdentifierRef
			'in'
			norm3_Expression
			')'
			)=>
			ruleLetIdentifierRef
			'in'
			norm3_Expression
			')'
		)
		    |
		(
			(
				('var' | 'const' | 'let')=>
				ruleVariableStatementKeyword
			)
			(
				(
					(norm2_BindingIdentifierAsVariableDeclaration
					(
						'in'
						    |
						'of'
					)
					(
						'await'
						    |
						'@'
						    |
						'('
						    |
						'async'
						    |
						'get'
						    |
						'set'
						    |
						'let'
						    |
						'project'
						    |
						'external'
						    |
						'abstract'
						    |
						'static'
						    |
						'as'
						    |
						'from'
						    |
						'constructor'
						    |
						'of'
						    |
						'target'
						    |
						'type'
						    |
						'union'
						    |
						'intersection'
						    |
						'This'
						    |
						'Promisify'
						    |
						'implements'
						    |
						'interface'
						    |
						'private'
						    |
						'protected'
						    |
						'public'
						    |
						'out'
						    |
						'yield'
						    |
						'new'
						    |
						'this'
						    |
						'super'
						    |
						'<'
						    |
						'import'
						    |
						'true'
						    |
						'false'
						    |
						'null'
						    |
						'/'
						    |
						'/='
						    |
						'['
						    |
						'{'
						    |
						'function'
						    |
						'class'
						    |
						'delete'
						    |
						'void'
						    |
						'typeof'
						    |
						'++'
						    |
						'--'
						    |
						'+'
						    |
						'-'
						    |
						'~'
						    |
						'!'
						    |
						RULE_IDENTIFIER
						    |
						RULE_DOUBLE
						    |
						RULE_INT
						    |
						RULE_BINARY_INT
						    |
						RULE_OCTAL_INT
						    |
						RULE_LEGACY_OCTAL_INT
						    |
						RULE_HEX_INT
						    |
						RULE_SCIENTIFIC_INT
						    |
						RULE_STRING
						    |
						RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
						    |
						RULE_TEMPLATE_HEAD
					)?
					)=>
					norm2_BindingIdentifierAsVariableDeclaration
					(
						'in'
						    |
						'of'
					)
					(
						('await' | '@' | '(' | 'async' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | 'yield' | 'new' | 'this' | 'super' | '<' | 'import' | 'true' | 'false' | 'null' | '/' | '/=' | '[' | '{' | 'function' | 'class' | 'delete' | 'void' | 'typeof' | '++' | '--' | '+' | '-' | '~' | '!' | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
						norm3_AssignmentExpression
					)?
				)
				    |
				norm6_VariableDeclarationOrBinding
				(
					(
						','
						norm2_VariableDeclarationOrBinding
					)*
					';'
					norm3_Expression
					?
					';'
					norm3_Expression
					?
					    |
					'in'
					norm3_Expression
					?
					    |
					'of'
					norm3_AssignmentExpression
					?
				)
			)
			    |
			norm2_Expression
			(
				';'
				norm3_Expression
				?
				';'
				norm3_Expression
				?
				    |
				'in'
				norm3_Expression
				?
				    |
				'of'
				norm3_AssignmentExpression
				?
			)
			    |
			';'
			norm3_Expression
			?
			';'
			norm3_Expression
			?
		)
		')'
	)
	norm1_Statement
;

// Rule LetIdentifierRef
ruleLetIdentifierRef:
	ruleLetAsIdentifier
;

// Rule LetAsIdentifier
ruleLetAsIdentifier:
	'let'
;

// Rule BindingIdentifierAsVariableDeclaration
ruleBindingIdentifierAsVariableDeclaration:
	ruleBindingIdentifier
;

// Rule BindingIdentifierAsVariableDeclaration
norm2_BindingIdentifierAsVariableDeclaration:
	norm1_BindingIdentifier
;

// Rule ContinueStatement
ruleContinueStatement:
	'continue'
	ruleBindingIdentifier
	?
	ruleSemi
;

// Rule ContinueStatement
norm1_ContinueStatement:
	'continue'
	norm1_BindingIdentifier
	?
	ruleSemi
;

// Rule BreakStatement
ruleBreakStatement:
	'break'
	ruleBindingIdentifier
	?
	ruleSemi
;

// Rule BreakStatement
norm1_BreakStatement:
	'break'
	norm1_BindingIdentifier
	?
	ruleSemi
;

// Rule ReturnStatement
ruleReturnStatement:
	'return'
	norm1_Expression
	?
	ruleSemi
;

// Rule ReturnStatement
norm1_ReturnStatement:
	'return'
	norm3_Expression
	?
	ruleSemi
;

// Rule WithStatement
ruleWithStatement:
	'with'
	'('
	norm1_Expression
	')'
	ruleStatement
;

// Rule WithStatement
norm1_WithStatement:
	'with'
	'('
	norm3_Expression
	')'
	norm1_Statement
;

// Rule SwitchStatement
ruleSwitchStatement:
	'switch'
	'('
	norm1_Expression
	')'
	'{'
	ruleCaseClause
	*
	(
		ruleDefaultClause
		ruleCaseClause
		*
	)?
	'}'
;

// Rule SwitchStatement
norm1_SwitchStatement:
	'switch'
	'('
	norm3_Expression
	')'
	'{'
	norm1_CaseClause
	*
	(
		norm1_DefaultClause
		norm1_CaseClause
		*
	)?
	'}'
;

// Rule CaseClause
ruleCaseClause:
	'case'
	norm1_Expression
	':'
	ruleStatement
	*
;

// Rule CaseClause
norm1_CaseClause:
	'case'
	norm3_Expression
	':'
	norm1_Statement
	*
;

// Rule DefaultClause
ruleDefaultClause:
	'default'
	':'
	ruleStatement
	*
;

// Rule DefaultClause
norm1_DefaultClause:
	'default'
	':'
	norm1_Statement
	*
;

// Rule LabelledStatement
ruleLabelledStatement:
	(
		(ruleBindingIdentifier
		':'
		)=>
		ruleBindingIdentifier
		':'
	)
	ruleStatement
;

// Rule LabelledStatement
norm1_LabelledStatement:
	(
		(norm1_BindingIdentifier
		':'
		)=>
		norm1_BindingIdentifier
		':'
	)
	norm1_Statement
;

// Rule ThrowStatement
ruleThrowStatement:
	'throw'
	norm1_Expression
	ruleSemi
;

// Rule ThrowStatement
norm1_ThrowStatement:
	'throw'
	norm3_Expression
	ruleSemi
;

// Rule TryStatement
ruleTryStatement:
	'try'
	(
		('{'
		)=>
		ruleBlock
	)
	(
		ruleCatchBlock
		ruleFinallyBlock
		?
		    |
		ruleFinallyBlock
	)
;

// Rule TryStatement
norm1_TryStatement:
	'try'
	(
		('{'
		)=>
		norm1_Block
	)
	(
		norm1_CatchBlock
		norm1_FinallyBlock
		?
		    |
		norm1_FinallyBlock
	)
;

// Rule CatchBlock
ruleCatchBlock:
	'catch'
	'('
	ruleCatchVariable
	')'
	(
		('{'
		)=>
		ruleBlock
	)
;

// Rule CatchBlock
norm1_CatchBlock:
	'catch'
	'('
	norm1_CatchVariable
	')'
	(
		('{'
		)=>
		norm1_Block
	)
;

// Rule CatchVariable
ruleCatchVariable:
	(
		(
			(ruleBindingPattern
			)=>
			ruleBindingPattern
		)
		    |
		(
			(ruleBindingIdentifier
			':'
			)=>
			ruleBindingIdentifier
			(
				(':')=>
				ruleColonSepDeclaredTypeRef
			)
		)
		    |
		ruleBogusTypeRefFragment?
		ruleBindingIdentifier
	)
;

// Rule CatchVariable
norm1_CatchVariable:
	(
		(
			(norm1_BindingPattern
			)=>
			norm1_BindingPattern
		)
		    |
		(
			(norm1_BindingIdentifier
			':'
			)=>
			norm1_BindingIdentifier
			(
				(':')=>
				ruleColonSepDeclaredTypeRef
			)
		)
		    |
		ruleBogusTypeRefFragment?
		norm1_BindingIdentifier
	)
;

// Rule FinallyBlock
ruleFinallyBlock:
	'finally'
	(
		('{'
		)=>
		ruleBlock
	)
;

// Rule FinallyBlock
norm1_FinallyBlock:
	'finally'
	(
		('{'
		)=>
		norm1_Block
	)
;

// Rule DebuggerStatement
ruleDebuggerStatement:
	'debugger'
	ruleSemi
;

// Rule PrimaryExpression
rulePrimaryExpression:
	(
		ruleThisLiteral
		    |
		ruleSuperLiteral
		    |
		ruleIdentifierRef
		    |
		ruleJSXFragment
		    |
		ruleJSXElement
		    |
		ruleImportCallExpression
		    |
		ruleParameterizedCallExpression
		    |
		ruleLiteral
		    |
		ruleArrayLiteral
		    |
		ruleObjectLiteral
		    |
		ruleParenExpression
		    |
		ruleAnnotatedExpression
		    |
		ruleFunctionExpression
		    |
		(
			('async'
			ruleNoLineTerminator
			'function'
			)=>
			ruleAsyncFunctionExpression
		)
		    |
		ruleN4ClassExpression
		    |
		ruleTemplateLiteral
	)
;

// Rule PrimaryExpression
norm1_PrimaryExpression:
	(
		ruleThisLiteral
		    |
		ruleSuperLiteral
		    |
		norm1_IdentifierRef
		    |
		ruleJSXFragment
		    |
		ruleJSXElement
		    |
		norm1_ImportCallExpression
		    |
		norm1_ParameterizedCallExpression
		    |
		ruleLiteral
		    |
		norm1_ArrayLiteral
		    |
		norm1_ObjectLiteral
		    |
		norm1_ParenExpression
		    |
		norm1_AnnotatedExpression
		    |
		ruleFunctionExpression
		    |
		(
			('async'
			ruleNoLineTerminator
			'function'
			)=>
			ruleAsyncFunctionExpression
		)
		    |
		norm1_N4ClassExpression
		    |
		norm1_TemplateLiteral
	)
;

// Rule ParenExpression
ruleParenExpression:
	'('
	norm1_Expression
	')'
;

// Rule ParenExpression
norm1_ParenExpression:
	'('
	norm3_Expression
	')'
;

// Rule IdentifierRef
ruleIdentifierRef:
	(
		ruleBindingIdentifier
		    |
		ruleBindingIdentifier
		ruleVersionRequest
	)
;

// Rule IdentifierRef
norm1_IdentifierRef:
	(
		norm1_BindingIdentifier
		    |
		norm1_BindingIdentifier
		ruleVersionRequest
	)
;

// Rule SuperLiteral
ruleSuperLiteral:
	'super'
;

// Rule ThisLiteral
ruleThisLiteral:
	'this'
;

// Rule ArrayLiteral
ruleArrayLiteral:
	'['
	ruleArrayPadding
	*
	(
		ruleArrayElement
		(
			','
			ruleArrayPadding
			*
			ruleArrayElement
		)*
		(
			','
			ruleArrayPadding
			*
		)?
	)?
	']'
;

// Rule ArrayLiteral
norm1_ArrayLiteral:
	'['
	ruleArrayPadding
	*
	(
		norm1_ArrayElement
		(
			','
			ruleArrayPadding
			*
			norm1_ArrayElement
		)*
		(
			','
			ruleArrayPadding
			*
		)?
	)?
	']'
;

// Rule ArrayPadding
ruleArrayPadding:
	','
;

// Rule ArrayElement
ruleArrayElement:
	'...'
	?
	norm1_AssignmentExpression
;

// Rule ArrayElement
norm1_ArrayElement:
	'...'
	?
	norm3_AssignmentExpression
;

// Rule ObjectLiteral
ruleObjectLiteral:
	'{'
	(
		rulePropertyAssignment
		(
			','
			rulePropertyAssignment
		)*
		','?
	)?
	'}'
;

// Rule ObjectLiteral
norm1_ObjectLiteral:
	'{'
	(
		norm1_PropertyAssignment
		(
			','
			norm1_PropertyAssignment
		)*
		','?
	)?
	'}'
;

// Rule PropertyAssignment
rulePropertyAssignment:
	(
		ruleAnnotatedPropertyAssignment
		    |
		(
			(ruleTypeRefWithModifiers
			?
			ruleLiteralOrComputedPropertyName
			'?'
			?
			':'
			)=>
			rulePropertyNameValuePair
		)
		    |
		(
			(ruleGetterHeader
			)=>
			rulePropertyGetterDeclaration
		)
		    |
		(
			('set'
			(
				'break'
				    |
				'case'
				    |
				'catch'
				    |
				'class'
				    |
				'const'
				    |
				'continue'
				    |
				'debugger'
				    |
				'default'
				    |
				'delete'
				    |
				'do'
				    |
				'else'
				    |
				'export'
				    |
				'extends'
				    |
				'finally'
				    |
				'for'
				    |
				'function'
				    |
				'if'
				    |
				'import'
				    |
				'in'
				    |
				'instanceof'
				    |
				'new'
				    |
				'return'
				    |
				'super'
				    |
				'switch'
				    |
				'this'
				    |
				'throw'
				    |
				'try'
				    |
				'typeof'
				    |
				'var'
				    |
				'void'
				    |
				'while'
				    |
				'with'
				    |
				'yield'
				    |
				'null'
				    |
				'true'
				    |
				'false'
				    |
				'enum'
				    |
				'get'
				    |
				'set'
				    |
				'let'
				    |
				'project'
				    |
				'external'
				    |
				'abstract'
				    |
				'static'
				    |
				'as'
				    |
				'from'
				    |
				'constructor'
				    |
				'of'
				    |
				'target'
				    |
				'type'
				    |
				'union'
				    |
				'intersection'
				    |
				'This'
				    |
				'Promisify'
				    |
				'await'
				    |
				'async'
				    |
				'implements'
				    |
				'interface'
				    |
				'private'
				    |
				'protected'
				    |
				'public'
				    |
				'out'
				    |
				'['
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
			)=>
			rulePropertySetterDeclaration
		)
		    |
		(
			(ruleTypeVariables?
			ruleTypeRefWithModifiers
			?
			(
				'*'
				ruleLiteralOrComputedPropertyName
				'('
				    |
				ruleLiteralOrComputedPropertyName
				'('
			)
			)=>
			rulePropertyMethodDeclaration
		)
		    |
		rulePropertyNameValuePairSingleName
		    |
		rulePropertySpread
	)
;

// Rule PropertyAssignment
norm1_PropertyAssignment:
	(
		norm1_AnnotatedPropertyAssignment
		    |
		(
			(ruleTypeRefWithModifiers
			?
			norm1_LiteralOrComputedPropertyName
			'?'
			?
			':'
			)=>
			norm1_PropertyNameValuePair
		)
		    |
		(
			(norm1_GetterHeader
			)=>
			norm1_PropertyGetterDeclaration
		)
		    |
		(
			('set'
			(
				'break'
				    |
				'case'
				    |
				'catch'
				    |
				'class'
				    |
				'const'
				    |
				'continue'
				    |
				'debugger'
				    |
				'default'
				    |
				'delete'
				    |
				'do'
				    |
				'else'
				    |
				'export'
				    |
				'extends'
				    |
				'finally'
				    |
				'for'
				    |
				'function'
				    |
				'if'
				    |
				'import'
				    |
				'in'
				    |
				'instanceof'
				    |
				'new'
				    |
				'return'
				    |
				'super'
				    |
				'switch'
				    |
				'this'
				    |
				'throw'
				    |
				'try'
				    |
				'typeof'
				    |
				'var'
				    |
				'void'
				    |
				'while'
				    |
				'with'
				    |
				'yield'
				    |
				'null'
				    |
				'true'
				    |
				'false'
				    |
				'enum'
				    |
				'get'
				    |
				'set'
				    |
				'let'
				    |
				'project'
				    |
				'external'
				    |
				'abstract'
				    |
				'static'
				    |
				'as'
				    |
				'from'
				    |
				'constructor'
				    |
				'of'
				    |
				'target'
				    |
				'type'
				    |
				'union'
				    |
				'intersection'
				    |
				'This'
				    |
				'Promisify'
				    |
				'await'
				    |
				'async'
				    |
				'implements'
				    |
				'interface'
				    |
				'private'
				    |
				'protected'
				    |
				'public'
				    |
				'out'
				    |
				'['
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
			)=>
			norm1_PropertySetterDeclaration
		)
		    |
		(
			(ruleTypeVariables?
			ruleTypeRefWithModifiers
			?
			(
				'*'
				norm1_LiteralOrComputedPropertyName
				'('
				    |
				norm1_LiteralOrComputedPropertyName
				'('
			)
			)=>
			norm1_PropertyMethodDeclaration
		)
		    |
		norm1_PropertyNameValuePairSingleName
		    |
		norm1_PropertySpread
	)
;

// Rule AnnotatedPropertyAssignment
ruleAnnotatedPropertyAssignment:
	rulePropertyAssignmentAnnotationList
	(
		(
			(ruleTypeRefWithModifiers
			?
			ruleLiteralOrComputedPropertyName
			':'
			)=>
			ruleTypeRefWithModifiers
			?
			ruleLiteralOrComputedPropertyName
			':'
		)
		norm1_AssignmentExpression
		    |
		(
			(ruleGetterHeader
			)=>
			ruleGetterHeader
		)
		(
			('{'
			)=>
			ruleBlock
		)
		    |
		(
			('set'
			(
				'break'
				    |
				'case'
				    |
				'catch'
				    |
				'class'
				    |
				'const'
				    |
				'continue'
				    |
				'debugger'
				    |
				'default'
				    |
				'delete'
				    |
				'do'
				    |
				'else'
				    |
				'export'
				    |
				'extends'
				    |
				'finally'
				    |
				'for'
				    |
				'function'
				    |
				'if'
				    |
				'import'
				    |
				'in'
				    |
				'instanceof'
				    |
				'new'
				    |
				'return'
				    |
				'super'
				    |
				'switch'
				    |
				'this'
				    |
				'throw'
				    |
				'try'
				    |
				'typeof'
				    |
				'var'
				    |
				'void'
				    |
				'while'
				    |
				'with'
				    |
				'yield'
				    |
				'null'
				    |
				'true'
				    |
				'false'
				    |
				'enum'
				    |
				'get'
				    |
				'set'
				    |
				'let'
				    |
				'project'
				    |
				'external'
				    |
				'abstract'
				    |
				'static'
				    |
				'as'
				    |
				'from'
				    |
				'constructor'
				    |
				'of'
				    |
				'target'
				    |
				'type'
				    |
				'union'
				    |
				'intersection'
				    |
				'This'
				    |
				'Promisify'
				    |
				'await'
				    |
				'async'
				    |
				'implements'
				    |
				'interface'
				    |
				'private'
				    |
				'protected'
				    |
				'public'
				    |
				'out'
				    |
				'['
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
			)=>
			'set'
			(
				('break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' | 'null' | 'true' | 'false' | 'enum' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '[' | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
				ruleLiteralOrComputedPropertyName
			)
		)
		'?'
		?
		'('
		ruleFormalParameter
		')'
		(
			('{'
			)=>
			ruleBlock
		)
		    |
		(
			(ruleTypeVariables?
			ruleTypeRefWithModifiers
			?
			(
				'*'
				ruleLiteralOrComputedPropertyName
				'('
				    |
				ruleLiteralOrComputedPropertyName
				'('
			)
			)=>
			ruleTypeVariables?
			ruleTypeRefWithModifiers
			?
			(
				'*'
				ruleLiteralOrComputedPropertyName
				(
					('(')=>
					norm1_MethodParamsAndBody
				)
				    |
				ruleLiteralOrComputedPropertyName
				(
					('(')=>
					ruleMethodParamsAndBody
				)
			)
		)
		';'?
		    |
		ruleTypeRef
		?
		ruleIdentifierRef
		(
			'='
			norm1_AssignmentExpression
		)?
		    |
		'...'
		norm1_AssignmentExpression
	)
;

// Rule AnnotatedPropertyAssignment
norm1_AnnotatedPropertyAssignment:
	rulePropertyAssignmentAnnotationList
	(
		(
			(ruleTypeRefWithModifiers
			?
			norm1_LiteralOrComputedPropertyName
			':'
			)=>
			ruleTypeRefWithModifiers
			?
			norm1_LiteralOrComputedPropertyName
			':'
		)
		norm3_AssignmentExpression
		    |
		(
			(norm1_GetterHeader
			)=>
			norm1_GetterHeader
		)
		(
			('{'
			)=>
			ruleBlock
		)
		    |
		(
			('set'
			(
				'break'
				    |
				'case'
				    |
				'catch'
				    |
				'class'
				    |
				'const'
				    |
				'continue'
				    |
				'debugger'
				    |
				'default'
				    |
				'delete'
				    |
				'do'
				    |
				'else'
				    |
				'export'
				    |
				'extends'
				    |
				'finally'
				    |
				'for'
				    |
				'function'
				    |
				'if'
				    |
				'import'
				    |
				'in'
				    |
				'instanceof'
				    |
				'new'
				    |
				'return'
				    |
				'super'
				    |
				'switch'
				    |
				'this'
				    |
				'throw'
				    |
				'try'
				    |
				'typeof'
				    |
				'var'
				    |
				'void'
				    |
				'while'
				    |
				'with'
				    |
				'yield'
				    |
				'null'
				    |
				'true'
				    |
				'false'
				    |
				'enum'
				    |
				'get'
				    |
				'set'
				    |
				'let'
				    |
				'project'
				    |
				'external'
				    |
				'abstract'
				    |
				'static'
				    |
				'as'
				    |
				'from'
				    |
				'constructor'
				    |
				'of'
				    |
				'target'
				    |
				'type'
				    |
				'union'
				    |
				'intersection'
				    |
				'This'
				    |
				'Promisify'
				    |
				'await'
				    |
				'async'
				    |
				'implements'
				    |
				'interface'
				    |
				'private'
				    |
				'protected'
				    |
				'public'
				    |
				'out'
				    |
				'['
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
			)=>
			'set'
			(
				('break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' | 'null' | 'true' | 'false' | 'enum' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '[' | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
				norm1_LiteralOrComputedPropertyName
			)
		)
		'?'
		?
		'('
		norm1_FormalParameter
		')'
		(
			('{'
			)=>
			ruleBlock
		)
		    |
		(
			(ruleTypeVariables?
			ruleTypeRefWithModifiers
			?
			(
				'*'
				norm1_LiteralOrComputedPropertyName
				'('
				    |
				norm1_LiteralOrComputedPropertyName
				'('
			)
			)=>
			ruleTypeVariables?
			ruleTypeRefWithModifiers
			?
			(
				'*'
				norm1_LiteralOrComputedPropertyName
				(
					('(')=>
					norm1_MethodParamsAndBody
				)
				    |
				norm1_LiteralOrComputedPropertyName
				(
					('(')=>
					ruleMethodParamsAndBody
				)
			)
		)
		';'?
		    |
		ruleTypeRef
		?
		norm1_IdentifierRef
		(
			'='
			norm3_AssignmentExpression
		)?
		    |
		'...'
		norm3_AssignmentExpression
	)
;

// Rule PropertyMethodDeclaration
rulePropertyMethodDeclaration:
	(
		(ruleTypeVariables?
		ruleTypeRefWithModifiers
		?
		(
			'*'
			ruleLiteralOrComputedPropertyName
			'('
			    |
			ruleLiteralOrComputedPropertyName
			'('
		)
		)=>
		ruleTypeVariables?
		ruleTypeRefWithModifiers
		?
		(
			'*'
			ruleLiteralOrComputedPropertyName
			(
				('(')=>
				norm1_MethodParamsAndBody
			)
			    |
			ruleLiteralOrComputedPropertyName
			(
				('(')=>
				ruleMethodParamsAndBody
			)
		)
	)
	';'?
;

// Rule PropertyMethodDeclaration
norm1_PropertyMethodDeclaration:
	(
		(ruleTypeVariables?
		ruleTypeRefWithModifiers
		?
		(
			'*'
			norm1_LiteralOrComputedPropertyName
			'('
			    |
			norm1_LiteralOrComputedPropertyName
			'('
		)
		)=>
		ruleTypeVariables?
		ruleTypeRefWithModifiers
		?
		(
			'*'
			norm1_LiteralOrComputedPropertyName
			(
				('(')=>
				norm1_MethodParamsAndBody
			)
			    |
			norm1_LiteralOrComputedPropertyName
			(
				('(')=>
				ruleMethodParamsAndBody
			)
		)
	)
	';'?
;

// Rule PropertyNameValuePair
rulePropertyNameValuePair:
	(
		(ruleTypeRefWithModifiers
		?
		ruleLiteralOrComputedPropertyName
		'?'
		?
		':'
		)=>
		ruleTypeRefWithModifiers
		?
		ruleLiteralOrComputedPropertyName
		'?'
		?
		':'
	)
	norm1_AssignmentExpression
;

// Rule PropertyNameValuePair
norm1_PropertyNameValuePair:
	(
		(ruleTypeRefWithModifiers
		?
		norm1_LiteralOrComputedPropertyName
		'?'
		?
		':'
		)=>
		ruleTypeRefWithModifiers
		?
		norm1_LiteralOrComputedPropertyName
		'?'
		?
		':'
	)
	norm3_AssignmentExpression
;

// Rule PropertyNameValuePairSingleName
rulePropertyNameValuePairSingleName:
	ruleTypeRef
	?
	ruleIdentifierRef
	(
		'='
		norm1_AssignmentExpression
	)?
;

// Rule PropertyNameValuePairSingleName
norm1_PropertyNameValuePairSingleName:
	ruleTypeRef
	?
	norm1_IdentifierRef
	(
		'='
		norm3_AssignmentExpression
	)?
;

// Rule PropertyGetterDeclaration
rulePropertyGetterDeclaration:
	(
		(ruleGetterHeader
		)=>
		ruleGetterHeader
	)
	(
		('{'
		)=>
		ruleBlock
	)
;

// Rule PropertyGetterDeclaration
norm1_PropertyGetterDeclaration:
	(
		(norm1_GetterHeader
		)=>
		norm1_GetterHeader
	)
	(
		('{'
		)=>
		ruleBlock
	)
;

// Rule PropertySetterDeclaration
rulePropertySetterDeclaration:
	(
		('set'
		(
			'break'
			    |
			'case'
			    |
			'catch'
			    |
			'class'
			    |
			'const'
			    |
			'continue'
			    |
			'debugger'
			    |
			'default'
			    |
			'delete'
			    |
			'do'
			    |
			'else'
			    |
			'export'
			    |
			'extends'
			    |
			'finally'
			    |
			'for'
			    |
			'function'
			    |
			'if'
			    |
			'import'
			    |
			'in'
			    |
			'instanceof'
			    |
			'new'
			    |
			'return'
			    |
			'super'
			    |
			'switch'
			    |
			'this'
			    |
			'throw'
			    |
			'try'
			    |
			'typeof'
			    |
			'var'
			    |
			'void'
			    |
			'while'
			    |
			'with'
			    |
			'yield'
			    |
			'null'
			    |
			'true'
			    |
			'false'
			    |
			'enum'
			    |
			'get'
			    |
			'set'
			    |
			'let'
			    |
			'project'
			    |
			'external'
			    |
			'abstract'
			    |
			'static'
			    |
			'as'
			    |
			'from'
			    |
			'constructor'
			    |
			'of'
			    |
			'target'
			    |
			'type'
			    |
			'union'
			    |
			'intersection'
			    |
			'This'
			    |
			'Promisify'
			    |
			'await'
			    |
			'async'
			    |
			'implements'
			    |
			'interface'
			    |
			'private'
			    |
			'protected'
			    |
			'public'
			    |
			'out'
			    |
			'['
			    |
			RULE_IDENTIFIER
			    |
			RULE_STRING
			    |
			RULE_DOUBLE
			    |
			RULE_INT
			    |
			RULE_OCTAL_INT
			    |
			RULE_HEX_INT
			    |
			RULE_SCIENTIFIC_INT
		)
		)=>
		'set'
		(
			('break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' | 'null' | 'true' | 'false' | 'enum' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '[' | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
			ruleLiteralOrComputedPropertyName
		)
	)
	'?'
	?
	'('
	ruleFormalParameter
	')'
	(
		('{'
		)=>
		ruleBlock
	)
;

// Rule PropertySetterDeclaration
norm1_PropertySetterDeclaration:
	(
		('set'
		(
			'break'
			    |
			'case'
			    |
			'catch'
			    |
			'class'
			    |
			'const'
			    |
			'continue'
			    |
			'debugger'
			    |
			'default'
			    |
			'delete'
			    |
			'do'
			    |
			'else'
			    |
			'export'
			    |
			'extends'
			    |
			'finally'
			    |
			'for'
			    |
			'function'
			    |
			'if'
			    |
			'import'
			    |
			'in'
			    |
			'instanceof'
			    |
			'new'
			    |
			'return'
			    |
			'super'
			    |
			'switch'
			    |
			'this'
			    |
			'throw'
			    |
			'try'
			    |
			'typeof'
			    |
			'var'
			    |
			'void'
			    |
			'while'
			    |
			'with'
			    |
			'yield'
			    |
			'null'
			    |
			'true'
			    |
			'false'
			    |
			'enum'
			    |
			'get'
			    |
			'set'
			    |
			'let'
			    |
			'project'
			    |
			'external'
			    |
			'abstract'
			    |
			'static'
			    |
			'as'
			    |
			'from'
			    |
			'constructor'
			    |
			'of'
			    |
			'target'
			    |
			'type'
			    |
			'union'
			    |
			'intersection'
			    |
			'This'
			    |
			'Promisify'
			    |
			'await'
			    |
			'async'
			    |
			'implements'
			    |
			'interface'
			    |
			'private'
			    |
			'protected'
			    |
			'public'
			    |
			'out'
			    |
			'['
			    |
			RULE_IDENTIFIER
			    |
			RULE_STRING
			    |
			RULE_DOUBLE
			    |
			RULE_INT
			    |
			RULE_OCTAL_INT
			    |
			RULE_HEX_INT
			    |
			RULE_SCIENTIFIC_INT
		)
		)=>
		'set'
		(
			('break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' | 'null' | 'true' | 'false' | 'enum' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '[' | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
			norm1_LiteralOrComputedPropertyName
		)
	)
	'?'
	?
	'('
	norm1_FormalParameter
	')'
	(
		('{'
		)=>
		ruleBlock
	)
;

// Rule PropertySpread
rulePropertySpread:
	'...'
	norm1_AssignmentExpression
;

// Rule PropertySpread
norm1_PropertySpread:
	'...'
	norm3_AssignmentExpression
;

// Rule ParameterizedCallExpression
ruleParameterizedCallExpression:
	ruleConcreteTypeArguments
	ruleIdentifierRef
	ruleArgumentsWithParentheses
;

// Rule ParameterizedCallExpression
norm1_ParameterizedCallExpression:
	ruleConcreteTypeArguments
	norm1_IdentifierRef
	norm1_ArgumentsWithParentheses
;

// Rule ConcreteTypeArguments
ruleConcreteTypeArguments:
	'<'
	ruleTypeRef
	(
		','
		ruleTypeRef
	)*
	'>'
;

// Rule ImportCallExpression
ruleImportCallExpression:
	'import'
	ruleArgumentsWithParentheses
;

// Rule ImportCallExpression
norm1_ImportCallExpression:
	'import'
	norm1_ArgumentsWithParentheses
;

// Rule LeftHandSideExpression
ruleLeftHandSideExpression:
	ruleMemberExpression
	(
		ruleArgumentsWithParentheses
		(
			ruleArgumentsWithParentheses
			    |
			ruleIndexedAccessExpressionTail
			    |
			ruleParameterizedPropertyAccessExpressionTail
			    |
			(
				(RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
				ruleTemplateLiteral
			)
		)*
	)?
;

// Rule LeftHandSideExpression
norm1_LeftHandSideExpression:
	norm1_MemberExpression
	(
		norm1_ArgumentsWithParentheses
		(
			norm1_ArgumentsWithParentheses
			    |
			norm1_IndexedAccessExpressionTail
			    |
			norm1_ParameterizedPropertyAccessExpressionTail
			    |
			(
				(RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
				norm1_TemplateLiteral
			)
		)*
	)?
;

// Rule ArgumentsWithParentheses
ruleArgumentsWithParentheses:
	'('
	ruleArguments?
	')'
;

// Rule ArgumentsWithParentheses
norm1_ArgumentsWithParentheses:
	'('
	norm1_Arguments?
	')'
;

// Rule Arguments
ruleArguments:
	ruleArgument
	(
		','
		ruleArgument
	)*
;

// Rule Arguments
norm1_Arguments:
	norm1_Argument
	(
		','
		norm1_Argument
	)*
;

// Rule Argument
ruleArgument:
	'...'
	?
	norm1_AssignmentExpression
;

// Rule Argument
norm1_Argument:
	'...'
	?
	norm3_AssignmentExpression
;

// Rule MemberExpression
ruleMemberExpression:
	(
		(
			('new'
			'.'
			)=>
			'new'
			'.'
		)
		'target'
		    |
		(
			('new'
			)=>
			'new'
		)
		ruleMemberExpression
		(
			('<')=>
			ruleConcreteTypeArguments
		)?
		(
			(
				('('
				)=>
				'('
			)
			ruleArguments?
			')'
			(
				ruleIndexedAccessExpressionTail
				    |
				ruleParameterizedPropertyAccessExpressionTail
				    |
				ruleTemplateLiteral
			)*
		)?
		    |
		rulePrimaryExpression
		(
			ruleIndexedAccessExpressionTail
			    |
			ruleParameterizedPropertyAccessExpressionTail
			    |
			ruleTemplateLiteral
		)*
	)
;

// Rule MemberExpression
norm1_MemberExpression:
	(
		(
			('new'
			'.'
			)=>
			'new'
			'.'
		)
		'target'
		    |
		(
			('new'
			)=>
			'new'
		)
		norm1_MemberExpression
		(
			('<')=>
			ruleConcreteTypeArguments
		)?
		(
			(
				('('
				)=>
				'('
			)
			norm1_Arguments?
			')'
			(
				norm1_IndexedAccessExpressionTail
				    |
				norm1_ParameterizedPropertyAccessExpressionTail
				    |
				norm1_TemplateLiteral
			)*
		)?
		    |
		norm1_PrimaryExpression
		(
			norm1_IndexedAccessExpressionTail
			    |
			norm1_ParameterizedPropertyAccessExpressionTail
			    |
			norm1_TemplateLiteral
		)*
	)
;

// Rule IndexedAccessExpressionTail
ruleIndexedAccessExpressionTail:
	'['
	norm1_Expression
	']'
;

// Rule IndexedAccessExpressionTail
norm1_IndexedAccessExpressionTail:
	'['
	norm3_Expression
	']'
;

// Rule ParameterizedPropertyAccessExpressionTail
ruleParameterizedPropertyAccessExpressionTail:
	'.'
	ruleConcreteTypeArguments?
	ruleIdentifierName
;

// Rule ParameterizedPropertyAccessExpressionTail
norm1_ParameterizedPropertyAccessExpressionTail:
	'.'
	ruleConcreteTypeArguments?
	ruleIdentifierName
;

// Rule PostfixExpression
rulePostfixExpression:
	ruleLeftHandSideExpression
	(
		(rulePostfixOperator
		)=>
		rulePostfixOperator
	)?
;

// Rule PostfixExpression
norm1_PostfixExpression:
	norm1_LeftHandSideExpression
	(
		(rulePostfixOperator
		)=>
		rulePostfixOperator
	)?
;

// Rule CastExpression
ruleCastExpression:
	rulePostfixExpression
	(
		(
			('as'
			)=>
			'as'
		)
		ruleArrayTypeExpression
	)?
;

// Rule CastExpression
norm1_CastExpression:
	norm1_PostfixExpression
	(
		(
			('as'
			)=>
			'as'
		)
		ruleArrayTypeExpression
	)?
;

// Rule UnaryExpression
ruleUnaryExpression:
	(
		ruleCastExpression
		    |
		ruleUnaryOperator
		ruleUnaryExpression
	)
;

// Rule UnaryExpression
norm1_UnaryExpression:
	(
		norm1_CastExpression
		    |
		ruleUnaryOperator
		norm1_UnaryExpression
	)
;

// Rule MultiplicativeExpression
ruleMultiplicativeExpression:
	ruleUnaryExpression
	(
		(
			(ruleMultiplicativeOperator
			)=>
			ruleMultiplicativeOperator
		)
		ruleUnaryExpression
	)*
;

// Rule MultiplicativeExpression
norm1_MultiplicativeExpression:
	norm1_UnaryExpression
	(
		(
			(ruleMultiplicativeOperator
			)=>
			ruleMultiplicativeOperator
		)
		norm1_UnaryExpression
	)*
;

// Rule AdditiveExpression
ruleAdditiveExpression:
	ruleMultiplicativeExpression
	(
		(
			(ruleAdditiveOperator
			)=>
			ruleAdditiveOperator
		)
		ruleMultiplicativeExpression
	)*
;

// Rule AdditiveExpression
norm1_AdditiveExpression:
	norm1_MultiplicativeExpression
	(
		(
			(ruleAdditiveOperator
			)=>
			ruleAdditiveOperator
		)
		norm1_MultiplicativeExpression
	)*
;

// Rule ShiftExpression
ruleShiftExpression:
	ruleAdditiveExpression
	(
		(ruleShiftOperator
		(
			'new'
			    |
			'this'
			    |
			'super'
			    |
			'yield'
			    |
			'get'
			    |
			'set'
			    |
			'let'
			    |
			'project'
			    |
			'external'
			    |
			'abstract'
			    |
			'static'
			    |
			'as'
			    |
			'from'
			    |
			'constructor'
			    |
			'of'
			    |
			'target'
			    |
			'type'
			    |
			'union'
			    |
			'intersection'
			    |
			'This'
			    |
			'Promisify'
			    |
			'await'
			    |
			'async'
			    |
			'implements'
			    |
			'interface'
			    |
			'private'
			    |
			'protected'
			    |
			'public'
			    |
			'out'
			    |
			'<'
			    |
			'import'
			    |
			'true'
			    |
			'false'
			    |
			'null'
			    |
			'/'
			    |
			'/='
			    |
			'['
			    |
			'{'
			    |
			'('
			    |
			'@'
			    |
			'function'
			    |
			'class'
			    |
			'delete'
			    |
			'void'
			    |
			'typeof'
			    |
			'++'
			    |
			'--'
			    |
			'+'
			    |
			'-'
			    |
			'~'
			    |
			'!'
			    |
			RULE_IDENTIFIER
			    |
			RULE_DOUBLE
			    |
			RULE_INT
			    |
			RULE_BINARY_INT
			    |
			RULE_OCTAL_INT
			    |
			RULE_LEGACY_OCTAL_INT
			    |
			RULE_HEX_INT
			    |
			RULE_SCIENTIFIC_INT
			    |
			RULE_STRING
			    |
			RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
			    |
			RULE_TEMPLATE_HEAD
		)
		)=>
		ruleShiftOperator
		(
			('new' | 'this' | 'super' | 'yield' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '<' | 'import' | 'true' | 'false' | 'null' | '/' | '/=' | '[' | '{' | '(' | '@' | 'function' | 'class' | 'delete' | 'void' | 'typeof' | '++' | '--' | '+' | '-' | '~' | '!' | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
			ruleAdditiveExpression
		)
	)*
;

// Rule ShiftExpression
norm1_ShiftExpression:
	norm1_AdditiveExpression
	(
		(ruleShiftOperator
		(
			'new'
			    |
			'this'
			    |
			'super'
			    |
			'get'
			    |
			'set'
			    |
			'let'
			    |
			'project'
			    |
			'external'
			    |
			'abstract'
			    |
			'static'
			    |
			'as'
			    |
			'from'
			    |
			'constructor'
			    |
			'of'
			    |
			'target'
			    |
			'type'
			    |
			'union'
			    |
			'intersection'
			    |
			'This'
			    |
			'Promisify'
			    |
			'await'
			    |
			'async'
			    |
			'implements'
			    |
			'interface'
			    |
			'private'
			    |
			'protected'
			    |
			'public'
			    |
			'out'
			    |
			'<'
			    |
			'import'
			    |
			'true'
			    |
			'false'
			    |
			'null'
			    |
			'/'
			    |
			'/='
			    |
			'['
			    |
			'{'
			    |
			'('
			    |
			'@'
			    |
			'function'
			    |
			'class'
			    |
			'delete'
			    |
			'void'
			    |
			'typeof'
			    |
			'++'
			    |
			'--'
			    |
			'+'
			    |
			'-'
			    |
			'~'
			    |
			'!'
			    |
			RULE_IDENTIFIER
			    |
			RULE_DOUBLE
			    |
			RULE_INT
			    |
			RULE_BINARY_INT
			    |
			RULE_OCTAL_INT
			    |
			RULE_LEGACY_OCTAL_INT
			    |
			RULE_HEX_INT
			    |
			RULE_SCIENTIFIC_INT
			    |
			RULE_STRING
			    |
			RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
			    |
			RULE_TEMPLATE_HEAD
		)
		)=>
		ruleShiftOperator
		(
			('new' | 'this' | 'super' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '<' | 'import' | 'true' | 'false' | 'null' | '/' | '/=' | '[' | '{' | '(' | '@' | 'function' | 'class' | 'delete' | 'void' | 'typeof' | '++' | '--' | '+' | '-' | '~' | '!' | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
			norm1_AdditiveExpression
		)
	)*
;

// Rule ShiftOperator
ruleShiftOperator:
	(
		'>'
		'>'
		'>'?
		    |
		'<<'
	)
;

// Rule RelationalExpression
ruleRelationalExpression:
	ruleShiftExpression
	(
		(ruleRelationalOperator
		(
			'new'
			    |
			'this'
			    |
			'super'
			    |
			'yield'
			    |
			'get'
			    |
			'set'
			    |
			'let'
			    |
			'project'
			    |
			'external'
			    |
			'abstract'
			    |
			'static'
			    |
			'as'
			    |
			'from'
			    |
			'constructor'
			    |
			'of'
			    |
			'target'
			    |
			'type'
			    |
			'union'
			    |
			'intersection'
			    |
			'This'
			    |
			'Promisify'
			    |
			'await'
			    |
			'async'
			    |
			'implements'
			    |
			'interface'
			    |
			'private'
			    |
			'protected'
			    |
			'public'
			    |
			'out'
			    |
			'<'
			    |
			'import'
			    |
			'true'
			    |
			'false'
			    |
			'null'
			    |
			'/'
			    |
			'/='
			    |
			'['
			    |
			'{'
			    |
			'('
			    |
			'@'
			    |
			'function'
			    |
			'class'
			    |
			'delete'
			    |
			'void'
			    |
			'typeof'
			    |
			'++'
			    |
			'--'
			    |
			'+'
			    |
			'-'
			    |
			'~'
			    |
			'!'
			    |
			RULE_IDENTIFIER
			    |
			RULE_DOUBLE
			    |
			RULE_INT
			    |
			RULE_BINARY_INT
			    |
			RULE_OCTAL_INT
			    |
			RULE_LEGACY_OCTAL_INT
			    |
			RULE_HEX_INT
			    |
			RULE_SCIENTIFIC_INT
			    |
			RULE_STRING
			    |
			RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
			    |
			RULE_TEMPLATE_HEAD
		)
		)=>
		ruleRelationalOperator
		(
			('new' | 'this' | 'super' | 'yield' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '<' | 'import' | 'true' | 'false' | 'null' | '/' | '/=' | '[' | '{' | '(' | '@' | 'function' | 'class' | 'delete' | 'void' | 'typeof' | '++' | '--' | '+' | '-' | '~' | '!' | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
			ruleShiftExpression
		)
	)*
;

// Rule RelationalExpression
norm1_RelationalExpression:
	ruleShiftExpression
	(
		(norm1_RelationalOperator
		(
			'new'
			    |
			'this'
			    |
			'super'
			    |
			'yield'
			    |
			'get'
			    |
			'set'
			    |
			'let'
			    |
			'project'
			    |
			'external'
			    |
			'abstract'
			    |
			'static'
			    |
			'as'
			    |
			'from'
			    |
			'constructor'
			    |
			'of'
			    |
			'target'
			    |
			'type'
			    |
			'union'
			    |
			'intersection'
			    |
			'This'
			    |
			'Promisify'
			    |
			'await'
			    |
			'async'
			    |
			'implements'
			    |
			'interface'
			    |
			'private'
			    |
			'protected'
			    |
			'public'
			    |
			'out'
			    |
			'<'
			    |
			'import'
			    |
			'true'
			    |
			'false'
			    |
			'null'
			    |
			'/'
			    |
			'/='
			    |
			'['
			    |
			'{'
			    |
			'('
			    |
			'@'
			    |
			'function'
			    |
			'class'
			    |
			'delete'
			    |
			'void'
			    |
			'typeof'
			    |
			'++'
			    |
			'--'
			    |
			'+'
			    |
			'-'
			    |
			'~'
			    |
			'!'
			    |
			RULE_IDENTIFIER
			    |
			RULE_DOUBLE
			    |
			RULE_INT
			    |
			RULE_BINARY_INT
			    |
			RULE_OCTAL_INT
			    |
			RULE_LEGACY_OCTAL_INT
			    |
			RULE_HEX_INT
			    |
			RULE_SCIENTIFIC_INT
			    |
			RULE_STRING
			    |
			RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
			    |
			RULE_TEMPLATE_HEAD
		)
		)=>
		norm1_RelationalOperator
		(
			('new' | 'this' | 'super' | 'yield' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '<' | 'import' | 'true' | 'false' | 'null' | '/' | '/=' | '[' | '{' | '(' | '@' | 'function' | 'class' | 'delete' | 'void' | 'typeof' | '++' | '--' | '+' | '-' | '~' | '!' | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
			ruleShiftExpression
		)
	)*
;

// Rule RelationalExpression
norm2_RelationalExpression:
	norm1_ShiftExpression
	(
		(ruleRelationalOperator
		(
			'new'
			    |
			'this'
			    |
			'super'
			    |
			'get'
			    |
			'set'
			    |
			'let'
			    |
			'project'
			    |
			'external'
			    |
			'abstract'
			    |
			'static'
			    |
			'as'
			    |
			'from'
			    |
			'constructor'
			    |
			'of'
			    |
			'target'
			    |
			'type'
			    |
			'union'
			    |
			'intersection'
			    |
			'This'
			    |
			'Promisify'
			    |
			'await'
			    |
			'async'
			    |
			'implements'
			    |
			'interface'
			    |
			'private'
			    |
			'protected'
			    |
			'public'
			    |
			'out'
			    |
			'<'
			    |
			'import'
			    |
			'true'
			    |
			'false'
			    |
			'null'
			    |
			'/'
			    |
			'/='
			    |
			'['
			    |
			'{'
			    |
			'('
			    |
			'@'
			    |
			'function'
			    |
			'class'
			    |
			'delete'
			    |
			'void'
			    |
			'typeof'
			    |
			'++'
			    |
			'--'
			    |
			'+'
			    |
			'-'
			    |
			'~'
			    |
			'!'
			    |
			RULE_IDENTIFIER
			    |
			RULE_DOUBLE
			    |
			RULE_INT
			    |
			RULE_BINARY_INT
			    |
			RULE_OCTAL_INT
			    |
			RULE_LEGACY_OCTAL_INT
			    |
			RULE_HEX_INT
			    |
			RULE_SCIENTIFIC_INT
			    |
			RULE_STRING
			    |
			RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
			    |
			RULE_TEMPLATE_HEAD
		)
		)=>
		ruleRelationalOperator
		(
			('new' | 'this' | 'super' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '<' | 'import' | 'true' | 'false' | 'null' | '/' | '/=' | '[' | '{' | '(' | '@' | 'function' | 'class' | 'delete' | 'void' | 'typeof' | '++' | '--' | '+' | '-' | '~' | '!' | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
			norm1_ShiftExpression
		)
	)*
;

// Rule RelationalExpression
norm3_RelationalExpression:
	norm1_ShiftExpression
	(
		(norm1_RelationalOperator
		(
			'new'
			    |
			'this'
			    |
			'super'
			    |
			'get'
			    |
			'set'
			    |
			'let'
			    |
			'project'
			    |
			'external'
			    |
			'abstract'
			    |
			'static'
			    |
			'as'
			    |
			'from'
			    |
			'constructor'
			    |
			'of'
			    |
			'target'
			    |
			'type'
			    |
			'union'
			    |
			'intersection'
			    |
			'This'
			    |
			'Promisify'
			    |
			'await'
			    |
			'async'
			    |
			'implements'
			    |
			'interface'
			    |
			'private'
			    |
			'protected'
			    |
			'public'
			    |
			'out'
			    |
			'<'
			    |
			'import'
			    |
			'true'
			    |
			'false'
			    |
			'null'
			    |
			'/'
			    |
			'/='
			    |
			'['
			    |
			'{'
			    |
			'('
			    |
			'@'
			    |
			'function'
			    |
			'class'
			    |
			'delete'
			    |
			'void'
			    |
			'typeof'
			    |
			'++'
			    |
			'--'
			    |
			'+'
			    |
			'-'
			    |
			'~'
			    |
			'!'
			    |
			RULE_IDENTIFIER
			    |
			RULE_DOUBLE
			    |
			RULE_INT
			    |
			RULE_BINARY_INT
			    |
			RULE_OCTAL_INT
			    |
			RULE_LEGACY_OCTAL_INT
			    |
			RULE_HEX_INT
			    |
			RULE_SCIENTIFIC_INT
			    |
			RULE_STRING
			    |
			RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
			    |
			RULE_TEMPLATE_HEAD
		)
		)=>
		norm1_RelationalOperator
		(
			('new' | 'this' | 'super' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '<' | 'import' | 'true' | 'false' | 'null' | '/' | '/=' | '[' | '{' | '(' | '@' | 'function' | 'class' | 'delete' | 'void' | 'typeof' | '++' | '--' | '+' | '-' | '~' | '!' | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
			norm1_ShiftExpression
		)
	)*
;

// Rule RelationalOperator
ruleRelationalOperator:
	(
		'<'
		    |
		'>'
		'='?
		    |
		'<='
		    |
		'instanceof'
	)
;

// Rule RelationalOperator
norm1_RelationalOperator:
	(
		'<'
		    |
		'>'
		'='?
		    |
		'<='
		    |
		'instanceof'
		    |
		'in'
	)
;

// Rule EqualityExpression
ruleEqualityExpression:
	ruleRelationalExpression
	(
		(
			(ruleEqualityOperator
			)=>
			ruleEqualityOperator
		)
		ruleRelationalExpression
	)*
;

// Rule EqualityExpression
norm1_EqualityExpression:
	norm1_RelationalExpression
	(
		(
			(ruleEqualityOperator
			)=>
			ruleEqualityOperator
		)
		norm1_RelationalExpression
	)*
;

// Rule EqualityExpression
norm2_EqualityExpression:
	norm2_RelationalExpression
	(
		(
			(ruleEqualityOperator
			)=>
			ruleEqualityOperator
		)
		norm2_RelationalExpression
	)*
;

// Rule EqualityExpression
norm3_EqualityExpression:
	norm3_RelationalExpression
	(
		(
			(ruleEqualityOperator
			)=>
			ruleEqualityOperator
		)
		norm3_RelationalExpression
	)*
;

// Rule BitwiseANDExpression
ruleBitwiseANDExpression:
	ruleEqualityExpression
	(
		(
			(ruleBitwiseANDOperator
			)=>
			ruleBitwiseANDOperator
		)
		ruleEqualityExpression
	)*
;

// Rule BitwiseANDExpression
norm1_BitwiseANDExpression:
	norm1_EqualityExpression
	(
		(
			(ruleBitwiseANDOperator
			)=>
			ruleBitwiseANDOperator
		)
		norm1_EqualityExpression
	)*
;

// Rule BitwiseANDExpression
norm2_BitwiseANDExpression:
	norm2_EqualityExpression
	(
		(
			(ruleBitwiseANDOperator
			)=>
			ruleBitwiseANDOperator
		)
		norm2_EqualityExpression
	)*
;

// Rule BitwiseANDExpression
norm3_BitwiseANDExpression:
	norm3_EqualityExpression
	(
		(
			(ruleBitwiseANDOperator
			)=>
			ruleBitwiseANDOperator
		)
		norm3_EqualityExpression
	)*
;

// Rule BitwiseANDOperator
ruleBitwiseANDOperator:
	'&'
;

// Rule BitwiseXORExpression
ruleBitwiseXORExpression:
	ruleBitwiseANDExpression
	(
		(
			(ruleBitwiseXOROperator
			)=>
			ruleBitwiseXOROperator
		)
		ruleBitwiseANDExpression
	)*
;

// Rule BitwiseXORExpression
norm1_BitwiseXORExpression:
	norm1_BitwiseANDExpression
	(
		(
			(ruleBitwiseXOROperator
			)=>
			ruleBitwiseXOROperator
		)
		norm1_BitwiseANDExpression
	)*
;

// Rule BitwiseXORExpression
norm2_BitwiseXORExpression:
	norm2_BitwiseANDExpression
	(
		(
			(ruleBitwiseXOROperator
			)=>
			ruleBitwiseXOROperator
		)
		norm2_BitwiseANDExpression
	)*
;

// Rule BitwiseXORExpression
norm3_BitwiseXORExpression:
	norm3_BitwiseANDExpression
	(
		(
			(ruleBitwiseXOROperator
			)=>
			ruleBitwiseXOROperator
		)
		norm3_BitwiseANDExpression
	)*
;

// Rule BitwiseXOROperator
ruleBitwiseXOROperator:
	'^'
;

// Rule BitwiseORExpression
ruleBitwiseORExpression:
	ruleBitwiseXORExpression
	(
		(
			(ruleBitwiseOROperator
			)=>
			ruleBitwiseOROperator
		)
		ruleBitwiseXORExpression
	)*
;

// Rule BitwiseORExpression
norm1_BitwiseORExpression:
	norm1_BitwiseXORExpression
	(
		(
			(ruleBitwiseOROperator
			)=>
			ruleBitwiseOROperator
		)
		norm1_BitwiseXORExpression
	)*
;

// Rule BitwiseORExpression
norm2_BitwiseORExpression:
	norm2_BitwiseXORExpression
	(
		(
			(ruleBitwiseOROperator
			)=>
			ruleBitwiseOROperator
		)
		norm2_BitwiseXORExpression
	)*
;

// Rule BitwiseORExpression
norm3_BitwiseORExpression:
	norm3_BitwiseXORExpression
	(
		(
			(ruleBitwiseOROperator
			)=>
			ruleBitwiseOROperator
		)
		norm3_BitwiseXORExpression
	)*
;

// Rule BitwiseOROperator
ruleBitwiseOROperator:
	'|'
;

// Rule LogicalANDExpression
ruleLogicalANDExpression:
	ruleBitwiseORExpression
	(
		(
			(ruleLogicalANDOperator
			)=>
			ruleLogicalANDOperator
		)
		ruleBitwiseORExpression
	)*
;

// Rule LogicalANDExpression
norm1_LogicalANDExpression:
	norm1_BitwiseORExpression
	(
		(
			(ruleLogicalANDOperator
			)=>
			ruleLogicalANDOperator
		)
		norm1_BitwiseORExpression
	)*
;

// Rule LogicalANDExpression
norm2_LogicalANDExpression:
	norm2_BitwiseORExpression
	(
		(
			(ruleLogicalANDOperator
			)=>
			ruleLogicalANDOperator
		)
		norm2_BitwiseORExpression
	)*
;

// Rule LogicalANDExpression
norm3_LogicalANDExpression:
	norm3_BitwiseORExpression
	(
		(
			(ruleLogicalANDOperator
			)=>
			ruleLogicalANDOperator
		)
		norm3_BitwiseORExpression
	)*
;

// Rule LogicalANDOperator
ruleLogicalANDOperator:
	'&&'
;

// Rule LogicalORExpression
ruleLogicalORExpression:
	ruleLogicalANDExpression
	(
		(
			(ruleLogicalOROperator
			)=>
			ruleLogicalOROperator
		)
		ruleLogicalANDExpression
	)*
;

// Rule LogicalORExpression
norm1_LogicalORExpression:
	norm1_LogicalANDExpression
	(
		(
			(ruleLogicalOROperator
			)=>
			ruleLogicalOROperator
		)
		norm1_LogicalANDExpression
	)*
;

// Rule LogicalORExpression
norm2_LogicalORExpression:
	norm2_LogicalANDExpression
	(
		(
			(ruleLogicalOROperator
			)=>
			ruleLogicalOROperator
		)
		norm2_LogicalANDExpression
	)*
;

// Rule LogicalORExpression
norm3_LogicalORExpression:
	norm3_LogicalANDExpression
	(
		(
			(ruleLogicalOROperator
			)=>
			ruleLogicalOROperator
		)
		norm3_LogicalANDExpression
	)*
;

// Rule LogicalOROperator
ruleLogicalOROperator:
	'||'
;

// Rule ConditionalExpression
ruleConditionalExpression:
	ruleLogicalORExpression
	(
		(
			('?'
			)=>
			'?'
		)
		norm1_AssignmentExpression
		':'
		ruleAssignmentExpression
	)?
;

// Rule ConditionalExpression
norm1_ConditionalExpression:
	norm1_LogicalORExpression
	(
		(
			('?'
			)=>
			'?'
		)
		norm1_AssignmentExpression
		':'
		norm1_AssignmentExpression
	)?
;

// Rule ConditionalExpression
norm2_ConditionalExpression:
	norm2_LogicalORExpression
	(
		(
			('?'
			)=>
			'?'
		)
		norm3_AssignmentExpression
		':'
		norm2_AssignmentExpression
	)?
;

// Rule ConditionalExpression
norm3_ConditionalExpression:
	norm3_LogicalORExpression
	(
		(
			('?'
			)=>
			'?'
		)
		norm3_AssignmentExpression
		':'
		norm3_AssignmentExpression
	)?
;

// Rule AssignmentExpression
ruleAssignmentExpression:
	(
		(
			('await'
			)=>
			ruleAwaitExpression
		)
		    |
		(
			('@'
			'Promisify'
			)=>
			rulePromisifyExpression
		)
		    |
		(
			((
				ruleStrictFormalParameters
				ruleColonSepReturnTypeRef?
				    |
				(
					('async'
					ruleNoLineTerminator
					'('
					)=>
					'async'
					ruleNoLineTerminator
					(
						('(')=>
						ruleStrictFormalParameters
					)
				)
				ruleColonSepReturnTypeRef?
				    |
				ruleBindingIdentifierAsFormalParameter
			)
			'=>'
			)=>
			ruleArrowExpression
		)
		    |
		ruleConditionalExpression
		(
			(
				(ruleAssignmentOperator
				)=>
				ruleAssignmentOperator
			)
			ruleAssignmentExpression
		)?
	)
;

// Rule AssignmentExpression
norm1_AssignmentExpression:
	(
		(
			('await'
			)=>
			norm1_AwaitExpression
		)
		    |
		(
			('@'
			'Promisify'
			)=>
			norm1_PromisifyExpression
		)
		    |
		(
			((
				ruleStrictFormalParameters
				ruleColonSepReturnTypeRef?
				    |
				(
					('async'
					ruleNoLineTerminator
					'('
					)=>
					'async'
					ruleNoLineTerminator
					(
						('(')=>
						ruleStrictFormalParameters
					)
				)
				ruleColonSepReturnTypeRef?
				    |
				ruleBindingIdentifierAsFormalParameter
			)
			'=>'
			)=>
			norm1_ArrowExpression
		)
		    |
		norm1_ConditionalExpression
		(
			(
				(ruleAssignmentOperator
				)=>
				ruleAssignmentOperator
			)
			norm1_AssignmentExpression
		)?
	)
;

// Rule AssignmentExpression
norm2_AssignmentExpression:
	(
		(
			('await'
			)=>
			norm2_AwaitExpression
		)
		    |
		(
			('@'
			'Promisify'
			)=>
			norm2_PromisifyExpression
		)
		    |
		(
			((
				norm1_StrictFormalParameters
				ruleColonSepReturnTypeRef?
				    |
				(
					('async'
					ruleNoLineTerminator
					'('
					)=>
					'async'
					ruleNoLineTerminator
					(
						('(')=>
						norm1_StrictFormalParameters
					)
				)
				ruleColonSepReturnTypeRef?
				    |
				norm1_BindingIdentifierAsFormalParameter
			)
			'=>'
			)=>
			norm2_ArrowExpression
		)
		    |
		ruleYieldExpression
		    |
		norm2_ConditionalExpression
		(
			(
				(ruleAssignmentOperator
				)=>
				ruleAssignmentOperator
			)
			norm2_AssignmentExpression
		)?
	)
;

// Rule AssignmentExpression
norm3_AssignmentExpression:
	(
		(
			('await'
			)=>
			norm3_AwaitExpression
		)
		    |
		(
			('@'
			'Promisify'
			)=>
			norm3_PromisifyExpression
		)
		    |
		(
			((
				norm1_StrictFormalParameters
				ruleColonSepReturnTypeRef?
				    |
				(
					('async'
					ruleNoLineTerminator
					'('
					)=>
					'async'
					ruleNoLineTerminator
					(
						('(')=>
						norm1_StrictFormalParameters
					)
				)
				ruleColonSepReturnTypeRef?
				    |
				norm1_BindingIdentifierAsFormalParameter
			)
			'=>'
			)=>
			norm3_ArrowExpression
		)
		    |
		norm1_YieldExpression
		    |
		norm3_ConditionalExpression
		(
			(
				(ruleAssignmentOperator
				)=>
				ruleAssignmentOperator
			)
			norm3_AssignmentExpression
		)?
	)
;

// Rule YieldExpression
ruleYieldExpression:
	'yield'
	(
		('*'
		)=>
		'*'
	)?
	(
		('await' | '@' | '(' | 'async' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | 'yield' | 'new' | 'this' | 'super' | '<' | 'import' | 'true' | 'false' | 'null' | '/' | '/=' | '[' | '{' | 'function' | 'class' | 'delete' | 'void' | 'typeof' | '++' | '--' | '+' | '-' | '~' | '!' | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
		norm2_AssignmentExpression
	)?
;

// Rule YieldExpression
norm1_YieldExpression:
	'yield'
	(
		('*'
		)=>
		'*'
	)?
	(
		('await' | '@' | '(' | 'async' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | 'yield' | 'new' | 'this' | 'super' | '<' | 'import' | 'true' | 'false' | 'null' | '/' | '/=' | '[' | '{' | 'function' | 'class' | 'delete' | 'void' | 'typeof' | '++' | '--' | '+' | '-' | '~' | '!' | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
		norm3_AssignmentExpression
	)?
;

// Rule AssignmentOperator
ruleAssignmentOperator:
	(
		'='
		    |
		'*='
		    |
		'/='
		    |
		'%='
		    |
		'+='
		    |
		'-'
		'='
		    |
		'<<='
		    |
		'>'
		'>'
		'>'?
		'='
		    |
		'&='
		    |
		'^='
		    |
		'|='
	)
;

// Rule AwaitExpression
ruleAwaitExpression:
	(
		('await'
		)=>
		'await'
	)
	ruleAssignmentExpression
;

// Rule AwaitExpression
norm1_AwaitExpression:
	(
		('await'
		)=>
		'await'
	)
	norm1_AssignmentExpression
;

// Rule AwaitExpression
norm2_AwaitExpression:
	(
		('await'
		)=>
		'await'
	)
	norm2_AssignmentExpression
;

// Rule AwaitExpression
norm3_AwaitExpression:
	(
		('await'
		)=>
		'await'
	)
	norm3_AssignmentExpression
;

// Rule PromisifyExpression
rulePromisifyExpression:
	(
		('@'
		'Promisify'
		)=>
		'@'
		'Promisify'
	)
	ruleAssignmentExpression
;

// Rule PromisifyExpression
norm1_PromisifyExpression:
	(
		('@'
		'Promisify'
		)=>
		'@'
		'Promisify'
	)
	norm1_AssignmentExpression
;

// Rule PromisifyExpression
norm2_PromisifyExpression:
	(
		('@'
		'Promisify'
		)=>
		'@'
		'Promisify'
	)
	norm2_AssignmentExpression
;

// Rule PromisifyExpression
norm3_PromisifyExpression:
	(
		('@'
		'Promisify'
		)=>
		'@'
		'Promisify'
	)
	norm3_AssignmentExpression
;

// Rule Expression
ruleExpression:
	ruleAssignmentExpression
	(
		','
		ruleAssignmentExpression
		(
			','
			ruleAssignmentExpression
		)*
	)?
;

// Rule Expression
norm1_Expression:
	norm1_AssignmentExpression
	(
		','
		norm1_AssignmentExpression
		(
			','
			norm1_AssignmentExpression
		)*
	)?
;

// Rule Expression
norm2_Expression:
	norm2_AssignmentExpression
	(
		','
		norm2_AssignmentExpression
		(
			','
			norm2_AssignmentExpression
		)*
	)?
;

// Rule Expression
norm3_Expression:
	norm3_AssignmentExpression
	(
		','
		norm3_AssignmentExpression
		(
			','
			norm3_AssignmentExpression
		)*
	)?
;

// Rule TemplateLiteral
ruleTemplateLiteral:
	(
		ruleNoSubstitutionTemplate
		    |
		ruleTemplateHead
		norm1_Expression
		?
		ruleTemplateExpressionEnd
		(
			ruleTemplateMiddle
			norm1_Expression
			?
			ruleTemplateExpressionEnd
		)*
		ruleTemplateTail
	)
;

// Rule TemplateLiteral
norm1_TemplateLiteral:
	(
		ruleNoSubstitutionTemplate
		    |
		ruleTemplateHead
		norm3_Expression
		?
		ruleTemplateExpressionEnd
		(
			ruleTemplateMiddle
			norm3_Expression
			?
			ruleTemplateExpressionEnd
		)*
		ruleTemplateTail
	)
;

// Rule TemplateExpressionEnd
ruleTemplateExpressionEnd:
	'}'
;

// Rule NoSubstitutionTemplate
ruleNoSubstitutionTemplate:
	RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
;

// Rule TemplateHead
ruleTemplateHead:
	RULE_TEMPLATE_HEAD
;

// Rule TemplateTail
ruleTemplateTail:
	ruleTemplateTailLiteral
;

// Rule TemplateMiddle
ruleTemplateMiddle:
	ruleTemplateMiddleLiteral
;

// Rule Literal
ruleLiteral:
	(
		ruleNumericLiteral
		    |
		ruleBooleanLiteral
		    |
		ruleStringLiteral
		    |
		ruleNullLiteral
		    |
		ruleRegularExpressionLiteral
	)
;

// Rule NullLiteral
ruleNullLiteral:
	'null'
;

// Rule BooleanLiteral
ruleBooleanLiteral:
	(
		'true'
		    |
		'false'
	)
;

// Rule StringLiteral
ruleStringLiteral:
	RULE_STRING
;

// Rule NumericLiteral
ruleNumericLiteral:
	(
		ruleDoubleLiteral
		    |
		ruleIntLiteral
		    |
		ruleBinaryIntLiteral
		    |
		ruleOctalIntLiteral
		    |
		ruleLegacyOctalIntLiteral
		    |
		ruleHexIntLiteral
		    |
		ruleScientificIntLiteral
	)
;

// Rule DoubleLiteral
ruleDoubleLiteral:
	RULE_DOUBLE
;

// Rule IntLiteral
ruleIntLiteral:
	RULE_INT
;

// Rule OctalIntLiteral
ruleOctalIntLiteral:
	RULE_OCTAL_INT
;

// Rule LegacyOctalIntLiteral
ruleLegacyOctalIntLiteral:
	RULE_LEGACY_OCTAL_INT
;

// Rule HexIntLiteral
ruleHexIntLiteral:
	RULE_HEX_INT
;

// Rule BinaryIntLiteral
ruleBinaryIntLiteral:
	RULE_BINARY_INT
;

// Rule ScientificIntLiteral
ruleScientificIntLiteral:
	RULE_SCIENTIFIC_INT
;

// Rule RegularExpressionLiteral
ruleRegularExpressionLiteral:
	ruleREGEX_LITERAL
;

// Rule NumericLiteralAsString
ruleNumericLiteralAsString:
	(
		RULE_DOUBLE
		    |
		RULE_INT
		    |
		RULE_OCTAL_INT
		    |
		RULE_HEX_INT
		    |
		RULE_SCIENTIFIC_INT
	)
;

// Rule IdentifierOrThis
ruleIdentifierOrThis:
	(
		RULE_IDENTIFIER
		    |
		'This'
		    |
		'Promisify'
		    |
		'target'
	)
;

// Rule AnnotationName
ruleAnnotationName:
	(
		RULE_IDENTIFIER
		    |
		'This'
		    |
		'target'
	)
;

// Rule REGEX_LITERAL
ruleREGEX_LITERAL:
	(
		'/'
		    |
		'/='
	)
	RULE_REGEX_TAIL?
;

// Rule TemplateTailLiteral
ruleTemplateTailLiteral:
	RULE_TEMPLATE_END?
;

// Rule TemplateMiddleLiteral
ruleTemplateMiddleLiteral:
	RULE_TEMPLATE_MIDDLE
;

// Rule Semi
ruleSemi:
	';'
;

// Rule NoLineTerminator
ruleNoLineTerminator:
	RULE_NO_LINE_TERMINATOR?
;

// Rule Annotation
ruleAnnotation:
	'@'
	ruleAnnotationNoAtSign
;

// Rule ScriptAnnotation
ruleScriptAnnotation:
	'@@'
	ruleAnnotationNoAtSign
;

// Rule AnnotationNoAtSign
ruleAnnotationNoAtSign:
	ruleAnnotationName
	(
		(
			('(')=>
			'('
		)
		(
			ruleAnnotationArgument
			(
				','
				ruleAnnotationArgument
			)*
		)?
		')'
	)?
;

// Rule AnnotationArgument
ruleAnnotationArgument:
	(
		ruleLiteralAnnotationArgument
		    |
		ruleTypeRefAnnotationArgument
	)
;

// Rule LiteralAnnotationArgument
ruleLiteralAnnotationArgument:
	ruleLiteral
;

// Rule TypeRefAnnotationArgument
ruleTypeRefAnnotationArgument:
	ruleTypeRef
;

// Rule AnnotationList
ruleAnnotationList:
	(
		('@'
		(
			'This'
			    |
			'target'
			    |
			RULE_IDENTIFIER
		)
		)=>
		'@'
		(
			('This' | 'target' | RULE_IDENTIFIER)=>
			ruleAnnotationNoAtSign
		)
	)
	ruleAnnotation
	*
;

// Rule ExpressionAnnotationList
ruleExpressionAnnotationList:
	ruleAnnotation
	+
;

// Rule PropertyAssignmentAnnotationList
rulePropertyAssignmentAnnotationList:
	ruleAnnotation
	+
;

// Rule N4MemberAnnotationList
ruleN4MemberAnnotationList:
	ruleAnnotation
	+
;

// Rule TypeReference
ruleTypeReference:
	(
		ruleTypeReferenceName
		'.'
	)?
	ruleTypeReferenceName
;

// Rule TypeReferenceName
ruleTypeReferenceName:
	(
		'void'
		    |
		'This'
		    |
		'await'
		    |
		'Promisify'
		    |
		'target'
		    |
		'default'
		    |
		RULE_IDENTIFIER
	)
;

// Rule N4ClassDeclaration
ruleN4ClassDeclaration:
	(
		(ruleN4Modifier
		*
		'class'
		ruleTypingStrategyDefSiteOperator
		?
		ruleBindingIdentifier
		?
		ruleVersionDeclaration?
		)=>
		ruleN4Modifier
		*
		'class'
		ruleTypingStrategyDefSiteOperator
		?
		ruleBindingIdentifier
		?
		ruleVersionDeclaration?
	)
	ruleTypeVariables?
	ruleClassExtendsImplements?
	ruleMembers
;

// Rule Members
ruleMembers:
	'{'
	ruleN4MemberDeclaration
	*
	'}'
;

// Rule Members
norm1_Members:
	'{'
	norm1_N4MemberDeclaration
	*
	'}'
;

// Rule ClassExtendsImplements
ruleClassExtendsImplements:
	(
		ruleClassExtendsClause
		ruleClassImplementsList?
		    |
		ruleClassImplementsList
		ruleClassExtendsClause?
	)
;

// Rule ClassExtendsImplements
norm1_ClassExtendsImplements:
	(
		norm1_ClassExtendsClause
		ruleClassImplementsList?
		    |
		ruleClassImplementsList
		norm1_ClassExtendsClause?
	)
;

// Rule ClassExtendsClause
ruleClassExtendsClause:
	'extends'
	(
		(
			(ruleParameterizedTypeRefNominal
			)=>
			ruleParameterizedTypeRefNominal
		)
		    |
		ruleLeftHandSideExpression
	)
;

// Rule ClassExtendsClause
norm1_ClassExtendsClause:
	'extends'
	(
		(
			(ruleParameterizedTypeRefNominal
			)=>
			ruleParameterizedTypeRefNominal
		)
		    |
		norm1_LeftHandSideExpression
	)
;

// Rule ClassImplementsList
ruleClassImplementsList:
	'implements'
	ruleParameterizedTypeRefNominal
	(
		','
		ruleParameterizedTypeRefNominal
	)*
;

// Rule N4ClassExpression
ruleN4ClassExpression:
	'class'
	ruleBindingIdentifier
	?
	ruleClassExtendsImplements?
	ruleMembers
;

// Rule N4ClassExpression
norm1_N4ClassExpression:
	'class'
	norm1_BindingIdentifier
	?
	norm1_ClassExtendsImplements?
	norm1_Members
;

// Rule N4InterfaceDeclaration
ruleN4InterfaceDeclaration:
	(
		(ruleN4Modifier
		*
		'interface'
		ruleTypingStrategyDefSiteOperator
		?
		ruleBindingIdentifier
		?
		ruleVersionDeclaration?
		)=>
		ruleN4Modifier
		*
		'interface'
		ruleTypingStrategyDefSiteOperator
		?
		ruleBindingIdentifier
		?
		ruleVersionDeclaration?
	)
	ruleTypeVariables?
	ruleInterfaceExtendsList?
	ruleMembers
;

// Rule InterfaceExtendsList
ruleInterfaceExtendsList:
	(
		'extends'
		    |
		'implements'
	)
	ruleParameterizedTypeRefNominal
	(
		','
		ruleParameterizedTypeRefNominal
	)*
;

// Rule N4EnumDeclaration
ruleN4EnumDeclaration:
	(
		(ruleN4Modifier
		*
		'enum'
		ruleBindingIdentifier
		?
		ruleVersionDeclaration?
		)=>
		ruleN4Modifier
		*
		'enum'
		ruleBindingIdentifier
		?
		ruleVersionDeclaration?
	)
	'{'
	(
		ruleN4EnumLiteral
		(
			','
			ruleN4EnumLiteral
		)*
	)?
	'}'
;

// Rule N4EnumLiteral
ruleN4EnumLiteral:
	ruleIdentifierName
	(
		':'
		RULE_STRING
	)?
;

// Rule N4MemberDeclaration
ruleN4MemberDeclaration:
	(
		ruleAnnotatedN4MemberDeclaration
		    |
		(
			(ruleN4Modifier
			*
			ruleGetterHeader
			)=>
			ruleN4GetterDeclaration
		)
		    |
		(
			(ruleN4Modifier
			*
			'set'
			(
				'break'
				    |
				'case'
				    |
				'catch'
				    |
				'class'
				    |
				'const'
				    |
				'continue'
				    |
				'debugger'
				    |
				'default'
				    |
				'delete'
				    |
				'do'
				    |
				'else'
				    |
				'export'
				    |
				'extends'
				    |
				'finally'
				    |
				'for'
				    |
				'function'
				    |
				'if'
				    |
				'import'
				    |
				'in'
				    |
				'instanceof'
				    |
				'new'
				    |
				'return'
				    |
				'super'
				    |
				'switch'
				    |
				'this'
				    |
				'throw'
				    |
				'try'
				    |
				'typeof'
				    |
				'var'
				    |
				'void'
				    |
				'while'
				    |
				'with'
				    |
				'yield'
				    |
				'null'
				    |
				'true'
				    |
				'false'
				    |
				'enum'
				    |
				'get'
				    |
				'set'
				    |
				'let'
				    |
				'project'
				    |
				'external'
				    |
				'abstract'
				    |
				'static'
				    |
				'as'
				    |
				'from'
				    |
				'constructor'
				    |
				'of'
				    |
				'target'
				    |
				'type'
				    |
				'union'
				    |
				'intersection'
				    |
				'This'
				    |
				'Promisify'
				    |
				'await'
				    |
				'async'
				    |
				'implements'
				    |
				'interface'
				    |
				'private'
				    |
				'protected'
				    |
				'public'
				    |
				'out'
				    |
				'['
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
			)=>
			ruleN4SetterDeclaration
		)
		    |
		(
			(ruleN4Modifier
			*
			ruleTypeVariables?
			ruleBogusTypeRefFragment?
			(
				'*'
				ruleLiteralOrComputedPropertyName
				'('
				    |
				ruleAsyncNoTrailingLineBreak
				ruleLiteralOrComputedPropertyName
				'('
			)
			)=>
			ruleN4MethodDeclaration
		)
		    |
		ruleN4FieldDeclaration
		    |
		ruleN4CallableConstructorDeclaration
	)
;

// Rule N4MemberDeclaration
norm1_N4MemberDeclaration:
	(
		norm1_AnnotatedN4MemberDeclaration
		    |
		(
			(ruleN4Modifier
			*
			norm1_GetterHeader
			)=>
			norm1_N4GetterDeclaration
		)
		    |
		(
			(ruleN4Modifier
			*
			'set'
			(
				'break'
				    |
				'case'
				    |
				'catch'
				    |
				'class'
				    |
				'const'
				    |
				'continue'
				    |
				'debugger'
				    |
				'default'
				    |
				'delete'
				    |
				'do'
				    |
				'else'
				    |
				'export'
				    |
				'extends'
				    |
				'finally'
				    |
				'for'
				    |
				'function'
				    |
				'if'
				    |
				'import'
				    |
				'in'
				    |
				'instanceof'
				    |
				'new'
				    |
				'return'
				    |
				'super'
				    |
				'switch'
				    |
				'this'
				    |
				'throw'
				    |
				'try'
				    |
				'typeof'
				    |
				'var'
				    |
				'void'
				    |
				'while'
				    |
				'with'
				    |
				'yield'
				    |
				'null'
				    |
				'true'
				    |
				'false'
				    |
				'enum'
				    |
				'get'
				    |
				'set'
				    |
				'let'
				    |
				'project'
				    |
				'external'
				    |
				'abstract'
				    |
				'static'
				    |
				'as'
				    |
				'from'
				    |
				'constructor'
				    |
				'of'
				    |
				'target'
				    |
				'type'
				    |
				'union'
				    |
				'intersection'
				    |
				'This'
				    |
				'Promisify'
				    |
				'await'
				    |
				'async'
				    |
				'implements'
				    |
				'interface'
				    |
				'private'
				    |
				'protected'
				    |
				'public'
				    |
				'out'
				    |
				'['
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
			)=>
			norm1_N4SetterDeclaration
		)
		    |
		(
			(ruleN4Modifier
			*
			ruleTypeVariables?
			ruleBogusTypeRefFragment?
			(
				'*'
				norm1_LiteralOrComputedPropertyName
				'('
				    |
				ruleAsyncNoTrailingLineBreak
				norm1_LiteralOrComputedPropertyName
				'('
			)
			)=>
			norm1_N4MethodDeclaration
		)
		    |
		norm1_N4FieldDeclaration
		    |
		norm1_N4CallableConstructorDeclaration
	)
;

// Rule AnnotatedN4MemberDeclaration
ruleAnnotatedN4MemberDeclaration:
	ruleN4MemberAnnotationList
	(
		(
			(ruleN4Modifier
			*
			ruleGetterHeader
			)=>
			ruleN4Modifier
			*
			ruleGetterHeader
		)
		(
			('{'
			)=>
			ruleBlock
		)?
		';'?
		    |
		(
			(ruleN4Modifier
			*
			'set'
			(
				'break'
				    |
				'case'
				    |
				'catch'
				    |
				'class'
				    |
				'const'
				    |
				'continue'
				    |
				'debugger'
				    |
				'default'
				    |
				'delete'
				    |
				'do'
				    |
				'else'
				    |
				'export'
				    |
				'extends'
				    |
				'finally'
				    |
				'for'
				    |
				'function'
				    |
				'if'
				    |
				'import'
				    |
				'in'
				    |
				'instanceof'
				    |
				'new'
				    |
				'return'
				    |
				'super'
				    |
				'switch'
				    |
				'this'
				    |
				'throw'
				    |
				'try'
				    |
				'typeof'
				    |
				'var'
				    |
				'void'
				    |
				'while'
				    |
				'with'
				    |
				'yield'
				    |
				'null'
				    |
				'true'
				    |
				'false'
				    |
				'enum'
				    |
				'get'
				    |
				'set'
				    |
				'let'
				    |
				'project'
				    |
				'external'
				    |
				'abstract'
				    |
				'static'
				    |
				'as'
				    |
				'from'
				    |
				'constructor'
				    |
				'of'
				    |
				'target'
				    |
				'type'
				    |
				'union'
				    |
				'intersection'
				    |
				'This'
				    |
				'Promisify'
				    |
				'await'
				    |
				'async'
				    |
				'implements'
				    |
				'interface'
				    |
				'private'
				    |
				'protected'
				    |
				'public'
				    |
				'out'
				    |
				'['
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
			)=>
			ruleN4Modifier
			*
			'set'
			(
				('break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' | 'null' | 'true' | 'false' | 'enum' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '[' | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
				ruleLiteralOrComputedPropertyName
			)
		)
		'?'
		?
		'('
		ruleFormalParameter
		')'
		(
			('{'
			)=>
			ruleBlock
		)?
		';'?
		    |
		(
			(ruleN4Modifier
			*
			ruleTypeVariables?
			ruleBogusTypeRefFragment?
			(
				'*'
				ruleLiteralOrComputedPropertyName
				'('
				    |
				ruleAsyncNoTrailingLineBreak
				ruleLiteralOrComputedPropertyName
				'('
			)
			)=>
			ruleN4Modifier
			*
			ruleTypeVariables?
			ruleBogusTypeRefFragment?
			(
				'*'
				ruleLiteralOrComputedPropertyName
				(
					('(')=>
					norm1_MethodParamsReturnAndBody
				)
				    |
				ruleAsyncNoTrailingLineBreak
				ruleLiteralOrComputedPropertyName
				(
					('(')=>
					ruleMethodParamsReturnAndBody
				)
			)
		)
		';'?
		    |
		ruleFieldDeclarationImpl
	)
;

// Rule AnnotatedN4MemberDeclaration
norm1_AnnotatedN4MemberDeclaration:
	ruleN4MemberAnnotationList
	(
		(
			(ruleN4Modifier
			*
			norm1_GetterHeader
			)=>
			ruleN4Modifier
			*
			norm1_GetterHeader
		)
		(
			('{'
			)=>
			norm1_Block
		)?
		';'?
		    |
		(
			(ruleN4Modifier
			*
			'set'
			(
				'break'
				    |
				'case'
				    |
				'catch'
				    |
				'class'
				    |
				'const'
				    |
				'continue'
				    |
				'debugger'
				    |
				'default'
				    |
				'delete'
				    |
				'do'
				    |
				'else'
				    |
				'export'
				    |
				'extends'
				    |
				'finally'
				    |
				'for'
				    |
				'function'
				    |
				'if'
				    |
				'import'
				    |
				'in'
				    |
				'instanceof'
				    |
				'new'
				    |
				'return'
				    |
				'super'
				    |
				'switch'
				    |
				'this'
				    |
				'throw'
				    |
				'try'
				    |
				'typeof'
				    |
				'var'
				    |
				'void'
				    |
				'while'
				    |
				'with'
				    |
				'yield'
				    |
				'null'
				    |
				'true'
				    |
				'false'
				    |
				'enum'
				    |
				'get'
				    |
				'set'
				    |
				'let'
				    |
				'project'
				    |
				'external'
				    |
				'abstract'
				    |
				'static'
				    |
				'as'
				    |
				'from'
				    |
				'constructor'
				    |
				'of'
				    |
				'target'
				    |
				'type'
				    |
				'union'
				    |
				'intersection'
				    |
				'This'
				    |
				'Promisify'
				    |
				'await'
				    |
				'async'
				    |
				'implements'
				    |
				'interface'
				    |
				'private'
				    |
				'protected'
				    |
				'public'
				    |
				'out'
				    |
				'['
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
			)=>
			ruleN4Modifier
			*
			'set'
			(
				('break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' | 'null' | 'true' | 'false' | 'enum' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '[' | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
				norm1_LiteralOrComputedPropertyName
			)
		)
		'?'
		?
		'('
		norm1_FormalParameter
		')'
		(
			('{'
			)=>
			norm1_Block
		)?
		';'?
		    |
		(
			(ruleN4Modifier
			*
			ruleTypeVariables?
			ruleBogusTypeRefFragment?
			(
				'*'
				norm1_LiteralOrComputedPropertyName
				'('
				    |
				ruleAsyncNoTrailingLineBreak
				norm1_LiteralOrComputedPropertyName
				'('
			)
			)=>
			ruleN4Modifier
			*
			ruleTypeVariables?
			ruleBogusTypeRefFragment?
			(
				'*'
				norm1_LiteralOrComputedPropertyName
				(
					('(')=>
					norm1_MethodParamsReturnAndBody
				)
				    |
				ruleAsyncNoTrailingLineBreak
				norm1_LiteralOrComputedPropertyName
				(
					('(')=>
					ruleMethodParamsReturnAndBody
				)
			)
		)
		';'?
		    |
		norm1_FieldDeclarationImpl
	)
;

// Rule FieldDeclarationImpl
ruleFieldDeclarationImpl:
	ruleN4Modifier
	*
	ruleBogusTypeRefFragment?
	ruleLiteralOrComputedPropertyName
	'?'
	?
	ruleColonSepDeclaredTypeRef?
	(
		'='
		norm1_Expression
	)?
	ruleSemi
;

// Rule FieldDeclarationImpl
norm1_FieldDeclarationImpl:
	ruleN4Modifier
	*
	ruleBogusTypeRefFragment?
	norm1_LiteralOrComputedPropertyName
	'?'
	?
	ruleColonSepDeclaredTypeRef?
	(
		'='
		norm3_Expression
	)?
	ruleSemi
;

// Rule N4FieldDeclaration
ruleN4FieldDeclaration:
	ruleFieldDeclarationImpl
;

// Rule N4FieldDeclaration
norm1_N4FieldDeclaration:
	norm1_FieldDeclarationImpl
;

// Rule N4MethodDeclaration
ruleN4MethodDeclaration:
	(
		(ruleN4Modifier
		*
		ruleTypeVariables?
		ruleBogusTypeRefFragment?
		(
			'*'
			ruleLiteralOrComputedPropertyName
			'('
			    |
			ruleAsyncNoTrailingLineBreak
			ruleLiteralOrComputedPropertyName
			'('
		)
		)=>
		ruleN4Modifier
		*
		ruleTypeVariables?
		ruleBogusTypeRefFragment?
		(
			'*'
			ruleLiteralOrComputedPropertyName
			(
				('(')=>
				norm1_MethodParamsReturnAndBody
			)
			    |
			ruleAsyncNoTrailingLineBreak
			ruleLiteralOrComputedPropertyName
			(
				('(')=>
				ruleMethodParamsReturnAndBody
			)
		)
	)
	';'?
;

// Rule N4MethodDeclaration
norm1_N4MethodDeclaration:
	(
		(ruleN4Modifier
		*
		ruleTypeVariables?
		ruleBogusTypeRefFragment?
		(
			'*'
			norm1_LiteralOrComputedPropertyName
			'('
			    |
			ruleAsyncNoTrailingLineBreak
			norm1_LiteralOrComputedPropertyName
			'('
		)
		)=>
		ruleN4Modifier
		*
		ruleTypeVariables?
		ruleBogusTypeRefFragment?
		(
			'*'
			norm1_LiteralOrComputedPropertyName
			(
				('(')=>
				norm1_MethodParamsReturnAndBody
			)
			    |
			ruleAsyncNoTrailingLineBreak
			norm1_LiteralOrComputedPropertyName
			(
				('(')=>
				ruleMethodParamsReturnAndBody
			)
		)
	)
	';'?
;

// Rule N4CallableConstructorDeclaration
ruleN4CallableConstructorDeclaration:
	ruleMethodParamsReturnAndBody
	';'?
;

// Rule N4CallableConstructorDeclaration
norm1_N4CallableConstructorDeclaration:
	ruleMethodParamsReturnAndBody
	';'?
;

// Rule MethodParamsAndBody
ruleMethodParamsAndBody:
	ruleStrictFormalParameters
	(
		('{'
		)=>
		ruleBlock
	)?
;

// Rule MethodParamsAndBody
norm1_MethodParamsAndBody:
	norm1_StrictFormalParameters
	(
		('{'
		)=>
		norm1_Block
	)?
;

// Rule MethodParamsReturnAndBody
ruleMethodParamsReturnAndBody:
	ruleStrictFormalParameters
	ruleColonSepReturnTypeRef?
	(
		('{'
		)=>
		ruleBlock
	)?
;

// Rule MethodParamsReturnAndBody
norm1_MethodParamsReturnAndBody:
	norm1_StrictFormalParameters
	ruleColonSepReturnTypeRef?
	(
		('{'
		)=>
		norm1_Block
	)?
;

// Rule N4GetterDeclaration
ruleN4GetterDeclaration:
	(
		(ruleN4Modifier
		*
		ruleGetterHeader
		)=>
		ruleN4Modifier
		*
		ruleGetterHeader
	)
	(
		('{'
		)=>
		ruleBlock
	)?
	';'?
;

// Rule N4GetterDeclaration
norm1_N4GetterDeclaration:
	(
		(ruleN4Modifier
		*
		norm1_GetterHeader
		)=>
		ruleN4Modifier
		*
		norm1_GetterHeader
	)
	(
		('{'
		)=>
		norm1_Block
	)?
	';'?
;

// Rule GetterHeader
ruleGetterHeader:
	ruleBogusTypeRefFragment?
	'get'
	(
		('break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' | 'null' | 'true' | 'false' | 'enum' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '[' | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
		ruleLiteralOrComputedPropertyName
	)
	'?'
	?
	'('
	')'
	ruleColonSepDeclaredTypeRef?
;

// Rule GetterHeader
norm1_GetterHeader:
	ruleBogusTypeRefFragment?
	'get'
	(
		('break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' | 'null' | 'true' | 'false' | 'enum' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '[' | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
		norm1_LiteralOrComputedPropertyName
	)
	'?'
	?
	'('
	')'
	ruleColonSepDeclaredTypeRef?
;

// Rule N4SetterDeclaration
ruleN4SetterDeclaration:
	(
		(ruleN4Modifier
		*
		'set'
		(
			'break'
			    |
			'case'
			    |
			'catch'
			    |
			'class'
			    |
			'const'
			    |
			'continue'
			    |
			'debugger'
			    |
			'default'
			    |
			'delete'
			    |
			'do'
			    |
			'else'
			    |
			'export'
			    |
			'extends'
			    |
			'finally'
			    |
			'for'
			    |
			'function'
			    |
			'if'
			    |
			'import'
			    |
			'in'
			    |
			'instanceof'
			    |
			'new'
			    |
			'return'
			    |
			'super'
			    |
			'switch'
			    |
			'this'
			    |
			'throw'
			    |
			'try'
			    |
			'typeof'
			    |
			'var'
			    |
			'void'
			    |
			'while'
			    |
			'with'
			    |
			'yield'
			    |
			'null'
			    |
			'true'
			    |
			'false'
			    |
			'enum'
			    |
			'get'
			    |
			'set'
			    |
			'let'
			    |
			'project'
			    |
			'external'
			    |
			'abstract'
			    |
			'static'
			    |
			'as'
			    |
			'from'
			    |
			'constructor'
			    |
			'of'
			    |
			'target'
			    |
			'type'
			    |
			'union'
			    |
			'intersection'
			    |
			'This'
			    |
			'Promisify'
			    |
			'await'
			    |
			'async'
			    |
			'implements'
			    |
			'interface'
			    |
			'private'
			    |
			'protected'
			    |
			'public'
			    |
			'out'
			    |
			'['
			    |
			RULE_IDENTIFIER
			    |
			RULE_STRING
			    |
			RULE_DOUBLE
			    |
			RULE_INT
			    |
			RULE_OCTAL_INT
			    |
			RULE_HEX_INT
			    |
			RULE_SCIENTIFIC_INT
		)
		)=>
		ruleN4Modifier
		*
		'set'
		(
			('break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' | 'null' | 'true' | 'false' | 'enum' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '[' | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
			ruleLiteralOrComputedPropertyName
		)
	)
	'?'
	?
	'('
	ruleFormalParameter
	')'
	(
		('{'
		)=>
		ruleBlock
	)?
	';'?
;

// Rule N4SetterDeclaration
norm1_N4SetterDeclaration:
	(
		(ruleN4Modifier
		*
		'set'
		(
			'break'
			    |
			'case'
			    |
			'catch'
			    |
			'class'
			    |
			'const'
			    |
			'continue'
			    |
			'debugger'
			    |
			'default'
			    |
			'delete'
			    |
			'do'
			    |
			'else'
			    |
			'export'
			    |
			'extends'
			    |
			'finally'
			    |
			'for'
			    |
			'function'
			    |
			'if'
			    |
			'import'
			    |
			'in'
			    |
			'instanceof'
			    |
			'new'
			    |
			'return'
			    |
			'super'
			    |
			'switch'
			    |
			'this'
			    |
			'throw'
			    |
			'try'
			    |
			'typeof'
			    |
			'var'
			    |
			'void'
			    |
			'while'
			    |
			'with'
			    |
			'yield'
			    |
			'null'
			    |
			'true'
			    |
			'false'
			    |
			'enum'
			    |
			'get'
			    |
			'set'
			    |
			'let'
			    |
			'project'
			    |
			'external'
			    |
			'abstract'
			    |
			'static'
			    |
			'as'
			    |
			'from'
			    |
			'constructor'
			    |
			'of'
			    |
			'target'
			    |
			'type'
			    |
			'union'
			    |
			'intersection'
			    |
			'This'
			    |
			'Promisify'
			    |
			'await'
			    |
			'async'
			    |
			'implements'
			    |
			'interface'
			    |
			'private'
			    |
			'protected'
			    |
			'public'
			    |
			'out'
			    |
			'['
			    |
			RULE_IDENTIFIER
			    |
			RULE_STRING
			    |
			RULE_DOUBLE
			    |
			RULE_INT
			    |
			RULE_OCTAL_INT
			    |
			RULE_HEX_INT
			    |
			RULE_SCIENTIFIC_INT
		)
		)=>
		ruleN4Modifier
		*
		'set'
		(
			('break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' | 'null' | 'true' | 'false' | 'enum' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | '[' | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
			norm1_LiteralOrComputedPropertyName
		)
	)
	'?'
	?
	'('
	norm1_FormalParameter
	')'
	(
		('{'
		)=>
		norm1_Block
	)?
	';'?
;

// Rule BindingPattern
ruleBindingPattern:
	(
		ruleObjectBindingPattern
		    |
		ruleArrayBindingPattern
	)
;

// Rule BindingPattern
norm1_BindingPattern:
	(
		norm1_ObjectBindingPattern
		    |
		norm1_ArrayBindingPattern
	)
;

// Rule ObjectBindingPattern
ruleObjectBindingPattern:
	'{'
	(
		ruleBindingProperty
		(
			','
			ruleBindingProperty
		)*
	)?
	'}'
;

// Rule ObjectBindingPattern
norm1_ObjectBindingPattern:
	'{'
	(
		norm1_BindingProperty
		(
			','
			norm1_BindingProperty
		)*
	)?
	'}'
;

// Rule ArrayBindingPattern
ruleArrayBindingPattern:
	'['
	ruleElision
	*
	(
		ruleBindingRestElement
		(
			','
			ruleElision
			*
			ruleBindingRestElement
		)*
		(
			','
			ruleElision
			*
		)?
	)?
	']'
;

// Rule ArrayBindingPattern
norm1_ArrayBindingPattern:
	'['
	ruleElision
	*
	(
		norm1_BindingRestElement
		(
			','
			ruleElision
			*
			norm1_BindingRestElement
		)*
		(
			','
			ruleElision
			*
		)?
	)?
	']'
;

// Rule BindingProperty
ruleBindingProperty:
	(
		(
			(ruleLiteralOrComputedPropertyName
			':'
			)=>
			ruleLiteralOrComputedPropertyName
			':'
		)
		ruleBindingElement
		    |
		ruleSingleNameBinding
	)
;

// Rule BindingProperty
norm1_BindingProperty:
	(
		(
			(norm1_LiteralOrComputedPropertyName
			':'
			)=>
			norm1_LiteralOrComputedPropertyName
			':'
		)
		norm1_BindingElement
		    |
		norm1_SingleNameBinding
	)
;

// Rule SingleNameBinding
ruleSingleNameBinding:
	norm1_VariableDeclaration
;

// Rule SingleNameBinding
norm1_SingleNameBinding:
	norm3_VariableDeclaration
;

// Rule SingleNameBinding
norm2_SingleNameBinding:
	norm5_VariableDeclaration
;

// Rule SingleNameBinding
norm3_SingleNameBinding:
	norm7_VariableDeclaration
;

// Rule BindingElement
ruleBindingElement:
	ruleBindingElementImpl
;

// Rule BindingElement
norm1_BindingElement:
	norm1_BindingElementImpl
;

// Rule BindingRestElement
ruleBindingRestElement:
	'...'
	?
	ruleBindingElementImpl
;

// Rule BindingRestElement
norm1_BindingRestElement:
	'...'
	?
	norm1_BindingElementImpl
;

// Rule BindingElementImpl
ruleBindingElementImpl:
	(
		(
			(ruleBindingPattern
			)=>
			ruleBindingPattern
		)
		(
			'='
			norm1_AssignmentExpression
		)?
		    |
		norm5_VariableDeclaration
	)
;

// Rule BindingElementImpl
norm1_BindingElementImpl:
	(
		(
			(norm1_BindingPattern
			)=>
			norm1_BindingPattern
		)
		(
			'='
			norm3_AssignmentExpression
		)?
		    |
		norm7_VariableDeclaration
	)
;

// Rule Elision
ruleElision:
	','
;

// Rule LiteralOrComputedPropertyName
ruleLiteralOrComputedPropertyName:
	(
		ruleIdentifierName
		    |
		RULE_STRING
		    |
		ruleNumericLiteralAsString
		    |
		'['
		norm1_AssignmentExpression
		']'
	)
;

// Rule LiteralOrComputedPropertyName
norm1_LiteralOrComputedPropertyName:
	(
		ruleIdentifierName
		    |
		RULE_STRING
		    |
		ruleNumericLiteralAsString
		    |
		'['
		norm3_AssignmentExpression
		']'
	)
;

// Rule JSXElement
ruleJSXElement:
	'<'
	ruleJSXElementName
	ruleJSXAttribute
	*
	(
		'>'
		ruleJSXChild
		*
		'<'
		'/'
		ruleJSXElementName
		'>'
		    |
		'/'
		'>'
	)
;

// Rule JSXFragment
ruleJSXFragment:
	'<'
	'>'
	ruleJSXChild
	*
	'<'
	'/'
	'>'
;

// Rule JSXChild
ruleJSXChild:
	(
		ruleJSXElement
		    |
		ruleJSXFragment
		    |
		ruleJSXExpression
	)
;

// Rule JSXExpression
ruleJSXExpression:
	'{'
	ruleAssignmentExpression
	'}'
;

// Rule JSXElementName
ruleJSXElementName:
	ruleJSXElementNameExpression
;

// Rule JSXElementNameExpression
ruleJSXElementNameExpression:
	ruleIdentifierRef
	(
		ruleParameterizedPropertyAccessExpressionTail
	)*
;

// Rule JSXAttribute
ruleJSXAttribute:
	(
		ruleJSXSpreadAttribute
		    |
		ruleJSXPropertyAttribute
	)
;

// Rule JSXSpreadAttribute
ruleJSXSpreadAttribute:
	'{'
	'...'
	ruleAssignmentExpression
	'}'
;

// Rule JSXIdentifier
ruleJSXIdentifier:
	ruleIdentifierName
	(
		(
			'-'
			    |
			'--'
		)
		(
			('break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' | 'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' | 'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' | 'null' | 'true' | 'false' | 'enum' | 'get' | 'set' | 'let' | 'project' | 'external' | 'abstract' | 'static' | 'as' | 'from' | 'constructor' | 'of' | 'target' | 'type' | 'union' | 'intersection' | 'This' | 'Promisify' | 'await' | 'async' | 'implements' | 'interface' | 'private' | 'protected' | 'public' | 'out' | RULE_INT | RULE_HEX_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_SCIENTIFIC_INT | RULE_LEGACY_OCTAL_INT | RULE_IDENTIFIER)=>
			(
				RULE_INT
				    |
				RULE_HEX_INT
				    |
				RULE_BINARY_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_SCIENTIFIC_INT
				    |
				RULE_LEGACY_OCTAL_INT
				    |
				ruleIdentifierName
			)
		)?
	)*
;

// Rule JSXPropertyAttribute
ruleJSXPropertyAttribute:
	ruleJSXIdentifier
	(
		'='
		(
			ruleStringLiteral
			    |
			ruleJSXElement
			    |
			ruleJSXFragment
			    |
			'{'
			ruleAssignmentExpression
			'}'
		)
	)?
;

// Rule VersionDeclaration
ruleVersionDeclaration:
	RULE_VERSION
;

// Rule TypeRef
ruleTypeRef:
	ruleIntersectionTypeExpression
	(
		(
			'|'
			ruleIntersectionTypeExpression
		)+
	)?
;

// Rule IntersectionTypeExpression
ruleIntersectionTypeExpression:
	ruleArrayTypeExpression
	(
		(
			'&'
			ruleArrayTypeExpression
		)+
	)?
;

// Rule ArrayTypeExpression
ruleArrayTypeExpression:
	(
		ruleWildcardOldNotationWithoutBound
		'['
		']'
		(
			('['
			']'
			)=>
			'['
			']'
		)*
		    |
		'('
		ruleWildcard
		')'
		'['
		']'
		(
			('['
			']'
			)=>
			'['
			']'
		)*
		    |
		rulePrimaryTypeExpression
		(
			('['
			']'
			)=>
			'['
			']'
		)*
	)
;

// Rule PrimaryTypeExpression
rulePrimaryTypeExpression:
	(
		(
			('('
			ruleTAnonymousFormalParameterList
			')'
			'=>'
			)=>
			ruleArrowFunctionTypeExpression
		)
		    |
		ruleIterableTypeExpression
		    |
		ruleTypeRefWithModifiers
		    |
		'('
		ruleTypeRef
		')'
	)
;

// Rule TypeRefWithModifiers
ruleTypeRefWithModifiers:
	ruleTypeRefWithoutModifiers
	(
		('?'
		)=>
		'?'
	)?
;

// Rule TypeRefWithoutModifiers
ruleTypeRefWithoutModifiers:
	(
		(
			ruleParameterizedTypeRef
			    |
			ruleThisTypeRef
		)
		(
			('+'
			)=>
			'+'
		)?
		    |
		ruleTypeTypeRef
		    |
		ruleFunctionTypeExpressionOLD
		    |
		ruleUnionTypeExpressionOLD
		    |
		ruleIntersectionTypeExpressionOLD
	)
;

// Rule TypeRefFunctionTypeExpression
ruleTypeRefFunctionTypeExpression:
	(
		ruleParameterizedTypeRef
		    |
		ruleIterableTypeExpression
		    |
		ruleTypeTypeRef
		    |
		ruleUnionTypeExpressionOLD
		    |
		ruleIntersectionTypeExpressionOLD
	)
;

// Rule TypeArgInTypeTypeRef
ruleTypeArgInTypeTypeRef:
	(
		ruleParameterizedTypeRefNominal
		    |
		ruleThisTypeRefNominal
		    |
		(
			('?'
			)=>
			ruleWildcardOldNotation
		)
	)
;

// Rule ThisTypeRef
ruleThisTypeRef:
	(
		ruleThisTypeRefNominal
		    |
		ruleThisTypeRefStructural
	)
;

// Rule ThisTypeRefNominal
ruleThisTypeRefNominal:
	'this'
;

// Rule ThisTypeRefStructural
ruleThisTypeRefStructural:
	ruleTypingStrategyUseSiteOperator
	'this'
	(
		'with'
		ruleTStructMemberList
	)?
;

// Rule FunctionTypeExpressionOLD
ruleFunctionTypeExpressionOLD:
	'{'
	(
		'@'
		'This'
		'('
		ruleTypeRefFunctionTypeExpression
		')'
	)?
	'function'
	(
		'<'
		ruleTypeVariable
		(
			','
			ruleTypeVariable
		)*
		'>'
	)?
	'('
	ruleTAnonymousFormalParameterList
	')'
	ruleColonSepReturnTypeRef?
	'}'
;

// Rule ArrowFunctionTypeExpression
ruleArrowFunctionTypeExpression:
	(
		('('
		ruleTAnonymousFormalParameterList
		')'
		'=>'
		)=>
		'('
		ruleTAnonymousFormalParameterList
		')'
		'=>'
	)
	rulePrimaryTypeExpression
;

// Rule TAnonymousFormalParameterList
ruleTAnonymousFormalParameterList:
	(
		ruleTAnonymousFormalParameter
		(
			','
			ruleTAnonymousFormalParameter
		)*
	)?
;

// Rule TAnonymousFormalParameter
ruleTAnonymousFormalParameter:
	'...'
	?
	(
		(
			(ruleBindingIdentifier
			':'
			)=>
			ruleBindingIdentifier
			(
				(':')=>
				ruleColonSepTypeRef
			)
		)
		    |
		ruleTypeRef
	)
	ruleDefaultFormalParameter
;

// Rule DefaultFormalParameter
ruleDefaultFormalParameter:
	(
		'='
		ruleTypeReferenceName
		?
	)?
;

// Rule UnionTypeExpressionOLD
ruleUnionTypeExpressionOLD:
	'union'
	'{'
	ruleTypeRef
	(
		','
		ruleTypeRef
	)*
	'}'
;

// Rule IntersectionTypeExpressionOLD
ruleIntersectionTypeExpressionOLD:
	'intersection'
	'{'
	ruleTypeRef
	(
		','
		ruleTypeRef
	)*
	'}'
;

// Rule ParameterizedTypeRef
ruleParameterizedTypeRef:
	(
		ruleParameterizedTypeRefNominal
		    |
		ruleParameterizedTypeRefStructural
	)
;

// Rule ParameterizedTypeRefNominal
ruleParameterizedTypeRefNominal:
	(
		ruleTypeReference
		    |
		ruleTypeReference
		ruleVersionRequest
	)
	(
		('<')=>
		ruleTypeArguments
	)?
;

// Rule ParameterizedTypeRefStructural
ruleParameterizedTypeRefStructural:
	(
		ruleTypingStrategyUseSiteOperator
		ruleTypeReference
		    |
		ruleTypingStrategyUseSiteOperator
		ruleTypeReference
		ruleVersionRequest
	)
	(
		('<')=>
		ruleTypeArguments
	)?
	(
		'with'
		ruleTStructMemberList
	)?
;

// Rule IterableTypeExpression
ruleIterableTypeExpression:
	'['
	(
		ruleEmptyIterableTypeExpressionTail
		    |
		ruleTypeArgument
		(
			','
			ruleTypeArgument
		)*
		']'
	)
;

// Rule EmptyIterableTypeExpressionTail
ruleEmptyIterableTypeExpressionTail:
	']'
;

// Rule VersionRequest
ruleVersionRequest:
	RULE_VERSION
;

// Rule TypeArguments
ruleTypeArguments:
	'<'
	ruleTypeArgument
	(
		','
		ruleTypeArgument
	)*
	'>'
;

// Rule TStructMemberList
ruleTStructMemberList:
	'{'
	(
		ruleTStructMember
		(
			';'
			    |
			','
		)?
	)*
	'}'
;

// Rule TStructMember
ruleTStructMember:
	(
		(
			('get'
			ruleIdentifierName
			)=>
			ruleTStructGetter
		)
		    |
		(
			('set'
			ruleIdentifierName
			)=>
			ruleTStructSetter
		)
		    |
		(
			(ruleTypeVariables?
			ruleIdentifierName
			'('
			)=>
			ruleTStructMethod
		)
		    |
		ruleTStructField
	)
;

// Rule TStructMethod
ruleTStructMethod:
	(
		(ruleTypeVariables?
		ruleIdentifierName
		'('
		)=>
		ruleTypeVariables?
		ruleIdentifierName
		'('
	)
	ruleTAnonymousFormalParameterList
	')'
	ruleColonSepReturnTypeRef?
;

// Rule TypeVariables
ruleTypeVariables:
	'<'
	ruleTypeVariable
	(
		','
		ruleTypeVariable
	)*
	'>'
;

// Rule ColonSepDeclaredTypeRef
ruleColonSepDeclaredTypeRef:
	':'
	ruleTypeRef
;

// Rule ColonSepTypeRef
ruleColonSepTypeRef:
	':'
	ruleTypeRef
;

// Rule ColonSepReturnTypeRef
ruleColonSepReturnTypeRef:
	':'
	ruleTypeRef
;

// Rule TStructField
ruleTStructField:
	ruleIdentifierName
	'?'
	?
	ruleColonSepTypeRef?
;

// Rule TStructGetter
ruleTStructGetter:
	(
		('get'
		ruleIdentifierName
		)=>
		'get'
		ruleIdentifierName
	)
	'?'
	?
	'('
	')'
	ruleColonSepDeclaredTypeRef?
;

// Rule TStructSetter
ruleTStructSetter:
	(
		('set'
		ruleIdentifierName
		)=>
		'set'
		ruleIdentifierName
	)
	'?'
	?
	'('
	ruleTAnonymousFormalParameter
	')'
;

// Rule TypingStrategyUseSiteOperator
ruleTypingStrategyUseSiteOperator:
	'~'
	(
		'~'
		    |
		RULE_STRUCTMODSUFFIX
	)?
;

// Rule TypingStrategyDefSiteOperator
ruleTypingStrategyDefSiteOperator:
	'~'
;

// Rule TypeTypeRef
ruleTypeTypeRef:
	(
		'type'
		    |
		'constructor'
	)
	'{'
	ruleTypeArgInTypeTypeRef
	'}'
;

// Rule TypeArgument
ruleTypeArgument:
	(
		ruleWildcard
		    |
		ruleTypeRef
	)
;

// Rule Wildcard
ruleWildcard:
	(
		(
			('?'
			)=>
			ruleWildcardOldNotation
		)
		    |
		ruleWildcardNewNotation
	)
;

// Rule WildcardOldNotation
ruleWildcardOldNotation:
	(
		('?'
		)=>
		'?'
	)
	(
		'extends'
		ruleTypeRef
		    |
		'super'
		ruleTypeRef
	)?
;

// Rule WildcardOldNotationWithoutBound
ruleWildcardOldNotationWithoutBound:
	'?'
;

// Rule WildcardNewNotation
ruleWildcardNewNotation:
	(
		'out'
		ruleTypeRef
		    |
		'in'
		ruleTypeRef
	)
;

// Rule BindingIdentifier
ruleBindingIdentifier:
	(
		RULE_IDENTIFIER
		    |
		'yield'
		    |
		ruleN4Keyword
	)
;

// Rule BindingIdentifier
norm1_BindingIdentifier:
	(
		RULE_IDENTIFIER
		    |
		ruleN4Keyword
	)
;

// Rule IdentifierName
ruleIdentifierName:
	(
		RULE_IDENTIFIER
		    |
		ruleReservedWord
		    |
		ruleN4Keyword
	)
;

// Rule ReservedWord
ruleReservedWord:
	(
		'break'
		    |
		'case'
		    |
		'catch'
		    |
		'class'
		    |
		'const'
		    |
		'continue'
		    |
		'debugger'
		    |
		'default'
		    |
		'delete'
		    |
		'do'
		    |
		'else'
		    |
		'export'
		    |
		'extends'
		    |
		'finally'
		    |
		'for'
		    |
		'function'
		    |
		'if'
		    |
		'import'
		    |
		'in'
		    |
		'instanceof'
		    |
		'new'
		    |
		'return'
		    |
		'super'
		    |
		'switch'
		    |
		'this'
		    |
		'throw'
		    |
		'try'
		    |
		'typeof'
		    |
		'var'
		    |
		'void'
		    |
		'while'
		    |
		'with'
		    |
		'yield'
		    |
		'null'
		    |
		'true'
		    |
		'false'
		    |
		'enum'
	)
;

// Rule N4Keyword
ruleN4Keyword:
	(
		'get'
		    |
		'set'
		    |
		'let'
		    |
		'project'
		    |
		'external'
		    |
		'abstract'
		    |
		'static'
		    |
		'as'
		    |
		'from'
		    |
		'constructor'
		    |
		'of'
		    |
		'target'
		    |
		'type'
		    |
		'union'
		    |
		'intersection'
		    |
		'This'
		    |
		'Promisify'
		    |
		'await'
		    |
		'async'
		    |
		'implements'
		    |
		'interface'
		    |
		'private'
		    |
		'protected'
		    |
		'public'
		    |
		'out'
	)
;

// Rule VariableStatementKeyword
ruleVariableStatementKeyword:
	(
		'var'
		    |
		'const'
		    |
		'let'
	)
;

// Rule PostfixOperator
rulePostfixOperator:
	(
		'++'
		    |
		'--'
	)
;

// Rule UnaryOperator
ruleUnaryOperator:
	(
		'delete'
		    |
		'void'
		    |
		'typeof'
		    |
		'++'
		    |
		'--'
		    |
		'+'
		    |
		'-'
		    |
		'~'
		    |
		'!'
	)
;

// Rule MultiplicativeOperator
ruleMultiplicativeOperator:
	(
		'*'
		    |
		'/'
		    |
		'%'
	)
;

// Rule AdditiveOperator
ruleAdditiveOperator:
	(
		'+'
		    |
		'-'
	)
;

// Rule EqualityOperator
ruleEqualityOperator:
	(
		'==='
		    |
		'!=='
		    |
		'=='
		    |
		'!='
	)
;

// Rule N4Modifier
ruleN4Modifier:
	(
		'private'
		    |
		'project'
		    |
		'protected'
		    |
		'public'
		    |
		'external'
		    |
		'abstract'
		    |
		'static'
		    |
		'const'
	)
;

RULE_DOUBLE : ('.' RULE_DECIMAL_DIGIT_FRAGMENT+ RULE_EXPONENT_PART?|RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT '.' RULE_DECIMAL_DIGIT_FRAGMENT* RULE_EXPONENT_PART?);

RULE_HEX_INT : '0' ('x'|'X') RULE_INT_SUFFIX;

RULE_BINARY_INT : '0' ('b'|'B') RULE_INT_SUFFIX;

RULE_OCTAL_INT : '0' ('o'|'O') RULE_INT_SUFFIX;

RULE_LEGACY_OCTAL_INT : '0' RULE_DECIMAL_DIGIT_FRAGMENT RULE_INT_SUFFIX;

fragment RULE_INT_SUFFIX : RULE_IDENTIFIER_PART*;

RULE_SCIENTIFIC_INT : RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT RULE_EXPONENT_PART;

fragment RULE_EXPONENT_PART : (('e'|'E') RULE_SIGNED_INT|RULE_IDENTIFIER);

fragment RULE_SIGNED_INT : ('+'|'-') RULE_DECIMAL_DIGIT_FRAGMENT+ RULE_IDENTIFIER?;

RULE_STRING : ('"' RULE_DOUBLE_STRING_CHAR* '"'?|'\'' RULE_SINGLE_STRING_CHAR* '\''?);

fragment RULE_DOUBLE_STRING_CHAR : (~((RULE_LINE_TERMINATOR_FRAGMENT|'"'|'\\'))|'\\' (RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT|~(RULE_LINE_TERMINATOR_FRAGMENT))?);

fragment RULE_SINGLE_STRING_CHAR : (~((RULE_LINE_TERMINATOR_FRAGMENT|'\''|'\\'))|'\\' (RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT|~(RULE_LINE_TERMINATOR_FRAGMENT))?);

fragment RULE_BACKSLASH_SEQUENCE : '\\' ~(RULE_LINE_TERMINATOR_FRAGMENT)?;

fragment RULE_REGEX_CHAR : (~((RULE_LINE_TERMINATOR_FRAGMENT|'\\'|'/'|'['))|RULE_BACKSLASH_SEQUENCE|'[' RULE_REGEX_CHAR_OR_BRACKET* ']'?);

fragment RULE_REGEX_CHAR_OR_BRACKET : (~((RULE_LINE_TERMINATOR_FRAGMENT|'\\'|']'))|RULE_BACKSLASH_SEQUENCE);

fragment RULE_ACTUAL_REGEX_TAIL : (RULE_REGEX_CHAR+ ('/' RULE_IDENTIFIER_PART*)?|'/' RULE_IDENTIFIER_PART*);

fragment RULE_REGEX_START : ('/'|'/=');

RULE_REGEX_TAIL : '//1';

RULE_TEMPLATE_HEAD : '`' RULE_TEMPLATE_LITERAL_CHAR* '$'+ '{';

RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL : '`' RULE_TEMPLATE_LITERAL_CHAR* '$'* '`'?;

fragment RULE_ACTUAL_TEMPLATE_END : RULE_TEMPLATE_LITERAL_CHAR* ('$'+ ('{'|'`'?)|'`'?);

fragment RULE_TEMPLATE_LITERAL_CHAR : (~((RULE_LINE_TERMINATOR_FRAGMENT|'`'|'\\'|'$'))|'$'+ ~(('{'|'`'|'$'))|RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT|'\\' (RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT|~(RULE_LINE_TERMINATOR_FRAGMENT))?);

RULE_TEMPLATE_MIDDLE : '//2';

RULE_TEMPLATE_END : '//3';

fragment RULE_TEMPLATE_CONTINUATION : '//4';

RULE_NO_LINE_TERMINATOR : '//5';

RULE_INCOMPLETE_ASYNC_ARROW : '@=';

RULE_STRUCTMODSUFFIX : ('r'|'i'|'w'|'\u2205') '~';

RULE_IDENTIFIER : RULE_IDENTIFIER_START RULE_IDENTIFIER_PART*;

RULE_INT : RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT;

RULE_ML_COMMENT : RULE_ML_COMMENT_FRAGMENT {skip();};

RULE_SL_COMMENT : '//' ~(RULE_LINE_TERMINATOR_FRAGMENT)* {skip();};

RULE_EOL : RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT {skip();};

RULE_WS : RULE_WHITESPACE_FRAGMENT+ {skip();};

fragment RULE_UNICODE_ESCAPE_FRAGMENT : '\\' ('u' (RULE_HEX_DIGIT (RULE_HEX_DIGIT (RULE_HEX_DIGIT RULE_HEX_DIGIT?)?)?|'{' RULE_HEX_DIGIT* '}'?)?)?;

fragment RULE_IDENTIFIER_START : (RULE_UNICODE_LETTER_FRAGMENT|'$'|'_'|RULE_UNICODE_ESCAPE_FRAGMENT);

fragment RULE_IDENTIFIER_PART : (RULE_UNICODE_LETTER_FRAGMENT|RULE_UNICODE_ESCAPE_FRAGMENT|'$'|RULE_UNICODE_COMBINING_MARK_FRAGMENT|RULE_UNICODE_DIGIT_FRAGMENT|RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT|RULE_ZWNJ|RULE_ZWJ);

RULE_DOT_DOT : '..';

RULE_VERSION : '#' RULE_WS* RULE_INT;

fragment RULE_HEX_DIGIT : (RULE_DECIMAL_DIGIT_FRAGMENT|'a'..'f'|'A'..'F');

fragment RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT : ('0'|'1'..'9' RULE_DECIMAL_DIGIT_FRAGMENT*);

fragment RULE_DECIMAL_DIGIT_FRAGMENT : '0'..'9';

fragment RULE_ZWJ : '\u200D';

fragment RULE_ZWNJ : '\u200C';

fragment RULE_BOM : '\uFEFF';

fragment RULE_WHITESPACE_FRAGMENT : ('\t'|'\u000B'|'\f'|' '|'\u00A0'|RULE_BOM|RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT);

fragment RULE_LINE_TERMINATOR_FRAGMENT : ('\n'|'\r'|'\u2028'|'\u2029');

fragment RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT : ('\n'|'\r' '\n'?|'\u2028'|'\u2029');

fragment RULE_SL_COMMENT_FRAGMENT : '//' ~(RULE_LINE_TERMINATOR_FRAGMENT)*;

fragment RULE_ML_COMMENT_FRAGMENT : '/*' ( options {greedy=false;} : . )*'*/';

fragment RULE_UNICODE_COMBINING_MARK_FRAGMENT : ('\u0300'..'\u036F'|'\u0483'..'\u0487'|'\u0591'..'\u05BD'|'\u05BF'|'\u05C1'..'\u05C2'|'\u05C4'..'\u05C5'|'\u05C7'|'\u0610'..'\u061A'|'\u064B'..'\u065F'|'\u0670'|'\u06D6'..'\u06DC'|'\u06DF'..'\u06E4'|'\u06E7'..'\u06E8'|'\u06EA'..'\u06ED'|'\u0711'|'\u0730'..'\u074A'|'\u07A6'..'\u07B0'|'\u07EB'..'\u07F3'|'\u0816'..'\u0819'|'\u081B'..'\u0823'|'\u0825'..'\u0827'|'\u0829'..'\u082D'|'\u0859'..'\u085B'|'\u08E3'..'\u0903'|'\u093A'..'\u093C'|'\u093E'..'\u094F'|'\u0951'..'\u0957'|'\u0962'..'\u0963'|'\u0981'..'\u0983'|'\u09BC'|'\u09BE'..'\u09C4'|'\u09C7'..'\u09C8'|'\u09CB'..'\u09CD'|'\u09D7'|'\u09E2'..'\u09E3'|'\u0A01'..'\u0A03'|'\u0A3C'|'\u0A3E'..'\u0A42'|'\u0A47'..'\u0A48'|'\u0A4B'..'\u0A4D'|'\u0A51'|'\u0A70'..'\u0A71'|'\u0A75'|'\u0A81'..'\u0A83'|'\u0ABC'|'\u0ABE'..'\u0AC5'|'\u0AC7'..'\u0AC9'|'\u0ACB'..'\u0ACD'|'\u0AE2'..'\u0AE3'|'\u0B01'..'\u0B03'|'\u0B3C'|'\u0B3E'..'\u0B44'|'\u0B47'..'\u0B48'|'\u0B4B'..'\u0B4D'|'\u0B56'..'\u0B57'|'\u0B62'..'\u0B63'|'\u0B82'|'\u0BBE'..'\u0BC2'|'\u0BC6'..'\u0BC8'|'\u0BCA'..'\u0BCD'|'\u0BD7'|'\u0C00'..'\u0C03'|'\u0C3E'..'\u0C44'|'\u0C46'..'\u0C48'|'\u0C4A'..'\u0C4D'|'\u0C55'..'\u0C56'|'\u0C62'..'\u0C63'|'\u0C81'..'\u0C83'|'\u0CBC'|'\u0CBE'..'\u0CC4'|'\u0CC6'..'\u0CC8'|'\u0CCA'..'\u0CCD'|'\u0CD5'..'\u0CD6'|'\u0CE2'..'\u0CE3'|'\u0D01'..'\u0D03'|'\u0D3E'..'\u0D44'|'\u0D46'..'\u0D48'|'\u0D4A'..'\u0D4D'|'\u0D57'|'\u0D62'..'\u0D63'|'\u0D82'..'\u0D83'|'\u0DCA'|'\u0DCF'..'\u0DD4'|'\u0DD6'|'\u0DD8'..'\u0DDF'|'\u0DF2'..'\u0DF3'|'\u0E31'|'\u0E34'..'\u0E3A'|'\u0E47'..'\u0E4E'|'\u0EB1'|'\u0EB4'..'\u0EB9'|'\u0EBB'..'\u0EBC'|'\u0EC8'..'\u0ECD'|'\u0F18'..'\u0F19'|'\u0F35'|'\u0F37'|'\u0F39'|'\u0F3E'..'\u0F3F'|'\u0F71'..'\u0F84'|'\u0F86'..'\u0F87'|'\u0F8D'..'\u0F97'|'\u0F99'..'\u0FBC'|'\u0FC6'|'\u102B'..'\u103E'|'\u1056'..'\u1059'|'\u105E'..'\u1060'|'\u1062'..'\u1064'|'\u1067'..'\u106D'|'\u1071'..'\u1074'|'\u1082'..'\u108D'|'\u108F'|'\u109A'..'\u109D'|'\u135D'..'\u135F'|'\u1712'..'\u1714'|'\u1732'..'\u1734'|'\u1752'..'\u1753'|'\u1772'..'\u1773'|'\u17B4'..'\u17D3'|'\u17DD'|'\u180B'..'\u180D'|'\u18A9'|'\u1920'..'\u192B'|'\u1930'..'\u193B'|'\u1A17'..'\u1A1B'|'\u1A55'..'\u1A5E'|'\u1A60'..'\u1A7C'|'\u1A7F'|'\u1AB0'..'\u1ABD'|'\u1B00'..'\u1B04'|'\u1B34'..'\u1B44'|'\u1B6B'..'\u1B73'|'\u1B80'..'\u1B82'|'\u1BA1'..'\u1BAD'|'\u1BE6'..'\u1BF3'|'\u1C24'..'\u1C37'|'\u1CD0'..'\u1CD2'|'\u1CD4'..'\u1CE8'|'\u1CED'|'\u1CF2'..'\u1CF4'|'\u1CF8'..'\u1CF9'|'\u1DC0'..'\u1DF5'|'\u1DFC'..'\u1DFF'|'\u20D0'..'\u20DC'|'\u20E1'|'\u20E5'..'\u20F0'|'\u2CEF'..'\u2CF1'|'\u2D7F'|'\u2DE0'..'\u2DFF'|'\u302A'..'\u302F'|'\u3099'..'\u309A'|'\uA66F'|'\uA674'..'\uA67D'|'\uA69E'..'\uA69F'|'\uA6F0'..'\uA6F1'|'\uA802'|'\uA806'|'\uA80B'|'\uA823'..'\uA827'|'\uA880'..'\uA881'|'\uA8B4'..'\uA8C4'|'\uA8E0'..'\uA8F1'|'\uA926'..'\uA92D'|'\uA947'..'\uA953'|'\uA980'..'\uA983'|'\uA9B3'..'\uA9C0'|'\uA9E5'|'\uAA29'..'\uAA36'|'\uAA43'|'\uAA4C'..'\uAA4D'|'\uAA7B'..'\uAA7D'|'\uAAB0'|'\uAAB2'..'\uAAB4'|'\uAAB7'..'\uAAB8'|'\uAABE'..'\uAABF'|'\uAAC1'|'\uAAEB'..'\uAAEF'|'\uAAF5'..'\uAAF6'|'\uABE3'..'\uABEA'|'\uABEC'..'\uABED'|'\uFB1E'|'\uFE00'..'\uFE0F'|'\uFE20'..'\uFE2F');

fragment RULE_UNICODE_DIGIT_FRAGMENT : ('0'..'9'|'\u0660'..'\u0669'|'\u06F0'..'\u06F9'|'\u07C0'..'\u07C9'|'\u0966'..'\u096F'|'\u09E6'..'\u09EF'|'\u0A66'..'\u0A6F'|'\u0AE6'..'\u0AEF'|'\u0B66'..'\u0B6F'|'\u0BE6'..'\u0BEF'|'\u0C66'..'\u0C6F'|'\u0CE6'..'\u0CEF'|'\u0D66'..'\u0D6F'|'\u0DE6'..'\u0DEF'|'\u0E50'..'\u0E59'|'\u0ED0'..'\u0ED9'|'\u0F20'..'\u0F29'|'\u1040'..'\u1049'|'\u1090'..'\u1099'|'\u17E0'..'\u17E9'|'\u1810'..'\u1819'|'\u1946'..'\u194F'|'\u19D0'..'\u19D9'|'\u1A80'..'\u1A89'|'\u1A90'..'\u1A99'|'\u1B50'..'\u1B59'|'\u1BB0'..'\u1BB9'|'\u1C40'..'\u1C49'|'\u1C50'..'\u1C59'|'\uA620'..'\uA629'|'\uA8D0'..'\uA8D9'|'\uA900'..'\uA909'|'\uA9D0'..'\uA9D9'|'\uA9F0'..'\uA9F9'|'\uAA50'..'\uAA59'|'\uABF0'..'\uABF9'|'\uFF10'..'\uFF19');

fragment RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT : ('_'|'\u203F'..'\u2040'|'\u2054'|'\uFE33'..'\uFE34'|'\uFE4D'..'\uFE4F'|'\uFF3F');

fragment RULE_UNICODE_LETTER_FRAGMENT : ('A'..'Z'|'a'..'z'|'\u00AA'|'\u00B5'|'\u00BA'|'\u00C0'..'\u00D6'|'\u00D8'..'\u00F6'|'\u00F8'..'\u02C1'|'\u02C6'..'\u02D1'|'\u02E0'..'\u02E4'|'\u02EC'|'\u02EE'|'\u0370'..'\u0374'|'\u0376'..'\u0377'|'\u037A'..'\u037D'|'\u037F'|'\u0386'|'\u0388'..'\u038A'|'\u038C'|'\u038E'..'\u03A1'|'\u03A3'..'\u03F5'|'\u03F7'..'\u0481'|'\u048A'..'\u052F'|'\u0531'..'\u0556'|'\u0559'|'\u0561'..'\u0587'|'\u05D0'..'\u05EA'|'\u05F0'..'\u05F2'|'\u0620'..'\u064A'|'\u066E'..'\u066F'|'\u0671'..'\u06D3'|'\u06D5'|'\u06E5'..'\u06E6'|'\u06EE'..'\u06EF'|'\u06FA'..'\u06FC'|'\u06FF'|'\u0710'|'\u0712'..'\u072F'|'\u074D'..'\u07A5'|'\u07B1'|'\u07CA'..'\u07EA'|'\u07F4'..'\u07F5'|'\u07FA'|'\u0800'..'\u0815'|'\u081A'|'\u0824'|'\u0828'|'\u0840'..'\u0858'|'\u08A0'..'\u08B4'|'\u0904'..'\u0939'|'\u093D'|'\u0950'|'\u0958'..'\u0961'|'\u0971'..'\u0980'|'\u0985'..'\u098C'|'\u098F'..'\u0990'|'\u0993'..'\u09A8'|'\u09AA'..'\u09B0'|'\u09B2'|'\u09B6'..'\u09B9'|'\u09BD'|'\u09CE'|'\u09DC'..'\u09DD'|'\u09DF'..'\u09E1'|'\u09F0'..'\u09F1'|'\u0A05'..'\u0A0A'|'\u0A0F'..'\u0A10'|'\u0A13'..'\u0A28'|'\u0A2A'..'\u0A30'|'\u0A32'..'\u0A33'|'\u0A35'..'\u0A36'|'\u0A38'..'\u0A39'|'\u0A59'..'\u0A5C'|'\u0A5E'|'\u0A72'..'\u0A74'|'\u0A85'..'\u0A8D'|'\u0A8F'..'\u0A91'|'\u0A93'..'\u0AA8'|'\u0AAA'..'\u0AB0'|'\u0AB2'..'\u0AB3'|'\u0AB5'..'\u0AB9'|'\u0ABD'|'\u0AD0'|'\u0AE0'..'\u0AE1'|'\u0AF9'|'\u0B05'..'\u0B0C'|'\u0B0F'..'\u0B10'|'\u0B13'..'\u0B28'|'\u0B2A'..'\u0B30'|'\u0B32'..'\u0B33'|'\u0B35'..'\u0B39'|'\u0B3D'|'\u0B5C'..'\u0B5D'|'\u0B5F'..'\u0B61'|'\u0B71'|'\u0B83'|'\u0B85'..'\u0B8A'|'\u0B8E'..'\u0B90'|'\u0B92'..'\u0B95'|'\u0B99'..'\u0B9A'|'\u0B9C'|'\u0B9E'..'\u0B9F'|'\u0BA3'..'\u0BA4'|'\u0BA8'..'\u0BAA'|'\u0BAE'..'\u0BB9'|'\u0BD0'|'\u0C05'..'\u0C0C'|'\u0C0E'..'\u0C10'|'\u0C12'..'\u0C28'|'\u0C2A'..'\u0C39'|'\u0C3D'|'\u0C58'..'\u0C5A'|'\u0C60'..'\u0C61'|'\u0C85'..'\u0C8C'|'\u0C8E'..'\u0C90'|'\u0C92'..'\u0CA8'|'\u0CAA'..'\u0CB3'|'\u0CB5'..'\u0CB9'|'\u0CBD'|'\u0CDE'|'\u0CE0'..'\u0CE1'|'\u0CF1'..'\u0CF2'|'\u0D05'..'\u0D0C'|'\u0D0E'..'\u0D10'|'\u0D12'..'\u0D3A'|'\u0D3D'|'\u0D4E'|'\u0D5F'..'\u0D61'|'\u0D7A'..'\u0D7F'|'\u0D85'..'\u0D96'|'\u0D9A'..'\u0DB1'|'\u0DB3'..'\u0DBB'|'\u0DBD'|'\u0DC0'..'\u0DC6'|'\u0E01'..'\u0E30'|'\u0E32'..'\u0E33'|'\u0E40'..'\u0E46'|'\u0E81'..'\u0E82'|'\u0E84'|'\u0E87'..'\u0E88'|'\u0E8A'|'\u0E8D'|'\u0E94'..'\u0E97'|'\u0E99'..'\u0E9F'|'\u0EA1'..'\u0EA3'|'\u0EA5'|'\u0EA7'|'\u0EAA'..'\u0EAB'|'\u0EAD'..'\u0EB0'|'\u0EB2'..'\u0EB3'|'\u0EBD'|'\u0EC0'..'\u0EC4'|'\u0EC6'|'\u0EDC'..'\u0EDF'|'\u0F00'|'\u0F40'..'\u0F47'|'\u0F49'..'\u0F6C'|'\u0F88'..'\u0F8C'|'\u1000'..'\u102A'|'\u103F'|'\u1050'..'\u1055'|'\u105A'..'\u105D'|'\u1061'|'\u1065'..'\u1066'|'\u106E'..'\u1070'|'\u1075'..'\u1081'|'\u108E'|'\u10A0'..'\u10C5'|'\u10C7'|'\u10CD'|'\u10D0'..'\u10FA'|'\u10FC'..'\u1248'|'\u124A'..'\u124D'|'\u1250'..'\u1256'|'\u1258'|'\u125A'..'\u125D'|'\u1260'..'\u1288'|'\u128A'..'\u128D'|'\u1290'..'\u12B0'|'\u12B2'..'\u12B5'|'\u12B8'..'\u12BE'|'\u12C0'|'\u12C2'..'\u12C5'|'\u12C8'..'\u12D6'|'\u12D8'..'\u1310'|'\u1312'..'\u1315'|'\u1318'..'\u135A'|'\u1380'..'\u138F'|'\u13A0'..'\u13F5'|'\u13F8'..'\u13FD'|'\u1401'..'\u166C'|'\u166F'..'\u167F'|'\u1681'..'\u169A'|'\u16A0'..'\u16EA'|'\u16EE'..'\u16F8'|'\u1700'..'\u170C'|'\u170E'..'\u1711'|'\u1720'..'\u1731'|'\u1740'..'\u1751'|'\u1760'..'\u176C'|'\u176E'..'\u1770'|'\u1780'..'\u17B3'|'\u17D7'|'\u17DC'|'\u1820'..'\u1877'|'\u1880'..'\u18A8'|'\u18AA'|'\u18B0'..'\u18F5'|'\u1900'..'\u191E'|'\u1950'..'\u196D'|'\u1970'..'\u1974'|'\u1980'..'\u19AB'|'\u19B0'..'\u19C9'|'\u1A00'..'\u1A16'|'\u1A20'..'\u1A54'|'\u1AA7'|'\u1B05'..'\u1B33'|'\u1B45'..'\u1B4B'|'\u1B83'..'\u1BA0'|'\u1BAE'..'\u1BAF'|'\u1BBA'..'\u1BE5'|'\u1C00'..'\u1C23'|'\u1C4D'..'\u1C4F'|'\u1C5A'..'\u1C7D'|'\u1CE9'..'\u1CEC'|'\u1CEE'..'\u1CF1'|'\u1CF5'..'\u1CF6'|'\u1D00'..'\u1DBF'|'\u1E00'..'\u1F15'|'\u1F18'..'\u1F1D'|'\u1F20'..'\u1F45'|'\u1F48'..'\u1F4D'|'\u1F50'..'\u1F57'|'\u1F59'|'\u1F5B'|'\u1F5D'|'\u1F5F'..'\u1F7D'|'\u1F80'..'\u1FB4'|'\u1FB6'..'\u1FBC'|'\u1FBE'|'\u1FC2'..'\u1FC4'|'\u1FC6'..'\u1FCC'|'\u1FD0'..'\u1FD3'|'\u1FD6'..'\u1FDB'|'\u1FE0'..'\u1FEC'|'\u1FF2'..'\u1FF4'|'\u1FF6'..'\u1FFC'|'\u2071'|'\u207F'|'\u2090'..'\u209C'|'\u2102'|'\u2107'|'\u210A'..'\u2113'|'\u2115'|'\u2119'..'\u211D'|'\u2124'|'\u2126'|'\u2128'|'\u212A'..'\u212D'|'\u212F'..'\u2139'|'\u213C'..'\u213F'|'\u2145'..'\u2149'|'\u214E'|'\u2160'..'\u2188'|'\u2C00'..'\u2C2E'|'\u2C30'..'\u2C5E'|'\u2C60'..'\u2CE4'|'\u2CEB'..'\u2CEE'|'\u2CF2'..'\u2CF3'|'\u2D00'..'\u2D25'|'\u2D27'|'\u2D2D'|'\u2D30'..'\u2D67'|'\u2D6F'|'\u2D80'..'\u2D96'|'\u2DA0'..'\u2DA6'|'\u2DA8'..'\u2DAE'|'\u2DB0'..'\u2DB6'|'\u2DB8'..'\u2DBE'|'\u2DC0'..'\u2DC6'|'\u2DC8'..'\u2DCE'|'\u2DD0'..'\u2DD6'|'\u2DD8'..'\u2DDE'|'\u2E2F'|'\u3005'..'\u3007'|'\u3021'..'\u3029'|'\u3031'..'\u3035'|'\u3038'..'\u303C'|'\u3041'..'\u3096'|'\u309D'..'\u309F'|'\u30A1'..'\u30FA'|'\u30FC'..'\u30FF'|'\u3105'..'\u312D'|'\u3131'..'\u318E'|'\u31A0'..'\u31BA'|'\u31F0'..'\u31FF'|'\u3400'..'\u4DB5'|'\u4E00'..'\u9FD5'|'\uA000'..'\uA48C'|'\uA4D0'..'\uA4FD'|'\uA500'..'\uA60C'|'\uA610'..'\uA61F'|'\uA62A'..'\uA62B'|'\uA640'..'\uA66E'|'\uA67F'..'\uA69D'|'\uA6A0'..'\uA6EF'|'\uA717'..'\uA71F'|'\uA722'..'\uA788'|'\uA78B'..'\uA7AD'|'\uA7B0'..'\uA7B7'|'\uA7F7'..'\uA801'|'\uA803'..'\uA805'|'\uA807'..'\uA80A'|'\uA80C'..'\uA822'|'\uA840'..'\uA873'|'\uA882'..'\uA8B3'|'\uA8F2'..'\uA8F7'|'\uA8FB'|'\uA8FD'|'\uA90A'..'\uA925'|'\uA930'..'\uA946'|'\uA960'..'\uA97C'|'\uA984'..'\uA9B2'|'\uA9CF'|'\uA9E0'..'\uA9E4'|'\uA9E6'..'\uA9EF'|'\uA9FA'..'\uA9FE'|'\uAA00'..'\uAA28'|'\uAA40'..'\uAA42'|'\uAA44'..'\uAA4B'|'\uAA60'..'\uAA76'|'\uAA7A'|'\uAA7E'..'\uAAAF'|'\uAAB1'|'\uAAB5'..'\uAAB6'|'\uAAB9'..'\uAABD'|'\uAAC0'|'\uAAC2'|'\uAADB'..'\uAADD'|'\uAAE0'..'\uAAEA'|'\uAAF2'..'\uAAF4'|'\uAB01'..'\uAB06'|'\uAB09'..'\uAB0E'|'\uAB11'..'\uAB16'|'\uAB20'..'\uAB26'|'\uAB28'..'\uAB2E'|'\uAB30'..'\uAB5A'|'\uAB5C'..'\uAB65'|'\uAB70'..'\uABE2'|'\uAC00'..'\uD7A3'|'\uD7B0'..'\uD7C6'|'\uD7CB'..'\uD7FB'|'\uF900'..'\uFA6D'|'\uFA70'..'\uFAD9'|'\uFB00'..'\uFB06'|'\uFB13'..'\uFB17'|'\uFB1D'|'\uFB1F'..'\uFB28'|'\uFB2A'..'\uFB36'|'\uFB38'..'\uFB3C'|'\uFB3E'|'\uFB40'..'\uFB41'|'\uFB43'..'\uFB44'|'\uFB46'..'\uFBB1'|'\uFBD3'..'\uFD3D'|'\uFD50'..'\uFD8F'|'\uFD92'..'\uFDC7'|'\uFDF0'..'\uFDFB'|'\uFE70'..'\uFE74'|'\uFE76'..'\uFEFC'|'\uFF21'..'\uFF3A'|'\uFF41'..'\uFF5A'|'\uFF66'..'\uFFBE'|'\uFFC2'..'\uFFC7'|'\uFFCA'..'\uFFCF'|'\uFFD2'..'\uFFD7'|'\uFFDA'..'\uFFDC');

fragment RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT : (' '|'\u00A0'|'\u1680'|'\u2000'..'\u200A'|'\u202F'|'\u205F'|'\u3000');

fragment RULE_ANY_OTHER : .;
