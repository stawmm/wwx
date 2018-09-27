package com.xgw.wwx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xgw.wwx.dto.db.DictMapDTO;
import com.xgw.wwx.mapper.DictMapMapper;
import com.xgw.wwx.service.DictMapService;

@Service
@Transactional
public class DictMapServiceImpl implements DictMapService {

	@Autowired
	private DictMapMapper dictMapMapper;

	@Override
	public List<DictMapDTO> findDictMapListByCode(String code) {
		return dictMapMapper.findDictMapListByCode(code);
	}

}
