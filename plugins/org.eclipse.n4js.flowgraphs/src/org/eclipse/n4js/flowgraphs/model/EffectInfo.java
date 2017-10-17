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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//TODO GH-235
@SuppressWarnings("javadoc")
public class EffectInfo {

	public static final EffectInfo ZERO = new EffectInfo();

	public Symbol assigned;
	public Symbol declares;
	public Symbol references;

	/* Direct side effect reads/writes */
	final public Set<Symbol> seReads = new HashSet<>();
	final public Set<Symbol> seWrites = new HashSet<>();

	final public Set<Symbol> allReads = new HashSet<>();
	final public Set<Symbol> allWrites = new HashSet<>();

	public EffectInfo() {
	}

	public EffectInfo(EffectInfo info) {
		assigned = info.assigned;
		declares = info.declares;
		references = info.references;
		seReads.addAll(info.seReads);
		seWrites.addAll(info.seWrites);
		allReads.addAll(info.allReads);
		allWrites.addAll(info.allWrites);
	}

	public void validate() {
		allReads.clear();
		if (references != null)
			allReads.add(references);
		allReads.addAll(seReads);

		allWrites.clear();
		if (assigned != null)
			allWrites.add(assigned);
		allWrites.addAll(seWrites);
	}

	@Override
	public String toString() {
		String s = "";

		if (declares != null) {
			s += "Decl: " + declares;
		}
		if (assigned != null) {
			s += "Asgn: " + assigned;
		}
		if (references != null) {
			s += "Ref: " + references;
		}
		if (!seReads.isEmpty()) {
			Iterator<Symbol> it = seReads.iterator();
			s += " SEr: " + it.next().toString();
			while (it.hasNext())
				s += ", " + it.next().toString();
		}
		if (!seWrites.isEmpty()) {
			Iterator<Symbol> it = seWrites.iterator();
			s += " SEw: " + it.next().toString();
			while (it.hasNext())
				s += ", " + it.next().toString();
		}

		if (s.length() == 0)
			s = "-";

		return s;
	}
}
