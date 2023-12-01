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

import static org.eclipse.n4js.N4JSLanguageConstants.EXPORT_KEYWORD;
import static org.eclipse.n4js.N4JSLanguageConstants.EXTENDS_KEYWORD;
import static org.eclipse.n4js.N4JSLanguageConstants.IMPLEMENTS_KEYWORD;
import static org.eclipse.n4js.validation.IssueCodes.AST_CATCH_VAR_TYPED;
import static org.eclipse.n4js.validation.IssueCodes.EXP_ONLY_TOP_LEVEL_ELEMENTS;
import static org.eclipse.n4js.validation.IssueCodes.SYN_KW_EXTENDS_IMPLEMENTS_MIXED_UP;
import static org.eclipse.n4js.validation.IssueCodes.SYN_KW_EXTENDS_IMPLEMENTS_WRONG_ORDER;
import static org.eclipse.n4js.validation.IssueCodes.SYN_MODIFIER_ACCESS_SEVERAL;
import static org.eclipse.n4js.validation.IssueCodes.SYN_MODIFIER_BAD_ORDER;
import static org.eclipse.n4js.validation.IssueCodes.SYN_MODIFIER_DUPLICATE;
import static org.eclipse.n4js.validation.IssueCodes.SYN_MODIFIER_INVALID;
import static org.eclipse.n4js.validation.IssueCodes.SYN_UNNECESSARY_ELEMENT;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.CatchVariable;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.ModifierUtils;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.nodemodel.BidiTreeIterator;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.common.base.Joiner;

/**
 * Validates syntax of N4JS not already checked by the parser. The parser is designed to accept some invalid constructs
 * in order to be able to create more user-friendly error messages here.
 */
public class N4JSSyntaxValidator extends AbstractN4JSDeclarativeValidator {

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

	/**
	 * Checks modifiers.
	 */
	@Check
	private boolean checkModifiers(ModifiableElement elem) {
		return holdsNoInvalidObsoleteOrDuplicateModifiers(elem)
				&& holdsNotMoreThanOneAccessModifier(elem)
				&& holdsCorrectOrder(elem);
	}

	/**
	 * Check for invalid modifiers (e.g. ABSTRACT on a field) and duplicates.
	 */
	private boolean holdsNoInvalidObsoleteOrDuplicateModifiers(ModifiableElement elem) {
		boolean hasIssue = false;
		final Set<N4Modifier> checked = new HashSet<>();
		for (int idx = 0; idx < elem.getDeclaredModifiers().size(); idx++) {
			final N4Modifier mod = elem.getDeclaredModifiers().get(idx);
			final boolean duplicate = !checked.add(mod);
			if (!ModifierUtils.isValid(elem, mod)) {
				final ILeafNode node = ModifierUtils.getNodeForModifier(elem, idx);
				addIssue(elem, node.getOffset(), node.getLength(),
						SYN_MODIFIER_INVALID.toIssueItem(mod.getName(), keywordProvider.keyword(elem)));
				hasIssue = true;
			} else if (duplicate) {
				final ILeafNode node = ModifierUtils.getNodeForModifier(elem, idx);
				addIssue(elem, node.getOffset(), node.getLength(),
						SYN_MODIFIER_DUPLICATE.toIssueItem(mod.getName()));
				hasIssue = true;
			} else if (ModifierUtils.isObsolete(elem, mod)) {
				final ILeafNode node = ModifierUtils.getNodeForModifier(elem, idx);
				addIssue(elem, node.getOffset(), node.getLength(),
						SYN_UNNECESSARY_ELEMENT.toIssueItem("modifier", mod.getName()));
				hasIssue = true;
			}
		}
		return !hasIssue;
	}

	/**
	 * Check that not more than one access modifier is given. Access modifiers are those for which
	 * {@link ModifierUtils#isAccessModifier(N4Modifier)} returns <code>true</code>.
	 */
	private boolean holdsNotMoreThanOneAccessModifier(ModifiableElement elem) {
		boolean hasIssue = false;
		boolean hasAccessModifier = false;
		for (int idx = 0; idx < elem.getDeclaredModifiers().size(); idx++) {
			final N4Modifier mod = elem.getDeclaredModifiers().get(idx);
			final boolean isAccessModifier = ModifierUtils.isAccessModifier(mod);
			if (hasAccessModifier && isAccessModifier) {
				final ILeafNode node = ModifierUtils.getNodeForModifier(elem, idx);
				addIssue(elem, node.getOffset(), node.getLength(),
						SYN_MODIFIER_ACCESS_SEVERAL.toIssueItem());
				hasIssue = true;
			}
			hasAccessModifier |= isAccessModifier;
		}
		return !hasIssue;
	}

	/**
	 * Check for correct ordering of access modifiers.
	 */
	private boolean holdsCorrectOrder(ModifiableElement elem) {
		boolean isOrderMessedUp = false;
		int lastValue = -1;
		for (N4Modifier mod : elem.getDeclaredModifiers()) {
			final int currValue = mod.getValue();
			if (currValue < lastValue) {
				isOrderMessedUp = true;
				break;
			}
			lastValue = currValue;
		}
		if (isOrderMessedUp) {
			final List<N4Modifier> modifiers = ModifierUtils.getSortedModifiers(elem.getDeclaredModifiers());
			final String modifiersStr = Joiner.on(' ').join(modifiers.iterator());
			final ILeafNode nodeFirst = ModifierUtils.getNodeForModifier(elem, 0);
			final ILeafNode nodeLast = ModifierUtils.getNodeForModifier(elem,
					elem.getDeclaredModifiers().size() - 1);
			addIssue(elem, nodeFirst.getOffset(),
					nodeLast.getOffset() - nodeFirst.getOffset() + nodeLast.getLength(),
					SYN_MODIFIER_BAD_ORDER.toIssueItem(modifiersStr));
			return false;
		}
		return true;
	}

	/**
	 * Checks whether that exported elements are on script / top level only
	 */
	@Check
	public void checkExportedKeyword(ExportDeclaration element) {
		if (element.getExportedElement() != null && element.eContainer() instanceof N4NamespaceDeclaration) {
			ICompositeNode node = NodeModelUtils.findActualNodeFor(element);
			ILeafNode keywordNode = findLeafWithKeyword(element, "{", node, EXPORT_KEYWORD, false);
			if (keywordNode != null) {
				addIssue(element, keywordNode.getTotalOffset(), keywordNode.getLength(),
						EXP_ONLY_TOP_LEVEL_ELEMENTS.toIssueItem());
			}
		}
	}

	/**
	 * Checks that no "with" is used and that list of implemented interfaces is separated with commas and not with
	 * keywords. These checks (with some warnings created instead of errors) should help the transition from roles to
	 * interfaces. However, they may be useful later on as well, e.g., if an interface is manually refactored into a
	 * class or vice versa.
	 * <p>
	 * Note that "with" is used in Dart for roles, so maybe it is useful to have a user-friendly message instead of a
	 * parser error.
	 */
	@Check
	public void checkClassDefinition(N4ClassDefinition n4ClassDefinition) {
		holdsCorrectOrderOfExtendsImplements(n4ClassDefinition);

		ICompositeNode node = NodeModelUtils.findActualNodeFor(n4ClassDefinition);
		ILeafNode keywordNode = findSecondLeafWithKeyword(n4ClassDefinition, "{", node, EXTENDS_KEYWORD, false);
		if (keywordNode != null) {
			TClass tclass = n4ClassDefinition.getDefinedTypeAsClass();
			if (tclass == null) {
				return; // avoid consequential errors
			}
			if (StreamSupport.stream(tclass.getImplementedInterfaceRefs().spliterator(), false).allMatch(
					superTypeRef -> superTypeRef.getDeclaredType() instanceof TInterface)) {
				List<? extends IdentifiableElement> interfaces = StreamSupport.stream(
						tclass.getImplementedInterfaceRefs().spliterator(), false)
						.map(ref -> (TInterface) (ref.getDeclaredType())).collect(Collectors.toList());
				IssueItem issueItem = SYN_KW_EXTENDS_IMPLEMENTS_MIXED_UP.toIssueItem(
						validatorMessageHelper.description(tclass), "extend",
						"interface" + (interfaces.size() > 1 ? "s " : " ") + validatorMessageHelper.names(interfaces),
						IMPLEMENTS_KEYWORD);
				addIssue(n4ClassDefinition, keywordNode.getTotalOffset(),
						keywordNode.getLength(), issueItem);
			}

		}
	}

	/**
	 * Checks that no "with" or "role" is used and that list of implemented interfaces is separated with commas and not
	 * with keywords. These checks (with some warnings created instead of errors) should help the transition from roles
	 * to interfaces. However, they may be useful later on as well, e.g., if an interface is manually refactored into a
	 * class or vice versa.
	 * <p>
	 * Note that "with" is used in Dart for roles, so maybe it is useful to have a user-friendly message instead of a
	 * parser error.
	 * <p>
	 * "role" will be removed in grammar.
	 */
	@Check
	public void checkInterfaceDeclaration(N4InterfaceDeclaration n4InterfaceDecl) {
		ICompositeNode node = NodeModelUtils.findActualNodeFor(n4InterfaceDecl);
		ILeafNode keywordNode;

		keywordNode = findLeafWithKeyword(n4InterfaceDecl, "{", node, IMPLEMENTS_KEYWORD, false);
		if (keywordNode != null) {
			TInterface tinterface = n4InterfaceDecl.getDefinedTypeAsInterface();
			if (tinterface == null) {
				return; // avoid consequential errors
			}
			if (tinterface.getSuperInterfaceRefs().isEmpty()) {
				return; // ok
			}
			if (tinterface.getSuperInterfaceRefs().stream().allMatch(
					superTypeRef -> superTypeRef.getDeclaredType() instanceof TInterface)) {
				List<? extends IdentifiableElement> interfaces = tinterface.getSuperInterfaceRefs()
						.stream()
						.flatMap((ParameterizedTypeRef ref) -> {
							Type declaredType = ref.getDeclaredType();
							if (declaredType instanceof TInterface) {
								return Stream.of((TInterface) declaredType);
							}
							return Stream.empty();
						})
						.collect(Collectors.toList());
				IssueItem issueItem = SYN_KW_EXTENDS_IMPLEMENTS_MIXED_UP.toIssueItem(
						validatorMessageHelper.description(tinterface), "implement",
						"interface" + (interfaces.size() > 1 ? "s " : " ") + validatorMessageHelper.names(interfaces),
						EXTENDS_KEYWORD);
				addIssue(n4InterfaceDecl, keywordNode.getTotalOffset(),
						keywordNode.getLength(), issueItem);
			}

		}

	}

	private boolean holdsCorrectOrderOfExtendsImplements(N4ClassDefinition semanticElement) {
		if (semanticElement.getSuperClassRef() == null || semanticElement.getImplementedInterfaceRefs().isEmpty()) {
			return true;
		}
		ICompositeNode node = NodeModelUtils.findActualNodeFor(semanticElement);
		if (node == null) {
			return true;
		}
		ILeafNode extendsNode = findLeafWithKeyword(semanticElement, "{", node, EXTENDS_KEYWORD, false);
		ILeafNode implementsNode = findLeafWithKeyword(semanticElement, "{", node, IMPLEMENTS_KEYWORD, false);
		if (extendsNode == null || implementsNode == null) {
			return true;
		}
		int extendsOffset = extendsNode.getOffset();
		int implementsOffset = implementsNode.getOffset();
		if (extendsOffset > implementsOffset) {
			addIssue(semanticElement, extendsOffset, EXTENDS_KEYWORD.length(),
					SYN_KW_EXTENDS_IMPLEMENTS_WRONG_ORDER.toIssueItem());
			return false;
		}
		return true;
	}

	private ILeafNode findLeafWithKeyword(EObject semanticElement, String stopAtKeyword, ICompositeNode node,
			String keyWord,
			boolean commaAlternative) {
		return doFindLeafWithKeyword(semanticElement, stopAtKeyword, node, keyWord, commaAlternative, 1);
	}

	private ILeafNode findSecondLeafWithKeyword(EObject semanticElement, String stopAtKeyword, ICompositeNode node,
			String keyWord,
			boolean commaAlternative) {
		return doFindLeafWithKeyword(semanticElement, stopAtKeyword, node, keyWord, commaAlternative, 2);

	}

	/**
	 * Returns the first keyword with the given value, or null if no such keyword is found.
	 */
	private ILeafNode doFindLeafWithKeyword(EObject semanticElement, String stopAtKeyword, ICompositeNode node,
			String keyWord,
			boolean commaAlternative, int hitNumber) {
		EObject grammarElement;
		int foundHits = 0;

		if (node == null) {
			return null;
		}

		for (BidiTreeIterator<INode> iter = node.getAsTreeIterable().iterator(); iter.hasNext();) {
			INode child = iter.next();
			EObject childSemElement = child.getSemanticElement();
			if (child != node && childSemElement != null && childSemElement != semanticElement) {
				iter.prune();
			} else if (child instanceof ILeafNode) {
				ILeafNode leaf = (ILeafNode) child;
				grammarElement = leaf.getGrammarElement();
				if (grammarElement instanceof Keyword) {
					String value = ((Keyword) grammarElement).getValue();
					if (stopAtKeyword.equals(value)) {
						return null;
					}
					if (keyWord.equals(value)) {
						if (grammarElement.eContainer() instanceof Alternatives) {
							AbstractElement first = ((Alternatives) (grammarElement.eContainer())).getElements().get(0);
							boolean inCommaAlternative = (first instanceof Keyword && ",".equals(((Keyword) first)
									.getValue()));
							if (inCommaAlternative == commaAlternative) {
								foundHits++;
								if (foundHits >= hitNumber) {
									return leaf;
								}
							}
						} else {
							if (!commaAlternative) {
								foundHits++;
								if (foundHits >= hitNumber) {
									return leaf;
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Ensures that a catch variable has not type annotation. This is supported by the parser to enable better error
	 * messages.
	 *
	 * @see "Spec, 9.1.8"
	 * @see <a href="https://github.com/NumberFour/n4js/issues/179">GHOLD-179</a>
	 */
	@Check
	public void checkCatchVariable(CatchVariable catchVariable) {
		if (catchVariable.getDeclaredTypeRefInAST() != null) {
			addIssue(catchVariable, N4JSPackage.Literals.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE,
					AST_CATCH_VAR_TYPED.toIssueItem());
		}
	}

}
