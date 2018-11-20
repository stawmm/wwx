package com.xgw.wwx.config.job;

import com.xgw.wwx.service.TaskService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadTaskFileJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(ReadTaskFileJob.class);

    private static ArrayList<String> listname = new ArrayList<String>();


    @Autowired
    private TaskService taskService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        logger.debug("-- ReadTaskFileJob execute start --  "+ df.format(new Date()));
        /*getFile("E:\\test");*/
        List<File> files = getFiles("E:\\images");
        for(File f : files){
            System.out.println(f.getName());
        }
        System.out.println(files.size());


       /* List<String> filess = getFile("E:\\images");
        for(String fe : filess){
            System.out.println(fe);
        }
        System.out.println(filess.size());*/
        logger.debug("-- ReadTaskFileJob execute end --  " + df.format(new Date()));

    }
    /**
     * 获取文件夹下所有文件名11
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
    /**
     * 获取文件夹下所有文件名222
     */
    public static List<File> getFiles(String path){
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

}
