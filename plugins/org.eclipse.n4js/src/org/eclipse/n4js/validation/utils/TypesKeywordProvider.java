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
package org.eclipse.n4js.validation.utils;

import java.util.Arrays;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.types.BuiltInType;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TDynamicElement;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.ts.types.TypeAlias;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.TypingStrategy;

import com.google.inject.Singleton;

/**
 * Helper returning the keyword of a given AST or type element, e.g., "class" for a class declaration.
 */
@Singleton
@SuppressWarnings({ "javadoc", "unused" })
public class TypesKeywordProvider {

	public String keywordWithIndefiniteArticle(EObject elem) {
		String keyword = keyword(elem);
		char firstChar = (keyword.length() > 0) ? Character.toLowerCase(keyword.charAt(0)) : 0;
		boolean startsWithVowel = "aeiou".indexOf(firstChar) >= 0;
		return (startsWithVowel) ? "an " + keyword : "a " + keyword;
	}

	public String keyword(final Object n4Class) {
		if (n4Class instanceof TClass) {
			return keyword((TClass) n4Class);
		} else if (n4Class instanceof TInterface) {
			return keyword((TInterface) n4Class);
		} else if (n4Class instanceof TStructGetter) {
			return keyword((TStructGetter) n4Class);
		} else if (n4Class instanceof TStructSetter) {
			return keyword((TStructSetter) n4Class);
		} else if (n4Class instanceof TClassifier) {
			return keyword((TClassifier) n4Class);
		} else if (n4Class instanceof TGetter) {
			return keyword((TGetter) n4Class);
		} else if (n4Class instanceof TMethod) {
			return keyword((TMethod) n4Class);
		} else if (n4Class instanceof TSetter) {
			return keyword((TSetter) n4Class);
		} else if (n4Class instanceof TStructField) {
			return keyword((TStructField) n4Class);
		} else if (n4Class instanceof PrimitiveType) {
			return keyword((PrimitiveType) n4Class);
		} else if (n4Class instanceof TField) {
			return keyword((TField) n4Class);
		} else if (n4Class instanceof TFunction) {
			return keyword((TFunction) n4Class);
		} else if (n4Class instanceof TypeAlias) {
			return keyword((TypeAlias) n4Class);
		} else if (n4Class instanceof BuiltInType) {
			return keyword((BuiltInType) n4Class);
		} else if (n4Class instanceof TEnum) {
			return keyword((TEnum) n4Class);
		} else if (n4Class instanceof TypeVariable) {
			return keyword((TypeVariable) n4Class);
		} else if (n4Class instanceof TDynamicElement) {
			return keyword((TDynamicElement) n4Class);
		} else if (n4Class instanceof TFormalParameter) {
			return keyword((TFormalParameter) n4Class);
		} else if (n4Class instanceof TMember) {
			return keyword((TMember) n4Class);
		} else if (n4Class instanceof TVariable) {
			return keyword((TVariable) n4Class);
		} else if (n4Class instanceof Type) {
			return keyword((Type) n4Class);
		} else if (n4Class instanceof MemberAccessModifier) {
			return keyword((MemberAccessModifier) n4Class);
		} else if (n4Class instanceof TypeAccessModifier) {
			return keyword((TypeAccessModifier) n4Class);
		} else if (n4Class instanceof TypingStrategy) {
			return keyword((TypingStrategy) n4Class);
		} else if (n4Class instanceof EObject) {
			return keyword((EObject) n4Class);
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.<Object> asList(n4Class).toString());
		}
	}

	public String keyword(EObject elem, TypingStrategy typingStrategy) {
		if (typingStrategy == TypingStrategy.STRUCTURAL_FIELD_INITIALIZER) {
			EClass replacementType = null;
			if (elem instanceof TStructGetter) {
				replacementType = TypesPackage.eINSTANCE.getTStructSetter();
			} else if (elem instanceof TStructSetter) {
				replacementType = TypesPackage.eINSTANCE.getTStructGetter();
			} else if (elem instanceof TGetter) {
				replacementType = TypesPackage.eINSTANCE.getTSetter();
			} else if (elem instanceof TSetter) {
				replacementType = TypesPackage.eINSTANCE.getTGetter();
			}
			if (replacementType != null) {
				return keyword(TypesFactory.eINSTANCE.create(replacementType));
			}
		}
		return keyword(elem);
	}

	public String keyword(EObject eobj) {
		String modelName = eobj.eClass().getName();
		StringBuilder strb = new StringBuilder(modelName.length() + 1);
		for (var i = 0; i < modelName.length(); i++) {
			char c = modelName.charAt(i);
			if (Character.isUpperCase(c)) {
				if (i > 0) {
					strb.append(' ');
				}
				strb.append(Character.toLowerCase(c));
			} else {
				strb.append(c);
			}
		}
		return strb.toString();
	}

	public String keyword(TClass n4Class) {
		return "class";
	}

	public String keyword(TEnum tEnum) {
		return "enum";
	}

	public String keyword(TInterface n4Interface) {
		return "interface";
	}

	public String keyword(TClassifier n4Classifier) {
		return "classifier";
	}

	public String keyword(TMember n4Member) {
		return "member";
	}

	public String keyword(TStructField structField) {
		return "structural field";
	}

	public String keyword(TStructGetter structGetter) {
		return "structural getter";
	}

	public String keyword(TStructSetter structSetter) {
		return "structural setter";
	}

	public String keyword(TField n4Field) {
		return "field";
	}

	public String keyword(TMethod tMethod) {
		if (tMethod.isConstructor()) {
			return "constructor";
		} else if (tMethod.isCallSignature()) {
			return "call signature";
		} else if (tMethod.isConstructSignature()) {
			return "construct signature";
		} else {
			return "method";
		}
	}

	public String keyword(TFunction function) {
		return "function";
	}

	public String keyword(TGetter n4Getter) {
		return "getter";
	}

	public String keyword(TSetter n4Setter) {
		return "setter";
	}

	public String keyword(TVariable variable) {
		return "variable";
	}

	public String keyword(TFormalParameter parameter) {
		return "parameter";
	}

	public String keyword(TypeVariable variable) {
		return "type variable";
	}

	public String keyword(PrimitiveType primitive) {
		return "primitive";
	}

	public String keyword(BuiltInType primitive) {
		return "built-in type";
	}

	public String keyword(TDynamicElement dynamicElement) {
		return "element";
	}

	public String keyword(TypeAlias typeAlias) {
		return "type alias";
	}

	// note: following method will also be required in case of unresolved proxies
	public String keyword(Type other) {
		return "type";
	}

	public String keyword(TypeAccessModifier accessModifier) {
		switch (accessModifier) {
		case PUBLIC_INTERNAL:
			return "@Internal public";
		default:
			return accessModifier.getName();
		}
	}

	public String keyword(MemberAccessModifier accessModifier) {
		switch (accessModifier) {
		case PUBLIC_INTERNAL:
			return "@Internal public";
		case PROTECTED_INTERNAL:
			return "@Internal protected";
		default:
			return accessModifier.getName();
		}
	}

	public String keyword(TypingStrategy strategy) {
		switch (strategy) {
		case DEFAULT:
			return "default";
		case NOMINAL:
			return "nominal";
		case STRUCTURAL:
			return "structural";
		case STRUCTURAL_FIELDS:
			return "structural fields";
		case STRUCTURAL_READ_ONLY_FIELDS:
			return "structural read-only fields";
		case STRUCTURAL_WRITE_ONLY_FIELDS:
			return "structural write-only fields";
		case STRUCTURAL_FIELD_INITIALIZER:
			return "structural field initializer";
		case EMPTY:
			return "structural empty";
		default:
			throw new IllegalStateException("unsupported typing strategy: " + strategy);
		}
	}
}
