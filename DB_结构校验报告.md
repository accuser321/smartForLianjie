# DB 结构校验报告

- 校验时间: 2026-02-20
- SQL 文件: `/Users/demo/Documents/New project/smart.sql`
- SQL 中建表数量: **16**
- 后端 Mapper 引用 smart_ 表数量: **22**
- 缺失表数量: **7**

## 缺失表清单
- `smart_com_empno_sourceinfo`
- `smart_com_enum`
- `smart_com_kj_yqh_signup`
- `smart_com_models_mobile_imgs`
- `smart_com_muser_card`
- `smart_com_system_setting`
- `smart_com_wxmsg`

## 缺失表引用位置
### `smart_com_empno_sourceinfo`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/material/SmartComMuserCardDaoMapper.xml:95` -> `select souhttp from smart_com_empno_sourceinfo where comid = #{comid}`

### `smart_com_enum`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/material/SmartComEmpnoDaoMapper.xml:74` -> `left JOIN smart_com_enum d ON d.enumcode = a.sex and d.defcode = 'SEX' and a.comid = d.comid`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/material/SmartComEmpnoDaoMapper.xml:75` -> `LEFT JOIN smart_com_enum e ON e.enumcode = a.nation and d.defcode = 'MINZHU' and a.comid = e.comid`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/material/SmartComEmpnoDaoMapper.xml:76` -> `LEFT JOIN smart_com_enum g ON g.enumcode = a.educational and d.defcode = 'XUELI' and a.comid = g.comid`

### `smart_com_kj_yqh_signup`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/material/SmartComKjLibwEmpnoDaoMapper.xml:89` -> `left join smart_com_kj_yqh_signup ackys on ackle.sno = ackys.sno and ackle.comid = ackys.comid`

### `smart_com_models_mobile_imgs`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/material/SmartComModelsMobileImgsDaoMapper.xml:24` -> `from smart_com_models_mobile_imgs`

### `smart_com_muser_card`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/record/SmartComMuserDaoMapper.xml:96` -> `select cardempname,cardmobile,wxnumber from smart_com_muser_card where empno = #{empno} and comid = #{comid}`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/material/SmartComMuserCardDaoMapper.xml:30` -> `update smart_com_muser_card`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/material/SmartComMuserCardDaoMapper.xml:53` -> `FROM smart_com_muser_card acmc`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/material/SmartComMuserCardDaoMapper.xml:74` -> `select acmc.cardempname,acmc.cardmobile,acm.headimg from smart_com_muser_card acmc`

### `smart_com_system_setting`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/material/SmartComMuserCardDaoMapper.xml:81` -> `select setcode,setcontent from smart_com_system_setting where defcode in('FCPHOTO','WEBSITE')`

### `smart_com_wxmsg`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/record/SmartComWxmsgDaoMapper.xml:31` -> `select count(*) from smart_com_wxmsg`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/record/SmartComWxmsgDaoMapper.xml:98` -> `FROM smart_com_wxmsg  WHERE comid=#{comid} AND tuserid=#{userid}`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/record/SmartComWxmsgDaoMapper.xml:114` -> `from smart_com_wxmsg where comid=#{comid}  and msgid=#{msgid}`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/record/SmartComWxmsgDaoMapper.xml:119` -> `update smart_com_wxmsg`
- `/Users/demo/Documents/New project/smartForLianjie/backend/src/main/resources/mybatis/mapper/com/nh/record/SmartComWxmsgDaoMapper.xml:126` -> `select count(*) from smart_com_wxmsg where tuserid = #{userid} and comid = #{comid} and status = '0'`

## SQL 中存在但当前 Mapper 未直接引用
- `smart_com_kj_empno_label`