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
package org.eclipse.n4js.ide.tests.helper.server.xt;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

/**
 * Superclass to be inherited from when defining a xt test directory. Use the annotations
 * <ul>
 * <li/>{@link XtFolder} to define the directory that contains all .xt files, and
 * <li/>{@link XtSuppressedIssues} to define which issues should be suppressed.
 * </ul>
 */
public class XtParentRunner extends ParentRunner<XtFileRunner> {
	final Class<?> testClass;
	final Path currentProject;
	final Path startLocation;
	final String xtFilesFolder;
	final Set<String> globallySuppressedIssues;

	XtIdeTest ideTest;
	List<XtFileRunner> fileRunners;
	boolean disposed = false;

	/** Constructor */
	public XtParentRunner(Class<?> testClass) throws InitializationError {
		super(testClass); // This will run methods annotated with @BeforeAll/@AfterAll

		// call the following as early as possible; otherwise the loggers will already have cached System.out and
		// System.err and the redirection of AbstractIdeTest would fail:
		AbstractIdeTest.redirectPrintStreams();

		this.testClass = testClass;
		this.currentProject = new File("").getAbsoluteFile().toPath();
		this.xtFilesFolder = getFolder(testClass);
		this.startLocation = currentProject.resolve(xtFilesFolder);
		this.globallySuppressedIssues = getGloballySuppressedIssues(testClass);
	}

	/** Creates the instanceof of {@link XtIdeTest} to be used by child runners for test execution. */
	protected void createIdeTest() {
		invokeBeforeClassMethods(XtIdeTest.class);
		this.ideTest = new XtIdeTest();
	}

	/** Executed after running all child runners. */
	protected void disposeIdeTest() {
		disposed = true;
		Class<?> ideTestClass = ideTest.getClass();
		this.ideTest = null;
		invokeAfterClassMethods(ideTestClass);
	}

	/** Throws exception if this runner is disposed. */
	protected void checkDisposed() {
		if (disposed) {
			Error err = new AssertionError("this " + XtParentRunner.class.getSimpleName() + " is already disposed");
			err.printStackTrace();
			throw err;
		}
	}

	@Override
	public List<XtFileRunner> getChildren() {
		return getOrFindFileRunners();
	}

	@Override
	public Description describeChild(XtFileRunner child) {
		return child.getDescription();
	}

	@Override
	public void run(RunNotifier notifier) {
		checkDisposed();
		if (this.ideTest != null) {
			throw new AssertionError("reentrant invocation of #run()");
		}
		createIdeTest();
		try {
			super.run(notifier);
		} finally {
			disposeIdeTest();
		}
	}

	@Override
	public void runChild(XtFileRunner child, RunNotifier notifier) {
		checkDisposed();
		child.setIdeTest(ideTest);
		try {
			child.run(notifier);
		} finally {
			child.setIdeTest(null);
		}
	}

	private List<XtFileRunner> getOrFindFileRunners() {
		if (fileRunners != null) {
			return fileRunners;
		}

		fileRunners = new ArrayList<>();
		try {
			String testClassName = testClass.getName();
			Files.walkFileTree(startLocation, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
					File file = path.toFile();
					if (file.isFile() && file.getName().endsWith(".xt")) {
						try {
							XtFileRunner fileRunner = new XtFileRunner(testClassName, file, globallySuppressedIssues);
							fileRunners.add(fileRunner);
						} catch (Exception e) {
							System.err.println("Error on file: " + file.getAbsolutePath().toString());
							// precondition failed. Problem should show up in the JUnit view.
							e.printStackTrace();
						}
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		fileRunners.sort(Comparator.comparing(XtFileRunner::getName));
		return fileRunners;
	}

	static private String getFolder(Class<?> testClass) throws InitializationError {
		try {
			for (Method m : testClass.getDeclaredMethods()) {
				XtFolder[] annFolder = m.getDeclaredAnnotationsByType(XtFolder.class);
				if (annFolder != null && annFolder.length > 0) {
					m.setAccessible(true);
					String folderName = (String) m.invoke(null);
					return folderName;
				}
			}
			return null;
		} catch (Exception e) {
			throw new InitializationError(e);
		}
	}

	/**
	 * Returns issue codes of issues that should be suppressed in all test files executed by this parent runner, as
	 * defined by the test class via a method annotated with {@link XtSuppressedIssues} or the
	 * {@link N4JSLanguageConstants#DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS global default}.
	 * <p>
	 * The configuration returned here can be overridden on a per-file basis in the Xt setup by way of an
	 * <code>IssueConfiguration</code>.
	 */
	static private Set<String> getGloballySuppressedIssues(Class<?> testClass) {
		try {
			for (Method m : testClass.getDeclaredMethods()) {
				XtSuppressedIssues[] annFolder = m.getDeclaredAnnotationsByType(XtSuppressedIssues.class);
				if (annFolder != null && annFolder.length > 0) {
					m.setAccessible(true);
					@SuppressWarnings("unchecked")
					Set<String> folderName = (Set<String>) m.invoke(null);
					return ImmutableSet.copyOf(folderName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return N4JSLanguageConstants.DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS;
	}

	static private void invokeBeforeClassMethods(Class<?> testClass) {
		invokeAnnotatedMethods(testClass, null, BeforeClass.class);
	}

	static /* package */ void invokeBeforeMethods(Object test) {
		invokeAnnotatedMethods(test.getClass(), test, Before.class);
	}

	static /* package */ void invokeAfterMethods(Object test) {
		invokeAnnotatedMethods(test.getClass(), test, After.class);
	}

	static private void invokeAfterClassMethods(Class<?> testClass) {
		invokeAnnotatedMethods(testClass, null, AfterClass.class);
	}

	static private void invokeAnnotatedMethods(Class<?> testClass, Object target, Class<? extends Annotation> ann) {
		try {
			// collect methods in well-defined order (cannot use Class#getMethods())
			boolean useStaticMethods = target == null;
			Set<String> instanceMethodNames = new HashSet<>();
			List<Method> methodsToInvoke = new ArrayList<>();
			Class<?> currCls = testClass;
			while (currCls != null && currCls != Object.class) {
				for (Method m : currCls.getDeclaredMethods()) {
					int modifiers = m.getModifiers();
					if (Modifier.isPublic(modifiers) && m.isAnnotationPresent(ann)) {
						boolean wrongStatic = useStaticMethods != Modifier.isStatic(modifiers);
						boolean overriddenInstanceMethod = !useStaticMethods
								&& !instanceMethodNames.add(m.getName());
						if (!wrongStatic && !overriddenInstanceMethod) {
							methodsToInvoke.add(m);
						}
					}
				}
				currCls = currCls.getSuperclass();
			}
			// invoke the methods with those of super classes having priority over those of subclasses
			for (Method m : Lists.reverse(methodsToInvoke)) {
				m.invoke(target);
			}
		} catch (Throwable th) {
			th.printStackTrace();
			throw new AssertionError("exception while invoking @BeforeClass methods on the Xt test class", th);
		}
	}
}
