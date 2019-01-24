package control;

import exceptions.InvalidParametersException;
import ini.IniSection;
import model.Event;
import model.NewLanesRoadEvent;

/**
 * This class is used by the Controller in order to parse the Events.
 * In this case, it checks whether the IniSection corresponds with a
 * new_road Event of type "lanes".
 *
 */
public class NewLanesRoadEventBuilder extends EventBuilder{

	public NewLanesRoadEventBuilder() {
		_tag = "new_road";
		_keys = new String[] {"time", "id", "src", "dest", "max_speed", "length", "type", "lanes"};
		_defaultValues = new String[] {"", "", "", "", "", "", "lanes", ""};
	}
	
	public Event parse(IniSection section) throws InvalidParametersException {
		try {
			if(!section.getTag().equals(_tag) || section.getValue("type") == null || !section.getValue("type").equals("lanes"))
				return null;
			else {
				return new NewLanesRoadEvent(EventBuilder.parseNonNegInt(section, "time", 0),
						EventBuilder.validID(section, "id"),
						EventBuilder.validID(section, "src"),
						EventBuilder.validID(section, "dest"),
						EventBuilder.parsePosInt(section, "max_speed"),
						EventBuilder.parsePosInt(section, "length"),
						EventBuilder.parsePosInt(section, "lanes"));
			}
		}
		catch(InvalidParametersException e) {
			throw new InvalidParametersException(_tag + " event not added. " + e.getMessage());
		}
	}
	
	public String toString() {
		return "New Lanes Road";
	}
}