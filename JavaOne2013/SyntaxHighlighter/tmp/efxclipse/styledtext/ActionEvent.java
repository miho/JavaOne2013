package at.bestsolution.efxclipse.styledtext;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class ActionEvent extends Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final EventType<ActionEvent> ACTION =
            new EventType<ActionEvent>(Event.ANY, "STYLED_TEXT_ACTION");
	
	public enum ActionType {
		DELETE_WORD_PREVIOUS,
		DELETE_WORD_NEXT,
		
		WORD_NEXT,
		WORD_PREVIOUS,
		
		LINE_START,
		LINE_END
	}
	
	public final ActionType type;
	
	public ActionEvent(Object source, EventTarget target, ActionType type) {
		super(source, target, ACTION);
		this.type = type;
	}
}
