/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4jsx.xpect.ui.tests

import com.google.inject.Inject
import org.eclipse.core.internal.resources.Workspace
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.n4js.N4JSUiInjectorProvider
import org.eclipse.n4js.tests.helper.documentprovider.CountPostChangeBroadcastChangeNotificationManager
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.ui.XtextProjectHelper
import org.eclipse.xtext.ui.testing.AbstractEditorTest
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions
import org.junit.Test
import org.junit.runner.RunWith

import static java.util.UUID.randomUUID

import static extension org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.*

/**
 * This class tests the fix for refresh N4JSX file problem in XtextDocumentProvider. GH-270.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSUiInjectorProvider)
class N4JSXAvoidRefreshDocumentProviderTest extends AbstractEditorTest {

	@Inject
	ReflectExtensions reflectExtensions

	val N4JSX_EDITOR_ID = 'org.eclipse.n4js.n4jsx.N4JSX'
	val PROJECT_NAME = 'testProject'
	val MF_FILE = 'manifest.nfmf'
	val SRC = 'src';

	override setUp() throws Exception {
		super.setUp()
		createN4JSProjectWithXtextNature
	}

	@Test
	public def void noRefreshWhenOpenningN4JSXFileTest() {
		val content = '<div/>';		
		val n4jsxFile = createFileWithContent(content)
		val workspace = ResourcesPlugin.getWorkspace() as Workspace
		val notificationManager = reflectExtensions.get(workspace, "notificationManager")

		val countBroadcastChangeNotificationManager = new CountPostChangeBroadcastChangeNotificationManager(workspace)

		try {
			// Use reflection to replace workspace's notification manager with our custom notification manager
			reflectExtensions.set(workspace, "notificationManager", countBroadcastChangeNotificationManager)
			// We need to startup the notification so that it can get the initial state of the workspace
			countBroadcastChangeNotificationManager.startup(null)

			// Open the editor
			n4jsxFile.openEditor
		} finally {
			// Restore the notification manager
			reflectExtensions.set(workspace, "notificationManager", notificationManager)
		}
		assertEquals("Exactly 1 POST_CHANGE broadcast event should have been triggered.", 1, countBroadcastChangeNotificationManager.numberPostChangeTriggered)
	}

	def private createN4JSProjectWithXtextNature() {
		createProject(PROJECT_NAME) => [
			project.addNature(XtextProjectHelper.NATURE_ID)
			createFolder('''«PROJECT_NAME»/«SRC»''')
			createN4MFFile
		]
	}

	def private createFileWithContent(String content) {
		val file = createFile('''«PROJECT_NAME»/«SRC»/«randomUUID».n4jsx''', content)
		file
	}

	def private createN4MFFile() {
		createFile('''«PROJECT_NAME»/«MF_FILE»''', MFFileContent.toString);
	}

	def private getMFFileContent() '''
		ProjectId: «PROJECT_NAME»
		ProjectType: library
		ProjectVersion: 0.0.1-SNAPSHOT
		VendorId: org.eclipse.n4js
		VendorName: 'Eclipse N4JS Project'
		Output: 'src-gen'
		Sources {
			source {
				'«SRC»'
			}
		}
	'''
	override protected getEditorId() {
		N4JSX_EDITOR_ID
	}
}
