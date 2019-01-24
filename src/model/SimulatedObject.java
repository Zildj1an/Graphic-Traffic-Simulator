package model;

import ini.IniSection;

/**
 * This is the super class of all the Simulated Objects : Vehicles, Roads and Junctions.
 * They all have an _id attribute in order to be distinguished. 
 *
 */
public abstract class SimulatedObject {
	
	protected String _id;
	
	public SimulatedObject(String id){
		_id = id;
	}
	
	public String getId() {
		return _id;
	}
	
	public String toString() {
		return _id;
	}
	
	public String generateReport(int time) {
		IniSection is = new IniSection(this.getReportSectionTag());
		is.setValue("id", this._id); 
		is.setValue("time", time);
		this.fillReportDetails(is);
		return is.toString();
	}
	
	protected abstract void fillReportDetails(IniSection ini);
	
	protected abstract String getReportSectionTag();
	
	abstract void advance() throws SimulatorError;
}