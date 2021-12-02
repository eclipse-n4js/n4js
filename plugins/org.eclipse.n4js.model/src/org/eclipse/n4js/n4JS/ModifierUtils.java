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
package org.eclipse.n4js.n4JS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.n4js.ts.types.AccessibleTypeElement;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

/**
 * Utility methods for {@link N4Modifier N4Modifiers}.
 */
public class ModifierUtils {

	// would be nice to use AnnotationDefinition.INTERNAL here, but not accessible from this bundle :(
	private static final String ANN_INTERNAL = "Internal";

	/**
	 * Checks if the given modifier is a legal modifier for AST nodes of type 'astNodeType'.
	 */
	public static final boolean isValid(ModifiableElement elem, N4Modifier modifier) {
		EClass astNodeType = elem.eClass();
		switch (modifier) {
		case PUBLIC:
		case PROJECT:
			return isN4TypeDeclaration(astNodeType)
					|| isNamespaceDeclaration(astNodeType)
					|| isN4MemberDeclaration(astNodeType)
					|| isFunctionDeclaration(astNodeType)
					|| isExportedVariableStatement(astNodeType);
		case PRIVATE:
			if (isN4MemberDeclaration(astNodeType)) {
				return true;
			}
			if (elem.eContainer() instanceof N4NamespaceDeclaration &&
					(isN4TypeDeclaration(astNodeType)
							|| isNamespaceDeclaration(astNodeType)
							|| isFunctionDeclaration(astNodeType)
							|| isExportedVariableStatement(astNodeType))) {

				return true;
			}
			return false;
		case PROTECTED:
			return isN4MemberDeclaration(astNodeType);
		case EXTERNAL:
			return isN4TypeDeclaration(astNodeType)
					|| isNamespaceDeclaration(astNodeType)
					|| isFunctionDeclaration(astNodeType)
					|| isExportedVariableStatement(astNodeType);
		case ABSTRACT:
			return isN4ClassDeclaration(astNodeType)
					|| isN4MethodDeclaration(astNodeType)
					|| isN4FieldAccessor(astNodeType);
		case STATIC:
			return isN4MemberDeclaration(astNodeType);
		case CONST:
			// note: 'const' is allowed for variables as well, but in that case it
			// is not a modifier but a VariableStatementKeyword (see n4js.xtext)
			return isN4FieldDeclaration(astNodeType);
		default:
			return false;
		}
	}

	/** Returns true iff the given modifier is obsolete due by being equal to the default modifier. */
	public static final boolean isObsolete(ModifiableElement elem, N4Modifier modifier) {
		switch (modifier) {
		case PUBLIC:
		case PROJECT:
		case PRIVATE:
		case PROTECTED:
			EClass astNodeType = elem.eClass();
			// First check for member since TMethods are TypeDefiningElements too
			if (isN4MemberDeclaration(astNodeType)) {
				N4MemberDeclaration typeDefElem = (N4MemberDeclaration) elem;
				TMember tMember = typeDefElem.getDefinedTypeElement();
				if (isTMemberWithAccessModifier(tMember.eClass())) {
					return false;

					// TODO: activate later
					// TMemberWithAccessModifier tmwam = (TMemberWithAccessModifier) tMember;
					// return tmwam.getDeclaredMemberAccessModifier() == tmwam.getDefaultMemberAccessModifier();
				}
			}
			if (isTypeDefiningElement(astNodeType)
					// TODO: remove later the following condition
					&& astNodeType instanceof N4NamespaceDeclaration) {
				TypeDefiningElement typeDefElem = (TypeDefiningElement) elem;
				Type definedType = typeDefElem.getDefinedType();
				if (isAccessibleTypeElement(definedType.eClass())) {
					AccessibleTypeElement ate = (AccessibleTypeElement) definedType;
					return ate.getDeclaredTypeAccessModifier() == ate.getDefaultTypeAccessModifier();
				}
			}
			return false;
		case EXTERNAL:
			return elem.isDeclaredExternal() && elem.isDefaultExternal();
		case ABSTRACT:
		case STATIC:
		case CONST:
		default:
			return false;
		}
	}

	/**
	 * Returns true iff the given modifier is an <em>access</em> modifier, i.e. PUBLIC, PRIVATE, etc. as opposed to
	 * ABSTRACT, STATIC, etc.
	 */
	public static final boolean isAccessModifier(N4Modifier modifier) {
		return modifier == N4Modifier.PRIVATE
				|| modifier == N4Modifier.PROJECT
				|| modifier == N4Modifier.PROTECTED
				|| modifier == N4Modifier.PUBLIC;
	}

	/**
	 * Converts an {@link N4Modifier} from the AST to a {@link TypeAccessModifier} in the types model.
	 */
	public static final TypeAccessModifier convertToTypeAccessModifier(Collection<? extends N4Modifier> modifiers,
			List<Annotation> annotations) {
		if (modifiers.contains(N4Modifier.PRIVATE)) {
			return TypeAccessModifier.PRIVATE;
		} else if (modifiers.contains(N4Modifier.PROJECT)) {
			return TypeAccessModifier.PROJECT;
		} else if (modifiers.contains(N4Modifier.PUBLIC)) {
			return annotations.stream().anyMatch(a -> ANN_INTERNAL.equals(a.getName()))
					? TypeAccessModifier.PUBLIC_INTERNAL
					: TypeAccessModifier.PUBLIC;
		} else {
			return TypeAccessModifier.UNDEFINED;
		}
	}

	/**
	 * Converts a {@link N4Modifier} from the AST to a {@link MemberAccessModifier} in the types model.
	 */
	public static final MemberAccessModifier convertToMemberAccessModifier(Collection<? extends N4Modifier> modifiers,
			List<Annotation> annotations) {
		if (modifiers.contains(N4Modifier.PRIVATE)) {
			return MemberAccessModifier.PRIVATE;
		} else if (modifiers.contains(N4Modifier.PROJECT)) {
			return MemberAccessModifier.PROJECT;
		} else if (modifiers.contains(N4Modifier.PROTECTED)) {
			return annotations.stream().anyMatch(a -> ANN_INTERNAL.equals(a.getName()))
					? MemberAccessModifier.PROTECTED_INTERNAL
					: MemberAccessModifier.PROTECTED;
		} else if (modifiers.contains(N4Modifier.PUBLIC)) {
			return annotations.stream().anyMatch(a -> ANN_INTERNAL.equals(a.getName()))
					? MemberAccessModifier.PUBLIC_INTERNAL
					: MemberAccessModifier.PUBLIC;
		} else {
			return MemberAccessModifier.UNDEFINED;
		}
	}

	/**
	 * Returns a sorted copy of the given collection of modifiers. The order is defined by each modifier's value in the
	 * N4Modifier enumeration.
	 */
	public static final List<N4Modifier> getSortedModifiers(Collection<? extends N4Modifier> modifiers) {
		final List<N4Modifier> result = new ArrayList<>(modifiers);
		Collections.sort(result, (m1, m2) -> Integer.compare(m1.getValue(), m2.getValue()));
		return result;
	}

	/**
	 * Returns the INode for a given modifier. This is intended for computing error message regions, quick fixes, etc.
	 */
	public static final ILeafNode getNodeForModifier(ModifiableElement elem, int index) {
		final List<INode> nodes = NodeModelUtils.findNodesForFeature(elem,
				N4JSPackage.eINSTANCE.getModifiableElement_DeclaredModifiers());
		if (index >= 0 && index < nodes.size()) {
			final Iterable<ILeafNode> leafNodes = nodes.get(index).getLeafNodes();
			for (ILeafNode currLeaf : leafNodes) {
				if (!currLeaf.isHidden())
					return currLeaf;
			}
		}
		return null;
	}

	// the following methods are used only to improve readability if #isValid() above

	private static final boolean isTypeDefiningElement(EClass astNodeType) {
		return N4JSPackage.eINSTANCE.getTypeDefiningElement().isSuperTypeOf(astNodeType);
	}

	private static final boolean isN4TypeDeclaration(EClass astNodeType) {
		return N4JSPackage.eINSTANCE.getN4TypeDeclaration().isSuperTypeOf(astNodeType);
	}

	private static final boolean isN4MemberDeclaration(EClass astNodeType) {
		return N4JSPackage.eINSTANCE.getN4MemberDeclaration().isSuperTypeOf(astNodeType);
	}

	private static final boolean isNamespaceDeclaration(EClass astNodeType) {
		return N4JSPackage.eINSTANCE.getN4NamespaceDeclaration().isSuperTypeOf(astNodeType);
	}

	private static final boolean isFunctionDeclaration(EClass astNodeType) {
		return N4JSPackage.eINSTANCE.getFunctionDeclaration().isSuperTypeOf(astNodeType);
	}

	private static final boolean isExportedVariableStatement(EClass astNodeType) {
		return N4JSPackage.eINSTANCE.getExportedVariableStatement().isSuperTypeOf(astNodeType);
	}

	private static final boolean isN4ClassDeclaration(EClass astNodeType) {
		return N4JSPackage.eINSTANCE.getN4ClassDeclaration().isSuperTypeOf(astNodeType);
	}

	private static final boolean isN4FieldDeclaration(EClass astNodeType) {
		return N4JSPackage.eINSTANCE.getN4FieldDeclaration().isSuperTypeOf(astNodeType);
	}

	private static final boolean isN4FieldAccessor(EClass astNodeType) {
		return N4JSPackage.eINSTANCE.getN4FieldAccessor().isSuperTypeOf(astNodeType);
	}

	private static final boolean isN4MethodDeclaration(EClass astNodeType) {
		return N4JSPackage.eINSTANCE.getN4MethodDeclaration().isSuperTypeOf(astNodeType);
	}

	private static final boolean isAccessibleTypeElement(EClass astNodeType) {
		return TypesPackage.eINSTANCE.getAccessibleTypeElement().isSuperTypeOf(astNodeType);
	}

	private static final boolean isTMemberWithAccessModifier(EClass astNodeType) {
		return TypesPackage.eINSTANCE.getTMemberWithAccessModifier().isSuperTypeOf(astNodeType);
	}
}
