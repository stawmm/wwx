package com.xgw.wwx.mapper;

import java.util.List;
import java.util.Map;

import com.xgw.wwx.dto.db.DictDTO;
import io.swagger.models.auth.In;

public interface DictMapper {
	
	public DictDTO getDictById(Long id);
	
	public List<DictDTO> findDeviceDicts(Long deviceId);
	
	public List<DictDTO> findDicts(Map<String,Object> params);

    public void createDict(DictDTO dictDTO);
    
    public void updateDict(DictDTO dictDTO);

    public void deleteDict(Long id);

    public int deleteDictAll(int[] ids);

	/*public int  deleteDictAll(List ids);*/

/*	void deleteDictAll(List<Integer> ids);*/

	public DictDTO getDictByName(String name);

	public List<DictDTO> findAllDicts(Map<String, Object> params);

	public List<DictDTO> findDictList(String userName);

	public List<DictDTO> findDictListByTaskId(Long taskId);

	public List<DictDTO> findNoCheckDictByTaskId(Long taskId);
    
}
