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

import java.util.List;

import at.bestsolution.javafx.ide.editor.ProblemMarker;
import at.bestsolution.javafx.ide.editor.orion.textview.TextView;
import javafx.util.Callback;

public interface Editor {
	public boolean isDirty();
	public void setAction(String action, Runnable actionRunnable);
	public void setInput(String title, String message, String contents);
	public void addEventListener(String type, Callback<? extends Object, Void> callback);
	public TextView getTextView();
	public void showProblems(List<ProblemMarker> markers);
}
