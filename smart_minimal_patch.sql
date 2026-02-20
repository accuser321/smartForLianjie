-- 最小可运行补丁（V2联调）
-- 用途：在导入 smart.sql 后补齐缺失表与关键基础数据
-- 执行方式：mysql -u root -p smart < smart_minimal_patch.sql

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS smart_com_empno_sourceinfo (
  id BIGINT NOT NULL AUTO_INCREMENT,
  comid VARCHAR(32) NOT NULL,
  empno VARCHAR(32) NOT NULL,
  soucode VARCHAR(32) NOT NULL,
  souhttp VARCHAR(512) DEFAULT '',
  status CHAR(1) DEFAULT '0',
  optime DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_source_emp (comid, empno, soucode, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS smart_com_enum (
  id BIGINT NOT NULL AUTO_INCREMENT,
  comid VARCHAR(32) NOT NULL,
  defcode VARCHAR(64) NOT NULL,
  enumcode VARCHAR(32) NOT NULL,
  enumname VARCHAR(128) DEFAULT '',
  status CHAR(1) DEFAULT '0',
  PRIMARY KEY (id),
  UNIQUE KEY uk_enum (comid, defcode, enumcode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS smart_com_kj_yqh_signup (
  id BIGINT NOT NULL AUTO_INCREMENT,
  comid VARCHAR(32) NOT NULL,
  sno VARCHAR(64) NOT NULL,
  userid BIGINT DEFAULT NULL,
  mobile VARCHAR(32) DEFAULT '',
  status CHAR(1) DEFAULT '0',
  optime DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_signup (comid, sno)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS smart_com_models_mobile_imgs (
  id BIGINT NOT NULL AUTO_INCREMENT,
  comid VARCHAR(32) NOT NULL,
  imgcode VARCHAR(64) NOT NULL,
  imgname VARCHAR(128) DEFAULT '',
  imgdesc VARCHAR(255) DEFAULT '',
  imgsize VARCHAR(32) DEFAULT '',
  imgurl VARCHAR(512) DEFAULT '',
  operno VARCHAR(32) DEFAULT '',
  optime DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_mobile_img (comid, imgcode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS smart_com_muser_card (
  id BIGINT NOT NULL AUTO_INCREMENT,
  comid VARCHAR(32) NOT NULL,
  empno VARCHAR(32) NOT NULL,
  cardempname VARCHAR(128) DEFAULT '',
  cardsex VARCHAR(8) DEFAULT '',
  cardmobile VARCHAR(32) DEFAULT '',
  workarea VARCHAR(255) DEFAULT '',
  email VARCHAR(128) DEFAULT '',
  pdesc VARCHAR(1024) DEFAULT '',
  wxnumber VARCHAR(64) DEFAULT '',
  wxewmurl VARCHAR(512) DEFAULT '',
  carddegreeno VARCHAR(64) DEFAULT '',
  cardstyle VARCHAR(64) DEFAULT '',
  flushtime DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_card_emp (comid, empno)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS smart_com_system_setting (
  id BIGINT NOT NULL AUTO_INCREMENT,
  comid VARCHAR(32) NOT NULL,
  defcode VARCHAR(64) NOT NULL,
  setcode VARCHAR(64) NOT NULL,
  setcontent VARCHAR(1024) DEFAULT '',
  status CHAR(1) DEFAULT '0',
  PRIMARY KEY (id),
  UNIQUE KEY uk_sys_setting (comid, defcode, setcode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS smart_com_wxmsg (
  msgid BIGINT NOT NULL AUTO_INCREMENT,
  comid VARCHAR(32) NOT NULL,
  fuserid BIGINT DEFAULT NULL,
  tuserid BIGINT DEFAULT NULL,
  msgtype VARCHAR(64) DEFAULT '',
  btagcode VARCHAR(32) DEFAULT '',
  otype VARCHAR(32) DEFAULT '',
  sno VARCHAR(64) DEFAULT '',
  mtitle VARCHAR(255) DEFAULT '',
  mdesc VARCHAR(1024) DEFAULT '',
  mcontent VARCHAR(2048) DEFAULT '',
  mhttpurl VARCHAR(1024) DEFAULT '',
  pushtime DATETIME DEFAULT CURRENT_TIMESTAMP,
  readtime DATETIME DEFAULT NULL,
  status CHAR(1) DEFAULT '0',
  PRIMARY KEY (msgid),
  KEY idx_wxmsg_user (comid, tuserid, status),
  KEY idx_wxmsg_follow (comid, fuserid, tuserid, msgtype)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 某些环境缺 smart_com_setting（getWxInfo 依赖）时补齐
CREATE TABLE IF NOT EXISTS smart_com_setting (
  id BIGINT NOT NULL AUTO_INCREMENT,
  comid VARCHAR(32) NOT NULL,
  appid VARCHAR(128) DEFAULT '',
  appsecrt VARCHAR(128) DEFAULT '',
  stylenum VARCHAR(32) DEFAULT '1',
  ossurl VARCHAR(512) DEFAULT '',
  ossaccesskeyid VARCHAR(128) DEFAULT '',
  ossaccesskeysecret VARCHAR(128) DEFAULT '',
  ossbucket VARCHAR(128) DEFAULT '',
  PRIMARY KEY (id),
  UNIQUE KEY uk_setting_comid (comid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 基础初始化数据（可按真实环境覆盖）
INSERT INTO smart_com_setting (comid, appid, appsecrt, stylenum, ossurl)
SELECT 'COMPAT', '', '', '1', ''
WHERE NOT EXISTS (SELECT 1 FROM smart_com_setting WHERE comid = 'COMPAT');

INSERT INTO smart_com_system_setting (comid, defcode, setcode, setcontent, status)
SELECT 'COMPAT', 'FCPHOTO', 'FCPHOTO', '', '0'
WHERE NOT EXISTS (SELECT 1 FROM smart_com_system_setting WHERE comid='COMPAT' AND defcode='FCPHOTO' AND setcode='FCPHOTO');

INSERT INTO smart_com_system_setting (comid, defcode, setcode, setcontent, status)
SELECT 'COMPAT', 'WEBSITE', 'WEBSITE', '', '0'
WHERE NOT EXISTS (SELECT 1 FROM smart_com_system_setting WHERE comid='COMPAT' AND defcode='WEBSITE' AND setcode='WEBSITE');

INSERT INTO smart_com_models_mobile_imgs (comid, imgcode, imgname, imgurl)
SELECT 'COMPAT', 'search', '搜索图标', ''
WHERE NOT EXISTS (SELECT 1 FROM smart_com_models_mobile_imgs WHERE comid='COMPAT' AND imgcode='search');

INSERT INTO smart_com_models_mobile_imgs (comid, imgcode, imgname, imgurl)
SELECT 'COMPAT', 'xxicon', '消息图标', ''
WHERE NOT EXISTS (SELECT 1 FROM smart_com_models_mobile_imgs WHERE comid='COMPAT' AND imgcode='xxicon');

INSERT INTO smart_com_models_mobile_imgs (comid, imgcode, imgname, imgurl)
SELECT 'COMPAT', 'zxicon', '咨询图标', ''
WHERE NOT EXISTS (SELECT 1 FROM smart_com_models_mobile_imgs WHERE comid='COMPAT' AND imgcode='zxicon');

INSERT INTO smart_com_enum (comid, defcode, enumcode, enumname)
SELECT 'COMPAT', 'SEX', '1', '女'
WHERE NOT EXISTS (SELECT 1 FROM smart_com_enum WHERE comid='COMPAT' AND defcode='SEX' AND enumcode='1');

INSERT INTO smart_com_enum (comid, defcode, enumcode, enumname)
SELECT 'COMPAT', 'SEX', '2', '男'
WHERE NOT EXISTS (SELECT 1 FROM smart_com_enum WHERE comid='COMPAT' AND defcode='SEX' AND enumcode='2');

SET FOREIGN_KEY_CHECKS = 1;
