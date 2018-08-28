package org.eclipse.n4js.ui.proposals.packagejson;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.json.ui.contentassist.AbstractJSONProposalProvider;
import org.eclipse.n4js.json.ui.contentassist.NameValuePairProposalFactory;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.utils.languages.N4LanguageUtils;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

/**
 * A factory for creating {@link ICompletionProposal}s that insert new name-value-pairs into a JSON Document.
 */
public class PackageJsonProposalProvider extends AbstractJSONProposalProvider {

	@Override
	public void complete_NameValuePair(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {

		NameValuePairProposalFactory nvpFactory = N4LanguageUtils
				.getServiceForContext(JSONModelUtils.FILE_EXTENSION, NameValuePairProposalFactory.class).get();

		List<String> namePath = getJsonPathNames(model);
		Set<String> alreadyUsedNames = getAlreadyUsedNames(model);

		List<PackageJsonProperties> pathProps = PackageJsonProperties.valuesOfPath(namePath);
		for (PackageJsonProperties pathProp : pathProps) {
			String name = pathProp.name;
			if (alreadyUsedNames.contains(name)) {
				continue;
			}
			String value = "\"\"";
			if (pathProp.valueType == JSONArray.class) {
				value = "[]";
			}
			if (pathProp.valueType == JSONObject.class) {
				value = "{}";
			}
			String descr = pathProp.description;
			ICompletionProposal pairProposal = nvpFactory.createNameValuePairProposal(context, name, value, descr);
			acceptor.accept(pairProposal);
		}
	}

	private List<String> getJsonPathNames(EObject model) {
		List<String> namePath = new LinkedList<>();
		EObject eobj = model;
		while (!(eobj instanceof JSONDocument)) {

			if (eobj instanceof NameValuePair) {
				NameValuePair nvp = (NameValuePair) eobj;
				namePath.add(0, nvp.getName());
			}

			eobj = eobj.eContainer();
		}
		return namePath;
	}

	private Set<String> getAlreadyUsedNames(EObject model) {
		Set<String> alreadyUsedNames = new HashSet<>();
		if (model instanceof JSONObject) {
			JSONObject jsonObj = (JSONObject) model;
			for (NameValuePair nvp : jsonObj.getNameValuePairs()) {
				alreadyUsedNames.add(nvp.getName());
			}
		}
		return alreadyUsedNames;
	}

}
