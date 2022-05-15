package com.vti.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.vti.entity.Account.AccountRole;

import lombok.Data;

@Entity
@Table(name = "Department")
@Data
public class Department {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", length = 50, nullable = false)
	private String name;
	
	@Column(name = "total_member", nullable = false)
	private int totalMember;
	
	@Column(name = "type", columnDefinition = "ENUM('Dev', 'Test', 'Scrum_master', 'PM')")
	@Enumerated(EnumType.STRING)
	private DepartmentType type;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;
	
//	@ManyToMany(mappedBy = "departments")
//	private List<Account> accounts;
	
	@OneToMany(mappedBy = "department")
	private List<Account> accounts;
	
	public enum DepartmentType {
		Dev, Test, ScrumMaster, PM;
		
		public static DepartmentType toEnum(String role) {
			for (DepartmentType item : DepartmentType.values()) {
				if (item.toString().equals(role)) return item;
			}
			return null;
		}
	}
	
}
