package control;

import exceptions.InvalidParametersException;
import ini.IniSection;
import model.Event;
import model.NewVehicleEvent;

/**
 * This class is used by the Controller in order to parse the Events.
 * In this case, it checks whether the IniSection corresponds with a
 * new_vehicle Event.
 *
 */
public class NewVehicleEventBuilder extends EventBuilder {

	public NewVehicleEventBuilder() {
		_tag = "new_vehicle";
		_keys = new String[] {"time", "id", "max_speed", "itinerary"};
		_defaultValues = new String[] {"", "", "", ""};
	}
	
	public Event parse(IniSection section) throws InvalidParametersException {
		try {
			if(!section.getTag().equals(_tag) || section.getValue("type") != null)
				return null;
			else {
				return new NewVehicleEvent(EventBuilder.parseNonNegInt(section, "time", 0),
						EventBuilder.validID(section, "id"),
						EventBuilder.parsePosInt(section, "max_speed"),
						EventBuilder.parseArray(section, "itinerary"));
			}
		}
		catch(InvalidParametersException e) {
			throw new InvalidParametersException(_tag + " event not added. " + e.getMessage());
		}
	}
	
	public String toString() {
		return "New Vehicle";
	}
}
