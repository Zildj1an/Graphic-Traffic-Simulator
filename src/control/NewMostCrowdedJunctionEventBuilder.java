package control;

import exceptions.InvalidParametersException;
import ini.IniSection;
import model.Event;
import model.NewMostCrowdedJunctionEvent;

/**
 * This class is used by the Controller in order to parse the Events.
 * In this case, it checks whether the IniSection corresponds with a
 * new_junction Event of type "mc".
 *
 */
public class NewMostCrowdedJunctionEventBuilder extends EventBuilder{

	public NewMostCrowdedJunctionEventBuilder() {
		_tag = "new_junction";
		_keys = new String[] {"time", "id", "type"};
		_defaultValues = new String[] {"", "", "mc"};
	}
	
	public Event parse(IniSection section) throws InvalidParametersException {
		try {
			if(!section.getTag().equals(_tag) || section.getValue("type") == null || !section.getValue("type").equals("mc"))
				return null;
			else {
				return new  NewMostCrowdedJunctionEvent(EventBuilder.parseNonNegInt(section, "time", 0),
						EventBuilder.validID(section, "id"));
			}
		}
		catch(InvalidParametersException e) {
			throw new InvalidParametersException(_tag + " event not added. " + e.getMessage());
		}
	}
	
	public String toString() {
		return "New MC Junction";
	}
}
