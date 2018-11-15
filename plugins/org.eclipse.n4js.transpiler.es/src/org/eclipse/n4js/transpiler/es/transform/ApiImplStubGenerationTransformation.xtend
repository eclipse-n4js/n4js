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
package org.eclipse.n4js.transpiler.es.transform

import com.google.inject.Inject
import java.util.stream.Stream
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.AnnotableScriptElement
import org.eclipse.n4js.n4JS.ExportableElement
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.ScriptElement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresBefore
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.es.assistants.DelegationAssistant
import org.eclipse.n4js.transpiler.utils.MissingApiMembersForTranspiler
import org.eclipse.n4js.transpiler.utils.ScriptApiTracker
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.validation.N4JSElementKeywordProvider

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.transpiler.utils.ScriptApiTracker.firstProjectComparisonAdapter

/**
 * Generation of code for missing implementations in projects implementing a specific API.
 */
@RequiresBefore(MemberPatchingTransformation)
class ApiImplStubGenerationTransformation extends Transformation {

	@Inject private DelegationAssistant delegationAssistant;
	@Inject private TypeAssistant typeAssistant;
	@Inject private ScriptApiTracker scriptApiTracker;
	@Inject private ContainerTypesHelper containerTypesHelper;
	@Inject private N4JSElementKeywordProvider n4jsElementKeywordProvider;


	override assertPreConditions() {
	}
	override assertPostConditions() {
	}

	override analyze() {
		// perform API/Impl compare for the resource to compile
		scriptApiTracker.initApiCompare(state.resource.script);
		// (reminder: code in #analyze() will be invoked once per resource to compile, so this is what we want)
	}

	override transform() {
		// add stubs for missing members in EXISTING classes and interfaces
		collectNodes(state.im, N4ClassifierDeclaration, false).forEach[addMissingMembers];
		// add missing types (classes, interfaces, enums) and other top-level elements (declared functions, variables)
		addMissingTopLevelElements();
	}

	def private void addMissingMembers(N4ClassifierDeclaration classifierDecl) {
		val type = state.info.getOriginalDefinedType(classifierDecl);
		val mamft = createMAMFT(type);

		// add API/Impl method stubs
		for(m : mamft.missingApiMethods) {
			val member = createApiImplStub(classifierDecl, m);
			classifierDecl.ownedMembersRaw += member;
		}
		// add API/Impl field accessor stubs
		for(accTuple : mamft.missingApiAccessorTuples) {
			if(accTuple.getter!==null) {
				val g = accTuple.getter;
				val member = createApiImplStub(classifierDecl, g);
				classifierDecl.ownedMembersRaw += member;
			}
			if(accTuple.setter!==null) {
				val s = accTuple.setter;
				val member = createApiImplStub(classifierDecl, s);
				classifierDecl.ownedMembersRaw += member;
			}
		}


		// add delegates to inherited fields/getters/setters shadowed by an owned setter XOR getter
		// NOTE: Partial shadowing in general is disallowed by validation. However, in incomplete
		// API-impl situation we still support this feature here to propagate generated stubs for
		// test reporting-purposes.
		for(accTuple : mamft.missingApiAccessorTuples) {
			if(accTuple.inheritedGetter!==null && accTuple.getter===null && accTuple.setter!==null) {
				// an owned setter is shadowing an inherited getter -> delegate to the inherited getter
				val delegator = delegationAssistant.createDelegatingMember(type, accTuple.inheritedGetter);
				classifierDecl.ownedMembersRaw += delegator;
			}
			if(accTuple.inheritedSetter!==null && accTuple.getter!==null && accTuple.setter===null) {
				// an owned getter is shadowing an inherited setter -> delegate to the inherited setter
				val delegator = delegationAssistant.createDelegatingMember(type, accTuple.inheritedSetter);
				classifierDecl.ownedMembersRaw += delegator;
			}
		}


	}

	def private void addMissingTopLevelElements() {

		val script = state.resource.script;

		scriptApiTracker.initApiCompare(script)
		val comparison = script.eResource.firstProjectComparisonAdapter.orElse(null);
		if (null === comparison) {
			return;
		}

		comparison.getEntryFor(script.module).allChildren.toIterable
			.filter[null === getElementImpl(0)] // no implementation
			.forEach[
				switch x:elementAPI{
					TMethod : { /* do nothing */ }
					TFunction : missing(x)
					TClass : missing(x)
					TInterface : missing(x)
					TEnum : missing(x)
					TVariable : missing(x)
				}
			];
	}

	def private dispatch missing(TInterface tinter){
		val  stub0 = _N4InterfaceDeclaration( tinter.name ) ;

		// annotations
		stub0.annotationList = _AnnotationList( tinter.annotations.map[ AnnotationDefinition.find(it.name) ] )

		// export
		val stub = wrapExported(tinter.isExported, stub0) as ScriptElement

		// members
		// (in an interface stub we need member stubs for static AND non-static members)
		val members = state.memberCollector.members(tinter, false, false);
		for(TMember m : members) {
			if(!(m instanceof TField)) {
				stub0.ownedMembersRaw += createApiImplStub(stub0, m);
			}
		}

		appendToScript(stub);

		state.info.setOriginalDefinedType(stub0, tinter);

		createSymbolTableEntryIMOnly(stub0)
	}
	def private dispatch missing(TClass tclass){
		val  stub0 = _N4ClassDeclaration( tclass.name ) =>[
			// at least a ctor throwing an exception is required here.
			it.ownedMembersRaw += _N4MethodDecl("constructor",
				_ThrowStmnt( _NewExpr(
					_IdentRef(   N4ApiNotImplementedErrorSTE ),
					_StringLiteral( '''Class «tclass.name» is not implemented yet.''')
				) ) )
		];

		// annotations
		stub0.annotationList = _AnnotationList( tclass.annotations.map[ AnnotationDefinition.find(it.name) ] )

		// export
		val stub = wrapExported(tclass.isExported, stub0) as ScriptElement

		// members
		// (in a class stub we need member stubs ONLY for static members)
		val members = state.memberCollector.members(tclass, false, false);
		for(TMember m : members) {
			if(!(m instanceof TField) && m.static) {
				stub0.ownedMembersRaw += createApiImplStub(stub0, m);
			}
		}

		appendToScript(stub);

		state.info.setOriginalDefinedType(stub0, tclass);

		createSymbolTableEntryIMOnly(stub0)
	}
	def private dispatch missing(TEnum tenum){

		val stringBased = TypeSystemHelper.isStringBasedEnumeration(tenum);

		val stub0 = _EnumDeclaration(tenum.name, tenum.literals.map[ _EnumLiteral(name, name) ] );

		// exported
		var ScriptElement stub = wrapExported( tenum.isExported ,
			stub0
		) as ScriptElement;

		// string based
		if( stringBased ) {
			(stub as AnnotableScriptElement).annotationList =_AnnotationList( #[AnnotationDefinition.STRING_BASED] )
		}

		appendToScript( stub )

		state.info.setOriginalDefinedType(stub0, tenum);

		createSymbolTableEntryIMOnly(stub0)
	}

	def private appendToScript(ScriptElement stub) {
		val script = state.im;
		if ( script.scriptElements.isEmpty ) script.scriptElements+=stub
		else insertAfter ( script.scriptElements.last, stub );
	}

	def private dispatch missing(TVariable tvar){
		missingFuncOrVar(tvar, tvar.isExported,"variable")
	}
	def private dispatch missing(TFunction func){
		missingFuncOrVar(func, func.isExported,"function")
	}

	def private N4ApiNotImplementedErrorSTE() {
		 steFor_N4ApiNotImplementedError;
	}

	def private missingFuncOrVar(IdentifiableElement func, boolean exported, String description) {
		val funcSTE = func.getSymbolTableEntryOriginal(true); // createSymbolTableEntry(func);

		val funcDecl = _FunDecl( funcSTE.name, _ThrowStmnt( _NewExpr(
			_IdentRef(   N4ApiNotImplementedErrorSTE ),
			_StringLiteral( '''«description» «funcSTE.name» is not implemented yet.''')
			) ) )
			=> [
				// Do some more configuration.
			];
		val stub = wrapExported(exported,funcDecl);

		insertAfter( state.im.scriptElements.last , stub );
	}

	def private wrapExported( boolean exported, ExportableElement toExportOrNotToExport )
	{
		if( exported ) _ExportDeclaration( toExportOrNotToExport ) else toExportOrNotToExport
	}

	/**
	 * Creates a member that servers as the stub for a missing member on implementation side, corresponding to the given
	 * member <code>apiMember</code> on API side.
	 */
	def private N4MemberDeclaration createApiImplStub(N4ClassifierDeclaration classifierDecl, TMember apiMember) {
		// here we create:
		//
		//     public m() {  // or a getter or setter
		//         throw new N4ApiNotImplementedError("API for method C.m not implemented yet.");
		//     }
		//
		val N4ApiNotImplementedErrorSTE = steFor_N4ApiNotImplementedError;
		val typeName = classifierDecl.name;
		val memberKeyword = n4jsElementKeywordProvider.keyword(apiMember);
		val memberName = apiMember.name;
		return _N4MemberDecl(apiMember,
			_ThrowStmnt(
				_NewExpr(_IdentRef(N4ApiNotImplementedErrorSTE), _StringLiteral(
					'''API for «memberKeyword» «typeName».«memberName» not implemented yet.'''
				))
			)
		);
	}

	/** Converts the stream into an iterable. */
	def private <T> Iterable<T> toIterable(Stream<T> stream) {
		return [stream.iterator];
	}

	// note: the following uses logic from old transpiler (MissingApiMembersForTranspiler, ScriptApiTracker)
	def private MissingApiMembersForTranspiler createMAMFT(TClassifier classifier) {
		val cmoft = typeAssistant.getOrCreateCMOFT(classifier);
		return MissingApiMembersForTranspiler.create(containerTypesHelper, scriptApiTracker,
			classifier, cmoft, state.resource.script);
	}
}
