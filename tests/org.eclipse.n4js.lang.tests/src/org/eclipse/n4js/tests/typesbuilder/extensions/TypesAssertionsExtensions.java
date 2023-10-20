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
package org.eclipse.n4js.tests.typesbuilder.extensions;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.resource.UserDataMapper;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.inject.Inject;

public class TypesAssertionsExtensions {

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	private TypeEqualityAssertionsExtension typeEqX;

	public Iterable<IEObjectDescription> assertAllExportedElementAreOnIndex(String phase,
			IResourceDescriptions resourceDescriptions,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairs) {

		Iterable<IEObjectDescription> eoDescs = flatten(map(resourceDescriptions
				.getAllResourceDescriptions(), rd -> rd.getExportedObjects()));
		int expectedTestContentCount = expectedExportedTypeToNamePairs.size();// type roots
		assertEquals(phase + ": count of elements are marked as exported should equal to EResourceDescriptions",
				expectedTestContentCount, size(eoDescs));
		typeEqX.assertExpectedTypes(phase, eoDescs, expectedExportedTypeToNamePairs);
		return eoDescs;
	}

	public void assertUserDataCreated(String phase, Resource testResource) {
		IResourceDescriptions resourceDescriptions = assertIndexHasBeenFilled(phase, testResource);
		Iterable<IEObjectDescription> eoDescs = flatten(map(resourceDescriptions
				.getAllResourceDescriptions(), rd -> rd.getExportedObjects()));
		IEObjectDescription syntaxEoDesc = head(eoDescs);
		assertNotNull(phase + ": user data not found",
				syntaxEoDesc.getUserData(UserDataMapper.USER_DATA_KEY_SERIALIZED_SCRIPT));
	}

	public void assertUserDataCreated(String phase, IResourceDescription resourceDescription) {
		Iterable<IEObjectDescription> eoDescs = resourceDescription.getExportedObjects();
		IEObjectDescription syntaxEoDesc = head(eoDescs);
		assertNotNull(phase + ": user data not found",
				syntaxEoDesc.getUserData(UserDataMapper.USER_DATA_KEY_SERIALIZED_SCRIPT));
	}

	public IResourceDescriptions assertIndexHasBeenFilled(String phase, Resource testResource) {
		assertTrue(phase + ": " + testResource.getErrors().toString(), testResource.getErrors().isEmpty());
		IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(testResource);
		assertFalse(phase + ": Test that the index has been filled",
				isEmpty(resourceDescriptions.getAllResourceDescriptions()));
		return resourceDescriptions;
	}

	public void assertExpectedTypesCount(String phase, Resource testResource, int expectedTypesCount) {
		assertEquals(phase + ": test content count", 2, testResource.getContents().size());
		TModule exportedScript = (TModule) last(testResource.getContents());
		assertEquals(phase + ": Should have the expected count of types", expectedTypesCount,
				size(exportedScript.getTypesAndFunctions()) + exportedScript.getExportedVariables().size());
	}
}
