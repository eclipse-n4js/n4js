/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xsemantics;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.inject.Inject;
import com.google.inject.Provider;
import it.xsemantics.runtime.ErrorInformation;
import it.xsemantics.runtime.Result;
import it.xsemantics.runtime.RuleApplicationTrace;
import it.xsemantics.runtime.RuleEnvironment;
import it.xsemantics.runtime.RuleFailedException;
import it.xsemantics.runtime.XsemanticsRuntimeSystem;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.compileTime.CompileTimeValue;
import org.eclipse.n4js.misc.DestructureHelper;
import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.AdditiveOperator;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.ArrayPadding;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AssignmentOperator;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.BooleanLiteral;
import org.eclipse.n4js.n4JS.CastExpression;
import org.eclipse.n4js.n4JS.CatchVariable;
import org.eclipse.n4js.n4JS.CommaExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.GetterDeclaration;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.LocalArgumentsVariable;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.NewTarget;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.PromisifyExpression;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.RegularExpressionLiteral;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.RelationalOperator;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.SetterDeclaration;
import org.eclipse.n4js.n4JS.ShiftExpression;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.TaggedTemplateString;
import org.eclipse.n4js.n4JS.TemplateLiteral;
import org.eclipse.n4js.n4JS.TemplateSegment;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.ThisTarget;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.YieldExpression;
import org.eclipse.n4js.postprocessing.ASTMetaInfoCacheHelper;
import org.eclipse.n4js.scoping.members.MemberScopingHelper;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.BaseTypeRef;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.StaticBaseTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.AnyType;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.NullType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TObjectPrototype;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.UndefinedType;
import org.eclipse.n4js.ts.types.VoidType;
import org.eclipse.n4js.ts.types.util.AllSuperTypeRefsCollector;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.ts.utils.TypeExtensions;
import org.eclipse.n4js.ts.utils.TypeHelper;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesbuilder.N4JSFunctionDefinitionTypesBuilder;
import org.eclipse.n4js.typesystem.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.StructuralTypingResult;
import org.eclipse.n4js.typesystem.TypeSystemErrorExtensions;
import org.eclipse.n4js.typesystem.TypeSystemHelper;
import org.eclipse.n4js.typesystem.UnsupportedExpressionTypeHelper;
import org.eclipse.n4js.typesystem.VersionResolver;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.PromisifyHelper;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * N4JS Type System.
 * 
 * <h3>Naming conventions</h3>
 * 
 * Rules and axioms are named using the following pattern:
 * <pre>
 * &lt;relationName>&lt;InputType(s)>&lt;Additional_description>
 * </pre>
 * If a rule has multiple input parameters, either only the first one is used (in case
 * the second is not that important), or all types are used (without any separator).
 * <p>
 * The rules/axioms are ordered by judgment, and then according to the ("major" or first) input type,
 * if possible the order is similar as the order used in the N4JS specification.
 * 
 * <h3>Changes to the Rule Environment</h3>
 * 
 * By convention, rules should <em>not</em> change the rule environment and can (and should) rely
 * on other rules not changing the rule environment in case of nested inference. Thus:
 * <ul>
 * <li>before modifying the rule environment a new, derived rule environment must be created,
 *     usually by using extension method #wrap(). For example:
 *     <pre>
 *     val G2 = G.wrap;
 *     G2.add("MY_KEY",someValue);
 *     G2.addSubstitutions(someTypeRef);
 *     </pre>
 * <li>when doing nested inference within a rule, no new rule environment should be created (because
 *     we assume it won't be changed by the nested rule calls)
 * </ul>
 * There are two ways of creating a new rule environment: create empty environment or wrap existing
 * rule environment. When passing the newly created environment on to a nested inference, it is important
 * to always derive, because otherwise recursion guards will be lost.
 * 
 * <h3>Bibliography</h3>
 * <dl>
 * <dt><a name="N4JS">[N4JS]</a></dt>
 * <dd>Pilgrim, Jens von et al.: N4JS Specification / NumberFour AG. Berlin, September 2013.
 * 			Specification. <a href="https://github.com/NumberFour/specs/">https://github.com/NumberFour/specs/</a></dd>
 * </dl>
 */
@SuppressWarnings("all")
public class InternalTypeSystem extends XsemanticsRuntimeSystem {
  public final static String TYPETYPE = "org.eclipse.n4js.xsemantics.TypeType";
  
  public final static String TYPETENUMLITERAL = "org.eclipse.n4js.xsemantics.TypeTEnumLiteral";
  
  public final static String TYPETFIELD = "org.eclipse.n4js.xsemantics.TypeTField";
  
  public final static String TYPETGETTER = "org.eclipse.n4js.xsemantics.TypeTGetter";
  
  public final static String TYPETSETTER = "org.eclipse.n4js.xsemantics.TypeTSetter";
  
  public final static String TYPETVARIABLE = "org.eclipse.n4js.xsemantics.TypeTVariable";
  
  public final static String TYPETYPEDEFININGELEMENT = "org.eclipse.n4js.xsemantics.TypeTypeDefiningElement";
  
  public final static String TYPEOBJECTLITERAL = "org.eclipse.n4js.xsemantics.TypeObjectLiteral";
  
  public final static String TYPETHISKEYWORD = "org.eclipse.n4js.xsemantics.TypeThisKeyword";
  
  public final static String TYPESUPERLITERAL = "org.eclipse.n4js.xsemantics.TypeSuperLiteral";
  
  public final static String TYPEIDENTIFIERREF = "org.eclipse.n4js.xsemantics.TypeIdentifierRef";
  
  public final static String TYPENULLLITERAL = "org.eclipse.n4js.xsemantics.TypeNullLiteral";
  
  public final static String TYPEBOOLEANLITERAL = "org.eclipse.n4js.xsemantics.TypeBooleanLiteral";
  
  public final static String TYPENUMERICLITERAL = "org.eclipse.n4js.xsemantics.TypeNumericLiteral";
  
  public final static String TYPESTRINGLITERAL = "org.eclipse.n4js.xsemantics.TypeStringLiteral";
  
  public final static String TYPEREGEXPLITERAL = "org.eclipse.n4js.xsemantics.TypeRegExpLiteral";
  
  public final static String TYPETAGGEDTEMPLATESTRING = "org.eclipse.n4js.xsemantics.TypeTaggedTemplateString";
  
  public final static String TYPETEMPLATELITERAL = "org.eclipse.n4js.xsemantics.TypeTemplateLiteral";
  
  public final static String TYPETEMPLATESEGMENT = "org.eclipse.n4js.xsemantics.TypeTemplateSegment";
  
  public final static String TYPEN4ENUMLITERAL = "org.eclipse.n4js.xsemantics.TypeN4EnumLiteral";
  
  public final static String TYPEARRAYLITERAL = "org.eclipse.n4js.xsemantics.TypeArrayLiteral";
  
  public final static String TYPEARRAYPADDING = "org.eclipse.n4js.xsemantics.TypeArrayPadding";
  
  public final static String TYPEARRAYELEMENT = "org.eclipse.n4js.xsemantics.TypeArrayElement";
  
  public final static String TYPEPROPERTYNAMEVALUEPAIR = "org.eclipse.n4js.xsemantics.TypePropertyNameValuePair";
  
  public final static String TYPEN4FIELDDECLARATION = "org.eclipse.n4js.xsemantics.TypeN4FieldDeclaration";
  
  public final static String TYPEGETTERDECLARATION = "org.eclipse.n4js.xsemantics.TypeGetterDeclaration";
  
  public final static String TYPESETTERDECLARATION = "org.eclipse.n4js.xsemantics.TypeSetterDeclaration";
  
  public final static String TYPEPARENEXPRESSION = "org.eclipse.n4js.xsemantics.TypeParenExpression";
  
  public final static String TYPEYIELDEXPRESSION = "org.eclipse.n4js.xsemantics.TypeYieldExpression";
  
  public final static String TYPEAWAITEXPRESSION = "org.eclipse.n4js.xsemantics.TypeAwaitExpression";
  
  public final static String TYPEPROMISIFYEXPRESSION = "org.eclipse.n4js.xsemantics.TypePromisifyExpression";
  
  public final static String TYPEINDEXEDACCESSEXPRESSION = "org.eclipse.n4js.xsemantics.TypeIndexedAccessExpression";
  
  public final static String TYPEPROPERTYACCESSEXPRESSION = "org.eclipse.n4js.xsemantics.TypePropertyAccessExpression";
  
  public final static String TYPENEWEXPRESSION = "org.eclipse.n4js.xsemantics.TypeNewExpression";
  
  public final static String TYPENEWTARGET = "org.eclipse.n4js.xsemantics.TypeNewTarget";
  
  public final static String TYPECALLEXPRESSION = "org.eclipse.n4js.xsemantics.TypeCallExpression";
  
  public final static String TYPEARGUMENT = "org.eclipse.n4js.xsemantics.TypeArgument";
  
  public final static String TYPEPOSTFIXEXPRESSION = "org.eclipse.n4js.xsemantics.TypePostfixExpression";
  
  public final static String TYPEUNARYEXPRESSION = "org.eclipse.n4js.xsemantics.TypeUnaryExpression";
  
  public final static String TYPEMULTIPLICATIVEEXPRESSION = "org.eclipse.n4js.xsemantics.TypeMultiplicativeExpression";
  
  public final static String TYPEADDITIVEEXPRESSION = "org.eclipse.n4js.xsemantics.TypeAdditiveExpression";
  
  public final static String TYPESHIFTEXPRESSION = "org.eclipse.n4js.xsemantics.TypeShiftExpression";
  
  public final static String TYPERELATIONALEXPRESSION = "org.eclipse.n4js.xsemantics.TypeRelationalExpression";
  
  public final static String TYPEEQUALITYEXPRESSION = "org.eclipse.n4js.xsemantics.TypeEqualityExpression";
  
  public final static String TYPEBINARYBITWISEEXPRESSION = "org.eclipse.n4js.xsemantics.TypeBinaryBitwiseExpression";
  
  public final static String TYPEBINARYLOGICALEXPRESSION = "org.eclipse.n4js.xsemantics.TypeBinaryLogicalExpression";
  
  public final static String TYPECONDITIONALEXPRESSION = "org.eclipse.n4js.xsemantics.TypeConditionalExpression";
  
  public final static String TYPEASSIGNMENTEXPRESSION = "org.eclipse.n4js.xsemantics.TypeAssignmentExpression";
  
  public final static String TYPECOMMAEXPRESSION = "org.eclipse.n4js.xsemantics.TypeCommaExpression";
  
  public final static String TYPECASTEXPRESSION = "org.eclipse.n4js.xsemantics.TypeCastExpression";
  
  public final static String TYPEN4CLASSEXPRESSION = "org.eclipse.n4js.xsemantics.TypeN4ClassExpression";
  
  public final static String TYPEFUNCTIONEXPRESSION = "org.eclipse.n4js.xsemantics.TypeFunctionExpression";
  
  public final static String TYPEUNSUPPORTEDEXPRESSION = "org.eclipse.n4js.xsemantics.TypeUnsupportedExpression";
  
  public final static String TYPEVARIABLEDECLARATION = "org.eclipse.n4js.xsemantics.TypeVariableDeclaration";
  
  public final static String TYPEFORMALPARAMETER = "org.eclipse.n4js.xsemantics.TypeFormalParameter";
  
  public final static String TYPECATCHVARIABLE = "org.eclipse.n4js.xsemantics.TypeCatchVariable";
  
  public final static String TYPELOCALARGUMENTSVARIABLE = "org.eclipse.n4js.xsemantics.TypeLocalArgumentsVariable";
  
  public final static String TYPEMODULENAMESPACE = "org.eclipse.n4js.xsemantics.TypeModuleNamespace";
  
  public final static String SUBTYPETYPEARGUMENT = "org.eclipse.n4js.xsemantics.SubtypeTypeArgument";
  
  public final static String SUBTYPETYPEREF = "org.eclipse.n4js.xsemantics.SubtypeTypeRef";
  
  public final static String SUBTYPEUNKNOWNTYPEREF_LEFT = "org.eclipse.n4js.xsemantics.SubtypeUnknownTypeRef_Left";
  
  public final static String SUBTYPEUNKNOWNTYPEREF_RIGHT = "org.eclipse.n4js.xsemantics.SubtypeUnknownTypeRef_Right";
  
  public final static String SUBTYPEPARAMETERIZEDTYPEREF = "org.eclipse.n4js.xsemantics.SubtypeParameterizedTypeRef";
  
  public final static String SUBTYPEUNION_LEFT = "org.eclipse.n4js.xsemantics.SubtypeUnion_Left";
  
  public final static String SUBTYPEUNION_RIGHT = "org.eclipse.n4js.xsemantics.SubtypeUnion_Right";
  
  public final static String SUBTYPEINTERSECTION_LEFTRIGHT = "org.eclipse.n4js.xsemantics.SubtypeIntersection_LeftRight";
  
  public final static String SUBTYPEINTERSECTION_LEFT = "org.eclipse.n4js.xsemantics.SubtypeIntersection_Left";
  
  public final static String SUBTYPEINTERSECTION_RIGHT = "org.eclipse.n4js.xsemantics.SubtypeIntersection_Right";
  
  public final static String SUBTYPEBOUNDTHISTYPEREF = "org.eclipse.n4js.xsemantics.SubtypeBoundThisTypeRef";
  
  public final static String SUBTYPEBOUNDTHISTYPEREFTYPEREF = "org.eclipse.n4js.xsemantics.SubtypeBoundThisTypeRefTypeRef";
  
  public final static String SUBTYPETYPEREFBOUNDTHISTYPEREF = "org.eclipse.n4js.xsemantics.SubtypeTypeRefBoundThisTypeRef";
  
  public final static String SUBTYPEEXISTENTIALTYPEREF_RIGHT = "org.eclipse.n4js.xsemantics.SubtypeExistentialTypeRef_Right";
  
  public final static String SUBTYPEEXISTENTIALTYPEREF_LEFT = "org.eclipse.n4js.xsemantics.SubtypeExistentialTypeRef_Left";
  
  public final static String SUBTYPETYPETYPEREF = "org.eclipse.n4js.xsemantics.SubtypeTypeTypeRef";
  
  public final static String SUBTYPETYPETYPEREF__PARAMETERIZEDTYPEREF = "org.eclipse.n4js.xsemantics.SubtypeTypeTypeRef__ParameterizedTypeRef";
  
  public final static String SUBTYPEFUNCTIONTYPEEXPRORREF = "org.eclipse.n4js.xsemantics.SubtypeFunctionTypeExprOrRef";
  
  public final static String SUBTYPEFUNCTIONTYPEREF = "org.eclipse.n4js.xsemantics.SubtypeFunctionTypeRef";
  
  public final static String SUBTYPEFUNCTIONTYPEEXPRESSION_PARAMETERIZEDTYPEREF = "org.eclipse.n4js.xsemantics.SubtypeFunctionTypeExpression_ParameterizedTypeRef";
  
  public final static String SUPERTYPETYPEREF = "org.eclipse.n4js.xsemantics.SupertypeTypeRef";
  
  public final static String EQUALTYPETYPEREF = "org.eclipse.n4js.xsemantics.EqualTypeTypeRef";
  
  public final static String EXPECTEDTYPEINFORMALPARAMETER = "org.eclipse.n4js.xsemantics.ExpectedTypeInFormalParameter";
  
  public final static String EXPECTEDTYPEOFARGUMENT = "org.eclipse.n4js.xsemantics.ExpectedTypeOfArgument";
  
  public final static String EXPECTEDTYPEINPOSTFIXEXPRESSION = "org.eclipse.n4js.xsemantics.ExpectedTypeInPostfixExpression";
  
  public final static String EXPECTEDTYPEINUNARYEXPRESSION = "org.eclipse.n4js.xsemantics.ExpectedTypeInUnaryExpression";
  
  public final static String EXPECTEDTYPEINMULTIPLICATIVEEXPRESSION = "org.eclipse.n4js.xsemantics.ExpectedTypeInMultiplicativeExpression";
  
  public final static String EXPECTEDTYPEINADDITIVEEXPRESSION = "org.eclipse.n4js.xsemantics.ExpectedTypeInAdditiveExpression";
  
  public final static String EXPECTEDTYPEINSHIFTEXPRESSION = "org.eclipse.n4js.xsemantics.ExpectedTypeInShiftExpression";
  
  public final static String EXPECTEDTYPEINRELATIONALEXPRESSION = "org.eclipse.n4js.xsemantics.ExpectedTypeInRelationalExpression";
  
  public final static String EXPECTEDTYPEINEQUALITYEXPRESSION = "org.eclipse.n4js.xsemantics.ExpectedTypeInEqualityExpression";
  
  public final static String EXPECTEDTYPEINBINARYBITWISEEXPRESSION = "org.eclipse.n4js.xsemantics.ExpectedTypeInBinaryBitwiseExpression";
  
  public final static String EXPECTEDTYPEINBINARYLOGICALEXPRESSION = "org.eclipse.n4js.xsemantics.ExpectedTypeInBinaryLogicalExpression";
  
  public final static String EXPECTEDTYPEINASSIGNMENTEXPRESSION = "org.eclipse.n4js.xsemantics.ExpectedTypeInAssignmentExpression";
  
  public final static String EXPECTEDTYPEOFRIGHTSIDEINVARIABLEDECLARATION = "org.eclipse.n4js.xsemantics.ExpectedTypeOfRightSideInVariableDeclaration";
  
  public final static String EXPECTEDTYPEOFRIGHTSIDEINVARIABLEBINDING = "org.eclipse.n4js.xsemantics.ExpectedTypeOfRightSideInVariableBinding";
  
  public final static String EXPECTEDTYPEOFRIGHTSIDEINN4FIELDDECLARATION = "org.eclipse.n4js.xsemantics.ExpectedTypeOfRightSideInN4FieldDeclaration";
  
  public final static String EXPECTEDTYPEOFRIGHTSIDEINPROPERTYNAMEVALUEPAIR = "org.eclipse.n4js.xsemantics.ExpectedTypeOfRightSideInPropertyNameValuePair";
  
  public final static String EXPECTEDTYPEINRETURNSTATEMENT = "org.eclipse.n4js.xsemantics.ExpectedTypeInReturnStatement";
  
  public final static String EXPECTEDTYPEINYIELDSTATEMENT = "org.eclipse.n4js.xsemantics.ExpectedTypeInYieldStatement";
  
  public final static String EXPECTEDTYPEINEXPRESSIONSTATEMENT = "org.eclipse.n4js.xsemantics.ExpectedTypeInExpressionStatement";
  
  public final static String EXPECTEDTYPEINFORSTATEMENT = "org.eclipse.n4js.xsemantics.ExpectedTypeInForStatement";
  
  public final static String EXPECTEDTYPEINAWAITEXPRESSION = "org.eclipse.n4js.xsemantics.ExpectedTypeInAwaitExpression";
  
  public final static String EXPECTEDTYPEINUNSUPPORTEDCONTAINER = "org.eclipse.n4js.xsemantics.ExpectedTypeInUnsupportedContainer";
  
  public final static String UPPERBOUNDTYPEREF = "org.eclipse.n4js.xsemantics.UpperBoundTypeRef";
  
  public final static String UPPERBOUNDWILDCARDTYPEREF = "org.eclipse.n4js.xsemantics.UpperBoundWildcardTypeRef";
  
  public final static String UPPERBOUNDEXISTENTIALTYPEREF = "org.eclipse.n4js.xsemantics.UpperBoundExistentialTypeRef";
  
  public final static String UPPERBOUNDUNIONTYPEEXPRESSION = "org.eclipse.n4js.xsemantics.UpperBoundUnionTypeExpression";
  
  public final static String UPPERBOUNDINTERSECTIONTYPEEXPRESSION = "org.eclipse.n4js.xsemantics.UpperBoundIntersectionTypeExpression";
  
  public final static String UPPERBOUNDPARAMETERIZEDTYPEREF = "org.eclipse.n4js.xsemantics.UpperBoundParameterizedTypeRef";
  
  public final static String UPPERBOUNDFUNCTIONTYPEREF = "org.eclipse.n4js.xsemantics.UpperBoundFunctionTypeRef";
  
  public final static String UPPERBOUNDFUNCTIONTYPEEXPRORREF = "org.eclipse.n4js.xsemantics.UpperBoundFunctionTypeExprOrRef";
  
  public final static String UPPERBOUNDTHISTYPEREF = "org.eclipse.n4js.xsemantics.UpperBoundThisTypeRef";
  
  public final static String UPPERBOUNDTYPETYPEREF = "org.eclipse.n4js.xsemantics.UpperBoundTypeTypeRef";
  
  public final static String LOWERBOUNDTYPEREF = "org.eclipse.n4js.xsemantics.LowerBoundTypeRef";
  
  public final static String LOWERBOUNDWILDCARD = "org.eclipse.n4js.xsemantics.LowerBoundWildcard";
  
  public final static String LOWERBOUNDEXISTENTIALTYPEREF = "org.eclipse.n4js.xsemantics.LowerBoundExistentialTypeRef";
  
  public final static String LOWERBOUNDUNIONTYPEEXPRESSION = "org.eclipse.n4js.xsemantics.LowerBoundUnionTypeExpression";
  
  public final static String LOWERBOUNDINTERSECTIONTYPEEXPRESSION = "org.eclipse.n4js.xsemantics.LowerBoundIntersectionTypeExpression";
  
  public final static String LOWERBOUNDPARAMETERIZEDTYPEREF = "org.eclipse.n4js.xsemantics.LowerBoundParameterizedTypeRef";
  
  public final static String LOWERBOUNDFUNCTIONTYPEREF = "org.eclipse.n4js.xsemantics.LowerBoundFunctionTypeRef";
  
  public final static String LOWERBOUNDFUNCTIONTYPEEXPRORREF = "org.eclipse.n4js.xsemantics.LowerBoundFunctionTypeExprOrRef";
  
  public final static String LOWERBOUNDTHISTYPEREF = "org.eclipse.n4js.xsemantics.LowerBoundThisTypeRef";
  
  public final static String SUBSTTYPEVARIABLESBASECASE = "org.eclipse.n4js.xsemantics.SubstTypeVariablesBaseCase";
  
  public final static String SUBSTTYPEVARIABLESWILDCARD = "org.eclipse.n4js.xsemantics.SubstTypeVariablesWildcard";
  
  public final static String SUBSTTYPEVARIABLESTHISTYPEREF = "org.eclipse.n4js.xsemantics.SubstTypeVariablesThisTypeRef";
  
  public final static String SUBSTTYPEVARIABLESTHISTYPEREFSTRUCTURAL = "org.eclipse.n4js.xsemantics.SubstTypeVariablesThisTypeRefStructural";
  
  public final static String SUBSTTYPEVARIABLESINFUNCTIONTYPEREF = "org.eclipse.n4js.xsemantics.SubstTypeVariablesInFunctionTypeRef";
  
  public final static String SUBSTTYPEVARIABLESINFUNCTIONTYPEEXPRORREF = "org.eclipse.n4js.xsemantics.SubstTypeVariablesInFunctionTypeExprOrRef";
  
  public final static String SUBSTTYPEVARIABLESINCOMPOSEDTYPEREF = "org.eclipse.n4js.xsemantics.SubstTypeVariablesInComposedTypeRef";
  
  public final static String SUBSTTYPEVARIABLESINTYPETYPEREF = "org.eclipse.n4js.xsemantics.SubstTypeVariablesInTypeTypeRef";
  
  public final static String SUBSTTYPEVARIABLESINPARAMETERIZEDTYPEREF = "org.eclipse.n4js.xsemantics.SubstTypeVariablesInParameterizedTypeRef";
  
  public final static String THISTYPEREFPARAMETERIZEDTYPEREF = "org.eclipse.n4js.xsemantics.ThisTypeRefParameterizedTypeRef";
  
  public final static String THISTYPEREFEOBJECT = "org.eclipse.n4js.xsemantics.ThisTypeRefEObject";
  
  @Inject
  private TypeSystemHelper typeSystemHelper;
  
  @Inject
  private N4JSFunctionDefinitionTypesBuilder functionDefinitionTypesBuilder;
  
  @Inject
  private TypeHelper typeHelper;
  
  @Inject
  private ContainerTypesHelper containerTypesHelper;
  
  @Inject
  private DestructureHelper destructureHelper;
  
  @Inject
  private MemberScopingHelper memberScopingHelper;
  
  @Inject
  private PromisifyHelper promisifyHelper;
  
  @Inject
  private JavaScriptVariantHelper jsVariantHelper;
  
  @Inject
  private VersionResolver versionResolver;
  
  @Inject
  private UnsupportedExpressionTypeHelper expressionTypeHelper;
  
  @Inject
  private ASTMetaInfoCacheHelper astMetaInfoCacheHelper;
  
  @Inject
  private IQualifiedNameConverter qualifiedNameConverter;
  
  private PolymorphicDispatcher<Result<TypeRef>> typeDispatcher;
  
  private PolymorphicDispatcher<Result<Boolean>> subtypeDispatcher;
  
  private PolymorphicDispatcher<Result<Boolean>> supertypeDispatcher;
  
  private PolymorphicDispatcher<Result<Boolean>> equaltypeDispatcher;
  
  private PolymorphicDispatcher<Result<TypeRef>> expectedTypeInDispatcher;
  
  private PolymorphicDispatcher<Result<TypeRef>> upperBoundDispatcher;
  
  private PolymorphicDispatcher<Result<TypeRef>> lowerBoundDispatcher;
  
  private PolymorphicDispatcher<Result<TypeArgument>> substTypeVariablesDispatcher;
  
  private PolymorphicDispatcher<Result<TypeRef>> thisTypeRefDispatcher;
  
  public InternalTypeSystem() {
    init();
  }
  
  public void init() {
    typeDispatcher = buildPolymorphicDispatcher1(
    	"typeImpl", 3, "|-", ":");
    subtypeDispatcher = buildPolymorphicDispatcher1(
    	"subtypeImpl", 4, "|-", "<:");
    supertypeDispatcher = buildPolymorphicDispatcher1(
    	"supertypeImpl", 4, "|-", ":>");
    equaltypeDispatcher = buildPolymorphicDispatcher1(
    	"equaltypeImpl", 4, "|-", "~~");
    expectedTypeInDispatcher = buildPolymorphicDispatcher1(
    	"expectedTypeInImpl", 4, "|-", "|>", ":");
    upperBoundDispatcher = buildPolymorphicDispatcher1(
    	"upperBoundImpl", 3, "|~", "/\\");
    lowerBoundDispatcher = buildPolymorphicDispatcher1(
    	"lowerBoundImpl", 3, "|~", "\\/");
    substTypeVariablesDispatcher = buildPolymorphicDispatcher1(
    	"substTypeVariablesImpl", 3, "|-", "~>");
    thisTypeRefDispatcher = buildPolymorphicDispatcher1(
    	"thisTypeRefImpl", 3, "|~", "~>");
  }
  
  public TypeSystemHelper getTypeSystemHelper() {
    return this.typeSystemHelper;
  }
  
  public void setTypeSystemHelper(final TypeSystemHelper typeSystemHelper) {
    this.typeSystemHelper = typeSystemHelper;
  }
  
  public N4JSFunctionDefinitionTypesBuilder getFunctionDefinitionTypesBuilder() {
    return this.functionDefinitionTypesBuilder;
  }
  
  public void setFunctionDefinitionTypesBuilder(final N4JSFunctionDefinitionTypesBuilder functionDefinitionTypesBuilder) {
    this.functionDefinitionTypesBuilder = functionDefinitionTypesBuilder;
  }
  
  public TypeHelper getTypeHelper() {
    return this.typeHelper;
  }
  
  public void setTypeHelper(final TypeHelper typeHelper) {
    this.typeHelper = typeHelper;
  }
  
  public ContainerTypesHelper getContainerTypesHelper() {
    return this.containerTypesHelper;
  }
  
  public void setContainerTypesHelper(final ContainerTypesHelper containerTypesHelper) {
    this.containerTypesHelper = containerTypesHelper;
  }
  
  public DestructureHelper getDestructureHelper() {
    return this.destructureHelper;
  }
  
  public void setDestructureHelper(final DestructureHelper destructureHelper) {
    this.destructureHelper = destructureHelper;
  }
  
  public MemberScopingHelper getMemberScopingHelper() {
    return this.memberScopingHelper;
  }
  
  public void setMemberScopingHelper(final MemberScopingHelper memberScopingHelper) {
    this.memberScopingHelper = memberScopingHelper;
  }
  
  public PromisifyHelper getPromisifyHelper() {
    return this.promisifyHelper;
  }
  
  public void setPromisifyHelper(final PromisifyHelper promisifyHelper) {
    this.promisifyHelper = promisifyHelper;
  }
  
  public JavaScriptVariantHelper getJsVariantHelper() {
    return this.jsVariantHelper;
  }
  
  public void setJsVariantHelper(final JavaScriptVariantHelper jsVariantHelper) {
    this.jsVariantHelper = jsVariantHelper;
  }
  
  public VersionResolver getVersionResolver() {
    return this.versionResolver;
  }
  
  public void setVersionResolver(final VersionResolver versionResolver) {
    this.versionResolver = versionResolver;
  }
  
  public UnsupportedExpressionTypeHelper getExpressionTypeHelper() {
    return this.expressionTypeHelper;
  }
  
  public void setExpressionTypeHelper(final UnsupportedExpressionTypeHelper expressionTypeHelper) {
    this.expressionTypeHelper = expressionTypeHelper;
  }
  
  public ASTMetaInfoCacheHelper getAstMetaInfoCacheHelper() {
    return this.astMetaInfoCacheHelper;
  }
  
  public void setAstMetaInfoCacheHelper(final ASTMetaInfoCacheHelper astMetaInfoCacheHelper) {
    this.astMetaInfoCacheHelper = astMetaInfoCacheHelper;
  }
  
  public IQualifiedNameConverter getQualifiedNameConverter() {
    return this.qualifiedNameConverter;
  }
  
  public void setQualifiedNameConverter(final IQualifiedNameConverter qualifiedNameConverter) {
    this.qualifiedNameConverter = qualifiedNameConverter;
  }
  
  public Result<TypeRef> type(final TypableElement element) {
    return type(new RuleEnvironment(), null, element);
  }
  
  public Result<TypeRef> type(final RuleEnvironment _environment_, final TypableElement element) {
    return type(_environment_, null, element);
  }
  
  public Result<TypeRef> type(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypableElement element) {
    try {
    	return typeInternal(_environment_, _trace_, element);
    } catch (Exception _e_type) {
    	return resultForFailure(_e_type);
    }
  }
  
  public Result<Boolean> subtype(final TypeArgument left, final TypeArgument right) {
    return subtype(new RuleEnvironment(), null, left, right);
  }
  
  public Result<Boolean> subtype(final RuleEnvironment _environment_, final TypeArgument left, final TypeArgument right) {
    return subtype(_environment_, null, left, right);
  }
  
  public Result<Boolean> subtype(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument left, final TypeArgument right) {
    try {
    	return subtypeInternal(_environment_, _trace_, left, right);
    } catch (Exception _e_subtype) {
    	return resultForFailure(_e_subtype);
    }
  }
  
  public Boolean subtypeSucceeded(final TypeArgument left, final TypeArgument right) {
    return subtypeSucceeded(new RuleEnvironment(), null, left, right);
  }
  
  public Boolean subtypeSucceeded(final RuleEnvironment _environment_, final TypeArgument left, final TypeArgument right) {
    return subtypeSucceeded(_environment_, null, left, right);
  }
  
  public Boolean subtypeSucceeded(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument left, final TypeArgument right) {
    try {
    	subtypeInternal(_environment_, _trace_, left, right);
    	return true;
    } catch (Exception _e_subtype) {
    	return false;
    }
  }
  
  public Result<Boolean> supertype(final TypeArgument left, final TypeArgument right) {
    return supertype(new RuleEnvironment(), null, left, right);
  }
  
  public Result<Boolean> supertype(final RuleEnvironment _environment_, final TypeArgument left, final TypeArgument right) {
    return supertype(_environment_, null, left, right);
  }
  
  public Result<Boolean> supertype(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument left, final TypeArgument right) {
    try {
    	return supertypeInternal(_environment_, _trace_, left, right);
    } catch (Exception _e_supertype) {
    	return resultForFailure(_e_supertype);
    }
  }
  
  public Boolean supertypeSucceeded(final TypeArgument left, final TypeArgument right) {
    return supertypeSucceeded(new RuleEnvironment(), null, left, right);
  }
  
  public Boolean supertypeSucceeded(final RuleEnvironment _environment_, final TypeArgument left, final TypeArgument right) {
    return supertypeSucceeded(_environment_, null, left, right);
  }
  
  public Boolean supertypeSucceeded(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument left, final TypeArgument right) {
    try {
    	supertypeInternal(_environment_, _trace_, left, right);
    	return true;
    } catch (Exception _e_supertype) {
    	return false;
    }
  }
  
  public Result<Boolean> equaltype(final TypeArgument left, final TypeArgument right) {
    return equaltype(new RuleEnvironment(), null, left, right);
  }
  
  public Result<Boolean> equaltype(final RuleEnvironment _environment_, final TypeArgument left, final TypeArgument right) {
    return equaltype(_environment_, null, left, right);
  }
  
  public Result<Boolean> equaltype(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument left, final TypeArgument right) {
    try {
    	return equaltypeInternal(_environment_, _trace_, left, right);
    } catch (Exception _e_equaltype) {
    	return resultForFailure(_e_equaltype);
    }
  }
  
  public Boolean equaltypeSucceeded(final TypeArgument left, final TypeArgument right) {
    return equaltypeSucceeded(new RuleEnvironment(), null, left, right);
  }
  
  public Boolean equaltypeSucceeded(final RuleEnvironment _environment_, final TypeArgument left, final TypeArgument right) {
    return equaltypeSucceeded(_environment_, null, left, right);
  }
  
  public Boolean equaltypeSucceeded(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument left, final TypeArgument right) {
    try {
    	equaltypeInternal(_environment_, _trace_, left, right);
    	return true;
    } catch (Exception _e_equaltype) {
    	return false;
    }
  }
  
  public Result<TypeRef> expectedTypeIn(final EObject container, final Expression expression) {
    return expectedTypeIn(new RuleEnvironment(), null, container, expression);
  }
  
  public Result<TypeRef> expectedTypeIn(final RuleEnvironment _environment_, final EObject container, final Expression expression) {
    return expectedTypeIn(_environment_, null, container, expression);
  }
  
  public Result<TypeRef> expectedTypeIn(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final EObject container, final Expression expression) {
    try {
    	return expectedTypeInInternal(_environment_, _trace_, container, expression);
    } catch (Exception _e_expectedTypeIn) {
    	return resultForFailure(_e_expectedTypeIn);
    }
  }
  
  public Result<TypeRef> upperBound(final TypeArgument typeArgument) {
    return upperBound(new RuleEnvironment(), null, typeArgument);
  }
  
  public Result<TypeRef> upperBound(final RuleEnvironment _environment_, final TypeArgument typeArgument) {
    return upperBound(_environment_, null, typeArgument);
  }
  
  public Result<TypeRef> upperBound(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument typeArgument) {
    try {
    	return upperBoundInternal(_environment_, _trace_, typeArgument);
    } catch (Exception _e_upperBound) {
    	return resultForFailure(_e_upperBound);
    }
  }
  
  public Result<TypeRef> lowerBound(final TypeArgument typeArgument) {
    return lowerBound(new RuleEnvironment(), null, typeArgument);
  }
  
  public Result<TypeRef> lowerBound(final RuleEnvironment _environment_, final TypeArgument typeArgument) {
    return lowerBound(_environment_, null, typeArgument);
  }
  
  public Result<TypeRef> lowerBound(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument typeArgument) {
    try {
    	return lowerBoundInternal(_environment_, _trace_, typeArgument);
    } catch (Exception _e_lowerBound) {
    	return resultForFailure(_e_lowerBound);
    }
  }
  
  public Result<TypeArgument> substTypeVariables(final TypeArgument typeArg) {
    return substTypeVariables(new RuleEnvironment(), null, typeArg);
  }
  
  public Result<TypeArgument> substTypeVariables(final RuleEnvironment _environment_, final TypeArgument typeArg) {
    return substTypeVariables(_environment_, null, typeArg);
  }
  
  public Result<TypeArgument> substTypeVariables(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument typeArg) {
    try {
    	return substTypeVariablesInternal(_environment_, _trace_, typeArg);
    } catch (Exception _e_substTypeVariables) {
    	return resultForFailure(_e_substTypeVariables);
    }
  }
  
  public Result<TypeRef> thisTypeRef(final EObject location) {
    return thisTypeRef(new RuleEnvironment(), null, location);
  }
  
  public Result<TypeRef> thisTypeRef(final RuleEnvironment _environment_, final EObject location) {
    return thisTypeRef(_environment_, null, location);
  }
  
  public Result<TypeRef> thisTypeRef(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final EObject location) {
    try {
    	return thisTypeRefInternal(_environment_, _trace_, location);
    } catch (Exception _e_thisTypeRef) {
    	return resultForFailure(_e_thisTypeRef);
    }
  }
  
  protected Result<TypeRef> typeInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypableElement element) {
    try {
    	checkParamsNotNull(element);
    	return typeDispatcher.invoke(_environment_, _trace_, element);
    } catch (Exception _e_type) {
    	sneakyThrowRuleFailedException(_e_type);
    	return null;
    }
  }
  
  protected void typeThrowException(final String _error, final String _issue, final Exception _ex, final TypableElement element, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    EClass _eClass = null;
    if (element!=null) {
      _eClass=element.eClass();
    }
    String _name = null;
    if (_eClass!=null) {
      _name=_eClass.getName();
    }
    String _plus = ("cannot type " + _name);
    String _plus_1 = (_plus + " ");
    String _stringRep = this.stringRep(element);
    String _plus_2 = (_plus_1 + _stringRep);
    String error = _plus_2;
    EObject source = element;
    throwRuleFailedException(error,
    	_issue, _ex, new ErrorInformation(source, null));
  }
  
  protected Result<Boolean> subtypeInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument left, final TypeArgument right) {
    try {
    	checkParamsNotNull(left, right);
    	return subtypeDispatcher.invoke(_environment_, _trace_, left, right);
    } catch (Exception _e_subtype) {
    	sneakyThrowRuleFailedException(_e_subtype);
    	return null;
    }
  }
  
  protected void subtypeThrowException(final String _error, final String _issue, final Exception _ex, final TypeArgument left, final TypeArgument right, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    String _stringRep = this.stringRep(left);
    String _plus = (_stringRep + " is not a subtype of ");
    String _stringRep_1 = this.stringRep(right);
    String _plus_1 = (_plus + _stringRep_1);
    String error = _plus_1;
    throwRuleFailedException(error,
    	_issue, _ex, new ErrorInformation(null, null));
  }
  
  protected Result<Boolean> supertypeInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument left, final TypeArgument right) {
    try {
    	checkParamsNotNull(left, right);
    	return supertypeDispatcher.invoke(_environment_, _trace_, left, right);
    } catch (Exception _e_supertype) {
    	sneakyThrowRuleFailedException(_e_supertype);
    	return null;
    }
  }
  
  protected void supertypeThrowException(final String _error, final String _issue, final Exception _ex, final TypeArgument left, final TypeArgument right, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    String _stringRep = this.stringRep(left);
    String _plus = (_stringRep + " is not a super type of ");
    String _stringRep_1 = this.stringRep(right);
    String _plus_1 = (_plus + _stringRep_1);
    String error = _plus_1;
    throwRuleFailedException(error,
    	_issue, _ex, new ErrorInformation(null, null));
  }
  
  protected Result<Boolean> equaltypeInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument left, final TypeArgument right) {
    try {
    	checkParamsNotNull(left, right);
    	return equaltypeDispatcher.invoke(_environment_, _trace_, left, right);
    } catch (Exception _e_equaltype) {
    	sneakyThrowRuleFailedException(_e_equaltype);
    	return null;
    }
  }
  
  protected void equaltypeThrowException(final String _error, final String _issue, final Exception _ex, final TypeArgument left, final TypeArgument right, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    String _stringRep = this.stringRep(left);
    String _plus = (_stringRep + " is not equal to ");
    String _stringRep_1 = this.stringRep(right);
    String _plus_1 = (_plus + _stringRep_1);
    String error = _plus_1;
    throwRuleFailedException(error,
    	_issue, _ex, new ErrorInformation(null, null));
  }
  
  protected Result<TypeRef> expectedTypeInInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final EObject container, final Expression expression) {
    try {
    	checkParamsNotNull(container, expression);
    	return expectedTypeInDispatcher.invoke(_environment_, _trace_, container, expression);
    } catch (Exception _e_expectedTypeIn) {
    	sneakyThrowRuleFailedException(_e_expectedTypeIn);
    	return null;
    }
  }
  
  protected void expectedTypeInThrowException(final String _error, final String _issue, final Exception _ex, final EObject container, final Expression expression, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected Result<TypeRef> upperBoundInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument typeArgument) {
    try {
    	checkParamsNotNull(typeArgument);
    	return upperBoundDispatcher.invoke(_environment_, _trace_, typeArgument);
    } catch (Exception _e_upperBound) {
    	sneakyThrowRuleFailedException(_e_upperBound);
    	return null;
    }
  }
  
  protected void upperBoundThrowException(final String _error, final String _issue, final Exception _ex, final TypeArgument typeArgument, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected Result<TypeRef> lowerBoundInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument typeArgument) {
    try {
    	checkParamsNotNull(typeArgument);
    	return lowerBoundDispatcher.invoke(_environment_, _trace_, typeArgument);
    } catch (Exception _e_lowerBound) {
    	sneakyThrowRuleFailedException(_e_lowerBound);
    	return null;
    }
  }
  
  protected void lowerBoundThrowException(final String _error, final String _issue, final Exception _ex, final TypeArgument typeArgument, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected Result<TypeArgument> substTypeVariablesInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final TypeArgument typeArg) {
    try {
    	checkParamsNotNull(typeArg);
    	return substTypeVariablesDispatcher.invoke(_environment_, _trace_, typeArg);
    } catch (Exception _e_substTypeVariables) {
    	sneakyThrowRuleFailedException(_e_substTypeVariables);
    	return null;
    }
  }
  
  protected void substTypeVariablesThrowException(final String _error, final String _issue, final Exception _ex, final TypeArgument typeArg, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected Result<TypeRef> thisTypeRefInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final EObject location) {
    try {
    	checkParamsNotNull(location);
    	return thisTypeRefDispatcher.invoke(_environment_, _trace_, location);
    } catch (Exception _e_thisTypeRef) {
    	sneakyThrowRuleFailedException(_e_thisTypeRef);
    	return null;
    }
  }
  
  protected void thisTypeRefThrowException(final String _error, final String _issue, final Exception _ex, final EObject location, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Type type) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeType(G, _subtrace_, type);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeType") + stringRepForEnv(G) + " |- " + stringRep(type) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeType) {
    	typeThrowException(ruleName("typeType") + stringRepForEnv(G) + " |- " + stringRep(type) + " : " + "TypeRef",
    		TYPETYPE,
    		e_applyRuleTypeType, type, new ErrorInformation[] {new ErrorInformation(type)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeType(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Type type) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeType_1(G, type));
  }
  
  private TypeRef _applyRuleTypeType_1(final RuleEnvironment G, final Type type) throws RuleFailedException {
    TypeRef _wrapTypeInTypeRef = TypeUtils.wrapTypeInTypeRef(type);
    return _wrapTypeInTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TEnumLiteral enumLiteral) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeTEnumLiteral(G, _subtrace_, enumLiteral);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeTEnumLiteral") + stringRepForEnv(G) + " |- " + stringRep(enumLiteral) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeTEnumLiteral) {
    	typeThrowException(ruleName("typeTEnumLiteral") + stringRepForEnv(G) + " |- " + stringRep(enumLiteral) + " : " + "TypeRef",
    		TYPETENUMLITERAL,
    		e_applyRuleTypeTEnumLiteral, enumLiteral, new ErrorInformation[] {new ErrorInformation(enumLiteral)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeTEnumLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TEnumLiteral enumLiteral) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeTEnumLiteral_1(G, enumLiteral));
  }
  
  private TypeRef _applyRuleTypeTEnumLiteral_1(final RuleEnvironment G, final TEnumLiteral enumLiteral) throws RuleFailedException {
    EObject _eContainer = enumLiteral.eContainer();
    TypeRef _ref = TypeExtensions.ref(((TEnum) _eContainer));
    return _ref;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TField tfield) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeTField(G, _subtrace_, tfield);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeTField") + stringRepForEnv(G) + " |- " + stringRep(tfield) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeTField) {
    	typeThrowException(ruleName("typeTField") + stringRepForEnv(G) + " |- " + stringRep(tfield) + " : " + "TypeRef",
    		TYPETFIELD,
    		e_applyRuleTypeTField, tfield, new ErrorInformation[] {new ErrorInformation(tfield)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeTField(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TField tfield) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = tfield.getTypeRef();
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TGetter tgetter) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeTGetter(G, _subtrace_, tgetter);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeTGetter") + stringRepForEnv(G) + " |- " + stringRep(tgetter) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeTGetter) {
    	typeThrowException(ruleName("typeTGetter") + stringRepForEnv(G) + " |- " + stringRep(tgetter) + " : " + "TypeRef",
    		TYPETGETTER,
    		e_applyRuleTypeTGetter, tgetter, new ErrorInformation[] {new ErrorInformation(tgetter)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeTGetter(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TGetter tgetter) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef _elvis = null;
    TypeRef _declaredTypeRef = tgetter.getDeclaredTypeRef();
    if (_declaredTypeRef != null) {
      _elvis = _declaredTypeRef;
    } else {
      ParameterizedTypeRef _anyTypeRef = RuleEnvironmentExtensions.anyTypeRef(G);
      _elvis = _anyTypeRef;
    }
    T = _elvis;
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TSetter tsetter) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeTSetter(G, _subtrace_, tsetter);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeTSetter") + stringRepForEnv(G) + " |- " + stringRep(tsetter) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeTSetter) {
    	typeThrowException(ruleName("typeTSetter") + stringRepForEnv(G) + " |- " + stringRep(tsetter) + " : " + "TypeRef",
    		TYPETSETTER,
    		e_applyRuleTypeTSetter, tsetter, new ErrorInformation[] {new ErrorInformation(tsetter)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeTSetter(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TSetter tsetter) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef _elvis = null;
    TypeRef _declaredTypeRef = tsetter.getDeclaredTypeRef();
    if (_declaredTypeRef != null) {
      _elvis = _declaredTypeRef;
    } else {
      ParameterizedTypeRef _anyTypeRef = RuleEnvironmentExtensions.anyTypeRef(G);
      _elvis = _anyTypeRef;
    }
    T = _elvis;
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TVariable tvariable) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeTVariable(G, _subtrace_, tvariable);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeTVariable") + stringRepForEnv(G) + " |- " + stringRep(tvariable) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeTVariable) {
    	typeThrowException(ruleName("typeTVariable") + stringRepForEnv(G) + " |- " + stringRep(tvariable) + " : " + "TypeRef",
    		TYPETVARIABLE,
    		e_applyRuleTypeTVariable, tvariable, new ErrorInformation[] {new ErrorInformation(tvariable)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeTVariable(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TVariable tvariable) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = tvariable.getTypeRef();
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeDefiningElement elem) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeTypeDefiningElement(G, _subtrace_, elem);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeTypeDefiningElement") + stringRepForEnv(G) + " |- " + stringRep(elem) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeTypeDefiningElement) {
    	typeThrowException(ruleName("typeTypeDefiningElement") + stringRepForEnv(G) + " |- " + stringRep(elem) + " : " + "TypeRef",
    		TYPETYPEDEFININGELEMENT,
    		e_applyRuleTypeTypeDefiningElement, elem, new ErrorInformation[] {new ErrorInformation(elem)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeTypeDefiningElement(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeDefiningElement elem) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeTypeDefiningElement_1(G, elem));
  }
  
  private TypeRef _applyRuleTypeTypeDefiningElement_1(final RuleEnvironment G, final TypeDefiningElement elem) throws RuleFailedException {
    TypeRef _elvis = null;
    TypeRef _wrapTypeInTypeRef = TypeUtils.wrapTypeInTypeRef(elem.getDefinedType());
    if (_wrapTypeInTypeRef != null) {
      _elvis = _wrapTypeInTypeRef;
    } else {
      UnknownTypeRef _createUnknownTypeRef = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
      _elvis = _createUnknownTypeRef;
    }
    return _elvis;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ObjectLiteral ol) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeObjectLiteral(G, _subtrace_, ol);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeObjectLiteral") + stringRepForEnv(G) + " |- " + stringRep(ol) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeObjectLiteral) {
    	typeThrowException(ruleName("typeObjectLiteral") + stringRepForEnv(G) + " |- " + stringRep(ol) + " : " + "TypeRef",
    		TYPEOBJECTLITERAL,
    		e_applyRuleTypeObjectLiteral, ol, new ErrorInformation[] {new ErrorInformation(ol)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeObjectLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ObjectLiteral ol) throws RuleFailedException {
    TypeRef T = null; // output parameter
    final ParameterizedTypeRefStructural ptr = TypeRefsFactory.eINSTANCE.createParameterizedTypeRefStructural();
    ptr.setDeclaredType(RuleEnvironmentExtensions.objectType(G));
    Type _definedType = ol.getDefinedType();
    ptr.setStructuralType(((TStructuralType) _definedType));
    ptr.setDefinedTypingStrategy(TypingStrategy.STRUCTURAL);
    T = ptr;
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ThisLiteral t) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeThisKeyword(G, _subtrace_, t);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeThisKeyword") + stringRepForEnv(G) + " |- " + stringRep(t) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeThisKeyword) {
    	typeThrowException(ruleName("typeThisKeyword") + stringRepForEnv(G) + " |- " + stringRep(t) + " : " + "TypeRef",
    		TYPETHISKEYWORD,
    		e_applyRuleTypeThisKeyword, t, new ErrorInformation[] {new ErrorInformation(t)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeThisKeyword(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ThisLiteral t) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef rawT = null;
    /* G |~ t ~> rawT */
    Result<TypeRef> result = thisTypeRefInternal(G, _trace_, t);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    rawT = (TypeRef) result.getFirst();
    
    rawT = this.versionResolver.<TypeRef, TypeRef>resolveVersion(rawT, rawT);
    T = TypeUtils.enforceNominalTyping(rawT);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final SuperLiteral superLiteral) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeSuperLiteral(G, _subtrace_, superLiteral);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeSuperLiteral") + stringRepForEnv(G) + " |- " + stringRep(superLiteral) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeSuperLiteral) {
    	typeThrowException(ruleName("typeSuperLiteral") + stringRepForEnv(G) + " |- " + stringRep(superLiteral) + " : " + "TypeRef",
    		TYPESUPERLITERAL,
    		e_applyRuleTypeSuperLiteral, superLiteral, new ErrorInformation[] {new ErrorInformation(superLiteral)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeSuperLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final SuperLiteral superLiteral) throws RuleFailedException {
    TypeRef T = null; // output parameter
    final N4MemberDeclaration containingMemberDecl = EcoreUtil2.<N4MemberDeclaration>getContainerOfType(superLiteral.eContainer(), N4MemberDeclaration.class);
    /* if (containingMemberDecl === null) { T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef } else { val containingClass = (containingMemberDecl.eContainer as N4ClassDeclaration).definedType as TClass; val superClass = G.getDeclaredOrImplicitSuperType(containingClass) var effectiveSuperClass = superClass if( containingClass.isStaticPolyfill ) { effectiveSuperClass = G.getDeclaredOrImplicitSuperType( superClass as TClass ) } { superLiteral.eContainer instanceof ParameterizedPropertyAccessExpression || superLiteral.eContainer instanceof IndexedAccessExpression if(containingMemberDecl.static) T = effectiveSuperClass?.createConstructorTypeRef else T = effectiveSuperClass?.createTypeRef if (T !== null) T = TypeUtils.enforceNominalTyping(T) } or { superLiteral.eContainer instanceof ParameterizedCallExpression if(containingMemberDecl instanceof N4MethodDeclaration && containingMemberDecl.name == 'constructor') { val ctor = containerTypesHelper.fromContext(superLiteral.eResource).findConstructor(effectiveSuperClass); T = ctor?.createTypeRef } else { T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef } } or { superLiteral.eContainer instanceof NewExpression } } or { T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef } */
    {
      RuleFailedException previousFailure = null;
      try {
        if ((containingMemberDecl == null)) {
          T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
        } else {
          EObject _eContainer = containingMemberDecl.eContainer();
          Type _definedType = ((N4ClassDeclaration) _eContainer).getDefinedType();
          final TClass containingClass = ((TClass) _definedType);
          final TClassifier superClass = RuleEnvironmentExtensions.getDeclaredOrImplicitSuperType(G, containingClass);
          TClassifier effectiveSuperClass = superClass;
          boolean _isStaticPolyfill = containingClass.isStaticPolyfill();
          if (_isStaticPolyfill) {
            effectiveSuperClass = RuleEnvironmentExtensions.getDeclaredOrImplicitSuperType(G, ((TClass) superClass));
          }
          /* { superLiteral.eContainer instanceof ParameterizedPropertyAccessExpression || superLiteral.eContainer instanceof IndexedAccessExpression if(containingMemberDecl.static) T = effectiveSuperClass?.createConstructorTypeRef else T = effectiveSuperClass?.createTypeRef if (T !== null) T = TypeUtils.enforceNominalTyping(T) } or { superLiteral.eContainer instanceof ParameterizedCallExpression if(containingMemberDecl instanceof N4MethodDeclaration && containingMemberDecl.name == 'constructor') { val ctor = containerTypesHelper.fromContext(superLiteral.eResource).findConstructor(effectiveSuperClass); T = ctor?.createTypeRef } else { T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef } } or { superLiteral.eContainer instanceof NewExpression } */
          {
            try {
              /* superLiteral.eContainer instanceof ParameterizedPropertyAccessExpression || superLiteral.eContainer instanceof IndexedAccessExpression */
              if (!((superLiteral.eContainer() instanceof ParameterizedPropertyAccessExpression) || (superLiteral.eContainer() instanceof IndexedAccessExpression))) {
                sneakyThrowRuleFailedException("superLiteral.eContainer instanceof ParameterizedPropertyAccessExpression || superLiteral.eContainer instanceof IndexedAccessExpression");
              }
              boolean _isStatic = containingMemberDecl.isStatic();
              if (_isStatic) {
                TypeRef _createConstructorTypeRef = null;
                if (effectiveSuperClass!=null) {
                  _createConstructorTypeRef=TypeUtils.createConstructorTypeRef(effectiveSuperClass);
                }
                T = _createConstructorTypeRef;
              } else {
                ParameterizedTypeRef _createTypeRef = null;
                if (effectiveSuperClass!=null) {
                  _createTypeRef=TypeUtils.createTypeRef(effectiveSuperClass);
                }
                T = _createTypeRef;
              }
              if ((T != null)) {
                T = TypeUtils.enforceNominalTyping(T);
              }
            } catch (Exception e) {
              previousFailure = extractRuleFailedException(e);
              /* { superLiteral.eContainer instanceof ParameterizedCallExpression if(containingMemberDecl instanceof N4MethodDeclaration && containingMemberDecl.name == 'constructor') { val ctor = containerTypesHelper.fromContext(superLiteral.eResource).findConstructor(effectiveSuperClass); T = ctor?.createTypeRef } else { T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef } } or { superLiteral.eContainer instanceof NewExpression } */
              {
                try {
                  EObject _eContainer_1 = superLiteral.eContainer();
                  /* superLiteral.eContainer instanceof ParameterizedCallExpression */
                  if (!(_eContainer_1 instanceof ParameterizedCallExpression)) {
                    sneakyThrowRuleFailedException("superLiteral.eContainer instanceof ParameterizedCallExpression");
                  }
                  if (((containingMemberDecl instanceof N4MethodDeclaration) && Objects.equal(containingMemberDecl.getName(), "constructor"))) {
                    final TMethod ctor = this.containerTypesHelper.fromContext(superLiteral.eResource()).findConstructor(effectiveSuperClass);
                    ParameterizedTypeRef _createTypeRef_1 = null;
                    if (ctor!=null) {
                      _createTypeRef_1=TypeUtils.createTypeRef(ctor);
                    }
                    T = _createTypeRef_1;
                  } else {
                    T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
                  }
                } catch (Exception e_1) {
                  previousFailure = extractRuleFailedException(e_1);
                  EObject _eContainer_2 = superLiteral.eContainer();
                  /* superLiteral.eContainer instanceof NewExpression */
                  if (!(_eContainer_2 instanceof NewExpression)) {
                    sneakyThrowRuleFailedException("superLiteral.eContainer instanceof NewExpression");
                  }
                }
              }
            }
          }
        }
      } catch (Exception e_2) {
        previousFailure = extractRuleFailedException(e_2);
        T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IdentifierRef idref) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeIdentifierRef(G, _subtrace_, idref);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeIdentifierRef") + stringRepForEnv(G) + " |- " + stringRep(idref) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeIdentifierRef) {
    	typeThrowException(ruleName("typeIdentifierRef") + stringRepForEnv(G) + " |- " + stringRep(idref) + " : " + "TypeRef",
    		TYPEIDENTIFIERREF,
    		e_applyRuleTypeIdentifierRef, idref, new ErrorInformation[] {new ErrorInformation(idref)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeIdentifierRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IdentifierRef idref) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* G |- idref.id : T */
    IdentifiableElement _id = idref.getId();
    Result<TypeRef> result = typeInternal(G, _trace_, _id);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    T = (TypeRef) result.getFirst();
    
    T = this.versionResolver.<TypeRef, IdentifierRef>resolveVersion(T, idref);
    if (((idref.eContainer() instanceof ParameterizedCallExpression) && (idref.eContainmentFeature() == N4JSPackage.eINSTANCE.getParameterizedCallExpression_Target()))) {
      final TMethod callableCtorFunction = this.typeSystemHelper.getCallableClassConstructorFunction(G, T);
      if ((callableCtorFunction != null)) {
        T = TypeExtensions.ref(callableCtorFunction);
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NullLiteral l) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeNullLiteral(G, _subtrace_, l);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeNullLiteral") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeNullLiteral) {
    	typeThrowException(ruleName("typeNullLiteral") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + "ParameterizedTypeRef",
    		TYPENULLLITERAL,
    		e_applyRuleTypeNullLiteral, l, new ErrorInformation[] {new ErrorInformation(l)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeNullLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NullLiteral l) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeNullLiteral_1(G, l));
  }
  
  private ParameterizedTypeRef _applyRuleTypeNullLiteral_1(final RuleEnvironment G, final NullLiteral l) throws RuleFailedException {
    ParameterizedTypeRef _nullTypeRef = RuleEnvironmentExtensions.nullTypeRef(G);
    return _nullTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BooleanLiteral l) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeBooleanLiteral(G, _subtrace_, l);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeBooleanLiteral") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeBooleanLiteral) {
    	typeThrowException(ruleName("typeBooleanLiteral") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + "ParameterizedTypeRef",
    		TYPEBOOLEANLITERAL,
    		e_applyRuleTypeBooleanLiteral, l, new ErrorInformation[] {new ErrorInformation(l)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeBooleanLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BooleanLiteral l) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeBooleanLiteral_1(G, l));
  }
  
  private ParameterizedTypeRef _applyRuleTypeBooleanLiteral_1(final RuleEnvironment G, final BooleanLiteral l) throws RuleFailedException {
    ParameterizedTypeRef _booleanTypeRef = RuleEnvironmentExtensions.booleanTypeRef(G);
    return _booleanTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NumericLiteral l) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeNumericLiteral(G, _subtrace_, l);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeNumericLiteral") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeNumericLiteral) {
    	typeThrowException(ruleName("typeNumericLiteral") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + "TypeRef",
    		TYPENUMERICLITERAL,
    		e_applyRuleTypeNumericLiteral, l, new ErrorInformation[] {new ErrorInformation(l)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeNumericLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NumericLiteral l) throws RuleFailedException {
    TypeRef T = null; // output parameter
    ParameterizedTypeRef _xifexpression = null;
    boolean _isIntLiteral = N4JSLanguageUtils.isIntLiteral(l);
    if (_isIntLiteral) {
      _xifexpression = RuleEnvironmentExtensions.intTypeRef(G);
    } else {
      _xifexpression = RuleEnvironmentExtensions.numberTypeRef(G);
    }
    T = _xifexpression;
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final StringLiteral l) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeStringLiteral(G, _subtrace_, l);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeStringLiteral") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeStringLiteral) {
    	typeThrowException(ruleName("typeStringLiteral") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + "ParameterizedTypeRef",
    		TYPESTRINGLITERAL,
    		e_applyRuleTypeStringLiteral, l, new ErrorInformation[] {new ErrorInformation(l)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeStringLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final StringLiteral l) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeStringLiteral_1(G, l));
  }
  
  private ParameterizedTypeRef _applyRuleTypeStringLiteral_1(final RuleEnvironment G, final StringLiteral l) throws RuleFailedException {
    ParameterizedTypeRef _stringTypeRef = RuleEnvironmentExtensions.stringTypeRef(G);
    return _stringTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final RegularExpressionLiteral l) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeRegExpLiteral(G, _subtrace_, l);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeRegExpLiteral") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeRegExpLiteral) {
    	typeThrowException(ruleName("typeRegExpLiteral") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + "ParameterizedTypeRef",
    		TYPEREGEXPLITERAL,
    		e_applyRuleTypeRegExpLiteral, l, new ErrorInformation[] {new ErrorInformation(l)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeRegExpLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final RegularExpressionLiteral l) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeRegExpLiteral_1(G, l));
  }
  
  private ParameterizedTypeRef _applyRuleTypeRegExpLiteral_1(final RuleEnvironment G, final RegularExpressionLiteral l) throws RuleFailedException {
    ParameterizedTypeRef _regexpTypeRef = RuleEnvironmentExtensions.regexpTypeRef(G);
    return _regexpTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TaggedTemplateString l) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeTaggedTemplateString(G, _subtrace_, l);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeTaggedTemplateString") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeTaggedTemplateString) {
    	typeThrowException(ruleName("typeTaggedTemplateString") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + "ParameterizedTypeRef",
    		TYPETAGGEDTEMPLATESTRING,
    		e_applyRuleTypeTaggedTemplateString, l, new ErrorInformation[] {new ErrorInformation(l)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeTaggedTemplateString(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TaggedTemplateString l) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeTaggedTemplateString_1(G, l));
  }
  
  private ParameterizedTypeRef _applyRuleTypeTaggedTemplateString_1(final RuleEnvironment G, final TaggedTemplateString l) throws RuleFailedException {
    ParameterizedTypeRef _stringTypeRef = RuleEnvironmentExtensions.stringTypeRef(G);
    return _stringTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TemplateLiteral l) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeTemplateLiteral(G, _subtrace_, l);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeTemplateLiteral") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeTemplateLiteral) {
    	typeThrowException(ruleName("typeTemplateLiteral") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + "ParameterizedTypeRef",
    		TYPETEMPLATELITERAL,
    		e_applyRuleTypeTemplateLiteral, l, new ErrorInformation[] {new ErrorInformation(l)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeTemplateLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TemplateLiteral l) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeTemplateLiteral_1(G, l));
  }
  
  private ParameterizedTypeRef _applyRuleTypeTemplateLiteral_1(final RuleEnvironment G, final TemplateLiteral l) throws RuleFailedException {
    ParameterizedTypeRef _stringTypeRef = RuleEnvironmentExtensions.stringTypeRef(G);
    return _stringTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TemplateSegment l) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeTemplateSegment(G, _subtrace_, l);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeTemplateSegment") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeTemplateSegment) {
    	typeThrowException(ruleName("typeTemplateSegment") + stringRepForEnv(G) + " |- " + stringRep(l) + " : " + "ParameterizedTypeRef",
    		TYPETEMPLATESEGMENT,
    		e_applyRuleTypeTemplateSegment, l, new ErrorInformation[] {new ErrorInformation(l)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeTemplateSegment(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TemplateSegment l) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeTemplateSegment_1(G, l));
  }
  
  private ParameterizedTypeRef _applyRuleTypeTemplateSegment_1(final RuleEnvironment G, final TemplateSegment l) throws RuleFailedException {
    ParameterizedTypeRef _stringTypeRef = RuleEnvironmentExtensions.stringTypeRef(G);
    return _stringTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final N4EnumLiteral enumLiteral) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeN4EnumLiteral(G, _subtrace_, enumLiteral);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeN4EnumLiteral") + stringRepForEnv(G) + " |- " + stringRep(enumLiteral) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeN4EnumLiteral) {
    	typeThrowException(ruleName("typeN4EnumLiteral") + stringRepForEnv(G) + " |- " + stringRep(enumLiteral) + " : " + "TypeRef",
    		TYPEN4ENUMLITERAL,
    		e_applyRuleTypeN4EnumLiteral, enumLiteral, new ErrorInformation[] {new ErrorInformation(enumLiteral)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeN4EnumLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final N4EnumLiteral enumLiteral) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeN4EnumLiteral_1(G, enumLiteral));
  }
  
  private TypeRef _applyRuleTypeN4EnumLiteral_1(final RuleEnvironment G, final N4EnumLiteral enumLiteral) throws RuleFailedException {
    EObject _eContainer = enumLiteral.getDefinedLiteral().eContainer();
    TypeRef _ref = TypeExtensions.ref(((TEnum) _eContainer));
    return _ref;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ArrayLiteral al) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeArrayLiteral(G, _subtrace_, al);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeArrayLiteral") + stringRepForEnv(G) + " |- " + stringRep(al) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeArrayLiteral) {
    	typeThrowException(ruleName("typeArrayLiteral") + stringRepForEnv(G) + " |- " + stringRep(al) + " : " + "TypeRef",
    		TYPEARRAYLITERAL,
    		e_applyRuleTypeArrayLiteral, al, new ErrorInformation[] {new ErrorInformation(al)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeArrayLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ArrayLiteral al) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* fail error "rule typeArrayLiteral should never be invoked (PolyComputer is responsible for typing ArrayLiterals)" */
    String error = "rule typeArrayLiteral should never be invoked (PolyComputer is responsible for typing ArrayLiterals)";
    throwForExplicitFail(error, new ErrorInformation(null, null));
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ArrayPadding p) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeArrayPadding(G, _subtrace_, p);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeArrayPadding") + stringRepForEnv(G) + " |- " + stringRep(p) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeArrayPadding) {
    	typeThrowException(ruleName("typeArrayPadding") + stringRepForEnv(G) + " |- " + stringRep(p) + " : " + "ParameterizedTypeRef",
    		TYPEARRAYPADDING,
    		e_applyRuleTypeArrayPadding, p, new ErrorInformation[] {new ErrorInformation(p)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeArrayPadding(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ArrayPadding p) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeArrayPadding_1(G, p));
  }
  
  private ParameterizedTypeRef _applyRuleTypeArrayPadding_1(final RuleEnvironment G, final ArrayPadding p) throws RuleFailedException {
    ParameterizedTypeRef _undefinedTypeRef = RuleEnvironmentExtensions.undefinedTypeRef(G);
    return _undefinedTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ArrayElement e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeArrayElement(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeArrayElement") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeArrayElement) {
    	typeThrowException(ruleName("typeArrayElement") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "TypeRef",
    		TYPEARRAYELEMENT,
    		e_applyRuleTypeArrayElement, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeArrayElement(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ArrayElement e) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* G |- e.expression: T */
    Expression _expression = e.getExpression();
    Result<TypeRef> result = typeInternal(G, _trace_, _expression);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    T = (TypeRef) result.getFirst();
    
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PropertyNameValuePair property) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypePropertyNameValuePair(G, _subtrace_, property);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typePropertyNameValuePair") + stringRepForEnv(G) + " |- " + stringRep(property) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypePropertyNameValuePair) {
    	typeThrowException(ruleName("typePropertyNameValuePair") + stringRepForEnv(G) + " |- " + stringRep(property) + " : " + "TypeRef",
    		TYPEPROPERTYNAMEVALUEPAIR,
    		e_applyRuleTypePropertyNameValuePair, property, new ErrorInformation[] {new ErrorInformation(property)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypePropertyNameValuePair(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PropertyNameValuePair property) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef _declaredTypeRef = property.getDeclaredTypeRef();
    boolean _tripleNotEquals = (_declaredTypeRef != null);
    if (_tripleNotEquals) {
      T = property.getDeclaredTypeRef();
    } else {
      Expression _expression = property.getExpression();
      boolean _tripleNotEquals_1 = (_expression != null);
      if (_tripleNotEquals_1) {
        /* G |- property.expression: var TypeRef E */
        Expression _expression_1 = property.getExpression();
        TypeRef E = null;
        Result<TypeRef> result = typeInternal(G, _trace_, _expression_1);
        checkAssignableTo(result.getFirst(), TypeRef.class);
        E = (TypeRef) result.getFirst();
        
        /* G |~ E /\ E */
        Result<TypeRef> result_1 = upperBoundInternal(G, _trace_, E);
        checkAssignableTo(result_1.getFirst(), TypeRef.class);
        E = (TypeRef) result_1.getFirst();
        
        if ((((E.getDeclaredType() == RuleEnvironmentExtensions.undefinedType(G)) || (E.getDeclaredType() == RuleEnvironmentExtensions.nullType(G))) || (E.getDeclaredType() == RuleEnvironmentExtensions.voidType(G)))) {
          T = RuleEnvironmentExtensions.anyTypeRef(G);
        } else {
          T = E;
        }
      } else {
        T = RuleEnvironmentExtensions.anyTypeRef(G);
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final N4FieldDeclaration fieldDecl) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeN4FieldDeclaration(G, _subtrace_, fieldDecl);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeN4FieldDeclaration") + stringRepForEnv(G) + " |- " + stringRep(fieldDecl) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeN4FieldDeclaration) {
    	typeThrowException(ruleName("typeN4FieldDeclaration") + stringRepForEnv(G) + " |- " + stringRep(fieldDecl) + " : " + "TypeRef",
    		TYPEN4FIELDDECLARATION,
    		e_applyRuleTypeN4FieldDeclaration, fieldDecl, new ErrorInformation[] {new ErrorInformation(fieldDecl)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeN4FieldDeclaration(final RuleEnvironment G, final RuleApplicationTrace _trace_, final N4FieldDeclaration fieldDecl) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef _declaredTypeRef = fieldDecl.getDeclaredTypeRef();
    boolean _tripleNotEquals = (_declaredTypeRef != null);
    if (_tripleNotEquals) {
      T = fieldDecl.getDeclaredTypeRef();
    } else {
      Expression _expression = fieldDecl.getExpression();
      boolean _tripleNotEquals_1 = (_expression != null);
      if (_tripleNotEquals_1) {
        /* G |- fieldDecl.expression : var TypeRef E */
        Expression _expression_1 = fieldDecl.getExpression();
        TypeRef E = null;
        Result<TypeRef> result = typeInternal(G, _trace_, _expression_1);
        checkAssignableTo(result.getFirst(), TypeRef.class);
        E = (TypeRef) result.getFirst();
        
        /* G |~ E /\ E */
        Result<TypeRef> result_1 = upperBoundInternal(G, _trace_, E);
        checkAssignableTo(result_1.getFirst(), TypeRef.class);
        E = (TypeRef) result_1.getFirst();
        
        if ((((E.getDeclaredType() == RuleEnvironmentExtensions.undefinedType(G)) || (E.getDeclaredType() == RuleEnvironmentExtensions.nullType(G))) || (E.getDeclaredType() == RuleEnvironmentExtensions.voidType(G)))) {
          T = RuleEnvironmentExtensions.anyTypeRef(G);
        } else {
          T = E;
        }
      } else {
        T = RuleEnvironmentExtensions.anyTypeRef(G);
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final GetterDeclaration getter) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeGetterDeclaration(G, _subtrace_, getter);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeGetterDeclaration") + stringRepForEnv(G) + " |- " + stringRep(getter) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeGetterDeclaration) {
    	typeThrowException(ruleName("typeGetterDeclaration") + stringRepForEnv(G) + " |- " + stringRep(getter) + " : " + "TypeRef",
    		TYPEGETTERDECLARATION,
    		e_applyRuleTypeGetterDeclaration, getter, new ErrorInformation[] {new ErrorInformation(getter)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeGetterDeclaration(final RuleEnvironment G, final RuleApplicationTrace _trace_, final GetterDeclaration getter) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef _declaredTypeRef = getter.getDeclaredTypeRef();
    boolean _tripleNotEquals = (_declaredTypeRef != null);
    if (_tripleNotEquals) {
      T = getter.getDeclaredTypeRef();
    } else {
      TGetter _definedGetter = getter.getDefinedGetter();
      TypeRef _declaredTypeRef_1 = null;
      if (_definedGetter!=null) {
        _declaredTypeRef_1=_definedGetter.getDeclaredTypeRef();
      }
      boolean _tripleNotEquals_1 = (_declaredTypeRef_1 != null);
      if (_tripleNotEquals_1) {
        T = getter.getDefinedGetter().getDeclaredTypeRef();
      } else {
        T = RuleEnvironmentExtensions.anyTypeRef(G);
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final SetterDeclaration setter) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeSetterDeclaration(G, _subtrace_, setter);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeSetterDeclaration") + stringRepForEnv(G) + " |- " + stringRep(setter) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeSetterDeclaration) {
    	typeThrowException(ruleName("typeSetterDeclaration") + stringRepForEnv(G) + " |- " + stringRep(setter) + " : " + "TypeRef",
    		TYPESETTERDECLARATION,
    		e_applyRuleTypeSetterDeclaration, setter, new ErrorInformation[] {new ErrorInformation(setter)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeSetterDeclaration(final RuleEnvironment G, final RuleApplicationTrace _trace_, final SetterDeclaration setter) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef _declaredTypeRef = setter.getDeclaredTypeRef();
    boolean _tripleNotEquals = (_declaredTypeRef != null);
    if (_tripleNotEquals) {
      T = setter.getDeclaredTypeRef();
    }
    if ((T == null)) {
      T = RuleEnvironmentExtensions.anyTypeRef(G);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParenExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeParenExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeParenExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeParenExpression) {
    	typeThrowException(ruleName("typeParenExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "TypeRef",
    		TYPEPARENEXPRESSION,
    		e_applyRuleTypeParenExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeParenExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParenExpression e) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* G |- e.expression: T */
    Expression _expression = e.getExpression();
    Result<TypeRef> result = typeInternal(G, _trace_, _expression);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    T = (TypeRef) result.getFirst();
    
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final YieldExpression y) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeYieldExpression(G, _subtrace_, y);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeYieldExpression") + stringRepForEnv(G) + " |- " + stringRep(y) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeYieldExpression) {
    	typeThrowException(ruleName("typeYieldExpression") + stringRepForEnv(G) + " |- " + stringRep(y) + " : " + "TypeRef",
    		TYPEYIELDEXPRESSION,
    		e_applyRuleTypeYieldExpression, y, new ErrorInformation[] {new ErrorInformation(y)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeYieldExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final YieldExpression y) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef t = null;
    boolean _isMany = y.isMany();
    if (_isMany) {
      final Expression yieldValue = y.getExpression();
      /* G |- yieldValue : var TypeRef yieldValueTypeRef */
      TypeRef yieldValueTypeRef = null;
      Result<TypeRef> result = typeInternal(G, _trace_, yieldValue);
      checkAssignableTo(result.getFirst(), TypeRef.class);
      yieldValueTypeRef = (TypeRef) result.getFirst();
      
      final BuiltInTypeScope scope = RuleEnvironmentExtensions.getPredefinedTypes(G).builtInTypeScope;
      boolean _isGenerator = TypeUtils.isGenerator(yieldValueTypeRef, scope);
      if (_isGenerator) {
        t = this.typeSystemHelper.getGeneratorTReturn(G, yieldValueTypeRef);
      } else {
        final ParameterizedTypeRef itTypeRef = RuleEnvironmentExtensions.iterableTypeRef(G, TypeUtils.createWildcard());
        /* G |- yieldValueTypeRef <: itTypeRef */
        boolean _ruleinvocation = subtypeSucceeded(G, _trace_, yieldValueTypeRef, itTypeRef);
        final boolean isIterable = _ruleinvocation;
        if (isIterable) {
          t = scope.getAnyTypeRef();
        }
      }
    } else {
      final TypeRef actualGenTypeRef = this.typeSystemHelper.getActualGeneratorReturnType(G, y);
      if ((actualGenTypeRef != null)) {
        t = this.typeSystemHelper.getGeneratorTNext(G, actualGenTypeRef);
      }
    }
    if ((t == null)) {
      t = RuleEnvironmentExtensions.anyTypeRef(G);
    }
    T = t;
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AwaitExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeAwaitExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeAwaitExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeAwaitExpression) {
    	typeThrowException(ruleName("typeAwaitExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "TypeRef",
    		TYPEAWAITEXPRESSION,
    		e_applyRuleTypeAwaitExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeAwaitExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AwaitExpression e) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* G |- e.expression : var TypeRef exprType */
    Expression _expression = e.getExpression();
    TypeRef exprType = null;
    Result<TypeRef> result = typeInternal(G, _trace_, _expression);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    exprType = (TypeRef) result.getFirst();
    
    Type _declaredType = exprType.getDeclaredType();
    TClass _promiseType = RuleEnvironmentExtensions.promiseType(G);
    boolean _tripleEquals = (_declaredType == _promiseType);
    if (_tripleEquals) {
      /* G |~ exprType.typeArgs.get(0) /\ T */
      TypeArgument _get = exprType.getTypeArgs().get(0);
      Result<TypeRef> result_1 = upperBoundInternal(G, _trace_, _get);
      checkAssignableTo(result_1.getFirst(), TypeRef.class);
      T = (TypeRef) result_1.getFirst();
      
    } else {
      boolean _isPromisifiableExpression = this.promisifyHelper.isPromisifiableExpression(e.getExpression());
      if (_isPromisifiableExpression) {
        final TypeRef promisifiedReturnTypeRef = this.promisifyHelper.extractPromisifiedReturnType(e.getExpression());
        Type _declaredType_1 = promisifiedReturnTypeRef.getDeclaredType();
        TClass _promiseType_1 = RuleEnvironmentExtensions.promiseType(G);
        boolean _tripleEquals_1 = (_declaredType_1 == _promiseType_1);
        if (_tripleEquals_1) {
          /* G |~ promisifiedReturnTypeRef.typeArgs.get(0) /\ T */
          TypeArgument _get_1 = promisifiedReturnTypeRef.getTypeArgs().get(0);
          Result<TypeRef> result_2 = upperBoundInternal(G, _trace_, _get_1);
          checkAssignableTo(result_2.getFirst(), TypeRef.class);
          T = (TypeRef) result_2.getFirst();
          
        } else {
          T = promisifiedReturnTypeRef;
        }
      } else {
        T = exprType;
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PromisifyExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypePromisifyExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typePromisifyExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypePromisifyExpression) {
    	typeThrowException(ruleName("typePromisifyExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "TypeRef",
    		TYPEPROMISIFYEXPRESSION,
    		e_applyRuleTypePromisifyExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypePromisifyExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PromisifyExpression e) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = this.promisifyHelper.extractPromisifiedReturnType(e.getExpression());
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IndexedAccessExpression expr) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeIndexedAccessExpression(G, _subtrace_, expr);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeIndexedAccessExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeIndexedAccessExpression) {
    	typeThrowException(ruleName("typeIndexedAccessExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " : " + "TypeRef",
    		TYPEINDEXEDACCESSEXPRESSION,
    		e_applyRuleTypeIndexedAccessExpression, expr, new ErrorInformation[] {new ErrorInformation(expr)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeIndexedAccessExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IndexedAccessExpression expr) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* { expr.target === null || expr.index === null; T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef } or { expr.target instanceof SuperLiteral T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef } or { G |- expr.target : var TypeRef targetTypeRef targetTypeRef = typeSystemHelper.resolveType(G, targetTypeRef); G |- expr.index : var TypeRef indexTypeRef; val targetDeclType = targetTypeRef.declaredType; val targetIsLiteralOfStringBasedEnum = targetDeclType instanceof TEnum && AnnotationDefinition.STRING_BASED.hasAnnotation(targetDeclType); val indexIsNumeric = { G |- indexTypeRef <: G.numberTypeRef }; val indexValue = astMetaInfoCacheHelper.getCompileTimeValue(expr.index); val memberName = N4JSLanguageUtils.derivePropertyNameFromCompileTimeValue(indexValue); if (indexIsNumeric && (targetTypeRef.isArrayLike || targetIsLiteralOfStringBasedEnum)) { if (targetDeclType.generic && targetTypeRef.typeArgs.isEmpty) { T = G.anyTypeRef } else { val G2 = G.wrap typeSystemHelper.addSubstitutions(G2, targetTypeRef) G2.addThisType(targetTypeRef) val elementTypeRef = if(targetIsLiteralOfStringBasedEnum) { G.stringType.elementType } else { targetDeclType.elementType }; G2 |- elementTypeRef ~> T } } else if (memberName!==null) { val staticAccess = (targetTypeRef instanceof TypeTypeRef) val checkVisibility = false val scope = memberScopingHelper.createMemberScope(targetTypeRef, expr, checkVisibility, staticAccess) val memberDesc = scope.getSingleElement(qualifiedNameConverter.toQualifiedName(memberName)); val member = if(memberDesc!==null && !IEObjectDescriptionWithError.isErrorDescription(memberDesc)) { memberDesc.getEObjectOrProxy() }; if(member instanceof TMember && !member.eIsProxy) { G |- (member as TMember) : var TypeRef memberTypeRef val G2 = G.wrap typeSystemHelper.addSubstitutions(G2,targetTypeRef) G2.addThisType(targetTypeRef) G2 |- memberTypeRef ~> T } else if (targetTypeRef.dynamic) { T = G.anyTypeRefDynamic } else { T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef } } else if (targetTypeRef.dynamic) { T = G.anyTypeRefDynamic } else { T = G.anyTypeRef } } */
    {
      RuleFailedException previousFailure = null;
      try {
        /* expr.target === null || expr.index === null */
        if (!((expr.getTarget() == null) || (expr.getIndex() == null))) {
          sneakyThrowRuleFailedException("expr.target === null || expr.index === null");
        }
        T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* { expr.target instanceof SuperLiteral T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef } or { G |- expr.target : var TypeRef targetTypeRef targetTypeRef = typeSystemHelper.resolveType(G, targetTypeRef); G |- expr.index : var TypeRef indexTypeRef; val targetDeclType = targetTypeRef.declaredType; val targetIsLiteralOfStringBasedEnum = targetDeclType instanceof TEnum && AnnotationDefinition.STRING_BASED.hasAnnotation(targetDeclType); val indexIsNumeric = { G |- indexTypeRef <: G.numberTypeRef }; val indexValue = astMetaInfoCacheHelper.getCompileTimeValue(expr.index); val memberName = N4JSLanguageUtils.derivePropertyNameFromCompileTimeValue(indexValue); if (indexIsNumeric && (targetTypeRef.isArrayLike || targetIsLiteralOfStringBasedEnum)) { if (targetDeclType.generic && targetTypeRef.typeArgs.isEmpty) { T = G.anyTypeRef } else { val G2 = G.wrap typeSystemHelper.addSubstitutions(G2, targetTypeRef) G2.addThisType(targetTypeRef) val elementTypeRef = if(targetIsLiteralOfStringBasedEnum) { G.stringType.elementType } else { targetDeclType.elementType }; G2 |- elementTypeRef ~> T } } else if (memberName!==null) { val staticAccess = (targetTypeRef instanceof TypeTypeRef) val checkVisibility = false val scope = memberScopingHelper.createMemberScope(targetTypeRef, expr, checkVisibility, staticAccess) val memberDesc = scope.getSingleElement(qualifiedNameConverter.toQualifiedName(memberName)); val member = if(memberDesc!==null && !IEObjectDescriptionWithError.isErrorDescription(memberDesc)) { memberDesc.getEObjectOrProxy() }; if(member instanceof TMember && !member.eIsProxy) { G |- (member as TMember) : var TypeRef memberTypeRef val G2 = G.wrap typeSystemHelper.addSubstitutions(G2,targetTypeRef) G2.addThisType(targetTypeRef) G2 |- memberTypeRef ~> T } else if (targetTypeRef.dynamic) { T = G.anyTypeRefDynamic } else { T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef } } else if (targetTypeRef.dynamic) { T = G.anyTypeRefDynamic } else { T = G.anyTypeRef } } */
        {
          try {
            Expression _target = expr.getTarget();
            /* expr.target instanceof SuperLiteral */
            if (!(_target instanceof SuperLiteral)) {
              sneakyThrowRuleFailedException("expr.target instanceof SuperLiteral");
            }
            T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
          } catch (Exception e_1) {
            previousFailure = extractRuleFailedException(e_1);
            /* G |- expr.target : var TypeRef targetTypeRef */
            Expression _target_1 = expr.getTarget();
            TypeRef targetTypeRef = null;
            Result<TypeRef> result = typeInternal(G, _trace_, _target_1);
            checkAssignableTo(result.getFirst(), TypeRef.class);
            targetTypeRef = (TypeRef) result.getFirst();
            
            targetTypeRef = this.typeSystemHelper.resolveType(G, targetTypeRef);
            /* G |- expr.index : var TypeRef indexTypeRef */
            Expression _index = expr.getIndex();
            TypeRef indexTypeRef = null;
            Result<TypeRef> result_1 = typeInternal(G, _trace_, _index);
            checkAssignableTo(result_1.getFirst(), TypeRef.class);
            indexTypeRef = (TypeRef) result_1.getFirst();
            
            final Type targetDeclType = targetTypeRef.getDeclaredType();
            final boolean targetIsLiteralOfStringBasedEnum = ((targetDeclType instanceof TEnum) && AnnotationDefinition.STRING_BASED.hasAnnotation(targetDeclType));
            /* G |- indexTypeRef <: G.numberTypeRef */
            ParameterizedTypeRef _numberTypeRef = RuleEnvironmentExtensions.numberTypeRef(G);
            boolean _ruleinvocation = subtypeSucceeded(G, _trace_, indexTypeRef, _numberTypeRef);
            final boolean indexIsNumeric = _ruleinvocation;
            final CompileTimeValue indexValue = this.astMetaInfoCacheHelper.getCompileTimeValue(expr.getIndex());
            final String memberName = N4JSLanguageUtils.derivePropertyNameFromCompileTimeValue(indexValue);
            if ((indexIsNumeric && (targetTypeRef.isArrayLike() || targetIsLiteralOfStringBasedEnum))) {
              if ((targetDeclType.isGeneric() && targetTypeRef.getTypeArgs().isEmpty())) {
                T = RuleEnvironmentExtensions.anyTypeRef(G);
              } else {
                final RuleEnvironment G2 = RuleEnvironmentExtensions.wrap(G);
                this.typeSystemHelper.addSubstitutions(G2, targetTypeRef);
                RuleEnvironmentExtensions.addThisType(G2, targetTypeRef);
                TypeRef _xifexpression = null;
                if (targetIsLiteralOfStringBasedEnum) {
                  _xifexpression = RuleEnvironmentExtensions.stringType(G).getElementType();
                } else {
                  _xifexpression = targetDeclType.getElementType();
                }
                final TypeRef elementTypeRef = _xifexpression;
                /* G2 |- elementTypeRef ~> T */
                Result<TypeArgument> result_2 = substTypeVariablesInternal(G2, _trace_, elementTypeRef);
                checkAssignableTo(result_2.getFirst(), TypeRef.class);
                T = (TypeRef) result_2.getFirst();
                
              }
            } else {
              if ((memberName != null)) {
                final boolean staticAccess = (targetTypeRef instanceof TypeTypeRef);
                final boolean checkVisibility = false;
                final IScope scope = this.memberScopingHelper.createMemberScope(targetTypeRef, expr, checkVisibility, staticAccess);
                final IEObjectDescription memberDesc = scope.getSingleElement(this.qualifiedNameConverter.toQualifiedName(memberName));
                EObject _xifexpression_1 = null;
                if (((memberDesc != null) && (!IEObjectDescriptionWithError.isErrorDescription(memberDesc)))) {
                  _xifexpression_1 = memberDesc.getEObjectOrProxy();
                }
                final EObject member = _xifexpression_1;
                if (((member instanceof TMember) && (!member.eIsProxy()))) {
                  /* G |- (member as TMember) : var TypeRef memberTypeRef */
                  TypeRef memberTypeRef = null;
                  Result<TypeRef> result_3 = typeInternal(G, _trace_, ((TMember) member));
                  checkAssignableTo(result_3.getFirst(), TypeRef.class);
                  memberTypeRef = (TypeRef) result_3.getFirst();
                  
                  final RuleEnvironment G2_1 = RuleEnvironmentExtensions.wrap(G);
                  this.typeSystemHelper.addSubstitutions(G2_1, targetTypeRef);
                  RuleEnvironmentExtensions.addThisType(G2_1, targetTypeRef);
                  /* G2 |- memberTypeRef ~> T */
                  Result<TypeArgument> result_4 = substTypeVariablesInternal(G2_1, _trace_, memberTypeRef);
                  checkAssignableTo(result_4.getFirst(), TypeRef.class);
                  T = (TypeRef) result_4.getFirst();
                  
                } else {
                  boolean _isDynamic = targetTypeRef.isDynamic();
                  if (_isDynamic) {
                    T = RuleEnvironmentExtensions.anyTypeRefDynamic(G);
                  } else {
                    T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
                  }
                }
              } else {
                boolean _isDynamic_1 = targetTypeRef.isDynamic();
                if (_isDynamic_1) {
                  T = RuleEnvironmentExtensions.anyTypeRefDynamic(G);
                } else {
                  T = RuleEnvironmentExtensions.anyTypeRef(G);
                }
              }
            }
          }
        }
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedPropertyAccessExpression expr) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypePropertyAccessExpression(G, _subtrace_, expr);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typePropertyAccessExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypePropertyAccessExpression) {
    	typeThrowException(ruleName("typePropertyAccessExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " : " + "TypeRef",
    		TYPEPROPERTYACCESSEXPRESSION,
    		e_applyRuleTypePropertyAccessExpression, expr, new ErrorInformation[] {new ErrorInformation(expr)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypePropertyAccessExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedPropertyAccessExpression expr) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* { T = env(G, GUARD_TYPE_PROPERTY_ACCESS_EXPRESSION -> expr, TypeRef) } or { val G2 = G.wrap G2.add(GUARD_TYPE_PROPERTY_ACCESS_EXPRESSION -> expr, G2.anyTypeRef) G2 |- expr.target : var TypeRef receiverTypeRef typeSystemHelper.addSubstitutions(G2,receiverTypeRef) G2.addThisType(receiverTypeRef) if (! (receiverTypeRef instanceof UnknownTypeRef) && (expr.target instanceof SuperLiteral || expr.target instanceof ThisLiteral) ) { var containingClass = EcoreUtil2.getContainerOfType(expr,N4ClassDeclaration)?.definedType; if (containingClass instanceof TClass) { if (containingClass.isStaticPolyfill) { containingClass = containingClass.superClassRef?.declaredType } if (containingClass instanceof TClass) { if (containingClass?.superClassRef!==null) { typeSystemHelper.addSubstitutions(G2, containingClass.superClassRef) } } } } val prop = expr.property; var TypeRef propTypeRef; if(prop instanceof TMethod && (prop as TMethod).isConstructor) { val TypeArgument ctorTypeArg = switch(receiverTypeRef) { TypeTypeRef: G.functionTypeRef ParameterizedTypeRef, BoundThisTypeRef: { val declType = if(receiverTypeRef instanceof BoundThisTypeRef) { receiverTypeRef.actualThisTypeRef?.declaredType } else { receiverTypeRef.declaredType }; val finalCtorSig = if(declType instanceof TClassifier) N4JSLanguageUtils.hasCovariantConstructor(declType); if(finalCtorSig) { declType.ref } else if(declType!==null) { TypeUtils.createWildcardExtends(declType.ref) } else { null } } }; propTypeRef = if(ctorTypeArg!==null) { TypeUtils.createTypeTypeRef(ctorTypeArg, true) } else { TypeRefsFactory.eINSTANCE.createUnknownTypeRef }; } else if(receiverTypeRef.dynamic && prop!==null && prop.eIsProxy) { propTypeRef = G.anyTypeRefDynamic; } else { G2.wrap |- prop : propTypeRef if(expr.parameterized) { typeSystemHelper.addSubstitutions(G2,expr); } } G2 |- propTypeRef ~> T T = versionResolver.resolveVersion(T, receiverTypeRef); if (expr.target instanceof SuperLiteral && T instanceof FunctionTypeExprOrRef ) { val F = T as FunctionTypeExprOrRef; if ((T as FunctionTypeExprOrRef).returnTypeRef instanceof BoundThisTypeRef) { var TypeRef rawT; G |~ expr ~> rawT; val thisTypeRef = TypeUtils.enforceNominalTyping(rawT); if (T instanceof FunctionTypeExpression && T.eContainer===null) { val fte = T as FunctionTypeExpression fte.returnTypeRef = TypeUtils.copyIfContained(thisTypeRef); } else { T = TypeUtils.createFunctionTypeExpression(null, F.typeVars, F.fpars, thisTypeRef); } } } } */
    {
      RuleFailedException previousFailure = null;
      try {
        Pair<String, ParameterizedPropertyAccessExpression> _mappedTo = Pair.<String, ParameterizedPropertyAccessExpression>of(RuleEnvironmentExtensions.GUARD_TYPE_PROPERTY_ACCESS_EXPRESSION, expr);
        T = this.<TypeRef>env(G, _mappedTo, TypeRef.class);
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        final RuleEnvironment G2 = RuleEnvironmentExtensions.wrap(G);
        Pair<String, ParameterizedPropertyAccessExpression> _mappedTo_1 = Pair.<String, ParameterizedPropertyAccessExpression>of(RuleEnvironmentExtensions.GUARD_TYPE_PROPERTY_ACCESS_EXPRESSION, expr);
        boolean _add = G2.add(_mappedTo_1, RuleEnvironmentExtensions.anyTypeRef(G2));
        /* G2.add(GUARD_TYPE_PROPERTY_ACCESS_EXPRESSION -> expr, G2.anyTypeRef) */
        if (!_add) {
          sneakyThrowRuleFailedException("G2.add(GUARD_TYPE_PROPERTY_ACCESS_EXPRESSION -> expr, G2.anyTypeRef)");
        }
        /* G2 |- expr.target : var TypeRef receiverTypeRef */
        Expression _target = expr.getTarget();
        TypeRef receiverTypeRef = null;
        Result<TypeRef> result = typeInternal(G2, _trace_, _target);
        checkAssignableTo(result.getFirst(), TypeRef.class);
        receiverTypeRef = (TypeRef) result.getFirst();
        
        this.typeSystemHelper.addSubstitutions(G2, receiverTypeRef);
        RuleEnvironmentExtensions.addThisType(G2, receiverTypeRef);
        if (((!(receiverTypeRef instanceof UnknownTypeRef)) && ((expr.getTarget() instanceof SuperLiteral) || (expr.getTarget() instanceof ThisLiteral)))) {
          N4ClassDeclaration _containerOfType = EcoreUtil2.<N4ClassDeclaration>getContainerOfType(expr, N4ClassDeclaration.class);
          Type _definedType = null;
          if (_containerOfType!=null) {
            _definedType=_containerOfType.getDefinedType();
          }
          Type containingClass = _definedType;
          if ((containingClass instanceof TClass)) {
            boolean _isStaticPolyfill = ((TClass)containingClass).isStaticPolyfill();
            if (_isStaticPolyfill) {
              ParameterizedTypeRef _superClassRef = ((TClass)containingClass).getSuperClassRef();
              Type _declaredType = null;
              if (_superClassRef!=null) {
                _declaredType=_superClassRef.getDeclaredType();
              }
              containingClass = _declaredType;
            }
            if ((containingClass instanceof TClass)) {
              ParameterizedTypeRef _superClassRef_1 = null;
              if (((TClass)containingClass)!=null) {
                _superClassRef_1=((TClass)containingClass).getSuperClassRef();
              }
              boolean _tripleNotEquals = (_superClassRef_1 != null);
              if (_tripleNotEquals) {
                this.typeSystemHelper.addSubstitutions(G2, ((TClass)containingClass).getSuperClassRef());
              }
            }
          }
        }
        final IdentifiableElement prop = expr.getProperty();
        TypeRef propTypeRef = null;
        if (((prop instanceof TMethod) && ((TMethod) prop).isConstructor())) {
          TypeArgument _switchResult = null;
          boolean _matched = false;
          if (receiverTypeRef instanceof TypeTypeRef) {
            _matched=true;
            _switchResult = RuleEnvironmentExtensions.functionTypeRef(G);
          }
          if (!_matched) {
            if (receiverTypeRef instanceof ParameterizedTypeRef) {
              _matched=true;
            }
            if (!_matched) {
              if (receiverTypeRef instanceof BoundThisTypeRef) {
                _matched=true;
              }
            }
            if (_matched) {
              TypeArgument _xblockexpression = null;
              {
                Type _xifexpression = null;
                if ((receiverTypeRef instanceof BoundThisTypeRef)) {
                  ParameterizedTypeRef _actualThisTypeRef = ((BoundThisTypeRef)receiverTypeRef).getActualThisTypeRef();
                  Type _declaredType_1 = null;
                  if (_actualThisTypeRef!=null) {
                    _declaredType_1=_actualThisTypeRef.getDeclaredType();
                  }
                  _xifexpression = _declaredType_1;
                } else {
                  _xifexpression = ((BaseTypeRef)receiverTypeRef).getDeclaredType();
                }
                final Type declType = _xifexpression;
                boolean _xifexpression_1 = false;
                if ((declType instanceof TClassifier)) {
                  _xifexpression_1 = N4JSLanguageUtils.hasCovariantConstructor(((TClassifier)declType));
                }
                final boolean finalCtorSig = _xifexpression_1;
                TypeArgument _xifexpression_2 = null;
                if (finalCtorSig) {
                  _xifexpression_2 = TypeExtensions.ref(declType);
                } else {
                  Wildcard _xifexpression_3 = null;
                  if ((declType != null)) {
                    _xifexpression_3 = TypeUtils.createWildcardExtends(TypeExtensions.ref(declType));
                  } else {
                    _xifexpression_3 = null;
                  }
                  _xifexpression_2 = _xifexpression_3;
                }
                _xblockexpression = (_xifexpression_2);
              }
              _switchResult = _xblockexpression;
            }
          }
          final TypeArgument ctorTypeArg = _switchResult;
          TypeRef _xifexpression = null;
          if ((ctorTypeArg != null)) {
            _xifexpression = TypeUtils.createTypeTypeRef(ctorTypeArg, true);
          } else {
            _xifexpression = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
          }
          propTypeRef = _xifexpression;
        } else {
          if (((receiverTypeRef.isDynamic() && (prop != null)) && prop.eIsProxy())) {
            propTypeRef = RuleEnvironmentExtensions.anyTypeRefDynamic(G);
          } else {
            /* G2.wrap |- prop : propTypeRef */
            RuleEnvironment _wrap = RuleEnvironmentExtensions.wrap(G2);
            Result<TypeRef> result_1 = typeInternal(_wrap, _trace_, prop);
            checkAssignableTo(result_1.getFirst(), TypeRef.class);
            propTypeRef = (TypeRef) result_1.getFirst();
            
            boolean _isParameterized = expr.isParameterized();
            if (_isParameterized) {
              this.typeSystemHelper.addSubstitutions(G2, expr);
            }
          }
        }
        /* G2 |- propTypeRef ~> T */
        Result<TypeArgument> result_2 = substTypeVariablesInternal(G2, _trace_, propTypeRef);
        checkAssignableTo(result_2.getFirst(), TypeRef.class);
        T = (TypeRef) result_2.getFirst();
        
        T = this.versionResolver.<TypeRef, TypeRef>resolveVersion(T, receiverTypeRef);
        if (((expr.getTarget() instanceof SuperLiteral) && (T instanceof FunctionTypeExprOrRef))) {
          final FunctionTypeExprOrRef F = ((FunctionTypeExprOrRef) T);
          TypeRef _returnTypeRef = ((FunctionTypeExprOrRef) T).getReturnTypeRef();
          if ((_returnTypeRef instanceof BoundThisTypeRef)) {
            TypeRef rawT = null;
            /* G |~ expr ~> rawT */
            Result<TypeRef> result_3 = thisTypeRefInternal(G, _trace_, expr);
            checkAssignableTo(result_3.getFirst(), TypeRef.class);
            rawT = (TypeRef) result_3.getFirst();
            
            final TypeRef thisTypeRef = TypeUtils.enforceNominalTyping(rawT);
            if (((T instanceof FunctionTypeExpression) && (T.eContainer() == null))) {
              final FunctionTypeExpression fte = ((FunctionTypeExpression) T);
              fte.setReturnTypeRef(TypeUtils.<TypeRef>copyIfContained(thisTypeRef));
            } else {
              T = TypeUtils.createFunctionTypeExpression(null, 
                F.getTypeVars(), F.getFpars(), thisTypeRef);
            }
          }
        }
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NewExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeNewExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeNewExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeNewExpression) {
    	typeThrowException(ruleName("typeNewExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "TypeRef",
    		TYPENEWEXPRESSION,
    		e_applyRuleTypeNewExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeNewExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NewExpression e) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* G |- e.callee: T */
    Expression _callee = e.getCallee();
    Result<TypeRef> result = typeInternal(G, _trace_, _callee);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    T = (TypeRef) result.getFirst();
    
    if ((T instanceof TypeTypeRef)) {
      T = this.typeSystemHelper.createTypeRefFromStaticType(G, ((TypeTypeRef)T), ((TypeArgument[])Conversions.unwrapArray(e.getTypeArgs(), TypeArgument.class)));
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NewTarget nt) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeNewTarget(G, _subtrace_, nt);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeNewTarget") + stringRepForEnv(G) + " |- " + stringRep(nt) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeNewTarget) {
    	typeThrowException(ruleName("typeNewTarget") + stringRepForEnv(G) + " |- " + stringRep(nt) + " : " + "TypeRef",
    		TYPENEWTARGET,
    		e_applyRuleTypeNewTarget, nt, new ErrorInformation[] {new ErrorInformation(nt)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeNewTarget(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NewTarget nt) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedCallExpression expr) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeCallExpression(G, _subtrace_, expr);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeCallExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeCallExpression) {
    	typeThrowException(ruleName("typeCallExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " : " + "TypeRef",
    		TYPECALLEXPRESSION,
    		e_applyRuleTypeCallExpression, expr, new ErrorInformation[] {new ErrorInformation(expr)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeCallExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedCallExpression expr) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* G |- expr.target : var TypeRef targetTypeRef */
    Expression _target = expr.getTarget();
    TypeRef targetTypeRef = null;
    Result<TypeRef> result = typeInternal(G, _trace_, _target);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    targetTypeRef = (TypeRef) result.getFirst();
    
    if ((targetTypeRef instanceof FunctionTypeExprOrRef)) {
      final FunctionTypeExprOrRef F = ((FunctionTypeExprOrRef)targetTypeRef);
      final TFunction tFunction = F.getFunctionType();
      /* { val inferring = env(G, GUARD_TYPE_CALL_EXPRESSION -> expr, TypeRef) G |- inferring ~> T } or { val G2 = G.wrap; G2.add(GUARD_TYPE_CALL_EXPRESSION -> expr, F.returnTypeRef) if(expr.eContainer instanceof AwaitExpression && expr.eContainmentFeature === N4JSPackage.eINSTANCE.getAwaitExpression_Expression() && tFunction!==null && AnnotationDefinition.PROMISIFIABLE.hasAnnotation(tFunction)) { T = promisifyHelper.extractPromisifiedReturnType(expr); } else { T = F.returnTypeRef ?: G.anyTypeRef; } typeSystemHelper.addSubstitutions(G2, expr, targetTypeRef); G2 |- T ~> T T = versionResolver.resolveVersion(T, F); if (T instanceof BoundThisTypeRef && !(expr.receiver instanceof ThisLiteral || expr.receiver instanceof SuperLiteral)) { G2 |~ T /\ T } } */
      {
        RuleFailedException previousFailure = null;
        try {
          Pair<String, ParameterizedCallExpression> _mappedTo = Pair.<String, ParameterizedCallExpression>of(RuleEnvironmentExtensions.GUARD_TYPE_CALL_EXPRESSION, expr);
          final TypeRef inferring = this.<TypeRef>env(G, _mappedTo, TypeRef.class);
          /* G |- inferring ~> T */
          Result<TypeArgument> result_1 = substTypeVariablesInternal(G, _trace_, inferring);
          checkAssignableTo(result_1.getFirst(), TypeRef.class);
          T = (TypeRef) result_1.getFirst();
          
        } catch (Exception e) {
          previousFailure = extractRuleFailedException(e);
          final RuleEnvironment G2 = RuleEnvironmentExtensions.wrap(G);
          Pair<String, ParameterizedCallExpression> _mappedTo_1 = Pair.<String, ParameterizedCallExpression>of(RuleEnvironmentExtensions.GUARD_TYPE_CALL_EXPRESSION, expr);
          boolean _add = G2.add(_mappedTo_1, F.getReturnTypeRef());
          /* G2.add(GUARD_TYPE_CALL_EXPRESSION -> expr, F.returnTypeRef) */
          if (!_add) {
            sneakyThrowRuleFailedException("G2.add(GUARD_TYPE_CALL_EXPRESSION -> expr, F.returnTypeRef)");
          }
          if (((((expr.eContainer() instanceof AwaitExpression) && (expr.eContainmentFeature() == N4JSPackage.eINSTANCE.getAwaitExpression_Expression())) && (tFunction != null)) && AnnotationDefinition.PROMISIFIABLE.hasAnnotation(tFunction))) {
            T = this.promisifyHelper.extractPromisifiedReturnType(expr);
          } else {
            TypeRef _elvis = null;
            TypeRef _returnTypeRef = F.getReturnTypeRef();
            if (_returnTypeRef != null) {
              _elvis = _returnTypeRef;
            } else {
              ParameterizedTypeRef _anyTypeRef = RuleEnvironmentExtensions.anyTypeRef(G);
              _elvis = _anyTypeRef;
            }
            T = _elvis;
          }
          this.typeSystemHelper.addSubstitutions(G2, expr, targetTypeRef);
          /* G2 |- T ~> T */
          Result<TypeArgument> result_2 = substTypeVariablesInternal(G2, _trace_, T);
          checkAssignableTo(result_2.getFirst(), TypeRef.class);
          T = (TypeRef) result_2.getFirst();
          
          T = this.versionResolver.<TypeRef, FunctionTypeExprOrRef>resolveVersion(T, F);
          if (((T instanceof BoundThisTypeRef) && (!((expr.getReceiver() instanceof ThisLiteral) || (expr.getReceiver() instanceof SuperLiteral))))) {
            /* G2 |~ T /\ T */
            Result<TypeRef> result_3 = upperBoundInternal(G2, _trace_, T);
            checkAssignableTo(result_3.getFirst(), TypeRef.class);
            T = (TypeRef) result_3.getFirst();
            
          }
        }
      }
    } else {
      Type _declaredType = null;
      if (targetTypeRef!=null) {
        _declaredType=targetTypeRef.getDeclaredType();
      }
      TObjectPrototype _functionType = RuleEnvironmentExtensions.functionType(G);
      boolean _tripleEquals = (_declaredType == _functionType);
      if (_tripleEquals) {
        T = RuleEnvironmentExtensions.anyTypeRef(G);
      } else {
        boolean _isDynamic = targetTypeRef.isDynamic();
        if (_isDynamic) {
          T = RuleEnvironmentExtensions.anyTypeRefDynamic(G);
        } else {
          T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
        }
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Argument arg) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeArgument(G, _subtrace_, arg);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeArgument") + stringRepForEnv(G) + " |- " + stringRep(arg) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeArgument) {
    	typeThrowException(ruleName("typeArgument") + stringRepForEnv(G) + " |- " + stringRep(arg) + " : " + "TypeRef",
    		TYPEARGUMENT,
    		e_applyRuleTypeArgument, arg, new ErrorInformation[] {new ErrorInformation(arg)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeArgument(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Argument arg) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* G |- arg.expression: T */
    Expression _expression = arg.getExpression();
    Result<TypeRef> result = typeInternal(G, _trace_, _expression);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    T = (TypeRef) result.getFirst();
    
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PostfixExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypePostfixExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typePostfixExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypePostfixExpression) {
    	typeThrowException(ruleName("typePostfixExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "ParameterizedTypeRef",
    		TYPEPOSTFIXEXPRESSION,
    		e_applyRuleTypePostfixExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypePostfixExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PostfixExpression e) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypePostfixExpression_1(G, e));
  }
  
  private ParameterizedTypeRef _applyRuleTypePostfixExpression_1(final RuleEnvironment G, final PostfixExpression e) throws RuleFailedException {
    ParameterizedTypeRef _numberTypeRef = RuleEnvironmentExtensions.numberTypeRef(G);
    return _numberTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UnaryExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeUnaryExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeUnaryExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeUnaryExpression) {
    	typeThrowException(ruleName("typeUnaryExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "TypeRef",
    		TYPEUNARYEXPRESSION,
    		e_applyRuleTypeUnaryExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeUnaryExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UnaryExpression e) throws RuleFailedException {
    TypeRef T = null; // output parameter
    if ((((e.getOp() == UnaryOperator.NEG) || (e.getOp() == UnaryOperator.POS)) && (e.getExpression() instanceof IntLiteral))) {
      /* G |- e.expression : T */
      Expression _expression = e.getExpression();
      Result<TypeRef> result = typeInternal(G, _trace_, _expression);
      checkAssignableTo(result.getFirst(), TypeRef.class);
      T = (TypeRef) result.getFirst();
      
    } else {
      UnaryOperator _op = e.getOp();
      if (_op != null) {
        switch (_op) {
          case DELETE:
            T = RuleEnvironmentExtensions.booleanTypeRef(G);
            break;
          case VOID:
            T = RuleEnvironmentExtensions.undefinedTypeRef(G);
            break;
          case TYPEOF:
            T = RuleEnvironmentExtensions.stringTypeRef(G);
            break;
          case NOT:
            T = RuleEnvironmentExtensions.booleanTypeRef(G);
            break;
          default:
            T = RuleEnvironmentExtensions.numberTypeRef(G);
            break;
        }
      } else {
        T = RuleEnvironmentExtensions.numberTypeRef(G);
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final MultiplicativeExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeMultiplicativeExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeMultiplicativeExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeMultiplicativeExpression) {
    	typeThrowException(ruleName("typeMultiplicativeExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "ParameterizedTypeRef",
    		TYPEMULTIPLICATIVEEXPRESSION,
    		e_applyRuleTypeMultiplicativeExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeMultiplicativeExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final MultiplicativeExpression e) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeMultiplicativeExpression_1(G, e));
  }
  
  private ParameterizedTypeRef _applyRuleTypeMultiplicativeExpression_1(final RuleEnvironment G, final MultiplicativeExpression e) throws RuleFailedException {
    ParameterizedTypeRef _numberTypeRef = RuleEnvironmentExtensions.numberTypeRef(G);
    return _numberTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AdditiveExpression expr) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeAdditiveExpression(G, _subtrace_, expr);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeAdditiveExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeAdditiveExpression) {
    	typeThrowException(ruleName("typeAdditiveExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " : " + "TypeRef",
    		TYPEADDITIVEEXPRESSION,
    		e_applyRuleTypeAdditiveExpression, expr, new ErrorInformation[] {new ErrorInformation(expr)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeAdditiveExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AdditiveExpression expr) throws RuleFailedException {
    TypeRef T = null; // output parameter
    AdditiveOperator _op = expr.getOp();
    boolean _equals = Objects.equal(_op, AdditiveOperator.ADD);
    if (_equals) {
      /* G |- expr.lhs: var TypeRef l */
      Expression _lhs = expr.getLhs();
      TypeRef l = null;
      Result<TypeRef> result = typeInternal(G, _trace_, _lhs);
      checkAssignableTo(result.getFirst(), TypeRef.class);
      l = (TypeRef) result.getFirst();
      
      /* G |- expr.rhs: var TypeRef r */
      Expression _rhs = expr.getRhs();
      TypeRef r = null;
      Result<TypeRef> result_1 = typeInternal(G, _trace_, _rhs);
      checkAssignableTo(result_1.getFirst(), TypeRef.class);
      r = (TypeRef) result_1.getFirst();
      
      final boolean lunknown = (l instanceof UnknownTypeRef);
      final boolean runknown = (r instanceof UnknownTypeRef);
      if ((lunknown && runknown)) {
        T = r;
      } else {
        final boolean lnum = (Objects.equal(l.getDeclaredType(), RuleEnvironmentExtensions.booleanType(G)) || RuleEnvironmentExtensions.isNumeric(G, l.getDeclaredType()));
        final boolean rnum = (Objects.equal(r.getDeclaredType(), RuleEnvironmentExtensions.booleanType(G)) || RuleEnvironmentExtensions.isNumeric(G, r.getDeclaredType()));
        final boolean undef = (((Objects.equal(l.getDeclaredType(), RuleEnvironmentExtensions.undefinedType(G)) || Objects.equal(l.getDeclaredType(), RuleEnvironmentExtensions.nullType(G))) || Objects.equal(r.getDeclaredType(), RuleEnvironmentExtensions.undefinedType(G))) || Objects.equal(r.getDeclaredType(), RuleEnvironmentExtensions.nullType(G)));
        if (((lnum && rnum) || (undef && (lnum || rnum)))) {
          T = RuleEnvironmentExtensions.numberTypeRef(G);
        } else {
          if (((lunknown || runknown) && ((lnum || rnum) || undef))) {
            TypeRef _xifexpression = null;
            if (lunknown) {
              _xifexpression = l;
            } else {
              _xifexpression = r;
            }
            T = _xifexpression;
          } else {
            T = RuleEnvironmentExtensions.stringTypeRef(G);
          }
        }
      }
    } else {
      T = RuleEnvironmentExtensions.numberTypeRef(G);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ShiftExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeShiftExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeShiftExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeShiftExpression) {
    	typeThrowException(ruleName("typeShiftExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "ParameterizedTypeRef",
    		TYPESHIFTEXPRESSION,
    		e_applyRuleTypeShiftExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeShiftExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ShiftExpression e) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeShiftExpression_1(G, e));
  }
  
  private ParameterizedTypeRef _applyRuleTypeShiftExpression_1(final RuleEnvironment G, final ShiftExpression e) throws RuleFailedException {
    ParameterizedTypeRef _numberTypeRef = RuleEnvironmentExtensions.numberTypeRef(G);
    return _numberTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final RelationalExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeRelationalExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeRelationalExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeRelationalExpression) {
    	typeThrowException(ruleName("typeRelationalExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "ParameterizedTypeRef",
    		TYPERELATIONALEXPRESSION,
    		e_applyRuleTypeRelationalExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeRelationalExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final RelationalExpression e) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeRelationalExpression_1(G, e));
  }
  
  private ParameterizedTypeRef _applyRuleTypeRelationalExpression_1(final RuleEnvironment G, final RelationalExpression e) throws RuleFailedException {
    ParameterizedTypeRef _booleanTypeRef = RuleEnvironmentExtensions.booleanTypeRef(G);
    return _booleanTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final EqualityExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeEqualityExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeEqualityExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeEqualityExpression) {
    	typeThrowException(ruleName("typeEqualityExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "ParameterizedTypeRef",
    		TYPEEQUALITYEXPRESSION,
    		e_applyRuleTypeEqualityExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeEqualityExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final EqualityExpression e) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeEqualityExpression_1(G, e));
  }
  
  private ParameterizedTypeRef _applyRuleTypeEqualityExpression_1(final RuleEnvironment G, final EqualityExpression e) throws RuleFailedException {
    ParameterizedTypeRef _booleanTypeRef = RuleEnvironmentExtensions.booleanTypeRef(G);
    return _booleanTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BinaryBitwiseExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeBinaryBitwiseExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeBinaryBitwiseExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeBinaryBitwiseExpression) {
    	typeThrowException(ruleName("typeBinaryBitwiseExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "ParameterizedTypeRef",
    		TYPEBINARYBITWISEEXPRESSION,
    		e_applyRuleTypeBinaryBitwiseExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeBinaryBitwiseExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BinaryBitwiseExpression e) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeBinaryBitwiseExpression_1(G, e));
  }
  
  private ParameterizedTypeRef _applyRuleTypeBinaryBitwiseExpression_1(final RuleEnvironment G, final BinaryBitwiseExpression e) throws RuleFailedException {
    ParameterizedTypeRef _numberTypeRef = RuleEnvironmentExtensions.numberTypeRef(G);
    return _numberTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BinaryLogicalExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeBinaryLogicalExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeBinaryLogicalExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeBinaryLogicalExpression) {
    	typeThrowException(ruleName("typeBinaryLogicalExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "TypeRef",
    		TYPEBINARYLOGICALEXPRESSION,
    		e_applyRuleTypeBinaryLogicalExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeBinaryLogicalExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BinaryLogicalExpression e) throws RuleFailedException {
    TypeRef T = null; // output parameter
    final Expression lhs = e.getLhs();
    final Expression rhs = e.getRhs();
    boolean _xifexpression = false;
    if ((lhs instanceof ArrayLiteral)) {
      _xifexpression = ((ArrayLiteral)lhs).getElements().isEmpty();
    }
    final boolean lhsIsEmptyArrayLiteral = _xifexpression;
    boolean _xifexpression_1 = false;
    if ((rhs instanceof ArrayLiteral)) {
      _xifexpression_1 = ((ArrayLiteral)rhs).getElements().isEmpty();
    }
    final boolean rhsIsEmptyArrayLiteral = _xifexpression_1;
    /* G |- e.lhs : var TypeRef L */
    Expression _lhs = e.getLhs();
    TypeRef L = null;
    Result<TypeRef> result = typeInternal(G, _trace_, _lhs);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    L = (TypeRef) result.getFirst();
    
    /* G |- e.rhs : var TypeRef R */
    Expression _rhs = e.getRhs();
    TypeRef R = null;
    Result<TypeRef> result_1 = typeInternal(G, _trace_, _rhs);
    checkAssignableTo(result_1.getFirst(), TypeRef.class);
    R = (TypeRef) result_1.getFirst();
    
    boolean _and = false;
    boolean _lhsIsEmptyArrayLiteral = lhsIsEmptyArrayLiteral;
    if (!_lhsIsEmptyArrayLiteral) {
      _and = false;
    } else {
      Type _declaredType = null;
      if (R!=null) {
        _declaredType=R.getDeclaredType();
      }
      TObjectPrototype _arrayType = RuleEnvironmentExtensions.arrayType(G);
      boolean _tripleEquals = (_declaredType == _arrayType);
      _and = _tripleEquals;
    }
    if (_and) {
      T = R;
    } else {
      boolean _and_1 = false;
      boolean _rhsIsEmptyArrayLiteral = rhsIsEmptyArrayLiteral;
      if (!_rhsIsEmptyArrayLiteral) {
        _and_1 = false;
      } else {
        Type _declaredType_1 = null;
        if (L!=null) {
          _declaredType_1=L.getDeclaredType();
        }
        TObjectPrototype _arrayType_1 = RuleEnvironmentExtensions.arrayType(G);
        boolean _tripleEquals_1 = (_declaredType_1 == _arrayType_1);
        _and_1 = _tripleEquals_1;
      }
      if (_and_1) {
        T = L;
      } else {
        T = this.typeSystemHelper.createUnionType(G, L, R);
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ConditionalExpression expr) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeConditionalExpression(G, _subtrace_, expr);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeConditionalExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeConditionalExpression) {
    	typeThrowException(ruleName("typeConditionalExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " : " + "TypeRef",
    		TYPECONDITIONALEXPRESSION,
    		e_applyRuleTypeConditionalExpression, expr, new ErrorInformation[] {new ErrorInformation(expr)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeConditionalExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ConditionalExpression expr) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* G |- expr.trueExpression : var TypeRef left */
    Expression _trueExpression = expr.getTrueExpression();
    TypeRef left = null;
    Result<TypeRef> result = typeInternal(G, _trace_, _trueExpression);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    left = (TypeRef) result.getFirst();
    
    /* G |- expr.falseExpression : var TypeRef right */
    Expression _falseExpression = expr.getFalseExpression();
    TypeRef right = null;
    Result<TypeRef> result_1 = typeInternal(G, _trace_, _falseExpression);
    checkAssignableTo(result_1.getFirst(), TypeRef.class);
    right = (TypeRef) result_1.getFirst();
    
    T = this.typeSystemHelper.createUnionType(G, left, right);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AssignmentExpression expr) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeAssignmentExpression(G, _subtrace_, expr);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeAssignmentExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeAssignmentExpression) {
    	typeThrowException(ruleName("typeAssignmentExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " : " + "TypeRef",
    		TYPEASSIGNMENTEXPRESSION,
    		e_applyRuleTypeAssignmentExpression, expr, new ErrorInformation[] {new ErrorInformation(expr)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeAssignmentExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AssignmentExpression expr) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* { expr.op===AssignmentOperator.ASSIGN; G |- expr.rhs: T } or { expr.op===AssignmentOperator.ADD_ASSIGN G |- expr.lhs: var ParameterizedTypeRef l G |- expr.rhs: var ParameterizedTypeRef r val lnum = l.declaredType == G.booleanType || G.isNumeric(l.declaredType); val rnum = r.declaredType == G.booleanType || G.isNumeric(r.declaredType); val undef = l.declaredType == G.undefinedType || l.declaredType == G.nullType || r.declaredType == G.undefinedType || r.declaredType == G.nullType; !(lnum && rnum); !(undef && (lnum || rnum)); T = G.stringTypeRef } or { T = G.numberTypeRef } */
    {
      RuleFailedException previousFailure = null;
      try {
        AssignmentOperator _op = expr.getOp();
        boolean _tripleEquals = (_op == AssignmentOperator.ASSIGN);
        /* expr.op===AssignmentOperator.ASSIGN */
        if (!_tripleEquals) {
          sneakyThrowRuleFailedException("expr.op===AssignmentOperator.ASSIGN");
        }
        /* G |- expr.rhs: T */
        Expression _rhs = expr.getRhs();
        Result<TypeRef> result = typeInternal(G, _trace_, _rhs);
        checkAssignableTo(result.getFirst(), TypeRef.class);
        T = (TypeRef) result.getFirst();
        
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* { expr.op===AssignmentOperator.ADD_ASSIGN G |- expr.lhs: var ParameterizedTypeRef l G |- expr.rhs: var ParameterizedTypeRef r val lnum = l.declaredType == G.booleanType || G.isNumeric(l.declaredType); val rnum = r.declaredType == G.booleanType || G.isNumeric(r.declaredType); val undef = l.declaredType == G.undefinedType || l.declaredType == G.nullType || r.declaredType == G.undefinedType || r.declaredType == G.nullType; !(lnum && rnum); !(undef && (lnum || rnum)); T = G.stringTypeRef } or { T = G.numberTypeRef } */
        {
          try {
            AssignmentOperator _op_1 = expr.getOp();
            boolean _tripleEquals_1 = (_op_1 == AssignmentOperator.ADD_ASSIGN);
            /* expr.op===AssignmentOperator.ADD_ASSIGN */
            if (!_tripleEquals_1) {
              sneakyThrowRuleFailedException("expr.op===AssignmentOperator.ADD_ASSIGN");
            }
            /* G |- expr.lhs: var ParameterizedTypeRef l */
            Expression _lhs = expr.getLhs();
            ParameterizedTypeRef l = null;
            Result<TypeRef> result_1 = typeInternal(G, _trace_, _lhs);
            checkAssignableTo(result_1.getFirst(), ParameterizedTypeRef.class);
            l = (ParameterizedTypeRef) result_1.getFirst();
            
            /* G |- expr.rhs: var ParameterizedTypeRef r */
            Expression _rhs_1 = expr.getRhs();
            ParameterizedTypeRef r = null;
            Result<TypeRef> result_2 = typeInternal(G, _trace_, _rhs_1);
            checkAssignableTo(result_2.getFirst(), ParameterizedTypeRef.class);
            r = (ParameterizedTypeRef) result_2.getFirst();
            
            final boolean lnum = (Objects.equal(l.getDeclaredType(), RuleEnvironmentExtensions.booleanType(G)) || RuleEnvironmentExtensions.isNumeric(G, l.getDeclaredType()));
            final boolean rnum = (Objects.equal(r.getDeclaredType(), RuleEnvironmentExtensions.booleanType(G)) || RuleEnvironmentExtensions.isNumeric(G, r.getDeclaredType()));
            final boolean undef = (((Objects.equal(l.getDeclaredType(), RuleEnvironmentExtensions.undefinedType(G)) || Objects.equal(l.getDeclaredType(), RuleEnvironmentExtensions.nullType(G))) || Objects.equal(r.getDeclaredType(), RuleEnvironmentExtensions.undefinedType(G))) || Objects.equal(r.getDeclaredType(), RuleEnvironmentExtensions.nullType(G)));
            /* !(lnum && rnum) */
            if (!(!(lnum && rnum))) {
              sneakyThrowRuleFailedException("!(lnum && rnum)");
            }
            /* !(undef && (lnum || rnum)) */
            if (!(!(undef && (lnum || rnum)))) {
              sneakyThrowRuleFailedException("!(undef && (lnum || rnum))");
            }
            T = RuleEnvironmentExtensions.stringTypeRef(G);
          } catch (Exception e_1) {
            previousFailure = extractRuleFailedException(e_1);
            T = RuleEnvironmentExtensions.numberTypeRef(G);
          }
        }
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final CommaExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeCommaExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeCommaExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeCommaExpression) {
    	typeThrowException(ruleName("typeCommaExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "TypeRef",
    		TYPECOMMAEXPRESSION,
    		e_applyRuleTypeCommaExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeCommaExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final CommaExpression e) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* G |- e.exprs.last: T */
    Expression _last = IterableExtensions.<Expression>last(e.getExprs());
    Result<TypeRef> result = typeInternal(G, _trace_, _last);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    T = (TypeRef) result.getFirst();
    
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final CastExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeCastExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeCastExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeCastExpression) {
    	typeThrowException(ruleName("typeCastExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "TypeRef",
    		TYPECASTEXPRESSION,
    		e_applyRuleTypeCastExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeCastExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final CastExpression e) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = e.getTargetTypeRef();
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final N4ClassExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeN4ClassExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeN4ClassExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeN4ClassExpression) {
    	typeThrowException(ruleName("typeN4ClassExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "TypeRef",
    		TYPEN4CLASSEXPRESSION,
    		e_applyRuleTypeN4ClassExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeN4ClassExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final N4ClassExpression e) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = this.applyRuleTypeTypeDefiningElement(G, _trace_, e).getValue();
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionExpression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeFunctionExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeFunctionExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeFunctionExpression) {
    	typeThrowException(ruleName("typeFunctionExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "TypeRef",
    		TYPEFUNCTIONEXPRESSION,
    		e_applyRuleTypeFunctionExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeFunctionExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionExpression e) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = this.applyRuleTypeTypeDefiningElement(G, _trace_, e).getValue();
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Expression e) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeUnsupportedExpression(G, _subtrace_, e);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeUnsupportedExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeUnsupportedExpression) {
    	typeThrowException(ruleName("typeUnsupportedExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " : " + "TypeRef",
    		TYPEUNSUPPORTEDEXPRESSION,
    		e_applyRuleTypeUnsupportedExpression, e, new ErrorInformation[] {new ErrorInformation(e)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeUnsupportedExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Expression e) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = this.expressionTypeHelper.typeExpression(e, G);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final VariableDeclaration vdecl) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeVariableDeclaration(G, _subtrace_, vdecl);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeVariableDeclaration") + stringRepForEnv(G) + " |- " + stringRep(vdecl) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeVariableDeclaration) {
    	typeThrowException(ruleName("typeVariableDeclaration") + stringRepForEnv(G) + " |- " + stringRep(vdecl) + " : " + "TypeRef",
    		TYPEVARIABLEDECLARATION,
    		e_applyRuleTypeVariableDeclaration, vdecl, new ErrorInformation[] {new ErrorInformation(vdecl)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeVariableDeclaration(final RuleEnvironment G, final RuleApplicationTrace _trace_, final VariableDeclaration vdecl) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef _declaredTypeRef = vdecl.getDeclaredTypeRef();
    boolean _tripleNotEquals = (_declaredTypeRef != null);
    if (_tripleNotEquals) {
      T = vdecl.getDeclaredTypeRef();
    } else {
      EObject _eContainer = vdecl.eContainer();
      if ((_eContainer instanceof BindingElement)) {
        Expression _expression = vdecl.getExpression();
        Pair<String, Expression> _mappedTo = Pair.<String, Expression>of(RuleEnvironmentExtensions.GUARD_VARIABLE_DECLARATION, _expression);
        Object _get = G.get(_mappedTo);
        boolean _tripleEquals = (_get == null);
        if (_tripleEquals) {
          final RuleEnvironment G2 = RuleEnvironmentExtensions.wrap(G);
          Expression _expression_1 = vdecl.getExpression();
          Pair<String, Expression> _mappedTo_1 = Pair.<String, Expression>of(RuleEnvironmentExtensions.GUARD_VARIABLE_DECLARATION, _expression_1);
          boolean _add = G2.add(_mappedTo_1, Boolean.TRUE);
          /* G2.add(GUARD_VARIABLE_DECLARATION->vdecl.expression,Boolean.TRUE) */
          if (!_add) {
            sneakyThrowRuleFailedException("G2.add(GUARD_VARIABLE_DECLARATION->vdecl.expression,Boolean.TRUE)");
          }
          TypeRef _elvis = null;
          TypeRef _typeOfVariableDeclarationInDestructuringPattern = this.destructureHelper.getTypeOfVariableDeclarationInDestructuringPattern(G2, vdecl);
          if (_typeOfVariableDeclarationInDestructuringPattern != null) {
            _elvis = _typeOfVariableDeclarationInDestructuringPattern;
          } else {
            ParameterizedTypeRef _anyTypeRef = RuleEnvironmentExtensions.anyTypeRef(G);
            _elvis = _anyTypeRef;
          }
          T = _elvis;
        } else {
          T = RuleEnvironmentExtensions.anyTypeRef(G);
        }
      } else {
        if (((vdecl.eContainer() instanceof ForStatement) && ((ForStatement) vdecl.eContainer()).isForOf())) {
          EObject _eContainer_1 = vdecl.eContainer();
          final ForStatement forOfStmnt = ((ForStatement) _eContainer_1);
          EObject _eContainer_2 = vdecl.eContainer();
          Pair<String, EObject> _mappedTo_2 = Pair.<String, EObject>of(RuleEnvironmentExtensions.GUARD_VARIABLE_DECLARATION, _eContainer_2);
          Object _get_1 = G.get(_mappedTo_2);
          boolean _tripleEquals_1 = (_get_1 == null);
          if (_tripleEquals_1) {
            final RuleEnvironment G2_1 = RuleEnvironmentExtensions.wrap(G);
            EObject _eContainer_3 = vdecl.eContainer();
            Pair<String, EObject> _mappedTo_3 = Pair.<String, EObject>of(RuleEnvironmentExtensions.GUARD_VARIABLE_DECLARATION, _eContainer_3);
            boolean _add_1 = G2_1.add(_mappedTo_3, Boolean.TRUE);
            /* G2.add(GUARD_VARIABLE_DECLARATION->vdecl.eContainer,Boolean.TRUE) */
            if (!_add_1) {
              sneakyThrowRuleFailedException("G2.add(GUARD_VARIABLE_DECLARATION->vdecl.eContainer,Boolean.TRUE)");
            }
            /* { G2 |- forOfStmnt.expression : var TypeRef ofPartTypeRef val elemType = destructureHelper.extractIterableElementType(G2, ofPartTypeRef) elemType!==null G2 |~ elemType /\ T } or { T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef } */
            {
              RuleFailedException previousFailure = null;
              try {
                /* G2 |- forOfStmnt.expression : var TypeRef ofPartTypeRef */
                Expression _expression_2 = forOfStmnt.getExpression();
                TypeRef ofPartTypeRef = null;
                Result<TypeRef> result = typeInternal(G2_1, _trace_, _expression_2);
                checkAssignableTo(result.getFirst(), TypeRef.class);
                ofPartTypeRef = (TypeRef) result.getFirst();
                
                final TypeArgument elemType = this.destructureHelper.extractIterableElementType(G2_1, ofPartTypeRef);
                /* elemType!==null */
                if (!(elemType != null)) {
                  sneakyThrowRuleFailedException("elemType!==null");
                }
                /* G2 |~ elemType /\ T */
                Result<TypeRef> result_1 = upperBoundInternal(G2_1, _trace_, elemType);
                checkAssignableTo(result_1.getFirst(), TypeRef.class);
                T = (TypeRef) result_1.getFirst();
                
              } catch (Exception e) {
                previousFailure = extractRuleFailedException(e);
                T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
              }
            }
          } else {
            T = RuleEnvironmentExtensions.anyTypeRef(G);
          }
        } else {
          if (((vdecl.eContainer() instanceof ForStatement) && ((ForStatement) vdecl.eContainer()).isForIn())) {
            T = RuleEnvironmentExtensions.stringTypeRef(G);
          } else {
            Expression _expression_3 = vdecl.getExpression();
            boolean _tripleNotEquals_1 = (_expression_3 != null);
            if (_tripleNotEquals_1) {
              Expression _expression_4 = vdecl.getExpression();
              Pair<String, Expression> _mappedTo_4 = Pair.<String, Expression>of(RuleEnvironmentExtensions.GUARD_VARIABLE_DECLARATION, _expression_4);
              Object _get_2 = G.get(_mappedTo_4);
              boolean _tripleEquals_2 = (_get_2 == null);
              if (_tripleEquals_2) {
                final RuleEnvironment G2_2 = RuleEnvironmentExtensions.wrap(G);
                Expression _expression_5 = vdecl.getExpression();
                Pair<String, Expression> _mappedTo_5 = Pair.<String, Expression>of(RuleEnvironmentExtensions.GUARD_VARIABLE_DECLARATION, _expression_5);
                boolean _add_2 = G2_2.add(_mappedTo_5, Boolean.TRUE);
                /* G2.add(GUARD_VARIABLE_DECLARATION->vdecl.expression,Boolean.TRUE) */
                if (!_add_2) {
                  sneakyThrowRuleFailedException("G2.add(GUARD_VARIABLE_DECLARATION->vdecl.expression,Boolean.TRUE)");
                }
                /* G2 |- vdecl.expression: var TypeRef E */
                Expression _expression_6 = vdecl.getExpression();
                TypeRef E = null;
                Result<TypeRef> result_2 = typeInternal(G2_2, _trace_, _expression_6);
                checkAssignableTo(result_2.getFirst(), TypeRef.class);
                E = (TypeRef) result_2.getFirst();
                
                if (((E instanceof BoundThisTypeRef) || ((E instanceof TypeTypeRef) && (((TypeTypeRef) E).getTypeArg() instanceof BoundThisTypeRef)))) {
                } else {
                  /* G2 |~ E /\ E */
                  Result<TypeRef> result_3 = upperBoundInternal(G2_2, _trace_, E);
                  checkAssignableTo(result_3.getFirst(), TypeRef.class);
                  E = (TypeRef) result_3.getFirst();
                  
                }
                if ((((E.getDeclaredType() == RuleEnvironmentExtensions.undefinedType(G)) || (E.getDeclaredType() == RuleEnvironmentExtensions.nullType(G))) || (E.getDeclaredType() == RuleEnvironmentExtensions.voidType(G)))) {
                  T = RuleEnvironmentExtensions.anyTypeRef(G);
                } else {
                  T = E;
                }
              } else {
                T = RuleEnvironmentExtensions.anyTypeRef(G);
              }
            } else {
              T = RuleEnvironmentExtensions.anyTypeRef(G);
            }
          }
        }
      }
    }
    boolean _enforceDynamicTypes = this.jsVariantHelper.enforceDynamicTypes(vdecl);
    if (_enforceDynamicTypes) {
      T = this.typeSystemHelper.makeDynamic(T);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FormalParameter fpar) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeFormalParameter(G, _subtrace_, fpar);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeFormalParameter") + stringRepForEnv(G) + " |- " + stringRep(fpar) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeFormalParameter) {
    	typeThrowException(ruleName("typeFormalParameter") + stringRepForEnv(G) + " |- " + stringRep(fpar) + " : " + "TypeRef",
    		TYPEFORMALPARAMETER,
    		e_applyRuleTypeFormalParameter, fpar, new ErrorInformation[] {new ErrorInformation(fpar)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeFormalParameter(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FormalParameter fpar) throws RuleFailedException {
    TypeRef T = null; // output parameter
    final TypeRef fparTypeRef = fpar.getDeclaredTypeRef();
    if ((fparTypeRef != null)) {
      if (((fparTypeRef instanceof ThisTypeRefStructural) || ((fparTypeRef instanceof FunctionTypeExpression) && IteratorExtensions.<TFormalParameter>exists(Iterators.<TFormalParameter>filter(((FunctionTypeExpression) fparTypeRef).eAllContents(), TFormalParameter.class), ((Function1<TFormalParameter, Boolean>) (TFormalParameter currFpar) -> {
        TypeRef _typeRef = currFpar.getTypeRef();
        return Boolean.valueOf((_typeRef instanceof ThisTypeRef));
      }))))) {
        T = this.typeSystemHelper.bindAndSubstituteThisTypeRef(G, fparTypeRef, fparTypeRef);
      } else {
        TypeRef _xifexpression = null;
        TFormalParameter _definedTypeElement = null;
        if (fpar!=null) {
          _definedTypeElement=fpar.getDefinedTypeElement();
        }
        TypeRef _typeRef = null;
        if (_definedTypeElement!=null) {
          _typeRef=_definedTypeElement.getTypeRef();
        }
        boolean _tripleNotEquals = (_typeRef != null);
        if (_tripleNotEquals) {
          _xifexpression = fpar.getDefinedTypeElement().getTypeRef();
        } else {
          _xifexpression = fpar.getDeclaredTypeRef();
        }
        T = _xifexpression;
      }
    } else {
      boolean _isHasInitializerAssignment = fpar.isHasInitializerAssignment();
      if (_isHasInitializerAssignment) {
        Expression _initializer = fpar.getInitializer();
        boolean _tripleNotEquals_1 = (_initializer != null);
        if (_tripleNotEquals_1) {
          /* G |- fpar.initializer : var TypeRef E */
          Expression _initializer_1 = fpar.getInitializer();
          TypeRef E = null;
          Result<TypeRef> result = typeInternal(G, _trace_, _initializer_1);
          checkAssignableTo(result.getFirst(), TypeRef.class);
          E = (TypeRef) result.getFirst();
          
          T = this.typeSystemHelper.sanitizeTypeOfVariableFieldProperty(G, E);
        } else {
          T = RuleEnvironmentExtensions.anyTypeRef(G);
        }
      } else {
        boolean _enforceDynamicTypes = this.jsVariantHelper.enforceDynamicTypes(fpar);
        if (_enforceDynamicTypes) {
          T = RuleEnvironmentExtensions.anyTypeRefDynamic(G);
        } else {
          /* T = env(G, fpar, TypeRef) or T = G.anyTypeRef */
          {
            RuleFailedException previousFailure = null;
            try {
              T = this.<TypeRef>env(G, fpar, TypeRef.class);
            } catch (Exception e) {
              previousFailure = extractRuleFailedException(e);
              T = RuleEnvironmentExtensions.anyTypeRef(G);
            }
          }
        }
      }
    }
    T = TypeUtils.wrapIfVariadic(RuleEnvironmentExtensions.getPredefinedTypes(G).builtInTypeScope, T, fpar);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final CatchVariable catchVariable) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeCatchVariable(G, _subtrace_, catchVariable);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeCatchVariable") + stringRepForEnv(G) + " |- " + stringRep(catchVariable) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeCatchVariable) {
    	typeThrowException(ruleName("typeCatchVariable") + stringRepForEnv(G) + " |- " + stringRep(catchVariable) + " : " + "ParameterizedTypeRef",
    		TYPECATCHVARIABLE,
    		e_applyRuleTypeCatchVariable, catchVariable, new ErrorInformation[] {new ErrorInformation(catchVariable)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeCatchVariable(final RuleEnvironment G, final RuleApplicationTrace _trace_, final CatchVariable catchVariable) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeCatchVariable_1(G, catchVariable));
  }
  
  private ParameterizedTypeRef _applyRuleTypeCatchVariable_1(final RuleEnvironment G, final CatchVariable catchVariable) throws RuleFailedException {
    ParameterizedTypeRef _xifexpression = null;
    boolean _enforceDynamicTypes = this.jsVariantHelper.enforceDynamicTypes(catchVariable);
    if (_enforceDynamicTypes) {
      _xifexpression = RuleEnvironmentExtensions.anyTypeRefDynamic(G);
    } else {
      _xifexpression = RuleEnvironmentExtensions.anyTypeRef(G);
    }
    return _xifexpression;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final LocalArgumentsVariable lArgumentsVar) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeLocalArgumentsVariable(G, _subtrace_, lArgumentsVar);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeLocalArgumentsVariable") + stringRepForEnv(G) + " |- " + stringRep(lArgumentsVar) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeLocalArgumentsVariable) {
    	typeThrowException(ruleName("typeLocalArgumentsVariable") + stringRepForEnv(G) + " |- " + stringRep(lArgumentsVar) + " : " + "ParameterizedTypeRef",
    		TYPELOCALARGUMENTSVARIABLE,
    		e_applyRuleTypeLocalArgumentsVariable, lArgumentsVar, new ErrorInformation[] {new ErrorInformation(lArgumentsVar)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeLocalArgumentsVariable(final RuleEnvironment G, final RuleApplicationTrace _trace_, final LocalArgumentsVariable lArgumentsVar) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleTypeLocalArgumentsVariable_1(G, lArgumentsVar));
  }
  
  private ParameterizedTypeRef _applyRuleTypeLocalArgumentsVariable_1(final RuleEnvironment G, final LocalArgumentsVariable lArgumentsVar) throws RuleFailedException {
    ParameterizedTypeRef _argumentsTypeRef = RuleEnvironmentExtensions.argumentsTypeRef(G);
    return _argumentsTypeRef;
  }
  
  protected Result<TypeRef> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ModuleNamespaceVirtualType t) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleTypeModuleNamespace(G, _subtrace_, t);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("typeModuleNamespace") + stringRepForEnv(G) + " |- " + stringRep(t) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeModuleNamespace) {
    	typeThrowException(ruleName("typeModuleNamespace") + stringRepForEnv(G) + " |- " + stringRep(t) + " : " + "TypeRef",
    		TYPEMODULENAMESPACE,
    		e_applyRuleTypeModuleNamespace, t, new ErrorInformation[] {new ErrorInformation(t)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleTypeModuleNamespace(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ModuleNamespaceVirtualType t) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = TypeUtils.createTypeRef(t);
    return new Result<TypeRef>(T);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeArgument left, final TypeArgument right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeTypeArgument(G, _subtrace_, left, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeTypeArgument") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeTypeArgument) {
    	subtypeThrowException(ruleName("subtypeTypeArgument") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right),
    		SUBTYPETYPEARGUMENT,
    		e_applyRuleSubtypeTypeArgument, left, right, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeTypeArgument(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeArgument left, final TypeArgument right) throws RuleFailedException {
    /* G |~ left /\ var TypeRef leftUpper */
    TypeRef leftUpper = null;
    Result<TypeRef> result = upperBoundInternal(G, _trace_, left);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    leftUpper = (TypeRef) result.getFirst();
    
    /* G |~ right \/ var TypeRef rightLower */
    TypeRef rightLower = null;
    Result<TypeRef> result_1 = lowerBoundInternal(G, _trace_, right);
    checkAssignableTo(result_1.getFirst(), TypeRef.class);
    rightLower = (TypeRef) result_1.getFirst();
    
    if (((leftUpper == left) && (rightLower == right))) {
      /* fail */
      throwForExplicitFail();
      /* "Prevent endless loop, check rules, see subtypeTypeArgument" */
    } else {
      /* G |- leftUpper <: rightLower */
      subtypeInternal(G, _trace_, leftUpper, rightLower);
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef left, final TypeRef right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeTypeRef(G, _subtrace_, left, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeTypeRef) {
    	subtypeThrowException(ruleName("subtypeTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right),
    		SUBTYPETYPEREF,
    		e_applyRuleSubtypeTypeRef, left, right, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef left, final TypeRef right) throws RuleFailedException {
    /* right.declaredType instanceof AnyType || left.declaredType instanceof NullType || left.declaredType instanceof UndefinedType */
    if (!(((right.getDeclaredType() instanceof AnyType) || (left.getDeclaredType() instanceof NullType)) || (left.getDeclaredType() instanceof UndefinedType))) {
      sneakyThrowRuleFailedException("right.declaredType instanceof AnyType || left.declaredType instanceof NullType || left.declaredType instanceof UndefinedType");
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UnknownTypeRef left, final TypeRef right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeUnknownTypeRef_Left(G, _subtrace_, left, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeUnknownTypeRef_Left") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeUnknownTypeRef_Left) {
    	subtypeThrowException(ruleName("subtypeUnknownTypeRef_Left") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right),
    		SUBTYPEUNKNOWNTYPEREF_LEFT,
    		e_applyRuleSubtypeUnknownTypeRef_Left, left, right, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeUnknownTypeRef_Left(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UnknownTypeRef left, final TypeRef right) throws RuleFailedException {
    /* true */
    if (!true) {
      sneakyThrowRuleFailedException("true");
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef left, final UnknownTypeRef right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeUnknownTypeRef_Right(G, _subtrace_, left, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeUnknownTypeRef_Right") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeUnknownTypeRef_Right) {
    	subtypeThrowException(ruleName("subtypeUnknownTypeRef_Right") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right),
    		SUBTYPEUNKNOWNTYPEREF_RIGHT,
    		e_applyRuleSubtypeUnknownTypeRef_Right, left, right, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeUnknownTypeRef_Right(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef left, final UnknownTypeRef right) throws RuleFailedException {
    /* true */
    if (!true) {
      sneakyThrowRuleFailedException("true");
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedTypeRef leftOriginal, final ParameterizedTypeRef rightOriginal) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeParameterizedTypeRef(G, _subtrace_, leftOriginal, rightOriginal);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeParameterizedTypeRef") + stringRepForEnv(G) + " |- " + stringRep(leftOriginal) + " <: " + stringRep(rightOriginal);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeParameterizedTypeRef) {
    	subtypeThrowException(ruleName("subtypeParameterizedTypeRef") + stringRepForEnv(G) + " |- " + stringRep(leftOriginal) + " <: " + stringRep(rightOriginal),
    		SUBTYPEPARAMETERIZEDTYPEREF,
    		e_applyRuleSubtypeParameterizedTypeRef, leftOriginal, rightOriginal, new ErrorInformation[] {new ErrorInformation(leftOriginal), new ErrorInformation(rightOriginal)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeParameterizedTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedTypeRef leftOriginal, final ParameterizedTypeRef rightOriginal) throws RuleFailedException {
    final TypeRef left = RuleEnvironmentExtensions.getReplacement(G, leftOriginal);
    final TypeRef right = RuleEnvironmentExtensions.getReplacement(G, rightOriginal);
    final Type leftDeclType = left.getDeclaredType();
    final Type rightDeclType = right.getDeclaredType();
    if (((leftDeclType == null) || (rightDeclType == null))) {
      /* true */
      if (!true) {
        sneakyThrowRuleFailedException("true");
      }
    } else {
      if ((leftDeclType.eIsProxy() || rightDeclType.eIsProxy())) {
        /* true */
        if (!true) {
          sneakyThrowRuleFailedException("true");
        }
      } else {
        if (((leftDeclType instanceof VoidType) || (rightDeclType instanceof VoidType))) {
          /* leftDeclType instanceof VoidType && rightDeclType instanceof VoidType */
          if (!((leftDeclType instanceof VoidType) && (rightDeclType instanceof VoidType))) {
            sneakyThrowRuleFailedException("leftDeclType instanceof VoidType && rightDeclType instanceof VoidType");
          }
        } else {
          if ((((leftDeclType instanceof UndefinedType) || ((leftDeclType instanceof NullType) && (!(rightDeclType instanceof UndefinedType)))) || (rightDeclType instanceof AnyType))) {
            /* true */
            if (!true) {
              sneakyThrowRuleFailedException("true");
            }
          } else {
            if ((((leftDeclType == RuleEnvironmentExtensions.intType(G)) && (rightDeclType == RuleEnvironmentExtensions.numberType(G))) || ((leftDeclType == RuleEnvironmentExtensions.numberType(G)) && (rightDeclType == RuleEnvironmentExtensions.intType(G))))) {
              /* true */
              if (!true) {
                sneakyThrowRuleFailedException("true");
              }
            } else {
              if (((leftDeclType instanceof TEnum) && (rightDeclType == RuleEnvironmentExtensions.n4EnumType(G)))) {
                boolean _hasAnnotation = AnnotationDefinition.STRING_BASED.hasAnnotation(leftDeclType);
                /* !AnnotationDefinition.STRING_BASED.hasAnnotation( leftDeclType ) */
                if (!(!_hasAnnotation)) {
                  sneakyThrowRuleFailedException("!AnnotationDefinition.STRING_BASED.hasAnnotation( leftDeclType )");
                }
              } else {
                if (((leftDeclType instanceof TEnum) && (((rightDeclType == RuleEnvironmentExtensions.n4StringBasedEnumType(G)) || (rightDeclType == RuleEnvironmentExtensions.stringType(G))) || (rightDeclType == RuleEnvironmentExtensions.stringObjectType(G))))) {
                  /* AnnotationDefinition.STRING_BASED.hasAnnotation( leftDeclType ) */
                  if (!AnnotationDefinition.STRING_BASED.hasAnnotation(leftDeclType)) {
                    sneakyThrowRuleFailedException("AnnotationDefinition.STRING_BASED.hasAnnotation( leftDeclType )");
                  }
                } else {
                  if (((leftDeclType == RuleEnvironmentExtensions.n4StringBasedEnumType(G)) && ((rightDeclType == RuleEnvironmentExtensions.stringType(G)) || (rightDeclType == RuleEnvironmentExtensions.stringObjectType(G))))) {
                    /* true */
                    if (!true) {
                      sneakyThrowRuleFailedException("true");
                    }
                  } else {
                    if (((leftDeclType instanceof PrimitiveType) && (((PrimitiveType) leftDeclType).getAssignmentCompatible() == rightDeclType))) {
                      /* true */
                      if (!true) {
                        sneakyThrowRuleFailedException("true");
                      }
                    } else {
                      if (((rightDeclType instanceof PrimitiveType) && (leftDeclType == ((PrimitiveType) rightDeclType).getAssignmentCompatible()))) {
                        /* true */
                        if (!true) {
                          sneakyThrowRuleFailedException("true");
                        }
                      } else {
                        if (((((leftDeclType instanceof TInterface) && (!(rightDeclType instanceof TInterface))) && Objects.equal(right.getTypingStrategy(), TypingStrategy.NOMINAL)) && (!(((rightDeclType == RuleEnvironmentExtensions.n4ObjectType(G)) || (rightDeclType == RuleEnvironmentExtensions.objectType(G))) || (rightDeclType == RuleEnvironmentExtensions.anyType(G)))))) {
                          /* false */
                          if (!false) {
                            sneakyThrowRuleFailedException("false");
                          }
                        } else {
                          boolean structuralTyping = false;
                          boolean _isUseSiteStructuralTyping = right.isUseSiteStructuralTyping();
                          if (_isUseSiteStructuralTyping) {
                            final StructuralTypingResult result = this.typeSystemHelper.isStructuralSubtype(G, left, right);
                            boolean _isValue = result.isValue();
                            boolean _not = (!_isValue);
                            if (_not) {
                              /* fail error result.message data PRIORITY_ERROR */
                              String _message = result.getMessage();
                              String error = _message;
                              Object data = TypeSystemErrorExtensions.PRIORITY_ERROR;
                              throwForExplicitFail(error, new ErrorInformation(null, null, data));
                            }
                            structuralTyping = true;
                          } else {
                            boolean _isDefSiteStructuralTyping = right.isDefSiteStructuralTyping();
                            if (_isDefSiteStructuralTyping) {
                              Pair<TypeRef, TypeRef> _mappedTo = Pair.<TypeRef, TypeRef>of(left, right);
                              Pair<String, Pair<TypeRef, TypeRef>> _mappedTo_1 = Pair.<String, Pair<TypeRef, TypeRef>>of(RuleEnvironmentExtensions.GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__STRUCT, _mappedTo);
                              Object _get = G.get(_mappedTo_1);
                              final Boolean guard = ((Boolean) _get);
                              if (((guard == null) || (!(guard).booleanValue()))) {
                                final StructuralTypingResult result_1 = this.typeSystemHelper.isStructuralSubtype(G, left, right);
                                boolean _isValue_1 = result_1.isValue();
                                boolean _not_1 = (!_isValue_1);
                                if (_not_1) {
                                  boolean _and = false;
                                  boolean _isN4ObjectOnLeftWithDefSite = result_1.isN4ObjectOnLeftWithDefSite();
                                  if (!_isN4ObjectOnLeftWithDefSite) {
                                    _and = false;
                                  } else {
                                    /* G.wrap, (GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__STRUCT->(left->right))<-true |- left <: right */
                                    RuleEnvironment _wrap = RuleEnvironmentExtensions.wrap(G);
                                    Pair<TypeRef, TypeRef> _mappedTo_2 = Pair.<TypeRef, TypeRef>of(left, right);
                                    Pair<String, Pair<TypeRef, TypeRef>> _mappedTo_3 = Pair.<String, Pair<TypeRef, TypeRef>>of(RuleEnvironmentExtensions.GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__STRUCT, _mappedTo_2);
                                    boolean _ruleinvocation = subtypeSucceeded(environmentComposition(
                                      _wrap, environmentEntry(_mappedTo_3, true)
                                    ), _trace_, left, right);
                                    _and = _ruleinvocation;
                                  }
                                  if (_and) {
                                    structuralTyping = true;
                                  } else {
                                    /* fail error result.message data PRIORITY_ERROR */
                                    String _message_1 = result_1.getMessage();
                                    String error_1 = _message_1;
                                    Object data_1 = TypeSystemErrorExtensions.PRIORITY_ERROR;
                                    throwForExplicitFail(error_1, new ErrorInformation(null, null, data_1));
                                  }
                                }
                                structuralTyping = result_1.isValue();
                              }
                            }
                          }
                          if ((!structuralTyping)) {
                            boolean _and_1 = false;
                            if (!((left.isUseSiteStructuralTyping() || left.isDefSiteStructuralTyping()) && 
                              (!((leftDeclType instanceof TypeVariable) || (rightDeclType instanceof TypeVariable))))) {
                              _and_1 = false;
                            } else {
                              /* G |- right <: G.n4ObjectTypeRef */
                              ParameterizedTypeRef _n4ObjectTypeRef = RuleEnvironmentExtensions.n4ObjectTypeRef(G);
                              boolean _ruleinvocation_1 = subtypeSucceeded(G, _trace_, right, _n4ObjectTypeRef);
                              _and_1 = _ruleinvocation_1;
                            }
                            if (_and_1) {
                              /* fail error "Structural type " + left.typeRefAsString + " is not a subtype of non-structural type " + right.typeRefAsString data PRIORITY_ERROR */
                              String _typeRefAsString = left.getTypeRefAsString();
                              String _plus = ("Structural type " + _typeRefAsString);
                              String _plus_1 = (_plus + " is not a subtype of non-structural type ");
                              String _typeRefAsString_1 = right.getTypeRefAsString();
                              String _plus_2 = (_plus_1 + _typeRefAsString_1);
                              String error_2 = _plus_2;
                              Object data_2 = TypeSystemErrorExtensions.PRIORITY_ERROR;
                              throwForExplicitFail(error_2, new ErrorInformation(null, null, data_2));
                            }
                            if (((leftDeclType instanceof TypeVariable) || (rightDeclType instanceof TypeVariable))) {
                              boolean _equals = Objects.equal(leftDeclType, rightDeclType);
                              if (_equals) {
                                /* true */
                                if (!true) {
                                  sneakyThrowRuleFailedException("true");
                                }
                              } else {
                                if ((leftDeclType instanceof TypeVariable)) {
                                  TypeRef _elvis = null;
                                  TypeRef _declaredUpperBound = ((TypeVariable)leftDeclType).getDeclaredUpperBound();
                                  if (_declaredUpperBound != null) {
                                    _elvis = _declaredUpperBound;
                                  } else {
                                    TypeRef _typeVariableImplicitUpperBound = N4JSLanguageUtils.getTypeVariableImplicitUpperBound(G);
                                    _elvis = _typeVariableImplicitUpperBound;
                                  }
                                  final TypeRef ub = _elvis;
                                  /* G |- ub <: right */
                                  subtypeInternal(G, _trace_, ub, right);
                                } else {
                                  /* false */
                                  if (!false) {
                                    sneakyThrowRuleFailedException("false");
                                  }
                                }
                              }
                            } else {
                              boolean _equals_1 = Objects.equal(leftDeclType, rightDeclType);
                              if (_equals_1) {
                                if (((left.getTypeArgs().size() > 0) && (left.getTypeArgs().size() <= right.getTypeArgs().size()))) {
                                  final int len = Math.min(Math.min(left.getTypeArgs().size(), right.getTypeArgs().size()), rightDeclType.getTypeVars().size());
                                  for (int i = 0; (i < len); i++) {
                                    final TypeArgument leftArg = left.getTypeArgs().get(i);
                                    final TypeArgument rightArg = right.getTypeArgs().get(i);
                                    final Variance variance = rightDeclType.getVarianceOfTypeVar(i);
                                    TypeRef leftArgUpper = null;
                                    /* G |~ leftArg /\ leftArgUpper */
                                    Result<TypeRef> result_2 = upperBoundInternal(G, _trace_, leftArg);
                                    checkAssignableTo(result_2.getFirst(), TypeRef.class);
                                    leftArgUpper = (TypeRef) result_2.getFirst();
                                    
                                    TypeRef leftArgLower = null;
                                    /* G |~ leftArg \/ leftArgLower */
                                    Result<TypeRef> result_3 = lowerBoundInternal(G, _trace_, leftArg);
                                    checkAssignableTo(result_3.getFirst(), TypeRef.class);
                                    leftArgLower = (TypeRef) result_3.getFirst();
                                    
                                    TypeRef rightArgUpper = null;
                                    /* G |~ rightArg /\ rightArgUpper */
                                    Result<TypeRef> result_4 = upperBoundInternal(G, _trace_, rightArg);
                                    checkAssignableTo(result_4.getFirst(), TypeRef.class);
                                    rightArgUpper = (TypeRef) result_4.getFirst();
                                    
                                    TypeRef rightArgLower = null;
                                    /* G |~ rightArg \/ rightArgLower */
                                    Result<TypeRef> result_5 = lowerBoundInternal(G, _trace_, rightArg);
                                    checkAssignableTo(result_5.getFirst(), TypeRef.class);
                                    rightArgLower = (TypeRef) result_5.getFirst();
                                    
                                    RuleEnvironment G2 = null;
                                    if (((rightArg instanceof Wildcard) && ((Wildcard) rightArg).isImplicitUpperBoundInEffect())) {
                                      Pair<String, TypeArgument> _mappedTo_4 = Pair.<String, TypeArgument>of(RuleEnvironmentExtensions.GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__ARGS, rightArg);
                                      Object _get_1 = G.get(_mappedTo_4);
                                      final boolean isGuarded = (_get_1 != null);
                                      if ((!isGuarded)) {
                                        G2 = RuleEnvironmentExtensions.wrap(G);
                                        Pair<String, TypeArgument> _mappedTo_5 = Pair.<String, TypeArgument>of(RuleEnvironmentExtensions.GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__ARGS, rightArg);
                                        /* G2.add(GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__ARGS->(rightArg), Boolean.TRUE) */
                                        if (!G2.add(_mappedTo_5, Boolean.TRUE)) {
                                          sneakyThrowRuleFailedException("G2.add(GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__ARGS->(rightArg), Boolean.TRUE)");
                                        }
                                      } else {
                                        rightArgUpper = RuleEnvironmentExtensions.topTypeRef(G);
                                        G2 = G;
                                      }
                                    } else {
                                      G2 = G;
                                    }
                                    /* { if(variance!=Variance.CONTRA) { G2 |- leftArgUpper <: rightArgUpper } if(variance!=Variance.CO) { G2 |- rightArgLower <: leftArgLower } } or { if(previousFailure.isOrCausedByPriorityError) { fail error stringRep(left) + " is not a subtype of " + stringRep(right) + " due to incompatible type arguments: " + previousFailure.compileMessage data PRIORITY_ERROR } else { fail } } */
                                    {
                                      RuleFailedException previousFailure = null;
                                      try {
                                        boolean _notEquals = (!Objects.equal(variance, Variance.CONTRA));
                                        if (_notEquals) {
                                          /* G2 |- leftArgUpper <: rightArgUpper */
                                          subtypeInternal(G2, _trace_, leftArgUpper, rightArgUpper);
                                        }
                                        boolean _notEquals_1 = (!Objects.equal(variance, Variance.CO));
                                        if (_notEquals_1) {
                                          /* G2 |- rightArgLower <: leftArgLower */
                                          subtypeInternal(G2, _trace_, rightArgLower, leftArgLower);
                                        }
                                      } catch (Exception e) {
                                        previousFailure = extractRuleFailedException(e);
                                        boolean _isOrCausedByPriorityError = TypeSystemErrorExtensions.isOrCausedByPriorityError(previousFailure);
                                        if (_isOrCausedByPriorityError) {
                                          /* fail error stringRep(left) + " is not a subtype of " + stringRep(right) + " due to incompatible type arguments: " + previousFailure.compileMessage data PRIORITY_ERROR */
                                          String _stringRep = this.stringRep(left);
                                          String _plus_3 = (_stringRep + " is not a subtype of ");
                                          String _stringRep_1 = this.stringRep(right);
                                          String _plus_4 = (_plus_3 + _stringRep_1);
                                          String _plus_5 = (_plus_4 + " due to incompatible type arguments: ");
                                          String _compileMessage = TypeSystemErrorExtensions.compileMessage(previousFailure);
                                          String _plus_6 = (_plus_5 + _compileMessage);
                                          String error_3 = _plus_6;
                                          Object data_3 = TypeSystemErrorExtensions.PRIORITY_ERROR;
                                          throwForExplicitFail(error_3, new ErrorInformation(null, null, data_3));
                                        } else {
                                          /* fail */
                                          throwForExplicitFail();
                                        }
                                      }
                                    }
                                  }
                                }
                              } else {
                                List<ParameterizedTypeRef> _xifexpression = null;
                                if ((leftDeclType instanceof ContainerType<?>)) {
                                  _xifexpression = AllSuperTypeRefsCollector.collect(((ContainerType<?>)leftDeclType));
                                } else {
                                  _xifexpression = CollectionLiterals.<ParameterizedTypeRef>newArrayList();
                                }
                                final List<ParameterizedTypeRef> allSuperTypeRefs = _xifexpression;
                                List<ParameterizedTypeRef> _collectAllImplicitSuperTypes = RuleEnvironmentExtensions.collectAllImplicitSuperTypes(G, left);
                                final Iterable<ParameterizedTypeRef> superTypeRefs = Iterables.<ParameterizedTypeRef>concat(allSuperTypeRefs, _collectAllImplicitSuperTypes);
                                final Function1<ParameterizedTypeRef, Boolean> _function = (ParameterizedTypeRef it) -> {
                                  Type _declaredType = it.getDeclaredType();
                                  return Boolean.valueOf((_declaredType == rightDeclType));
                                };
                                boolean _exists = IterableExtensions.<ParameterizedTypeRef>exists(superTypeRefs, _function);
                                if (_exists) {
                                  final RuleEnvironment localG_left = RuleEnvironmentExtensions.wrap(G);
                                  this.typeSystemHelper.addSubstitutions(localG_left, left);
                                  final Function1<TypeVariable, TypeRef> _function_1 = (TypeVariable it) -> {
                                    return TypeExtensions.ref(it);
                                  };
                                  final TypeRef syntheticTypeRef = TypeExtensions.ref(rightDeclType, ((TypeArgument[])Conversions.unwrapArray(ListExtensions.<TypeVariable, TypeRef>map(rightDeclType.getTypeVars(), _function_1), TypeArgument.class)));
                                  /* localG_left |- syntheticTypeRef ~> var TypeRef effectiveSuperTypeRef */
                                  TypeRef effectiveSuperTypeRef = null;
                                  Result<TypeArgument> result_2 = substTypeVariablesInternal(localG_left, _trace_, syntheticTypeRef);
                                  checkAssignableTo(result_2.getFirst(), TypeRef.class);
                                  effectiveSuperTypeRef = (TypeRef) result_2.getFirst();
                                  
                                  /* G |- effectiveSuperTypeRef <: right */
                                  subtypeInternal(G, _trace_, effectiveSuperTypeRef, right);
                                } else {
                                  /* false */
                                  if (!false) {
                                    sneakyThrowRuleFailedException("false");
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UnionTypeExpression U, final TypeRef S) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeUnion_Left(G, _subtrace_, U, S);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeUnion_Left") + stringRepForEnv(G) + " |- " + stringRep(U) + " <: " + stringRep(S);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeUnion_Left) {
    	subtypeThrowException(ruleName("subtypeUnion_Left") + stringRepForEnv(G) + " |- " + stringRep(U) + " <: " + stringRep(S),
    		SUBTYPEUNION_LEFT,
    		e_applyRuleSubtypeUnion_Left, U, S, new ErrorInformation[] {new ErrorInformation(U), new ErrorInformation(S)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeUnion_Left(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UnionTypeExpression U, final TypeRef S) throws RuleFailedException {
    final Function1<TypeRef, Boolean> _function = (TypeRef T) -> {
      /* G |- T <: S */
      boolean _ruleinvocation = subtypeSucceeded(G, _trace_, T, S);
      return Boolean.valueOf(_ruleinvocation);
    };
    /* U.typeRefs.forall[T| G |- T <: S ] */
    if (!IterableExtensions.<TypeRef>forall(U.getTypeRefs(), _function)) {
      sneakyThrowRuleFailedException("U.typeRefs.forall[T| G |- T <: S ]");
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef S, final UnionTypeExpression U) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeUnion_Right(G, _subtrace_, S, U);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeUnion_Right") + stringRepForEnv(G) + " |- " + stringRep(S) + " <: " + stringRep(U);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeUnion_Right) {
    	subtypeThrowException(ruleName("subtypeUnion_Right") + stringRepForEnv(G) + " |- " + stringRep(S) + " <: " + stringRep(U),
    		SUBTYPEUNION_RIGHT,
    		e_applyRuleSubtypeUnion_Right, S, U, new ErrorInformation[] {new ErrorInformation(S), new ErrorInformation(U)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeUnion_Right(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef S, final UnionTypeExpression U) throws RuleFailedException {
    final Function1<TypeRef, Boolean> _function = (TypeRef T) -> {
      /* G |- S <: T */
      boolean _ruleinvocation = subtypeSucceeded(G, _trace_, S, T);
      return Boolean.valueOf(_ruleinvocation);
    };
    /* U.typeRefs.exists[T| G |- S <: T ] */
    if (!IterableExtensions.<TypeRef>exists(U.getTypeRefs(), _function)) {
      sneakyThrowRuleFailedException("U.typeRefs.exists[T| G |- S <: T ]");
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IntersectionTypeExpression S, final IntersectionTypeExpression I) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeIntersection_LeftRight(G, _subtrace_, S, I);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeIntersection_LeftRight") + stringRepForEnv(G) + " |- " + stringRep(S) + " <: " + stringRep(I);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeIntersection_LeftRight) {
    	subtypeThrowException(ruleName("subtypeIntersection_LeftRight") + stringRepForEnv(G) + " |- " + stringRep(S) + " <: " + stringRep(I),
    		SUBTYPEINTERSECTION_LEFTRIGHT,
    		e_applyRuleSubtypeIntersection_LeftRight, S, I, new ErrorInformation[] {new ErrorInformation(S), new ErrorInformation(I)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeIntersection_LeftRight(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IntersectionTypeExpression S, final IntersectionTypeExpression I) throws RuleFailedException {
    final Function1<TypeRef, Boolean> _function = (TypeRef T) -> {
      /* G |- S <: T */
      boolean _ruleinvocation = subtypeSucceeded(G, _trace_, S, T);
      return Boolean.valueOf(_ruleinvocation);
    };
    /* I.typeRefs.forall[T| G |- S <: T ] */
    if (!IterableExtensions.<TypeRef>forall(I.getTypeRefs(), _function)) {
      sneakyThrowRuleFailedException("I.typeRefs.forall[T| G |- S <: T ]");
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IntersectionTypeExpression I, final TypeRef S) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeIntersection_Left(G, _subtrace_, I, S);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeIntersection_Left") + stringRepForEnv(G) + " |- " + stringRep(I) + " <: " + stringRep(S);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeIntersection_Left) {
    	subtypeThrowException(ruleName("subtypeIntersection_Left") + stringRepForEnv(G) + " |- " + stringRep(I) + " <: " + stringRep(S),
    		SUBTYPEINTERSECTION_LEFT,
    		e_applyRuleSubtypeIntersection_Left, I, S, new ErrorInformation[] {new ErrorInformation(I), new ErrorInformation(S)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeIntersection_Left(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IntersectionTypeExpression I, final TypeRef S) throws RuleFailedException {
    final Function1<TypeRef, Boolean> _function = (TypeRef T) -> {
      /* G |- T <: S */
      boolean _ruleinvocation = subtypeSucceeded(G, _trace_, T, S);
      return Boolean.valueOf(_ruleinvocation);
    };
    /* I.typeRefs.exists[T| G |- T <: S ] */
    if (!IterableExtensions.<TypeRef>exists(I.getTypeRefs(), _function)) {
      sneakyThrowRuleFailedException("I.typeRefs.exists[T| G |- T <: S ]");
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef S, final IntersectionTypeExpression I) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeIntersection_Right(G, _subtrace_, S, I);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeIntersection_Right") + stringRepForEnv(G) + " |- " + stringRep(S) + " <: " + stringRep(I);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeIntersection_Right) {
    	subtypeThrowException(ruleName("subtypeIntersection_Right") + stringRepForEnv(G) + " |- " + stringRep(S) + " <: " + stringRep(I),
    		SUBTYPEINTERSECTION_RIGHT,
    		e_applyRuleSubtypeIntersection_Right, S, I, new ErrorInformation[] {new ErrorInformation(S), new ErrorInformation(I)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeIntersection_Right(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef S, final IntersectionTypeExpression I) throws RuleFailedException {
    final Function1<TypeRef, Boolean> _function = (TypeRef T) -> {
      /* G |- S <: T */
      boolean _ruleinvocation = subtypeSucceeded(G, _trace_, S, T);
      return Boolean.valueOf(_ruleinvocation);
    };
    /* I.typeRefs.forall[T| G |- S <: T ] */
    if (!IterableExtensions.<TypeRef>forall(I.getTypeRefs(), _function)) {
      sneakyThrowRuleFailedException("I.typeRefs.forall[T| G |- S <: T ]");
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BoundThisTypeRef left, final BoundThisTypeRef right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeBoundThisTypeRef(G, _subtrace_, left, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeBoundThisTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeBoundThisTypeRef) {
    	subtypeThrowException(ruleName("subtypeBoundThisTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right),
    		SUBTYPEBOUNDTHISTYPEREF,
    		e_applyRuleSubtypeBoundThisTypeRef, left, right, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeBoundThisTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BoundThisTypeRef left, final BoundThisTypeRef right) throws RuleFailedException {
    boolean _isUseSiteStructuralTyping = right.isUseSiteStructuralTyping();
    if (_isUseSiteStructuralTyping) {
      final StructuralTypingResult result = this.typeSystemHelper.isStructuralSubtype(G, left, right);
      boolean _isValue = result.isValue();
      boolean _not = (!_isValue);
      if (_not) {
        /* fail error result.message data PRIORITY_ERROR */
        String _message = result.getMessage();
        String error = _message;
        Object data = TypeSystemErrorExtensions.PRIORITY_ERROR;
        throwForExplicitFail(error, new ErrorInformation(null, null, data));
      }
    } else {
      boolean _isUseSiteStructuralTyping_1 = left.isUseSiteStructuralTyping();
      boolean _isUseSiteStructuralTyping_2 = right.isUseSiteStructuralTyping();
      boolean _tripleEquals = (Boolean.valueOf(_isUseSiteStructuralTyping_1) == Boolean.valueOf(_isUseSiteStructuralTyping_2));
      /* left.useSiteStructuralTyping === right.useSiteStructuralTyping */
      if (!_tripleEquals) {
        sneakyThrowRuleFailedException("left.useSiteStructuralTyping === right.useSiteStructuralTyping");
      }
      /* G |- left.actualThisTypeRef <: right.actualThisTypeRef */
      ParameterizedTypeRef _actualThisTypeRef = left.getActualThisTypeRef();
      ParameterizedTypeRef _actualThisTypeRef_1 = right.getActualThisTypeRef();
      subtypeInternal(G, _trace_, _actualThisTypeRef, _actualThisTypeRef_1);
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BoundThisTypeRef boundThisTypeRef, final TypeRef right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeBoundThisTypeRefTypeRef(G, _subtrace_, boundThisTypeRef, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeBoundThisTypeRefTypeRef") + stringRepForEnv(G) + " |- " + stringRep(boundThisTypeRef) + " <: " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeBoundThisTypeRefTypeRef) {
    	subtypeThrowException(ruleName("subtypeBoundThisTypeRefTypeRef") + stringRepForEnv(G) + " |- " + stringRep(boundThisTypeRef) + " <: " + stringRep(right),
    		SUBTYPEBOUNDTHISTYPEREFTYPEREF,
    		e_applyRuleSubtypeBoundThisTypeRefTypeRef, boundThisTypeRef, right, new ErrorInformation[] {new ErrorInformation(boundThisTypeRef), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeBoundThisTypeRefTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BoundThisTypeRef boundThisTypeRef, final TypeRef right) throws RuleFailedException {
    /* boundThisTypeRef === right or { G |~ boundThisTypeRef /\ var TypeRef upperExt G |- upperExt <: right } */
    {
      RuleFailedException previousFailure = null;
      try {
        /* boundThisTypeRef === right */
        if (!(boundThisTypeRef == right)) {
          sneakyThrowRuleFailedException("boundThisTypeRef === right");
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* G |~ boundThisTypeRef /\ var TypeRef upperExt */
        TypeRef upperExt = null;
        Result<TypeRef> result = upperBoundInternal(G, _trace_, boundThisTypeRef);
        checkAssignableTo(result.getFirst(), TypeRef.class);
        upperExt = (TypeRef) result.getFirst();
        
        /* G |- upperExt <: right */
        subtypeInternal(G, _trace_, upperExt, right);
      }
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef left, final BoundThisTypeRef boundThisTypeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeTypeRefBoundThisTypeRef(G, _subtrace_, left, boundThisTypeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeTypeRefBoundThisTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(boundThisTypeRef);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeTypeRefBoundThisTypeRef) {
    	subtypeThrowException(ruleName("subtypeTypeRefBoundThisTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(boundThisTypeRef),
    		SUBTYPETYPEREFBOUNDTHISTYPEREF,
    		e_applyRuleSubtypeTypeRefBoundThisTypeRef, left, boundThisTypeRef, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(boundThisTypeRef)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeTypeRefBoundThisTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef left, final BoundThisTypeRef boundThisTypeRef) throws RuleFailedException {
    /* left===boundThisTypeRef or { val leftType = left.declaredType leftType===G.undefinedType || leftType===G.nullType } or { if (boundThisTypeRef.isUseSiteStructuralTyping || (null !== boundThisTypeRef.actualThisTypeRef && null !== boundThisTypeRef.actualThisTypeRef.declaredType && boundThisTypeRef.actualThisTypeRef.declaredType.final)) { val resolvedTypeRef = TypeUtils.createResolvedThisTypeRef(boundThisTypeRef); G |- left <: resolvedTypeRef } else { fail } } */
    {
      RuleFailedException previousFailure = null;
      try {
        /* left===boundThisTypeRef */
        if (!(left == boundThisTypeRef)) {
          sneakyThrowRuleFailedException("left===boundThisTypeRef");
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* { val leftType = left.declaredType leftType===G.undefinedType || leftType===G.nullType } or { if (boundThisTypeRef.isUseSiteStructuralTyping || (null !== boundThisTypeRef.actualThisTypeRef && null !== boundThisTypeRef.actualThisTypeRef.declaredType && boundThisTypeRef.actualThisTypeRef.declaredType.final)) { val resolvedTypeRef = TypeUtils.createResolvedThisTypeRef(boundThisTypeRef); G |- left <: resolvedTypeRef } else { fail } } */
        {
          try {
            final Type leftType = left.getDeclaredType();
            /* leftType===G.undefinedType || leftType===G.nullType */
            if (!((leftType == RuleEnvironmentExtensions.undefinedType(G)) || (leftType == RuleEnvironmentExtensions.nullType(G)))) {
              sneakyThrowRuleFailedException("leftType===G.undefinedType || leftType===G.nullType");
            }
          } catch (Exception e_1) {
            previousFailure = extractRuleFailedException(e_1);
            if ((boundThisTypeRef.isUseSiteStructuralTyping() || (((null != boundThisTypeRef.getActualThisTypeRef()) && (null != boundThisTypeRef.getActualThisTypeRef().getDeclaredType())) && boundThisTypeRef.getActualThisTypeRef().getDeclaredType().isFinal()))) {
              final ParameterizedTypeRef resolvedTypeRef = TypeUtils.createResolvedThisTypeRef(boundThisTypeRef);
              /* G |- left <: resolvedTypeRef */
              subtypeInternal(G, _trace_, left, resolvedTypeRef);
            } else {
              /* fail */
              throwForExplicitFail();
            }
          }
        }
      }
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef left, final ExistentialTypeRef existentialTypeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeExistentialTypeRef_Right(G, _subtrace_, left, existentialTypeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeExistentialTypeRef_Right") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(existentialTypeRef);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeExistentialTypeRef_Right) {
    	subtypeThrowException(ruleName("subtypeExistentialTypeRef_Right") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(existentialTypeRef),
    		SUBTYPEEXISTENTIALTYPEREF_RIGHT,
    		e_applyRuleSubtypeExistentialTypeRef_Right, left, existentialTypeRef, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(existentialTypeRef)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeExistentialTypeRef_Right(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef left, final ExistentialTypeRef existentialTypeRef) throws RuleFailedException {
    boolean _isExistentialTypeToBeReopened = RuleEnvironmentExtensions.isExistentialTypeToBeReopened(G, existentialTypeRef);
    if (_isExistentialTypeToBeReopened) {
      final Wildcard wildThing = existentialTypeRef.getWildcard();
      /* G |~ wildThing /\ var TypeRef upperBound */
      TypeRef upperBound = null;
      Result<TypeRef> result = upperBoundInternal(G, _trace_, wildThing);
      checkAssignableTo(result.getFirst(), TypeRef.class);
      upperBound = (TypeRef) result.getFirst();
      
      /* G |~ wildThing \/ var TypeRef lowerBound */
      TypeRef lowerBound = null;
      Result<TypeRef> result_1 = lowerBoundInternal(G, _trace_, wildThing);
      checkAssignableTo(result_1.getFirst(), TypeRef.class);
      lowerBound = (TypeRef) result_1.getFirst();
      
      /* G |- left <: upperBound */
      subtypeInternal(G, _trace_, left, upperBound);
      /* G |- lowerBound <: left */
      subtypeInternal(G, _trace_, lowerBound, left);
    } else {
      /* left===existentialTypeRef or { left instanceof ParameterizedTypeRef && (left as ParameterizedTypeRef).declaredType instanceof NullType } or { G |~ existentialTypeRef \/ var TypeRef lowerExt G |- left <: lowerExt } */
      {
        RuleFailedException previousFailure = null;
        try {
          /* left===existentialTypeRef */
          if (!(left == existentialTypeRef)) {
            sneakyThrowRuleFailedException("left===existentialTypeRef");
          }
        } catch (Exception e) {
          previousFailure = extractRuleFailedException(e);
          /* { left instanceof ParameterizedTypeRef && (left as ParameterizedTypeRef).declaredType instanceof NullType } or { G |~ existentialTypeRef \/ var TypeRef lowerExt G |- left <: lowerExt } */
          {
            try {
              /* left instanceof ParameterizedTypeRef && (left as ParameterizedTypeRef).declaredType instanceof NullType */
              if (!((left instanceof ParameterizedTypeRef) && 
                (((ParameterizedTypeRef) left).getDeclaredType() instanceof NullType))) {
                sneakyThrowRuleFailedException("left instanceof ParameterizedTypeRef && (left as ParameterizedTypeRef).declaredType instanceof NullType");
              }
            } catch (Exception e_1) {
              previousFailure = extractRuleFailedException(e_1);
              /* G |~ existentialTypeRef \/ var TypeRef lowerExt */
              TypeRef lowerExt = null;
              Result<TypeRef> result_2 = lowerBoundInternal(G, _trace_, existentialTypeRef);
              checkAssignableTo(result_2.getFirst(), TypeRef.class);
              lowerExt = (TypeRef) result_2.getFirst();
              
              /* G |- left <: lowerExt */
              subtypeInternal(G, _trace_, left, lowerExt);
            }
          }
        }
      }
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ExistentialTypeRef existentialTypeRef, final TypeRef right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeExistentialTypeRef_Left(G, _subtrace_, existentialTypeRef, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeExistentialTypeRef_Left") + stringRepForEnv(G) + " |- " + stringRep(existentialTypeRef) + " <: " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeExistentialTypeRef_Left) {
    	subtypeThrowException(ruleName("subtypeExistentialTypeRef_Left") + stringRepForEnv(G) + " |- " + stringRep(existentialTypeRef) + " <: " + stringRep(right),
    		SUBTYPEEXISTENTIALTYPEREF_LEFT,
    		e_applyRuleSubtypeExistentialTypeRef_Left, existentialTypeRef, right, new ErrorInformation[] {new ErrorInformation(existentialTypeRef), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeExistentialTypeRef_Left(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ExistentialTypeRef existentialTypeRef, final TypeRef right) throws RuleFailedException {
    boolean _isExistentialTypeToBeReopened = RuleEnvironmentExtensions.isExistentialTypeToBeReopened(G, existentialTypeRef);
    if (_isExistentialTypeToBeReopened) {
      final Wildcard wildThing = existentialTypeRef.getWildcard();
      /* G |~ wildThing /\ var TypeRef upperBound */
      TypeRef upperBound = null;
      Result<TypeRef> result = upperBoundInternal(G, _trace_, wildThing);
      checkAssignableTo(result.getFirst(), TypeRef.class);
      upperBound = (TypeRef) result.getFirst();
      
      /* G |~ wildThing \/ var TypeRef lowerBound */
      TypeRef lowerBound = null;
      Result<TypeRef> result_1 = lowerBoundInternal(G, _trace_, wildThing);
      checkAssignableTo(result_1.getFirst(), TypeRef.class);
      lowerBound = (TypeRef) result_1.getFirst();
      
      /* G |- right <: upperBound */
      subtypeInternal(G, _trace_, right, upperBound);
      /* G |- lowerBound <: right */
      subtypeInternal(G, _trace_, lowerBound, right);
    } else {
      /* existentialTypeRef===right or { G |~ existentialTypeRef /\ var TypeRef upperExt G |- upperExt <: right } */
      {
        RuleFailedException previousFailure = null;
        try {
          /* existentialTypeRef===right */
          if (!(existentialTypeRef == right)) {
            sneakyThrowRuleFailedException("existentialTypeRef===right");
          }
        } catch (Exception e) {
          previousFailure = extractRuleFailedException(e);
          /* G |~ existentialTypeRef /\ var TypeRef upperExt */
          TypeRef upperExt = null;
          Result<TypeRef> result_2 = upperBoundInternal(G, _trace_, existentialTypeRef);
          checkAssignableTo(result_2.getFirst(), TypeRef.class);
          upperExt = (TypeRef) result_2.getFirst();
          
          /* G |- upperExt <: right */
          subtypeInternal(G, _trace_, upperExt, right);
        }
      }
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeTypeRef left, final TypeTypeRef right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeTypeTypeRef(G, _subtrace_, left, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeTypeTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeTypeTypeRef) {
    	subtypeThrowException(ruleName("subtypeTypeTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right),
    		SUBTYPETYPETYPEREF,
    		e_applyRuleSubtypeTypeTypeRef, left, right, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeTypeTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeTypeRef left, final TypeTypeRef right) throws RuleFailedException {
    final TypeArgument leftTypeArg = left.getTypeArg();
    final TypeArgument rightTypeArg = right.getTypeArg();
    final boolean leftIsCtorRef = left.isConstructorRef();
    final boolean rightIsCtorRef = right.isConstructorRef();
    final boolean rightHasTypeRef = (rightTypeArg instanceof TypeRef);
    if (((!leftIsCtorRef) && rightIsCtorRef)) {
      /* fail */
      throwForExplicitFail();
    } else {
      if ((rightHasTypeRef && (!rightIsCtorRef))) {
        /* G |- leftTypeArg <: rightTypeArg */
        subtypeInternal(G, _trace_, leftTypeArg, rightTypeArg);
      } else {
        if ((rightHasTypeRef && rightIsCtorRef)) {
          final Type left_staticType = this.typeSystemHelper.getStaticType(G, left);
          final Type right_staticType = this.typeSystemHelper.getStaticType(G, right);
          final boolean leftHasCovariantConstructor = ((left_staticType instanceof TClassifier) && N4JSLanguageUtils.hasCovariantConstructor(((TClassifier) left_staticType)));
          /* !(leftTypeArg instanceof Wildcard || leftTypeArg instanceof ExistentialTypeRef || leftTypeArg instanceof ThisTypeRef) || leftHasCovariantConstructor */
          if (!((!(((leftTypeArg instanceof Wildcard) || (leftTypeArg instanceof ExistentialTypeRef)) || (leftTypeArg instanceof ThisTypeRef))) || leftHasCovariantConstructor)) {
            sneakyThrowRuleFailedException("!(leftTypeArg instanceof Wildcard || leftTypeArg instanceof ExistentialTypeRef || leftTypeArg instanceof ThisTypeRef) || leftHasCovariantConstructor");
          }
          /* G |- leftTypeArg <: rightTypeArg */
          subtypeInternal(G, _trace_, leftTypeArg, rightTypeArg);
          if (((left_staticType instanceof TypeVariable) || (right_staticType instanceof TypeVariable))) {
            /* left_staticType === right_staticType */
            if (!(left_staticType == right_staticType)) {
              sneakyThrowRuleFailedException("left_staticType === right_staticType");
            }
          } else {
            final TMethod leftCtor = this.containerTypesHelper.fromContext(RuleEnvironmentExtensions.getContextResource(G)).findConstructor(((ContainerType<?>) left_staticType));
            final TMethod rightCtor = this.containerTypesHelper.fromContext(RuleEnvironmentExtensions.getContextResource(G)).findConstructor(((ContainerType<?>) right_staticType));
            /* leftCtor!==null && rightCtor!==null */
            if (!((leftCtor != null) && (rightCtor != null))) {
              sneakyThrowRuleFailedException("leftCtor!==null && rightCtor!==null");
            }
            /* G |- leftCtor : var TypeRef leftCtorRef */
            TypeRef leftCtorRef = null;
            Result<TypeRef> result = typeInternal(G, _trace_, leftCtor);
            checkAssignableTo(result.getFirst(), TypeRef.class);
            leftCtorRef = (TypeRef) result.getFirst();
            
            /* G |- rightCtor : var TypeRef rightCtorRef */
            TypeRef rightCtorRef = null;
            Result<TypeRef> result_1 = typeInternal(G, _trace_, rightCtor);
            checkAssignableTo(result_1.getFirst(), TypeRef.class);
            rightCtorRef = (TypeRef) result_1.getFirst();
            
            final RuleEnvironment G_left = RuleEnvironmentExtensions.wrap(G);
            final RuleEnvironment G_right = RuleEnvironmentExtensions.wrap(G);
            this.typeSystemHelper.addSubstitutions(G_left, TypeExtensions.ref(left_staticType));
            RuleEnvironmentExtensions.addThisType(G_left, TypeExtensions.ref(left_staticType));
            this.typeSystemHelper.addSubstitutions(G_right, TypeExtensions.ref(right_staticType));
            RuleEnvironmentExtensions.addThisType(G_right, TypeExtensions.ref(right_staticType));
            /* G_left |- leftCtorRef ~> var TypeRef leftCtorRefSubst */
            TypeRef leftCtorRefSubst = null;
            Result<TypeArgument> result_2 = substTypeVariablesInternal(G_left, _trace_, leftCtorRef);
            checkAssignableTo(result_2.getFirst(), TypeRef.class);
            leftCtorRefSubst = (TypeRef) result_2.getFirst();
            
            /* G_right |- rightCtorRef ~> var TypeRef rightCtorRefSubst */
            TypeRef rightCtorRefSubst = null;
            Result<TypeArgument> result_3 = substTypeVariablesInternal(G_right, _trace_, rightCtorRef);
            checkAssignableTo(result_3.getFirst(), TypeRef.class);
            rightCtorRefSubst = (TypeRef) result_3.getFirst();
            
            /* G |- leftCtorRefSubst <: rightCtorRefSubst */
            subtypeInternal(G, _trace_, leftCtorRefSubst, rightCtorRefSubst);
          }
        } else {
          /* G |~ leftTypeArg /\ var TypeRef upperBoundLeft */
          TypeRef upperBoundLeft = null;
          Result<TypeRef> result_4 = upperBoundInternal(G, _trace_, leftTypeArg);
          checkAssignableTo(result_4.getFirst(), TypeRef.class);
          upperBoundLeft = (TypeRef) result_4.getFirst();
          
          /* G |~ leftTypeArg \/ var TypeRef lowerBoundLeft */
          TypeRef lowerBoundLeft = null;
          Result<TypeRef> result_5 = lowerBoundInternal(G, _trace_, leftTypeArg);
          checkAssignableTo(result_5.getFirst(), TypeRef.class);
          lowerBoundLeft = (TypeRef) result_5.getFirst();
          
          /* G |~ rightTypeArg /\ var TypeRef upperBoundRight */
          TypeRef upperBoundRight = null;
          Result<TypeRef> result_6 = upperBoundInternal(G, _trace_, rightTypeArg);
          checkAssignableTo(result_6.getFirst(), TypeRef.class);
          upperBoundRight = (TypeRef) result_6.getFirst();
          
          /* G |~ rightTypeArg \/ var TypeRef lowerBoundRight */
          TypeRef lowerBoundRight = null;
          Result<TypeRef> result_7 = lowerBoundInternal(G, _trace_, rightTypeArg);
          checkAssignableTo(result_7.getFirst(), TypeRef.class);
          lowerBoundRight = (TypeRef) result_7.getFirst();
          
          /* G |- upperBoundLeft <: upperBoundRight */
          subtypeInternal(G, _trace_, upperBoundLeft, upperBoundRight);
          /* G |- lowerBoundRight <: lowerBoundLeft */
          subtypeInternal(G, _trace_, lowerBoundRight, lowerBoundLeft);
        }
      }
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeTypeRef left, final ParameterizedTypeRef right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeTypeTypeRef__ParameterizedTypeRef(G, _subtrace_, left, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeTypeTypeRef__ParameterizedTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeTypeTypeRef__ParameterizedTypeRef) {
    	subtypeThrowException(ruleName("subtypeTypeTypeRef__ParameterizedTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right),
    		SUBTYPETYPETYPEREF__PARAMETERIZEDTYPEREF,
    		e_applyRuleSubtypeTypeTypeRef__ParameterizedTypeRef, left, right, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeTypeTypeRef__ParameterizedTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeTypeRef left, final ParameterizedTypeRef right) throws RuleFailedException {
    /* right.declaredType === G.anyType || right.declaredType === G.objectType || ( right.declaredType === G.functionType && left.isConstructorRef ) */
    if (!(((right.getDeclaredType() == RuleEnvironmentExtensions.anyType(G)) || 
      (right.getDeclaredType() == RuleEnvironmentExtensions.objectType(G))) || 
      ((right.getDeclaredType() == RuleEnvironmentExtensions.functionType(G)) && 
        left.isConstructorRef()))) {
      sneakyThrowRuleFailedException("right.declaredType === G.anyType || right.declaredType === G.objectType || ( right.declaredType === G.functionType && left.isConstructorRef )");
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeExprOrRef left, final FunctionTypeExprOrRef right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeFunctionTypeExprOrRef(G, _subtrace_, left, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeFunctionTypeExprOrRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeFunctionTypeExprOrRef) {
    	subtypeThrowException(ruleName("subtypeFunctionTypeExprOrRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right),
    		SUBTYPEFUNCTIONTYPEEXPRORREF,
    		e_applyRuleSubtypeFunctionTypeExprOrRef, left, right, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeFunctionTypeExprOrRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeExprOrRef left, final FunctionTypeExprOrRef right) throws RuleFailedException {
    /* typeSystemHelper.isSubtypeFunction(G,left,right) */
    if (!this.typeSystemHelper.isSubtypeFunction(G, left, right)) {
      sneakyThrowRuleFailedException("typeSystemHelper.isSubtypeFunction(G,left,right)");
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeRef left, final FunctionTypeRef right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeFunctionTypeRef(G, _subtrace_, left, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeFunctionTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeFunctionTypeRef) {
    	subtypeThrowException(ruleName("subtypeFunctionTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right),
    		SUBTYPEFUNCTIONTYPEREF,
    		e_applyRuleSubtypeFunctionTypeRef, left, right, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeFunctionTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeRef left, final FunctionTypeRef right) throws RuleFailedException {
    /* typeSystemHelper.isSubtypeFunction(G,left,right) */
    if (!this.typeSystemHelper.isSubtypeFunction(G, left, right)) {
      sneakyThrowRuleFailedException("typeSystemHelper.isSubtypeFunction(G,left,right)");
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeExpression left, final ParameterizedTypeRef right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeFunctionTypeExpression_ParameterizedTypeRef(G, _subtrace_, left, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("subtypeFunctionTypeExpression_ParameterizedTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeFunctionTypeExpression_ParameterizedTypeRef) {
    	subtypeThrowException(ruleName("subtypeFunctionTypeExpression_ParameterizedTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right),
    		SUBTYPEFUNCTIONTYPEEXPRESSION_PARAMETERIZEDTYPEREF,
    		e_applyRuleSubtypeFunctionTypeExpression_ParameterizedTypeRef, left, right, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeFunctionTypeExpression_ParameterizedTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeExpression left, final ParameterizedTypeRef right) throws RuleFailedException {
    if ((right instanceof FunctionTypeExprOrRef)) {
      /* typeSystemHelper.isSubtypeFunction(G,left,right) */
      if (!this.typeSystemHelper.isSubtypeFunction(G, left, ((FunctionTypeExprOrRef)right))) {
        sneakyThrowRuleFailedException("typeSystemHelper.isSubtypeFunction(G,left,right)");
      }
    } else {
      /* right.declaredType instanceof AnyType || right.declaredType === G.functionType */
      if (!((right.getDeclaredType() instanceof AnyType) || 
        (right.getDeclaredType() == RuleEnvironmentExtensions.functionType(G)))) {
        sneakyThrowRuleFailedException("right.declaredType instanceof AnyType || right.declaredType === G.functionType");
      }
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> supertypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef left, final TypeRef right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSupertypeTypeRef(G, _subtrace_, left, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("supertypeTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " :> " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSupertypeTypeRef) {
    	supertypeThrowException(ruleName("supertypeTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " :> " + stringRep(right),
    		SUPERTYPETYPEREF,
    		e_applyRuleSupertypeTypeRef, left, right, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSupertypeTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef left, final TypeRef right) throws RuleFailedException {
    /* G |- right <: left */
    subtypeInternal(G, _trace_, right, left);
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> equaltypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef left, final TypeRef right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleEqualTypeTypeRef(G, _subtrace_, left, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("equalTypeTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " ~~ " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleEqualTypeTypeRef) {
    	equaltypeThrowException(ruleName("equalTypeTypeRef") + stringRepForEnv(G) + " |- " + stringRep(left) + " ~~ " + stringRep(right),
    		EQUALTYPETYPEREF,
    		e_applyRuleEqualTypeTypeRef, left, right, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleEqualTypeTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef left, final TypeRef right) throws RuleFailedException {
    /* G |- left <: right */
    subtypeInternal(G, _trace_, left, right);
    /* G |- right <: left */
    subtypeInternal(G, _trace_, right, left);
    return new Result<Boolean>(true);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FormalParameter formalParam, final Expression initializer) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInFormalParameter(G, _subtrace_, formalParam, initializer);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInFormalParameter") + stringRepForEnv(G) + " |- " + stringRep(formalParam) + " |> " + stringRep(initializer) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInFormalParameter) {
    	expectedTypeInThrowException(ruleName("expectedTypeInFormalParameter") + stringRepForEnv(G) + " |- " + stringRep(formalParam) + " |> " + stringRep(initializer) + " : " + "TypeRef",
    		EXPECTEDTYPEINFORMALPARAMETER,
    		e_applyRuleExpectedTypeInFormalParameter, formalParam, initializer, new ErrorInformation[] {new ErrorInformation(formalParam), new ErrorInformation(initializer)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInFormalParameter(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FormalParameter formalParam, final Expression initializer) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef _declaredTypeRef = formalParam.getDeclaredTypeRef();
    boolean _tripleNotEquals = (_declaredTypeRef != null);
    if (_tripleNotEquals) {
      T = formalParam.getDeclaredTypeRef();
    } else {
      TFormalParameter _definedTypeElement = formalParam.getDefinedTypeElement();
      TypeRef _typeRef = null;
      if (_definedTypeElement!=null) {
        _typeRef=_definedTypeElement.getTypeRef();
      }
      boolean _tripleNotEquals_1 = (_typeRef != null);
      if (_tripleNotEquals_1) {
        T = formalParam.getDefinedTypeElement().getTypeRef();
      } else {
        T = RuleEnvironmentExtensions.anyTypeRef(G);
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Argument argument, final Expression argumentExpression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeOfArgument(G, _subtrace_, argument, argumentExpression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeOfArgument") + stringRepForEnv(G) + " |- " + stringRep(argument) + " |> " + stringRep(argumentExpression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeOfArgument) {
    	expectedTypeInThrowException(ruleName("expectedTypeOfArgument") + stringRepForEnv(G) + " |- " + stringRep(argument) + " |> " + stringRep(argumentExpression) + " : " + "TypeRef",
    		EXPECTEDTYPEOFARGUMENT,
    		e_applyRuleExpectedTypeOfArgument, argument, argumentExpression, new ErrorInformation[] {new ErrorInformation(argument), new ErrorInformation(argumentExpression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeOfArgument(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Argument argument, final Expression argumentExpression) throws RuleFailedException {
    TypeRef T = null; // output parameter
    final EObject expr = argument.eContainer();
    if ((expr instanceof NewExpression)) {
      boolean _contains = ((NewExpression)expr).getArguments().contains(argument);
      boolean _not = (!_contains);
      if (_not) {
      } else {
        /* G |- expr.callee : var TypeTypeRef ctorTypeRef */
        Expression _callee = ((NewExpression)expr).getCallee();
        TypeTypeRef ctorTypeRef = null;
        Result<TypeRef> result = typeInternal(G, _trace_, _callee);
        checkAssignableTo(result.getFirst(), TypeTypeRef.class);
        ctorTypeRef = (TypeTypeRef) result.getFirst();
        
        TypeRef typeRefOfInstanceToCreate = this.typeSystemHelper.createTypeRefFromStaticType(G, ctorTypeRef, ((TypeArgument[])Conversions.unwrapArray(((NewExpression)expr).getTypeArgs(), TypeArgument.class)));
        Type _declaredType = typeRefOfInstanceToCreate.getDeclaredType();
        ContainerType<?> typeOfInstanceToCreate = ((ContainerType<?>) _declaredType);
        final RuleEnvironment G2 = RuleEnvironmentExtensions.wrap(G);
        this.typeSystemHelper.addSubstitutions(G2, typeRefOfInstanceToCreate);
        RuleEnvironmentExtensions.addThisType(G2, typeRefOfInstanceToCreate);
        TMethod ctor = this.containerTypesHelper.fromContext(((NewExpression)expr).eResource()).findConstructor(typeOfInstanceToCreate);
        TFormalParameter _fparForArgIdx = null;
        if (ctor!=null) {
          _fparForArgIdx=ctor.getFparForArgIdx(ECollections.indexOf(((NewExpression)expr).getArguments(), argument, 0));
        }
        final TFormalParameter fpar = _fparForArgIdx;
        if ((fpar == null)) {
          T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
        } else {
          final TypeRef paramType = fpar.getTypeRef();
          if ((paramType == null)) {
            T = RuleEnvironmentExtensions.anyTypeRef(G2);
          } else {
            /* G2 |- paramType ~> T */
            Result<TypeArgument> result_1 = substTypeVariablesInternal(G2, _trace_, paramType);
            checkAssignableTo(result_1.getFirst(), TypeRef.class);
            T = (TypeRef) result_1.getFirst();
            
          }
        }
      }
    } else {
      if ((expr instanceof ParameterizedCallExpression)) {
        boolean _contains_1 = ((ParameterizedCallExpression)expr).getArguments().contains(argument);
        /* expr.arguments.contains(argument) */
        if (!_contains_1) {
          sneakyThrowRuleFailedException("expr.arguments.contains(argument)");
        }
        /* G |- expr.target : var TypeRef targetTypeRef */
        Expression _target = ((ParameterizedCallExpression)expr).getTarget();
        TypeRef targetTypeRef = null;
        Result<TypeRef> result_2 = typeInternal(G, _trace_, _target);
        checkAssignableTo(result_2.getFirst(), TypeRef.class);
        targetTypeRef = (TypeRef) result_2.getFirst();
        
        if ((targetTypeRef instanceof FunctionTypeExprOrRef)) {
          final FunctionTypeExprOrRef F = ((FunctionTypeExprOrRef)targetTypeRef);
          final int argIndex = ECollections.indexOf(((ParameterizedCallExpression)expr).getArguments(), argument, 0);
          final TFormalParameter fpar_1 = F.getFparForArgIdx(argIndex);
          if ((fpar_1 == null)) {
            T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
          } else {
            final TypeRef paramType_1 = fpar_1.getTypeRef();
            if ((paramType_1 == null)) {
              T = RuleEnvironmentExtensions.anyTypeRef(G);
            } else {
              final RuleEnvironment G2_1 = RuleEnvironmentExtensions.wrap(G);
              this.typeSystemHelper.addSubstitutions(G2_1, ((ParameterizedCallExpression)expr), F);
              Expression _target_1 = ((ParameterizedCallExpression)expr).getTarget();
              if ((_target_1 instanceof SuperLiteral)) {
                N4ClassDeclaration _containerOfType = EcoreUtil2.<N4ClassDeclaration>getContainerOfType(expr, N4ClassDeclaration.class);
                Type _definedType = null;
                if (_containerOfType!=null) {
                  _definedType=_containerOfType.getDefinedType();
                }
                final Type containingClass = _definedType;
                if ((containingClass instanceof TClass)) {
                  RuleEnvironmentExtensions.addThisType(G2_1, TypeExtensions.ref(containingClass));
                  ParameterizedTypeRef _superClassRef = ((TClass)containingClass).getSuperClassRef();
                  boolean _tripleNotEquals = (_superClassRef != null);
                  if (_tripleNotEquals) {
                    this.typeSystemHelper.addSubstitutions(G2_1, ((TClass)containingClass).getSuperClassRef());
                  }
                  if ((paramType_1 instanceof ThisTypeRefStructural)) {
                    RuleEnvironmentExtensions.addThisType(G2_1, ((TClass)containingClass).getSuperClassRef());
                  }
                }
              }
              /* G2 |- paramType ~> T */
              Result<TypeArgument> result_3 = substTypeVariablesInternal(G2_1, _trace_, paramType_1);
              checkAssignableTo(result_3.getFirst(), TypeRef.class);
              T = (TypeRef) result_3.getFirst();
              
            }
          }
        } else {
          T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
        }
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PostfixExpression e, final Expression expression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInPostfixExpression(G, _subtrace_, e, expression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInPostfixExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInPostfixExpression) {
    	expectedTypeInThrowException(ruleName("expectedTypeInPostfixExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + "TypeRef",
    		EXPECTEDTYPEINPOSTFIXEXPRESSION,
    		e_applyRuleExpectedTypeInPostfixExpression, e, expression, new ErrorInformation[] {new ErrorInformation(e), new ErrorInformation(expression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInPostfixExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PostfixExpression e, final Expression expression) throws RuleFailedException {
    TypeRef T = null; // output parameter
    boolean _isTypeAware = this.jsVariantHelper.isTypeAware(e);
    if (_isTypeAware) {
      T = RuleEnvironmentExtensions.numberTypeRef(G);
    } else {
      T = RuleEnvironmentExtensions.anyTypeRef(G);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UnaryExpression e, final Expression expression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInUnaryExpression(G, _subtrace_, e, expression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInUnaryExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInUnaryExpression) {
    	expectedTypeInThrowException(ruleName("expectedTypeInUnaryExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + "TypeRef",
    		EXPECTEDTYPEINUNARYEXPRESSION,
    		e_applyRuleExpectedTypeInUnaryExpression, e, expression, new ErrorInformation[] {new ErrorInformation(e), new ErrorInformation(expression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInUnaryExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UnaryExpression e, final Expression expression) throws RuleFailedException {
    TypeRef T = null; // output parameter
    StaticBaseTypeRef _xifexpression = null;
    boolean _isTypeAware = this.jsVariantHelper.isTypeAware(e);
    if (_isTypeAware) {
      StaticBaseTypeRef _switchResult = null;
      UnaryOperator _op = e.getOp();
      if (_op != null) {
        switch (_op) {
          case DELETE:
            _switchResult = TypeUtils.createNonSimplifiedUnionType(RuleEnvironmentExtensions.anyTypeRef(G), RuleEnvironmentExtensions.voidTypeRef(G));
            break;
          case VOID:
            _switchResult = TypeUtils.createNonSimplifiedUnionType(RuleEnvironmentExtensions.anyTypeRef(G), RuleEnvironmentExtensions.voidTypeRef(G));
            break;
          case TYPEOF:
            _switchResult = TypeUtils.createNonSimplifiedUnionType(RuleEnvironmentExtensions.anyTypeRef(G), RuleEnvironmentExtensions.voidTypeRef(G));
            break;
          case INC:
            _switchResult = RuleEnvironmentExtensions.numberTypeRef(G);
            break;
          case DEC:
            _switchResult = RuleEnvironmentExtensions.numberTypeRef(G);
            break;
          case POS:
            _switchResult = RuleEnvironmentExtensions.numberTypeRef(G);
            break;
          case NEG:
            _switchResult = RuleEnvironmentExtensions.numberTypeRef(G);
            break;
          case INV:
            _switchResult = RuleEnvironmentExtensions.numberTypeRef(G);
            break;
          case NOT:
            _switchResult = RuleEnvironmentExtensions.anyTypeRef(G);
            break;
          default:
            _switchResult = RuleEnvironmentExtensions.anyTypeRef(G);
            break;
        }
      } else {
        _switchResult = RuleEnvironmentExtensions.anyTypeRef(G);
      }
      _xifexpression = _switchResult;
    } else {
      StaticBaseTypeRef _switchResult_1 = null;
      UnaryOperator _op_1 = e.getOp();
      if (_op_1 != null) {
        switch (_op_1) {
          case DELETE:
            _switchResult_1 = TypeUtils.createNonSimplifiedUnionType(RuleEnvironmentExtensions.anyTypeRef(G), RuleEnvironmentExtensions.voidTypeRef(G));
            break;
          case VOID:
            _switchResult_1 = TypeUtils.createNonSimplifiedUnionType(RuleEnvironmentExtensions.anyTypeRef(G), RuleEnvironmentExtensions.voidTypeRef(G));
            break;
          case TYPEOF:
            _switchResult_1 = TypeUtils.createNonSimplifiedUnionType(RuleEnvironmentExtensions.anyTypeRef(G), RuleEnvironmentExtensions.voidTypeRef(G));
            break;
          default:
            _switchResult_1 = RuleEnvironmentExtensions.anyTypeRef(G);
            break;
        }
      } else {
        _switchResult_1 = RuleEnvironmentExtensions.anyTypeRef(G);
      }
      _xifexpression = _switchResult_1;
    }
    T = _xifexpression;
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final MultiplicativeExpression e, final Expression expression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInMultiplicativeExpression(G, _subtrace_, e, expression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInMultiplicativeExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInMultiplicativeExpression) {
    	expectedTypeInThrowException(ruleName("expectedTypeInMultiplicativeExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + "TypeRef",
    		EXPECTEDTYPEINMULTIPLICATIVEEXPRESSION,
    		e_applyRuleExpectedTypeInMultiplicativeExpression, e, expression, new ErrorInformation[] {new ErrorInformation(e), new ErrorInformation(expression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInMultiplicativeExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final MultiplicativeExpression e, final Expression expression) throws RuleFailedException {
    TypeRef T = null; // output parameter
    boolean _isTypeAware = this.jsVariantHelper.isTypeAware(e);
    if (_isTypeAware) {
      T = RuleEnvironmentExtensions.numberTypeRef(G);
    } else {
      T = RuleEnvironmentExtensions.anyTypeRef(G);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AdditiveExpression e, final Expression expression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInAdditiveExpression(G, _subtrace_, e, expression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInAdditiveExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInAdditiveExpression) {
    	expectedTypeInThrowException(ruleName("expectedTypeInAdditiveExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + "TypeRef",
    		EXPECTEDTYPEINADDITIVEEXPRESSION,
    		e_applyRuleExpectedTypeInAdditiveExpression, e, expression, new ErrorInformation[] {new ErrorInformation(e), new ErrorInformation(expression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInAdditiveExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AdditiveExpression e, final Expression expression) throws RuleFailedException {
    TypeRef T = null; // output parameter
    if (((!Objects.equal(e.getOp(), AdditiveOperator.ADD)) && this.jsVariantHelper.isTypeAware(e))) {
      T = RuleEnvironmentExtensions.numberTypeRef(G);
    } else {
      T = RuleEnvironmentExtensions.anyTypeRef(G);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ShiftExpression e, final Expression expression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInShiftExpression(G, _subtrace_, e, expression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInShiftExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInShiftExpression) {
    	expectedTypeInThrowException(ruleName("expectedTypeInShiftExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + "TypeRef",
    		EXPECTEDTYPEINSHIFTEXPRESSION,
    		e_applyRuleExpectedTypeInShiftExpression, e, expression, new ErrorInformation[] {new ErrorInformation(e), new ErrorInformation(expression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInShiftExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ShiftExpression e, final Expression expression) throws RuleFailedException {
    TypeRef T = null; // output parameter
    boolean _isTypeAware = this.jsVariantHelper.isTypeAware(e);
    if (_isTypeAware) {
      T = RuleEnvironmentExtensions.numberTypeRef(G);
    } else {
      T = RuleEnvironmentExtensions.anyTypeRef(G);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final RelationalExpression e, final Expression expression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInRelationalExpression(G, _subtrace_, e, expression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInRelationalExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInRelationalExpression) {
    	expectedTypeInThrowException(ruleName("expectedTypeInRelationalExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + "TypeRef",
    		EXPECTEDTYPEINRELATIONALEXPRESSION,
    		e_applyRuleExpectedTypeInRelationalExpression, e, expression, new ErrorInformation[] {new ErrorInformation(e), new ErrorInformation(expression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInRelationalExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final RelationalExpression e, final Expression expression) throws RuleFailedException {
    TypeRef T = null; // output parameter
    RelationalOperator _op = e.getOp();
    if (_op != null) {
      switch (_op) {
        case INSTANCEOF:
          Expression _rhs = e.getRhs();
          boolean _tripleEquals = (expression == _rhs);
          if (_tripleEquals) {
            T = TypeUtils.createNonSimplifiedUnionType(RuleEnvironmentExtensions.functionTypeRef(G), 
              TypeUtils.createTypeTypeRef(RuleEnvironmentExtensions.objectTypeRef(G), false), TypeUtils.createTypeTypeRef(RuleEnvironmentExtensions.n4EnumTypeRef(G), false));
          } else {
            T = RuleEnvironmentExtensions.anyTypeRef(G);
          }
          break;
        case IN:
          Expression _rhs_1 = e.getRhs();
          boolean _tripleEquals_1 = (expression == _rhs_1);
          if (_tripleEquals_1) {
            T = RuleEnvironmentExtensions.objectTypeRef(G);
          } else {
            boolean _isTypeAware = this.jsVariantHelper.isTypeAware(e);
            if (_isTypeAware) {
              T = TypeUtils.createNonSimplifiedUnionType(RuleEnvironmentExtensions.numberTypeRef(G), RuleEnvironmentExtensions.stringTypeRef(G));
            } else {
              T = RuleEnvironmentExtensions.anyTypeRef(G);
            }
          }
          break;
        default:
          boolean _isTypeAware_1 = this.jsVariantHelper.isTypeAware(e);
          if (_isTypeAware_1) {
            final UnionTypeExpression primsTR = TypeUtils.createNonSimplifiedUnionType(RuleEnvironmentExtensions.numberTypeRef(G), RuleEnvironmentExtensions.stringTypeRef(G), RuleEnvironmentExtensions.booleanTypeRef(G));
            Expression _xifexpression = null;
            Expression _lhs = e.getLhs();
            boolean _tripleEquals_2 = (expression == _lhs);
            if (_tripleEquals_2) {
              _xifexpression = e.getRhs();
            } else {
              _xifexpression = e.getLhs();
            }
            final Expression otherSide = _xifexpression;
            /* G |- otherSide : var TypeRef otherSideTR */
            TypeRef otherSideTR = null;
            Result<TypeRef> result = typeInternal(G, _trace_, otherSide);
            checkAssignableTo(result.getFirst(), TypeRef.class);
            otherSideTR = (TypeRef) result.getFirst();
            
            boolean _and = false;
            /* G |- otherSideTR <: primsTR */
            boolean _ruleinvocation = subtypeSucceeded(G, _trace_, otherSideTR, primsTR);
            if (!_ruleinvocation) {
              _and = false;
            } else {
              /* G |- otherSideTR <: G.nullTypeRef */
              ParameterizedTypeRef _nullTypeRef = RuleEnvironmentExtensions.nullTypeRef(G);
              boolean _ruleinvocation_1 = subtypeSucceeded(G, _trace_, otherSideTR, _nullTypeRef);
              boolean _not = (!_ruleinvocation_1);
              _and = _not;
            }
            if (_and) {
              T = otherSideTR;
            } else {
              T = primsTR;
            }
          } else {
            T = RuleEnvironmentExtensions.anyTypeRef(G);
          }
          break;
      }
    } else {
      boolean _isTypeAware_1 = this.jsVariantHelper.isTypeAware(e);
      if (_isTypeAware_1) {
        final UnionTypeExpression primsTR = TypeUtils.createNonSimplifiedUnionType(RuleEnvironmentExtensions.numberTypeRef(G), RuleEnvironmentExtensions.stringTypeRef(G), RuleEnvironmentExtensions.booleanTypeRef(G));
        Expression _xifexpression = null;
        Expression _lhs = e.getLhs();
        boolean _tripleEquals_2 = (expression == _lhs);
        if (_tripleEquals_2) {
          _xifexpression = e.getRhs();
        } else {
          _xifexpression = e.getLhs();
        }
        final Expression otherSide = _xifexpression;
        /* G |- otherSide : var TypeRef otherSideTR */
        TypeRef otherSideTR = null;
        Result<TypeRef> result = typeInternal(G, _trace_, otherSide);
        checkAssignableTo(result.getFirst(), TypeRef.class);
        otherSideTR = (TypeRef) result.getFirst();
        
        boolean _and = false;
        /* G |- otherSideTR <: primsTR */
        boolean _ruleinvocation = subtypeSucceeded(G, _trace_, otherSideTR, primsTR);
        if (!_ruleinvocation) {
          _and = false;
        } else {
          /* G |- otherSideTR <: G.nullTypeRef */
          ParameterizedTypeRef _nullTypeRef = RuleEnvironmentExtensions.nullTypeRef(G);
          boolean _ruleinvocation_1 = subtypeSucceeded(G, _trace_, otherSideTR, _nullTypeRef);
          boolean _not = (!_ruleinvocation_1);
          _and = _not;
        }
        if (_and) {
          T = otherSideTR;
        } else {
          T = primsTR;
        }
      } else {
        T = RuleEnvironmentExtensions.anyTypeRef(G);
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final EqualityExpression e, final Expression expression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInEqualityExpression(G, _subtrace_, e, expression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInEqualityExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInEqualityExpression) {
    	expectedTypeInThrowException(ruleName("expectedTypeInEqualityExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + "ParameterizedTypeRef",
    		EXPECTEDTYPEINEQUALITYEXPRESSION,
    		e_applyRuleExpectedTypeInEqualityExpression, e, expression, new ErrorInformation[] {new ErrorInformation(e), new ErrorInformation(expression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInEqualityExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final EqualityExpression e, final Expression expression) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleExpectedTypeInEqualityExpression_2(G, e, expression));
  }
  
  private ParameterizedTypeRef _applyRuleExpectedTypeInEqualityExpression_2(final RuleEnvironment G, final EqualityExpression e, final Expression expression) throws RuleFailedException {
    ParameterizedTypeRef _anyTypeRef = RuleEnvironmentExtensions.anyTypeRef(G);
    return _anyTypeRef;
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BinaryBitwiseExpression e, final Expression expression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInBinaryBitwiseExpression(G, _subtrace_, e, expression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInBinaryBitwiseExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInBinaryBitwiseExpression) {
    	expectedTypeInThrowException(ruleName("expectedTypeInBinaryBitwiseExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + "TypeRef",
    		EXPECTEDTYPEINBINARYBITWISEEXPRESSION,
    		e_applyRuleExpectedTypeInBinaryBitwiseExpression, e, expression, new ErrorInformation[] {new ErrorInformation(e), new ErrorInformation(expression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInBinaryBitwiseExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BinaryBitwiseExpression e, final Expression expression) throws RuleFailedException {
    TypeRef T = null; // output parameter
    boolean _isTypeAware = this.jsVariantHelper.isTypeAware(e);
    if (_isTypeAware) {
      T = RuleEnvironmentExtensions.numberTypeRef(G);
    } else {
      T = RuleEnvironmentExtensions.anyTypeRef(G);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BinaryLogicalExpression e, final Expression expression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInBinaryLogicalExpression(G, _subtrace_, e, expression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInBinaryLogicalExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInBinaryLogicalExpression) {
    	expectedTypeInThrowException(ruleName("expectedTypeInBinaryLogicalExpression") + stringRepForEnv(G) + " |- " + stringRep(e) + " |> " + stringRep(expression) + " : " + "ParameterizedTypeRef",
    		EXPECTEDTYPEINBINARYLOGICALEXPRESSION,
    		e_applyRuleExpectedTypeInBinaryLogicalExpression, e, expression, new ErrorInformation[] {new ErrorInformation(e), new ErrorInformation(expression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInBinaryLogicalExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BinaryLogicalExpression e, final Expression expression) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleExpectedTypeInBinaryLogicalExpression_2(G, e, expression));
  }
  
  private ParameterizedTypeRef _applyRuleExpectedTypeInBinaryLogicalExpression_2(final RuleEnvironment G, final BinaryLogicalExpression e, final Expression expression) throws RuleFailedException {
    ParameterizedTypeRef _anyTypeRef = RuleEnvironmentExtensions.anyTypeRef(G);
    return _anyTypeRef;
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AssignmentExpression expr, final Expression operand) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInAssignmentExpression(G, _subtrace_, expr, operand);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInAssignmentExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " |> " + stringRep(operand) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInAssignmentExpression) {
    	expectedTypeInThrowException(ruleName("expectedTypeInAssignmentExpression") + stringRepForEnv(G) + " |- " + stringRep(expr) + " |> " + stringRep(operand) + " : " + "TypeRef",
    		EXPECTEDTYPEINASSIGNMENTEXPRESSION,
    		e_applyRuleExpectedTypeInAssignmentExpression, expr, operand, new ErrorInformation[] {new ErrorInformation(expr), new ErrorInformation(operand)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInAssignmentExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AssignmentExpression expr, final Expression operand) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* { ! jsVariantHelper.isTypeAware(expr) if (operand===expr.lhs) { T = G.bottomTypeRef } else { T = G.topTypeRef } } or { N4JSASTUtils.isDestructuringAssignment(expr) if (operand===expr.lhs) { T = G.bottomTypeRef } else { T = G.topTypeRef } } or { expr.op===AssignmentOperator.ASSIGN; if (operand===expr.lhs) { T = G.bottomTypeRef } else { G |- expr.lhs : T } } or { expr.op===AssignmentOperator.ADD_ASSIGN if (operand===expr.lhs) { T = TypeUtils.createNonSimplifiedIntersectionType(G.numberTypeRef, G.stringTypeRef); } else { G |- expr.lhs : var ParameterizedTypeRef lhsTypeRef if (lhsTypeRef.declaredType === G.stringType) { T = G.anyTypeRef } else if(G.isNumeric(lhsTypeRef.declaredType)) { T = G.numberTypeRef } else { T = G.anyTypeRef } } } or { T = G.numberTypeRef } */
    {
      RuleFailedException previousFailure = null;
      try {
        boolean _isTypeAware = this.jsVariantHelper.isTypeAware(expr);
        boolean _not = (!_isTypeAware);
        /* ! jsVariantHelper.isTypeAware(expr) */
        if (!_not) {
          sneakyThrowRuleFailedException("! jsVariantHelper.isTypeAware(expr)");
        }
        Expression _lhs = expr.getLhs();
        boolean _tripleEquals = (operand == _lhs);
        if (_tripleEquals) {
          T = RuleEnvironmentExtensions.bottomTypeRef(G);
        } else {
          T = RuleEnvironmentExtensions.topTypeRef(G);
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* { N4JSASTUtils.isDestructuringAssignment(expr) if (operand===expr.lhs) { T = G.bottomTypeRef } else { T = G.topTypeRef } } or { expr.op===AssignmentOperator.ASSIGN; if (operand===expr.lhs) { T = G.bottomTypeRef } else { G |- expr.lhs : T } } or { expr.op===AssignmentOperator.ADD_ASSIGN if (operand===expr.lhs) { T = TypeUtils.createNonSimplifiedIntersectionType(G.numberTypeRef, G.stringTypeRef); } else { G |- expr.lhs : var ParameterizedTypeRef lhsTypeRef if (lhsTypeRef.declaredType === G.stringType) { T = G.anyTypeRef } else if(G.isNumeric(lhsTypeRef.declaredType)) { T = G.numberTypeRef } else { T = G.anyTypeRef } } } or { T = G.numberTypeRef } */
        {
          try {
            boolean _isDestructuringAssignment = N4JSASTUtils.isDestructuringAssignment(expr);
            /* N4JSASTUtils.isDestructuringAssignment(expr) */
            if (!_isDestructuringAssignment) {
              sneakyThrowRuleFailedException("N4JSASTUtils.isDestructuringAssignment(expr)");
            }
            Expression _lhs_1 = expr.getLhs();
            boolean _tripleEquals_1 = (operand == _lhs_1);
            if (_tripleEquals_1) {
              T = RuleEnvironmentExtensions.bottomTypeRef(G);
            } else {
              T = RuleEnvironmentExtensions.topTypeRef(G);
            }
          } catch (Exception e_1) {
            previousFailure = extractRuleFailedException(e_1);
            /* { expr.op===AssignmentOperator.ASSIGN; if (operand===expr.lhs) { T = G.bottomTypeRef } else { G |- expr.lhs : T } } or { expr.op===AssignmentOperator.ADD_ASSIGN if (operand===expr.lhs) { T = TypeUtils.createNonSimplifiedIntersectionType(G.numberTypeRef, G.stringTypeRef); } else { G |- expr.lhs : var ParameterizedTypeRef lhsTypeRef if (lhsTypeRef.declaredType === G.stringType) { T = G.anyTypeRef } else if(G.isNumeric(lhsTypeRef.declaredType)) { T = G.numberTypeRef } else { T = G.anyTypeRef } } } or { T = G.numberTypeRef } */
            {
              try {
                AssignmentOperator _op = expr.getOp();
                boolean _tripleEquals_2 = (_op == AssignmentOperator.ASSIGN);
                /* expr.op===AssignmentOperator.ASSIGN */
                if (!_tripleEquals_2) {
                  sneakyThrowRuleFailedException("expr.op===AssignmentOperator.ASSIGN");
                }
                Expression _lhs_2 = expr.getLhs();
                boolean _tripleEquals_3 = (operand == _lhs_2);
                if (_tripleEquals_3) {
                  T = RuleEnvironmentExtensions.bottomTypeRef(G);
                } else {
                  /* G |- expr.lhs : T */
                  Expression _lhs_3 = expr.getLhs();
                  Result<TypeRef> result = typeInternal(G, _trace_, _lhs_3);
                  checkAssignableTo(result.getFirst(), TypeRef.class);
                  T = (TypeRef) result.getFirst();
                  
                }
              } catch (Exception e_2) {
                previousFailure = extractRuleFailedException(e_2);
                /* { expr.op===AssignmentOperator.ADD_ASSIGN if (operand===expr.lhs) { T = TypeUtils.createNonSimplifiedIntersectionType(G.numberTypeRef, G.stringTypeRef); } else { G |- expr.lhs : var ParameterizedTypeRef lhsTypeRef if (lhsTypeRef.declaredType === G.stringType) { T = G.anyTypeRef } else if(G.isNumeric(lhsTypeRef.declaredType)) { T = G.numberTypeRef } else { T = G.anyTypeRef } } } or { T = G.numberTypeRef } */
                {
                  try {
                    AssignmentOperator _op_1 = expr.getOp();
                    boolean _tripleEquals_4 = (_op_1 == AssignmentOperator.ADD_ASSIGN);
                    /* expr.op===AssignmentOperator.ADD_ASSIGN */
                    if (!_tripleEquals_4) {
                      sneakyThrowRuleFailedException("expr.op===AssignmentOperator.ADD_ASSIGN");
                    }
                    Expression _lhs_4 = expr.getLhs();
                    boolean _tripleEquals_5 = (operand == _lhs_4);
                    if (_tripleEquals_5) {
                      T = TypeUtils.createNonSimplifiedIntersectionType(RuleEnvironmentExtensions.numberTypeRef(G), RuleEnvironmentExtensions.stringTypeRef(G));
                    } else {
                      /* G |- expr.lhs : var ParameterizedTypeRef lhsTypeRef */
                      Expression _lhs_5 = expr.getLhs();
                      ParameterizedTypeRef lhsTypeRef = null;
                      Result<TypeRef> result_1 = typeInternal(G, _trace_, _lhs_5);
                      checkAssignableTo(result_1.getFirst(), ParameterizedTypeRef.class);
                      lhsTypeRef = (ParameterizedTypeRef) result_1.getFirst();
                      
                      Type _declaredType = lhsTypeRef.getDeclaredType();
                      PrimitiveType _stringType = RuleEnvironmentExtensions.stringType(G);
                      boolean _tripleEquals_6 = (_declaredType == _stringType);
                      if (_tripleEquals_6) {
                        T = RuleEnvironmentExtensions.anyTypeRef(G);
                      } else {
                        boolean _isNumeric = RuleEnvironmentExtensions.isNumeric(G, lhsTypeRef.getDeclaredType());
                        if (_isNumeric) {
                          T = RuleEnvironmentExtensions.numberTypeRef(G);
                        } else {
                          T = RuleEnvironmentExtensions.anyTypeRef(G);
                        }
                      }
                    }
                  } catch (Exception e_3) {
                    previousFailure = extractRuleFailedException(e_3);
                    T = RuleEnvironmentExtensions.numberTypeRef(G);
                  }
                }
              }
            }
          }
        }
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final VariableDeclaration vdecl, final Expression rhs) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeOfRightSideInVariableDeclaration(G, _subtrace_, vdecl, rhs);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeOfRightSideInVariableDeclaration") + stringRepForEnv(G) + " |- " + stringRep(vdecl) + " |> " + stringRep(rhs) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeOfRightSideInVariableDeclaration) {
    	expectedTypeInThrowException(ruleName("expectedTypeOfRightSideInVariableDeclaration") + stringRepForEnv(G) + " |- " + stringRep(vdecl) + " |> " + stringRep(rhs) + " : " + "TypeRef",
    		EXPECTEDTYPEOFRIGHTSIDEINVARIABLEDECLARATION,
    		e_applyRuleExpectedTypeOfRightSideInVariableDeclaration, vdecl, rhs, new ErrorInformation[] {new ErrorInformation(vdecl), new ErrorInformation(rhs)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeOfRightSideInVariableDeclaration(final RuleEnvironment G, final RuleApplicationTrace _trace_, final VariableDeclaration vdecl, final Expression rhs) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef _declaredTypeRef = vdecl.getDeclaredTypeRef();
    boolean _tripleNotEquals = (_declaredTypeRef != null);
    if (_tripleNotEquals) {
      T = vdecl.getDeclaredTypeRef();
    } else {
      T = RuleEnvironmentExtensions.topTypeRef(G);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final VariableBinding binding, final Expression initExpr) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeOfRightSideInVariableBinding(G, _subtrace_, binding, initExpr);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeOfRightSideInVariableBinding") + stringRepForEnv(G) + " |- " + stringRep(binding) + " |> " + stringRep(initExpr) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeOfRightSideInVariableBinding) {
    	expectedTypeInThrowException(ruleName("expectedTypeOfRightSideInVariableBinding") + stringRepForEnv(G) + " |- " + stringRep(binding) + " |> " + stringRep(initExpr) + " : " + "TypeRef",
    		EXPECTEDTYPEOFRIGHTSIDEINVARIABLEBINDING,
    		e_applyRuleExpectedTypeOfRightSideInVariableBinding, binding, initExpr, new ErrorInformation[] {new ErrorInformation(binding), new ErrorInformation(initExpr)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeOfRightSideInVariableBinding(final RuleEnvironment G, final RuleApplicationTrace _trace_, final VariableBinding binding, final Expression initExpr) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = RuleEnvironmentExtensions.topTypeRef(G);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final N4FieldDeclaration fdecl, final Expression rhs) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeOfRightSideInN4FieldDeclaration(G, _subtrace_, fdecl, rhs);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeOfRightSideInN4FieldDeclaration") + stringRepForEnv(G) + " |- " + stringRep(fdecl) + " |> " + stringRep(rhs) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeOfRightSideInN4FieldDeclaration) {
    	expectedTypeInThrowException(ruleName("expectedTypeOfRightSideInN4FieldDeclaration") + stringRepForEnv(G) + " |- " + stringRep(fdecl) + " |> " + stringRep(rhs) + " : " + "TypeRef",
    		EXPECTEDTYPEOFRIGHTSIDEINN4FIELDDECLARATION,
    		e_applyRuleExpectedTypeOfRightSideInN4FieldDeclaration, fdecl, rhs, new ErrorInformation[] {new ErrorInformation(fdecl), new ErrorInformation(rhs)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeOfRightSideInN4FieldDeclaration(final RuleEnvironment G, final RuleApplicationTrace _trace_, final N4FieldDeclaration fdecl, final Expression rhs) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef _declaredTypeRef = fdecl.getDeclaredTypeRef();
    boolean _tripleNotEquals = (_declaredTypeRef != null);
    if (_tripleNotEquals) {
      T = fdecl.getDeclaredTypeRef();
    } else {
      T = RuleEnvironmentExtensions.topTypeRef(G);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PropertyNameValuePair pnvp, final Expression rhs) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeOfRightSideInPropertyNameValuePair(G, _subtrace_, pnvp, rhs);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeOfRightSideInPropertyNameValuePair") + stringRepForEnv(G) + " |- " + stringRep(pnvp) + " |> " + stringRep(rhs) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeOfRightSideInPropertyNameValuePair) {
    	expectedTypeInThrowException(ruleName("expectedTypeOfRightSideInPropertyNameValuePair") + stringRepForEnv(G) + " |- " + stringRep(pnvp) + " |> " + stringRep(rhs) + " : " + "TypeRef",
    		EXPECTEDTYPEOFRIGHTSIDEINPROPERTYNAMEVALUEPAIR,
    		e_applyRuleExpectedTypeOfRightSideInPropertyNameValuePair, pnvp, rhs, new ErrorInformation[] {new ErrorInformation(pnvp), new ErrorInformation(rhs)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeOfRightSideInPropertyNameValuePair(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PropertyNameValuePair pnvp, final Expression rhs) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef _declaredTypeRef = pnvp.getDeclaredTypeRef();
    boolean _tripleNotEquals = (_declaredTypeRef != null);
    if (_tripleNotEquals) {
      T = pnvp.getDeclaredTypeRef();
    } else {
      T = RuleEnvironmentExtensions.topTypeRef(G);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ReturnStatement stmt, final Expression expression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInReturnStatement(G, _subtrace_, stmt, expression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInReturnStatement") + stringRepForEnv(G) + " |- " + stringRep(stmt) + " |> " + stringRep(expression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInReturnStatement) {
    	expectedTypeInThrowException(ruleName("expectedTypeInReturnStatement") + stringRepForEnv(G) + " |- " + stringRep(stmt) + " |> " + stringRep(expression) + " : " + "TypeRef",
    		EXPECTEDTYPEINRETURNSTATEMENT,
    		e_applyRuleExpectedTypeInReturnStatement, stmt, expression, new ErrorInformation[] {new ErrorInformation(stmt), new ErrorInformation(expression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInReturnStatement(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ReturnStatement stmt, final Expression expression) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = this.typeSystemHelper.getExpectedTypeOfReturnValueExpression(G, expression);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final YieldExpression yieldExpr, final Expression expression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInYieldStatement(G, _subtrace_, yieldExpr, expression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInYieldStatement") + stringRepForEnv(G) + " |- " + stringRep(yieldExpr) + " |> " + stringRep(expression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInYieldStatement) {
    	expectedTypeInThrowException(ruleName("expectedTypeInYieldStatement") + stringRepForEnv(G) + " |- " + stringRep(yieldExpr) + " |> " + stringRep(expression) + " : " + "TypeRef",
    		EXPECTEDTYPEINYIELDSTATEMENT,
    		e_applyRuleExpectedTypeInYieldStatement, yieldExpr, expression, new ErrorInformation[] {new ErrorInformation(yieldExpr), new ErrorInformation(expression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInYieldStatement(final RuleEnvironment G, final RuleApplicationTrace _trace_, final YieldExpression yieldExpr, final Expression expression) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* G |- expression : var TypeRef exprTypeRef */
    TypeRef exprTypeRef = null;
    Result<TypeRef> result = typeInternal(G, _trace_, expression);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    exprTypeRef = (TypeRef) result.getFirst();
    
    T = this.typeSystemHelper.getExpectedTypeOfYieldValueExpression(G, yieldExpr, exprTypeRef);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ExpressionStatement exprStmnt, final Expression expression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInExpressionStatement(G, _subtrace_, exprStmnt, expression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInExpressionStatement") + stringRepForEnv(G) + " |- " + stringRep(exprStmnt) + " |> " + stringRep(expression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInExpressionStatement) {
    	expectedTypeInThrowException(ruleName("expectedTypeInExpressionStatement") + stringRepForEnv(G) + " |- " + stringRep(exprStmnt) + " |> " + stringRep(expression) + " : " + "TypeRef",
    		EXPECTEDTYPEINEXPRESSIONSTATEMENT,
    		e_applyRuleExpectedTypeInExpressionStatement, exprStmnt, expression, new ErrorInformation[] {new ErrorInformation(exprStmnt), new ErrorInformation(expression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInExpressionStatement(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ExpressionStatement exprStmnt, final Expression expression) throws RuleFailedException {
    TypeRef T = null; // output parameter
    ArrowFunction _containingSingleExpressionArrowFunction = N4JSASTUtils.getContainingSingleExpressionArrowFunction(expression);
    boolean _tripleNotEquals = (_containingSingleExpressionArrowFunction != null);
    if (_tripleNotEquals) {
      T = this.typeSystemHelper.getExpectedTypeOfReturnValueExpression(G, expression);
    } else {
      T = null;
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ForStatement forStmnt, final Expression expression) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInForStatement(G, _subtrace_, forStmnt, expression);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInForStatement") + stringRepForEnv(G) + " |- " + stringRep(forStmnt) + " |> " + stringRep(expression) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInForStatement) {
    	expectedTypeInThrowException(ruleName("expectedTypeInForStatement") + stringRepForEnv(G) + " |- " + stringRep(forStmnt) + " |> " + stringRep(expression) + " : " + "TypeRef",
    		EXPECTEDTYPEINFORSTATEMENT,
    		e_applyRuleExpectedTypeInForStatement, forStmnt, expression, new ErrorInformation[] {new ErrorInformation(forStmnt), new ErrorInformation(expression)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInForStatement(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ForStatement forStmnt, final Expression expression) throws RuleFailedException {
    TypeRef T = null; // output parameter
    if ((forStmnt.isForOf() && (expression == forStmnt.getExpression()))) {
      final Wildcard wildThing = TypeRefsFactory.eINSTANCE.createWildcard();
      boolean _isDestructuringForStatement = N4JSASTUtils.isDestructuringForStatement(forStmnt);
      if (_isDestructuringForStatement) {
      } else {
        VariableDeclaration _xifexpression = null;
        boolean _isEmpty = forStmnt.getVarDecl().isEmpty();
        boolean _not = (!_isEmpty);
        if (_not) {
          _xifexpression = forStmnt.getVarDecl().get(0);
        }
        final VariableDeclaration varDeclInFor = _xifexpression;
        VariableDeclaration _xifexpression_1 = null;
        if (((forStmnt.getInitExpr() instanceof IdentifierRef) && (((IdentifierRef) forStmnt.getInitExpr()).getId() instanceof VariableDeclaration))) {
          Expression _initExpr = forStmnt.getInitExpr();
          IdentifiableElement _id = ((IdentifierRef) _initExpr).getId();
          _xifexpression_1 = ((VariableDeclaration) _id);
        }
        final VariableDeclaration varDeclOutside = _xifexpression_1;
        boolean _or = false;
        TypeRef _declaredTypeRef = null;
        if (varDeclInFor!=null) {
          _declaredTypeRef=varDeclInFor.getDeclaredTypeRef();
        }
        boolean _tripleNotEquals = (_declaredTypeRef != null);
        if (_tripleNotEquals) {
          _or = true;
        } else {
          _or = (varDeclOutside != null);
        }
        if (_or) {
          VariableDeclaration _xifexpression_2 = null;
          if ((varDeclOutside != null)) {
            _xifexpression_2 = varDeclOutside;
          } else {
            _xifexpression_2 = varDeclInFor;
          }
          final VariableDeclaration varDecl = _xifexpression_2;
          /* G |- varDecl : var TypeRef varTypeRef */
          TypeRef varTypeRef = null;
          Result<TypeRef> result = typeInternal(G, _trace_, varDecl);
          checkAssignableTo(result.getFirst(), TypeRef.class);
          varTypeRef = (TypeRef) result.getFirst();
          
          wildThing.setDeclaredUpperBound(TypeUtils.<TypeRef>copyIfContained(varTypeRef));
        }
      }
      T = RuleEnvironmentExtensions.iterableTypeRef(G, wildThing);
    } else {
      if ((forStmnt.isForIn() && (expression == forStmnt.getExpression()))) {
        T = TypeUtils.createNonSimplifiedUnionType(RuleEnvironmentExtensions.objectTypeRef(G), RuleEnvironmentExtensions.stringTypeRef(G), RuleEnvironmentExtensions.argumentsTypeRef(G));
      }
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AwaitExpression await, final Expression expr) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInAwaitExpression(G, _subtrace_, await, expr);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInAwaitExpression") + stringRepForEnv(G) + " |- " + stringRep(await) + " |> " + stringRep(expr) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInAwaitExpression) {
    	expectedTypeInThrowException(ruleName("expectedTypeInAwaitExpression") + stringRepForEnv(G) + " |- " + stringRep(await) + " |> " + stringRep(expr) + " : " + "TypeRef",
    		EXPECTEDTYPEINAWAITEXPRESSION,
    		e_applyRuleExpectedTypeInAwaitExpression, await, expr, new ErrorInformation[] {new ErrorInformation(await), new ErrorInformation(expr)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInAwaitExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AwaitExpression await, final Expression expr) throws RuleFailedException {
    TypeRef T = null; // output parameter
    boolean _isAutoPromisify = this.promisifyHelper.isAutoPromisify(await);
    if (_isAutoPromisify) {
      T = null;
    } else {
      T = RuleEnvironmentExtensions.anyTypeRef(G);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> expectedTypeInImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final EObject container, final Expression expr) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleExpectedTypeInUnsupportedContainer(G, _subtrace_, container, expr);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("expectedTypeInUnsupportedContainer") + stringRepForEnv(G) + " |- " + stringRep(container) + " |> " + stringRep(expr) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpectedTypeInUnsupportedContainer) {
    	expectedTypeInThrowException(ruleName("expectedTypeInUnsupportedContainer") + stringRepForEnv(G) + " |- " + stringRep(container) + " |> " + stringRep(expr) + " : " + "TypeRef",
    		EXPECTEDTYPEINUNSUPPORTEDCONTAINER,
    		e_applyRuleExpectedTypeInUnsupportedContainer, container, expr, new ErrorInformation[] {new ErrorInformation(container), new ErrorInformation(expr)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleExpectedTypeInUnsupportedContainer(final RuleEnvironment G, final RuleApplicationTrace _trace_, final EObject container, final Expression expr) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = this.expressionTypeHelper.expectedExpressionTypeInEObject(container, expr, G);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> upperBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef typeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleUpperBoundTypeRef(G, _subtrace_, typeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("upperBoundTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(typeRef) + " /\\ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleUpperBoundTypeRef) {
    	upperBoundThrowException(ruleName("upperBoundTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(typeRef) + " /\\ " + "TypeRef",
    		UPPERBOUNDTYPEREF,
    		e_applyRuleUpperBoundTypeRef, typeRef, new ErrorInformation[] {new ErrorInformation(typeRef)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleUpperBoundTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef typeRef) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleUpperBoundTypeRef_1(G, typeRef));
  }
  
  private TypeRef _applyRuleUpperBoundTypeRef_1(final RuleEnvironment G, final TypeRef typeRef) throws RuleFailedException {
    return typeRef;
  }
  
  protected Result<TypeRef> upperBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Wildcard wildcard) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleUpperBoundWildcardTypeRef(G, _subtrace_, wildcard);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("upperBoundWildcardTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(wildcard) + " /\\ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleUpperBoundWildcardTypeRef) {
    	upperBoundThrowException(ruleName("upperBoundWildcardTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(wildcard) + " /\\ " + "TypeRef",
    		UPPERBOUNDWILDCARDTYPEREF,
    		e_applyRuleUpperBoundWildcardTypeRef, wildcard, new ErrorInformation[] {new ErrorInformation(wildcard)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleUpperBoundWildcardTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Wildcard wildcard) throws RuleFailedException {
    TypeRef T = null; // output parameter
    final TypeRef ub = wildcard.getDeclaredOrImplicitUpperBound();
    if ((ub != null)) {
      T = ub;
    } else {
      T = RuleEnvironmentExtensions.anyTypeRef(G);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> upperBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ExistentialTypeRef existentialTypeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleUpperBoundExistentialTypeRef(G, _subtrace_, existentialTypeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("upperBoundExistentialTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(existentialTypeRef) + " /\\ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleUpperBoundExistentialTypeRef) {
    	upperBoundThrowException(ruleName("upperBoundExistentialTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(existentialTypeRef) + " /\\ " + "TypeRef",
    		UPPERBOUNDEXISTENTIALTYPEREF,
    		e_applyRuleUpperBoundExistentialTypeRef, existentialTypeRef, new ErrorInformation[] {new ErrorInformation(existentialTypeRef)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleUpperBoundExistentialTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ExistentialTypeRef existentialTypeRef) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* G |~ existentialTypeRef.wildcard /\ T */
    Wildcard _wildcard = existentialTypeRef.getWildcard();
    Result<TypeRef> result = upperBoundInternal(G, _trace_, _wildcard);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    T = (TypeRef) result.getFirst();
    
    T = TypeUtils.<TypeRef>copy(T);
    TypeUtils.copyTypeModifiers(T, existentialTypeRef);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> upperBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UnionTypeExpression U) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleUpperBoundUnionTypeExpression(G, _subtrace_, U);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("upperBoundUnionTypeExpression") + stringRepForEnv(G) + " |~ " + stringRep(U) + " /\\ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleUpperBoundUnionTypeExpression) {
    	upperBoundThrowException(ruleName("upperBoundUnionTypeExpression") + stringRepForEnv(G) + " |~ " + stringRep(U) + " /\\ " + "TypeRef",
    		UPPERBOUNDUNIONTYPEEXPRESSION,
    		e_applyRuleUpperBoundUnionTypeExpression, U, new ErrorInformation[] {new ErrorInformation(U)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleUpperBoundUnionTypeExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UnionTypeExpression U) throws RuleFailedException {
    TypeRef T = null; // output parameter
    final Function1<TypeRef, TypeRef> _function = (TypeRef it) -> {
      TypeRef _xblockexpression = null;
      {
        TypeRef E = null;
        /* G|~ it /\ E */
        Result<TypeRef> result = upperBoundInternal(G, _trace_, it);
        checkAssignableTo(result.getFirst(), TypeRef.class);
        E = (TypeRef) result.getFirst();
        
        _xblockexpression = E;
      }
      return _xblockexpression;
    };
    T = TypeUtils.createNonSimplifiedUnionType(
      ListExtensions.<TypeRef, TypeRef>map(U.getTypeRefs(), _function));
    TypeUtils.copyTypeModifiers(T, U);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> upperBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IntersectionTypeExpression I) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleUpperBoundIntersectionTypeExpression(G, _subtrace_, I);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("upperBoundIntersectionTypeExpression") + stringRepForEnv(G) + " |~ " + stringRep(I) + " /\\ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleUpperBoundIntersectionTypeExpression) {
    	upperBoundThrowException(ruleName("upperBoundIntersectionTypeExpression") + stringRepForEnv(G) + " |~ " + stringRep(I) + " /\\ " + "TypeRef",
    		UPPERBOUNDINTERSECTIONTYPEEXPRESSION,
    		e_applyRuleUpperBoundIntersectionTypeExpression, I, new ErrorInformation[] {new ErrorInformation(I)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleUpperBoundIntersectionTypeExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IntersectionTypeExpression I) throws RuleFailedException {
    TypeRef T = null; // output parameter
    final Function1<TypeRef, TypeRef> _function = (TypeRef it) -> {
      TypeRef _xblockexpression = null;
      {
        TypeRef E = null;
        /* G|~ it /\ E */
        Result<TypeRef> result = upperBoundInternal(G, _trace_, it);
        checkAssignableTo(result.getFirst(), TypeRef.class);
        E = (TypeRef) result.getFirst();
        
        _xblockexpression = E;
      }
      return _xblockexpression;
    };
    T = TypeUtils.createNonSimplifiedIntersectionType(
      ListExtensions.<TypeRef, TypeRef>map(I.getTypeRefs(), _function));
    TypeUtils.copyTypeModifiers(T, I);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> upperBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedTypeRef ptr) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleUpperBoundParameterizedTypeRef(G, _subtrace_, ptr);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("upperBoundParameterizedTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(ptr) + " /\\ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleUpperBoundParameterizedTypeRef) {
    	upperBoundThrowException(ruleName("upperBoundParameterizedTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(ptr) + " /\\ " + "TypeRef",
    		UPPERBOUNDPARAMETERIZEDTYPEREF,
    		e_applyRuleUpperBoundParameterizedTypeRef, ptr, new ErrorInformation[] {new ErrorInformation(ptr)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleUpperBoundParameterizedTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedTypeRef ptr) throws RuleFailedException {
    TypeRef T = null; // output parameter
    Type _declaredType = ptr.getDeclaredType();
    if ((_declaredType instanceof TypeVariable)) {
      T = ptr;
    } else {
      T = ptr;
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> upperBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeRef F) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleUpperBoundFunctionTypeRef(G, _subtrace_, F);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("upperBoundFunctionTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(F) + " /\\ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleUpperBoundFunctionTypeRef) {
    	upperBoundThrowException(ruleName("upperBoundFunctionTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(F) + " /\\ " + "TypeRef",
    		UPPERBOUNDFUNCTIONTYPEREF,
    		e_applyRuleUpperBoundFunctionTypeRef, F, new ErrorInformation[] {new ErrorInformation(F)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleUpperBoundFunctionTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeRef F) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = this.applyRuleUpperBoundFunctionTypeExprOrRef(G, _trace_, F).getValue();
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> upperBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeExprOrRef F) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleUpperBoundFunctionTypeExprOrRef(G, _subtrace_, F);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("upperBoundFunctionTypeExprOrRef") + stringRepForEnv(G) + " |~ " + stringRep(F) + " /\\ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleUpperBoundFunctionTypeExprOrRef) {
    	upperBoundThrowException(ruleName("upperBoundFunctionTypeExprOrRef") + stringRepForEnv(G) + " |~ " + stringRep(F) + " /\\ " + "TypeRef",
    		UPPERBOUNDFUNCTIONTYPEEXPRORREF,
    		e_applyRuleUpperBoundFunctionTypeExprOrRef, F, new ErrorInformation[] {new ErrorInformation(F)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleUpperBoundFunctionTypeExprOrRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeExprOrRef F) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = this.typeSystemHelper.createUpperBoundOfFunctionTypeExprOrRef(G, F);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> upperBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BoundThisTypeRef boundThisTypeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleUpperBoundThisTypeRef(G, _subtrace_, boundThisTypeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("upperBoundThisTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(boundThisTypeRef) + " /\\ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleUpperBoundThisTypeRef) {
    	upperBoundThrowException(ruleName("upperBoundThisTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(boundThisTypeRef) + " /\\ " + "TypeRef",
    		UPPERBOUNDTHISTYPEREF,
    		e_applyRuleUpperBoundThisTypeRef, boundThisTypeRef, new ErrorInformation[] {new ErrorInformation(boundThisTypeRef)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleUpperBoundThisTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BoundThisTypeRef boundThisTypeRef) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = TypeUtils.createResolvedThisTypeRef(boundThisTypeRef);
    TypeUtils.copyTypeModifiers(T, boundThisTypeRef);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> upperBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeTypeRef ct) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleUpperBoundTypeTypeRef(G, _subtrace_, ct);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("upperBoundTypeTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(ct) + " /\\ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleUpperBoundTypeTypeRef) {
    	upperBoundThrowException(ruleName("upperBoundTypeTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(ct) + " /\\ " + "TypeRef",
    		UPPERBOUNDTYPETYPEREF,
    		e_applyRuleUpperBoundTypeTypeRef, ct, new ErrorInformation[] {new ErrorInformation(ct)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleUpperBoundTypeTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeTypeRef ct) throws RuleFailedException {
    TypeRef T = null; // output parameter
    final TypeArgument typeArg = ct.getTypeArg();
    if ((typeArg instanceof BoundThisTypeRef)) {
      final ParameterizedTypeRef typeArgNew = TypeUtils.createResolvedThisTypeRef(((BoundThisTypeRef)typeArg));
      T = TypeUtils.createTypeTypeRef(typeArgNew, ct.isConstructorRef());
    } else {
      T = ct;
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> lowerBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef typeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleLowerBoundTypeRef(G, _subtrace_, typeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("lowerBoundTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(typeRef) + " \\/ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleLowerBoundTypeRef) {
    	lowerBoundThrowException(ruleName("lowerBoundTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(typeRef) + " \\/ " + "TypeRef",
    		LOWERBOUNDTYPEREF,
    		e_applyRuleLowerBoundTypeRef, typeRef, new ErrorInformation[] {new ErrorInformation(typeRef)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleLowerBoundTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeRef typeRef) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleLowerBoundTypeRef_1(G, typeRef));
  }
  
  private TypeRef _applyRuleLowerBoundTypeRef_1(final RuleEnvironment G, final TypeRef typeRef) throws RuleFailedException {
    return typeRef;
  }
  
  protected Result<TypeRef> lowerBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Wildcard wildcard) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleLowerBoundWildcard(G, _subtrace_, wildcard);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("lowerBoundWildcard") + stringRepForEnv(G) + " |~ " + stringRep(wildcard) + " \\/ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleLowerBoundWildcard) {
    	lowerBoundThrowException(ruleName("lowerBoundWildcard") + stringRepForEnv(G) + " |~ " + stringRep(wildcard) + " \\/ " + "TypeRef",
    		LOWERBOUNDWILDCARD,
    		e_applyRuleLowerBoundWildcard, wildcard, new ErrorInformation[] {new ErrorInformation(wildcard)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleLowerBoundWildcard(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Wildcard wildcard) throws RuleFailedException {
    TypeRef T = null; // output parameter
    TypeRef _declaredLowerBound = wildcard.getDeclaredLowerBound();
    boolean _tripleNotEquals = (_declaredLowerBound != null);
    if (_tripleNotEquals) {
      T = wildcard.getDeclaredLowerBound();
    } else {
      T = RuleEnvironmentExtensions.bottomTypeRef(G);
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> lowerBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ExistentialTypeRef existentialTypeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleLowerBoundExistentialTypeRef(G, _subtrace_, existentialTypeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("lowerBoundExistentialTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(existentialTypeRef) + " \\/ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleLowerBoundExistentialTypeRef) {
    	lowerBoundThrowException(ruleName("lowerBoundExistentialTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(existentialTypeRef) + " \\/ " + "TypeRef",
    		LOWERBOUNDEXISTENTIALTYPEREF,
    		e_applyRuleLowerBoundExistentialTypeRef, existentialTypeRef, new ErrorInformation[] {new ErrorInformation(existentialTypeRef)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleLowerBoundExistentialTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ExistentialTypeRef existentialTypeRef) throws RuleFailedException {
    TypeRef T = null; // output parameter
    /* G |~ existentialTypeRef.wildcard \/ T */
    Wildcard _wildcard = existentialTypeRef.getWildcard();
    Result<TypeRef> result = lowerBoundInternal(G, _trace_, _wildcard);
    checkAssignableTo(result.getFirst(), TypeRef.class);
    T = (TypeRef) result.getFirst();
    
    T = TypeUtils.<TypeRef>copy(T);
    TypeUtils.copyTypeModifiers(T, existentialTypeRef);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> lowerBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UnionTypeExpression U) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleLowerBoundUnionTypeExpression(G, _subtrace_, U);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("lowerBoundUnionTypeExpression") + stringRepForEnv(G) + " |~ " + stringRep(U) + " \\/ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleLowerBoundUnionTypeExpression) {
    	lowerBoundThrowException(ruleName("lowerBoundUnionTypeExpression") + stringRepForEnv(G) + " |~ " + stringRep(U) + " \\/ " + "TypeRef",
    		LOWERBOUNDUNIONTYPEEXPRESSION,
    		e_applyRuleLowerBoundUnionTypeExpression, U, new ErrorInformation[] {new ErrorInformation(U)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleLowerBoundUnionTypeExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UnionTypeExpression U) throws RuleFailedException {
    TypeRef T = null; // output parameter
    final Function1<TypeRef, TypeRef> _function = (TypeRef it) -> {
      TypeRef _xblockexpression = null;
      {
        TypeRef E = null;
        /* G|~ it \/ E */
        Result<TypeRef> result = lowerBoundInternal(G, _trace_, it);
        checkAssignableTo(result.getFirst(), TypeRef.class);
        E = (TypeRef) result.getFirst();
        
        _xblockexpression = E;
      }
      return _xblockexpression;
    };
    T = TypeUtils.createNonSimplifiedUnionType(
      ListExtensions.<TypeRef, TypeRef>map(U.getTypeRefs(), _function));
    TypeUtils.copyTypeModifiers(T, U);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> lowerBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IntersectionTypeExpression I) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleLowerBoundIntersectionTypeExpression(G, _subtrace_, I);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("lowerBoundIntersectionTypeExpression") + stringRepForEnv(G) + " |~ " + stringRep(I) + " \\/ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleLowerBoundIntersectionTypeExpression) {
    	lowerBoundThrowException(ruleName("lowerBoundIntersectionTypeExpression") + stringRepForEnv(G) + " |~ " + stringRep(I) + " \\/ " + "TypeRef",
    		LOWERBOUNDINTERSECTIONTYPEEXPRESSION,
    		e_applyRuleLowerBoundIntersectionTypeExpression, I, new ErrorInformation[] {new ErrorInformation(I)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleLowerBoundIntersectionTypeExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IntersectionTypeExpression I) throws RuleFailedException {
    TypeRef T = null; // output parameter
    final Function1<TypeRef, TypeRef> _function = (TypeRef it) -> {
      TypeRef _xblockexpression = null;
      {
        TypeRef E = null;
        /* G|~ it \/ E */
        Result<TypeRef> result = lowerBoundInternal(G, _trace_, it);
        checkAssignableTo(result.getFirst(), TypeRef.class);
        E = (TypeRef) result.getFirst();
        
        _xblockexpression = E;
      }
      return _xblockexpression;
    };
    T = TypeUtils.createNonSimplifiedIntersectionType(
      ListExtensions.<TypeRef, TypeRef>map(I.getTypeRefs(), _function));
    TypeUtils.copyTypeModifiers(T, I);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> lowerBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedTypeRef ptr) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleLowerBoundParameterizedTypeRef(G, _subtrace_, ptr);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("lowerBoundParameterizedTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(ptr) + " \\/ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleLowerBoundParameterizedTypeRef) {
    	lowerBoundThrowException(ruleName("lowerBoundParameterizedTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(ptr) + " \\/ " + "TypeRef",
    		LOWERBOUNDPARAMETERIZEDTYPEREF,
    		e_applyRuleLowerBoundParameterizedTypeRef, ptr, new ErrorInformation[] {new ErrorInformation(ptr)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleLowerBoundParameterizedTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedTypeRef ptr) throws RuleFailedException {
    TypeRef T = null; // output parameter
    Type _declaredType = ptr.getDeclaredType();
    if ((_declaredType instanceof TypeVariable)) {
      T = ptr;
    } else {
      T = ptr;
    }
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> lowerBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeRef F) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleLowerBoundFunctionTypeRef(G, _subtrace_, F);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("lowerBoundFunctionTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(F) + " \\/ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleLowerBoundFunctionTypeRef) {
    	lowerBoundThrowException(ruleName("lowerBoundFunctionTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(F) + " \\/ " + "TypeRef",
    		LOWERBOUNDFUNCTIONTYPEREF,
    		e_applyRuleLowerBoundFunctionTypeRef, F, new ErrorInformation[] {new ErrorInformation(F)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleLowerBoundFunctionTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeRef F) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = this.applyRuleLowerBoundFunctionTypeExprOrRef(G, _trace_, F).getValue();
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> lowerBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeExprOrRef F) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleLowerBoundFunctionTypeExprOrRef(G, _subtrace_, F);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("lowerBoundFunctionTypeExprOrRef") + stringRepForEnv(G) + " |~ " + stringRep(F) + " \\/ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleLowerBoundFunctionTypeExprOrRef) {
    	lowerBoundThrowException(ruleName("lowerBoundFunctionTypeExprOrRef") + stringRepForEnv(G) + " |~ " + stringRep(F) + " \\/ " + "TypeRef",
    		LOWERBOUNDFUNCTIONTYPEEXPRORREF,
    		e_applyRuleLowerBoundFunctionTypeExprOrRef, F, new ErrorInformation[] {new ErrorInformation(F)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleLowerBoundFunctionTypeExprOrRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeExprOrRef F) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = this.typeSystemHelper.createLowerBoundOfFunctionTypeExprOrRef(G, F);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeRef> lowerBoundImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BoundThisTypeRef boundThisTypeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleLowerBoundThisTypeRef(G, _subtrace_, boundThisTypeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("lowerBoundThisTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(boundThisTypeRef) + " \\/ " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleLowerBoundThisTypeRef) {
    	lowerBoundThrowException(ruleName("lowerBoundThisTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(boundThisTypeRef) + " \\/ " + "TypeRef",
    		LOWERBOUNDTHISTYPEREF,
    		e_applyRuleLowerBoundThisTypeRef, boundThisTypeRef, new ErrorInformation[] {new ErrorInformation(boundThisTypeRef)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleLowerBoundThisTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BoundThisTypeRef boundThisTypeRef) throws RuleFailedException {
    TypeRef T = null; // output parameter
    T = RuleEnvironmentExtensions.undefinedTypeRef(G);
    TypeUtils.copyTypeModifiers(T, boundThisTypeRef);
    return new Result<TypeRef>(T);
  }
  
  protected Result<TypeArgument> substTypeVariablesImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeArgument type) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeArgument> _result_ = applyRuleSubstTypeVariablesBaseCase(G, _subtrace_, type);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("substTypeVariablesBaseCase") + stringRepForEnv(G) + " |- " + stringRep(type) + " ~> " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubstTypeVariablesBaseCase) {
    	substTypeVariablesThrowException(ruleName("substTypeVariablesBaseCase") + stringRepForEnv(G) + " |- " + stringRep(type) + " ~> " + "TypeArgument",
    		SUBSTTYPEVARIABLESBASECASE,
    		e_applyRuleSubstTypeVariablesBaseCase, type, new ErrorInformation[] {new ErrorInformation(type)});
    	return null;
    }
  }
  
  protected Result<TypeArgument> applyRuleSubstTypeVariablesBaseCase(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeArgument type) throws RuleFailedException {
    
    return new Result<TypeArgument>(_applyRuleSubstTypeVariablesBaseCase_1(G, type));
  }
  
  private TypeArgument _applyRuleSubstTypeVariablesBaseCase_1(final RuleEnvironment G, final TypeArgument type) throws RuleFailedException {
    return type;
  }
  
  protected Result<TypeArgument> substTypeVariablesImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Wildcard wildcard) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeArgument> _result_ = applyRuleSubstTypeVariablesWildcard(G, _subtrace_, wildcard);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("substTypeVariablesWildcard") + stringRepForEnv(G) + " |- " + stringRep(wildcard) + " ~> " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubstTypeVariablesWildcard) {
    	substTypeVariablesThrowException(ruleName("substTypeVariablesWildcard") + stringRepForEnv(G) + " |- " + stringRep(wildcard) + " ~> " + "Wildcard",
    		SUBSTTYPEVARIABLESWILDCARD,
    		e_applyRuleSubstTypeVariablesWildcard, wildcard, new ErrorInformation[] {new ErrorInformation(wildcard)});
    	return null;
    }
  }
  
  protected Result<TypeArgument> applyRuleSubstTypeVariablesWildcard(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Wildcard wildcard) throws RuleFailedException {
    Wildcard T = null; // output parameter
    TypeRef ub = wildcard.getDeclaredUpperBound();
    if ((ub != null)) {
      /* G |- ub ~> ub */
      Result<TypeArgument> result = substTypeVariablesInternal(G, _trace_, ub);
      checkAssignableTo(result.getFirst(), TypeRef.class);
      ub = (TypeRef) result.getFirst();
      
    }
    TypeRef lb = wildcard.getDeclaredLowerBound();
    if ((lb != null)) {
      /* G |- lb ~> lb */
      Result<TypeArgument> result_1 = substTypeVariablesInternal(G, _trace_, lb);
      checkAssignableTo(result_1.getFirst(), TypeRef.class);
      lb = (TypeRef) result_1.getFirst();
      
    }
    if (((ub != wildcard.getDeclaredUpperBound()) || (lb != wildcard.getDeclaredLowerBound()))) {
      T = TypeUtils.<Wildcard>copy(wildcard);
      T.setDeclaredUpperBound(TypeUtils.<TypeRef>copyIfContained(ub));
      T.setDeclaredLowerBound(TypeUtils.<TypeRef>copyIfContained(lb));
    } else {
      T = wildcard;
    }
    return new Result<TypeArgument>(T);
  }
  
  protected Result<TypeArgument> substTypeVariablesImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ThisTypeRef thisTypeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeArgument> _result_ = applyRuleSubstTypeVariablesThisTypeRef(G, _subtrace_, thisTypeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("substTypeVariablesThisTypeRef") + stringRepForEnv(G) + " |- " + stringRep(thisTypeRef) + " ~> " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubstTypeVariablesThisTypeRef) {
    	substTypeVariablesThrowException(ruleName("substTypeVariablesThisTypeRef") + stringRepForEnv(G) + " |- " + stringRep(thisTypeRef) + " ~> " + "ThisTypeRef",
    		SUBSTTYPEVARIABLESTHISTYPEREF,
    		e_applyRuleSubstTypeVariablesThisTypeRef, thisTypeRef, new ErrorInformation[] {new ErrorInformation(thisTypeRef)});
    	return null;
    }
  }
  
  protected Result<TypeArgument> applyRuleSubstTypeVariablesThisTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ThisTypeRef thisTypeRef) throws RuleFailedException {
    ThisTypeRef T = null; // output parameter
    /* { val BoundThisTypeRef boundRefFromEnv = G.getThisType() as BoundThisTypeRef; if (boundRefFromEnv !== null) { val boundRef = TypeUtils.createBoundThisTypeRef(boundRefFromEnv.actualThisTypeRef); boundRef.setTypingStrategy(thisTypeRef.typingStrategy); TypeUtils.copyTypeModifiers(boundRef, thisTypeRef); T = boundRef; } else { T = thisTypeRef } } or { T = thisTypeRef } */
    {
      RuleFailedException previousFailure = null;
      try {
        TypeRef _thisType = RuleEnvironmentExtensions.getThisType(G);
        final BoundThisTypeRef boundRefFromEnv = ((BoundThisTypeRef) _thisType);
        if ((boundRefFromEnv != null)) {
          final BoundThisTypeRef boundRef = TypeUtils.createBoundThisTypeRef(boundRefFromEnv.getActualThisTypeRef());
          boundRef.setTypingStrategy(thisTypeRef.getTypingStrategy());
          TypeUtils.copyTypeModifiers(boundRef, thisTypeRef);
          T = boundRef;
        } else {
          T = thisTypeRef;
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        T = thisTypeRef;
      }
    }
    return new Result<TypeArgument>(T);
  }
  
  protected Result<TypeArgument> substTypeVariablesImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ThisTypeRefStructural thisTypeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeArgument> _result_ = applyRuleSubstTypeVariablesThisTypeRefStructural(G, _subtrace_, thisTypeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("substTypeVariablesThisTypeRefStructural") + stringRepForEnv(G) + " |- " + stringRep(thisTypeRef) + " ~> " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubstTypeVariablesThisTypeRefStructural) {
    	substTypeVariablesThrowException(ruleName("substTypeVariablesThisTypeRefStructural") + stringRepForEnv(G) + " |- " + stringRep(thisTypeRef) + " ~> " + "ThisTypeRef",
    		SUBSTTYPEVARIABLESTHISTYPEREFSTRUCTURAL,
    		e_applyRuleSubstTypeVariablesThisTypeRefStructural, thisTypeRef, new ErrorInformation[] {new ErrorInformation(thisTypeRef)});
    	return null;
    }
  }
  
  protected Result<TypeArgument> applyRuleSubstTypeVariablesThisTypeRefStructural(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ThisTypeRefStructural thisTypeRef) throws RuleFailedException {
    ThisTypeRef T = null; // output parameter
    /* { val BoundThisTypeRef boundRefFromEnv = G.getThisType() as BoundThisTypeRef; val boundRef = TypeUtils.createBoundThisTypeRefStructural(boundRefFromEnv.actualThisTypeRef, thisTypeRef); TypeUtils.copyTypeModifiers(boundRef, thisTypeRef); T = boundRef; } or { T = thisTypeRef } */
    {
      RuleFailedException previousFailure = null;
      try {
        TypeRef _thisType = RuleEnvironmentExtensions.getThisType(G);
        final BoundThisTypeRef boundRefFromEnv = ((BoundThisTypeRef) _thisType);
        final BoundThisTypeRef boundRef = TypeUtils.createBoundThisTypeRefStructural(boundRefFromEnv.getActualThisTypeRef(), thisTypeRef);
        TypeUtils.copyTypeModifiers(boundRef, thisTypeRef);
        T = boundRef;
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        T = thisTypeRef;
      }
    }
    return new Result<TypeArgument>(T);
  }
  
  protected Result<TypeArgument> substTypeVariablesImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeRef typeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeArgument> _result_ = applyRuleSubstTypeVariablesInFunctionTypeRef(G, _subtrace_, typeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("substTypeVariablesInFunctionTypeRef") + stringRepForEnv(G) + " |- " + stringRep(typeRef) + " ~> " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubstTypeVariablesInFunctionTypeRef) {
    	substTypeVariablesThrowException(ruleName("substTypeVariablesInFunctionTypeRef") + stringRepForEnv(G) + " |- " + stringRep(typeRef) + " ~> " + "TypeRef",
    		SUBSTTYPEVARIABLESINFUNCTIONTYPEREF,
    		e_applyRuleSubstTypeVariablesInFunctionTypeRef, typeRef, new ErrorInformation[] {new ErrorInformation(typeRef)});
    	return null;
    }
  }
  
  protected Result<TypeArgument> applyRuleSubstTypeVariablesInFunctionTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeRef typeRef) throws RuleFailedException {
    TypeRef result = null; // output parameter
    TypeArgument _value = this.applyRuleSubstTypeVariablesInFunctionTypeExprOrRef(G, _trace_, typeRef).getValue();
    result = ((TypeRef) _value);
    return new Result<TypeArgument>(result);
  }
  
  protected Result<TypeArgument> substTypeVariablesImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeExprOrRef typeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeArgument> _result_ = applyRuleSubstTypeVariablesInFunctionTypeExprOrRef(G, _subtrace_, typeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("substTypeVariablesInFunctionTypeExprOrRef") + stringRepForEnv(G) + " |- " + stringRep(typeRef) + " ~> " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubstTypeVariablesInFunctionTypeExprOrRef) {
    	substTypeVariablesThrowException(ruleName("substTypeVariablesInFunctionTypeExprOrRef") + stringRepForEnv(G) + " |- " + stringRep(typeRef) + " ~> " + "TypeRef",
    		SUBSTTYPEVARIABLESINFUNCTIONTYPEEXPRORREF,
    		e_applyRuleSubstTypeVariablesInFunctionTypeExprOrRef, typeRef, new ErrorInformation[] {new ErrorInformation(typeRef)});
    	return null;
    }
  }
  
  protected Result<TypeArgument> applyRuleSubstTypeVariablesInFunctionTypeExprOrRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FunctionTypeExprOrRef typeRef) throws RuleFailedException {
    TypeRef result = null; // output parameter
    result = this.typeSystemHelper.createSubstitutionOfFunctionTypeExprOrRef(G, typeRef);
    return new Result<TypeArgument>(result);
  }
  
  protected Result<TypeArgument> substTypeVariablesImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ComposedTypeRef typeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeArgument> _result_ = applyRuleSubstTypeVariablesInComposedTypeRef(G, _subtrace_, typeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("substTypeVariablesInComposedTypeRef") + stringRepForEnv(G) + " |- " + stringRep(typeRef) + " ~> " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubstTypeVariablesInComposedTypeRef) {
    	substTypeVariablesThrowException(ruleName("substTypeVariablesInComposedTypeRef") + stringRepForEnv(G) + " |- " + stringRep(typeRef) + " ~> " + "ComposedTypeRef",
    		SUBSTTYPEVARIABLESINCOMPOSEDTYPEREF,
    		e_applyRuleSubstTypeVariablesInComposedTypeRef, typeRef, new ErrorInformation[] {new ErrorInformation(typeRef)});
    	return null;
    }
  }
  
  protected Result<TypeArgument> applyRuleSubstTypeVariablesInComposedTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ComposedTypeRef typeRef) throws RuleFailedException {
    ComposedTypeRef result = null; // output parameter
    boolean haveReplacement = false;
    final ArrayList<TypeRef> substTypeRefs = CollectionLiterals.<TypeRef>newArrayList();
    EList<TypeRef> _typeRefs = typeRef.getTypeRefs();
    for (final TypeRef currTypeRef : _typeRefs) {
      /* G |- currTypeRef ~> var TypeRef substTypeRef */
      TypeRef substTypeRef = null;
      Result<TypeArgument> result_1 = substTypeVariablesInternal(G, _trace_, currTypeRef);
      checkAssignableTo(result_1.getFirst(), TypeRef.class);
      substTypeRef = (TypeRef) result_1.getFirst();
      
      boolean _add = substTypeRefs.add(substTypeRef);
      /* substTypeRefs.add(substTypeRef) */
      if (!_add) {
        sneakyThrowRuleFailedException("substTypeRefs.add(substTypeRef)");
      }
      haveReplacement = (haveReplacement || (substTypeRef != currTypeRef));
    }
    if (haveReplacement) {
      result = TypeUtils.<ComposedTypeRef>copy(typeRef);
      result.getTypeRefs().clear();
      /* result.typeRefs.addAll(TypeUtils.copyAll(substTypeRefs)) */
      if (!result.getTypeRefs().addAll(TypeUtils.<TypeRef>copyAll(substTypeRefs))) {
        sneakyThrowRuleFailedException("result.typeRefs.addAll(TypeUtils.copyAll(substTypeRefs))");
      }
    } else {
      result = typeRef;
    }
    return new Result<TypeArgument>(result);
  }
  
  protected Result<TypeArgument> substTypeVariablesImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeTypeRef typeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeArgument> _result_ = applyRuleSubstTypeVariablesInTypeTypeRef(G, _subtrace_, typeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("substTypeVariablesInTypeTypeRef") + stringRepForEnv(G) + " |- " + stringRep(typeRef) + " ~> " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubstTypeVariablesInTypeTypeRef) {
    	substTypeVariablesThrowException(ruleName("substTypeVariablesInTypeTypeRef") + stringRepForEnv(G) + " |- " + stringRep(typeRef) + " ~> " + "TypeTypeRef",
    		SUBSTTYPEVARIABLESINTYPETYPEREF,
    		e_applyRuleSubstTypeVariablesInTypeTypeRef, typeRef, new ErrorInformation[] {new ErrorInformation(typeRef)});
    	return null;
    }
  }
  
  protected Result<TypeArgument> applyRuleSubstTypeVariablesInTypeTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeTypeRef typeRef) throws RuleFailedException {
    TypeTypeRef result = null; // output parameter
    /* G |- typeRef.getTypeArg ~> var TypeArgument tResult */
    TypeArgument _typeArg = typeRef.getTypeArg();
    TypeArgument tResult = null;
    Result<TypeArgument> result_1 = substTypeVariablesInternal(G, _trace_, _typeArg);
    checkAssignableTo(result_1.getFirst(), TypeArgument.class);
    tResult = (TypeArgument) result_1.getFirst();
    
    TypeArgument _typeArg_1 = typeRef.getTypeArg();
    boolean _tripleNotEquals = (_typeArg_1 != tResult);
    if (_tripleNotEquals) {
      tResult = TypeUtils.<TypeArgument>copyIfContained(tResult);
      result = TypeUtils.<TypeTypeRef>copyIfContained(typeRef);
      result.setTypeArg(tResult);
    } else {
      result = typeRef;
    }
    return new Result<TypeArgument>(result);
  }
  
  protected Result<TypeArgument> substTypeVariablesImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedTypeRef typeRef) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeArgument> _result_ = applyRuleSubstTypeVariablesInParameterizedTypeRef(G, _subtrace_, typeRef);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("substTypeVariablesInParameterizedTypeRef") + stringRepForEnv(G) + " |- " + stringRep(typeRef) + " ~> " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubstTypeVariablesInParameterizedTypeRef) {
    	substTypeVariablesThrowException(ruleName("substTypeVariablesInParameterizedTypeRef") + stringRepForEnv(G) + " |- " + stringRep(typeRef) + " ~> " + "TypeRef",
    		SUBSTTYPEVARIABLESINPARAMETERIZEDTYPEREF,
    		e_applyRuleSubstTypeVariablesInParameterizedTypeRef, typeRef, new ErrorInformation[] {new ErrorInformation(typeRef)});
    	return null;
    }
  }
  
  protected Result<TypeArgument> applyRuleSubstTypeVariablesInParameterizedTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedTypeRef typeRef) throws RuleFailedException {
    TypeRef result = null; // output parameter
    result = typeRef;
    Type _declaredType = typeRef.getDeclaredType();
    if ((_declaredType instanceof TypeVariable)) {
      Type _declaredType_1 = typeRef.getDeclaredType();
      final TypeVariable typeVar = ((TypeVariable) _declaredType_1);
      /* { var temp = env(G, typeVar, TypeRef) if (typeRef instanceof ParameterizedTypeRefStructural) { if (temp instanceof ParameterizedTypeRef) { var ptrs = TypeUtils.copyToParameterizedTypeRefStructural(temp); ptrs.setTypingStrategy(typeRef.getTypingStrategy()); temp = ptrs; } } val tempDeclaredType = temp.declaredType if (typeVar !== tempDeclaredType && (TypeUtils.isOrContainsRefToTypeVar(temp) || (tempDeclaredType !== null && tempDeclaredType.generic)) && G.get(GUARD_SUBST_TYPE_VARS -> temp) === null) { val G2 = G.wrap; G2.add(GUARD_SUBST_TYPE_VARS -> temp, Boolean.TRUE) G2 |- temp ~> result result = TypeUtils.copy(result); } else { result = TypeUtils.copy(temp); } TypeUtils.copyTypeModifiers(result, typeRef) } or { val List<TypeRef> l_raw = env(G, typeVar, List) val l = newArrayList; for(var i=0;i<l_raw.size;i++) { val temp = l_raw.get(i); val tempDeclaredType = temp.declaredType; if(typeVar !== tempDeclaredType && (TypeUtils.isOrContainsRefToTypeVar(temp) || (tempDeclaredType !== null && tempDeclaredType.generic)) && G.get(GUARD_SUBST_TYPE_VARS -> temp) === null) { val G2 = G.wrap; G2.add(GUARD_SUBST_TYPE_VARS -> temp, Boolean.TRUE) G2 |- temp ~> var TypeRef tempResult tempResult = TypeUtils.copy(tempResult); l += tempResult; } else { l += TypeUtils.copy(temp); } } result = if(typeVar.declaredCovariant) { typeSystemHelper.createIntersectionType(G,l) } else if(typeVar.declaredContravariant) { typeSystemHelper.createUnionType(G,l) } else { G.addInconsistentSubstitutions(typeVar, l); TypeRefsFactory.eINSTANCE.createUnknownTypeRef }; TypeUtils.copyTypeModifiers(result, typeRef) } or { } */
      {
        RuleFailedException previousFailure = null;
        try {
          TypeRef temp = this.<TypeRef>env(G, typeVar, TypeRef.class);
          if ((typeRef instanceof ParameterizedTypeRefStructural)) {
            if ((temp instanceof ParameterizedTypeRef)) {
              ParameterizedTypeRefStructural ptrs = TypeUtils.copyToParameterizedTypeRefStructural(((ParameterizedTypeRef)temp));
              ptrs.setTypingStrategy(((ParameterizedTypeRefStructural)typeRef).getTypingStrategy());
              temp = ptrs;
            }
          }
          final Type tempDeclaredType = temp.getDeclaredType();
          if ((((typeVar != tempDeclaredType) && (TypeUtils.isOrContainsRefToTypeVar(temp) || ((tempDeclaredType != null) && tempDeclaredType.isGeneric()))) && (G.get(Pair.<String, TypeRef>of(RuleEnvironmentExtensions.GUARD_SUBST_TYPE_VARS, temp)) == null))) {
            final RuleEnvironment G2 = RuleEnvironmentExtensions.wrap(G);
            Pair<String, TypeRef> _mappedTo = Pair.<String, TypeRef>of(RuleEnvironmentExtensions.GUARD_SUBST_TYPE_VARS, temp);
            boolean _add = G2.add(_mappedTo, Boolean.TRUE);
            /* G2.add(GUARD_SUBST_TYPE_VARS -> temp, Boolean.TRUE) */
            if (!_add) {
              sneakyThrowRuleFailedException("G2.add(GUARD_SUBST_TYPE_VARS -> temp, Boolean.TRUE)");
            }
            /* G2 |- temp ~> result */
            Result<TypeArgument> result_1 = substTypeVariablesInternal(G2, _trace_, temp);
            checkAssignableTo(result_1.getFirst(), TypeRef.class);
            result = (TypeRef) result_1.getFirst();
            
            result = TypeUtils.<TypeRef>copy(result);
          } else {
            result = TypeUtils.<TypeRef>copy(temp);
          }
          TypeUtils.copyTypeModifiers(result, typeRef);
        } catch (Exception e) {
          previousFailure = extractRuleFailedException(e);
          /* { val List<TypeRef> l_raw = env(G, typeVar, List) val l = newArrayList; for(var i=0;i<l_raw.size;i++) { val temp = l_raw.get(i); val tempDeclaredType = temp.declaredType; if(typeVar !== tempDeclaredType && (TypeUtils.isOrContainsRefToTypeVar(temp) || (tempDeclaredType !== null && tempDeclaredType.generic)) && G.get(GUARD_SUBST_TYPE_VARS -> temp) === null) { val G2 = G.wrap; G2.add(GUARD_SUBST_TYPE_VARS -> temp, Boolean.TRUE) G2 |- temp ~> var TypeRef tempResult tempResult = TypeUtils.copy(tempResult); l += tempResult; } else { l += TypeUtils.copy(temp); } } result = if(typeVar.declaredCovariant) { typeSystemHelper.createIntersectionType(G,l) } else if(typeVar.declaredContravariant) { typeSystemHelper.createUnionType(G,l) } else { G.addInconsistentSubstitutions(typeVar, l); TypeRefsFactory.eINSTANCE.createUnknownTypeRef }; TypeUtils.copyTypeModifiers(result, typeRef) } or { } */
          {
            try {
              final List<TypeRef> l_raw = this.<List>env(G, typeVar, List.class);
              final ArrayList<TypeRef> l = CollectionLiterals.<TypeRef>newArrayList();
              for (int i = 0; (i < l_raw.size()); i++) {
                final TypeRef temp_1 = l_raw.get(i);
                final Type tempDeclaredType_1 = temp_1.getDeclaredType();
                if ((((typeVar != tempDeclaredType_1) && (TypeUtils.isOrContainsRefToTypeVar(temp_1) || ((tempDeclaredType_1 != null) && tempDeclaredType_1.isGeneric()))) && (G.get(Pair.<String, TypeRef>of(RuleEnvironmentExtensions.GUARD_SUBST_TYPE_VARS, temp_1)) == null))) {
                  final RuleEnvironment G2_1 = RuleEnvironmentExtensions.wrap(G);
                  Pair<String, TypeRef> _mappedTo_1 = Pair.<String, TypeRef>of(RuleEnvironmentExtensions.GUARD_SUBST_TYPE_VARS, temp_1);
                  boolean _add_1 = G2_1.add(_mappedTo_1, Boolean.TRUE);
                  /* G2.add(GUARD_SUBST_TYPE_VARS -> temp, Boolean.TRUE) */
                  if (!_add_1) {
                    sneakyThrowRuleFailedException("G2.add(GUARD_SUBST_TYPE_VARS -> temp, Boolean.TRUE)");
                  }
                  /* G2 |- temp ~> var TypeRef tempResult */
                  TypeRef tempResult = null;
                  Result<TypeArgument> result_2 = substTypeVariablesInternal(G2_1, _trace_, temp_1);
                  checkAssignableTo(result_2.getFirst(), TypeRef.class);
                  tempResult = (TypeRef) result_2.getFirst();
                  
                  tempResult = TypeUtils.<TypeRef>copy(tempResult);
                  /* l += tempResult */
                  if (!l.add(tempResult)) {
                    sneakyThrowRuleFailedException("l += tempResult");
                  }
                } else {
                  TypeRef _copy = TypeUtils.<TypeRef>copy(temp_1);
                  /* l += TypeUtils.copy(temp) */
                  if (!l.add(_copy)) {
                    sneakyThrowRuleFailedException("l += TypeUtils.copy(temp)");
                  }
                }
              }
              TypeRef _xifexpression = null;
              boolean _isDeclaredCovariant = typeVar.isDeclaredCovariant();
              if (_isDeclaredCovariant) {
                _xifexpression = this.typeSystemHelper.createIntersectionType(G, ((TypeRef[])Conversions.unwrapArray(l, TypeRef.class)));
              } else {
                TypeRef _xifexpression_1 = null;
                boolean _isDeclaredContravariant = typeVar.isDeclaredContravariant();
                if (_isDeclaredContravariant) {
                  _xifexpression_1 = this.typeSystemHelper.createUnionType(G, ((TypeRef[])Conversions.unwrapArray(l, TypeRef.class)));
                } else {
                  UnknownTypeRef _xblockexpression = null;
                  {
                    RuleEnvironmentExtensions.addInconsistentSubstitutions(G, typeVar, l);
                    _xblockexpression = (TypeRefsFactory.eINSTANCE.createUnknownTypeRef());
                  }
                  _xifexpression_1 = _xblockexpression;
                }
                _xifexpression = _xifexpression_1;
              }
              result = _xifexpression;
              TypeUtils.copyTypeModifiers(result, typeRef);
            } catch (Exception e_1) {
              previousFailure = extractRuleFailedException(e_1);
            }
          }
        }
      }
    }
    boolean _and = false;
    Type _declaredType_2 = null;
    if (typeRef!=null) {
      _declaredType_2=typeRef.getDeclaredType();
    }
    boolean _tripleNotEquals = (_declaredType_2 != null);
    if (!_tripleNotEquals) {
      _and = false;
    } else {
      boolean _isGeneric = typeRef.getDeclaredType().isGeneric();
      _and = _isGeneric;
    }
    if (_and) {
      final int len = typeRef.getTypeArgs().size();
      boolean haveSubstitution = false;
      final TypeArgument[] argsChanged = new TypeArgument[len];
      for (int i = 0; (i < len); i++) {
        final TypeArgument arg = typeRef.getTypeArgs().get(i);
        /* G |- arg ~> var TypeArgument argSubst */
        TypeArgument argSubst = null;
        Result<TypeArgument> result_2 = substTypeVariablesInternal(G, _trace_, arg);
        checkAssignableTo(result_2.getFirst(), TypeArgument.class);
        argSubst = (TypeArgument) result_2.getFirst();
        
        if ((argSubst != arg)) {
          argsChanged[i] = argSubst;
          haveSubstitution = true;
        }
      }
      if (haveSubstitution) {
        if ((result == typeRef)) {
          result = TypeUtils.<ParameterizedTypeRef>copy(typeRef);
        }
        for (int i = 0; (i < len); i++) {
          final TypeArgument argCh = argsChanged[i];
          if ((argCh != null)) {
            result.getTypeArgs().set(i, argCh);
          }
        }
      }
    }
    if ((result instanceof StructuralTypeRef)) {
      result = this.typeSystemHelper.substTypeVariablesInStructuralMembers(G, ((StructuralTypeRef)result));
    }
    return new Result<TypeArgument>(result);
  }
  
  protected Result<TypeRef> thisTypeRefImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedTypeRef type) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleThisTypeRefParameterizedTypeRef(G, _subtrace_, type);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("thisTypeRefParameterizedTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(type) + " ~> " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleThisTypeRefParameterizedTypeRef) {
    	thisTypeRefThrowException(ruleName("thisTypeRefParameterizedTypeRef") + stringRepForEnv(G) + " |~ " + stringRep(type) + " ~> " + "BoundThisTypeRef",
    		THISTYPEREFPARAMETERIZEDTYPEREF,
    		e_applyRuleThisTypeRefParameterizedTypeRef, type, new ErrorInformation[] {new ErrorInformation(type)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleThisTypeRefParameterizedTypeRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ParameterizedTypeRef type) throws RuleFailedException {
    
    return new Result<TypeRef>(_applyRuleThisTypeRefParameterizedTypeRef_1(G, type));
  }
  
  private BoundThisTypeRef _applyRuleThisTypeRefParameterizedTypeRef_1(final RuleEnvironment G, final ParameterizedTypeRef type) throws RuleFailedException {
    BoundThisTypeRef _createBoundThisTypeRef = TypeUtils.createBoundThisTypeRef(type);
    return _createBoundThisTypeRef;
  }
  
  protected Result<TypeRef> thisTypeRefImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final EObject location) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<TypeRef> _result_ = applyRuleThisTypeRefEObject(G, _subtrace_, location);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("thisTypeRefEObject") + stringRepForEnv(G) + " |~ " + stringRep(location) + " ~> " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleThisTypeRefEObject) {
    	thisTypeRefThrowException(ruleName("thisTypeRefEObject") + stringRepForEnv(G) + " |~ " + stringRep(location) + " ~> " + "TypeRef",
    		THISTYPEREFEOBJECT,
    		e_applyRuleThisTypeRefEObject, location, new ErrorInformation[] {new ErrorInformation(location)});
    	return null;
    }
  }
  
  protected Result<TypeRef> applyRuleThisTypeRefEObject(final RuleEnvironment G, final RuleApplicationTrace _trace_, final EObject location) throws RuleFailedException {
    TypeRef T = null; // output parameter
    final FunctionOrFieldAccessor containingFunctionOrAccessor = N4JSASTUtils.getContainingFunctionOrAccessor(location);
    boolean _matched = false;
    if (containingFunctionOrAccessor instanceof ArrowFunction) {
      _matched=true;
      /* G |~ containingFunctionOrAccessor ~> T */
      Result<TypeRef> result = thisTypeRefInternal(G, _trace_, ((ArrowFunction)containingFunctionOrAccessor));
      checkAssignableTo(result.getFirst(), TypeRef.class);
      T = (TypeRef) result.getFirst();
      
    }
    if (!_matched) {
      IdentifiableElement _definedFunctionOrAccessor = null;
      if (containingFunctionOrAccessor!=null) {
        _definedFunctionOrAccessor=containingFunctionOrAccessor.getDefinedFunctionOrAccessor();
      }
      final IdentifiableElement containingTFunctionOrAccessor = _definedFunctionOrAccessor;
      final TypeRef declaredThisType = TypeSystemHelper.declaredThisType(containingTFunctionOrAccessor);
      if ((declaredThisType != null)) {
        if ((declaredThisType instanceof ParameterizedTypeRef)) {
          /* G |~ declaredThisType ~> T */
          Result<TypeRef> result = thisTypeRefInternal(G, _trace_, ((ParameterizedTypeRef)declaredThisType));
          checkAssignableTo(result.getFirst(), TypeRef.class);
          T = (TypeRef) result.getFirst();
          
        } else {
          T = declaredThisType;
        }
      } else {
        final ThisTarget thisTarget = N4JSASTUtils.getProbableThisTarget(location);
        boolean _matched_1 = false;
        if (thisTarget instanceof ObjectLiteral) {
          _matched_1=true;
          /* G |- thisTarget: T */
          Result<TypeRef> result_1 = typeInternal(G, _trace_, ((ObjectLiteral)thisTarget));
          checkAssignableTo(result_1.getFirst(), TypeRef.class);
          T = (TypeRef) result_1.getFirst();
          
        }
        if (!_matched_1) {
          if (thisTarget instanceof N4ClassifierDefinition) {
            _matched_1=true;
            Type thisTargetDEFTYPE = ((N4ClassifierDefinition)thisTarget).getDefinedType();
            if ((thisTarget instanceof N4ClassDeclaration)) {
              final TClass clazz = ((N4ClassDeclaration)thisTarget).getDefinedTypeAsClass();
              if (((clazz != null) && clazz.isStaticPolyfill())) {
                final Type actualClazz = clazz.getSuperClassRef().getDeclaredType();
                if ((actualClazz != null)) {
                  thisTargetDEFTYPE = actualClazz;
                }
              }
            }
            if ((thisTargetDEFTYPE != null)) {
              final FunctionDefinition containingFunction = N4JSASTUtils.getContainingFunction(location);
              if (((containingFunction instanceof N4MethodDeclaration) && 
                ((N4MemberDeclaration) containingFunction).isStatic())) {
                boolean _isInReturnDeclaration_Of_StaticMethod = RuleEnvironmentExtensions.isInReturnDeclaration_Of_StaticMethod(location, ((N4MethodDeclaration) containingFunction));
                if (_isInReturnDeclaration_Of_StaticMethod) {
                  /* G |~ thisTargetDEFTYPE.ref ~> T */
                  TypeRef _ref = TypeExtensions.ref(thisTargetDEFTYPE);
                  Result<TypeRef> result_1 = thisTypeRefInternal(G, _trace_, _ref);
                  checkAssignableTo(result_1.getFirst(), TypeRef.class);
                  T = (TypeRef) result_1.getFirst();
                  
                } else {
                  boolean _isInBody_Of_StaticMethod = RuleEnvironmentExtensions.isInBody_Of_StaticMethod(location, ((N4MethodDeclaration) containingFunction));
                  if (_isInBody_Of_StaticMethod) {
                    T = TypeUtils.createClassifierBoundThisTypeRef(TypeUtils.createTypeTypeRef(TypeExtensions.ref(thisTargetDEFTYPE), false));
                  } else {
                    T = TypeUtils.createConstructorTypeRef(thisTargetDEFTYPE);
                  }
                }
              } else {
                final N4FieldDeclaration n4Field = EcoreUtil2.<N4FieldDeclaration>getContainerOfType(location, N4FieldDeclaration.class);
                if (((n4Field != null) && n4Field.isStatic())) {
                  T = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
                } else {
                  final N4GetterDeclaration n4Getter = EcoreUtil2.<N4GetterDeclaration>getContainerOfType(location, N4GetterDeclaration.class);
                  if (((n4Getter != null) && n4Getter.isStatic())) {
                    T = TypeUtils.createConstructorTypeRef(thisTargetDEFTYPE);
                  } else {
                    final N4SetterDeclaration n4Setter = EcoreUtil2.<N4SetterDeclaration>getContainerOfType(location, N4SetterDeclaration.class);
                    if (((n4Setter != null) && n4Setter.isStatic())) {
                      T = TypeUtils.createConstructorTypeRef(thisTargetDEFTYPE);
                    } else {
                      /* G |~ thisTargetDEFTYPE.ref ~> T */
                      TypeRef _ref_1 = TypeExtensions.ref(thisTargetDEFTYPE);
                      Result<TypeRef> result_2 = thisTypeRefInternal(G, _trace_, _ref_1);
                      checkAssignableTo(result_2.getFirst(), TypeRef.class);
                      T = (TypeRef) result_2.getFirst();
                      
                    }
                  }
                }
              }
            } else {
              T = RuleEnvironmentExtensions.anyTypeRefDynamic(G);
            }
          }
        }
        if (!_matched_1) {
          boolean _hasGlobalObject = this.jsVariantHelper.hasGlobalObject(location);
          if (_hasGlobalObject) {
            T = RuleEnvironmentExtensions.globalObjectTypeRef(G);
          } else {
            T = RuleEnvironmentExtensions.undefinedTypeRef(G);
          }
        }
      }
    }
    return new Result<TypeRef>(T);
  }
}
