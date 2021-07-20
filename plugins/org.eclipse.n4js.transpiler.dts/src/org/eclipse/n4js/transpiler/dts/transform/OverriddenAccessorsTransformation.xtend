/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts.transform

import com.google.common.collect.Lists
import com.google.inject.Inject
import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*
import java.util.HashMap

/**
 * Transformer to deal with the inability of JavaScript to overwrite class fields by getter/setter pairs and vice versa
 */
class OverriddenAccessorsTransformation extends Transformation {

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	private MemberCollector memberCollector;
	

	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// prepare a member collector for later use
		memberCollector = containerTypesHelper.fromContext(state.resource);
	}

	override transform() {
		omitOrConvertOverridingFieldsAndAccessors();
	}

	def private void omitOrConvertOverridingFieldsAndAccessors() {
		for (rootElemRaw : state.im.scriptElements) {
			val rootElem = if (rootElemRaw instanceof ExportDeclaration) rootElemRaw.exportedElement else rootElemRaw;


			if (rootElem instanceof N4ClassDeclaration) {
				val removeMembers = new ArrayList();
				val addMembers = new ArrayList();
				val addMembersToOriginal = new HashMap();
				
				for (field : rootElem.ownedFields) {
					if (isOverriding(field)) {
						val overriddenMembers = findOverriddenMembers(field);
						if (overriddenMembers.isEmpty) {
							// cannot happen since field has no errors and @Override annotation
						} else if (overriddenMembers.size == 1) {
							val origAstNodeField = state.tracer.getOriginalASTNode(field);
							val overriddenMember = overriddenMembers.get(0);
							if (overriddenMember instanceof TGetter) {
								// consequently there is no setter defined --> add setter instead of field
								removeMembers.add(field);
								val fPar = _Fpar("value");
								fPar.declaredTypeRefNode = field.declaredTypeRefNode;
								val setter = _N4SetterDecl(field.declaredName, fPar, null);
								setter.declaredModifiers += field.declaredModifiers;
								addMembers.add(setter);
								addMembersToOriginal.put(setter, origAstNodeField);
								
							} else if (overriddenMember instanceof TSetter) {
								// consequently there is no getter defined --> add getter instead of field
								removeMembers.add(field);
								val getter = _N4GetterDecl(field.declaredName, null);
								getter.declaredTypeRefNode = field.declaredTypeRefNode;
								getter.declaredModifiers += field.declaredModifiers;
								addMembers.add(getter);
								
								state.tracer.getOriginalASTNode(field);
								addMembersToOriginal.put(getter, origAstNodeField);
							}
							
						} else if (overriddenMembers.size == 2) {
							// omit
							removeMembers.add(field);
						}
					}
				}
				
				for (accessor : (rootElem.ownedGetters + rootElem.ownedSetters)) {
					if (isOverriding(accessor)) {
						val overriddenMembers = findOverriddenMembers(accessor);
						if (overriddenMembers.isEmpty) {
							// cannot happen since field has no errors and @Override annotation
						} else if (overriddenMembers.size == 1) {
							val overriddenMember = overriddenMembers.get(0);
							if (overriddenMember instanceof TField) {
								// omit
								removeMembers.add(accessor);
							}							
						}
					}
				}
	
				rootElem.ownedMembersRaw.removeAll(removeMembers);
				rootElem.ownedMembersRaw.addAll(addMembers);
				for (addMember : addMembers) {
					// will cause JSDoc to appear at the getter
					val origAstNodeField = addMembersToOriginal.get(addMember);
					state.tracer.setOriginalASTNode(addMember, origAstNodeField);
				}
			}
		}
	}
	
	def private boolean isOverriding(AnnotableN4MemberDeclaration member) {
		return AnnotationDefinition.OVERRIDE.hasAnnotation(member);
	}
	
	/** Finds the overridden member using name comparison. */
	def private List<TMember> findOverriddenMembers(AnnotableN4MemberDeclaration member) {
		val clazz = member.eContainer() as N4ClassDeclaration;
		if (state.steCache.mapNamedElement_2_STE.get(clazz) instanceof SymbolTableEntryOriginal) {
			val steo = state.steCache.mapNamedElement_2_STE.get(clazz) as SymbolTableEntryOriginal;
			if (steo.getOriginalTarget() instanceof TClass) {
				val type = steo.getOriginalTarget() as TClass;
				val inheritedMembers = memberCollector.inheritedMembers(type);
				val overriddenMembers = IterableExtensions.filter(inheritedMembers,
					[it.getName() == member.getName()]);

				return Lists.newArrayList(overriddenMembers);
			}
		}

		return Collections.emptyList();
	}
}
