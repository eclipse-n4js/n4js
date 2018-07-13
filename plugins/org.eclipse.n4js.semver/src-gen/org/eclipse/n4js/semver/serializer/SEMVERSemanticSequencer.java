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
import org.eclipse.n4js.semver.SEMVER.GitHubVersion;
import org.eclipse.n4js.semver.SEMVER.HyphenVersionRange;
import org.eclipse.n4js.semver.SEMVER.LocalPathVersion;
import org.eclipse.n4js.semver.SEMVER.Qualifier;
import org.eclipse.n4js.semver.SEMVER.QualifierTag;
import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;
import org.eclipse.n4js.semver.SEMVER.TagVersion;
import org.eclipse.n4js.semver.SEMVER.URLCommitISH;
import org.eclipse.n4js.semver.SEMVER.URLSemver;
import org.eclipse.n4js.semver.SEMVER.URLVersion;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;
import org.eclipse.n4js.semver.SEMVER.VersionPart;
import org.eclipse.n4js.semver.SEMVER.VersionRangeConstraint;
import org.eclipse.n4js.semver.SEMVER.VersionRangeSet;
import org.eclipse.n4js.semver.services.SEMVERGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class SEMVERSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private SEMVERGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == SEMVERPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case SEMVERPackage.GIT_HUB_VERSION:
				sequence_GitHubVersion(context, (GitHubVersion) semanticObject); 
				return; 
			case SEMVERPackage.HYPHEN_VERSION_RANGE:
				sequence_HyphenVersionRange(context, (HyphenVersionRange) semanticObject); 
				return; 
			case SEMVERPackage.LOCAL_PATH_VERSION:
				sequence_LocalPathVersion(context, (LocalPathVersion) semanticObject); 
				return; 
			case SEMVERPackage.QUALIFIER:
				sequence_Qualifier(context, (Qualifier) semanticObject); 
				return; 
			case SEMVERPackage.QUALIFIER_TAG:
				sequence_QualifierTag(context, (QualifierTag) semanticObject); 
				return; 
			case SEMVERPackage.SIMPLE_VERSION:
				sequence_SimpleVersion(context, (SimpleVersion) semanticObject); 
				return; 
			case SEMVERPackage.TAG_VERSION:
				sequence_TagVersion(context, (TagVersion) semanticObject); 
				return; 
			case SEMVERPackage.URL_COMMIT_ISH:
				sequence_URLCommitISH(context, (URLCommitISH) semanticObject); 
				return; 
			case SEMVERPackage.URL_SEMVER:
				sequence_URLSemver(context, (URLSemver) semanticObject); 
				return; 
			case SEMVERPackage.URL_VERSION:
				sequence_URLVersion(context, (URLVersion) semanticObject); 
				return; 
			case SEMVERPackage.VERSION_NUMBER:
				sequence_VersionNumber(context, (VersionNumber) semanticObject); 
				return; 
			case SEMVERPackage.VERSION_PART:
				sequence_VersionPart(context, (VersionPart) semanticObject); 
				return; 
			case SEMVERPackage.VERSION_RANGE_CONSTRAINT:
				sequence_VersionRangeContraint(context, (VersionRangeConstraint) semanticObject); 
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
	 *     NPMVersion returns GitHubVersion
	 *     GitHubVersion returns GitHubVersion
	 *
	 * Constraint:
	 *     (githubUrl=URL commitISH=ALPHA_NUMERIC_CHARS?)
	 */
	protected void sequence_GitHubVersion(ISerializationContext context, GitHubVersion semanticObject) {
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
		feeder.accept(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_5_0(), semanticObject.getTo());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NPMVersion returns LocalPathVersion
	 *     LocalPathVersion returns LocalPathVersion
	 *
	 * Constraint:
	 *     localPath=PATH
	 */
	protected void sequence_LocalPathVersion(ISerializationContext context, LocalPathVersion semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, SEMVERPackage.Literals.LOCAL_PATH_VERSION__LOCAL_PATH) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SEMVERPackage.Literals.LOCAL_PATH_VERSION__LOCAL_PATH));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getLocalPathVersionAccess().getLocalPathPATHParserRuleCall_1_0(), semanticObject.getLocalPath());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     QualifierTag returns QualifierTag
	 *
	 * Constraint:
	 *     (parts+=ALPHA_NUMERIC_CHARS parts+=ALPHA_NUMERIC_CHARS*)
	 */
	protected void sequence_QualifierTag(ISerializationContext context, QualifierTag semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Qualifier returns Qualifier
	 *
	 * Constraint:
	 *     (preRelease=QualifierTag | buildMetadata=QualifierTag | (preRelease=QualifierTag buildMetadata=QualifierTag))
	 */
	protected void sequence_Qualifier(ISerializationContext context, Qualifier semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     SimpleVersion returns SimpleVersion
	 *
	 * Constraint:
	 *     (comparators+=VersionComparator* number=VersionNumber)
	 */
	protected void sequence_SimpleVersion(ISerializationContext context, SimpleVersion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NPMVersion returns TagVersion
	 *     TagVersion returns TagVersion
	 *
	 * Constraint:
	 *     tagName=TAG
	 */
	protected void sequence_TagVersion(ISerializationContext context, TagVersion semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, SEMVERPackage.Literals.TAG_VERSION__TAG_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SEMVERPackage.Literals.TAG_VERSION__TAG_NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getTagVersionAccess().getTagNameTAGParserRuleCall_0(), semanticObject.getTagName());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     URLVersionSpecifier returns URLCommitISH
	 *     URLCommitISH returns URLCommitISH
	 *
	 * Constraint:
	 *     commitISH=ALPHA_NUMERIC_CHARS
	 */
	protected void sequence_URLCommitISH(ISerializationContext context, URLCommitISH semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, SEMVERPackage.Literals.URL_COMMIT_ISH__COMMIT_ISH) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SEMVERPackage.Literals.URL_COMMIT_ISH__COMMIT_ISH));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getURLCommitISHAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_0(), semanticObject.getCommitISH());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     URLVersionSpecifier returns URLSemver
	 *     URLSemver returns URLSemver
	 *
	 * Constraint:
	 *     simpleVersion=SimpleVersion
	 */
	protected void sequence_URLSemver(ISerializationContext context, URLSemver semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, SEMVERPackage.Literals.URL_SEMVER__SIMPLE_VERSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SEMVERPackage.Literals.URL_SEMVER__SIMPLE_VERSION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getURLSemverAccess().getSimpleVersionSimpleVersionParserRuleCall_1_0(), semanticObject.getSimpleVersion());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NPMVersion returns URLVersion
	 *     URLVersion returns URLVersion
	 *
	 * Constraint:
	 *     (protocol=URL_PROTOCOL url=URL versionSpecifier=URLVersionSpecifier?)
	 */
	protected void sequence_URLVersion(ISerializationContext context, URLVersion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     VersionNumber returns VersionNumber
	 *
	 * Constraint:
	 *     (major=VersionPart (minor=VersionPart (patch=VersionPart extended+=VersionPart*)?)? qualifier=Qualifier?)
	 */
	protected void sequence_VersionNumber(ISerializationContext context, VersionNumber semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     VersionPart returns VersionPart
	 *
	 * Constraint:
	 *     (wildcard?=WILDCARD | numberRaw=DIGITS)
	 */
	protected void sequence_VersionPart(ISerializationContext context, VersionPart semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     VersionRange returns VersionRangeConstraint
	 *     VersionRangeContraint returns VersionRangeConstraint
	 *
	 * Constraint:
	 *     (versionConstraints+=SimpleVersion versionConstraints+=SimpleVersion*)
	 */
	protected void sequence_VersionRangeContraint(ISerializationContext context, VersionRangeConstraint semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NPMVersion returns VersionRangeSet
	 *     VersionRangeSet returns VersionRangeSet
	 *
	 * Constraint:
	 *     (ranges+=VersionRange ranges+=VersionRange*)?
	 */
	protected void sequence_VersionRangeSet(ISerializationContext context, VersionRangeSet semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
