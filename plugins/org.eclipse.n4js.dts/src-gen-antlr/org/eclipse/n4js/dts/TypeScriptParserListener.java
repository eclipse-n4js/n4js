// Generated from /Users/marcusmews/Eclipses/ECL3/git/n4js/plugins/org.eclipse.n4js.dts/grammar/TypeScriptParser.g4 by ANTLR 4.7.2
package org.eclipse.n4js.dts;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TypeScriptParser}.
 */
public interface TypeScriptParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(TypeScriptParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(TypeScriptParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(TypeScriptParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(TypeScriptParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#declareStatement}.
	 * @param ctx the parse tree
	 */
	void enterDeclareStatement(TypeScriptParser.DeclareStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#declareStatement}.
	 * @param ctx the parse tree
	 */
	void exitDeclareStatement(TypeScriptParser.DeclareStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#declarationStatement}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationStatement(TypeScriptParser.DeclarationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#declarationStatement}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationStatement(TypeScriptParser.DeclarationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(TypeScriptParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(TypeScriptParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#statementList}.
	 * @param ctx the parse tree
	 */
	void enterStatementList(TypeScriptParser.StatementListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#statementList}.
	 * @param ctx the parse tree
	 */
	void exitStatementList(TypeScriptParser.StatementListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#colonSepTypeRef}.
	 * @param ctx the parse tree
	 */
	void enterColonSepTypeRef(TypeScriptParser.ColonSepTypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#colonSepTypeRef}.
	 * @param ctx the parse tree
	 */
	void exitColonSepTypeRef(TypeScriptParser.ColonSepTypeRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeRef}.
	 * @param ctx the parse tree
	 */
	void enterTypeRef(TypeScriptParser.TypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeRef}.
	 * @param ctx the parse tree
	 */
	void exitTypeRef(TypeScriptParser.TypeRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#conditionalTypeRef}.
	 * @param ctx the parse tree
	 */
	void enterConditionalTypeRef(TypeScriptParser.ConditionalTypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#conditionalTypeRef}.
	 * @param ctx the parse tree
	 */
	void exitConditionalTypeRef(TypeScriptParser.ConditionalTypeRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#unionTypeExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnionTypeExpression(TypeScriptParser.UnionTypeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#unionTypeExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnionTypeExpression(TypeScriptParser.UnionTypeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#intersectionTypeExpression}.
	 * @param ctx the parse tree
	 */
	void enterIntersectionTypeExpression(TypeScriptParser.IntersectionTypeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#intersectionTypeExpression}.
	 * @param ctx the parse tree
	 */
	void exitIntersectionTypeExpression(TypeScriptParser.IntersectionTypeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#operatorTypeRef}.
	 * @param ctx the parse tree
	 */
	void enterOperatorTypeRef(TypeScriptParser.OperatorTypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#operatorTypeRef}.
	 * @param ctx the parse tree
	 */
	void exitOperatorTypeRef(TypeScriptParser.OperatorTypeRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeOperator}.
	 * @param ctx the parse tree
	 */
	void enterTypeOperator(TypeScriptParser.TypeOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeOperator}.
	 * @param ctx the parse tree
	 */
	void exitTypeOperator(TypeScriptParser.TypeOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#arrayTypeExpression}.
	 * @param ctx the parse tree
	 */
	void enterArrayTypeExpression(TypeScriptParser.ArrayTypeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#arrayTypeExpression}.
	 * @param ctx the parse tree
	 */
	void exitArrayTypeExpression(TypeScriptParser.ArrayTypeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#primaryTypeExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryTypeExpression(TypeScriptParser.PrimaryTypeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#primaryTypeExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryTypeExpression(TypeScriptParser.PrimaryTypeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#literalType}.
	 * @param ctx the parse tree
	 */
	void enterLiteralType(TypeScriptParser.LiteralTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#literalType}.
	 * @param ctx the parse tree
	 */
	void exitLiteralType(TypeScriptParser.LiteralTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#arrowFunctionTypeExpression}.
	 * @param ctx the parse tree
	 */
	void enterArrowFunctionTypeExpression(TypeScriptParser.ArrowFunctionTypeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#arrowFunctionTypeExpression}.
	 * @param ctx the parse tree
	 */
	void exitArrowFunctionTypeExpression(TypeScriptParser.ArrowFunctionTypeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#tupleTypeExpression}.
	 * @param ctx the parse tree
	 */
	void enterTupleTypeExpression(TypeScriptParser.TupleTypeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#tupleTypeExpression}.
	 * @param ctx the parse tree
	 */
	void exitTupleTypeExpression(TypeScriptParser.TupleTypeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#tupleTypeArgument}.
	 * @param ctx the parse tree
	 */
	void enterTupleTypeArgument(TypeScriptParser.TupleTypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#tupleTypeArgument}.
	 * @param ctx the parse tree
	 */
	void exitTupleTypeArgument(TypeScriptParser.TupleTypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeVariable}.
	 * @param ctx the parse tree
	 */
	void enterTypeVariable(TypeScriptParser.TypeVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeVariable}.
	 * @param ctx the parse tree
	 */
	void exitTypeVariable(TypeScriptParser.TypeVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeRefWithModifiers}.
	 * @param ctx the parse tree
	 */
	void enterTypeRefWithModifiers(TypeScriptParser.TypeRefWithModifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeRefWithModifiers}.
	 * @param ctx the parse tree
	 */
	void exitTypeRefWithModifiers(TypeScriptParser.TypeRefWithModifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#parameterizedTypeRef}.
	 * @param ctx the parse tree
	 */
	void enterParameterizedTypeRef(TypeScriptParser.ParameterizedTypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#parameterizedTypeRef}.
	 * @param ctx the parse tree
	 */
	void exitParameterizedTypeRef(TypeScriptParser.ParameterizedTypeRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(TypeScriptParser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(TypeScriptParser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeGeneric}.
	 * @param ctx the parse tree
	 */
	void enterTypeGeneric(TypeScriptParser.TypeGenericContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeGeneric}.
	 * @param ctx the parse tree
	 */
	void exitTypeGeneric(TypeScriptParser.TypeGenericContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeArgumentList}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgumentList(TypeScriptParser.TypeArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeArgumentList}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgumentList(TypeScriptParser.TypeArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgument(TypeScriptParser.TypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgument(TypeScriptParser.TypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void enterTypeArguments(TypeScriptParser.TypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void exitTypeArguments(TypeScriptParser.TypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#objectLiteralTypeRef}.
	 * @param ctx the parse tree
	 */
	void enterObjectLiteralTypeRef(TypeScriptParser.ObjectLiteralTypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#objectLiteralTypeRef}.
	 * @param ctx the parse tree
	 */
	void exitObjectLiteralTypeRef(TypeScriptParser.ObjectLiteralTypeRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#thisTypeRef}.
	 * @param ctx the parse tree
	 */
	void enterThisTypeRef(TypeScriptParser.ThisTypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#thisTypeRef}.
	 * @param ctx the parse tree
	 */
	void exitThisTypeRef(TypeScriptParser.ThisTypeRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#queryTypeRef}.
	 * @param ctx the parse tree
	 */
	void enterQueryTypeRef(TypeScriptParser.QueryTypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#queryTypeRef}.
	 * @param ctx the parse tree
	 */
	void exitQueryTypeRef(TypeScriptParser.QueryTypeRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#importTypeRef}.
	 * @param ctx the parse tree
	 */
	void enterImportTypeRef(TypeScriptParser.ImportTypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#importTypeRef}.
	 * @param ctx the parse tree
	 */
	void exitImportTypeRef(TypeScriptParser.ImportTypeRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#anonymousFormalParameterListWithDeclaredThisType}.
	 * @param ctx the parse tree
	 */
	void enterAnonymousFormalParameterListWithDeclaredThisType(TypeScriptParser.AnonymousFormalParameterListWithDeclaredThisTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#anonymousFormalParameterListWithDeclaredThisType}.
	 * @param ctx the parse tree
	 */
	void exitAnonymousFormalParameterListWithDeclaredThisType(TypeScriptParser.AnonymousFormalParameterListWithDeclaredThisTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#anonymousFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterAnonymousFormalParameter(TypeScriptParser.AnonymousFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#anonymousFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitAnonymousFormalParameter(TypeScriptParser.AnonymousFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#defaultFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterDefaultFormalParameter(TypeScriptParser.DefaultFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#defaultFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitDefaultFormalParameter(TypeScriptParser.DefaultFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typePredicateWithOperatorTypeRef}.
	 * @param ctx the parse tree
	 */
	void enterTypePredicateWithOperatorTypeRef(TypeScriptParser.TypePredicateWithOperatorTypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typePredicateWithOperatorTypeRef}.
	 * @param ctx the parse tree
	 */
	void exitTypePredicateWithOperatorTypeRef(TypeScriptParser.TypePredicateWithOperatorTypeRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#bindingIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterBindingIdentifier(TypeScriptParser.BindingIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#bindingIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitBindingIdentifier(TypeScriptParser.BindingIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#propertyAccessExpressionInTypeRef}.
	 * @param ctx the parse tree
	 */
	void enterPropertyAccessExpressionInTypeRef(TypeScriptParser.PropertyAccessExpressionInTypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#propertyAccessExpressionInTypeRef}.
	 * @param ctx the parse tree
	 */
	void exitPropertyAccessExpressionInTypeRef(TypeScriptParser.PropertyAccessExpressionInTypeRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#inferTypeRef}.
	 * @param ctx the parse tree
	 */
	void enterInferTypeRef(TypeScriptParser.InferTypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#inferTypeRef}.
	 * @param ctx the parse tree
	 */
	void exitInferTypeRef(TypeScriptParser.InferTypeRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#propertySignature}.
	 * @param ctx the parse tree
	 */
	void enterPropertySignature(TypeScriptParser.PropertySignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#propertySignature}.
	 * @param ctx the parse tree
	 */
	void exitPropertySignature(TypeScriptParser.PropertySignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#constructSignature}.
	 * @param ctx the parse tree
	 */
	void enterConstructSignature(TypeScriptParser.ConstructSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#constructSignature}.
	 * @param ctx the parse tree
	 */
	void exitConstructSignature(TypeScriptParser.ConstructSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameters(TypeScriptParser.TypeParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameters(TypeScriptParser.TypeParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeParameterList}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameterList(TypeScriptParser.TypeParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeParameterList}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameterList(TypeScriptParser.TypeParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(TypeScriptParser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(TypeScriptParser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterConstraint(TypeScriptParser.ConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitConstraint(TypeScriptParser.ConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#defaultType}.
	 * @param ctx the parse tree
	 */
	void enterDefaultType(TypeScriptParser.DefaultTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#defaultType}.
	 * @param ctx the parse tree
	 */
	void exitDefaultType(TypeScriptParser.DefaultTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#indexSignature}.
	 * @param ctx the parse tree
	 */
	void enterIndexSignature(TypeScriptParser.IndexSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#indexSignature}.
	 * @param ctx the parse tree
	 */
	void exitIndexSignature(TypeScriptParser.IndexSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#indexSignatureElement}.
	 * @param ctx the parse tree
	 */
	void enterIndexSignatureElement(TypeScriptParser.IndexSignatureElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#indexSignatureElement}.
	 * @param ctx the parse tree
	 */
	void exitIndexSignatureElement(TypeScriptParser.IndexSignatureElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#methodSignature}.
	 * @param ctx the parse tree
	 */
	void enterMethodSignature(TypeScriptParser.MethodSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#methodSignature}.
	 * @param ctx the parse tree
	 */
	void exitMethodSignature(TypeScriptParser.MethodSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeAliasDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTypeAliasDeclaration(TypeScriptParser.TypeAliasDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeAliasDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTypeAliasDeclaration(TypeScriptParser.TypeAliasDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#moduleDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterModuleDeclaration(TypeScriptParser.ModuleDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#moduleDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitModuleDeclaration(TypeScriptParser.ModuleDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#moduleName}.
	 * @param ctx the parse tree
	 */
	void enterModuleName(TypeScriptParser.ModuleNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#moduleName}.
	 * @param ctx the parse tree
	 */
	void exitModuleName(TypeScriptParser.ModuleNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#namespaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterNamespaceDeclaration(TypeScriptParser.NamespaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#namespaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitNamespaceDeclaration(TypeScriptParser.NamespaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#namespaceName}.
	 * @param ctx the parse tree
	 */
	void enterNamespaceName(TypeScriptParser.NamespaceNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#namespaceName}.
	 * @param ctx the parse tree
	 */
	void exitNamespaceName(TypeScriptParser.NamespaceNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#decoratorList}.
	 * @param ctx the parse tree
	 */
	void enterDecoratorList(TypeScriptParser.DecoratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#decoratorList}.
	 * @param ctx the parse tree
	 */
	void exitDecoratorList(TypeScriptParser.DecoratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#decorator}.
	 * @param ctx the parse tree
	 */
	void enterDecorator(TypeScriptParser.DecoratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#decorator}.
	 * @param ctx the parse tree
	 */
	void exitDecorator(TypeScriptParser.DecoratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#decoratorMemberExpression}.
	 * @param ctx the parse tree
	 */
	void enterDecoratorMemberExpression(TypeScriptParser.DecoratorMemberExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#decoratorMemberExpression}.
	 * @param ctx the parse tree
	 */
	void exitDecoratorMemberExpression(TypeScriptParser.DecoratorMemberExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#decoratorCallExpression}.
	 * @param ctx the parse tree
	 */
	void enterDecoratorCallExpression(TypeScriptParser.DecoratorCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#decoratorCallExpression}.
	 * @param ctx the parse tree
	 */
	void exitDecoratorCallExpression(TypeScriptParser.DecoratorCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceDeclaration(TypeScriptParser.InterfaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceDeclaration(TypeScriptParser.InterfaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#interfaceExtendsClause}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceExtendsClause(TypeScriptParser.InterfaceExtendsClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#interfaceExtendsClause}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceExtendsClause(TypeScriptParser.InterfaceExtendsClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#classOrInterfaceTypeList}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceTypeList(TypeScriptParser.ClassOrInterfaceTypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#classOrInterfaceTypeList}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceTypeList(TypeScriptParser.ClassOrInterfaceTypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceBody(TypeScriptParser.InterfaceBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceBody(TypeScriptParser.InterfaceBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#interfaceMemberList}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMemberList(TypeScriptParser.InterfaceMemberListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#interfaceMemberList}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMemberList(TypeScriptParser.InterfaceMemberListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#interfaceMember}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMember(TypeScriptParser.InterfaceMemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#interfaceMember}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMember(TypeScriptParser.InterfaceMemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterEnumDeclaration(TypeScriptParser.EnumDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitEnumDeclaration(TypeScriptParser.EnumDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#enumBody}.
	 * @param ctx the parse tree
	 */
	void enterEnumBody(TypeScriptParser.EnumBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#enumBody}.
	 * @param ctx the parse tree
	 */
	void exitEnumBody(TypeScriptParser.EnumBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#enumMemberList}.
	 * @param ctx the parse tree
	 */
	void enterEnumMemberList(TypeScriptParser.EnumMemberListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#enumMemberList}.
	 * @param ctx the parse tree
	 */
	void exitEnumMemberList(TypeScriptParser.EnumMemberListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#enumMember}.
	 * @param ctx the parse tree
	 */
	void enterEnumMember(TypeScriptParser.EnumMemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#enumMember}.
	 * @param ctx the parse tree
	 */
	void exitEnumMember(TypeScriptParser.EnumMemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(TypeScriptParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(TypeScriptParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(TypeScriptParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(TypeScriptParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#classHeritage}.
	 * @param ctx the parse tree
	 */
	void enterClassHeritage(TypeScriptParser.ClassHeritageContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#classHeritage}.
	 * @param ctx the parse tree
	 */
	void exitClassHeritage(TypeScriptParser.ClassHeritageContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#classExtendsClause}.
	 * @param ctx the parse tree
	 */
	void enterClassExtendsClause(TypeScriptParser.ClassExtendsClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#classExtendsClause}.
	 * @param ctx the parse tree
	 */
	void exitClassExtendsClause(TypeScriptParser.ClassExtendsClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#classImplementsClause}.
	 * @param ctx the parse tree
	 */
	void enterClassImplementsClause(TypeScriptParser.ClassImplementsClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#classImplementsClause}.
	 * @param ctx the parse tree
	 */
	void exitClassImplementsClause(TypeScriptParser.ClassImplementsClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(TypeScriptParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(TypeScriptParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#classMemberList}.
	 * @param ctx the parse tree
	 */
	void enterClassMemberList(TypeScriptParser.ClassMemberListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#classMemberList}.
	 * @param ctx the parse tree
	 */
	void exitClassMemberList(TypeScriptParser.ClassMemberListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#classMember}.
	 * @param ctx the parse tree
	 */
	void enterClassMember(TypeScriptParser.ClassMemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#classMember}.
	 * @param ctx the parse tree
	 */
	void exitClassMember(TypeScriptParser.ClassMemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclaration(TypeScriptParser.ConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclaration(TypeScriptParser.ConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#propertyMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPropertyMemberDeclaration(TypeScriptParser.PropertyMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#propertyMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPropertyMemberDeclaration(TypeScriptParser.PropertyMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#abstractDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAbstractDeclaration(TypeScriptParser.AbstractDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#abstractDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAbstractDeclaration(TypeScriptParser.AbstractDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#propertyMember}.
	 * @param ctx the parse tree
	 */
	void enterPropertyMember(TypeScriptParser.PropertyMemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#propertyMember}.
	 * @param ctx the parse tree
	 */
	void exitPropertyMember(TypeScriptParser.PropertyMemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#propertyMemberBase}.
	 * @param ctx the parse tree
	 */
	void enterPropertyMemberBase(TypeScriptParser.PropertyMemberBaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#propertyMemberBase}.
	 * @param ctx the parse tree
	 */
	void exitPropertyMemberBase(TypeScriptParser.PropertyMemberBaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#propertyOrMethod}.
	 * @param ctx the parse tree
	 */
	void enterPropertyOrMethod(TypeScriptParser.PropertyOrMethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#propertyOrMethod}.
	 * @param ctx the parse tree
	 */
	void exitPropertyOrMethod(TypeScriptParser.PropertyOrMethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#initializer}.
	 * @param ctx the parse tree
	 */
	void enterInitializer(TypeScriptParser.InitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#initializer}.
	 * @param ctx the parse tree
	 */
	void exitInitializer(TypeScriptParser.InitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#callSignature}.
	 * @param ctx the parse tree
	 */
	void enterCallSignature(TypeScriptParser.CallSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#callSignature}.
	 * @param ctx the parse tree
	 */
	void exitCallSignature(TypeScriptParser.CallSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#parameterBlock}.
	 * @param ctx the parse tree
	 */
	void enterParameterBlock(TypeScriptParser.ParameterBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#parameterBlock}.
	 * @param ctx the parse tree
	 */
	void exitParameterBlock(TypeScriptParser.ParameterBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#parameterListTrailingComma}.
	 * @param ctx the parse tree
	 */
	void enterParameterListTrailingComma(TypeScriptParser.ParameterListTrailingCommaContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#parameterListTrailingComma}.
	 * @param ctx the parse tree
	 */
	void exitParameterListTrailingComma(TypeScriptParser.ParameterListTrailingCommaContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(TypeScriptParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(TypeScriptParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#restParameter}.
	 * @param ctx the parse tree
	 */
	void enterRestParameter(TypeScriptParser.RestParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#restParameter}.
	 * @param ctx the parse tree
	 */
	void exitRestParameter(TypeScriptParser.RestParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(TypeScriptParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(TypeScriptParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#requiredParameter}.
	 * @param ctx the parse tree
	 */
	void enterRequiredParameter(TypeScriptParser.RequiredParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#requiredParameter}.
	 * @param ctx the parse tree
	 */
	void exitRequiredParameter(TypeScriptParser.RequiredParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#optionalParameter}.
	 * @param ctx the parse tree
	 */
	void enterOptionalParameter(TypeScriptParser.OptionalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#optionalParameter}.
	 * @param ctx the parse tree
	 */
	void exitOptionalParameter(TypeScriptParser.OptionalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#accessibilityModifier}.
	 * @param ctx the parse tree
	 */
	void enterAccessibilityModifier(TypeScriptParser.AccessibilityModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#accessibilityModifier}.
	 * @param ctx the parse tree
	 */
	void exitAccessibilityModifier(TypeScriptParser.AccessibilityModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#identifierOrPattern}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierOrPattern(TypeScriptParser.IdentifierOrPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#identifierOrPattern}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierOrPattern(TypeScriptParser.IdentifierOrPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#bindingPattern}.
	 * @param ctx the parse tree
	 */
	void enterBindingPattern(TypeScriptParser.BindingPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#bindingPattern}.
	 * @param ctx the parse tree
	 */
	void exitBindingPattern(TypeScriptParser.BindingPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#arrayLiteral}.
	 * @param ctx the parse tree
	 */
	void enterArrayLiteral(TypeScriptParser.ArrayLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#arrayLiteral}.
	 * @param ctx the parse tree
	 */
	void exitArrayLiteral(TypeScriptParser.ArrayLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#elementList}.
	 * @param ctx the parse tree
	 */
	void enterElementList(TypeScriptParser.ElementListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#elementList}.
	 * @param ctx the parse tree
	 */
	void exitElementList(TypeScriptParser.ElementListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#arrayElement}.
	 * @param ctx the parse tree
	 */
	void enterArrayElement(TypeScriptParser.ArrayElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#arrayElement}.
	 * @param ctx the parse tree
	 */
	void exitArrayElement(TypeScriptParser.ArrayElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#bindingElement}.
	 * @param ctx the parse tree
	 */
	void enterBindingElement(TypeScriptParser.BindingElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#bindingElement}.
	 * @param ctx the parse tree
	 */
	void exitBindingElement(TypeScriptParser.BindingElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#objectLiteral}.
	 * @param ctx the parse tree
	 */
	void enterObjectLiteral(TypeScriptParser.ObjectLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#objectLiteral}.
	 * @param ctx the parse tree
	 */
	void exitObjectLiteral(TypeScriptParser.ObjectLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PropertyExpressionAssignment}
	 * labeled alternative in {@link TypeScriptParser#propertyAssignment}.
	 * @param ctx the parse tree
	 */
	void enterPropertyExpressionAssignment(TypeScriptParser.PropertyExpressionAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PropertyExpressionAssignment}
	 * labeled alternative in {@link TypeScriptParser#propertyAssignment}.
	 * @param ctx the parse tree
	 */
	void exitPropertyExpressionAssignment(TypeScriptParser.PropertyExpressionAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PropertyGetter}
	 * labeled alternative in {@link TypeScriptParser#propertyAssignment}.
	 * @param ctx the parse tree
	 */
	void enterPropertyGetter(TypeScriptParser.PropertyGetterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PropertyGetter}
	 * labeled alternative in {@link TypeScriptParser#propertyAssignment}.
	 * @param ctx the parse tree
	 */
	void exitPropertyGetter(TypeScriptParser.PropertyGetterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PropertySetter}
	 * labeled alternative in {@link TypeScriptParser#propertyAssignment}.
	 * @param ctx the parse tree
	 */
	void enterPropertySetter(TypeScriptParser.PropertySetterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PropertySetter}
	 * labeled alternative in {@link TypeScriptParser#propertyAssignment}.
	 * @param ctx the parse tree
	 */
	void exitPropertySetter(TypeScriptParser.PropertySetterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MethodProperty}
	 * labeled alternative in {@link TypeScriptParser#propertyAssignment}.
	 * @param ctx the parse tree
	 */
	void enterMethodProperty(TypeScriptParser.MethodPropertyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MethodProperty}
	 * labeled alternative in {@link TypeScriptParser#propertyAssignment}.
	 * @param ctx the parse tree
	 */
	void exitMethodProperty(TypeScriptParser.MethodPropertyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RestParameterInObject}
	 * labeled alternative in {@link TypeScriptParser#propertyAssignment}.
	 * @param ctx the parse tree
	 */
	void enterRestParameterInObject(TypeScriptParser.RestParameterInObjectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RestParameterInObject}
	 * labeled alternative in {@link TypeScriptParser#propertyAssignment}.
	 * @param ctx the parse tree
	 */
	void exitRestParameterInObject(TypeScriptParser.RestParameterInObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#propertyName}.
	 * @param ctx the parse tree
	 */
	void enterPropertyName(TypeScriptParser.PropertyNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#propertyName}.
	 * @param ctx the parse tree
	 */
	void exitPropertyName(TypeScriptParser.PropertyNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#getAccessor}.
	 * @param ctx the parse tree
	 */
	void enterGetAccessor(TypeScriptParser.GetAccessorContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#getAccessor}.
	 * @param ctx the parse tree
	 */
	void exitGetAccessor(TypeScriptParser.GetAccessorContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#setAccessor}.
	 * @param ctx the parse tree
	 */
	void enterSetAccessor(TypeScriptParser.SetAccessorContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#setAccessor}.
	 * @param ctx the parse tree
	 */
	void exitSetAccessor(TypeScriptParser.SetAccessorContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#generatorMethod}.
	 * @param ctx the parse tree
	 */
	void enterGeneratorMethod(TypeScriptParser.GeneratorMethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#generatorMethod}.
	 * @param ctx the parse tree
	 */
	void exitGeneratorMethod(TypeScriptParser.GeneratorMethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(TypeScriptParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(TypeScriptParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(TypeScriptParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(TypeScriptParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(TypeScriptParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(TypeScriptParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void enterImportStatement(TypeScriptParser.ImportStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void exitImportStatement(TypeScriptParser.ImportStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#importFromBlock}.
	 * @param ctx the parse tree
	 */
	void enterImportFromBlock(TypeScriptParser.ImportFromBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#importFromBlock}.
	 * @param ctx the parse tree
	 */
	void exitImportFromBlock(TypeScriptParser.ImportFromBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#multipleImportElements}.
	 * @param ctx the parse tree
	 */
	void enterMultipleImportElements(TypeScriptParser.MultipleImportElementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#multipleImportElements}.
	 * @param ctx the parse tree
	 */
	void exitMultipleImportElements(TypeScriptParser.MultipleImportElementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#importedElement}.
	 * @param ctx the parse tree
	 */
	void enterImportedElement(TypeScriptParser.ImportedElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#importedElement}.
	 * @param ctx the parse tree
	 */
	void exitImportedElement(TypeScriptParser.ImportedElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#importAliasDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterImportAliasDeclaration(TypeScriptParser.ImportAliasDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#importAliasDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitImportAliasDeclaration(TypeScriptParser.ImportAliasDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#exportStatement}.
	 * @param ctx the parse tree
	 */
	void enterExportStatement(TypeScriptParser.ExportStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#exportStatement}.
	 * @param ctx the parse tree
	 */
	void exitExportStatement(TypeScriptParser.ExportStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExportAsNamespace}
	 * labeled alternative in {@link TypeScriptParser#exportStatementTail}.
	 * @param ctx the parse tree
	 */
	void enterExportAsNamespace(TypeScriptParser.ExportAsNamespaceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExportAsNamespace}
	 * labeled alternative in {@link TypeScriptParser#exportStatementTail}.
	 * @param ctx the parse tree
	 */
	void exitExportAsNamespace(TypeScriptParser.ExportAsNamespaceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExportEquals}
	 * labeled alternative in {@link TypeScriptParser#exportStatementTail}.
	 * @param ctx the parse tree
	 */
	void enterExportEquals(TypeScriptParser.ExportEqualsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExportEquals}
	 * labeled alternative in {@link TypeScriptParser#exportStatementTail}.
	 * @param ctx the parse tree
	 */
	void exitExportEquals(TypeScriptParser.ExportEqualsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExportImport}
	 * labeled alternative in {@link TypeScriptParser#exportStatementTail}.
	 * @param ctx the parse tree
	 */
	void enterExportImport(TypeScriptParser.ExportImportContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExportImport}
	 * labeled alternative in {@link TypeScriptParser#exportStatementTail}.
	 * @param ctx the parse tree
	 */
	void exitExportImport(TypeScriptParser.ExportImportContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExportDeclareStatement}
	 * labeled alternative in {@link TypeScriptParser#exportStatementTail}.
	 * @param ctx the parse tree
	 */
	void enterExportDeclareStatement(TypeScriptParser.ExportDeclareStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExportDeclareStatement}
	 * labeled alternative in {@link TypeScriptParser#exportStatementTail}.
	 * @param ctx the parse tree
	 */
	void exitExportDeclareStatement(TypeScriptParser.ExportDeclareStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExportElement}
	 * labeled alternative in {@link TypeScriptParser#exportStatementTail}.
	 * @param ctx the parse tree
	 */
	void enterExportElement(TypeScriptParser.ExportElementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExportElement}
	 * labeled alternative in {@link TypeScriptParser#exportStatementTail}.
	 * @param ctx the parse tree
	 */
	void exitExportElement(TypeScriptParser.ExportElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#exportFromBlock}.
	 * @param ctx the parse tree
	 */
	void enterExportFromBlock(TypeScriptParser.ExportFromBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#exportFromBlock}.
	 * @param ctx the parse tree
	 */
	void exitExportFromBlock(TypeScriptParser.ExportFromBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#variableStatement}.
	 * @param ctx the parse tree
	 */
	void enterVariableStatement(TypeScriptParser.VariableStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#variableStatement}.
	 * @param ctx the parse tree
	 */
	void exitVariableStatement(TypeScriptParser.VariableStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#varModifier}.
	 * @param ctx the parse tree
	 */
	void enterVarModifier(TypeScriptParser.VarModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#varModifier}.
	 * @param ctx the parse tree
	 */
	void exitVarModifier(TypeScriptParser.VarModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#bindingPatternBlock}.
	 * @param ctx the parse tree
	 */
	void enterBindingPatternBlock(TypeScriptParser.BindingPatternBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#bindingPatternBlock}.
	 * @param ctx the parse tree
	 */
	void exitBindingPatternBlock(TypeScriptParser.BindingPatternBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#variableDeclarationList}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarationList(TypeScriptParser.VariableDeclarationListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#variableDeclarationList}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarationList(TypeScriptParser.VariableDeclarationListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(TypeScriptParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(TypeScriptParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void enterSwitchStatement(TypeScriptParser.SwitchStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void exitSwitchStatement(TypeScriptParser.SwitchStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#caseBlock}.
	 * @param ctx the parse tree
	 */
	void enterCaseBlock(TypeScriptParser.CaseBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#caseBlock}.
	 * @param ctx the parse tree
	 */
	void exitCaseBlock(TypeScriptParser.CaseBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#caseClauses}.
	 * @param ctx the parse tree
	 */
	void enterCaseClauses(TypeScriptParser.CaseClausesContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#caseClauses}.
	 * @param ctx the parse tree
	 */
	void exitCaseClauses(TypeScriptParser.CaseClausesContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#caseClause}.
	 * @param ctx the parse tree
	 */
	void enterCaseClause(TypeScriptParser.CaseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#caseClause}.
	 * @param ctx the parse tree
	 */
	void exitCaseClause(TypeScriptParser.CaseClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#defaultClause}.
	 * @param ctx the parse tree
	 */
	void enterDefaultClause(TypeScriptParser.DefaultClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#defaultClause}.
	 * @param ctx the parse tree
	 */
	void exitDefaultClause(TypeScriptParser.DefaultClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void enterTryStatement(TypeScriptParser.TryStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void exitTryStatement(TypeScriptParser.TryStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#catchProduction}.
	 * @param ctx the parse tree
	 */
	void enterCatchProduction(TypeScriptParser.CatchProductionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#catchProduction}.
	 * @param ctx the parse tree
	 */
	void exitCatchProduction(TypeScriptParser.CatchProductionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#finallyProduction}.
	 * @param ctx the parse tree
	 */
	void enterFinallyProduction(TypeScriptParser.FinallyProductionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#finallyProduction}.
	 * @param ctx the parse tree
	 */
	void exitFinallyProduction(TypeScriptParser.FinallyProductionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStatement(TypeScriptParser.EmptyStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStatement(TypeScriptParser.EmptyStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(TypeScriptParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(TypeScriptParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DoStatement}
	 * labeled alternative in {@link TypeScriptParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoStatement(TypeScriptParser.DoStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DoStatement}
	 * labeled alternative in {@link TypeScriptParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoStatement(TypeScriptParser.DoStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileStatement}
	 * labeled alternative in {@link TypeScriptParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(TypeScriptParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileStatement}
	 * labeled alternative in {@link TypeScriptParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(TypeScriptParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForStatement}
	 * labeled alternative in {@link TypeScriptParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(TypeScriptParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForStatement}
	 * labeled alternative in {@link TypeScriptParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(TypeScriptParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForVarStatement}
	 * labeled alternative in {@link TypeScriptParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterForVarStatement(TypeScriptParser.ForVarStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForVarStatement}
	 * labeled alternative in {@link TypeScriptParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitForVarStatement(TypeScriptParser.ForVarStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForInStatement}
	 * labeled alternative in {@link TypeScriptParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterForInStatement(TypeScriptParser.ForInStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForInStatement}
	 * labeled alternative in {@link TypeScriptParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitForInStatement(TypeScriptParser.ForInStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForVarInStatement}
	 * labeled alternative in {@link TypeScriptParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterForVarInStatement(TypeScriptParser.ForVarInStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForVarInStatement}
	 * labeled alternative in {@link TypeScriptParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitForVarInStatement(TypeScriptParser.ForVarInStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(TypeScriptParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(TypeScriptParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(TypeScriptParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(TypeScriptParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(TypeScriptParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(TypeScriptParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#withStatement}.
	 * @param ctx the parse tree
	 */
	void enterWithStatement(TypeScriptParser.WithStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#withStatement}.
	 * @param ctx the parse tree
	 */
	void exitWithStatement(TypeScriptParser.WithStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#labelledStatement}.
	 * @param ctx the parse tree
	 */
	void enterLabelledStatement(TypeScriptParser.LabelledStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#labelledStatement}.
	 * @param ctx the parse tree
	 */
	void exitLabelledStatement(TypeScriptParser.LabelledStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#throwStatement}.
	 * @param ctx the parse tree
	 */
	void enterThrowStatement(TypeScriptParser.ThrowStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#throwStatement}.
	 * @param ctx the parse tree
	 */
	void exitThrowStatement(TypeScriptParser.ThrowStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#debuggerStatement}.
	 * @param ctx the parse tree
	 */
	void enterDebuggerStatement(TypeScriptParser.DebuggerStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#debuggerStatement}.
	 * @param ctx the parse tree
	 */
	void exitDebuggerStatement(TypeScriptParser.DebuggerStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(TypeScriptParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(TypeScriptParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#expressionSequence}.
	 * @param ctx the parse tree
	 */
	void enterExpressionSequence(TypeScriptParser.ExpressionSequenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#expressionSequence}.
	 * @param ctx the parse tree
	 */
	void exitExpressionSequence(TypeScriptParser.ExpressionSequenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TemplateStringExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterTemplateStringExpression(TypeScriptParser.TemplateStringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TemplateStringExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitTemplateStringExpression(TypeScriptParser.TemplateStringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TernaryExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterTernaryExpression(TypeScriptParser.TernaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TernaryExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitTernaryExpression(TypeScriptParser.TernaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ObjectLiteralExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterObjectLiteralExpression(TypeScriptParser.ObjectLiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ObjectLiteralExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitObjectLiteralExpression(TypeScriptParser.ObjectLiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(TypeScriptParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(TypeScriptParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionExpressionL}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionExpressionL(TypeScriptParser.FunctionExpressionLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionExpressionL}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionExpressionL(TypeScriptParser.FunctionExpressionLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ThisExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterThisExpression(TypeScriptParser.ThisExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ThisExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitThisExpression(TypeScriptParser.ThisExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AwaitExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterAwaitExpression(TypeScriptParser.AwaitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AwaitExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitAwaitExpression(TypeScriptParser.AwaitExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LogicalExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalExpression(TypeScriptParser.LogicalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LogicalExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalExpression(TypeScriptParser.LogicalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(TypeScriptParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(TypeScriptParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpression(TypeScriptParser.BinaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpression(TypeScriptParser.BinaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EqualityExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(TypeScriptParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EqualityExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(TypeScriptParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CastAsExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterCastAsExpression(TypeScriptParser.CastAsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CastAsExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitCastAsExpression(TypeScriptParser.CastAsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SuperExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterSuperExpression(TypeScriptParser.SuperExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SuperExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitSuperExpression(TypeScriptParser.SuperExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MultiplicativeExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(TypeScriptParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MultiplicativeExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(TypeScriptParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterCallExpression(TypeScriptParser.CallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitCallExpression(TypeScriptParser.CallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BitShiftExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterBitShiftExpression(TypeScriptParser.BitShiftExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BitShiftExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitBitShiftExpression(TypeScriptParser.BitShiftExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenthesizedExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(TypeScriptParser.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenthesizedExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(TypeScriptParser.ParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code coalesceExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterCoalesceExpression(TypeScriptParser.CoalesceExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code coalesceExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitCoalesceExpression(TypeScriptParser.CoalesceExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RelationalExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(TypeScriptParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RelationalExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(TypeScriptParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AdditiveExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(TypeScriptParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AdditiveExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(TypeScriptParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IndexedAccessExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterIndexedAccessExpression(TypeScriptParser.IndexedAccessExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IndexedAccessExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitIndexedAccessExpression(TypeScriptParser.IndexedAccessExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code YieldExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterYieldExpression(TypeScriptParser.YieldExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code YieldExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitYieldExpression(TypeScriptParser.YieldExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpression(TypeScriptParser.LiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpression(TypeScriptParser.LiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayLiteralExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterArrayLiteralExpression(TypeScriptParser.ArrayLiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayLiteralExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitArrayLiteralExpression(TypeScriptParser.ArrayLiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ClassExpressionL}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterClassExpressionL(TypeScriptParser.ClassExpressionLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ClassExpressionL}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitClassExpressionL(TypeScriptParser.ClassExpressionLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewExpressionL}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpressionL(TypeScriptParser.NewExpressionLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewExpressionL}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpressionL(TypeScriptParser.NewExpressionLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpression(TypeScriptParser.IdentifierExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpression(TypeScriptParser.IdentifierExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PropertyAccessExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterPropertyAccessExpression(TypeScriptParser.PropertyAccessExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PropertyAccessExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitPropertyAccessExpression(TypeScriptParser.PropertyAccessExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PostfixExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(TypeScriptParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PostfixExpression}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(TypeScriptParser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrowFunctionExpressionL}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterArrowFunctionExpressionL(TypeScriptParser.ArrowFunctionExpressionLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrowFunctionExpressionL}
	 * labeled alternative in {@link TypeScriptParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitArrowFunctionExpressionL(TypeScriptParser.ArrowFunctionExpressionLContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#functionExpression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionExpression(TypeScriptParser.FunctionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#functionExpression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionExpression(TypeScriptParser.FunctionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#arrowFunctionExpression}.
	 * @param ctx the parse tree
	 */
	void enterArrowFunctionExpression(TypeScriptParser.ArrowFunctionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#arrowFunctionExpression}.
	 * @param ctx the parse tree
	 */
	void exitArrowFunctionExpression(TypeScriptParser.ArrowFunctionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#arrowFunctionBody}.
	 * @param ctx the parse tree
	 */
	void enterArrowFunctionBody(TypeScriptParser.ArrowFunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#arrowFunctionBody}.
	 * @param ctx the parse tree
	 */
	void exitArrowFunctionBody(TypeScriptParser.ArrowFunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#classExpression}.
	 * @param ctx the parse tree
	 */
	void enterClassExpression(TypeScriptParser.ClassExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#classExpression}.
	 * @param ctx the parse tree
	 */
	void exitClassExpression(TypeScriptParser.ClassExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(TypeScriptParser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(TypeScriptParser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#relationalOperator}.
	 * @param ctx the parse tree
	 */
	void enterRelationalOperator(TypeScriptParser.RelationalOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#relationalOperator}.
	 * @param ctx the parse tree
	 */
	void exitRelationalOperator(TypeScriptParser.RelationalOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperator(TypeScriptParser.UnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperator(TypeScriptParser.UnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#newExpression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpression(TypeScriptParser.NewExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#newExpression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpression(TypeScriptParser.NewExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#generatorBlock}.
	 * @param ctx the parse tree
	 */
	void enterGeneratorBlock(TypeScriptParser.GeneratorBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#generatorBlock}.
	 * @param ctx the parse tree
	 */
	void exitGeneratorBlock(TypeScriptParser.GeneratorBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#generatorDefinition}.
	 * @param ctx the parse tree
	 */
	void enterGeneratorDefinition(TypeScriptParser.GeneratorDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#generatorDefinition}.
	 * @param ctx the parse tree
	 */
	void exitGeneratorDefinition(TypeScriptParser.GeneratorDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#iteratorBlock}.
	 * @param ctx the parse tree
	 */
	void enterIteratorBlock(TypeScriptParser.IteratorBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#iteratorBlock}.
	 * @param ctx the parse tree
	 */
	void exitIteratorBlock(TypeScriptParser.IteratorBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#iteratorDefinition}.
	 * @param ctx the parse tree
	 */
	void enterIteratorDefinition(TypeScriptParser.IteratorDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#iteratorDefinition}.
	 * @param ctx the parse tree
	 */
	void exitIteratorDefinition(TypeScriptParser.IteratorDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(TypeScriptParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(TypeScriptParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#templateStringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterTemplateStringLiteral(TypeScriptParser.TemplateStringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#templateStringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitTemplateStringLiteral(TypeScriptParser.TemplateStringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#templateStringAtom}.
	 * @param ctx the parse tree
	 */
	void enterTemplateStringAtom(TypeScriptParser.TemplateStringAtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#templateStringAtom}.
	 * @param ctx the parse tree
	 */
	void exitTemplateStringAtom(TypeScriptParser.TemplateStringAtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#numericLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteral(TypeScriptParser.NumericLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#numericLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteral(TypeScriptParser.NumericLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#identifierOrKeyWord}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierOrKeyWord(TypeScriptParser.IdentifierOrKeyWordContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#identifierOrKeyWord}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierOrKeyWord(TypeScriptParser.IdentifierOrKeyWordContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#identifierName}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierName(TypeScriptParser.IdentifierNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#identifierName}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierName(TypeScriptParser.IdentifierNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#reservedWord}.
	 * @param ctx the parse tree
	 */
	void enterReservedWord(TypeScriptParser.ReservedWordContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#reservedWord}.
	 * @param ctx the parse tree
	 */
	void exitReservedWord(TypeScriptParser.ReservedWordContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#typeReferenceName}.
	 * @param ctx the parse tree
	 */
	void enterTypeReferenceName(TypeScriptParser.TypeReferenceNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#typeReferenceName}.
	 * @param ctx the parse tree
	 */
	void exitTypeReferenceName(TypeScriptParser.TypeReferenceNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#keyword}.
	 * @param ctx the parse tree
	 */
	void enterKeyword(TypeScriptParser.KeywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#keyword}.
	 * @param ctx the parse tree
	 */
	void exitKeyword(TypeScriptParser.KeywordContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#keywordAllowedInTypeReferences}.
	 * @param ctx the parse tree
	 */
	void enterKeywordAllowedInTypeReferences(TypeScriptParser.KeywordAllowedInTypeReferencesContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#keywordAllowedInTypeReferences}.
	 * @param ctx the parse tree
	 */
	void exitKeywordAllowedInTypeReferences(TypeScriptParser.KeywordAllowedInTypeReferencesContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#getter}.
	 * @param ctx the parse tree
	 */
	void enterGetter(TypeScriptParser.GetterContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#getter}.
	 * @param ctx the parse tree
	 */
	void exitGetter(TypeScriptParser.GetterContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#setter}.
	 * @param ctx the parse tree
	 */
	void enterSetter(TypeScriptParser.SetterContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#setter}.
	 * @param ctx the parse tree
	 */
	void exitSetter(TypeScriptParser.SetterContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeScriptParser#eos}.
	 * @param ctx the parse tree
	 */
	void enterEos(TypeScriptParser.EosContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeScriptParser#eos}.
	 * @param ctx the parse tree
	 */
	void exitEos(TypeScriptParser.EosContext ctx);
}