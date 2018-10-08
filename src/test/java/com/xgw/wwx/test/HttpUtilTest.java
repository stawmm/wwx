package com.xgw.wwx.test;


import com.xgw.wwx.mapper.DictMapper;
import com.xgw.wwx.mapper.StrategyMapper;
import com.xgw.wwx.mapper.UserMapper;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpUtilTest {

	@Autowired
	private DictMapper dictMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private StrategyMapper strategyMapper;

	@Test
	@Ignore
	public void testGetNodeInfo() {
		/*String url = "http://192.168.1.100:8088/node/getinfo/";
		DeviceRespDTO data = HttpUtil.doHttpGet(url, DeviceRespDTO.class);
		System.out.println(data.toString());*/
	}

	@Test
	@Ignore
	public void testdeleteDictAll(){
		System.out.println("测试开始==========================");
		//简单验证结果集是否正确
		int[] ids={1,2};
		dictMapper.deleteDictAll(ids);
		System.out.println("成功=============================");

	}

	@Test
	@Ignore
	public void testdeleteUser(){
		System.out.println("测试开始====================================");
		userMapper.deleteUser(2L);
		System.out.println("成功=========================================");
	}
	@Test
	@Ignore
	public void testdeleteDict(){
		System.out.println("测试开始====================================");
		dictMapper.deleteDict(3L);
		System.out.println("成功=========================================");
	}

	@Test
	@Ignore
	public void testdeleteStrategyAll(){
		System.out.println("测试开始==========================");
		//简单验证结果集是否正确
		int[] ids={1,2};
		/*strategyMapper.deleteStrategyAll(ids);*/
		System.out.println("成功=============================");
	}




}
