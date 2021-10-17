package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<ToDoList 앱 명령어 사용법>");
        System.out.println("add - 항목 추가");
        System.out.println("del - 항목 삭제");
        System.out.println("edit - 항목 수정");
        System.out.println("ls - 전체 목록");
        System.out.println("ls_name - 제목순 정렬");
        System.out.println("ls_name_desc - 제목역순 정렬");
        System.out.println("ls_date - 날짜순 정렬");
        System.out.println("ls_date_desc - 날짜역순 정렬");
        System.out.println("find <검색어> - 항목 검색");
        System.out.println("find_cate <검색어> - 항목 검색");
        System.out.println("ls_cate - 카테고리 목록");
        System.out.println("comp <번호> (다중항목일 경우 , 사용) - 완료된 목록 체크");
        System.out.println("ls_comp - 완료된 목록 ");
        System.out.println("set_priority <번호> (default: 0) - 우선순위 매기기");
        System.out.println("ls_priority - 우선순위 순서대로 나열");
        System.out.println("ls_prioriy_desc - 우선순위 역순으로 나열");
        System.out.println("ongoing <번호> - 진행중인 목록 체크 ");
        System.out.println("ls_ongoing - 진행중인 목록 나열 ");
        System.out.println("help - menu 나열");
        System.out.println("exit - 종료");
    }
    
    public static void prompt()
    {
    	System.out.print("\nCommand >> ");
    }
}
