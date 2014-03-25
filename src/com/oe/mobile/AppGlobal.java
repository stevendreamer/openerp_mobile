/*
 * Copyright (C) 2013  stevendreamer (in github)
 * Project Location: https://github.com/stevendreamer/openerp_mobile

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * Addition: any copy of this program should keep the author name info.
 * any copy without the author info will be a pirate

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
	private static Menu menu;

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
	
	public static Menu getMenu(){
		if(AppGlobal.menu == null){
			AppGlobal.menu=new Menu();
		}
		return AppGlobal.menu;
	}

}
