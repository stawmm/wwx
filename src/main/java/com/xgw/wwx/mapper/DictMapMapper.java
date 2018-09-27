package com.xgw.wwx.mapper;

import java.util.List;

import com.xgw.wwx.dto.db.DictMapDTO;

public interface DictMapMapper {

	public List<DictMapDTO> findDictMapListByCode(String code);

}
