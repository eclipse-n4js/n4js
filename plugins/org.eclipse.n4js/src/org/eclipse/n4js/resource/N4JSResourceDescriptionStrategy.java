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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.ts.types.AbstractModule;
import org.eclipse.n4js.ts.types.ElementExportDefinition;
import org.eclipse.n4js.ts.types.ExportDefinition;
import org.eclipse.n4js.ts.types.ModuleExportDefinition;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TConstableElement;
import org.eclipse.n4js.ts.types.TDeclaredModule;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.LineAndColumn;

import com.google.common.collect.ForwardingMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Only produce {@link EObjectDescription descriptions} for instances of the type-representation of the resource.
 */
@Singleton
public class N4JSResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {

	/**
	 * Source code location information attached to each {@link IEObjectDescription}. See
	 * {@link N4JSResourceDescriptionStrategy#getLocation(IEObjectDescription)}.
	 */
	public static class Location {
		/** Zero-based. */
		public final int startLine;
		/** Zero-based. */
		public final int startColumn;
		/** Zero-based. */
		public final int endLine;
		/** Zero-based. */
		public final int endColumn;

		/** Creates a new {@link Location}. */
		public Location(int startLine, int startColumn, int endLine, int endColumn) {
			this.startLine = startLine;
			this.startColumn = startColumn;
			this.endLine = endLine;
			this.endColumn = endColumn;
		}

		@Override
		public String toString() {
			return toString(startLine, startColumn, endLine, endColumn);
		}

		/** Converts this location to string format. */
		public static String toString(int startLine, int startColumn, int endLine, int endColumn) {
			return startLine + ":" + startColumn + "-" + endLine + ":" + endColumn;
		}

		/** Parses a string returned by {@link #toString(int, int, int, int)} back to a {@link Location} instance. */
		public static Location fromString(String str) {
			int firstColon = -1;
			int dash = -1;
			int secondColon = -1;
			int len = str.length();
			for (int i = 0; i < len; i++) {
				char ch = str.charAt(i);
				if (ch == '-' && dash == -1) {
					dash = i;
				} else if (ch == ':') {
					if (dash == -1) {
						firstColon = i;
					} else {
						secondColon = i;
						break;
					}
				}
			}
			if (firstColon < 0 || dash < 0 || secondColon < 0) {
				throw new NumberFormatException("invalid format of location string: " + str);
			}
			int startLine = Integer.parseInt(str.substring(0, firstColon));
			int startColumn = Integer.parseInt(str.substring(firstColon + 1, dash));
			int endLine = Integer.parseInt(str.substring(dash + 1, secondColon));
			int endColumn = Integer.parseInt(str.substring(secondColon + 1));
			return new Location(startLine, startColumn, endLine, endColumn);
		}

	}

	/**
	 * User data key to store the {@link Location source code location} of an element.
	 */
	private static final String LOCATION_KEY = "LOCATION";
	private static final Location LOCATION_DEFAULT = null;

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
	 * Additional user data for storing the {@link TClassifier#isDirectlyExported() exported} property in the index.
	 * Used by test discovery helper. If the class is not exported this key could be missing, in other words, a class is
	 * marked as exported if this key has an associated value and the value {@link Boolean#parseBoolean(String)} is
	 * {@code true}.
	 */
	private static final String DIRECTLY_EXPORTED_CLASSIFIER_KEY = "DIRECTLY_EXPORTED_CLASSIFIER";
	private static final boolean DIRECTLY_EXPORTED_CLASSIFIER_DEFAULT = false;

	/**
	 * User data to store the {@link TClass#isStaticPolyfill() staticPolyfill} property of a {@link TClass} in the index
	 * without resolving the actual proxy. This property distinguishes between static/non-static polyfills. A static
	 * polyfill also has the {@link TClass#isStaticPolyfill() staticPolyfill} set to {@code true} Used by N4JS
	 * validation. Could also be used to show other icons. Note: Only {@link IEObjectDescription object descriptions}
	 * representing a {@link TClass} have this user data.
	 */
	private static final String STATIC_POLYFILL_KEY = "STATIC_POLYFILL";
	private static final boolean STATIC_POLYFILL_DEFAULT = false;

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Inject
	private ILocationInFileProvider locationInFileProvider;

	@Override
	public boolean createEObjectDescriptions(final EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
		if (getQualifiedNameProvider() == null)
			return false;
		if (eObject instanceof TModule) {
			TModule module = (TModule) eObject;
			internalCreateEObjectDescriptionForRoot(module, acceptor);
			internalCreateEObjectDescriptionsForModuleContents(module, acceptor);
		}
		// export is only possible for top-level elements
		return false;
	}

	private void internalCreateEObjectDescriptionsForModuleContents(AbstractModule module,
			IAcceptor<IEObjectDescription> acceptor) {
		for (ExportDefinition exportDef : module.getExportDefinitions()) {
			if (exportDef instanceof ElementExportDefinition) {
				internalCreateEObjectDescription((ElementExportDefinition) exportDef, acceptor);
			} else if (exportDef instanceof ModuleExportDefinition) {
				// FIXME
			}
		}
		boolean isRoot = module instanceof TModule;
		if (isRoot) {
			// declared modules nested inside other declared modules cannot be imported in TS, so we do not yet support
			// this either and therefore the following is done only for TDeclaredModules directly contained in the root
			// TModule:
			for (TDeclaredModule declModule : module.getModules()) {
				internalCreateEObjectDescriptionForDeclaredModule(declModule, acceptor);
				internalCreateEObjectDescriptionsForModuleContents(declModule, acceptor);
			}
		}
	}

	/** @return the source code location of the element represented by the given {@link IEObjectDescription}. */
	public static Location getLocation(IEObjectDescription description) {
		String userData = description.getUserData(LOCATION_KEY);
		if (userData == null) {
			return LOCATION_DEFAULT;
		}
		return Location.fromString(userData);
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

	/** @return the {@link TClassifier#isDirectlyExported() directlyExported} property of the given description. */
	public static boolean getDirectlyExported(IEObjectDescription description) {
		String userData = description.getUserData(DIRECTLY_EXPORTED_CLASSIFIER_KEY);
		if (userData == null) {
			return DIRECTLY_EXPORTED_CLASSIFIER_DEFAULT;
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

	private void internalCreateEObjectDescriptionForRoot(final TModule module,
			IAcceptor<IEObjectDescription> acceptor) {
		// user data: serialized representation
		final Map<String, String> userData = createModuleUserData(module);
		QualifiedName qualifiedName = qualifiedNameProvider.getFullyQualifiedName(module);

		IEObjectDescription eod = new EObjectDescription(qualifiedName, module, userData);
		acceptor.accept(eod);
	}

	private void internalCreateEObjectDescriptionForDeclaredModule(final TDeclaredModule module,
			IAcceptor<IEObjectDescription> acceptor) {
		QualifiedName qualifiedName = qualifiedNameProvider.getFullyQualifiedName(module);
		if (qualifiedName == null) {
			return; // syntax error, etc.
		}

		Map<String, String> userData = new HashMap<>();
		addLocationUserData(userData, module);

		IEObjectDescription eod = new EObjectDescription(qualifiedName, module, userData);
		acceptor.accept(eod);
	}

	private Map<String, String> createModuleUserData(final TModule module) {
		// TODO GH-230 consider disallowing serializing reconciled modules to index with fail-safe behavior:
		// if (module.isPreLinkingPhase() || module.isReconciled()) {
		if (module.isPreLinkingPhase()) {
			return UserDataMapper.createTimestampUserData(module);
		}
		Location location = computeLocation(module);
		return new ForwardingMap<>() {

			private Map<String, String> delegate;

			@Override
			protected Map<String, String> delegate() {
				if (delegate == null) {
					try {
						delegate = UserDataMapper.createUserData(module);
						if (location != null) {
							delegate.put(LOCATION_KEY, location.toString());
						}
						N4JSResource resource = (N4JSResource) module.eResource();
						UserDataMapper.writeDependenciesToUserData(resource, delegate);
					} catch (Exception e) {
						throw new IllegalStateException(e);
					}
				}
				return delegate;
			}
		};

	}

	private void internalCreateEObjectDescription(ElementExportDefinition exportDef,
			IAcceptor<IEObjectDescription> acceptor) {

		TExportableElement element = exportDef.getExportedElement();
		if (element == null || element.eIsProxy()) {
			return;
		}
		if (element instanceof Type) {
			internalCreateEObjectDescription(exportDef, (Type) element, acceptor);
		} else if (element instanceof TVariable) {
			internalCreateEObjectDescription(exportDef, (TVariable) element, acceptor);
		} else {
			throw new UnsupportedOperationException(
					"unsupported subclass of TExportableElement: " + element.eClass().getName());
		}
	}

	/**
	 * Create EObjectDescriptions for elements for which N4JSQualifiedNameProvider provides a FQN; elements with a FQN
	 * of <code>null</code> will be ignored.
	 */
	private void internalCreateEObjectDescription(ExportDefinition exportDef, Type type,
			IAcceptor<IEObjectDescription> acceptor) {

		final String typeName = type.getName();
		if (typeName != null && typeName.length() != 0) {
			QualifiedName qualifiedName = qualifiedNameProvider.getFullyQualifiedName(exportDef);
			if (qualifiedName != null) {
				Map<String, String> userData = new HashMap<>();
				addLocationUserData(userData, type);
				addAccessModifierUserData(userData, type.getTypeAccessModifier());

				// Add additional user data for descriptions representing a TClassifier
				if (type instanceof TClassifier) {
					final TClassifier tClassifier = (TClassifier) type;
					addClassifierUserData(userData, tClassifier);
				}

				IEObjectDescription eod = N4JSEObjectDescription.create(qualifiedName, type, userData);
				acceptor.accept(eod);
			}
		}
	}

	private void addLocationUserData(Map<String, String> userData, EObject elem) {
		Location location = computeLocation(elem);
		if (location != null) {
			userData.put(LOCATION_KEY, location.toString());
		}
	}

	private Location computeLocation(EObject obj) {
		ITextRegion region = locationInFileProvider.getSignificantTextRegion(obj);
		if (region == null) {
			return null;
		}
		int offset = region.getOffset();
		int length = region.getLength();
		Resource resource = obj.eResource();
		if (resource instanceof XtextResource) {
			ICompositeNode rootNode = ((XtextResource) resource).getParseResult().getRootNode();
			LineAndColumn start = NodeModelUtils.getLineAndColumn(rootNode, offset);
			LineAndColumn end = length > 0 ? NodeModelUtils.getLineAndColumn(rootNode, offset + length) : start;
			if (start != null && end != null) {
				return new Location(
						start.getLine() - 1, start.getColumn() - 1,
						end.getLine() - 1, end.getColumn() - 1);
			}
		}
		return null;
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

	/**
	 * Create EObjectDescriptions for variables for which N4JSQualifiedNameProvider provides a FQN; variables with a FQN
	 * of <code>null</code> (currently all non-exported variables) will be ignored.
	 */
	private void internalCreateEObjectDescription(ExportDefinition exportDef, TVariable variable,
			IAcceptor<IEObjectDescription> acceptor) {

		QualifiedName qualifiedName = qualifiedNameProvider.getFullyQualifiedName(exportDef);
		if (qualifiedName != null) {
			Map<String, String> userData = new HashMap<>();
			addLocationUserData(userData, variable);
			addAccessModifierUserData(userData, variable.getTypeAccessModifier());
			addConstUserData(userData, variable);

			IEObjectDescription eod = EObjectDescription.create(qualifiedName, variable, userData);
			acceptor.accept(eod);
		}
	}

	/**
	 * Creates the additional user data map for elements of type {@link TClassifier}.
	 *
	 * @param userData
	 *            Map that will be populated with data.
	 * @param tClassifier
	 *            The {@link TClassifier} element to create user data for.
	 * @returns An immutable user-data map
	 */
	private void addClassifierUserData(final Map<String, String> userData, TClassifier tClassifier) {
		if (tClassifier.isDirectlyExported() != DIRECTLY_EXPORTED_CLASSIFIER_DEFAULT) {
			userData.put(DIRECTLY_EXPORTED_CLASSIFIER_KEY, Boolean.toString(tClassifier.isDirectlyExported()));
		}
		if (tClassifier.isAbstract() != ABSTRACT_DEFAULT) {
			userData.put(ABSTRACT_KEY, Boolean.toString(tClassifier.isAbstract()));
		}
		if (tClassifier.isFinal() != FINAL_DEFAULT) {
			userData.put(FINAL_KEY, Boolean.toString(tClassifier.isFinal()));
		}
		if (tClassifier.isPolyfill() != POLYFILL_DEFAULT) {
			userData.put(POLYFILL_KEY, Boolean.toString(tClassifier.isPolyfill()));
		}
		if (tClassifier.isStaticPolyfill() != STATIC_POLYFILL_DEFAULT) {
			userData.put(STATIC_POLYFILL_KEY, Boolean.toString(tClassifier.isStaticPolyfill()));
		}

		if (tClassifier instanceof TClass) {
			boolean hasTestMethod = false;
			for (TMember member : tClassifier.getOwnedMembers()) {
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
}
