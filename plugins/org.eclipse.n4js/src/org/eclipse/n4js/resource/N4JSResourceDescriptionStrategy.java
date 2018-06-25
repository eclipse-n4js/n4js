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
	public static final String MAIN_MODULE_KEY = "MAIN_MODULE_KEY";

	/**
	 * The user data key for the type access modifier - used to compute visibility without creating a resource just from
	 * the proxy in the index.
	 */
	public static final String ACCESS_MODIFIERY_KEY = "ACCESS_MODIFIER_KEY";

	/**
	 * User data to store the {@link TClass#isFinal() final} property of a {@link TClass} in the index without resolving
	 * the actual proxy. Used by N4JS type search. Note: Only {@link IEObjectDescription object description}s
	 * representing a {@link TClass} has this user data.
	 */
	public static final String FINAL_KEY = "FINAL_KEY";

	/**
	 * User data to store the {@link TClass#isAbstract() abstract} property of a {@link TClass} in the index without
	 * resolving the actual proxy. Used by N4JS type search. Note: Only {@link IEObjectDescription object description}s
	 * representing a {@link TClass} has this user data.
	 */
	public static final String ABSTRACT_KEY = "ABSTRACT_KEY";

	/**
	 * User data telling if a {@link TClass} in the index is a test class, i.e. contains one or more methods annotated
	 * with &#64;Test, without resolving the actual proxy. Used by test discovery. Note: Only {@link IEObjectDescription
	 * object description}s representing a {@link TClass} have this user data.
	 */
	public static final String TEST_CLASS_KEY = "TEST_CLASS_KEY";

	/**
	 * User data to store the {@link TClass#isPolyfill() polyfill} property of a {@link TClass} in the index without
	 * resolving the actual proxy. Used by N4JS validation. Could also be used to show other icons. Note: Only
	 * {@link IEObjectDescription object descriptions} representing a {@link TClass} have this user data.
	 */
	public static final String POLYFILL_KEY = "POLYFILL_KEY";

	/**
	 * Additional user data for storing the {@link TClass#isExported() exported} property in the index. Used by test
	 * discovery helper. If the class is not exported this key could be missing, in other words, a class is marked as
	 * exported if this key has an associated value and the value {@link Boolean#parseBoolean(String)} is {@code true}.
	 */
	public static final String EXPORTED_CLASS_KEY = "EXPORTED_CLASS_KEY";

	/**
	 * User data to store the {@link TClass#isStaticPolyfill() staticPolyfill} property of a {@link TClass} in the index
	 * without resolving the actual proxy. This property distinguishes between static/non-static polyfills. A static
	 * polyfill also has the {@link TClass#isStaticPolyfill() staticPolyfill} set to {@code true} Used by N4JS
	 * validation. Could also be used to show other icons. Note: Only {@link IEObjectDescription object descriptions}
	 * representing a {@link TClass} have this user data.
	 */
	public static final String STATIC_POLYFILL_KEY = "STATIC_POLYFILL_KEY";

	/**
	 * Additional user data for storing the {@link TClass#isExported() exported} property in the index. Used by test
	 * discovery helper. If the class is not exported this key could be missing, in other words, a class is marked as
	 * exported if this key has an associated value and the value {@link Boolean#parseBoolean(String)} is {@code true}.
	 */
	public static final String EXPORTED_DEFAULT_KEY = "EXPORTED_DEFAULT";

	/**
	 * Version declared in {@link Versionable} types. Used by content assist to show version information. If this user
	 * data is missing, no version information is shown.
	 */
	public static final String VERSIONABLE_VERSION = "VERSIONABLE_VERSION";

	/**
	 * Version declared in {@link Versionable} types. Used by content assist to show version information. If this user
	 * data is missing, no version information is shown.
	 */
	public static final String TMODULE_TYPE = "TMODULE_TYPE";

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
		userData.put(EXPORTED_CLASS_KEY, Boolean.toString(tClass.isExported()));
		userData.put(ABSTRACT_KEY, Boolean.toString(tClass.isAbstract()));
		userData.put(FINAL_KEY, Boolean.toString(tClass.isFinal()));
		userData.put(POLYFILL_KEY, Boolean.toString(tClass.isPolyfill()));
		userData.put(STATIC_POLYFILL_KEY, Boolean.toString(tClass.isStaticPolyfill()));

		boolean hasTestMethod = false;
		for (TMember member : tClass.getOwnedMembers()) {
			if (member instanceof TMethod) {
				if (AnnotationDefinition.TEST_METHOD.hasAnnotation(member)) {
					hasTestMethod = true;
					break;
				}
			}
		}
		userData.put(TEST_CLASS_KEY, Boolean.toString(hasTestMethod));
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
		return new ForwardingMap<String, String>() {

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
	 * Returns the access modifier stored on the given description or PUBLIC.
	 */
	public static TypeAccessModifier tryGetAccessModifier(IEObjectDescription description) {
		try {
			String userData = description.getUserData(ACCESS_MODIFIERY_KEY);
			if (userData == null) {
				return TypeAccessModifier.PUBLIC;
			}
			return TypeAccessModifier.get(Integer.parseInt(userData));
		} catch (NumberFormatException e) {
			return TypeAccessModifier.PUBLIC;
		}
	}

	/**
	 * Returns the version number stored on the given description or 0.
	 */
	public static int tryGetVersionableVersion(IEObjectDescription description) {
		try {
			String userData = description.getUserData(VERSIONABLE_VERSION);
			if (userData == null) {
				return 0;
			}
			return Integer.parseInt(userData);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Returns the version number stored on the given description or 0.
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends TClass> tryGetType(IEObjectDescription description) {
		try {
			String eClassName = description.getUserData(TMODULE_TYPE);
			if (eClassName == null) {
				return TClass.class;
			}

			Class<?> loadedClass = ClassLoader.getSystemClassLoader().loadClass(eClassName);
			return (Class<? extends TClass>) loadedClass;
		} catch (NumberFormatException e) {
			return TClass.class;
		} catch (ClassNotFoundException e) {
			return TClass.class;
		}
	}

	/**
	 * Supplies the given userData map with the user data value for the access modifier.
	 */
	protected void addType(Map<String, String> userData, Type type) {
		userData.put(TMODULE_TYPE, type.eClass().getInstanceClassName());
	}

	/**
	 * Supplies the given userData map with the user data value for the access modifier.
	 */
	protected void addAccessModifierUserData(Map<String, String> userData, TypeAccessModifier typeAccessModifier) {
		// don't write public visibity to the index since it is treated as the default
		if (TypeAccessModifier.PUBLIC != typeAccessModifier) {
			userData.put(ACCESS_MODIFIERY_KEY, String.valueOf(typeAccessModifier.getValue()));
		}
	}

	/**
	 * Supplies the given userData map with the user data value for the access modifier.
	 */
	protected void addVersionableVersion(Map<String, String> userData, Type type) {
		int version = type.getVersion();
		if (version != 0) {
			userData.put(VERSIONABLE_VERSION, String.valueOf(version));
		}
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
				addType(userData, type);

				// Add additional user data for descriptions representing a TClass
				if (type instanceof TClass) {
					final TClass tClass = (TClass) type;
					addClassUserData(userData, tClass);
					if (N4JSLanguageConstants.EXPORT_DEFAULT_NAME.equals(tClass.getExportedName())) {
						userData.put(EXPORTED_DEFAULT_KEY, "1");
					}
				} else if (N4JSLanguageConstants.EXPORT_DEFAULT_NAME.equals(type.getExportedName())) {
					userData.put(EXPORTED_DEFAULT_KEY, "1");
				}

				IEObjectDescription eod = N4JSEObjectDescription.create(qualifiedName, type, userData);
				acceptor.accept(eod);
			}
		}
	}

	/**
	 * Create EObjectDescriptions for variables for which N4JSQualifiedNameProvider provides a FQN; variables with a FQN
	 * of <code>null</code> (currently all non-exported variables) will be ignored.
	 */
	private void internalCreateEObjectDescription(TVariable type, IAcceptor<IEObjectDescription> acceptor) {
		QualifiedName qualifiedName = qualifiedNameProvider.getFullyQualifiedName(type);
		if (qualifiedName != null) { // e.g. non-exported variables will return null for FQN
			Map<String, String> userData = new HashMap<>();
			addAccessModifierUserData(userData, type.getTypeAccessModifier());

			IEObjectDescription eod = EObjectDescription.create(qualifiedName, type, userData);
			acceptor.accept(eod);
		}
	}

}
