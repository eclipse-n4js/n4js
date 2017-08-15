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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.n4js.smith.graph.graph.Edge;
import org.eclipse.n4js.smith.graph.graph.GraphProvider;
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
public class ASTGraphProvider implements GraphProvider<Object, Object> {

	/**
	 * Names of elements in the built-in resources (builtin_js.n4ts and builtin_n4.n4ts) that should appear in the
	 * graph, including their contents. Names listed here should be those returned by method {@link #getName(EObject)}.
	 */
	private static final String[] SHOW_BUILT_IN = {};

	private boolean showBuiltInContains(EObject eObj) {
		return org.eclipse.xtext.util.Arrays.contains(SHOW_BUILT_IN, getName(eObj));
	}

	@Override
	public List<Object> getElements(Object input) {
		final List<Object> result = new ArrayList<>();
		if (input instanceof ResourceSet) {
			final List<Resource> ignoredResources = getElements((ResourceSet) input, result);
			// show ignored resources without contents at the end
			result.addAll(ignoredResources);

		} else if (input instanceof EObject) {
			// show all contained EObjects
			collectAllMatchingNoResolve((EObject) input, result, this::hasNoHideComment, false);
		}

		return result;
	}

	private List<Resource> getElements(ResourceSet resSet, final List<Object> result) {
		final List<Resource> ignoredResources = new ArrayList<>();

		for (Resource res : new ArrayList<>(resSet.getResources())) {
			final boolean isFirstResource = result.isEmpty();
			// only show contents of first resource + .n4js resources
			// (avoid showing built-in types or letting graph become to large)
			String uriStr = res.getURI().toString();
			if (isFirstResource || uriStr.endsWith(".n4js") || uriStr.endsWith(".n4jsd")) {
				getElementsForN4JSs(result, res);

			} else if (SHOW_BUILT_IN.length > 0
					&& (uriStr.endsWith("builtin_js.n4ts") || uriStr.endsWith("builtin_n4.n4ts"))) {

				getElementsForBuiltIns(result, ignoredResources, res);

			} else {
				// ignore the resource
				ignoredResources.add(res);
			}
		}
		return ignoredResources;
	}

	/**
	 * Show *all* contents of first resource + .n4js and .n4jsd resources
	 */
	private void getElementsForN4JSs(final List<Object> result, Resource res) {
		// Add resource
		result.add(res);
		// add contents of resource
		for (EObject currRoot : getContentsNoResolve(res)) {
			collectAllMatchingNoResolve(currRoot, result, this::hasNoHideComment, false);
		}
	}

	/**
	 * Show selected named elements in built-in resources. Search for named elements to show.
	 */
	private void getElementsForBuiltIns(final List<Object> result, final List<Resource> ignoredResources,
			Resource res) {

		final List<EObject> namedElementsToShow = new ArrayList<>();
		for (EObject currRoot : getContentsNoResolve(res)) {
			collectAllMatchingNoResolve(currRoot, namedElementsToShow, this::showBuiltInContains, true);
		}
		if (!namedElementsToShow.isEmpty()) {
			// found one or more named elements to show (including their contents)
			result.add(res);
			for (EObject currRoot : namedElementsToShow) {
				collectAllMatchingNoResolve(currRoot, result, this::hasNoHideComment, false);
			}

		} else {
			// no named elements to show -> ignore the resource
			ignoredResources.add(res);
		}
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
			getConnectedEdgesForResource(node, allNodes, result, (Resource) element);
		} else if (element instanceof EObject) {
			getConnectedEdgesForEObject(node, allNodes, result, (EObject) element);
		}
		return result;
	}

	/**
	 * Add outgoing references
	 */
	private void getConnectedEdgesForEObject(Node node, final List<Node> allNodes, final List<Edge> result,
			final EObject eobj) {

		for (EReference currRef : eobj.eClass().getEAllReferences()) {
			if (!currRef.isDerived() && !currRef.isContainer()) {
				if (currRef.isMany()) {
					final Object targets = eobj.eGet(currRef, false);
					if (targets instanceof Collection<?>) {
						getConnectedEdgesForEObjectManyCase(node, allNodes, result, currRef, targets);
					}
				} else {
					final Object target = eobj.eGet(currRef, false);
					if (target instanceof EObject) {
						getConnectedEdgesForEObjectSingleCase(node, allNodes, result, currRef, target);
					}
				}
			}
		}

		// add reference to containing Resource if immediate container is not in graph
		// (required when showing lower-level objects while hiding their ancestors)
		Node nodeForElement = GraphUtils.getNodeForElement(eobj.eContainer(), allNodes);

		if (eobj.eResource() != null && eobj.eContainer() != null && nodeForElement == null) {
			final Node nodeForResource = GraphUtils.getNodeForElement(eobj.eResource(), allNodes);
			if (nodeForResource != null) {
				Edge edge = new Edge(
						"<... containment omitted ...>",
						false, // not a cross-link
						nodeForResource,
						Collections.singletonList(node),
						Collections.emptyList());

				result.add(edge);
			}
		}
	}

	private void getConnectedEdgesForEObjectManyCase(Node node, final List<Node> allNodes, final List<Edge> result,
			EReference currRef, final Object targets) {

		final List<Node> targetNodes = new ArrayList<>();
		final List<String> targetNodesExternal = new ArrayList<>();
		for (Object currTarget : (Collection<?>) targets) {
			final Node currTargetNode = GraphUtils.getNodeForElement(currTarget, allNodes);

			if (currTargetNode != null)
				targetNodes.add(currTargetNode);
			else if (currTarget instanceof EObject && ((EObject) currTarget).eIsProxy())
				targetNodesExternal.add(EcoreUtil.getURI((EObject) currTarget).toString());
		}
		if (!targetNodes.isEmpty() || !targetNodesExternal.isEmpty()) {
			Edge edge = new Edge(
					currRef.getName(),
					!currRef.isContainment(),
					node,
					targetNodes,
					targetNodesExternal);

			result.add(edge);
		}
	}

	private void getConnectedEdgesForEObjectSingleCase(Node node, final List<Node> allNodes, final List<Edge> result,
			EReference currRef, final Object target) {

		final Node targetNode = GraphUtils.getNodeForElement(target, allNodes);
		final String targetNodeExternal;

		if (targetNode == null && ((EObject) target).eIsProxy())
			targetNodeExternal = EcoreUtil.getURI((EObject) target).toString();
		else
			targetNodeExternal = null;

		if (targetNode != null || targetNodeExternal != null) {
			Edge edge = new Edge(
					currRef.getName(),
					!currRef.isContainment(),
					node,
					asCollection(targetNode),
					asCollection(targetNodeExternal));

			result.add(edge);
		}
	}

	private void getConnectedEdgesForResource(Node node, final List<Node> allNodes, final List<Edge> result,
			final Resource res) {

		final List<EObject> targets = getContentsNoResolve(res);
		final List<Node> targetNodes = GraphUtils.getNodesForElements(targets, allNodes);
		if (!targetNodes.isEmpty()) {
			Edge edge = new Edge("contents", false, node, targetNodes, Collections.emptyList());
			result.add(edge);
		}
	}

	/**
	 * @param eobj
	 *            will be non-null and not a proxy
	 * @return may return null
	 */
	private String getName(EObject eobj) {
		if (eobj != null) {
			// use reflection (or duck typing) to obtain the name
			EList<EAttribute> eAllAttributes = eobj.eClass().getEAllAttributes();
			for (EAttribute attr : eAllAttributes) {
				if ("name".equals(attr.getName())) {
					final Object value = eobj.eGet(attr, false);
					if (value instanceof String) {
						return (String) value;
					}
				}
			}
		}
		return null;
	}

	/**
	 * @param eobj
	 *            will be non-null and not a proxy
	 * @return may return null
	 */
	private String getDescription(EObject eobj) {
		final List<String> lines = new ArrayList<>();
		// attributes
		for (EAttribute a : eobj.eClass().getEAllAttributes()) {
			String line = a.getName() + ": \t" + eobj.eGet(a);
			lines.add(line);
		}
		String result = lines.isEmpty() ? null : Joiner.on('\n').join(lines);
		return result;
	}

	private static final void collectAllMatchingNoResolve(EObject obj, List<? super EObject> addHere,
			Predicate<EObject> predicate, boolean collectBelowNonMatching) {

		final boolean matches = predicate.test(obj);
		if (matches)
			addHere.add(obj);
		if (matches || collectBelowNonMatching) {
			final Iterator<EObject> i = eContentsNoResolve(obj);
			while (i.hasNext()) {
				collectAllMatchingNoResolve(i.next(), addHere, predicate, collectBelowNonMatching);
			}
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

	private boolean hasNoHideComment(EObject eobj) {
		return !hasHideComment(eobj);
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

				EObject grammarElem = leafNode.getGrammarElement();
				if (grammarElem instanceof TerminalRule
						&& "ML_COMMENT".equalsIgnoreCase(((TerminalRule) grammarElem).getName())) {

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
