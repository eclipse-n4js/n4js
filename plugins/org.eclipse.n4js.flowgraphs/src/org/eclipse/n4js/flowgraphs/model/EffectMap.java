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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.n4js.n4JS.Expression;

import java17.ast.JavaFeature;

public class EffectMap {
	static final EffectInfo ZERO_INFO = new EffectInfo();
	final private Map<JavaFeature, EffectInfo> effectMap = new HashMap<>();
	final private Map<Node, EffectInfo> nodeEffectMap = new HashMap<>();

	public EffectInfo getEffectInfo(Expression expr) {
		EffectInfo info = effectMap.map(expr);
		if (info == null)
			info = ZERO_INFO;
		return info;
	}

	public EffectInfo getEffectInfo(Node node) {
		EffectInfo info = null;
		// check, if overwrite info is available
		info = nodeEffectMap.get(node);
		if (info != null)
			return info;

		// otherwise, use ordinary info
		info = effectMap.map(node.cfeElem);
		if (info == null)
			info = ZERO_INFO;
		return applyMask(node, info);
	}

	private EffectInfo applyMask(Node node, EffectInfo info) {
		info = new EffectInfo(info);

		EffectInfoMask mask = node.getEffectInfoMask();

		if (!mask.showAssigned)
			info.assigned = null;
		if (!mask.showDeclares)
			info.declares = null;
		if (!mask.showReferences)
			info.references = null;
		if (!mask.showSEReads)
			info.seReads.clear();
		if (!mask.showSEWrites)
			info.seWrites.clear();
		info.validate();

		return info;
	}

	public EffectInfoSummary getEffectSummary(ComplexNode cn) {
		EffectInfoSummary iSum = new EffectInfoSummary();
		for (Node n : cn.getNodes()) {
			iSum.add(getEffectInfo(n));
		}
		return iSum;
	}

	public void put(Expression expr, EffectInfo info) {
		effectMap.put(expr, info);
	}

	public void overwrite(Node node, EffectInfo info) {
		nodeEffectMap.put(node, info);
	}

	public void clearOverwrites() {
		nodeEffectMap.clear();
	}

}
