package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	Connection conn;
	
	// 초기 데이터를 이전하기 위해 처음에만 실행
	/*
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date)" 
					+ " values (?,?,?,?,?);";
			int records = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date= st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				int count = pstmt.executeUpdate();
				if(count > 0) records++;
				pstmt.close();
			}
			System.out.println(records + " records read!");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	

	public TodoList() {
		this.conn = DbConnect.getConnection();
	}
	
	

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date)" 
				+ " values (?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	

	public int deleteItem(int index) {
		String sql = "delete from list where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);;
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	

	public int updateItem(TodoItem t) {
		String sql  = "update list set title=?, memo=?, category=?, current_date=?, due_date=?, is_completed=?, priority=?, ongoing=?"
				+ "where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 0);
			pstmt.setInt(8, 0);
			pstmt.setInt(9, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list  = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int priority = rs.getInt("priority");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setPriority(priority);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public ArrayList<TodoItem> getList(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		try{
			String sql = "SELECT * FROM list WHERE title like? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int priority = rs.getInt("priority");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setPriority(priority);
				t.setCurrent_date(current_date);
				list.add(t);
		}
		pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public ArrayList<TodoItem> getList(int comp){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try{
			String sql = "SELECT * FROM list WHERE is_completed = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int priority = rs.getInt("priority");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setPriority(priority);
				t.setCurrent_date(current_date);
				list.add(t);
		}
		pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public ArrayList<TodoItem> getOnList(int on){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try{
			String sql = "SELECT * FROM list WHERE ongoing = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int priority = rs.getInt("priority");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setPriority(priority);
				t.setCurrent_date(current_date);
				list.add(t);
		}
		pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String cate = rs.getString("category");
				list.add(cate);
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int priority = rs.getInt("priority");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setPriority(priority);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if(ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int priority = rs.getInt("priority");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setPriority(priority);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getPriorityList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby ;
			if(ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int priority = rs.getInt("priority");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setPriority(priority);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public ArrayList<TodoItem> deleteZere(ArrayList<TodoItem> l){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		ArrayList<TodoItem> zero = new ArrayList<TodoItem>();
		for(TodoItem item : l) {
			if(item.getPriority() != 0)
				list.add(item);
			else
				zero.add(item);
		}
		
		for(TodoItem t : zero)
			list.add(t);
		return list;
	}
	
	
	
	public int completeItem(int index) {
		PreparedStatement pstmt;
		String tit = getTitle(index) + "[V]";
		int count = 0;
		try {
			String sql  = "update list set is_completed=1, title=?, ongoing=?"
					+ "where id = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tit);
			pstmt.setInt(2, 0);
			pstmt.setInt(3, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	public int ongoingItem(int n) {
		PreparedStatement pstmt;
		String tit =  getTitle(n) + " [Ongoing]";
		int count = 0;
		try {
			String sql  = "update list set ongoing=1, is_completed=0, title=?"
					+ "where id = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tit);
			pstmt.setInt(2, n);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	public int setPriority(int num, int priority) {
		PreparedStatement pstmt;
		int count = 0;
		try {
			String sql = "update list set priority =?" + "where id = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, priority);
			pstmt.setInt(2, num);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	public String getTitle(int index) {
		String title = null;
		PreparedStatement pstmt;
		try {
			String sql = "SELECT title FROM list where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			title = rs.getString("title");
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		title = title.trim();
		title = title.replace("[Ongoing]", "");
		title = title.replace("[V]", "");
		title = title.trim();
		return title;
	}
	

	
	
	/*
	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public static void listAll(TodoList l) {
		System.out.println("\n"
				+ "inside list_All method\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.getTitle() + myitem.getDesc());
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}
	*/

	public Boolean isDuplicate(String title) {
		for (TodoItem item : getList()) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
}
