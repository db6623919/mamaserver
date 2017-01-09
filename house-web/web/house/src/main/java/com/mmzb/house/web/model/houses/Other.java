package com.mmzb.house.web.model.houses;

/**
 * 其它
 * @author dengbiao
 *
 */
public class Other {

	private int cook;//可做饭:1提供，0 不提供
	private int party;//适合聚会:1提供，0 不提供
	private int smoking;//可吸烟:1提供，0 不提供
	private int pet;//可带宠物:1提供，0 不提供
	private int extrabed;//可加床:1提供，0 不提供
	private int guests;//接待外宾:1提供，0 不提供
	private int breakfast;//提供早餐:1提供，0 不提供
	private int childrenstay;//欢迎孩子入住:1提供，0 不提供
	
	public int getCook() {
		return cook;
	}
	public void setCook(int cook) {
		this.cook = cook;
	}
	public int getParty() {
		return party;
	}
	public void setParty(int party) {
		this.party = party;
	}
	public int getSmoking() {
		return smoking;
	}
	public void setSmoking(int smoking) {
		this.smoking = smoking;
	}
	public int getPet() {
		return pet;
	}
	public void setPet(int pet) {
		this.pet = pet;
	}
	public int getExtrabed() {
		return extrabed;
	}
	public void setExtrabed(int extrabed) {
		this.extrabed = extrabed;
	}
	public int getGuests() {
		return guests;
	}
	public void setGuests(int guests) {
		this.guests = guests;
	}
	public int getBreakfast() {
		return breakfast;
	}
	public void setBreakfast(int breakfast) {
		this.breakfast = breakfast;
	}
	public int getChildrenstay() {
		return childrenstay;
	}
	public void setChildrenstay(int childrenstay) {
		this.childrenstay = childrenstay;
	}
	
}