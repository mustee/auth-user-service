package service;


import com.algorithms.auth.userservice.Application;
import com.algorithms.auth.userservice.domain.Company;
import com.algorithms.auth.userservice.domain.User;
import com.algorithms.auth.userservice.domain.draft.UserDraft;
import com.algorithms.auth.userservice.domain.enums.Gender;
import com.algorithms.auth.userservice.domain.enums.Locale;
import com.algorithms.auth.userservice.domain.enums.Provider;
import com.algorithms.auth.userservice.repository.CompanyRepository;
import com.algorithms.auth.userservice.repository.IdentityRepository;
import com.algorithms.auth.userservice.repository.UserRepository;
import com.algorithms.auth.userservice.result.EntityResult;
import com.algorithms.auth.userservice.result.Result;
import com.algorithms.auth.userservice.service.impl.UserServiceImpl;
import com.algorithms.auth.userservice.utils.PasswordHasher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest{

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IdentityRepository identityRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PasswordHasher passwordHasher;

    private UserServiceImpl userService;

    private User user;

    @Before
    public void setup(){
        userService = new UserServiceImpl(companyRepository, userRepository, identityRepository, messageSource, passwordHasher);
        user = new User(1, new UserDraft(1, null, null, null, "test@test.com",
                "test@test.com", Gender.Female, Locale.English, passwordHasher.hash("password"), null,
                UUID.randomUUID().toString(), null, false, null, false,
                false, null,false, 0, true, null, new Date(),
                new Date(), new Date()));
    }

    @Test
    public void testCreate(){
        when(companyRepository.get(1)).thenReturn(Optional.of(new Company(1, "test", new Date())));
        doReturn(Optional.empty()).when(userRepository).findByEmail(1, "test@test.com");
        doReturn(Optional.empty()).when(userRepository).findByUsername(1, "test@test.com");
        doReturn(1L).when(userRepository).insert(user);

        Result result = userService.create(1, "test@test.com", "test@test.com", null, null, null,
                Gender.Female, Locale.English, "password", true, Provider.Auth, null);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result instanceof EntityResult);
    }

    @Test
    public void testEmailAlreadyExistsOnCreate(){
        when(companyRepository.get(1)).thenReturn(Optional.of(new Company(1, "test", new Date())));
        doReturn(Optional.of(user)).when(userRepository).findByEmail(1, "test@test.com");

        Result result = userService.create(1, "test@test.com", "test@test.com", null, null, null,
                Gender.Female, Locale.English, "password", true, Provider.Auth, "1");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.hasError());
        Assert.assertTrue(result.getErrors().size() == 1);
    }


    @Test
    public void testUsernameAlreadyExistsOnCreate(){
        when(companyRepository.get(1)).thenReturn(Optional.of(new Company(1, "test", new Date())));
        doReturn(Optional.empty()).when(userRepository).findByEmail(1, "test@test.com");
        doReturn(Optional.of(user)).when(userRepository).findByUsername(1, "test@test.com");

        Result result = userService.create(1, "test@test.com", "test@test.com", null, null, null,
                Gender.Female, Locale.English, "password", true, Provider.Auth, "1");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.hasError());
        Assert.assertTrue(result.getErrors().size() == 1);
    }


    @Test
    public void testCreateParameters(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.create(1, null, "test@test.com", null, null, null,
                    Gender.Female, Locale.English, "password", true, Provider.Auth, "1");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.create(1, "test@test.com", null, null, null, null,
                    Gender.Female, Locale.English, "password", true, Provider.Auth, "1");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.create(1, "test@test.com", "test@test.com", null, null, null,
                    Gender.Female, Locale.English, null, true, Provider.Auth, "1");
        });

        Assertions.assertThrows(NullPointerException.class, () -> {
            userService.create(1, "test@test.com", "test@test.com", null, null, null,
                    Gender.Female, Locale.English, "password", true, null, "1");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.create(1, "test@test.com", "test@test.com", null, null, null,
                    Gender.Female, Locale.English, "password", true, Provider.Facebook, null);
        });
    }

}
