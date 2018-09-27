package com.xgw.wwx.config.task;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xgw.wwx.common.constant.WebUrlConstant;
import com.xgw.wwx.common.em.TempStatusType;
import com.xgw.wwx.common.util.HttpUtil;
import com.xgw.wwx.dto.common.DeviceTaskDTO;
import com.xgw.wwx.dto.db.DeviceDTO;
import com.xgw.wwx.dto.request.CardStatusRespDTO;
import com.xgw.wwx.dto.request.DeviceRespDTO;
import com.xgw.wwx.dto.request.StatusCardRespDTO;

public class DeviceInfoTask implements Callable<DeviceTaskDTO> {

	private static final Logger logger = LoggerFactory.getLogger(DeviceInfoTask.class);

	private DeviceDTO deviceDTO;

	private DeviceRespDTO deviceRespDTO;

	public DeviceRespDTO getDeviceRespDTO() {
		return deviceRespDTO;
	}

	public void setDeviceRespDTO(DeviceRespDTO deviceRespDTO) {
		this.deviceRespDTO = deviceRespDTO;
	}

	public DeviceInfoTask(DeviceDTO deviceDTO) {
		super();
		this.deviceDTO = deviceDTO;
	}

	public DeviceDTO getDeviceDTO() {
		return deviceDTO;
	}

	public void setDeviceDTO(DeviceDTO deviceDTO) {
		this.deviceDTO = deviceDTO;
	}

	@Override
	public DeviceTaskDTO call() throws Exception {
		DeviceTaskDTO deviceTaskDTO = new DeviceTaskDTO();
		deviceTaskDTO.setDeviceDTO(deviceDTO);
		int temp = TempStatusType.ERROR.getStatus();
		try {
			String statusUrl = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_NODE_CARD_STATUS, deviceDTO.getIp(), deviceDTO.getPort(), deviceDTO.getNodeType() + "");
			StatusCardRespDTO statusCardRespDTO = HttpUtil.doHttpGet(statusUrl, StatusCardRespDTO.class);
			if (null != statusCardRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(statusCardRespDTO.getResult())) {
				deviceTaskDTO.setResult(true);
				// 获取实时状态
				List<CardStatusRespDTO> statuslist = statusCardRespDTO.getStatuslist();
				if (null != statuslist && !statuslist.isEmpty()) {
					boolean errorFlag = false;
					for (CardStatusRespDTO cardStatus : statuslist) {
						if (cardStatus.getStatus() == 2) {
							errorFlag = true;
							break;
						}
					}
					if (errorFlag) {
						temp = TempStatusType.ERROR.getStatus();
					} else {
						boolean tmpFlag = false;
						for (CardStatusRespDTO cardStatus : statuslist) {
							if (cardStatus.getStatus() == 1) {
								tmpFlag = true;
								break;
							}
						}
						if (tmpFlag) {
							temp = TempStatusType.THRESHOLD.getStatus();
						} else {
							temp = TempStatusType.NORMAL.getStatus();
						}
					}
				}
			} else {
				deviceTaskDTO.setResult(false);
				logger.info("--  device ip:{}, port:{} getTmep Http tmpty --", deviceDTO.getIp(), deviceDTO.getPort());
			}
		} catch (Exception e) {
			logger.error("-- getTmep error --" + e);
			logger.info("--  device ip:{}, port:{} getTmep failed --", deviceDTO.getIp(), deviceDTO.getPort());
			deviceTaskDTO.setResult(false);
		}
		deviceTaskDTO.setTemp(temp);
		return deviceTaskDTO;

		/*
		 * try { String url = String.format(WebUrlConstant.REQUEST_URL, deviceDTO.getIp(), deviceDTO.getPort()) + WebUrlConstant.URL_NODE_INFO; DeviceRespDTO deviceRespDTO = HttpUtil.doHttpGet(url, DeviceRespDTO.class); if (null != deviceRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(deviceRespDTO.getResult())) { deviceTaskDTO.setResult(true); deviceTaskDTO.setTemp(getTmep()); } else { deviceTaskDTO.setResult(false); } deviceTaskDTO.setDeviceRespDTO(deviceRespDTO); } catch (Exception e) { logger.error("-- DeviceInfoTask call error --" + e); logger.info("--  device ip:{}, port:{} connected failed --", deviceDTO.getIp(), deviceDTO.getPort()); deviceTaskDTO.setResult(false); deviceTaskDTO.setDeviceRespDTO(null); } return deviceTaskDTO;
		 */
	}

	/*
	 * private int getTmep() { int temp = 2; try { String statusUrl = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_NODE_CARD_STATUS, deviceDTO.getIp(), deviceDTO.getPort(), deviceDTO.getNodeType()); StatusCardRespDTO statusCardRespDTO = HttpUtil.doHttpGet(statusUrl, StatusCardRespDTO.class); if (null != statusCardRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(statusCardRespDTO.getResult())) { // 获取实时状态 List<CardStatusRespDTO> statuslist = statusCardRespDTO.getStatuslist(); if (null != statuslist && !statuslist.isEmpty()) { boolean errorFlag = false; for (CardStatusRespDTO cardStatus : statuslist) { if (cardStatus.getStatus() == 2) { errorFlag = true; break; } } if (errorFlag) { temp = 2; } else { boolean tmpFlag = false; for (CardStatusRespDTO cardStatus : statuslist) { if (cardStatus.getStatus() == 1) { tmpFlag = true; break; } } if (tmpFlag) { temp = 1; } else { temp = 0; } } } } else { logger.info("--  device ip:{}, port:{} getTmep Http tmpty --", deviceDTO.getIp(),
	 * deviceDTO.getPort()); } } catch (Exception e) { logger.error("-- getTmep error --" + e); logger.info("--  device ip:{}, port:{} getTmep failed --", deviceDTO.getIp(), deviceDTO.getPort()); temp = 2; } return temp; }
	 */

}
