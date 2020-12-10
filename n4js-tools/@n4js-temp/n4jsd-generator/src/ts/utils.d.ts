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

export const PACKAGE_JSON: string;

export enum IssueKind {
	WARNING, ERROR
}

export class Issue {
	kind: IssueKind;
	message: string;
 
	constructor(msg: string, kind?: IssueKind)
}
export function error(msg: string): Issue
export function warning(msg: string): Issue

export function resolveGlobPattern(pathWithGlob: string): string[];

export function pushAll<T>(arr: T[], elemsToPush: T[]): void;

export function testFlagsOR(value: number, ...flags: number[]): boolean
export function testFlagsAND(value: number, ...flags: number[]): boolean
export function testFlag(value: number, flag: number): boolean
