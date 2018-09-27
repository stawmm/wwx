package com.xgw.wwx.dto.db;

import java.util.List;

public class TaskTimeCountDTO {

	private Long speed;

	private List<StrategyDTO> strategys;

	public Long getSpeed() {
		return speed;
	}

	public void setSpeed(Long speed) {
		this.speed = speed;
	}

	public List<StrategyDTO> getStrategys() {
		return strategys;
	}

	public void setStrategys(List<StrategyDTO> strategys) {
		this.strategys = strategys;
	}

}
