/**
 * Copyright (c) 2024 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.ide.server.build;

import org.eclipse.lsp4j.ProgressParams;
import org.eclipse.lsp4j.WorkDoneProgressBegin;
import org.eclipse.lsp4j.WorkDoneProgressCreateParams;
import org.eclipse.lsp4j.WorkDoneProgressEnd;
import org.eclipse.lsp4j.WorkDoneProgressNotification;
import org.eclipse.lsp4j.WorkDoneProgressReport;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.utils.TameAutoClosable;
import org.eclipse.n4js.xtext.ide.server.XLanguageServerImpl;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;

/**
 *
 */
public class LspProgessUtil {

	@Inject
	private XLanguageServerImpl languageServer;

	WorkDoneProgressCreateParams startProgress(WorkDoneProgressCreateParams currentProgress, String title,
			String message) {

		if (currentProgress == null) {
			Either<String, Integer> token = Either.forRight((int) (System.currentTimeMillis() % Integer.MAX_VALUE));
			currentProgress = new WorkDoneProgressCreateParams(token);
			languageServer.getLanguageClient().createProgress(currentProgress);

			WorkDoneProgressBegin progressNotification = new WorkDoneProgressBegin();
			progressNotification.setTitle(title);
			progressNotification.setMessage(message);
			progressNotification.setCancellable(false);
			progressNotification.setPercentage(0);
			Either<WorkDoneProgressNotification, Object> notification = Either.forLeft(progressNotification);
			ProgressParams progressParams = new ProgressParams(currentProgress.getToken(), notification);
			languageServer.getLanguageClient().notifyProgress(progressParams);
		}
		return currentProgress;
	}

	void updateProgress(WorkDoneProgressCreateParams currentProgress, String message, int percentage) {
		if (currentProgress != null) {
			WorkDoneProgressReport progressNotification = new WorkDoneProgressReport();
			progressNotification.setMessage(message);
			progressNotification.setCancellable(false);
			progressNotification.setPercentage(percentage);
			Either<WorkDoneProgressNotification, Object> notification = Either.forLeft(progressNotification);
			ProgressParams progressParams = new ProgressParams(currentProgress.getToken(), notification);
			languageServer.getLanguageClient().notifyProgress(progressParams);
		}
	}

	void endProgress(WorkDoneProgressCreateParams currentProgress, String message) {
		if (currentProgress != null) {
			WorkDoneProgressEnd progressNotification = new WorkDoneProgressEnd();
			progressNotification.setMessage(message);
			Either<WorkDoneProgressNotification, Object> notification = Either.forLeft(progressNotification);
			ProgressParams progressParams = new ProgressParams(currentProgress.getToken(), notification);
			languageServer.getLanguageClient().notifyProgress(progressParams);
		}
	}

	ProgressClosable start(String title, String message) {
		WorkDoneProgressCreateParams progress = startProgress(null, title, message);
		return new ProgressClosable(progress);
	}

	class ProgressClosable implements TameAutoClosable {
		final WorkDoneProgressCreateParams progress;
		final Stopwatch stopwatch = Stopwatch.createUnstarted();
		String closeMsg;

		ProgressClosable(WorkDoneProgressCreateParams progress) {
			this.progress = progress;
			this.stopwatch.start();
		}

		public void update(String message, int percentage) {
			updateProgress(progress, message, percentage);
		}

		public void end(String msg) {
			stopwatch.stop();
			closeMsg = msg;
		}

		public void endWithTime(String msg) {
			stopwatch.stop();
			closeMsg = msg + " (" + stopwatch.toString() + ")";
		}

		@Override
		public void close() {
			if (stopwatch.isRunning()) {
				stopwatch.stop();
			}
			endProgress(progress, closeMsg);
		}
	}
}
