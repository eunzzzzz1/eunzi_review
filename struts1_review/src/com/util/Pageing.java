package com.util;

public class Pageing {
	/**
	 * 페이징 처리를 위한 객체 
	 * 메소드 1) 전체 페이지를 구하는 메소드 getPageCount
	 * 메소드 2) 페이징 처리를 위한 페이지인덱스를 반환하는 메소드 getPageCount
	 */

	/**
	 * 전체 페이지 수를 반환하는 메소드
	 * @param numPerPage 한 페이지 당 몇 개의 게시글을?
	 * @param dataCount 게시글의 총 개수
	 * @return pageCount 페이지의 개수
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
	 
	 
	 /**
	  * 페이지 인덱스 html을 반환하는 메소드
	  * @param currentPage
	  * @param totalPage
	  * @param listUrl
	  * @return 페이지 인덱스 html
	  */
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
		 
		/** -----------------------------------
		 * Url 처리하기
		 * -----------------------------------
		 * 각 페이지마다 달려야할 하이퍼링크의 모습은
		 * 	검색을 안 했을 경우 : "~/list.do?pageNum=5"
		 * 	검색을 했을 경우
		 * 	: "~/list.do?searchKey=subject&searchValue=aaa&pageNum=5"
		 * 
		 * 즉 listUrl에
		 * 	?가 없으면 => ?pageNum=5를
		 * 	?가 있으면 => &pageNum=5를
		 * 붙여준다.
		 * 
		 * .indexOf("?")에서 ?가 없으면 -1이 뜨는 성질을 이용.
		 * -----------------------------------
		 */
		 
		
		if(listUrl.indexOf("?")!=-1) {
			// url에 ?가 있을 경우
			listUrl += "&";
		} else {
			// url에 ?가 없을 경우
			listUrl += "?";
		}
		 
		 
		 
		 
		/** -----------------------------------
		 * "◀이전" 처리하기
		 * -----------------------------------
		 */

	 	/** --------------------------------------------
	 	 * 1. "◀이전"을 누르면 나와야하는 페이지 번호
	 	 *  --------------------------------------------
	 	 *  (currentPage / numPerBlock)
	 	 *  => '◀이전' 페이지가 몇 번째 블럭에 속하는지 알 수 있다.
	 	 *  현재 9번 페이지면,
	 	 *  	'◀이전' 을 눌렀을 때는
	 	 *  	(1) 1 2 3 4 5 / (2) 6 7 8 9 10
	 	 *  	 (1)번째 블럭이 나와야한다.
	 	 *  
	 	 *  *numPerBlock
	 	 *  => 해당 블럭의 가장 마지막 페이지 번호를 알 수 있다.
	 	 *  
	 	 *  하지만 현재 페이지가 0으로 딱 나눠떨어진다면
	 	 *  (currentPage/numPerBlock)*numPerBlock를 했을 경우
	 	 *  자기 자신을 가리키게 됨.
	 	 *  => numPerBlock을 한번 더 빼주어 전 블럭을 가리키게 해준다.
	 	 *   --------------------------------------------
	 	 */
		 prePage = (currentPage/numPerBlock)*numPerBlock;
		 
		 if(currentPage % numPerBlock == 0) {
			 prePage = prePage - numPerBlock;
		 }
		 
		 
		 /** --------------------------------------------
		  * 2. "◀이전" 에 하이퍼링크 달기
		  *  --------------------------------------------
		  *  1 ~ 5페이지가 있는 첫 번째 Block에서는
		  *  ◀이전 버튼이 뜰 필요가 없다는 것을 염두에 두고,
		  *  (즉, prePage가 0이면 이전버튼이 필요하지 않다.)
		  *  그 경우가 아닐 경우에만 StringBuffer에
		  *  "이전" 하이퍼링크가 담기도록 하자.
		  *  --------------------------------------------
		  */
		  
		 if(totalPage > numPerBlock && prePage > 0) {
			 /**
			  *  전체 페이지 수가 numPerBlock으로 설정된 5보다 커야
			  *  "◀이전" 버튼이 의미가 있다.
			  *  
			  *  "◀이전"의 html 모습
			  *  <a href="list.do?pageNum=0">◀이전</a>
			  *  
			  */
			 
			 sb.append("<a href=\"" + listUrl + "pageNum=" + prePage + "\">◀이전</a>");
		 
		 }
		 
		 /** --------------------------------------------
		  * 3. 번호별로 하이퍼링크 달기
		  *  --------------------------------------------
		  *  반복문을 이용할 것
		  *  	- page가 numPerBlock만큼만 반복되도록 할 것
		  *  	- page가 totalPage가 될 때까지 반복
		  * 
		  *  선택된 페이지는 Fuchsia 색과 함께 진한 표시가 뜨게 할 것
		  *  선택되지 않은 페이지는 해당 pageNum으로 하이퍼링크를 걸 것
		  *  
		  *  선택된 페이지의 html
		  *  	<font color="Fuchsia"><b>1</b></font>&nbsp;
		  *  선택되지 않은 페이지의 html
		  *  	<a href="list.do?pageNum=2">2</a>&nbsp;
		  *  
		  *  시작하는 page는 prePage + 1
		  */  

		 page = prePage +1;
		 while(page <= (prePage + numPerBlock) && page <= totalPage) {
			 
			 if(page == currentPage) {
				 sb.append("<font color=\"Fuchsia\"><b>"+ page + "</b></font>&nbsp;");
			 } else {
				 sb.append("<a href=\""+ listUrl + "pageNum=" + page + "\">" + page + "</a>&nbsp;");
			 }
			
			 page++; 
		 }
		
		 /** --------------------------------------------
		  * 4. "다음▶" 에 하이퍼링크 달기
		  *  --------------------------------------------
		  *  마지막 페이지를 포함한 Block에는 "다음▶"이 필요 없음.
		  *  즉, 남은 페이지 수가 numPerBlock보다 많으면 필요하다.
		  *  
		  *  html
		  *  <a href="list.do?pageNum=12">12</a>
		  *  --------------------------------------------
		  */
		 
		 if(totalPage - currentPage > numPerBlock) {
			 
			sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">다음▶</a>");
			 
		 }
		 
		 return sb.toString();
	 }
	 
	 
	 
	 
}
