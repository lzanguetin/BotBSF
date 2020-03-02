package br.com.voxage.botbsf.models;

import java.util.HashMap;

public class TransferProp {
	private HashMap <String, String> subjectIds;
	private HashMap <String, String> groups;
	
	public HashMap<String, String> getGroups() {
		return groups;
	}
	
	public void setGroups(HashMap<String, String> groups) {
		this.groups = groups;
	}
	
	public HashMap <String, String> getSubjectIds() {
		return subjectIds;
	}
	
	public void setSubjectIds(HashMap <String, String> subjectIds) {
		this.subjectIds = subjectIds;
	}
}
