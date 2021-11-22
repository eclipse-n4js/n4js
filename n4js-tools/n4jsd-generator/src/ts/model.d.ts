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
	args: (string|TypeRef)[];

	constructor(name?: string);
}

export interface AnnotatableElement {
	annotations: Annotation[];
}

export class Script {
	tsFileName: string;
	tsFilePath: string;
	mode: DTSMode;
	preamble?: string;
	imports: Import[];
	topLevelElements: ExportableElement[];
	/** If defined, all other properties will be ignored and this string will be emitted. */
	replacementCode?: string;
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
	jsdoc?: string;
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

export interface GenericElement {
	typeParams: TypeParameter[];
}

export class TypeParameter extends NamedElement {
	upperBound?: TypeRef;
	defaultArgument?: TypeRef;
}

export class Type extends ExportableElement implements AnnotatableElement, GenericElement {
	kind: TypeKind;
	defSiteStructural?: boolean;
	primitiveBased?: PrimitiveBasedKind;
	extends: TypeRef[];
	implements: TypeRef[];
	members: Member[];
	literals: EnumLiteral[];
	aliasedType: TypeRef;
	additionalCode: string[];

	// from interfaces:
	annotations: Annotation[];
	typeParams: TypeParameter[];
}

export class EnumLiteral extends NamedElement {
	value?: string | number;
}

export enum MemberKind {
	CTOR,
	CALL_SIGNATURE, CONSTRUCT_SIGNATURE, INDEX_SIGNATURE,
	FIELD, GETTER, SETTER, METHOD
}

export class Member extends NamedElement implements AnnotatableElement {
	kind: MemberKind;
	accessibility: Accessibility;
	isStatic: boolean;
	isOptional: boolean;
	/** Will be defined iff this member is a data field or field accessor. */
	type?: TypeRef;
	signatures?: Signature[];
	replacementCode?: string;

	// from interfaces:
	annotations: Annotation[];
}

export class Signature implements GenericElement {
	parameters: Parameter[];
	/** Will be undefined iff this signature belongs to a constructor. */
	returnType?: TypeRef;

	// from interfaces:
	typeParams: TypeParameter[];
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
	/** In this case, TypeRef#targetTypeName is either "Iterable*" or "Array*" and TypeRef#targetTypeArgs define the element types. */
	TUPLE,
	UNION,
	INTERSECTION,
	PARENTHESES,
	// the following are not actually supported on N4JS side:
	TYPE_PREDICATE,
	TYPE_QUERY,
	INDEXED_ACCESS_TYPE,
	MAPPED_TYPE
}

export enum TypeRefOperator {
	KEYOF,
	UNIQUE,
	READONLY
}

export class TypeRef implements AnnotatableElement { // note: annotations only supported for TypeRefKind === FUNCTION
	kind: TypeRefKind;
	dynamic: boolean;
	targetTypeName: string;
	targetTypeArgs: TypeRef[];
	signature: Signature;
	members: Member[];
	composedTypeRefs: TypeRef[];
	parenthesizedTypeRef: TypeRef;
	/** The type operators that preceded this type reference on the TypeScript side. */
	tsOperators: TypeRefOperator[];
	/** The type reference as given in the TypeScript source code. */
	tsSourceString: string;

	// from interfaces:
	annotations: Annotation[];

	public isBuiltInUndefined(): boolean;
	public isComposed(): boolean;
}

export function chooseSignature(signatures: Signature[]): [boolean, Signature];
export function getSymbolNameFromPropertyName(propertyName: string): string;
export function scriptToString(script: Script): string;
export function createUndefined(): TypeRef;
export function createAnyPlus(): TypeRef;
