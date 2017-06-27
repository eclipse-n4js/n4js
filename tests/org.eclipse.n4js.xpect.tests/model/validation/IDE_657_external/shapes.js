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

module.exports = {
	"Point": function Point(x, y) {
 	this.x = x;
 	this.y = y;
},

"Circle": function Circle(center, radius) {
	this.center = center;
 	this.radius = radius;
}
