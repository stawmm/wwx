package com.xgw.wwx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xgw.wwx.common.constant.WebUrlConstant;
import com.xgw.wwx.common.exception.ProgramRuntimeException;
import com.xgw.wwx.common.util.GsonUtil;
import com.xgw.wwx.dto.db.DeviceDTO;
import com.xgw.wwx.dto.db.ProgramDTO;
import com.xgw.wwx.dto.request.BaseRespDTO;
import com.xgw.wwx.mapper.ProgramMapper;
import com.xgw.wwx.service.DeviceService;
import com.xgw.wwx.service.ProgramService;
import com.xgw.wwx.service.RequestService;

@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {

	private static final Logger logger = LoggerFactory.getLogger(ProgramServiceImpl.class);

	@Autowired
	private ProgramMapper programMapper;

	@Autowired
	private RequestService requestService;

	@Autowired
	private DeviceService deviceService;

	@Override
	public ProgramDTO getProgramById(Long id) {
		return programMapper.getProgramById(id);
	}

	@Override
	public ProgramDTO getProgramByName(String name) {
		return programMapper.getProgramByName(name);
	}

	@Override
	public PageInfo<ProgramDTO> findProgramsByPage(Integer pageSize, Integer pageNum, Map<String, Object> params) {
		PageHelper.startPage(pageNum, pageSize);
		List<ProgramDTO> list = programMapper.findPrograms(params);
		return new PageInfo<ProgramDTO>(list);
	}

	@Override
	public List<ProgramDTO> findPrograms(Map<String, Object> params) {
		return programMapper.findPrograms(params);
	}

	@Override
	public void createProgram(ProgramDTO programDTO) {
		programMapper.createProgram(programDTO);
	}

	@Override
	public void updateProgram(ProgramDTO programDTO) {
		programMapper.updateProgram(programDTO);
	}

	@Override
	public void deleteProgram(Long id) {
		programMapper.deleteProgram(id);
	}

	@Override
	public void gpuUpgrade(List<String> fileList, List<Long> deviceList) {
		try {
			// 更新请求
			for (Long deviceId : deviceList) {
				DeviceDTO device = deviceService.getDeviceById(deviceId);

				Map<String, Object> params = new HashMap<String, Object>();
				params = new HashMap<String, Object>();
				params.put("Updatetype", "gpu_program");
				params.put("Filelist", fileList);

				String requestUrl = String.format(WebUrlConstant.REQUEST_URL, device.getIp(), device.getPort()) + WebUrlConstant.UPDATE;
				// 发送请求更新
				BaseRespDTO baseRespDTO = requestService.doHttpPostJson(requestUrl, GsonUtil.GsonString(params), BaseRespDTO.class);
				if (null != baseRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(baseRespDTO.getResult())) {
					logger.debug("-- gpuUpgrade ip:{}, port:{} success --", device.getIp(), device.getPort());

				}

			}
		} catch (Exception e) {
			logger.error("-- gpuUpgrade error --", e);
			throw new ProgramRuntimeException("GPU_UPGRADE_ERROR", "GPU升级失败");
		}

	}

	@Override
	public void fpgaUpgrade(List<String> fileList, List<Long> deviceList) {
		try {
			// 更新请求
			for (Long deviceId : deviceList) {
				DeviceDTO device = deviceService.getDeviceById(deviceId);

				Map<String, Object> params = new HashMap<String, Object>();
				params = new HashMap<String, Object>();
				params.put("Updatetype", "fpga_program");
				params.put("Filelist", fileList);

				String requestUrl = String.format(WebUrlConstant.REQUEST_URL, device.getIp(), device.getPort()) + WebUrlConstant.UPDATE;
				// 发送请求更新
				BaseRespDTO baseRespDTO = requestService.doHttpPostJson(requestUrl, GsonUtil.GsonString(params), BaseRespDTO.class);
				if (null != baseRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(baseRespDTO.getResult())) {
					logger.debug("-- fpgaUpgrade ip:{}, port:{} success --", device.getIp(), device.getPort());
				}

			}

		} catch (Exception e) {
			logger.error("-- fpgaUpgrade error --", e);
			throw new ProgramRuntimeException("FPGA_UPGRADE_ERROR", "FPGA升级失败");
		}
	}

	@Override
	public void dictUpgrade(List<String> fileList, List<Long> deviceList) {
		try {
			// 更新请求
			for (Long deviceId : deviceList) {
				DeviceDTO device = deviceService.getDeviceById(deviceId);

				Map<String, Object> params = new HashMap<String, Object>();
				params = new HashMap<String, Object>();
				params.put("Updatetype", "dic");
				params.put("Filelist", fileList);

				String requestUrl = String.format(WebUrlConstant.REQUEST_URL, device.getIp(), device.getPort()) + WebUrlConstant.UPDATE;
				// 发送请求更新
				BaseRespDTO baseRespDTO = requestService.doHttpPostJson(requestUrl, GsonUtil.GsonString(params), BaseRespDTO.class);
				if (null != baseRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(baseRespDTO.getResult())) {
					logger.debug("-- dictUpgrade ip:{}, port:{} success --", device.getIp(), device.getPort());
				}
			}
		} catch (Exception e) {
			logger.error("-- dictUpgrade error --", e);
			throw new ProgramRuntimeException("DICT_DISTRIBUTION_ERROR", "字典分配失败");
		}
	}

	@Override
	public void fpgaOneUpgrade(List<String> fileList, List<Long> deviceList) {
		try {
			// 更新请求
			for (Long deviceId : deviceList) {
				DeviceDTO device = deviceService.getDeviceById(deviceId);

				Map<String, Object> params = new HashMap<String, Object>();
				params = new HashMap<String, Object>();
				params.put("Updatetype", "fpgaburn1");
				params.put("Filelist", fileList);

				String requestUrl = String.format(WebUrlConstant.REQUEST_URL, device.getIp(), device.getPort()) + WebUrlConstant.UPDATE;
				// 发送请求更新
				BaseRespDTO baseRespDTO = requestService.doHttpPostJson(requestUrl, GsonUtil.GsonString(params), BaseRespDTO.class);
				if (null != baseRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(baseRespDTO.getResult())) {
					logger.debug("-- fpgaOneUpgrade ip:{}, port:{} success --", device.getIp(), device.getPort());
				}
			}
		} catch (Exception e) {
			logger.error("-- fpgaOneUpgrade error --", e);
			throw new ProgramRuntimeException("FPGA_ONE_UPGRADE_ERROR", "FPGA一代算法升级失败");
		}

	}

	@Override
	public void fpgaTwoUpgrade(List<String> fileList, List<Long> deviceList) {
		try {
			// 更新请求
			for (Long deviceId : deviceList) {
				DeviceDTO device = deviceService.getDeviceById(deviceId);

				Map<String, Object> params = new HashMap<String, Object>();
				params = new HashMap<String, Object>();
				params.put("Updatetype", "fpgaburn2");
				params.put("Filelist", fileList);

				String requestUrl = String.format(WebUrlConstant.REQUEST_URL, device.getIp(), device.getPort()) + WebUrlConstant.UPDATE;
				// 发送请求更新
				BaseRespDTO baseRespDTO = requestService.doHttpPostJson(requestUrl, GsonUtil.GsonString(params), BaseRespDTO.class);
				if (null != baseRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(baseRespDTO.getResult())) {
					logger.debug("-- fpgaTwoUpgrade ip:{}, port:{} success --", device.getIp(), device.getPort());
				}
			}
		} catch (Exception e) {
			logger.error("-- fpgaTwoUpgrade error --", e);
			throw new ProgramRuntimeException("FPGA_TWO_UPGRADE_ERROR", "FPGA二代算法升级失败");
		}

	}

}
