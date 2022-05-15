package com.vti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.vti.entity.Department;

public interface IDepartmentRepository extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department>{
	
	Department findById(int id);
	
	@Modifying
	@Transactional
	@Query("DELETE Department dp WHERE dp.id IN(:ids)")
	void deleteMultipleDepartments(List<Integer> ids);

}
