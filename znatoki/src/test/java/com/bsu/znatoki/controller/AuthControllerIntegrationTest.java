package com.bsu.znatoki.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bsu.znatoki.config.SpringMvcConfig;
import com.bsu.znatoki.dto.LoginDto;
import com.bsu.znatoki.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.annotation.Value;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringMvcConfig.class})
@WebAppConfiguration(value = "src/main/java/com/leverx/blog")
@Sql(scripts = {"init_tables.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"drop_tables.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthControllerIntegrationTest {
    @Value("${existingEmail}")
    private String email;
    @Value("${existingPassword}")
    private String password;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup()  {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void loginWithCorrectData_ShouldReturnStatusOk() throws Exception {
        LoginDto loginDto = new LoginDto(email, password);
        mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void loginWithWrongData_ShouldReturnStatusNotFound() throws Exception {
        LoginDto loginDto = new LoginDto(email, "1234");
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void registerWithCorrectData_ShouldReturnStatusOk() throws Exception {
        mockMvc.perform(post("/auth")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(getUserDtoRegistration())))
                .andExpect(status().isOk());
    }

    private UserDto getUserDtoRegistration() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("test1");
        userDto.setLastName("test2");
        userDto.setPassword("test3");
        userDto.setEmail("test4@test5.test");
        return userDto;
    }
}
