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

import java.util.Collections
import java.util.LinkedHashMap
import java.util.Map
import org.eclipse.core.runtime.CoreException
import org.eclipse.debug.core.ILaunchConfiguration
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab
import org.eclipse.jface.layout.GridDataFactory
import org.eclipse.jface.layout.GridLayoutFactory
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Group
import org.eclipse.swt.widgets.Text

import static com.google.common.base.Strings.nullToEmpty
import static org.eclipse.n4js.runner.RunConfiguration.CUSTOM_ENGINE_PATH
import static org.eclipse.n4js.runner.RunConfiguration.ENGINE_OPTIONS
import static org.eclipse.n4js.runner.RunConfiguration.ENV_VARS
import static org.eclipse.swt.SWT.*

/**
 * Launch configuration tab for configuring Node.js NODE_PATH and other options.
 */
class NodejsLaunchConfigurationTab extends AbstractLaunchConfigurationTab {

	/** Text field for storing any arbitrary options for execution engine. */
	var Text optionsText;

	/**
	 * Text field storing env. variables (VAR=...)
	 */
	var Text environmentVariablesText;

	/** Text field for storing any arbitrary custom path settings. For instance for node it store NODE_PATH values. */
	var Text customPathText;

	/**
	 * Converts a map to text, each entry is put on a line, key and value are separated by "=".
	 * For convenience reasons, the map is sorted by key.
	 * @param map may be empty or even null.
	 */
	static def String mapToString(Map<String, String> map) {
		if (map===null) return "";
		map.entrySet.sortWith[o1,o2|return o1.key.compareTo(o2.key)].join("\n", [it.key+"="+it.value]);
	}

	/**
	 * Converts text to map, each entry is assumed on a line (separated by \n), key and value are
	 * separated by "=". Empty lines are ignored.
	 */
	static def Map<String, String> stringToMap(String text) {
		val Map<String, String> map = new LinkedHashMap();
		if (text !== null) {
			text.split("\n").forEach [
				val line = it.trim();
				if (! line.isEmpty) {
					val String[] keyVal = line.split("=");
					if (keyVal.length != 2) {
						throw new IllegalArgumentException(
							"Env. vars are expected to be saved in lines with key=val; found: " + line);
					}
					map.put(keyVal.get(0).trim(), keyVal.get(1).trim())
				}
			]
		}
		return map;
	}

	override createControl(Composite parent) {
		val childControl = new Composite(parent, NONE) => [
			layout = GridLayoutFactory.swtDefaults.create;
			layoutData = GridDataFactory.swtDefaults.grab(true, true).align(FILL, FILL).create;
		];
		customPathText = childControl.createGroupWithMultiText('NODE_PATH');
		optionsText = childControl.createGroupWithMultiText('Node.js options');
		environmentVariablesText = childControl.createGroupWithMultiText('Environment Variables (VAR=...)');
		control = childControl;
	}

	override getName() {
		'Node.js settings';
	}

	override initializeFrom(ILaunchConfiguration configuration) {
		try {
			optionsText.text = configuration.getAttribute(ENGINE_OPTIONS, '');
			environmentVariablesText.text = mapToString(configuration.getAttribute(ENV_VARS, Collections.emptyMap()));
			customPathText.text = configuration.getAttribute(CUSTOM_ENGINE_PATH, '');
		} catch (CoreException e) {
			errorMessage = e.message;
		}
	}

	override performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(ENGINE_OPTIONS, nullToEmpty(optionsText.text));
		configuration.setAttribute(ENV_VARS, stringToMap(environmentVariablesText.text));
		configuration.setAttribute(CUSTOM_ENGINE_PATH, nullToEmpty(customPathText.text));
	}

	override setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(CUSTOM_ENGINE_PATH, '');
		configuration.setAttribute(ENGINE_OPTIONS, '');
	}

	private def createMultiText(Composite parent) {
		return new Text(parent, MULTI.bitwiseOr(BORDER)) => [
			layoutData = GridDataFactory.swtDefaults.grab(true, true).align(FILL, FILL).create;
			addModifyListener[updateLaunchConfigurationDialog];
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
}
