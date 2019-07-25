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
package org.eclipse.n4js.compare;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.util.Arrays;

/**
 * Each ProjectComparisonEntry relates a single project/type/member on API side to N projects/types/members on
 * implementation side, with N being the number of implementations covered by the containing {@link ProjectComparison},
 * i.e. the number of IDs returned by method {@link ProjectComparison#getImplIds()}.
 * <p>
 * The implementation indices used in this class are defined by the containing <code>ProjectComparison</code>. For
 * example, let's assume an iOS implementation with ID "impl.ios": if that ID is located at index 3 in the array
 * returned by method {@link ProjectComparison#getImplIds() getImplIds()} of the containing
 * <code>ProjectComparison</code>, then the iOS implementation will be referred to via implementation index 3 throughout
 * this class, i.e. you could use
 *
 * <pre>
 * entry.getElementImpl(3)
 * </pre>
 *
 * to obtain the type or member within the iOS implementation that corresponds to the API-side element returned by
 * <code>entry.getElementApi()</code>.
 */
@SuppressWarnings("javadoc")
public class ProjectComparisonEntry {

	public static final String LABEL_MISSING = "*MISSING*";

	private final ProjectComparison root;
	private final ProjectComparisonEntry parent;
	private final List<ProjectComparisonEntry> children = new ArrayList<>();

	private final IN4JSProject projectAPI;
	private final IN4JSProject projectImpl[]; // non-null iff this is a project entry
	private final EObject elementAPI;
	private final EObject[] elementImpl; // non-null iff this is an element entry
	private final ProjectCompareResult[] cachedCompareResults; // non-null iff this is an element entry

	public ProjectComparisonEntry(
			ProjectComparison root, IN4JSProject projectAPI, IN4JSProject... projectImpl) {
		this(root, null, -1, projectAPI, projectImpl, null, null);
	}

	public ProjectComparisonEntry(
			ProjectComparisonEntry parent, int index, EObject elementAPI, EObject... elementImpl) {
		this(parent.root, parent, index, null, null, elementAPI, elementImpl);
	}

	/**
	 * @param index
	 *            -1 means add at the end.
	 */
	protected ProjectComparisonEntry(
			ProjectComparison root,
			ProjectComparisonEntry parent, int index,
			IN4JSProject projectAPI, IN4JSProject[] projectImpl,
			EObject elementAPI, EObject[] elementImpl) {

		if (root == null)
			throw new IllegalArgumentException();
		if (parent != null && parent.root != root)
			throw new IllegalArgumentException();
		if ((projectImpl == null) == (elementImpl == null))
			throw new IllegalArgumentException();

		this.root = root;
		this.parent = parent;
		this.projectAPI = projectAPI;
		this.projectImpl = projectImpl;
		this.elementAPI = elementAPI;
		this.elementImpl = elementImpl;
		this.cachedCompareResults = elementImpl != null ? new ProjectCompareResult[elementImpl.length] : null;

		if (parent != null)
			parent.addChild(index, this);
		else
			root.internalAddRootEntry(this);

		// notify ProjectComparison that we were added
		this.root.onEntryAdded(this);
	}

	private void addChild(int index, ProjectComparisonEntry child) {
		if (child != null) {
			if (index < 0)
				index = children.size();
			children.add(index, child);
		}
	}

	public ProjectComparison getRoot() {
		return root;
	}

	public ProjectComparisonEntry getParent() {
		return parent;
	}

	public boolean hasChildren() {
		return getChildren().length > 0;
	}

	public ProjectComparisonEntry[] getChildren() {
		return children.toArray(new ProjectComparisonEntry[children.size()]);
	}

	public int getChildIndex(ProjectComparisonEntry child) {
		return children.indexOf(child);
	}

	public ProjectComparisonEntry getChildForElementImpl(@SuppressWarnings("hiding") EObject elementImpl) {
		if (elementImpl == null)
			throw new IllegalArgumentException("elementImpl may not be null");
		for (ProjectComparisonEntry currE : getChildren())
			if (Arrays.contains(currE.elementImpl, elementImpl))
				return currE;
		return null;
	}

	public ProjectComparisonEntry getChildForElementAPI(@SuppressWarnings("hiding") EObject elementAPI) {
		if (elementAPI == null)
			throw new IllegalArgumentException("elementAPI may not be null");
		for (ProjectComparisonEntry currE : getChildren()) {
			if (currE.elementAPI != null && currE.elementAPI.equals(elementAPI)) {
				return currE;
			}
		}
		return null;
	}

	public int getImplCount() {
		if (projectImpl != null)
			return projectImpl.length;
		if (elementImpl != null)
			return elementImpl.length;
		return 0;
	}

	public boolean isProjectEntry() {
		return projectImpl != null;
	}

	public boolean isElementEntry() {
		return elementImpl != null;
	}

	public IN4JSProject getProjectAPI() {
		return projectAPI;
	}

	public IN4JSProject[] getProjectImpl() {
		return projectImpl;
	}

	public IN4JSProject getProjectImpl(int implIdx) {
		if (projectImpl != null) {
			return implIdx >= 0 && implIdx < projectImpl.length ? projectImpl[implIdx] : null;
		}
		return null;
	}

	public EObject getElementAPI() {
		return elementAPI;
	}

	public EObject[] getElementImpl() {
		return elementImpl;
	}

	public EObject getElementImpl(int implIdx) {
		if (elementImpl != null) {
			return implIdx >= 0 && implIdx < elementImpl.length ? elementImpl[implIdx] : null;
		}
		return null;
	}

	public Object getProjectOrElementApi() {
		return projectAPI != null ? projectAPI : elementAPI;
	}

	public Object getProjectOrElementImpl(int implIdx) {
		if (isProjectEntry())
			return getProjectImpl(implIdx);
		return getElementImpl(implIdx);
	}

	/**
	 * If this is an element entry, returns the API element (if any) and all implementation elements (if they exist).
	 * Never returns <code>null</code> and the returned array never contains <code>null</code> entries.
	 */
	public EObject[] getAllElements() {
		if (isElementEntry()) {
			final List<EObject> result = new ArrayList<>();
			if (elementAPI != null)
				result.add(elementAPI);
			for (EObject currElemImpl : elementImpl)
				if (currElemImpl != null)
					result.add(currElemImpl);
			return result.toArray(new EObject[result.size()]);
		}
		return new EObject[0];
	}

	public ProjectCompareResult getCachedCompareResult(int implIdx) {
		if (isElementEntry()) {
			if (implIdx >= 0 && implIdx < cachedCompareResults.length) {
				return cachedCompareResults[implIdx];
			}
		}
		return null;
	}

	public void storeCachedCompareResult(int implIdx, ProjectCompareResult result) {
		if (isElementEntry() && implIdx >= 0 && implIdx < cachedCompareResults.length)
			cachedCompareResults[implIdx] = result;
	}

	public void clearCachedCompareResults() {
		if (isElementEntry()) {
			for (int idx = 0; idx < cachedCompareResults.length; idx++)
				cachedCompareResults[idx] = null;
		}
	}

	public String getTextTEMP(int idx) {
		if (idx == -1)
			return getTextAPI();
		else
			return getTextImpl(idx);
	}

	public String getTextAPI() {
		final Object api = getProjectOrElementApi();
		if (api == null) {
			return "-";
		}
		return toText(api);
	}

	public String getTextImpl(int implIdx) {
		if (implIdx < 0 || implIdx >= getImplCount()) {
			// index out of bounds -> leave cell empty
			return null;
		}
		final Object api = getProjectOrElementApi();
		final Object impl = getProjectOrElementImpl(implIdx);
		if (impl == null) {
			if (api != null) {
				// no project/element here but in API -> show "MISSING"
				return LABEL_MISSING;
			} else {
				// no project/element here nor in API -> show "-"
				// (the entry was created due to a surplus element in another implementation)
				return "-";
			}
		}
		return toText(impl);
	}

	private static String toText(Object element) {
		if (element instanceof IN4JSProject)
			return ((IN4JSProject) element).getProjectName().getRawName();
		if (element instanceof TModule)
			return ((TModule) element).getQualifiedName();
		if (element instanceof TMember)
			return ((TMember) element).getMemberAsString();
		if (element instanceof TFormalParameter)
			return ((TFormalParameter) element).getFormalParameterAsString();
		if (element instanceof Type)
			return ((Type) element).getTypeAsString();
		if (element instanceof TypeRef)
			return ((TypeRef) element).getTypeRefAsString();
		if (element instanceof IdentifiableElement)
			return ((IdentifiableElement) element).getName();
		return element != null ? element.toString() : null;
	}

	public boolean isInheritedTEMP(int idx) {
		if (idx == -1)
			return isInherited();
		else
			return isInherited(idx);
	}

	public boolean isInherited() {
		final EObject implParent = parent != null ? parent.getElementAPI() : null;
		final EObject impl = getElementAPI();
		if (implParent instanceof ContainerType<?> && impl instanceof TMember) {
			return impl.eContainer() != implParent;
		}
		return false;
	}

	/**
	 * Returns true iff the implementation element at index <code>implIdx</code> is a TMember that was directly or
	 * indirectly inherited/consumed from a super class or implemented interface. Always returns <code>false</code> if
	 * this is not an element entry.
	 */
	public boolean isInherited(int implIdx) {
		final EObject implParent = parent != null ? parent.getElementImpl(implIdx) : null;
		final EObject impl = getElementImpl(implIdx);
		if (implParent instanceof ContainerType<?> && impl instanceof TMember) {
			return impl.eContainer() != implParent;
		}
		return false;
	}

	public boolean isOverrideTEMP(int idx) {
		if (idx == -1)
			return isOverride();
		else
			return isOverride(idx);
	}

	public boolean isOverride() {
		final EObject impl = getElementAPI();
		if (impl instanceof TMember) {
			// we can rely on the @Override annotation, because if the member overrides another member without having
			// this annotation, it would be a compile error.
			return ((TMember) impl).isDeclaredOverride();
		}
		return false;
	}

	/**
	 * Returns true iff the implementation element at index <code>implIdx</code> is a TMember that overrides another
	 * member in a super class or implemented interface. Always returns <code>false</code> if this is not an element
	 * entry.
	 */
	public boolean isOverride(int implIdx) {
		final EObject impl = getElementImpl(implIdx);
		if (impl instanceof TMember) {
			// we can rely on the @Override annotation, because if the member overrides another member without having
			// this annotation, it would be a compile error.
			return ((TMember) impl).isDeclaredOverride();
		}
		return false;
	}

	public Stream<ProjectComparisonEntry> allChildren() {
		return Stream.concat(Stream.of(this), children.stream().flatMap(child -> child.allChildren()));
	}
}
