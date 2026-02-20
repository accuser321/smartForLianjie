#!/usr/bin/env bash
set -euo pipefail

BASE_URL="${1:-http://127.0.0.1:8080}"
PASS=0
FAIL=0

log() { printf "%s\n" "$*"; }
check() {
  local name="$1"; shift
  local body
  if ! body=$(eval "$*" 2>/dev/null); then
    log "[FAIL] $name (request error)"
    FAIL=$((FAIL+1))
    return
  fi
  if [[ -z "$body" ]]; then
    log "[FAIL] $name (empty response)"
    FAIL=$((FAIL+1))
    return
  fi
  log "[PASS] $name"
  PASS=$((PASS+1))
}

contains_check() {
  local name="$1"; local cmd="$2"; local needle="$3"
  local body
  if ! body=$(eval "$cmd" 2>/dev/null); then
    log "[FAIL] $name (request error)"
    FAIL=$((FAIL+1)); return
  fi
  if echo "$body" | grep -q "$needle"; then
    log "[PASS] $name"
    PASS=$((PASS+1))
  else
    log "[FAIL] $name (missing $needle)"
    FAIL=$((FAIL+1))
  fi
}

contains_check "wx配置" "curl -sS '$BASE_URL/auth/getWxInfo'" "appid"
contains_check "当前用户信息" "curl -sS '$BASE_URL/auth/getUerInfo'" "menulist"
contains_check "首页统计" "curl -sS '$BASE_URL/abt/abtComKjActionRecord/getNowDayCount'" "JRZF"
contains_check "素材列表" "curl -sS -X POST '$BASE_URL/smartComKjLibw/selectPage' -H 'Content-Type: application/json' -d '{\"page\":1,\"size\":5,\"btagcode\":\"1\",\"flag\":\"0\"}'" "rows"
contains_check "素材详情" "curl -sS '$BASE_URL/smartComKjLibw/selectOneBySno?btagcode=1&sno=1&flag=1'" "stitle"
contains_check "谁看了我" "curl -sS -X POST '$BASE_URL/smartComKjActionRecord/getWZRdZf' -H 'Content-Type: application/json' -d '{\"page\":1,\"size\":5,\"recordtype\":\"1\",\"sno\":\"1\",\"type\":\"1\"}'" "rows"
contains_check "咨询写入" "curl -sS -X POST '$BASE_URL/abt/abtComKjActionRecord/insertKjChat' -H 'Content-Type: application/json' -d '{\"empno\":\"E10001\",\"khuserid\":\"10001\",\"content\":\"smoke-test\",\"type\":\"1\",\"read\":\"1\",\"rytype\":\"W\",\"date\":\"2026-02-21 10:00:00\"}'" "flag"
contains_check "咨询历史" "curl -sS -X POST '$BASE_URL/abt/abtComKjActionRecord/getLS' -H 'Content-Type: application/json' -d '{\"sender\":\"E10001\",\"receiver\":\"APPID#10001\",\"time\":\"\",\"msgDecompression\":0}'" "result"
contains_check "客户关系图" "curl -sS -X POST '$BASE_URL/abt/abtComKjActionRecord/getKHContacts' -H 'Content-Type: application/json' -d '{\"userid\":\"10001\"}'" "relationship"
contains_check "App入口access" "curl -sS -X POST '$BASE_URL/saas/dr/access' -H 'Content-Type: application/json' -d '{\"appurl\":\"https://example.com/home\"}'" "function"

log "----"
log "PASS=$PASS FAIL=$FAIL"
if [[ "$FAIL" -gt 0 ]]; then
  exit 1
fi
