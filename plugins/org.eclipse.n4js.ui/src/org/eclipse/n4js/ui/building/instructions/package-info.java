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
/**
 * Provides {@link org.eclipse.n4js.ui.building.instructions.IBuildParticipantInstruction}, an adapter for the
 * resource set during the build. It is created in {@link org.eclipse.n4js.ui.building.N4JSBuilderParticipant} while
 * preparing the build and contains configuration for generating the JavaScript files out of the n4js resp. js files. It
 * comes in three variants:
 * <ul>
 * <li>{@link org.eclipse.n4js.ui.building.instructions.NoopInstruction}, applied, when no build type is set, this
 * builder does nothing</li>
 * <li>{@link org.eclipse.n4js.ui.building.instructions.CleanInstruction}, applied, when build type is clean, this
 * builder will clean the output directories</li>
 * <li>{@link org.eclipse.n4js.ui.building.instructions.BuildInstruction}, applied, when build type is set to build,
 * this builder does call the actual generator (bound in the grammar project) when there is changed content in this
 * project</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ui.building.N4JSBuilderParticipant
 */
package org.eclipse.n4js.ui.building.instructions;
