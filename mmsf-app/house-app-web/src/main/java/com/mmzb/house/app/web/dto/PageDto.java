package com.mmzb.house.app.web.dto;

public class PageDto {
	
	private boolean isEnd;

	/** 
     * 总条数
     */
    private int itemCount = 0;
    
    /**
     * 总页数
     */
    private int pageCount = 0;
    
    /**
     * 页大小
     */
    private int pageSize = 0;
    
    /**
     * 当前页
     */
    private int pageIndex = 0;

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
    
}
