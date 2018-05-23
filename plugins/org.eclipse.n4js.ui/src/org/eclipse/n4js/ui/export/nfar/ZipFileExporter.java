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
package org.eclipse.n4js.ui.export.nfar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Creates new ZipFile and populates with given Files/Directories
 */
public class ZipFileExporter {
	private final ZipOutputStream outputStream;

	/**
	 * Creates new ZipOutputStream from archiveFile FileOutputStream
	 */
	public ZipFileExporter(File archiveFile) throws IOException {
		outputStream = new ZipOutputStream(new FileOutputStream(archiveFile));
	}

	/**
	 * Do all required cleanup now that we're finished with the currently-open .zip
	 */
	public void finished() throws IOException {
		outputStream.close();
	}

	/**
	 * Create a new entry in the zip file with the given content.
	 */
	public void writeFile(InputStream contentStream, long timeStamp, String destinationPath) throws IOException {
		ZipEntry newEntry = new ZipEntry(destinationPath);
		byte[] readBuffer = new byte[4096];

		newEntry.setTime(timeStamp);

		outputStream.putNextEntry(newEntry);
		try {
			int n;
			while ((n = contentStream.read(readBuffer)) > 0) {
				outputStream.write(readBuffer, 0, n);
			}
		} finally {
			if (contentStream != null) {
				contentStream.close();
			}
		}
		outputStream.closeEntry();
	}

}
