/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: User Model
 */
package com.innobitsysytems.ipis.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
//@SQLDelete(sql = "UPDATE users SET status='Deleted' WHERE id=?")
//@Where(clause = "status <> 'Deleted'")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Size(max=40)
    @NotNull
    @Pattern(regexp ="^[ A-Za-z]+$",message ="firstname should be alphabets")
	@Column(name = "firstname", nullable = false)
    private String firstname;
    
    @Size(max=40)
    @NotNull
    @Pattern(regexp ="^[ A-Za-z]+$",message ="lastname should be alphabets")
	@Column(name = "lastname", nullable = false)
    private String lastname;
    
    @Email(message ="email should be valid")
    @NotBlank
	@Column(name = "email", nullable = false,unique=true)
    private String email;
    
    @Size(min=10,max=10)
    @Pattern(regexp ="^\\d{10}$")
    @NotNull(message ="mobile no should be valid")
    @Column(name = "mobileNo", nullable = false,unique=true)
    private String mobileNo;
    
   
    @ManyToOne()
    @JoinColumn(name = "roleId",referencedColumnName = "id",nullable = false)
    private UserRole userRole;
    
//    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<Login> login;
    
    @NotNull(message = "password is mandatory")
    @Length(min=6,max=100,message="length should be between 7 to 15")
    @Column(name = "password", nullable = false)
    private String password;
    
    @NotNull(message = "userstatus   is mandatory")
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(columnDefinition = "boolean default false",name = "isVerified", nullable = false)
    private boolean isVerified;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @NotNull(message = "created by  is mandatory")
    @Column(name = "created_by", nullable = false)
    @CreatedBy
    private String createdBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "updated_by", nullable = true)
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "deleted_by", nullable = true)
    @LastModifiedBy
    private String deletedBy;
    

  public long getId() {
        return id;
    }

  public void setId(long id) {
        this.id = id;
    }
  
  public String getFirstname() {
	return firstname;
}

public void setFirstname(String firstname) {
	this.firstname = firstname;
}

public String getLastname() {
	return lastname;
}

public void setLastname(String lastname) {
	this.lastname = lastname;
}

public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
    

  public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

public String getPassword() {
        return password;
    }

   public void setPassword(String password) {
        this.password = password;
    }

	public String getStatus() {

        return status;
    }

  public void setStatus(String status) {
    this.status = status;
    }
  
  public Date getCreatedAt() {
        return createdAt;
    }

 
  public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

  
  public String getCreatedBy() {
        return createdBy;
    }

  public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

  public Date getUpdatedAt() {
        return updatedAt;
    }

  
  public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


  public String getUpdatedBy() {
        return updatedBy;
    }


  public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

  
  public String getDeletedBy() {
        return deletedBy;
    }

  public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public boolean isVerified() {
	return isVerified;
}

public void setVerified(boolean isVerified) {
	this.isVerified = isVerified;
}

@Override
public String toString() {
	return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", mobileNo="
			+ mobileNo + ", userRole=" + userRole + ", password=" + password + ", status=" + status + ", isVerified="
			+ isVerified + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", updatedAt=" + updatedAt
			+ ", updatedBy=" + updatedBy + ", deletedBy=" + deletedBy + "]";
}

	
}
