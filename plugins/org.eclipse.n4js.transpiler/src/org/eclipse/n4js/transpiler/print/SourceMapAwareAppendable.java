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
package org.eclipse.n4js.transpiler.print;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.transpiler.sourcemap.FilePosition;
import org.eclipse.n4js.transpiler.utils.TranspilerUtils;

/**
 * Wraps a {@link LineColTrackingAppendable} and adds some more high-level methods, allowing this class to compute
 * source map data along the way.
 */
/* package */ class SourceMapAwareAppendable implements Appendable {

	private final LineColTrackingAppendable out;
	private final boolean collectSourceMapData;

	private final Stack<SourceOutputMapping> openRegions = new Stack<>();
	private final List<SourceOutputMapping> mappings;

	public static final class SourceOutputMapping {
		public final EObject elementInIM;
		public final FilePosition outputStart;
		public final FilePosition outputEnd;

		/**
		 * Creates a partial mapping that represents a region that was not yet closed via {@link #closeRegion(EObject)}.
		 * These mappings are internal and will never be returned to client code.
		 */
		private SourceOutputMapping(EObject elementInIM, FilePosition outputStart) {
			if (elementInIM == null || outputStart == null)
				throw new IllegalArgumentException();
			this.elementInIM = elementInIM;
			this.outputStart = outputStart;
			this.outputEnd = null;
		}

		public SourceOutputMapping(EObject elementInIM, FilePosition outputStart, FilePosition outputEnd) {
			if (elementInIM == null || outputStart == null || outputEnd == null)
				throw new IllegalArgumentException();
			this.elementInIM = elementInIM;
			this.outputStart = outputStart;
			this.outputEnd = outputEnd;
		}
	}

	/** Creates a new instance. */
	public SourceMapAwareAppendable(Appendable out, CharSequence indent, boolean collectSourceMapData) {
		this.out = new LineColTrackingAppendable(out, indent);
		this.collectSourceMapData = collectSourceMapData;
		this.mappings = collectSourceMapData ? new ArrayList<>() : null;
	}

	@Override
	public Appendable append(char c) throws IOException {
		out.append(c);
		return this;
	}

	@Override
	public Appendable append(CharSequence csq) throws IOException {
		out.append(csq);
		return this;
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end) throws IOException {
		out.append(csq, start, end);
		return this;
	}

	/**
	 * @see LineColTrackingAppendable#newLine()
	 */
	public void newLine() throws IOException {
		out.newLine();
	}

	/**
	 * @see LineColTrackingAppendable#indent()
	 */
	public void indent() {
		out.indent();
	}

	/**
	 * @see LineColTrackingAppendable#undent()
	 */
	public void undent() {
		out.undent();
	}

	public void openRegion(EObject elementInIntermediateModel) {
		if (elementInIntermediateModel == null)
			throw new IllegalArgumentException("element in intermediate model may not be null");
		if (!TranspilerUtils.isIntermediateModelElement(elementInIntermediateModel))
			throw new IllegalArgumentException("not an element in the intermediate model");

		// even if !collectSourceMapData we track the regions to have the same consistency checks in both modes!
		openRegions.push(new SourceOutputMapping(elementInIntermediateModel, out.getCurrentPos()));
	}

	public void closeRegion(EObject elementInIntermediateModel) {
		if (elementInIntermediateModel == null)
			throw new IllegalArgumentException("element in intermediate model may not be null");
		if (!TranspilerUtils.isIntermediateModelElement(elementInIntermediateModel))
			throw new IllegalArgumentException("not an element in the intermediate model");

		final SourceOutputMapping partialMapping = openRegions.pop();
		if (elementInIntermediateModel != partialMapping.elementInIM) {
			throw new IllegalStateException(
					"region open/close mismatch: trying to close region for "
							+ elementInIntermediateModel.eClass().getName()
							+ " but last opened region was for "
							+ partialMapping.elementInIM.eClass().getName());
		}

		if (collectSourceMapData) {
			// add the end position to the partial mapping
			final SourceOutputMapping completeMapping = new SourceOutputMapping(
					partialMapping.elementInIM,
					partialMapping.outputStart,
					out.getCurrentPos());
			// and store it for later retrieval
			mappings.add(completeMapping);
		}
	}

	public List<SourceOutputMapping> getSourceMapData() {
		if (!collectSourceMapData)
			throw new IllegalStateException("source map data not available");
		if (!openRegions.isEmpty())
			throw new IllegalStateException("trying to obtain source map data before all opened regions were closed");
		return Collections.unmodifiableList(mappings);
	}
}
