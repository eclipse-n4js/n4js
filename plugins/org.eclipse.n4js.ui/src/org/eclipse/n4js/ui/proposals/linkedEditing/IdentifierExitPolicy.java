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
package org.eclipse.n4js.ui.proposals.linkedEditing;

import org.eclipse.jface.text.link.ILinkedModeListener;
import org.eclipse.jface.text.link.LinkedModeModel;
import org.eclipse.jface.text.link.LinkedModeUI.ExitFlags;
import org.eclipse.jface.text.link.LinkedModeUI.IExitPolicy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;

/**
 * An exit policy that'll leave the linked editing mode as soon as a char is entered that is not a valid identifier
 * part.
 *
 * @see IExitPolicy
 *
 *      Does not work with unicode escape sequences.
 */
public class IdentifierExitPolicy implements IExitPolicy {

	private final char exitCharacters[];

	/**
	 * Creates an exit policy for identifiers with some additional exit chars that should not be processed if entered
	 * but still leave the linked position editing. Usually {@code '\n'} is such an exit char.
	 */
	public IdentifierExitPolicy(char... additionalExitChars) {
		exitCharacters = additionalExitChars;
	}

	/**
	 * {@inheritDoc}
	 *
	 * If the entered character is not a valid identifier part, it is processed after linked editing has been quit.
	 * Exceptions are the {@link #exitCharacters} that have been passed into the constructor.
	 */
	@Override
	public ExitFlags doExit(LinkedModeModel environment, VerifyEvent event, int offset, int length) {
		if (event.character == '\0')
			return null;
		for (char c : exitCharacters) {
			if (event.character == c) {
				return new ExitFlags(ILinkedModeListener.UPDATE_CARET, false);
			}
		}
		switch (event.character) {
		case SWT.CR:
			return new ExitFlags(ILinkedModeListener.UPDATE_CARET, false);
		default: {
			if (!Character.isJavaIdentifierPart(event.character)) {
				return new ExitFlags(ILinkedModeListener.UPDATE_CARET, true);
			}
			return null;
		}
		}
	}

}
