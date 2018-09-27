package com.xgw.wwx.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xgw.wwx.dto.common.CommonResponseDTO;
import com.xgw.wwx.service.SqlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Created by EX-ZHONGJUN001 on 2018/4/8.
 */
@RequestMapping("/system")
@RestController
@Api(value = "系统接口", tags = "系统操作接口")
public class SystemController {

	private static final Logger logger = LoggerFactory.getLogger(SystemController.class);

	@Autowired
	private SqlService sqlService;

	@GetMapping("/sql/init")
	@ApiOperation(value = "初始化sql", notes = "数据库初始化sql")
	@ApiImplicitParam(name = "tk", value = "token参数", required = true, dataType = "String", paramType = "query")
	public ResponseEntity<CommonResponseDTO<Boolean>> sqlInit(@RequestParam("tk") String tk) {
		try {
			Assert.notNull(tk, "token参数为空");
			if (!"WwxAdmin".endsWith(tk)) {
				throw new RuntimeException("token参数不匹配");
			}
			sqlService.executeResourceSql("/sql/wwx_ddl.sql");
			sqlService.executeResourceSql("/sql/wwx_dml.sql");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (Exception e) {
			logger.error("-- sqlInit error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>("SQL_INIT_ERROR", "数据库SQL初始化异常"));
		}
	}

	@GetMapping("/sql/reInit")
	@ApiOperation(value = "重新初始化sql", notes = "数据库重新初始化sql")
	@ApiImplicitParam(name = "tk", value = "token参数", required = true, dataType = "String", paramType = "query")
	public ResponseEntity<CommonResponseDTO<Boolean>> sqlReInit(@RequestParam("tk") String tk) {
		try {
			Assert.notNull(tk, "token参数为空");
			if (!"WwxAdmin".endsWith(tk)) {
				throw new RuntimeException("token参数不匹配");
			}
			sqlService.executeResourceSql("/sql/wwx_rollback.sql");
			sqlService.executeResourceSql("/sql/wwx_ddl.sql");
			sqlService.executeResourceSql("/sql/wwx_dml.sql");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (Exception e) {
			logger.error("-- sqlReInit error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>("SQL_RE_INIT_ERROR", "数据库SQLc重新初始化异常"));
		}
	}

	@GetMapping("/sql/rollback")
	@ApiOperation(value = "回滚所有sql", notes = "数据库回滚所有sql")
	@ApiImplicitParam(name = "tk", value = "token参数", required = true, dataType = "String", paramType = "query")
	public ResponseEntity<CommonResponseDTO<Boolean>> sqlRollback(@RequestParam("tk") String tk) {
		try {
			Assert.notNull(tk, "token参数为空");
			if (!"WwxAdmin".endsWith(tk)) {
				throw new RuntimeException("token参数不匹配");
			}
			sqlService.executeResourceSql("/sql/wwx_rollback.sql");
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (Exception e) {
			logger.error("-- sqlRollback error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>("SQL_ROLLBACK_ERROR", "数据库SQL回滚异常"));
		}
	}

	@GetMapping("/sql/use/classPath")
	@ApiOperation(value = "指定运行ClassPath下的sql文件", notes = "数据库指定运行ClassPath下的sql文件")
	@ApiImplicitParams({ @ApiImplicitParam(name = "tk", value = "token参数", required = true, dataType = "String", paramType = "query"), @ApiImplicitParam(name = "classPath", value = "classPath路径", required = true, dataType = "String", paramType = "query") })
	public ResponseEntity<CommonResponseDTO<Boolean>> sqlUseClassPath(@RequestParam("tk") String tk, @RequestParam("classPath") String classPath) {
		try {
			Assert.notNull(tk, "token参数为空");
			Assert.notNull(classPath, "路径不能为空");
			if (!"WwxAdmin".endsWith(tk)) {
				throw new RuntimeException("token参数不匹配");
			}
			sqlService.executeResourceSql(classPath);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (Exception e) {
			logger.error("-- sqlUseClassPath error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>("SQL_USE_CLASSPATH_ERROR", "数据库运行指定sql文件异常"));
		}
	}

	@GetMapping("/sql/use/filePath")
	@ApiOperation(value = "指定运行FilePath(系统路径)下的sql文件", notes = "数据库指定运行FilePath(系统路径)下的sql文件")
	@ApiImplicitParams({ @ApiImplicitParam(name = "tk", value = "token参数", required = true, dataType = "String", paramType = "query"), @ApiImplicitParam(name = "filePath", value = "filePath路径", required = true, dataType = "String", paramType = "query") })
	public ResponseEntity<CommonResponseDTO<Boolean>> sqlUseFilePath(@RequestParam("tk") String tk, @RequestParam("filePath") String filePath) {
		try {
			Assert.notNull(tk, "token参数为空");
			Assert.notNull(filePath, "路径不能为空");
			if (!"WwxAdmin".endsWith(tk)) {
				throw new RuntimeException("token参数不匹配");
			}
			sqlService.executeFilePathSql(filePath);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>(Boolean.TRUE));
		} catch (Exception e) {
			logger.error("-- sqlUseFilePath error --", e);
			return ResponseEntity.ok(new CommonResponseDTO<Boolean>("SQL_USE_FILEPATH_ERROR", "数据库运行指定sql文件异常"));
		}
	}

}
