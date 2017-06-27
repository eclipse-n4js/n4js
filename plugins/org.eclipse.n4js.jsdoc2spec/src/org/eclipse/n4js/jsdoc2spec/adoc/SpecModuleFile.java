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
package org.eclipse.n4js.jsdoc2spec.adoc;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.jsdoc2spec.CopyrightHeader;
import org.eclipse.n4js.jsdoc2spec.SpecFile;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;

/**
 * A {@link SpecModuleFile} contains {@link SpecSection}s of one module. These {@link SpecSection}s are ordered in the
 * following way:
 * <ol>
 * <li>variables
 * <li>functions
 * <li>interfaces
 * <li>enums
 * <li>classes
 * </ol>
 * Also, within each type, the properties are ordered as follows:
 * <ol>
 * <li>constructor
 * <li>data fields
 * <li>data accessors
 * <li>methods
 * </ol>
 */
public class SpecModuleFile extends SpecFile {
	final private MemberComparator memberComparator = new MemberComparator();
	final private TypeComparator typeComparator = new TypeComparator();

	final private List<SpecIdentifiableElementSection> allSections = new ArrayList<>();
	final private SortedSet<SpecIdentifiableElementSection> variables = new TreeSet<>();
	final private SortedSet<SpecIdentifiableElementSection> functions = new TreeSet<>();
	final private SortedSet<Type> types = new TreeSet<>(typeComparator);

	final private Map<Type, SortedSet<SpecIdentifiableElementSection>> typeElementsOfType = new TreeMap<>(
			typeComparator);

	final private Map<SpecIdentifiableElementSection, Integer> startlineOfElem = new HashMap<>();

	private String newContent;

	/**
	 * Returns the length of the generated headers. This is used when merging all generated module adoc files to one
	 * adoc (the basis for the pdf file) file, since the generated header are not included there.
	 */
	public static int getHeaderLength() {
		String dummyHeader = generateModuleHeader("", "");
		return StringCountUtils.countLines(dummyHeader);
	}

	/**
	 * Constructor for a file
	 */
	public SpecModuleFile(File file) {
		super(file);
	}

	/**
	 * Returns the name of the package, that is, the path of the module except the name of the module file.
	 */
	@Override
	public String getPackageDisplayName() {
		SpecIdentifiableElementSection src = allSections.get(0);
		String packageName = src.getSourceEntry().packageName;
		return packageName;
	}

	/**
	 * Returns the name of the module.
	 */
	public String getModuleName() {
		SpecIdentifiableElementSection src = allSections.get(0);
		String moduleName = src.getSourceEntry().module;
		return moduleName;
	}

	/**
	 * Adds another region change entry to this file.
	 */
	@Override
	public void add(SpecSection specElem) {
		if (!(specElem instanceof SpecIdentifiableElementSection))
			return;

		SpecIdentifiableElementSection specIE = (SpecIdentifiableElementSection) specElem;

		EObject container = specIE.idElement.eContainer();
		if (container instanceof Type) {
			addTypeElement((Type) container, specIE);
			return;
		}

		if (specIE.idElement instanceof TFunction) {
			functions.add(specIE);
			allSections.add(specIE);
			return;
		}

		if (specIE.idElement instanceof TVariable) {
			variables.add(specIE);
			allSections.add(specIE);
			return;
		}

		if (specIE.idElement instanceof Type) {
			addTypeElement((Type) specIE.idElement, specIE);
			return;
		}

		throw new RuntimeException("Missing Implementation");
	}

	private void addTypeElement(Type type, SpecIdentifiableElementSection specIE) {
		types.add(type);

		if (!typeElementsOfType.containsKey(type)) {
			SortedSet<SpecIdentifiableElementSection> containerElements = new TreeSet<>(memberComparator);
			typeElementsOfType.put(type, containerElements);
		}
		SortedSet<SpecIdentifiableElementSection> containerElements = typeElementsOfType.get(type);

		boolean addToList = !containerElements.contains(specIE);
		if (specIE.idElement.eContainer() instanceof TClass) {
			TClass tClass = (TClass) specIE.idElement.eContainer();
			addToList |= tClass.isStaticPolyfill();
		}
		if (addToList) {
			containerElements.add(specIE);
			allSections.add(specIE);
		}
	}

	@Override
	public Collection<SpecIdentifiableElementSection> getSpecSections() {
		return allSections;
	}

	/**
	 * Returns the content of the generated file.
	 */
	@Override
	public String getNewContent() {
		ensureNewContent();

		return newContent;
	}

	/**
	 * Returns the start of the offset of the given {@code entry} in the generated file.
	 */
	@Override
	public int getOffsetStart(SpecSection entry) {
		ensureNewContent();

		if (!startlineOfElem.containsKey(entry))
			throw new RuntimeException("Entry not in change set.");

		int startOffset = startlineOfElem.get(entry);
		return startOffset;
	}

	/**
	 * Returns the end of the offset of the given {@code entry} in the generated file.
	 */
	@Override
	public int getOffsetEnd(SpecSection entry) {
		ensureNewContent();

		if (!startlineOfElem.containsKey(entry))
			throw new RuntimeException("Entry not in change set.");

		return getOffsetStart(entry) + entry.getGeneratedLineCount();
	}

	private String ensureNewContent() {
		if (newContent != null)
			return newContent;

		StringBuilder strb = new StringBuilder();
		int startline = 0;
		startline = generateModuleHeader(strb, startline);
		startline = generateVariableSection(strb, startline);
		startline = generateFunctionSection(strb, startline);

		for (Iterator<Type> it = types.iterator(); it.hasNext();) {
			Type type = it.next();
			startline = generateTypeSection(strb, startline, type);
		}

		newContent = strb.toString();
		return newContent;
	}

	private int generateModuleHeader(StringBuilder strb, int startline) {
		if (variables.isEmpty() && functions.isEmpty() && types.isEmpty())
			return startline;

		String title = getModuleName().replaceAll("/", ".");
		String header = generateModuleHeader(title, getBaseDir());
		strb.append(header);
		int nextStartline = startline + StringCountUtils.countLines(header);
		return nextStartline;
	}

	static private String generateModuleHeader(String title, String basedir) {
		String header = CopyrightHeader.getAdoc();
		header += "include::{find}config.adoc[]\n";
		header += ":docinfodir: " + basedir + "headers/apiModules\n";
		// 'linkattrs' necessary because srclnks are mapped to links with attributes, see SourceLinkPreprocessor
		header += ":linkattrs:\n";
		header += ":toc:\n\n";
		header += "= Module ";
		header += title;
		header += "\n\n";
		return header;
	}

	private int generateVariableSection(StringBuilder strb, int startline) {
		if (variables.isEmpty())
			return startline;

		strb.append("== Variables\n\n");
		startline += 3;
		startline = generateSubElements(strb, startline, variables);
		return startline;
	}

	private int generateFunctionSection(StringBuilder strb, int startline) {
		if (functions.isEmpty())
			return startline;

		strb.append("== Functions\n\n");
		startline += 3;
		startline = generateSubElements(strb, startline, functions);
		return startline;
	}

	private int generateTypeSection(StringBuilder strb, int startline, Type type) {
		if (typeElementsOfType.get(type).isEmpty())
			return startline;

		startline = generateTypeHeader(strb, startline, type);
		SortedSet<SpecIdentifiableElementSection> typeElements = typeElementsOfType.get(type);
		startline = generateSubElements(strb, startline, typeElements);
		return startline;
	}

	private int generateSubElements(StringBuilder strb, int startline,
			SortedSet<SpecIdentifiableElementSection> elems) {

		for (SpecIdentifiableElementSection property : elems) {
			strb.append(property.getGeneratedADocText());
			strb.append("\n");

			startlineOfElem.put(property, startline);
			startline += property.getGeneratedLineCount() + 1;
		}
		return startline;
	}

	private int generateTypeHeader(StringBuilder strb, int startline, Type type) {
		strb.append("== ");
		if (type instanceof TClass)
			strb.append("Class ");
		if (type instanceof TInterface)
			strb.append("Interface ");
		if (type instanceof TEnum)
			strb.append("Enum ");
		strb.append(type.getName());
		strb.append("\n\n");
		return startline + 3;
	}

	private String getBaseDir() {
		SpecIdentifiableElementSection src = allSections.get(0);
		int upLinkDepth = src.getSourceEntry().adocPathElems.length;

		String basedir = "../";
		for (int i = 0; i < upLinkDepth; i++)
			basedir += "../";
		return basedir;
	}

	static private class TypeComparator implements Comparator<Type> {
		@Override
		public int compare(Type t1, Type t2) {
			String name1 = getCompareName(t1);
			String name2 = getCompareName(t2);
			return name1.compareTo(name2);
		}

		private String getCompareName(Type t1) {
			String name = "Z ";
			if (t1 instanceof TInterface)
				name = "A ";
			if (t1 instanceof TEnum)
				name = "B ";
			if (t1 instanceof TClass)
				name = "C ";
			name += t1.getName();
			return name;
		}
	}

	static private class MemberComparator implements Comparator<SpecIdentifiableElementSection> {
		@Override
		public int compare(SpecIdentifiableElementSection m1, SpecIdentifiableElementSection m2) {
			String name1 = getCompareName(m1);
			String name2 = getCompareName(m2);
			return name1.compareTo(name2);
		}

		private String getCompareName(SpecIdentifiableElementSection m) {
			SourceEntry sourceEntry = m.getSourceEntry();

			String name = "Z ";
			if (m.idElement instanceof TN4Classifier || m.idElement instanceof TEnum)
				name = "A ";
			if (m.idElement instanceof TField)
				name = "C ";
			if (m.idElement instanceof FieldAccessor)
				name = "D ";
			if (m.idElement instanceof TMethod)
				name = "E ";
			if (sourceEntry.property.equals("constructor")) // note: constructor is a method
				name = "B ";

			name += sourceEntry.element;
			name += sourceEntry.delimiter;
			name += sourceEntry.property;
			return name;
		}
	}

}
