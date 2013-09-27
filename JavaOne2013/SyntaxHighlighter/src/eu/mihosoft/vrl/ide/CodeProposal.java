/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.ide;

import at.bestsolution.javafx.ide.editor.ContentProposalComputer;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class CodeProposal {
    private ContentProposalComputer.Type type;
    private String code;
    private String infoText;

    public CodeProposal(ContentProposalComputer.Type type, String infoText, String code) {
        this.type = type;
        this.code = code;
        this.infoText = infoText;
    }

    /**
     * @return the type
     */
    public ContentProposalComputer.Type getType() {
        return type;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the infoText
     */
    public String getInfoText() {
        return infoText;
    }


}
