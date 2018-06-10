package com.algorithms.auth.userservice.repository;

import com.algorithms.auth.userservice.domain.Company;
import com.algorithms.auth.userservice.domain.draft.CompanyDraft;

import java.util.List;


public interface CompanyRepository extends Repository<Company> {

    long insert(CompanyDraft draft);

    List<String> companyNamesLikeDefault();
}
