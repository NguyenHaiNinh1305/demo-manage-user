package com.vti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;
import com.vti.repository.IAccountRepository;
import com.vti.specification.AccountSpecification;

@Service
public class AccountService implements IAccountService{
	
	@Autowired
	private IAccountRepository acRepository;
	
	@Override
	public List<Account> getListAccounts() {
		return acRepository.findAll();
	}

	@Override
	public Page<Account> getListAccountsPaging(Pageable pageable, String search, AccountFilterForm acFF) {
		Specification<Account> where = AccountSpecification.buildWhere(search, acFF);
		
		return acRepository.findAll(where, pageable);
	}
	
	@Override
	public void deleteAccount(int id) {
		acRepository.deleteById(id);
	}
	
	@Override
	public void deleteMultipleAccounts(List<Integer> ids) {
		acRepository.deleteMultipleAccounts(ids);
	}
	
	@Override
	public void updateAccount(Account ac) {
		//Load account by id
		Account ac2 = acRepository.findById(ac.getId());
		
		//setup properties to update
		ac2.setUsername(ac.getUsername());
		ac2.setFirstName(ac.getFirstName());
		ac2.setLastName(ac.getLastName());
		ac2.setRole(ac.getRole());
		ac2.setDepartment(ac.getDepartment());
		
		//save account
		acRepository.save(ac2);
	}
	
	@Override
	public void createAccount(Account ac) {
		acRepository.save(ac);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account ac = acRepository.findByUsername(username);
		if (ac == null) throw new UsernameNotFoundException(username);
		if (ac.getRole() != null) {
			return new User(
					ac.getUsername(),
					ac.getPassword(),
					AuthorityUtils.createAuthorityList(ac.getRole().toString())
			);
		}else {
			return new User(
					ac.getUsername(),
					ac.getPassword(),
					AuthorityUtils.createAuthorityList("Employee")
			);
		}
	}

	@Override
	public List<Account> getListAccountsAddDepartment() {
		return acRepository.getListAccountsAddDepartment();
	}

}












