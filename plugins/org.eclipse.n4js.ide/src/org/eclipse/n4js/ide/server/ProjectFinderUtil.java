package org.eclipse.n4js.ide.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;

public class ProjectFinderUtil {

	static public Collection<URI> collectAllProjectDirs(URI baseDir) {
		final Collection<URI> projectDirs = new LinkedList<>();
		Path start = Paths.get(baseDir.toFileString());

		try {
			Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
				int nodeModuleFolderCounter = 0;

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					if (dir.endsWith(N4JSGlobals.NODE_MODULES)) {
						if (nodeModuleFolderCounter > 0) {
							return FileVisitResult.SKIP_SUBTREE;
						}
						nodeModuleFolderCounter++;
					}

					return super.preVisitDirectory(dir, attrs);
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
					if (dir.endsWith(N4JSGlobals.NODE_MODULES)) {
						nodeModuleFolderCounter--;
					}

					Path pckJson = dir.resolve(N4JSGlobals.PACKAGE_JSON);
					if (pckJson.toFile().isFile()) {
						projectDirs.add(toFileUri(dir.toUri()));
						System.out.println(" + " + dir.toString());
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return projectDirs;
	}

	/** copied from URIUtils#toFileUri(java.net.URI) */
	static public org.eclipse.emf.common.util.URI toFileUri(java.net.URI jnUri) {
		if (jnUri == null) {
			return null;
		}

		File file = new File(jnUri);
		String path = file.getAbsolutePath();
		URI uri = URI.createFileURI(path);
		uri = addEmptyAuthority(uri);

		return uri;
	}

	/** Adds empty authority to the given URI. Necessary for windows platform. */
	static public URI addEmptyAuthority(URI uri) {
		uri = URI.createHierarchicalURI(uri.scheme(), "", uri.device(), uri.segments(), uri.query(), uri.fragment());
		return uri;
	}
}
