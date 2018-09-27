package com.xgw.wwx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xgw.wwx.dto.db.HistoryDTO;
import com.xgw.wwx.mapper.HistoryMapper;
import com.xgw.wwx.service.HistoryService;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {

	@Autowired
	private HistoryMapper historyMapper;
	
	@Override
	public HistoryDTO getHistoryById(Long id) {
		return historyMapper.getHistoryById(id);
	}

	@Override
	public HistoryDTO getHistoryByName(String name) {
		return historyMapper.getHistoryByName(name);
	}

	@Override
	public PageInfo<HistoryDTO> findHistorysByPage(Integer pageSize, Integer pageNum, Map<String, Object> params) {
		PageHelper.startPage(pageNum, pageSize);
		List<HistoryDTO> list = historyMapper.findHistorys(params);
		return new PageInfo<HistoryDTO>(list);
	}

	@Override
	public List<HistoryDTO> findHistorys(Map<String, Object> params) {
		return historyMapper.findHistorys(params);
	}

	@Override
	public void createHistory(HistoryDTO historyDTO) {
		historyMapper.createHistory(historyDTO);
	}

	@Override
	public void updateHistory(HistoryDTO historyDTO) {
		historyMapper.updateHistory(historyDTO);
	}

	@Override
	public void deleteHistory(Long id) {
		historyMapper.deleteHistory(id);
	}

}
