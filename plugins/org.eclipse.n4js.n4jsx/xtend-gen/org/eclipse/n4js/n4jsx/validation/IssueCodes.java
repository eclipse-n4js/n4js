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
package org.eclipse.n4js.n4jsx.validation;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.n4js.utils.NLS;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

/**
 * Provides messages and helper methods for creating NLS messages.
 * This class uses an active annotation {@code @NLS} to derive constants and methods out of the entries of the
 * {@code messages.properties}. See {@link org.eclipse.n4js.utils.NLSProcessor} for details about the message format.
 */
@NLS(propertyFileName = "messages")
@SuppressWarnings("all")
public class IssueCodes extends org.eclipse.n4js.validation.IssueCodes {
  private final static String BUNDLE_NAME = IssueCodes.class.getPackage().getName() + ".messages";
  
  private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
  
  private final static String INITIALIZER = new Function0<String>() {
        public String apply() {
          org.eclipse.osgi.util.NLS.initializeMessages(BUNDLE_NAME, IssueCodes.class);
          return "";
        }
      }.apply();;
  
  private static String getString(final String key) {
    try {
    	String value = RESOURCE_BUNDLE.getString(key);
    	String[] parts = value.split(";;;");
    	return parts[1];
    } catch (MissingResourceException e) {
    	return '!' + key + '!';
    }
  }
  
  public static Severity getDefaultSeverity(final String key) {
    try {
    	String value = RESOURCE_BUNDLE.getString(key);
    	String[] parts = value.split(";;;");
    	String defaultSeverity = parts[0];
    	return Severity.valueOf(defaultSeverity.toUpperCase());
    } catch (MissingResourceException e) {
    	return null;
    }
  }
  
  public final static String HTMLTAG_UNKNOWN = "HTMLTAG_UNKNOWN";
  
  /**
   * Tag {0} is an unknown HTML tag.
   */
  public static String getMessageForHTMLTAG_UNKNOWN(final Object param0) {
    return org.eclipse.osgi.util.NLS.bind(getString(HTMLTAG_UNKNOWN), new Object [] { param0 });
  }
  
  public final static String JSXELEMENT_NOT_BIND_TO_REACT_COMPONENT = "JSXELEMENT_NOT_BIND_TO_REACT_COMPONENT";
  
  /**
   * JSX element {0} does not bind to any valid React component.
   */
  public static String getMessageForJSXELEMENT_NOT_BIND_TO_REACT_COMPONENT(final Object param0) {
    return org.eclipse.osgi.util.NLS.bind(getString(JSXELEMENT_NOT_BIND_TO_REACT_COMPONENT), new Object [] { param0 });
  }
  
  public final static String JSXELEMENT_OPENING_CLOSING_ELEMENT_NOT_MATCH = "JSXELEMENT_OPENING_CLOSING_ELEMENT_NOT_MATCH";
  
  /**
   * Opening element {0} does not match with closing element {1}.
   */
  public static String getMessageForJSXELEMENT_OPENING_CLOSING_ELEMENT_NOT_MATCH(final Object param0, final Object param1) {
    return org.eclipse.osgi.util.NLS.bind(getString(JSXELEMENT_OPENING_CLOSING_ELEMENT_NOT_MATCH), new Object [] { param0, param1 });
  }
  
  public final static String JSXPROPERTY_ATTRIBUTE_NON_OPTIONAL_PROPERTY_NOT_SPECIFIED = "JSXPROPERTY_ATTRIBUTE_NON_OPTIONAL_PROPERTY_NOT_SPECIFIED";
  
  /**
   * Non-optional property {0} should be specified.
   */
  public static String getMessageForJSXPROPERTY_ATTRIBUTE_NON_OPTIONAL_PROPERTY_NOT_SPECIFIED(final Object param0) {
    return org.eclipse.osgi.util.NLS.bind(getString(JSXPROPERTY_ATTRIBUTE_NON_OPTIONAL_PROPERTY_NOT_SPECIFIED), new Object [] { param0 });
  }
  
  public final static String JSXSPREADATTRIBUTE_NOT_DECLARED_IN_PROPS = "JSXSPREADATTRIBUTE_NOT_DECLARED_IN_PROPS";
  
  /**
   * Attribute {0} is not a declared property in the props of {1}.
   */
  public static String getMessageForJSXSPREADATTRIBUTE_NOT_DECLARED_IN_PROPS(final Object param0, final Object param1) {
    return org.eclipse.osgi.util.NLS.bind(getString(JSXSPREADATTRIBUTE_NOT_DECLARED_IN_PROPS), new Object [] { param0, param1 });
  }
  
  public final static String JSXSPREADATTRIBUTE_WRONG_SUBTYPE = "JSXSPREADATTRIBUTE_WRONG_SUBTYPE";
  
  /**
   * Attribute {0} has wrong type because {1} not subtype of {2}.
   */
  public static String getMessageForJSXSPREADATTRIBUTE_WRONG_SUBTYPE(final Object param0, final Object param1, final Object param2) {
    return org.eclipse.osgi.util.NLS.bind(getString(JSXSPREADATTRIBUTE_WRONG_SUBTYPE), new Object [] { param0, param1, param2 });
  }
  
  public final static String JSXSPROPERTYATTRIBUTE_NOT_DECLARED_IN_PROPS = "JSXSPROPERTYATTRIBUTE_NOT_DECLARED_IN_PROPS";
  
  /**
   * Attribute {0} is not a declared property in the props of {1}.
   */
  public static String getMessageForJSXSPROPERTYATTRIBUTE_NOT_DECLARED_IN_PROPS(final Object param0, final Object param1) {
    return org.eclipse.osgi.util.NLS.bind(getString(JSXSPROPERTYATTRIBUTE_NOT_DECLARED_IN_PROPS), new Object [] { param0, param1 });
  }
  
  public final static String REACT_CLASS_COMPONENT_CANNOT_START_WITH_LOWER_CASE = "REACT_CLASS_COMPONENT_CANNOT_START_WITH_LOWER_CASE";
  
  /**
   * React class component {0} cannot start with lower case.
   */
  public static String getMessageForREACT_CLASS_COMPONENT_CANNOT_START_WITH_LOWER_CASE(final Object param0) {
    return org.eclipse.osgi.util.NLS.bind(getString(REACT_CLASS_COMPONENT_CANNOT_START_WITH_LOWER_CASE), new Object [] { param0 });
  }
  
  public final static String REACT_ELEMENT_CLASS_NOT_REACT_ELEMENT_ERROR = "REACT_ELEMENT_CLASS_NOT_REACT_ELEMENT_ERROR";
  
  /**
   * The referred class is not a subtype of React.Component
   */
  public static String getMessageForREACT_ELEMENT_CLASS_NOT_REACT_ELEMENT_ERROR() {
    return org.eclipse.osgi.util.NLS.bind(getString(REACT_ELEMENT_CLASS_NOT_REACT_ELEMENT_ERROR), new Object [] {  });
  }
  
  public final static String REACT_ELEMENT_FUNCTION_NOT_REACT_ELEMENT_ERROR = "REACT_ELEMENT_FUNCTION_NOT_REACT_ELEMENT_ERROR";
  
  /**
   * Expect a function returning React.Element but the return type is {0}.
   */
  public static String getMessageForREACT_ELEMENT_FUNCTION_NOT_REACT_ELEMENT_ERROR(final Object param0) {
    return org.eclipse.osgi.util.NLS.bind(getString(REACT_ELEMENT_FUNCTION_NOT_REACT_ELEMENT_ERROR), new Object [] { param0 });
  }
  
  public final static String REACT_ELEMENT_NOT_FUNCTION_OR_CLASS_ERROR = "REACT_ELEMENT_NOT_FUNCTION_OR_CLASS_ERROR";
  
  /**
   * JSX element is expected to bind to either a function or class, but bind to type {0} instead.
   */
  public static String getMessageForREACT_ELEMENT_NOT_FUNCTION_OR_CLASS_ERROR(final Object param0) {
    return org.eclipse.osgi.util.NLS.bind(getString(REACT_ELEMENT_NOT_FUNCTION_OR_CLASS_ERROR), new Object [] { param0 });
  }
  
  public final static String REACT_FUNCTIONAL_COMPONENT_CANNOT_START_WITH_LOWER_CASE = "REACT_FUNCTIONAL_COMPONENT_CANNOT_START_WITH_LOWER_CASE";
  
  /**
   * React functional component {0} cannot start with lower case.
   */
  public static String getMessageForREACT_FUNCTIONAL_COMPONENT_CANNOT_START_WITH_LOWER_CASE(final Object param0) {
    return org.eclipse.osgi.util.NLS.bind(getString(REACT_FUNCTIONAL_COMPONENT_CANNOT_START_WITH_LOWER_CASE), new Object [] { param0 });
  }
  
  public final static String REACT_NAMESPACE_NOT_ALLOWED = "REACT_NAMESPACE_NOT_ALLOWED";
  
  /**
   * Namespace to react must be React.
   */
  public static String getMessageForREACT_NAMESPACE_NOT_ALLOWED() {
    return org.eclipse.osgi.util.NLS.bind(getString(REACT_NAMESPACE_NOT_ALLOWED), new Object [] {  });
  }
}
