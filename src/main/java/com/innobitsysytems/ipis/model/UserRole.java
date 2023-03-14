/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: UserRole Model
 */
package com.innobitsysytems.ipis.model;

import java.util.Arrays;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "usersrole")
@EntityListeners(AuditingEntityListener.class)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "roleText", nullable = false)
    private String roleText;
    

    @NotNull(message = "userPermissions is mandatory")
    @Column(name = "userPermissions", nullable = false,columnDefinition = "text[]")
    @Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
    private String[] userPermissions;
    


	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleText() {
        return roleText;
    }

    public void setRoleText(String roleText) {
        this.roleText = roleText;
    }

   
    public String[] getUserPermissions() {
		return userPermissions;
	}

	public void setUserPermissions(String[] userPermissions) {
		this.userPermissions = userPermissions;
	}

	@Override
	public String toString() {
		return "UserRole [id=" + id + ", roleText=" + roleText + ", userPermissions=" + Arrays.toString(userPermissions)
				+ "]";
	}
}
