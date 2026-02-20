# JAR、当前源码、V2白名单差异说明

## 1. JAR 实际能力（反编译结果）
JAR 中只有 3 个 Controller：
- `SmartComKjLibwController`
- `SmartComKjActionRecordController`
- `AuthController`

说明：JAR 本身并不包含你要的 V2 扩展控制器。

## 2. 当前源码相对 JAR 的新增（已补）
- 新增 `compat` 控制器：
  - `V2CompatActionController`
  - `V2CompatCustomBasemsgController`
  - `V2CompatSelectController`
  - `V2CompatUploadController`
  - `V2CompatAlertmsgController`
  - `V2CompatComplainController`
  - `V2CompatMuserCardController`
- 老接口兼容增强：
  - `SmartComKjLibwController` 增加 query 兼容与 `insertWZ/updateWZ/useHB`
  - `AuthController` 增加 `/auth/getinfobyid?userid=...`

## 3. 数据库完整性结论
- 你提供的 `smart.sql` 不完整（详见 `DB_结构校验报告.md`）。
- 当前阶段可继续做“接口层开发和联调准备”；
- 当要做真实业务联调（读写客户、消息、提醒等）时，必须安装并导入完整 MySQL 数据库。

## 4. 现在的可见运行结果在哪里
在未安装 MySQL 的情况下，可验证结果：
- 反编译结果（已执行）
- 接口/控制器新增代码：`smartForLianjie/backend/src/main/java/com/nh/smart/controller`
- 差异文档：本文件、`DB_结构校验报告.md`

真实运行结果（页面联调、数据正确性）依赖：
- 本机 MySQL + 完整表结构 + 基础测试数据
