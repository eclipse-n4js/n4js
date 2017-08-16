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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.AbstractEList;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.SegmentSequence;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.conversion.AbstractN4JSStringValueConverter;
import org.eclipse.n4js.conversion.LegacyOctalIntValueConverter;
import org.eclipse.n4js.conversion.N4JSStringValueConverter;
import org.eclipse.n4js.conversion.RegExLiteralConverter;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.parser.InternalSemicolonInjectingParser;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.scoping.diagnosing.N4JSScopingDiagnostician;
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeRegistrar;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;
import org.eclipse.n4js.utils.emf.ProxyResolvingResource;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IFragmentProvider;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;
import org.eclipse.xtext.resource.XtextSyntaxDiagnosticWithRange;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.OnChangeEvictingCache;
import org.eclipse.xtext.util.Triple;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Special resource that stores the N4JS AST in slot 0, and types exported by this module as containments of a
 * {@link TModule} in slot 1.
 * <p>
 * Usually, the root elements of the contents are no proxies. Thus, these elements are not resolved by the default
 * contents implementations ({@link org.eclipse.emf.ecore.resource.impl.ResourceImpl.ContentsEList} etc.). As the first
 * slot, the actual AST, could be a proxy (we usually do not want to parse the AST if we want to access the type
 * information), and thus it must be able to resolve this first element. This is transparently done in the custom
 * contents class {@link ModuleAwareContentsList}.
 */
public class N4JSResource extends PostProcessingAwareResource implements ProxyResolvingResource {

	/**
	 * Special contents list which allows for the first slot to be a proxy in case the resource has been created by
	 * loading the type information (slot 1) from the xtext index, i.e. the IObjectDescrition's user data.
	 * <p>
	 * As this is no default behavior, the list has to hide this first proxy element, cf sneaky... methods.
	 */
	private final class ModuleAwareContentsList extends ContentsEList<EObject> {
		private static final long serialVersionUID = 1L;

		public ModuleAwareContentsList() {
		}

		/**
		 * If the first element, that is the AST, is requested (index == 0) and if it is a proxy (because the resource
		 * has not really been loaded but created with the type information from the xtext index), then this element is
		 * actually loaded and the type information, formally loaded from the IObjectDescrition's user data is replace
		 * with the newly retrieved information.
		 *
		 * In all other cases {@link AbstractEList#resolve(int, Object)} will be invoked.
		 */
		@Override
		protected EObject resolve(int index, EObject object) {
			if (index == 0 && !aboutToBeUnloaded && isASTProxy(object)) {
				EObject result = demandLoadResource(object);
				return result;
			}
			return super.resolve(index, object);
		}

		/**
		 * Do not propagate any notifications that the AST has been modified or loaded, if the given object is the
		 * proxified AST.
		 */
		@Override
		protected void didAdd(int index, EObject object) {
			if (index == 0 && isASTProxy(object))
				return;
			super.didAdd(index, object);
		}

		/**
		 * Don't announce any notification but keep the inverse references in sync.
		 *
		 * @param object
		 *            the to-be-added object
		 */
		protected void sneakyAdd(EObject object) {
			sneakyGrow(size + 1);
			assign(size, validate(size, object));
			size++;
			inverseAdd(object, null);
		}

		/**
		 * Creates a new array for the content. The calculation of its new size is optimized related to the last size
		 * and the minimum required size. This method is called by {@link ModuleAwareContentsList#sneakyAdd(EObject)}
		 * and {@link ModuleAwareContentsList#sneakyAdd(int, EObject)}.
		 *
		 * @param minimumCapacity
		 *            the expected minimumCapacity of the list.
		 */
		private void sneakyGrow(int minimumCapacity) {
			int oldCapacity = data == null ? 0 : data.length;
			if (minimumCapacity > oldCapacity) {
				Object oldData[] = data;

				// This seems to be a pretty sweet formula that supports good
				// growth.
				// Adding an object to a list will create a list of capacity 4,
				// which is just about the average list size.
				//
				int newCapacity = oldCapacity + oldCapacity / 2 + 4;
				if (newCapacity < minimumCapacity) {
					newCapacity = minimumCapacity;
				}
				data = newData(newCapacity);
				if (oldData != null) {
					System.arraycopy(oldData, 0, data, 0, size);
				}
			}
		}

		/**
		 * Don't announce any notification but keep the inverse references in sync.
		 *
		 * @param index
		 *            the index where the object should be added to
		 * @param object
		 *            the to-be-added object
		 */
		protected void sneakyAdd(int index, EObject object) {
			sneakyGrow(size + 1);

			EObject validatedObject = validate(index, object);
			if (index != size) {
				System.arraycopy(data, index, data, index + 1, size - index);
			}
			assign(index, validatedObject);
			++size;
			inverseAdd(object, null);
		}

		/* Copied and stripped down from super.clear() */
		/**
		 * Avoid changes to the mod counter and events
		 */
		protected void sneakyClear() {
			List<EObject> collection = new UnmodifiableEList<>(size, data);
			sneakyDoClear();
			for (Iterator<EObject> i = collection.iterator(); i.hasNext();) {
				inverseRemove(i.next(), null);
			}
		}

		private void sneakyDoClear() {
			data = null;
			size = 0;
		}
	}

	private static final Logger logger = Logger.getLogger(N4JSResource.class);

	/** artificial fragment to use for the URL of the proxified first slot */
	public static final String AST_PROXY_FRAGMENT = ":astProxy";

	/**
	 * Set by the dirty state support to announce an upcoming unloading request.
	 */
	private boolean aboutToBeUnloaded;

	/**
	 * Set to true while we are currently discarding an adapter.
	 */
	private boolean removingAdapters;

	@Inject
	private N4JSScopingDiagnostician scopingDiagnostician;

	@Inject
	private BuiltInSchemeRegistrar registrar;

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private CanLoadFromDescriptionHelper canLoadFromDescriptionHelper;

	/*
	 * Even though the constructor is empty, it simplifies debugging (allows to set a breakpoint) thus we keep it here.
	 */
	/**
	 * Public default constructor.
	 */
	public N4JSResource() {
		super();
	}

	@Override
	protected URIConverter getURIConverter() {
		return getResourceSet() == null ? createNewURIConverter() : getResourceSet().getURIConverter();
	}

	private URIConverter createNewURIConverter() {
		URIConverter result = new ExtensibleURIConverterImpl();
		registrar.registerScheme(result);
		return result;
	}

	@Override
	public synchronized EList<EObject> getContents() {
		if (!removingAdapters) {
			return super.getContents();
		} else {
			return doGetContents();
		}
	}

	/**
	 * Create a customized contents list, to handle a proxified first slot and a second slot holding the types model.
	 */
	@Override
	protected EList<EObject> doGetContents() {
		if (contents == null) {
			contents = new ModuleAwareContentsList();
		}
		return contents;
	}

	@Override
	public EList<Adapter> eAdapters() {
		if (eAdapters == null) {
			eAdapters = new EAdapterList<Adapter>(this) {
				@Override
				protected void didRemove(int index, Adapter oldObject) {
					boolean wasRemoving = removingAdapters;
					try {
						removingAdapters = true;
						super.didRemove(index, oldObject);
					} finally {
						removingAdapters = wasRemoving;
					}
				}
			};
		}
		return eAdapters;
	}

	/**
	 * Populate the contents list from the serialized type data of an {@link IEObjectDescription}. See
	 * {@link #isLoadedFromDescription()} for details on resources that are being loaded from a description.
	 *
	 * @param description
	 *            the description that carries the type data in its user data
	 */
	public boolean loadFromDescription(IResourceDescription description) {
		if (isLoaded)
			throw new IllegalStateException("Resource was already loaded");

		boolean wasDeliver = eDeliver();
		try {
			eSetDeliver(false);
			ModuleAwareContentsList theContents = (ModuleAwareContentsList) getContents();
			if (!theContents.isEmpty())
				throw new IllegalStateException("There is already something in the contents list: " + theContents);
			InternalEObject astProxy = (InternalEObject) N4JSFactory.eINSTANCE.createScript();
			astProxy.eSetProxyURI(URI.createURI("#" + AST_PROXY_FRAGMENT));
			theContents.sneakyAdd(astProxy);

			boolean didLoadModule = false;
			Iterable<IEObjectDescription> modules = description.getExportedObjectsByType(TypesPackage.Literals.TMODULE);
			for (IEObjectDescription module : modules) {
				TModule deserializedModule = UserdataMapper.getDeserializedModuleFromDescription(module, getURI());
				if (deserializedModule != null) {
					theContents.sneakyAdd(deserializedModule);
					didLoadModule = true;
					break;
				}
			}
			// TODO: It is possible that TModule is null (e.g. if a module becomes invalid and thus
			// ResourceDescriptionWithoutUserData is created and stored in the index).
			// In that case, contents has an AST proxy without TModule. Is this an allowed state??
			if (didLoadModule) {
				fullyInitialized = true;
				// TModule loaded from index had been fully post-processed prior to serialization
				fullyPostProcessed = true;
				return true;
			} else {
				return false;
			}
		} finally {
			eSetDeliver(wasDeliver);
		}
	}

	/**
	 * Returns true iff the receiving resource is in the intermediate state of having the module loaded from the index
	 * (or created in some other way) but the AST is still unloaded, i.e. the contents list contains an
	 * {@link #isASTProxy(EObject) AST proxy} at index 0 and a non-proxy TModule at index 1.
	 * <p>
	 * Some notes:
	 * <ol>
	 * <li>here, "loaded from description" means "loaded from IResourceDescription / IEObjectDescription" or "loaded
	 * from Xtext index".
	 * <li>once the resource is fully loaded, i.e. also the AST has been loaded, this method will return {@code false}.
	 * <li>while in the loaded-from-description state, the {@link #isLoaded() isLoaded} flag may be {@code true} or
	 * {@code false}. This means, from the outside, such a resource may seem to be loaded or unloaded.
	 * </ol>
	 * Thus, an N4JSResource has 4 states and the following table shows what the {@link #getContents() contents} list
	 * contains in each of those states:
	 * <!-- @formatter:off -->
	 * <table border=1>
	 * <tr><th colspan=2 rowspan=2></th><th colspan=2>isLoadedFromDescription</th></tr>
	 * <tr><th>false</th><th>true</th></tr>
	 * <tr><th rowspan=2>isLoaded</th><th>false</th><td>empty</td><td>AST proxy + TModule</td></tr>
	 * <tr><th>true</th><td>AST + TModule</td><td>AST proxy + TModule</td></tr>
	 * </table>
	 * <!-- @formatter:on -->
	 * Note how the {@link #isLoaded() isLoaded} flag does not have any impact on the resource's actual contents in case
	 * {@code isLoadedFromDescription} is {@code true}.
	 */
	public boolean isLoadedFromDescription() {
		final Script script = getScript();
		final TModule module = getModule();
		return script != null && module != null && isASTProxy(script) && !module.eIsProxy();
	}

	/**
	 * Adds just a check, that a not loaded resource is not allowed to be saved.
	 */
	@Override
	public void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		if (!isLoaded()) {
			throw new IllegalStateException("Attempt to save a resource that was not loaded: " + getURI());
		}
		super.doSave(outputStream, options);
	}

	/**
	 *
	 * Discards the current content of the resource, sets the parser result as first slot, installs derived state (this
	 * will build up the second slot again) and sends notifications that proxies have been resolved. The resource will
	 * be even loaded if its already marked as loaded.
	 *
	 * @param object
	 *            the object which resource should be loaded
	 * @return the loaded/resolved object
	 */
	// TODO ?jvp only called from ModuleAwareContentsList, which is the contents, isn't it? Thus, contents can never be
	// null here? Can it be empty?
	private EObject demandLoadResource(EObject object) {
		List<EObject> discardedState = null;
		try {
			discardedState = Lists.newArrayList(discardStateFromDescription());
			if (contents != null && !contents.isEmpty()) {
				discardedState.add(0, contents.basicGet(0));
				((ModuleAwareContentsList) contents).sneakyClear();
			}
			eSetDeliver(false);

			if (isLoaded) {
				// Loads the resource even its marked as being already loaded
				isLoaded = false;
			}
			superLoad(null);

			eSetDeliver(true);
			EObject result = getParseResult().getRootASTElement();
			if (contents != null && contents.isEmpty()) {
				((ModuleAwareContentsList) contents).sneakyAdd(0, result);
				forceInstallDerivedState(false);
			} else {
				installDerivedState(false);
			}

			for (int i = 0; i < discardedState.size(); i++) {
				notifyProxyResolved(i, discardedState.get(i));
			}
			fullyPostProcessed = false;
			return result;
		} catch (IOException ioe) {
			if (discardedState != null) {
				for (int i = 0; i < discardedState.size(); i++) {
					((ModuleAwareContentsList) contents).sneakyAdd(i, discardedState.get(i));
				}
			}
			Throwable cause = ioe.getCause();
			if (cause instanceof CoreException) {
				IStatus status = ((CoreException) cause).getStatus();
				if (IResourceStatus.RESOURCE_NOT_FOUND == status.getCode()) {
					return object;
				}
			}
			logger.error("Error in demandLoadResource for " + getURI(), ioe);
			return object;
		}
	}

	@SuppressWarnings("restriction")
	private void superLoad(Map<?, ?> options) throws IOException {
		super.load(options);
	}

	private void forceInstallDerivedState(boolean preIndexingPhase) {
		if (!isLoaded)
			throw new IllegalStateException("The resource must be loaded, before installDerivedState can be called.");
		fullyInitialized = false;
		isInitializing = false;
		super.installDerivedState(preIndexingPhase);
	}

	/**
	 * Creates a custom notification and sends it for proxy and loaded object. Registers adapters to loaded object.
	 *
	 * @param idx
	 *            index in the contents list (first or second slot)
	 * @param oldProxy
	 *            the proxified object before being loaded
	 */
	protected void notifyProxyResolved(int idx, EObject oldProxy) {
		if (eNotificationRequired() && idx < contents.size()) {
			EObject newObject = contents.basicGet(idx);
			Notification notification = new NotificationImpl(Notification.RESOLVE, oldProxy, newObject) {
				@Override
				public Object getNotifier() {
					return N4JSResource.this;
				}

				@Override
				public int getFeatureID(Class<?> expectedClass) {
					return RESOURCE__CONTENTS;
				}
			};
			eNotify(notification);
			for (Adapter adapter : eAdapters()) {
				if (adapter instanceof EContentAdapter && !newObject.eAdapters().contains(adapter)) {
					newObject.eAdapters().add(adapter);
				}
			}
		}
	}

	/**
	 * See {@link ModuleAwareContentsList#sneakyAdd(EObject)}.
	 */
	public void sneakyAddToContent(EObject object) {
		((ModuleAwareContentsList) contents).sneakyAdd(object);
	}

	/**
	 * Overridden to make sure that the cache is initialized during {@link #isLoading() loading}.
	 */
	@Override
	protected void updateInternalState(IParseResult newParseResult) {
		setParseResult(newParseResult);
		if (newParseResult.getRootASTElement() != null && !getContents().contains(newParseResult.getRootASTElement())) {
			sneakyAddToContent(newParseResult.getRootASTElement());
		}
		reattachModificationTracker(newParseResult.getRootASTElement());
		clearErrorsAndWarnings();
		addSyntaxErrors();
		doLinking();

		// make sure that the cache adapter is installed on this resource
		IResourceScopeCache cache = getCache();
		if (cache instanceof OnChangeEvictingCache) {
			((OnChangeEvictingCache) cache).getOrCreate(this);
		}
	}

	/**
	 * Minor optimization, do no lazily create warnings and error objects.
	 */
	@Override
	protected void clearErrorsAndWarnings() {
		if (warnings != null)
			warnings.clear();
		if (errors != null)
			errors.clear();
	}

	/**
	 * Ensures that state is discarded before loading (i.e. the second slot is unloaded).
	 */
	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		if (contents != null && !contents.isEmpty())
			discardStateFromDescription();
		super.doLoad(inputStream, options);
	}

	@Override
	protected void doUnload() {
		aboutToBeUnloaded = false;
		super.doUnload();

		// These are cleared when linking takes place., but we eagerly clear them here as a memory optimization.
		clearLazyProxyInformation();
	}

	/**
	 * Unloads the AST, but leaves the type model intact. Calling this method puts this resource into the same state as
	 * if it was loaded from the index.
	 *
	 * <ul>
	 * <li>The AST is discarded and all references to it are proxified.</li>
	 * <li>The parse result (node model) is set to <code>null</code>.</li>
	 * <li>All errors and warnings are cleared.</li>
	 * <li>The flags are set as follows:
	 * <ul>
	 * <li><code>fullyInitialized</code> remains unchanged</li>
	 * <li><code>fullyPostProcessed</code> is set to the same value as <code>fullyInitialized</code></li>
	 * <li><code>aboutToBeUnloaded</code> is <code>false</code></li>
	 * <li><code>isInitializing</code> is <code>false</code></li>
	 * <li><code>isLoading</code> is <code>false</code></li>
	 * <li><code>isLoaded</code> is <code>false</code></li>
	 * <li><code>isPostProcessing</code> is <code>false</code></li>
	 * <li><code>isUpdating</code> is <code>false</code></li>
	 * <li><code>isLoadedFromStorage</code> is unchanged due to API restrictions</li>
	 * </ul>
	 * </li>
	 * <li>Finally, all lazy proxy information is cleared by calling {@link #clearLazyProxyInformation()}.</li>
	 * </ul>
	 * Calling this method takes the resources either to the same state as if it was just created, or to the same state
	 * as if it was just loaded from a resource description, depending on which state the resource is in.
	 * <ul>
	 * <li>If the resource was just <b>created</b>, then it will remain so.</li>
	 * <li>If the resource was <b>loaded</b>, then it will be taken back to the <b>created</b> state.</li>
	 * <li>If the resource was <b>initialized</b>, then it will be taken to the <b>loaded from description</b>
	 * state.</li>
	 * <li>If the resource was <b>fully processed</b>, then it will be taken to the <b>loaded from description</b>
	 * state.</li>
	 * <li>If the resource was <b>loaded from description</b>, then it will remain so.</li>
	 * </ul>
	 */
	public void unloadAST() {
		if (getScript() == null || getScript().eIsProxy()) {
			// We are either freshly created and not loaded or we are loaded from resource description and thus already
			// have an AST proxy.
			return;
		}

		// Discard AST and proxify all references.
		discardAST();

		// Discard the parse result (node model).
		setParseResult(null);

		// Clear errors and warnings.
		getErrors().clear();
		getWarnings().clear();

		fullyPostProcessed = fullyInitialized;
		aboutToBeUnloaded = false;
		isInitializing = false;
		isLoading = false;
		isLoaded = false;
		isPostProcessing = false;
		isUpdating = false;

		// We cannot call this method because it is not API. We leave this comment as documentation that the flag
		// isLoadedFromStorage should be false at this point.
		// setIsLoadedFromStorage(false);

		// These are cleared when linking takes place., but we eagerly clear them here as a memory optimization.
		clearLazyProxyInformation();
	}

	/**
	 * Sends a is loaded notification if the content is not empty and sets the resource being fully initialized but do
	 * not actually invoke load. Load is only called when the content is empty. This behavior prevents loading the
	 * resource when e.g. calling EcoreUtil.resolve which would try to load the resource as its first slot is marked as
	 * proxy.
	 */
	@Override
	public void load(Map<?, ?> options) throws IOException {
		if (contents != null && !contents.isEmpty()) {
			Notification notification = setLoaded(true);
			if (notification != null) {
				eNotify(notification);
			}
			setModified(false);
			fullyInitialized = contents.size() > 1;
		} else {
			superLoad(options);
		}
	}

	/**
	 * To prepare unloading.
	 */
	public void forceSetLoaded() {
		isLoaded = true;
		aboutToBeUnloaded = true;
	}

	/**
	 * Discard the AST and proxify all referenced nodes. Does nothing if the AST is already unloaded.
	 */
	protected void discardAST() {
		EObject script = getScript();
		if (script != null && !script.eIsProxy()) {

			// Create a proxy for the AST.
			InternalEObject scriptProxy = (InternalEObject) EcoreUtil.create(script.eClass());
			scriptProxy.eSetProxyURI(EcoreUtil.getURI(script));

			TModule module = null;
			ModuleAwareContentsList theContents = (ModuleAwareContentsList) contents;
			if (isFullyInitialized()) {
				module = getModule();
				if (module != null && !module.eIsProxy()) {
					proxifyASTReferences(module);
					module.setAstElement(scriptProxy);
				}
			}

			// Unload the AST.
			unloadElements(theContents.subList(0, 1));

			theContents.sneakyClear();
			theContents.sneakyAdd(scriptProxy);

			if (module != null) {
				theContents.sneakyAdd(module);
			}

			getCache().clear(this);
		}
	}

	private void proxifyASTReferences(EObject object) {
		if (object instanceof SyntaxRelatedTElement) {
			SyntaxRelatedTElement element = (SyntaxRelatedTElement) object;
			EObject astElement = element.getAstElement();
			if (astElement != null && !astElement.eIsProxy()) {
				InternalEObject proxy = (InternalEObject) EcoreUtil.create(astElement.eClass());
				proxy.eSetProxyURI(EcoreUtil.getURI(astElement));
				element.setAstElement(proxy);
			}
		}

		for (EObject child : object.eContents()) {
			proxifyASTReferences(child);
		}
	}

	/**
	 * Unloads the {@link TModule} slot (second slot in resource).
	 *
	 * @return list of unloaded slots, will be only one element as now all types are aggregated under TModule.
	 */
	protected List<EObject> discardStateFromDescription() {
		ModuleAwareContentsList theContents = (ModuleAwareContentsList) contents;
		if (contents != null && !theContents.isEmpty()) {
			List<EObject> toBeUnloaded = theContents.subList(1, theContents.size());
			unloadElements(toBeUnloaded);
			return toBeUnloaded;
		}
		return Collections.emptyList();
	}

	private void unloadElements(List<EObject> toBeUnloaded) {
		for (EObject object : toBeUnloaded) {
			getUnloader().unloadRoot(object);
		}
	}

	/**
	 * If this resource contains an AST proxy a custom URI fragment calculation is provided. This prevents registering
	 * an adapter that later would trigger loading the resource, which we do not want.
	 */
	@Override
	public String getURIFragment(EObject eObject) {
		if (eDeliver()) {
			if (contents != null && !contents.isEmpty() && isASTProxy(contents.basicGet(0))) {
				return defaultGetURIFragment(eObject);
			}
			return super.getURIFragment(eObject);
		} else {
			return defaultGetURIFragment(eObject);
		}
	}

	/**
	 * Invoked from {@link ProxyResolvingEObjectImpl#eResolveProxy(InternalEObject)} whenever an EMF proxy inside an
	 * N4JSResource is being resolved.
	 *
	 * @param proxy
	 *            the proxy to resolve.
	 * @param objectContext
	 *            the {@code EObject} contained in this resource that holds the given proxy.
	 */
	@Override
	public EObject doResolveProxy(InternalEObject proxy, EObject objectContext) {
		final URI targetUri = proxy.eProxyURI();
		final boolean isLazyLinkingProxy = getEncoder().isCrossLinkFragment(this, targetUri.fragment());
		if (!isLazyLinkingProxy) {
			// we have an ordinary EMF proxy (not one of Xtext's lazy linking proxies) ...
			final ResourceSet resSet = getResourceSet();
			final URI targetResourceUri = targetUri.trimFragment();
			final String targetFileExt = targetResourceUri.fileExtension();
			if (N4JSGlobals.N4JS_FILE_EXTENSION.equals(targetFileExt)
					|| N4JSGlobals.N4JSD_FILE_EXTENSION.equals(targetFileExt)
					|| N4JSGlobals.N4JSX_FILE_EXTENSION.equals(targetFileExt)) {

				// proxy is pointing into an .n4js or .n4jsd file ...
				// check if we can work with the TModule from the index or if it is mandatory to load from source
				if (canResolveFromDescription(targetUri)) {

					final String targetFragment = targetUri.fragment();
					final Resource targetResource = resSet.getResource(targetResourceUri, false);

					// special handling #1:
					// if targetResource is not loaded yet, try to load it from index first
					if (targetResource == null) {
						if (targetFragment != null
								&& (targetFragment.equals("/1") || targetFragment.startsWith("/1/"))) {
							// uri points to a TModule element in a resource not yet contained in our resource set
							// --> try to load target resource from index
							final IResourceDescriptions index = n4jsCore.getXtextIndex(resSet);
							final IResourceDescription resDesc = index.getResourceDescription(targetResourceUri);
							if (resDesc != null) {
								// next line will add the new resource to resSet.resources
								n4jsCore.loadModuleFromIndex(resSet, resDesc, false);
							}
						}
					}
				}
				// standard behavior:
				// obtain target EObject from targetResource in the usual way
				// (might load targetResource from disk if it wasn't loaded from index above)
				final EObject targetObject = resSet.getEObject(targetUri, true);
				// special handling #2:
				// if targetResource exists, make sure it is post-processed *iff* this resource is post-processed
				// (only relevant in case targetResource wasn't loaded from index, because after loading from index it
				// is always marked as fullyPostProcessed==true)
				if (targetObject != null && (this.isProcessing() || this.isFullyProcessed())) {
					final Resource targetResource2 = targetObject.eResource();
					if (targetResource2 instanceof N4JSResource) {
						// no harm done, if already running/completed
						((N4JSResource) targetResource2).performPostProcessing();
					}
				}
				// return resolved target object
				return targetObject != null ? targetObject : proxy; // important: return proxy itself if unresolvable!
			}
		}
		// we will get here if
		// a) we have an Xtext lazy linking proxy or
		// b) targetUri points to an n4ts resource or some other, non-N4JS resource
		// --> above special handling not required, so just apply EMF's default resolution behavior
		return EcoreUtil.resolve(proxy, this);
	}

	/**
	 * @param targetUri
	 *            the uri to be resolved
	 * @return true, if the referenced object may come from the the index TModule.
	 */
	private boolean canResolveFromDescription(URI targetUri) {
		return canLoadFromDescriptionHelper.canLoadFromDescription(targetUri.trimFragment(), getResourceSet());
	}

	/**
	 * Copied from {@link ResourceImpl#getEObjectForURIFragmentRootSegment(String)} only differs, that instead of
	 * getContent contents is accessed directly.
	 */
	@Override
	protected EObject getEObjectForURIFragmentRootSegment(String uriFragmentRootSegment) {
		if (contents != null) {
			if (isASTProxy(contents.basicGet(0))) {
				int position = 0;
				if (uriFragmentRootSegment.length() > 0) {
					try {
						// e.g. uriFragmentRootSegment could something like /1
						position = Integer.parseInt(uriFragmentRootSegment);
					} catch (NumberFormatException exception) {
						throw new WrappedException(exception);
					}
				}
				// avoid invocation of getContent
				if (position < contents.size() && position >= 1) {
					return contents.get(position);
				}
				if (position >= 1 && isLoaded && isASTProxy(contents.basicGet(0)) && contents.size() == 1) {
					// requested position exceeds contents length
					// apparently we have an astProxy at index 0 but no module
					// was deserialized from the index
					// try to obtain the module from a freshly loaded ast

					// Note: this would be a good place to track when a proxified AST is being reloaded.
					contents.get(0);
				}
			}
		}
		return super.getEObjectForURIFragmentRootSegment(uriFragmentRootSegment);
	}

	/**
	 * We don't use a {@link IFragmentProvider} here. The implementation is a complete copied from
	 * {@link ResourceImpl#getURIFragment}
	 *
	 * @param eObject
	 *            the object the URI fragment should be calculated for.
	 * @return the calculated URI fragment
	 */
	private String defaultGetURIFragment(EObject eObject) {
		// Copied from ResourceImpl.getURIFragment to avoid the caching
		// mechanism which will add a content
		// adapter which in turn will resolve / load the resource (while the
		// purpose of all the code is to
		// avoid resource loading)
		InternalEObject internalEObject = (InternalEObject) eObject;
		if (internalEObject.eDirectResource() == this || unloadingContents != null
				&& unloadingContents.contains(internalEObject)) {
			return "/" + getURIFragmentRootSegment(eObject);
		} else {
			SegmentSequence.Builder builder = SegmentSequence.newBuilder("/");

			boolean isContained = false;
			for (InternalEObject container = internalEObject
					.eInternalContainer(); container != null; container = internalEObject
							.eInternalContainer()) {
				builder.append(container.eURIFragmentSegment(internalEObject.eContainingFeature(), internalEObject));
				internalEObject = container;
				if (container.eDirectResource() == this || unloadingContents != null
						&& unloadingContents.contains(container)) {
					isContained = true;
					break;
				}
			}

			if (!isContained) {
				return "/-1";
			}

			builder.append(getURIFragmentRootSegment(internalEObject));
			builder.append("");
			builder.reverse();

			// This comment also resides in ResourceImpl.getURIFragment:
			// Note that we convert it to a segment sequence because the
			// most common use case is that callers of this method will call
			// URI.appendFragment.
			// By creating the segment sequence here, we ensure that it's
			// found in the cache.
			//
			return builder.toSegmentSequence().toString();
		}
	}

	/**
	 *
	 * @param object
	 *            the EObject to check
	 * @return true, if the given object is a proxy and its fragment starts with {@link N4JSResource#AST_PROXY_FRAGMENT}
	 *         .
	 */
	protected boolean isASTProxy(EObject object) {
		if (object.eIsProxy()) {
			String fragment = EcoreUtil.getURI(object).fragment();
			return fragment.equals(AST_PROXY_FRAGMENT);
		}
		return false;
	}

	@Override
	public Triple<EObject, EReference, INode> getLazyProxyInformation(int idx) {
		// note: following line was copied from the old index-URI implementation (the one that used field "uris")
		// to make the new implementation behave as the old one; whether doing a demand load here actually
		// makes sense remains to be reconsidered (see IDEBUG-257 and IDEBUG-233) ...
		contents.get(0); // trigger demand load if necessary
		return super.getLazyProxyInformation(idx);
	}

	/**
	 * This is aware of warnings from the {@link N4JSStringValueConverter}.
	 *
	 * Issues from the parser are commonly treated as errors but here we want to create a warning.
	 */
	@Override
	protected void addSyntaxErrors() {
		if (isValidationDisabled())
			return;
		// EList.add unnecessarily checks for uniqueness by default
		// so we use #addUnique below to save some CPU cycles for heavily broken
		// models
		BasicEList<Diagnostic> errorList = (BasicEList<Diagnostic>) getErrors();
		BasicEList<Diagnostic> warningList = (BasicEList<Diagnostic>) getWarnings();

		for (INode error : getParseResult().getSyntaxErrors()) {
			XtextSyntaxDiagnostic diagnostic = createSyntaxDiagnostic(error);
			String code = diagnostic.getCode();
			if (AbstractN4JSStringValueConverter.WARN_ISSUE_CODE.equals(code)
					|| RegExLiteralConverter.ISSUE_CODE.equals(code)
					|| LegacyOctalIntValueConverter.ISSUE_CODE.equals(code)) {
				warningList.addUnique(diagnostic);
			} else if (!InternalSemicolonInjectingParser.SEMICOLON_INSERTED.equals(code)) {
				errorList.addUnique(diagnostic);
			}
		}
	}

	private XtextSyntaxDiagnostic createSyntaxDiagnostic(INode error) {
		SyntaxErrorMessage syntaxErrorMessage = error.getSyntaxErrorMessage();
		if (org.eclipse.xtext.diagnostics.Diagnostic.SYNTAX_DIAGNOSTIC_WITH_RANGE.equals(syntaxErrorMessage
				.getIssueCode())) {
			String[] issueData = syntaxErrorMessage.getIssueData();
			if (issueData.length == 1) {
				String data = issueData[0];
				int colon = data.indexOf(':');
				return new XtextSyntaxDiagnosticWithRange(error, Integer.valueOf(data.substring(0, colon)),
						Integer.valueOf(data.substring(colon + 1)), null) {
					@Override
					public int getLine() {
						return getNode().getTotalStartLine();
					}
				};
			}
		}
		return new XtextSyntaxDiagnostic(error);
	}

	// FIXME the following method should no longer be required once TypingASTWalker is fully functional
	@Override
	protected EObject handleCyclicResolution(Triple<EObject, EReference, INode> triple) throws AssertionError {
		// Don't throw exception for cyclic resolution of IdentifierRef.id or PropertyAccess.property
		// since this is currently unavoidable because type system and scoping don't work together
		// but have independent control flow logic.
		// This JS snippet will cause trouble:
		//
		// function(a){ return a.b }
		// System.err.println("CYCLIC RESOLUTION FOR: " + NodeModelUtils.getTokenText(triple.getThird()));
		if (N4JSPackage.Literals.IDENTIFIER_REF__ID == triple.getSecond()
				|| N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY == triple.getSecond()) {
			return null;
		}
		return super.handleCyclicResolution(triple);
	}

	/**
	 * Convenience method to fully resolve all lazy cross-references and perform post-processing on the containing
	 * N4JSResource of 'object'. Does nothing if 'object' is not contained in an N4JSResource.
	 */
	public static final void postProcessContainingN4JSResourceOf(EObject object) {
		if (object != null)
			postProcess(object.eResource());
	}

	/**
	 * Convenience method to fully resolve all lazy cross-references and perform post-processing on the N4JSResource
	 * 'resource'. Does nothing if 'resource' is <code>null</code> or not an N4JSResource.
	 */
	public static final void postProcess(Resource resource) {
		if (resource instanceof N4JSResource && resource.isLoaded())
			((N4JSResource) resource).performPostProcessing();
	}

	@Override
	public void performPostProcessing(CancelIndicator cancelIndicator) {
		// make sure post-processing is never performed in pre-linking phase
		// FIXME should never happen -> better throw an exception here if isPreLinkingPhase===true?
		final TModule module = getModule();
		final boolean isPreLinkingPhase = module != null && module.isPreLinkingPhase();
		if (!isPreLinkingPhase) {
			super.performPostProcessing(cancelIndicator);
		}
	}

	@Override
	public void resolveLazyCrossReferences(CancelIndicator mon) {
		// called from builder before resource descriptions are created + called from validator
		final Script script = getScriptResolved(); // need to be called before resolve() since that one injects a proxy
		// at resource.content[0]
		super.resolveLazyCrossReferences(mon);
		if (script != null) {
			// FIXME freezing of used imports tracking can/should now be moved to N4JSPostProcessor or ASTProcessor
			EcoreUtilN4.doWithDeliver(false,
					// freezing Tracking of used imports in OriginAwareScope
					() -> script.setFlaggedUsageMarkingFinished(true),
					script);
		}
	}

	/**
	 * Returns the script. May return <code>null</code> or a proxy if the script isn't fully initialized yet. It is safe
	 * to call this method at any time.
	 */
	public Script getScript() {
		return contents != null && !contents.isEmpty() ? (Script) contents.basicGet(0) : null;
	}

	/**
	 * Returns the script or <code>null</code> if not available. If the script is a proxy, it will be resolved. Since
	 * this method may change the receiving resource, it should be used with care and cannot be called at all times.
	 */
	public Script getScriptResolved() {
		return getContents().size() >= 1 ? (Script) getContents().get(0) : null;
	}

	/**
	 * Returns the module. May return <code>null</code> or a TModule with {@link TModule#isPreLinkingPhase()
	 * preLinkingPhase}==true if the module isn't fully initialized yet. It is safe to call this method at any time.
	 */
	public TModule getModule() {
		return getContents().size() >= 2 ? (TModule) getContents().get(1) : null;
	}

	/**
	 * Retrieves the TModule contained in a resource and tries to avoid resolution of AST if possible.
	 *
	 * @return the TModule contained in the resource, or null if no TModule has been found.
	 */
	public static TModule getModule(Resource contextResource) {
		if (contextResource == null) {
			return null;
		}
		List<EObject> resourceContents = contextResource.getContents();
		for (int i = resourceContents.size() - 1; i >= 0; i--) {
			EObject candidate = resourceContents.get(i);
			if (candidate instanceof TModule) {
				return (TModule) candidate;
			}
			if (candidate instanceof Script) {
				return ((Script) candidate).getModule();
			}
		}
		return null;
	}

	/**
	 * Only called from one place; see there why this is required.
	 *
	 * FIXME clean up this method (remove or find better solution)
	 */
	public void clearResolving() {
		resolving.clear();
	}

	@Override
	protected void createAndAddDiagnostic(Triple<EObject, EReference, INode> triple) {
		// check if unresolved reference is special case handled by {@link N4JSScopingDiagnostician}
		DiagnosticMessage scopingDiagnostic = scopingDiagnostician.getMessageFor(triple.getFirst(), triple.getSecond(),
				triple.getThird());
		// if so, use more specific diagnostic message
		if (null != scopingDiagnostic) {
			List<Diagnostic> list = getDiagnosticList(scopingDiagnostic);
			Diagnostic diagnostic = createDiagnostic(triple, scopingDiagnostic);
			if (!list.contains(diagnostic)) {
				list.add(diagnostic);
			}
		} else {
			// if not, use default generic scoping message
			super.createAndAddDiagnostic(triple);
		}
	}
}
