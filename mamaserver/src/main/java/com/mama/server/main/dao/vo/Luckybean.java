package com.mama.server.main.dao.vo;

/**
 * 中奖率
 * @author whl
 *
 */
public class Luckybean {

	private int beanNums;
	private int probability;
	private int randomNumRange;
	private String note;
	
	public int getBeanNums() {
		return beanNums;
	}
	public void setBeanNums(int beanNums) {
		this.beanNums = beanNums;
	}
	public int getProbability() {
		return probability;
	}
	public void setProbability(int probability) {
		this.probability = probability;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getRandomNumRange() {
		return randomNumRange;
	}
	public void setRandomNumRange(int randomNumRange) {
		this.randomNumRange = randomNumRange;
	}
	
	
	
	
}
