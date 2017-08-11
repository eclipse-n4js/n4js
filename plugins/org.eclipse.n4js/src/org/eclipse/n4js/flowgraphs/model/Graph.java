/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.model;

import java.util.List;

import org.eclipse.n4js.flowgraphs.factories.JavaFeatures;
import org.eclipse.n4js.n4JS.Block;

public class Graph {
	final public ProgramBlock program;
	final public JavaFeatures javaFeatures;
	final public EffectMap effectMap = new EffectMap();
	final public Hierarchy hierarchy = new Hierarchy();

	public Graph(ProgramBlock program, JavaFeatures featureMap) {
		this.program = program;
		this.javaFeatures = featureMap;
	}

	public List<Block> getAllBlocks() {
		return program.getMethodCNode().getBlocksTransitive(javaFeatures);
	}
}
