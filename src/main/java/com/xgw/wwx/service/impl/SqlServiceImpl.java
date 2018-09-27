package com.xgw.wwx.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xgw.wwx.common.util.SpringScriptUtil;
import com.xgw.wwx.service.SqlService;

/**
 * Created by EX-ZHONGJUN001 on 2018/4/8.
 */
@Service
@Transactional
public class SqlServiceImpl implements SqlService {

    @Override
    public void executeResourceSql(String classPath) {
        Assert.notNull(classPath,"文件路径不能为空!");
        SpringScriptUtil.executeWithClassPath(classPath);
    }

    @Override
    public void executeFilePathSql(String filePath) {
        Assert.notNull(filePath,"文件路径不能为空!");
        SpringScriptUtil.executeWithFilePath(filePath);
    }

}
