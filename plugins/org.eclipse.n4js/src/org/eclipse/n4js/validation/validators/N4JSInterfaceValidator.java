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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.n4js.validation.IssueCodes.CLF_EXTENDS_PRIMITIVE_GENERIC_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_INHERITANCE_CYCLE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_WRONG_META_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.ITF_NO_FIELD_INITIALIZER;
import static org.eclipse.n4js.validation.IssueCodes.ITF_NO_FINAL;
import static org.eclipse.n4js.validation.IssueCodes.ITF_NO_PROPERTY_BODY;
import static org.eclipse.n4js.validation.IssueCodes.STRCT_ITF_CANNOT_EXTEND_INTERFACE;
import static org.eclipse.n4js.validation.validators.StaticPolyfillValidatorExtension.internalCheckNotInStaticPolyfillModule;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 */
@SuppressWarnings("javadoc")
public class N4JSInterfaceValidator extends AbstractN4JSDeclarativeValidator implements PolyfillValidatorHost {

	@Inject
	private PolyfillValidatorFragment polyfillValidatorFragment;

	/**
	 * NEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	public static String STATIC_MEMBER_IN_ROLE = "STATIC_MEMBER_IN_ROLE";

	@Check
	public void checkN4InterfaceDeclaration(N4InterfaceDeclaration n4Interface) {

		// wrong parsed
		if (!(n4Interface.getDefinedType() instanceof TInterface)) {
			return;
		}

		if (polyfillValidatorFragment.holdsPolyfill(this, n4Interface, getCancelIndicator())) {

			holdsNoCyclicInheritance(n4Interface);

			internalCheckExtendedInterfaces(n4Interface);
			internalCheckNotFinal(n4Interface);
			getInternalCheckNoFieldInitizializer(n4Interface);
			internalCheckNotInStaticPolyfillModule(n4Interface, this);// IDE-1735
		}
	}

	// publish this method.
	@Override
	public void addIssue(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData) {
		super.addIssue(message, source, feature, issueCode, issueData);
	}

	private void internalCheckNotFinal(N4InterfaceDeclaration n4InterfaceDeclaration) {
		if (AnnotationDefinition.FINAL.hasAnnotation(n4InterfaceDeclaration)) {
			addIssue(n4InterfaceDeclaration, N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME,
					ITF_NO_FINAL.toIssueItem());
		}
	}

	// cf. IDEBUG-174
	private void getInternalCheckNoFieldInitizializer(N4InterfaceDeclaration n4Interface) {
		if (n4Interface.getTypingStrategy() == TypingStrategy.STRUCTURAL) {
			for (N4MemberDeclaration member : n4Interface.getOwnedMembers()) {
				if (member instanceof N4FieldDeclaration) {
					N4FieldDeclaration fd = (N4FieldDeclaration) member;
					if (fd.getExpression() != null) {
						addIssue(fd.getExpression(),
								ITF_NO_FIELD_INITIALIZER.toIssueItem(fd.getName(), n4Interface.getName()));
					}
				}
				if (member instanceof N4MethodDeclaration) {
					N4MethodDeclaration md = (N4MethodDeclaration) member;
					if (md.getBody() != null) {
						if (md.isCallSignature() || md.isConstructSignature()) {
							// specialized validation: N4JSMemberValidator#holdsCallConstructSignatureConstraints()
						} else {
							addIssue(md.getBody(), ITF_NO_PROPERTY_BODY.toIssueItem("Methods", "structural "));
						}
					}
				}
				if (member instanceof N4GetterDeclaration) {
					N4GetterDeclaration gd = (N4GetterDeclaration) member;
					if (gd.getBody() != null) {
						addIssue(gd.getBody(), ITF_NO_PROPERTY_BODY.toIssueItem("Getters", "structural "));
					}
				}
				if (member instanceof N4SetterDeclaration) {
					N4SetterDeclaration sd = (N4SetterDeclaration) member;
					if (sd.getBody() != null) {
						addIssue(sd.getBody(), ITF_NO_PROPERTY_BODY.toIssueItem("Setters", "structural "));
					}
				}
			}
		}
	}

	private void internalCheckExtendedInterfaces(N4InterfaceDeclaration n4Interface) {
		for (TypeReferenceNode<ParameterizedTypeRef> superIfc : n4Interface.getSuperInterfaceRefs()) {
			TypeRef extendedTypeRef = superIfc.getTypeRef();
			Type extendedType = extendedTypeRef == null ? null : extendedTypeRef.getDeclaredType();
			// note: in case extendedType.name==null, the type reference is completely invalid and other, more
			// appropriate error messages have been created elsewhere
			if (extendedType != null && extendedType.getName() != null) {

				// extended type must be an interface
				if (extendedType instanceof TInterface) {
					if (n4Interface.getTypingStrategy() == TypingStrategy.STRUCTURAL
							&& ((TInterface) extendedType).getTypingStrategy() != TypingStrategy.STRUCTURAL) {
						addIssue(superIfc, null, STRCT_ITF_CANNOT_EXTEND_INTERFACE.toIssueItem());
					}
				} else {
					if (extendedType instanceof PrimitiveType) {
						if (!N4Scheme.isFromResourceWithN4Scheme(n4Interface)) { // primitive types may be extended in
																					// built-in types
							addIssue(superIfc, null,
									CLF_EXTENDS_PRIMITIVE_GENERIC_TYPE.toIssueItem(extendedType.getName()));
						}
					} else if (RuleEnvironmentExtensions.isAnyDynamic(
							RuleEnvironmentExtensions.newRuleEnvironment(extendedType), extendedTypeRef)) {
						// allow any+ as a supertype (motivated from type alias being any+ used in d.ts files

					} else {
						IssueItem issueItem = CLF_WRONG_META_TYPE.toIssueItem(
								validatorMessageHelper.description(n4Interface), "extend",
								validatorMessageHelper.description(extendedType));
						addIssue(superIfc, null, issueItem);
					}
				}
			} else if (extendedTypeRef != null && extendedTypeRef.isAliasResolved()) {
				// not all aliases are illegal after "extends", but if we get to this point we have an illegal case:
				IssueItem issueItem = CLF_WRONG_META_TYPE.toIssueItem(validatorMessageHelper.description(n4Interface),
						"extend",
						extendedTypeRef.getTypeRefAsStringWithAliasResolution());
				addIssue(superIfc, null, issueItem);
			}
		}
	}

	private boolean holdsNoCyclicInheritance(N4InterfaceDeclaration n4InterfaceDeclaration) {
		TInterface ifc = n4InterfaceDeclaration.getDefinedTypeAsInterface();
		List<TClassifier> cycle = findCyclicInheritance(ifc);
		if (cycle != null) {
			TypeReferenceNode<ParameterizedTypeRef> miscreant = findFirst(
					n4InterfaceDeclaration.getSuperInterfaceRefs(),
					sir -> sir.getTypeRef() == null ? null : sir.getTypeRef().getDeclaredType() == cycle.get(1));
			IssueItem issueItem = CLF_INHERITANCE_CYCLE.toIssueItem(Strings.join(", ", map(cycle, tc -> tc.getName())));
			addIssue(miscreant, issueItem);
			return false;
		}
		return true;
	}
}
