package com.my.user.service;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import com.alibaba.druid.util.StringUtils;
import com.my.common.utils.EncryptionUtils;

public class PasswordMatcher implements CredentialsMatcher{

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		char[] pw = (char[])token.getCredentials();
		return StringUtils.equals(EncryptionUtils.hash(EncryptionUtils.SHA256, String.valueOf(pw), null), ((String)info.getCredentials()).toUpperCase());
	}

}
