package com.oe.mobile;

import java.util.ArrayList;

public class Menu {
	// sets all the menuItem
		private class MenuItem {
			private int sequence;
			private String parentMenu;
			private String menu;

			// use sequence to order the menu
			// this is the sequence of the particular menu
			// etc, when the menu is MenuItem(1, "CRM", "Customer"), it will show as the first menu item in the "CRM" menu
			public MenuItem(int sequence, String parentMenu, String menu) {
				this.sequence=sequence;
				this.parentMenu = parentMenu;
				this.menu = menu;
			}

			public String getParent() {
				return parentMenu;
			}

			public String getMenu() {
				return menu;
			}

			public boolean isRoot() {
				if ("".endsWith(parentMenu))
					return true;

				return false;
			}
		}

		ArrayList<MenuItem> menuList = new ArrayList<MenuItem>();
		
		public Menu(){
			this.initMenu();
		}

		public void initMenu() {
			// root menu
			menuList.add(new MenuItem(1,"","Message"));
			menuList.add(new MenuItem(2,"","CRM"));
			menuList.add(new MenuItem(3,"","库存"));
			menuList.add(new MenuItem(4,"","采购"));
			menuList.add(new MenuItem(5,"","销售"));
			menuList.add(new MenuItem(6,"","生产"));
			menuList.add(new MenuItem(7,"","报表"));
			menuList.add(new MenuItem(8,"","TechTest"));
			
			// menu of 消息
			menuList.add(new MenuItem(1, "Message","消息"));
			
			// menu of CRM
			menuList.add(new MenuItem(1,"CRM","客户信息"));
			menuList.add(new MenuItem(2,"CRM","线索"));
			menuList.add(new MenuItem(3,"CRM","商机"));
			
			// menu of 库存
			menuList.add(new MenuItem(1,"库存","物料查询"));
			menuList.add(new MenuItem(2,"库存","子库存查询"));
			menuList.add(new MenuItem(3,"库存","库存接收"));
			
			// menu of 采购
			menuList.add(new MenuItem(1,"采购","采购订单"));
			menuList.add(new MenuItem(2,"采购","入库单"));
			
			// menu of 销售
			menuList.add(new MenuItem(1,"销售","销售订单"));
			menuList.add(new MenuItem(2,"销售","发货单"));
			
			// menu of 生产
			menuList.add(new MenuItem(1,"生产","工单"));
			menuList.add(new MenuItem(2,"生产","工票"));
			menuList.add(new MenuItem(3,"生产","工单物料需求"));
			menuList.add(new MenuItem(4,"生产","工序转移"));
			menuList.add(new MenuItem(5,"生产","在制品检查"));
			
			// menu of TechTest
			menuList.add(new MenuItem(1,"TechTest","ChartTest"));
			menuList.add(new MenuItem(2,"TechTest","ScanTest"));
			menuList.add(new MenuItem(3,"TechTest","ImageTest"));
			menuList.add(new MenuItem(4,"TechTest","GPSTest"));
			
			
		}
		
		// get the particular menu Strings
		public ArrayList<String> constructMenu(String parentMenu){
			ArrayList<String> menu = new ArrayList<String>();
			for(MenuItem i: menuList){
				if (i.getParent().equals(parentMenu)){
					menu.add(i.getMenu());
				}
			}
			return menu;
		}
}
