package com.vti.controller;

import java.util.List;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.AccountDTO;
import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;
import com.vti.form.AccountForm;
import com.vti.service.IAccountService;

@RestController
@RequestMapping(value = "api/accounts")
@CrossOrigin("*")
public class AccountController {
	
	@Autowired
	private IAccountService acService;
	
	@Autowired
	private ModelMapper modelMapper;
	@PreAuthorize("Admin; Employee")
	@GetMapping
	public List<AccountDTO> getListAccounts() {
		
		List<Account> accounts = acService.getListAccounts();
		
		List<AccountDTO> lsAcDTO = modelMapper.map(accounts, new TypeToken< List<AccountDTO> >() {}.getType()); 
		return lsAcDTO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/paging")
	public Page<AccountDTO> getListAccountsPaging(Pageable pageable,
				@RequestParam(value = "search", required = false) String search,
				AccountFilterForm acFF
			) {
		System.out.println("acFF: ");
		System.out.println(acFF.toString());
		
		Page<Account> pageAccount = acService.getListAccountsPaging(pageable, search, acFF);
		
		List<AccountDTO> lsAcDTO = modelMapper.map(pageAccount.getContent(), new TypeToken< List<AccountDTO> >() {}.getType());
		
		Page<AccountDTO> pageAcDTO = new PageImpl(lsAcDTO, pageable, pageAccount.getTotalElements());
		
		return pageAcDTO;
	}
	
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> deleteAccount(@PathVariable(name = "id") int id) {
		acService.deleteAccount(id);
		return ResponseEntity.status(HttpStatus.OK).body("Account deleted!");
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAccount(@PathVariable(name = "id") int id,
		@RequestBody AccountForm accountForm
	) {
		
		Account ac = modelMapper.map(accountForm, Account.class);
		
		ac.setId(id);
		
		acService.updateAccount(ac);
		
		JSONObject message = new JSONObject();
		message.put("rusultText", "Account updated successfully!");
		message.put("status", 200);
		
		return ResponseEntity.status(HttpStatus.OK).body(message.toString());
	}
	
	@PostMapping
	public ResponseEntity<?> createAccount(@RequestBody AccountForm accountForm
	) {
		
		Account ac = modelMapper.map(accountForm, Account.class);
		
		//Encrypt password before insert to database
		BCryptPasswordEncoder psEncoder = new BCryptPasswordEncoder();
		String encryptPassword = psEncoder.encode(accountForm.getPassword());
		ac.setPassword(encryptPassword);
		
		acService.createAccount(ac);
		
		JSONObject message = new JSONObject();
		message.put("rusultText", "Account created successfully!");
		message.put("status", 200);
		
		return ResponseEntity.status(HttpStatus.OK).body(message.toString());
	}
	
	@RequestMapping(value = "/delete-multiple", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMultipleAccounts(@RequestBody List<Integer> ids) {
		System.out.println(ids);
		acService.deleteMultipleAccounts(ids);
		JSONObject message = new JSONObject();
		message.put("rusultText", "Accounts deleted");
		message.put("status", 200);
		return ResponseEntity.status(HttpStatus.OK).body(message.toString());
	}
	
	@GetMapping("/departmentId-null")
	public List<AccountDTO> getListAccountsAddDepartment() {
		List<Account> listAccount = acService.getListAccountsAddDepartment();
		
		List<AccountDTO> listAccountDTO = modelMapper.map(listAccount, new TypeToken< List<AccountDTO> >(){}.getType());
		
		return listAccountDTO;
	}
	
}




























