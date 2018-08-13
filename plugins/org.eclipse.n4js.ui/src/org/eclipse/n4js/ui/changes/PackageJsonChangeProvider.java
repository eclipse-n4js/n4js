/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.changes;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.packagejson.PackageJsonUtils;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

/**
 *
 */
public class PackageJsonChangeProvider {

	/**
	 * Returns change instance to set the ProjectType to the given value.
	 *
	 * @param pckJsonResource
	 *            The package.json resource
	 * @param projectType
	 *            The project type to set
	 * @param projectDescription
	 *            The project description object of the package.json
	 */
	public static IAtomicChange setProjectType(Resource pckJsonResource, ProjectType projectType,
			ProjectDescription projectDescription) {

		URI uri = pckJsonResource.getURI();
		String newProjectTypeInQuotations = "\"" + projectType.getName().toLowerCase() + "\"";

		List<JSONStringLiteral> pairs = PackageJsonUtils.findNameValuePairs(pckJsonResource,
				PackageJsonProperties.PROJECT_TYPE, JSONStringLiteral.class);

		INode prjTypeNode = null;
		if (!pairs.isEmpty()) {
			JSONStringLiteral jsonStringLiteral = pairs.get(0);
			prjTypeNode = NodeModelUtils.findActualNodeFor(jsonStringLiteral);
		}

		if (prjTypeNode == null) {
			// Append a new entry
			List<JSONObject> n4jss = PackageJsonUtils.findNameValuePairs(pckJsonResource, PackageJsonProperties.N4JS,
					JSONObject.class);

			if (!n4jss.isEmpty()) {
				prjTypeNode = NodeModelUtils.findActualNodeFor(n4jss.get(0));
				int location = (prjTypeNode == null) ? 0 : prjTypeNode.getEndOffset();

				String name = PackageJsonProperties.PROJECT_TYPE.name;
				String nameValuePair = "\n" + name + ": " + newProjectTypeInQuotations + ((location == 0) ? "\n" : "");
				return new Replacement(uri, location, 0, nameValuePair);
			}
		} else {
			// Replace existing entry
			return new Replacement(uri, prjTypeNode.getOffset(), prjTypeNode.getLength(), newProjectTypeInQuotations);
		}

		return new Replacement(uri, 0, 0, "");
	}
}
