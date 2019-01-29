package org.eclipse.n4js.doctools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Command line tool to split a large HTML file into chunks. If it is called without any parameters, the help is printed
 * out.
 *
 * <h3>How it works</h3> Assume a document that looks like that:
 *
 * <pre>
 * some head information
 * xxxx
 * some introduction
 * Chapter Some Title
 * content
 * Chapter Another Title
 * content
 * ...
 * yyyy
 * some foot
 * </pre>
 *
 * The chunker identifies the head separator ("xxxx") and the foot separator ("yyyyy"). It the remaining document is
 * split into chunks by the chapter markers ("Chapter"). This leads to the following parts:
 *
 * <dl>
 * <dt>Head</dt>
 * <dd>
 *
 * <pre>
 * some head information
 * xxxx
 * </pre>
 *
 * </dd>
 * <dt>Foot</dt>
 * <dd>
 *
 * <pre>
 * yyyy
 * some foot
 * </pre>
 *
 * </dd>
 * <dt>Chunk 0</dt>
 * <dd>
 *
 * <pre>
 * some introduction
 * </pre>
 *
 * </dd>
 * <dt>Chunk 1</dt>
 * <dd>
 *
 * <pre>
 * Chapter Some Title
 * content
 * </pre>
 *
 * </dd>
 * <dt>Chunk 2</dt>
 * <dd>
 *
 * <pre>
 * Chapter Another Title
 * content
 * ...
 * </pre>
 *
 * </dd>
 * </dl>
 * Now a document is created for every chunk, prepending the head and appending the foot. Chunk 0 is usually the index
 * document. Chunk 1 and 2 are named after the title found in the chunk. All internal links are adjusted accordingly.
 *
 * <h3>Notes</h3>
 *
 * We do not use an XML parser here in order to be robust against invalid html. The separators are all defined by means
 * of regular expressions to make it as flexible as possible. Internally the sections within the html are stored by
 * means of {@link Range} instance.
 */
public class Chunker {

	static class Range {
		public final String name;
		public final int start;
		public final int end;

		public Range(String name, int start, int end) {
			this.name = name;
			this.start = start;
			this.end = end;
		}

		@Override
		public String toString() {
			return name + "(" + start + "-" + end + ")";
		}
	}

	private final static Pattern IDS = Pattern.compile("(?:\\s|<|\\\")id\\s*=\\s*\\\"([^\"]+)\"");
	private final static Pattern REFS = Pattern.compile("(?:\\s|<|\")href=\"#([^\"]+)");

	private final static String DEF_CHUNK = "<(?:h|H)1[^>]*>";
	private final static String DEF_FOOT = "</(?:body|BODY)>";
	private final static String DEF_NAME = "h1";
	private final static String DEF_INDEX = "index";

	private final String html;

	private final Pattern headPattern;
	private final Pattern footPattern;
	private final Pattern chunkPattern;
	private final Pattern nameTagStartPattern;
	private final Pattern nameTagEndPattern;
	private final String indexName;

	private Range head;
	private Range foot;
	/** Package visible for testing. */
	final List<Range> chunks = new ArrayList<>();
	/** Package visible for testing. */
	final Map<String, Range> anchorMappings = new HashMap<>();
	private final Matcher chunkMatcher;

	/**
	 * If called without arguments prints out help. In general: Parses commands, initializes an instance and use that to
	 * create the chunks.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length % 2 != 1) {
			printHelp();
			System.exit(1);
		}

		String head = null;
		String foot = null;
		String tag = null;
		String name = null;
		String index = null;

		String dir = ".";
		String in = null;

		for (int f = 0; f < args.length - 1; f += 2) {
			String flag = args[f];
			String value = args[f + 1];

			switch (flag) {
			case "-h":
				head = value;
				break;
			case "-f":
				foot = value;
				break;
			case "-c":
				tag = value;
				break;
			case "-d":
				dir = value;
				break;
			case "-n":
				name = value;
				break;
			case "-i":
				index = value;
				break;
			default:
				System.out.println("Flag " + flag + " not recognized.");
				printHelp();
				System.exit(2);
			}
		}
		in = args[args.length - 1];

		String html = new String(Files.readAllBytes(new File(in).toPath()));
		Chunker chunker = new Chunker(html, head, foot, tag, name, index);
		for (Range range : chunker.chunks) {
			String fileName = dir + File.separator + range.name + ".html";
			System.out.println("Writing chunk " + fileName);
			File f = new File(fileName);
			Files.write(f.toPath(), chunker.getChunk(range).toString().getBytes(), StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
		}

	}

	private static void printHelp() {
		System.out.println("chunker [-h head] [-f foot] [-c tag] [-d output-directory] inputfile\n"
				+ "-h regex    Separator to identify the end of the head,\n"
				+ "            if omitted the first tag is used to determine the head\n"
				+ "            If head is specified, the content between head and first tag is\n"
				+ "            used for index page.\n"
				+ "-i string   Name of the index file, if omitted " + DEF_INDEX + " is used.\n"
				+ "-f regex    Separator to identify foot, if omitted " + DEF_FOOT + " is used.\n"
				+ "-c regex    The marker to determine a chapter, if omitted\n"
				+ "            " + DEF_CHUNK + " is used.\n"
				+ "-n string   The name tag, if omitted " + DEF_NAME + " is used.\n"
				+ "-d output-directory\n"
				+ "            The output directly, if omitted the current folder is used.");
	}

	/**
	 * @param html
	 *            The full HTML source.
	 * @param head
	 *            The regex (as string) for finding the end of the head, may be null (in this case, no index will be
	 *            generated)
	 * @param foot
	 *            The regex (as string) for the foot, may be null (in this case, {@link #DEF_FOOT} is used).
	 * @param chunk
	 *            The regex (as string) for finding the start of a chunk, may be null (in this case, {@link #DEF_CHUNK}
	 *            is used).
	 * @param name
	 *            The tag of which the content is used as name of a chunk, may be null (in this case, {@link #DEF_NAME}
	 *            is used).
	 */
	public Chunker(String html, String head, String foot, String chunk, String name, String index) {

		this.html = html;
		if (html == null || html.isEmpty()) {
			throw new ChunkError("No html specified");
		}
		this.headPattern = head != null ? Pattern.compile(head) : null;
		this.footPattern = Pattern.compile(foot == null ? DEF_FOOT : foot);
		this.chunkPattern = Pattern.compile(chunk == null ? DEF_CHUNK : chunk);
		if (name == null) {
			name = DEF_NAME;
		}
		this.nameTagStartPattern = Pattern.compile("<(?:" + name.toLowerCase() + "|" + name.toUpperCase() + ")[^>]*>");
		this.nameTagEndPattern = Pattern.compile("</(?:" + name.toLowerCase() + "|" + name.toUpperCase() + ")[^>]*>");

		this.chunkMatcher = chunkPattern.matcher(html);

		this.indexName = index == null ? DEF_INDEX : index;

		findHead();
		findFoot();
		findChunks();

		createAnchorMappings();
	}

	/**
	 * Creates map {@link #anchorMappings}. At the moment, only ID attributes are recognized.
	 */
	private void createAnchorMappings() {
		Matcher m = IDS.matcher(html);
		while (m.find()) {
			String id = m.group(1);
			int start = m.start();
			Range chunk = findChunk(start);
			if (chunk != null) {
				anchorMappings.put(id, chunk);
			}
		}
	}

	/**
	 * Returns a chunk at the given offset. Returns null, if no chunk is found.
	 */
	private Range findChunk(int offset) {
		for (Range chunk : chunks) {
			if (chunk.end >= offset) {
				return chunk;
			}
		}
		return null; // head or foot
	}

	/**
	 * Try to find the {@link #headPattern} in order to identify the head. If no head pattern is defined, the head is
	 * assumed to end before the first chunk.
	 */
	private void findHead() {
		if (headPattern != null) {
			Matcher m = headPattern.matcher(html);
			if (!m.find()) {
				throw new ChunkError("Head separator not found.");
			}
			int pos = m.end();
			head = new Range("head", 0, pos);
		} else {
			int pos = findNextChunkStart(0);
			if (pos < 0) {
				throw new ChunkError("No chunk found, no head identified.");
			}
			head = new Range("head", 0, pos);
		}
	}

	/**
	 * Tries to find the {@link #footPattern} in order to identify the foot. If the foot pattern is not found, an
	 * {@link ChunkError} is thrown.
	 */
	private void findFoot() {
		Matcher m = footPattern.matcher(html);
		if (!m.find()) {
			throw new ChunkError("Foot separator not found.");
		}
		int pos = m.start();
		foot = new Range("foot", pos, html.length());
	}

	/**
	 * Try to find the next chunk starting at the given offset. It returns the start of the match or -1, if no chunk is
	 * found anymore.
	 */
	private int findNextChunkStart(int offset) {
		if (chunkMatcher.find(offset)) {
			int start = chunkMatcher.start();
			return start;
		}
		return -1;
	}

	/**
	 * Tries to find all chunks, including chunk 0 (between head and start of the first chunk separator).
	 */
	private void findChunks() {
		int start = head.end + 1;
		while (start < foot.start) {
			int end = findNextChunkStart(start);
			if (end < 0) {
				end = foot.start;
			}
			String name = null;
			if (headPattern != null && chunks.isEmpty()) {
				name = indexName;
			} else {
				name = findChunkName(start, end - 1);
				if (name == null)
					name = "Chunk_" + chunks.size() + 1;

				int i = 0;
				boolean foundDuplicateName = true;
				do {
					final String uniqueName = i == 0 ? name : name + "_" + i;
					name = uniqueName;
					i++;
					foundDuplicateName = chunks.stream().anyMatch(chunk -> chunk.name.equals(uniqueName));
				} while (foundDuplicateName);
			}

			Range chunk = new Range(name, start - 1, end);
			chunks.add(chunk);
			start = end + 1;
		}
	}

	/**
	 * Tries to extract the name of the chunk. This is done by using the tag pattern. Uses {@link ChunkHelper} since we
	 * use the same logic in {@link EclipseHelpTOCGenerator}.
	 */
	private String findChunkName(int start, int end) {

		// find tag content
		Matcher mStart = nameTagStartPattern.matcher(html);
		if (!mStart.find(start - 1)) {
			return null;
		}
		int startOfName = mStart.end();

		Matcher mEnd = nameTagEndPattern.matcher(html);
		if (!mEnd.find(startOfName)) {
			return null;
		}
		int endOfName = mEnd.start();
		if (endOfName > end) {
			return null;
		}

		return ChunkHelper.extractFileNameFromTitle(html, startOfName, endOfName);

	}

	/** Package visible for testing. Returns the content of the range. */
	CharSequence getChunk(Range range) {
		StringBuilder strb = new StringBuilder();
		appendWithRewrittenLinks(range, strb, html.substring(head.start, head.end));
		appendWithRewrittenLinks(range, strb, html.substring(range.start, range.end));
		appendWithRewrittenLinks(range, strb, html.substring(foot.start, foot.end));
		return strb;
	}

	/**
	 * Appends the content to the {@link StringBuilder}. Within the content, all links to other ranges are rewritten.
	 */
	private void appendWithRewrittenLinks(
			Range chunkFrom,
			StringBuilder strb, String content) {

		Matcher m = REFS.matcher(content);

		int offset = 0;
		while (m.find()) {
			String ref = m.group(1);
			String prefix = null;
			Range chunkTo = anchorMappings.get(ref);
			if (chunkTo != null && chunkTo != chunkFrom) {
				prefix = chunkTo.name;
			}
			if (prefix != null) {
				strb.append(content.substring(offset, m.start(1) - 1));
				strb.append(prefix).append(".html");
				strb.append('#');
				strb.append(ref);
				offset = m.end();
			}
		}
		if (offset < content.length()) {
			strb.append(content.substring(offset));
		}
	}

}
