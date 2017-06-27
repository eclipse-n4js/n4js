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
package org.eclipse.n4js.ui.quickfix;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;

import com.google.inject.Inject;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.ui.changes.IChange;
import org.eclipse.n4js.ui.changes.SemanticChangeProvider;

/**
 * Provides resolutions for top level visibility issues. This includes top-level classes, functions and variables.
 */
public class TopLevelVisibilityFixProvider {

	static private final String INTERNAL_ANNOTATION = AnnotationDefinition.INTERNAL.name;

	@Inject
	SemanticChangeProvider semanticChangeProvider;

	TopLevelVisibilityFix provideFixFor(String typeLabel, String suggestion) {
		return new TopLevelVisibilityFix(typeLabel, suggestion);
	}

	/**
	 * Helper class to decode the IssueProperties concerning Visibility issues of variables, types and functions and
	 * also calculate the IChanges
	 */
	public class TopLevelVisibilityFix {

		/** A short description of the change */
		private final String description;
		/** The modifier to resolve the visibility issue */
		private N4Modifier modifier = N4Modifier.PUBLIC;
		/** The necessity of an Internal annotation */
		private boolean internal = false;
		// Default is true as an unexported top level element is never visible.
		/** The necessity of an export modifier */
		private final boolean export = true;

		/**
		 * Decode accessModifierSuggestion. Also provide quick fix labels with typeLabel as subject.
		 *
		 * @param typeLabel
		 *            subject to change (e.g. type, variable,... )
		 * @param suggestion
		 *            access modifier suggestion passed by the validator.
		 */
		TopLevelVisibilityFix(String typeLabel, String suggestion)
				throws IllegalArgumentException {

			description = "Declare " + typeLabel + " as export " + QuickfixUtil.readableStringForSuggestion(suggestion);
			modifier = QuickfixUtil.modifierForSuggestion(suggestion);
			internal = QuickfixUtil.modifierSuggestionIsInternal(suggestion);

		}

		/**
		 * Returns the IChanges necessary to follow the suggestion.
		 */
		IChange[] changes(EObject element, IXtextDocument doc) throws BadLocationException {
			ArrayList<IChange> changes = new ArrayList<>();

			// In case of function declaration
			if (element instanceof TypeDefiningElement) {
				changes.add(semanticChangeProvider.setAccessModifier(doc, (TypeDefiningElement) element, this.modifier,
						this.export));
			}
			// In case of a exported variable
			else if (element instanceof ExportedVariableStatement) {
				changes.add(semanticChangeProvider.setAccessModifiers(doc, (ModifiableElement) element, this.modifier));
			} else {
				return changes.toArray(new IChange[changes.size()]);
			}

			if (element instanceof AnnotableElement) {
				if (this.internal) {
					changes.add(
							semanticChangeProvider.addAnnotation(doc, (AnnotableElement) element, INTERNAL_ANNOTATION));
				} else {
					changes.add(semanticChangeProvider.removeAnnotation(doc, (AnnotableElement) element,
							INTERNAL_ANNOTATION));
				}
				return changes.toArray(new IChange[changes.size()]);

			}
			return new IChange[] {};
		}

		/** The description of this visibility fix */
		String getDescription() {
			return this.description;
		}

		/** True if visibility fix includes insertion of an export modifier */
		boolean isExport() {
			return this.export;
		}

		/** The minimal modifier to fix the visibility issue */
		N4Modifier getModifier() {
			return this.modifier;
		}

		/** True if visibility fix includes insertion of an internal annotation */
		boolean isInternal() {
			return internal;
		}

	}
}
