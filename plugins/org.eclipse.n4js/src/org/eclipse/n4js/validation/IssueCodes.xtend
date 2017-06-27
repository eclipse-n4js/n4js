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
package org.eclipse.n4js.validation

import org.eclipse.n4js.utils.NLS

/**
 * Provides messages and helper methods for creating NLS messages.
 * This class uses an active annotation {@code @NLS} to derive constants and methods out of the entries of the
 * {@code messages.properties}. See {@link org.eclipse.n4js.utils.NLSProcessor} for details about the message format.
 */
@NLS(propertyFileName="messages")
class IssueCodes {}
