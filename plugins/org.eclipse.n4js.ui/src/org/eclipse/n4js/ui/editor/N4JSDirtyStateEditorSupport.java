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
package org.eclipse.n4js.ui.editor;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Event;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.DirtyStateEditorSupport;
import org.eclipse.xtext.ui.editor.SchedulingRuleFactory;

import org.eclipse.n4js.resource.N4JSResource;

/**
 * Used to use a custom update job that is triggered when a change in the project happened. It has to be customized as
 * otherwise the job family cannot be accessed.
 */
public class N4JSDirtyStateEditorSupport extends DirtyStateEditorSupport {
	private static ISchedulingRule SCHEDULING_RULE = SchedulingRuleFactory.INSTANCE.newSequence();
	/**
	 * The family of the update job that is spawned by the state change event.
	 */
	public static final Object FAMILY_UPDATE_JOB = new Object();
	private N4JSUpdateEditorStateJob updateEditorStateJob;

	private class N4JSUpdateEditorStateJob extends DirtyStateEditorSupport.UpdateEditorStateJob {

		N4JSUpdateEditorStateJob(ISchedulingRule rule) {
			super(rule);
		}

		@Override
		public boolean belongsTo(Object family) {
			return FAMILY_UPDATE_JOB == family;
		}

		@Override
		protected void scheduleFor(IResourceDescription.Event event) {
			super.scheduleFor(event);
		}
	}

	@Override
	protected N4JSUpdateEditorStateJob createUpdateEditorJob() {
		return new N4JSUpdateEditorStateJob(SCHEDULING_RULE);
	}

	@Override
	public void scheduleUpdateEditorJob(final IResourceDescription.Event event) {
		N4JSUpdateEditorStateJob job = updateEditorStateJob;
		if (job == null) {
			job = createUpdateEditorJob();
			updateEditorStateJob = job;
		}
		job.scheduleFor(event);
	}

	@Override
	protected Collection<Resource> collectAffectedResources(XtextResource resource, Event event) {
		Collection<Resource> result = super.collectAffectedResources(resource, event);
		if (result.isEmpty())
			return result;
		Iterator<Resource> iter = result.iterator();
		while (iter.hasNext()) {
			Resource affected = iter.next();
			if (affected instanceof N4JSResource) {
				((N4JSResource) affected).forceSetLoaded();
			}
		}
		return result;
	}

	@Override
	public void modelChanged(XtextResource resource) {
		if (resource == null || !getDirtyResource().isInitialized())
			return;
		resource.getContents(); // trigger init
		super.modelChanged(resource);
	}
}
