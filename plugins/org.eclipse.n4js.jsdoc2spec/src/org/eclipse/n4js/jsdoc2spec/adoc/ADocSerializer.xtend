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
package org.eclipse.n4js.jsdoc2spec.adoc;

import com.google.inject.Inject
import java.util.Collection
import java.util.List
import java.util.Map
import java.util.Map.Entry
import java.util.Set
import java.util.SortedSet
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.jsdoc.dom.Composite
import org.eclipse.n4js.jsdoc.dom.ContentNode
import org.eclipse.n4js.jsdoc.dom.Doclet
import org.eclipse.n4js.jsdoc.dom.InlineTag
import org.eclipse.n4js.jsdoc.dom.Literal
import org.eclipse.n4js.jsdoc.dom.SimpleTypeReference
import org.eclipse.n4js.jsdoc.dom.Text
import org.eclipse.n4js.jsdoc.dom.VariableReference
import org.eclipse.n4js.jsdoc.tags.DefaultLineTagDefinition
import org.eclipse.n4js.jsdoc2spec.KeyUtils
import org.eclipse.n4js.jsdoc2spec.RepoRelativePath
import org.eclipse.n4js.jsdoc2spec.SpecTestInfo
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.FieldAccessor
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMemberWithAccessModifier
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TN4Classifier
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.typesystem.utils.AllSuperTypesCollector
import org.eclipse.n4js.utils.DeclMergingHelper
import org.eclipse.n4js.utils.Strings
import org.eclipse.n4js.validation.N4JSElementKeywordProvider
import org.eclipse.n4js.validation.ValidatorMessageHelper

import static org.eclipse.n4js.jsdoc.N4JSDocletParser.*

/**
 * Print AsciiDoc code of specification JSDoc. Start and end markers are printed by client.
 *
 * Needs to be injectd.
 */
class ADocSerializer {
	@Inject extension Html2ADocConverter;
	@Inject ValidatorMessageHelper validatorMessageHelper;
	@Inject N4JSElementKeywordProvider keywordProvider;
	@Inject	RepoRelativePathHolder repoPathHolder;
	@Inject	DeclMergingHelper declMergingHelper;


	def String process(SpecRequirementSection spec, Map<String, SpecSection> specsByKey) {
		val strb = new StringBuilder();
		strb.appendSpecElementPost(spec, specsByKey);
		return Strings.stripAllTrailing(strb.toString());
	}

	def String process(SpecIdentifiableElementSection spec, Map<String, SpecSection> specsByKey) {
		val strb = new StringBuilder();
		strb.appendSpecElementPre(spec);
		strb.appendSpec(spec);
		strb.appendSpecElementPost(spec, specsByKey);
		return Strings.stripAllTrailing(strb.toString());
	}

	private def StringBuilder appendSpecElementPost(StringBuilder strb, SpecRequirementSection spec, Map<String, SpecSection> map) {
		if (! spec.getTestInfosForType.isNullOrEmpty) {
			val Map<String, List<SpecTestInfo>> groupdTests = spec.getTestInfosForType.groupBy[testTitle];
			strb.appendApiConstraints(groupdTests);
		}
		return strb
	}

	private def StringBuilder appendSpec(StringBuilder strb, SpecIdentifiableElementSection spec) {
		strb.append("\n");

		var addedTaskLinks = false;
		val taskTags = spec.getDoclet.lineTags(TAG_TASK.title);
		for (tag : taskTags) {
			val taskID = TAG_TASK.getValue(tag, "");
			if (!taskID.empty) {
				if (taskID.startsWith("*")) {
					strb.appendTaskLink(taskID.substring(1));
				} else {
					strb.appendTaskLink(taskID);
				}
				strb.append(" ");
				addedTaskLinks = true;
			}
		}
		if(addedTaskLinks)
			strb.append("\n\n");

		strb.appendSpecDescription(spec);
		return strb
	}

	private def StringBuilder appendSpecDescription(StringBuilder strb, SpecIdentifiableElementSection spec) {
		val doclet = spec.getDoclet;
		val bSpecFromDescr = doclet.hasLineTag(TAG_SPECFROMDESCR.title);
		val reqID = getReqId(doclet);
		val specTags = doclet.lineTags(TAG_SPEC.title);

		if (specTags.empty && !bSpecFromDescr && reqID.isEmpty)
			return strb;

		if (!(spec.idElement instanceof TN4Classifier || spec.idElement instanceof TEnum))
			strb.append("==== Description\n\n");

		if (!reqID.isEmpty) {
			strb.append("See req:"+reqID +"[].\n");
		}

		strb.appendContents(doclet);
		for (tag : specTags) {
			strb.appendContents(tag.getValueByKey(DefaultLineTagDefinition.CONTENTS));
		}

		strb.append("\n");

		return strb;
	}

	private def StringBuilder appendSpecDescriptions(StringBuilder strb, Iterable<Doclet> doclets) {
		for (doclet : doclets) {
			var bSpecFromDescr = doclet.hasLineTag(TAG_SPECFROMDESCR.title);
			val specTags = doclet.lineTags(TAG_SPEC.title);
			if (! specTags.empty || bSpecFromDescr) {
				strb.appendContents(doclet);
				for (tag : specTags) {
					strb.appendContents(tag.getValueByKey(DefaultLineTagDefinition.CONTENTS));
				}
			}
		}
		return strb;
	}

	private def StringBuilder appendContents(StringBuilder strb, Composite composite) {
		for (c : composite.contents) {
			strb.append(processContent(c));
		}
		if (!composite.contents.isEmpty) {
			strb.append("\n");
		}
		return strb;
	}


	private def dispatch CharSequence processContent(ContentNode node) {}
	private def dispatch CharSequence processContent(Text node) {
		return transformHTML(node.text);
	}
	private def dispatch CharSequence processContent(Literal node) {
		return transformHTML(node.value);
	}
	private def dispatch CharSequence processContent(SimpleTypeReference node) {
		return passThenMonospace(transformHTML(node.typeName));
	}
	private def dispatch CharSequence processContent(VariableReference node) {
		return passThenMonospace(transformHTML(node.variableName));
	}
	private def dispatch CharSequence processContent(InlineTag node) {
		switch (node.title.title) {
			case TAG_CODE.title: passThenMonospace(transformHTML(TAG_CODE.getValue(node, "")))
			case TAG_LINK.title: passThenMonospace(transformHTML(TAG_LINK.getValue(node, "")))
			default: {
				val StringBuilder strb = new StringBuilder();
				node.values.forEach[strb.appendContents(it)];
				return strb;
			}
		}
	}


	private def StringBuilder appendSpecElementPre(StringBuilder strb, SpecIdentifiableElementSection spec) {
		return strb.appendElementCodePre(spec.identifiableElement, spec);
	}


	/**
	 * E.g. classes
	 */
	private def dispatch StringBuilder appendElementCodePre(StringBuilder strb, IdentifiableElement element, SpecIdentifiableElementSection spec) {
		if (hasTodo(spec.doclet))
			strb.append(getTodoLink(spec.doclet));
		return strb;
	}
	private def dispatch StringBuilder appendElementCodePre(StringBuilder strb, TMember element, SpecIdentifiableElementSection spec) {
		return strb.appendMemberOrVarOrFuncPre(
			validatorMessageHelper.shortDescription(element),
			validatorMessageHelper.shortQualifiedName(element),
			element.memberAsString,
			element,
			spec
		);
	}
	private def dispatch StringBuilder appendElementCodePre(StringBuilder strb, TMethod element, SpecIdentifiableElementSection spec) {
		return strb.appendMemberOrVarOrFuncPre(
			validatorMessageHelper.shortDescription(element as TMember),
			validatorMessageHelper.shortQualifiedName(element as TMember),
			element.memberAsString,
			element,
			spec
		);
	}
	private def dispatch StringBuilder appendElementCodePre(StringBuilder strb, TFunction element, SpecIdentifiableElementSection spec) {
		return strb.appendMemberOrVarOrFuncPre(
			validatorMessageHelper.shortDescription(element),
			validatorMessageHelper.shortQualifiedName(element),
			element.name,
			element,
			spec
		);
	}
	private def dispatch StringBuilder appendElementCodePre(StringBuilder strb, TVariable element, SpecIdentifiableElementSection spec) {
		return strb.appendMemberOrVarOrFuncPre(
			validatorMessageHelper.shortDescription(element),
			validatorMessageHelper.shortQualifiedName(element),
			element.name,
			element,
			spec
		);
	}


	private def StringBuilder appendMemberOrVarOrFuncPre(StringBuilder strb, String shortDescr, String shortQN,
			String reqName, SyntaxRelatedTElement element, SpecIdentifiableElementSection spec) {

		val boolean isIntegratedFromPolyfill = spec.sourceEntry.trueFolder != spec.sourceEntry.folder;
		val String trueSrcFolder = spec.sourceEntry.repository + ":" + spec.sourceEntry.trueFolder;

		strb.append(
		'''

		[[gsec:spec_«spec.sourceEntry.adocCompatibleAnchorID»]]
		[role=memberdoc]
		=== «pass(shortDescr.toFirstUpper)»
		«IF hasTodo(spec.doclet)»
		«getTodoLink(spec.doclet)»
		«ENDIF»
		«IF isIntegratedFromPolyfill»

		[.small]#(Integrated from static polyfill aware class in: «trueSrcFolder»)#
		«ENDIF»

		==== Signature

		«codeLink(element)»

		''');
		return strb;
	}


	private def dispatch CharSequence codeLink(TMember member) {
		return doCodeLink(member, fullSignature(member));
	}
	private def dispatch CharSequence codeLink(TMethod method) {
		return doCodeLink(method, fullSignature(method));
	}
	private def dispatch CharSequence codeLink(TFunction func) {
		return doCodeLink(func, fullSignature(func));
	}
	private def dispatch CharSequence codeLink(TVariable tvar) {
		return doCodeLink(tvar, fullSignature(tvar));
	}


	private def fullSignature(TMember member) {
		val StringBuilder strb = new StringBuilder();
		for (a: member.annotations.filter[it.name!=AnnotationDefinition.INTERNAL.name]) {
			strb.append(a.annotationAsString + " ");
		}
		strb.append(keywordProvider.keyword(member.memberAccessModifier) + " ");
		if (member.abstract) {
			strb.append("@abstract ");
		}
		strb.append(member.memberAsString);

		return strb.toString;
	}
	private def fullSignature(TMethod method) {
		return validatorMessageHelper.fullFunctionSignature(method);
	}
	private def fullSignature(TFunction func) {
		return validatorMessageHelper.fullFunctionSignature(func);
	}
	private def fullSignature(TVariable tvar) {
		if (tvar.typeRef===null) {
			return tvar.name;
		}
		return '''«tvar.name»: «tvar.typeRef.typeRefAsString»''';
	}


	private def CharSequence doCodeLink(IdentifiableElement element, String signature) {
		val RepoRelativePath rrp = repoPathHolder.get(element);
		val strb = new StringBuilder();

		if (rrp !== null) {
			val SourceEntry se = SourceEntryFactory.create(repoPathHolder, rrp, element);
			strb.appendSourceLink(se, passThenMonospace(signature));
		}

		return strb.toString();
	}


	private def StringBuilder appendSpecElementPost(StringBuilder strb, SpecIdentifiableElementSection spec, Map<String, SpecSection> map) {
		return strb.appendElementPost(spec.identifiableElement, spec, map);
	}

	private def dispatch StringBuilder appendElementPost(StringBuilder strb,
		IdentifiableElement element, SpecIdentifiableElementSection specRegion, Map<String, SpecSection> specsByKey) {

		if (element instanceof ContainerType<?>) {
			var Map<TMember, SortedSet<SpecTestInfo>> testsForInherited = specRegion.getTestInfosForInheritedMember
			if (testsForInherited === null || testsForInherited.empty) {
				return strb;
			}

			val typeName = element.name;
			val superTypes = AllSuperTypesCollector.collect(
				element, declMergingHelper
			)

			val tests = testsForInherited.entrySet.filter[value!==null && !value.empty];
			val sortedTests = tests.sortBy[getFormattedID(it, superTypes)];
			for (tmemberSpecs : sortedTests) {
				val shortDescr = validatorMessageHelper.shortDescription(tmemberSpecs.key);
				val secSpecLink = typeName + "." + validatorMessageHelper.shortQualifiedName(tmemberSpecs.key);
				val secSpecLinkEsc = SourceEntry.getEscapedAdocAnchorString(secSpecLink);
				val description = validatorMessageHelper.description(tmemberSpecs.key.containingType);
				val shortQualName = validatorMessageHelper.shortQualifiedName(tmemberSpecs.key);

				strb.append(
				'''

				[[gsec:spec_«secSpecLinkEsc»]]
				[role=memberdoc]
				=== «pass(shortDescr.toFirstUpper)»

				Inherited from
				«pass(description)»
				«IF isInSpec(tmemberSpecs.key, specsByKey)»
					<<gsec:spec_«pass(shortQualName)»>>
				«ENDIF»

				''');

				strb.appendConstraints(tmemberSpecs.key, specRegion, tmemberSpecs.value, false);
			}
		}
		return strb
	}


	private def boolean isInSpec(TMember member, Map<String, SpecSection> specsByKey) {
		if (member === null) {
			return false;
		}
		return specsByKey.containsKey(KeyUtils.getSpecKey(repoPathHolder, member));
	}

	private def String getFormattedID(Entry<TMember, SortedSet<SpecTestInfo>> entry, List<TClassifier> superTypes) {
		val index = superTypes.indexOf(entry.key.containingType);
		return String.format("%04d", index) + entry.key.name
	}


	private def dispatch StringBuilder appendElementPost(StringBuilder strb, TMember element, SpecIdentifiableElementSection specRegion, Map<String, SpecSection> specsByKey) {
		strb.appendConstraints(element, specRegion, specRegion.getTestInfosForMember, ! hasReqId(specRegion.getDoclet));
		return strb;
	}
	private def dispatch StringBuilder appendElementPost(StringBuilder strb, TMethod element, SpecIdentifiableElementSection specRegion, Map<String, SpecSection> specsByKey) {
		strb.appendConstraints(element, specRegion, specRegion.getTestInfosForMember, ! hasReqId(specRegion.getDoclet));
		return strb;
	}
	private def dispatch StringBuilder appendElementPost(StringBuilder strb, TFunction element, SpecIdentifiableElementSection specRegion, Map<String, SpecSection> specsByKey) {

		if (! specRegion.getTestInfosForType.isNullOrEmpty) {
			val Map<String, List<SpecTestInfo>> groupdTests = specRegion.getTestInfosForType.groupBy[testTitle];
			strb.append("==== Semantics\n");
			strb.appendApiConstraints(groupdTests);
		} else {

			val reqID = getReqId(specRegion.getDoclet);
			if (reqID.isEmpty) {
				val String todoLink = getTodoLink(
						"Add tests specifying semantics for " + passThenMonospace(element.name),
						"test function " + pass(element.name));

				strb.append(
				'''

				==== Semantics
				«todoLink»
				''');
			} else {
				strb.append('''% tests see «reqID»''');
			}
		}

		return strb;
	}
	private def dispatch StringBuilder appendElementPost(StringBuilder strb, TVariable element, SpecSection specRegion, Map<String, SpecSection> specsByKey) {
		if (! specRegion.getTestInfosForType.isNullOrEmpty) {
			val Map<String, List<SpecTestInfo>> groupdTests = specRegion.getTestInfosForType.groupBy[testTitle];
			strb.append("==== Semantics\n");
			strb.appendApiConstraints(groupdTests)
		}
		return strb; // test are optional for variables.
	}


	private def StringBuilder appendConstraints(StringBuilder strb, TMember element, SpecIdentifiableElementSection specRegion, Set<SpecTestInfo> specTestInfos, boolean addTodo) {
		if (! specTestInfos.isNullOrEmpty) {
			val Map<String, List<SpecTestInfo>> groupdTests = specTestInfos.groupBy[testTitle];
			strb.append("==== Semantics\n");
			strb.appendApiConstraints(groupdTests);

		} else if (addTodo) {

			if (elementMayNeedsTest(element, specRegion)) {
				val String todoLink = getTodoLink(
					"Add tests specifying semantics for " + passThenMonospace(element.memberAsString),
					"test " + pass(element.containingType.name + "." + element.name));

				strb.append(
				'''

				==== Semantics
				«todoLink»
				''');
			}
		}
		return strb
	}

	private def boolean elementMayNeedsTest(TMember element, SpecIdentifiableElementSection spec) {
		// there are tests, so we show them
		if (! spec.getTestInfosForType.isNullOrEmpty) {
			return true;
		}
		if ((element instanceof TMethod) || (element instanceof FieldAccessor)) {
			if (element.containingType instanceof TInterface) {
				if (element instanceof TMemberWithAccessModifier) {
					return ! element.hasNoBody
				}
			}
			return !element.isAbstract;
		}
		return false;
	}

	/**
	 * List of tests in apiConstraint macros.
	 */
	private def <T> StringBuilder appendApiConstraints(StringBuilder strb, Map<T, ? extends Collection<SpecTestInfo>> groupdTests) {
		for (group : groupdTests.entrySet.sortBy[it.key.toString]) {
			strb.append("\n");
			strb.append(". *");
			val key = group.key.toString;
			val keyWithoutPrecedingNumber = removePrecedingNumber(key);
			strb.append(pass(keyWithoutPrecedingNumber));
			strb.append("* (");
			val iter = group.value.iterator;
			while (iter.hasNext) {
				val SpecTestInfo testSpec = iter.next;
				strb.append(nfgitTest(testSpec));
				if (iter.hasNext) {
					strb.append(", \n");
				}
			}
			strb.append(")\n");
			val Iterable<Doclet> doclets = group.value.filter[doclet !== null].map[doclet];
			val strbTmp = new StringBuilder();
			strbTmp.appendSpecDescriptions(doclets);
			if (strbTmp.length > 0) {
				strb.append("+\n");
				strb.append("[.generatedApiConstraint]\n");
				strb.append("====\n\n");
				strb.append(strbTmp);
				strb.append("\n====\n");
			}
		}
		return strb
	}

	private def CharSequence nfgitTest(SpecTestInfo testInfo) {
		val strb = new StringBuilder();
		if (testInfo.rrp === null) {
			strb.append(small(testInfo.testModuleSpec() + "."));
			strb.append(testInfo.testMethodTypeName() + "." + testInfo.testMethodName());
		} else {
			val pc = SourceEntryFactory.create(testInfo);
			val strCase = if (testInfo.testCase.nullOrEmpty) "Test" else {
				var formattedCase = removePrecedingNumber(testInfo.testCase);
				if (formattedCase.nullOrEmpty) {
					formattedCase = testInfo.testCase;
				} 
				pass(formattedCase);
			}
			val strbTmp = new StringBuilder();
			strbTmp.appendSourceLink(pc, strCase);
			strb.append(small(strbTmp));
		}
		return strb.toString();
	}

	/**
	 * Reminder: Escaping the caption using the method {@link Html2ADocConverter#pass} is recommended.
	 */
	private def StringBuilder appendSourceLink(StringBuilder strb, SourceEntry pc, String caption) {
		strb.append(
		'''srclnk:++«
			pc.toPQN
			»++[«
			caption
			»]''');
		return strb;
	}

	/**
	 * Returns req id, may be an empty string but never null.
	 */
	private def String getReqId(Doclet doclet) {
		return TAG_REQID.getValue(doclet, "");
	}

	/**
	 * Returns true, if spec contains a reference to a requirement id.
	 */
	private def boolean hasReqId(Doclet doclet) {
		return ! getReqId(doclet).isEmpty;
	}

	/**
	 * Returns true, if spec contains a reference to a todo.
	 */
	private def boolean hasTodo(Doclet doclet) {
		return ! getTodo(doclet).isEmpty;
	}

	/**
	 * Returns todo, may be an empty string but never null.
	 */
	private def String getTodo(Doclet doclet) {
		return TAG_TODO.getValue(doclet, "");
	}

	private def String getTodoLink(String todoText, String sideText) {
		val todo =
			'''

			[TODO«
			IF !sideText.isNullOrEmpty
			», title="«sideText»"«
			ENDIF
			»]
			--
			«todoText»
			--

			'''
		return todo;
	}

	private def String getTodoLink(Doclet doclet) {
		return getTodoLink(getTodo(doclet), "");
	}

	private def StringBuilder appendTaskLink(StringBuilder strb, String taskID) {
		strb.append('''task:«taskID»[]''');
		return strb;
	}

	private def String small(CharSequence smallString) {
		return '''[.small]#«smallString»#''';
	}

	private def String removePrecedingNumber(String key) {
		for (var i=0; i<key.length; i++) {
			val stringAt = Character.toString(key.charAt(i));
			if (!"0123456789 ".contains(stringAt)) {
				return key.substring(i);
			}
		}
		return "";
	}

}
