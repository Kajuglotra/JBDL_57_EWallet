package org.gfg.UserService.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfg.UserService.Request.CreateUserRequest;
import org.gfg.UserService.Service.UserService;
import org.gfg.Utils.UserIdentifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {UserController.class})
@ImportAutoConfiguration(MessageSourceAutoConfiguration.class)
public class TestUserController {

    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(UserController.class).build();
    }

    public CreateUserRequest getUserRequest(){
        return CreateUserRequest.builder().
                email("test@gmail.com").
                contact("76567654").userIdentifier(UserIdentifier.PAN).
                userIdentifierValue("jhgfghgf").
                build();
    }

    @Test
    public void testUserCreate() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/create").
                content(new ObjectMapper().writeValueAsString(getUserRequest())).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertNotNull(mvcResult);
    }



}
