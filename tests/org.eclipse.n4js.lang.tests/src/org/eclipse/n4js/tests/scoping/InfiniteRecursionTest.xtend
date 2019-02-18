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
package org.eclipse.n4js.tests.scoping

import com.google.common.base.Charsets
import com.google.common.io.Resources
import com.google.inject.Inject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Just a test to make sure this finishes in a reasonable time.
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class InfiniteRecursionTest {

	@Inject extension ParseHelper<Script>

	@Test(timeout = 3500)
	def void testComputationFinishes_01() {
		val script = '''
			var BrowserBot = require("n4/lupenrein_js/uirobot/BrowserBot"),
			    Promise=require("n4/async/Promise"),
			    Frame = require("n4/lupenrein_js/uirobot/Frame");
			var TestApi = require("n4/lupenrein_js/TestAPI");
			var LAYERS_PAGE = "?n4-load=devel&n4-nologin&n4-noanim#WebTeamTest/layers";
			var SOME_PAGE = "?n4-load=devel&n4-nologin&n4-noanim#n4/newsfeed";
			/**
			 * @Category LT
			 * @Timeout 999000
			 */
			module.exports = TestClass({
			    does: [TestApi],

			    has: {
			        /**
			         * @type {n4/lupenrein_js/uirobot/BrowserBot}
			         */
			        bot: {
			            init: function () {
			                return new BrowserBot({});
			            }
			        }
			    },
			    override: {
			        /** @param {n4/lupenrein_js/Continuation} con */
			        beforeAll: function (con) {
			            var t = this.bot;

			            Promise.when(null).then(function () {
			                t.openPage(SOME_PAGE);
			                return t.waitFor(".appl-middlepane", [['is', ':visible']], true, undefined, "waiting for some page view failed", 555 * 1000, 1 * 1000);
			            }).then(function () {
			                t.delay(7 * 1000);
			                t.openPage(LAYERS_PAGE);
			                return t.waitFor(".appl-middlepane", [['is', ':visible']], true, undefined, "waiting for layers page view failed", 555 * 1000, 1 * 1000);
			            }).then(function (res) {
			                    con.success()
			                }, function (e) {
			                    con.failure(e);
			                });
			        }
			    },

			    should: {

			        /** @param {n4/lupenrein_js/Continuation} continuation */
			        "keyboard_navigation": function (continuation) {
			            var t = this.bot;

			            Promise.when( (function () {
			                t.openPage(LAYERS_PAGE);
			                return t.waitFor(".appl-middlepane", [['is', ':visible']], true, undefined, "waiting for complex page view failed", 555 * 1000, 1 * 1000);
			            })()).then(function () {
			//                return t.assertFrame("initialState", Frame.create(20,0,1240,700));
			//            }).then(function () {
			                t.delay(10000);
			                t.moveToElement("#agent", 20, 20).click().delay(500).pressTab();
			                return t.assertFrame("initalSheet", Frame.create(305, 110, 460, 450));
			            }).then(function () {
			                t.pressShiftTab().pressBackSpace(5).type("Borat").pressTab().pressEnter().delay(500);
			                return t.assertFrame("statusOpen", Frame.create(305, 110, 460, 450));
			            }).then(function () {
			                t.pressArrowUp(2).pressEnter();
			                return t.assertFrame("mrBorat", Frame.create(305, 110, 460, 450));
			            }).then(function () {
			                t.pressTab();
			                return t.assertFrame("appearanceFocus", Frame.create(305, 110, 460, 450));
			            }).then(function () {
			                t.pressEnter().delay(500).pressEnter().delay(200).pressArrowDown().pressEnter();
			                return t.assertFrame("hairColorChosen", Frame.create(305, 110, 460, 300));
			            }).then(function () {
			                t.pressTab().pressEnter().delay(200).pressArrowDown().pressEnter();
			                return t.assertFrame("eyesColorChosen", Frame.create(305, 110, 460, 300));
			            }).then(function () {
			                t.pressTab(2).pressEnter().delay(300).pressTab().moveBy(300,0);
			                return t.assertFrame("fortuneFocused", Frame.create(305, 110, 460, 450));
			            }).then(function () {
			                t.type("12").pressArrowDown().delay(300).pressArrowUp();
			                return t.assertFrame("chosingDollar", Frame.create(305, 110, 460, 450));
			            }).then(function () {
			                t.pressEnter().pressTab();
			                return t.assertFrame("12$", Frame.create(305, 110, 460, 450));
			            }).then(function () {
			                t.pressEnter().delay(2000).pressArrowDown();
			                return t.assertFrame("ALBANIA", Frame.create(425, 420, 310, 370))
			            }).then(function () {
			                t.pressEnter().pressTab();
			                return t.assertFrame("describeManagerFocus", Frame.create(305, 110, 460, 450));
			            }).then(function () {
			                t.pressEnter().pressEnter().pressArrowDown().pressEnter().pressTab().type("managgg").pressShiftTab();
			                return t.assertFrame("ManagerMSFocus", Frame.create(305, 110, 460, 550));
			            }).then(function () {
			                t.pressTab().pressEnter().pressEnter().pressTab();
			                return t.assertFrame("addFamillyFocus", Frame.create(305, 110, 460, 550));
			            }).then(function () {/*opening sheet with layouted list*/
			                t.pressEnter().delay(500)/*opening sibling sheet*/.pressEnter().delay(500);
			                t.type("Akhmet").pressShiftTab();
			                return t.assertFrame("akhmetSaveFocus", Frame.create(290, 110, 610, 200));
			            }).then(function () {
			                t.pressEnter().delay(900);
			                return t.assertFrame("akhmetInList", Frame.create(290, 110, 610, 200));
			            }).then(function () {
			                t.pressEnter().delay(500).type("Makhmoot").pressShiftTab();
			                return t.assertFrame("MakhmootSaveFocus", Frame.create(290, 110, 610, 200));
			            }).then(function () {
			                t.pressEnter().delay(900);
			                return t.assertFrame("akhmetAndMakhmoot", Frame.create(290, 110, 610, 200));
			            }).then(function () {
			                t.pressShiftTab().pressEnter().delay(500);
			                return t.assertFrame("familiesAdded", Frame.create(305, 110, 460, 550));
			            }).then(function () {
			                t.pressTab(2).pressEnter().delay(1000);
			                return t.assertFrame("agentAdded", Frame.create(280,60,500,170));
			            }).then(function () {
			                    continuation.success();
			                }, function (err) {
			                    continuation.failure(err);
			            });
			        }
			    }

			});
		'''.parse
		EcoreUtil.resolveAll(script)
	}

	@Ignore
	@Test //(timeout = 3500)
	def void testComputationFinishes_02() {
		val fileURL = class.getResource("InfiniteComputationTest_02.txt")
		val scriptAsString = Resources.toString(fileURL, Charsets.UTF_8)

		val script = scriptAsString.parse
		EcoreUtil.resolveAll(script)
	}
}
