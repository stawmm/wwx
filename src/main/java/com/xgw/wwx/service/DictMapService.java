package com.xgw.wwx.service;

import java.util.List;

import com.xgw.wwx.dto.db.DictMapDTO;

public interface DictMapService {

	public List<DictMapDTO> findDictMapListByCode(String code);
	
}
