package service;


import com.algorithms.auth.userservice.Application;
import com.algorithms.auth.userservice.domain.Client;
import com.algorithms.auth.userservice.domain.Company;
import com.algorithms.auth.userservice.domain.User;
import com.algorithms.auth.userservice.domain.draft.CompanyDraft;
import com.algorithms.auth.userservice.domain.draft.UserDraft;
import com.algorithms.auth.userservice.domain.enums.ClientType;
import com.algorithms.auth.userservice.domain.enums.Gender;
import com.algorithms.auth.userservice.domain.enums.Locale;
import com.algorithms.auth.userservice.domain.enums.Provider;
import com.algorithms.auth.userservice.repository.ClientRepository;
import com.algorithms.auth.userservice.repository.CompanyRepository;
import com.algorithms.auth.userservice.repository.UserRepository;
import com.algorithms.auth.userservice.result.EntityResult;
import com.algorithms.auth.userservice.result.Result;
import com.algorithms.auth.userservice.result.models.ClientModel;
import com.algorithms.auth.userservice.service.AccountService;
import com.algorithms.auth.userservice.service.impl.AccountServiceImpl;
import com.algorithms.auth.userservice.service.impl.UserServiceImpl;
import com.algorithms.auth.userservice.utils.Defaults;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AccountServiceTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientRepository clientRepository;

    @Autowired
    private MessageSource messageSource;

    private AccountService accountService;

    private Company company;
    private User user;


    @Before
    public void setup(){
        accountService = new AccountServiceImpl(userService, companyRepository, userRepository, clientRepository, messageSource);
        company = new Company(1, new CompanyDraft("test", new Date()));
        user = new User(1, new UserDraft(1, null, null, null, "test@test.com",
                "test@test.com", Gender.Female, Locale.English, "password", null,
                UUID.randomUUID().toString(), null, false, null, false,
                false, null,false, 0, true, null, new Date(),
                new Date(), new Date()));
    }

    @Test
    public void testCreateCompanyParameters(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            accountService.createCompany(null, "test@test.com", "password", Provider.Auth, "1")
        );

        Assertions.assertThrows(IllegalArgumentException.class, () ->
            accountService.createCompany("test@test.com", null, "password", Provider.Auth, "1")
        );

        Assertions.assertThrows(IllegalArgumentException.class, () ->
            accountService.createCompany("test@test.com", "test@test.com", null, Provider.Auth, "1")
        );

        Assertions.assertThrows(NullPointerException.class, () ->
            accountService.createCompany("test@test.com", "test@test.com", "password", null, "1")
        );

        Assertions.assertThrows(IllegalArgumentException.class, () ->
            accountService.createCompany("test@test.com", "test@test.com", "password", Provider.Facebook, null)
        );
    }


    @Test
    public void testCreateCompany(){
        when(userRepository.findPrimaryAdminByEmail("test@test.com")).thenReturn(Optional.empty());

        Result result = accountService.createCompany("test@test.com", "test@test.com", "password",  Provider.Auth, null);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getSuccess());
    }


    @Test
    public void testUserAlreadyExistsCreateCompany(){
        when(userRepository.findPrimaryAdminByEmail("test@test.com")).thenReturn(Optional.of(user));

        Result result = accountService.createCompany("test@test.com", "test@test.com", "password",  Provider.Auth, null);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getSuccess());
    }


    @Test
    public void testCompleteRegistrationParameters(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                accountService.completeRegistration(null, "test")
        );

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                accountService.completeRegistration("fdghdrffd", "")
        );

    }


    @Test
    public void testEmailConfirmationTokenDoesNotExist(){
        when(userRepository.findByEmailConfirmationToken("test")).thenReturn(Optional.empty());

        Result result = accountService.completeRegistration("test", "test");
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getSuccess());
    }


    @Test
    public void testCompanyDoesNotExist(){
        when(userRepository.findByEmailConfirmationToken("test")).thenReturn(Optional.of(user));
        doReturn(Optional.empty()).when(companyRepository).get(1);

        Result result = accountService.completeRegistration("test", "test");
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getSuccess());
    }


    @Test
    public void testCompleteRegistration(){
        when(userRepository.findByEmailConfirmationToken("test")).thenReturn(Optional.of(user));
        doReturn(Optional.of(company)).when(companyRepository).get(1);

        Result result = accountService.completeRegistration("test", "test");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getSuccess());
        Assertions.assertTrue(result instanceof EntityResult);
    }

    @Test
    public void testNextDefaultCompanyNameNoDefaults(){
        doReturn(new ArrayList<String>()).when(companyRepository).companyNamesLikeDefault();

        final String first = Defaults.COMPANY_NAME + " 1";
        Result result = accountService.nextDefaultCompanyName();
        Assertions.assertTrue(result.getSuccess());
        Assertions.assertEquals(((EntityResult<String>) result).getEntity(), first);
    }


    @Test
    public void testNextDefaultCompanyName(){
        doReturn(Arrays.asList( Defaults.COMPANY_NAME + " 1",  Defaults.COMPANY_NAME + " sfsdfds",  Defaults.COMPANY_NAME + " 5")).when(companyRepository).companyNamesLikeDefault();

        final String first = Defaults.COMPANY_NAME + " 6";
        Result result = accountService.nextDefaultCompanyName();
        Assertions.assertTrue(result.getSuccess());
        Assertions.assertEquals(((EntityResult<String>) result).getEntity(), first);
    }


    @Test
    public void testGetClientNotFound(){
        doReturn(Optional.empty()).when(clientRepository).findByClientId("test");

        Result result = accountService.getClient("test");
        Assertions.assertTrue(result.hasError());
    }


    @Test
    public void testGetClient(){
        doReturn(Optional.of(new Client(1, 1, "test", "test", "test",
                ClientType.NonIteractiveClient, 60000, false, new Date(), "")))
                .when(clientRepository).findByClientId("test");

        Result result = accountService.getClient("test");
        Assertions.assertTrue(result.getSuccess());
        Assertions.assertNotNull(((EntityResult<ClientModel>) result).getEntity());
    }
}
