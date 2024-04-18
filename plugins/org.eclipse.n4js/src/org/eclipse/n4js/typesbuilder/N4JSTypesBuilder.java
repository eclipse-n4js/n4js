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
package org.eclipse.n4js.typesbuilder;

import static org.eclipse.n4js.utils.N4JSLanguageUtils.isContainedInStaticPolyfillAware;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isContainedInStaticPolyfillModule;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.MethodDeclaration;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.NamespaceExportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.TryStatement;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.VariableDeclarationContainer;
import org.eclipse.n4js.naming.ModuleNameComputer;
import org.eclipse.n4js.naming.SpecifierConverter;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.xbase.lib.ListExtensions;

import com.google.inject.Inject;

/**
 * This class with its {@link N4JSTypesBuilder#createTModuleFromSource(DerivedStateAwareResource,boolean)
 * createTModuleFromSource()} method is the single entry point for the types builder package. The only exception is the
 * public method
 * {@link N4JSFunctionDefinitionTypesBuilder#updateTFunction(FunctionExpression, FunctionTypeExprOrRef, TypeRef)
 * updateTFunction()} in N4JSFunctionDefinitionTypesBuilder, which is called from Xsemantics.
 * <p>
 * Derives the types model from the AST, i.e. creates a {@link TModule} with its contents from a {@link Script} and its
 * children. The types model is stored at the second index of the resource. The types model contains for each exportable
 * or type defining element a corresponding entry e.g. for a {@link N4ClassDeclaration} a {@link TClass} is created.
 * Later when linking only the types are used in place of the original objects.
 * <p>
 * The types builder must not use type inference because this would lead to a resolution of lazy cross-references at a
 * too early stage. Instead, the types builder creates ComputedTypeRefs that will later be resolved either on demand or
 * by calling {@link N4JSResource#flattenModule()}.
 */
@SuppressWarnings({ "javadoc", "unused" })
public class N4JSTypesBuilder {

	@Inject(optional = true)
	TypesFactory typesFactory = TypesFactory.eINSTANCE;
	@Inject
	N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;
	@Inject
	N4JSNamespaceDeclarationTypesBuilder _n4JSNamespaceDeclarationTypesBuilder;
	@Inject
	N4JSClassDeclarationTypesBuilder _n4JSClassDeclarationTypesBuilder;
	@Inject
	N4JSInterfaceDeclarationTypesBuilder _n4JSInterfaceDeclarationTypesBuilder;
	@Inject
	N4JSEnumDeclarationTypesBuilder _n4JSEnumDeclarationTypesBuilder;
	@Inject
	N4JSTypeAliasDeclarationTypesBuilder _n4JSTypeAliasDeclarationTypesBuilder;
	@Inject
	N4JSObjectLiteralTypesBuilder _n4JSObjectLiteralTypesBuilder;
	@Inject
	N4JSFunctionDefinitionTypesBuilder _n4JSFunctionDefinitionTypesBuilder;
	@Inject
	N4JSVariableStatementTypesBuilder _n4JSVariableStatementTypesBuilder;
	@Inject
	N4JSTypesFromTypeRefBuilder _n4JSTypesFromTypeRefBuilder;
	@Inject
	N4JSImportTypesBuilder _n4JSImportTypesBuilder;
	@Inject
	N4JSExportDefinitionTypesBuilder _n4JSExportDefinitionTypesBuilder;

	@Inject
	ModuleNameComputer _moduleNameComputer;
	@Inject
	private WorkspaceAccess workspaceAccess;
	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;
	@Inject
	private SpecifierConverter specifierConverter;
	@Inject
	protected JavaScriptVariantHelper jsVariantHelper;

	/** Thrown when reconciliation of a TModule fails due to a hash mismatch. */
	public static class RelinkTModuleHashMismatchException extends IllegalStateException {
		public RelinkTModuleHashMismatchException(URI resourceURI) {
			super("cannot link existing TModule to new AST due to hash mismatch: " + resourceURI);
		}
	}

	/**
	 * When demand-loading an AST for a resource that already has a TModule (usually retrieved from the index) by
	 * calling SyntaxRelatedTElement#getAstElement(), we are facing a challenge: we could simply replace the original
	 * TModule by a new TModule created in the same way as in the standard case of loading an empty resource from
	 * source, i.e. with method {@link N4JSTypesBuilder#createTModuleFromSource(DerivedStateAwareResource, boolean)
	 * #createTModuleFromSource()}. However, this would lead to the issue of all existing references to the original,
	 * now replaced TModule to now have an invalid target object (not contained in a resource anymore, proxy resolution
	 * impossible, etc.).
	 * <p>
	 * As a solution, this method provides a 2nd mode of the types builder in which not a new TModule is created from
	 * the AST, but an existing TModule is reused, i.e. the types builder does not create anything but simply creates
	 * the bidirectional links between AST nodes and TModule elements.
	 * <p>
	 * This method should be called after the AST has been loaded, with the original TModule at second position in the
	 * resource's contents. If the AST and TModule were created from different versions of the source, checked via an
	 * MD5 hash, or the rewiring fails for other reasons, an {@link IllegalStateException} is thrown. In that case, the
	 * state of the AST and TModule are undefined (i.e. linking may have taken place partially).
	 */
	public void relinkTModuleToSource(DerivedStateAwareResource resource, boolean preLinkingPhase) {
		try (Measurement m1 = N4JSDataCollectors.dcTypesBuilder.getMeasurement();
				Measurement m2 = N4JSDataCollectors.dcTypesBuilderRelink.getMeasurement()) {
			m1.getClass(); // suppress unused variable warning from Xtend
			m2.getClass(); // suppress unused variable warning from Xtend

			doRelinkTModuleToSource(resource, preLinkingPhase);
		}
	}

	private void doRelinkTModuleToSource(DerivedStateAwareResource resource, boolean preLinkingPhase) {
		IParseResult parseResult = resource.getParseResult();
		if (parseResult != null) {

			Script script = (Script) parseResult.getRootASTElement();

			TModule module = (TModule) resource.getContents().get(1);

			String astMD5New = N4JSASTUtils.md5Hex(resource);
			if (!Objects.equals(astMD5New, module.getAstMD5())) {
				throw new RelinkTModuleHashMismatchException(resource.getURI());
			}
			module.setReconciled(true);

			_n4JSImportTypesBuilder.relinkTypeModelElementsForImports(script, module, preLinkingPhase);

			buildTypesFromTypeRefs(script, module, preLinkingPhase);

			relinkTypes(script, module, preLinkingPhase, new RelinkIndices());

			module.setAstElement(script);
			script.setModule(module);

		} else {
			throw new IllegalStateException("resource has no parse result: " + resource.getURI());
		}
	}

	/**
	 * This method is the single entry point for the types builder package. The only exception is the public method
	 * {@link N4JSFunctionDefinitionTypesBuilder#updateTFunction(FunctionExpression,FunctionTypeExprOrRef,TypeRef)
	 * updateTFunction()} in N4JSFunctionDefinitionTypesBuilder, which is called from Xsemantics.
	 * <p>
	 * Creates an {@link TModule} with the module path name (@see {@link ModuleNameComputer#getModulePath(Resource)}) of
	 * the resource (replacing the dots with slashes). For all {@link ExportableElement} and {@link TypeDefiningElement}
	 * corresponding types like {@link TClass}, {@link TInterface} are created and assigned as containment references to
	 * {@link TModule}. Afterwards the so created {@link TModule} tree is browsed for {@link TVariable}s which do not
	 * have a type reference yet. For these there right hand side expressions is checked for a
	 * {@link TypeDefiningElement} for this a {@link ParameterizedTypeRef} is created having this element as declared
	 * type. The parameterized type ref is then assigned as type ref to the {@link TVariable}.
	 */
	public void createTModuleFromSource(DerivedStateAwareResource resource, boolean preLinkingPhase) {
		try (Measurement m1 = N4JSDataCollectors.dcTypesBuilder.getMeasurement();
				Measurement m2 = N4JSDataCollectors.dcTypesBuilderCreate.getMeasurement()) {
			m1.getClass(); // suppress unused variable warning from Xtend
			m2.getClass(); // suppress unused variable warning from Xtend

			doCreateTModuleFromSource(resource, preLinkingPhase);
		}
	}

	private void doCreateTModuleFromSource(DerivedStateAwareResource resource, boolean preLinkingPhase) {
		IParseResult parseResult = resource.getParseResult();
		if (parseResult != null) {

			// UtilN4.takeSnapshotInGraphView("TB start (preLinking=="+preLinkingPhase+")",resource.resourceSet);
			Script script = (Script) parseResult.getRootASTElement();

			TModule result = typesFactory.createTModule();

			result.setAstMD5(N4JSASTUtils.md5Hex(resource));
			result.setReconciled(false);

			QualifiedName qualifiedModuleName = _moduleNameComputer.getQualifiedModuleName(resource);
			result.setSimpleName(qualifiedModuleName.getLastSegment());
			result.setQualifiedName(qualifiedNameConverter.toString(qualifiedModuleName));
			result.setPreLinkingPhase(preLinkingPhase);

			N4JSProjectConfigSnapshot project = workspaceAccess.findProjectContaining(resource);
			if (project != null) {
				result.setProjectID(project.getName());
				result.setPackageName(project.getPackageName());
				result.setVendorID(project.getVendorId());

				// main module
				String mainModuleSpec = project.getMainModule();
				if (mainModuleSpec != null) {
					result.setMainModule(
							Objects.equals(qualifiedModuleName, specifierConverter.toQualifiedName(mainModuleSpec)));
				}
			}

			_n4JSTypesBuilderHelper.copyAnnotations(result, script, preLinkingPhase);

			_n4JSImportTypesBuilder.createTypeModelElementsForImports(script, result, preLinkingPhase);

			result.setN4jsdModule(jsVariantHelper.isExternalMode(script));

			// Setting Polyfill property.
			result.setStaticPolyfillModule(isContainedInStaticPolyfillModule(result));
			result.setStaticPolyfillAware(isContainedInStaticPolyfillAware(result));

			buildTypesFromTypeRefs(script, result, preLinkingPhase);

			buildTypes(script, result, preLinkingPhase);

			result.setAstElement(script);
			script.setModule(result);
			((N4JSResource) resource).sneakyAddToContent(result);
			// UtilN4.takeSnapshotInGraphView("TB end (preLinking=="+preLinkingPhase+")",resource.resourceSet);
		} else {
			throw new IllegalStateException(resource.getURI() + " has no parse result.");
		}
	}

	/**
	 * Create types for those TypeRefs that define a type if they play the role of an AST node.
	 * <p>
	 * This has to be done up-front, because in the rest of the types builder code we do not know where such a TypeRef
	 * shows up; to avoid having to check for them at every occurrence of a TypeRef, we do this ahead of the main types
	 * builder phase.
	 */
	private void buildTypesFromTypeRefs(Script script, TModule target, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			// important to do the following in bottom-up order!
			// The structural members of a higher-level StructuralTypeRef STR may contain another StructuralTypeRef
			// STR';
			// when the TStructuralType of STR is created, the structural members are copied, including contained
			// TypeRefs;
			// at that time the lower-level STR' must already have been prepared for copying!
			// Example:
			// var ~Object with {
			// ~Object with { number fieldOfField; } field;
			// } x;
			List<Type> addTypesToTargets = new ArrayList<>();
			for (TypeRef tr : ListExtensions.reverseView(toList(filter(script.eAllContents(), TypeRef.class)))) {
				if (tr instanceof StructuralTypeRef) {
					StructuralTypeRef str = (StructuralTypeRef) tr;
					_n4JSTypesFromTypeRefBuilder.createStructuralType(str);
					if (str.getStructuralType() != null) {
						addTypesToTargets.add(str.getStructuralType());
					}
				} else if (tr instanceof FunctionTypeExpression) {
					FunctionTypeExpression ftr = (FunctionTypeExpression) tr;
					_n4JSTypesFromTypeRefBuilder.createTFunction(ftr);
					if (ftr.getDeclaredType() != null) {
						addTypesToTargets.add(ftr.getDeclaredType());
					}
				} else if (tr instanceof N4NamespaceDeclaration) {
					for (Type type : addTypesToTargets) {
						target.getInternalTypes().add(type);
					}
					addTypesToTargets.clear();
				}
			}

			for (Type type : addTypesToTargets) {
				target.getInternalTypes().add(type);
			}
		}
	}

	static class RelinkIndices {
		int namespacesIdx = 0;
		int typesIdx = 0;
		int functionIdx = 0;
		int variableIdx = 0;
	}

	private void relinkTypes(EObject container, AbstractNamespace target, boolean preLinkingPhase, RelinkIndices rlis) {
		for (EObject n : container.eContents()) {
			// relink types of n (if applicable)

			if (n instanceof N4NamespaceDeclaration) {
				rlis.namespacesIdx = relinkNamespace((N4NamespaceDeclaration) n, target, preLinkingPhase,
						rlis.namespacesIdx);
			} else if (n instanceof FunctionDefinition) {
				rlis.functionIdx = relinkFunction(n, target, preLinkingPhase, rlis.functionIdx);
			} else if (n instanceof TypeDefiningElement) {
				rlis.typesIdx = relinkType(n, target, preLinkingPhase, rlis.typesIdx);
			} else if (n instanceof VariableDeclarationContainer) {
				rlis.variableIdx = relinkType(n, target, preLinkingPhase,
						rlis.variableIdx);
			} else if (n instanceof TryStatement) {
				rlis.variableIdx = relinkType(n, target, preLinkingPhase, rlis.variableIdx);
			}

			// relink types of child nodes
			AbstractNamespace nextTarget;
			RelinkIndices nextRelinkIndices;
			if (n instanceof N4NamespaceDeclaration) {
				nextTarget = ((N4NamespaceDeclaration) n).getDefinedNamespace();
				nextRelinkIndices = new RelinkIndices();
			} else {
				nextTarget = target;
				nextRelinkIndices = rlis;
			}
			if (nextTarget != null) { // can be null in broken ASTs
				relinkTypes(n, nextTarget, preLinkingPhase, nextRelinkIndices);
			}
			// handle exports
			// -> nothing to do
			// (AST nodes of type ExportDeclaration are not TypeDefiningElements and
			// type model elements of type ExportDefinition are not SyntaxRelatedTElements,
			// so we do not have to relink anything between AST and types model here)
		}
	}

	protected int relinkNamespace(N4NamespaceDeclaration n4Namespace, AbstractNamespace target, boolean preLinkingPhase,
			int idx) {
		if (_n4JSNamespaceDeclarationTypesBuilder.relinkTNamespace(n4Namespace, target, preLinkingPhase, idx)) {
			return idx + 1;
		}
		return idx;
	}

	protected int relinkType(TypeDefiningElement other, AbstractNamespace target, boolean preLinkingPhase, int idx) {
		throw new IllegalArgumentException(
				"unknown subclass of TypeDefiningElement: " + (other == null ? null : other.eClass().getName()));
	}

	protected int relinkType(NamespaceImportSpecifier nsImpSpec, AbstractNamespace target, boolean preLinkingPhase,
			int idx) {
		// already handled up-front in N4JSNamespaceImportTypesBuilder#relinkNamespaceTypes
		return idx;
	}

	protected int relinkFunction(MethodDeclaration n4MethodDecl, AbstractNamespace target, boolean preLinkingPhase,
			int idx) {
		// methods are handled in their containing class/interface -> ignore them here
		return idx;
	}

	protected int relinkFunction(FunctionDeclaration n4FunctionDecl, AbstractNamespace target, boolean preLinkingPhase,
			int idx) {
		if (_n4JSFunctionDefinitionTypesBuilder.relinkTFunction(n4FunctionDecl, target, preLinkingPhase, idx)) {
			return idx + 1;
		}
		return idx;
	}

	/**
	 * Function expressions are special, see
	 * {@link N4JSFunctionDefinitionTypesBuilder#createTFunction(FunctionExpression,TModule,boolean)}.
	 */
	protected int relinkFunction(FunctionExpression n4FunctionExpr, AbstractNamespace target, boolean preLinkingPhase,
			int idx) {
		_n4JSFunctionDefinitionTypesBuilder.createTFunction(n4FunctionExpr, target, preLinkingPhase);
		return idx;
	}

	protected int relinkType(N4ClassDeclaration n4Class, AbstractNamespace target, boolean preLinkingPhase, int idx) {
		if (_n4JSClassDeclarationTypesBuilder.relinkTClass(n4Class, target, preLinkingPhase, idx)) {
			return idx + 1;
		}
		return idx;
	}

	protected int relinkType(N4ClassExpression n4Class, AbstractNamespace target, boolean preLinkingPhase, int idx) {
		_n4JSClassDeclarationTypesBuilder.createTClass(n4Class, target, preLinkingPhase);
		// do not increment the index
		return idx;
	}

	protected int relinkType(N4InterfaceDeclaration n4Interface, AbstractNamespace target, boolean preLinkingPhase,
			int idx) {
		if (_n4JSInterfaceDeclarationTypesBuilder.relinkTInterface(n4Interface, target, preLinkingPhase, idx)) {
			return idx + 1;
		}
		return idx;
	}

	protected int relinkType(N4EnumDeclaration n4Enum, AbstractNamespace target, boolean preLinkingPhase, int idx) {
		if (_n4JSEnumDeclarationTypesBuilder.relinkTEnum(n4Enum, target, preLinkingPhase, idx)) {
			return idx + 1;
		}
		return idx;
	}

	protected int relinkType(N4TypeAliasDeclaration n4TypeAlias, AbstractNamespace target, boolean preLinkingPhase,
			int idx) {
		if (_n4JSTypeAliasDeclarationTypesBuilder.relinkTypeAlias(n4TypeAlias, target, preLinkingPhase, idx)) {
			return idx + 1;
		}
		return idx;
	}

	protected int relinkType(ObjectLiteral objectLiteral, AbstractNamespace target, boolean preLinkingPhase, int idx) {
		_n4JSObjectLiteralTypesBuilder.createObjectLiteral(objectLiteral, target, preLinkingPhase);
		return idx;
	}

	protected int relinkType(VariableDeclarationContainer n4VarDeclContainer, AbstractNamespace target,
			boolean preLinkingPhase, int idx) {
		return _n4JSVariableStatementTypesBuilder.relinkVariableTypes(n4VarDeclContainer, target, preLinkingPhase, idx);
	}

	protected int relinkType(TryStatement tryStmnt, AbstractNamespace target, boolean preLinkingPhase, int idx) {
		return _n4JSVariableStatementTypesBuilder.relinkVariableTypes(tryStmnt, target, preLinkingPhase, idx);
	}

	protected int relinkType(NamespaceExportSpecifier n4NamespaceExportSpecifier, AbstractNamespace target,
			boolean preLinkingPhase, int idx) {
		// namespace export specifiers are handled as part of their containing ExportDeclaration -> ignore them here
		return idx;
	}

	private void buildTypes(EObject container, AbstractNamespace target, boolean preLinkingPhase) {
		for (EObject n : container.eContents()) {
			// build type for n (if applicable)
			if (n instanceof N4NamespaceDeclaration) {
				createNamespace((N4NamespaceDeclaration) n, target, preLinkingPhase);
			} else if (n instanceof FunctionDefinition) {
				createFunction(n, target, preLinkingPhase);
			} else if (n instanceof TypeDefiningElement) {
				createType(n, target, preLinkingPhase);
			} else if (n instanceof VariableDeclarationContainer) {
				// VariableStatement and ForStatement
				createType(n, target, preLinkingPhase);
			} else if (n instanceof TryStatement) {
				createType(n, target, preLinkingPhase);
			}

			// build types for child nodes
			AbstractNamespace nextTarget = (n instanceof N4NamespaceDeclaration)
					? ((N4NamespaceDeclaration) n).getDefinedNamespace()
					: target;
			if (nextTarget != null) { // can be null in broken ASTs
				buildTypes(n, nextTarget, preLinkingPhase);
			}
			// handle exports
			if (n instanceof ExportDeclaration) {
				createType(n, target, preLinkingPhase);
			} else if (n instanceof ExportableElement) {
				ExportableElement ee = (ExportableElement) n;
				if (!ee.isDeclaredExported() && ee.isExportedByNamespace()) {
					boolean isDtsExceptionCase = ResourceType.getResourceType(ee) == ResourceType.DTS
							&& ee instanceof ModifiableElement
							&& ((ModifiableElement) ee).getDeclaredModifiers().contains(N4Modifier.PRIVATE);
					if (!isDtsExceptionCase) {
						_n4JSExportDefinitionTypesBuilder.createExportDefinitionForDirectlyExportedElement(ee, target,
								preLinkingPhase);
					}
				}
			}
		}
	}

	protected void createNamespace(N4NamespaceDeclaration n4Namespace, AbstractNamespace target,
			boolean preLinkingPhase) {
		_n4JSNamespaceDeclarationTypesBuilder.createTNamespace(n4Namespace, target, preLinkingPhase);
	}

	protected void createFunction(MethodDeclaration n4MethodDecl, AbstractNamespace target, boolean preLinkingPhase) {
		// methods are handled in their containing class/interface -> ignore them here
	}

	protected void createFunction(FunctionDeclaration n4FunctionDecl, AbstractNamespace target,
			boolean preLinkingPhase) {
		_n4JSFunctionDefinitionTypesBuilder.createTFunction(n4FunctionDecl, target, preLinkingPhase);
	}

	/**
	 * Function expressions are special, see
	 * {@link N4JSFunctionDefinitionTypesBuilder#createTFunction(FunctionExpression,TModule,boolean)}.
	 */
	protected void createFunction(FunctionExpression n4FunctionExpr, AbstractNamespace target,
			boolean preLinkingPhase) {
		_n4JSFunctionDefinitionTypesBuilder.createTFunction(n4FunctionExpr, target, preLinkingPhase);
	}

	protected void createType(TypeDefiningElement other, AbstractNamespace target, boolean preLinkingPhase) {
		throw new IllegalArgumentException(
				"unknown subclass of TypeDefiningElement: " + (other == null ? null : other.eClass().getName()));
	}

	protected void createType(NamespaceImportSpecifier nsImpSpec, AbstractNamespace target, boolean preLinkingPhase) {
		// already handled up-front in #buildNamespacesTypesFromModuleImports()
	}

	protected void createType(N4ClassDeclaration n4Class, AbstractNamespace target, boolean preLinkingPhase) {
		_n4JSClassDeclarationTypesBuilder.createTClass(n4Class, target, preLinkingPhase);
	}

	protected void createType(N4ClassExpression n4Class, AbstractNamespace target, boolean preLinkingPhase) {
		_n4JSClassDeclarationTypesBuilder.createTClass(n4Class, target, preLinkingPhase);
	}

	protected void createType(N4InterfaceDeclaration n4Interface, AbstractNamespace target, boolean preLinkingPhase) {
		_n4JSInterfaceDeclarationTypesBuilder.createTInterface(n4Interface, target, preLinkingPhase);
	}

	protected void createType(N4EnumDeclaration n4Enum, AbstractNamespace target, boolean preLinkingPhase) {
		_n4JSEnumDeclarationTypesBuilder.createTEnum(n4Enum, target, preLinkingPhase);
	}

	protected void createType(N4TypeAliasDeclaration n4TypeAliasDecl, AbstractNamespace target,
			boolean preLinkingPhase) {
		_n4JSTypeAliasDeclarationTypesBuilder.createTypeAlias(n4TypeAliasDecl, target, preLinkingPhase);
	}

	protected void createType(ObjectLiteral objectLiteral, AbstractNamespace target, boolean preLinkingPhase) {
		_n4JSObjectLiteralTypesBuilder.createObjectLiteral(objectLiteral, target, preLinkingPhase);
	}

	protected void createType(VariableDeclarationContainer n4VarDeclContainer, AbstractNamespace target,
			boolean preLinkingPhase) {
		_n4JSVariableStatementTypesBuilder.createVariableTypes(n4VarDeclContainer, target, preLinkingPhase);
	}

	protected void createType(TryStatement tryStmnt, AbstractNamespace target, boolean preLinkingPhase) {
		_n4JSVariableStatementTypesBuilder.createVariableTypes(tryStmnt, target, preLinkingPhase);
	}

	protected void createType(ExportDeclaration n4ExportDeclaration, AbstractNamespace target,
			boolean preLinkingPhase) {
		_n4JSExportDefinitionTypesBuilder.createExportDefinition(n4ExportDeclaration, target, preLinkingPhase);
	}

	protected void createType(NamespaceExportSpecifier n4NamespaceExportSpecifier, AbstractNamespace target,
			boolean preLinkingPhase) {
		// namespace export specifiers are handled as part of their containing ExportDeclaration -> ignore them here
	}

	protected int relinkType(final EObject n4Class, final AbstractNamespace target, final boolean preLinkingPhase,
			final int idx) {
		if (n4Class instanceof N4ClassDeclaration) {
			return relinkType((N4ClassDeclaration) n4Class, target, preLinkingPhase, idx);
		} else if (n4Class instanceof N4ClassExpression) {
			return relinkType((N4ClassExpression) n4Class, target, preLinkingPhase, idx);
		} else if (n4Class instanceof N4InterfaceDeclaration) {
			return relinkType((N4InterfaceDeclaration) n4Class, target, preLinkingPhase, idx);
		} else if (n4Class instanceof N4EnumDeclaration) {
			return relinkType((N4EnumDeclaration) n4Class, target, preLinkingPhase, idx);
		} else if (n4Class instanceof N4TypeAliasDeclaration) {
			return relinkType((N4TypeAliasDeclaration) n4Class, target, preLinkingPhase, idx);
		} else if (n4Class instanceof ObjectLiteral) {
			return relinkType((ObjectLiteral) n4Class, target, preLinkingPhase, idx);
		} else if (n4Class instanceof NamespaceExportSpecifier) {
			return relinkType((NamespaceExportSpecifier) n4Class, target, preLinkingPhase, idx);
		} else if (n4Class instanceof NamespaceImportSpecifier) {
			return relinkType((NamespaceImportSpecifier) n4Class, target, preLinkingPhase, idx);
		} else if (n4Class instanceof TryStatement) {
			return relinkType((TryStatement) n4Class, target, preLinkingPhase, idx);
		} else if (n4Class instanceof TypeDefiningElement) {
			return relinkType((TypeDefiningElement) n4Class, target, preLinkingPhase, idx);
		} else if (n4Class instanceof VariableDeclarationContainer) {
			return relinkType((VariableDeclarationContainer) n4Class, target, preLinkingPhase, idx);
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.asList(n4Class, target, preLinkingPhase, idx).toString());
		}
	}

	protected int relinkFunction(final EObject n4FunctionDecl, final AbstractNamespace target,
			final boolean preLinkingPhase, final int idx) {
		if (n4FunctionDecl instanceof FunctionDeclaration) {
			return relinkFunction((FunctionDeclaration) n4FunctionDecl, target, preLinkingPhase, idx);
		} else if (n4FunctionDecl instanceof FunctionExpression) {
			return relinkFunction((FunctionExpression) n4FunctionDecl, target, preLinkingPhase, idx);
		} else if (n4FunctionDecl instanceof MethodDeclaration) {
			return relinkFunction((MethodDeclaration) n4FunctionDecl, target, preLinkingPhase, idx);
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.asList(n4FunctionDecl, target, preLinkingPhase, idx).toString());
		}
	}

	protected void createFunction(final EObject n4FunctionDecl, final AbstractNamespace target,
			final boolean preLinkingPhase) {
		if (n4FunctionDecl instanceof FunctionDeclaration) {
			createFunction((FunctionDeclaration) n4FunctionDecl, target, preLinkingPhase);
			return;
		} else if (n4FunctionDecl instanceof FunctionExpression) {
			createFunction((FunctionExpression) n4FunctionDecl, target, preLinkingPhase);
			return;
		} else if (n4FunctionDecl instanceof MethodDeclaration) {
			createFunction((MethodDeclaration) n4FunctionDecl, target, preLinkingPhase);
			return;
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.asList(n4FunctionDecl, target, preLinkingPhase).toString());
		}
	}

	protected void createType(final EObject n4Class, final AbstractNamespace target, final boolean preLinkingPhase) {
		if (n4Class instanceof N4ClassDeclaration) {
			createType((N4ClassDeclaration) n4Class, target, preLinkingPhase);
			return;
		} else if (n4Class instanceof N4ClassExpression) {
			createType((N4ClassExpression) n4Class, target, preLinkingPhase);
			return;
		} else if (n4Class instanceof N4InterfaceDeclaration) {
			createType((N4InterfaceDeclaration) n4Class, target, preLinkingPhase);
			return;
		} else if (n4Class instanceof N4EnumDeclaration) {
			createType((N4EnumDeclaration) n4Class, target, preLinkingPhase);
			return;
		} else if (n4Class instanceof N4TypeAliasDeclaration) {
			createType((N4TypeAliasDeclaration) n4Class, target, preLinkingPhase);
			return;
		} else if (n4Class instanceof ObjectLiteral) {
			createType((ObjectLiteral) n4Class, target, preLinkingPhase);
			return;
		} else if (n4Class instanceof ExportDeclaration) {
			createType((ExportDeclaration) n4Class, target, preLinkingPhase);
			return;
		} else if (n4Class instanceof NamespaceExportSpecifier) {
			createType((NamespaceExportSpecifier) n4Class, target, preLinkingPhase);
			return;
		} else if (n4Class instanceof NamespaceImportSpecifier) {
			createType((NamespaceImportSpecifier) n4Class, target, preLinkingPhase);
			return;
		} else if (n4Class instanceof TryStatement) {
			createType((TryStatement) n4Class, target, preLinkingPhase);
			return;
		} else if (n4Class instanceof TypeDefiningElement) {
			createType((TypeDefiningElement) n4Class, target, preLinkingPhase);
			return;
		} else if (n4Class instanceof VariableDeclarationContainer) {
			createType((VariableDeclarationContainer) n4Class, target, preLinkingPhase);
			return;
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.asList(n4Class, target, preLinkingPhase).toString());
		}
	}
}
