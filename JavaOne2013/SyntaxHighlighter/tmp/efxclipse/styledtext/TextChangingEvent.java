package at.bestsolution.efxclipse.styledtext;


public class TextChangingEvent {

	public final StyledTextContent source;
	
	public int offset;
	public int replaceCharCount;
	public final int replaceLineCount;
	public final String newText;
	public final int newCharCount;
	public final int newLineCount;
	
	private TextChangingEvent(StyledTextContent source,int offset, int replaceCharCount, int replaceLineCount, String newText, int newCharCount, int newLineCount) {
		this.source = source;
		this.offset = offset;
		this.replaceCharCount = replaceCharCount;
		this.replaceLineCount = replaceLineCount;
		this.newText = newText;
		this.newCharCount = newCharCount;
		this.newLineCount = newLineCount;
	}
	
	public static TextChangingEvent textChanging(StyledTextContent source, int offset, int replaceCharCount, int replaceLineCount, String newText, int newCharCount, int newLineCount) {
		return new TextChangingEvent(source, offset, replaceCharCount, replaceLineCount, newText, newCharCount, newLineCount);
	}
}
