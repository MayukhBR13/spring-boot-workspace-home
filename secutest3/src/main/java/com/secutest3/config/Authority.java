package com.secutest3.config;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority{

	    private String authority;
	    Authority(String authority){
	        this.authority=authority;
	    }
	    @Override
	    public String getAuthority() {
	        
	        return this.authority;
	    }
	    
	}

