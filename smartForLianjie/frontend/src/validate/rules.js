/*
 * @Author: 王鹏
 * @Date: 2019-09-12 11:44:20
 * @LastEditors: 刘格优
 * @LastEditTime: 2020-03-06 15:02:05
 */

export const extendRules = V => {
  // 手机号
  V.extend("mobile", {
    getMessage: field => field + "格式不正确",
    validate: value => {
      return (
        /^((13|14|15|17|18|19)[0-9]{1}\d{8})$/.test(value) ||
        /^((0\d{2,3}-?\d{7,8})|(1[3584]\d{9}))$/.test(value)
      );
    }
  });

  // 身份证
  V.extend("idcard", {
    getMessage: field => "身份证号码必须是18位有效字符",
    validate: value => {
      return (
        value.length === 18 &&
        /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(
          value
        )
      );
    }
  });

  // 整数
  V.extend("num", {
    getMessage: field => field + "必须是整数",
    validate: value => {
      return /^[0-9]*$/.test(value);
    }
  });

  // 银行卡
  V.extend("credit", {
    getMessage: field => field + "必须是16位或19位数字",
    validate: value => {
      return /^(\d{16}|\d{19})$/.test(value);
    }
  });

  // 小数
  V.extend("point", {
    getMessage: field => "比例格式不正确",
    validate: value => {
      return /^(((\d|[1-9]\d)(\.\d{1,2})?)|100|100.0|100.00)$/.test(value);
    }
  });

  // 邮箱
  V.extend("email", {
    getMessage: field => field + "必须是有效字符",
    validate: value => {
      return /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/.test(
        value
      );
    }
  });
};
