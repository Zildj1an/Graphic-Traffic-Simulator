package control;

import exceptions.InvalidParametersException;
import ini.IniSection;
import model.Event;
import model.NewRoundRobinJunctionEvent;

/**
 * This class is used by the Controller in order to parse the Events.
 * In this case, it checks whether the IniSection corresponds with a
 * new_junction Event of type "rr".
 *
 */
public class NewRoundRobinJunctionEventBuilder extends EventBuilder{

	public NewRoundRobinJunctionEventBuilder() {
		_tag = "new_junction";
		_keys = new String[] {"time", "id", "type", "queues"};
		_defaultValues = new String[] {"", "", "rr", ""};
	}
	
	public Event parse(IniSection section) throws InvalidParametersException {
		try {
			if(!section.getTag().equals(_tag) || section.getValue("type") == null || !section.getValue("type").equals("rr"))
				return null;
			else {
				return new NewRoundRobinJunctionEvent(EventBuilder.parseNonNegInt(section, "time", 0),
						EventBuilder.validID(section, "id"),
						EventBuilder.parsePosInt(section, "max_time_slice"),
						EventBuilder.parsePosInt(section, "min_time_slice"));
			}
		}
		catch(InvalidParametersException e) {
			throw new InvalidParametersException(_tag + " event not added. " + e.getMessage());
		}
	}
	
	
	public String toString() {
		return "New RR Junction";
	}
}