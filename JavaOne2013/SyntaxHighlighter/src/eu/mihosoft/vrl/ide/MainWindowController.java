/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.ide;

import at.bestsolution.javafx.ide.editor.ContentProposalComputer;
import at.bestsolution.javafx.ide.editor.Document;
import at.bestsolution.javafx.ide.editor.SourceEditor;
import groovy.lang.GroovyClassLoader;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooserBuilder;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilationUnit.PrimaryClassNodeOperation;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.Phases;
import org.codehaus.groovy.control.SourceUnit;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class MainWindowController implements Initializable {

    File currentDocument;
    @FXML
    TextArea editor;
    @FXML
    Pane view;
    private Pane rootPane;
//    private StyledTextArea styledEditor;
//    private SwingNode editorContainer = new SwingNode();
    private JTextPane swingEditor = new JTextPane();
    private VCodeEditor sourceEditor = new VCodeEditor();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        System.out.println("Init");
//
//        ScalableContentPane canvas = new ScalableContentPane();
//        canvas.setStyle("-fx-background-color: rgb(0,0, 0)");
//        view.getChildren().add(canvas);
//
//        Pane root = new Pane();
//        canvas.setContentPane(root);
//        root.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(10,32,60), rgb(42,52,120));");
//
//        rootPane = root;
//
//        flow = FlowFactory.newFlow();
//        styledEditor = new StyledTextArea();
//        styledEditor.getContent().setText("\nTEST\n");
//        view.getChildren().add(styledEditor);

//        try {
//            SwingUtilities.invokeAndWait(new Runnable() {
//                @Override
//                public void run() {
//                    
//                    editorContainer.setContent(swingEditor);
//                }
//            });
//        } catch (InterruptedException ex) {
//            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvocationTargetException ex) {
//            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        view.getChildren().add(editorContainer);
//
//        class KeyStrokeListener implements KeyListener {
//
//            public void keyPressed(KeyEvent e) {
//                
//            }
//
//            public void keyTyped(KeyEvent e) {
//                
//            }
//
//            public void keyReleased(KeyEvent e) {
//                updateView();
//            }
//        }
//
//
//
//        KeyStrokeListener keyboardListener = new KeyStrokeListener();
//
//        swingEditor.addKeyListener(keyboardListener);
        
//        final Document document = new Document() {
//            private String string = "";
//
//            @Override
//            public String get() {
//                return string;
//            }
//
//            @Override
//            public void set(String data) {
//                this.string = data;
//            }
//
//            @Override
//            public void insert(int start, String content) {
//                this.string = this.string.substring(0, start) + content + this.string.substring(start);
//            }
//        };

//        sourceEditor.setDocument(document);

        sourceEditor.setContentProposalComputer(new ContentProposalComputer() {
            private List<ContentProposalComputer.Proposal> proposals = new ArrayList<>();

            @Override
            public List<ContentProposalComputer.Proposal> computeProposals(String line, String prefix, int offset) {
                proposals = new ArrayList<>();

                proposals.add(new Proposal(Type.TYPE, "MyClass1", new StyledString("MyClass1 : My Class1 ...")));
                proposals.add(new Proposal(Type.TYPE, "MyClass2", new StyledString("MyClass2 : My Class2 ...")));
                proposals.add(new Proposal(Type.TYPE, "MyClass3", new StyledString("MyClass3 : My Class3 ...")));
                
                proposals.add(new Proposal(Type.METHOD, "System.out.println()", new StyledString("System.out.println() : print ...")));

                System.out.println("SOUT: " + sourceEditor.getText());

                return proposals;
            }
        });

        view.getChildren().add(sourceEditor);

    }

//    @FXML
//    public void onKeyTyped(KeyEvent evt) {
//        //
//    }
    @FXML
    public void onLoadAction(ActionEvent e) {
        loadTextFile(null);
    }

    @FXML
    public void onSaveAction(ActionEvent e) {
        saveDocument(false);
        updateView();
    }

    private void saveDocument(boolean askForLocationIfAlreadyOpened) {

        if (askForLocationIfAlreadyOpened || currentDocument == null) {
            FileChooser.ExtensionFilter mdFilter =
                    new FileChooser.ExtensionFilter("Text Files (*.groovy, *.txt)", "*.groovy", "*.txt");

            FileChooser.ExtensionFilter allFilesfilter =
                    new FileChooser.ExtensionFilter("All Files (*.*)", "*.*");

            currentDocument =
                    FileChooserBuilder.create().title("Save Groovy File").
                    extensionFilters(mdFilter, allFilesfilter).build().
                    showSaveDialog(null).getAbsoluteFile();
        }

        try (FileWriter fileWriter = new FileWriter(currentDocument)) {
            fileWriter.write(swingEditor.getText());
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

    }

    private void insertStringAtCurrentPosition(String s) {
        editor.insertText(editor.getCaretPosition(), s);
    }

    @FXML
    public void onSaveAsAction(ActionEvent e) {
        saveDocument(true);
        updateView();
    }

    @FXML
    public void onCloseAction(ActionEvent e) {
    }

    void loadTextFile(File f) {

        try {
            if (f == null) {
                FileChooser.ExtensionFilter mdFilter =
                        new FileChooser.ExtensionFilter("Text Files (*.groovy, *.txt)", "*.groovy", "*.txt");

                FileChooser.ExtensionFilter allFilesfilter =
                        new FileChooser.ExtensionFilter("All Files (*.*)", "*.*");

                currentDocument =
                        FileChooserBuilder.create().title("Open Groovy File").
                        extensionFilters(mdFilter, allFilesfilter).build().
                        showOpenDialog(null).getAbsoluteFile();
            } else {
                currentDocument = f;
            }

            List<String> lines =
                    Files.readAllLines(Paths.get(currentDocument.getAbsolutePath()),
                    Charset.defaultCharset());

            String document = "";

            for (String l : lines) {
                document += l + "\n";
            }

            editor.setText(document);
            swingEditor.setText(document);

            updateView();

        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    private void updateView() {

//        if (rootPane == null) {
//            System.err.println("UI NOT READY");
//            return;
//        }

//        GroovyHighlighter gcl = new GroovyHighlighter();
//
//
//        gcl.parseClass(editor.getText());

//        System.out.println("UPDATE UI");

        ANTLRHighlighter highlighter = new ANTLRHighlighter();
        highlighter.highlight(editor, swingEditor);

    }
}

class ANTLRHighlighter {

    public void highlight(TextArea editor, JTextPane swingEditor) throws RecognitionException {

//        InputStream is = null;
//
//        if (inputFile != null && Files.isRegularFile(inputFile)) {
//            is = Files.newInputStream(inputFile);
//        }

        ANTLRInputStream input = new ANTLRInputStream(swingEditor.getText());

        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParserRuleContext tree = parser.compilationUnit();

        ParseTreeWalker walker = new ParseTreeWalker();
        SyntaxListener extractor = new SyntaxListener(parser, editor, swingEditor);
        walker.walk(extractor, tree);

    }
}

class GroovyHighlighter extends GroovyClassLoader {

    private TextArea editor;
    private JTextPane swingEditor;

    public GroovyHighlighter() {
        //
    }

    public void highlightCode(TextArea editor, JTextPane swingEditor) {
        String code = editor.getText();
        this.editor = editor;
        this.swingEditor = swingEditor;
        super.parseClass(code);

    }

    @Override
    protected CompilationUnit createCompilationUnit(CompilerConfiguration config, CodeSource source) {
        CompilationUnit cu = super.createCompilationUnit(config, source);
        cu.addPhaseOperation(new PrimaryClassNodeOperation() {
            @Override
            public void call(SourceUnit source, GeneratorContext context, ClassNode classNode) throws CompilationFailedException {
                for (ClassNode cN : source.getAST().getClasses()) {
                    System.out.println("cls: " + cN.getName());
                }
            }
        }, Phases.CONVERSION);



        return cu;
    }
}
