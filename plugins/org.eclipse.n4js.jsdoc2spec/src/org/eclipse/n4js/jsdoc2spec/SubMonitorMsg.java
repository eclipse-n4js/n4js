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
package org.eclipse.n4js.jsdoc2spec;

import java.util.function.Consumer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

/**
 * This class is a {@link SubMonitor} that also relays the task names to a given callback method.<br>
 * Note: Unfortunately a {@link SubMonitor} is final.
 */
public class SubMonitorMsg implements IProgressMonitor {
	private final IProgressMonitor monitor;
	private final SubMonitor subMonitor;
	private final Consumer<String> callbackMsg;
	private final Consumer<String> callbackErr;
	private final CheckCanceled checkCanceled;

	/**
	 * get a NullProgressMonitor here!
	 */
	static public SubMonitorMsg nullProgressMonitor() {
		return new SubMonitorMsg();
	}

	/**
	 * Wraps a {@link SubMonitor}
	 */
	private SubMonitorMsg() {
		this.subMonitor = null;
		this.monitor = new NullProgressMonitor();
		this.callbackMsg = this::devNull;
		this.callbackErr = this::devNull;
		this.checkCanceled = this::noCheck;
	}

	void devNull(@SuppressWarnings("unused") String str) {
		// for Null constructor only
	}

	void noCheck(@SuppressWarnings("unused") IProgressMonitor m) {
		// for Null constructor only
	}

	/**
	 * Wraps a {@link SubMonitor}
	 */
	public SubMonitorMsg(SubMonitor subMonitor, Consumer<String> callbackMsg, CheckCanceled checkCanceled) {
		this(subMonitor, callbackMsg, callbackMsg, checkCanceled);
	}

	/**
	 * Wraps a {@link SubMonitor}
	 */
	public SubMonitorMsg(SubMonitor subMonitor, Consumer<String> callbackMsg, Consumer<String> callbackErr,
			CheckCanceled checkCanceled) {

		this.subMonitor = subMonitor;
		this.monitor = subMonitor;
		this.callbackMsg = callbackMsg;
		this.callbackErr = callbackErr;
		this.checkCanceled = checkCanceled;
	}

	/**
	 * c.f. {@link SubMonitor#newChild(int)}
	 */
	public SubMonitorMsg newChild(int i) {
		SubMonitor child = subMonitor.newChild(i);
		return new SubMonitorMsg(child, callbackMsg, callbackErr, checkCanceled);
	}

	/**
	 * c.f. {@link SubMonitor#convert(org.eclipse.core.runtime.IProgressMonitor, int)}
	 */
	public SubMonitorMsg convert(int i) {
		SubMonitor sub = SubMonitor.convert(subMonitor, i);
		return new SubMonitorMsg(sub, callbackMsg, callbackErr, checkCanceled);
	}

	@Override
	public void beginTask(String name, int totalWork) {
		monitor.beginTask(name, totalWork);
		fireMsgString(name);
	}

	@Override
	public void done() {
		monitor.done();
	}

	@Override
	public void internalWorked(double work) {
		monitor.internalWorked(work);
	}

	@Override
	public boolean isCanceled() {
		return monitor.isCanceled();
	}

	@Override
	public void setCanceled(boolean value) {
		monitor.setCanceled(value);
	}

	@Override
	public void setTaskName(String name) {
		monitor.setTaskName(name);
		fireMsgString(name);
	}

	@Override
	public void subTask(String name) {
		monitor.subTask(name);
		fireMsgString(name);
	}

	@Override
	public void worked(int work) {
		monitor.worked(work);
	}

	private void fireMsgString(String msg) {
		callbackMsg.accept(msg);
	}

	private void fireErrString(String msg) {
		callbackErr.accept(msg);
	}

	/**
	 * Relays the message to the callback function. The subtask name is not modified.
	 */
	public String pushMessage(String msg) {
		fireMsgString(msg);
		return msg;
	}

	/**
	 * Relays the message to the callback function. The subtask name is not modified.
	 */
	public String pushError(String msg) {
		fireErrString(msg);
		return msg;
	}

	/**
	 * Relays the check for cancellation events.
	 */
	public void checkCanceled() throws InterruptedException {
		checkCanceled.check(monitor);
	}
}
