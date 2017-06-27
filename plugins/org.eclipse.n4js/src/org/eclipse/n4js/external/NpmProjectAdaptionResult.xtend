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
package org.eclipse.n4js.external

import com.google.common.base.Preconditions
import java.net.URI
import java.util.Collection
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Status
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

/**
 * Representation of an npm project adaption result.
 */
@FinalFieldsConstructor
class NpmProjectAdaptionResult implements IStatus {

	@Delegate
	val IStatus status;

	@Accessors
	val ToBeBuilt toBeBuilt;

	/**
	 * Creates a new success project adaption result with the projects that has to be updated/deleted.
	 */
	static def newOkResult(Iterable<? extends URI> toBeUpdated, Iterable<? extends URI> toBeDeleted) {
		return new NpmProjectAdaptionResult(Status.OK_STATUS, toBeUpdated, toBeDeleted);
	}

	/**
	 * Creates a new project adaption result that represents an error.
	 */
	static def newErrorResult(IStatus errorStatus) {
		Preconditions.checkArgument(!errorStatus.OK, 'Expected error status.');
		return new NpmProjectAdaptionResult(errorStatus, emptyList, emptyList);
	}

	private new(IStatus status, Iterable<? extends URI> toBeUpdated, Iterable<? extends URI> toBeDeleted) {
		this.status = status;
		toBeBuilt = new ToBeBuilt(toBeUpdated.toSet.immutableCopy, toBeDeleted.toSet.immutableCopy);
	}

	@Override
	override toString() {
		return status + '\n' + toBeBuilt;
	}

	/**
	 * Representation of a collection of external projects that should be updated or deleted.
	 */
	@FinalFieldsConstructor
	@Accessors(value = PUBLIC_GETTER)
	static class ToBeBuilt {

		val Collection<URI> toBeUpdated;
		val Collection<URI> toBeDeleted;

		@Override
		override toString() {
			val sb = new StringBuilder;
			sb.append('ToBeBuilt [\n\ttoBeUpdated=');
			sb.append(toBeUpdated);
			sb.append(',\n\ttoBeDeleted=');
			sb.append(toBeDeleted);
			sb.append('\n]');
			return sb.toString;
		}

	}


}
