/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.organize.imports

import org.eclipse.n4js.ui.organize.imports.BreakException.UserCanceledBreakException
import org.eclipse.n4js.utils.collections.Multimaps3
import com.google.common.collect.Multimap
import java.util.List
import org.eclipse.jface.window.Window
import org.eclipse.jface.viewers.ILabelProvider

/**
 * Utility for handling disambiguation of the imports.
 */
class DisambiguateUtil {

	/**
	 * Depending on interaction-mode:
	 * Present a user dialog and let the user choose a distinct import for each unresolved problem.
	 * 
	 * For each key in {@code multiMap} exactly one solution is returned.
	 * 
	 */
	static def <T> List<T> disambiguate(Multimap<String, T> multiMapName2Candidates, Interaction interaction,
		ILabelProvider importProvidedElementLabelprovider) throws BreakException {
		
		if (multiMapName2Candidates.empty) 
			return <T>newArrayList();

		// for each name exactly one solution must be picked: 
		switch (interaction) {
			case breakBuild:
				throw new BreakException("Cannot automatically disambiguate the imports of " +
					multiMapName2Candidates.keySet.toList)
			case takeFirst:
				return takefirst(multiMapName2Candidates)
			case queryUser: {
				return askUser(multiMapName2Candidates, importProvidedElementLabelprovider)
			}
			default:
				throw new RuntimeException("Unsupported interaction " + interaction)
		}
	}
	
	/** Takes first result available.  */
	private static def <T> List<T> askUser(Multimap<String, T> multimap, ILabelProvider importProvidedElementLabelprovider) throws UserCanceledBreakException {
		val result = <T>newArrayList();

		val Object[][] openChoices = Multimaps3.createOptions(multimap);
		val MultiElementListSelectionDialog dialog = new MultiElementListSelectionDialog(null,
			importProvidedElementLabelprovider);

		dialog.setTitle("Organize Imports");
		dialog.setMessage("Choose type to import:");
		dialog.setElements(openChoices);

		if (dialog.open() == Window.OK) {
			val Object[] res = dialog.getResult();

			for (var int i = 0; i < res.length; i++) {
				val Object[] array = res.get(i) as Object[];
				if (array.length > 0)
					result.add(array.get(0) as T)
			}
		} else 
			throw new UserCanceledBreakException("User canceled.");

		return result;
	}

	/** Takes first result available.  */
	private static def <T> List<T> takefirst(Multimap<String, T> multimap) {
		val result = <T>newArrayList();

		for (name : multimap.keySet) {
			result += multimap.get(name).head
		}

		return result;
	}
}
