/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.tests;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileRunner;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtFolder;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtParentRunner;
import org.eclipse.n4js.utils.Strings;
import org.junit.After;
import org.junit.Assert;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 *
 */
public abstract class AbstractXtParentRunnerTest {

	static class DynamicXtFolder implements XtFolder {
		final String value;

		/**  */
		public DynamicXtFolder(String value) {
			this.value = value;
		}

		@Override
		public Class<? extends Annotation> annotationType() {
			return XtFolder.class;
		}

		@Override
		public String value() {
			return value;
		}
	}

	TestRunSimulator runListener;
	Description parentDescription;
	List<XtFileRunner> children;
	LinkedHashMultimap<String, Object> events = LinkedHashMultimap.create();
	Multimap<String, String> results = LinkedHashMultimap.create();

	class TestRunSimulator extends RunListener {

		void run(String folderName) throws Exception {
			XtFolder dynamicXtFolder = new DynamicXtFolder(folderName);

			alterAnnotationOn(XtTestSetupTestMockup.class, XtFolder.class, dynamicXtFolder);

			XtParentRunner xtParentRunner = new XtParentRunner(XtTestSetupTestMockup.class);
			RunNotifier rn = new RunNotifier();
			rn.addListener(this);

			parentDescription = xtParentRunner.getDescription();
			children = xtParentRunner.getChildren();
			xtParentRunner.run(rn);
		}

		@Override
		public void testRunStarted(Description description) throws Exception {
			events.put("testRunStarted", description);
		}

		@Override
		public void testRunFinished(Result result) throws Exception {
			events.put("testRunFinished", result);
		}

		@Override
		public void testStarted(Description description) throws Exception {
			events.put("testStarted", description);
		}

		@Override
		public void testFinished(Description description) throws Exception {
			events.put("testFinished", description);
			String methodName = getMethodOrDisplayName(description);
			String commentOrMethodName = getCommentOrMethodName(description);
			results.put(commentOrMethodName, "Passed: " + methodName);
		}

		@Override
		public void testFailure(Failure failure) throws Exception {
			events.put("testFailure", failure);
			Description description = failure.getDescription();
			String methodName = getMethodOrDisplayName(description);
			String commentOrMethodName = getCommentOrMethodName(description);
			results.put(commentOrMethodName, "Failed: " + methodName + ". " + failure.getMessage());
		}

		@Override
		public void testAssumptionFailure(Failure failure) {
			events.put("testAssumptionFailure", failure);
			Description description = failure.getDescription();
			String methodName = getMethodOrDisplayName(description);
			String commentOrMethodName = getCommentOrMethodName(description);
			results.put(commentOrMethodName, "Failed Assumption: " + methodName + ". " + failure.getMessage());
		}

		@Override
		public void testIgnored(Description description) throws Exception {
			events.put("testIgnored", description);
			String methodName = getMethodOrDisplayName(description);
			String commentOrMethodName = getCommentOrMethodName(description);
			results.put(commentOrMethodName, "Ignored: " + methodName);
		}

		String getCommentOrMethodName(Description description) {
			String methodName = description.getMethodName();
			String displayName = description.getDisplayName();
			String commentOrMethodName = methodName == null ? displayName : displayName.substring(methodName.length());
			return commentOrMethodName;
		}

		String getMethodOrDisplayName(Description description) {
			String methodName = description.getMethodName();
			String displayName = description.getDisplayName();
			String methodOrDisplayName = methodName == null ? displayName : methodName;
			return methodOrDisplayName;
		}
	}

	/** After every test run check for test assumption failures */
	@After
	public void endWithoutTestAssumptionFailures() {
		assertNoTestAssumptionFailures();
	}

	void run(String folderName) throws Exception {
		runListener = new TestRunSimulator();
		runListener.run(folderName);
	}

	void assertFiles(String files) {
		Assert.assertEquals(files, Strings.join("\n", c -> c.file.getName(), children));
	}

	void assertTestStructure(String string) {
		String structure = getChild(parentDescription, 1);
		Assert.assertEquals(string, structure);
	}

	private String getChild(Description descr, int depth) {
		String structure = descr.getDisplayName();

		for (Description child : descr.getChildren()) {
			structure += "\n " + "+++++++++".substring(0, depth) + " " + getChild(child, depth + 1);
		}

		return structure;
	}

	void assertEventNames(String eventNames) {
		Assert.assertEquals(eventNames, Strings.join("\n", events.keys()));
	}

	void assertResults(String expectedResults) {
		Assert.assertEquals(expectedResults, Strings.join("\n", results.values()));
	}

	void assertResult(String methodName, String expectedResults) {
		if (results.containsKey(methodName)) {
			Assert.assertEquals(expectedResults, Strings.join("\n", results.get(methodName)));
		} else {
			String resultStr = Strings.join("\n", e -> e.getKey() + " => " + e.getValue(), results.entries());
			Assert.fail("'" + methodName + "' not found. Results are:\n" + resultStr);
		}
	}

	void assertNoTestAssumptionFailures() {
		Assert.assertFalse("Test Assumption Failure: " + Strings.join("\n", events.get("testAssumptionFailure")),
				events.containsKey("testAssumptionFailure"));
	}

	static <A extends Annotation, A_NEW extends A> void alterAnnotationOn(Class<?> clazzToLookFor,
			Class<A> annotationToAlter, A_NEW annotationValue) {

		// Supported by JDK 8+
		try {
			// In JDK8 Class has a private method called annotationData().
			// We first need to invoke it to obtain a reference to AnnotationData class which is a private class
			Method method = Class.class.getDeclaredMethod("annotationData");
			method.setAccessible(true);
			// Since AnnotationData is a private class we cannot create a direct reference to it. We will have to
			// manage with just Object
			Object annotationData = method.invoke(clazzToLookFor);
			// We now look for the map called "annotations" within AnnotationData object.
			Field fieldAnnotations = annotationData.getClass().getDeclaredField("annotations");
			fieldAnnotations.setAccessible(true);
			@SuppressWarnings("unchecked")
			Map<Class<? extends Annotation>, Annotation> map = (Map<Class<? extends Annotation>, Annotation>) fieldAnnotations
					.get(annotationData);
			map.put(annotationToAlter, annotationValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
