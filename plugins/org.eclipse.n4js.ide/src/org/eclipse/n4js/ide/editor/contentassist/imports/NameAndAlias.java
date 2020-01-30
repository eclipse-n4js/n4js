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
package org.eclipse.n4js.ide.editor.contentassist.imports;

import org.eclipse.xtext.naming.QualifiedName;

/**
 */
class NameAndAlias {
	QualifiedName name;
	String alias;

	/** Constructor */
	public NameAndAlias(QualifiedName qualifiedName, String alias) {
		this.name = qualifiedName;
		this.alias = alias;
	}

	public QualifiedName getName() {
		return name;
	}

	public void setName(QualifiedName name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
