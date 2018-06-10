package repository;

import com.algorithms.auth.userservice.Application;
import com.algorithms.auth.userservice.domain.User;
import com.algorithms.auth.userservice.domain.draft.CompanyDraft;
import com.algorithms.auth.userservice.domain.draft.UserDraft;
import com.algorithms.auth.userservice.domain.enums.Gender;
import com.algorithms.auth.userservice.domain.enums.Locale;
import com.algorithms.auth.userservice.repository.CompanyRepository;
import com.algorithms.auth.userservice.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;


    private long companyId;
    private long userId;

    private final String emailConfirmationToken = RandomStringUtils.randomAscii(60);
    private final String passwordResetToken = RandomStringUtils.randomAscii(60);


    @Before
    public void setUp(){
        CompanyDraft companyDraft = new CompanyDraft.Builder().name("Test").dateCreated(new Date()).build();
        companyId = companyRepository.insert(companyDraft);

        UserDraft userDraft = new UserDraft(companyId, null, null, null,
                "test@test.com", "test", null, Locale.English, null, passwordResetToken,
                null, emailConfirmationToken, false, null, false,
                false, new Date(), false, 0, true, null,
                new Date(), new Date(), new Date());
        userId = userRepository.insert(userDraft);
    }


    @Test
    public void get(){
        Optional<User> user = userRepository.get(userId);
        Assert.assertTrue(user.isPresent());
    }


    @Test
    public void update(){
        Optional<User> user = userRepository.get(userId);
        int updated = userRepository.update(new User(userId, user.get().draft().gender(Gender.Female)
                .lastUpdated(new Date()).build()));
        Assert.assertEquals(updated, 1);

        Optional<User> updatedUser = userRepository.get(userId);
        Assert.assertTrue(user.get().getUsername().equals(updatedUser.get().getUsername()));
        Assert.assertEquals(updatedUser.get().getGender(), Gender.Female);
    }


    @Test
    public void findByEmail(){
        Optional<User> user = userRepository.findByEmail(companyId,"test@test.com");
        Assert.assertTrue(user.isPresent());
    }


    @Test
    public void findByEmailConfirmationToken(){
        Optional<User> user = userRepository.findByEmailConfirmationToken(emailConfirmationToken);
        Assert.assertTrue(user.isPresent());
    }


    @Test
    public void findByPasswordResetToken(){
        Optional<User> user = userRepository.findByPasswordResetToken(passwordResetToken);
        Assert.assertTrue(user.isPresent());
    }


    @Test
    public void findByUsername(){
        Optional<User> user = userRepository.findByUsername(companyId, "test");
        Assert.assertTrue(user.isPresent());
    }


    @Test
    public void findPrimaryAdmin(){
        Optional<User> user = userRepository.findPrimaryAdmin(companyId);
        Assert.assertTrue(user.isPresent());
    }


    @Test
    public void findPrimaryAdminByEmail(){
        Optional<User> user = userRepository.findPrimaryAdminByEmail("test@test.com");
        Assert.assertTrue(user.isPresent());
    }


    @After
    public void tearDown(){
        userRepository.delete(userId);
        companyRepository.delete(companyId);
    }

}
