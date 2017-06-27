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
package org.eclipse.n4js.validation.validators

import com.google.common.base.Joiner
import org.eclipse.n4js.n4JS.AbstractCaseClause
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.BreakStatement
import org.eclipse.n4js.n4JS.ContinueStatement
import org.eclipse.n4js.n4JS.DebuggerStatement
import org.eclipse.n4js.n4JS.DoStatement
import org.eclipse.n4js.n4JS.ForStatement
import org.eclipse.n4js.n4JS.IfStatement
import org.eclipse.n4js.n4JS.IterationStatement
import org.eclipse.n4js.n4JS.LabelledStatement
import org.eclipse.n4js.n4JS.ReturnStatement
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.SwitchStatement
import org.eclipse.n4js.n4JS.ThrowStatement
import org.eclipse.n4js.n4JS.TryStatement
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.n4JS.WhileStatement
import org.eclipse.n4js.n4JS.WithStatement
import java.text.Collator
import java.util.Deque
import java.util.List
import java.util.Set

import static com.google.common.collect.Lists.*
import static com.google.common.collect.Sets.*

import static extension org.eclipse.n4js.validation.validators.LabelStackEntry.*
import static extension org.eclipse.n4js.validation.validators.ReturnMode.*

/**
 * Possible ReturnModes are
 * <ul>
 * <li> noReturnsMode - doesn't return and has no uncaught explicit throw-statement
 * <li> breakOrContinue - StatementList may exit with breaks at different label locations
 * <li> throwsMode - explicit throw
 * <li> returnsMode - explicit return
 * </ul>
 *
 * What still remains are always nested thrown objects originating in function-calls.
 */
public class ReturnMode {
	public final static ReturnMode noReturnsMode = new ReturnMode('noReturnsMode')

	// public final static ReturnMode ReturnOrThrow = new ReturnMode('returnOrThrow')
	public final static ReturnMode throwsMode = new ReturnMode('throwsMode')
	public final static ReturnMode returnsMode = new ReturnMode('returnsMode')

	String representation;

	protected new(String rep) {
		representation = rep
	}

	def removeLabel(String string) {
		// do nothing in standard, only for BreakOrContinue
	}

	def override toString() {
		return representation;
	}

	/**
     * Assessing one statement different flows could be possible. The overall result of
     * the addressed statement is the weakest possible flow. in terms of return-checking :
     * NoReturn < BreakOrContinue < Throw < Return
     */
	def static ReturnMode sameLevelCombine(ReturnMode a, ReturnMode b) {

		// One is null means this level has not been reached:
		if( b === null ) return a;
		if( a === null ) return b;

		// One is NoReturn
		if (a === noReturnsMode || b === noReturnsMode) {
			return noReturnsMode;
		}

		// One is BreakOrContinue
		if (a instanceof BreakOrContinue) {
			if (b instanceof BreakOrContinue) {

				// combine
				return a.combine(b)
			} else {
				return a;
			}
		} else {
			if (b instanceof BreakOrContinue) {
				return b;
			}
		}

		// One is Throw
		if (a === throwsMode || b === throwsMode) {
			return throwsMode
		}

		// Both must be of type returnMode:
		if (! (  a === returnsMode && b === returnsMode )) throw new IllegalStateException(
			"Something utterly wrong in ReturnMode calculation.")

		return returnsMode

	}
}

class BreakOrContinue extends ReturnMode {

	// SortedSet for output in xpect:
	private Set<String> labels = <String>newTreeSet(Collator.instance);

	public new(String ... lbls) {
		super('breakOrContinue')
		lbls.forEach[withLabel(it)]
	}

	public new() {
		super('breakOrContinue')
	}

	def withLabel(String s) {
		labels.add(s);
	}

	def isEmpty() {
		labels.empty
	}

	def addAll(BreakOrContinue b) {
		labels.addAll(b.labels)
	}

	/**
	 * Combine all labels possible to reach
	 * @param other Break with labels
	 * @return new instance with union of all labels
	 */
	def BreakOrContinue combine(BreakOrContinue b) {

		val ret = new BreakOrContinue()
		ret.addAll(this)
		ret.addAll(b)
		return ret

	}

	def override removeLabel(String label) {
		labels.remove(label)
	}

	def override toString() {
		super.toString + " labels " + labels.asString
	}

	def String asString(Iterable<String> strings) {
		Joiner.on(", ").join(strings)
	}

}

/**
 * Members of the labelstack which tracks the scope of break-statements.
 */
class LabelStackEntry {

	// Label of the break-scope, can be null for unlabeled iteration-entries
	val String label;
	// it true signals at least one break/continue jumped to or over this label
	public var jumpedTo = false;

	new() {
		label = null;
	}

	new(String name) {
		label = name
	}

	override String toString() {
		val j = if (jumpedTo) "*" else "."
		return "(" + (if (label === null) "" else label) + ";" + j + ")"
	}

	/**
	 * if the iteration statement is not labeled, it has to pushed on top of the stack.
	 */
	def static pushUnnamed(LabelStateContext entries, IterationStatement statement) {
		pushUnnamedG(entries, statement);
	}

	/**
	 * if the switch statement is not labeled, it has to pushed on top of the stack.
	 */
	def static pushUnnamed(LabelStateContext entries, SwitchStatement statement) {
		pushUnnamedG(entries, statement);
	}

	/**
	 * if the iteration/switch statement is not labeled, it has to pushed on top of the stack.
	 */
	private def static pushUnnamedG(LabelStateContext entries, Statement statement) {
		entries.push(new LabelStackEntry)
	}

	/** Marks the top of stack (down to the referenced label, if any)  as having jump-out references inside of its blocks.
	 * */
	def static void mark(LabelStateContext lsContext, BreakStatement statement) {
		mark(lsContext,statement, statement.label)
	}
	/** Marks the top of stack (down to the referenced label, if any)  as having jump-out references inside of its blocks.
	 * */
	def static void mark(LabelStateContext lsContext, ContinueStatement statement) {
		mark(lsContext,statement, statement.label)
	}
	private def static void mark(LabelStateContext lsContext, Statement statement, LabelledStatement label) {
		// Guard this method:
		switch( statement ) {
			ContinueStatement: {}
			BreakStatement: {}
			default: {throw new IllegalStateException("This method should only be called for Continue")}
		}

		// might be empty for subentry-checks.
		if (! lsContext.empty)
			lsContext.peek.jumpedTo = true

		// the break is labeled:
		if (label !== null) {

			// go down till label is found.
			for (entry : lsContext) {
				entry.jumpedTo = true;
				if (label.name == entry.label) return;
			}

			// should never reach in a closed function body -
			// if evaluating subexpressions this could happen.
			// throw new BreakOrContinueOutException("Reached bottom of labelstack for break/continue.", new BreakOrContinue( statement.label.name ))
			lsContext.registerOutOfScopeBreakOrContinue(label)
		}
	}
}

/**
 * context comprises of
 * <li> the current label stack of labeled statements and
 * <li> a list of breaks/continues that did not find a match on this stack
 * <li> a list of dead code ranges (tails of a sequences) as {@code DeadCodeBuckets}.
 */
class LabelStateContext implements Iterable<LabelStackEntry> {

	// Label-stack

	Deque<LabelStackEntry> entries = <LabelStackEntry>newLinkedList()

	def void push(LabelStackEntry e) {
		entries.push(e)
	}

	def boolean isEmpty() {
		entries.empty
	}

	def LabelStackEntry peek() {
		entries.peek()
	}

	def LabelStackEntry pop() {
		entries.pop
	}

	override iterator() {
		entries.iterator
	}

    // Scope-Breaks

	public List<BreakOrContinue> outerScopeBreaks = newArrayList

	def registerOutOfScopeBreakOrContinue(LabelledStatement label) {
		outerScopeBreaks.add(new BreakOrContinue(label.name))
	}

	def squashedOuterScopeBreaks() {
		outerScopeBreaks.reduce[p1, p2|p1.combine(p2)]
	}

	// Dead-code

	public val List<DeadCodeBucket> deadCode = newArrayList

	/** Guarded add of Dead Code, only if the list of dead statements is not empty. */
	def addDeadCodeBucket(Statement exitStmt, List<Statement> dStmts) {
		if( dStmts.nullOrEmpty ) return;

		// if the dead code stems from a direct switch-computation, only the active case should be considered.
		// @see #evalCase and the squashing.

		// Avoid double-entries:
		if( deadCode.findFirst[ lastExecutedStmt === exitStmt ] !== null ) {
			return;
		}

		val exitStmtContainer = exitStmt.eContainer
		if( exitStmtContainer instanceof AbstractCaseClause ) {
			// keep only those statements which are
			dStmts.retainAll( exitStmtContainer.statements )
		}


		if(dStmts.empty) return;

		deadCode+=new DeadCodeBucket(exitStmt,dStmts)
	}

}

/**
 * Marking of Dead code.
 */
class DeadCodeBucket {
	// Marking of Dead Code Trees.
	// last executed, usually break / throw / return / continue
	public val Statement lastExecutedStmt;
	public val statements = <Statement>newArrayList

	new(Statement last, Statement...stmts) {
		lastExecutedStmt = last
		statements.addAll(stmts)
	}
}

/**
 * Container-Class for reporting all checks from an ReturnOrThrowAnalysis
 */
class FunctionFullReport {
	public val ReturnMode returnMode
	public val List<DeadCodeBucket> deadCode

	new(ReturnMode rm,List<DeadCodeBucket> dc){
		returnMode = rm;
		deadCode = dc
	}
}

/**
 */
class ReturnOrThrowAnalysis {

	/**
	 * Wrapping a single statement in a list. Used to have similar computation for blocks and single statements.
	 */
	private def List<Statement> asList(Statement stmt) {
		newArrayList(stmt);
	}

	// Discussion with Jens:
	// Solution to Tracking is Labeling of all IterationStatements
	// Maintaining a stack of current label-scope with an boolean state for each label.
	// if a break to label X happens, all stack-element from Top to Label X will get state true.
	// unnamed label will be just simply put on top
	// each return-statement is bound to the top-element of the stack.
	// if the top-element's jumpedTo state is true, the return is not armed, hence
	// the return might not be called due to an earlier break in control flow
	//
	// Similar approach to throw + catch statements in try-blocks.
	//

//	/** Wrapper call to #exitBehaviour with single-element list containing the statement. */
//	def ReturnMode exitBehaviour(Statement stmt) {
//		return exitBehaviour(stmt.asList)
//	}

	/** Wrapper call to #exitBehaviour for a substatement and reporting break-statements out of scope.
	 * Delegates to #evalSubstatementF
	 */
	def ReturnMode evalSubstatement(Statement stmt) {
		return evalSubstatementF(stmt).returnMode
	}

	/**
	 *  delegates to #exitBehaviour, analyses breaks/continous referring outer scope and if any
	 *  modifies the the returnmode in the FunctionFullReport.
	 *
	 */
	def FunctionFullReport evalSubstatementF(Statement stmt) {

		val LabelStateContext lblStck = new LabelStateContext();

		var ret = exitBehaviour(stmt.asList, lblStck)

		// If outerscopes are target of breaks/continous this is what the Returnmode of the statement is.
		if (! lblStck.outerScopeBreaks.empty) {
			ret = lblStck.squashedOuterScopeBreaks
		}


		return new FunctionFullReport(ret,lblStck.deadCode)
	}

    /**
     * Computing Function-Body-Behaviour - only interested in Returnmode,
     * delegates to #exitBehaviourWithFullReport .
     */
	def ReturnMode exitBehaviour(List<Statement> stmts) {
	   return exitBehaviourWithFullReport(stmts).returnMode
	}

	/**
     * Computing Function-Body-Behaviour - FullReport.
     */
	def FunctionFullReport exitBehaviourWithFullReport(List<Statement> stmts) {
		// State:
		val LabelStateContext lblStck = new LabelStateContext()

		val rm = exitBehaviour(stmts, lblStck)

		return new FunctionFullReport(rm, lblStck.deadCode )
	}

	private def ReturnMode exitBehaviour(List<Statement> stmts, LabelStateContext lblStck) {

		// TODO dead-code marking must be rethought in terms of (nested) switch-evaluation

		if (stmts===null) {
			return noReturnsMode
		}

  		var int i=0
        while( i<stmts.size ){
			val stmt = stmts.get(i)
			i=i+1

			// calc exit mode of current statement:
			var ReturnMode mode = null;

			switch stmt {
				VariableStatement: {
					// nothing to do, maybe forced Exceptions in function literals?
					// e.g.: //var any s = function() { throw '4' } ();
				}
				// BlockStatement
				Block: {
					mode = exitBehaviour(stmt.statements, lblStck)
				}
				IfStatement: {
					val IfStatement ifstmt = stmt
					val ifblock = exitBehaviour(ifstmt.ifStmt.asList, lblStck)

					// else must always be evaluated:
					val elseblock = if (ifstmt.elseStmt !== null) exitBehaviour(ifstmt.elseStmt.asList, lblStck) else noReturnsMode;

					mode = ifblock.sameLevelCombine(elseblock)

					// Modes of continue/break,throw and return cause dead code in the stmts-list:
					if( #{returnsMode, throwsMode }.contains(mode) || mode instanceof BreakOrContinue )
					{
						// all Code after this statement is dead:
					    lblStck.addDeadCodeBucket(stmt,stmts.subList(i,stmts.size))
					}
				}
				// iterations:
				DoStatement: {
					lblStck.pushUnnamed(stmt)
					mode = exitBehaviour(stmt.statement.asList, lblStck)
					mode = lblStck.popAndAdjustMode(mode)
				}
				ForStatement: {
					lblStck.pushUnnamed(stmt)

					mode = exitBehaviour(stmt.statement.asList, lblStck)

					// only for-loops without continuation-expression are guaranteed to execute their iteration-statement
					// otherwise it's a noreturn:
					if (stmt.forPlain && stmt.expression === null) {
						// guaranteed to be run at least once:
						// TODO if mode is noReturn we do have an endless loop here!
					} else {
						// for-body might never be called.
						mode = noReturnsMode
					}

					mode = lblStck.popAndAdjustMode(mode)
				}
				WhileStatement: {
					lblStck.pushUnnamed(stmt)
					mode = exitBehaviour(stmt.statement.asList, lblStck)

					// ignore while-body, may be not processed at all
					mode = noReturnsMode
					mode = lblStck.popAndAdjustMode(mode)
				}
				BreakStatement: {
					// TODO we have to consider the type of subtree we are walking in,
					// if this a unconditional flow all the rest is dead code.

					// keep looking forward for other breaks.
					lblStck.mark(stmt)
					val boc = new BreakOrContinue()
					if (stmt.label !== null) boc.withLabel(stmt.label.name)
					mode = boc;
					// all Code after this statement is dead:
					lblStck.addDeadCodeBucket(stmt,stmts.subList(i,stmts.size))

				}
				ContinueStatement: {
					lblStck.mark(stmt)
					val boc = new BreakOrContinue()
					if (stmt.label !== null) boc.withLabel(stmt.label.name)
					mode = boc;
					// all Code after this statement is dead:
					lblStck.addDeadCodeBucket(stmt,stmts.subList(i,stmts.size))
				}
				ReturnStatement: {

					// Maybe checking nested throws in return-expression ?
					mode = returnsMode
					// all Code after this statement is dead:
					lblStck.addDeadCodeBucket(stmt,stmts.subList(i,stmts.size))

				}
				WithStatement: {

					// outdated construct?
					mode = exitBehaviour(stmt.statement.asList, lblStck)
				}
				// break in switch...
				SwitchStatement: {
					lblStck.pushUnnamed(stmt)

					// It's not that simple since a missing break in one case lets the control flow carry on over the statements of
					// subsequent cases.
					// Eval all cases (if any)
					// Start with assuming all have returns:
					mode = if (stmt.cases.nullOrEmpty) noReturnsMode else returnsMode;
					for (cse : stmt.cases) {
						val cseRet = evalCase(cse, lblStck)
						mode = mode.sameLevelCombine(cseRet)
					}
					if (stmt.defaultClause !== null) {
						// eval already done & modes are combined.
					} else {

						// no default induces it might never be called:
						mode = noReturnsMode;
					}
					mode = lblStck.popAndAdjustMode(mode)
				}
				LabelledStatement: {
					lblStck.push(new LabelStackEntry(stmt.name))

					mode = exitBehaviour(stmt.statement.asList, lblStck)

					// remove current label (useful only for BreakOrContinue:
					mode.removeLabel(stmt.name)

					mode = lblStck.popAndAdjustMode(mode)
				}
				ThrowStatement: {

					// return ReturnOrThrow;
					mode = throwsMode
					// all Code after this statement is dead:
					lblStck.addDeadCodeBucket(stmt,stmts.subList(i,stmts.size))

				}
				TryStatement: {

					// cf [ECMA-6] 6.2.2 The Completion Record Specification Type
					// cf [ECMA-6] 13.14.6 Runtime Semantics: Evaluation

					// either try-finally   of try-catch or try-catch-finally

					val finly = if(stmt.^finally !== null) exitBehaviour(stmt.^finally.block.asList, lblStck)
					val tried = exitBehaviour(stmt.block.asList, lblStck)
					val caught = if (stmt.^catch !== null) exitBehaviour(stmt.^catch.block.asList, lblStck) else null

					if (stmt.^finally !== null) {

						// finally block exists:
						if (finly === returnsMode || finly === throwsMode) {
							mode = finly;
						} else {

							// finally block but doesn't override throw/return
							if (stmt.^catch !== null) {

								// catch and finally
								// tried-Throws getting eaten up:
								if (tried == throwsMode) {
									mode = caught
								} else {
									// we cannot say if a throw happens or not.
									mode = sameLevelCombine(tried, caught)
								}

								if( finly != noReturnsMode ) {
									mode = finly
								}
							} else {

								// finally but no catch:
								// It's not same level, since finally comes after
								// and modifies behaviour.
								if( finly == noReturnsMode ) {mode = tried} else
								{ mode = finly }
							}
						}

					} else {

						// no finally--> there must be a catch cf. SPEC-


						// no finally but catch:
						if (tried == throwsMode) {

							// catch eats up:
							mode = caught
						} else {
							mode = sameLevelCombine(tried, caught)
						}

					}
				}
				//          return
				//                containsReturnOrThrow(finally-block)
				//                ||
				//                (containsReturnOrThrow(try-block)
				//                && (catch-block==null || containsReturnOrThrow(catch-block)))
				DebuggerStatement: {
					// nothing to be done:
				}
			} // End of switch

			// if for an statement no break-labels were issued
			// and the returncode is returnsMode, we could go back
			if (lblStck.empty || ( ! lblStck.peek.jumpedTo )) {

				if (mode === returnsMode || mode === throwsMode) {

					// return, the rest must be dead code, collection of
					// it already accomplished at returns/throws statements.
					return mode
				}

			// mode is a BreakOrContinue -> ????
			//// TODO--> shall we still continue or
			////  can we drop out to the outmost label which could have been targeted?
			// mode is a noReturns -> eval next in sequence
			}

		} // End of for
		return noReturnsMode;
	}

	/**
	 * Popping last element from stack.
	 * if this was the jump-target in break/continue mode, then reset mode to noReturnsMode
	 *
	 * @param lblStck Stack to be modified
	 * @param mode original Mode, will be changed only if of Type BreakOrContinue
	 * @return adjusted mode
	 *
	 */
	private def popAndAdjustMode(LabelStateContext lblStck, ReturnMode mode) {

		var ret = mode

		lblStck.pop
		if (mode instanceof BreakOrContinue && ! lblStck.peek.jumpedTo) {

			// break/continuation-Mode reached it's targeted scope: continue
			//  as if nothing had happend.
			ret = noReturnsMode
		}

		return ret
	}

	/**
	 * Evaluates activating the passed in (switch statement) clause (ordinary or default doesn't matter).
	 *
	 * Problem addressed is the fall-through nature of case-clauses without an ending break-statement.
	 *
	 * @param clause - an arbitrary clause of an SwitchStatement.
	 * @param lblStck  the context of the current evaluation.
	 */
	private def ReturnMode evalCase(AbstractCaseClause clause, LabelStateContext lblStck) {
		val allCases = (clause.eContainer as SwitchStatement).cases
		val possiblyActiveCases = allCases.subList(allCases.indexOf(clause), allCases.length)

		// Squash all statements:
		val List<Statement> stmtTail = newArrayList
		possiblyActiveCases.fold(stmtTail, [r, t|r.addAll(t.statements); r])

		return exitBehaviour(stmtTail, lblStck)
	}

}
