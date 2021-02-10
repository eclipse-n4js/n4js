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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileRunner;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtFolder;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtParentRunner;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.xbase.lib.Pair;
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
	List<Pair<String, Object>> events = new ArrayList<>();
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
			events.add(Pair.of("testRunStarted", description));
		}

		@Override
		public void testRunFinished(Result result) throws Exception {
			events.add(Pair.of("testRunFinished", result));
		}

		@Override
		public void testStarted(Description description) throws Exception {
			events.add(Pair.of("testStarted", description));
		}

		@Override
		public void testFinished(Description description) throws Exception {
			events.add(Pair.of("testFinished", description));
			String methodName = getMethodOrDisplayName(description);
			String commentOrArgs = getCommentOrArgs(description);
			results.put(commentOrArgs, "Passed: " + methodName);
		}

		@Override
		public void testFailure(Failure failure) throws Exception {
			events.add(Pair.of("testFailure", failure));
			Description description = failure.getDescription();
			String methodName = getMethodOrDisplayName(description);
			String commentOrArgs = getCommentOrArgs(description);
			results.put(commentOrArgs, "Failed: " + methodName + ". " + failure.getMessage());
		}

		@Override
		public void testAssumptionFailure(Failure failure) {
			events.add(Pair.of("testAssumptionFailure", failure));
			Description description = failure.getDescription();
			String methodName = getMethodOrDisplayName(description);
			String commentOrArgs = getCommentOrArgs(description);
			results.put(commentOrArgs, "Failed Assumption: " + methodName + ". " + failure.getMessage());
		}

		@Override
		public void testIgnored(Description description) throws Exception {
			events.add(Pair.of("testIgnored", description));
			String methodName = getMethodOrDisplayName(description);
			String commentOrArgs = getCommentOrArgs(description);
			results.put(commentOrArgs, "Ignored: " + methodName);
		}

		String getCommentOrArgs(Description description) {
			String methodName = description.getMethodName();
			String displayName = description.getDisplayName();
			String commentOrArgs = methodName == null ? displayName : displayName.substring(methodName.length());
			return commentOrArgs;
		}

		String getMethodOrDisplayName(Description description) {
			String methodName = description.getMethodName();
			String displayName = description.getDisplayName();
			String methodOrDisplayName = methodName == null ? displayName : methodName;
			return methodOrDisplayName;
		}
	}

	void run(String folderName) throws Exception {
		runListener = new TestRunSimulator();
		runListener.run(folderName);
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
		Assert.assertEquals(eventNames, Strings.toString(p -> p.getKey(), events));
	}

	void assertResults(String expectedResults) {
		Assert.assertEquals(expectedResults, Strings.join("\n", results.values()));
	}

	void assertSingleTestResult(String methodName, String expectedResults) {
		Assert.assertEquals(expectedResults, Strings.join("\n", results.get(methodName)));
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
