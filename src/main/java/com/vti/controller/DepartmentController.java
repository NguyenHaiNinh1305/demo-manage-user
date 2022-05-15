package com.vti.controller;

import java.util.List;

import javax.validation.Valid;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.DepartmentDTO;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.form.AccountForm;
import com.vti.form.DepartmentFilterForm;
import com.vti.form.DepartmentUpdateForm;
import com.vti.repository.IDepartmentRepository;
import com.vti.service.IDepartmentService;

@RestController
@RequestMapping(value = "/api/departments")
public class DepartmentController {
	
	@Autowired
	private IDepartmentRepository dpRepository;
	
	@Autowired
	private IDepartmentService dpService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<DepartmentDTO> getListDepartments() {
		List<Department> departments = dpRepository.findAll();
		List<DepartmentDTO> lsDpDTO = modelMapper.map(departments, new TypeToken< List<DepartmentDTO> >() {}.getType());
		return lsDpDTO;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/paging")
	public Page<DepartmentDTO> getListDepartmentsPaging(Pageable pageable, 
				@RequestParam(name = "search", required = false) String search,
				DepartmentFilterForm dpFF
			) {
		Page<Department> pgDepartment = dpService.getListDepartmentsPaging(pageable, search, dpFF);
		List<DepartmentDTO> listDepartmentDTO = modelMapper.map(pgDepartment.getContent(), new TypeToken< List<DepartmentDTO> >() {}.getType());
		Page<DepartmentDTO> pgDepartmentDTO = new PageImpl(listDepartmentDTO, pageable, pgDepartment.getTotalElements());
		return pgDepartmentDTO;
	}
	
	@PostMapping
	public ResponseEntity<?> createDepartment(@RequestBody @Valid DepartmentDTO dpDTO) {
		System.out.println(dpDTO);
		Department dp = modelMapper.map(dpDTO, Department.class);
		dpService.createDepartment(dp);
		
		JSONObject message = new JSONObject();
		message.put("rusultText", "Department inserted successfully");
		message.put("status", 200);
		
		return ResponseEntity.status(HttpStatus.OK).body(message.toString());
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateDepartment(@PathVariable(name = "id") int id,
		@RequestBody DepartmentUpdateForm dpUpdateForm
	) {
		
		Department dp = modelMapper.map(dpUpdateForm, Department.class);
		
		dp.setId(id);
		System.out.println("department update: ");
		System.out.println(dp);
		
		dpService.updateDepartment(dp);
		
		JSONObject message = new JSONObject();
		message.put("rusultText", "Department updated successfully!");
		message.put("status", 200);
		
		return ResponseEntity.status(HttpStatus.OK).body(message.toString());
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> deleteDepartment(@PathVariable(name = "id") int id) {
		dpService.deleteDepartment(id);
		JSONObject message = new JSONObject();
		message.put("rusultText", "Department deleted successfully");
		message.put("status", 200);
		return ResponseEntity.status(HttpStatus.OK).body(message.toString());
	}
	
	@RequestMapping(value = "/delete-multiple", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMultipleAccounts(@RequestBody List<Integer> ids) {
		System.out.println(ids);
		dpRepository.deleteMultipleDepartments(ids);
		JSONObject message = new JSONObject();
		message.put("rusultText", "Accounts deleted");
		message.put("status", 200);
		return ResponseEntity.status(HttpStatus.OK).body(message.toString());
	}
	
}
