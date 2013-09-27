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
package at.bestsolution.javafx.ide.editor.orion.textview;

import javafx.util.Callback;

public interface TextView {
	public void addEventListener(String type, Callback<? extends Object, Void> callback);
	public String getText();
	public String getText(int start, int end);
}
