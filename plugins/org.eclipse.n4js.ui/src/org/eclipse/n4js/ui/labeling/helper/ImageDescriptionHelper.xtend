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
package org.eclipse.n4js.ui.labeling.helper

import com.google.inject.Inject
import java.util.List
import java.util.Optional
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.jface.resource.ResourceLocator
import org.eclipse.jface.viewers.IDecoration
import org.eclipse.n4js.resource.N4JSCache
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider
import org.eclipse.ui.ISharedImages
import org.eclipse.ui.PlatformUI
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.diagnostics.Severity
import org.eclipse.xtext.resource.FileExtensionProvider
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.validation.IResourceValidator
import org.eclipse.xtext.validation.Issue

/**
 * Helper class to create and cache image descriptions as triggered by
 * {@link ImageCalculationHelper}.
 * <br /><br />
 * Be sure to use convertToImageDescriptor and convertToImage provided by
 * {@link N4JSLabelProvider} as they do already some caching and registering
 * image descriptions in a image description library. So never use createImage
 * directly.
 * <br /><br />
 * It creates a {@link N4JSDecoratorRow} whenever one or more decorators are needed.
 * Unlike {@code DecorationOverlayIcon}, a {@link N4JSDecoratorRow} doesn't hold references to {@code Image}.
 * <br /><br />
 * For common images like error markers you can use
 * PlatformUI.workbench.sharedImages.getImageDescriptor instead of providing
 * your own image file. Remember, never call dispose on these images.
 * <br /><br />
 * Validation issues calculated for an element are cached in this class, but
 * maybe it would make sense to centralize this, as also the compiler needs
 * validation issues to decide whether it is allowed to compile or not.
 * As validation should only be done for N4JS elements but the label provider
 * also visits all elements in the parse tree even the keywords, that are defined
 * in n4js.xtext, so there is a check that only resources with an expected
 * file extensions (as provided by the file extension provider) are validated.
 * <br /><br />
 * Image descriptions for overlays are fetched from {@link N4JSImageDescriptionLibrary}.
 */
class ImageDescriptionHelper {
	private static String PLUGIN_ID = "org.eclipse.n4js.ui"

	@Inject
	private N4JSCache cache

	/** Injecting language specific instance of {@link FileExtensionProvider} is fine, assuming this class is always called in
	 * context of the editor, which is language specific.
	 */
	@Inject
	private FileExtensionProvider fileExtensionProvider

	@Inject
	private IResourceValidator resourceValidator

	@Inject
	private extension N4JSImageDescriptionLibrary n4jsImageDescriptionLibrary

	private N4JSLabelProvider labelProvider;

	def setLabelProvider(N4JSLabelProvider provider) {
		this.labelProvider = provider;
		n4jsImageDescriptionLibrary.setImageDescriptionHelper(this)
	}

	def getImageDescriptionLibrary() {
		return this.n4jsImageDescriptionLibrary
	}

	/**
	 * Creates an image descriptor for the given image file and adds validation decorators (ERROR or WARNING)
	 * if the given EObject has validation issues.
	 * <p>
	 * IDE-1723 A cache is used behind the scenes but it's still expensive to scan all issues for the whole resource
	 * when all we want to know is whether any exist for the AST element in question.
	 */
	def ImageDescriptor createValidationAwareImageDescriptor(EObject eo, String imageFileName) {
		val mainImageDescriptor = createSimpleImageDescriptor(imageFileName)
		val mon = (if (null !== labelProvider?.cancelIndicator) labelProvider.cancelIndicator else CancelIndicator.NullImpl);
		val optSeverity = getMaxSeverityAtOrBelow(eo, mon)
		if (optSeverity.present) {
			if (optSeverity.get == Severity.ERROR) {
				val decorator = PlatformUI.workbench.sharedImages.getImageDescriptor(ISharedImages.IMG_DEC_FIELD_ERROR)
				return createDecorationOverlayIcon(mainImageDescriptor, decorator, IDecoration.BOTTOM_LEFT)
			}
			if (optSeverity.get == Severity.WARNING) {
				val decorator = PlatformUI.workbench.sharedImages.getImageDescriptor(ISharedImages.IMG_DEC_FIELD_WARNING)
				return createDecorationOverlayIcon(mainImageDescriptor, decorator, IDecoration.BOTTOM_LEFT)
			}
		}
		mainImageDescriptor
	}

	/**
	 * Creates an decorated image with using the given main image and placing the given decorator in the
	 * corner given by the quadrant. Valid values for quadrant are IDecoration.BOTTOM_LEFT, IDecoration.BOTTOM_RIGHT,
	 * IDecoration.TOP_RIGHT, IDecoration.TOP_LEFT.
	 * <b/>
	 * This method should be used for a single decorator.
	 * For two or more decorators see {@link #createDecorationComposite} is better suited.
	 */
	def ImageDescriptor createDecorationOverlayIcon(ImageDescriptor main, ImageDescriptor decorator, int quadrant) {
		return new N4JSDecoratorRow(main, quadrant, decorator)
	}

	/**
	 * returns an ImageDescriptor to display the decorators on top-right.
	 * <b/>
	 * This method should be used for two or more decorators. For a single decorator {@link #createDecorationOverlayIcon} is better suited.
	 */
	def ImageDescriptor createDecorationComposite(ImageDescriptor main, ImageDescriptor... decorators) {
		return new N4JSDecoratorRow(main, IDecoration.TOP_RIGHT, decorators)
	}

	/**
	 * Look up all issues for the resource of the AST element, cache them,
	 * and return the maximum severity (but limited to ERROR or WARNING) for the AST element or any of its contained elements.
	 * <p>
	 * The above is achieved by lookup using the resource as key. On cache miss, the resource is validated as a whole and the issues cached.
	 * Finally those issues are selected based on the AST element's URI and severity of interest.
	 * <p>
	 * The cache is invalidated upon semantic change in the resource.
	 */
	def private Optional<Severity> getMaxSeverityAtOrBelow(EObject eo, CancelIndicator cancelIndicator) {
		if (!isN4Resource(eo.eResource) || !isCompletelyLoaded(eo.eResource as N4JSResource)) {
			return Optional.empty
		}
		val summary = getOrElseUpdateSummary(eo, cancelIndicator)
		return summary.getMaxSeverityAtOrBelow(eo)
	}

	/**
	 * The cache keeps two kinds of entries for each resource: list of issues (these are shared with the transpiler)
	 * and summary of issues (of interest to the outline view only).
	 * <p>
	 * Given a list-of-issues, preparing its summary is fast, no need to check for cancelation in between.
	 * The lookup of issue-summary is always preceded by a lookup of list-of-issues. Why? To leverage the cancelation-handling
	 * mechanism in {@link N4JSCache#getOrElseUpdateIssues}. Depending on its result, an additional get-or-else-update
	 * (this time for issue-summary) can proceed, this time without having to handle cancelations.
	 * <p>
	 * During an activation of this method the cache lock is held, ie the transpiler can't get issues in the meantime.
	 * Same goes in the other direction.
	 */
	def private IssueSummary getOrElseUpdateSummary(EObject eo, CancelIndicator cancelIndicator) {
		val res = eo.eResource
		synchronized(cache) {
			val issues = cache.getOrElseUpdateIssues(resourceValidator, res, cancelIndicator)
			if ((null === issues) || cancelIndicator.isCanceled) {
				// due to cancellation the list of issues may be incomplete, thus don't store in the cache
				return IssueSummary.EMPTY_SUMMARY
			}
			val summary = cache.get("ImageDescriptionHelper-IssueSummary", res, [| IssueSummary.create(issues)])
			return summary
		}
	}

	/**
	 * "N4JSResource completely loaded" means not just TModule loaded from Xtext index, but also the resource's script is available.
	 */
	private static def boolean isCompletelyLoaded(N4JSResource res) {
		val scr = res?.script // n.b.: won't resolve script if it is a proxy
		return (scr !== null) && !(scr.eIsProxy)
	}

	/**
	 * Keeps track of errors and warnings not as issues but as their URI strings.
	 * A fresh instance of this class is populated via calls to {@link #add}.
	 * Afterwards, it's queried via {@link #getMaxSeverityAtOrBelow}.
	 */
	public static final class IssueSummary {

		public static final IssueSummary EMPTY_SUMMARY = new IssueSummary(null, null)

		/**
		 * For performance at most one issue representative is tracked for a method.
		 */
		public static def IssueSummary create(List<Issue> issues) {
			if ((null === issues) || issues.isEmpty) {
				return EMPTY_SUMMARY
			}
			val errors = deduplicated(issues, Severity.ERROR)
			val warnings = deduplicated(issues, Severity.WARNING)
			warnings.removeAll(errors)
			if (errors.isEmpty && warnings.isEmpty) {
				// observation: issues may contain only non-error, non-warning ones
				// in that case both errors and warnings are empty
				return EMPTY_SUMMARY
			}
			return new IssueSummary(asCondensedArray(errors), asCondensedArray(warnings));
		}

		/**
		 * Once sub-method issues have been collapsed as string-URIs for the method,
		 * there may be duplicate string-URIs. This method de-duplicates them.
		 */
		private static def Set<String> deduplicated(List<Issue> issues, Severity severityOfInterest) {
			val chosenIssues = issues.filter[i| i.severity === severityOfInterest && (null !== i.uriToProblem)]
			val collapsed = chosenIssues.map[i| minusBody(i.uriToProblem.toString)]
			return collapsed.toSet
		}

		/**
		 * For the purposes of displaying a warning or error decorator on the Outline view,
		 * the finer granularity we're interested in is field or method (and not for example some expression within a method).
		 */
		private def static String minusBody(String uri) {
			// why lastIndexOf? Assuming local methods, we'd like to preserve which one
			// the (at)body that appears below isn't the body of an N4JS AST element, but part of the encoding of URIs by EMF.
			val idx = uri.lastIndexOf("/@body")
			if (idx !== -1) {
				return uri.substring(0, idx)
			}
			return uri
		}

		/**
		 * A "condensed array" means null represents the empty array. As GC-friendly as it gets.
		 */
		private def static String[] asCondensedArray(Set<String> uris) {
			if (uris.isEmpty) {
				return null
			}
			val String[] result = newArrayOfSize(uris.size)
			uris.forEach[uri, idx| result.set(idx, uri) ]
			return result
		}

		private final String[] errors;
		private final String[] warnings;

		new(String[] errors, String[] warnings) {
			this.errors = errors;
			this.warnings = warnings;
		}

		private def boolean isEmpty() {
			return (null === errors) && (null === warnings)
		}

		/**
		 * Return the maximum severity (but limited to ERROR or WARNING) for the given AST element or any of its contained elements.
		 */
		private def Optional<Severity> getMaxSeverityAtOrBelow(EObject eo) {
			if (isEmpty) {
				return Optional.empty
			}
			val astElem = if(eo instanceof SyntaxRelatedTElement) eo.astElement else eo;
			if (astElem === null) {
				return Optional.empty
			}
			val uri = EcoreUtil2.getURI(astElem)
			val currentURI = uri.toString;
			if (enclosesAnyOf(currentURI, errors)) {
				return Optional.of(Severity.ERROR)
			}
			if (enclosesAnyOf(currentURI, warnings)) {
				return Optional.of(Severity.WARNING)
			}
			return Optional.empty
		}

		private static final char FORWARD_SLASH = '/';

		/**
		 * Is the candidate a prefix of some of the strings?
		 * In terms of AST nodes: does the candidate contain a problematic AST node?
		 */
		private def boolean enclosesAnyOf(String candidate, String[] problems) {
			if (null !== problems) {
				// problems.exists[] would use behind the scenes Conversions.doWrapArray,
				// a reflection-like utility. Now that we have arrays let us not regress to list performance.
				var idx = 0;
				while (idx < problems.length) {
					val problem = problems.get(idx)
					if (problem.startsWith(candidate)) {
						val clen = candidate.length
						if (problem.length === clen) {
							return true
						}
						// we need to weed out the false positives resulting from "candidate" ending in a digit
						// and "problem" extending that not by "/" but by a digit. In that case,
						// both URIs refer to different elements, not "problem" contained in "candidate"
						if (problem.charAt(clen) === FORWARD_SLASH) {
							return true
						}
					}
					idx++
				}
			}
			return false
		}

	}

	private def boolean isN4Resource(Resource res) {
		if (fileExtensionProvider.fileExtensions.contains(res?.URI?.fileExtension)) {
			return (res instanceof N4JSResource)
		}
		return false
	}

	/**
	 * returns either the original ImageDescriptor (in case of public access modifier) or an ImageDescriptor showing the access modifier on the bottom right.
	 */
	def ImageDescriptor addAccessibiltyImageDecorator(ImageDescriptor main, TypeAccessModifier typeAccessModifier) {
		switch typeAccessModifier {
			case TypeAccessModifier.PUBLIC: main
			default: {
				val decorator = switch(typeAccessModifier) {
					case TypeAccessModifier.PUBLIC_INTERNAL: createPublicInternalVisibleImageDecorator
					case TypeAccessModifier.PROJECT: createProjectVisibleImageDecorator
					case TypeAccessModifier.PRIVATE: createPrivateVisibleImageDecorator
					default: createProjectVisibleImageDecorator
				}
				createDecorationOverlayIcon(main, decorator, IDecoration.BOTTOM_RIGHT)
			}
		}
	}

	def ImageDescriptor createSimpleImageDescriptor(String imageFileName) {
		if(imageFileName !== null) {
			val existingImageDescriptor = labelProvider.asImageDescriptor(imageFileName)
			existingImageDescriptor ?: ResourceLocator.imageDescriptorFromBundle(PLUGIN_ID, "icons/" + imageFileName).orElse(null)
		} else {
			N4JSLabelProvider.getDefaultImageDescriptor
		}
	}

}
