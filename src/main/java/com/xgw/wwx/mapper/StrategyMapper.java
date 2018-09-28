package com.xgw.wwx.mapper;

import java.util.List;
import java.util.Map;

import com.xgw.wwx.dto.db.StrategyDTO;

public interface StrategyMapper {

	public StrategyDTO getStrategyById(Long id);

	public StrategyDTO getStrategyByName(String name);

	public List<StrategyDTO> findStrategys(Map<String, Object> params);

	public void createStrategy(StrategyDTO strategyDTO);

	public void updateStrategy(StrategyDTO strategyDTO);

	public void deleteStrategy(Long id);

	public int deleteStrategyAll(int[] ids);

	public List<StrategyDTO> findAllStrategys(String userName);

	public List<StrategyDTO> findStrategysByTaskId(Long taskId);

}
