package com.xgw.wwx.service;

import com.xgw.wwx.dto.db.CarryDTO;

public interface CommandService {

	public CarryDTO getFileCarryMessage(String filePath, Long algId);

	public Long getStrategyTimeCount(String filePath, Long speed);

	public Long getVpndesTimeCount(Long speed);

}
