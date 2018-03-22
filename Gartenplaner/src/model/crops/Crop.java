package model.crops;

import com.google.gson.annotations.SerializedName;

public class Crop {
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("description")
	private String description;
	
	@SerializedName("sun_requirements")
	private String sunRequirements;
	
	@SerializedName("sowing_method")
	private String sowingMethod;
	
	@SerializedName("spread")
	private int spread;
	
	@SerializedName("row_spacing")
	private int rowSpacing;
	
	@SerializedName("height")
	private int height;
	
	@SerializedName("guides_count")
	private int guidesCount;
	
	@SerializedName("main_image_path")
	private String imageUrl;

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

	public int getSpread() {
		return spread;
	}

	public void setSpread(int spread) {
		this.spread = spread;
	}

	public int getRowSpacing() {
		return rowSpacing;
	}

	public void setRowSpacing(int rowSpacing) {
		this.rowSpacing = rowSpacing;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getGuidesCount() {
		return guidesCount;
	}

	public void setGuidesCount(int guidesCount) {
		this.guidesCount = guidesCount;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "Crop [name=" + name + ", description=" + description + ", sunRequirements=" + sunRequirements
				+ ", sowingMethod=" + sowingMethod + ", spread=" + spread + ", rowSpacing=" + rowSpacing + ", height="
				+ height + ", guidesCount=" + guidesCount + ", imageUrl=" + imageUrl + "]";
	}
	
	

}
