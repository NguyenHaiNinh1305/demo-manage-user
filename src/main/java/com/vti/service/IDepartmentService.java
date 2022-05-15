package com.vti.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;

public interface IDepartmentService {
	List<Department> getListDepartments();
	
	Page<Department> getListDepartmentsPaging(Pageable pageable, String search, DepartmentFilterForm dpFF);

	void createDepartment(Department dp);

	void deleteDepartment(int id);
	
	void deleteMultipleDepartments(List<Integer> ids);

	void updateDepartment(Department dp);
}
