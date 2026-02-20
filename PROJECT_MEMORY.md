# 项目长期记忆（Smart）

## 1. 协作原则（用户要求）
- 用户主要负责产品审核，不手写代码。
- 代码开发、接口补齐、联调、测试、部署由助手主导完成。
- 所有关键结论必须落盘到文件，并进入 git 提交记录，避免上下文丢失。
- 若源码不具备 V2 白名单功能，允许并要求独立开发补齐。

## 2. 项目路径
- 工作根目录：`/Users/demo/Documents/New project`
- 后端源码：`/Users/demo/Documents/New project/smartForLianjie/backend`
- 前端源码：`/Users/demo/Documents/New project/smartForLianjie/frontend`
- 需求相关 SQL：`/Users/demo/Documents/New project/smart.sql`
- 可运行 jar：`/Users/demo/Documents/New project/smart-0.0.1-SNAPSHOT.jar`

## 3. 已确认的关键事实
- `smart-0.0.1-SNAPSHOT.jar` 可正常解包，控制器只有 3 个：
  - `SmartComKjLibwController`
  - `SmartComKjActionRecordController`
  - `AuthController`
- 说明：jar 与源码均不足以覆盖 V2 白名单，需要增量开发 `compat` 控制器。
- `smart.sql` 不完整：与 mapper 引用比对存在缺失表（详见 `DB_结构校验报告.md`）。

## 4. 当前策略
- 先完成“接口可联调覆盖”（前端 API 路径全部有后端映射）。
- 再将占位返回逐步改为真实业务查询/写入。
- 当进入真实数据联调时，必须安装 MySQL 并导入完整库。

## 5. 已产出文档
- `DB_结构校验报告.md`：数据库结构缺口
- `JAR_源码_V2差异说明.md`：jar 与源码与 V2 的差异
- `接口覆盖报告_第2轮.md`：前端 API 路径覆盖状态

## 6. 环境现状
- 已就绪：JDK8 / Maven / Node / npm / IDEA CE / VS Code / git / gh
- 未就绪：MySQL / Redis

## 7. 唤醒助手（新会话必做）
在新对话第一句发送：
1) “请先读取 `/Users/demo/Documents/New project/PROJECT_MEMORY.md`、`/Users/demo/Documents/New project/DB_结构校验报告.md`、`/Users/demo/Documents/New project/JAR_源码_V2差异说明.md`、`/Users/demo/Documents/New project/接口覆盖报告_第2轮.md`，再继续开发。”
2) “再执行 `git log --oneline -n 20`，基于最新提交继续。”

## 8. 当前阶段目标
- 按 V2 白名单继续把关键链路从“占位接口”升级成“真实业务实现”。

## 9. 最新进展（2026-02-20 晚）
- 已补齐 `wholookme` 与 `aiana` 页面依赖的核心分析接口返回结构，页面可在“无真实库数据”模式下正常渲染：
  - `getAIAnalysis`
  - `selectTimeKHByEmpno`
  - `selectKHByOtype`
  - `getRecordAnalyze`
  - `getRecordKH`
  - `getRecordRD`
  - `getYSLD`
- 当前这些接口为“结构化模拟数据”；后续按你确认的功能优先级替换成真实 SQL。
- 编译现状：JDK8 环境可用，但当前网络 DNS 无法访问 Maven 中央仓库，`mvn compile` 暂不可完成。

## 10. 最新进展（2026-02-20 夜）
- 已继续补“消息/提醒”链路，重点处理前端直接使用字段导致的结构风险：
  - `V2CompatAlertmsgController`：`selectByPage/checkDetails/statistics` 已改为可渲染结构；
  - `V2CompatComplainController`：`complainlist` 改为返回数组；
  - `V2CompatCouponController`：`grantCoupon` 改为返回数组；
  - `V2CompatUserCardController`：`getTXRecord/getTXRecords` 已补齐提现页面依赖字段（含 `blankmessage`）。
- 当前策略保持不变：先保证“页面不报错 + 可联调”，再逐条替换为真实业务查询。

## 11. 最新进展（2026-02-20 深夜）
- 已继续补齐首页/个人中心/消息推送链路的接口结构，减少前端 `undefined` 风险：
  - `V2CompatHxController`：`selectGZByEmpno/selectGRZXByEmpno`
  - `V2CompatUserCardController`：`selectKHbyEmpno`
  - `V2CompatActivityController`：`selectActivity/getActivity`
  - `V2CompatYqhSignupController`：`selectPush/selectKFMsgList`
- 结果：工资明细、热门活动详情、消息推送页、保单预警页在“无真实库”模式下结构可联调。

## 12. 最新进展（2026-02-20 深夜 2）
- 已补齐 App 入口兼容返回：
  - `POST /saas/dr/access` 现在返回 `function` 与 `data.user`，适配 `appoauth` 页面本地存储逻辑。

## 13. 最新进展（2026-02-20 深夜 3）
- 已补齐首页统计与积分链路：
  - `getNowDayCount` 追加首页所需字段（`JRZF/JRFC/JRHK/JYZF/JYFC/JYHK/wdMsgCount`）；
  - 新增 `saas/abtComEmpnoJifen` 兼容控制器（`jfList/getJF/jfSum`）。

## 14. 最新进展（2026-02-21 凌晨）
- 按用户确认范围继续开发（1/2/3）：
  - 咨询链路：`insertKjChat/getLS/updateMSG` 已增加兼容降级，外部依赖异常时不阻断前端流程；
  - 用户中心资料：`getPersonalData/getZYZMessage/getSFZMessage` 已补齐前端页面依赖字段；
  - 活动与消息推送链路沿用已补齐结构（上一轮已完成）。
- 用户明确暂停功能（记录为白名单外）：
  - 提现与银行卡：`upMount/upYHKMessage/selectYHByEmpno`
  - 优惠券链路：`selectempCouponList/useFWCoupon/grantCoupon`
