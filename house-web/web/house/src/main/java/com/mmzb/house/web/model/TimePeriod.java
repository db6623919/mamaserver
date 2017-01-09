package com.mmzb.house.web.model;

import java.util.Calendar;

public class TimePeriod {

	private Calendar startDateTime;
	private Calendar endDateTime;
	public Calendar getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Calendar startDateTime) {
		this.startDateTime = startDateTime;
	}
	public Calendar getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(Calendar endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	
}
