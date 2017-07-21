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
import java.util.List
import org.eclipse.jface.viewers.StyledString
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ui.labeling.EObjectWithContext
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider
import org.eclipse.n4js.ui.labeling.N4JSStylers
import org.eclipse.xtext.ui.label.AbstractLabelProvider
import org.eclipse.n4js.ts.types.TClass

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

	/** A styler which is used to emphasize inferred types in the outline */
	private static val INFERRED_TYPE_STYLER = StyledString.DECORATIONS_STYLER;

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

	// fallback
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

		if (objectWithContext.obj instanceof N4MemberDeclaration) {
			val TMember member = (objectWithContext.obj as N4MemberDeclaration).definedTypeElement;
			if (member !== null && member.containingType !== null &&
				member.containingType != objectWithContext.context) {
					
				val orgDest = " from " + labelCalculationHelper.dispatchDoGetText(member.containingType);	
					
				if (member.polyfilled) {
					styledText.append(" polyfilled " + orgDest, N4JSStylers.POLYFILLED_MEMBERS_STYLER);
				} else if (member.containingType instanceof TInterface
					&& objectWithContext.context instanceof TClass) {
						styledText.append(" consumed " + orgDest, N4JSStylers.CONSUMED_MEMBERS_STYLER);
				} else {
					styledText.append(" inherited " + orgDest, N4JSStylers.INHERITED_MEMBERS_STYLER);
				}
				
			}
		}
		return styledText;
	}

	// produces e.g. functionName(param1TypeName, param2TypeName) : returnTypeName
	def dispatch StyledString dispatchGetStyledText(FunctionDefinition functionDefinition) {
		var styledText = getLabelProvider.getSuperStyledText(functionDefinition)
		val definedType = functionDefinition.definedType
		
		if (definedType instanceof TFunction) {
			styledText = styledText.appendStyledTextForFormalParameters(functionDefinition, definedType)
			styledText = styledText.append(definedType.typeVars.handleTypeVars)

			val typeStr = (if (!definedType.constructor && definedType.returnTypeRef !== null)
					" : " + getTypeRefDescription(definedType.returnTypeRef)
				else
					"")

			return if (functionDefinition.returnTypeRef === null) {
				// type was inferred
				styledText.append(typeStr, INFERRED_TYPE_STYLER)
			} else {
				styledText.append(typeStr)
			}
		}
		return styledText
	}

	// produces e.g. <T, U>
	def private handleTypeVars(List<TypeVariable> typeVars) {
		if (typeVars.size > 0) " <" + typeVars.map[name].join(", ") + ">" else ""
	}

	// produces e.g. getterName() : returnTypeName
	def dispatch StyledString dispatchGetStyledText(N4GetterDeclaration n4GetterDeclaration) {
		var styledText = getLabelProvider.getSuperStyledText(n4GetterDeclaration)
		val definedGetter = n4GetterDeclaration.definedGetter

		if (definedGetter !== null) {
			val typeStr = (if (definedGetter.declaredTypeRef !== null)
					" : " + getTypeRefDescription(definedGetter.declaredTypeRef)
				else
					"")

			return if (n4GetterDeclaration.declaredTypeRef === null) {
				// type was inferred
				styledText.append(typeStr, INFERRED_TYPE_STYLER)
			} else {
				styledText.append(typeStr)
			}
		}
	}

	/** produces e.g. setterName: paramTypeName */
	def dispatch StyledString dispatchGetStyledText(N4SetterDeclaration n4SetterDeclaration) {
		var styledText = getLabelProvider.getSuperStyledText(n4SetterDeclaration)
		val definedSetter = n4SetterDeclaration.definedSetter
		if (definedSetter !== null) {
			if (n4SetterDeclaration.fpar !== null && definedSetter.fpar !== null) {
				styledText.append(" : ").append(
					getStyledTextForFormalParameter(n4SetterDeclaration.fpar, definedSetter.fpar))
			}
		}
	}

	// produces e.g. fieldName : fieldTypeName
	def dispatch StyledString dispatchGetStyledText(N4FieldDeclaration n4FieldDeclaration) {
		val styledText = getLabelProvider.getSuperStyledText(n4FieldDeclaration)
		val definedField = n4FieldDeclaration.definedField
		
		val typeStr = (if (definedField?.typeRef !== null)
				" : " + getTypeRefDescription(definedField.typeRef)
			else
				"")
		
		return if (n4FieldDeclaration.declaredTypeRef === null) {
			// type was inferred
			styledText?.append(typeStr, INFERRED_TYPE_STYLER)
		} else {
			styledText?.append(typeStr)
		}
	}

	// produces e.g. variableName : variableTypeName
	def dispatch StyledString dispatchGetStyledText(ExportedVariableDeclaration variableDeclaration) {
		val styledText = getLabelProvider.getSuperStyledText(variableDeclaration)
		val definedVariable = variableDeclaration.definedVariable

		val typeRefString = (if (definedVariable?.typeRef !== null)
				" : " + getTypeRefDescription(definedVariable.typeRef)
			else
				"");

		return if (variableDeclaration.declaredTypeRef === null) {
			// type was inferred
			styledText?.append(typeRefString, INFERRED_TYPE_STYLER)
		} else {
			styledText?.append(typeRefString)
		}
	}

	// produces e.g. (param1TypeName, param2TypeName)
	def private appendStyledTextForFormalParameters(StyledString styledText, FunctionDefinition n4Function,
		TFunction tFunction) {
		(styledText.append("(") => [
			if (tFunction.fpars.size > 0) {
				it.append((0 .. tFunction.fpars.size - 1).map [
					getStyledTextForFormalParameter(n4Function.fpars.get(it), tFunction.fpars.get(it))
				].reduce(l, r|l.append(", ").append(r)))
			}
		]).append(")")
	}

	def private getStyledTextForFormalParameter(FormalParameter formalParameter, TFormalParameter tFormalParameter) {
		return if (formalParameter.declaredTypeRef === null) {
			// type was inferred
			getTypeRefDescription(tFormalParameter.typeRef)
		} else {
			getTypeRefDescription(formalParameter.declaredTypeRef)
		}
	}

	/**
	 * Returns a type reference description which is compressed according to the {@link StyledTextCalculationHelper#OUTLINE_TYPE_REF_LENGTH_THRESHOLD} constant.
	 */
	def private StyledString getTypeRefDescription(TypeRef ref) {
		val typeRefDescription = new StyledString();
		getCompressedTypeRefDescription(ref, typeRefDescription);
		return typeRefDescription;
	}

	/**
	 * Appends the description of the given type reference to the styled string. If the {@link StyledTextCalculationHelper#OUTLINE_TYPE_REF_LENGTH_THRESHOLD} is exceeded
	 * further nested type information is omitted and replaced by three dots (...).
	 */
	def private boolean getCompressedTypeRefDescription(TypeRef typeRef, StyledString styledString) {
		if (styledString.length > OUTLINE_TYPE_REF_LENGTH_THRESHOLD) {
			styledString.append("...");
			return false;
		} else if (typeRef !== null) {
			dispatchGetTypeRefDescription(typeRef, styledString);
			return true;
		} else {
			styledString.append("null");
			return true;
		}
	}

	// appends the simple type name to the styled string
	def dispatch private void dispatchGetTypeRefDescription(TypeRef ref, StyledString styledString) {
		styledString.append(ref.declaredType?.name ?: "<unknown>");
	}

	// produces 'this' for ThisType references
	def dispatch private void dispatchGetTypeRefDescription(ThisTypeRef ref, StyledString styledString) {
		styledString.append("this");
	}

	// produces (param1, param2,...) => returnType
	def dispatch private void dispatchGetTypeRefDescription(FunctionTypeExpression ref, StyledString styledString) {
		styledString.append("(");

		// build parameter and return type in two different styled strings to reset the compression threshold for each of them
		val parameterString = new StyledString();
		appendCommaSeparatedTypeRefList(ref.fpars.map[it.typeRef], parameterString, true);
		styledString.append(parameterString);

		val returnTypeString = new StyledString();
		styledString.append(") => ");
		if (ref.returnTypeRef !== null) {
			getCompressedTypeRefDescription(ref.returnTypeRef, returnTypeString);
		} else {
			returnTypeString.append("void");
		}
		styledString.append(returnTypeString);
	}

	// produces type{typeName} or constructor{typeName}
	def dispatch private void dispatchGetTypeRefDescription(TypeTypeRef ref, StyledString styledString) {
		val typeName = switch ref.typeArg {
			ThisTypeRef:
				"this"
			default:
				ref.nominalTypeNameOrWildCard
		}
		if (ref.isConstructorRef) {
			styledString.append('''constructor{«typeName»}''')
		} else {
			styledString.append('''type{«typeName»}''')
		}
	}

	// produces union{type1, type2, ...} or intersection{type1, type2, ...}
	def dispatch private void dispatchGetTypeRefDescription(ComposedTypeRef ref, StyledString styledString) {
		if (ref instanceof UnionTypeExpression) {
			styledString.append("union{");
		} else {
			styledString.append("intersection{");
		}
		appendCommaSeparatedTypeRefList(ref.typeRefs, styledString, true);

		styledString.append("}");
	}

	// produces a comma separated TypeRef description list element by element
	def private void appendCommaSeparatedTypeRefList(Iterable<TypeRef> refs, StyledString styledString, boolean first) {
		if (!refs.iterator.hasNext) {
			return;
		}
		if (OUTLINE_TYPE_REF_LENGTH_THRESHOLD > styledString.length) {
			if (!first) {
				styledString.append(", ");
			}
			getCompressedTypeRefDescription(refs.iterator.next, styledString);
			appendCommaSeparatedTypeRefList(refs.drop(1), styledString, false);
		} else if (!first) {
			styledString.append(",...");
		} else {
			styledString.append("...");
		}
	}

	def private String getWildcardDescription(Wildcard wildcard) {
		val string = new StyledString();
		string.append("?");
		if (wildcard.declaredLowerBound !== null) {
			string.append(" super ");
			getCompressedTypeRefDescription(wildcard.declaredLowerBound, string);
		} else {
			string.append(" extends ");
			getCompressedTypeRefDescription(wildcard.declaredUpperBound, string);
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
}
