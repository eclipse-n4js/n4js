/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */

import * as utils from "./utils";

export enum DTSMode {
	NONE,
	MODULE,
	LEGACY
}

export enum Accessibility {
	PUBLIC,
	PROTECTED,
	PRIVATE
}

export class Annotation {
	name: string;
}

export interface AnnotatableElement {
	annotations: Annotation[];
}

export class Script {
	mode: DTSMode;
	preamble?: string;
	imports: Import[];
	topLevelElements: ExportableElement[];
	issues: utils.Issue[];
}

export abstract class Import {
	moduleSpecifier: string;
}

export class DefaultImport extends Import {
	localName: string;
}

export class NamespaceImport extends Import {
	namespaceName: string;
}

export class NamedImport extends Import {
	importedElementName: string;
	aliasName?: string;
}

export abstract class NamedElement {
	name: string;
}

export abstract class ExportableElement extends NamedElement {
	exported: boolean;
	exportedAsDefault: boolean;
}

export enum VariableKeyword {
	VAR, LET, CONST
}

export class Variable extends ExportableElement {
	keyword: VariableKeyword;
	type: TypeRef;
}

export class Function extends ExportableElement {
	signatures: Signature[];
}

export enum TypeKind {
	INTERFACE, CLASS, ENUM, TYPE_ALIAS
}

export enum PrimitiveBasedKind {
	STRING_BASED, NUMBER_BASED
}

export class Type extends ExportableElement implements AnnotatableElement {
	annotations: Annotation[];
	kind: TypeKind;
	defSiteStructural?: boolean;
	primitiveBased?: PrimitiveBasedKind;
	typeParams: string[];
	extends?: TypeRef;
	members: Member[];
	literals: EnumLiteral[];
	aliasedType: TypeRef;
}

export class EnumLiteral extends NamedElement {
	value?: string | number;
}

export enum MemberKind {
	CTOR, CALLABLE_CTOR, FIELD, GETTER, SETTER, METHOD
}

export class Member extends NamedElement implements AnnotatableElement {
	annotations: Annotation[];
	kind: MemberKind;
	accessibility: Accessibility;
	isStatic: boolean;
	typeParams: string[];
	/** Will be defined iff this member is a data field or field accessor. */
	type?: TypeRef;
	signatures?: Signature[];
}

export class Signature {
	parameters: Parameter[];
	/** Will be undefined iff this signature belongs to a constructor. */
	returnType?: TypeRef;
}

export class Parameter extends NamedElement {
	type: TypeRef;
	isOptional: boolean;
	isVariadic: boolean;
}

export enum TypeRefKind {
	/** Referring to a type by name or alias. Corresponds to ParameterizedTypeRef in N4JS. */
	NAMED,
	LITERAL,
	FUNCTION,
	OBJECT,
	THIS,
	UNION,
	INTERSECTION,
	PARENTHESES
}

export class TypeRef {
	kind: TypeRefKind;
	dynamic: boolean;
	targetTypeName: string;
	targetTypeArgs: TypeRef[];
	signature: Signature;
	members: Member[];
	composedTypeRefs: TypeRef[];
	parenthesizedTypeRef: TypeRef;
	/** The type reference as given in the TypeScript source code. */
	tsSourceString: string;

	public isComposed(): boolean;
}

export function scriptToString(script: Script): string;
export function createAnyPlus(): TypeRef;
