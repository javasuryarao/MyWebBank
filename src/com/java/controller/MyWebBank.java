package com.java.controller;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.bean.Account;
import com.java.dao.mongo.AccountDAO;
import com.java.dao.mongo.UpdateHits;
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
 		UpdateHits.update();
 		return new ModelAndView("welcome", "message", buildMessage(account));
	}

	private Account buildAccount(HttpServletRequest request) {
		SessionHelper helper = new SessionHelper(request);
		Account account = AccountDAO.getAccount(buildUserName(helper));
		account.setAccountBalance(depositMoney(account, 123.00));
		account.setHits(buildPageHits(helper));
		System.out.println(account);
		return account;
	}

	private double depositMoney(Account account, double depositAmount) {
		return AccountDAO.deposit(account, depositAmount);
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

	private String buildMessage(Account account) {
		String message = "<br><div style='text-align:center;'>"
				+ "<h1>Hello "+account.getUserName()+"!</h1><div><br>Your account balance is $"+formatBalance(account.getAccountBalance())+"</div><br>";
		return message;
	}

	private String formatBalance(double value) {
		
		
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