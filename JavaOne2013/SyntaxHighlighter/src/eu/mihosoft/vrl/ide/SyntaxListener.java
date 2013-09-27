/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.ide;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TextArea;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.NotNull;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class SyntaxListener extends JavaBaseListener {

    private JavaParser parser;
    private TextArea editor;
    private JTextPane swingEditor;
    private Map<Integer, AttributeSet> attributes = new HashMap<>();
    
    private AttributeSet defaultAttribute = new SimpleAttributeSet();

    public SyntaxListener(JavaParser parser, TextArea editor, JTextPane swingEditor) {
        this.parser = parser;
        this.editor = editor;
        this.swingEditor = swingEditor;

        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, Color.GRAY);
        attributes.put(JavaParser.COMMENT, set);
        attributes.put(JavaParser.LINE_COMMENT, set);

        set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, Color.orange);
        attributes.put(JavaParser.StringLiteral, set);

        set = new SimpleAttributeSet();
        StyleConstants.setBold(set, true);
        attributes.put(JavaParser.CLASS, set);
    }

    @Override
    public void enterCompilationUnit(@NotNull JavaParser.CompilationUnitContext ctx) {
        TokenStream tokens = parser.getTokenStream();

        for (int i = 0; i < tokens.size(); i++) {
//            System.out.print("token: " + tokens.get(i).getText());

            Token t = tokens.get(i);

            int tokenType = t.getType();




//                System.out.println(" --> type: " + tokenType + ": " + JavaParser.tokenNames[tokenType]);
            
            AttributeSet set = attributes.get(tokenType);
            
            if (set!=null) {
//                set = defaultAttribute;
                
                  swingEditor.getStyledDocument().setCharacterAttributes(
                        t.getStartIndex(), t.getStopIndex() - t.getStartIndex() + 1, set, true);
            }
            
          

//            if (tokenType == JavaParser.CLASS) {
//                StyleConstants.setBold(set, true);
//                swingEditor.getStyledDocument().setCharacterAttributes(
//                        t.getStartIndex(), t.getStopIndex() - t.getStartIndex() + 1, set, true);
//
//            } else if (tokenType == JavaParser.COMMENT || tokenType == JavaParser.LINE_COMMENT) {
//                StyleConstants.setForeground(set, Color.GRAY);
//                swingEditor.getStyledDocument().setCharacterAttributes(
//                        t.getStartIndex(), t.getStopIndex() - t.getStartIndex() + 1, set, true);
//            } else if (tokenType == JavaParser.StringLiteral) {
//                StyleConstants.setForeground(set, Color.ORANGE);
//                swingEditor.getStyledDocument().setCharacterAttributes(
//                        t.getStartIndex(), t.getStopIndex() - t.getStartIndex() + 1, set, true);
//
//            } else {
//                swingEditor.getStyledDocument().setCharacterAttributes(
//                        t.getStartIndex(), t.getStopIndex() - t.getStartIndex() + 1, null, true);
//            }




//            System.out.println("START: " + t.getStartIndex() + ", STOP: " + t.getStopIndex());



//        String code = tokens.getText(
//                ctx.Identifier().getSourceInterval());
//
//        System.out.println("CODE: " + code);

            //
        }
    }
}
