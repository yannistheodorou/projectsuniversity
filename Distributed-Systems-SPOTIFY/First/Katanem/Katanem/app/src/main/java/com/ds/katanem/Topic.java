package com.ds.katanem;
import java.io.Serializable;

public class Topic implements Serializable {
	public String lineCode;
	public String lineId;
	public String description;

	public Topic(String lineCode, String lineId, String description) {
		this.lineCode = lineCode;
		this.lineId = lineId;
		this.description = description;
	}

	public Topic() {
		this("", "", "");
	}
	
	@Override
	public String toString() {
		return "LineCode: " + lineCode + " LineID: " + lineId + " Description: " + description;
	}
	
	@Override
	public int hashCode() {
		return lineId.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (o == this)
			return true;
		
		if (!(o instanceof Topic))
			return false;
		
		Topic t = (Topic) o;
		
		return lineCode.equals(t.lineCode);

	}
}
