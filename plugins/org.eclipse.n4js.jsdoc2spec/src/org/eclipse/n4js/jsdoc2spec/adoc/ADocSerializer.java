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

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.eclipse.n4js.jsdoc.N4JSDocletParser.TAG_CODE;
import static org.eclipse.n4js.jsdoc.N4JSDocletParser.TAG_LINK;
import static org.eclipse.n4js.jsdoc.N4JSDocletParser.TAG_REQID;
import static org.eclipse.n4js.jsdoc.N4JSDocletParser.TAG_SPEC;
import static org.eclipse.n4js.jsdoc.N4JSDocletParser.TAG_SPECFROMDESCR;
import static org.eclipse.n4js.jsdoc.N4JSDocletParser.TAG_TASK;
import static org.eclipse.n4js.jsdoc.N4JSDocletParser.TAG_TODO;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.groupBy;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isNullOrEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.sortBy;
import static org.eclipse.xtext.xbase.lib.StringExtensions.toFirstUpper;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.jsdoc.dom.Composite;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.InlineTag;
import org.eclipse.n4js.jsdoc.dom.LineTag;
import org.eclipse.n4js.jsdoc.dom.Literal;
import org.eclipse.n4js.jsdoc.dom.SimpleTypeReference;
import org.eclipse.n4js.jsdoc.dom.TagValue;
import org.eclipse.n4js.jsdoc.dom.Text;
import org.eclipse.n4js.jsdoc.dom.VariableReference;
import org.eclipse.n4js.jsdoc.tags.DefaultLineTagDefinition;
import org.eclipse.n4js.jsdoc2spec.KeyUtils;
import org.eclipse.n4js.jsdoc2spec.RepoRelativePath;
import org.eclipse.n4js.jsdoc2spec.SpecTestInfo;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMemberWithAccessModifier;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.typesystem.utils.AllSuperTypesCollector;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.N4JSElementKeywordProvider;
import org.eclipse.n4js.validation.ValidatorMessageHelper;

import com.google.inject.Inject;

/**
 * Print AsciiDoc code of specification JSDoc. Start and end markers are printed by client.
 *
 * Needs to be injected.
 */
class ADocSerializer {
	@Inject
	Html2ADocConverter html2aDocConverter;
	@Inject
	ValidatorMessageHelper validatorMessageHelper;
	@Inject
	N4JSElementKeywordProvider keywordProvider;
	@Inject
	RepoRelativePathHolder repoPathHolder;
	@Inject
	DeclMergingHelper declMergingHelper;

	String process(SpecRequirementSection spec) {
		StringBuilder strb = new StringBuilder();
		appendSpecElementPost(strb, spec);
		return Strings.stripAllTrailing(strb.toString());
	}

	String process(SpecIdentifiableElementSection spec, Map<String, SpecSection> specsByKey) {
		StringBuilder strb = new StringBuilder();
		appendSpecElementPre(strb, spec);
		appendSpec(strb, spec);
		appendSpecElementPost(strb, spec, specsByKey);
		return Strings.stripAllTrailing(strb.toString());
	}

	private StringBuilder appendSpecElementPost(StringBuilder strb, SpecRequirementSection spec) {
		if (!isNullOrEmpty(spec.getTestInfosForType())) {
			Map<String, List<SpecTestInfo>> groupdTests = groupBy(spec.getTestInfosForType(), sti -> sti.testTitle);
			appendApiConstraints(strb, groupdTests);
		}
		return strb;
	}

	private StringBuilder appendSpec(StringBuilder strb, SpecIdentifiableElementSection spec) {
		strb.append("\n");

		boolean addedTaskLinks = false;
		List<LineTag> taskTags = spec.getDoclet().lineTags(TAG_TASK.getTitle());
		for (LineTag tag : taskTags) {
			String taskID = TAG_TASK.getValue(tag, "");
			if (!taskID.isEmpty()) {
				if (taskID.startsWith("*")) {
					appendTaskLink(strb, taskID.substring(1));
				} else {
					appendTaskLink(strb, taskID);
				}
				strb.append(" ");
				addedTaskLinks = true;
			}
		}
		if (addedTaskLinks)
			strb.append("\n\n");

		appendSpecDescription(strb, spec);
		return strb;
	}

	private StringBuilder appendSpecDescription(StringBuilder strb, SpecIdentifiableElementSection spec) {
		Doclet doclet = spec.getDoclet();
		boolean bSpecFromDescr = doclet.hasLineTag(TAG_SPECFROMDESCR.getTitle());
		String reqID = getReqId(doclet);
		List<LineTag> specTags = doclet.lineTags(TAG_SPEC.getTitle());

		if (specTags.isEmpty() && !bSpecFromDescr && reqID.isEmpty()) {
			return strb;
		}

		if (!(spec.idElement instanceof TN4Classifier || spec.idElement instanceof TEnum))
			strb.append("==== Description\n\n");

		if (!reqID.isEmpty()) {
			strb.append("See req:" + reqID + "[].\n");
		}

		appendContents(strb, doclet);
		for (LineTag tag : specTags) {
			appendContents(strb, tag.getValueByKey(DefaultLineTagDefinition.CONTENTS));
		}

		strb.append("\n");

		return strb;
	}

	private StringBuilder appendSpecDescriptions(StringBuilder strb, Iterable<Doclet> doclets) {
		for (Doclet doclet : doclets) {
			boolean bSpecFromDescr = doclet.hasLineTag(TAG_SPECFROMDESCR.getTitle());
			List<LineTag> specTags = doclet.lineTags(TAG_SPEC.getTitle());
			if (!specTags.isEmpty() || bSpecFromDescr) {
				appendContents(strb, doclet);
				for (LineTag tag : specTags) {
					appendContents(strb, tag.getValueByKey(DefaultLineTagDefinition.CONTENTS));
				}
			}
		}
		return strb;
	}

	private StringBuilder appendContents(StringBuilder strb, Composite composite) {
		boolean contentAdded = false;
		for (EObject c : composite.eContents()) {
			String newContent = processContent(c).toString();
			strb.append(newContent);
			contentAdded |= !newContent.isBlank();
		}
		if (contentAdded) {
			strb.append("\n");
		}
		return strb;
	}

	private CharSequence processContent(EObject node) {
		if (node instanceof Text) {
			return processContent((Text) node);
		}
		if (node instanceof Literal) {
			return processContent((Literal) node);
		}
		if (node instanceof SimpleTypeReference) {
			return processContent((SimpleTypeReference) node);
		}
		if (node instanceof VariableReference) {
			return processContent((VariableReference) node);
		}
		if (node instanceof InlineTag) {
			return processContent((InlineTag) node);
		}

		return "";
	}

	private CharSequence processContent(Text node) {
		return html2aDocConverter.transformHTML(node.getText());
	}

	private CharSequence processContent(Literal node) {
		return html2aDocConverter.transformHTML(node.getValue());
	}

	private CharSequence processContent(SimpleTypeReference node) {
		return html2aDocConverter.passThenMonospace(html2aDocConverter.transformHTML(node.getTypeName()));
	}

	private CharSequence processContent(VariableReference node) {
		return html2aDocConverter.passThenMonospace(html2aDocConverter.transformHTML(node.getVariableName()));
	}

	private CharSequence processContent(InlineTag node) {
		if (Objects.equals(TAG_CODE.getTitle(), node.getTitle().getTitle())) {
			return html2aDocConverter.passThenMonospace(html2aDocConverter.transformHTML(TAG_CODE.getValue(node, "")));
		}
		if (Objects.equals(TAG_LINK.getTitle(), node.getTitle().getTitle())) {
			return html2aDocConverter.passThenMonospace(html2aDocConverter.transformHTML(TAG_LINK.getValue(node, "")));
		}

		StringBuilder strb = new StringBuilder();
		for (TagValue tv : node.getValues()) {
			appendContents(strb, tv);
		}
		return strb;
	}

	private StringBuilder appendSpecElementPre(StringBuilder strb, SpecIdentifiableElementSection spec) {
		IdentifiableElement element = spec.getIdentifiableElement();
		if (element instanceof TMember) {
			return appendElementCodePre(strb, (TMember) element, spec);
		}
		if (element instanceof TMethod) {
			return appendElementCodePre(strb, (TMethod) element, spec);
		}
		if (element instanceof TFunction) {
			return appendElementCodePre(strb, (TFunction) element, spec);
		}
		if (element instanceof TVariable) {
			return appendElementCodePre(strb, (TVariable) element, spec);
		}
		return appendElementCodePre(strb, element, spec);
	}

	/**
	 * E.g. classes
	 */
	private StringBuilder appendElementCodePre(StringBuilder strb,
			@SuppressWarnings("unused") IdentifiableElement element, SpecIdentifiableElementSection spec) {

		if (hasTodo(spec.getDoclet())) {
			strb.append(getTodoLink(spec.getDoclet()));
		}
		return strb;
	}

	private StringBuilder appendElementCodePre(StringBuilder strb, TMember element,
			SpecIdentifiableElementSection spec) {
		return appendMemberOrVarOrFuncPre(strb,
				validatorMessageHelper.shortDescription(element),
				element,
				spec);
	}

	private StringBuilder appendElementCodePre(StringBuilder strb, TMethod element,
			SpecIdentifiableElementSection spec) {
		return appendMemberOrVarOrFuncPre(strb,
				validatorMessageHelper.shortDescription((TMember) element),
				element,
				spec);
	}

	private StringBuilder appendElementCodePre(StringBuilder strb, TFunction element,
			SpecIdentifiableElementSection spec) {
		return appendMemberOrVarOrFuncPre(strb,
				validatorMessageHelper.shortDescription(element),
				element,
				spec);
	}

	private StringBuilder appendElementCodePre(StringBuilder strb, TVariable element,
			SpecIdentifiableElementSection spec) {
		return appendMemberOrVarOrFuncPre(strb,
				validatorMessageHelper.shortDescription(element),
				element,
				spec);
	}

	private StringBuilder appendMemberOrVarOrFuncPre(StringBuilder strb, String shortDescr,
			SyntaxRelatedTElement element, SpecIdentifiableElementSection spec) {

		boolean isIntegratedFromPolyfill = !Objects.equals(spec.sourceEntry.trueFolder, spec.sourceEntry.folder);
		String trueSrcFolder = spec.sourceEntry.repository + ":" + spec.sourceEntry.trueFolder;
		String todoLink = hasTodo(spec.getDoclet()) ? "\n" + getTodoLink(spec.getDoclet()) : "";
		String polyfill = isIntegratedFromPolyfill
				? "\n\n[.small]#(Integrated from static polyfill aware class in: %s)#".formatted(trueSrcFolder)
				: "";

		strb.append("""

				[[gsec:spec_%s]]
				[role=memberdoc]
				=== %s%s%s

				==== Signature

				%s

				""".formatted(
				spec.sourceEntry.getAdocCompatibleAnchorID(),
				html2aDocConverter.pass(toFirstUpper(shortDescr)),
				todoLink,
				polyfill,
				codeLink(element)));
		return strb;
	}

	private CharSequence codeLink(EObject element) {
		if (element instanceof TVariable) {
			return codeLink((TVariable) element);
		}
		if (element instanceof TMethod) {
			return codeLink((TMethod) element);
		}
		if (element instanceof TFunction) {
			return codeLink((TFunction) element);
		}
		if (element instanceof TMember) {
			return codeLink((TMember) element);
		}

		throw new IllegalArgumentException();
	}

	private CharSequence codeLink(TMember member) {
		return doCodeLink(member, fullSignature(member));
	}

	private CharSequence codeLink(TMethod method) {
		return doCodeLink(method, fullSignature(method));
	}

	private CharSequence codeLink(TFunction func) {
		return doCodeLink(func, fullSignature(func));
	}

	private CharSequence codeLink(TVariable tvar) {
		return doCodeLink(tvar, fullSignature(tvar));
	}

	private String fullSignature(TMember member) {
		StringBuilder strb = new StringBuilder();
		for (TAnnotation a : filter(member.getAnnotations(),
				ann -> !AnnotationDefinition.INTERNAL.name.equals(ann.getName()))) {

			strb.append(a.getAnnotationAsString() + " ");
		}
		strb.append(keywordProvider.keyword(member.getMemberAccessModifier()) + " ");
		if (member.isAbstract()) {
			strb.append("@abstract ");
		}
		strb.append(member.getMemberAsString());

		return strb.toString();
	}

	private String fullSignature(TMethod method) {
		return validatorMessageHelper.fullFunctionSignature(method);
	}

	private String fullSignature(TFunction func) {
		return validatorMessageHelper.fullFunctionSignature(func);
	}

	private String fullSignature(TVariable tvar) {
		if (tvar.getTypeRef() == null) {
			return tvar.getName();
		}
		return tvar.getName() + ": " + tvar.getTypeRef().getTypeRefAsString();
	}

	private CharSequence doCodeLink(IdentifiableElement element, String signature) {
		RepoRelativePath rrp = repoPathHolder.get(element);
		StringBuilder strb = new StringBuilder();

		if (rrp != null) {
			SourceEntry se = SourceEntryFactory.create(repoPathHolder, rrp, element);
			appendSourceLink(strb, se, html2aDocConverter.passThenMonospace(signature));
		}

		return strb.toString();
	}

	private boolean isInSpec(TMember member, Map<String, SpecSection> specsByKey) {
		if (member == null) {
			return false;
		}
		return specsByKey.containsKey(KeyUtils.getSpecKey(repoPathHolder, member));
	}

	private String getFormattedID(Entry<TMember, SortedSet<SpecTestInfo>> entry, List<TClassifier> superTypes) {
		int index = superTypes.indexOf(entry.getKey().getContainingType());
		return String.format("%04d", index) + entry.getKey().getName();
	}

	private StringBuilder appendSpecElementPost(StringBuilder strb, SpecIdentifiableElementSection specRegion,
			Map<String, SpecSection> specsByKey) {

		IdentifiableElement element = specRegion.getIdentifiableElement();
		if (element instanceof TMember) {
			return appendElementPost(strb, (TMember) element, specRegion, specsByKey);
		}
		if (element instanceof TMethod) {
			return appendElementPost(strb, (TMethod) element, specRegion, specsByKey);
		}
		if (element instanceof TFunction) {
			return appendElementPost(strb, (TFunction) element, specRegion, specsByKey);
		}
		if (element instanceof TVariable) {
			return appendElementPost(strb, (TVariable) element, specRegion, specsByKey);
		}
		return appendElementPost(strb, element, specRegion, specsByKey);
	}

	private StringBuilder appendElementPost(StringBuilder strb,
			IdentifiableElement element, SpecIdentifiableElementSection specRegion,
			Map<String, SpecSection> specsByKey) {

		if (element instanceof ContainerType<?>) {
			Map<TMember, SortedSet<SpecTestInfo>> testsForInherited = specRegion.getTestInfosForInheritedMember();
			if (testsForInherited == null || testsForInherited.isEmpty()) {
				return strb;
			}

			String typeName = element.getName();
			List<TClassifier> superTypes = AllSuperTypesCollector.collect((ContainerType<?>) element,
					declMergingHelper);

			Iterable<Entry<TMember, SortedSet<SpecTestInfo>>> tests = filter(testsForInherited.entrySet(),
					e -> e.getValue() != null && !e.getValue().isEmpty());
			Iterable<Entry<TMember, SortedSet<SpecTestInfo>>> sortedTests = sortBy(tests,
					e -> getFormattedID(e, superTypes));

			for (Entry<TMember, SortedSet<SpecTestInfo>> tmemberSpecs : sortedTests) {
				String shortDescr = validatorMessageHelper.shortDescription(tmemberSpecs.getKey());
				String secSpecLink = typeName + "." + validatorMessageHelper.shortQualifiedName(tmemberSpecs.getKey());
				String secSpecLinkEsc = SourceEntry.getEscapedAdocAnchorString(secSpecLink);
				String description = validatorMessageHelper.description(tmemberSpecs.getKey().getContainingType());
				String shortQualName = validatorMessageHelper.shortQualifiedName(tmemberSpecs.getKey());
				String gsecSpec = isInSpec(tmemberSpecs.getKey(), specsByKey)
						? "\n\t<<gsec:spec_%s>>\n".formatted(html2aDocConverter.pass(shortQualName))
						: "";

				strb.append("""

						[[gsec:spec_%s]]
						[role=memberdoc]
						=== %s

						Inherited from
						%s%s

						""".formatted(
						secSpecLinkEsc,
						html2aDocConverter.pass(toFirstUpper(shortDescr)),
						html2aDocConverter.pass(description),
						gsecSpec));

				appendConstraints(strb, tmemberSpecs.getKey(), specRegion, tmemberSpecs.getValue(), false);
			}

		}
		return strb;

	}

	private StringBuilder appendElementPost(StringBuilder strb, TMember element,
			SpecIdentifiableElementSection specRegion,
			@SuppressWarnings("unused") Map<String, SpecSection> specsByKey) {

		appendConstraints(strb, element, specRegion, specRegion.getTestInfosForMember(),
				!hasReqId(specRegion.getDoclet()));
		return strb;
	}

	private StringBuilder appendElementPost(StringBuilder strb, TMethod element,
			SpecIdentifiableElementSection specRegion,
			@SuppressWarnings("unused") Map<String, SpecSection> specsByKey) {

		appendConstraints(strb, element, specRegion, specRegion.getTestInfosForMember(),
				!hasReqId(specRegion.getDoclet()));
		return strb;
	}

	private StringBuilder appendElementPost(StringBuilder strb, TFunction element,
			SpecIdentifiableElementSection specRegion,
			@SuppressWarnings("unused") Map<String, SpecSection> specsByKey) {

		if (!isNullOrEmpty(specRegion.getTestInfosForType())) {
			Map<String, List<SpecTestInfo>> groupdTests = groupBy(specRegion.getTestInfosForType(),
					sti -> sti.testTitle);

			strb.append("==== Semantics\n");
			appendApiConstraints(strb, groupdTests);
		} else {

			String reqID = getReqId(specRegion.getDoclet());
			if (reqID.isEmpty()) {
				String todoLink = getTodoLink(
						"Add tests specifying semantics for " + html2aDocConverter.passThenMonospace(element.getName()),
						"test function " + html2aDocConverter.pass(element.getName()));

				strb.append("""

						==== Semantics
						%s
						""".formatted(todoLink));
			} else {
				strb.append("% tests see " + reqID);
			}
		}

		return strb;
	}

	private StringBuilder appendElementPost(StringBuilder strb, @SuppressWarnings("unused") TVariable element,
			SpecIdentifiableElementSection specRegion,
			@SuppressWarnings("unused") Map<String, SpecSection> specsByKey) {

		if (!isNullOrEmpty(specRegion.getTestInfosForType())) {
			Map<String, List<SpecTestInfo>> groupdTests = groupBy(specRegion.getTestInfosForType(),
					sti -> sti.testTitle);

			strb.append("==== Semantics\n");
			appendApiConstraints(strb, groupdTests);
		}
		return strb; // test are optional for variables.
	}

	private StringBuilder appendConstraints(StringBuilder strb, TMember element,
			SpecIdentifiableElementSection specRegion, Set<SpecTestInfo> specTestInfos, boolean addTodo) {

		if (!isNullOrEmpty(specTestInfos)) {
			Map<String, List<SpecTestInfo>> groupdTests = groupBy(specTestInfos, sti -> sti.testTitle);
			strb.append("==== Semantics\n");
			appendApiConstraints(strb, groupdTests);

		} else if (addTodo) {

			if (elementMayNeedsTest(element, specRegion)) {
				String todoLink = getTodoLink(
						"Add tests specifying semantics for "
								+ html2aDocConverter.passThenMonospace(element.getMemberAsString()),
						"test " + html2aDocConverter
								.pass(element.getContainingType().getName() + "." + element.getName()));

				strb.append("""

						==== Semantics
						%s
						""".formatted(todoLink));
			}
		}
		return strb;
	}

	private boolean elementMayNeedsTest(TMember element, SpecIdentifiableElementSection spec) {
		// there are tests, so we show them
		if (!isNullOrEmpty(spec.getTestInfosForType())) {
			return true;
		}
		if ((element instanceof TMethod) || (element instanceof FieldAccessor)) {
			if (element.getContainingType() instanceof TInterface) {
				if (element instanceof TMemberWithAccessModifier) {
					return !((TMemberWithAccessModifier) element).isHasNoBody();
				}
			}
			return !element.isAbstract();
		}
		return false;
	}

	/**
	 * List of tests in apiConstraint macros.
	 */
	private <T> StringBuilder appendApiConstraints(StringBuilder strb,
			Map<T, ? extends Collection<SpecTestInfo>> groupdTests) {
		for (Map.Entry<T, ? extends Collection<SpecTestInfo>> group : sortBy(groupdTests.entrySet(),
				e -> e.getKey().toString())) {
			strb.append("\n");
			strb.append(". *");
			String key = group.getKey().toString();
			String keyWithoutPrecedingNumber = removePrecedingNumber(key);
			strb.append(html2aDocConverter.pass(keyWithoutPrecedingNumber));
			strb.append("* (");
			Iterator<SpecTestInfo> iter = group.getValue().iterator();
			while (iter.hasNext()) {
				SpecTestInfo testSpec = iter.next();
				strb.append(nfgitTest(testSpec));
				if (iter.hasNext()) {
					strb.append(", \n");
				}
			}
			strb.append(")\n");
			Iterable<Doclet> doclets = map(filter(group.getValue(), spi -> spi.doclet != null), spi -> spi.doclet);
			StringBuilder strbTmp = new StringBuilder();
			appendSpecDescriptions(strbTmp, doclets);
			if (strbTmp.length() > 0) {
				strb.append("+\n");
				strb.append("[.generatedApiConstraint]\n");
				strb.append("====\n\n");
				strb.append(strbTmp);
				strb.append("\n====\n");
			}
		}
		return strb;
	}

	private CharSequence nfgitTest(SpecTestInfo testInfo) {
		StringBuilder strb = new StringBuilder();
		if (testInfo.rrp == null) {
			strb.append(small(testInfo.testModuleSpec() + "."));
			strb.append(testInfo.testMethodTypeName() + "." + testInfo.testMethodName());
		} else {
			SourceEntry pc = SourceEntryFactory.create(testInfo);
			String strCase = "Test";
			if (!isNullOrEmpty(testInfo.testCase)) {
				String formattedCase = removePrecedingNumber(testInfo.testCase);
				if (isNullOrEmpty(formattedCase)) {
					formattedCase = testInfo.testCase;
				}
				html2aDocConverter.pass(formattedCase);
			}
			StringBuilder strbTmp = new StringBuilder();
			appendSourceLink(strbTmp, pc, strCase);
			strb.append(small(strbTmp));
		}
		return strb.toString();
	}

	/**
	 * Reminder: Escaping the caption using the method {@link Html2ADocConverter#pass} is recommended.
	 */
	private StringBuilder appendSourceLink(StringBuilder strb, SourceEntry pc, String caption) {
		strb.append("srclnk:++" + pc.toPQN() + "++[" + caption + "]");
		return strb;
	}

	/**
	 * Returns req id, may be an empty string but never null.
	 */
	private String getReqId(Doclet doclet) {
		return TAG_REQID.getValue(doclet, "");
	}

	/**
	 * Returns true, if spec contains a reference to a requirement id.
	 */
	private boolean hasReqId(Doclet doclet) {
		return !getReqId(doclet).isEmpty();
	}

	/**
	 * Returns true, if spec contains a reference to a todo.
	 */
	private boolean hasTodo(Doclet doclet) {
		return !getTodo(doclet).isEmpty();
	}

	/**
	 * Returns todo, may be an empty string but never null.
	 */
	private String getTodo(Doclet doclet) {
		return TAG_TODO.getValue(doclet, "");
	}

	private String getTodoLink(String todoText, String sideText) {
		String str = isNullOrEmpty(sideText) ? "" : ", title=\"" + sideText + "\"";
		String todo = """

				[TODO%s]
				--
				%s
				--
				""".formatted(str, todoText);
		return todo;
	}

	private String getTodoLink(Doclet doclet) {
		return getTodoLink(getTodo(doclet), "");
	}

	private StringBuilder appendTaskLink(StringBuilder strb, String taskID) {
		strb.append("task:" + taskID + "[]");
		return strb;
	}

	private String small(CharSequence smallString) {
		return "[.small]#" + smallString + "#";
	}

	private String removePrecedingNumber(String key) {
		for (var i = 0; i < key.length(); i++) {
			String stringAt = Character.toString(key.charAt(i));
			if (!"0123456789 ".contains(stringAt)) {
				return key.substring(i);
			}
		}
		return "";
	}

}
