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
import org.eclipse.n4js.semver.Semver.GitHubVersionRequirement;
import org.eclipse.n4js.semver.Semver.HyphenVersionRange;
import org.eclipse.n4js.semver.Semver.LocalPathVersionRequirement;
import org.eclipse.n4js.semver.Semver.Qualifier;
import org.eclipse.n4js.semver.Semver.QualifierTag;
import org.eclipse.n4js.semver.Semver.SemverPackage;
import org.eclipse.n4js.semver.Semver.SimpleVersion;
import org.eclipse.n4js.semver.Semver.TagVersionRequirement;
import org.eclipse.n4js.semver.Semver.URLCommitISH;
import org.eclipse.n4js.semver.Semver.URLSemver;
import org.eclipse.n4js.semver.Semver.URLVersionRequirement;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.Semver.VersionPart;
import org.eclipse.n4js.semver.Semver.VersionRangeConstraint;
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement;
import org.eclipse.n4js.semver.services.SemverGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class SemverSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private SemverGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == SemverPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case SemverPackage.GIT_HUB_VERSION_REQUIREMENT:
				sequence_GitHubVersionRequirement(context, (GitHubVersionRequirement) semanticObject); 
				return; 
			case SemverPackage.HYPHEN_VERSION_RANGE:
				sequence_HyphenVersionRange(context, (HyphenVersionRange) semanticObject); 
				return; 
			case SemverPackage.LOCAL_PATH_VERSION_REQUIREMENT:
				sequence_LocalPathVersionRequirement(context, (LocalPathVersionRequirement) semanticObject); 
				return; 
			case SemverPackage.QUALIFIER:
				sequence_Qualifier(context, (Qualifier) semanticObject); 
				return; 
			case SemverPackage.QUALIFIER_TAG:
				sequence_QualifierTag(context, (QualifierTag) semanticObject); 
				return; 
			case SemverPackage.SIMPLE_VERSION:
				sequence_SimpleVersion(context, (SimpleVersion) semanticObject); 
				return; 
			case SemverPackage.TAG_VERSION_REQUIREMENT:
				sequence_TagVersionRequirement(context, (TagVersionRequirement) semanticObject); 
				return; 
			case SemverPackage.URL_COMMIT_ISH:
				sequence_URLVersionSpecifier(context, (URLCommitISH) semanticObject); 
				return; 
			case SemverPackage.URL_SEMVER:
				sequence_URLSemver(context, (URLSemver) semanticObject); 
				return; 
			case SemverPackage.URL_VERSION_REQUIREMENT:
				sequence_URLVersionRequirement(context, (URLVersionRequirement) semanticObject); 
				return; 
			case SemverPackage.VERSION_NUMBER:
				sequence_VersionNumber(context, (VersionNumber) semanticObject); 
				return; 
			case SemverPackage.VERSION_PART:
				sequence_VersionPart(context, (VersionPart) semanticObject); 
				return; 
			case SemverPackage.VERSION_RANGE_CONSTRAINT:
				sequence_VersionRangeContraint(context, (VersionRangeConstraint) semanticObject); 
				return; 
			case SemverPackage.VERSION_RANGE_SET_REQUIREMENT:
				sequence_VersionRangeSetRequirement(context, (VersionRangeSetRequirement) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     NPMVersionRequirement returns GitHubVersionRequirement
	 *     GitHubVersionRequirement returns GitHubVersionRequirement
	 *
	 * Constraint:
	 *     (githubUrl=URL_NO_VX commitISH=ALPHA_NUMERIC_CHARS?)
	 */
	protected void sequence_GitHubVersionRequirement(ISerializationContext context, GitHubVersionRequirement semanticObject) {
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
			if (transientValues.isValueTransient(semanticObject, SemverPackage.Literals.HYPHEN_VERSION_RANGE__FROM) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SemverPackage.Literals.HYPHEN_VERSION_RANGE__FROM));
			if (transientValues.isValueTransient(semanticObject, SemverPackage.Literals.HYPHEN_VERSION_RANGE__TO) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SemverPackage.Literals.HYPHEN_VERSION_RANGE__TO));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0(), semanticObject.getFrom());
		feeder.accept(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_5_0(), semanticObject.getTo());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NPMVersionRequirement returns LocalPathVersionRequirement
	 *     LocalPathVersionRequirement returns LocalPathVersionRequirement
	 *
	 * Constraint:
	 *     localPath=PATH
	 */
	protected void sequence_LocalPathVersionRequirement(ISerializationContext context, LocalPathVersionRequirement semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, SemverPackage.Literals.LOCAL_PATH_VERSION_REQUIREMENT__LOCAL_PATH) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SemverPackage.Literals.LOCAL_PATH_VERSION_REQUIREMENT__LOCAL_PATH));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathPATHParserRuleCall_1_0(), semanticObject.getLocalPath());
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
	 *     ((preRelease=QualifierTag buildMetadata=QualifierTag?) | buildMetadata=QualifierTag)
	 */
	protected void sequence_Qualifier(ISerializationContext context, Qualifier semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     SimpleVersion returns SimpleVersion
	 *
	 * Constraint:
	 *     (comparators+=VersionComparator* withLetterV?=LETTER_V? number=VersionNumber)
	 */
	protected void sequence_SimpleVersion(ISerializationContext context, SimpleVersion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NPMVersionRequirement returns TagVersionRequirement
	 *     TagVersionRequirement returns TagVersionRequirement
	 *
	 * Constraint:
	 *     tagName=TAG
	 */
	protected void sequence_TagVersionRequirement(ISerializationContext context, TagVersionRequirement semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, SemverPackage.Literals.TAG_VERSION_REQUIREMENT__TAG_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SemverPackage.Literals.TAG_VERSION_REQUIREMENT__TAG_NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getTagVersionRequirementAccess().getTagNameTAGParserRuleCall_0(), semanticObject.getTagName());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     URLVersionSpecifier returns URLSemver
	 *     URLSemver returns URLSemver
	 *
	 * Constraint:
	 *     (withSemverTag?=SEMVER_TAG? simpleVersion=SimpleVersion)
	 */
	protected void sequence_URLSemver(ISerializationContext context, URLSemver semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NPMVersionRequirement returns URLVersionRequirement
	 *     URLVersionRequirement returns URLVersionRequirement
	 *
	 * Constraint:
	 *     (protocol=URL_PROTOCOL url=URL versionSpecifier=URLVersionSpecifier?)
	 */
	protected void sequence_URLVersionRequirement(ISerializationContext context, URLVersionRequirement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     URLVersionSpecifier returns URLCommitISH
	 *
	 * Constraint:
	 *     (commitISH=ALPHA_NUMERIC_CHARS_START_WITH_DIGITS | commitISH=ALPHA_NUMERIC_CHARS)
	 */
	protected void sequence_URLVersionSpecifier(ISerializationContext context, URLCommitISH semanticObject) {
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
	 *     NPMVersionRequirement returns VersionRangeSetRequirement
	 *     VersionRangeSetRequirement returns VersionRangeSetRequirement
	 *
	 * Constraint:
	 *     (ranges+=VersionRange ranges+=VersionRange*)?
	 */
	protected void sequence_VersionRangeSetRequirement(ISerializationContext context, VersionRangeSetRequirement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
