package repository;

import com.algorithms.auth.userservice.Application;
import com.algorithms.auth.userservice.domain.AllowedUrl;
import com.algorithms.auth.userservice.domain.draft.AccountDraft;
import com.algorithms.auth.userservice.domain.draft.AllowedUrlDraft;
import com.algorithms.auth.userservice.domain.draft.ClientDraft;
import com.algorithms.auth.userservice.domain.draft.CompanyDraft;
import com.algorithms.auth.userservice.domain.enums.ClientType;
import com.algorithms.auth.userservice.domain.enums.UrlType;
import com.algorithms.auth.userservice.repository.AccountRepository;
import com.algorithms.auth.userservice.repository.AllowedUrlRepository;
import com.algorithms.auth.userservice.repository.ClientRepository;
import com.algorithms.auth.userservice.repository.CompanyRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class AllowedUrlRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AllowedUrlRepository allowedUrlRepository;

    private long companyId;
    private long accountId;
    private long clientId;
    private long allowedUrlId;

    @Before
    public void setUp(){
        CompanyDraft companyDraft = new CompanyDraft.Builder().name("Test").dateCreated(new Date()).build();
        companyId = companyRepository.insert(companyDraft);

        AccountDraft accountDraft = new AccountDraft(companyId, "Test", new Date(), 0, false,
                false, false, false);
        accountId = accountRepository.insert(accountDraft);

        ClientDraft clientDraft = new ClientDraft.Builder().clientId(RandomStringUtils.randomAscii(10))
                .clientSecret(RandomStringUtils.randomAscii(20)).accountId(accountId).clientType(ClientType.SinglePageApplication)
                .jwtExpiration(60000).name("Test").useUsernamePasswordAuthentication(true).dateAdded(new Date()).build();
        clientId = clientRepository.insert(clientDraft);

        AllowedUrlDraft allowedUrlDraft = new AllowedUrlDraft(clientId, "https://localhost", UrlType.AllowedUrl);
        allowedUrlId = allowedUrlRepository.insert(allowedUrlDraft);
    }


    @Test
    public void get(){
        Optional<AllowedUrl> allowedUrl = allowedUrlRepository.get(allowedUrlId);
        Assert.assertTrue(allowedUrl.isPresent());
    }


    @Test
    public void update(){
        Optional<AllowedUrl> allowedUrl = allowedUrlRepository.get(allowedUrlId);
        int updated = allowedUrlRepository.update(new AllowedUrl(allowedUrlId, allowedUrl.get().draft().urlType(UrlType.CrossOriginUrl).build()));
        Assert.assertTrue(updated == 1);

        Optional<AllowedUrl> updatedAllowedUrl = allowedUrlRepository.get(allowedUrlId);
        Assert.assertFalse(allowedUrl.get().getUrlType().equals(updatedAllowedUrl.get().getUrlType()));
    }


    @Test
    public void findByClientId(){
        List<AllowedUrl> allowedUrls = allowedUrlRepository.findByClientId(clientId);
        Assert.assertEquals(allowedUrls.size(), 1);
    }

    @After
    public void tearDown(){
        allowedUrlRepository.delete(allowedUrlId);
        companyRepository.delete(companyId);
    }
}
