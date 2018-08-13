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
package org.eclipse.n4js.generator.headless;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.generator.GeneratorException;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Joiner;

/**
 * Class for registering progress during headless compile-actions.
 */
public class N4ProgressStateRecorder {

	/**
	 * Public interface for progress-states.
	 */
	public static interface IProgressState {
		/**
		 * @return user-message.
		 */
		String getMessage();
	}

	/** */
	public static class TimedState<E extends IProgressState> implements IProgressState {
		long time;
		E data;

		/**
		 * @param data
		 *            real state
		 */
		public TimedState(E data) {
			time = System.currentTimeMillis();
			this.data = data;
		}

		@Override
		public String getMessage() {
			return data.getMessage();
		}
	}

	/***/
	public static class Info implements IProgressState {
		private final String msg;

		Info(String msg) {
			this.msg = msg;
		}

		@Override
		public String getMessage() {
			return msg;
		}

	}

	// /// // // // // // // /// // // // // // // /// // // // // // // /// // // // // // //
	LinkedList<IProgressState> progressEntries = new LinkedList<>();
	private final long start;
	private int indent = 0;

	// /// // // // // // // /// // // // // // // /// // // // // // // /// // // // // // //
	/**
	 *
	 */
	public N4ProgressStateRecorder() {
		start = System.currentTimeMillis();
	}

	/**
	 * dump the whole list into an stringbuffer.
	 *
	 * @param sb
	 *            where the list will be rendered into.
	 */
	public void dumpTo(StringBuffer sb) {
		for (IProgressState entry : progressEntries) {
			if (entry instanceof TimedState<?>) {
				TimedState<?> timedState = (TimedState<?>) entry;
				sb.append(String.format("%10d - %s \n", (timedState.time - start), timedState.data.getMessage()));
			} else {
				sb.append("           - ").append(entry.getMessage());
			}
		}
	}

	/***/
	public void info(String msg) {
		add(new Info(indentToString() + msg));
	}

	/**
	 */
	private String indentToString() {
		return "                                                                                         ".substring(0,
				indent);
	}

	void increaseIndent() {
		indent += 2;
	}

	void decreaseIndent() {
		indent -= 2;
		if (indent < 0)
			indent = 0;
	}

	/***/
	private void add(IProgressState info) {
		progressEntries.add(new TimedState<>(info));
	}

	/***/
	public void markProcessing(IN4JSProject project) {
		info("processing " + project.getProjectName());
		increaseIndent();
	}

	/**
	 * @param project
	 *            done
	 */
	public void markEndProcessing(IN4JSProject project) {
		decreaseIndent();
		info("processing of " + project.getProjectName() + " done ");
	}

	/***/
	public void markStartLoading(MarkedProject markedProject) {
		info("start loading " + markedProject.project.getProjectName());
	}

	/**
	 * @param uri
	 *            failed
	 */
	public void markFailedCreateResource(URI uri) {
		info("failed creating resource with uri=" + uri);
	}

	/**
	 * @param res
	 *            failed resource
	 */
	public void markLoadResourceFailed(Resource res) {
		info("loading of resource failed");
	}

	/**
	 * @param resource
	 *            where.
	 * @param issues
	 *            problematic things
	 */
	public void markResourceIssues(Resource resource, List<Issue> issues) {
		info("issues in resource r="
				+ resource.getURI()
				+ " "
				+ Joiner.on(", ").join(
						issues.stream().map(i -> i.getSeverity() + " " + i.getMessage() + " " + i.getLineNumber())
								.iterator()));
	}

	/**
	 * @param markedProject
	 *            to compile
	 */
	public void markStartCompiling(MarkedProject markedProject) {
		info("Project, compiling " + markedProject.project.getProjectName());
		increaseIndent();
	}

	/**
	 * @param markedProject
	 *            compiled prj.
	 */
	public void markEndCompiling(MarkedProject markedProject) {
		decreaseIndent();
		info("Project, finished compiling of " + markedProject.project.getProjectName());
	}

	/**
	 * @param input
	 *            to generatefor
	 */
	public void markStartCompile(Resource input) {
		info("about to compile " + input.getURI());
		increaseIndent();
	}

	/**
	 * @param input
	 *            resource to compile
	 */
	public void markEndCompile(Resource input) {
		decreaseIndent();
		info("finished compiling " + input.getURI());
	}

	/**
	 * Mark the broken build
	 *
	 * @param e
	 *            cause of breakdown
	 */
	public void markBrokenCompile(GeneratorException e) {
		decreaseIndent();
		info("failed compiling: '" + e.getMessage() + "' line " + e.getLine() + " in " + e.getFile());
	}

	/**
	 * @param input
	 *            skipped
	 */
	public void markSkippedCompile(Resource input) {
		info("skipped compiling of " + input.getURI());
	}

	/**
	 * @param markedProject
	 *            to unload
	 */
	public void markStartUnloading(MarkedProject markedProject) {
		info("about to unload " + markedProject.project.getProjectName());
		increaseIndent();
	}

	/**
	 * @param markedProject
	 *            unloaded
	 */
	public void markFinishedUnloading(MarkedProject markedProject) {
		decreaseIndent();
		info("finished unloading of " + markedProject.project.getProjectName());
	}

	/**
	 * @param res
	 *            to unload
	 */
	public void markUnloadingOf(Resource res) {
		info("unloading of resource " + res.getURI());
	}

	/**
	 * @param e
	 *            exception
	 */
	public void compileException(N4JSCompileErrorException e) {
		add(e);
	}

	/**
	 * Just dump to a logfile.
	 *
	 * @param logFile
	 *            filname to write to. if {@code null} do nothing.
	 */
	public void dumpToLogfile(String logFile) {
		if (logFile == null)
			return;
		StringBuffer sb = new StringBuffer();

		dumpTo(sb);

		try {
			Files.write(new File(logFile).toPath(), sb.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
