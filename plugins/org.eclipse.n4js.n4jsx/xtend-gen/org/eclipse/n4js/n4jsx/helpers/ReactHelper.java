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
package org.eclipse.n4js.n4jsx.helpers;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;
import java.io.File;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElement;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.N4JSScopeProvider;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.TypeSystemHelper;
import org.eclipse.xsemantics.runtime.Result;
import org.eclipse.xsemantics.runtime.RuleEnvironment;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

/**
 * This helper provides utilities for looking up React definitions such as React.Component or React.Element or
 * for calculating types related to React (e.g. of props property) etc.
 */
@SuppressWarnings("all")
public class ReactHelper {
  @Inject
  protected N4JSTypeSystem ts;
  
  @Inject
  protected TypeSystemHelper tsh;
  
  @Inject
  private IResourceScopeCache resourceScopeCacheHelper;
  
  @Inject
  private IScopeProvider scopeProvider;
  
  public final static String REACT_PROJECT_ID = "react";
  
  public final static String REACT_FILE_NAME = "index";
  
  public final static String REACT_COMPONENT = "Component";
  
  public final static String REACT_ELEMENT = "Element";
  
  public final static String REACT_NAMESPACE = StringExtensions.toFirstUpper(ReactHelper.REACT_PROJECT_ID);
  
  public final static String REACT_SCOPE_PREFIX = ReactHelper.REACT_FILE_NAME;
  
  public final static String REACT_KEY = ("KEY__" + ReactHelper.REACT_PROJECT_ID);
  
  public final static String REACT_DEFINITION_FILE = ((((ReactHelper.REACT_PROJECT_ID + Character.valueOf(File.separatorChar)) + ReactHelper.REACT_FILE_NAME) + ".") + N4JSGlobals.N4JSD_FILE_EXTENSION);
  
  /**
   * Check if a module is a React module.
   */
  public boolean isReactModule(final TModule module) {
    return ((module != null) && ReactHelper.REACT_PROJECT_ID.equals(module.getProjectId()));
  }
  
  /**
   * Look up React.Element in the index.
   * 
   * @param context the EObject serving the context to look for React.Element.
   */
  public TClassifier lookUpReactElement(final EObject context) {
    final TClassifier reactElement = this.lookUpReactClassifier(context, ReactHelper.REACT_ELEMENT);
    return reactElement;
  }
  
  /**
   * Look up React.Component in the index.
   * 
   * @param context the EObject serving the context to look for React.Component.
   */
  public TClassifier lookUpReactComponent(final EObject context) {
    final TClassifier reactComponent = this.lookUpReactClassifier(context, ReactHelper.REACT_COMPONENT);
    return reactComponent;
  }
  
  /**
   * Lookup React component/element type. For increased efficiency, the found results are cached.
   * 
   * @param context the EObject serving the context to look for React classifiers.
   * @param reactClassifierName the name of React classifier.
   */
  private TClassifier lookUpReactClassifier(final EObject context, final String reactClassifierName) {
    final Resource resource = context.eResource();
    final String key = ((ReactHelper.REACT_KEY + ".") + reactClassifierName);
    final Provider<TClassifier> _function = () -> {
      final TModule tModule = this.lookUpReactTModule(resource);
      if ((tModule != null)) {
        final Function1<TClassifier, Boolean> _function_1 = (TClassifier it) -> {
          String _name = it.getName();
          return Boolean.valueOf(Objects.equal(_name, reactClassifierName));
        };
        final TClassifier tClassifier = IterableExtensions.<TClassifier>findFirst(Iterables.<TClassifier>filter(tModule.getTopLevelTypes(), TClassifier.class), _function_1);
        return tClassifier;
      }
      return null;
    };
    return this.resourceScopeCacheHelper.<TClassifier>get(key, resource, _function);
  }
  
  /**
   * Look up react's main TModule in the index.
   */
  public TModule lookUpReactTModule(final Resource resource) {
    final String key = ((ReactHelper.REACT_KEY + ".") + "TMODULE");
    final Provider<TModule> _function = () -> {
      final IScope scope = ((N4JSScopeProvider) this.scopeProvider).getScopeForImplicitImports(((N4JSResource) resource));
      final IEObjectDescription desc = scope.getSingleElement(QualifiedName.create(ReactHelper.REACT_PROJECT_ID));
      EObject _eObjectOrProxy = null;
      if (desc!=null) {
        _eObjectOrProxy=desc.getEObjectOrProxy();
      }
      TModule tModule = ((TModule) _eObjectOrProxy);
      EObject _resolve = EcoreUtil2.resolve(tModule, resource);
      tModule = ((TModule) _resolve);
      return tModule;
    };
    return this.resourceScopeCacheHelper.<TModule>get(key, resource, _function);
  }
  
  /**
   * Calculate the type that an JSX element is binding to, usually class/function type
   * 
   * @param jsxElem the input JSX element
   * @return the typeref that the JSX element is binding to and null if not found
   */
  public TypeRef getJSXElementBindingType(final JSXElement jsxElem) {
    final Expression expr = jsxElem.getJsxElementName().getExpression();
    final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(expr);
    final Result<TypeRef> exprResult = this.ts.type(G, expr);
    boolean _failed = exprResult.failed();
    if (_failed) {
      return null;
    } else {
      return exprResult.getValue();
    }
  }
  
  /**
   * Calculate the "props" type of an JSX element. It is either the first type parameter of React.Component class or
   * the type of the first parameter of a functional React component
   * 
   * @param jsxElement the input JSX element
   * @return the typeref if exists and null otherwise
   */
  public TypeRef getPropsType(final JSXElement jsxElem) {
    final TypeRef exprTypeRef = this.getJSXElementBindingType(jsxElem);
    if ((exprTypeRef == null)) {
      return null;
    }
    final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(jsxElem);
    if (((exprTypeRef instanceof TypeTypeRef) && ((TypeTypeRef) exprTypeRef).isConstructorRef())) {
      final Type tclass = this.tsh.getStaticType(G, ((TypeTypeRef) exprTypeRef));
      final TClassifier tComponentClassifier = this.lookUpReactClassifier(jsxElem, ReactHelper.REACT_COMPONENT);
      if (((tComponentClassifier == null) || tComponentClassifier.getTypeVars().isEmpty())) {
        return null;
      }
      final TypeVariable reactComponentProps = tComponentClassifier.getTypeVars().get(0);
      this.tsh.addSubstitutions(G, TypeUtils.createTypeRef(tclass));
      final TypeRef reactComponentPropsTypeRef = this.ts.substTypeVariablesInTypeRef(G, TypeUtils.createTypeRef(reactComponentProps));
      return reactComponentPropsTypeRef;
    } else {
      if ((exprTypeRef instanceof FunctionTypeExprOrRef)) {
        int _length = ((Object[])Conversions.unwrapArray(((FunctionTypeExprOrRef)exprTypeRef).getFpars(), Object.class)).length;
        boolean _greaterThan = (_length > 0);
        if (_greaterThan) {
          final TFormalParameter tPropsParam = ((FunctionTypeExprOrRef)exprTypeRef).getFpars().get(0);
          return tPropsParam.getTypeRef();
        }
      }
    }
    return null;
  }
  
  /**
   * Return the type of a field or return type of a getter.
   * 
   * @param member MUST be either a field or getter (otherwise an exception is thrown).
   */
  public TypeRef typeRefOfFieldOrGetter(final TMember member, final TypeRef context) {
    if (((member instanceof TField) || (member instanceof TGetter))) {
      return this.ts.tau(member, context);
    } else {
      String _plus = (member + " must be either a TField or TGetter");
      throw new IllegalArgumentException(_plus);
    }
  }
}
