package com.cyb.cas;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月25日
 */
public class UserDetailsManager implements UserDetailsService,java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(UserDetailsManager.class);

	/**
	 * 此处的参数[loginId]为CAS登录画面输入的用户名
	 */
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		UserDetails userDetails = new UserDetails() {
			Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			private static final long serialVersionUID = 1L;

			/**
			 * Returns the authorities granted to the user. Cannot return
			 * <code>null</code>.
			 * 
			 * @return the authorities, sorted by natural key (never
			 *         <code>null</code>)
			 */
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				// 根据自定义逻辑来返回用户权限，如果用户权限返回空或者和拦截路径对应权限不同，验证不通过ROLE_
				if (username.equals("admin")) {
					GrantedAuthority au = new SimpleGrantedAuthority("ROLE_ADMIN");
					auths.add(au);
				} else if (username.equals("user")) {
					GrantedAuthority au = new SimpleGrantedAuthority("ROLE_USER");
					auths.add(au);
				} else if (username.equals("other")) {
					GrantedAuthority au = new SimpleGrantedAuthority("ROLE_OTHER");
					auths.add(au);
					// 没有任何角色
				} else {// any
					GrantedAuthority au1 = new SimpleGrantedAuthority("ROLE_USER");
					auths.add(au1);
					GrantedAuthority au2 = new SimpleGrantedAuthority("ROLE_ADMIN");
					auths.add(au2);
				}
				log.info(username+" 初始化角色信息！" + auths.size());
				return auths;
			}

			/**
			 * Returns the password used to authenticate the user.
			 * 
			 * @return the password
			 */
			@Override
			public String getPassword() {
				return "111111";
			}

			/**
			 * Returns the username used to authenticate the user. Cannot return
			 * <code>null</code> .
			 * 
			 * @return the username (never <code>null</code>)
			 */
			@Override
			public String getUsername() {
				return username;
			}

			/**
			 * Indicates whether the user's account has expired. An expired
			 * account cannot be authenticated.
			 * 
			 * @return <code>true</code> if the user's account is valid (ie
			 *         non-expired), <code>false</code> if no longer valid (ie
			 *         expired)
			 */
			@Override
			public boolean isAccountNonExpired() {
				return true;
			}

			/**
			 * Indicates whether the user is locked or unlocked. A locked user
			 * cannot be authenticated.
			 * 
			 * @return <code>true</code> if the user is not locked,
			 *         <code>false</code> otherwise
			 */
			@Override
			public boolean isAccountNonLocked() {
				return true;
			}

			/**
			 * Indicates whether the user's credentials (password) has expired.
			 * Expired credentials prevent authentication.
			 * 
			 * @return <code>true</code> if the user's credentials are valid (ie
			 *         non-expired), <code>false</code> if no longer valid (ie
			 *         expired)
			 */
			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}

			/**
			 * Indicates whether the user is enabled or disabled. A disabled
			 * user cannot be authenticated.
			 * 
			 * @return <code>true</code> if the user is enabled,
			 *         <code>false</code> otherwise
			 */
			@Override
			public boolean isEnabled() {
				return true;
			}
		};

		return userDetails;
	}
}
