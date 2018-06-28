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
package org.eclipse.n4js.semver.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.n4js.semver.SEMVER.EnumeratedVersionRange;
import org.eclipse.n4js.semver.SEMVER.HyphenVersionRange;
import org.eclipse.n4js.semver.SEMVER.Qualifier;
import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;
import org.eclipse.n4js.semver.SEMVER.VersionRangeSet;
import org.eclipse.n4js.semver.services.SemanticVersioningGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class SemanticVersioningSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private SemanticVersioningGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == SEMVERPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case SEMVERPackage.ENUMERATED_VERSION_RANGE:
				sequence_EnumeratedVersionRange(context, (EnumeratedVersionRange) semanticObject); 
				return; 
			case SEMVERPackage.HYPHEN_VERSION_RANGE:
				sequence_HyphenVersionRange(context, (HyphenVersionRange) semanticObject); 
				return; 
			case SEMVERPackage.QUALIFIER:
				sequence_Qualifier(context, (Qualifier) semanticObject); 
				return; 
			case SEMVERPackage.SIMPLE_VERSION:
				sequence_SimpleVersion(context, (SimpleVersion) semanticObject); 
				return; 
			case SEMVERPackage.VERSION_NUMBER:
				sequence_VersionNumber(context, (VersionNumber) semanticObject); 
				return; 
			case SEMVERPackage.VERSION_RANGE_SET:
				sequence_VersionRangeSet(context, (VersionRangeSet) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     VersionRange returns EnumeratedVersionRange
	 *     EnumeratedVersionRange returns EnumeratedVersionRange
	 *
	 * Constraint:
	 *     simpleVersions+=SimpleVersion+
	 */
	protected void sequence_EnumeratedVersionRange(ISerializationContext context, EnumeratedVersionRange semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     VersionRange returns HyphenVersionRange
	 *     HyphenVersionRange returns HyphenVersionRange
	 *
	 * Constraint:
	 *     (from=VersionNumber to=VersionNumber)
	 */
	protected void sequence_HyphenVersionRange(ISerializationContext context, HyphenVersionRange semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, SEMVERPackage.Literals.HYPHEN_VERSION_RANGE__FROM) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SEMVERPackage.Literals.HYPHEN_VERSION_RANGE__FROM));
			if (transientValues.isValueTransient(semanticObject, SEMVERPackage.Literals.HYPHEN_VERSION_RANGE__TO) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SEMVERPackage.Literals.HYPHEN_VERSION_RANGE__TO));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0(), semanticObject.getFrom());
		feeder.accept(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_3_0(), semanticObject.getTo());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Qualifier returns Qualifier
	 *
	 * Constraint:
	 *     (preRelease=Parts | buildMetadata=Parts)
	 */
	protected void sequence_Qualifier(ISerializationContext context, Qualifier semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     SimpleVersion returns SimpleVersion
	 *
	 * Constraint:
	 *     (comparator=VersionComparator? hasTilde?='~' hasCaret?='^' number=VersionNumber)
	 */
	protected void sequence_SimpleVersion(ISerializationContext context, SimpleVersion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     VersionNumber returns VersionNumber
	 *
	 * Constraint:
	 *     (major=Xr (minor=Xr (path=Xr qualifier=Qualifier?)?)?)
	 */
	protected void sequence_VersionNumber(ISerializationContext context, VersionNumber semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     VersionRangeSet returns VersionRangeSet
	 *
	 * Constraint:
	 *     ((ranges+=VersionRange ranges+=VersionRange+) | ranges+=VersionRange+)?
	 */
	protected void sequence_VersionRangeSet(ISerializationContext context, VersionRangeSet semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
