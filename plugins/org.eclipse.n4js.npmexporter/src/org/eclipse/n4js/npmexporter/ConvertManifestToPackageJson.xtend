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
package org.eclipse.n4js.npmexporter

import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.runner.^extension.RuntimeEnvironment
import java.nio.file.Path

import static extension org.eclipse.n4js.npmexporter.NpmExporterUtil.*

/**
 * Converting all relevant Manifest-data into an DataObject.
 */
class ConvertManifestToPackageJson {


	static  def  PackageJsonData convert(IN4JSProject project, Path outputPathComplete)
	{
		 val data = new PackageJsonData();
//	TODO	val pDescription = project.projectName  /
		data.name = project.projectId;
		// TODO data.author = // map to project.vendorId ??
		data.version = project.versionAsSemverString;
		val depIn4jsprojects = project.getDependencies; // TODO includes requiered RT-Libs, OK?

		data.dependencies = newLinkedHashMap();
		data.devDependencies = newLinkedHashMap();

		for(depX : depIn4jsprojects) {
			(if(depX.projectType === ProjectType.RUNTIME_LIBRARY) {
				data.devDependencies
			} else {
				data.dependencies
			}).put( depX.projectId, "*" );
		}

		// add tested projects to dependencies
		for(tp : project.testedProjects) {
			data.dependencies.put( tp.projectId, "*" );
		}

		// add extended runtime environment to dependencies
		val extRE = project.extendedRuntimeEnvironment.orNull;
		if(extRE!==null) {
			// note: inconsistency in return type between getExtendedRuntimeEnvironment() and getDependencies()
			if(extRE instanceof IN4JSProject) {
				data.dependencies.put(extRE.projectId, "*");
			} else {
				throw new IllegalStateException("expected an IN4JSProject but got: " + extRE.class.name);
			}
		}

		// add N4JS node runtime environment to dependencies
		// (note: this is not defined in the manifest; we assume (for now) that all npm packages are intended to
		// be run on node.js)
		if(project.projectType!==ProjectType.RUNTIME_ENVIRONMENT && project.projectType!==ProjectType.RUNTIME_LIBRARY) {
			data.dependencies.put(RuntimeEnvironment.NODEJS.projectId, "*");
		}

		val outputPathCompleteAsFile = outputPathComplete.toFile;
		// note: it might be valid that there is no output folder (e.g. runtime libraries containing only .n4jsd files)
		if(outputPathCompleteAsFile.exists && outputPathCompleteAsFile.isDirectory) {
			data.n4js_outputFolderContents = outputPathCompleteAsFile.listFiles.map[name];
		}

		if(data.dependencies.isEmpty) {
			data.dependencies = null;
		}
		if(data.devDependencies.isEmpty) {
			data.devDependencies = null;
		}

		return data;
	}
}
