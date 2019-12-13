/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.semver;

import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.n4js.semver.serializer.CustomSemverSyntacticSequencer;
import org.eclipse.n4js.semver.serializer.SemverSyntacticSequencer;
import org.eclipse.n4js.semver.validation.SemverIssueCodes;
import org.eclipse.n4js.semver.validation.SemverIssueSeveritiesProvider;
import org.eclipse.n4js.xtext.serializer.SerializerPatchModule;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.validation.IssueSeveritiesProvider;

import com.google.inject.Binder;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class SemverRuntimeModule extends AbstractSemverRuntimeModule {

	/**
	 * A custom serializer class.
	 */
	@Override
	public Class<? extends ISerializer> bindISerializer() {
		return SemverSerializer.class;
	}

	/** Bind custom SEMVER issue severities provider that operates based on {@link SemverIssueCodes}. */
	public Class<? extends IssueSeveritiesProvider> bindIssueSeveritiesProvider() {
		return SemverIssueSeveritiesProvider.class;
	}

	/** Avoid races in internal serializer caches */
	public void configureSerializerPatches(Binder binder) {
		new SerializerPatchModule().configure(binder);
	}

	/** Fix serialization of data type values */
	public Class<? extends SemverSyntacticSequencer> bindSemverSyntacticSequencer() {
		return CustomSemverSyntacticSequencer.class;
	}
}
