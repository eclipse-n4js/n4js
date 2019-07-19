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
package org.eclipse.n4js.ui.quickfix

import com.google.inject.Inject
import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.Map
import java.util.concurrent.atomic.AtomicReference
import org.eclipse.core.resources.IMarker
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.CoreException
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.dialogs.ErrorDialog
import org.eclipse.jface.dialogs.ProgressMonitorDialog
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.binaries.IllegalBinaryStateException
import org.eclipse.n4js.external.LibraryManager
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ModifiableElement
import org.eclipse.n4js.n4JS.ModifierUtils
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4FieldAccessor
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.PropertyNameOwner
import org.eclipse.n4js.projectDescription.ProjectDependency
import org.eclipse.n4js.projectDescription.ProjectReference
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement
import org.eclipse.n4js.semver.SemverUtils
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement
import org.eclipse.n4js.ts.types.TAnnotableElement
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ui.binaries.IllegalBinaryStateDialog
import org.eclipse.n4js.ui.changes.IChange
import org.eclipse.n4js.ui.changes.SemanticChangeProvider
import org.eclipse.n4js.ui.internal.N4JSActivator
import org.eclipse.n4js.ui.labeling.helper.ImageNames
import org.eclipse.n4js.ui.quickfix.TopLevelVisibilityFixProvider.TopLevelVisibilityFix
import org.eclipse.n4js.ui.utils.ImportUtil
import org.eclipse.n4js.ui.utils.UIUtils
import org.eclipse.n4js.utils.StatusHelper
import org.eclipse.n4js.utils.StatusUtils
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.IssueUserDataKeys
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.diagnostics.Diagnostic
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext
import org.eclipse.xtext.ui.editor.quickfix.Fix
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor
import org.eclipse.xtext.validation.Issue

import static org.eclipse.core.resources.IncrementalProjectBuilder.CLEAN_BUILD
import static org.eclipse.n4js.ui.changes.ChangeProvider.*
import static org.eclipse.n4js.ui.quickfix.QuickfixUtil.*
import org.eclipse.n4js.projectModel.names.N4JSProjectName
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI

/**
 * N4JS quick fixes.
 *
 * see http://www.eclipse.org/Xtext/documentation.html#quickfixes
 */
class N4JSQuickfixProvider extends AbstractN4JSQuickfixProvider {

	@Inject
	extension ImportUtil

	@Inject
	extension StatusHelper

	@Inject
	extension QuickfixUtil.IssueUserDataKeysExtension

	@Inject
	private TopLevelVisibilityFixProvider topLevelVisibilityFixProvider;

	@Inject
	extension SemanticChangeProvider

	@Inject
	protected JavaScriptVariantHelper jsVariantHelper;

	@Inject
	private LibraryManager libraryManager;

	/** Retrieve annotation constants from AnnotationDefinition */
	static final String INTERNAL_ANNOTATION = AnnotationDefinition.INTERNAL.name;
	static final String OVERRIDE_ANNOTATION = AnnotationDefinition.OVERRIDE.name;
	static final String FINAL_ANNOTATION = AnnotationDefinition.FINAL.name;

	// EXAMPLE FOR STYLE #1 (lambda expression)

	// @Fix(IssueCodes.SOME_CODE)
	def someSimpleQuickFixExample(Issue issue, IssueResolutionAcceptor acceptor) {
		// <--- do pre-processing here (if required)
		acceptor.accept(issue, 'Some Label', 'Some enlightening description.', 'SomeImage.gif') [ context, marker, offset, length, element |
			// <--- this is executed when the fix is actually applied
			//      Do not use variable 'issue' here, and do not change the document here directly, but instead
			//      create and return a list of instances of IChange using the convenience methods in ChangeProvider:
			return #[
				insertLineAbove(context.xtextDocument, offset, "@SomeAnnotationToBeAdded", true)
				// <--- could add more changes here ... (separated by comma)
			];
		]
	}


	// EXAMPLE FOR STYLE #2 (anonymous class)

	// @Fix(IssueCodes.SOME_CODE)
	def someComplexQuickFixExample(Issue issue, IssueResolutionAcceptor acceptor) {
		// <--- do pre-processing here (if required)
		acceptor.accept(issue, 'Some Label', 'Some enlightening description.', 'SomeImage.gif', new N4Modification() {
			override computeChanges(IModificationContext context, IMarker marker, int offset, int length, EObject element) throws Exception {
				// <--- create and return instances of IChange here (as in the above example)
			}
			override supportsMultiApply() {
				return true; // <--- true is the default; return false to turn of multi-apply completely for this quick fix
			}
			override isApplicableTo(IMarker marker) {
				return true; // <--- true is the default; return value depending on 'marker' to fine-tune what other problems this quick fix is applicable to
			}
		});
	}

	@Fix(IssueCodes.CLF_FIELD_OPTIONAL_OLD_SYNTAX)
	def fixOldSyntaxForOptionalFields(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, 'Change to new syntax', 'The syntax for optional fields has changed. This quick fix will change the code to the new syntax.', ImageNames.REORDER) [ context, marker, offset, length, element |
			val offsetNameEnd = getOffsetOfNameEnd(element.eContainer);
			return #[
				replace(context.xtextDocument, offset + length - 1, 1, ""), // removes the ? at the old location
				replace(context.xtextDocument, offsetNameEnd, 0, "?") // inserts a ? at the new location (behind the field or accessor name)
			];
		]
	}
	def private int getOffsetOfNameEnd(EObject parent) {
		val nodeOfName = switch(parent) {
			N4FieldDeclaration:
				NodeModelUtils.findActualNodeFor((parent as PropertyNameOwner).declaredName)
			TField:
				NodeModelUtils.findNodesForFeature(parent, TypesPackage.eINSTANCE.identifiableElement_Name).head
		};
		return if(nodeOfName!==null) {
			nodeOfName.offset + nodeOfName.length
		} else {
			-1
		};
	}
	@Fix(IssueCodes.FUN_PARAM_OPTIONAL_WRONG_SYNTAX)
	def fixOldSyntaxForOptionalFpars(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, 'Change to Default Parameter', 'Some enlightening description.', ImageNames.REORDER) [ context, marker, offset, length, element |
			return #[
				replace(context.xtextDocument, offset + length - 1, 1, " = undefined") // replace the '?' with a ' = undefined' (first space is mandatory!)
			];
		]
	}

	@Fix(IssueCodes.CLF_OVERRIDE_ANNOTATION)
	def addOverrideAnnotation(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, 'Add @Override', 'Add missing @Override annotation.', ImageNames.ANNOTATION_ADD) [ context, marker, offset, length, element |
			return #[
				insertLineAbove(context.xtextDocument, offset, "@"+OVERRIDE_ANNOTATION, true)
			];
		]
	}

	@Fix(IssueCodes.CLF_OVERRIDE_NON_EXISTENT)
	def removeOverrideAnnotation(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, 'Remove @Override', 'Remove unnecessary @Override annotation.',
			ImageNames.ANNOTATION_REMOVE) [ context, marker, offset, length, element |
			if (element instanceof N4MethodDeclaration)
				return #[
					removeSemanticObject(context.xtextDocument, element.annotations.findFirst[name == OVERRIDE_ANNOTATION], true)
				]
			else
				return #[];
		]
	}

	@Fix(IssueCodes.SYN_MODIFIER_BAD_ORDER)
	def orderModifiers(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, 'Order modifiers', 'Rearrange the modifiers to make them appear in correct order.',
			ImageNames.REORDER) [ context, marker, offset, length, element |
			if (element instanceof ModifiableElement) {
				val modifierStr = ModifierUtils.getSortedModifiers(element.declaredModifiers).join(' ');
				return #[replace(context.xtextDocument, offset, length, modifierStr)];
			} else
				return #[];
		]
	}

	@Fix(Diagnostic.LINKING_DIAGNOSTIC)
	def addImportForUnresolvedReference(Issue issue, IssueResolutionAcceptor acceptor) {
		// note: different errors use the above issue code
		// (see org.eclipse.xtext.linking.impl.LinkingDiagnosticMessageProvider for an overview)
		if (issue.message.startsWith("Couldn't resolve reference to ")) {
			val proposals = issue.findImportCandidates(false);
			for (currProp : proposals)
				acceptor.accept(issue, 'Import ' + currProp.displayString, 'Add import declaration for the element.',
					ImageNames.IMPORT, currProp);
		}
	}

	@Fix(IssueCodes.CLF_ABSTRACT_BODY)
	def removeAbstractAnnotationFromMethodWithBody(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Remove abstract annotation", "", ImageNames.ANNOTATION_REMOVE) [ context, marker, offset, length, element |
			if (element instanceof N4MethodDeclaration) {
				return #[removeModifier(context.xtextDocument, element, N4Modifier.ABSTRACT)]
			}
			return #[]
		]
	}

	@Fix(IssueCodes.CLF_ABSTRACT_MISSING)
	def declareClassWithAbstractMethodAsAbstract(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Declare class as abstract", "", ImageNames.ANNOTATION_ADD, new N4Modification() {

			override computeChanges(IModificationContext context, IMarker marker, int offset, int length,
				EObject element) throws Exception {
				if (element instanceof N4MemberDeclaration) {
					val containingClass = element.eContainer;
					if (containingClass instanceof N4ClassDeclaration) {
						return #[addModifier(context.xtextDocument, containingClass, N4Modifier.ABSTRACT)]
					}
				}
				return #[]
			}

			override supportsMultiApply() {
				return false;
			}
		})
	}

	@Fix(IssueCodes.CLF_MISSING_IMPLEMENTATION)
	def declareClassNotImplementingAbstractMethodAsAbstract(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Declare class as abstract", "", null) [ context, marker, offset, length, element |
			if (element instanceof N4ClassDeclaration) {
				return #[addModifier(context.xtextDocument, element, N4Modifier.ABSTRACT)]
			}
		]
	}

	@Fix(IssueCodes.CLF_MISSING_BODY)
	def declareMemberWithoutBodyAsAbstract(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Declare member as abstract", "", null) [ context, marker, offset, length, element |
			if (element instanceof N4MethodDeclaration) {
				return #[addModifier(context.xtextDocument, element, N4Modifier.ABSTRACT)]
			} else if (element instanceof N4FieldAccessor) {
				return #[addModifier(context.xtextDocument, element, N4Modifier.ABSTRACT)]
			}
			return #[]
		]
	}

	@Fix(IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS)
	def changeAccessModifierOfMemberDeclaration(Issue issue, IssueResolutionAcceptor acceptor) {

		val declarationURI = issue.getUserData(IssueUserDataKeys.VIS_ILLEGAL_MEMBER_ACCESS.DECLARATION_OBJECT_URI);
		val accessorSuggestion = issue.getUserData(IssueUserDataKeys.VIS_ILLEGAL_MEMBER_ACCESS.ACCESS_SUGGESTION);

		if (declarationURI === null || accessorSuggestion === null) {
			return;
		}

		if (!isContainingResourceModifiable(declarationURI)) {
			return;
		}

		// Make final to be available in nested class
		val fixModifier = modifierForSuggestion(accessorSuggestion)
		val fixAddInternal = modifierSuggestionIsInternal(accessorSuggestion);
		val quickFixLabel = "Declare member as " + readableStringForSuggestion(accessorSuggestion)

		acceptor.accept(issue, quickFixLabel, "", null,
			new N4Modification() {

				override computeChanges(IModificationContext context, IMarker marker, int offset, int length,
					EObject element) throws Exception {
					if (element instanceof ParameterizedPropertyAccessExpression) {
						val propertyDeclaration = element.property

						if ((propertyDeclaration instanceof Type || propertyDeclaration instanceof TMember) &&
							propertyDeclaration instanceof SyntaxRelatedTElement &&
							propertyDeclaration instanceof TAnnotableElement ) {

								val doc = context.getXtextDocument(propertyDeclaration.eResource.URI);
								val astElement = (propertyDeclaration as SyntaxRelatedTElement).astElement;

								if (astElement instanceof N4MemberDeclaration) {
									var changes = new ArrayList<IChange>
									changes.add(setAccessModifiers(doc, astElement, fixModifier));

									if (fixAddInternal) {
										changes.add(addAnnotation(doc, astElement, INTERNAL_ANNOTATION));
									} else {
										changes.add(
											removeAnnotation(doc, astElement, INTERNAL_ANNOTATION)
										);
									}
									return changes;
								}
							}
						}
						return #[]
					}

					override supportsMultiApply() {
						false
					}
				})

		}

	@Fix(IssueCodes.VIS_ILLEGAL_TYPE_ACCESS)
	def changeAccessModifierOfTypeDeclaration(Issue issue, IssueResolutionAcceptor acceptor) {

		val accessModifierSuggestion = issue.getUserData(
		IssueUserDataKeys.VIS_ILLEGAL_TYPE_ACCESS.ACCESS_SUGGESTION);
		val declarationObjectUri = issue.getUserData(
			IssueUserDataKeys.VIS_ILLEGAL_TYPE_ACCESS.DECLARATION_OBJECT_URI)
		if (accessModifierSuggestion === null || declarationObjectUri === null) {
			return;
		}

		// Check modifiability of target resource
		if (!isContainingResourceModifiable(declarationObjectUri)) {
			return;
		}

		var TopLevelVisibilityFix fix;
		try {
			fix = topLevelVisibilityFixProvider.provideFixFor("type", accessModifierSuggestion);
		} catch (IllegalArgumentException e) {
			return
		} // In case of empty suggestion or invalid modifiers
		// Make final to be available in nested class
		val finalFix = fix;

		acceptor.accept(issue, fix.description, "", null,new N4Modification() {

			override computeChanges(IModificationContext context, IMarker marker, int offset, int length,EObject element)
				throws Exception {

					var EObject typeDeclaration = null;

					if (element instanceof ParameterizedPropertyAccessExpression) {
						typeDeclaration = element.property;
					} else if (element instanceof TypeRef) {
						typeDeclaration = element.declaredType
					} else if (element instanceof NamedImportSpecifier) {
						typeDeclaration = element.importedElement;
					} else if (element instanceof IdentifierRef) {
						typeDeclaration = element.id;
					}
						if (typeDeclaration === null) {
						return #[];
					} else if (typeDeclaration instanceof Type &&
						typeDeclaration instanceof SyntaxRelatedTElement &&
						typeDeclaration instanceof TAnnotableElement
				) {
							var declarationAstElement = (typeDeclaration as SyntaxRelatedTElement).astElement;
							val doc = context.getXtextDocument(URI.createURI(declarationObjectUri));
						return finalFix.changes(declarationAstElement, doc);
					}
					return #[]
				}
					override supportsMultiApply() {
					false
				}

		});
	}

	@Fix(IssueCodes.VIS_ILLEGAL_VARIABLE_ACCESS)
	def changeAccessModifierOfVariableDeclaration(Issue issue, IssueResolutionAcceptor acceptor) {

		val accessModifierSuggestion = issue.getUserData(
			IssueUserDataKeys.VIS_ILLEGAL_VARIABLE_ACCESS.ACCESS_SUGGESTION);
		val declarationObjectURI = issue.getUserData(
			IssueUserDataKeys.VIS_ILLEGAL_VARIABLE_ACCESS.DECLARATION_OBJECT_URI);

		if (accessModifierSuggestion === null || declarationObjectURI === null) {
			return;
		}
		if (!isContainingResourceModifiable(declarationObjectURI)) {
			return;
		}

		var TopLevelVisibilityFix fix

		try {
			fix = topLevelVisibilityFixProvider.provideFixFor("variable", accessModifierSuggestion);
		} catch (IllegalArgumentException e) {
			// In case of empty suggestion or invalid modifiers
			return
		}

		// Make final to be available in nested class
		val finalFix = fix;

		acceptor.accept(issue, fix.description, "", null,new N4Modification() {

			override computeChanges(IModificationContext context, IMarker marker, int offset, int length,
				EObject element) throws Exception {
				var EObject variableDeclaration

				if (element instanceof IdentifierRef) {
					variableDeclaration = element.id;
				} else if (element instanceof ParameterizedPropertyAccessExpression) {
					variableDeclaration = element.property
				} else if ( element instanceof NamedImportSpecifier ) {
					if ( element.importedElement instanceof TVariable ) {
						variableDeclaration = element.importedElement;
					}
				}

				if (variableDeclaration === null) {
					return #[];
				}
				if (variableDeclaration instanceof TVariable) {

					var variableNode = variableDeclaration.astElement;
					val doc = context.getXtextDocument(URI.createURI(declarationObjectURI));

					if (variableNode instanceof ExportedVariableDeclaration) {
						val statement = variableNode.eContainer;
						return finalFix.changes(statement, doc);
					}
				}
				return #[]
			}

			override supportsMultiApply() {
				false
			}

		});

	}

	@Fix(IssueCodes.VIS_ILLEGAL_FUN_ACCESS)
	def changeAccessModifierOfFunctionDeclaration(Issue issue, IssueResolutionAcceptor acceptor) {

		val accessModifierSuggestion = issue.getUserData(
			IssueUserDataKeys.VIS_ILLEGAL_FUN_ACCESS.ACCESS_SUGGESTION);
		val declarationObjectUri = issue.getUserData(
		IssueUserDataKeys.VIS_ILLEGAL_FUN_ACCESS.DECLARATION_OBJECT_URI)

		if (accessModifierSuggestion === null || declarationObjectUri === null) {
			return;
		}

		// Check writability of target resource
		if (!isContainingResourceModifiable(declarationObjectUri)) {
			return;
		}

		var TopLevelVisibilityFix fix
		try {
			fix = topLevelVisibilityFixProvider.provideFixFor("function", accessModifierSuggestion);
		} catch (IllegalArgumentException e) {
			// In case of empty suggestion or invalid modifiers
			return
		}
		// Make final to be available in nested class

		val finalFix = fix;

		acceptor.accept(issue, fix.description, "", null,new N4Modification() {

			override computeChanges(IModificationContext context, IMarker marker, int offset, int length,
				EObject element) throws Exception {

				var EObject functionImpl

				if (element instanceof IdentifierRef) {
					functionImpl = element.id;
				} else if (element instanceof ParameterizedPropertyAccessExpression) {
					functionImpl = element.property;
				} else if ( element instanceof NamedImportSpecifier ) {
					functionImpl = element.importedElement;
				}

				if (functionImpl === null) {
					return #[];
				}

				if (functionImpl instanceof TFunction) {
					val functionAstElement = functionImpl.astElement

					// Get target resource document
					val doc = context.getXtextDocument(URI.createURI(declarationObjectUri));
					return finalFix.changes(functionAstElement, doc);
				}

				return #[]
			}

			override supportsMultiApply() {
				false
			}

		});
	}

	@Fix(IssueCodes.CLF_OVERRIDE_VISIBILITY)
	def changeOverriddenMemberAccessModifier(Issue issue, IssueResolutionAcceptor acceptor) {
		val accessSuggestion = issue.getUserData(
			IssueUserDataKeys.CLF_OVERRIDE_VISIBILITY.OVERRIDDEN_MEMBER_ACCESS_MODIFIER);
		val memberName = issue.getUserData(IssueUserDataKeys.CLF_OVERRIDE_VISIBILITY.OVERRIDDEN_MEMBER_NAME);
		val superClassName = issue.getUserData(IssueUserDataKeys.CLF_OVERRIDE_VISIBILITY.SUPER_CLASS_NAME);

		if (accessSuggestion === null || memberName === null || superClassName === null) {
			return;
		}

		val msg = "Set access modifier to \"" + readableStringForSuggestion(accessSuggestion) + "\" (align with " + superClassName + "." + memberName + ")";
		acceptor.accept(issue, msg, "", null) [ context, marker, offset, length, element |
			if (element instanceof N4MemberDeclaration) {
				var changes = new ArrayList<IChange>();
				changes.add(setAccessModifiers(context.xtextDocument, element, modifierForSuggestion(accessSuggestion)));
				if ( modifierSuggestionIsInternal(accessSuggestion) ) {
					changes.add(addAnnotation(context.xtextDocument,element,INTERNAL_ANNOTATION));
				}
				else {
					changes.add(removeAnnotation(context.xtextDocument,element,INTERNAL_ANNOTATION));
				}
				return changes;
			}
			return #[]
		];
	}

	@Fix(IssueCodes.CLF_NOT_EXPORTED_NOT_PRIVATE)
	def markElementAsExported(Issue issue, IssueResolutionAcceptor acceptor) {

		acceptor.accept(issue, "Declare element as exported", "", null) [ context, marker, offset, length, element |

			//Check for .n4jsd file
			if (jsVariantHelper.isExternalMode(element) && element instanceof ModifiableElement ) {
				return #[addCustomModifier(context.xtextDocument, element as ModifiableElement,N4JSLanguageConstants.EXPORT_KEYWORD+" "+N4JSLanguageConstants.EXTERNAL_KEYWORD)]
			}
			else if (element instanceof ModifiableElement) {
				return #[addCustomModifier(context.xtextDocument, element,N4JSLanguageConstants.EXPORT_KEYWORD)]
			}
				return #[]
		];
	}

	@Fix(IssueCodes.CLF_EXTEND_FINAL)
	def unmarkSuperTypeAsFinal(Issue issue, IssueResolutionAcceptor acceptor) {
		val superTypeDeclarationUri = issue.getUserData(
			IssueUserDataKeys.CLF_EXTEND_FINAL.SUPER_TYPE_DECLARATION_URI);

		if (superTypeDeclarationUri === null) {
			return;
		}

		if (!isContainingResourceModifiable(superTypeDeclarationUri)) {
			return;
		}

		acceptor.accept(issue, "Remove @Final annotation from super type", "", null, new N4Modification() {

			override computeChanges(IModificationContext context, IMarker marker, int offset, int length,
				EObject element) throws Exception {

				if (element instanceof ParameterizedTypeRef) {
					val superClassDeclaration = element.declaredType;
					if (superClassDeclaration instanceof TClassifier) {
						val astDeclaration = superClassDeclaration.astElement
						if (astDeclaration instanceof N4ClassifierDeclaration) {
							val doc = context.getXtextDocument(URI.createURI(superTypeDeclarationUri));
							return #[removeAnnotation(doc, astDeclaration, FINAL_ANNOTATION)]
						}
					}
					return #[];
				}
			}

			// Non local changes aren't multi applicable
			override supportsMultiApply() {
				false;
			}

		});
	}

	@Fix(IssueCodes.CLF_OVERRIDE_FINAL)
	def removeFinalAnnotationFromMember(Issue issue, IssueResolutionAcceptor acceptor) {
		val overriddenMemberDeclarationUri = issue.getUserData(
			IssueUserDataKeys.CLF_OVERRIDE_FINAL.OVERRIDDEN_MEMBER_URI);

		if (overriddenMemberDeclarationUri === null) {
			return;
		}

		if (!isContainingResourceModifiable(overriddenMemberDeclarationUri)) {
			return;
		}

		acceptor.accept(issue, "Remove @Final annotation from overridden member", "", null,new N4Modification() {

			override computeChanges(IModificationContext context, IMarker marker, int offset, int length,
				EObject element) throws Exception {

				val resource = element.eResource;
				val overriddenMemberDeclaration = QuickfixUtil.getEObjectForUri(resource.resourceSet,overriddenMemberDeclarationUri);

				if (overriddenMemberDeclaration === null) {
					return #[];
				}

				if (overriddenMemberDeclaration instanceof SyntaxRelatedTElement) {
					val astMemberDeclaration = overriddenMemberDeclaration.astElement;
					if (astMemberDeclaration instanceof N4MemberDeclaration) {
						val doc = context.getXtextDocument(URI.createURI(overriddenMemberDeclarationUri));
						return #[removeAnnotation(doc, astMemberDeclaration, FINAL_ANNOTATION)]
					}
				}
				return #[];
			}

			override supportsMultiApply() {
				false;
			}

		});
	}

	@Fix(IssueCodes.NON_EXISTING_PROJECT)
	def tryInstallMissingDependencyFromNpm(Issue issue, IssueResolutionAcceptor acceptor) {

		val modification = new N4Modification() {
			var boolean multipleInvocations;

			override Collection<? extends IChange> computeChanges(IModificationContext context, IMarker marker, int offset, int length, EObject element) throws Exception {
				invokeLibraryManager(element);
			}
			override Collection<? extends IChange> computeOneOfMultipleChanges(IModificationContext context, IMarker marker, int offset, int length, EObject element) throws Exception {
				invokeLibraryManager(element);
			}
			override void computeFinalChanges() throws Exception {
				if (multipleInvocations) {
					new ProgressMonitorDialog(UIUtils.shell).run(true, true, [monitor |
						try {
							ResourcesPlugin.getWorkspace().build(CLEAN_BUILD, monitor);
						} catch (IllegalBinaryStateException e) {
						} catch (CoreException e) {
						}
					]);
				}
			}

			def Collection<? extends IChange> invokeLibraryManager(EObject element) throws Exception {
				val dependency = element as ProjectReference;
				val packageName = new N4JSProjectName(dependency.projectName);
				val packageVersion = if (dependency instanceof ProjectDependency) {
						dependency.versionRequirement
					} else {
						SemverUtils.createEmptyVersionRequirement()
					};

				val illegalBinaryExcRef = new AtomicReference
				val multiStatus = createMultiStatus("Installing npm package '" + packageName + "'.");

				new ProgressMonitorDialog(UIUtils.shell).run(true, false, [monitor |
					try {
						val Map<N4JSProjectName, NPMVersionRequirement> package = Collections.singletonMap(packageName, packageVersion);
						multiStatus.merge(libraryManager.installNPMs(package, false, new PlatformResourceURI(issue.uriToProblem).toFileURI, monitor));

					} catch (IllegalBinaryStateException e) {
						illegalBinaryExcRef.set(e);

					} catch (Exception e) {
						val msg = "Error while uninstalling npm package: '" + packageName + "'.";
						multiStatus.merge(createError(msg, e));
					}
				]);

				if (null !== illegalBinaryExcRef.get) {
					new IllegalBinaryStateDialog(illegalBinaryExcRef.get).open;

				} else if (!multiStatus.isOK()) {
					N4JSActivator.getInstance().getLog().log(multiStatus);
					UIUtils.display.asyncExec([
						val title = "NPM Install Failed";
						val descr = StatusUtils.getErrorMessage(multiStatus, true);
						ErrorDialog.openError(UIUtils.shell, title, descr, multiStatus);
					]);
				}

			return #[];
			}
		}

		acceptor.accept(issue, 'Install npm package to workspace', 'Download and install missing dependency from npm.', null, modification);
	}

	/**
	 * N4IDL-related quick-fix which adds a "@VersionAware" annotation to 
	 * classes which do not declare an explicit type version.
	 */
	@Fix(IssueCodes.IDL_VERSIONED_ELEMENT_MISSING_VERSION)
	def addVersionAwareAnnotation(Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, 'Declare this type as @VersionAware', 'Add @VersionAware annotation.', ImageNames.ANNOTATION_ADD) [ context, marker, offset, length, element |
			return #[
				insertLineAbove(context.xtextDocument, offset, "@"+AnnotationDefinition.VERSION_AWARE.name, true)
			];
		]
	}
}
