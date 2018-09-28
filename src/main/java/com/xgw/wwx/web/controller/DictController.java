package com.xgw.wwx.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xgw.wwx.dto.db.TaskDTO;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.xgw.wwx.common.code.BaseCodeEnum;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.common.helper.AuthHelper;
import com.xgw.wwx.common.helper.DictHelper;
import com.xgw.wwx.common.util.FileUtil;
import com.xgw.wwx.dto.common.CommonResponseDTO;
import com.xgw.wwx.dto.db.DictDTO;
import com.xgw.wwx.service.DictService;
import com.xgw.wwx.service.HzLoggerService;

@RestController
@RequestMapping("/dict")
public class DictController {

	private static final Logger logger = LoggerFactory.getLogger(DictController.class);

	@Autowired
	private HzLoggerService hzLoggerService;

	@Value("${wwx.dict.upgrade.dir}")
	private String dictUpgradePath;

	@Autowired

	private DictService dictService;

	@GetMapping("/find/{id}")
	public ResponseEntity<CommonResponseDTO<DictDTO>> getDictById(@PathVariable("id") Long id) {
		try {
			logger.info("-- info message, id={} --", id);
			DictDTO dictDTO = dictService.getDictById(id);
			return ResponseEntity.ok(new CommonResponseDTO<DictDTO>(dictDTO));
		} catch (WxxRuntimeException e) {
			logger.error("-- getDictById Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<DictDTO>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- getDictById Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<DictDTO>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/find/list")
	public ResponseEntity<CommonResponseDTO<PageInfo<DictDTO>>> findDicts(HttpServletRequest request, DictDTO dictDTO) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("searchWord", dictDTO.getSearchWord());
			Integer pageNum = dictDTO.getPageNum();
			Integer pageSize = dictDTO.getPageSize();
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<DictDTO>>(dictService.findDictsByPage(pageSize, pageNum, params)));
		} catch (WxxRuntimeException e) {
			logger.error("-- findDicts Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<DictDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findDicts Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<PageInfo<DictDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/list")
	public ResponseEntity<CommonResponseDTO<List<DictDTO>>> findDictList(HttpServletRequest request) {
		try {
			String userName = AuthHelper.getUserName(request);
			return ResponseEntity.ok(new CommonResponseDTO<List<DictDTO>>(dictService.findDictList(userName)));
		} catch (WxxRuntimeException e) {
			logger.error("-- findDictList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DictDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findDictList Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DictDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@GetMapping("/task/list")
	public ResponseEntity<CommonResponseDTO<List<DictDTO>>> findDictListByTaskId(@RequestParam("taskId") Long taskId) {
		try {
			return ResponseEntity.ok(new CommonResponseDTO<List<DictDTO>>(dictService.findDictListByTaskId(taskId)));
		} catch (WxxRuntimeException e) {
			logger.error("-- findDictListByTaskId Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DictDTO>>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- findDictListByTaskId Exception error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<List<DictDTO>>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/create")
	public ResponseEntity<CommonResponseDTO<Boolean>> createDict(HttpServletRequest request, @RequestParam("group") String group, @RequestParam("files") MultipartFile[] files) {
		String userName = AuthHelper.getUserName(request);
		try {
			if (null == files || files.length == 0) {
				throw new WxxRuntimeException("DICT_FILE_IS_NULL", "字典文件为空");
			}
			for (MultipartFile file : files) {
				String fileName = file.getOriginalFilename();
				DictDTO dictDTO = dictService.getDictByName(fileName);
				if (null != dictDTO) {
					throw new WxxRuntimeException("DICT_NAME_IS_EXIST", "字典名称‘" + fileName + "’已经存在");
				}
			}
			DictDTO dictDTO = null;
			for (MultipartFile file : files) {
				// 文件位置
				String fileName = file.getOriginalFilename();
				File uploadFile = new File(dictUpgradePath + "/" + fileName);
				FileUtil.createFile(uploadFile);
				file.transferTo(uploadFile);
				// 创建字典
				dictDTO = new DictDTO();
				dictDTO.setCreateUser(userName);
				dictDTO.setName(fileName);
				dictDTO.setGroup(group);
				dictDTO.setType(1);
				dictDTO.setStatus(1);
				dictDTO.setSize(DictHelper.getDictSize(fileName));
				dictService.createDict(dictDTO);
			}
			// 创建字典成功
			hzLoggerService.createSuccessLogger(userName, "创建字典", "字典模块", "用户“" + userName + "”创建字典,字典组：“" + group + "”成功！");

			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- createDict Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "创建字典", "字典模块", e.getCode(), "用户“" + userName + "”创建字典,字典组：“" + group + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- createDict Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "创建字典", "字典模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”创建字典,字典组：“" + group + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<CommonResponseDTO<Boolean>> deleteDict(HttpServletRequest request, @PathVariable("id") Long id) {
		String userName = AuthHelper.getUserName(request);
		DictDTO dictDTO = null;
		try {
			dictDTO = dictService.getDictById(id);
			dictService.deleteDict(id);
			hzLoggerService.createSuccessLogger(userName, "删除字典", "字典模块", "用户“" + userName + "”删除字典,字典组：“" + dictDTO.getGroup() + "”成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- deleteDict Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "删除字典", "字典模块", e.getCode(), "用户“" + userName + "”删除字典,字典组：“" + dictDTO.getGroup() + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- deleteDict Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "删除字典", "字典模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”删除字典,字典组：“" + dictDTO.getGroup() + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@DeleteMapping("/deleteAll")
	public ResponseEntity<CommonResponseDTO<Boolean>> deleteDictAll( HttpServletRequest request,@RequestParam("id") int[] id ) {
		String userName = AuthHelper.getUserName(request);
		try {
			dictService.deleteDictAll(id);
			hzLoggerService.createSuccessLogger(userName, "删除全部字典", "字典模块", "用户“" + userName + "”删除字典“" + "”成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- deleteDict Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "删除全部字典", "字典模块", e.getCode(), "用户“" + userName + "”删除字典“"  + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- deleteDict Excepti" + "on error --", e);
			hzLoggerService.createFailedLogger(userName, "删除全部字典", "字典模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”删除字典“"  + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

	@PostMapping("/distribution")
	public ResponseEntity<CommonResponseDTO<Boolean>> distributionDict(HttpServletRequest request, @RequestBody DictDTO dictDTO) {
		String userName = AuthHelper.getUserName(request);
		try {
			dictService.distributionDict(dictDTO);
			hzLoggerService.createSuccessLogger(userName, "分配字典", "字典模块", "用户“" + userName + "”分配字典,字典组：“" + dictDTO.getGroup() + "”成功！");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE, true));
		} catch (WxxRuntimeException e) {
			logger.error("-- distributionDict Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "分配字典", "字典模块", e.getCode(), "用户“" + userName + "”分配字典,字典组：“" + dictDTO.getGroup() + "”失败！错误：" + e.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(e.getCode(), e.getMessage()));
		} catch (Exception e) {
			logger.error("-- createDict Exception error --", e);
			hzLoggerService.createFailedLogger(userName, "分配字典", "字典模块", BaseCodeEnum.SYSTEM_ERROR.getCode(), "用户“" + userName + "”分配字典,字典组：“" + dictDTO.getGroup() + "”失败！错误：" + BaseCodeEnum.SYSTEM_ERROR.getMessage());
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(BaseCodeEnum.SYSTEM_ERROR.getCode(), BaseCodeEnum.SYSTEM_ERROR.getMessage()));
		}
	}

}
