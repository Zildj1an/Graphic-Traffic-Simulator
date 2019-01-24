package control;

import exceptions.InvalidParametersException;
import ini.IniSection;
import model.Event;
import model.NewJunctionEvent;

/**
 * This class is used by the Controller in order to parse the Events.
 * In this case, it checks whether the IniSection corresponds with a
 * new_junction Event.
 *
 */
public class NewJunctionEventBuilder extends EventBuilder{

	public NewJunctionEventBuilder() {
		_tag = "new_junction";
		_keys = new String[] {"time", "id"};
		_defaultValues = new String[] {"", ""};
	}
	
	public Event parse(IniSection section) throws InvalidParametersException {
		try {
			if(!section.getTag().equals(_tag) || section.getValue("type") != null)
				return null;
			else {
				return new NewJunctionEvent(EventBuilder.parseNonNegInt(section, "time", 0),
						EventBuilder.validID(section, "id"));
			}
		}
		catch(InvalidParametersException e) {
			throw new InvalidParametersException(_tag + " event not added. " + e.getMessage());
		}
	}
	
	public String toString() {
		return "New Junction";
	}
}
