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
package org.eclipse.n4js.tests.parser

import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.NumericLiteral
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.junit.Test

class LookAheadTest extends AbstractParserTest{

	@Test
	def void testNumber() {
		val script = '''
			1
			2
			3
		'''.parse

		assertEquals(3, script.scriptElements.size)
		
		val stmt = script.scriptElements.head as ExpressionStatement
		val expr = stmt.expression as NumericLiteral
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(1, node.lookAhead)
		
		val stmt2 = script.scriptElements.last as ExpressionStatement
		val expr2 = stmt2.expression as NumericLiteral
		
		val node2 = NodeModelUtils.getNode(expr2)
		
		assertEquals(1, node2.lookAhead)
	}
	
	@Test
	def void testNumberWithSemi() {
		val script = '''
			1;
			2;
			3
		'''.parse

		assertEquals(3, script.scriptElements.size)
		
		val stmt = script.scriptElements.head as ExpressionStatement
		val expr = stmt.expression as NumericLiteral
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(1, node.lookAhead)
		
		val stmt2 = script.scriptElements.last as ExpressionStatement
		val expr2 = stmt2.expression as NumericLiteral
		
		val node2 = NodeModelUtils.getNode(expr2)
		
		assertEquals(1, node2.lookAhead)
	}
	
	// identifier are part of a predicate that tries to find a ':' -> LA(2)
	
	@Test
	def void testIdentifierWithSemi() {
		val script = '''
			// comment 
			myIdent
			/* 
			  ml comment 
			  spannign multiple lines
			*/
			;
		'''.parse

		assertEquals(1, script.scriptElements.size)
		
		val stmt = script.scriptElements.head as ExpressionStatement
		val expr = stmt.expression as IdentifierRef
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(2, node.lookAhead)
		node.assertParentLookAhead
	}
	
	@Test
	def void testIdentifier() {
		val script = '''
			/*comment*/myIdent
		'''.parse

		assertEquals(1, script.scriptElements.size)
		
		val stmt = script.scriptElements.head as ExpressionStatement
		val expr = stmt.expression as IdentifierRef
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(2, node.lookAhead)
		node.assertParentLookAhead
	}
	
	@Test
	def void testIdentifierAfterImport_01() {
		val script = '''
			import * from "";
			myIdent
		'''.parse

		assertEquals(2, script.scriptElements.size)
		
		val stmt = script.scriptElements.last as ExpressionStatement
		val expr = stmt.expression as IdentifierRef
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(2, node.lookAhead)
		node.assertParentLookAhead
	}
	
	@Test
	def void testIdentifierAfterImport_02() {
		val script = '''
			import * from ""
			myIdent
		'''.parse

		assertEquals(2, script.scriptElements.size)
		
		val stmt = script.scriptElements.last as ExpressionStatement
		val expr = stmt.expression as IdentifierRef
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(2, node.lookAhead)
		node.assertParentLookAhead
	}
	
	@Test
	def void testIdentifierWithSemiAfterImport_01() {
		val script = '''
			import * from "";
			myIdent;
		'''.parse

		assertEquals(2, script.scriptElements.size)
		
		val stmt = script.scriptElements.last as ExpressionStatement
		val expr = stmt.expression as IdentifierRef
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(2, node.lookAhead)
		node.assertParentLookAhead
	}
	
	@Test
	def void testIdentifierWithSemiAfterImport_02() {
		val script = '''
			import * from ""
			myIdent;
		'''.parse

		assertEquals(2, script.scriptElements.size)
		
		val stmt = script.scriptElements.last as ExpressionStatement
		val expr = stmt.expression as IdentifierRef
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(2, node.lookAhead)
		node.assertParentLookAhead
	}
	
	@Test
	def void testIdentifierWithSemiAfterImport_03() {
		val script = '''
			import * as x from ""
			myIdent;
		'''.parse

		assertEquals(2, script.scriptElements.size)
		
		val stmt = script.scriptElements.last as ExpressionStatement
		val expr = stmt.expression as IdentifierRef
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(2, node.lookAhead)
		node.assertParentLookAhead
	}
	
	@Test
	def void testIdentifierWithSemiAfterImport_04() {
		val script = '''
			import x from ""
			myIdent;
		'''.parse

		assertEquals(2, script.scriptElements.size)
		
		val stmt = script.scriptElements.last as ExpressionStatement
		val expr = stmt.expression as IdentifierRef
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(2, node.lookAhead)
		node.assertParentLookAhead
	}
	
	@Test
	def void testIdentifierWithSemiAfterImport_05() {
		val script = '''
			import ""
			myIdent;
		'''.parse

		assertEquals(2, script.scriptElements.size)
		
		val stmt = script.scriptElements.last as ExpressionStatement
		val expr = stmt.expression as IdentifierRef
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(2, node.lookAhead)
		node.assertParentLookAhead
	}
	
	@Test
	def void testIdentifierWithSemiAfterImport_06() {
		val script = '''
			import x, * as a from ""
			myIdent;
		'''.parse

		assertEquals(2, script.scriptElements.size)
		
		val stmt = script.scriptElements.last as ExpressionStatement
		val expr = stmt.expression as IdentifierRef
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(2, node.lookAhead)
		node.assertParentLookAhead
	}
	
	@Test
	def void testIdentifierWithSemiAfterImport_07() {
		val script = '''
			import {x} from ""
			myIdent;
		'''.parse

		assertEquals(2, script.scriptElements.size)
		
		val stmt = script.scriptElements.last as ExpressionStatement
		val expr = stmt.expression as IdentifierRef
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(2, node.lookAhead)
		node.assertParentLookAhead
	}
	
	@Test
	def void testIdentifierWithSemiAfterImport_08() {
		val script = '''
			import {x as y} from ""
			myIdent;
		'''.parse

		assertEquals(2, script.scriptElements.size)
		
		val stmt = script.scriptElements.last as ExpressionStatement
		val expr = stmt.expression as IdentifierRef
		
		val node = NodeModelUtils.getNode(expr)
		
		assertEquals(2, node.lookAhead)
		node.assertParentLookAhead
	}
	
	@Test
	def void testComplexScenario() {
		val script = '''
			import CollectionChangedEvent      from 'n4/events/CollectionChangedEvent';
			import Event                 	   from 'n4/events/Event';
			import ChangeEvent                 from 'n4/events/ChangeEvent';
			import ConstraintCollectEvent      from 'n4/events/ConstraintCollectEvent';
			import CollectionChangeType        from "n4/events/CollectionChangeType";
			import CollectionChangedItemsEntry from "n4/events/CollectionChangedItemsEntry";
			import PropertyChangedEvent        from "n4/events/PropertyChangedEvent";
			import DataObject                  from 'n4/model/DataObject';
			import Change                      from 'n4/model/persistence/Change';
			import ChangeType                  from 'n4/model/persistence/ChangeType';
			import *  from "n4/model/internal/ObservableHelper";
			import _                           from 'underscore/underscore';
			    
			/////////////////////////////////////////////////////////////////////////    
			// used in bodies:
			var @Undefined undef;
			/////////////////////////////////////////////////////////////////////////    
			
			class ObservableHelper{
				static void callListenersWithEvent(any p1, any p2) {}
			}
			class ObservableItem extends IDO {
				boolean _$observable;
				Array<ChangeBroker> _$listeners;
			}
			class ChangeBroker {
				any _$list;
			}
			
			class IDO {
				boolean _$n4ido; 
				string id;
				boolean _$reference;
				void getID() {}
			}
			
			/**
			 * @type-param {T}
			 */
			export class ListBase<T extends ObservableItem> {
			    /**
			     * Check whether the given value is a List.
			     *
			     * @param {*} val
			     * @return {Boolean}
			     * @public
			     */
			    public static boolean isList(any val) { return null; }
			    
			    
			    /**
			     * Duck types instances of this class as being collections like list or sets.
			     * 
			     *  @type {Boolean} 
			     */
			    boolean _$collectable = true;
			    /**
			     * Duck types instances of this class as being a lists.
			     * 
			     *  @type {Boolean} 
			     */
			    boolean _$isList = true;
			    /**
			     * List is defined as observable.
			     *  
			     * @type {Boolean} 
			     */
			    boolean _$observable = true;
			
			    
			    /**
			     * The array of items of the list
			     *  
			     * @type {Array.<T>} 
			     */
			    Array<T> items_;
			    
			    /**
			     * The number of items in the list.
			     *  
			     * @type {Number}
			     * @public 
			     */
			    public number length = 0;
			    
			    /** 
			     * properties to handle change detection / notification
			     */
			    
			    /**
			     * Flag defines if list is actual observed his items.
			     *  
			     * @type {Boolean} 
			     */
			    boolean observing = false;
			    
			    /**
			     * Array of change listeners of the list.
			     *  
			     * @type {Array<Function>} 
			     */
			    Array<{function()}> _$listeners;
			    // TODO {
			    //    init : Joose.I.Array
			    // },
			    
			    /**
			     * Collected changes on the list or the items of the list.
			     *  
			     * @type {Array.<n4/model/persistence/Change>} 
			     */
			    Array<Change> _$activeChanges = {}; // TODO
			    
			    /**
			     * Flag to check on add/insert only mutable objects
			     * @type {Boolean}  
			     */
			    boolean _$checkForMutableOnly = void 0; // undef;;
			    /**
			     * Flag defines if change events are tracked as changes. 
			     * If switched off no change delta calculation is possible.
			     * 
			     * @type {Boolean}
			     */
			    boolean _$trackChanges = true;
			
			    /**
				  * @param {Map<String,Object>=} spec
				  * @this {n4/lang/ListBase}
				  */
				// TODO spec is a map?  
			    ListBase(~Object with {Array<T> items_; number length;} spec) {
				    spec = spec || {};
				    this.items_ = spec.items_ || [];
				    this.length = spec.length || 0;
				    this.observing = false;
				    this._$listeners = [];
				    this._$trackChanges = true;
			    }
			    
			    /**
			     * Private method to send to all listeners the event
			     * 
			     * @param {n4/events/Event} event
			     * @param {Number=} repetition - if item exit multiple in the list
			     */
			    fireListeners(Event event, number? repetition) {
			    	var /** @type {n4/events/ChangeEvent}*/ ChangeEvent ce;
			    
			        if (this.observing && this._$trackChanges && event instanceof ChangeEvent) {
			            ce = event;
			            this._$trackChange(ce);    
			        }
			        callListenersWithEvent(this, event);
			    }
			    
			    /**
			     * Send event if the size of the list has been changed.
			     * @param {Number} oldLen
			     */
			    fireSizeChangedEvent(number oldLen) {
			    	var Event event;
					
					//var l = this.length;
			                
			        if (this.observing && oldLen !== this.length) {
			            event = new PropertyChangedEvent({
			            	length: 0,
			            	source: this,
			                value: this.length,
			                oldValue: oldLen,
			                propertyName: "length",
			                ignoreAsTrackChange: true
			            });
			            callListenersWithEvent(this, event);
			        }
			    }
			    
			    /**
			     *  Get the size of this list.
			     *
			     *  @return {Number}
			     *  @public
			     */
			    public number size() {
			    	return this.items_.length;
			    }
			    
			    /**
			     * Get an item from this list.
			     * 
			     * @param {Number} index
			     * @return {T}
			     * @public
			     */
			    public T get(number index) {
			    	 return this.items_[index];
			    }
			    
			    /**
			     * Get a range of items from this list as an array. 
			     *
			     * The `from` and `to` arguments are inclusive. If the specified range lies outside the size of this list, then
			     * `null` will be returned.
			     *
			     * @public 
			     * @param {Number} from     The lower bound of the range, should be `>= 0`
			     * @param {Number} to       The upper bound of the range, should be `<= this.size()`
			     * @return {?Array.<T>}
			     */
			    public Array<T> getRange(number from, number to) {
			    	var number tmp, /** @type {Array<T>} */ Array<T> ret;
			        if (to < from) { // swap
			            tmp = to;
			            to = from;
			            from = tmp;
			        }
			        if (from < 0 || to >= this.size()) {
			            ret = null;
			        } else {
			            ret = [];
			            for (tmp = from; tmp <= to; ++tmp) {
			                ret.push(this.get(tmp));
			            }
			        }
			        return ret;
			    }
			    
			    /**
			     *  Get the index of the given item in this list. -1 is returned if the item does not exist in the list.
			     *  
			     *  @param  {T}     item        The item to look for. Only exact matches (===) will be returned.
			     *  @return {Number}            The index of that item, or -1 if it wasn't found.
			     *  @public
			     */
			    public number indexOf(T item) {
			    	return _.indexOf(this.items_, item, undefined);
			    }
			    
			    /**
			     * @param {T} value A value to add to the list
			     * @return {n4/lang/ListBase<T>}
			     * @public
			     */
			    public ListBase<T> push(T value) {
			    	var number idx, oldLen = this.length;
			                
			        if (this._$checkForMutableOnly && true === value['_$immutable']) {
			            throw new Error("Not allow to insert immutable objects");
			        }
			        this.items_ = this.items_ || [];            
			        this.items_.push(value);
			        idx = this.items_.length-1;
			        this._$setLength(idx+1);
			        
			        if (this.observing) {
			            this._$observeItem(value);
			            this.fireListeners(new CollectionChangedEvent({
			                source: this,
			                type: CollectionChangeType.ADDED,
			                items: [new CollectionChangedItemsEntry({indexOrKey: idx, value: value})]
			            }));
			            this.fireSizeChangedEvent(oldLen);
			        }
			
			        return this;
			    }
			    
			    /**
			     * Removes an item from list by its index.
			     * 
			     * @param {Number}  index   The index of the item to remove.
			     * @return {T}         The item which was removed.
			     * @public
			     */
			    public T remove(number index) {
			    	var /** @type {T} */ T item, oldLen = this.length;
			    
			        if (this.observing) {
			            this._$unobserveItem(this.items_[index]);
			
			            item = this.items_.splice(index, 1, undefined)[0];
			            this._$setLength(this.items_.length);
			
			            this.fireListeners(new CollectionChangedEvent({
			                source: this,
			                type: CollectionChangeType.REMOVED,
			                items: [new CollectionChangedItemsEntry({indexOrKey: index, oldValue: item})]
			            }));
			            this.fireSizeChangedEvent(oldLen);
			        } else {
			            item = this.items_.splice(index, 1, undefined)[0];
			            this._$setLength(this.items_.length);
			        }
			
			        return item;
			    }
			    /**
			     * Clear the complete list by removing all item
			     * @public
			     */
			    public clear() {
			    	var self = this, 
			            /** @type {Array<n4/events/CollectionChangedItemsEntry>}*/ Array<n4/events/CollectionChangedItemsEntry> entries = [];
			        
			        if (this.observing) {
			           _.each(this.items_, function(any item, number? idx){
			               self._$unobserveItem(item, true);
			               entries.push(new CollectionChangedItemsEntry({indexOrKey: idx, oldValue: item}));
			           });
			        }
			        this.items_ = [];
			        this.length = 0;
			        if (this.observing) {
			            this.fireListeners(new CollectionChangedEvent({
			                source: this,
			                type: CollectionChangeType.REMOVED, items: entries
			            }));
			            this.fireSizeChangedEvent(entries.length);
			        }
			        
			        var Event e;
			        e.mergeWith(undefined);
			    }
			    
			    /**
			     * Return the item with given itemID and if the item is of type IDO/PDO.
			     * In other case null returned.
			     * 
			     * @param {String} itemID
			     * @return {T}
			     * @public
			     */
			    public T getByID(string itemID) {
			    	 var /** @type {T}*/ T ido = _.detect(this.items_, function(IDO item){
			                return (item._$n4ido && item.id === itemID) || 
			                        (item._$reference && item.getID() === itemID);
			            }) || null;
			        return ido;
			    }
			    
			    /**
			     * turn on observation
			     * 
			     * @public 
			     * @return {n4/lang/ListBase<T>} the current list
			     */
			    public ListBase<T> observe() {
			    	var self = this;
			    
			        if (this.observing === false) {
			            _.each(this.items_, function(item){
			                self._$observeItem(item);
			            })
			            this.observing = true;
			        }
			
			        return this;
			    }
			    
			    /**
			     * turns off observation for this list
			     * 
			     * @public 
			     * @return {n4/lang/ListBase<T>} the list
			     */
			    public ListBase<T> unobserve() {
			    	var self = this;
			    
			        if (this.observing === true) {
			            _.each(this.items_, function(item) {
			                self._$unobserveItem(item, true);
			            });
			            this.observing = false;
			            this._$activeChanges = undef;
			        }
			
			        return this;
			    }
			    
			    /**
			     * Enables or disables change tracking for this list. If the list
			     * has been tracking changes, the already tracked changes are 
			     * removed from memory.
			     * 
			     * @param {Boolean} track  Whether the map should track changes or not.
			     * @internal 
			     */
			     // TODO internal?
			    setTrackingChanges(boolean track) {
			    	this._$trackChanges = track;
			        this._$activeChanges = undef;
			    }
			    
			    /**
			     * Set value as new length; own method to optional overload by derived classes
			     * @param {Number} newLen
			     */
			    _$setLength (number newLen) {
			    	 this.length = newLen;
			    }
			    
			    /**
			     * Observe a single item by registering the List in the
			     * observation callbacks. Attach information of the list
			     * to the callback function so we can remove it when un-
			     * observing the item
			     * 
			     * @param {T} item - The item to observe.
			     */
			    _$observeItem(T item) {
			    	var isObservingAlready, ChangeBroker handler, list = this;
			                
			//        list = this;
			
			        if (item && item._$observable) {
			            isObservingAlready = _.any(item._$listeners, function(ChangeBroker cb) {
			                return cb._$list === list;
			            });
			        
			            if (!isObservingAlready) {
			                handler = function (Event e) {
			                    var repetition = 0, length = list._$listeners.length,
			                        /** @type {n4/events/ConstraintCollectEvent} */ ConstraintCollectEvent eventClone,
			                        isConstraintEvent, isChangedEvent = false;
			
			                    isConstraintEvent = e instanceof ConstraintCollectEvent;
			                    if( !isConstraintEvent ) {
			                        isChangedEvent = e instanceof ChangeEvent;
			                    }
			                    
			                    if (list.observing) {
			                        /*
			                         * An item may appear in the list multiple times and even
			                         * if it just appears once, its index may have been changed
			                         * due to other items being added/removed. Thus we have to
			                         * find the current index(es) of the item to fire the right
			                         * event(s)
			                         */
			                        _.each(list.items_, function(itm, number idx) {
			                            if (itm === item) {
			                                if (isConstraintEvent) {
			                                    if( length ) {
			                                        eventClone = e.cloneWithoutConstraints();
			                                        eventClone.addArrayIndex( idx );
			                                        ObservableHelper.callListenersWithEvent(list, eventClone);
			                                        e.mergeWith( eventClone );
			                                    }
			                                }
			                                else if (isChangedEvent){
			                                    // found item at index "idx"
			                                        list.fireListeners(new CollectionChangedEvent({
			                                            cause: e,
			                                            source: list,
			                                            type: CollectionChangeType.MODIFIED,
			                                            items: [new CollectionChangedItemsEntry({indexOrKey: idx, value: itm})]
			                                        }),repetition);
			                                    } else {
			                                        list.fireListeners(e,repetition);
			                                    }
			                                    ++repetition;
			                                }
			                            });
			                        }
			                    };
			                    handler._$list = this;
			 
			                    item._$listeners = item._$listeners || [];
			                    item._$listeners.push(handler);
			                }
			            }
			    	
			    }
			    
			    /**
			     * Turns off observation for an item. Removes the callback method that
			     * was added with _$observeItem but does not touch any other callbacks.
			     * 
			     * @param {T} item - The item to stop observing
			     * @param {Boolean=} withoutMultipleCheck - dont check for multiple added items
			     */
			    _$unobserveItem(T item, boolean? withoutMultipleCheck) {
			    	var ListBase<T> list, number repetition = 0;
			                
			        list = this;
			
			        if (item && item._$observable) {
			            /*
			             * We need to check if the item appears at another index
			             * as well and, if it does, not remove the callback...
			             */
			            if (withoutMultipleCheck || !_.any(this.items_, function(itm, idx) {
			                // Same item but not the first one
			                return itm === item && 0 !== repetition++;
			            })) {
			                item._$listeners = _.select(item._$listeners, function(ChangeBroker cb) {
			                    return cb._$list !== list;
			                });
			            }
			                }
			    }
			    
			    /** 
			     * Private method to collect all changes  
			     * @param {n4/events/ChangeEvent} event
			     */
			    _$trackChange(ChangeEvent event) {
			    	var /**@type{Array.<n4/model/persistence/Change>}*/ Array<Change> changes, self = this;
			                
			        changes = Change.createChangesFromEvent(event);
			        if (changes) {
			            this._$activeChanges = this._$activeChanges || [];
			            _.each(changes, function(Change change) { 
			                change.addToExistingChanges(self._$activeChanges);     
			            });
			        }
			    }
			    
			    /**
			     * Returns the list of all created or modified changes
			     * @return {Array.<n4/model/persistence/Change>}
			     */
			    Array<Change> _$getCreateOrModifyChanges() {
			    	if (this._$activeChanges)
			            return _.select(this._$activeChanges,function(Change change){
			                return ChangeType.CREATED === change.type || ChangeType.MODIFIED === change.type;
			            });
			        return [];
			    }
			    
			    /**
			     * Returns the list of all deleted changes
			     * @return {Array.<n4/model/persistence/Change>}
			     */
			    Array<Change> _$getDeleteChanges () {
			    	if (this._$activeChanges)
			            return _.select(this._$activeChanges,function(Change change){
			                return ChangeType.DELETED === change.type;
			            });
			        return [];
			    }
			    
			    /**
			     * Returns if any change is tracked
			     * @return {Boolean}
			     */
			    boolean _$hasAnyChanges () {
			    	return this._$activeChanges ? 0 !== this._$activeChanges.length : false;
			    }
			 
			    /**
			     * Set a new root for list deltas. All until now collected changes will be removed.
			     */
			    _$setDeltaRoot() {
			    	this._$activeChanges = undef;
			    }
			
			    /** 
			     * internal function will be called if the object is
			     * cloned by copying the data. But some data need 
			     * special handling like here for the change tracking handler.
			     * This need to set new.
			     * 
			     * @param {n4/model/DataObject} copiedFrom
			     * @param {Boolean} toMutable
			     */
			    _$cloned(DataObject copiedFrom, boolean toMutable) {
			    	this.unobserve();
			        if (copiedFrom.observing || toMutable) {
			            this.observe();
			        }
			    }
			    
			    /**
			     * Private debug method to show content of the list
			     * @return {String}
			     */
			    string toString() {
			    	var number i, number l, Array<String> out = [];
			        for (i = 0, l = this.size(); i < l; ++i) {
			            out.push(String(this.get(i)));
			        }
			        return out.join(", ").slice(0, 50);
			    }
			}
		'''.parse

		val exportDecl = script.scriptElements.last as ExportDeclaration
		assertEquals(1, NodeModelUtils.getNode(exportDecl).lookAhead)
		
		val classDecl = exportDecl.exportedElement as N4ClassDeclaration
		assertEquals(3, NodeModelUtils.getNode(classDecl).lookAhead)
		
		var max = 0;
		// Array<{function()}> _$listeners;
		// has exactly 10 tokens LA
		val expectedMax = 10;
		for(node: NodeModelUtils.getNode(classDecl).asTreeIterable) {
			if (node instanceof ICompositeNode) {
				val la = node.lookAhead
				max = Math.max(la, max)
				assertTrue(node.text + "[" + la + "]", la <= expectedMax)
			}
		}
		// in case we improve the grammar, we want this test to fail such that we can make it more restrictive
		assertEquals(expectedMax, max)
	}
	
	def private void assertParentLookAhead(ICompositeNode node) {
		var la = node.lookAhead
		var parent = node.parent
		while(parent !== null) {
			assertTrue(la >= parent.lookAhead)
			la = parent.lookAhead
			parent = parent.parent
		}	
	}
}
