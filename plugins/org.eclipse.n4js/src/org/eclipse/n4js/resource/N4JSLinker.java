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
package org.eclipse.n4js.resource;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4JS.BindingProperty;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.JSXPropertyAttribute;
import org.eclipse.n4js.n4JS.LabelRef;
import org.eclipse.n4js.n4JS.ModuleRef;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.parser.conversion.AbstractN4JSStringValueConverter.BadEscapementException;
import org.eclipse.n4js.parser.conversion.N4JSValueConverterException;
import org.eclipse.n4js.parser.conversion.N4JSValueConverterWithValueException;
import org.eclipse.n4js.scoping.members.ComposedMemberScope;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.ts.typeRefs.NamespaceLikeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.validation.ASTStructureValidator;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.IDiagnosticConsumer;
import org.eclipse.xtext.diagnostics.IDiagnosticProducer;
import org.eclipse.xtext.linking.impl.ImportedNamesAdapter;
import org.eclipse.xtext.linking.impl.LinkingDiagnosticProducer;
import org.eclipse.xtext.linking.lazy.LazyLinker;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.common.base.Objects;
import com.google.inject.Inject;

/**
 * Clears the transitive fields in the AST (e.g. references to type model element such as N4ClassDefinition) on model
 * changes, and other adaptations in usage of proxy fragment and value converter.
 *
 * Transitive fields should recalculated again after refreshing the resources and their contained EObject descriptions.
 *
 * This implementation uses a more concise proxy fragment than the default lazy linker. Furthermore, it validates the
 * content of the node against the value converter and produces errors for invalid input sequences.
 */
public class N4JSLinker extends LazyLinker {

	/** Custom delimiter to use for encoded URI fragments. */
	public static final char N4JS_CROSSREF_DELIM = '|';

	private static final EReference PARAMETERIZED_TYPE_REF__DECLARED_TYPE = TypeRefsPackage.eINSTANCE
			.getParameterizedTypeRef_DeclaredType();

	@Inject
	private IValueConverterService valueConverterService;

	@Inject
	private ASTStructureValidator structureValidator;

	@Inject
	private N4JSPreProcessor preProcessor;

	/**
	 * Clears the list of encoded URIs in {@link N4JSResource}, installs proxies for all cross references inside the
	 * parsed model. If validation is enabled finally the {@link ASTStructureValidator} is triggered, that checks the
	 * now linked AST structure.
	 */
	@Override
	protected void doLinkModel(final EObject model, final IDiagnosticConsumer consumer) {
		final LinkingDiagnosticProducer producer = new LinkingDiagnosticProducer(consumer);
		getCache().execWithoutCacheClear((N4JSResource) model.eResource(), new IUnitOfWork.Void<N4JSResource>() {

			@Override
			public void process(N4JSResource resource) throws Exception {
				try (Measurement m = N4JSDataCollectors.dcPreProcess.getMeasurement()) {
					doProcess(resource);
				}
			}

			private void doProcess(N4JSResource resource) throws Exception {
				// install lazy linking proxies

				// in .d.ts resources, the Dts*Builders are responsible for installing lazy-linking proxies,
				// so we must skip #clearLazyProxyInformation() and #clearReferences() to avoid losing those
				boolean isDTS = Objects.equal(N4JSGlobals.DTS_FILE_EXTENSION,
						URIUtils.fileExtension(resource.getURI()));
				if (!isDTS) {
					resource.clearLazyProxyInformation();
					clearReferences(model);
				}

				installProxies(resource, model, producer);
				TreeIterator<EObject> iterator = model.eAllContents();
				while (iterator.hasNext()) {
					EObject eObject = iterator.next();
					if (!isDTS) {
						clearReferences(eObject);
					}
					installProxies(resource, eObject, producer);
				}

				// pre-processing of AST
				preProcessor.process(resource.getScript(), resource);

				// AST structure validation
				if (!isDTS && !resource.isValidationDisabled()) {
					getStructureValidator().validate(model, consumer);
				}
			}
		});
	}

	/**
	 * @return the structureValidator
	 */
	private ASTStructureValidator getStructureValidator() {
		return structureValidator;
	}

	/**
	 * Installs only a proxy for EObjects that have a representation in the Xtext document node model.
	 */
	private void installProxies(N4JSResource resource, EObject obj, IDiagnosticProducer producer) {
		ICompositeNode node = NodeModelUtils.getNode(obj);
		if (node == null) {
			return;
		}
		installProxies(resource, obj, producer, node, false);
	}

	/**
	 * Installs proxies for all non containment references and only if the node representing the EObject that contains
	 * the cross reference has got leaf nodes (as a leaf node represents the cross reference).
	 *
	 * @param resource
	 *            the N4JSResource
	 * @param obj
	 *            the EObject containing the cross reference
	 * @param producer
	 *            the error/warning producer
	 * @param parentNode
	 *            the node representing obj inside the node model
	 */
	private void installProxies(N4JSResource resource, EObject obj, IDiagnosticProducer producer,
			ICompositeNode parentNode, boolean dontCheckParent) {
		final EClass eClass = obj.eClass();
		if (eClass.getEAllReferences().size() - eClass.getEAllContainments().size() == 0)
			return;

		for (INode node = parentNode.getFirstChild(); node != null; node = node.getNextSibling()) {
			EObject grammarElement = node.getGrammarElement();
			if (grammarElement instanceof CrossReference && hasLeafNodes(node)) {
				producer.setNode(node);
				CrossReference crossReference = (CrossReference) grammarElement;
				final EReference eRef = GrammarUtil.getReference(crossReference, eClass);
				if (eRef == null) {
					ParserRule parserRule = GrammarUtil.containingParserRule(crossReference);
					final String feature = GrammarUtil.containingAssignment(crossReference).getFeature();
					throw new IllegalStateException("Couldn't find EReference for crossreference '" + eClass.getName()
							+ "::" + feature + "' in parser rule '" + parserRule.getName() + "'.");
				}
				createAndSetProxy(resource, obj, node, eRef, crossReference, producer);
				afterCreateAndSetProxy(obj, node, eRef, crossReference, producer);
			} else if (grammarElement instanceof RuleCall && node instanceof ICompositeNode) {
				RuleCall ruleCall = (RuleCall) grammarElement;
				AbstractRule calledRule = ruleCall.getRule();
				if (calledRule instanceof ParserRule && ((ParserRule) calledRule).isFragment()) {
					installProxies(resource, obj, producer, (ICompositeNode) node, true);
				}
			}
		}
		if (!dontCheckParent && shouldCheckParentNode(parentNode)) {
			installProxies(resource, obj, producer, parentNode.getParent(), dontCheckParent);
		}
	}

	/**
	 * Creates a proxy instance that will later on allow to lazily resolve the semantically referenced instance for the
	 * given {@link CrossReference xref}.
	 */
	@SuppressWarnings("unchecked")
	private void createAndSetProxy(N4JSResource resource, EObject obj, INode node, EReference eRef,
			CrossReference xref,
			IDiagnosticProducer diagnosticProducer) {
		final EObject proxy = createProxy(resource, obj, node, eRef, xref, diagnosticProducer);
		proxy.eSetDeliver(false);
		if (eRef.isMany()) {
			((InternalEList<EObject>) obj.eGet(eRef, false)).addUnique(proxy);
		} else {
			obj.eSet(eRef, proxy);
		}
	}

	/**
	 * Creates the proxy with a custom encoded URI format (starting with "|"). The object used to produce the encoded
	 * URI are collected as tuple inside {@link N4JSResource}. Then the node text is checked if it is convertible to a
	 * valid value. If there is a {@link BadEscapementException} is thrown then there is either a warning or an error
	 * produced via the diagnosticProducer.
	 *
	 *
	 * @param resource
	 *            the N4JSResource
	 * @param obj
	 *            the EObject containing the cross reference
	 * @param node
	 *            the node representing the EObject
	 * @param eRef
	 *            the cross reference in the domain model
	 * @param xref
	 *            the cross reference in the node model
	 * @param diagnosticProducer
	 *            to produce errors or warnings
	 * @return the created proxy
	 */
	private EObject createProxy(N4JSResource resource, EObject obj, INode node, EReference eRef, CrossReference xref,
			IDiagnosticProducer diagnosticProducer) {
		final URI uri = resource.getURI();
		/*
		 * as otherwise with 0 the EObjectDescription created for Script would be fetched
		 */
		final int fragmentNumber = resource.addLazyProxyInformation(obj, eRef, node);
		final URI encodedLink = uri.appendFragment("|" + fragmentNumber);
		EClass referenceType = findInstantiableCompatible(eRef.getEReferenceType());
		final EObject proxy = EcoreUtil.create(referenceType);
		((InternalEObject) proxy).eSetProxyURI(encodedLink);
		AbstractElement terminal = xref.getTerminal();
		if (!(terminal instanceof RuleCall)) {
			throw new IllegalArgumentException(String.valueOf(xref));
		}
		AbstractRule rule = ((RuleCall) terminal).getRule();
		try {
			String tokenText = NodeModelUtils.getTokenText(node);
			Object value = valueConverterService.toValue(tokenText, rule.getName(), node);
			setTokenText(obj, tokenText, value, eRef);
		} catch (BadEscapementException e) {
			diagnosticProducer.addDiagnostic(new DiagnosticMessage(e.getMessage(), e.getSeverity(), e.getIssueCode(),
					Strings.EMPTY_ARRAY));
		} catch (N4JSValueConverterException vce) {
			diagnosticProducer.addDiagnostic(new DiagnosticMessage(vce.getMessage(), vce.getSeverity(),
					vce.getIssueCode(), Strings.EMPTY_ARRAY));
		} catch (N4JSValueConverterWithValueException vcwve) {
			diagnosticProducer.addDiagnostic(new DiagnosticMessage(vcwve.getMessage(), vcwve.getSeverity(),
					vcwve.getIssueCode(), Strings.EMPTY_ARRAY));
		}
		return proxy;
	}

	private void setTokenText(EObject obj, String tokenText, Object value, EReference eRef) {
		/*
		 * as otherwise with 0 the EObjectDescription created for Script would be fetched
		 */
		if (obj instanceof IdentifierRef && value instanceof String) {
			((IdentifierRef) obj).setIdAsText((String) value);
		} else if (obj instanceof ParameterizedTypeRef && value instanceof String) {
			ParameterizedTypeRef ptr = (ParameterizedTypeRef) obj;
			String valueStr = (String) value;
			if (eRef == PARAMETERIZED_TYPE_REF__DECLARED_TYPE) {
				ptr.setDeclaredTypeAsText(valueStr);
			}
			// Type typeProxy = (Type) proxy;
			// typeProxy.setName(valueStr);

		} else if (obj instanceof NamespaceLikeRef && value instanceof String) {
			((NamespaceLikeRef) obj).setDeclaredTypeAsText((String) value);
		} else if (obj instanceof LabelRef && value instanceof String) {
			((LabelRef) obj).setLabelAsText((String) value);
		} else if (obj instanceof ParameterizedPropertyAccessExpression && value instanceof String) {
			((ParameterizedPropertyAccessExpression) obj).setPropertyAsText((String) value);
		} else if (obj instanceof BindingProperty && value instanceof String) {
			((BindingProperty) obj).setPropertyAsText((String) value);
		} else if (obj instanceof PropertyNameValuePair) {
			((PropertyNameValuePair) obj).setPropertyAsText((String) value);
		} else if (obj instanceof ModuleRef && value instanceof String) {
			((ModuleRef) obj).setModuleSpecifierAsText((String) value);
		} else if (obj instanceof NamedImportSpecifier && value instanceof String) {
			((NamedImportSpecifier) obj).setImportedElementAsText((String) value);
		} else if ((obj instanceof JSXPropertyAttribute) && (value instanceof String)) {
			((JSXPropertyAttribute) obj).setPropertyAsText((String) value);
		} else {
			setOtherElementAsText(tokenText, obj, value);
		}
	}

	/**
	 * Extension point for subclasses for providing further methods for setting elements as text.
	 *
	 *
	 * @param tokenText
	 *            The token text of the node
	 * @param obj
	 *            the object whose string as text should be set
	 * @param value
	 *            the value to be set
	 */
	protected void setOtherElementAsText(String tokenText, EObject obj, Object value) {
		// Do nothing, subclasses can add further setElementAsText here
	}

	@Override
	protected void clearReferences(EObject obj) {
		super.clearReferences(obj);
		if (obj instanceof Script) {
			((Script) obj).setFlaggedUsageMarkingFinished(false); // open transient flag for new used-resolutions
		} else if (obj instanceof ModuleRef) {
			((ModuleRef) obj).setModuleSpecifierAsText(null);
		} else if (obj instanceof ImportSpecifier) {
			ImportSpecifier specifier = (ImportSpecifier) obj;
			specifier.setFlaggedUsedInCode(false); // clear transient
		} else if (obj instanceof NamedImportSpecifier) {
			((NamedImportSpecifier) obj).setImportedElementAsText(null);
		} else if (obj instanceof IdentifierRef) {
			((IdentifierRef) obj).setIdAsText(null);
			((IdentifierRef) obj).setOriginImport(null);
		} else if (obj instanceof LabelRef) {
			((LabelRef) obj).setLabelAsText(null);
		} else if (obj instanceof ParameterizedPropertyAccessExpression) {
			((ParameterizedPropertyAccessExpression) obj).setPropertyAsText(null);
		} else if (obj instanceof BindingProperty) {
			((BindingProperty) obj).setPropertyAsText(null);
		} else if (obj instanceof PropertyNameValuePair) {
			((PropertyNameValuePair) obj).setPropertyAsText(null);
		} else if (obj instanceof ParameterizedTypeRef) {
			((ParameterizedTypeRef) obj).setDeclaredTypeAsText(null);
		} else if (obj instanceof NamespaceLikeRef) {
			((NamespaceLikeRef) obj).setDeclaredTypeAsText(null);
		}
	}

	/**
	 * Removes the imported names before linking and cleans other caches attached to the AST.
	 */
	@Override
	protected void beforeModelLinked(EObject model, IDiagnosticConsumer diagnosticsConsumer) {
		ImportedNamesAdapter adapter = ImportedNamesAdapter.find(model.eResource());
		if (adapter != null)
			adapter.clear();

		ComposedMemberScope.clearCachedComposedMembers(model);
	}

	/**
	 * The node itself has content or if it is a composite node its last child node is a leaf (i.e. this child node has
	 * no further child nodes).
	 */
	@Override
	protected boolean hasLeafNodes(INode node) {
		if (node.getTotalLength() > 0)
			return true;
		if (node instanceof ICompositeNode) {
			return ((ICompositeNode) node).getLastChild() instanceof ILeafNode;
		}
		return false;
	}

	/**
	 * Later here have to be checked the inheritance hierarchy, too. Currently only the type itself is accepted as type
	 * for the cross reference. But the cross reference maybe also typed with a super type of the given type.
	 */
	@Override
	protected EClass findInstantiableCompatible(EClass eType) {
		if (!isInstantiatableSubType(eType, eType)) {
			throw new IllegalStateException(String.valueOf(eType));
			// TODO: check local Package
			// EPackage ePackage = eType.getEPackage();
			// EClass eClass = findSubTypeInEPackage(ePackage, eType);
			// if (eClass != null)
			// return eClass;
			// return globalFindInstantiableCompatible(eType);
		}
		return eType;
	}

	private boolean isInstantiatableSubType(EClass c, EClass superType) {
		return !c.isAbstract() && !c.isInterface() && EcoreUtil2.isAssignableFrom(superType, c);
	}
}
