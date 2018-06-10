package repository;

import com.algorithms.auth.userservice.Application;
import com.algorithms.auth.userservice.domain.Account;
import com.algorithms.auth.userservice.domain.draft.AccountDraft;
import com.algorithms.auth.userservice.domain.draft.CompanyDraft;
import com.algorithms.auth.userservice.repository.AccountRepository;
import com.algorithms.auth.userservice.repository.CompanyRepository;
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
public class AccountRepositoryTest {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AccountRepository accountRepository;

    private long companyId;
    private long accountId;


    @Before
    public void setUp(){
        CompanyDraft draft = new CompanyDraft.Builder().name("Test").dateCreated(new Date()).build();
        companyId = companyRepository.insert(draft);

        AccountDraft accountDraft = new AccountDraft(companyId, "Test", new Date(), 0, false,
                false, false, false);
        accountId = accountRepository.insert(accountDraft);
    }

    @Test
    public void get(){
        Optional<Account> account = accountRepository.get(accountId);
        Assert.assertTrue(account.isPresent());
    }


    @Test
    public void update(){
        Optional<Account> account = accountRepository.get(accountId);
        int updated = accountRepository.update(new Account(accountId, account.get().draft().name("Tester").requireNonLetterOrDigit(true).build()));
        Assert.assertTrue(updated == 1);

        Optional<Account> updatedAccount = accountRepository.get(accountId);
        Assert.assertFalse(account.get().getName().equals(updatedAccount.get().getName()));
        Assert.assertTrue(account.get().getDateAdded().equals(updatedAccount.get().getDateAdded()));
        Assert.assertTrue(updatedAccount.get().isRequireNonLetterOrDigit());
    }


    @Test
    public void findByName(){
        Optional<Account> account = accountRepository.findByName(companyId,"Test");
        Assert.assertTrue(account.isPresent());
    }


    @After
    public void tearDown(){
        accountRepository.delete(accountId);
        companyRepository.delete(companyId);
    }
}
