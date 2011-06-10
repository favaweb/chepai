package com.hovto.chepai.tool;

public class Page {

	private int currentPage;
	private int lastPage; 
	private int pageSize = 22;
	
	
	public int getCurrentPage() {
		if(currentPage <= 1) {
			this.currentPage = 1;
			return currentPage;
		}
		if(currentPage > lastPage && lastPage != 0) {
			this.currentPage = lastPage;
			return currentPage;
		} else if(currentPage > lastPage && lastPage <= 0) {
			return 1;
		}
		return currentPage;
	}
	public int getLastPage() {
		if(lastPage <= 0)
			return 1;
		return lastPage;
	}
	public void setLastPage(int resultCount) {
		this.lastPage = (resultCount + pageSize - 1) / pageSize;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
