package at.bestsolution.efxclipse.styledtext;


public class TextChangedEvent {

	public final StyledTextContent source;
	
	public final int offset;
	public final int replaceCharCount;
	public final int replaceLineCount;
	public final String newText;
	public final int newCharCount;
	public final int newLineCount;
	
	private TextChangedEvent(StyledTextContent source,int offset, int replaceCharCount, int replaceLineCount, String newText, int newCharCount, int newLineCount) {
		this.source = source;
		this.offset = offset;
		this.replaceCharCount = replaceCharCount;
		this.replaceLineCount = replaceLineCount;
		this.newText = newText;
		this.newCharCount = newCharCount;
		this.newLineCount = newLineCount;
	}
	
	public static TextChangedEvent textChanged(StyledTextContent source) {
		return new TextChangedEvent(source, 0, 0, 0, null, 0, 0);
	}
	
	public static TextChangedEvent textSet(StyledTextContent source) {
		return new TextChangedEvent(source, 0, 0, 0, null, 0, 0);
	}
	
}
