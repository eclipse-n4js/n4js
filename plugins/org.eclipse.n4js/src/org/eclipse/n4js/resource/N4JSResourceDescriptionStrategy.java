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
package org.eclipse.n4js.resource;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.ts.typeRefs.Versionable;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TConstableElement;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy;
import org.eclipse.xtext.util.IAcceptor;

import com.google.common.collect.ForwardingMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Only produce {@link EObjectDescription descriptions} for instances of the type-representation of the resource.
 */
@Singleton
public class N4JSResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {

	/**
	 * User data to store the {@link TModule#isMainModule() mainModule} property of a {@link TModule} in the index
	 * without resolving the actual proxy. This property distinguishes between projects main module and other modules.
	 * Main module has different shadowing rules in the scoping. Note: Only {@link IEObjectDescription object
	 * descriptions} representing a {@link TModule} have this user data.
	 */
	public static final String MAIN_MODULE_KEY = "MAIN_MODULE";
	private static final boolean MAIN_MODULE_DEFAULT = false;

	/**
	 * The user data key for the type access modifier - used to compute visibility without creating a resource just from
	 * the proxy in the index.
	 */
	private static final String TYPE_ACCESS_MODIFIER_KEY = "TYPE_ACCESS_MODIFIER";
	private static final TypeAccessModifier TYPE_ACCESS_MODIFIER_DEFAULT = TypeAccessModifier.PUBLIC;

	/**
	 * The user data key for the const flag of {@link TConstableElement}s - used to distinguish between var/let and
	 * const without creating a resource just from the proxy in the index.
	 */
	private static final String CONST_KEY = "CONST";
	private static final boolean CONST_DEFAULT = false;

	/**
	 * User data to store the {@link TClass#isFinal() final} property of a {@link TClass} in the index without resolving
	 * the actual proxy. Used by N4JS type search. Note: Only {@link IEObjectDescription object description}s
	 * representing a {@link TClass} has this user data.
	 */
	private static final String FINAL_KEY = "FINAL";
	private static final boolean FINAL_DEFAULT = false;

	/**
	 * User data to store the {@link TClass#isAbstract() abstract} property of a {@link TClass} in the index without
	 * resolving the actual proxy. Used by N4JS type search. Note: Only {@link IEObjectDescription object description}s
	 * representing a {@link TClass} has this user data.
	 */
	private static final String ABSTRACT_KEY = "ABSTRACT";
	private static final boolean ABSTRACT_DEFAULT = false;

	/**
	 * User data telling if a {@link TClass} in the index is a test class, i.e. contains one or more methods annotated
	 * with &#64;Test, without resolving the actual proxy. Used by test discovery. Note: Only {@link IEObjectDescription
	 * object description}s representing a {@link TClass} have this user data.
	 */
	private static final String TEST_CLASS_KEY = "TEST_CLASS";
	private static final boolean TEST_CLASS_DEFAULT = false;

	/**
	 * User data to store the {@link TClass#isPolyfill() polyfill} property of a {@link TClass} in the index without
	 * resolving the actual proxy. Used by N4JS validation. Could also be used to show other icons. Note: Only
	 * {@link IEObjectDescription object descriptions} representing a {@link TClass} have this user data.
	 */
	private static final String POLYFILL_KEY = "POLYFILL";
	private static final boolean POLYFILL_DEFAULT = false;

	/**
	 * Additional user data for storing the {@link TClass#isExported() exported} property in the index. Used by test
	 * discovery helper. If the class is not exported this key could be missing, in other words, a class is marked as
	 * exported if this key has an associated value and the value {@link Boolean#parseBoolean(String)} is {@code true}.
	 */
	private static final String EXPORTED_CLASS_KEY = "EXPORTED_CLASS";
	private static final boolean EXPORTED_CLASS_DEFAULT = false;

	/**
	 * User data to store the {@link TClass#isStaticPolyfill() staticPolyfill} property of a {@link TClass} in the index
	 * without resolving the actual proxy. This property distinguishes between static/non-static polyfills. A static
	 * polyfill also has the {@link TClass#isStaticPolyfill() staticPolyfill} set to {@code true} Used by N4JS
	 * validation. Could also be used to show other icons. Note: Only {@link IEObjectDescription object descriptions}
	 * representing a {@link TClass} have this user data.
	 */
	private static final String STATIC_POLYFILL_KEY = "STATIC_POLYFILL";
	private static final boolean STATIC_POLYFILL_DEFAULT = false;

	/**
	 * Additional user data for storing the {@link TClass#isExported() exported} property in the index. Used by test
	 * discovery helper. If the class is not exported this key could be missing, in other words, a class is marked as
	 * exported if this key has an associated value and the value {@link Boolean#parseBoolean(String)} is {@code true}.
	 */
	private static final String EXPORT_DEFAULT_KEY = "EXPORTED_DEFAULT";
	private static final boolean EXPORT_DEFAULT_DEFAULT = false;

	/**
	 * Version declared in {@link Versionable} types. Used by content assist to show version information. If this user
	 * data is missing, no version information is shown.
	 */
	private static final String VERSION = "VERSION";
	private static final int VERSION_DEFAULT = 0;

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Override
	public boolean createEObjectDescriptions(final EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
		if (getQualifiedNameProvider() == null)
			return false;
		if (eObject instanceof TModule) {
			TModule module = (TModule) eObject;
			internalCreateEObjectDescriptionForRoot(module, acceptor);
			for (Type type : module.getTopLevelTypes()) {
				internalCreateEObjectDescription(type, acceptor);
			}
			for (TVariable variable : module.getVariables()) {
				internalCreateEObjectDescription(variable, acceptor);
			}
		}
		// export is only possible for top-level elements
		return false;
	}

	/** @return the whether the given description is the main module. */
	public static boolean getMainModule(IEObjectDescription description) {
		String userData = description.getUserData(MAIN_MODULE_KEY);
		if (userData == null) {
			return MAIN_MODULE_DEFAULT;
		}
		return Boolean.parseBoolean(userData);
	}

	/** @return the final modifier of the given description. */
	public static boolean getFinal(IEObjectDescription description) {
		String userData = description.getUserData(FINAL_KEY);
		if (userData == null) {
			return FINAL_DEFAULT;
		}
		return Boolean.parseBoolean(userData);
	}

	/** @return the abstract modifier of the given description. */
	public static boolean getAbstract(IEObjectDescription description) {
		String userData = description.getUserData(ABSTRACT_KEY);
		if (userData == null) {
			return ABSTRACT_DEFAULT;
		}
		return Boolean.parseBoolean(userData);
	}

	/** @return the test class modifier of the given description. */
	public static boolean getTestClass(IEObjectDescription description) {
		String userData = description.getUserData(TEST_CLASS_KEY);
		if (userData == null) {
			return TEST_CLASS_DEFAULT;
		}
		return Boolean.parseBoolean(userData);
	}

	/** @return the exported modifier of the given description. */
	public static boolean getExported(IEObjectDescription description) {
		String userData = description.getUserData(EXPORTED_CLASS_KEY);
		if (userData == null) {
			return EXPORTED_CLASS_DEFAULT;
		}
		return Boolean.parseBoolean(userData);
	}

	/** @return the polyfill modifier of the given description. */
	public static boolean getPolyfill(IEObjectDescription description) {
		String userData = description.getUserData(POLYFILL_KEY);
		if (userData == null) {
			return POLYFILL_DEFAULT;
		}
		return Boolean.parseBoolean(userData);
	}

	/** @return the static polyfill modifier of the given description. */
	public static boolean getStaticPolyfill(IEObjectDescription description) {
		String userData = description.getUserData(STATIC_POLYFILL_KEY);
		if (userData == null) {
			return STATIC_POLYFILL_DEFAULT;
		}
		return Boolean.parseBoolean(userData);
	}

	/** @return the export default modifier of the given description. Default return is public. */
	public static boolean getExportDefault(IEObjectDescription description) {
		String userData = description.getUserData(EXPORT_DEFAULT_KEY);
		if (userData == null) {
			return EXPORT_DEFAULT_DEFAULT;
		}
		return Boolean.parseBoolean(userData);
	}

	/** @return the type access modifier of the given description. Default return is public. */
	public static TypeAccessModifier getTypeAccessModifier(IEObjectDescription description) {
		try {
			String userData = description.getUserData(TYPE_ACCESS_MODIFIER_KEY);
			if (userData == null) {
				return TYPE_ACCESS_MODIFIER_DEFAULT;
			}
			return TypeAccessModifier.get(Integer.parseInt(userData));
		} catch (NumberFormatException e) {
			return TYPE_ACCESS_MODIFIER_DEFAULT;
		}
	}

	/** @return the const flag of the given description. Default value is {@value #CONST_DEFAULT}. */
	public static boolean getConst(IEObjectDescription description) {
		String value = description.getUserData(CONST_KEY);
		if (value == null) {
			return CONST_DEFAULT;
		}
		return Boolean.parseBoolean(value);
	}

	/** @return the version number of the given description. */
	public static int getVersion(IEObjectDescription description) {
		try {
			String userData = description.getUserData(VERSION);
			if (userData == null) {
				return VERSION_DEFAULT;
			}
			return Integer.parseInt(userData);
		} catch (NumberFormatException e) {
			return VERSION_DEFAULT;
		}
	}

	private void internalCreateEObjectDescriptionForRoot(final TModule module,
			IAcceptor<IEObjectDescription> acceptor) {
		// user data: serialized representation
		final Map<String, String> userData = createModuleUserData(module);
		QualifiedName qualifiedName = qualifiedNameProvider.getFullyQualifiedName(module);

		IEObjectDescription eod = new EObjectDescription(qualifiedName, module, userData);
		acceptor.accept(eod);
	}

	private Map<String, String> createModuleUserData(final TModule module) {
		// TODO GH-230 consider disallowing serializing reconciled modules to index with fail-safe behavior:
		// if (module.isPreLinkingPhase() || module.isReconciled()) {
		if (module.isPreLinkingPhase()) {
			return UserdataMapper.createTimestampUserData(module);
		}
		return new ForwardingMap<>() {

			private Map<String, String> delegate;

			@Override
			protected Map<String, String> delegate() {
				if (delegate == null) {
					try {
						delegate = UserdataMapper.createUserData(module);
						N4JSResource resource = (N4JSResource) module.eResource();
						UserdataMapper.writeDependenciesToUserData(resource, delegate);
					} catch (Exception e) {
						throw new IllegalStateException(e);
					}
				}
				return delegate;
			}
		};

	}

	/**
	 * Create EObjectDescriptions for elements for which N4JSQualifiedNameProvider provides a FQN; elements with a FQN
	 * of <code>null</code> will be ignored.
	 */
	private void internalCreateEObjectDescription(Type type, IAcceptor<IEObjectDescription> acceptor) {
		final String exportedName = type.getExportedName();
		final String typeName = exportedName != null ? exportedName : type.getName();
		if (typeName != null && typeName.length() != 0) {
			QualifiedName qualifiedName = qualifiedNameProvider.getFullyQualifiedName(type);
			if (qualifiedName != null) { // e.g. non-exported declared functions will return null for FQN
				Map<String, String> userData = new HashMap<>();
				addAccessModifierUserData(userData, type.getTypeAccessModifier());
				addVersionableVersion(userData, type);

				// Add additional user data for descriptions representing a TClass
				if (type instanceof TClass) {
					final TClass tClass = (TClass) type;
					addClassUserData(userData, tClass);
					if (N4JSLanguageConstants.EXPORT_DEFAULT_NAME.equals(tClass.getExportedName())) {
						userData.put(EXPORT_DEFAULT_KEY, Boolean.toString(true));
					}
				} else if (N4JSLanguageConstants.EXPORT_DEFAULT_NAME.equals(type.getExportedName())) {
					userData.put(EXPORT_DEFAULT_KEY, Boolean.toString(true));
				}

				IEObjectDescription eod = N4JSEObjectDescription.create(qualifiedName, type, userData);
				acceptor.accept(eod);
			}
		}
	}

	/** Supplies the given userData map with the user data value for the access modifier. */
	private void addAccessModifierUserData(Map<String, String> userData, TypeAccessModifier typeAccessModifier) {
		// don't write public visibility to the index since it is treated as the default
		if (TYPE_ACCESS_MODIFIER_DEFAULT != typeAccessModifier) {
			userData.put(TYPE_ACCESS_MODIFIER_KEY, String.valueOf(typeAccessModifier.getValue()));
		}
	}

	/** Supplies the given userData map with the user data value for the const flag of {@link TConstableElement}s. */
	private void addConstUserData(Map<String, String> userData, TConstableElement element) {
		boolean isConst = element.isConst();
		if (isConst != CONST_DEFAULT) {
			userData.put(CONST_KEY, Boolean.toString(isConst));
		}
	}

	/** Supplies the given userData map with the user data value for the access modifier. */
	private void addVersionableVersion(Map<String, String> userData, Type type) {
		int version = type.getVersion();
		if (version != VERSION_DEFAULT) {
			userData.put(VERSION, String.valueOf(version));
		}
	}

	/**
	 * Create EObjectDescriptions for variables for which N4JSQualifiedNameProvider provides a FQN; variables with a FQN
	 * of <code>null</code> (currently all non-exported variables) will be ignored.
	 */
	private void internalCreateEObjectDescription(TVariable variable, IAcceptor<IEObjectDescription> acceptor) {
		QualifiedName qualifiedName = qualifiedNameProvider.getFullyQualifiedName(variable);
		if (qualifiedName != null) { // e.g. non-exported variables will return null for FQN
			Map<String, String> userData = new HashMap<>();
			addAccessModifierUserData(userData, variable.getTypeAccessModifier());
			addConstUserData(userData, variable);

			IEObjectDescription eod = EObjectDescription.create(qualifiedName, variable, userData);
			acceptor.accept(eod);
		}
	}

	/**
	 * Creates the additional user data map for elements of type {@link TClass}.
	 *
	 * @param userData
	 *            Map that will be populated with data.
	 * @param tClass
	 *            The {@link TClass} element to create user data for.
	 * @returns An immutable user-data map
	 */
	private void addClassUserData(final Map<String, String> userData, TClass tClass) {
		if (tClass.isExported() != EXPORTED_CLASS_DEFAULT) {
			userData.put(EXPORTED_CLASS_KEY, Boolean.toString(tClass.isExported()));
		}
		if (tClass.isAbstract() != ABSTRACT_DEFAULT) {
			userData.put(ABSTRACT_KEY, Boolean.toString(tClass.isAbstract()));
		}
		if (tClass.isFinal() != FINAL_DEFAULT) {
			userData.put(FINAL_KEY, Boolean.toString(tClass.isFinal()));
		}
		if (tClass.isPolyfill() != POLYFILL_DEFAULT) {
			userData.put(POLYFILL_KEY, Boolean.toString(tClass.isPolyfill()));
		}
		if (tClass.isStaticPolyfill() != STATIC_POLYFILL_DEFAULT) {
			userData.put(STATIC_POLYFILL_KEY, Boolean.toString(tClass.isStaticPolyfill()));
		}

		boolean hasTestMethod = false;
		for (TMember member : tClass.getOwnedMembers()) {
			if (member instanceof TMethod) {
				if (AnnotationDefinition.TEST_METHOD.hasAnnotation(member)) {
					hasTestMethod = true;
					break;
				}
			}
		}
		if (hasTestMethod != TEST_CLASS_DEFAULT) {
			userData.put(TEST_CLASS_KEY, Boolean.toString(hasTestMethod));
		}
	}
}
