package com.xgw.wwx.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xgw.wwx.dto.db.DictDTO;

public interface DictService {

	public DictDTO getDictById(Long id);

	public DictDTO getDictByName(String name);

	public PageInfo<DictDTO> findDictsByPage(Integer pageSize, Integer pageNum, Map<String, Object> params);

	public List<DictDTO> findDicts(Map<String,Object> params);

    public void createDict(DictDTO dictDTO);

    public void updateDict(DictDTO dictDTO);

    public void deleteDict(Long id);

	/*public void deleteDictAll(List delList);*/

    public int deleteDictAll(int[] ids);


	public void distributionDict(DictDTO dictDTO);

	public List<DictDTO> findDictList(String userName);

	public List<DictDTO> findDictListByTaskId(Long taskId);

}
