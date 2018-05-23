package org.eclipse.n4js.json.validation.extension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.validation.JSONIssueCodes;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * An abstract implementation of {@link IJSONValidatorExtension} that enables the functionality of 
 * an {@link AbstractDeclarativeValidator} (check methods, addIssue methods, etc.).
 * 
 * Subclasses of this class can be registered as {@code org.eclipse.n4js.json.validation} extension.
 * 
 * Subclasses may further customize their scope by overriding {@link #isResponsible(Map, EObject)} so
 * that they only apply to certain resources.
 */
public class AbstractJSONValidatorExtension extends AbstractDeclarativeValidator implements IJSONValidatorExtension {
	
	@Override
	public final void validateJSON(JSONDocument document, DiagnosticChain diagnosticChain) {
		// use an empty context for the JSON validation
		final HashMap<Object, Object> context = new HashMap<>();
	
		// early exit, if this validator is not responsible for this particular document
		if (!this.isResponsible(context, document)) {
			return;
		}
		
		// validate document itself
		this.validate(JSONPackage.Literals.JSON_DOCUMENT, document, diagnosticChain, context);
		
		// validate all contents
		document.eAllContents().forEachRemaining(child -> {
			this.validate(child.eClass(), child, diagnosticChain, context);
		});
	}

	/**
	 * Collects all {@code CheckJSONKey} methods of this class and invokes them on the 
	 * corresponding properties in the given JSON document
	 */
	@Check
	public void checkUsingCheckKeyMethods(JSONDocument document) {
		final List<Method> allMethods = Arrays.asList(this.getClass().getDeclaredMethods());
		final List<Pair<CheckProperty, Method>> checkKeyMethods = allMethods.stream()
				// filter for methods with CheckJSONKey annotation
				.filter(m -> m.getAnnotationsByType(CheckProperty.class).length != 0)
				// filter for methods with a single parameter type
				.filter(m -> m.getParameterTypes().length == 1)
				// map to annotation instance + method pair
				.map(m -> Pair.of(m.getAnnotationsByType(CheckProperty.class)[0], m))
				.collect(Collectors.toList());
	
		final Multimap<String, JSONValue> documentValues = collectDocumentValues(document);
	
		for (Pair<CheckProperty, Method> methodPair : checkKeyMethods) {
			final CheckProperty annotation = methodPair.getKey();
			final Method method = methodPair.getValue();
	
			final String keyPath = annotation.propertyPath();
			final Collection<JSONValue> values = documentValues.get(keyPath);
	
			// if the property is mandatory, check for its presence first
			if (annotation.mandatory()) {
				checkIsPresent(document, documentValues, keyPath);
			}
	
			if (!isValidCheckKeyMethod(method)) {
				throw new IllegalStateException("Not a valid @CheckJSONKey validation method " + method + "."
						+ " Only methods with a single JSONValue parameter are considered valid @CheckJSONKey methods.");
			}
	
			// check each value that has been specified for keyPath
			for (JSONValue value : values) {
				if (value != null) {
					try {
						method.invoke(this, value);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new IllegalStateException("Failed to invoke @CheckJSONKey method " + method + ": " + e);
					}
				}
			}
		}
	}

	@Override
	public void register(EValidatorRegistrar registrar) {
		// do not register with EMF registry (uses extension points instead)
	}

	/** Returns a user-facing description of the given {@link JSONValue} */
	protected static String getJSONValueDescription(JSONValue value) {
		return getJSONValueDescription(value.eClass());
	}

	/** Returns a user-facing description of the given {@link JSONValue} subclass. */
	protected static String getJSONValueDescription(EClass valueClass) {
		if (valueClass == JSONPackage.eINSTANCE.getJSONStringLiteral()) {
			return "string";
		} else if (valueClass == JSONPackage.eINSTANCE.getJSONNumericLiteral()) {
			return "number";
		} else if (valueClass == JSONPackage.eINSTANCE.getJSONBooleanLiteral()) {
			return "boolean";
		} else if (valueClass == JSONPackage.eINSTANCE.getJSONObject()) {
			return "object";
		} else if (valueClass == JSONPackage.eINSTANCE.getJSONArray()) {
			return "array";
		} else {
			return "<unknown>";
		}
	}

	@Override
	protected List<EPackage> getEPackages() {
		// by default, JSON validator extensions only apply to elements of the JSON model
		return Arrays.asList(JSONPackage.eINSTANCE);
	}
	
	@Override
	protected boolean isResponsible(Map<Object, Object> context, EObject eObject) {
		// by default, JSON validator extensions are responsible for all elements of the JSON model
		return eObject.eClass().getEPackage() == JSONPackage.eINSTANCE; 
	}

	/**
	 * Checks that in the given JSON {@code documentValues}, a value has been set for the given property path.
	 *
	 * Adds an {@code IssueCodes#JSON_MISSING_PROPERTY} issue to {@code document} otherwise.
	 */
	protected void checkIsPresent(JSONDocument document, Multimap<String, JSONValue> documentValues, String propertyPath) {
		if (!documentValues.containsKey(propertyPath)) {
			addIssue(JSONIssueCodes.getMessageForJSON_MISSING_PROPERTY(propertyPath), document,
					JSONIssueCodes.JSON_MISSING_PROPERTY);
		}
	}

	/**
	 * Checks that the given JSON {@code value} is an instance of the given {@code valueClass}.
	 *
	 * Adds an {@link JSONIssueCodes#JSON_EXPECTED_DIFFERENT_VALUE_TYPE} to {@code value} if not.
	 */
	protected boolean checkIsType(JSONValue value, EClass valueClass) {
		if (!valueClass.isInstance(value)) {
			addIssue(JSONIssueCodes.getMessageForJSON_EXPECTED_DIFFERENT_VALUE_TYPE(getJSONValueDescription(valueClass),
					getJSONValueDescription(value), ""), value, JSONIssueCodes.JSON_EXPECTED_DIFFERENT_VALUE_TYPE);
			return false;
		}
		return true;
	}

	/** Returns {@code true} iff {@code method} is a valid {@code @CheckJSONKey} method. */
	private boolean isValidCheckKeyMethod(Method method) {
		return method.getParameterTypes().length == 1 &&
				method.getParameterTypes()[0] == JSONValue.class;
	}

	/**
	 * Collects all key-path-value associations that can be extracted from the given JSON {@code document}.
	 */
	private Multimap<String, JSONValue> collectDocumentValues(JSONDocument document) {
		if (document == null || document.getContent() == null) {
			return ImmutableMultimap.<String, JSONValue> of(); // empty map
		}
	
		final JSONValue documentContent = document.getContent();
		if (!(documentContent instanceof JSONObject)) {
			return ImmutableMultimap.<String, JSONValue> of(); // empty map
		}
	
		return collectDocumentValues((JSONObject) documentContent,
				LinkedHashMultimap.<String, JSONValue> create(), "");
	}

	/**
	 * Collects all key-path-value pairs that can be extracted from the given {@code object} and stores them in
	 * {@code documentValues}.
	 *
	 * Key-paths are represented in terms of concatenated strings, separated by a {@code .} character (e.g.
	 * nested.name.value => JSONValue).
	 *
	 * @param object
	 *            The {@link JSONObject} to collect the values form.
	 * @param documentValues
	 *            The map to store the values to.
	 * @param prefix
	 *            The prefix to use for key-paths.
	 */
	private Multimap<String, JSONValue> collectDocumentValues(JSONObject object, Multimap<String, JSONValue> documentValues, String prefix) {
		for (NameValuePair pair : object.getNameValuePairs()) {
			final String pairName = pair.getName();
			final String name = prefix.isEmpty() ? pairName : prefix + "." + pairName;
			final JSONValue value = pair.getValue();
			documentValues.put(name, value);
	
			if (value instanceof JSONObject) {
				collectDocumentValues((JSONObject) value, documentValues, name);
			}
		}
		return documentValues;
	}
}
