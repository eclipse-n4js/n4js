/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.jsdoc2spec;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.FullMemberReference;
import org.eclipse.n4js.jsdoc2spec.adoc.RepoRelativePathHolder;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.scoping.N4JSGlobalScopeProvider;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Basically a multi map from names to spec infos. Instead of directly using a map we introduced this helper class to
 * controll access to the map.
 */
public class SpecInfosByName {

	private final Multimap<String, SpecInfo> specInfoByName = HashMultimap.create();
	private final IJSDoc2SpecIssueAcceptor issueAcceptor;
	private final ContainerTypesHelper containerTypesHelper;
	private final N4JSGlobalScopeProvider globalScopeProvider;
	private final IN4JSCore n4jsCore;

	/**
	 * @param issueAcceptor
	 *            injected in or added to creator and passed here to avoid DI for this helper type
	 * @param globalScopeProvider
	 *            injected in creator and passed here to avoid DI for this helper type
	 * @param containerTypesHelper
	 *            injected in creator and passed here to avoid DI for this helper type
	 * @param n4jsCore
	 *            injected in creator and passed here to avoid DI for this helper type
	 */
	SpecInfosByName(IJSDoc2SpecIssueAcceptor issueAcceptor, N4JSGlobalScopeProvider globalScopeProvider,
			ContainerTypesHelper containerTypesHelper, IN4JSCore n4jsCore) {
		this.issueAcceptor = issueAcceptor;
		this.globalScopeProvider = globalScopeProvider;
		this.containerTypesHelper = containerTypesHelper;
		this.n4jsCore = n4jsCore;
	}

	void createTypeSpecInfo(Type type, RepoRelativePathHolder rrph) {
		SpecInfo typeInfo = new SpecInfo(type);
		String regionName = KeyUtils.getSpecKeyWithoutProjectFolder(rrph, type);
		specInfoByName.put(regionName, typeInfo);

		Collection<SpecInfo> identicalSpecInfo = specInfoByName.get(regionName);
		if (identicalSpecInfo.size() > 1) {
			SpecInfo polyfillAware = null;
			List<SpecInfo> polyfilling = new LinkedList<>();
			for (SpecInfo si : identicalSpecInfo) {
				Type moduleType = si.specElementRef.getElementAsType();

				if (moduleType != null) {
					TModule typeModule = moduleType.getContainingModule();
					if (typeModule.isStaticPolyfillModule()) {
						polyfilling.add(si);
					} else if (typeModule.isStaticPolyfillAware()) {
						polyfillAware = si;
					}
				}
			}

			if (polyfillAware != null) {
				Type polyfillAwareType = polyfillAware.specElementRef.getElementAsType();
				for (SpecInfo si : polyfilling) {
					si.specElementRef.polyfillAware = polyfillAwareType;
				}
			}
		}
	}

	void createTVarSpecInfo(TVariable tvar, RepoRelativePathHolder rrph) {
		String name = KeyUtils.getSpecKeyWithoutProjectFolder(rrph, tvar);
		specInfoByName.put(name, new SpecInfo(tvar));
	}

	/**
	 * Adds test info to an identified element.
	 */
	void addTestInfoForCodeElement(RepoRelativePath rrpOfTest, Doclet testMethodDoclet, FullMemberReference ref,
			TMember testMember) {

		RepoRelativePath rrpOfTestee = computeRRP(ref, testMember);

		String fullTypeName = ref.fullTypeName();
		String regionName = KeyUtils.getSpecKeyWithoutProjectFolder(rrpOfTestee, fullTypeName);
		Collection<SpecInfo> specInfos = specInfoByName.get(regionName);

		boolean testeeMemberFound = false;
		for (SpecInfo specInfo : specInfos) {
			for (Type testee : specInfo.specElementRef.getTypes()) {
				if (testee instanceof ContainerType<?> && ref.memberNameSet()) {
					TMember testeeMember = getRefMember((ContainerType<?>) testee, ref);
					if (testeeMember != null) {
						String testeeName = testeeMember.getName();
						SpecTestInfo testSpecInfo = createTestSpecInfo(testeeName, testMethodDoclet, testMember,
								rrpOfTest);
						specInfo.addMemberTestInfo(testeeMember, testSpecInfo);
					}
					testeeMemberFound = true;
				}
			}
		}

		if (!testeeMemberFound) {
			for (SpecInfo specInfo : specInfos) {
				// Type, TFunction of TVariable
				String elementName = specInfo.specElementRef.identifiableElement.getName();
				SpecTestInfo testSpecInfo = createTestSpecInfo(elementName, testMethodDoclet, testMember, rrpOfTest);
				specInfo.addTypeTestInfo(testSpecInfo);
			}

			if (specInfos.isEmpty()) {
				issueAcceptor.addWarning("Testee " + fullTypeName + " not found", testMember);
			}
		}
	}

	private RepoRelativePath computeRRP(FullMemberReference ref, TMember testMember) {
		IScope scope = globalScopeProvider.getScope(testMember.eResource(),
				N4JSPackage.Literals.IMPORT_DECLARATION__MODULE);
		QualifiedName qn = QualifiedName.create(ref.getModuleName().split("/"));
		IEObjectDescription eod = scope.getSingleElement(qn);
		if (eod != null) {
			FileURI uri = new FileURI(eod.getEObjectURI());
			RepoRelativePath rrp = RepoRelativePath.compute(uri, n4jsCore);
			return rrp;
		} else {
			issueAcceptor.addWarning("Cannot resolve testee " + ref, testMember);
			return null;
		}

	}

	private SpecTestInfo createTestSpecInfo(String testeeName, Doclet doclet, TMember testMember,
			RepoRelativePath rrpOfTest) {

		String moduleName = testMember.getContainingModule().getModuleSpecifier();
		String typeName = testMember.getContainingType().getName();
		String memberName = testMember.getName();
		QualifiedName qualifiedName = QualifiedName.create(moduleName, typeName, memberName);
		RepoRelativePath repoRelPath = rrpOfTest != null ? rrpOfTest.withLine(testMember) : null;
		return new SpecTestInfo(testeeName, qualifiedName, doclet, repoRelPath);
	}

	// TODO fqn of getter vs setter, fqn of static vs instance
	private TMember getRefMember(ContainerType<?> ct, FullMemberReference ref) {
		TMember member = null;
		String memberName = ref.getMemberName();
		boolean _static = ref.isStaticMember();
		MemberCollector memberCollector = containerTypesHelper.fromContext(ct);
		member = memberCollector.findMember(ct, memberName, false, _static);

		if (member == null) {
			member = memberCollector.findMember(ct, memberName, false, !_static);
			if (member == null) {
				member = memberCollector.findMember(ct, memberName, true, _static);
				if (member == null) {
					member = memberCollector.findMember(ct, memberName, true, !_static);
				}
			}
		}
		return member;

	}

	void addTestInfoForRequirement(RepoRelativePath rrp, Doclet testMethodDoclet, String reqid,
			TMember testMember) {

		Collection<SpecInfo> specInfos = specInfoByName.get(SpecElementRef.reqidKey(reqid));
		if (specInfos.isEmpty()) {
			SpecInfo specInfo = new SpecInfo(reqid);
			specInfoByName.put(SpecElementRef.reqidKey(reqid), specInfo);
		}
		for (SpecInfo specInfo : specInfos) {
			specInfo.addTypeTestInfo(createTestSpecInfo(reqid, testMethodDoclet, testMember, rrp));
		}
	}

	/**
	 * @return all spec infos, may contain duplicates, i.e. multiple spec infos for the same name.
	 */
	Collection<SpecInfo> values() {
		return specInfoByName.values();
	}
}
