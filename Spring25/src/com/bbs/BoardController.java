package com.bbs;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.util.MyUtil;
import com.util.dao.CommonDAO;

@Controller("bbs.boardController")
public class BoardController {
	
	/**
	 * 어노테이션으로 생성해준 객체들은
	 * @Resource 어노테이션으로 가지고 온다.
	 */
	
	@Resource(name="dao")
	private CommonDAO dao;
	
	@Resource(name="myUtil")
	private MyUtil myUtil;
	
	
	
	@RequestMapping(value="/bbs/created.action", method= {RequestMethod.GET, RequestMethod.POST})
	public String created(BoardCommand command, HttpServletRequest req) throws Exception {
		
		// 만약 /bbs/created.action에 처음 들어온 상태
		if(command==null || command.getMode()==null || command.getMode().equals("")) {
			req.setAttribute("mode", "insert");
			
			return "board/created";
		}
		
		/**
		 * /bbs/created.action 경로에 mode를 가지고 들어온 경우
		 * 		=> created.jsp에서 submit을 누른 경우
		 */	
		
		int numMax = dao.getIntValue("bbs.numMax");
		
		command.setNum(numMax + 1);
		command.setIpAddr(req.getRemoteAddr());
		
		dao.insertData("bbs.insertData", command);
		
		return "redirect:/bbs/list.action";
		
	}
	
	@RequestMapping(value="/bbs/list.action", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(HttpServletRequest req,HttpSession session) throws Exception {
		
		String cp = req.getContextPath();
		int numPerPage = 5;
		int totalPage = 0;
		int dataCount = 0;
		
		int currentPage = 1;
		String pageNum = req.getParameter("pageNum");
		
		//수정, 삭제로 넘어오는 pageNum은 redirect를 거치기 때문에 세션에 올려놓을 것
		/*if(pageNum==null || pageNum.equals("")) {
			pageNum = (String)session.getAttribute("pageNum");
		}*/
		
		//pageNum이 있으면 currentPage로 받아준다.
		if(pageNum!=null && !pageNum.equals("")) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		
		// 검색기능
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		
		if(searchValue==null || searchValue.equals("")) {
			searchKey = "subject";
			searchValue = "";
		}
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			searchValue = URLDecoder.decode(searchValue,"UTF-8");
		}

		
		Map<String, Object> hMap = new HashMap<String, Object>();
		
		hMap.put("searchKey", searchKey);
		hMap.put("searchValue", searchValue);
		
		
		
		dataCount = dao.getIntValue("bbs.dataCount", hMap);
		
		if(dataCount!=0) {
			totalPage = myUtil.getPageCount(numPerPage, dataCount);
		}
		
		
		if(currentPage>totalPage) {
			currentPage = totalPage;
			// 마지막 페이지에서 게시글을 삭제 해 페이지가 존재하지 않게 될 경우를 대비
		}
		
		int start = (currentPage-1)*numPerPage+1;
		int end = currentPage*numPerPage;

		hMap.put("start", start);
		hMap.put("end", end);
		
		List<Object> lists = (List<Object>)dao.getListData("bbs.listData",hMap);
		
		int listNum, n=0;
		
		Iterator<Object> it = lists.iterator();
		while(it.hasNext()) {
			
			BoardCommand vo = (BoardCommand)it.next();
			listNum = dataCount - (start + n - 1);
			vo.setListNum(listNum);
			n++;
			
		}
		
		// 경로 정리
		String params="";
		String urlList = "";
		String urlArticle = "";
		
		if(!searchValue.equals("")) {
			//검색을 안 했을 경우
			searchValue = URLEncoder.encode(searchValue,"UTF-8");
			params = "searchKey=" + searchKey + "&searchValue=" + searchValue;
		}
		
		if(params.equals("")) {
			// 검색을 안한 것
			urlList = cp + "/bbs/list.action";
			urlArticle = cp + "/bbs/article.action?pageNum=" + currentPage;
		} else {
			urlList = cp + "/bbs/list.action?"+params;
			urlArticle = cp + "/bbs/article.action?pageNum=" + currentPage + "&" + params;
		}
		
		
		req.setAttribute("lists", lists);
		req.setAttribute("urlArticle", urlArticle);
		req.setAttribute("pageNum", currentPage);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, urlList));
		
		return "board/list"; 
	}
	

}















