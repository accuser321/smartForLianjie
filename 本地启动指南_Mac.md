# 本地启动指南（Mac）

- 日期：2026-02-21
- 目标：功能优先，尽快跑通

## 0. 路径约定
- 项目根：`/Users/demo/Documents/New project`
- 后端：`/Users/demo/Documents/New project/smartForLianjie/backend`
- 前端：`/Users/demo/Documents/New project/smartForLianjie/frontend`

## 1. 准备环境
1. 打开终端
2. 执行：`source /Users/demo/Documents/tools/use_dev_env.sh`
3. 检查：
- `java -version`
- `node -v`
- `npm -v`

## 2. 启动 MySQL 并导库
1. 创建数据库：`smart`
2. 导入主库：`smart.sql`
3. 导入补丁：`smart_minimal_patch.sql`

示例命令：
```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS smart DEFAULT CHARSET utf8mb4;"
mysql -u root -p smart < '/Users/demo/Documents/New project/smart.sql'
mysql -u root -p smart < '/Users/demo/Documents/New project/smart_minimal_patch.sql'
```

## 3. 后端启动
1. 打开 `application.yml`，改本机 MySQL 账号密码
2. 启动：
```bash
cd '/Users/demo/Documents/New project/smartForLianjie/backend'
/Users/demo/Documents/tools/maven/bin/mvn -Dmaven.repo.local='/Users/demo/Documents/New project/.m2/repository' spring-boot:run
```

如果 Maven 下载失败（网络问题），先重试网络后再执行。

## 4. 前端启动
```bash
cd '/Users/demo/Documents/New project/smartForLianjie/frontend'
npm install
npm run dev
```

## 5. 最小自测（上线前）
按文档执行：
- `/Users/demo/Documents/New project/上线最小闭环自测清单.md`

## 6. 常见问题
1. 前端白屏：先看 `/auth/getUerInfo` 是否返回 `menulist/banner/bar`
2. 素材页空白：检查 `/smartComKjLibw/selectPage`
3. 咨询历史无数据：检查 `insertKjChat` 后再调用 `getLS`
4. 角标不变：先调 `selectPush` 再看 `selectWDKFMsg`

