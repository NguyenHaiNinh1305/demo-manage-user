package com.vti.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;

public class AccountSpecification {
	@SuppressWarnings("deprecation")
	public static Specification<Account> buildWhere(String search, AccountFilterForm acFF) {
		Specification<Account> where = null;
		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			
			CustomSpecificationAccount username = new CustomSpecificationAccount("username", search);
			CustomSpecificationAccount firstName = new CustomSpecificationAccount("firstName", search);
			CustomSpecificationAccount lastName = new CustomSpecificationAccount("lastName", search);
			
			where = Specification.where(username).or(firstName).or(lastName);
		}
		
		if (acFF != null && !StringUtils.isEmpty(acFF.getRole())) {
			CustomSpecificationAccount role = new CustomSpecificationAccount("role", acFF.getRole());
			if (where == null) where = role;
			else where = where.and(role);
		}
		
		if (acFF != null && acFF.getDepartmentId() != 0) {
			CustomSpecificationAccount deapartmentId = 
					new CustomSpecificationAccount("departmentId", acFF.getDepartmentId());
			if (where == null) where = deapartmentId;
			else where = where.and(deapartmentId);
		}
		
		return where;
	}
}
