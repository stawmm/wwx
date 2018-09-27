package com.xgw.wwx.mapper;

import java.util.List;
import java.util.Map;

import com.xgw.wwx.dto.db.HistoryDTO;

public interface HistoryMapper {
	
	public HistoryDTO getHistoryById(Long id);
	
	public HistoryDTO getHistoryByName(String name);
	
	public List<HistoryDTO> findHistorys(Map<String,Object> params);

    public void createHistory(HistoryDTO historyDTO);
    
    public void updateHistory(HistoryDTO historyDTO);

    public void deleteHistory(Long id);
    
}
