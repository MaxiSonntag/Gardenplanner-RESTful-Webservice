package model.outputModel;

import com.google.gson.annotations.SerializedName;

public class CropOutputModel {
	
	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("sun_requirements")
	private String sunRequirements;
	@SerializedName("sowing_method")
	private String sowingMethod;
	@SerializedName("height")
	private int height;
	@SerializedName("main_image_path")
	private String imageUrl;
	
	
	public CropOutputModel(String name, String description, String sunRequirements, String sowingMethod,
			int height, String imageUrl) {
		super();
		this.name = name;
		this.description = description;
		this.sunRequirements = sunRequirements;
		this.sowingMethod = sowingMethod;
		this.height = height;
		this.imageUrl = imageUrl;
	}


	public CropOutputModel() {
		super();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getSunRequirements() {
		return sunRequirements;
	}


	public void setSunRequirements(String sunRequirements) {
		this.sunRequirements = sunRequirements;
	}


	public String getSowingMethod() {
		return sowingMethod;
	}


	public void setSowingMethod(String sowingMethod) {
		this.sowingMethod = sowingMethod;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	
	

}
