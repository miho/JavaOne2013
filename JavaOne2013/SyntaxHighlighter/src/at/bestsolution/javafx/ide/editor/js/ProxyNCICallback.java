/*******************************************************************************
 * Copyright (c) 2012 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl<tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package at.bestsolution.javafx.ide.editor.js;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import netscape.javascript.JSObject;

public abstract class ProxyNCICallback<O> implements JavaScriptNCICallback<JSObject> {
	private Class<O> jInterface;
	
	public ProxyNCICallback(Class<O> jInterface) {
		this.jInterface = jInterface;
	}
	
	@Override
	public void initJava(JSObject jsObject) {
		O o = (O) Proxy.newProxyInstance(jInterface.getClassLoader(), new Class[] {jInterface}, new JsInvocationHandler(jsObject, jInterface));
		jsObject.setMember("__javaObject", o);
		init(o);
	}
	
	protected abstract void init(O o);
	
	public static class JsInvocationHandler<O> implements InvocationHandler {
		private JSObject jsObject;
		private Class<O> jInterface;
		
		public JsInvocationHandler(JSObject jsObject, Class<O> jInterface) {
			this.jsObject = jsObject;
			this.jInterface = jInterface;
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			if( method.getDeclaringClass() != Object.class ) {
				jsObject.call(method.getName(), args);
			} else if( method.getName().equals("toString") ) {
				return "proxy " + jInterface.getName() + "(" + jsObject + ")";
			} else  {
				throw new UnsupportedOperationException("The method '"+method.getName()+"' is not supported.");
			}
			
			return null;
		}
		
	}
}
