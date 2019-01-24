package control;

import exceptions.InvalidParametersException;
import ini.IniSection;
import model.Event;
import model.MakeVehicleFaultyEvent;

/**
 * This class is used by the Controller in order to parse the Events.
 * In this case, it checks whether the IniSection corresponds with a
 * make_vehicle_faulty Event.
 *
 */
public class MakeVehicleFaultyEventBuilder extends EventBuilder{

	public MakeVehicleFaultyEventBuilder() {
		_tag = "make_vehicle_faulty";
		_keys = new String[] {"time", "vehicles", "duration"};
		_defaultValues = new String[] {"", "", ""};
	}
	
	public Event parse(IniSection section) throws InvalidParametersException {
		try {
			if(!section.getTag().equals(_tag))
				return null;
			else {
				return new MakeVehicleFaultyEvent(EventBuilder.parseNonNegInt(section, "time", 0),
						EventBuilder.parsePosInt(section, "duration"),
						EventBuilder.parseArray(section, "vehicles"));
			}
		}
		catch(InvalidParametersException e) {
			throw new InvalidParametersException(_tag + " event not added. " + e.getMessage());
		}
	}
	
	
	public String toString() {
		return "Make Vehicle Faulty";
	}
}

