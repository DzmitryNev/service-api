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

package com.epam.ta.reportportal.ws.controller;

import com.epam.ta.reportportal.ws.BaseMvcTest;
import org.junit.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.io.Serializable;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class ActivityControllerTest extends BaseMvcTest {

	@Test
	public void getActivitiesByWrongTestItemId() throws Exception {
		this.mvcMock.perform(get(PROJECT_BASE_URL + "/activity/101203").principal(authentication())).andExpect(status().is(404));
	}

	@Override
	protected Authentication authentication() {
		return new OAuth2Authentication(getOauth2Request(), getAuthentication());
	}

	private OAuth2Request getOauth2Request() {
		String clientId = "oauth-client-id";
		Map<String, String> requestParameters = Collections.emptyMap();
		boolean approved = true;
		String redirectUrl = "http://my-redirect-url.com";
		Set<String> responseTypes = Collections.emptySet();
		Set<String> scopes = Collections.emptySet();
		Set<String> resourceIds = Collections.emptySet();
		Map<String, Serializable> extensionProperties = Collections.emptyMap();
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("Everything");

		OAuth2Request oAuth2Request = new OAuth2Request(requestParameters,
				clientId,
				authorities,
				approved,
				scopes,
				resourceIds,
				redirectUrl,
				responseTypes,
				extensionProperties
		);

		return oAuth2Request;
	}

	private Authentication getAuthentication() {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("Everything");

		User userPrincipal = new User("user", "", true, true, true, true, authorities);

		HashMap<String, String> details = new HashMap<String, String>();
		details.put("user_name", "bwatkins");
		details.put("email", "bwatkins@test.org");
		details.put("name", "Brian Watkins");

		TestingAuthenticationToken token = new TestingAuthenticationToken(userPrincipal, null, authorities);
		token.setAuthenticated(true);
		token.setDetails(details);

		return token;
	}
}