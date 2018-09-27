-- 创建用户表t_wwx_user
CREATE TABLE `t_wwx_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(512) NOT NULL COMMENT '用户名',
  `pass_word` varchar(512) NOT NULL COMMENT '密码',
  `salt` varchar(512) DEFAULT NULL COMMENT '密码盐',
  `user_no` varchar(512) NOT NULL COMMENT '员工编号',
  `real_name` varchar(512) NOT NULL COMMENT '姓名',
  `sex` tinyint(1) NOT NULL COMMENT '性别：1男0女',
  `phone` varchar(512) NOT NULL COMMENT '手机号',
  `user_type` tinyint(1) NOT NULL COMMENT '用户类型：1管理员2普通用户',
  `status` tinyint(1) DEFAULT 1 COMMENT '用户状态：1启用2禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;


-- 创建日志表t_wwx_log
CREATE TABLE `t_wwx_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(512) DEFAULT NULL COMMENT '用户',
  `user_type` varchar(512) DEFAULT NULL COMMENT '用户类型',
  `method` varchar(512) DEFAULT NULL COMMENT '请求方法',
  `url` varchar(512) DEFAULT NULL COMMENT '请求路径',
  `params` text DEFAULT NULL COMMENT '参数',
  `error_msg` text DEFAULT NULL COMMENT '错误信息',
  `mode_type` varchar(512) DEFAULT NULL COMMENT '所属模块',
  `level` varchar(512) DEFAULT NULL COMMENT '级别',  
  `status` tinyint(1) DEFAULT 1 COMMENT '日志状态：1成功0失败',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;


-- 创建字典表t_wwx_map
CREATE TABLE `t_wwx_map` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(512) DEFAULT NULL COMMENT '类型',
  `key` varchar(512) DEFAULT NULL COMMENT '键',
  `value` varchar(512) DEFAULT NULL COMMENT '值',
  `value_name` varchar(512) DEFAULT NULL COMMENT '值',
  `order` int(4) DEFAULT NULL COMMENT '顺序',
  `parent_id` bigint DEFAULT NULL COMMENT '父级ID',  
  `status` tinyint(1) DEFAULT 1 COMMENT '状态：1成功0失败',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

-- 创建设备表
CREATE TABLE `t_wwx_device` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ip` varchar(512) DEFAULT NULL COMMENT '设备IP',
  `name` varchar(512) DEFAULT NULL COMMENT '设备名称',
  `desc` varchar(512) DEFAULT NULL COMMENT '设备描述',
  `port` int(6) DEFAULT NULL COMMENT '设备端口', 
  `mac` varchar(512) DEFAULT NULL COMMENT '设备MAC',
  `node_type` varchar(512) DEFAULT NULL COMMENT '设备类型',
  `card_num` tinyint(4)  DEFAULT NULL COMMENT '板卡数量',
  `alg_num` tinyint(4)  DEFAULT NULL COMMENT '算法数量',
  `dict_num` tinyint(4)  DEFAULT NULL COMMENT '字典数量',
  `role_type` tinyint(1) DEFAULT '1' COMMENT '1客户端2服务端',
  `fault_times` tinyint(4)  DEFAULT NULL COMMENT '设备故障次数',
  `task_id` bigint  DEFAULT NULL COMMENT '设备上运行的任务',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用2禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;


-- 创建设备板卡表
CREATE TABLE `t_wwx_device_card` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `device_id` bigint DEFAULT NULL COMMENT '设备Id',
  `card_type` varchar(512) DEFAULT NULL COMMENT '板卡类型',
  `card_version` varchar(512) DEFAULT NULL COMMENT '板卡版本',
  `chip_num` tinyint(4)  DEFAULT NULL COMMENT '芯片数量',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用0禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;


-- 创建设备支持的算法清单
CREATE TABLE `t_wwx_alg` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `node_type` tinyint(4) DEFAULT NULL COMMENT '节点类型',
  `card_type` tinyint(4) DEFAULT NULL COMMENT '板卡类型',
  `alg_id` bigint DEFAULT NULL COMMENT '算法ID',
  `alg_type` tinyint(4) DEFAULT NULL COMMENT '算法类型',
  `alg_type_name` varchar(512) DEFAULT NULL COMMENT '算法类型名称',
  `alg_name` varchar(512) DEFAULT NULL COMMENT '算法名称',
  `speed` bigint  DEFAULT NULL COMMENT '算法速率',
  `core_num` bigint  DEFAULT NULL COMMENT '算核',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用0禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;


-- 字典表
CREATE TABLE `t_wwx_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(512) DEFAULT NULL COMMENT '字典名称',
  `group` varchar(512) DEFAULT NULL COMMENT '字典组',
  `size` bigint DEFAULT NULL COMMENT '字典大小',
  `desc` varchar(512) DEFAULT NULL COMMENT '字典说明',
  `type` tinyint(1) DEFAULT '1' COMMENT '1默认2未知',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用2禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

-- 创建设备支持的算法清单
CREATE TABLE `t_wwx_device_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_id` bigint DEFAULT NULL COMMENT '设备Id',
  `device_id` bigint DEFAULT NULL COMMENT '设备Id',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;


-- 创建任务表
CREATE TABLE `t_wwx_task` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(512) DEFAULT NULL COMMENT '任务名称',
  `desc` varchar(512) DEFAULT NULL COMMENT '任务描述',
  `type` bigint DEFAULT NULL COMMENT '任务类型',
  `has_vpndes` TINYINT(1) DEFAULT NULL COMMENT '是否穷举',
  `remain_time` bigint DEFAULT NULL COMMENT '剩余时间',
  `predict_time` bigint DEFAULT NULL COMMENT '预计运行时间',
  `hit_pwd` varchar(512) DEFAULT NULL COMMENT '命中密码',
  `dict_command` bigint DEFAULT NULL COMMENT '字典口令总数',
  `dict_run_command` bigint DEFAULT NULL COMMENT '字典运行的口令总数',
  `strategy_command` bigint DEFAULT NULL COMMENT '策略口令总数',
  `strategy_run_command` bigint DEFAULT NULL COMMENT '策略运行的口令总数',
  `total_count` tinyint(4) DEFAULT NULL COMMENT '子任务总数',
  `run_count` tinyint(4) DEFAULT NULL COMMENT '运行子任务',
  `remain_count` tinyint(4) DEFAULT NULL COMMENT '剩余子任务',
  `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `his_async` tinyint(1) DEFAULT '0' COMMENT '0未同步1已同步',
  `status` tinyint(1) DEFAULT '1' COMMENT '0排队1完成2命中3运行中',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 任务破解信息
CREATE TABLE `t_wwx_task_file` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_id` bigint DEFAULT NULL COMMENT '任务Id',
  `file_name` varchar(512) DEFAULT NULL COMMENT '破解文件名称',
  `file_path` varchar(512) DEFAULT NULL COMMENT '破解文件路径',
  `alg_name` varchar(512) DEFAULT NULL COMMENT '算法名称',
  `alg_id` bigint DEFAULT NULL COMMENT '算法id',
  `special_char` text DEFAULT NULL COMMENT '特征串',
  `special_file_name` varchar(512) DEFAULT NULL COMMENT '提串文件名称',
  `special_file_path` varchar(512) DEFAULT NULL COMMENT '提串文件路径',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用2禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 任务资源
CREATE TABLE `t_wwx_task_resource` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_id` bigint DEFAULT NULL COMMENT '任务Id',
  `node_type` bigint DEFAULT NULL COMMENT '节点类型',
  `node_name` bigint DEFAULT NULL COMMENT '节点名称',
  `node_num` bigint DEFAULT NULL COMMENT '节点数量',
  `total_num` bigint DEFAULT NULL COMMENT '任务创建时数量',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用2禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 任务字典
CREATE TABLE `t_wwx_task_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_id` bigint NOT NULL COMMENT '任务Id',
  `dict_id` bigint DEFAULT NULL COMMENT '字典ID',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用2禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 任务策略
CREATE TABLE `t_wwx_task_strategy` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_id` bigint DEFAULT NULL COMMENT '任务Id',
  `strategy_id` bigint DEFAULT NULL COMMENT '策略Id',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用2禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 字典文件信息
CREATE TABLE `t_wwx_task_strategy_file` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_id` bigint DEFAULT NULL COMMENT '任务Id',
  `file_name` varchar(512) DEFAULT NULL COMMENT '文件名',
  `file_path` varchar(512) DEFAULT NULL COMMENT '文件路径',
  `prepare` bigint DEFAULT NULL  COMMENT '是否预处理',
  `vaild` bigint DEFAULT NULL  COMMENT '是否有效',
  `finish` bigint DEFAULT NULL  COMMENT '是否完成',
  `file_loc` bigint DEFAULT NULL  COMMENT 'mask文件内容位置偏移',
  `current_mask` varchar(512) DEFAULT NULL COMMENT '将要切片的掩码',
  `offset_loc` bigint DEFAULT NULL  COMMENT '表示掩码切分位',
  `output` varchar(512) DEFAULT NULL COMMENT '切片文件输出路径',
  `cut_file_name` varchar(512) DEFAULT NULL COMMENT '切片文件名',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用0禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 字典文件信息
CREATE TABLE `t_wwx_task_vpndes` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_id` bigint DEFAULT NULL COMMENT '任务Id',
  `file_name` varchar(512) DEFAULT NULL COMMENT '文件名',
  `file_path` varchar(512) DEFAULT NULL COMMENT '文件路径',
  `vaild` bigint DEFAULT NULL  COMMENT '是否有效',
  `finish` bigint DEFAULT NULL  COMMENT '是否完成',
  `des_loc` varchar(512) DEFAULT NULL COMMENT '切片定位',
  `output` varchar(512) DEFAULT NULL COMMENT '切片文件输出路径',
  `cut_file_name` varchar(512) DEFAULT NULL COMMENT '切片文件名',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用0禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 创建子任务
CREATE TABLE `t_wwx_sub_task` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_id` bigint DEFAULT NULL COMMENT '任务Id',
  `device_id` bigint DEFAULT NULL COMMENT '任务Id',
  `card_type` bigint DEFAULT NULL COMMENT '板卡类型',
  `alg_id` bigint DEFAULT NULL COMMENT '算法类型',
  `crack_info` text DEFAULT NULL COMMENT '特征串',
  `crack_mode` tinyint(6) DEFAULT NULL COMMENT '破解模式',
  `card_num` tinyint(6) DEFAULT NULL COMMENT '选用卡数',
  `cut_info_names` varchar(512) DEFAULT NULL COMMENT '提串切片文件',
  `time_count` bigint DEFAULT NULL COMMENT '预估时间',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用2禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 创建历史任务表
CREATE TABLE `t_wwx_task_history` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_id` bigint NOT NULL COMMENT '任务ID',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用2禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 创建策略表
-- ALTER TABLE `t_wwx_strategy` add COLUMN (`type` tinyint(1) DEFAULT 1 COMMENT '1定长2不定长3高级策略');
CREATE TABLE `t_wwx_strategy` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(512) NOT NULL COMMENT '名称',
  `desc` varchar(512) NOT NULL COMMENT '描述',
  `type` tinyint(1) DEFAULT 1 COMMENT '1定长2不定长3高级策略',
  `start_length` bigint DEFAULT NULL COMMENT '开始长度',
  `end_length` bigint DEFAULT NULL COMMENT '结束长度',
  `express` varchar(512) DEFAULT NULL COMMENT '表达式',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用2禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 策略切片信息
CREATE TABLE `t_wwx_mask_slice` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sub_task_id` bigint DEFAULT NULL COMMENT '任务Id',
  `file_name` varchar(512) DEFAULT NULL COMMENT '文件名',
  `file_path` varchar(512) DEFAULT NULL COMMENT '文件路径',
  `map_key` varchar(512) DEFAULT NULL COMMENT '指针key',
  `jmtype` int(12) DEFAULT NULL COMMENT '节点类型',
  `cardnum` int(12) DEFAULT NULL COMMENT '把卡数量',
  `sunzinum` int(12) DEFAULT NULL COMMENT '算核',
  `speed` bigint DEFAULT NULL  COMMENT '速率',
  `vaild` bigint DEFAULT NULL  COMMENT '是否有效',
  `finish` bigint DEFAULT NULL  COMMENT '是否完成',
  `file_loc` bigint DEFAULT NULL  COMMENT 'mask文件内容位置偏移',
  `current_mask` varchar(512) DEFAULT NULL COMMENT '将要切片的掩码',
  `offset_loc` bigint DEFAULT NULL  COMMENT '表示掩码切分位',
  `output` varchar(512) DEFAULT NULL COMMENT '切片文件输出路径',
  `cut_file_name` varchar(512) DEFAULT NULL COMMENT '切片文件名',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用0禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- des切片信息
CREATE TABLE `t_wwx_des_slice` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sub_task_id` bigint DEFAULT NULL COMMENT '任务Id',
  `file_name` varchar(512) DEFAULT NULL COMMENT '文件名',
  `file_path` varchar(512) DEFAULT NULL COMMENT '文件路径',
  `map_key` varchar(512) DEFAULT NULL COMMENT '指针key',
  `jmtype` int(12) DEFAULT NULL COMMENT '节点类型',
  `cardnum` int(12) DEFAULT NULL COMMENT '把卡数量',
  `sunzinum` int(12) DEFAULT NULL COMMENT '算核',
  `speed` bigint DEFAULT NULL  COMMENT '速率',
  `vaild` bigint DEFAULT NULL  COMMENT '是否有效',
  `finish` bigint DEFAULT NULL  COMMENT '是否完成',
  `des_loc` varchar(512) DEFAULT NULL COMMENT '切片定位',
  `output` varchar(512) DEFAULT NULL COMMENT '切片文件输出路径',
  `cut_file_name` varchar(512) DEFAULT NULL COMMENT '切片文件名',
  `status` tinyint(1) DEFAULT '1' COMMENT '1启用0禁用',
  `create_user` varchar(512) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(512) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_hz_logger` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户',
  `mode_type` varchar(255) DEFAULT NULL COMMENT '所属模块',
  `action_msg` varchar(255) DEFAULT NULL COMMENT '操作信息',
  `success_msg` varchar(255) DEFAULT NULL COMMENT '成功信息',
  `error_code` varchar(255) DEFAULT NULL COMMENT '错误码',
  `error_msg` varchar(255) DEFAULT NULL COMMENT '错误信息',
  `status` tinyint(1) DEFAULT 1 COMMENT '日志状态：1成功0失败',
  `memo` varchar(255) DEFAULT NULL COMMENT '错误信息',
  `level` varchar(255) DEFAULT NULL COMMENT '日志级别',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

