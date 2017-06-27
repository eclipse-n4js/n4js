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
package org.eclipse.n4js.runner.nodejs.ui.launch

import org.eclipse.n4js.runner.SystemLoaderInfo
import org.eclipse.core.runtime.CoreException
import org.eclipse.debug.core.ILaunchConfiguration
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab
import org.eclipse.jface.layout.GridDataFactory
import org.eclipse.jface.layout.GridLayoutFactory
import org.eclipse.jface.viewers.ArrayContentProvider
import org.eclipse.jface.viewers.ComboViewer
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.jface.viewers.StructuredSelection
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Group
import org.eclipse.swt.widgets.Text

import static com.google.common.base.Strings.nullToEmpty
import static org.eclipse.n4js.runner.RunConfiguration.CUSTOM_ENGINE_PATH
import static org.eclipse.n4js.runner.RunConfiguration.ENGINE_OPTIONS
import static org.eclipse.n4js.runner.RunConfiguration.SYSTEM_LOADER
import static org.eclipse.n4js.runner.SystemLoaderInfo.*
import static org.eclipse.swt.SWT.*

/**
 * Launch configuration tab for configuring Node.js NODE_PATH and other options.
 */
class NodejsLaunchConfigurationTab extends AbstractLaunchConfigurationTab {

	/** Text field for storing any arbitrary options for execution engine. */
	var Text optionsText;

	/** Text field for storing any arbitrary custom path settings. For instance for node it store NODE_PATH values. */
	var Text customPathText;

	/** Text field for storing the id of the system loader to use. (SystemJS, CommonJS) */
	var ComboViewer systemLoaderCombo;

	@Override
	override createControl(Composite parent) {
		val childControl = new Composite(parent, NONE) => [
			layout = GridLayoutFactory.swtDefaults.create;
			layoutData = GridDataFactory.swtDefaults.grab(true, true).align(FILL, FILL).create;
		];
		customPathText = childControl.createGroupWithMultiText('NODE_PATH');
		optionsText = childControl.createGroupWithMultiText('Node.js options');
		systemLoaderCombo = childControl.createGroupWithComboViewer('System loader');
		systemLoaderCombo.input = SystemLoaderInfo.values;
		control = childControl;
	}

	@Override
	override getName() {
		'Node.js settings';
	}

	@Override
	override initializeFrom(ILaunchConfiguration configuration) {
		try {
			optionsText.text = configuration.getAttribute(ENGINE_OPTIONS, '');
			customPathText.text = configuration.getAttribute(CUSTOM_ENGINE_PATH, '');
			val systemLoader = SystemLoaderInfo.fromString(configuration.getAttribute(SYSTEM_LOADER, ''));
			systemLoaderCombo.selection = new StructuredSelection(if (null === systemLoader) SYSTEM_JS else systemLoader);
		} catch (CoreException e) {
			errorMessage = e.message;
		}
	}

	@Override
	override performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(ENGINE_OPTIONS, nullToEmpty(optionsText.text));
		configuration.setAttribute(CUSTOM_ENGINE_PATH, nullToEmpty(customPathText.text));
		val selection = systemLoaderCombo.selection;
		var systemLoader = SYSTEM_JS; // Initial pessimistic
		if (selection instanceof IStructuredSelection) {
			val firstElement = selection.firstElement;
			if (firstElement instanceof SystemLoaderInfo) {
				systemLoader = firstElement;
			}
		}
		configuration.setAttribute(SYSTEM_LOADER, nullToEmpty(systemLoader.id));
	}

	@Override
	override setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(CUSTOM_ENGINE_PATH, '');
		configuration.setAttribute(ENGINE_OPTIONS, '');
		configuration.setAttribute(SYSTEM_LOADER, '')
	}

	private def createMultiText(Composite parent) {
		return new Text(parent, MULTI.bitwiseOr(BORDER)) => [
			layoutData = GridDataFactory.swtDefaults.grab(true, true).align(FILL, FILL).create;
			addModifyListener[updateLaunchConfigurationDialog];
		];
	}

	private def createComboViewer(Composite parent) {
		return new ComboViewer(parent, BORDER.bitwiseOr(READ_ONLY)) => [
			control.layoutData = GridDataFactory.swtDefaults.grab(true, false).align(FILL, FILL).create;
			contentProvider = ArrayContentProvider.instance;
			addSelectionChangedListener([updateLaunchConfigurationDialog]);
		];
	}

	private def createGroupWithMultiText(Composite parent, String groupText) {
		val group = new Group(parent, NONE) => [
			text = groupText;
			layout = GridLayoutFactory.swtDefaults.create;
			layoutData = GridDataFactory.swtDefaults.grab(true, true).align(FILL, FILL).create;
		];
		return group.createMultiText;
	}

	private def createGroupWithComboViewer(Composite parent, String groupText) {
		val group = new Group(parent, NONE) => [
			text = groupText;
			layout = GridLayoutFactory.swtDefaults.create;
			layoutData = GridDataFactory.swtDefaults.grab(true, false).align(FILL, FILL).create;
		];
		return group.createComboViewer;
	}





}
