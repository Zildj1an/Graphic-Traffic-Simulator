package control;

import exceptions.InvalidParametersException;
import ini.IniSection;
import model.Event;
import model.NewRoadEvent;

/**
 * This class is used by the Controller in order to parse the Events.
 * In this case, it checks whether the IniSection corresponds with a
 * new_road Event.
 *
 */
public class NewRoadEventBuilder extends EventBuilder{
	
	public NewRoadEventBuilder() {
		_tag = "new_road";
		_keys = new String[] {"time", "id", "src", "dest", "max_speed", "length"};
		_defaultValues = new String[] {"", "", "", "", "", ""};
	}
	
	public Event parse(IniSection section) throws InvalidParametersException {
		try {
			if(!section.getTag().equals(_tag) || section.getValue("type") != null)
				return null;
			else {
				return new NewRoadEvent(EventBuilder.parseNonNegInt(section, "time", 0),
						EventBuilder.validID(section, "id"),
						EventBuilder.validID(section, "src"),
						EventBuilder.validID(section, "dest"),
						EventBuilder.parsePosInt(section, "max_speed"),
						EventBuilder.parsePosInt(section, "length"));
			}
		}
		catch(InvalidParametersException e) {
			throw new InvalidParametersException(_tag + " event not added. " + e.getMessage());
		}
	}
	
	public String toString() {
		return "New Road";
	}
}