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
package org.eclipse.n4js.typesbuilder

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.ExportableElement
import org.eclipse.n4js.n4JS.ExportedVariableStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.MethodDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassExpression
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.TypeDefiningElement
import org.eclipse.n4js.naming.ModuleNameComputer
import org.eclipse.n4js.naming.SpecifierConverter
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.resource.DerivedStateAwareResource

import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * This class with its {@link N4JSTypesBuilder#createTModuleFromSource(DerivedStateAwareResource,boolean) createTModuleFromSource()}
 * method is the single entry point for the types builder package. The only exception is the public method
 * {@link N4JSFunctionDefinitionTypesBuilder#updateTFunction(FunctionExpression, FunctionTypeExprOrRef, TypeRef) updateTFunction()}
 * in N4JSFunctionDefinitionTypesBuilder, which is called from Xsemantics.
 * <p>
 * Derives the types model from the AST, i.e. creates a {@link TModule} with its contents
 * from a {@link Script} and its children. The types model is stored at the second index of the resource.
 * The types model contains for each exportable or type defining element a corresponding entry e.g.
 * for a {@link N4ClassDeclaration} a {@link TClass} is created. Later when linking only the types are
 * used in place of the original objects.
 * <p>
 * The types builder must not use type inference because this would lead to a resolution of lazy
 * cross-references at a too early stage. Instead, the types builder creates ComputedTypeRefs that
 * will later be resolved either on demand or by calling {@link N4JSResource#flattenModule()}.
 */
public class N4JSTypesBuilder {

	@Inject(optional=true) TypesFactory typesFactory = TypesFactory.eINSTANCE
	@Inject extension N4JSTypesBuilderHelper
	@Inject extension N4JSClassDeclarationTypesBuilder
	@Inject extension N4JSInterfaceDeclarationTypesBuilder
	@Inject extension N4JSEnumDeclarationTypesBuilder
	@Inject extension N4JSObjectLiteralTypesBuilder
	@Inject extension N4JSFunctionDefinitionTypesBuilder
	@Inject extension N4JSVariableStatementTypesBuilder
	@Inject extension N4JSTypesFromTypeRefBuilder
	@Inject extension ModuleNameComputer
	@Inject private IN4JSCore n4jscore
	@Inject private IQualifiedNameConverter qualifiedNameConverter
	@Inject private SpecifierConverter specifierConverter
	@Inject protected JavaScriptVariantHelper jsVariantHelper;


	/**
	 * This method is the single entry point for the types builder package. The only exception is the public method
	 * {@link N4JSFunctionDefinitionTypesBuilder#updateTFunction(FunctionExpression,FunctionTypeExprOrRef,TypeRef) updateTFunction()}
	 * in N4JSFunctionDefinitionTypesBuilder, which is called from Xsemantics.
	 * <p>
	 * Creates an {@link TModule} with the module path name (@see {@link ModuleNameComputer#getModulePath(Resource)})
	 * of the resource (replacing the dots with slashes). For all {@link ExportableElement} and
	 * {@link TypeDefiningElement} corresponding types like {@link TClass}, {@link TInterface} are created and assigned
	 * as containment references to {@link TModule}. Afterwards the so created {@link TModule} tree is browsed for
	 * {@link TVariable}s which do not have a type reference yet. For these there right hand side expressions is
	 * checked for a {@link TypeDefiningElement} for this a {@link ParameterizedTypeRef} is created having this element
	 * as declared type. The parameterized type ref is then assigned as type ref to the {@link TVariable}.
	 */
	def public void createTModuleFromSource(DerivedStateAwareResource resource, boolean preLinkingPhase) {
		val parseResult = resource.getParseResult();
		if (parseResult !== null) {

//			UtilN4.takeSnapshotInGraphView("TB start (preLinking=="+preLinkingPhase+")",resource.resourceSet);

			val script = parseResult.rootASTElement as Script;

			val TModule result = typesFactory.createTModule;
			var qualifiedModuleName = resource.qualifiedModuleName;
			result.qualifiedName = qualifiedNameConverter.toString(qualifiedModuleName);
			result.preLinkingPhase = preLinkingPhase;

			val optionalProject = n4jscore.findProject(resource.URI);
			if (optionalProject.present) {
				val project = optionalProject.get;
				result.projectId = project.projectId;
				result.vendorID = project.vendorID;
				result.moduleLoader = project.moduleLoader?.literal;

				//main module
				val mainModuleSpec = project.mainModule;
				if (mainModuleSpec !== null) {
					result.mainModule = resource.qualifiedModuleName == specifierConverter.toQualifiedName(mainModuleSpec);
				}
			}

			result.copyAnnotations(script, preLinkingPhase);

			script.buildNamespacesTypesFromModuleImports(result,preLinkingPhase);

			result.n4jsdModule = jsVariantHelper.isExternalMode(script);

			// Setting Polyfill property.
			result.staticPolyfillModule = result.isContainedInStaticPolyfillModule;
			result.staticPolyfillAware = result.isContainedInStaticPolyfillAware;

			// create types for those TypeRefs that define a type if they play the role of an AST node
			// (has to be done up-front, because in the rest of the types builder code we do not know
			// where such a TypeRef shows up; to avoid having to check for them at every occurrence of
			// a TypeRef, we do this here)
			script.buildTypesFromTypeRefs(result,preLinkingPhase);

			script.buildTypes(result,preLinkingPhase);

			result.astElement = script;
			script.module = result;
			(resource as N4JSResource).sneakyAddToContent(result);

//			UtilN4.takeSnapshotInGraphView("TB end (preLinking=="+preLinkingPhase+")",resource.resourceSet);

		} else {
			throw new IllegalStateException(resource.URI + " has no parse result.");
		}
	}


	def private void buildNamespacesTypesFromModuleImports(Script script, TModule target, boolean preLinkingPhase) {
		if(!preLinkingPhase) {
			//process namespace imports
			for (importDeclaration : script.scriptElements.filter(ImportDeclaration).toList) {
				val namespaceImportSpecifiers = importDeclaration.importSpecifiers.filter(NamespaceImportSpecifier).toList
				if(!namespaceImportSpecifiers.empty){
					//for NamespaceImportpecifiers there is only ImportSpecifier one in the ImportDeclaration
					val namespaceImportSpecifier = namespaceImportSpecifiers.head
					val type = TypesFactory.eINSTANCE.createModuleNamespaceVirtualType
					type.name = namespaceImportSpecifier.alias
					/* don't resolve module proxy */
					type.module = importDeclaration.eGet(N4JSPackage.eINSTANCE.importDeclaration_Module, false)  as TModule;
					type.declaredDynamic = namespaceImportSpecifier.declaredDynamic;
					// link
					type.astElement = namespaceImportSpecifier;
					namespaceImportSpecifier.definedType = type;

					target.internalTypes += type;
				}
			}
		}
	}

	def private void buildTypesFromTypeRefs(Script script, TModule target, boolean preLinkingPhase) {
		if(!preLinkingPhase) {
			// important to do the following in bottom-up order!
			// The structural members of a higher-level StructuralTypeRef STR may contain another StructuralTypeRef STR';
			// when the TStructuralType of STR is created, the structural members are copied, including contained TypeRefs;
			// at that time the lower-level STR' must already have been prepared for copying!
			// Example:
			// var ~Object with {
			//     ~Object with { number fieldOfField; } field;
			// } x;
			for (tr : script.eAllContents.filter(TypeRef).toList.reverseView) {
				switch tr {
					StructuralTypeRef:
						tr.createStructuralType(target)
					FunctionTypeExpression:
						tr.createTFunction(target)
				}
			}
		}
	}

	def private void buildTypes(Script script, TModule target, boolean preLinkingPhase) {
		for (n : script.eAllContents.toIterable) {
			switch n {
				TypeDefiningElement:
					n.createType(target, preLinkingPhase)
				ExportedVariableStatement:
					n.createType(target, preLinkingPhase)
			}
		}
	}

	def protected dispatch void createType(TypeDefiningElement other, TModule target, boolean preLinkingPhase) {
		throw new IllegalArgumentException("unknown subclass of TypeDefiningElement: "+other?.eClass.name);
	}

	def protected dispatch void createType(NamespaceImportSpecifier nsImpSpec, TModule target, boolean preLinkingPhase) {
		// already handled up-front in #buildNamespacesTypesFromModuleImports()
	}

	def protected dispatch void createType(N4ClassDeclaration n4Class, TModule target, boolean preLinkingPhase) {
		n4Class.createTClass(target, preLinkingPhase)
	}

	def protected dispatch void createType(N4ClassExpression n4Class, TModule target, boolean preLinkingPhase) {
		n4Class.createTClass(target, preLinkingPhase)
	}

	def protected dispatch void createType(N4InterfaceDeclaration n4Interface, TModule target, boolean preLinkingPhase) {
		n4Interface.createTInterface(target, preLinkingPhase)
	}

	def protected dispatch void createType(N4EnumDeclaration n4Enum, TModule target, boolean preLinkingPhase) {
		n4Enum.createTEnum(target, preLinkingPhase)
	}

	def protected dispatch void createType(ObjectLiteral objectLiteral, TModule target, boolean preLinkingPhase) {
		objectLiteral.createObjectLiteral(target, preLinkingPhase)
	}

	def protected dispatch void createType(MethodDeclaration n4MethodDecl, TModule target, boolean preLinkingPhase) {
		// methods are handled in their containing class/interface -> ignore them here
	}

	def protected dispatch void createType(FunctionDeclaration n4FunctionDecl, TModule target, boolean preLinkingPhase) {
		n4FunctionDecl.createTFunction(target, preLinkingPhase)
	}

	/** Function expressions are special, see {@link N4JSFunctionDefinitionTypesBuilder#createTFunction(FunctionExpression,TModule,boolean)}. */
	def protected dispatch void createType(FunctionExpression n4FunctionExpr, TModule target, boolean preLinkingPhase) {
		n4FunctionExpr.createTFunction(target, preLinkingPhase)
	}

	def protected dispatch void createType(ExportedVariableStatement n4VariableStatement, TModule target, boolean preLinkingPhase) {
		n4VariableStatement.createVariableTypes(target, preLinkingPhase)
	}
}
