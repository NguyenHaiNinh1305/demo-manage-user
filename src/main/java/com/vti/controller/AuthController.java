package com.vti.controller;

import javax.validation.Valid;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.JwtResponseDTO;
import com.vti.dto.SigninDTO;
import com.vti.dto.SignupDTO;
import com.vti.entity.Account;
import com.vti.repository.IAccountRepository;
import com.vti.service.IAccountService;
import com.vti.utils.JwtUtils;

@RestController
@RequestMapping(value = "/api/auth")
@Validated
public class AuthController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Autowired
	private IAccountService acService;
	
	@Autowired
	private IAccountRepository acRepository;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupAccount(@RequestBody @Valid SignupDTO signupDTO) {
		System.out.println(signupDTO.toString());
		
		Account ac = modelMapper.map(signupDTO, Account.class);
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String enCryptPassword = bCryptPasswordEncoder.encode(signupDTO.getPassword());
		ac.setPassword(enCryptPassword);
		
		acService.createAccount(ac);
		
		JSONObject message = new JSONObject();
		message.put("status", 200);
		message.put("resultText", "Register account successfully!");
		
		return ResponseEntity.status(HttpStatus.OK).body(message.toString());
		
	}
	
	@SuppressWarnings("unused")
	@GetMapping("/signin")
	public ResponseEntity<?> signinAccount(@RequestBody @Valid SigninDTO signinDTO) {
		System.out.println(signinDTO.toString());
		
		Account ac = acRepository.findByUsername(signinDTO.getUsername());
		
		System.out.println(ac);
		
		if (ac == null) throw new UsernameNotFoundException(signinDTO.getUsername());
		
		
		Authentication auth = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(signinDTO.getUsername(), signinDTO.getPassword())
		);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String jwtToken = jwtUtils.generateJwtToken(auth);
		
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		
		return ResponseEntity.ok(new JwtResponseDTO(
			jwtToken, userDetails.getUsername(), userDetails.getAuthorities().toString()
		));
		
	}
	
	
	
	
	
}
