package com.xgw.wwx.config.job;

import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.common.helper.AuthHelper;
import com.xgw.wwx.dto.db.*;
import com.xgw.wwx.service.HzLoggerService;
import com.xgw.wwx.service.TaskService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.*;

public class ReadTaskFileJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(ReadTaskFileJob.class);

    private static final String TASK_FILE_PATH = "E:\\test";	//文件存放路径

    @Autowired
    private TaskService taskService;

    @Autowired
    HzLoggerService hzLoggerService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.debug("-- ReadTaskFileJob execute start --  ");
        //1.破解文件
        List<CarryDTO> carryDTOS = null;
        Long algId = 100000L;
        List<File> files = getFiles(TASK_FILE_PATH);
        if (null == files || files.size() == 0) {
            throw new WxxRuntimeException("FILE_CARRY_EMPTY", "破解文件为空");
        }
        // 遍历、破解文件
        carryDTOS = new ArrayList<CarryDTO>();
        Set<Long> hashModelSet = new HashSet<Long>();
        for (File file : files) {
            System.out.println(file.getName());
            String carryPath = TASK_FILE_PATH + "/" + System.currentTimeMillis() + "_" + file.getName();
            CarryDTO carryDTO = taskService.taskCarryOn(carryPath, algId);
            logger.info("-- fileCarryOn message carryDTO=[{}] --", carryDTO);
            if (!carryDTO.isResult()) {
                throw new WxxRuntimeException("FILE_CARRY_ERROR", "提串结果失败");
            } else {
                Long hashMode = carryDTO.getHashModel();
                if (null == hashMode || !taskService.checkHashMode(hashMode)) {
                    throw new WxxRuntimeException("ALG_ID_IS_EMPTY", "算法不支持: " + hashMode);
                }
            }
            carryDTO.setFileName(file.getName());
            carryDTO.setCarryPath(carryPath);
            // 天加到setl
            hashModelSet.add(carryDTO.getHashModel());
            // 添加到list
            carryDTOS.add(carryDTO);
        }// 算法类型不一致
        if (hashModelSet.size() != 1) {
            throw new WxxRuntimeException("FILE_ALG_ID_ERROR", "破解文件算法ID不一致");
        }

        //2.配置资源
        List<TaskResourceDTO> resourceList = taskService.findTaskResourceList();

        //3.口令计算

        //字典预估时间
        TaskTimeCountDTO taskTimeCountDTO = new TaskTimeCountDTO();
        Long count = taskService.vpndesTimeCount(taskTimeCountDTO);
        //策略预估时间
        logger.info("---------: {}", taskTimeCountDTO.getSpeed());
        Long count1 = taskService.strategyTimeCount(taskTimeCountDTO);

        //4.创建任务
        TaskDTO taskDTO = new TaskDTO();
        List<TaskFileDTO> filelist = taskDTO.getFiles();

        // 批量创建任务
        for (TaskFileDTO file1 : filelist) {
            taskDTO.setFile(file1);
            taskService.createTask(taskDTO);
        }

        logger.debug("-- ReadTaskFileJob execute end --  ");

    }

    /**
     * 获取文件夹下所有文件名(可以递归拿到文件名)
     */
    public  List<File> getFiles(String path){
        File file = new File(path);
        List<File> files = new ArrayList<File>();
        if(!file.isDirectory()){
            files.add(file);
        }else{
            File[] subFiles = file.listFiles();
            for(File f : subFiles){
                files.addAll(getFiles(f.getAbsolutePath()));
            }
        }
        return files;
    }
    /**
     * 获取文件夹下所有文件名(不能递归拿到文件)
     */
    public static List<String> getFile(String path) {
        // get file list where the path has
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();
        List<String> names = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                names.add(array[i].getName());
            } else if (array[i].isDirectory()) {
                getFile(array[i].getPath());
            }
        }
        return names;
    }


}
