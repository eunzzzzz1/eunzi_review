package com.util;

import org.springframework.stereotype.Service;

@Service("myUtil")
public class MyUtil {
	
	/**
	 * 전체 페이지 수를 구하는 메소드
	 * @param int numPerPage 한 페이지당 몇 개의 게시글을 불러올래?
	 * @param int dataCount 전체 데이터(게시글)의 개수가 몇 개야?
	 */
	public int getPageCount(int numPerPage, int dataCount) {
		
		int pageCount = 0;
		
		pageCount = dataCount / numPerPage;
		
		if(dataCount % numPerPage !=0) {
			pageCount++;
			// 만약 나머지가 0이 아니면 +1
		}
		
		return pageCount;
		
	}
	
	public String pageIndexList(int currentPage, int totalPage) {
		
		int numPerBlock = 5;
		int currentPageSetup; // 이전페이지
		
		int page;
		String strList = "";
		
		if(currentPage==0) {
			return "";
		}
		
		// 표시할 페이지 만들기
		currentPageSetup = (currentPage/numPerBlock)*numPerBlock;
		if(currentPage % numPerBlock == 0) {
			currentPageSetup = currentPageSetup - numPerBlock;
		}
		
		if(totalPage>numPerBlock && currentPageSetup>0) {
			strList
			= "<a onclick='listPage(" + currentPageSetup + ");'>◀이전</a>&nbsp;";
					//페이지를 함수가 만들게끔 할거야!
			
		}
		
		//바로가기 페이지
		page = currentPageSetup = 1;
		
		while((page<=totalPage)&&(page<=currentPageSetup+numPerBlock)) {
			
			if(page==currentPage) {
				strList += "<font color='Fuchsia'>" + page + "</font>&nbsp;";
			} else {
				strList += "<a onclick='listPage(" + page + ");'>" + page
						+ "</a>&nbsp;";
			}
			page++;
			
		}
		
		
		//이전 페이지 작업
		if(totalPage-currentPageSetup>numPerBlock) {
			strList
			+= "<a onclick='listPage(" + page + ");'>다음▶</a>&nbsp;";
		}
		
		return strList;
		
		// 페이징 처리를 listPage라는 자바스크립트 함수가 하게끔 나들거야.
		
		
	}


	/**
	 * 페이지 처리 메소드
	 * 
	 * @param int currentPage - 원하는 페이지번호
	 * @param int totalPage - 전체 페이지의 개수
	 * @param String listUrl - 내용을 뿌릴 파일의 경로
	 */
	public String pageIndexList(int currentPage, int totalPage, String listUrl) {
		
		int numPerBlock = 5; // 몇 개의 페이지목록을 표시할 건지 ◀이전 6 7 8 9 10 다음▶ = 5개
		int currentPageSetup; // 이전 페이지 (표시할 첫 페이지 -1 ) 
		int page; // for문의 i와 같은 역할
		
		StringBuffer sb = new StringBuffer();
		
		if(currentPage==0 || totalPage==0) {
			return "";
			// 만약 현재페이지가 0이고 전체페이지수가 0이면 메소드 실행 ㄴㄴ
		}
		
		if(listUrl.indexOf("?")!=-1) {
			
			// url 형태가 두 개가 될 거야
			// 단순히 list 페이지를 볼 때 = list.jsp
			// 검색을 통해 게시글을 불러올 때 = list.jsp?selectKey=subject&selectValue=수지
			
			// 만약 총 데이터 10개, 3개씩 보여줌, 2page를 보려면 - 총 페이지 4개 / currentPage = 2 
			// 만약 검색을 통해 나온 데이터가 6개, 2page를 보려면 - 총 페이지는 2개로 세팅됨 / urrentPage = 2 
			// 검색 후 게시글을 읽고, 다시 목록으로 나가서도 검색이 된 상태의 list가 보여야한다.
			// 그래서 이 검색된 데이터를 list에 가지고 들어가야함! list.jsp?selectKey=subject&selectValue=수지 처럼
			
			// 만약 list.jsp?selectKey=subject&selectValue=수지 형태의 주소를 가지고 들어왔다면
			// 2page를 보는 주소는 list.jsp?selectKey=subject&selectValue=수지&pageNum=2 가 되어야함.
			// 즉, 주소 뒤에 &이 붙어야한다.
			listUrl += "&";
			
		} else {
			
			// 만약 list.jsp 형태의 주소를 들고 들어왔다면
			// 2page를 보는 주소는 list.jsp?pageNum=2 형태가 되어야함
			// 즉, 주소 뒤에 ?가 붙어야한다.
			listUrl += "?";
			
		}
		
		// ◀이전 6 7 8 9 10 다음▶
		// 이전을 누르면 나와야하는 페이지번호 공식
		currentPageSetup = (currentPage/numPerBlock)*numPerBlock;
		
		// 나머지가 남으면 위 식이 잘 적용되지만
		// 나머지가 남지 않으면 자기자신을 가리키게 됨
		
		if(currentPage % numPerBlock == 0) {
			currentPageSetup = currentPageSetup - numPerBlock;
		}
		
		
		
		
		
		// ◀이전 버튼 눌렀을때 작동할 HTML
		
		if(totalPage > numPerBlock && currentPageSetup > 0) {
			sb.append("<a href=\"" + listUrl + "pageNum=" + currentPageSetup + "\">◀이전</a>&nbsp;");
		}
		// 만약 1 2 3 4 5 다음▶ 처럼
		// 1 ~ 5 페이지번호를 만든다면 currentPageSetup이 0일테고, 그렇다면 이전 버튼이 필요없음
		// 그래서 상단의 위 if문을 뛰어넘게 됨
		
		//<a href="?pageNum=5">◀이전</a>&nbsp;
		
		
		
		// 페이지번호 찍어주고, 거기 하이퍼링크 달기
		page = currentPageSetup + 1;
		while(page <= totalPage && page <= (currentPageSetup+numPerBlock)) {
			
			//◀이전 6 7 8 9 10 다음▶
			// page = currentPageSetup + 1; = 6
			// currentPageSetup은 5 + numPerBlock (페이지번호를 표시할 개수) 5 = 10
			// 6 ~ 10까지 찍는다.
			
			if(page==currentPage) {
				sb.append("<font color=\"Fuchsia\">" + page + "</font>&nbsp;");
				// 선택된 페이지라면
				//<font color="Fuchsia"> 8 </font>
			} else {
				
				// 선택된 페이지가 아니면
				sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">" + page + "</a>&nbsp;");
				// <a href="list.jsp?pageNum=7">7</a>&nbsp;
				
			}
			
			page++; // 페이지를 1씩 증가 
			
		}
		
		
		
		
		
		
		// ◀다음 버튼 눌렀을때 작동할 HTML
		//◀이전 6 7 8 9 10 다음▶
		//◀이전 11 12
		
		if(totalPage - currentPageSetup > numPerBlock) {
			// 12           10               5
		
			sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">다음▶</a>&nbsp;");
			//<a href="pageNum="2">다음▶</a>&nbsp;"
		
		}
		
		return sb.toString();
		
		
	}
	
}