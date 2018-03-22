package model.nutrition.search;

import com.google.gson.annotations.SerializedName;

public class NutritionDataListItem {
	
	@SerializedName("offset")
	private int entry;
	
	@SerializedName("group")
	private String group;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("ndbno")
	private String id;
	
	

	public int getEntry() {
		return entry;
	}

	public void setEntry(int entry) {
		this.entry = entry;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "NutritionDataListItem [entry=" + entry + ", group=" + group + ", name=" + name + ", id=" + id + "]";
	}
	
	
	

}
