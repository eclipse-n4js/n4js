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
package org.eclipse.n4js.utils.emf;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * An EMF {@link Resource} that takes responsibility for resolving the proxies of its contained {@link EObject}s.
 * <p>
 * The default EMF behavior for resolving proxies is to directly invoke {@link EcoreUtil#resolve(EObject, EObject)}
 * which then delegates resolution to the containing {@link ResourceSet} (this default behavior is implemented in
 * {@link BasicEObjectImpl#eResolveProxy(InternalEObject)}). By ...
 * <ol>
 * <li>letting the containing {@link Resource} implement this interface, and ...
 * <li>configuring the EMF code generator to use {@link ProxyResolvingEObjectImpl} as a base class for all EObject,
 * </ol>
 * the early handling of proxy resolution can be customized.
 * <p>
 * In Xcore, the aforementioned configuration of the EObject base implementation can be done as follows:
 *
 * <pre>
 * &#64;Ecore(nsURI="http://www.eclipse.org/n4js/n4js/N4JS")
 * &#64;GenModel(fileExtensions="n4js",
 *     rootExtendsClass="org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl",
 *     // ...
 * )
 * </pre>
 */
public interface ProxyResolvingResource extends Resource {

	/**
	 * Invoked whenever a proxy of an {@link EObject} contained in this {@link ProxyResolvingResource} is to be
	 * resolved. The default EMF behavior would be to invoke
	 *
	 * <pre>
	 * EcoreUtil.resolve(proxy, objectContext);
	 * </pre>
	 *
	 * but some special handling may be performed, instead.
	 *
	 * <b>IMPORTANT:</b> in case of unresolvable proxies, the EMF contract is to return the proxy, not
	 * <code>null</code>!
	 *
	 * @param proxy
	 *            the proxy to resolve.
	 * @param objectContext
	 *            the {@code EObject} contained in this resource that holds the given proxy.
	 * @return the resolved target object or the given proxy itself iff it is unresolvable; never <code>null</code>.
	 */
	public EObject doResolveProxy(InternalEObject proxy, EObject objectContext);
}
