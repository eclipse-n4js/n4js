/*
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */

//Mock implementation for React.createElement
module.exports.createElement = function(type, props, ...children) {
	return {type: type, props: props, children: children};
}

//Implement React.Component.constructor as function prototype
var Component = function(props) {
	this.props = props;
}
Component.prototype.constructor = Component;
module.exports.Component = Component;
