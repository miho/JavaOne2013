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
package at.bestsolution.javafx.ide.editor.orion.editor;

public class Util {
	public static String escapeForJSON(String value) {
//		System.err.println(value);
		value = value.replace("\\", "\\\\");
		value = value.replace("\"", "\\\\\"");
//		System.err.println(value);
		return value;
	}
}
