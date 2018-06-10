package repository;

import com.algorithms.auth.userservice.Application;
import com.algorithms.auth.userservice.domain.Client;
import com.algorithms.auth.userservice.domain.draft.AccountDraft;
import com.algorithms.auth.userservice.domain.draft.ClientDraft;
import com.algorithms.auth.userservice.domain.draft.CompanyDraft;
import com.algorithms.auth.userservice.domain.enums.ClientType;
import com.algorithms.auth.userservice.repository.AccountRepository;
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
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class ClientRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    private long companyId;
    private long accountId;
    private long id;

    private final String clientId = RandomStringUtils.randomAscii(30);
    private final String clientSecret = RandomStringUtils.randomAscii(60);

    @Before
    public void setUp(){
        CompanyDraft companyDraft = new CompanyDraft.Builder().name("Test").dateCreated(new Date()).build();
        companyId = companyRepository.insert(companyDraft);

        AccountDraft accountDraft = new AccountDraft(companyId, "Test", new Date(), 0, false,
                false, false, false);
        accountId = accountRepository.insert(accountDraft);

        ClientDraft clientDraft = new ClientDraft.Builder().clientId(clientId).clientSecret(clientSecret).accountId(accountId)
                .clientType(ClientType.SinglePageApplication).jwtExpiration(60000).name("Test").useUsernamePasswordAuthentication(true)
                .dateAdded(new Date()).build();
        id = clientRepository.insert(clientDraft);
    }

    @Test
    public void get(){
        Optional<Client> client = clientRepository.get(id);
        Assert.assertTrue(client.isPresent());
    }


    @Test
    public void update(){
        Optional<Client> client = clientRepository.get(id);
        int updated = clientRepository.update(new Client(id, client.get().draft().name("Tester").clientType(ClientType.NonIteractiveClient)
                .useUsernamePasswordAuthentication(false).build()));
        Assert.assertTrue(updated == 1);

        Optional<Client> updatedClient = clientRepository.get(id);
        Assert.assertFalse(client.get().getName().equals(updatedClient.get().getName()));
        Assert.assertFalse(client.get().isUseUsernamePasswordAuthentication() && updatedClient.get().isUseUsernamePasswordAuthentication());
        Assert.assertTrue(updatedClient.get().getClientType() == ClientType.NonIteractiveClient);
    }


    @Test
    public void findByClientId(){
        Optional<Client> client = clientRepository.findByClientId(clientId);
        Assert.assertTrue(client.isPresent());
    }


    @After
    public void tearDown(){
        clientRepository.delete(id);
        accountRepository.delete(accountId);
        companyRepository.delete(companyId);
    }
}
