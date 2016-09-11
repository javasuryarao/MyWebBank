package com.java.util;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import com.java.common.Constants;

public class FileHelper {
	public ApplicationContext appContext;

	public String name;

	public String readFile() {
		String fileContent = Constants.EMPTY_STRING;
		try {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
			Resource resource = ctx.getResource(getName());
			File file = resource.getFile();
			StringBuilder fileContents = new StringBuilder((int) file.length());
			Scanner scanner = new Scanner(file);
			String lineSeparator = System.getProperty("line.separator");

			try {
				while (scanner.hasNextLine()) {
					fileContents.append(scanner.nextLine() + lineSeparator);
				}
				fileContent = fileContents.toString();
			} finally {
				ctx.close();
				scanner.close();
			}

		} catch (Exception e) {
			System.out.println(getName() + " did not work.");
		}
		return fileContent;

	}

	public String updateFile(double depositAmount) {
		String fileContent = Constants.EMPTY_STRING;
		try {

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
			Resource resource = ctx.getResource(getName());
			File file = resource.getFile();

			StringBuilder fileContents = new StringBuilder((int) file.length());
			Scanner scanner = new Scanner(file);
			String lineSeparator = System.getProperty("line.separator");
			PrintWriter writer = null;
			try {
				while (scanner.hasNextLine()) {
					fileContents.append(scanner.nextLine() + lineSeparator);
				}
				fileContent = fileContents.toString();

				writer = new PrintWriter(file);
				double currentBalance = Double.parseDouble(fileContent);
				double newBalance = currentBalance + depositAmount;
				fileContent = newBalance + "";
				writer.println(fileContent);

			} finally {
				writer.close();
				ctx.close();
				scanner.close();
			}

		} catch (Exception e) {
			System.out.println(getName() + " did not work.");
		}
		return fileContent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}