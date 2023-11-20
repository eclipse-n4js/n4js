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
package org.eclipse.n4js.transpiler.dts.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Fpar;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4GetterDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4SetterDecl;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.operator_plus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresBefore;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.util.MemberList;

import com.google.common.collect.Lists;

/**
 * Transformer to deal with the inability of JavaScript to overwrite class fields by getter/setter pairs and vice versa.
 * <p>
 * Dependencies:
 * <ul>
 * <li>requires {@link InferredTypesTransformation} to run before, because the fields being converted to getters/setters
 * by this transformation might have an inferred type that must be available in the IM at time of conversion.
 * </ul>
 */
@RequiresBefore(InferredTypesTransformation.class)
public class OverriddenAccessorsTransformation extends Transformation {

	@Override
	public void assertPreConditions() {
		// empty
	}

	@Override
	public void assertPostConditions() {
		// empty
	}

	@Override
	public void analyze() {
		// empty
	}

	@Override
	public void transform() {
		omitOrConvertOverridingFieldsAndAccessors();
	}

	private void omitOrConvertOverridingFieldsAndAccessors() {
		for (ScriptElement rootElemRaw : getState().im.getScriptElements()) {
			ScriptElement rootElem = (rootElemRaw instanceof ExportDeclaration)
					? ((ExportDeclaration) rootElemRaw).getExportedElement()
					: rootElemRaw;

			if (rootElem instanceof N4ClassDeclaration) {
				N4ClassDeclaration classDecl = (N4ClassDeclaration) rootElem;
				List<N4MemberDeclaration> removeMembers = new ArrayList<>();
				List<N4MemberDeclaration> addMembers = new ArrayList<>();
				Map<N4MemberDeclaration, EObject> addMembersToOriginal = new HashMap<>();

				for (N4FieldDeclaration field : classDecl.getOwnedFields()) {
					if (isOverriding(field)) {
						List<TMember> overriddenMembers = findOverriddenMembers(field);
						if (overriddenMembers.isEmpty()) {
							// cannot happen since field has no errors and @Override annotation
						} else if (overriddenMembers.size() == 1) {
							EObject origAstNodeField = getState().tracer.getOriginalASTNode(field);
							TMember overriddenMember = overriddenMembers.get(0);
							if (overriddenMember instanceof TGetter) {
								// consequently there is no setter defined --> add setter instead of field
								removeMembers.add(field);
								FormalParameter fPar = _Fpar("value");
								fPar.setDeclaredTypeRefNode(field.getDeclaredTypeRefNode());
								N4SetterDeclaration setter = _N4SetterDecl(field.getDeclaredName(), fPar, null);
								setter.getDeclaredModifiers().addAll(field.getDeclaredModifiers());
								addMembers.add(setter);
								addMembersToOriginal.put(setter, origAstNodeField);

							} else if (overriddenMember instanceof TSetter) {
								// consequently there is no getter defined --> add getter instead of field
								removeMembers.add(field);
								N4GetterDeclaration getter = _N4GetterDecl(field.getDeclaredName(), null);
								getter.setDeclaredTypeRefNode(field.getDeclaredTypeRefNode());
								getter.getDeclaredModifiers().addAll(field.getDeclaredModifiers());
								addMembers.add(getter);

								getState().tracer.getOriginalASTNode(field);
								addMembersToOriginal.put(getter, origAstNodeField);
							}

						} else if (overriddenMembers.size() == 2) {
							// omit
							removeMembers.add(field);
						}
					}
				}

				for (AnnotableN4MemberDeclaration accessor : operator_plus(classDecl.getOwnedGetters(),
						classDecl.getOwnedSetters())) {
					if (isOverriding(accessor)) {
						List<TMember> overriddenMembers = findOverriddenMembers(accessor);
						if (overriddenMembers.isEmpty()) {
							// cannot happen since field has no errors and @Override annotation
						} else if (overriddenMembers.size() == 1) {
							TMember overriddenMember = overriddenMembers.get(0);
							if (overriddenMember instanceof TField) {
								// omit
								removeMembers.add(accessor);
							}
						}
					}
				}

				classDecl.getOwnedMembersRaw().removeAll(removeMembers);
				classDecl.getOwnedMembersRaw().addAll(addMembers);
				for (N4MemberDeclaration addMember : addMembers) {
					// will cause JSDoc to appear at the getter
					EObject origAstNodeField = addMembersToOriginal.get(addMember);
					getState().tracer.setOriginalASTNode(addMember, origAstNodeField);
				}
			}
		}
	}

	private boolean isOverriding(AnnotableN4MemberDeclaration member) {
		return AnnotationDefinition.OVERRIDE.hasAnnotation(member);
	}

	/** Finds the overridden member using name comparison. */
	private List<TMember> findOverriddenMembers(AnnotableN4MemberDeclaration member) {
		N4ClassDeclaration clazz = (N4ClassDeclaration) member.eContainer();
		if (getState().steCache.mapNamedElement_2_STE.get(clazz) instanceof SymbolTableEntryOriginal) {
			SymbolTableEntryOriginal steo = (SymbolTableEntryOriginal) getState().steCache.mapNamedElement_2_STE
					.get(clazz);
			if (steo.getOriginalTarget() instanceof TClass) {
				TClass type = (TClass) steo.getOriginalTarget();
				MemberList<TMember> inheritedMembers = getState().memberCollector.inheritedMembers(type);
				Iterable<TMember> overriddenMembers = filter(inheritedMembers, m -> m.getName() == member.getName());

				return Lists.newArrayList(overriddenMembers);
			}
		}

		return Collections.emptyList();
	}
}
