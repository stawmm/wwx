package com.xgw.wwx.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.xgw.wwx.common.constant.WebUrlConstant;
import com.xgw.wwx.common.em.LoggerLevelType;
import com.xgw.wwx.common.em.LoggerStatusType;
import com.xgw.wwx.common.em.NodeType;
import com.xgw.wwx.common.em.TaskStatusType;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.common.util.ByteUtil;
import com.xgw.wwx.common.util.GsonUtil;
import com.xgw.wwx.dto.db.DeviceDTO;
import com.xgw.wwx.dto.db.LoggerDTO;
import com.xgw.wwx.dto.db.TaskDTO;
import com.xgw.wwx.dto.jna.DesSliceLocationDTO;
import com.xgw.wwx.dto.jna.MaskSliceLocationDTO;
import com.xgw.wwx.dto.request.BaseRespDTO;
import com.xgw.wwx.mapper.DeviceMapper;
import com.xgw.wwx.mapper.HzLoggerMapper;
import com.xgw.wwx.mapper.TaskMapper;
import com.xgw.wwx.service.JnaService;
import com.xgw.wwx.service.RequestService;

@Service
public class JnaServiceImpl implements JnaService {

	private static final Logger logger = LoggerFactory.getLogger(JnaServiceImpl.class);

	private static ConcurrentHashMap<String, Structure.ByReference> referMap = new ConcurrentHashMap<String, Structure.ByReference>();

	private static ConcurrentHashMap<String, Structure.ByValue> valueMap = new ConcurrentHashMap<String, Structure.ByValue>();

	@Autowired
	private TaskMapper taskMapper;
	
//	@Autowired
//	private SubTaskMapper subTaskMapper;
	
	@Autowired
	private DeviceMapper deviceMapper;
	
	@Autowired
	HzLoggerMapper hzLoggerMapper;
	
	@Autowired
	private RequestService requestService;
	
	/**
	 * 切片动态库
	 * 
	 * @author ZJ
	 *
	 */
	public interface PwdsliceLibrary extends Library {

		PwdsliceLibrary INSTANCE = (PwdsliceLibrary) Native.loadLibrary("pwdslice", PwdsliceLibrary.class);

		public static class MaskSliceLocation extends Structure {

			public static class ByReference extends MaskSliceLocation implements Structure.ByReference {
			}

			public static class ByValue extends MaskSliceLocation implements Structure.ByValue {
			}

			public MaskSliceLocation() {
			}

			public byte vaild = 1;//是否有效

			public byte finish = 0;//是否完成

			public byte[] filePath = new byte[256];//文件路径

			public long fileLoc = 0l;//mask文件内容位置偏移

			public byte[] currentMask = new byte[256];//将要切片的掩码

			public int offsetLoc = 0;//表示掩码切分位

			public byte[] output = new byte[64];//切片文件输出路径

			public byte[] cutFileName = new byte[512];//切片文件名称

			@Override
			protected List<String> getFieldOrder() {
				List<String> list = new ArrayList<String>();
				list.add("vaild");
				list.add("finish");
				list.add("filePath");
				list.add("fileLoc");
				list.add("currentMask");
				list.add("offsetLoc");
				list.add("output");
				list.add("cutFileName");
				return list;
			}
		}

		public static class DesSliceLocation extends Structure {

			public static class ByReference extends DesSliceLocation implements Structure.ByReference {
			}

			public static class ByValue extends DesSliceLocation implements Structure.ByValue {
			}

			public DesSliceLocation() {
			}

			public byte vaild = 1;

			public byte finish = 0;

			public byte[] desLoc = new byte[17];

			public byte[] output = new byte[64];

			public byte[] cutFileName = new byte[512];

			@Override
			protected List<String> getFieldOrder() {
				List<String> list = new ArrayList<String>();
				list.add("vaild");
				list.add("finish");
				list.add("desLoc");
				list.add("output");
				list.add("cutFileName");
				return list;
			}
		}

		public byte Password_DesSlices(int jmtype, int cardnum, long speed, int sunzinum, DesSliceLocation.ByValue sliceLoc, DesSliceLocation.ByReference result) throws Exception;

		public byte Password_MaskSlices(int jmtype, int cardnum, long speed, int sunzinum, MaskSliceLocation.ByValue sliceLoc, MaskSliceLocation.ByReference result) throws Exception;

		public int maskfileExpansion(String filename) throws Exception;

	}

	@Override
	public int maskfileExpansion(String fileName) {
		try {
			return PwdsliceLibrary.INSTANCE.maskfileExpansion(fileName);
		} catch (Exception e) {
			logger.error("--- maskfileExpansion error ---", e);
			throw new WxxRuntimeException("MASKFILEEXPANSION_ERROR", "预处理失败");
		}
	}

	@Override
	public DesSliceLocationDTO desSlice(String mapKey, int jmtype, int cardnum, long speed, int sunzinum, DesSliceLocationDTO sliceLoc) {
		DesSliceLocationDTO desSliceLocationDTO = null;
		PwdsliceLibrary.DesSliceLocation.ByValue pdbv = null;
		PwdsliceLibrary.DesSliceLocation.ByReference pdbr = null;
		try {
			logger.debug("-- Password_DesSlices slice mapKey:{}, jmtype:{}, cardnum:{}, speed:{}, sunzinum:{}--", mapKey, jmtype, cardnum, speed, sunzinum);
			logger.debug("-- Password_DesSlices slice DesSliceLocationDTO sliceLoc: {}--", GsonUtil.GsonString(sliceLoc));
			pdbv = getDesValue(mapKey, sliceLoc);
			pdbr = new PwdsliceLibrary.DesSliceLocation.ByReference();
			byte result = PwdsliceLibrary.INSTANCE.Password_DesSlices(jmtype, cardnum, speed, sunzinum, pdbv, pdbr);
			logger.debug("---------------- Password_DesSlices slice mapKey:{}, result: {}--------------------", mapKey, result);
			if (0 == result) {
				desSliceLocationDTO = new DesSliceLocationDTO();
				desSliceLocationDTO.setVaild(pdbr.vaild);
				desSliceLocationDTO.setFinish(pdbr.finish);
				desSliceLocationDTO.setDesLoc(ByteUtil.bytesToString(pdbr.desLoc).trim());
				desSliceLocationDTO.setOutput(ByteUtil.bytesToString(pdbr.output).trim());
				desSliceLocationDTO.setCutFileName(ByteUtil.byteToStringArray(pdbr.cutFileName));

				if (1 == desSliceLocationDTO.getFinish()) {
					valueMap.remove(mapKey);
					referMap.remove(mapKey);
				} else {
					if (!existSlice(mapKey)) {
						valueMap.put(mapKey, pdbv);
					}
				}
				referMap.put(mapKey, pdbr);
			} else {
				long taskId = getTaskId(mapKey);
				logger.debug("---------------- Password_DesSlices slice failed taskId: {}--------------------", taskId);
				// 获取主任务
				TaskDTO taskDTO = taskMapper.getTaskById(taskId);
				// 更新主任状态日志
				LoggerDTO loggerDTO = new LoggerDTO();
				loggerDTO.setUserName("system");
				loggerDTO.setCreateUser("system");
				loggerDTO.setActionMsg("任务des切片失败");
				loggerDTO.setModeType("任务模块");
				loggerDTO.setSuccessMsg(taskDTO.getName() + " 任务des切片失败");
				loggerDTO.setLevel(LoggerLevelType.ALERT.getLevel());
				loggerDTO.setStatus(LoggerStatusType.SUCCESS.getStatus());
				hzLoggerMapper.createLogger(loggerDTO);
				// 停止任务
				stopTask(taskId);
			}
			logger.debug("-- Password_DesSlices result: {}--", GsonUtil.GsonString(desSliceLocationDTO));
			return desSliceLocationDTO;
		} catch (Exception e) {
			logger.error("--- desSlices Password_DesSlices error ---", e);
			throw new WxxRuntimeException("DESSLICES_ERROR", "DES切片失败");
		} finally {
			pdbv = null;
			pdbr = null;
		}
	}

	@Override
	public MaskSliceLocationDTO maskSlice(String mapKey, int jmtype, int cardnum, long speed, int sunzinum, MaskSliceLocationDTO sliceLoc) {
		MaskSliceLocationDTO maskSliceLocationDTO = null;
		PwdsliceLibrary.MaskSliceLocation.ByValue pmbv = null;
		PwdsliceLibrary.MaskSliceLocation.ByReference pmbr = null;
		try {
			logger.debug("-- Password_MaskSlices slice mapKey:{}, jmtype:{}, cardnum:{}, speed:{}, sunzinum:{}--", mapKey, jmtype, cardnum, speed, sunzinum);
			logger.debug("-- Password_MaskSlices slice MaskSliceLocationDTO sliceLoc: {}--", GsonUtil.GsonString(sliceLoc));

			pmbv = getMaskValue(mapKey, sliceLoc);
			pmbr = new PwdsliceLibrary.MaskSliceLocation.ByReference();
			byte result = PwdsliceLibrary.INSTANCE.Password_MaskSlices(jmtype, cardnum, speed, sunzinum, pmbv, pmbr);

			logger.debug("--- Password_MaskSlices slice mapKey:{}, result: {}--", mapKey, result);
			if (0 == result) {
				maskSliceLocationDTO = new MaskSliceLocationDTO();
				maskSliceLocationDTO.setVaild(pmbr.vaild);
				maskSliceLocationDTO.setFinish(pmbr.finish);
				maskSliceLocationDTO.setFilePath(ByteUtil.bytesToString(pmbr.filePath).trim());
				maskSliceLocationDTO.setFileLoc(pmbr.fileLoc);
				maskSliceLocationDTO.setCurrentMask(ByteUtil.bytesToString(pmbr.currentMask).trim());
				maskSliceLocationDTO.setOffsetLoc(pmbr.offsetLoc);
				maskSliceLocationDTO.setOutput(ByteUtil.bytesToString(pmbr.output).trim());
				maskSliceLocationDTO.setCutFileName(ByteUtil.byteToStringArray(pmbr.cutFileName));

				if (1 == maskSliceLocationDTO.getFinish()) {
					valueMap.remove(mapKey);
					referMap.remove(mapKey);
				} else {
					if (!existSlice(mapKey)) {
						valueMap.put(mapKey, pmbv);
					}
					referMap.put(mapKey, pmbr);
				}

			} else {
				long taskId = getTaskId(mapKey);
				logger.debug("---------------- Password_MaskSlices slice failed taskId: {}--------------------", taskId);
				// 获取主任务
				TaskDTO taskDTO = taskMapper.getTaskById(taskId);
				// 更新主任状态日志
				LoggerDTO loggerDTO = new LoggerDTO();
				loggerDTO.setUserName("system");
				loggerDTO.setCreateUser("system");
				loggerDTO.setActionMsg("任务mask切片失败");
				loggerDTO.setModeType("任务模块");
				loggerDTO.setSuccessMsg(taskDTO.getName() + " 任务mask切片失败");
				loggerDTO.setLevel(LoggerLevelType.ALERT.getLevel());
				loggerDTO.setStatus(LoggerStatusType.SUCCESS.getStatus());
				hzLoggerMapper.createLogger(loggerDTO);
				// 停止任务
				stopTask(taskId);
			}
			logger.debug("-- Password_MaskSlices result: {}--", GsonUtil.GsonString(maskSliceLocationDTO));
			return maskSliceLocationDTO;
		} catch (Exception e) {
			logger.error("--- maskSlice Password_MaskSlices error ---", e);
			throw new WxxRuntimeException("MASKSLICE_ERROR", "MASK切片失败");
		} finally {
			pmbv = null;
			pmbr = null;
		}
	}

	private boolean existSlice(String valueKey) {
		return null != valueMap.get(valueKey);
	}

	private PwdsliceLibrary.MaskSliceLocation.ByValue getMaskValue(String key, MaskSliceLocationDTO sliceLoc) {
		PwdsliceLibrary.MaskSliceLocation.ByValue pmbv = null;
		if (existSlice(key)) {
			pmbv = (PwdsliceLibrary.MaskSliceLocation.ByValue) valueMap.get(key);
			PwdsliceLibrary.MaskSliceLocation.ByReference pmbr = (PwdsliceLibrary.MaskSliceLocation.ByReference) referMap.get(key);
			pmbv.vaild = pmbr.vaild;
			pmbv.finish = pmbr.finish;
			pmbv.filePath = pmbr.filePath;
			pmbv.fileLoc = pmbr.fileLoc;
			pmbv.currentMask = pmbr.currentMask;
			pmbv.offsetLoc = pmbr.offsetLoc;
			pmbv.cutFileName = pmbr.cutFileName;
		} else {
			pmbv = new PwdsliceLibrary.MaskSliceLocation.ByValue();
			pmbv.filePath = ByteUtil.stringToByteLength(sliceLoc.getFilePath(), pmbv.filePath.length);
			pmbv.currentMask = ByteUtil.currentMaskToByte(sliceLoc.getCurrentMask(), pmbv.currentMask.length);
			pmbv.output = ByteUtil.stringToByteLength(sliceLoc.getOutput(), pmbv.output.length);
			pmbv.cutFileName = ByteUtil.stringToByteLength("", pmbv.cutFileName.length);
		}
		return pmbv;
	}

	private PwdsliceLibrary.DesSliceLocation.ByValue getDesValue(String key, DesSliceLocationDTO sliceLoc) {
		PwdsliceLibrary.DesSliceLocation.ByValue pdbv = null;
		if (existSlice(key)) {
			pdbv = (PwdsliceLibrary.DesSliceLocation.ByValue) valueMap.get(key);
			PwdsliceLibrary.DesSliceLocation.ByReference pdbr = (PwdsliceLibrary.DesSliceLocation.ByReference) referMap.get(key);
			pdbv.vaild = pdbr.vaild;
			pdbv.finish = pdbr.finish;
			pdbv.desLoc = pdbr.desLoc;
			pdbv.cutFileName = pdbr.cutFileName;
		} else {
			pdbv = new PwdsliceLibrary.DesSliceLocation.ByValue();
			pdbv.desLoc = ByteUtil.stringToByteLength(sliceLoc.getDesLoc(), pdbv.desLoc.length);
			pdbv.output = ByteUtil.stringToByteLength(sliceLoc.getOutput(), pdbv.output.length);
			pdbv.cutFileName = ByteUtil.stringToByteLength("", pdbv.cutFileName.length);
		}
		return pdbv;
	}

	@Override
	public MaskSliceLocationDTO maskSliceNext(String mapKey, int jmtype, int cardnum, long speed, int sunzinum, MaskSliceLocationDTO sliceLoc) {
		long count = 0l;
		MaskSliceLocationDTO dto = null;
		while (true) {
			dto = maskSlice(mapKey, jmtype, cardnum, speed, sunzinum, sliceLoc);
			count++;
			logger.debug("----------------maskSliceNext slice mapKey:{}, finish: {}--------------------", mapKey, dto.getFinish());
			logger.debug("----------------maskSliceNext slice mapKey:{}, times: {}--------------------", mapKey, count);
			if (null != dto && 1 == dto.getFinish()) {
				valueMap.remove(mapKey);
				referMap.remove(mapKey);
				break;
			}
		}
		return dto;
	}

	@Override
	public DesSliceLocationDTO desSliceNext(String mapKey, int jmtype, int cardnum, long speed, int sunzinum, DesSliceLocationDTO sliceLoc) {
		long count = 0l;
		DesSliceLocationDTO dto = null;
		while (true) {
			dto = desSlice(mapKey, jmtype, cardnum, speed, sunzinum, sliceLoc);
			count++;
			logger.debug("----------------desSliceNext slice mapKey:{}, finish: {}--------------------", mapKey, dto.getFinish());
			logger.debug("----------------desSliceNext slice mapKey:{}, times: {}--------------------", mapKey, count);
			if (null != dto && 1 == dto.getFinish()) {
				valueMap.remove(mapKey);
				referMap.remove(mapKey);
				break;
			}
		}
		return dto;
	}

	@Override
	public DesSliceLocationDTO desSlice(int jmtype, int cardnum, long speed, int sunzinum, DesSliceLocationDTO sliceLoc) {
		DesSliceLocationDTO desSliceLocationDTO = null;
		PwdsliceLibrary.DesSliceLocation.ByValue pdbv = null;
		PwdsliceLibrary.DesSliceLocation.ByReference pdbr = null;
		try {
			logger.debug("-- Password_DesSlices slice jmtype:{}, cardnum:{}, speed:{}, sunzinum:{}--", jmtype, cardnum, speed, sunzinum);
			logger.debug("-- Password_DesSlices slice DesSliceLocationDTO sliceLoc: {}--", GsonUtil.GsonString(sliceLoc));

			pdbv = new PwdsliceLibrary.DesSliceLocation.ByValue();
			pdbv.finish = sliceLoc.getFinish();
			pdbv.vaild = sliceLoc.getVaild();
			pdbv.desLoc = ByteUtil.stringToByteLength(sliceLoc.getDesLoc(), pdbv.desLoc.length);
			pdbv.output = ByteUtil.stringToByteLength(sliceLoc.getOutput(), pdbv.output.length);
			pdbv.cutFileName = ByteUtil.stringToByteLength("", pdbv.cutFileName.length);

			pdbr = new PwdsliceLibrary.DesSliceLocation.ByReference();
			byte result = PwdsliceLibrary.INSTANCE.Password_DesSlices(jmtype, cardnum, speed, sunzinum, pdbv, pdbr);
			logger.debug("---------------- Password_DesSlices slice result: {}--------------------", result);
			if (0 == result) {
				desSliceLocationDTO = new DesSliceLocationDTO();
				desSliceLocationDTO.setVaild(pdbr.vaild);
				desSliceLocationDTO.setFinish(pdbr.finish);
				desSliceLocationDTO.setDesLoc(ByteUtil.bytesToString(pdbr.desLoc).trim());
				desSliceLocationDTO.setOutput(ByteUtil.bytesToString(pdbr.output).trim());
				desSliceLocationDTO.setCutFileName(ByteUtil.byteToStringArray(pdbr.cutFileName));
			}
			logger.debug("-- Password_DesSlices result: {}--", GsonUtil.GsonString(desSliceLocationDTO));
			return desSliceLocationDTO;
		} catch (Exception e) {
			logger.error("--- desSlices Password_DesSlices error ---", e);
			throw new WxxRuntimeException("DESSLICES_ERROR", "DES切片失败");
		} finally {
			pdbv = null;
			pdbr = null;
		}
	}

	@Override
	public MaskSliceLocationDTO maskSlice(int jmtype, int cardnum, long speed, int sunzinum, MaskSliceLocationDTO sliceLoc) {
		MaskSliceLocationDTO maskSliceLocationDTO = null;
		PwdsliceLibrary.MaskSliceLocation.ByValue pmbv = null;
		PwdsliceLibrary.MaskSliceLocation.ByReference pmbr = null;
		try {
			logger.debug("-- Password_MaskSlices slice jmtype:{}, cardnum:{}, speed:{}, sunzinum:{}--", jmtype, cardnum, speed, sunzinum);
			logger.debug("-- Password_MaskSlices slice MaskSliceLocationDTO sliceLoc: {}--", GsonUtil.GsonString(sliceLoc));

			pmbv = new PwdsliceLibrary.MaskSliceLocation.ByValue();
			pmbv.vaild = sliceLoc.getVaild();
			pmbv.finish = sliceLoc.getFinish();
			pmbv.filePath = ByteUtil.stringToByteLength(sliceLoc.getFilePath(), pmbv.filePath.length);
			pmbv.currentMask = ByteUtil.currentMaskToByte(sliceLoc.getCurrentMask(), pmbv.currentMask.length);
			pmbv.output = ByteUtil.stringToByteLength(sliceLoc.getOutput(), pmbv.output.length);
			pmbv.cutFileName = ByteUtil.stringToByteLength("", pmbv.cutFileName.length);
			pmbv.fileLoc = sliceLoc.getFileLoc();
			pmbv.offsetLoc = sliceLoc.getOffsetLoc();

			pmbr = new PwdsliceLibrary.MaskSliceLocation.ByReference();
			byte result = PwdsliceLibrary.INSTANCE.Password_MaskSlices(jmtype, cardnum, speed, sunzinum, pmbv, pmbr);

			logger.debug("--- Password_MaskSlices slice result: {}--", result);
			if (0 == result) {
				maskSliceLocationDTO = new MaskSliceLocationDTO();
				maskSliceLocationDTO.setVaild(pmbr.vaild);
				maskSliceLocationDTO.setFinish(pmbr.finish);
				maskSliceLocationDTO.setFilePath(ByteUtil.bytesToString(pmbr.filePath).trim());
				maskSliceLocationDTO.setFileLoc(pmbr.fileLoc);
				maskSliceLocationDTO.setCurrentMask(ByteUtil.bytesToString(pmbr.currentMask).trim());
				maskSliceLocationDTO.setOffsetLoc(pmbr.offsetLoc);
				maskSliceLocationDTO.setOutput(ByteUtil.bytesToString(pmbr.output).trim());
				maskSliceLocationDTO.setCutFileName(ByteUtil.byteToStringArray(pmbr.cutFileName));
			}
			logger.debug("-- Password_MaskSlices result: {}--", GsonUtil.GsonString(maskSliceLocationDTO));
			return maskSliceLocationDTO;
		} catch (Exception e) {
			logger.error("--- maskSlice Password_MaskSlices error ---", e);
			throw new WxxRuntimeException("MASKSLICE_ERROR", "MASK切片失败");
		} finally {
			pmbv = null;
			pmbr = null;
		}
	}

	private long getTaskId(String mapKey) {
		String[] array = mapKey.split("_");
		if (array.length == 3) {
			return NumberUtils.toLong(array[2]);
		}
		return 0l;
	}
	
	public void stopTask(Long id) {
		// 获取主任务
		TaskDTO mainTask = taskMapper.getTaskById(id);
		if (null != mainTask) {
			if (TaskStatusType.RUNNING.getStatus() == mainTask.getStatus()) {
				// 更新主任状态
				mainTask.setStatus(TaskStatusType.FAULT.getStatus());
				taskMapper.updateTask(mainTask);
				/*// 获取子任务
				List<SubTaskDTO> subTaskDTOs = subTaskMapper.findRunSubTasks(id);
				if (null != subTaskDTOs && !subTaskDTOs.isEmpty()) {
					for (SubTaskDTO subTaskDTO : subTaskDTOs) {
						// 子任务故障
						subTaskDTO.setStatus(TaskStatusType.FAULT.getStatus());
						subTaskMapper.updateSubTask(subTaskDTO);

						// 获取设备信息
						DeviceDTO deviceDTO = deviceMapper.getDeviceByTaskId(subTaskDTO.getId());

						// 给节点发送暂停任务请求
						if (null != deviceDTO) {
							stopSubTask(deviceDTO, subTaskDTO.getId());
						}
					}
				}*/
			}
		}
	}
	
	
	public void stopSubTask(DeviceDTO deviceDTO, Long id) {
		try {
			String url = null;
			if (NodeType.GPU.getType() == deviceDTO.getNodeType().intValue()) {
				url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_TASK_STOP_GPU, deviceDTO.getIp(), deviceDTO.getPort());
			} else {
				url = WebUrlConstant.getRequestUrl(WebUrlConstant.URL_TASK_STOP_FPGA, deviceDTO.getIp(), deviceDTO.getPort(), id + "");
			}
			BaseRespDTO baseRespDTO = requestService.doHttpGet(url, BaseRespDTO.class);
			if (null != baseRespDTO && WebUrlConstant.REQUEST_SUCCESS.equals(baseRespDTO.getResult())) {
				logger.debug("-- stopSubTask ip:{}, port:{}, subTaskId:{} success --", deviceDTO.getIp(), deviceDTO.getPort(), id);
			}

			// 是否设备
			deviceDTO.setTaskId(null);
			deviceMapper.updateDevice(deviceDTO);
		} catch (Exception e) {
			logger.debug("-- stopSubTask ip:{}, port:{}, subTaskId:{} error --", deviceDTO.getIp(), deviceDTO.getPort(), id);
		}
	}

}
