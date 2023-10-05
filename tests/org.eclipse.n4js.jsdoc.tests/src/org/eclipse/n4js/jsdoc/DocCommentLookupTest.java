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
package org.eclipse.n4js.jsdoc;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.jsdoc.lookup.CommentCandidate;
import org.eclipse.n4js.jsdoc.lookup.DocCommentLookup;

/**
 * SingleLine comment should be ignored by comment picker.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class DocCommentLookupTest {
	@Inject
	private ParseHelper<EObject> parser;

	@Inject
	private DocCommentLookup lkpDC;

	private CommentCandidate getCommentConsiderDoubleStar(String code) throws Exception {
		return lkpDC.findJSDocComment(parser.parse(code), true);
	}

	private CommentCandidate getCommentIgnoreDoubleStar(String code) throws Exception {
		return lkpDC.findJSDocComment(parser.parse(code), false);
	}

	
	@Test
	public void NullForNull() throws Exception {
		Assert.assertNull(lkpDC.findJSDocComment(null, true));
		Assert.assertNull(lkpDC.findJSDocComment(null, false));
	}

	
	@Test
	public void NUllforNoComments() throws Exception {
		String code = "var a = 'a';";
		Assert.assertNull(getCommentConsiderDoubleStar(code));
		Assert.assertNull(getCommentIgnoreDoubleStar(code));
	}

	
	@Test
	public void NullForSingleLineComment() throws Exception {
		String code = "// foo \n var a = 'a';";
		Assert.assertNull(getCommentConsiderDoubleStar(code));
		Assert.assertNull(getCommentIgnoreDoubleStar(code));
	}

	
	@Test
	public void SingleStarML() throws Exception {
		String code = "/* foo */ \n var a = 'a';";
		Assert.assertEquals("/* foo */", getCommentConsiderDoubleStar(code).getText());
		Assert.assertEquals("/* foo */", getCommentIgnoreDoubleStar(code).getText());
	}

	
	@Test
	public void SingleStarMLAndSL() throws Exception {
		String code = "/* foo */ \n //bar \n var a = 'a';";
		Assert.assertEquals("/* foo */", getCommentConsiderDoubleStar(code).getText());
		Assert.assertEquals("/* foo */", getCommentIgnoreDoubleStar(code).getText());
	}

	
	@Test
	public void pickNearestSingleStarML() throws Exception {
		String code = "/* foo */ \n /* bar */ var a = 'a';";
		Assert.assertEquals("/* bar */", getCommentConsiderDoubleStar(code).getText());
		Assert.assertEquals("/* bar */", getCommentIgnoreDoubleStar(code).getText());
	}

	
	@Test
	public void DoubleStarML() throws Exception {
		String code = "/** foo */ var a = 'a';";
		Assert.assertEquals("/** foo */", getCommentConsiderDoubleStar(code).getText());
		Assert.assertNull(getCommentIgnoreDoubleStar(code));
	}

	
	@Test
	public void pickNearestDoubleStarML() throws Exception {
		String code = "/** foo */ /** bar */ var a = 'a';";
		Assert.assertEquals("/** bar */", getCommentConsiderDoubleStar(code).getText());
		Assert.assertNull(getCommentIgnoreDoubleStar(code));
	}

	
	@Test
	public void SingleStarAndDoubleStar() throws Exception {
		String code = "/** foo */ /* bar */ var a = 'a';";
		Assert.assertEquals("/** foo */", getCommentConsiderDoubleStar(code).getText());
		Assert.assertEquals("/* bar */", getCommentIgnoreDoubleStar(code).getText());
	}

	
	@Test
	public void DoubleStarAndSingleStar() throws Exception {
		String code = "/* foo */ /** bar */ var a = 'a';";
		Assert.assertEquals("/** bar */", getCommentConsiderDoubleStar(code).getText());
		Assert.assertEquals("/* foo */", getCommentIgnoreDoubleStar(code).getText());
	}

}
