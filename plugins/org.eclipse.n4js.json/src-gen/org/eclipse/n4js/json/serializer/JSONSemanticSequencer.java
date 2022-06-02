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
package org.eclipse.n4js.json.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONBooleanLiteral;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONNullLiteral;
import org.eclipse.n4js.json.JSON.JSONNumericLiteral;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.services.JSONGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class JSONSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private JSONGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == JSONPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case JSONPackage.JSON_ARRAY:
				sequence_JSONArray(context, (JSONArray) semanticObject); 
				return; 
			case JSONPackage.JSON_BOOLEAN_LITERAL:
				sequence_JSONBooleanLiteral(context, (JSONBooleanLiteral) semanticObject); 
				return; 
			case JSONPackage.JSON_DOCUMENT:
				sequence_JSONDocument(context, (JSONDocument) semanticObject); 
				return; 
			case JSONPackage.JSON_NULL_LITERAL:
				sequence_JSONNullLiteral(context, (JSONNullLiteral) semanticObject); 
				return; 
			case JSONPackage.JSON_NUMERIC_LITERAL:
				sequence_JSONNumericLiteral(context, (JSONNumericLiteral) semanticObject); 
				return; 
			case JSONPackage.JSON_OBJECT:
				sequence_JSONObject(context, (JSONObject) semanticObject); 
				return; 
			case JSONPackage.JSON_STRING_LITERAL:
				sequence_JSONStringLiteral(context, (JSONStringLiteral) semanticObject); 
				return; 
			case JSONPackage.NAME_VALUE_PAIR:
				sequence_NameValuePair(context, (NameValuePair) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     JSONArray returns JSONArray
	 *     JSONValue returns JSONArray
	 *
	 * Constraint:
	 *     (elements+=JSONValue elements+=JSONValue*)?
	 * </pre>
	 */
	protected void sequence_JSONArray(ISerializationContext context, JSONArray semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     JSONValue returns JSONBooleanLiteral
	 *     JSONBooleanLiteral returns JSONBooleanLiteral
	 *
	 * Constraint:
	 *     booleanValue?='true'?
	 * </pre>
	 */
	protected void sequence_JSONBooleanLiteral(ISerializationContext context, JSONBooleanLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     JSONDocument returns JSONDocument
	 *
	 * Constraint:
	 *     content=JSONValue?
	 * </pre>
	 */
	protected void sequence_JSONDocument(ISerializationContext context, JSONDocument semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     JSONValue returns JSONNullLiteral
	 *     JSONNullLiteral returns JSONNullLiteral
	 *
	 * Constraint:
	 *     {JSONNullLiteral}
	 * </pre>
	 */
	protected void sequence_JSONNullLiteral(ISerializationContext context, JSONNullLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     JSONValue returns JSONNumericLiteral
	 *     JSONNumericLiteral returns JSONNumericLiteral
	 *
	 * Constraint:
	 *     value=NUMBER
	 * </pre>
	 */
	protected void sequence_JSONNumericLiteral(ISerializationContext context, JSONNumericLiteral semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, JSONPackage.Literals.JSON_NUMERIC_LITERAL__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JSONPackage.Literals.JSON_NUMERIC_LITERAL__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getJSONNumericLiteralAccess().getValueNUMBERTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     JSONObject returns JSONObject
	 *     JSONValue returns JSONObject
	 *
	 * Constraint:
	 *     (nameValuePairs+=NameValuePair nameValuePairs+=NameValuePair*)?
	 * </pre>
	 */
	protected void sequence_JSONObject(ISerializationContext context, JSONObject semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     JSONValue returns JSONStringLiteral
	 *     JSONStringLiteral returns JSONStringLiteral
	 *
	 * Constraint:
	 *     value=STRING
	 * </pre>
	 */
	protected void sequence_JSONStringLiteral(ISerializationContext context, JSONStringLiteral semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, JSONPackage.Literals.JSON_STRING_LITERAL__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JSONPackage.Literals.JSON_STRING_LITERAL__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getJSONStringLiteralAccess().getValueSTRINGTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     NameValuePair returns NameValuePair
	 *
	 * Constraint:
	 *     (name=STRING value=JSONValue)
	 * </pre>
	 */
	protected void sequence_NameValuePair(ISerializationContext context, NameValuePair semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, JSONPackage.Literals.NAME_VALUE_PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JSONPackage.Literals.NAME_VALUE_PAIR__NAME));
			if (transientValues.isValueTransient(semanticObject, JSONPackage.Literals.NAME_VALUE_PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, JSONPackage.Literals.NAME_VALUE_PAIR__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getNameValuePairAccess().getNameSTRINGTerminalRuleCall_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getNameValuePairAccess().getValueJSONValueParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
}
