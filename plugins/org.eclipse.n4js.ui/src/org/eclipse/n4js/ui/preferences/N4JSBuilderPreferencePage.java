/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from
 * 		org.eclipse.xtext.builder.preferences.BuilderPreferencePage,
 * 		org.eclipse.xtext.builder.preferences.BuilderConfigurationBlock,
 * 		org.eclipse.xtext.ui.editor.syntaxcoloring.SyntaxColoringPreferencePage,
 * 		org.eclipse.xtext.ui.preferences.PropertyAndPreferencePage and
 * 		org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock
 *  in bundles org.eclipse.xtext.builder and org.eclipse.xtext.ui
 *	available under the terms of the Eclipse Public License 2.0
 *  Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 *  Part of these have been itself copied from
 *  		org.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage and
 *  		org.eclipse.jdt.internal.ui.preferences.OptionsConfigurationBlock
 *      in bundle org.eclipse.jdt.ui
 *      available under the terms of the Eclipse Public License 2.0
 *      Copyright (c) 2000, 2011 IBM Corporation and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferencePageContainer;
import org.eclipse.n4js.generator.CompilerDescriptor;
import org.eclipse.n4js.generator.CompilerProperties;
import org.eclipse.n4js.generator.ICompositeGenerator;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.xtext.builder.DerivedResourceCleanerJob;
import org.eclipse.xtext.builder.EclipseOutputConfigurationProvider;
import org.eclipse.xtext.builder.preferences.Messages;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock;
import org.eclipse.xtext.util.Triple;
import org.eclipse.xtext.util.Tuples;

import com.google.common.collect.MapDifference.ValueDifference;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Copied and adapted from org.eclipse.xtext.builder.preferences.BuilderPreferencePage.
 * <p>
 * This preference page is assembly of functionality copied from:
 * <ul>
 * <li>org.eclipse.xtext.builder.preferences.BuilderPreferencePage</li>
 * <li>org.eclipse.xtext.ui.editor.syntaxcoloring.SyntaxColoringPreferencePage</li>
 * <li>org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock</li>
 * <li>org.eclipse.xtext.builder.preferences.BuilderConfigurationBlock</li>
 * <li>org.eclipse.xtext.ui.preferences.PropertyAndPreferencePage</li>
 * </ul>
 * <p/>
 *
 * In constrast to the BuilderPreferencePage this preference page allows the configuration of multiple generators (in
 * our case compilers resp. transpilers). It reuses the approach from SyntaxColoringPreferencePage.
 */
@SuppressWarnings("restriction")
public class N4JSBuilderPreferencePage extends AbstractN4JSPreferencePage<CompilerDescriptor> {
	EclipseOutputConfigurationProvider configurationProvider;
	private Provider<DerivedResourceCleanerJob> cleanerProvider;

	@Inject
	private N4JSPreferenceStoreAccessor preferenceStoreAccessor;

	/**
	 * Initializes default compiler configuration.
	 */
	@Inject
	public N4JSBuilderPreferencePage(ICompositeGenerator compositeGenerator) {
		super(new ArrayList<Triple<String, String, CompilerDescriptor>>());
		for (CompilerDescriptor compilerDescriptor : compositeGenerator.getCompilerDescriptors()) {
			this.components.add(Tuples.create(compilerDescriptor.getIdentifier(), compilerDescriptor.getName(),
					compilerDescriptor));
		}
	}

	@Override
	protected CompilerProperties[] getComponentPropertiesValues() {
		CompilerProperties[] values = CompilerProperties.values();
		return values;
	}

	/**
	 * This method has been copied from org.eclipse.xtext.builder.preferences.BuilderPreferencePage.
	 */
	@Inject
	public void setConfigurationProvider(EclipseOutputConfigurationProvider configurationProvider) {
		this.configurationProvider = configurationProvider;
		rebuildCount = getRebuildCount();
	}

	/**
	 * This method has been copied from org.eclipse.xtext.builder.preferences.BuilderPreferencePage.
	 */
	@Inject
	public void setCleanerProvider(Provider<DerivedResourceCleanerJob> cleanerProvider) {
		this.cleanerProvider = cleanerProvider;
	}

	/**
	 * This method has been copied from org.eclipse.xtext.builder.preferences.BuilderConfigurationBlock.
	 */
	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		String title = Messages.BuilderConfigurationBlock_SettingsChanged_Title;
		String message;
		if (workspaceSettings) {
			message = Messages.BuilderConfigurationBlock_SettingsChanged_WorkspaceBuild;
		} else {
			message = Messages.BuilderConfigurationBlock_SettingsChanged_ProjectBuild;
		}
		return new String[] { title, message };
	}

	/**
	 * This property has been copied and adapted from org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock.
	 */
	int rebuildCount = -1;

	/**
	 * This method has been copied and adapted from org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock.
	 */
	int getRebuildCount() {
		return getPreferenceStore().getDefaultInt(REBUILD_COUNT_KEY);
	}

	/**
	 * This method has been copied and adapted from org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock.
	 */
	void incrementRebuildCount() {
		getPreferenceStore().setDefault(REBUILD_COUNT_KEY, getRebuildCount() + 1);
	}

	/**
	 * This method has been copied from org.eclipse.xtext.builder.preferences.BuilderConfigurationBlock.
	 *
	 * @param projectToBuild
	 *            the project to build
	 * @return the configured build job
	 */
	protected Job getBuildJob(IProject projectToBuild) {
		Job buildJob = new OptionsConfigurationBlock.BuildJob(Messages.BuilderConfigurationBlock_BuildJob_Title0,
				projectToBuild);
		buildJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
		buildJob.setUser(true);
		return buildJob;
	}

	/**
	 * This method has been copied from org.eclipse.xtext.builder.preferences.BuilderPreferencePage.
	 */
	private void scheduleCleanerJobIfNecessary(IPreferencePageContainer preferencePageContainer) {
		Map<String, ValueDifference<String>> changes = getPreferenceChanges();
		for (String key : changes.keySet()) {
			if (key.matches("^" + CompilerProperties.OUTPUT_PREFERENCE_TAG + "\\.\\w+\\."
					+ CompilerProperties.OUTPUT_PREFERENCE_TAG + "$")) {
				ValueDifference<String> difference = changes.get(key);
				scheduleCleanerJob(preferencePageContainer, difference.rightValue());
			}
		}
	}

	/**
	 * This method has been copied from org.eclipse.xtext.builder.preferences.BuilderPreferencePage.
	 */
	private void scheduleCleanerJob(IPreferencePageContainer preferencePageContainer, String folderNameToClean) {
		DerivedResourceCleanerJob derivedResourceCleanerJob = cleanerProvider.get();
		derivedResourceCleanerJob.setUser(true);
		derivedResourceCleanerJob.initialize(getProject(), folderNameToClean);
		if (preferencePageContainer != null) {
			IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getContainer();
			container.registerUpdateJob(derivedResourceCleanerJob);
		} else {
			derivedResourceCleanerJob.schedule();
		}
	}

	@Override
	protected void refreshAttributes() {
		Collections.sort(components, this);
		Set<OutputConfiguration> outputConfigs = configurationProvider.getOutputConfigurations(getProject());
		for (Triple<String, String, CompilerDescriptor> compiler : components) {
			for (OutputConfiguration outputConfiguration : outputConfigs) {
				if (outputConfiguration.getName().equals(compiler.getFirst())) {
					if (compiler.getThird().getOutputConfiguration() == null) {
						compiler.getThird().setOutputConfiguration(outputConfiguration);
					}
				}
			}
		}
		for (Triple<String, String, CompilerDescriptor> compiler : components) {
			preferenceStoreAccessor
					.populateCompilerConfiguration(compiler.getFirst(),
							compiler.getThird());
		}
	}

	@Override
	public boolean performOk() {
		scheduleCleanerJobIfNecessary(getContainer());
		return super.performOk();
	}

	/**
	 * This method has been copied and adapted from org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock.
	 */
	@Override
	protected boolean processChanges(IWorkbenchPreferenceContainer container) {
		boolean needsBuild = !getPreferenceChanges().isEmpty() | projectSpecificChanged;
		boolean doBuild = false;
		if (needsBuild) {
			int count = getRebuildCount();
			if (count > rebuildCount) {
				needsBuild = false;
				rebuildCount = count;
			}
		}
		if (needsBuild) {
			String[] strings = getFullBuildDialogStrings(project == null);
			if (strings != null) {
				MessageDialog dialog = new MessageDialog(this.getShell(), strings[0], null, strings[1],
						MessageDialog.QUESTION,
						new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL,
								IDialogConstants.CANCEL_LABEL },
						2);
				int res = dialog.open();
				if (res == 0) {
					doBuild = true;
				} else if (res != 1) {
					return false;
				}
			}
		}
		if (container != null) {
			if (doBuild) {
				incrementRebuildCount();
				container.registerUpdateJob(getBuildJob(getProject()));
			}
		} else {
			if (doBuild) {
				getBuildJob(getProject()).schedule();
			}
		}
		return true;
	}

}
