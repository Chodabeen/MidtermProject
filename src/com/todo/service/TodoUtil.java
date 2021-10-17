package com.todo.service;

import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import java.io.Writer;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class TodoUtil {
	
	public static void createItem(TodoList l) {
		
		String title, desc, cate, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("<항목 추가>\n" + "카테고리 : ");
		cate = sc.nextLine();
		
		System.out.print("제목: ");
		title = sc.nextLine().trim();
		if (l.isDuplicate(title)) {
			System.out.printf("중복되는 제목입니다!");
			return;
		}
		System.out.print("내용 : ");
		desc = sc.nextLine().trim();
	
		System.out.print("마감날짜(yyyy/MM/dd): ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc, cate, due_date);
		if(l.addItem(t) > 0)
			System.out.println("추가되었습니다");
	}
	
	

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("<항목 삭제>\n" + "삭제할 항목의 번호 : ");
		int index = sc.nextInt();
		if(l.deleteItem(index) > 0)
			System.out.println("삭제되었습니다");
		
		/*for (TodoItem item : l.getList()) {
			if (num-1 == l.indexOf(item)) {
				System.out.println(num + ". [" + item.getCategory() + "] - " +item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
				System.out.print("위 항목을 삭제하시겠습니까? (y/n) ");
				char answer = sc.next().charAt(0);
				if(answer == 'y') {
					l.deleteItem(item);
					System.out.println("삭제되었습니다");
					break;
				}
				System.out.println("취소되었습니다 ");
				break;
			}
		}
		*/
	}
	


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("<항목 수정>\n" + "수정할 항목의 번호 : ");
		int index = sc.nextInt();
		/*if (num > (l.getList().size())+1 || num < 1) {
			System.out.println("존재하지 않는 항목입니다!");
			return;
		}*/
		
		sc.nextLine();
		System.out.print("새 카테고리 : ");
		String new_category = sc.nextLine();

		System.out.print("세 제목 : ");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("중복되는 제목입니다!");
			return;
		}
	
		System.out.print("새 내용 : ");
		String new_desc = sc.nextLine().trim();
		System.out.print("새 마감날짜(yyyy/MM/dd) : ");
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date);
		t.setId(index);
		if(l.updateItem(t) > 0)
			System.out.println("수정되었습니다");
	}
	
	
	
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
			System.out.println("총 " + count + "개의 항목을 찾았습니다");
				
	}
	
	
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
			System.out.println("총 " + count + "개의 항목을 찾았습니다");
	}
	
	
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}
	
	

	public static void listAll(TodoList l) {
		System.out.printf("[총 %d개의 목록]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.print(item.toString());
		}
	}
	
	
	
	public static void listAll(TodoList l, int comp) {
		int count = 0;
		for (TodoItem item : l.getList(comp)) {
			System.out.print(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 목록이 완료되었습니다\n", count);
	}
	
	
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[총 %d개의 목록]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.print(item.toString());
		}
	}
	
	public static void completeItem(TodoList l, int index) {
		if(l.completeItem(index) > 0)
				System.out.println("완료 체크하였습니다");
		
	}
	
	
	/* 
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();	
			System.out.println("모든 데이터가 저장되었습니다");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			int count = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();				
				String current_date = st.nextToken();
				TodoItem item = new TodoItem(title, description, category, due_date);
				item.setCurrent_date(current_date);
				l.addItem(item);
				count++;
			}
			br.close();
			System.out.println(count + "개의 항목을 읽었습니다 ");
		} catch (FileNotFoundException e) {
			System.out.println(filename + " 파일이 없습니다 ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	*/
	
}
