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
package org.eclipse.n4js.ide.server.codeActions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.ide.editor.contentassist.imports.ImportUtil;
import org.eclipse.n4js.ide.editor.contentassist.imports.ImportsAwareReferenceProposalCreator;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2.Options;
import org.eclipse.xtext.util.ReplaceRegion;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * N4JS quick fixes.
 *
 * see http://www.eclipse.org/Xtext/documentation.html#quickfixes
 */
@SuppressWarnings("restriction")
@Singleton
public class N4JSQuickfixProvider implements IQuickfixProvider {

	class QuickfixContext {
		final String code;
		final Options options;

		public QuickfixContext(String code, Options options) {
			this.code = code;
			this.options = options;
		}
	}

	final Class<?>[] quickfixProviders = { N4JSQuickfixProvider.class };

	Multimap<String, BiConsumer<QuickfixContext, CodeActionAcceptor>> quickfixMap = HashMultimap.create();

	// @Inject
	// private StatusHelper statusHelper;
	// @Inject
	// private IChangeSerializer serializer;
	// @Inject
	// private JavaScriptVariantHelper jsVariantHelper;

	@Inject
	private ImportUtil importUtil;

	/** Retrieve annotation constants from AnnotationDefinition */
	static final String INTERNAL_ANNOTATION = AnnotationDefinition.INTERNAL.name;
	static final String OVERRIDE_ANNOTATION = AnnotationDefinition.OVERRIDE.name;
	static final String FINAL_ANNOTATION = AnnotationDefinition.FINAL.name;

	/**
	 * Using reflection, all classes of {@link #quickfixProviders} are inspected to find all methods that are annotated
	 * with {@link Fix}.
	 */
	@Inject
	public void init(Injector injector) {
		for (Class<?> potQP : quickfixProviders) {
			if (IQuickfixProvider.class.isAssignableFrom(potQP)) {
				IQuickfixProvider qpInstance = (IQuickfixProvider) injector.getInstance(potQP);

				Method[] methods = potQP.getMethods();
				for (Method method : methods) {
					Fix fixAnnotation = method.getAnnotation(Fix.class);
					if (fixAnnotation != null) {
						String issueCode = fixAnnotation.issueCode();

						BiConsumer<QuickfixContext, CodeActionAcceptor> quickfixMethod = new BiConsumer<>() {
							@Override
							public void accept(QuickfixContext qc, CodeActionAcceptor caa) {
								try {
									method.invoke(qpInstance, qc, caa);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						};

						quickfixMap.put(issueCode, quickfixMethod);
					}
				}
			}
		}
	}

	/** Finds quick-fixes for the given issue code and adds these quick-fixes to the acceptor iff available. */
	public void addQuickfix(String code, Options options, CodeActionAcceptor acceptor) {
		for (BiConsumer<QuickfixContext, CodeActionAcceptor> quickfix : quickfixMap.get(code)) {
			QuickfixContext qc = new QuickfixContext(code, options);
			quickfix.accept(qc, acceptor);
		}
	}

	/**
	 * Resolves missing import statements by re-using content assist and {@link ImportsAwareReferenceProposalCreator}
	 */
	@Fix(issueCode = Diagnostic.LINKING_DIAGNOSTIC)
	public void addImportForUnresolvedReference(QuickfixContext context, CodeActionAcceptor acceptor) {
		Document doc = context.options.getDocument();
		Set<ContentAssistEntry> caEntries = importUtil.findImportCandidates(context.options);

		for (ContentAssistEntry cae : caEntries) {
			ArrayList<ReplaceRegion> replacements = cae.getTextReplacements();
			if (replacements != null && !replacements.isEmpty()) {
				String description = cae.getDescription();
				List<TextEdit> textEdits = new ArrayList<>();
				for (ReplaceRegion replaceRegion : replacements) {
					Position posStart = doc.getPosition(replaceRegion.getOffset());
					Position posEnd = doc.getPosition(replaceRegion.getOffset() + replaceRegion.getLength());
					Range rangeOfImport = new Range(posStart, posEnd);
					TextEdit textEdit = new TextEdit(rangeOfImport, replaceRegion.getText());
					textEdits.add(textEdit);
				}

				acceptor.acceptQuickfixCodeAction(context, "Add import from module " + description, textEdits);
			}
		}
	}

	// EXAMPLE FOR STYLE #2 (anonymous class)

	// @Fix(IssueCodes.SOME_CODE)
	// public void someComplexQuickFixExample(Issue issue, IssueResolutionAcceptor acceptor) {
	// // <--- do pre-processing here (if required)
	// acceptor.accept(issue,'Some Label','Some enlightening description.', 'SomeImage.gif',new
	// N4Modification(){override computeChanges(IModificationContext context,IMarker marker,int offset,int
	// length,EObject element)throws Exception{
	// // <--- create and return instances of IChange here (as in the above example)
	// }override supportsMultiApply(){return true; // <--- true is the default; return false to turn of multi-apply
	// // completely for this quick fix
	// }override isApplicableTo(IMarker marker){return true; // <--- true is the default; return value depending on
	// // 'marker' to fine-tune what other problems this quick
	// // fix is applicable to
	// }});
	// }

	/*
	 * If a type declaration has Java-form such as 'int foo() {}', then the following quickfix proposed which transforms
	 * it to colon style: 'foo(): int {}' It searches the bogus return type (which is 'int' in the example above)
	 * beginning from the parent node (N4MethodDeclarationImpl) of the method declaration. The bogus return type removed
	 * from the old position. A colon followed by the bogus return type added to the right side of the method name.
	 */
	// @Fix(IssueCodes.TYS_INVALID_TYPE_SYNTAX)
	// public void transformJavaTypeAnnotationToColonStyle(Issue issue, IssueResolutionAcceptor acceptor) {
	// acceptor.accept(issue,'Convert to colon style', 'The type annotation should be in colon style.This quick fix
	// will change the code to colon style.',ImageNames.REORDER)[context,marker,offset,length,element|if(!(element
	// instanceof ParameterizedTypeRefImpl)||!((element as ParameterizedTypeRefImpl).eContainer instanceof
	// N4MethodDeclarationImpl)){return#[]; // Its
	// // not
	// // a
	// // N4MethodDeclarationImpl.
	// // Not
	// // implemented
	// // yet.
	// }
	//
	// val node=NodeModelUtils.getNode(element)var parentNode=node.parent;while(!(parentNode instanceof
	// CompositeNodeWithSemanticElement)||!(parentNode.semanticElement instanceof
	// N4MethodDeclarationImpl)){parentNode=parentNode.parent;}
	//
	// val roundBracketNode=NodeModelUtilsN4.findKeywordNode(parentNode,')')val
	// bogusNode=NodeModelUtils.findNodesForFeature((parentNode as
	// CompositeNodeWithSemanticElement).semanticElement,N4JSPackage.Literals.TYPED_ELEMENT__BOGUS_TYPE_REF).head;
	//
	// val stringOfBogusType=NodeModelUtilsN4.getTokenTextWithHiddenTokens(bogusNode);
	//
	// val nodeAfterBogus=NodeModelUtils.findLeafNodeAtOffset(parentNode,bogusNode.endOffset);val
	// spaceAfterBogusLength=if(nodeAfterBogus!==null&&nodeAfterBogus.text.startsWith(" ")){1}else{0};
	//
	// val offsetBogusType=bogusNode.offset;val bogusTypeLength=stringOfBogusType.length;
	//
	// val offsetRoundBracket=roundBracketNode.totalOffset;
	//
	// return#[replace(context.xtextDocument,offsetBogusType,bogusTypeLength+spaceAfterBogusLength,""), // removes
	// the
	// // bogus
	// // type and
	// // whitespace
	// // at the
	// // old
	// // location
	// replace(context.xtextDocument,offsetRoundBracket+1,0,": "+stringOfBogusType) // inserts the bogus type at the
	// // new location (behind the
	// // closing round bracket)
	// ];]
	// }

	// @Fix(IssueCodes.CLF_FIELD_OPTIONAL_OLD_SYNTAX)
	// public void fixOldSyntaxForOptionalFields(Issue issue, IssueResolutionAcceptor acceptor) {
	// acceptor.accept(issue,'Change to new syntax','The syntax for optional fields has changed.This quick fix will
	// change the code to the new syntax.',ImageNames.REORDER)[context,marker,offset,length,element|val
	// offsetNameEnd=getOffsetOfNameEnd(element.eContainer);return#[replace(context.xtextDocument,offset+length-1,1,""),
	// // removes
	// // the
	// // ?
	// // at
	// // the
	// // old
	// // location
	// replace(context.xtextDocument,offsetNameEnd,0,"?") // inserts a ? at the new location (behind the field or
	// // accessor name)
	// ];]
	// }
	//
	// private int getOffsetOfNameEnd(EObject parent) {
	// val nodeOfName=switch(parent){N4FieldDeclaration:NodeModelUtils.findActualNodeFor((parent as
	// PropertyNameOwner).declaredName)TField:NodeModelUtils.findNodesForFeature(parent,TypesPackage.eINSTANCE.identifiableElement_Name).head};return
	// if(nodeOfName!==null){nodeOfName.offset+nodeOfName.length}else{-1};
	// }
	//
	// @Fix(IssueCodes.FUN_PARAM_OPTIONAL_WRONG_SYNTAX)
	// public void fixOldSyntaxForOptionalFpars(Issue issue, IssueResolutionAcceptor acceptor) {
	// acceptor.accept(issue,'Change to Default Parameter', 'Some enlightening
	// description.',ImageNames.REORDER)[context,marker,offset,length,element|return#[replace(context.xtextDocument,offset+length-1,1,"
	// = undefined") // replace
	// // the
	// // '?'
	// // with
	// // a
	// // '
	// // =
	// // undefined'
	// // (first
	// // space
	// // is
	// // mandatory!)
	// ];]
	// }
	//
	// @Fix(IssueCodes.CLF_OVERRIDE_ANNOTATION)
	// public void addOverrideAnnotation(Issue issue, IssueResolutionAcceptor acceptor) {
	// acceptor.accept(issue,'Add @Override','Add missing @Override
	// annotation.',ImageNames.ANNOTATION_ADD)[context,marker,offset,length,element|return#[insertLineAbove(context.xtextDocument,offset,"@"+OVERRIDE_ANNOTATION,true)];]
	// }
	//
	// @Fix(IssueCodes.CLF_OVERRIDE_NON_EXISTENT)
	// public void removeOverrideAnnotation(Issue issue, IssueResolutionAcceptor acceptor) {
	// acceptor.accept(issue,'Remove @Override','Remove unnecessary @Override
	// annotation.',ImageNames.ANNOTATION_REMOVE)[context,marker,offset,length,element|if(element instanceof
	// N4MethodDeclaration)return#[removeSemanticObject(context.xtextDocument,element.annotations.findFirst[name==OVERRIDE_ANNOTATION],true)]else
	// return#[];]
	// }
	//
	// @Fix(IssueCodes.SYN_MODIFIER_BAD_ORDER)
	// public void orderModifiers(Issue issue, IssueResolutionAcceptor acceptor) {
	// acceptor.accept(issue,'Order modifiers','Rearrange the modifiers to make them appear in correct
	// order.',ImageNames.REORDER)[context,marker,offset,length,element|if(element instanceof ModifiableElement){val
	// modifierStr=ModifierUtils.getSortedModifiers(element.declaredModifiers).join('
	// ');return#[replace(context.xtextDocument,offset,length,modifierStr)];}else return#[];]
	// }
	//
	// @Fix(Diagnostic.LINKING_DIAGNOSTIC)
	// public void addImportForUnresolvedReference(Issue issue, IssueResolutionAcceptor acceptor) {
	// // note: different errors use the above issue code
	// // (see org.eclipse.xtext.linking.impl.LinkingDiagnosticMessageProvider for an overview)
	// if (issue.message.startsWith("Couldn't resolve reference to ")) {
	// val proposals = issue.findImportCandidates(false);
	// for (currProp : proposals)
	// acceptor.accept(issue, 'Import ' + currProp.displayString, 'Add
	//
	// import org.eclipse.n4js.AnnotationDefinition;
	//
	// import declaration for the element.',ImageNames.IMPORT,currProp);}}
	//
	// @Fix(IssueCodes.CLF_ABSTRACT_BODY)
	// public void removeAbstractAnnotationFromMethodWithBody(Issue issue, IssueResolutionAcceptor acceptor) {
	// acceptor.accept(issue, "Remove abstract annotation", "", ImageNames.ANNOTATION_REMOVE) [ context, marker, offset,
	// length, element |
	// if (element instanceof N4MethodDeclaration) {
	// return #[removeModifier(context.xtextDocument, element, N4Modifier.ABSTRACT)]
	// }
	// return #[]
	// ]
	// }
	//
	// @Fix(IssueCodes.CLF_ABSTRACT_MISSING)
	// public void declareClassWithAbstractMethodAsAbstract(Issue issue, IssueResolutionAcceptor acceptor) {
	// acceptor.accept(issue, "Declare class as abstract", "", ImageNames.ANNOTATION_ADD, new N4Modification() {
	//
	// override computeChanges(IModificationContext context, IMarker marker, int offset, int length,
	// EObject element) throws Exception {
	// if (element instanceof N4MemberDeclaration) {
	// val containingClass = element.eContainer;
	// if (containingClass instanceof N4ClassDeclaration) {
	// return #[addModifier(context.xtextDocument, containingClass, N4Modifier.ABSTRACT)]
	// }
	// }
	// return #[]
	// }
	//
	// override supportsMultiApply() {
	// return false;
	// }
	// })
	// }
	//
	// @Fix(IssueCodes.CLF_MISSING_IMPLEMENTATION)
	// public void declareClassNotImplementingAbstractMethodAsAbstract(Issue issue, IssueResolutionAcceptor acceptor) {
	// acceptor.accept(issue, "Declare class as abstract", "", null) [ context, marker, offset, length, element |
	// if (element instanceof N4ClassDeclaration) {
	// return #[addModifier(context.xtextDocument, element, N4Modifier.ABSTRACT)]
	// }
	// ]
	// }
	//
	// @Fix(IssueCodes.CLF_MISSING_BODY)
	// public void declareMemberWithoutBodyAsAbstract(Issue issue, IssueResolutionAcceptor acceptor) {
	// acceptor.accept(issue, "Declare member as abstract", "", null) [ context, marker, offset, length, element |
	// if (element instanceof N4MethodDeclaration) {
	// return #[addModifier(context.xtextDocument, element, N4Modifier.ABSTRACT)]
	// } else if (element instanceof N4FieldAccessor) {
	// return #[addModifier(context.xtextDocument, element, N4Modifier.ABSTRACT)]
	// }
	// return #[]
	// ]
	// }
	//
	// @Fix(IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS)
	// public void changeAccessModifierOfMemberDeclaration(Issue issue, IssueResolutionAcceptor acceptor) {
	//
	// val declarationURI=issue.getUserData(IssueUserDataKeys.VIS_ILLEGAL_MEMBER_ACCESS.DECLARATION_OBJECT_URI);val
	// accessorSuggestion=issue.getUserData(IssueUserDataKeys.VIS_ILLEGAL_MEMBER_ACCESS.ACCESS_SUGGESTION);
	//
	// if(declarationURI===null||accessorSuggestion===null){return;}
	//
	// if(!isContainingResourceModifiable(declarationURI)){return;}
	//
	// // Make final to be available in nested class
	// val fixModifier=modifierForSuggestion(accessorSuggestion)val
	// fixAddInternal=modifierSuggestionIsInternal(accessorSuggestion);val quickFixLabel="Declare member as
	// "+readableStringForSuggestion(accessorSuggestion)
	//
	// acceptor.accept(issue,quickFixLabel,"",null,new N4Modification(){
	//
	// override computeChanges(IModificationContext context,IMarker marker,int offset,int length,EObject element)throws
	// Exception{if(element instanceof ParameterizedPropertyAccessExpression){val propertyDeclaration=element.property
	//
	// if((propertyDeclaration instanceof Type||propertyDeclaration instanceof TMember)&&propertyDeclaration instanceof
	// SyntaxRelatedTElement&&propertyDeclaration instanceof TAnnotableElement){
	//
	// val doc=context.getXtextDocument(propertyDeclaration.eResource.URI);val astElement=(propertyDeclaration as
	// SyntaxRelatedTElement).astElement;
	//
	// if(astElement instanceof N4MemberDeclaration){var changes=new
	// ArrayList<IChange>changes.add(setAccessModifiers(doc,astElement,fixModifier));
	//
	// if(fixAddInternal){changes.add(addAnnotation(doc,astElement,INTERNAL_ANNOTATION));}else{changes.add(removeAnnotation(doc,astElement,INTERNAL_ANNOTATION));}return
	// changes;}}}return#[]}
	//
	// override supportsMultiApply(){false}})
	//
	// }
	//
	// @Fix(IssueCodes.VIS_ILLEGAL_TYPE_ACCESS)
	// public void changeAccessModifierOfTypeDeclaration(Issue issue, IssueResolutionAcceptor acceptor) {
	//
	// val accessModifierSuggestion=issue.getUserData(IssueUserDataKeys.VIS_ILLEGAL_TYPE_ACCESS.ACCESS_SUGGESTION);val
	// declarationObjectUri=issue.getUserData(IssueUserDataKeys.VIS_ILLEGAL_TYPE_ACCESS.DECLARATION_OBJECT_URI)if(accessModifierSuggestion===null||declarationObjectUri===null){return;}
	//
	// // Check modifiability of target resource
	// if(!isContainingResourceModifiable(declarationObjectUri)){return;}
	//
	// var TopLevelVisibilityFix
	// fix;try{fix=topLevelVisibilityFixProvider.provideFixFor("type",accessModifierSuggestion);}catch(IllegalArgumentException
	// e){return} // In
	// // case
	// // of
	// // empty
	// // suggestion
	// // or
	// // invalid
	// // modifiers
	// // Make
	// // final
	// // to
	// // be
	// // available
	// // in
	// // nested
	// // class
	// val finalFix=fix;
	//
	// acceptor.accept(issue,fix.description,"",null,new N4Modification(){
	//
	// override computeChanges(IModificationContext context,IMarker marker,int offset,int length,EObject element)throws
	// Exception{
	//
	// var EObject typeDeclaration=null;
	//
	// if(element instanceof ParameterizedPropertyAccessExpression){typeDeclaration=element.property;}else if(element
	// instanceof TypeRef){typeDeclaration=element.declaredType}else if(element instanceof
	// NamedImportSpecifier){typeDeclaration=element.importedElement;}else if(element instanceof
	// IdentifierRef){typeDeclaration=element.id;}if(typeDeclaration===null){return#[];}else if(typeDeclaration
	// instanceof Type&&typeDeclaration instanceof SyntaxRelatedTElement&&typeDeclaration instanceof
	// TAnnotableElement){var declarationAstElement=(typeDeclaration as SyntaxRelatedTElement).astElement;val
	// doc=context.getXtextDocument(URI.createURI(declarationObjectUri));return
	// finalFix.changes(declarationAstElement,doc);}return#[]}override supportsMultiApply(){false}
	//
	// });
	// }
	//
	// @Fix(IssueCodes.VIS_ILLEGAL_VARIABLE_ACCESS)
	// public void changeAccessModifierOfVariableDeclaration(Issue issue, IssueResolutionAcceptor acceptor) {
	//
	// val
	// accessModifierSuggestion=issue.getUserData(IssueUserDataKeys.VIS_ILLEGAL_VARIABLE_ACCESS.ACCESS_SUGGESTION);val
	// declarationObjectURI=issue.getUserData(IssueUserDataKeys.VIS_ILLEGAL_VARIABLE_ACCESS.DECLARATION_OBJECT_URI);
	//
	// if(accessModifierSuggestion===null||declarationObjectURI===null){return;}if(!isContainingResourceModifiable(declarationObjectURI)){return;}
	//
	// var TopLevelVisibilityFix fix
	//
	// try{fix=topLevelVisibilityFixProvider.provideFixFor("variable",accessModifierSuggestion);}catch(IllegalArgumentException
	// e){
	// // In case of empty suggestion or invalid modifiers
	// return}
	//
	// // Make final to be available in nested class
	// val finalFix=fix;
	//
	// acceptor.accept(issue,fix.description,"",null,new N4Modification(){
	//
	// override computeChanges(IModificationContext context,IMarker marker,int offset,int length,EObject element)throws
	// Exception{var EObject variableDeclaration
	//
	// if(element instanceof IdentifierRef){variableDeclaration=element.id;}else if(element instanceof
	// ParameterizedPropertyAccessExpression){variableDeclaration=element.property}else if(element instanceof
	// NamedImportSpecifier){if(element.importedElement instanceof
	// TVariable){variableDeclaration=element.importedElement;}}
	//
	// if(variableDeclaration===null){return#[];}if(variableDeclaration instanceof TVariable){
	//
	// var variableNode=variableDeclaration.astElement;val
	// doc=context.getXtextDocument(URI.createURI(declarationObjectURI));
	//
	// if(variableNode instanceof ExportedVariableDeclaration){val statement=variableNode.eContainer;return
	// finalFix.changes(statement,doc);}}return#[]}
	//
	// override supportsMultiApply(){false}
	//
	// });
	//
	// }
	//
	// @Fix(IssueCodes.VIS_ILLEGAL_FUN_ACCESS)
	// public void changeAccessModifierOfFunctionDeclaration(Issue issue, IssueResolutionAcceptor acceptor) {
	//
	// val accessModifierSuggestion=issue.getUserData(IssueUserDataKeys.VIS_ILLEGAL_FUN_ACCESS.ACCESS_SUGGESTION);val
	// declarationObjectUri=issue.getUserData(IssueUserDataKeys.VIS_ILLEGAL_FUN_ACCESS.DECLARATION_OBJECT_URI)
	//
	// if(accessModifierSuggestion===null||declarationObjectUri===null){return;}
	//
	// // Check writability of target resource
	// if(!isContainingResourceModifiable(declarationObjectUri)){return;}
	//
	// var TopLevelVisibilityFix fix
	// try{fix=topLevelVisibilityFixProvider.provideFixFor("function",accessModifierSuggestion);}catch(IllegalArgumentException
	// e){
	// // In case of empty suggestion or invalid modifiers
	// return}
	// // Make final to be available in nested class
	//
	// val finalFix=fix;
	//
	// acceptor.accept(issue,fix.description,"",null,new N4Modification(){
	//
	// override computeChanges(IModificationContext context,IMarker marker,int offset,int length,EObject element)throws
	// Exception{
	//
	// var EObject functionImpl
	//
	// if(element instanceof IdentifierRef){functionImpl=element.id;}else if(element instanceof
	// ParameterizedPropertyAccessExpression){functionImpl=element.property;}else if(element instanceof
	// NamedImportSpecifier){functionImpl=element.importedElement;}
	//
	// if(functionImpl===null){return#[];}
	//
	// if(functionImpl instanceof TFunction){val functionAstElement=functionImpl.astElement
	//
	// // Get target resource document
	// val doc=context.getXtextDocument(URI.createURI(declarationObjectUri));return
	// finalFix.changes(functionAstElement,doc);}
	//
	// return#[]}
	//
	// override supportsMultiApply(){false}
	//
	// });
	// }
	//
	// @Fix(IssueCodes.CLF_OVERRIDE_VISIBILITY)
	// public void changeOverriddenMemberAccessModifier(Issue issue, IssueResolutionAcceptor acceptor) {
	// val
	// accessSuggestion=issue.getUserData(IssueUserDataKeys.CLF_OVERRIDE_VISIBILITY.OVERRIDDEN_MEMBER_ACCESS_MODIFIER);val
	// memberName=issue.getUserData(IssueUserDataKeys.CLF_OVERRIDE_VISIBILITY.OVERRIDDEN_MEMBER_NAME);val
	// superClassName=issue.getUserData(IssueUserDataKeys.CLF_OVERRIDE_VISIBILITY.SUPER_CLASS_NAME);
	//
	// if(accessSuggestion===null||memberName===null||superClassName===null){return;}
	//
	// val msg="Set access modifier to \""+readableStringForSuggestion(accessSuggestion)+"\" (align with
	// "+superClassName+"."+memberName+")";acceptor.accept(issue,msg,"",null)[context,marker,offset,length,element|if(element
	// instanceof N4MemberDeclaration){var changes=new
	// ArrayList<IChange>();changes.add(setAccessModifiers(context.xtextDocument,element,modifierForSuggestion(accessSuggestion)));if(modifierSuggestionIsInternal(accessSuggestion)){changes.add(addAnnotation(context.xtextDocument,element,INTERNAL_ANNOTATION));}else{changes.add(removeAnnotation(context.xtextDocument,element,INTERNAL_ANNOTATION));}return
	// changes;}return#[]];
	// }
	//
	// @Fix(IssueCodes.CLF_NOT_EXPORTED_NOT_PRIVATE)
	// public void markElementAsExported(Issue issue, IssueResolutionAcceptor acceptor) {
	//
	// acceptor.accept(issue,"Declare element as exported","",null)[context,marker,offset,length,element|
	//
	// // Check for .n4jsd file
	// if(jsVariantHelper.isExternalMode(element)&&element instanceof
	// ModifiableElement){return#[addCustomModifier(context.xtextDocument,element as
	// ModifiableElement,N4JSLanguageConstants.EXPORT_KEYWORD+" "+N4JSLanguageConstants.EXTERNAL_KEYWORD)]}else
	// if(element instanceof
	// ModifiableElement){return#[addCustomModifier(context.xtextDocument,element,N4JSLanguageConstants.EXPORT_KEYWORD)]}return#[]];
	// }
	//
	// @Fix(IssueCodes.CLF_EXTEND_FINAL)
	// public void unmarkSuperTypeAsFinal(Issue issue, IssueResolutionAcceptor acceptor) {
	// val superTypeDeclarationUri=issue.getUserData(IssueUserDataKeys.CLF_EXTEND_FINAL.SUPER_TYPE_DECLARATION_URI);
	//
	// if(superTypeDeclarationUri===null){return;}
	//
	// if(!isContainingResourceModifiable(superTypeDeclarationUri)){return;}
	//
	// acceptor.accept(issue,"Remove @Final annotation from super type","",null,new N4Modification(){
	//
	// override computeChanges(IModificationContext context,IMarker marker,int offset,int length,EObject element)throws
	// Exception{
	//
	// if(element instanceof ParameterizedTypeRef){val
	// superClassDeclaration=element.declaredType;if(superClassDeclaration instanceof TClassifier){val
	// astDeclaration=superClassDeclaration.astElement if(astDeclaration instanceof N4ClassifierDeclaration){val
	// doc=context.getXtextDocument(URI.createURI(superTypeDeclarationUri));return#[removeAnnotation(doc,astDeclaration,FINAL_ANNOTATION)]}}return#[];}}
	//
	// // Non local changes aren't multi applicable
	// override supportsMultiApply(){false;}
	//
	// });
	// }
	//
	// @Fix(IssueCodes.CLF_OVERRIDE_FINAL)
	// public void removeFinalAnnotationFromMember(Issue issue, IssueResolutionAcceptor acceptor) {
	// val overriddenMemberDeclarationUri=issue.getUserData(IssueUserDataKeys.CLF_OVERRIDE_FINAL.OVERRIDDEN_MEMBER_URI);
	//
	// if(overriddenMemberDeclarationUri===null){return;}
	//
	// if(!isContainingResourceModifiable(overriddenMemberDeclarationUri)){return;}
	//
	// acceptor.accept(issue,"Remove @Final annotation from overridden member","",null,new N4Modification(){
	//
	// override computeChanges(IModificationContext context,IMarker marker,int offset,int length,EObject element)throws
	// Exception{
	//
	// val resource=element.eResource;val
	// overriddenMemberDeclaration=QuickfixUtil.getEObjectForUri(resource.resourceSet,overriddenMemberDeclarationUri);
	//
	// if(overriddenMemberDeclaration===null){return#[];}
	//
	// if(overriddenMemberDeclaration instanceof SyntaxRelatedTElement){val
	// astMemberDeclaration=overriddenMemberDeclaration.astElement;if(astMemberDeclaration instanceof
	// N4MemberDeclaration){val
	// doc=context.getXtextDocument(URI.createURI(overriddenMemberDeclarationUri));return#[removeAnnotation(doc,astMemberDeclaration,FINAL_ANNOTATION)]}}return#[];}
	//
	// override supportsMultiApply(){false;}
	//
	// });
	// }
	//
	// @Fix(IssueCodes.NON_EXISTING_PROJECT)
	// public void tryInstallMissingDependencyFromNpm(Issue issue, IssueResolutionAcceptor acceptor) {
	//
	// val modification=new N4Modification(){var boolean multipleInvocations;
	//
	// override Collection<?extends IChange>computeChanges(IModificationContext context,IMarker marker,int offset,int
	// length,EObject element)throws Exception{invokeLibraryManager(element);}override Collection<?extends
	// IChange>computeOneOfMultipleChanges(IModificationContext context,IMarker marker,int offset,int length,EObject
	// element)throws Exception{invokeLibraryManager(element);}override void computeFinalChanges()throws
	// Exception{if(multipleInvocations){new
	// ProgressMonitorDialog(UIUtils.shell).run(true,true,[monitor|try{ResourcesPlugin.getWorkspace().build(CLEAN_BUILD,monitor);}catch(IllegalBinaryStateException
	// e){}catch(CoreException e){}]);}}
	//
	// def Collection<?extends IChange>invokeLibraryManager(EObject element)throws Exception{val dependency=element as
	// ProjectReference;val packageName=new N4JSProjectName(dependency.projectName);val packageVersion=if(dependency
	// instanceof ProjectDependency){dependency.versionRequirement}else{SemverUtils.createEmptyVersionRequirement()};
	//
	// val illegalBinaryExcRef=new AtomicReference val multiStatus=createMultiStatus("Installing npm package
	// '"+packageName+"'.");
	//
	// new ProgressMonitorDialog(UIUtils.shell).run(true,false,[monitor|try{val
	// Map<N4JSProjectName,NPMVersionRequirement>package=Collections.singletonMap(packageName,packageVersion);multiStatus.merge(libraryManager.installNPMs(package,false,new
	// PlatformResourceURI(issue.uriToProblem).toFileURI,monitor));
	//
	// }catch(IllegalBinaryStateException e){illegalBinaryExcRef.set(e);
	//
	// }catch(Exception e){val msg="Error while uninstalling npm package:
	// '"+packageName+"'.";multiStatus.merge(createError(msg,e));}]);
	//
	// if(null!==illegalBinaryExcRef.get){new IllegalBinaryStateDialog(illegalBinaryExcRef.get).open;
	//
	// }else
	// if(!multiStatus.isOK()){N4JSActivator.getInstance().getLog().log(multiStatus);UIUtils.display.asyncExec([val
	// title="NPM Install Failed";val
	// descr=StatusUtils.getErrorMessage(multiStatus,true);ErrorDialog.openError(UIUtils.shell,title,descr,multiStatus);]);}
	//
	// return#[];}}
	//
	// acceptor.accept(issue,'Install npm package to
	//
	// workspace', 'Download and install missing dependency from npm.',null,modification);
	// }
	//
	// /**
	// * N4IDL-related quick-fix which adds a "@VersionAware" annotation to classes which do not declare an explicit
	// type
	// * version.
	// */
	// @Fix(IssueCodes.IDL_VERSIONED_ELEMENT_MISSING_VERSION)
	// public void addVersionAwareAnnotation(Issue issue, IssueResolutionAcceptor acceptor) {
	// acceptor.accept(issue,'Declare this type as @VersionAware', 'Add @VersionAware
	// annotation.',ImageNames.ANNOTATION_ADD)[context,marker,offset,length,element|return#[insertLineAbove(context.xtextDocument,offset,"@"+AnnotationDefinition.VERSION_AWARE.name,true)];]
	// }
}
