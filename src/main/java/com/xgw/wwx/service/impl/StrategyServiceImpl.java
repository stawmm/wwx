package com.xgw.wwx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xgw.wwx.common.em.StrategyEnum;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.dto.db.StrategyDTO;
import com.xgw.wwx.mapper.StrategyMapper;
import com.xgw.wwx.service.StrategyService;

@Service
public class StrategyServiceImpl implements StrategyService {

	@Autowired
	private StrategyMapper strategyMapper;

	@Override
	public StrategyDTO getStrategyById(Long id) {
		return strategyMapper.getStrategyById(id);
	}

	@Override
	public StrategyDTO getStrategyByName(String name) {
		return strategyMapper.getStrategyByName(name);
	}

	@Override
	public PageInfo<StrategyDTO> findStrategysByPage(Integer pageSize, Integer pageNum, Map<String, Object> params) {
		PageHelper.startPage(pageNum, pageSize);
		List<StrategyDTO> list = strategyMapper.findStrategys(params);
		return new PageInfo<StrategyDTO>(list);
	}

	@Override
	public List<StrategyDTO> findStrategys(String userName) {
		return strategyMapper.findAllStrategys(userName);
	}

	@Override
	public List<StrategyDTO> findStrategysByTaskId(Long taskId) {
		return strategyMapper.findStrategysByTaskId(taskId);
	}

	@Override
	public void createStrategy(StrategyDTO strategyDTO) {
		StrategyDTO dbStrategyDTO = strategyMapper.getStrategyByName(strategyDTO.getName());
		if (null != dbStrategyDTO) {
			throw new WxxRuntimeException("Strategy_name_exist", "策略名称已经存在");
		}
		strategyMapper.createStrategy(strategyDTO);
	}

	@Override
	public void updateStrategy(StrategyDTO strategyDTO) {
		StrategyDTO dbStrategyDTO = strategyMapper.getStrategyByName(strategyDTO.getName());
		if (null != dbStrategyDTO && dbStrategyDTO.getId().longValue() != strategyDTO.getId().longValue()) {
			throw new WxxRuntimeException("Strategy_name_exist", "策略名称已经存在");
		}
		strategyMapper.updateStrategy(strategyDTO);
	}

	@Override
	public void deleteStrategy(Long id) {
		strategyMapper.deleteStrategy(id);
	}

	/*@Override
	public int  deleteStrategyAll(int[] ids) {
		return  strategyMapper.deleteStrategyAll(ids);
	}*/

	@Override
	public List<Map<String, Object>> findStrategyType() {
		List<Map<String, Object>> types = new ArrayList<Map<String, Object>>();
		Map<String, Object> typeMap = null;
		for (StrategyEnum strategyEnum : StrategyEnum.values()) {
			typeMap = new HashMap<String, Object>();
			typeMap.put("strategyId", strategyEnum.getType());
			typeMap.put("strategyName", strategyEnum.getDesc());
			types.add(typeMap);
		}
		return types;
	}

}
