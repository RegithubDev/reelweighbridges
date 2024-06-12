package com.resustainability.reisp.model;

public class AttOutputModel {

	private String employee_id,employee_name,contact_no,punchIn_loc,punchOut_loc,dateIn,dateOut,timeIn,timeOut,hours_between_punches,minutes_between_punches;

	@Override
	public String toString() {
		return "AttOutputModel [employee_id=" + employee_id + ", employee_name=" + employee_name + ", contact_no="
				+ contact_no + ", punchIn_loc=" + punchIn_loc + ", punchOut_loc=" + punchOut_loc + ", dateIn=" + dateIn
				+ ", dateOut=" + dateOut + ", timeIn=" + timeIn + ", timeOut=" + timeOut + ", hours_between_punches="
				+ hours_between_punches + ", minutes_between_punches=" + minutes_between_punches + "]";
	}
	public AttOutputModel() {
		
	}
	public AttOutputModel(String employee_id, String employee_name, String contact_no, String punchIn_loc,
			String punchOut_loc, String dateIn, String dateOut, String timeIn, String timeOut,
			String hours_between_punches, String minutes_between_punches) {
		super();
		this.employee_id = employee_id;
		this.employee_name = employee_name;
		this.contact_no = contact_no;
		this.punchIn_loc = punchIn_loc;
		this.punchOut_loc = punchOut_loc;
		this.dateIn = dateIn;
		this.dateOut = dateOut;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
		this.hours_between_punches = hours_between_punches;
		this.minutes_between_punches = minutes_between_punches;
	}


	public String getHours_between_punches() {
		return hours_between_punches;
	}
	public void setHours_between_punches(String hours_between_punches) {
		this.hours_between_punches = hours_between_punches;
	}
	public String getMinutes_between_punches() {
		return minutes_between_punches;
	}
	public void setMinutes_between_punches(String minutes_between_punches) {
		this.minutes_between_punches = minutes_between_punches;
	}
	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getContact_no() {
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

	public String getPunchIn_loc() {
		return punchIn_loc;
	}

	public void setPunchIn_loc(String punchIn_loc) {
		this.punchIn_loc = punchIn_loc;
	}

	public String getPunchOut_loc() {
		return punchOut_loc;
	}

	public void setPunchOut_loc(String punchOut_loc) {
		this.punchOut_loc = punchOut_loc;
	}

	public String getDateIn() {
		return dateIn;
	}

	public void setDateIn(String dateIn) {
		this.dateIn = dateIn;
	}

	public String getDateOut() {
		return dateOut;
	}

	public void setDateOut(String dateOut) {
		this.dateOut = dateOut;
	}

	public String getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	
	
}
