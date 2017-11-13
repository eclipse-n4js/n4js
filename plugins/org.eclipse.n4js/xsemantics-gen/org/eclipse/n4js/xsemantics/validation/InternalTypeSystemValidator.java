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
package org.eclipse.n4js.xsemantics.validation;

import com.google.inject.Inject;
import org.eclipse.n4js.validation.AbstractMessageAdjustingN4JSValidator;
import org.eclipse.n4js.xsemantics.InternalTypeSystem;
import org.eclipse.xsemantics.runtime.validation.XsemanticsValidatorErrorGenerator;

/**
 * N4JS Type System.
 * 
 * <h3>Naming conventions</h3>
 * 
 * Rules and axioms are named using the following pattern:
 * <pre>
 * &lt;relationName>&lt;InputType(s)>&lt;Additional_description>
 * </pre>
 * If a rule has multiple input parameters, either only the first one is used (in case
 * the second is not that important), or all types are used (without any separator).
 * <p>
 * The rules/axioms are ordered by judgment, and then according to the ("major" or first) input type,
 * if possible the order is similar as the order used in the N4JS specification.
 * 
 * <h3>Changes to the Rule Environment</h3>
 * 
 * By convention, rules should <em>not</em> change the rule environment and can (and should) rely
 * on other rules not changing the rule environment in case of nested inference. Thus:
 * <ul>
 * <li>before modifying the rule environment a new, derived rule environment must be created,
 *     usually by using extension method #wrap(). For example:
 *     <pre>
 *     val G2 = G.wrap;
 *     G2.add("MY_KEY",someValue);
 *     G2.addSubstitutions(someTypeRef);
 *     </pre>
 * <li>when doing nested inference within a rule, no new rule environment should be created (because
 *     we assume it won't be changed by the nested rule calls)
 * </ul>
 * There are two ways of creating a new rule environment: create empty environment or wrap existing
 * rule environment. When passing the newly created environment on to a nested inference, it is important
 * to always derive, because otherwise recursion guards will be lost.
 * 
 * <h3>Bibliography</h3>
 * <dl>
 * <dt><a name="N4JS">[N4JS]</a></dt>
 * <dd>Pilgrim, Jens von et al.: N4JS Specification / NumberFour AG. Berlin, September 2013.
 * 			Specification. <a href="https://github.com/NumberFour/specs/">https://github.com/NumberFour/specs/</a></dd>
 * </dl>
 */
@SuppressWarnings("all")
public class InternalTypeSystemValidator extends AbstractMessageAdjustingN4JSValidator {
  @Inject
  protected XsemanticsValidatorErrorGenerator errorGenerator;
  
  @Inject
  protected InternalTypeSystem xsemanticsSystem;
  
  protected InternalTypeSystem getXsemanticsSystem() {
    return this.xsemanticsSystem;
  }
}
