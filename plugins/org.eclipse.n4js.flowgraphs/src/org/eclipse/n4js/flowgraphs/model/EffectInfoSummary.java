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
public class EffectInfoSummary {
	public Set<Symbol> assigneds = new HashSet<>();
	public Set<Symbol> declares = new HashSet<>();
	public Set<Symbol> references = new HashSet<>();

	/* Direct side effect reads/writes */
	final public Set<Symbol> seReads = new HashSet<>();
	final public Set<Symbol> seWrites = new HashSet<>();

	final public Set<Symbol> allReads = new HashSet<>();
	final public Set<Symbol> allWrites = new HashSet<>();

	public void add(EffectInfo ei) {
		if (ei.assigned != null)
			assigneds.add(ei.assigned);
		if (ei.declares != null)
			declares.add(ei.declares);
		if (ei.references != null)
			references.add(ei.references);
		seReads.addAll(ei.seReads);
		seWrites.addAll(ei.seWrites);
		validate();
	}

	public void validate() {
		allReads.clear();
		allReads.addAll(references);
		allReads.addAll(seReads);

		allWrites.clear();
		allWrites.addAll(assigneds);
		allWrites.addAll(seWrites);
	}

	@Override
	public String toString() {
		String s = "";

		if (!declares.isEmpty()) {
			Iterator<Symbol> it = declares.iterator();
			s += " Decls: " + it.next().toString();
			while (it.hasNext())
				s += ", " + it.next().toString();
		}
		if (!assigneds.isEmpty()) {
			Iterator<Symbol> it = assigneds.iterator();
			s += " Asgns: " + it.next().toString();
			while (it.hasNext())
				s += ", " + it.next().toString();
		}
		if (!references.isEmpty()) {
			Iterator<Symbol> it = references.iterator();
			s += " Refs: " + it.next().toString();
			while (it.hasNext())
				s += ", " + it.next().toString();
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
