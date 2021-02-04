/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt;

/**
 *
 */
public class XtSetupWorkspace {

	static public class NamedEntity {
		final public String name;

		public NamedEntity(String name) {
			this.name = name;
		}
	}

	static public class Project extends NamedEntity {
		final public boolean isYarn;

		public Project(String name, boolean isYarn) {
			super(name);
			this.isYarn = isYarn;
		}
	}

	static public class Folder extends NamedEntity {
		public Folder(String name) {
			super(name);
		}
	}

	static public class File extends NamedEntity {
		final public boolean isThisFile;
		final public String from;

		public File(String name, boolean isThisFile, String from) {
			super(name);
			this.isThisFile = isThisFile;
			this.from = from;
		}
	}

}
