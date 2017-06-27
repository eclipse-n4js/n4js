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

import com.google.common.base.Charsets
import com.google.common.io.Files
import com.google.common.net.UrlEscapers
import com.google.inject.Inject
import org.eclipse.n4js.generator.common.AbstractSubGenerator
import org.eclipse.n4js.npmexporter.validation.IssueConsumer
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.projectModel.ProjectUtils
import org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator
import org.eclipse.n4js.utils.io.FileCopier
import org.eclipse.n4js.utils.io.FileDeleter
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.StandardOpenOption
import java.nio.file.attribute.BasicFileAttributes
import java.util.List
import java.util.Set
import org.apache.commons.compress.archivers.ArchiveException
import org.apache.commons.compress.archivers.ArchiveOutputStream
import org.apache.commons.compress.archivers.ArchiveStreamFactory
import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.compressors.CompressorException
import org.apache.commons.compress.compressors.CompressorStreamFactory
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static org.eclipse.n4js.npmexporter.validation.ExporterIssueCodes.*

/**
 */
class NpmExporter {

	private static final String PACKAGE_JSON = "package.json";
	private static final String NPM_PACKAGE_PATH_PREFIX = "package/";

	/** List of file names that are not exported to the npm package. */
	private static final Set<String> IGNORED_FILES = #[
		// for Eclipse:
		".project",
		// defined by npm / package.json standard (see https://docs.npmjs.com/files/package.json#files):
		".git",
    	"CVS",
    	".svn",
    	".hg",
    	".lock-wscript",
    	".wafpickle-N",
    	// "*.swp", // not supported because of wildcard
    	".DS_Store",
    	// "._*", // not supported because of wildcard
    	"npm-debug.log"
	].toSet;


	@Inject ProjectUtils projectUtils;
	@Inject EcmaScriptSubGenerator es5SubGen;
	@Inject PackageJsonTemplate packageJsonTemplate;


	/** Export mutliple projects. */
	def void export(List<? extends IN4JSProject> projects, File baseFolder) throws IOException {
		for ( project : projects) {
			export(project,baseFolder)
		}
	}

	/** Export a single project */
	def void export(IN4JSProject project, File baseFolder) throws IOException {
		val exportWarnings = <NpmExporter.ExporterMessage>newArrayList()

		val targetFolder = exportDestination(project,baseFolder);
		if(targetFolder.exists) {
			// clear it up front:
			FileDeleter.delete(targetFolder);
		}
		// create a folder
		targetFolder.mkdirs;

		// first copy entire project
		val Path project_loc = project.locationPath;
		FileCopier.copy(project_loc, targetFolder.toPath);

		// now delete output folder in targetFolder
		val File outputFolderInTarget = new File(targetFolder, project.outputPath);
		if(outputFolderInTarget.exists && outputFolderInTarget.isDirectory) {
			FileDeleter.delete(outputFolderInTarget);
		}

		// copy files from original output folder to targetFolder
//		val Path project_loc = project.locationPath;
		val String project_out = project.outputPath;
		// TODO improve generator-access, here ES-transpiler is directly referenced
		val String project_transpiledContent = AbstractSubGenerator.calculateOutputDirectory(project_out, es5SubGen.compilerID )
		val Path transpiledContentFolder = project_loc.resolve(project_transpiledContent);

		val transpiledContentFolderAsFile = transpiledContentFolder.toFile;
		// note: it might be valid that there is no output folder (e.g. runtime libraries containing only .n4jsd files)
		if(transpiledContentFolderAsFile.exists && transpiledContentFolderAsFile.isDirectory) {
			val filesToCopy = transpiledContentFolderAsFile.listFiles;
			for( file :filesToCopy ){
				FileCopier.copy( file.toPath , targetFolder.toPath);
			}
		}

		// generate package json
		val filePackageJson = new File( targetFolder, "package.json");

		val mergeRes = readExistingAndMerge(project);
		exportWarnings += mergeRes.messages;
		val packageJsonText_merged = mergeRes.getFinalPackageJson;

		// TODO Decide if to continue when seeing warnings in merger.
		for( warning : exportWarnings) {
			println( warning.type.toString +": "+warning.message )
		}

		Files.write( packageJsonText_merged , filePackageJson,  Charsets.UTF_8); // TODO line-ending ???

	}

	/** Location to export a single project to.*/
	def File exportDestination(IN4JSProject project, File baseFolder) throws IOException {
			return project.toProjectFolderIn(baseFolder);
	}


	/* @return an object describing the result with project, user-data, result and warning-list  */
	def MergeResult readExistingAndMerge(IN4JSProject project ) throws IOException {
		val List<ExporterMessage> exportWarnings = newArrayList();
		val filePackageJson_user = new File( project.locationPath.toFile, "package.json");

		val outputPathComplete = projectUtils.getOutputPathComplete(project, es5SubGen.compilerID);
		val data = ConvertManifestToPackageJson.convert(project, outputPathComplete);

		val packageJsonText = packageJsonTemplate.generateTemplate(data);
		// --> pick up existing package json
		val existingJsonText = if( filePackageJson_user.exists ) {
			Files.readLines(filePackageJson_user,Charsets.UTF_8).join("\n")
		} else {
			null
		};

		val packageJsonText_merged = if( existingJsonText === null) {
			packageJsonText
		} else {
			PackageJsonTemplate.merge( packageJsonText, existingJsonText,
				[ exportWarnings += new NpmExporter.ExporterMessage(MessageType.packageJsongMerger,it) ]
			)
		};

		return new MergeResult( project, existingJsonText , packageJsonText_merged, exportWarnings);
	}


	/** Creates project-corresponding folder inside of baseFolder */
	def static File toProjectFolderIn(IN4JSProject project, File baseFolder) {
		val pname = project.projectId;
		return new File( baseFolder,pname );
	}

	/** Export mutliple projects. */
	def void tarAndZip(List<? extends IN4JSProject> projects, File baseFolder) throws IOException , CompressorException, ArchiveException {
		for ( project : projects) {
			tarAndZip(project,baseFolder)
		}
	}

	/** Export a single project */
	def void tarAndZip(IN4JSProject project, File baseFolder) throws IOException , CompressorException , ArchiveException {
		val archiveFile = new File(baseFolder, project.projectId+".tgz");

		val tempFile = File.createTempFile("tmp",".tgz",baseFolder);
		tempFile.delete(); // make sure we just captured the name.

		val out = java.nio.file.Files.newOutputStream(tempFile.toPath,StandardOpenOption.CREATE_NEW);
		val bout = new BufferedOutputStream(out);
		val compressedOut = new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.GZIP, bout );
		val archiveOut = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.TAR, compressedOut );

		val projectFolder = project.toProjectFolderIn( baseFolder );
		val projectFolderPath = projectFolder.toPath;

		// package.json must be added first to the tar archive (npm expects this)
		val packageJsonFile = new File(projectFolder, PACKAGE_JSON);
		archiveOut.addArchiveEntry(packageJsonFile, PACKAGE_JSON);

		// recursively go over the files (except package.json)
		val visitor = new SimpleFileVisitor<Path> {
			override preVisitDirectory(Path path, BasicFileAttributes attrs)throws IOException {
				if( ! path.equals(projectFolderPath)) {
					val relPathStr = projectFolderPath.relativeLocation( path );
					val File file = path.toFile;
					// create an entry
					archiveOut.putArchiveEntry( new TarArchiveEntry(file, NPM_PACKAGE_PATH_PREFIX + relPathStr) );
					archiveOut.closeArchiveEntry( );
				}
				return FileVisitResult.CONTINUE;
			}

			override visitFile(Path path, BasicFileAttributes attrs) throws IOException {
				val File file = path.toFile;
				// make file's path & name relative to projectFolder
				val relPathName = projectFolderPath.relativeLocation( path );
				val toBeIncluded =
					!PACKAGE_JSON.equals(relPathName) // package.json in root folder already included above!
					&& !IGNORED_FILES.contains(file.name); // must not have an ignored file name
				if(toBeIncluded) {
					// create an entry
					archiveOut.addArchiveEntry(file, relPathName);
				}
				return FileVisitResult.CONTINUE;
			}
		};
		// do the walking.
		java.nio.file.Files.walkFileTree(projectFolder.toPath,visitor);

		// close streams
		archiveOut.close;

		println("Done creating "+tempFile+" will rename to "+archiveFile);

		// remove old version if found:
		if( archiveFile.exists ) archiveFile.delete;
		// rename to final filename
		tempFile.renameTo( archiveFile );

	}

	def private void addArchiveEntry(ArchiveOutputStream out, File file, String pathAndNameInArchive) throws IOException {
		if(pathAndNameInArchive.startsWith("/"))
			throw new IllegalArgumentException();

		// create an entry
		out.putArchiveEntry( new TarArchiveEntry(file, NPM_PACKAGE_PATH_PREFIX + pathAndNameInArchive) );

		// copy content
		var BufferedInputStream bin;
		try{

			val FileInputStream in = new FileInputStream(file);
			bin = new BufferedInputStream( in );
			val byte[] buf = newByteArrayOfSize(8192);

			var int read = 0;
			while ((read = bin.read(buf)) > 0) {
					out.write(buf, 0, read);
			}

		} finally {
			bin.close();
		}

		out.closeArchiveEntry( );
	}

	def static String relativeLocation(Path path, Path path2) {
//  	TODO sanitzize for windows ?
			 path.toUri.relativize( path2.toUri ).toString
	}


	/// *************************************** ///

	/** @return true if a user-defined file "package.json" exists */
	def boolean requiresJsonMerge(IN4JSProject project) {
		val Path project_loc = project.locationPath;
		val filePackageJson_user = new File( project_loc.toFile, "package.json");
		return filePackageJson_user.exists
	}

	/// *************************************** ///


	def  validate(IN4JSProject project, IssueConsumer issueSink ) {
		isExportable(project, issueSink )
	}

	/** quick-check if we can throw it out */
	def boolean isExportable(IN4JSProject project, IssueConsumer issueSink) {
		return holdsConsistentProjectId(project, issueSink)
			&& holdsNonClashingPackageJson(project, issueSink);
	}

	/** Ensures npm-module-naming conventions. */
	def boolean holdsNonClashingPackageJson(IN4JSProject project, IssueConsumer issueSink) {
		// TODO
		return true;
	}

	/** Ensures npm-module-naming conventions. */
	def boolean holdsConsistentProjectId(IN4JSProject project,  IssueConsumer issueSink ) {
		return holdsConsistentProjectId( project.projectId, issueSink );
	}

	/** Ensures npm-module-naming conventions. */
	def boolean holdsConsistentProjectId(String projectId, IssueConsumer issueSink) {
		/*-
			Some rules:

			§1 The name must be less than or equal to 214 characters. This includes the scope for scoped packages.
			§2 The name can't start with a dot or an underscore.
			§3 New packages must not have uppercase letters in the name.
			§4 The name ends up being part of a URL, an argument on the command line, and a folder name.
				 Therefore, the name can't contain any non-URL-safe characters.
		*/

		// check §1
		if( projectId.length > 214 ){
			// name is to long, will be rejected by npm
			val msg = messageForNPM_PROJECT_NAME_EXCEEDS_CHAR_COUNT;
			issueSink.accept(NPM_PROJECT_NAME_EXCEEDS_CHAR_COUNT, msg);
			return false;
		}

		// check §2
		if( projectId.startsWith(".") || projectId.startsWith("_") ) {
			// illegal first character.
			val msg = getMessageForNPM_PROJECT_NAME_MUST_NOT_START_WITH_DASH_OR_DOT(projectId);
			issueSink.accept(NPM_PROJECT_NAME_MUST_NOT_START_WITH_DASH_OR_DOT,msg);
			return false;
		}

		// check §3
		if( projectId.toLowerCase != projectId ) {
			// contains upper case letters which will be rejected on npm-publish.
			val msg = getMessageForNPM_PROJECT_NAME_MUST_NOT_CONTAIN_UPPER_CASE_LETTERS(projectId);
			issueSink.accept(NPM_PROJECT_NAME_MUST_NOT_CONTAIN_UPPER_CASE_LETTERS,msg);
			return false;
		}

		// check §4
		val urlEscapedProjectId = UrlEscapers.urlFormParameterEscaper.escape(projectId);
		if( urlEscapedProjectId != projectId ) {
			// contains invalid characters.
			val msg = getMessageForNPM_PROJECT_NAME_MUST_NOT_CONTAIN_URL_EXOTIC_CHARACTERS(projectId);
			issueSink.accept(NPM_PROJECT_NAME_MUST_NOT_CONTAIN_URL_EXOTIC_CHARACTERS,msg);
			return false;
		}

		return true;
	}


	// Data class collecting user-feedback.
	@Data @FinalFieldsConstructor
	static class ExporterMessage {
		val MessageType type
		val String message
	}

	/** categorizing expoter messages. */
	static enum MessageType{
		packageJsongMerger
	}

	/** Data class after merging package-json. */
	@Data @FinalFieldsConstructor
	static class MergeResult {
		val IN4JSProject project;
		val String userPackagaJson;
		val String finalPackageJson;
		val List<ExporterMessage> messages;
	}

}
