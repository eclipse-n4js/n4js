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
package org.eclipse.n4js.smith.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.n4js.smith.graph.graph.Edge;
import org.eclipse.n4js.smith.graph.graph.Graph.GraphProvider;
import org.eclipse.n4js.smith.graph.graph.GraphUtils;
import org.eclipse.n4js.smith.graph.graph.Node;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterators;

/**
 * Creates nodes and edges from an EMF {@link ResourceSet} as input.
 */
public class EMFGraphProvider implements GraphProvider {

	/**
	 * Names of elements in the built-in resources (builtin_js.n4ts and builtin_n4.n4ts) that should appear in the
	 * graph, including their contents. Names listed here should be those returned by method {@link #getName(EObject)}.
	 */
	private static final String[] SHOW_BUILT_IN = {};

	@Override
	public List<Object> getElements(Object input) {
		final List<Object> result = new ArrayList<>();
		if (input instanceof ResourceSet) {
			final ResourceSet resSet = (ResourceSet) input;
			final List<Resource> ignoredResources = new ArrayList<>();
			for (Resource res : new ArrayList<>(resSet.getResources())) {
				final boolean isFirstResource = result.isEmpty();
				// only show contents of first resource + .n4js resources
				// (to avoid showing built-in types or letting graph become to large)
				if (isFirstResource || res.getURI().toString().endsWith(".n4js")
						|| res.getURI().toString().endsWith(".n4jsd")) {
					// (1) show *all* contents of first resource + .n4js and .n4jsd resources
					// add resource
					result.add(res);
					// add contents of resource
					for (EObject currRoot : getContentsNoResolve(res))
						collectAllMatchingNoResolve(currRoot, result, currObj -> !hasHideComment(currObj), false);
				} else if (SHOW_BUILT_IN.length > 0 && (res.getURI().toString().endsWith("builtin_js.n4ts")
						|| res.getURI().toString().endsWith("builtin_n4.n4ts"))) {
					// (2) show selected named elements in built-in resources
					// search for named elements to show
					final List<EObject> namedElementsToShow = new ArrayList<>();
					for (EObject currRoot : getContentsNoResolve(res)) {
						collectAllMatchingNoResolve(currRoot, namedElementsToShow,
								currObj -> org.eclipse.xtext.util.Arrays.contains(SHOW_BUILT_IN, getName(currObj)),
								true);
					}
					if (!namedElementsToShow.isEmpty()) {
						// found one or more named elements to show (including their contents)
						result.add(res);
						for (EObject currRoot : namedElementsToShow) {
							collectAllMatchingNoResolve(currRoot, result, currObj -> !hasHideComment(currObj), false);
						}
					} else {
						// no named elements to show -> ignore the resource
						ignoredResources.add(res);
					}
				} else {
					// (3) in all other cases: ignore the resource
					ignoredResources.add(res);
				}
			}
			// show ignored resources without contents (and always at the end!)
			result.addAll(ignoredResources);
		} else if (input instanceof EObject) {
			// if an EObject is given as the input, simply show all contained EObjects
			collectAllMatchingNoResolve((EObject) input, result, currObj -> !hasHideComment(currObj), false);
		}
		return result;
	}

	@Override
	public Node getNode(Object element) {
		final String type;
		final String name;
		final String desc;
		if (element instanceof Resource) {
			// case: Resource
			final URI uri = ((Resource) element).getURI();
			type = "Resource";
			name = uri != null ? uri.lastSegment() : null;
			desc = null;
		} else if (element instanceof EObject) {
			final EObject eobj = (EObject) element;
			if (eobj.eIsProxy()) {
				// case: proxy
				type = "PROXY(" + eobj.eClass().getName() + ")";
				name = null;
				desc = "proxy URI:\n" + EcoreUtil.getURI(eobj);
			} else {
				// case: non-proxy EObject
				type = eobj.eClass().getName();
				name = getName(eobj);
				desc = getDescription(eobj);
			}
		} else {
			// case: any POJO
			type = element.getClass().getSimpleName();
			name = null;
			desc = null;
		}
		final String title = type + (name != null ? " " + name : "");
		return new Node(element, title, desc);
	}

	@Override
	public List<Edge> getConnectedEdges(Node node, final List<Node> allNodes) {
		final List<Edge> result = new ArrayList<>();
		final Object element = node.getElement();
		if (element instanceof Resource) {
			// Resources
			final Resource res = (Resource) element;
			final List<EObject> targets = getContentsNoResolve(res);
			final List<Node> targetNodes = GraphUtils.getNodesForElements(targets, allNodes);
			if (!targetNodes.isEmpty())
				result.add(new Edge("contents", false, node, targetNodes, Collections.emptyList()));
		} else if (element instanceof EObject) {
			// EObjects
			final EObject eobj = (EObject) element;
			// add outgoing references
			for (EReference currRef : eobj.eClass().getEAllReferences()) {
				if (!currRef.isDerived() && !currRef.isContainer()) {
					if (currRef.isMany()) {
						final Object targets = eobj.eGet(currRef, false);
						if (targets instanceof Collection<?>) {
							final List<Node> targetNodes = new ArrayList<>();
							final List<String> targetNodesExternal = new ArrayList<>();
							for (Object currTarget : (Collection<?>) targets) {
								final Node currTargetNode = GraphUtils.getNodeForElement(currTarget, allNodes);
								if (currTargetNode != null)
									targetNodes.add(currTargetNode);
								else if (currTarget instanceof EObject && ((EObject) currTarget).eIsProxy())
									targetNodesExternal.add(EcoreUtil.getURI((EObject) currTarget).toString());
							}
							if (!targetNodes.isEmpty() || !targetNodesExternal.isEmpty())
								result.add(new Edge(currRef.getName(), !currRef.isContainment(), node, targetNodes,
										targetNodesExternal));
						}
					} else {
						final Object target = eobj.eGet(currRef, false);
						if (target instanceof EObject) {
							final Node targetNode = GraphUtils.getNodeForElement(target, allNodes);
							final String targetNodeExternal;
							if (targetNode == null && ((EObject) target).eIsProxy())
								targetNodeExternal = EcoreUtil.getURI((EObject) target).toString();
							else
								targetNodeExternal = null;
							if (targetNode != null || targetNodeExternal != null) {
								result.add(new Edge(
										currRef.getName(),
										!currRef.isContainment(),
										node,
										asCollection(targetNode),
										asCollection(targetNodeExternal)));
							}
						}
					}
				}
			}
			// add reference to containing Resource if immediate container is not in graph
			// (required when showing lower-level objects while hiding their ancestors)
			if (eobj.eResource() != null && eobj.eContainer() != null &&
					GraphUtils.getNodeForElement(eobj.eContainer(), allNodes) == null) {
				final Node nodeForResource = GraphUtils.getNodeForElement(eobj.eResource(), allNodes);
				if (nodeForResource != null) {
					result.add(new Edge(
							"<... containment omitted ...>",
							false, // not a cross-link
							nodeForResource,
							Collections.singletonList(node),
							Collections.emptyList()));
				}
			}
		}
		return result;
	}

	// eobj will be non-null and not a proxy; may return null.
	private String getName(EObject eobj) {
		if (eobj != null) {
			// use reflection to obtain the name (or let's say duck typing)
			final Iterator<EAttribute> i = eobj.eClass().getEAllAttributes().stream()
					.filter(a -> "name".equals(a.getName())).iterator();
			if (i.hasNext()) {
				final Object value = eobj.eGet(i.next(), false);
				if (value instanceof String)
					return (String) value;
			}
		}
		return null;
	}

	// eobj will be non-null and not a proxy; may return null.
	private String getDescription(EObject eobj) {
		final List<String> lines = new ArrayList<>();
		// attributes
		for (EAttribute a : eobj.eClass().getEAllAttributes()) {
			lines.add(a.getName() + ": \t" + eobj.eGet(a));
		}
		return !lines.isEmpty() ? Joiner.on('\n').join(lines) : null;
	}

	private static final void collectAllMatchingNoResolve(EObject obj, List<? super EObject> addHere,
			Predicate<EObject> predicate, boolean collectBelowNonMatching) {
		final boolean matches = predicate.test(obj);
		if (matches)
			addHere.add(obj);
		if (matches || collectBelowNonMatching) {
			final Iterator<EObject> i = eContentsNoResolve(obj);
			while (i.hasNext())
				collectAllMatchingNoResolve(i.next(), addHere, predicate, collectBelowNonMatching);
		}
	}

	private static final List<EObject> getContentsNoResolve(Resource res) {
		return Arrays.asList(Iterators.toArray(eContentsNoResolve(res), EObject.class));
	}

	private static final Iterator<EObject> eContentsNoResolve(Resource res) {
		return ((InternalEList<EObject>) res.getContents()).basicIterator();
	}

	private static final Iterator<EObject> eContentsNoResolve(EObject obj) {
		return ((InternalEList<EObject>) obj.eContents()).basicIterator();
	}

	private boolean hasHideComment(EObject eobj) {
		if (eobj.eIsProxy()) // TODO consider activating HIDE comment checking even for proxies
			return false;
		final String doc = getDocumentation(eobj);
		return doc != null && doc.contains("HIDE");
	}

	private final String startTag = "/\\*\\*?";
	private final Pattern commentStartTagRegex = Pattern.compile("(?s)" + startTag + ".*");

	private String getDocumentation(/* @NonNull */EObject object) {
		if (object.eContainer() == null) {
			// if a comment is at the beginning of the file it will be returned for
			// the root element (e.g. Script in N4JS) as well -> avoid this!
			return null;
		}
		ICompositeNode node = NodeModelUtils.getNode(object);
		if (node != null) {
			// get the last multi line comment before a non hidden leaf node
			for (ILeafNode leafNode : node.getLeafNodes()) {
				if (!leafNode.isHidden())
					break;
				if (leafNode.getGrammarElement() instanceof TerminalRule
						&& "ML_COMMENT".equalsIgnoreCase(((TerminalRule) leafNode.getGrammarElement()).getName())) {
					String comment = leafNode.getText();
					if (commentStartTagRegex.matcher(comment).matches()) {
						return leafNode.getText();
					}
				}
			}
		}
		return null;
	}

	private static final <T> Collection<T> asCollection(T object) {
		return object != null ? Collections.singletonList(object) : Collections.emptyList();
	}
}
