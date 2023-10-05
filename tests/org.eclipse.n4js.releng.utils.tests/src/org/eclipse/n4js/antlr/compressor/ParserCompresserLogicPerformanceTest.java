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
package org.eclipse.n4js.antlr.compressor;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;


public class ParserCompresserLogicPerformanceTest {

	static final int[] tm = {0, 10,20,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,-1,-1,-1,-1,-1,-1,-1,-1,-1,1000};

	/**
	 * This test demos that the array look-up used to compress the parsers does not slow down the parser too much.
	 * Actually, in this naïve test (who knows what the compiler does), the look-up is 4 times faster then the cascade. This is promising at least.
	 */
	/* @formatter:off */
	@Test @Ignore("We do not want to repeat this test over and over again.") public void cascadeVsArrayLookup() {



		long t0 = System.currentTimeMillis();
		int sum1 = 0;
		for (int loop = 0; loop<1000000; loop++) {
			for (int k=30; k<60; k++) {
				int s = -1;
				if (k==31) { s = 10; }
				else if (k==30) { s = 0; }
				else if (k==39) { s = 90; }
				else if (k==32) { s = 20; }
				else if (k==38) { s = 80; }
				else if (k==33) { s = 30; }
				else if (k==37) { s = 70; }
				else if (k==34) { s = 40; }
				else if (k==36) { s = 60; }
				else if (k==35) { s = 50; }
				else if (k==40) { s = 100; }
				else if (k==49) { s = 190; }
				else if (k==42) { s = 120; }
				else if (k==48) { s = 180; }
				else if (k==43) { s = 130; }
				else if (k==47) { s = 170; }
				else if (k==44) { s = 140; }
				else if (k==46) { s = 160; }
				else if (k==45) { s = 150; }
				else if (k==41) { s = 110; }
				else if (k==59) { s = 1000; }
				sum1 += s;
			}
		}

		long t1 = System.currentTimeMillis();
		int sum2 = 0;
		for (int loop = 0; loop<1000000; loop++) {
			for (int k=30; k<60; k++) {
				int s = -1;
				if (k>=30 && k<=59 && ((s=tm[k-30])>0)) {/* already set in condition */ }
				sum2 += s;
			}
		}
		long t2 = System.currentTimeMillis();

		long cascadeTime = t1-t0;
		long lookupTime = t2-t1;

		assertEquals(sum1, sum2);
		assertTrue("Lookup shoud not slowdown parser", lookupTime<=cascadeTime);
//		System.out.println(cascadeTime + " - " + lookupTime);

	}



}
