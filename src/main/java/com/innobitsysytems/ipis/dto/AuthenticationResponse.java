/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Authentication Response
 */
package com.innobitsysytems.ipis.dto;


import java.time.Instant;


public class AuthenticationResponse {
    private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
    private String username;
    private String fname;
    private String lname;
    private long id;
    private String role;
    private boolean isVerified;
    
    public AuthenticationResponse(String authenticationToken, String refreshToken, Instant expiresAt, String username, String fname, String lname, long id, String role, boolean isVerified) {
		super();
		this.authenticationToken = authenticationToken;
		this.refreshToken = refreshToken;
		this.expiresAt = expiresAt;
		this.username = username;
                this.fname = fname;
                this.lname = lname;
                this.id = id;
                this.role = role;
                this.isVerified= isVerified;
                
	}


	public static AuthenticationResponse.Builder builder() {
        return new AuthenticationResponse.Builder();
    }
    
    
	public String getAuthenticationToken() {
		return authenticationToken;
	}
	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public Instant getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }


	@Override
	public String toString() {
		return "AuthenticationResponse [authenticationToken=" + authenticationToken + ", refreshToken=" + refreshToken
				+ ", expiresAt=" + expiresAt + ", username=" + username + "]";
	}
	
	public static class Builder {
            
	    private String authenticationToken;
	    private String refreshToken;
	    private Instant expiresAt;
	    private String username;
            private String fname;
            private String lname;
            private long id;
            private String role;
            private boolean isVerified;
            
	   
	    
	    public AuthenticationResponse.Builder authenticationToken(String authenticationToken) {
	      this.authenticationToken = authenticationToken;
	      return this;
	    }
	   
	    public AuthenticationResponse.Builder refreshToken(String refreshToken) {
	      this.refreshToken = refreshToken;
	      return this;
	    }
	    
	    public AuthenticationResponse.Builder expiresAt(Instant expiresAt) {
		      this.expiresAt = expiresAt;
		      return this;
	    }
	    
	    public AuthenticationResponse.Builder username(String username) {
	      this.username = username;
	      return this;
	    }
            
             public AuthenticationResponse.Builder fname(String fname) {
	      this.fname = fname;
	      return this;
	    }
	    public AuthenticationResponse.Builder lname(String lname) {
	      this.lname = lname;
	      return this;
	    }
            
            public AuthenticationResponse.Builder id(long id) {
	      this.id = id;
	      return this;
	    }
             public AuthenticationResponse.Builder role(String role) {
	      this.role = role;
	      return this;
	    }
              public AuthenticationResponse.Builder isVerified(boolean isVerified) {
	      this.isVerified = isVerified;
	      return this;
	    }
	    
	    public AuthenticationResponse build() {
	       return new AuthenticationResponse(authenticationToken, refreshToken, expiresAt,username,fname,lname,id,role,isVerified);
	    }
	  } 
	
}