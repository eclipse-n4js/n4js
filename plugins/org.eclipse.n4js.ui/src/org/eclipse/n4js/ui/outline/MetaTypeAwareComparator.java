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
package org.eclipse.n4js.ui.outline;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.SortOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineFilterAndSorter.IComparator;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Inject;

/**
 *
 */
public class MetaTypeAwareComparator implements IComparator {

	@Inject
	private IPreferenceStoreAccess preferenceStoreAccess;

	final static Map<Integer, Integer> METATYPESORTORDER;

	static {
		METATYPESORTORDER = new HashMap<>();
		int[][] tto = {
				{ TypesPackage.TMODULE, 1 },
				{ TypesPackage.TVARIABLE, 2 },
				{ TypesPackage.TFUNCTION, 3 },
				{ TypesPackage.TENUM, 4 },
				{ TypesPackage.TINTERFACE, 5 },
				{ TypesPackage.TCLASS, 6 },
				{ TypesPackage.TFIELD, 7 },
				{ TypesPackage.TGETTER, 7 },
				{ TypesPackage.TSETTER, 7 },
				{ TypesPackage.TMETHOD, 8 }
		};
		for (int[] typeToOrder : tto) {
			METATYPESORTORDER.put(typeToOrder[0], typeToOrder[1]);
		}
	}

	@Override
	public int compare(IOutlineNode o1, IOutlineNode o2) {
		int category1 = getCategory(o1);
		int category2 = getCategory(o2);
		if (category1 != category2)
			return category1 - category2;
		return o1.getText().toString().compareTo(o2.getText().toString());
	}

	@Override
	public boolean isEnabled() {
		return preferenceStoreAccess.getPreferenceStore().getBoolean(SortOutlineContribution.PREFERENCE_KEY);
	}

	private int getCategory(IOutlineNode node) {
		if (node instanceof EObjectNode) {
			EClass eclass = ((EObjectNode) node).getEClass();
			int id = eclass.getClassifierID();
			int key = 10000 + id;
			Integer sortKey = METATYPESORTORDER.get(id);
			if (sortKey != null) {
				key = sortKey * 1000;
			}
			if (node instanceof N4JSEObjectNode) {
				N4JSEObjectNode n4node = (N4JSEObjectNode) node;
				if (!n4node.isStatic) {
					key += 100;
				}
			}
			return key;
		}
		return -1;
	}

}
