package at.bestsolution.javafx.ide.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface ContentProposalComputer {
	enum Type {
		METHOD,
		FIELD,
		TYPE
	}
	
	public static class StyledString {
		private List<Segment> list = new ArrayList<Segment>();
		
		public StyledString(String value) {
			list.add(new Segment(value, null));
		}
		
		public void appendString(String value) {
			appendString(value, null);
		}
		
		public void appendString(String value, Style style) {
			list.add(new Segment(value, style));
		}
		
		public List<Segment> getList() {
			return Collections.unmodifiableList(list);
		}
		
		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			for( Segment s : list ) {
				b.append(s.value);
			}
			return b.toString();
		}
	}
	
	public static class Style {
		public final String fontname;
		public final String color;
		public final boolean bold;
		public final boolean italic;
		
		public Style(String fontname, String color, boolean bold, boolean italic) {
			this.fontname = fontname;
			this.color = color;
			this.bold = bold;
			this.italic = italic;
		}
		
		public static Style bold() {
			return new Style(null, null, true, false);
		}
		
		public static Style italic() {
			return new Style(null, null, false, true);
		}
		
		public static Style colored(String color) {
			return new Style(null, color, false, false);
		}
	}
	
	public static class Segment {
		public final String value;
		public final Style style;
		
		public Segment(String value, Style style) {
			this.value = value;
			this.style = style;
		}
	}
	
	
	
	public static class Proposal {
		public final Type type;
		public final String value;
		public final StyledString description;
		
		public Proposal(Type type, String value, StyledString description) {
			this.type = type;
			this.value = value;
			this.description = description;
		}
	}
	
	public List<Proposal> computeProposals(String line, String prefix, int offset);
}
