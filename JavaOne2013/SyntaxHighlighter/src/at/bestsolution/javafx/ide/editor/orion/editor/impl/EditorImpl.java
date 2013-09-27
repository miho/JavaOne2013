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
package at.bestsolution.javafx.ide.editor.orion.editor.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.web.WebEngine;

import netscape.javascript.JSObject;
import at.bestsolution.javafx.ide.editor.ProblemMarker;
import at.bestsolution.javafx.ide.editor.ProblemMarker.Type;
import at.bestsolution.javafx.ide.editor.orion.editor.Editor;
import at.bestsolution.javafx.ide.editor.orion.editor.Util;
import at.bestsolution.javafx.ide.editor.orion.textview.TextView;
import at.bestsolution.javafx.ide.editor.orion.textview.impl.TextViewImpl;

@SuppressWarnings("restriction")
public class EditorImpl extends NativeObjectWrapper implements Editor {
	private Map<String, Runnable> actionSet = new HashMap<>();
	
	public EditorImpl(WebEngine e, JSObject jsObject) {
		super(e, jsObject);
	}

	public TextView getTextView() {
		JSObject jsTextView = (JSObject) getJSObject().call("getTextView");
		Object o = jsTextView.getMember("__javaObject");
		if( o != null && o instanceof TextView ) {
			return (TextView) o;
		} else {
			return new TextViewImpl(e, jsTextView);
		}
	}
	
	@Override
	public void setInput(String title, String message, String contents) {
		getJSObject().call("setInput", title, message, contents);
	}

	public void setAction(String action, Runnable actionRunnable) {
		actionSet.put(action, actionRunnable);
	}
	
	public void _js_Action(String action) {
		if( actionSet.containsKey(action) ) {
			actionSet.get(action).run();
		}
	}
	
	public void _js_StatusReporter(Object message, Object error) {
//		System.err.println("Status-Report: " + message + " => " + error);
	}

	@Override
	public void showProblems(List<ProblemMarker> markers) {
		JSObject o = (JSObject) e.executeScript("JSON.parse('"+toJSON(markers)+"')");
//		JSObject o = (JSObject) e.executeScript(toJSON(markers));
		getJSObject().call("showProblems", o);
	}
	
	public String toJSON(List<ProblemMarker> markers) {
		StringBuilder b = new StringBuilder();
		boolean flag = false;
		b.append("[");
		for( ProblemMarker m : markers ) {
			Integer lineOffset = (Integer) ((JSObject)getJSObject().call("getModel")).call("getLineStart", m.linenumber-1); 
			
			if( flag ) {
				b.append(",");
			}
			b.append("{");
			b.append("\"description\":\"" + Util.escapeForJSON(m.description) + "\"");
			b.append(",\"line\": " + m.linenumber);
			b.append(",\"severity\": \""+(m.type == Type.ERROR?"error":"warning")+"\"");
			b.append(",\"start\":  " + (m.startSrcPosition - lineOffset.intValue()));
			 b.append(",\"end\":  " +  (m.endSrcPosition - lineOffset.intValue()));
			b.append("}");
			
			flag = true;
		}
		b.append("]");
		System.err.println(" =========> " + b);
		return b.toString();
	}
	
	@Override
	public boolean isDirty() {
		Object o = getJSObject().call("isDirty");
		if( o instanceof Boolean ) {
			return ((Boolean)o).booleanValue();
		}
		return false;
	}

	
}
