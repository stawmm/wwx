package com.xgw.wwx.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xgw.wwx.dto.db.HistoryDTO;

public interface HistoryService {
	
	public HistoryDTO getHistoryById(Long id);
	
	public HistoryDTO getHistoryByName(String name);
	
	public PageInfo<HistoryDTO> findHistorysByPage(Integer pageSize, Integer pageNum, Map<String, Object> params);
	
	public List<HistoryDTO> findHistorys(Map<String,Object> params);

    public void createHistory(HistoryDTO historyDTO);
    
    public void updateHistory(HistoryDTO historyDTO);

    public void deleteHistory(Long id);
    
}
