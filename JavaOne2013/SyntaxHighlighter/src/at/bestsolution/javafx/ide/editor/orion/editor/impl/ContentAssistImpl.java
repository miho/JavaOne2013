package at.bestsolution.javafx.ide.editor.orion.editor.impl;

import netscape.javascript.JSObject;
import at.bestsolution.javafx.ide.editor.ContentProposalComputer;
import at.bestsolution.javafx.ide.editor.ContentProposalComputer.Proposal;
import at.bestsolution.javafx.ide.editor.ContentProposalComputer.Segment;
import at.bestsolution.javafx.ide.editor.ContentProposalComputer.StyledString;
import at.bestsolution.javafx.ide.editor.ContentProposalComputer.Type;
import at.bestsolution.javafx.ide.editor.orion.editor.ContentAssist;

@SuppressWarnings("restriction")
public class ContentAssistImpl implements ContentAssist {
	private ContentProposalComputer computer;
	
	public ContentAssistImpl(ContentProposalComputer computer, JSObject jsObject) {
		jsObject.setMember("__javaObject", this);
		this.computer = computer;
	}
	
	public String[] testMe() {
		return new String[] {"h1","h2"};
	}

	@Override
	public String getProposals(String line, String prefix, int offset) {
		StringBuilder b = new StringBuilder();
		b.append("[");
		boolean flag = false;
		for( Proposal p : computer.computeProposals(line, prefix, offset) ) {
			if(flag) {
				b.append(",\n");
			}
			String v = p.value;
			
			b.append("	{ ");
			b.append("\"proposal\": \""+v+"\" ");
			
			String description = "";
						
			description = "{";
			if( p.type == Type.TYPE ) {
				description += " \"icon\":  { \"src\":\"../js/editor/textview/class_obj.gif\"  }, ";
			} else if(p.type == Type.METHOD) {
				description += " \"icon\":  { \"src\":\"../js/editor/textview/methpub_obj.gif\"  }, ";
			} else if(p.type == Type.FIELD) {
				description += " \"icon\":  { \"src\":\"../js/editor/textview/field_public_obj.gif\"  }, ";
			}
			
			description += "\"segments\": " + toJSON(p.description) + "}";
			
			b.append(",\"description\":  "+description+" ");
			if( p.type == Type.METHOD ) {
				b.append(",\"escapePosition\": "+(offset+v.length()-1 - prefix.length())+" ");
			}
			b.append(", \"style\": \"attributedString\"");
			b.append(", \"replace\":  true");
			
			b.append("}");
			flag = true;
		}
		b.append("]");
		
		return b.toString();
	}

	private static String toJSON(StyledString s) {
		StringBuilder b = new StringBuilder();
		b.append("[");
		boolean flag = false;
		for( Segment segment : s.getList() ) {
			if( flag ) {
				b.append(",");
			}
			b.append("{");
			b.append("\"value\":\"" +segment.value+"\"");
			if( segment.style != null ) {
				b.append(", \"style\": {");
				b.append("\"bold\":  " + segment.style.bold + " ");
				b.append(", \"italic\": " + segment.style.italic + " ");
				if(segment.style.color != null) {
					b.append(", \"color\":\"" + segment.style.color + "\"");
				}
				if(segment.style.fontname != null) {
					b.append(", \"fontName\":\"" + segment.style.fontname + "\"");
				}
				b.append("");
				b.append("}");
			}
			b.append("}");
			flag = true;
		}
		b.append("]");
		return b.toString();
	}
}
