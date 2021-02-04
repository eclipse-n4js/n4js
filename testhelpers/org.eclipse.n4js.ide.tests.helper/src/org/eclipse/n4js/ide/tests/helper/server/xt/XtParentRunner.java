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
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import com.google.common.base.Preconditions;

/**
 *
 */
public class XtParentRunner extends ParentRunner<XtFileRunner> {
	final Class<?> testClass;
	final XtIdeTest ideTest = new XtIdeTest();
	final Path currentProject;
	final Path startLocation;
	final String xtFilesFolder;

	List<XtFileRunner> fileRunners;

	/**
	 */
	public XtParentRunner(Class<?> testClass) throws InitializationError {
		super(XtIdeTest.class); // This will run methods annotated with @BeforeAll/@AfterAll
		this.testClass = testClass;
		this.currentProject = new File("").getAbsoluteFile().toPath();
		this.xtFilesFolder = getFolder();
		this.startLocation = currentProject.resolve(xtFilesFolder);
	}

	@Override
	protected List<XtFileRunner> getChildren() {
		return getOrFindFileRunners();
	}

	@Override
	protected Description describeChild(XtFileRunner child) {
		return child.getDescription();
	}

	@Override
	protected void runChild(XtFileRunner child, RunNotifier notifier) {
		child.run(notifier);
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
							XtFileRunner fileRunner = new XtFileRunner(ideTest, xtFilesFolder, file);
							if (testClassName.equals(fileRunner.getSetupRunnerName())) {
								fileRunners.add(fileRunner);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (

		IOException e) {
			e.printStackTrace();
		}
		return fileRunners;
	}

	private String getFolder() {
		XtFolder[] xtFolders = testClass.getAnnotationsByType(XtFolder.class);
		Preconditions.checkState(xtFolders != null && xtFolders.length == 1);
		String folder = xtFolders[0].value();
		return folder;
	}

}