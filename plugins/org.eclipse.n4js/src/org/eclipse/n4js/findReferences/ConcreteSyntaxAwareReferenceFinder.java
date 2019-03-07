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
package org.eclipse.n4js.findReferences;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.findReferences.SimpleResourceAccess;
import org.eclipse.n4js.ts.findReferences.TargetURIKey;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.findReferences.ReferenceFinder;
import org.eclipse.xtext.findReferences.TargetURIs;
import org.eclipse.xtext.linking.impl.IllegalNodeException;
import org.eclipse.xtext.linking.impl.LinkingHelper;
import org.eclipse.xtext.linking.lazy.LazyURIEncoder;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A reference finder that will check the concrete syntax in the document before it attempts to resolve a proxy.
 */
@SuppressWarnings("restriction")
@Singleton
public class ConcreteSyntaxAwareReferenceFinder extends ReferenceFinder {

	@Inject
	private TargetURIKey keys;

	@Inject
	private LazyURIEncoder uriEncoder;

	@Inject
	private LinkingHelper linkingHelper;

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Override
	protected void findReferencesInDescription(TargetURIs targetURIs, IResourceDescription resourceDescription,
			IResourceAccess resourceAccess, Acceptor acceptor, IProgressMonitor monitor) {
		TargetURIKey.Data findReferencesData = keys.getData(targetURIs, resourceAccess);
		if (monitor.isCanceled())
			throw new OperationCanceledException();

		final Set<QualifiedName> typesOrModulesToFind = findReferencesData.getTypesOrModulesToFind();
		if (typesOrModulesToFind.isEmpty()) {
			return;
		}
		Iterable<QualifiedName> importedNames = resourceDescription.getImportedNames();
		// scenario 1: imported names from index
		// TODO check if imported names from editors are returned as a set?
		// maybe we need another check for instanceof SortedSet<?>
		if (importedNames instanceof List<?>) {
			List<QualifiedName> sorted = (List<QualifiedName>) importedNames;
			List<QualifiedName> searchMe = sorted;
			// Optimize search
			for (QualifiedName typeOrModuleToFind : typesOrModulesToFind) {
				int insertionIndex = Collections.binarySearch(searchMe, typeOrModuleToFind);
				if (insertionIndex >= 0) {
					resourceAccess.readOnly(
							resourceDescription.getURI(),
							(resourceSet) -> {
								findReferences(targetURIs, resourceSet.getResource(resourceDescription.getURI(), true),
										acceptor, monitor);
								return null;
							});
					return;
				} else {
					int startFrom = -(insertionIndex + 1);
					if (startFrom >= sorted.size()) {
						return;
					}
					searchMe = sorted.subList(startFrom, sorted.size());
				}
			}
		} else {
			for (QualifiedName importedName : importedNames) {
				if (typesOrModulesToFind.contains(importedName)) {
					resourceAccess.readOnly(
							resourceDescription.getURI(),
							(resourceSet) -> {
								findReferences(targetURIs, resourceSet.getResource(resourceDescription.getURI(), true),
										acceptor, monitor);
								return null;
							});
					return;
				}
			}
		}
	}

	@Override
	public void findReferences(Predicate<URI> targetURIs, Resource resource, Acceptor acceptor,
			IProgressMonitor monitor) {
		// make sure data is present
		keys.getData((TargetURIs) targetURIs, new SimpleResourceAccess(resource.getResourceSet()));
		EList<EObject> astContents;
		if (resource instanceof N4JSResource) {
			// In case of N4JSResource, we search only in the AST but NOT in TModule tree!
			Script script = (Script) ((N4JSResource) resource).getContents().get(0);
			astContents = new BasicEList<>();
			astContents.add(script);
		} else {
			astContents = resource.getContents();
		}
		for (EObject content : astContents) {
			findReferences(targetURIs, content, acceptor, monitor);
		}
	}

	@Override
	protected boolean doProcess(EReference reference, Predicate<URI> targetURISet) {
		return ((TargetURIs) targetURISet).getUserData(TargetURIKey.KEY)
				.isEReferenceTypeApplicable(reference.getEReferenceType());
	}

	@Override
	protected EObject toValidInstanceOrNull(Resource resource, Predicate<URI> targetURIs, EObject value) {
		EObject result = value;
		if (result.eIsProxy()) {
			URI proxyURI = EcoreUtil.getURI(result);
			if (uriEncoder.isCrossLinkFragment(resource, proxyURI.fragment())) {
				INode node = uriEncoder.decode(resource, proxyURI.fragment()).getThird();
				try {
					String string = linkingHelper.getCrossRefNodeAsString(node, true);
					if (((TargetURIs) targetURIs).getUserData(TargetURIKey.KEY).isMatchingConcreteSyntax(string)) {
						result = resolveInternalProxy(value, resource);
					} else {
						result = null;
					}
				} catch (IllegalNodeException ine) {
					// illegal nodes exist in broken ASTs
					// fired in linkingHelper.getCrossRefNodeAsString(...)
					result = null;
				}
			} else {
				result = resolveInternalProxy(value, resource);
			}
		}
		return result;
	}

	/**
	 * This method overrides Xtext's standard behavior because we need special handling for composed members. The places
	 * with custom behavior are highlighted.
	 */
	@Override
	protected void findLocalReferencesFromElement(
			Predicate<URI> targetURIs,
			EObject sourceCandidate,
			Resource localResource,
			Acceptor acceptor) {
		URI sourceURI = null;
		if (doProcess(sourceCandidate, targetURIs)) {
			for (EReference ref : sourceCandidate.eClass().getEAllReferences()) {
				Object value = sourceCandidate.eGet(ref, false);
				if (sourceCandidate.eIsSet(ref) && value != null) {
					if (ref.isContainment()) {
						if (ref.isMany()) {
							@SuppressWarnings("unchecked")
							InternalEList<EObject> contentList = (InternalEList<EObject>) value;
							for (int i = 0; i < contentList.size(); ++i) {
								EObject childElement = contentList.basicGet(i);
								if (!childElement.eIsProxy()) {
									findLocalReferencesFromElement(targetURIs, childElement, localResource, acceptor);
								}
							}
						} else {
							EObject childElement = (EObject) value;
							if (!childElement.eIsProxy()) {
								findLocalReferencesFromElement(targetURIs, childElement, localResource, acceptor);
							}
						}
					} else if (!ref.isContainer()
							// CUSTOM BEHAVIOR: Ignore defined corresponding TModule nodes of AST nodes because we do
							// not want
							// declarations to be part of the find ref result.
							// These cases correspond to those in {@link InferredElements} + the TStructMember case !
							&& ref != N4JSPackage.Literals.SCRIPT__MODULE
							&& ref != N4JSPackage.Literals.TYPE_DEFINING_ELEMENT__DEFINED_TYPE
							&& ref != N4JSPackage.Literals.N4_FIELD_DECLARATION__DEFINED_FIELD
							&& ref != N4JSPackage.Literals.GETTER_DECLARATION__DEFINED_GETTER
							&& ref != N4JSPackage.Literals.SETTER_DECLARATION__DEFINED_SETTER
							&& ref != N4JSPackage.Literals.N4_ENUM_LITERAL__DEFINED_LITERAL
							&& ref != N4JSPackage.Literals.EXPORTED_VARIABLE_DECLARATION__DEFINED_VARIABLE
							&& ref != TypesPackage.Literals.TSTRUCT_MEMBER__DEFINED_MEMBER) {
						if (doProcess(ref, targetURIs)) {
							if (ref.isMany()) {
								@SuppressWarnings("unchecked")
								InternalEList<EObject> values = (InternalEList<EObject>) value;
								for (int i = 0; i < values.size(); ++i) {
									EObject instanceOrProxy = toValidInstanceOrNull(localResource, targetURIs,
											values.basicGet(i));
									if (instanceOrProxy != null) {
										URI refURI = EcoreUtil2.getPlatformResourceOrNormalizedURI(instanceOrProxy);
										// CUSTOM BEHAVIOR: handle composed members
										if (referenceHasBeenFound(targetURIs, refURI, instanceOrProxy)) {
											sourceURI = (sourceURI == null)
													? EcoreUtil2.getPlatformResourceOrNormalizedURI(sourceCandidate)
													: sourceURI;
											acceptor.accept(sourceCandidate, sourceURI, ref, i, instanceOrProxy,
													refURI);
										}
									}
								}
							} else {
								EObject instanceOrProxy = toValidInstanceOrNull(localResource, targetURIs,
										(EObject) value);

								// EObject instanceOrProxy = (EObject) value;
								if (instanceOrProxy != null) {
									URI refURI = EcoreUtil2.getPlatformResourceOrNormalizedURI(instanceOrProxy);
									// CUSTOM BEHAVIOR: handle composed members
									if (referenceHasBeenFound(targetURIs, refURI, instanceOrProxy)) {
										sourceURI = (sourceURI == null) ? EcoreUtil2
												.getPlatformResourceOrNormalizedURI(sourceCandidate) : sourceURI;
										acceptor.accept(sourceCandidate, sourceURI, ref, -1, instanceOrProxy, refURI);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean referenceHasBeenFound(Predicate<URI> targetURIs, URI refURI, EObject instanceOrProxy) {
		boolean result = false;
		// If the EObject is a composed member, we compare the target URIs with the URIs of the constituent members.
		if (instanceOrProxy instanceof TMember && ((TMember) instanceOrProxy).isComposed()) {
			TMember member = (TMember) instanceOrProxy;
			if (member.isComposed()) {
				for (TMember constituentMember : member.getConstituentMembers()) {
					URI constituentReffURI = EcoreUtil2
							.getPlatformResourceOrNormalizedURI(constituentMember);
					result = result || targetURIs.apply(constituentReffURI);
				}
			}
		} else {
			// Standard case
			result = targetURIs.apply(refURI);
		}
		return result;
	}
}
