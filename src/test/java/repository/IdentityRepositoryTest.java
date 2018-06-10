package repository;


import com.algorithms.auth.userservice.Application;
import com.algorithms.auth.userservice.domain.Identity;
import com.algorithms.auth.userservice.domain.draft.CompanyDraft;
import com.algorithms.auth.userservice.domain.draft.IdentityDraft;
import com.algorithms.auth.userservice.domain.draft.UserDraft;
import com.algorithms.auth.userservice.domain.enums.Locale;
import com.algorithms.auth.userservice.domain.enums.Provider;
import com.algorithms.auth.userservice.repository.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
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
class IdentityRepositoryTest {


    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdentityRepository identityRepository;

    private long companyId;
    private long identityId;

    private final String emailConfirmationToken = RandomStringUtils.randomAscii(60);
    private final String passwordResetToken = RandomStringUtils.randomAscii(60);


    @BeforeEach
    void setUp(){
        CompanyDraft companyDraft = new CompanyDraft.Builder().name("Test").dateCreated(new Date()).build();
        companyId = companyRepository.insert(companyDraft);

        UserDraft userDraft = new UserDraft(companyId, null, null, null,
                "test@test.com", "test", null, Locale.English, null, passwordResetToken,
                null, emailConfirmationToken, false, null, false,
                false, new Date(), false, 0, true, null,
                new Date(), new Date(), new Date());
        long userId = userRepository.insert(userDraft);

        IdentityDraft identityDraft = new IdentityDraft(userId, Provider.Auth, "1");
        identityId = identityRepository.insert(identityDraft);
    }

    @Test
    void get(){
        Optional<Identity> identity = identityRepository.get(identityId);
        Assertions.assertTrue(identity.isPresent());
    }

    @Test
    void update(){
        Optional<Identity> identity = identityRepository.get(identityId);
        int updated = identityRepository.update(new Identity(identityId, identity.get().draft().providerId("Tester").build()));
        Assertions.assertEquals(1, updated);

        Optional<Identity> updatedIdentity = identityRepository.get(identityId);
        Assertions.assertFalse(identity.get().getProviderId().equals(updatedIdentity.get().getProviderId()));
    }


    @AfterEach
    void tearDown(){
        companyRepository.delete(companyId);
    }

}
