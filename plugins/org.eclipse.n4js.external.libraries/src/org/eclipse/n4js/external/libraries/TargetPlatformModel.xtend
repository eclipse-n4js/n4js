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
package org.eclipse.n4js.external.libraries

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Preconditions
import org.eclipse.n4js.utils.JsonPrettyPrinterFactory
import java.io.File
import java.net.URI
import java.util.Collection
import java.util.HashMap
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.stream.Collectors
import java.io.IOException
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import java.util.Set
import java.util.Map

/**
 * POJO for representing an N4JS target platform file.
 *
 * <pre>
 *{
 *  "location" : [
 *    {
 *      "config" : {
 *        "anotherConfigurationKey" : "value",
 *        "someConfigurationKey" : 36
 *      },
 *      "repoType" : "npm",
 *      "projects" : {
 *        "project.without.version.id" : {
 *        },
 *        "another.prject.id" : {
 *          "version" : "1.2.3"
 *        },
 *        "some.project.id" : {
 *          "version" : "0.0.1"
 *        }
 *      }
 *    }
 *  ]
 *}
 * </pre>
 */
@Accessors
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
class TargetPlatformModel {

	/** The file extension (without the '.') of the N4JS target platform file */
	public static val TP_EXTENSION = 'n4tp';

	/** The file extension file. Starts with star (*), followed by dot (.) finally the actual extension. */
	public static val TP_FILTER_EXTENSION = '''*.«TP_EXTENSION»''';

	/** The unique file name of the target platform file. (targetplatform.n4tp)*/
	public static val TP_FILE_NAME = '''targetplatform.«TP_EXTENSION»'''

	/**
	 * Creates a new target platform model instance with {@link RepositoryType#npm npm} repository type
	 * and with projects according to the set of project identifiers argument.
	 */
	static def createFromNpmProjectIds(Set<String> projectIds) {
		return new TargetPlatformModel => [ projectIds.forEach[id | addNpmDependency(id)] ];
	}

	/**
	 * Creates a new target platform model instance with {@link RepositoryType#npm npm} repository type
	 * and with projects according to the mapping of project identifiers and their version argument.
	 */
	static def createFromVersionedNpmProjectIds(Map<String, String> projectIdsWithVersions) {
		return new TargetPlatformModel => [ projectIdsWithVersions.forEach[id,version | addNpmDependency(id, version)] ];
	}


	var Collection<Location> location;

	@Accessors
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	static class Location {
		var Config config;
		var RepositoryType repoType;
		var Projects projects;
	}

	@Accessors
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	static class Config extends HashMap<String, Object> {

		new() {
			super(16, 0.75F)
		}

	}

	@Accessors
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	static class Projects extends HashMap<String, ProjectProperties> {

		new() {
			super(16, 0.75F)
		}

	}

	@Accessors
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	static class ProjectProperties {
		var String version;
	}

	/**
	 * Adds a project with the given project ID as a new npm dependency without version specifier.
	 * Initializes the model if not initialized yet.
	 */
	@JsonIgnore
	def addNpmDependency(String projectId) {
		Preconditions.checkNotNull(projectId, 'projectId');
		addNpmDependency(projectId, null as String);
	}

	/**
	 * Adds a project with the given project ID as a new npm dependency with the given version specifier.
	 * If the version is {@code null}, then no version specifier will be used. Initializes the model if
	 * not initialized yet.
	 */
	@JsonIgnore
	def addNpmDependency(String projectId, String version) {
		Preconditions.checkNotNull(projectId, 'projectId');
		val properties = new ProjectProperties();
		properties.version = version;
		addNpmDependency(projectId, properties);
	}

	/**
	 * Adds a project with the given project ID as a new npm dependency with the given project properties.
	 * If the version is not available in the project properties, or the properties argument is{@code null},
	 * then no version specifier will be used. Initializes the model if not initialized yet.
	 */
	@JsonIgnore
	def addNpmDependency(String projectId, ProjectProperties properties) {
		Preconditions.checkNotNull(projectId, 'projectId');
		if (null === location) {
			location = newArrayList();
		}
		if (!location.exists[repoType === RepositoryType.npm]) {
			location += new Location => [
				config = new Config;
				repoType = RepositoryType.npm;
				projects = new Projects;
			];
		}
		val loc = location.findFirst[repoType === RepositoryType.npm];
		val proj = location.findFirst[repoType === RepositoryType.npm].projects;
		if (null === proj) {
			loc.projects = new Projects;
		}

		loc.projects.put(projectId, properties);
	}
	@Override
	override String toString() {
		try {
			val mapper  = new ObjectMapper(new JsonPrettyPrinterFactory());
			return mapper.writeValueAsString(this);
		} catch (Exception e) {
			throw new RuntimeException('''Error while serializing target platform file: «this»''', e);
		}
	}

	/**
	 * Reads and parses the target platform file content and returns with an assembled
	 * instance representing the content of the file.
	 *
	 * @param location the location of the target platform file.
	 *
	 * @return a new instance representing the content of the parsed target platform file.
	 */
	static def TargetPlatformModel readValue(URI location) {
		try {
			throwingReadValue(location)
		} catch (Exception e) {
			throw new RuntimeException('''Error while reading target platform content from «location».''', e);
		}
	}

	static enum RepositoryType {
		npm
	}

	private static def throwingReadValue(URI location)throws IOException, JsonParseException, JsonMappingException {
		val mapper  = new ObjectMapper(new JsonPrettyPrinterFactory());
		return mapper.readValue(new File(location), TargetPlatformModel);
	}

	/**
	 * Convenience api, reads model specified by the provided location and returns collection
	 * of npm package names in that file. Caller needs to validate if provided source is valid
	 * (e.g. file exists, file has proper contents, etc.).
	 *
	 * @param location the location of the target platform file.
	 *
	 * @return collection of npm package names in the provided file.
	 *
	 * @throws IOException in case of errors reading value.
	 */
	static def Collection<String> npmPackageNamesFrom(URI platformFileLocation) throws IOException, JsonParseException, JsonMappingException {
			return TargetPlatformModel
					.throwingReadValue(platformFileLocation)
					.getLocation()
					.stream()
					.filter(l | l.getRepoType().equals(TargetPlatformModel.RepositoryType.npm))
					.map(l | l.getProjects())
					.flatMap(p | p.entrySet().stream())// TODO refactor TargetPlatformModel, e.g.
														// class Projects extends HashMap<String,
														// ProjectProperties>) while it is more like
														// class Projects extends Pair<String,
														// ProjectProperties>, ideally class with
														// fields [String name, ProjectProperties properties]
					.map(e | e.getKey()).collect(Collectors.toSet());
		}

	/**
	 * Convenience api, reads model specified by the provided location and returns collection
	 * of npm package names in that file. Caller needs to validate if provided source is valid
	 * (e.g. file exists, file has proper contents, etc.).
	 *
	 * @param location the location of the target platform file.
	 *
	 * @return collection of npm package names in the provided file.
	 *
	 * @throws IOException in case of errors reading value.
	 */
	static def Map<String,String> npmVersionedPackageNamesFrom(URI platformFileLocation) throws IOException, JsonParseException, JsonMappingException {
			return TargetPlatformModel
					.throwingReadValue(platformFileLocation)
					.getLocation()
					.stream()
					.filter(l | l.getRepoType().equals(TargetPlatformModel.RepositoryType.npm))
					.map(l | l.getProjects())
					.flatMap(p | p.entrySet().stream())// TODO refactor TargetPlatformModel, e.g.
														// class Projects extends HashMap<String,
														// ProjectProperties>) while it is more like
														// class Projects extends Pair<String,
														// ProjectProperties>, ideally class with
														// fields [String name, ProjectProperties properties]
//					.map(e | e.getKey()->e.value.getVersion?:"").collect(Collectors.toMap([p | p.key],[e | e.value.version?:""]));
					.collect(Collectors.toMap([e | e.key],[e | e.value.version?:""]));
		}
}
