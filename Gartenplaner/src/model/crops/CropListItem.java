package model.crops;

import com.google.gson.annotations.SerializedName;

public class CropListItem {
	
	@SerializedName("id")
	private String id;
	
	@SerializedName("type")
	private String type;
	
	@SerializedName("attributes")
	private Crop crop;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	@Override
	public String toString() {
		return "CropListItem [id=" + id + ", type=" + type + ", crop=" + crop + "]";
	}
	
	

}
