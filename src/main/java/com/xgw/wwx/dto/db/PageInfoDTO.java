package com.xgw.wwx.dto.db;

import java.io.Serializable;

public class PageInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer pageSize;

	private Integer pageNum;

	private String searchWord;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

}
