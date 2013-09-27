/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.ide;

import at.bestsolution.javafx.ide.editor.ContentProposalComputer;
import at.bestsolution.javafx.ide.editor.Document;
import at.bestsolution.javafx.ide.editor.SourceEditor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class VCodeEditor extends SourceEditor {

    private Document document = new DocumentImpl();
    
    private List<CodeProposal> customProposals = new ArrayList<>();

    public VCodeEditor() {
        setDocument(document);

        this.setContentProposalComputer(new ContentProposalComputer() {
            private List<ContentProposalComputer.Proposal> proposals = new ArrayList<>();

            @Override
            public List<ContentProposalComputer.Proposal> computeProposals(String line, String prefix, int offset) {
                proposals = new ArrayList<>();

                proposals.add(new ContentProposalComputer.Proposal(ContentProposalComputer.Type.TYPE, "GroovyShell", new ContentProposalComputer.StyledString("GroovyShell : parse & run scripts")));
                proposals.add(new ContentProposalComputer.Proposal(ContentProposalComputer.Type.TYPE, "Script", new ContentProposalComputer.StyledString("Script : a script object")));
                proposals.add(new ContentProposalComputer.Proposal(ContentProposalComputer.Type.METHOD, "System.out.println()", new ContentProposalComputer.StyledString("System.out.println() : print ...")));
                
                for (CodeProposal p : customProposals) {
                    proposals.add(
                            new ContentProposalComputer.Proposal(
                            p.getType(), p.getCode(),
                            new ContentProposalComputer.StyledString(p.getInfoText())));
                }
                
                return proposals;
            }
        });
    }
    
    public void addProposal(CodeProposal proposal) {
        customProposals.add(proposal);
    }

    public String getText() {
        return document.get();
    }

    public void setText(String s) {
        document.set(s);
        super.setDocument(document);
    }
}

class DocumentImpl implements Document {

    private String string = "";

    @Override
    public String get() {
        return string;
    }

    @Override
    public void set(String data) {
        this.string = data;
    }

    @Override
    public void insert(int start, String content) {
        this.string = this.string.substring(0, start) + content + this.string.substring(start);
    }
}
