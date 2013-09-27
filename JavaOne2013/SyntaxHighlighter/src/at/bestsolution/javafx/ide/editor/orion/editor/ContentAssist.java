package at.bestsolution.javafx.ide.editor.orion.editor;


public interface ContentAssist {
	public String getProposals(String line, String prefix, int offset);
}
