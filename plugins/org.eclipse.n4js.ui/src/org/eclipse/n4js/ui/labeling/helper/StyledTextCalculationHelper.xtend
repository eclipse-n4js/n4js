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
package org.eclipse.n4js.ui.labeling.helper

import com.google.inject.Inject
import org.eclipse.jface.viewers.StyledString
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ui.labeling.EObjectWithContext
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider
import org.eclipse.n4js.ui.labeling.N4JSStylers
import org.eclipse.xtext.ui.label.AbstractLabelProvider

/**
 * This helper class serves as replacement for the polymorphic dispatch done
 * by {@link AbstractLabelProvider} in favor of
 * Xtend dispatch methods. Here the dispatch of styled labels is done.
 * It is called in {@link N4JSLabelProvider#getStyledText}. getStyledText
 * calls {@link N4JSLabelProvider#doGetText} so in most cases its good
 * practice to call here getLabelProvider.getSuperStyledText to keep this
 * behavior. By this you can reuse the label calculated by
 * {@link LabelCalculationHelper} so far. Here you can then append the styled
 * parts of the label (so its still possible to append non styled strings as
 * well).
 * <br /><br />
 * General use case for styled string is the highlighting of inferred types
 * in the outline (to see the difference to declared types). So styled texts
 * are created for fields, methods, getters, setters and exported variables
 * as only those can have types.
 */
class StyledTextCalculationHelper {

	/** Threshold length at which a type reference description is compressed*/
	private static val OUTLINE_TYPE_REF_LENGTH_THRESHOLD = 30;

	private N4JSLabelProvider labelProvider;

	@Inject
	private LabelCalculationHelper labelCalculationHelper

	def setLabelProvider(N4JSLabelProvider provider) {
		this.labelProvider = provider;
	}

	def getLabelProvider() {
		return this.labelProvider
	}

	/**
	 * Should not happen as this will lead to consequential errors, still added here to make helper more robust.
	 */
	def dispatch StyledString dispatchGetStyledText(Void _null) {
		return new StyledString("*error*")
	}

	/**
	 * fallback
	 */
	def dispatch StyledString dispatchGetStyledText(Object element) {
		getLabelProvider.getSuperStyledText(element)
	}

	/**
	 * Adds origin to inherited, consumed, or polyfilled members.
	 * 
	 * @see https://github.com/eclipse/n4js/issues/99
	 */
	def dispatch StyledString dispatchGetStyledText(EObjectWithContext objectWithContext) {
		var StyledString styledText = dispatchGetStyledText(objectWithContext.obj);
		if (styledText === null) return unknown;

		val TMember member = if (objectWithContext.obj instanceof N4MemberDeclaration) {
				(objectWithContext.obj as N4MemberDeclaration).definedTypeElement
			} else if (objectWithContext.obj instanceof TMember) {
				objectWithContext.obj as TMember
			} else
				null;

		if (member !== null && member.containingType !== null && member.containingType != objectWithContext.context) {

			val orgDest = " from " + labelCalculationHelper.dispatchDoGetText(member.containingType);

			if (member.polyfilled) {
				styledText.append(" - polyfilled" + orgDest, N4JSStylers.POLYFILLED_MEMBERS_STYLER);
			} else if (member.containingType instanceof TInterface && objectWithContext.context instanceof TClass) {
				styledText.append(" - consumed" + orgDest, N4JSStylers.CONSUMED_MEMBERS_STYLER);
			} else {
				styledText.append(" - inherited" + orgDest, N4JSStylers.INHERITED_MEMBERS_STYLER);
			}

		}
		return styledText;
	}

	/**
	 * produces e.g. functionName(param1TypeName, param2TypeName): returnTypeName
	 */
	def dispatch StyledString dispatchGetStyledText(FunctionDefinition functionDefinition) {
		val definedType = functionDefinition.definedType;
		if (definedType instanceof TFunction) {
			return dispatchGetStyledText(definedType);
		}
		getLabelProvider.getSuperStyledText(functionDefinition);
	}

	/**
	 * Does not access AST. 
	 */
	def dispatch StyledString dispatchGetStyledText(TFunction tfunction) {
		var styledText = getLabelProvider.getSuperStyledText(tfunction);
		if (styledText === null) return unknown;
		if (tfunction.constructor) {
			styledText.setStyle(0, styledText.length, N4JSStylers.CONSTRUCTOR_STYLER)
		}
		styledText = styledText.appendStyledTextForFormalParameters(tfunction);
		if (!tfunction.typeVars.isEmpty()) {
			styledText.append(tfunction.typeVars.join("<", ", ", ">", [name]))
		}

		if (!tfunction.constructor && tfunction.returnTypeRef !== null) {
			styledText.append(": ");
			styledText.append(getTypeRefDescription(tfunction.returnTypeRef))
		}

		return styledText;
	}

	/**
	 * produces e.g. getterName(): returnTypeName
	 */
	def dispatch StyledString dispatchGetStyledText(N4GetterDeclaration n4GetterDeclaration) {
		if (n4GetterDeclaration.definedGetter !== null)
			return dispatchGetStyledText(n4GetterDeclaration.definedGetter);

		return getLabelProvider().getSuperStyledText(n4GetterDeclaration);

	}

	/**
	 * Returns the styled text for the given getter, does not access the AST.
	 */
	def dispatch StyledString dispatchGetStyledText(TGetter tgetter) {
		var styledText = getLabelProvider.getSuperStyledText(tgetter);
		if (styledText === null) return unknown;
		styledText.setStyle(0, styledText.length, N4JSStylers.FIELD_OR_VAR_STYLER)
		
		if (tgetter.isOptional) {
			styledText.append("?");
		}

		if (tgetter.typeRef !== null) {
			styledText.append(": ");
			styledText.append(getTypeRefDescription(tgetter.typeRef));
		}

		return styledText;
	}

	/** 
	 * produces e.g. setterName: paramTypeName 
	 */
	def dispatch StyledString dispatchGetStyledText(N4SetterDeclaration n4SetterDeclaration) {
		if (n4SetterDeclaration.definedSetter !== null)
			return dispatchGetStyledText(n4SetterDeclaration.definedSetter);

		return getLabelProvider.getSuperStyledText(n4SetterDeclaration);
	}

	/**
	 * Returns the styled text for the given setter, does not access AST.
	 */
	def dispatch StyledString dispatchGetStyledText(TSetter tsetter) {
		var styledText = getLabelProvider.getSuperStyledText(tsetter)
		if (styledText === null) return unknown;
		styledText.setStyle(0, styledText.length, N4JSStylers.FIELD_OR_VAR_STYLER)
		
		if (tsetter.isOptional) {
			styledText.append("?");
		}
				
		if (tsetter.fpar !== null) {
			styledText.append(": ").append(getStyledTextForFormalParameter(tsetter.fpar))
		}

	}

	/**
	 * produces e.g. fieldName: fieldTypeName
	 */
	def dispatch StyledString dispatchGetStyledText(N4FieldDeclaration n4FieldDeclaration) {
		if (n4FieldDeclaration.definedField !== null)
			return dispatchGetStyledText(n4FieldDeclaration.definedField);

		return getLabelProvider.getSuperStyledText(n4FieldDeclaration);
	}

	/**
	 * Returns the styled text for the given field, does not accesses the AST.
	 */
	def dispatch StyledString dispatchGetStyledText(TField tfield) {
		var styledText = getLabelProvider.getSuperStyledText(tfield);
		if (styledText === null) return unknown;
		styledText.setStyle(0, styledText.length, N4JSStylers.FIELD_OR_VAR_STYLER)
		if (tfield.isOptional) {
			styledText.append("?");
		}
		if (tfield.typeRef !== null) {
			styledText.append(": ");
			styledText.append(getTypeRefDescription(tfield.typeRef));
		}
		return styledText;
	}

	/**
	 * produces e.g. variableName: variableTypeName
	 */
	def dispatch StyledString dispatchGetStyledText(ExportedVariableDeclaration variableDeclaration) {
		var styledText = getLabelProvider.getSuperStyledText(variableDeclaration)
		if (styledText === null) return unknown;

		styledText.setStyle(0, styledText.length, N4JSStylers.FIELD_OR_VAR_STYLER);
		val definedVariable = variableDeclaration.definedVariable

		if (definedVariable?.typeRef !== null) {
			styledText.append(": ");
			styledText.append(getTypeRefDescription(definedVariable.typeRef));
		}

		return styledText;
	}

	/**
	 * produces e.g. (param1TypeName, param2TypeName)
	 */
	def private StyledString appendStyledTextForFormalParameters(StyledString styledText, TFunction tFunction) {
		styledText.append("(");
		if (! tFunction.fpars.empty) {
			styledText.append(tFunction.fpars.map[getStyledTextForFormalParameter].reduce [ l, r |
				if (l !== null)
					l.append(", ").append(r)
				else if (r !== null) r else new StyledString();
			]);
		}
		styledText.append(")");
	}

	def private StyledString getStyledTextForFormalParameter(TFormalParameter tFormalParameter) {
		var styledText = getTypeRefDescription(tFormalParameter.typeRef);
		if (tFormalParameter.isVariadic) {
			styledText = new StyledString("…").append(styledText);
		}
		if (tFormalParameter.hasInitializerAssignment) {
			styledText.append("=");
		}
		return styledText;
	}

	/**
	 * Returns a type reference description which is compressed according to the {@link StyledTextCalculationHelper#OUTLINE_TYPE_REF_LENGTH_THRESHOLD} constant.
	 */
	def private StyledString getTypeRefDescription(TypeRef ref) {
		val typeRefDescription = new StyledString(getCompressedTypeRefDescription(ref, 0), N4JSStylers.TYPEREF_STYLER);

		return typeRefDescription;
	}

	/**
	 * Appends the description of the given type reference to the styled string. If the {@link StyledTextCalculationHelper#OUTLINE_TYPE_REF_LENGTH_THRESHOLD} is exceeded
	 * further nested type information is omitted and replaced by three dots (...).
	 */
	def private String getCompressedTypeRefDescription(TypeRef typeRef, int currentLength) {
		if (currentLength > OUTLINE_TYPE_REF_LENGTH_THRESHOLD) {
			return "...";
		} else if (typeRef !== null) {
			return getTypeRefDescriptionString(typeRef);
		} else {
			return "<unknown>";
		}
	}

	/**
	 * appends the simple type name to the styled string
	 */
	def dispatch private String getTypeRefDescriptionString(TypeArgument ref) {
		val name = ref.getTypeRefAsString();
		if (name === null) {
			return "<unknown>"
		} else {
			return name;
		}
	}

	/**
	 * appends the simple type name to the styled string
	 */
	def dispatch private String getTypeRefDescriptionString(TypeRef ref) {
		val name = ref.declaredType?.name;
		if (name === null) {
			return "<unknown>"
		} else {
			return name;
		}
	}

	/**
	 * appends the simple type name to the styled string
	 */
	def dispatch private String getTypeRefDescriptionString(ParameterizedTypeRef ref) {
		var name = ref.declaredType?.name;
		if (name === null) {
			return "<unknown>"
		} else {
			if (ref.typingStrategy !== null && ref.typingStrategy != TypingStrategy.DEFAULT) {
				name = ref.typingStrategy + name;
			}

			if (name.equals("Array") && ref.declaredType.isArrayLike) {
				name = "[" + ref.typeArgs.join(",", [arg|getTypeRefDescriptionString(arg)]) + "]";
			} else {
				name += ref.typeArgs.join("<", ",", ">", [arg|getTypeRefDescriptionString(arg)]);
			}
			return name;
		}
	}

	/**
	 * produces 'this' for ThisType references
	 */
	def dispatch private String getTypeRefDescriptionString(ThisTypeRef ref) {
		if (ref.typingStrategy !== null && ref.typingStrategy != TypingStrategy.DEFAULT) {
			return ref.typingStrategy + "this";
		}
		return "this";
	}

	/**
	 * produces (param1, param2,...)=>returnType
	 */
	def dispatch private String getTypeRefDescriptionString(FunctionTypeExpression ref) {
		val result = new StringBuilder();
		result.append("(");

		// build parameter and return type in two different styled strings to reset the compression threshold for each of them
		val parameterString = new StyledString();
		appendCommaSeparatedTypeRefList(ref.fpars.map[it.typeRef], parameterString, true, ", ");
		result.append(parameterString).toString();

		result.append(")=>");
		if (ref.returnTypeRef !== null) {
			result.append(getCompressedTypeRefDescription(ref.returnTypeRef, 0));
		} else {
			result.append("void");
		}
		return result.toString;
	}

	/**
	 * produces type{typeName} or constructor{typeName}
	 */
	def dispatch private String getTypeRefDescriptionString(TypeTypeRef ref) {
		val typeName = switch ref.typeArg {
			ThisTypeRef:
				"this"
			default:
				ref.nominalTypeNameOrWildCard
		}
		if (ref.isConstructorRef) {
			return '''constructor{«typeName»}''';
		} else {
			return '''type{«typeName»}''';
		}
	}

	/**
	 * produces type1 | type2 or type1 & type2, adding parenthesis if needed
	 */
	def dispatch private String getTypeRefDescriptionString(ComposedTypeRef ref) {
		val result = new StyledString();
		val cf = ref.eContainingFeature; 
		val needsParens = ref.eContainer instanceof ComposedTypeRef ||
			((cf == TypesPackage.Literals.TFUNCTION__RETURN_TYPE_REF
				||cf == TypeRefsPackage.Literals.FUNCTION_TYPE_EXPR_OR_REF___GET_RETURN_TYPE_REF
				||cf == TypeRefsPackage.Literals.FUNCTION_TYPE_EXPRESSION__RETURN_TYPE_REF)
				 &&
				ref.eContainer.eContainer instanceof ComposedTypeRef
			);

		if (needsParens) {
			result.append("(");
		}

		val separator = if (ref instanceof UnionTypeExpression) {
				" | "
			} else {
				" & "
			}
		appendCommaSeparatedTypeRefList(ref.typeRefs, result, true, separator);

		if (needsParens) {
			result.append(")");
		}


		return result.toString();
	}

	/**
	 * produces a comma separated TypeRef description list element by element
	 */
	def private void appendCommaSeparatedTypeRefList(Iterable<TypeRef> refs, StyledString styledString, boolean first, String separator) {
		if (!refs.iterator.hasNext) {
			return;
		}
		if (OUTLINE_TYPE_REF_LENGTH_THRESHOLD > styledString.length) {
			if (!first) {
				styledString.append(separator);
			}
			styledString.append(getCompressedTypeRefDescription(refs.iterator.next, styledString.length));
			appendCommaSeparatedTypeRefList(refs.drop(1), styledString, false, separator);
		} else if (!first) {
			styledString.append(separator + "…");
		} else {
			styledString.append("…");
		}
	}

	def private String getWildcardDescription(Wildcard wildcard) {
		val string = new StyledString();
		string.append("?");
		if (wildcard.declaredLowerBound !== null) {
			string.append(" super ");
			string.append(getCompressedTypeRefDescription(wildcard.declaredLowerBound, string.length));
		} else {
			string.append(" extends ");
			string.append(getCompressedTypeRefDescription(wildcard.declaredUpperBound, string.length));
		}
		return string.string;
	}

	def private String nominalTypeNameOrWildCard(TypeTypeRef ref) {
		switch (ref.typeArg) {
			TypeRef: (ref.typeArg as TypeRef).declaredType?.name
			Wildcard: getWildcardDescription(ref.typeArg as Wildcard)
			default: ""
		}
	}

	def private StyledString unknown() {
		return new StyledString("<unknown>");
	}
}
