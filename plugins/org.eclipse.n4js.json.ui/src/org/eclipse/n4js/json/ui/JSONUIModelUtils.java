package org.eclipse.n4js.json.ui;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONValue;

/**
 * Static utility class for dealing with {@link JSONPackage} elements in the UI.
 */
public class JSONUIModelUtils {
	/**
	 * Returns the children of the given {@link JSONValue}.
	 * 
	 * Returns an empty list for non-container {@link JSONValue}s.
	 * 
	 * @See {@link #isContainer(JSONValue)}
	 */
	public static List<? extends EObject> getChildren(JSONValue value) {
		if (value instanceof JSONArray) {
			return ((JSONArray) value).getElements();
		} else if (value instanceof JSONObject) {
			return ((JSONObject) value).getNameValuePairs();
		} else {
			// non-container types do not have any children
			return Collections.emptyList();
		}
	}

	/**
	 * Returns {@code true} iff the given {@link JSONValue} may contain children
	 * (e.g. object, array) and does not just represent a primitive value (e.g.
	 * string, boolean).
	 */
	public static boolean isContainer(JSONValue value) {
		return (value instanceof JSONObject) || (value instanceof JSONArray);
	}

	private JSONUIModelUtils() {
		// non-instantiable utility class
	}
}
