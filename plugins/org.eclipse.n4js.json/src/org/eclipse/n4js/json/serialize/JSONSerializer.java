package org.eclipse.n4js.json.serialize;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.impl.Serializer;
import org.eclipse.xtext.util.ReplaceRegion;

/**
 * Custom JSON-serializer which has custom behavior when it comes to partially serializing model elements and
 * replacements (cf. {@link JSONSerializer#serializeReplacement(EObject, SaveOptions)}.
 */
@SuppressWarnings("restriction")
public class JSONSerializer extends Serializer {
	@Override
	public ReplaceRegion serializeReplacement(EObject obj, SaveOptions options) {
		EObject toReplace = obj;

		// In case of objects or array, make sure to widen the replacement scope to the whole document
		// (partial replacement is currently not supported, since the formatter cannot handle it).
		// The actual formatting may be limited by {@link SaveOptions#isFormatting} and thus the formatter
		// may actually only be executed on edited elements (those w/o node model).
		if (toReplace instanceof JSONObject || toReplace instanceof JSONArray) {
			toReplace = toReplace.eResource().getContents().get(0);
		}

		return super.serializeReplacement(toReplace, options);
	}
}
