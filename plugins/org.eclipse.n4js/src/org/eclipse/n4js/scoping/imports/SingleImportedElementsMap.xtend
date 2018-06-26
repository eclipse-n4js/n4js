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
package org.eclipse.n4js.scoping.imports

import java.util.HashMap
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription

/** 
 * HashMap-based implementation of {@link ImportedElementsMap}.
 * 
 * This map can only hold a single {@link IEObjectDescription} per qualified name.  
 */
public class SingleImportedElementsMap implements ImportedElementsMap {
	private HashMap<QualifiedName, IEObjectDescription> elementsMap;
	
	new() {
		this.elementsMap = newHashMap(); 
	}
	
	override containsElement(QualifiedName name) {
		return this.elementsMap.containsKey(name);
	}
	
	override getElements(QualifiedName name) {
		val result = this.elementsMap.get(name);
		if (null !== result) {
			return #[result];	
		} else {
			return emptyList;
		}
	}
	
	override put(QualifiedName name, IEObjectDescription element) {
		this.elementsMap.put(name, element);
	}
	
	override values() {
		this.elementsMap.values;
	}
	
}
