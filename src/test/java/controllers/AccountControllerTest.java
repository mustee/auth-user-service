package controllers;



import com.algorithms.auth.userservice.Application;
import com.algorithms.auth.userservice.controllers.AccountController;
import com.algorithms.auth.userservice.controllers.models.CreateCompanyModel;
import com.algorithms.auth.userservice.domain.enums.ClientType;
import com.algorithms.auth.userservice.domain.enums.Provider;
import com.algorithms.auth.userservice.result.EntityResult;
import com.algorithms.auth.userservice.result.Result;
import com.algorithms.auth.userservice.result.models.ClientModel;
import com.algorithms.auth.userservice.service.AccountService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doReturn;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService)).build();
    }


    @Test
    public void testCreateCompanyWithInvalidModel() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/account/create-company", new CreateCompanyModel()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void testCreateCompanyErrorResult() throws Exception{
        CreateCompanyModel model = new CreateCompanyModel("test@test.com", "password", "Auth", null);
        doReturn(Result.failed("Company is invalid")).when(accountService)
                .createCompany( model.getEmail(), model.getEmail(), model.getPassword(),  Provider.valueOf(model.getProvider()), model.getProviderId());

        Gson gson = new Gson();
        MvcResult mvcResult =this.mockMvc.perform(MockMvcRequestBuilders.post("/account/create-company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(model)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        Result result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Result.class);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getErrors().size() > 0);
    }


    @Test
    public void testCreateCompany() throws Exception{
        CreateCompanyModel model = new CreateCompanyModel("test@test.com", "password", "Auth", null);
        doReturn(Result.succeeded()).when(accountService)
                .createCompany( model.getEmail(), model.getEmail(), model.getPassword(),  Provider.valueOf(model.getProvider()), model.getProviderId());

        Gson gson = new Gson();
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/account/create-company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(model)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Result result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Result.class);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getSuccess());
    }


    @Test
    public void testClientEmptyClientId() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/account/client")
                .param("clientId", ""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void testClient() throws Exception{
        doReturn(new EntityResult<>(new ClientModel(1, "test", "test", "test",
                ClientType.NonIteractiveClient, 60000, false, "")))
                .when(accountService)
                .getClient( "test");

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/account/client")
                .param("clientId", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Gson gson = new Gson();
        EntityResult<ClientModel> result = (EntityResult<ClientModel>)gson.fromJson(mvcResult.getResponse().getContentAsString(), EntityResult.class);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getSuccess());
    }
}
