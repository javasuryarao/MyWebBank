package com.java.controller;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.bean.Account;
import com.java.util.FileHelper;
import com.java.util.SessionHelper;
import com.java.util.StringHelper;

@Controller
public class MyWebBank {

	@Autowired
	private FileHelper fileHelper;
	
	@RequestMapping("/welcome")
	public ModelAndView MyWebBank(HttpServletRequest request) {
 		Account account = buildAccount(request);
		return new ModelAndView("welcome", "message", buildMessage(account.getUserName()));
	}

	private Account buildAccount(HttpServletRequest request) {
		SessionHelper helper = new SessionHelper(request);
		Account account = new Account();
		account.setAccountBalance(fetchAccountBalance());
		account.setAccountBalance(depositMoney(123.00));
		account.setHits(buildPageHits(helper));
		account.setUserName(buildUserName(helper));
		System.out.println(account);
		return account;
	}

	private String depositMoney(double depositAmount) {
		String newAccountBalace = getFileHelper().updateFile(depositAmount);
		return newAccountBalace;
	}

	private String buildUserName(SessionHelper helper) {
		String userName = helper.getRequest().getParameter("userName");
		if(userName == null)
		{
			userName =helper.getString(("userName"));
			
		}
		helper.setAttribute("userName", userName);
		return userName;
	}

	private String buildMessage(String userName) {
		String accountBalance = fetchAccountBalance();
		String message = "<br><div style='text-align:center;'>"
				+ "<h1>Hello "+userName+"!</h1><div><br>Your account balance is $"+accountBalance+"</div><br>";
		return message;
	}

	private String fetchAccountBalance() {
		String accountBalanceOnFile = getFileHelper().readFile();
		double value = Math.random()*1000000;
		if(StringHelper.isNotEmpty(accountBalanceOnFile))
		{
			value = Double.parseDouble(accountBalanceOnFile);
		}
		
		DecimalFormat myFormatter = new DecimalFormat("###,###.##");
	    String accountBalance = myFormatter.format(value);
	    return accountBalance;
	}

	private int buildPageHits(SessionHelper helper) {
		int pageHit =helper.getInt("pageHit");
		helper.setAttribute("pageHit", ++pageHit);
		return pageHit;
	}

	public FileHelper getFileHelper() {
		return fileHelper;
	}

	public void setFileHelper(FileHelper fileHelper) {
		this.fileHelper = fileHelper;
	}
	
	
}