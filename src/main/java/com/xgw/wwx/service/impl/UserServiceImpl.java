package com.xgw.wwx.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xgw.wwx.common.code.UserCodeEnum;
import com.xgw.wwx.common.exception.UserRuntimeException;
import com.xgw.wwx.common.exception.WxxRuntimeException;
import com.xgw.wwx.common.util.PasswordUtil;
import com.xgw.wwx.dto.db.UserDTO;
import com.xgw.wwx.mapper.UserMapper;
import com.xgw.wwx.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Value("${wwx.user.password.default}")
	private String defaultPassWord;

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDTO getUserById(Long id) {
		return userMapper.getUserById(id);
	}

	@Override
	public UserDTO getUserByName(String userName) {
		return userMapper.getUserByName(userName);
	}

	@Override
	public PageInfo<UserDTO> findUsersByPage(Integer pageSize, Integer pageNum, Map<String, Object> params) {
		PageHelper.startPage(pageNum, pageSize);
		List<UserDTO> list = userMapper.findUsers(params);
		return new PageInfo<UserDTO>(list);
	}

	@Override
	public List<UserDTO> findUsers(Map<String, Object> params) {
		return userMapper.findUsers(params);
	}

	@Override
	public void createUser(UserDTO userDTO) {
		UserDTO dbUser = userMapper.getUserByName(userDTO.getUserName());
		if (null != dbUser) {
			throw new UserRuntimeException(UserCodeEnum.USER_USERNAME_EXIST);
		}
		userMapper.createUser(userDTO);
	}

	@Override
	public void updateUser(UserDTO userDTO) {
		UserDTO checkUser = userMapper.getUserByName(userDTO.getUserName());
		if (null != checkUser && userDTO.getId().longValue() != checkUser.getId().longValue()) {
			throw new UserRuntimeException(UserCodeEnum.USER_USERNAME_EXIST);
		}
		UserDTO dbUser = userMapper.getUserById(userDTO.getId());
		dbUser.setUpdateUser(userDTO.getUpdateUser());
		dbUser.setUserType(userDTO.getUserType());
		dbUser.setSex(userDTO.getSex());
		dbUser.setRealName(userDTO.getRealName());
		dbUser.setUserNo(userDTO.getUserNo());
		dbUser.setPhone(userDTO.getPhone());
		dbUser.setUserName(userDTO.getUserName());
		userMapper.updateUser(dbUser);
	}

	@Override
	public void deleteUser(Long id) {
		userMapper.deleteUser(id);
	}

	@Override
	public void deleteUserAll(List delList){
		userMapper.deleteUserAll(delList);
	}
	@Override
	public void enableUser(Long id) {
		userMapper.enableUser(id);
	}

	@Override
	public void disabledUser(Long id) {
		userMapper.disabledUser(id);
	}

	@Override
	public void resetPassWord(Long id) {
		UserDTO userDTO = userMapper.getUserById(id);
		String pwd = PasswordUtil.md5Hex(defaultPassWord, userDTO.getSalt());
		userMapper.resetPassWord(id, pwd);
	}

	@Override
	public void updatePwd(UserDTO userDTO) {
		UserDTO dbUser = userMapper.getUserById(userDTO.getId());
		if(!StringUtils.equalsIgnoreCase(userDTO.getPassWord(), dbUser.getPassWord())) {
			throw new WxxRuntimeException("USER_PWD_ERROR","原密码不正确");
		}
		userMapper.resetPassWord(userDTO.getId(), userDTO.getNewPwd());
	}

}
