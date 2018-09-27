package com.xgw.wwx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xgw.wwx.dto.db.UserDTO;

public interface UserMapper {

	public UserDTO getUserById(Long id);

	public UserDTO getUserByName(String userName);

	public List<UserDTO> findUsers(Map<String, Object> params);

	public void createUser(UserDTO userDTO);

	public void updateUser(UserDTO userDTO);

	public void deleteUser(Long id);

	public void deleteUserAll(List delList);

	public void enableUser(Long id);

	public void disabledUser(Long id);

	public void resetPassWord(@Param("id") Long id, @Param("pwd") String pwd);

}
