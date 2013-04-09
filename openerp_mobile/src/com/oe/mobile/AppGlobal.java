package com.oe.mobile;

import java.util.ArrayList;

import com.debortoliwines.openerp.api.Session;

public class AppGlobal {
	private static Session oesession;
	private static String hostname;
	private static int port;
	private static String dbname;
	private static ArrayList<String> dblist;
	private static String username;
	private static String password;

	public static Session getOesession() {
		return oesession;
	}

	public static void setOesession(Session oesession) {
		AppGlobal.oesession = oesession;
	}

	public static String getHostname() {
		return hostname;
	}

	public static void setHostname(String hostname) {
		AppGlobal.hostname = hostname;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		AppGlobal.port = port;
	}

	public static String getDbname() {
		return dbname;
	}

	public static void setDbname(String dbname) {
		AppGlobal.dbname = dbname;
	}

	public static ArrayList<String> getDblist() {
		return dblist;
	}

	public static void setDblist(ArrayList<String> dblist) {
		AppGlobal.dblist = dblist;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		AppGlobal.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		AppGlobal.password = password;
	}

}
