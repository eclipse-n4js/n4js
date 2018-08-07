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
package org.eclipse.n4js.ui.quickfix

import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.validation.Issue

/**
 *
 * Utility methods for QuickFixes
 */
public class QuickfixUtil {

	/**
	 * Returns the eobject at the given uri.
	 * @param set
	 * 		Resource set containing the demanded resource
	 * @param uri
	 * 		String uri of the demanded object (partly resource uri partly object uri)
	 *
	 *
	 */
	def static EObject getEObjectForUri(ResourceSet set, String uri) {

		// '#' is the delimiter between the file system uri and the inner file uri
		val fileFragments = uri.split("#");

		if ( fileFragments.length < 2 ) {
			return null;
		}

		val fileURI = fileFragments.get(0)
		val objectURI = fileFragments.get(1)

		val resource = set.getResource(URI.createURI(fileURI),true);

		if ( resource !== null ) {
			val object = resource.getEObject(objectURI);
			return object;
		}

		return null
	}

	 /**

	 * True if the objectUri containing resource is modifiable (i.e. file system write access)
	 *
	 * @param objectUri
	 * 		URI referring to eobject in resource
	 */
	def static boolean isContainingResourceModifiable(String objectUri) {
		var uri = URI.createURI(objectUri, true);
		if (uri === null) {
			return false;
		}

		// File system permission check
		var fileHandle = WorkspaceFileHandle.fileContainingObjectURI(uri);

		if (fileHandle === null) {
			return false;
		}
		if (fileHandle.isDerived) {
			return false;
		}
		if (fileHandle.readOnly) {
			return false;
		}

		return true;
	}
	/**
	 * Helper class to check file system access
	 */
	static class WorkspaceFileHandle {

		private var IPath path
		private var IFile file

		static def WorkspaceFileHandle fileContainingObjectURI(URI uri) {
			if (uri === null) {
				return null
			}
			if (uri.toString.length < 1) {
				return null
			}

			// Get rid of protocol prefix
			var fileURI = uri.deresolve(URI.createURI("platform:/resource/"))

			// Get rid of eobject uri suffix
			var fileFragments = fileURI.toString.split("#")

			return new WorkspaceFileHandle(new Path(fileFragments.get(0)));
		}

		new(IPath path) {
			this.path = path;
			this.file = ResourcesPlugin.workspace.root.getFile(this.path);
		}

		def getReadOnly() {
			return file.readOnly;
		}

		def getIsDerived() {
			return file.isDerived;
		}
	}

	/**
	  * Returns the N4Modifier for a given suggestion
	  *
	  * @param suggestion
	  * 	String encoded access modifier suggestion
	  */
	public static def modifierForSuggestion( String suggestion ) {
		switch( suggestion.toUpperCase ) {
			case "PUBLIC" : {
				return N4Modifier.PUBLIC
			}
			case "PROTECTED": {
				return N4Modifier.PROTECTED
			}
			case "PUBLICINTERNAL": {
				return N4Modifier.PUBLIC
			}
			case "PROTECTEDINTERNAL": {
				return N4Modifier.PROTECTED
			}
			case "PROJECT": {
				return N4Modifier.PROJECT
			}
			default: {
				return N4Modifier.UNDEFINED;
			}
		}
	}

	/**
	  * Returns if the suggestions asks for @Internal
	  *
	  * @param suggestion
	  * 	String encoded access modifier suggestion
	  */
	 public static def modifierSuggestionIsInternal( String suggestion ) {
	 	if ( suggestion.toUpperCase == "PUBLICINTERNAL" || suggestion.toUpperCase ==  "PROTECTEDINTERNAL" ) {
	 		return true;
	 	}
	 	false;
	 }

	 /**
	  * Returns a human readable string describing the suggested access modifier
	  *
	  * @param suggestion
	  * 	String encoded access modifier suggestion
	  */
	 public static def readableStringForSuggestion( String suggestion ) {
	 	val modifier = modifierForSuggestion(suggestion);
	 	val isInternal = modifierSuggestionIsInternal(suggestion);

	 	var output = "";

	 	switch ( modifier ) {
	 		case N4Modifier.PRIVATE:
	 			output += "private"
			case N4Modifier.PROJECT:
				output += "project"
			case N4Modifier.PROTECTED:
				output += "protected"
			case N4Modifier.PUBLIC:
				output += "public"
			default:
				output += "undefined"
	 	}
	 	if ( isInternal ) {
	 		output += ", @Internal";
	 	}
	 	return output;
	 }


	public static class IssueUserDataKeysExtension {
		def getUserData(Issue issue, String key) {
			val userData = issue.getData();
			if (null === userData) {
				return null;
			}
			val userDataLength = userData.length;
			for (var i = 0; i < userDataLength; i += 2) {
				if (userData.get(i).equals(key) && (i + 1) < userDataLength) {
					return userData.get(i + 1);
				}
			}
			return null;
		}
	}
}
