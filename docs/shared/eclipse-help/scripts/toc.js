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

$( "button#collapsetoc" ).click(function () {
    $(this).toggleClass("inactive");
     if ( $( this ).hasClass( "inactive" ) ) {
        $('.toc2 > ul').treeView('expandAll');
        /*$(this).css("background-image", "collapse.png");*/
    }
    else {
        $('.toc2 > ul').treeView('collapseAll');
        /*$(this).css("background-image", "expand.png");*/
    }
});

// simulate click on parent element for 'a' elements in TOC
$( ".toc2 a" ).click(function () {
    $(this).parent('li').trigger('click');
});

$(window).resize(function() {
    if ($(window).width() < 768) {
        $( "#header, #content, #footnotes, #footer, #links" ).animate({left: '40px'},"slow");
        $( "body.toc2").css("width", "90%");
    }
    else if ($("#tocbutton").css('left') == '270px') {
            $( "#header, #content, #footnotes, #footer, #links" ).animate({left: '260px'},"slow");
            $( "body.toc2").css("width", "80%");
    }
});

function search() {
    // Declare variables
    var input, filter, li, a, i;
    input = document.getElementById('pagesearch');
    filter = input.value.toUpperCase();
    li = document.getElementById("toclist").getElementsByTagName('li');
    $('.toc2 > ul').treeView('expandAll');
    // Loop through all list items, and hide those who don't match the search query
    for (i = 0; i < li.length; i++) {
        //a = li[i].getElementsByTagName("a")[0];
        if (li[i].innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "block";
            $(li[i]).addClass("found");
        } else {
            $(li[i]).removeClass("found");
            li[i].style.display = "none";
        }
    }
}
