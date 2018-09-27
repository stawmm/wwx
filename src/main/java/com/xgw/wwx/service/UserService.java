package com.xgw.wwx.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xgw.wwx.dto.common.CommonResponseDTO;
import com.xgw.wwx.dto.db.UserDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

	public UserDTO getUserById(Long id);

	public UserDTO getUserByName(String userName);

	public PageInfo<UserDTO> findUsersByPage(Integer pageSize, Integer pageNum, Map<String, Object> params);



	public List<UserDTO> findUsers(Map<String, Object> params);

	public void createUser(UserDTO userDTO);

	public void updateUser(UserDTO userDTO);

	public void deleteUser(Long id);
	public void deleteUserAll(List delList);
	
	public void resetPassWord(Long id);

	public void disabledUser(Long id);

	public void enableUser(Long id);

	public void updatePwd(UserDTO userDTO);

}
