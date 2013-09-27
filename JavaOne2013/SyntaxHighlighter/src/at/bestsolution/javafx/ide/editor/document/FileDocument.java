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
package at.bestsolution.javafx.ide.editor.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import at.bestsolution.javafx.ide.editor.Document;

public class FileDocument implements Document {
	private File f;
	private StringBuilder content;

	public FileDocument(File f) {
		this.f = f;
	}
	
	@Override
	public void insert(int start, String data) {
		content.insert(start, data);
	}
	
	@Override
	public void set(String data) {
		content = new StringBuilder(data);
	}

	@Override
	public String get() {
		if (content == null) {

			content = new StringBuilder();
			try (FileInputStream in = new FileInputStream(f);) {
				byte[] b = new byte[1024];
				int l = 0;
				while ((l = in.read(b)) != -1) {
					content.append(new String(b, 0, l));
				}
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return content != null ? content.toString() : null;
	}
}
