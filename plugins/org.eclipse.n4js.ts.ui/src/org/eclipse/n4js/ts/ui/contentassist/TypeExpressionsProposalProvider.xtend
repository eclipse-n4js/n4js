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
package org.eclipse.n4js.ts.ui.contentassist

import com.google.common.collect.Lists
import java.util.Collections
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor
import org.eclipse.xtext.util.PolymorphicDispatcher
import org.eclipse.xtext.util.PolymorphicDispatcher.ErrorHandler
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions

/**
 * see http://www.eclipse.org/Xtext/documentation.html#contentAssist on how to customize content assistant
 */
class TypeExpressionsProposalProvider extends AbstractTypeExpressionsProposalProvider {
	
	private final static Logger log = Logger.getLogger(TypeExpressionsProposalProvider);

	val ReflectExtensions reflector = new ReflectExtensions

	override protected void invokeMethod(String methodName, ICompletionProposalAcceptor acceptor, Object... params) {
		val dispatchers = getDispatchersMap()
		var PolymorphicDispatcher<Void> dispatcher = dispatchers.get(methodName)
		if (dispatcher === null) {
			// customizing is here: Log error instead of Warning.
			var ErrorHandler<Void> errorHandler = new LogErrorHandler(log)
			dispatcher = new PolymorphicDispatcher<Void>(methodName, params.length + 1, params.length + 1,
				Collections.singletonList(this), errorHandler) {
				override Class<?> getDefaultClass(int paramIndex) {
					if (paramIndex === 0) return EObject
					return super.getDefaultClass(paramIndex)
				}
			}
			dispatchers.put(methodName, dispatcher)
		}
		var Object[] paramAsArray = newArrayOfSize(params.length + 1)
		System.arraycopy(params, 0, paramAsArray, 0, params.length)
		{
			val _wrVal_paramAsArray = paramAsArray
			val _wrIndx_paramAsArray = params.length
			_wrVal_paramAsArray.set(_wrIndx_paramAsArray, acceptor)
		}
		if (announceProcessing(Lists.asList(methodName, paramAsArray))) {
			dispatcher.invoke(paramAsArray)
		}
	}
	
	private def Map<String, PolymorphicDispatcher<Void>> getDispatchersMap() {
		try {
			return reflector.get(this, 'dispatchers')
		} catch (NoSuchFieldException | IllegalAccessException exc) {
			throw new RuntimeException(exc)
		}
	}
	
	static class LogErrorHandler<RT> implements ErrorHandler<RT>{
		val Logger logger
		new(Logger logger) {
			this.logger=logger 
		}
		override RT handle(Object[] params, Throwable throwable) {
			logger.error('''Error in polymorphic dispatcher : «throwable.getMessage()»''', throwable) 
			return null 
		}
	}

}
