-- 初始化管理员
insert into `t_wwx_user`(`user_name`,`pass_word`,`real_name`,`user_no`,`sex`,`phone`,`user_type`,`status`)
values('admin','E10ADC3949BA59ABBE56E057F20F883E','系统管理员','000000',1,'15966668888',1,1);

insert into `t_wwx_user`(`user_name`,`pass_word`,`real_name`,`user_no`,`sex`,`phone`,`user_type`,`status`)
values('root','E10ADC3949BA59ABBE56E057F20F883E','系统管理员','000000',1,'15966668888',1,1);

insert into `t_wwx_user`(`user_name`,`pass_word`,`real_name`,`user_no`,`sex`,`phone`,`user_type`,`status`)
values('guest','E10ADC3949BA59ABBE56E057F20F883E','来宾用户','000000',1,'15966668888',0,1);



-- 初始化字典
insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('USER_SEX_TYPE','1','男',1,1,'system',now());
insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('USER_SEX_TYPE','0','女',2,1,'system',now());


insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('USER_TYPE','1','管理员',1,1,'system',now());
insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('USER_TYPE','0','普通用户',2,1,'system',now());


insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('NODE_TYPE','0','GPU节点',1,1,'system',now());
insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('NODE_TYPE','1','FPGA一代卡节点',2,1,'system',now());
insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('NODE_TYPE','2','FPGA二代卡节点',3,1,'system',now());
insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('NODE_TYPE','3','混插(暂不支持,未来扩展)',4,1,'system',now());


insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('CARD_TYPE','0','GPU卡',1,1,'system',now());
insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('CARD_TYPE','1','FPGA一代卡',2,1,'system',now());
insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('CARD_TYPE','2','FPGA二代卡',3,1,'system',now());


insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('CRACK_MODE','0','字典模式',1,1,'system',now());
insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('CRACK_MODE','1','mask模式(策略和暴力穷举)',2,1,'system',now());
insert into `t_wwx_map`(`code`,`key`,`value`,`order`,`status`,`create_user`,`create_time`)
values('CRACK_MODE','2','特殊模式(vpndes)',3,1,'system',now());



INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 20000, '通讯链路', 'vpn-pptp-des', 209715200, 64, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 20001, '通讯链路', 'vpn-pptp-password', 104857600, 12, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 2500, '设备密码', 'wpa/wpa2', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 3000, '设备密码', 'LM-HASH', 209715200, 48, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 1000, '设备密码', 'NTLM-HASH', 209715200, 48, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 9400, '文档', 'office07', 5400, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 9500, '文档', 'office10', 2700, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 9600, '文档', 'office13', 1350, 2, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 10400, '文档', 'PDF(1.1)', 157286400, 6, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 10500, '文档', 'PDF(1.4)', 157286400, 6, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 10600, '文档', 'PDF(1.7)', 157286400, 6, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 0, '加密', 'MD5', 157286400, 20, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 12500, '压缩', 'Rar3.x~4.x(6位)', 1260, 5, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 13600, '压缩', 'WinZip', 75000, 5, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 13000, '压缩', 'Rar5.x', 3963, 3, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 300, '数据库', 'MySql', 157286400, 8, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 131, '数据库', 'MSSQL(2000)', 157286400, 8, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 132, '数据库', 'MSSQL(2005)', 157286400, 8, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 112, '数据库', 'Oracle S:Type(Oracle 11+)', 8888, 2, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, 400, '平台', 'Wordpress', 18310, 10, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, '磁盘加密', 'PGP SDA Archive', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, '磁盘加密', 'PGP Disk 6（AES）', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, '磁盘加密', 'PGP Disk 6（CAST）', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, '磁盘加密', 'PGP Zip Archive', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 0, '磁盘加密', 'Apple iTunes IOS Device Backup', 16479, 4, 1, 'system', now());




INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 20000, '通讯链路', 'vpn-pptp-des', 209715200, 64, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 20001, '通讯链路', 'vpn-pptp-password', 104857600, 12, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 2500, '设备密码', 'wpa/wpa2', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 3000, '设备密码', 'LM-HASH', 209715200, 48, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 1000, '设备密码', 'NTLM-HASH', 209715200, 48, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 9400, '文档', 'office07', 5400, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 9500, '文档', 'office10', 2700, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 9600, '文档', 'office13', 1350, 2, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 10400, '文档', 'PDF(1.1)', 157286400, 6, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 10500, '文档', 'PDF(1.4)', 157286400, 6, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 10600, '文档', 'PDF(1.7)', 157286400, 6, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 0, '加密', 'MD5', 157286400, 20, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 12500, '压缩', 'Rar3.x~4.x(6位)', 1260, 5, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 13600, '压缩', 'WinZip', 75000, 5, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 13000, '压缩', 'Rar5.x', 3963, 3, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 300, '数据库', 'MySql', 157286400, 8, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 131, '数据库', 'MSSQL(2000)', 157286400, 8, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 132, '数据库', 'MSSQL(2005)', 157286400, 8, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 112, '数据库', 'Oracle S:Type(Oracle 11+)', 8888, 2, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, 400, '平台', 'Wordpress', 18310, 10, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, '磁盘加密', 'PGP SDA Archive', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, '磁盘加密', 'PGP Disk 6（AES）', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, '磁盘加密', 'PGP Disk 6（CAST）', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, '磁盘加密', 'PGP Zip Archive', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 1, '磁盘加密', 'Apple iTunes IOS Device Backup', 16479, 4, 1, 'system', now());


INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 20000, '通讯链路', 'vpn-pptp-des', 209715200, 64, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 20001, '通讯链路', 'vpn-pptp-password', 104857600, 12, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 2500, '设备密码', 'wpa/wpa2', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 3000, '设备密码', 'LM-HASH', 209715200, 48, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 1000, '设备密码', 'NTLM-HASH', 209715200, 48, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 9400, '文档', 'office07', 5400, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 9500, '文档', 'office10', 2700, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 9600, '文档', 'office13', 1350, 2, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 10400, '文档', 'PDF(1.1)', 157286400, 6, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 10500, '文档', 'PDF(1.4)', 157286400, 6, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 10600, '文档', 'PDF(1.7)', 157286400, 6, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 0, '加密', 'MD5', 157286400, 20, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 12500, '压缩', 'Rar3.x~4.x(6位)', 1260, 5, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 13600, '压缩', 'WinZip', 75000, 5, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 13000, '压缩', 'Rar5.x', 3963, 3, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 300, '数据库', 'MySql', 157286400, 8, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 131, '数据库', 'MSSQL(2000)', 157286400, 8, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 132, '数据库', 'MSSQL(2005)', 157286400, 8, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 112, '数据库', 'Oracle S:Type(Oracle 11+)', 8888, 2, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_id`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, 400, '平台', 'Wordpress', 18310, 10, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, '磁盘加密', 'PGP SDA Archive', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, '磁盘加密', 'PGP Disk 6（AES）', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, '磁盘加密', 'PGP Disk 6（CAST）', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, '磁盘加密', 'PGP Zip Archive', 16479, 4, 1, 'system', now());
INSERT INTO `t_wwx_alg` (`node_type`, `alg_type_name`, `alg_name`, `speed`, `core_num`, `status`, `create_user`, `create_time`) 
VALUES ( 2, '磁盘加密', 'Apple iTunes IOS Device Backup', 16479, 4, 1, 'system', now());




