package com.xgw.wwx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xgw.wwx.common.constant.WebUrlConstant;
import com.xgw.wwx.common.exception.ProgramRuntimeException;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.common.util.GsonUtil;
import com.xgw.wwx.dto.db.DeviceDTO;
import com.xgw.wwx.dto.db.DictDTO;
import com.xgw.wwx.dto.db.DictDeviceDTO;
import com.xgw.wwx.dto.request.BaseRespDTO;
import com.xgw.wwx.mapper.DeviceMapper;
import com.xgw.wwx.mapper.DictDeviceMapper;
import com.xgw.wwx.mapper.DictMapper;
import com.xgw.wwx.service.DictService;
import com.xgw.wwx.service.RequestService;

@Service
@Transactional
public class DictServiceImpl implements DictService {

	private static Logger logger = LoggerFactory.getLogger(DictServiceImpl.class);

	@Autowired
	private DictMapper dictMapper;

	@Autowired
	private RequestService requestService;

	@Autowired
	private DictDeviceMapper dictDeviceMapper;

	@Autowired
	private DeviceMapper deviceMapper;

	@Override
	public DictDTO getDictById(Long id) {
		DictDTO dictDTO = dictMapper.getDictById(id);
		if (null != dictDTO) {
			List<DeviceDTO> devices = deviceMapper.findDevicesByDictId(id);
			dictDTO.setDeviceList(devices);
		}
		return dictDTO;
	}

	@Override
	public DictDTO getDictByName(String name) {
		return dictMapper.getDictByName(name);
	}

	@Override
	public PageInfo<DictDTO> findDictsByPage(Integer pageSize, Integer pageNum, Map<String, Object> params) {
		PageHelper.startPage(pageNum, pageSize);
		List<DictDTO> list = dictMapper.findDicts(params);
		return new PageInfo<DictDTO>(list);
	}

	@Override
	public List<DictDTO> findDicts(Map<String, Object> params) {
		return dictMapper.findAllDicts(params);
	}

	@Override
	public void createDict(DictDTO dictDTO) {
		DictDTO dbDictDTO = dictMapper.getDictByName(dictDTO.getName());
		if (null != dbDictDTO) {
			throw new WxxRuntimeException("Dict_name_exist", "字典名称已经存在");
		}
		dictMapper.createDict(dictDTO);
	}

	@Override
	public void updateDict(DictDTO dictDTO) {
		dictMapper.updateDict(dictDTO);
	}

	@Override
	public void deleteDict(Long id) {
		dictMapper.deleteDict(id);
	}


    @Override
    public int deleteDictAll(int[] ids){
        return dictMapper.deleteDictAll(ids);
    }


    @Override
	public void distributionDict(DictDTO dictDTO) {
		try {
			Long dictId = dictDTO.getId();
			DictDTO dbDictDTO = dictMapper.getDictById(dictId);
			List<String> fileList = new ArrayList<String>();
			fileList.add(dbDictDTO.getName());

			// 没有选择设备
			if (null == dictDTO.getDeviceIds() || dictDTO.getDeviceIds().isEmpty()) {
				// 刪除字典对应设备关系
				dictDeviceMapper.deleteByDictId(dictId);
				return;
			}

			// 所有设备
			List<DeviceDTO> devices = deviceMapper.findAllDevices();
			Map<String, Object> params = null;
			for (DeviceDTO deviceDTO : devices) {
				long deviceId = deviceDTO.getId();
				if (dictDTO.getDeviceIds().contains(deviceId)) {
					params = new HashMap<String, Object>();
					params.put("Updatetype", "dic");
					params.put("Filelist", fileList);
					DeviceDTO device = deviceMapper.getDeviceById(deviceId);
					String requestUrl = String.format(WebUrlConstant.REQUEST_URL, device.getIp(), device.getPort()) + WebUrlConstant.UPDATE;
					// 发送请求更新
					BaseRespDTO baseRespDTO = requestService.doHttpPostJson(requestUrl, GsonUtil.GsonString(params), BaseRespDTO.class);
					if (null != baseRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(baseRespDTO.getResult())) {
						logger.debug("-- distributionDict ip:{}, port:{} success --", device.getIp(), device.getPort());
						// 刪除设备和字典关系
						dictDeviceMapper.deleteByDictIdAndDeviceId(dictId, deviceId);
						// 添加设备和字典关系
						DictDeviceDTO dictDeviceDTO = new DictDeviceDTO();
						dictDeviceDTO.setDeviceId(deviceId);
						dictDeviceDTO.setDictId(dictId);
						dictDeviceMapper.createDictDeviceAsso(dictDeviceDTO);
					}
				} else {
					// 刪除设备和字典关系
					dictDeviceMapper.deleteByDictIdAndDeviceId(dictId, deviceId);
				}
			}
		} catch (Exception e) {
			logger.error("-- dict distributionDict error --", e);
			throw new ProgramRuntimeException("DICT_DISTRIBUTION_ERROR", "字典分配文件失败");
		}

	}

	@Override
	public List<DictDTO> findDictList(String userName) {
		return dictMapper.findDictList(userName);
	}

	@Override
	public List<DictDTO> findDictListByTaskId(Long taskId) {
		return dictMapper.findDictListByTaskId(taskId);
	}

}
