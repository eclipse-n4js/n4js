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

 
// Create a back to top button
$('body').prepend('<a href="#" class="back-to-top">Back to Top</a>');
var amountScrolled = 300;
$(window).scroll(function() {
if ( $(window).scrollTop() > amountScrolled ) {
    $('a.back-to-top').fadeIn('slow');
} else {
    $('a.back-to-top').fadeOut('slow');
}
});
$('a.back-to-top, a.simple-back-to-top').click(function() {
$('html, body').animate({
    scrollTop: 0
}, 700);
return false;
});
