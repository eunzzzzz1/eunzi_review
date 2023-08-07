package com.util;

public class Paging {
	/**
	 * 페이징 처리를 위한 객체 
	 * 메소드 1) 전체 페이지를 구하는 메소드 getPageCount
	 * 메소드 2) 페이징 처리를 위한 페이지인덱스를 반환하는 메소드 getPageCount
	 */

	 public int getPageCount(int numPerPage, int dataCount) {
		 
		 int pageCount =0;
		 
		 pageCount = dataCount / numPerPage;
		 // 전체 데이터 수 / 한 페이지 당 들어갈 게시글의 개수
		 
		 
		 if(dataCount % numPerPage !=0) {
			 pageCount++;
		 // 만약 나머지가 남으면, 1페이지가 더 필요할 것이기 때문에
		 // 1을 더해준다.
		 
		 }
		 
		 return pageCount;
	 }
	 
	 public String pageIndexList(int currentPage, int totalPage, String listUrl) {
		 
		 StringBuffer sb = new StringBuffer();
		 
		 int numPerBlock = 5;
		 /** numPerBlock
		  * 페이지 인덱스에 페이지를 몇 개 표시할건지?
		  * 5개 = ◀이전 6 7 8 9 10 다음▶
		  */
		 int prePage; // 현재 페이지의 전 페이지
		 int page; // 페이지
		 
		 // 걸러주기
		 if(currentPage==0 || totalPage ==0) {
			 return "";
			 // 만약 현재 페이지가 0이거나, totalPage가 0이라면 메소드를 실행하지 않는다.
			 // 즉 등록된 데이터가 없으면 페이지인덱스가 표시되지 않도록
			 // ""을 돌려준다.
		 }
		 
		 
		 
		 
		 
		 
		 return sb.toString();
	 }
	 
	 
	 
	 
}
