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
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.transpiler.AbstractTranspiler.SourceMapInfo;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.im.Script_IM;
import org.eclipse.n4js.transpiler.print.SourceMapAwareAppendable.SourceOutputMapping;
import org.eclipse.n4js.transpiler.sourcemap.FilePosition;
import org.eclipse.n4js.transpiler.sourcemap.SourceMapGenerator;
import org.eclipse.n4js.transpiler.sourcemap.SourceMapRev3Generator;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.util.ITextRegion;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * A not so pretty printer.
 */
public class PrettyPrinter {

	private static final String INDENT = "\t";

	@Inject
	private ILocationInFileProvider locationInFileProvider;

	/**
	 * Serialize the intermediate model in the given transpiler state to <code>outCode</code> and emit source maps to
	 * <code>optSourceMapData</code>.
	 *
	 * @param optPreamble
	 *            an optional preamble that will be prepended to the output code. Use '\n' as line separator. If absent,
	 *            no preamble will be prepended.<br>
	 *            If present, a single line break will be used to separate the preamble from the main output, but
	 *            additional line feed characters may be added at the end of the preamble string if empty lines are
	 *            desired between preamble and main output.
	 */
	public void print(TranspilerState state, Writer outCode, Optional<String> optPreamble,
			Optional<SourceMapInfo> optSourceMapInfo) throws IOException {

		final boolean emitSourceMaps = optSourceMapInfo.isPresent();
		final SourceMapAwareAppendable out = new SourceMapAwareAppendable(outCode, INDENT, emitSourceMaps);

		PrettyPrinterSwitch.append(out, state, optPreamble);

		if (emitSourceMaps) {
			final SourceMapInfo sourceMapInfo = optSourceMapInfo.get();
			final SourceMapGenerator generator = new SourceMapRev3Generator();

			// append link to source maps to outCode
			out.newLine();
			out.append("//# sourceMappingURL=" + sourceMapInfo.simpleSourceMapFileName);
			out.newLine();

			// get the mappings collected by SourceMapAwareAppendable
			final List<SourceOutputMapping> mappings = new ArrayList<>(out.getSourceMapData());

			// perform some tweaks on the mappings (TEMPORARY)
			removeCatchAllMapping(mappings);

			// Convert the source/output mappings produced by SourceMapAwareAppendable
			final PositionProvider positionProvider = PositionProvider.from(state.resource);
			for (SourceOutputMapping m : mappings) {
				final EObject originalASTNode = state.tracer.getOriginalASTNode(m.elementInIM);

				if (originalASTNode != null // it's ok if this is null
						&& originalASTNode.eResource() instanceof N4JSResource) {
					final ITextRegion region = locationInFileProvider.getSignificantTextRegion(originalASTNode);
					// get the resource and compute the path.
					final String path = sourceMapInfo.resolve((N4JSResource) originalASTNode.eResource());

					final FilePosition sourceStartPosition = positionProvider.toPosition(region.getOffset());
					generator.addMapping(
							path,
							null, // TODO source maps: support for original symbol name
							sourceStartPosition,
							m.outputStart,
							m.outputEnd);
				}
			}

			// append actual source maps to the buffer passed in via 'sourceMapInfo'
			generator.appendTo(sourceMapInfo.sourceMapBuff, sourceMapInfo.simpleCompiledFileName);
		}
	}

	private void removeCatchAllMapping(List<SourceOutputMapping> mappings) {
		final int lastIdx = mappings.size() - 1;
		if (!(mappings.get(lastIdx).elementInIM instanceof Script_IM))
			throw new IllegalStateException("did not find the expected mapping for the Script_IM element");
		mappings.remove(lastIdx);
	}

}
