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

import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.resource.OutdatedStateManager;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;

/**
 * A resource that manages some derived state, similar to {@link DerivedStateAwareResource}. Differences to
 * {@code DerivedStateAwareResource} are:
 * <ol>
 * <li>derived state computation may <b>not</b> resolve proxies from {@link LazyLinkingResource}; post processing may
 * resolve proxies.
 * <li>derived state computation is triggered when the resource's {@link #getContents() contents} are read; post
 * processing is performed later:
 * <ol>
 * <li>just before the first proxy is being resolved (either because client code called an EMF getter for a
 * cross-reference that contains a proxy or client code explicitly requested proxy resolution by invoking method
 * {@link #resolveLazyCrossReferences(CancelIndicator) resolveLazyCrossReferences()}, or
 * <li>when client code actively requests post-processing by calling method
 * {@link #performPostProcessing(CancelIndicator) performPostProcessing()}.
 * </ol>
 * </ol>
 * Details of what is being performed during post-processing is defined by the {@link PostProcessor} implementation in
 * use.
 */
public class PostProcessingAwareResource extends DerivedStateAwareResource {

	@Inject(optional = true)
	private PostProcessor postProcessor;

	@Inject
	private OutdatedStateManager outdatedStateManager;

	/**
	 * True iff {@link #performPostProcessing(CancelIndicator)} has been invoked and has completed successfully.
	 */
	protected volatile boolean fullyPostProcessed = false;
	/**
	 * True iff {@link #performPostProcessing(CancelIndicator)} is in progress, i.e. has been invoked but has not yet
	 * completed.
	 */
	protected volatile boolean isPostProcessing = false;

	/**
	 * Implementations of this interface are used by a {@link PostProcessingAwareResource} to perform post-processing of
	 * an EMF / Xtext resource.
	 */
	public static interface PostProcessor {
		/**
		 * Tells if the receiving post processor wants to perform its post-processing on a resource where all lazy cross
		 * references have been resolved up-front. By default, this returns <code>true</code>. Implementors may change
		 * this to return <code>false</code> in order to have full control over lazy-link resolution.
		 */
		default public boolean expectsLazyLinkResolution() {
			return true;
		}

		/**
		 * Perform post-processing of the given resource.
		 * <p>
		 * IMPORTANT: if method {@link #expectsLazyLinkResolution()} returns <code>false</code>, then this method is
		 * responsible for resolving all lazy cross-references as if method
		 * {@link LazyLinkingResource#resolveLazyCrossReferences(CancelIndicator) resolveLazyCrossReferences()} had been
		 * called and had completed; otherwise, this method can assume that lazy cross-references have been resolved
		 * before this method is called.
		 */
		public void performPostProcessing(PostProcessingAwareResource resource, CancelIndicator cancelIndicator);

		// note: so far, we do not need something like discardDerivedState() here (may be added later)
	}

	/**
	 * Returns true iff post-processing for the receiving resource has been performed and has completed successfully.
	 */
	public boolean isFullyProcessed() {
		return fullyPostProcessed;
	}

	/**
	 * Returns true iff the receiving resource is currently performing its post-processing as defined by the
	 * {@link PostProcessor} in use.
	 */
	public boolean isProcessing() {
		return isPostProcessing;
	}

	@Override
	public void discardDerivedState() {
		super.discardDerivedState();
		fullyPostProcessed = false;
	}

	@Override
	protected void doUnload() {
		super.doUnload();
		fullyPostProcessed = false;
	}

	/**
	 * Calls method {@link #performPostProcessing(CancelIndicator) performPostProcessing()} which in turn calls the
	 * super-class method, but also performs other resolutions defined by the {@link PostProcessor} is use.
	 * <p>
	 * From super class:
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	public void resolveLazyCrossReferences(CancelIndicator cancelIndicator) {
		performPostProcessing(cancelIndicator);
	}

	/**
	 * Like {@link #performPostProcessing(CancelIndicator)}, but will try to use a cancel indicator obtained from Xtext.
	 */
	public void performPostProcessing() {
		performPostProcessing(null);
	}

	/**
	 * By calling this method, client code can initiate post-processing of the receiving EMF / Xtext resource.
	 * <p>
	 * However, this will be performed automatically in most situations (e.g. proxy resolution), so it should usually
	 * not be required to call this method directly. On the other hand, this method will simply do nothing if
	 * post-processing has already been performed <u>or is currently in progress</u>, so unnecessarily calling this
	 * method should not do any major harm.
	 * <p>
	 * Will throw an exception if called on an {@link #isLoaded() unloaded} resource.
	 *
	 * @param cancelIndicator
	 *            a cancel indicator to use or <code>null</code> if none is available. If <code>null</code> this method
	 *            will try to obtain a cancel indicator from Xtext (if that fails, processing will proceed without the
	 *            use of a cancel indicator).
	 */
	public void performPostProcessing(CancelIndicator cancelIndicator) {
		if (fullyPostProcessed)
			return;
		if (!isLoaded)
			throw new IllegalStateException(
					"The resource must be loaded, before PostProcessedResource#performPostProcessing() can be called.");
		if (!fullyInitialized)
			installDerivedState(false);
		if (isLoaded && fullyInitialized && !fullyPostProcessed && !isPostProcessing) {
			// initiate post-processing ...
			try {
				isPostProcessing = true;
				if (postProcessor == null) {
					throw new IllegalStateException("post processor is null");
				}
				if (cancelIndicator == null) {
					// obtain a valid cancel indicator from Xtext
					cancelIndicator = outdatedStateManager.newCancelIndicator(this.getResourceSet());
				}
				if (postProcessor.expectsLazyLinkResolution()) {
					// in next line: call on 'super' because the method in 'this' forwards here!!
					super.resolveLazyCrossReferences(cancelIndicator);
				}
				postProcessor.performPostProcessing(this, cancelIndicator);
				// fullyPostProcessed is only set to true when performPostProcessing above is successful and does not
				// throw any exception. When a resource's fullPostProcessed flag is true, it is guaranteed that the
				// resource's ASTMetaInfoCache
				// contains cached types.
				fullyPostProcessed = true;
			} finally {
				isPostProcessing = false;
			}
		}
	}
}
