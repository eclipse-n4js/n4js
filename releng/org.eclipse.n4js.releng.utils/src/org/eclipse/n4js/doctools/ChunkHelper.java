package org.eclipse.n4js.doctools;

/**
 * Helper methods for {@link Chunker} and {@link EclipseHelpTOCGenerator} generator.
 *
 */
public final class ChunkHelper {

	/**
	 * Returns a file name for the given chapter. The file name is extracted from the content (i.e. CData) between start
	 * and end. Nested tags are recognized and only their content is taken into account. Numbers at the beginning are
	 * ignored as well. Other characters (spaces, special characters) between letters are converted to underscore.
	 */
	public static String extractFileNameFromTitle(String source, int startOfName, int endOfName) {
		// skip nested tags and heading number
		boolean separatorRequired = false;
		boolean inHTML = false;
		StringBuilder strb = new StringBuilder();
		for (int i = startOfName; i < endOfName; i++) {
			char c = source.charAt(i);
			if (inHTML) {
				if (c == '>') {
					inHTML = false;
				}
			} else {
				if (c == '<') {
					inHTML = true;
				}
				if (strb.length() != 0 || Character.isLetter(c)) {
					if (Character.isLetterOrDigit(c)) {
						if (separatorRequired) {
							strb.append('_');
							separatorRequired = false;
						}
						strb.append(Character.toLowerCase(c));
					} else {
						separatorRequired = true;
					}
				}
			}
		}
		return strb.toString();
	}
}
