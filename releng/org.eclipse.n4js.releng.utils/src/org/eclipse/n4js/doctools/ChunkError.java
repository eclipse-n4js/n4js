package org.eclipse.n4js.doctools;

/**
 * Generic error created by the {@link Chunker} tool.
 */
public class ChunkError extends RuntimeException {

	ChunkError(String msg) {
		super(msg);
	}

}
