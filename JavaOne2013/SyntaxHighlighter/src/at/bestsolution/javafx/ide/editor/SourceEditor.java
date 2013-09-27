/**
 * *****************************************************************************
 * Copyright (c) 2012 BestSolution.at and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Tom Schindl<tom.schindl@bestsolution.at> - initial API and
 * implementation
 ******************************************************************************
 */
package at.bestsolution.javafx.ide.editor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//import org.eclipse.osgi.service.urlconversion.URLConverter;
//import org.osgi.framework.Bundle;
//import org.osgi.framework.BundleContext;
//import org.osgi.framework.FrameworkUtil;
//import org.osgi.framework.InvalidSyntaxException;
//import org.osgi.framework.ServiceReference;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import netscape.javascript.JSObject;
import at.bestsolution.javafx.ide.editor.js.JavaScriptNCICallback;
import at.bestsolution.javafx.ide.editor.orion.editor.Editor;
import at.bestsolution.javafx.ide.editor.orion.editor.impl.ContentAssistImpl;
import at.bestsolution.javafx.ide.editor.orion.editor.impl.EditorImpl;
import at.bestsolution.javafx.ide.editor.orion.textview.ModelChangedEvent;
import com.sun.javafx.css.converters.URLConverter;
import java.net.MalformedURLException;

@SuppressWarnings("restriction")
public class SourceEditor extends BorderPane {

    private WebView webView;
    private Editor editor;
    private Document document;
    private Runnable runnable;
    private ContentProposalComputer computer;
    private List<ProblemMarker> markers = new ArrayList<ProblemMarker>();

    public class LoadEditorCallback implements JavaScriptNCICallback<JSObject> {

        private WebEngine engine;

        public LoadEditorCallback(WebEngine engine) {
            this.engine = engine;
        }

        @Override
        public void initJava(JSObject jsObject) {
            EditorImpl e = new EditorImpl(engine, jsObject);
            initEditor(e);
        }
    }

    public class LoadContentAssisCallback implements JavaScriptNCICallback<JSObject> {

        @Override
        public void initJava(JSObject jsObject) {
            ContentProposalComputer delegate = new ContentProposalComputer() {
                @Override
                public List<Proposal> computeProposals(String line, String prefix,
                        int offset) {
                    if (computer != null) {
                        return computer.computeProposals(line, prefix, offset);
                    } else {
                        return Collections.emptyList();
                    }
                }
            };
            new ContentAssistImpl(delegate, jsObject);
        }
    }

    public class SaveRunnable implements Runnable {

        @Override
        public void run() {
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public SourceEditor() {
        this.webView = new WebView();
        this.webView.setFontSmoothingType(FontSmoothingType.LCD);
        final WebEngine engine = this.webView.getEngine();

        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> observable,
                    State oldValue, State newValue) {
                if (newValue == State.SUCCEEDED) {
                    JSObject win = (JSObject) engine.executeScript("window");
                    win.setMember("javaEditor", new LoadEditorCallback(engine));
                    win.setMember("javaContentAssist", new LoadContentAssisCallback());
                }
            }
        });

        try {
//			Bundle b = FrameworkUtil.getBundle(getClass());
//			URL rootEntry = b.getEntry("/");
//			URLConverter converter = getURLConverter(b.getBundleContext(), rootEntry);
//			rootEntry = converter.resolve(rootEntry);
//                      File installLocation = new File(rootEntry.getPath());

            File installLocation = new File(".");
            File html = new File(installLocation, "orion/html/editor.html");
            
            engine.load(html.toURL().toString());
            engine.setOnAlert(new EventHandler<WebEvent<String>>() {
                @Override
                public void handle(WebEvent<String> event) {
                    System.err.println("This is an alert: " + event);
                }
            });
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        setCenter(webView);
    }

//	void initContentAssist(Object o) {
//		System.err.println(o);
//	}
//	private static URLConverter getURLConverter(BundleContext context, URL url) {
//		String protocol = url.getProtocol();
//		String FILTER_PREFIX = "(&(objectClass=" + URLConverter.class.getName() + ")(protocol="; //$NON-NLS-1$ //$NON-NLS-2$
//		String FILTER_POSTFIX = "))"; //$NON-NLS-1$
//		try {
//			Collection<ServiceReference<URLConverter>> refs;
//			refs = context.getServiceReferences(URLConverter.class, FILTER_PREFIX + protocol + FILTER_POSTFIX);
//			if (!refs.isEmpty()) {
//				return context.getService(refs.iterator().next());
//			}
//		} catch (InvalidSyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return null;
//	}
    public void setProblemMarkers(List<ProblemMarker> markers) {
        this.markers.clear();
        this.markers.addAll(markers);
        if (editor != null) {
            editor.showProblems(this.markers);
        }
    }

    void initEditor(final Editor editor) {
        this.editor = editor;

        if (document != null) {
            editor.setInput("myclass.java", null, document.get());
        }

        this.editor.getTextView().addEventListener("ModelChanged", new Callback<ModelChangedEvent, Void>() {
            @Override
            public Void call(ModelChangedEvent param) {
                if (param.getRemovedCharCount() == 0 && param.getRemovedLineCount() == 0) {
                    int start = param.getStart();
                    int end = start + param.getAddedCharCount();
                    document.insert(param.getStart(), SourceEditor.this.editor.getTextView().getText(start, end));
                } else {
                    document.set(SourceEditor.this.editor.getTextView().getText());
                }
                return null;
            }
        });

        this.editor.setAction("Save", new SaveRunnable());
        if (!this.markers.isEmpty()) {
            this.editor.showProblems(markers);
        }
    }

    public void setContentProposalComputer(ContentProposalComputer computer) {
        this.computer = computer;
    }

    public void setDocument(Document document) {
        this.document = document;
        if (editor != null) {
            editor.setInput("myclass.java", null, document.get());
        }
    }

    public void setSaveCallback(Runnable runnable) {
        this.runnable = runnable;
    }
}
