package repository;

import com.algorithms.auth.userservice.Application;
import com.algorithms.auth.userservice.domain.Company;
import com.algorithms.auth.userservice.domain.draft.CompanyDraft;
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
public class CompanyRepositoryTest {
    @Autowired
    private CompanyRepository companyRepository;

    private  long id;

    @Before
    public void setUp(){
        CompanyDraft draft = new CompanyDraft.Builder().name("Test").dateCreated(new Date()).build();
        id = companyRepository.insert(draft);
    }

    @Test
    public void get(){
        Optional<Company> company = companyRepository.get(id);
        Assert.assertTrue(company.isPresent());
    }

    @Test
    public void update(){
        Optional<Company> company = companyRepository.get(id);
        int updated = companyRepository.update(new Company(id, company.get().draft().name("Tester").build()));
        Assert.assertTrue(updated == 1);

        Optional<Company> updatedCompany = companyRepository.get(id);
        Assert.assertFalse(company.get().getName().equals(updatedCompany.get().getName()));
        Assert.assertTrue(updatedCompany.get().getDateCreated().equals(company.get().getDateCreated()));
    }

    @After
    public void tearDown(){
        companyRepository.delete(id);
    }
}
