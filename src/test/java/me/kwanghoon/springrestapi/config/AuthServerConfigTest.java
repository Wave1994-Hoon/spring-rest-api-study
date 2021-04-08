package me.kwanghoon.springrestapi.config;

import me.kwanghoon.springrestapi.BaseControllerTest;
import me.kwanghoon.springrestapi.accounts.Account;
import me.kwanghoon.springrestapi.accounts.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("인증 토큰을 발급받는 테스트")
    public void getAuthToken() throws Exception {
        String username = "test";
        String password = "test";
        Account.builder()
            .email(username)
            .password(password)
            .build();

        String clientId = "String";
        String clientSecret = "String";

//        mockMvc.perform(post("/oauth/token")
//            .with(httpBasic(clientId, clientSecret))
//            .param("username", username)
//            .param("password", password)
//            .param("grant_type", "password"))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("access_token").exists());
    }
}