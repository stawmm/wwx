package com.xgw.wwx.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xgw.wwx.dto.db.StrategyDTO;

public interface StrategyService {
	
	public StrategyDTO getStrategyById(Long id);
	
	public StrategyDTO getStrategyByName(String name);
	
	public PageInfo<StrategyDTO> findStrategysByPage(Integer pageSize, Integer pageNum, Map<String, Object> params);
	
	public List<StrategyDTO> findStrategys(String userName);
	
	public List<StrategyDTO> findStrategysByTaskId(Long taskId);

    public void createStrategy(StrategyDTO strategyDTO);
    
    public void updateStrategy(StrategyDTO strategyDTO);

    public void deleteStrategy(Long id);

	public List<Map<String, Object>> findStrategyType();
	
}
