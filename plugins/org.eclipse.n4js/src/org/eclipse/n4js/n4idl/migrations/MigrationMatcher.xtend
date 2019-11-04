/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl.migrations

import java.util.HashSet
import java.util.Set
import org.eclipse.n4js.ts.types.TMigration
import java.util.List

/**
 * A {@link MigrationMatcher} accumulates all migration candidates handed to its {@link #match}
 * method and keeps track of the best match (and thus distance) matched so far.
 */
class MigrationMatcher {

	/**
	 * Creates a new empty {@link MigrationMatcher}.
	 */
	public static def MigrationMatcher emptyMatcher() {
		return new MigrationMatcher();
	}

	private double bestDistance
	private Set<TMigration> matches = new HashSet<TMigration>(); 
	
	/**
	 * Initializes a new {@link MigrationMatcher}.
	 */
	private new() {
		this.bestDistance = TypeDistanceComputer.MAX_DISTANCE;
	}
	
	/**
	 * Matches the given match and distance with this matcher.
	 * 
	 * Returns an {@link MigrationMatcher} which also considers the given
	 * match.
	 */
	public def void match(TMigration match, double distance) {
		if (this.bestDistance < distance) {
			// the match is worse than existing matches, do not record it
		} else if (this.bestDistance == distance) {
			// we found an equally-good match
			this.matches.add(match);
		} else { // this.bestDistance > distance
			// we found a better match, replace current matches and bestDistance
			this.matches = new HashSet(#[match]);
			this.bestDistance = distance;
		}
	}
	
	/**
	 * Returns {@code true} iff this matcher has found the 
	 * perfect match (best distance == 0). 
	 */
	public def boolean hasPerfectMatch() {
		return this.distance == 0;
	}
	
	/**
	 * Returns the best distance this matcher has matched.
	 * 
	 * {@link #getAllMatches} can be assumed to be of this distance.
	 */
	public def double getDistance() {
		return this.bestDistance;
	}
	
	/** 
	 * Returns all matches matched by this {@link MigrationMatcher}.
	 * 
	 * All returned candidates can be assumed to be of the same {@link #getDistance}.
	 */
	public def List<TMigration> getAllMatches() {
		return this.matches.toList;
	}
	
	/**
	 * Returns any match from this matcher or {@code null} if no
	 * match has been found yet.
	 */
	public def TMigration anyMatch() {
		return this.matches.head;
	}
	
	/** 
	 * Returns {@code true} iff this matcher has not matched any migration yet. 
	 */
	public def boolean isEmpty() {
		return this.matches.size == 0;
	}
}
