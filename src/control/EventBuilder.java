package control;

import exceptions.InvalidParametersException;
import ini.IniSection;
import model.Event;

/**
 * This class is a super class from the ones used by the Controller in order to parse the Events.
 * It has an abstract parse method, as all the classes that extend this one, use it in a specific way.
 * It also has methods in order to parse the different types of parameters used to create Events.
 * The _tag attribute represents the name of the Event to be parsed. 
 *
 */
public abstract class EventBuilder {

	protected String _tag;
	protected String[] _keys;
	protected String[] _defaultValues;
	
	EventBuilder(){
		_tag = null;
		_keys = null;
		_defaultValues = null;
	}

	public abstract Event parse(IniSection section) throws InvalidParametersException;
	
	public abstract String toString();
	
	public String template(){
		String str = "[" + _tag + "]" + "\n";
		int i = 0;
		for (String s : _keys){
			str += s;
			str += " = ";
			str += _defaultValues[i] + "\n";
			i++;
		}
		return str;
	}
	
	protected static int parseInt(IniSection section, String key) throws InvalidParametersException {
		String v = section.getValue(key);
		if (v != null)
			return Integer.parseInt(v);
		else
			throw new InvalidParametersException("Non-existent value for the key " + key);
	}
	
	protected static int parseInt(IniSection section, String key, int defaultValue) {
		String v = section.getValue(key);
		if (v != null)
			return Integer.parseInt(v);
		else
			return defaultValue;
	}
	
	protected static String[] parseArray(IniSection section, String key) throws InvalidParametersException {
		String i = section.getValue(key);
		if (i != null) {
			String[] array = i.split(",");
			for(int index = 0; index < array.length; index++) {
				if(!isValidKey(array[index]))
					throw new InvalidParametersException("The value " + array[index] + " for " + key + " is not a valid ID");
			}
			return array;
		}
		else
			throw new InvalidParametersException("Non-existent value for the key " + key);
	}
	
	protected static int parsePosInt(IniSection section, String key) throws InvalidParametersException {
		int i = parseInt(section, key);
		if(i <= 0)
			throw new InvalidParametersException("The value " + i + " for " + key + " should be positive");
		else
			return i;
	}
	
	protected static long parsePosLong(IniSection section, String key, long defaultValue) throws InvalidParametersException {
		String str = section.getValue(key);
		Long l;
		if (str != null) {
			 l = Long.parseLong(section.getValue(key));
			 if(l <= 0)
				 throw new InvalidParametersException("The value " + l + " for " + key + " should be positive");
			 else
				 return l;
		}
		else
			return defaultValue;
	}
	
	protected static int parseNonNegInt(IniSection section, String key, int defaultValue) throws InvalidParametersException {
		int i = parseInt(section, key, defaultValue);
		if(i < 0)
			throw new InvalidParametersException("The value " + i + " for " + key + " should not be negative");
		else
			return i;
	}
	
	protected static Double parseNonNegDouble(IniSection section, String key) throws InvalidParametersException {
		String str = section.getValue(key);
		Double d;
		if (str != null) {
			 d = Double.parseDouble(section.getValue(key));
			 if(d < 0)
				 throw new InvalidParametersException("The value " + d + " for " + key + " should not be negative");
			 else
				 return d;
		}
		else
			throw new InvalidParametersException("Non-existent value for the key " + key);
	}
	
	protected static String validID(IniSection section, String key) throws InvalidParametersException {
		String s = section.getValue(key);
		if(!isValidKey(s))
			throw new InvalidParametersException("The value " + s + " for " + key + " is not a valid ID");
		else
			return s;
	}
	
	private static boolean isValidKey(String id) {
		return id != null && id.matches("[a-z0-9A-Z_]+");
	}
}
