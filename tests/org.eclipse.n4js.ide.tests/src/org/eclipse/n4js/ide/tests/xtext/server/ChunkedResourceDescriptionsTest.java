/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.xtext.server;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.n4js.xtext.ide.server.util.IndexedChunkedResourceDescriptions;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.persistence.SerializableEObjectDescription;
import org.eclipse.xtext.resource.persistence.SerializableResourceDescription;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.math.StatsAccumulator;

/**
 * Performance test for resource description implementations.
 */
@Ignore("TODO Implement a way to make this a real test with assertions on the timing")
@SuppressWarnings({ "javadoc", "restriction" })
public class ChunkedResourceDescriptionsTest {

	@Test
	public void testChunkedPerformance_EmptyContainers() {
		ChunkedResourceDescriptions descriptions = new ChunkedResourceDescriptions();
		// Test with 1000 modules, all empty
		for (int i = 0; i < 1000; i++) {
			descriptions.setContainer("" + i, new ResourceDescriptionsData(Collections.emptyList()));
		}

		QualifiedName name = QualifiedName.create("any");
		for (int warmup = 0; warmup < 100; warmup++) {
			descriptions.getExportedObjects(EcorePackage.Literals.EOBJECT, name, false);
		}

		StatsAccumulator stats = new StatsAccumulator();
		for (int iteration = 0; iteration < 100; iteration++) {
			Stopwatch sw = Stopwatch.createStarted();
			for (int i = 0; i < 1000; i++) {
				Iterable<IEObjectDescription> emptyResult = descriptions
						.getExportedObjects(EcorePackage.Literals.EOBJECT, name, false);
				Assert.assertEquals(0, Iterables.size(emptyResult));
			}
			sw.stop();
			stats.add(sw.elapsed(TimeUnit.MILLISECONDS));
		}
		System.out.println(stats.snapshot());
	}

	@Test
	public void testChunkedPerformance_FilledContainers() {
		ChunkedResourceDescriptions descriptions = new ChunkedResourceDescriptions();
		// Test with 1000 modules, all empty
		QualifiedName name = QualifiedName.create("any");
		for (int i = 0; i < 1000; i++) {
			ResourceDescriptionsData segment = new ResourceDescriptionsData(Collections.emptyList());
			SerializableResourceDescription resDesc = new SerializableResourceDescription();
			resDesc.setURI(URI.createURI("" + i));
			SerializableEObjectDescription objDesc = new SerializableEObjectDescription();
			objDesc.setQualifiedName(name.append("" + i));
			objDesc.setEClass(EcorePackage.Literals.EOBJECT);
			resDesc.setDescriptions(Collections.singletonList(objDesc));
			segment.addDescription(resDesc.getURI(), resDesc);
			descriptions.setContainer("" + i, segment);
		}

		for (int warmup = 0; warmup < 100; warmup++) {
			descriptions.getExportedObjects(EcorePackage.Literals.EOBJECT, name, false);
		}

		StatsAccumulator stats = new StatsAccumulator();
		for (int iteration = 0; iteration < 100; iteration++) {
			Stopwatch sw = Stopwatch.createStarted();
			for (int i = 0; i < 1000; i++) {
				Iterable<IEObjectDescription> emptyResult = descriptions
						.getExportedObjects(EcorePackage.Literals.EOBJECT, name.append("" + 1), false);
				Assert.assertEquals(1, Iterables.size(emptyResult));
			}
			sw.stop();
			stats.add(sw.elapsed(TimeUnit.MILLISECONDS));
		}
		System.out.println(stats.snapshot());
	}

	@Test
	public void testDataPerformance_Empty() {
		ResourceDescriptionsData descriptions = new ResourceDescriptionsData(Collections.emptyList());

		QualifiedName name = QualifiedName.create("any");
		for (int warmup = 0; warmup < 100; warmup++) {
			descriptions.getExportedObjects(EcorePackage.Literals.EOBJECT, name, false);
		}

		StatsAccumulator stats = new StatsAccumulator();
		for (int iteration = 0; iteration < 100; iteration++) {
			Stopwatch sw = Stopwatch.createStarted();
			for (int i = 0; i < 1000; i++) {
				Iterable<IEObjectDescription> emptyResult = descriptions
						.getExportedObjects(EcorePackage.Literals.EOBJECT, name, false);
				Assert.assertEquals(0, Iterables.size(emptyResult));
			}
			sw.stop();
			stats.add(sw.elapsed(TimeUnit.MILLISECONDS));
		}
		System.out.println(stats.snapshot());
	}

	@Test
	public void testDataPerformance_Filled() {
		ResourceDescriptionsData descriptions = new ResourceDescriptionsData(Collections.emptyList());
		// Test with 1000 modules, all empty
		QualifiedName name = QualifiedName.create("any");
		for (int i = 0; i < 1000; i++) {
			SerializableResourceDescription resDesc = new SerializableResourceDescription();
			SerializableEObjectDescription objDesc = new SerializableEObjectDescription();
			objDesc.setQualifiedName(name.append("" + i));
			objDesc.setEClass(EcorePackage.Literals.EOBJECT);
			resDesc.setDescriptions(Collections.singletonList(objDesc));
			descriptions.addDescription(URI.createURI("" + i), resDesc);
		}

		for (int warmup = 0; warmup < 100; warmup++) {
			descriptions.getExportedObjects(EcorePackage.Literals.EOBJECT, name, false);
		}

		StatsAccumulator stats = new StatsAccumulator();
		for (int iteration = 0; iteration < 100; iteration++) {
			Stopwatch sw = Stopwatch.createStarted();
			for (int i = 0; i < 1000; i++) {
				Iterable<IEObjectDescription> emptyResult = descriptions
						.getExportedObjects(EcorePackage.Literals.EOBJECT, name.append("" + 1), false);
				Assert.assertEquals(1, Iterables.size(emptyResult));
			}
			sw.stop();
			stats.add(sw.elapsed(TimeUnit.MILLISECONDS));
		}
		System.out.println(stats.snapshot());
	}

	@Test
	public void testIndexedChunkedPerformance_EmptyContainers() {
		IndexedChunkedResourceDescriptions descriptions = new IndexedChunkedResourceDescriptions();
		// Test with 1000 modules, all empty
		for (int i = 0; i < 1000; i++) {
			descriptions.setContainer("" + i, new ResourceDescriptionsData(Collections.emptyList()));
		}

		QualifiedName name = QualifiedName.create("any");
		for (int warmup = 0; warmup < 100; warmup++) {
			descriptions.getExportedObjects(EcorePackage.Literals.EOBJECT, name, false);
		}

		StatsAccumulator stats = new StatsAccumulator();
		for (int iteration = 0; iteration < 100; iteration++) {
			Stopwatch sw = Stopwatch.createStarted();
			for (int i = 0; i < 1000; i++) {
				Iterable<IEObjectDescription> emptyResult = descriptions
						.getExportedObjects(EcorePackage.Literals.EOBJECT, name, false);
				Assert.assertEquals(0, Iterables.size(emptyResult));
			}
			sw.stop();
			stats.add(sw.elapsed(TimeUnit.MILLISECONDS));
		}
		System.out.println(stats.snapshot());
	}

	@Test
	public void testIndexedChunkedPerformance_FilledContainers() {
		IndexedChunkedResourceDescriptions descriptions = new IndexedChunkedResourceDescriptions();
		// Test with 1000 modules, all empty
		QualifiedName name = QualifiedName.create("any");
		for (int i = 0; i < 1000; i++) {
			ResourceDescriptionsData segment = new ResourceDescriptionsData(Collections.emptyList());
			SerializableResourceDescription resDesc = new SerializableResourceDescription();
			resDesc.setURI(URI.createURI("" + i));
			SerializableEObjectDescription objDesc = new SerializableEObjectDescription();
			objDesc.setQualifiedName(name.append("" + i));
			objDesc.setEClass(EcorePackage.Literals.EOBJECT);
			resDesc.setDescriptions(Collections.singletonList(objDesc));
			segment.addDescription(resDesc.getURI(), resDesc);
			descriptions.setContainer("" + i, segment);
		}

		for (int warmup = 0; warmup < 100; warmup++) {
			descriptions.getExportedObjects(EcorePackage.Literals.EOBJECT, name, false);
		}

		StatsAccumulator stats = new StatsAccumulator();
		for (int iteration = 0; iteration < 100; iteration++) {
			Stopwatch sw = Stopwatch.createStarted();
			for (int i = 0; i < 1000; i++) {
				Iterable<IEObjectDescription> emptyResult = descriptions
						.getExportedObjects(EcorePackage.Literals.EOBJECT, name.append("" + 1), false);
				Assert.assertEquals(1, Iterables.size(emptyResult));
			}
			sw.stop();
			stats.add(sw.elapsed(TimeUnit.MILLISECONDS));
		}
		System.out.println(stats.snapshot());
	}

}