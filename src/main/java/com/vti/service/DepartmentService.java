package com.vti.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vti.dto.DepartmentDTO;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;
import com.vti.repository.IAccountRepository;
import com.vti.repository.IDepartmentRepository;
import com.vti.specification.DepartmentSpecification;

@Service
public class DepartmentService implements IDepartmentService{

	@Autowired
	private IDepartmentRepository dpRepository;
	
	@Autowired
	private IAccountRepository acRepository;
	
	@Override
	public List<Department> getListDepartments() {
		return dpRepository.findAll();
	}


	@Override
	public Page<Department> getListDepartmentsPaging(Pageable pageable, String search, DepartmentFilterForm dpFF) {
		Specification<Department> where = DepartmentSpecification.buildWhere(search, dpFF);
		return dpRepository.findAll(where, pageable);
	}

	@Override
	@Transactional
	public void createDepartment(Department dp) {
		System.out.println(dp.getAccounts().toString());
		
		List<Account> accounts = new ArrayList<>();
		
		for (Account ac : dp.getAccounts()) {//dp.getAccounts() return list accounts have only id
			Account ac2 = acRepository.findById(ac.getId());//Get account details by id
			accounts.add(ac2);
		}
		
		dp.setAccounts(accounts);//Add list accounts details for department
		
		System.out.println(dp);
		
		Department department  = dpRepository.save(dp);
		
		for(Account account : accounts) {
			account.setDepartment(department);
		}
		acRepository.saveAll(accounts);
		
		
	}

	@Override
	public void deleteDepartment(int id) {
		dpRepository.deleteById(id);
	}

	@Override
	public void deleteMultipleDepartments(List<Integer> ids) {
		dpRepository.deleteMultipleDepartments(ids);
	}


	@Override
	public void updateDepartment(Department dp) {
		//Load department by id
		Department dp2 = dpRepository.findById(dp.getId());
		
		//setup properties to update
		dp2.setName(dp.getName());
		dp2.setType(dp.getType());
		
		//save account
		dpRepository.save(dp2);
	}

}
