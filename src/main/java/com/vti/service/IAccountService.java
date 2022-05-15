package com.vti.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;

public interface IAccountService extends UserDetailsService{
	List<Account> getListAccounts();
	
	Page<Account> getListAccountsPaging(Pageable pageable, String search, AccountFilterForm acFF);

	void deleteAccount(int id);

	void updateAccount(Account ac);

	void createAccount(Account ac);

	void deleteMultipleAccounts(List<Integer> ids);

	List<Account> getListAccountsAddDepartment();
}
