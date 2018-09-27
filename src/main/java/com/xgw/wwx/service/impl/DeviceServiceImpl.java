package com.xgw.wwx.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xgw.wwx.common.constant.WebUrlConstant;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.common.helper.DictHelper;
import com.xgw.wwx.common.util.IpUtil;
import com.xgw.wwx.config.task.IpTask;
import com.xgw.wwx.dto.common.IpResultDTO;
import com.xgw.wwx.dto.db.AlgDTO;
import com.xgw.wwx.dto.db.CardDTO;
import com.xgw.wwx.dto.db.DeviceDTO;
import com.xgw.wwx.dto.db.DictDTO;
import com.xgw.wwx.dto.db.DictDeviceDTO;
import com.xgw.wwx.dto.db.SubTaskDTO;
import com.xgw.wwx.dto.request.BaseRespDTO;
import com.xgw.wwx.dto.request.CardRespDTO;
import com.xgw.wwx.dto.request.CardStatusRespDTO;
import com.xgw.wwx.dto.request.DeviceRespDTO;
import com.xgw.wwx.dto.request.DictRespDTO;
import com.xgw.wwx.dto.request.StatusCardRespDTO;
import com.xgw.wwx.mapper.AlgMapper;
import com.xgw.wwx.mapper.CardMapper;
import com.xgw.wwx.mapper.DeviceMapper;
import com.xgw.wwx.mapper.DictDeviceMapper;
import com.xgw.wwx.mapper.DictMapper;
import com.xgw.wwx.mapper.SubTaskMapper;
import com.xgw.wwx.service.DeviceService;
import com.xgw.wwx.service.RequestService;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

	private static Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

	@Autowired
	private DeviceMapper deviceMapper;

	@Autowired
	private CardMapper cardMapper;

	@Autowired
	private DictDeviceMapper dictDeviceMapper;

	@Autowired
	private DictMapper dictMapper;

	@Autowired
	private AlgMapper algMapper;

	@Autowired
	private RequestService requestService;

	@Autowired
	private SubTaskMapper subTaskMapper;

	@Value("${wwx.localhost.eth0}")
	private String localNetCardName;

	private static final int port = 8088;

	@Override
	public DeviceDTO getDeviceById(Long id) {
		DeviceDTO deviceDTO = deviceMapper.getDeviceById(id);
		if (null != deviceDTO) {
			List<CardDTO> cards = cardMapper.findCards(id);
			List<DictDTO> dicts = dictMapper.findDeviceDicts(id);
			List<AlgDTO> algs = algMapper.findAlgs(id);
			deviceDTO.setAlgList(algs);
			deviceDTO.setCardList(cards);
			deviceDTO.setDictList(dicts);
		}
		return deviceDTO;
	}

	@Override
	public DeviceDTO getDeviceByName(String name) {
		return deviceMapper.getDeviceByName(name);
	}

	@Override
	public DeviceDTO getDeviceByIp(String ip) {
		return deviceMapper.getDeviceByIp(ip);
	}

	@Override
	public PageInfo<DeviceDTO> findDevicesByPage(Integer pageSize, Integer pageNum, Map<String, Object> params) {
		PageHelper.startPage(pageNum, pageSize);
		List<DeviceDTO> list = deviceMapper.findDevices(params);
		return new PageInfo<DeviceDTO>(list);
	}

	@Override
	public List<DeviceDTO> findDevices(Map<String, Object> params) {
		return deviceMapper.findDevices(params);
	}

	@Override
	public void createDevice(DeviceDTO deviceDTO) {
		// 重复check
		DeviceDTO dbDeviceDTO = deviceMapper.getDeviceByName(deviceDTO.getName());
		if (null != dbDeviceDTO) {
			throw new WxxRuntimeException("DEVICE_NAME_EXISTS", "设备名称已经存在");
		}
		DeviceRespDTO deviceRespDTO = null;
		try {
			// 请求url
			String nodeInfoUrl = String.format(WebUrlConstant.REQUEST_URL, deviceDTO.getIp(), deviceDTO.getPort()) + WebUrlConstant.URL_NODE_INFO;
			deviceRespDTO = requestService.doHttpGet(nodeInfoUrl, DeviceRespDTO.class);
		} catch (Exception e) {
			deviceRespDTO = null;
			logger.error("-- 创建设备异常 --", e);
		}
		if (null != deviceRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(deviceRespDTO.getResult())) {
			// 设备存入数据
			deviceDTO.setMac(deviceRespDTO.getMac());
			deviceDTO.setNodeType(deviceRespDTO.getNodeType());
			if (deviceRespDTO.getIp().equals(IpUtil.getLocalHost(localNetCardName))) {
				deviceDTO.setRoleType(2);
			} else {
				deviceDTO.setRoleType(1);
			}
			logger.debug("-- roleType:{} --", deviceDTO.getRoleType());
			deviceDTO.setCardNum(deviceRespDTO.getCardNum());
			deviceDTO.setAlgNum(deviceRespDTO.getAlgNum());
			deviceDTO.setDictNum(deviceRespDTO.getDictNum());
			deviceDTO.setCreateUser(deviceDTO.getCreateUser());
			deviceDTO.setStatus(1);
			deviceMapper.createDevice(deviceDTO);
			// 获取设备Id
			Long deviceId = deviceDTO.getId();
			// 添加板卡
			List<CardRespDTO> cards = deviceRespDTO.getCardlist();
			if (null != cards && !cards.isEmpty()) {
				for (CardRespDTO card : cards) {
					CardDTO cardDTO = new CardDTO();
					cardDTO.setDeviceId(deviceId);
					cardDTO.setStatus(1);
					cardDTO.setCardType(card.getCardType());
					cardDTO.setChipNum(card.getChipNum());
					cardDTO.setCardVersion(card.getCardVersion());
					cardDTO.setCreateUser(deviceDTO.getCreateUser());
					cardMapper.createCard(cardDTO);
				}
			}
			// 添加字典
			List<DictRespDTO> dicts = deviceRespDTO.getDictList();
			if (null != dicts && !dicts.isEmpty()) {
				for (DictRespDTO dict : dicts) {
					DictDTO dictDTO = dictMapper.getDictByName(dict.getName());
					if (null == dictDTO) {
						dictDTO = new DictDTO();
						dictDTO.setStatus(1);
						dictDTO.setType(0);
						dictDTO.setName(dict.getName());
						dictDTO.setSize(DictHelper.getDictSize(dict.getName()));
						dictDTO.setCreateUser(deviceDTO.getCreateUser());
						dictMapper.createDict(dictDTO);
					}

					// 添加关系映射
					Long dictId = dictDTO.getId();
					DictDeviceDTO dictDeviceDTO = dictDeviceMapper.selectByDeviceAndDict(dictId, deviceId);
					if (null == dictDeviceDTO) {
						dictDeviceDTO = new DictDeviceDTO();
						dictDeviceDTO.setDeviceId(deviceId);
						dictDeviceDTO.setDictId(dictId);
						dictDeviceMapper.createDictDeviceAsso(dictDeviceDTO);
					}

				}
			}
		} else {
			// deviceMapper.createDevice(deviceDTO);
			throw new WxxRuntimeException("DEVICE_NOT_ETISTS", "未扫描到设备");
		}

	}

	@Override
	public void updateDevice(DeviceDTO deviceDTO) {
		// 重复check
		DeviceDTO dbDeviceDTO = deviceMapper.getDeviceByName(deviceDTO.getName());
		if (null != dbDeviceDTO && dbDeviceDTO.getId().longValue() != dbDeviceDTO.getId().longValue()) {
			throw new WxxRuntimeException("DEVICE_NAME_EXISTS", "设备名称已经存在");
		}
		deviceMapper.updateDevice(deviceDTO);
	}

	@Override
	public void deleteDevice(Long id) {
		// 查看设备上是否有任务
		SubTaskDTO subTaskDTO = subTaskMapper.getDeviceSubTask(id);
		if (null != subTaskDTO) {
			throw new WxxRuntimeException("DEVICE_HAS_TASK", "设备存在运行的任务，不允许删除");
		}
		try {
			DeviceDTO deviceDTO = deviceMapper.getDeviceById(id);
			if (null != deviceDTO) {
				// 远程删除节点
				String url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_NODE_DELETE, deviceDTO.getIp(), deviceDTO.getPort(), "");
				requestService.doHttpGet(url, BaseRespDTO.class);

			}
		} catch (Exception e) {
			logger.error("-- 远程删除节点异常 --", e);
		}
		// 获取设备卡
		List<CardDTO> cards = cardMapper.findCards(id);
		if (null != cards && !cards.isEmpty()) {
			for (CardDTO card : cards) {
				cardMapper.deleteCard(card.getId());
			}
		}
		// 删除和字典关系
		dictDeviceMapper.deleteByDeviceId(id);
		// 删除设备
		deviceMapper.deleteDevice(id);
	}

	@Override
	public List<DeviceDTO> findSearchDevices(String startIp, String endIp) {
		List<DeviceDTO> devices = new ArrayList<DeviceDTO>();
		try {
			Long start = IpUtil.ipToLong(startIp);
			Long end = IpUtil.ipToLong(endIp);
			if (start.longValue() > end.longValue()) {
				throw new WxxRuntimeException("IP_FORMAT_ERROR", "IP起始地址大于结束地址");
			}

			ExecutorService executor = Executors.newCachedThreadPool();
			Collection<Callable<IpResultDTO>> tasks = new LinkedList<Callable<IpResultDTO>>();
			Collection<Future<IpResultDTO>> results = null;
			for (long i = start; i <= end; i++) {
				String tempIp = IpUtil.longToIp(i);
				tasks.add(new IpTask(tempIp, port));
			}
			results = executor.invokeAll(tasks, 10, TimeUnit.SECONDS);
			executor.shutdown();
			// 循环获取
			for (Future<IpResultDTO> result : results) {
				IpResultDTO ipResultDTO = result.get();
				if (ipResultDTO.getResult()) {
					DeviceDTO deviceDTO = getDevice(ipResultDTO);
					if (null != deviceDTO) {
						devices.add(deviceDTO);
					}
				}
			}
		} catch (WxxRuntimeException e) {
			logger.error("port scan is error", e);
			throw e;
		} catch (Exception e) {
			logger.error("port scan is error", e);
		}
		return devices;
	}

	public DeviceDTO getDevice(IpResultDTO ipResultDTO) {
		try {
			String url = String.format(WebUrlConstant.REQUEST_URL, ipResultDTO.getHost(), ipResultDTO.getPort()) + WebUrlConstant.URL_NODE_INFO;
			DeviceRespDTO deviceRespDTO = requestService.doHttpGet(url, DeviceRespDTO.class);
			if (null == deviceRespDTO) {
				logger.warn("--- getNodeInfo return is null ---");
			}
			if (WebUrlConstant.REQUEST_FAILED.equals(deviceRespDTO.getResult())) {
				logger.warn("--- getNodeInfo return is failed ---");
			}
			if (null != deviceRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(deviceRespDTO.getResult())) {
				String ip = ipResultDTO.getHost();
				if (null == deviceMapper.getDeviceByIp(ip)) {
					// 设备存入数据
					DeviceDTO deviceDTO = new DeviceDTO();
					deviceDTO.setMac(deviceRespDTO.getMac());
					deviceDTO.setName(ipResultDTO.getHost());
					deviceDTO.setStatus(1);
					if (ipResultDTO.getHost().equals(IpUtil.getLocalHost(localNetCardName))) {
						deviceDTO.setRoleType(2);
					} else {
						deviceDTO.setRoleType(1);
					}
					logger.debug("-- roleType:{} --", deviceDTO.getRoleType());
					deviceDTO.setIp(ipResultDTO.getHost());
					deviceDTO.setPort(ipResultDTO.getPort());
					deviceDTO.setCardNum(deviceRespDTO.getCardNum());
					return deviceDTO;
				}
			}
		} catch (Exception e) {
			logger.error("getDevice request[ip={},port={}] is error", ipResultDTO.getHost(), ipResultDTO.getPort());
			return null;
		}
		return null;
	}

	@Override
	public void distributionDict(DeviceDTO deviceDTO) {

	}

	@Override
	public List<DeviceDTO> findAllDevices() {
		return deviceMapper.findAllDevices();
	}

	@Override
	public DeviceDTO getDeviceByTaskId(Long taskId) {
		Assert.notNull(taskId, "taskId不能为空");
		return deviceMapper.getDeviceByTaskId(taskId);
	}

	@Override
	public List<CardStatusRespDTO> getDeviceCardStatus(Long id) {
		DeviceDTO deviceDTO = deviceMapper.getDeviceById(id);
		if (null != deviceDTO) {
			String url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_NODE_CARD_STATUS, deviceDTO.getIp(), deviceDTO.getPort(), deviceDTO.getNodeType() + "");
			StatusCardRespDTO statusCardRespDTO = requestService.doHttpGet(url, StatusCardRespDTO.class);
			if (null != statusCardRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(statusCardRespDTO.getResult())) {
				return statusCardRespDTO.getStatuslist();
			}
		}
		return null;
	}

	@Override
	public List<DeviceDTO> findDevicesByDictId(Long dictId) {
		return deviceMapper.findDevicesByDictId(dictId);
	}

	@Override
	@Async
	public void updateDeviceErrorTimes(Long id, Integer errorTimes) {
		logger.debug("-- updateDeviceErrorTimes id:{},errorTimes:{}--", id, errorTimes);
		deviceMapper.updateDeviceErrorTimes(id, errorTimes);
	}

	@Override
	public List<DeviceDTO> findDevicesByNodeType(String nodeType) {
		if ("gpu".equals(nodeType)) {
			return deviceMapper.findDevicesByGpu();
		} else if ("fpga".equals(nodeType)) {
			return deviceMapper.findDevicesByFpga();
		}
		return null;
	}

	@Override
	@Async
	public void updateDeviceStatus(Long deviceId, int status) {
		deviceMapper.updateDeviceStatus(deviceId, status);
	}

	@Override
	public List<DeviceDTO> findDevicesByTaskId(Long taskId) {
		return deviceMapper.findDevicesByTaskId(taskId);
	}

	@Override
	public List<DeviceDTO> findAliveDevices() {
		List<DeviceDTO> aliveList = null;
		try {
			List<DeviceDTO> allDevices = deviceMapper.findAllDevices();
			if (null != allDevices && !allDevices.isEmpty()) {
				aliveList = new ArrayList<DeviceDTO>();
				ExecutorService executor = Executors.newCachedThreadPool();
				Collection<Callable<IpResultDTO>> tasks = new LinkedList<Callable<IpResultDTO>>();
				for (DeviceDTO deviceDTO : allDevices) {
					tasks.add(new IpTask(deviceDTO.getIp(), deviceDTO.getPort(), deviceDTO));
				}
				Collection<Future<IpResultDTO>> results = executor.invokeAll(tasks, 10, TimeUnit.SECONDS);
				executor.shutdown();

				// 循环获取
				for (Future<IpResultDTO> result : results) {
					IpResultDTO ipResultDTO = result.get();
					if (ipResultDTO.getResult()) {
						aliveList.add(ipResultDTO.getDeviceDTO());
					}
				}
				if (aliveList.isEmpty()) {
					return null;
				}
			}
		} catch (Exception e) {
			logger.error("-- findAliveDevices error --", e);
		}
		return aliveList;
	}

	@Override
	public List<DeviceDTO> findAliveDevicesByNodeType(String nodeType) {
		List<DeviceDTO> aliveList = null;
		List<DeviceDTO> allDevices = null;
		try {
			if ("gpu".equals(nodeType)) {
				allDevices = deviceMapper.findDevicesByGpu();
			} else if ("fpga".equals(nodeType)) {
				allDevices = deviceMapper.findDevicesByFpga();
			}
			// 设备查询
			if (null != allDevices && !allDevices.isEmpty()) {
				aliveList = new ArrayList<DeviceDTO>();
				ExecutorService executor = Executors.newCachedThreadPool();
				Collection<Callable<IpResultDTO>> tasks = new LinkedList<Callable<IpResultDTO>>();
				for (DeviceDTO deviceDTO : allDevices) {
					tasks.add(new IpTask(deviceDTO.getIp(), deviceDTO.getPort(), deviceDTO));
				}
				Collection<Future<IpResultDTO>> results = executor.invokeAll(tasks, 10, TimeUnit.SECONDS);
				executor.shutdown();
				// 循环获取
				for (Future<IpResultDTO> result : results) {
					IpResultDTO ipResultDTO = result.get();
					if (ipResultDTO.getResult()) {
						aliveList.add(ipResultDTO.getDeviceDTO());
					}
				}
				if (aliveList.isEmpty()) {
					return null;
				}
			}
		} catch (Exception e) {
			logger.error("-- findAliveDevicesByNodeType error --", e);
		}
		return aliveList;
	}

}
