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
package org.eclipse.n4js.ide.tests.rename;

import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.AbstractRenameTest;
import org.junit.Test;

/**
 * Tests to ensure that various AST nodes can be renamed.
 */

public class RenameReactPropsTest extends AbstractRenameTest {
	/** this is a poor-mans fake of React to enable the use of react in MyReactModule.n4jsx */
	static final String reactIndexN4JSX_Name = CFG_NODE_MODULES + "react" + CFG_SRC + "index.n4jsd";
	static final String reactIndexN4JSX_Content = """
				export external public abstract class ReactElement {
				    private constructor();
				};

				export external public interface ~ComponentProps {
				    public children?: Array<ReactElement>;
				    public ref?: {function(Object /* node */): void};
				    public key?: string;
				}

				export external public abstract class Component<PropsT extends ComponentProps, StateT extends Object> {
				    public get props(): PropsT;
				    public state: StateT;
				    public context: Object+;
				    public get refs(): Object+;
				    public static name: Object+;
				    public abstract render(): ReactElement;
				    @CovariantConstructor
				    public constructor(props: PropsT);
				}

				export external public function createElement(
				    type: union{string, {function(Object =): ReactElement}, constructor{Component}},
				    props: Object =,
				    ...children: union{string, ReactElement, Array<ReactElement>}): ReactElement;

				@Final
				export external public class Fragment extends Component<ComponentProps, Object> {
				    @Override
				    public render(): ReactElement;
				}
			""";

	@Test
	public void testParameterizedTypeRef_atMember() {
		testAtCursors(Map.of(
				"MyReactModule.n4jsx", """
							import * as React from "react";

							class MyProps implements React.ComponentProps {
							    public my<|>Field : string;
							}

							class MyComp extends React.Component<MyProps, Object> {
							    @Override
							    public render() : React.ReactElement { return null; }
							}

							let c = <MyComp myField="test"/>;
						""",
				reactIndexN4JSX_Name, reactIndexN4JSX_Content),
				"newFieldName",
				Map.of("MyReactModule.n4jsx", """
							import * as React from "react";

							class MyProps implements React.ComponentProps {
							    public newFieldName : string;
							}

							class MyComp extends React.Component<MyProps, Object> {
							    @Override
							    public render() : React.ReactElement { return null; }
							}

							let c = <MyComp newFieldName="test"/>;
						"""),
				null);
	}

	@Test
	public void testParameterizedTypeRef_atReactProp() {
		testAtCursors(Map.of(
				"MyReactModule.n4jsx", """
							import * as React from "react";

							class MyProps implements React.ComponentProps {
							    public myField : string;
							}

							class MyComp extends React.Component<MyProps, Object> {
							    @Override
							    public render() : React.ReactElement { return null; }
							}

							let c = <MyComp my<|>Field="test"/>;
						""",
				reactIndexN4JSX_Name, reactIndexN4JSX_Content),
				"newFieldName",
				Map.of("MyReactModule.n4jsx", """
							import * as React from "react";

							class MyProps implements React.ComponentProps {
							    public newFieldName : string;
							}

							class MyComp extends React.Component<MyProps, Object> {
							    @Override
							    public render() : React.ReactElement { return null; }
							}

							let c = <MyComp newFieldName="test"/>;
						"""),
				null);
	}

}
