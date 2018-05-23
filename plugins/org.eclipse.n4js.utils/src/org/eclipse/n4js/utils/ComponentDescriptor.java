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
package org.eclipse.n4js.utils;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.MapDifference.ValueDifference;

/**
 * Base class for configurable components such as compilers or engines. Used in preferences. Value object to store the
 * components configuration. It also holds the changes to the changes to the before store components configuration, so
 * this information can be used to decide whether e.g. a rebuild is required.
 */
public abstract class ComponentDescriptor {

	private String identifier;
	private String name;
	private String description;
	private Map<String, ValueDifference<String>> changes = new HashMap<>();
	private ComponentDescriptor currentlyStoredComponentDescriptor;

	/**
	 * @return the unique identifier of this component, it must be the same name value as used for
	 *         getOutputConfiguration#name
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the unique identifier of this component, it must be the same name value as used for
	 *            getOutputConfiguration#name
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the name to show e.g. in the component configuration preference page
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to show e.g. in the component configuration preference page
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return an optional detailed description of the purpose / functionality of this component.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            an optional detailed description of the purpose / functionality of this component.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return a map keyed by the preference store key (e.g. outlet.es5.autobuilding for component (resp. output
	 *         configuration) with name 'es5' and property 'autobuilding') containing old and new value. Only keys whose
	 *         values has been changed are included.
	 */
	public Map<String, ValueDifference<String>> getChanges() {
		return changes;
	}

	/**
	 * @param changes
	 *            a map keyed by the preference store key (e.g. outlet.es5.autobuilding for component (resp. output
	 *            configuration) with name 'es5' and property 'autobuilding') containing old and new value. Only keys
	 *            whose values has been changed are included.
	 */
	public void setChanges(Map<String, ValueDifference<String>> changes) {
		this.changes = changes;
	}

	/**
	 * @return the component descriptor before the changes in the preference page
	 */
	public ComponentDescriptor getCurrentlyStoredComponentDescriptor() {
		return currentlyStoredComponentDescriptor;
	}

	/**
	 * @param currentlyStoredComponentDescriptor
	 *            set the currently configured component descriptor
	 */
	public void setCurrentlyStoredComponentDescriptor(
			ComponentDescriptor currentlyStoredComponentDescriptor) {
		if (currentlyStoredComponentDescriptor != null) {
			// avoid deep nesting
			currentlyStoredComponentDescriptor.setCurrentlyStoredComponentDescriptor(null);
		}
		this.currentlyStoredComponentDescriptor = currentlyStoredComponentDescriptor;
	}

	/**
	 * Returns a copy of the given descriptor that has no reference to the original object
	 */

	public abstract ComponentDescriptor copy();

	/**
	 * Creates a map of preference store key to value out of the component descriptor. This is usually done via the
	 * corresponding properties enum.
	 *
	 * @param outputName
	 *            the output name to for the output configuration to create if the passed component descriptor hasn't a
	 *            output configuration
	 * @return the map preference store key to value
	 */
	public abstract Map<String, String> fillMap(String outputName);

}
