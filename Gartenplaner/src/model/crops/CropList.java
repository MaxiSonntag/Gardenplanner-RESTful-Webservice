package model.crops;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class CropList {
	
	@SerializedName("data")
	private ArrayList<CropListItem> listItem;

	public ArrayList<CropListItem> getListItem() {
		return listItem;
	}

	public void setListItem(ArrayList<CropListItem> listItem) {
		this.listItem = listItem;
	}
	
	@Override
	public String toString() {
		return "CropList [listItem=" + listItem + "]";
	}

}
