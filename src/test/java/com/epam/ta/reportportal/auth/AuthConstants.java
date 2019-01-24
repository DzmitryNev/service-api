/*
 * Copyright 2018 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.auth;

import com.epam.ta.reportportal.entity.user.UserRole;
import com.google.common.collect.ImmutableList;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Set of test constants for AUTH tests. <br>
 * Data from {@link /ws/src/test/resources/db/setupData.xml} fixtures
 *
 * @author Andrei Varabyeu
 */
public abstract class AuthConstants {

	/**
	 * Demo user name
	 */
	public static final String TEST_USER = "default";

	public static final String TEST_UPSA_USER = "upsa_user";

	/**
	 * Demo password
	 */
	public static final String USER_PASSWORD = "1q2w3e";

	/**
	 * Project of demo user
	 */
	public static final String USER_PROJECT = "default_personal";

	/**
	 * Demo {@link UserRole}
	 */
	public static final UserRole ROLE = UserRole.ADMINISTRATOR;

	public static final Authentication ADMINISTRATOR = newAuthentication(AuthConstants.TEST_USER,
			AuthConstants.USER_PASSWORD,
			true,
			new SimpleGrantedAuthority(UserRole.ADMINISTRATOR.getAuthority())
	);

	public static final Authentication DEFAULT = newAuthentication(AuthConstants.TEST_USER,
			AuthConstants.USER_PASSWORD,
			true,
			new SimpleGrantedAuthority(UserRole.USER.getAuthority())
	);

	public static final Authentication PROJECT_USER = newAuthentication(AuthConstants.TEST_USER,
			AuthConstants.USER_PASSWORD,
			true,
			new SimpleGrantedAuthority(UserRole.ADMINISTRATOR.getAuthority())
	);

	public static final Authentication NOT_AUTHENTIFICATED = newAuthentication(AuthConstants.TEST_USER, AuthConstants.USER_PASSWORD, false);

	public static final Authentication UPSA_USER = newAuthentication(AuthConstants.TEST_UPSA_USER,
			AuthConstants.USER_PASSWORD,
			true,
			new SimpleGrantedAuthority(UserRole.ADMINISTRATOR.getAuthority())
	);

	/**
	 * Constructrs authentification using provided credentials and authorities
	 *
	 * @param user
	 * @param password
	 * @param authenticated
	 * @param authorities
	 * @return
	 */
	public static Authentication newAuthentication(String user, String password, final boolean authenticated,
			GrantedAuthority... authorities) {
		return new TestingAuthenticationToken(user,
				password,
				ImmutableList.<org.springframework.security.core.GrantedAuthority>builder().add(authorities).build()
		) {
			private static final long serialVersionUID = 1L;

			{
				setAuthenticated(authenticated);
			}
		};
	}
}