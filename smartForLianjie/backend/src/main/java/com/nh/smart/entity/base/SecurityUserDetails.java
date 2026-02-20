package com.nh.smart.entity.base;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @ClassName: SecurityUserDetails
 * @Description: 实行自定义用户安全类
 * @Author Demo
 * @DateTime 2020年5月7日 下午6:42:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecurityUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	// 用户工号
	private String empno;

	// openid+comid
	private String openid;

	// 密码
	private String password;

	// 用户状态，0表示有效用户，1表示无效用户
	private String state;

	// 用户所属机构
	private String tjcode;

	// 用户所属岗位id SecurityUserDetails securityUserDetails;
	private String gwcode;

	// 用户id号码(用户编号,10000001开始计数)
	private Integer userid;

	// 用户的权限集合
	private Collection<? extends GrantedAuthority> authorties;

	public SecurityUserDetails(String openid, Integer userid) {
		this.openid = openid;
		this.userid = userid;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorties;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return openid;
	}

	// 用户编号
	public Integer getUserid() {
		return userid;
	}

	/**
	 * 指示用户的帐户是否已过期。过期的帐户无法通过身份验证。
	 *
	 * @return true如果用户的帐户有效（即未过期）， false如果不再有效（即已过期）
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 指示用户是锁定还是解锁。锁定的用户无法进行身份验证。
	 *
	 * @return true是未锁定，false是已锁定
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 指示用户的凭据（密码）是否已过期。过期的凭据会阻止身份验证
	 *
	 * @return true如果用户的凭证有效（即未过期）， false如果不再有效（即已过期）
	 */
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 指示用户是启用还是禁用。禁用的用户无法进行身份验证。
	 *
	 * @return true用户已启用，false用户已经禁用
	 */
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return "0".equals(state);
	}

}
