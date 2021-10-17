package com.todo;

import java.util.ArrayList;
import java.util.Scanner;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
	
		boolean quit = false;
		
		
		Menu.displaymenu();
		do {
			Menu.prompt();
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name":
				System.out.println("제목순으로 정렬했습니다");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬했습니다");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬했습니다");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜역순으로 정렬했습니다");
				TodoUtil.listAll(l, "due_date", 0);
				break;
				
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;
				
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findCateList(l, cate);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "comp":
				int index = sc.nextInt();
				TodoUtil.completeItem(l, index);
				break;
				
			case "ls_comp":
				TodoUtil.listAll(l, 1);
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				break;

			default:
				System.out.println("정확한 명령어로 입력해주세요. (도움말 - help)");
				break;
			}
			
		} while (!quit);
	}
}
