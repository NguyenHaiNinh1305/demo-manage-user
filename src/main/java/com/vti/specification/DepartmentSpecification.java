package com.vti.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;

public class DepartmentSpecification {
	public static Specification<Department> buildWhere(String search, DepartmentFilterForm dpFF) {
		Specification<Department> where = null;
		
		if (!StringUtils.isEmpty(search)) {
			CustomSpecificationDepartment name = new CustomSpecificationDepartment("name", search.trim());
			where = Specification.where(name);
		}
		
		if (dpFF != null && !StringUtils.isEmpty(dpFF.getType())) {
			CustomSpecificationDepartment type = new CustomSpecificationDepartment("type", dpFF.getType());
			if (where == null) where = type;
			else where = where.and(type);
		}
		
		if (dpFF != null && dpFF.getMinDate() != null) {
			CustomSpecificationDepartment minDate = new CustomSpecificationDepartment("minDate", dpFF.getMinDate());
			if (where == null) where = minDate;
			else where = where.and(minDate);
		}
		
		if (dpFF != null && dpFF.getMaxDate() != null) {
			CustomSpecificationDepartment maxDate = new CustomSpecificationDepartment("maxDate", dpFF.getMaxDate());
			if (where == null) where = maxDate;
			else where = where.and(maxDate);
		}
		
		return where;
	}
}
