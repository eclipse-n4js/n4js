/*

The MIT License (MIT)

Copyright (c) 2015 Samarjeet Singh

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

*/

// Source - https://github.com/samarjeet27/TreeViewJS

(function ($){
    // doesn't work without jquery
    if (!$) return;
    // treeView
    function treeView($me) {
        // add treeview class name if not present
        $me.addClass('treeview');
        // collapsable elements i.e. the li with a ul in it
        var $collapse = $me.find('li>ul').parent();
        // generate tree from data
        function generateTree(data, $root, options) {
            // create a node from a node object
            function createNode(nObj, $target) {
                var li = $('<li>').appendTo($target);
                // node icons require using a span element
                var useSpan = options.useSpan || options.imageList.length > 0;
                if (useSpan) {
                    li.append($('<span>').text(nObj.label));
                } else {
                    li.text(nObj.label);
                }
                if(options.imageList.length > 0){
                    // the image
                    var image = 'url('+options.imageList[nObj.imageIndex]+')';
                    // requires using span
                    var $span = li.find('span');
                    // indicates that it has a node image
                    $span.addClass('has-node-icon');
                    $span.css('background-image', image);
                }
                if (nObj.children != undefined && nObj.children.length > 0) {
                    var innerList = $('<ul>').appendTo(li);
                    for (var i = 0; i < nObj.children.length; i++) {
                        var child = nObj.children[i];
                        createNode(child, innerList);
                    };
                }
                
                return li;
            }
            for (var i = 0; i < data.length; i++) {
                createNode(data[i], $root);
            }
        }

        return {
            //initialize control
            init: function (data) {
                // handle undefined error
                data = data || { };

                // default optoins
                var defaults = {
                    model: null, // treeview data model
                    useSpan: false, // use <span> to build model
                    imageList: [], // add icons to nodes
                    // ajax: null, TODO: load data using ajax
                    expanded: false // the tree is expanded
                };
                // configuration
                var options = { };
                
                if (typeof data.concat != 'undefined') {
                    // concat is an array method, thus checks if data is array
                    // typeof array returns object otherwise
                    defaults.model = data;
                    // data has model only, which is transferred to defaults.model
                    // prevents wrong merge in $.extend
                    data = null;
                }
                // merge options
                options = $.extend(defaults, data);

                if (options.model != null) {
                    // generate the tree
                    generateTree(options.model, $me, options);
                    // re assign var value for new dom structure
                    $collapse = $me.find('li>ul').parent();
                }
                // all the collapsable items which have something
                $collapse.addClass('contains-items');
                // user config
                if (options.expanded){
                    $collapse.addClass('items-expanded')
                } else {
                    $me.find('ul').css('display', 'none');
                }
                // expand items which have something
                $me.find('.contains-items').on('click', function (event) {
                    if ($(event.target).hasClass('contains-items')){
                        // expand icon
                        $(this).toggleClass('items-expanded');
                        // the inner list
                        var $a = $(this).find('>ul');
                        // slide effect
                        $a.slideToggle();
                        // stop propagation of inner elements
                        event.stopPropagation();
                    }
                });
            },
            // expand all items
            expandAll: function() {
                var items = $me.find('.contains-items');
                items.find('ul').slideDown();
                items.addClass('items-expanded');
            },
            // collapse all items
            collapseAll: function() {
                var items = $me.find('.contains-items');
                items.find('ul').slideUp();
                items.removeClass('items-expanded');
            }
        }
    }
    // treeView jQuery plugin
    $.fn.treeView = function(options) {
        // if it's a function arguments
        var args = (arguments.length > 1) ? Array.prototype.slice.call(arguments, 1) : undefined;
        // all the elements by selector
        return this.each(function () {
            var instance = new treeView($(this));
            if ( instance[options] ) {
                // has requested method
                return instance[options](args);
            } else {
                // no method requested, so initialize
                instance.init(options);
            }
        });
    }

})(window.jQuery);