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
	
	@RequestMapping(value="/bbs/article.action", method= {RequestMethod.GET, RequestMethod.POST})
	public String article(HttpServletRequest req) throws Exception {
		
		int num = Integer.parseInt(req.getParameter("num"));
		String pageNum = req.getParameter("pageNum");
		
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		
		if(searchValue==null) {
			//검색을 안 한 것
			searchKey = "subject";
			searchValue = "";
		}
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
		
		dao.updateData("bbs.updateHitCount", num); // 조회수 증가
		
		// num에 해당하는 게시글 읽어오기
		BoardCommand dto = (BoardCommand)dao.getReadData("bbs.readData",num);
		
		// 라인 수 구하기
		int lineSu = dto.getContent().split("\r\n").length;
		
		// \r\n을 <br>로 바꾸어주기
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br>"));
		
		
		/**
		 * 이전글, 다음글에 searchKey, searchValue, num 세 개의 값을 넘겨야 함
		 * 데이터를 넘길 hMap 생성
		 */

		Map<String, Object> hMap = new HashMap<>();
		
		hMap.put("searchKey", searchKey);
		hMap.put("searchValue", searchValue);
		hMap.put("num", num);
		
		
		/**
		 * 이전글, 다음글
		 */
		
		BoardCommand pre = (BoardCommand)dao.getReadData("bbs.preReadData",hMap);
		int preNum = 0;
		String preSubject = null;
	
		
		if(pre!=null) { //이전글, 다음글 데이터가 있을 때만 코드를 실행한다.
			preNum = pre.getNum();
			preSubject = pre.getSubject();
			System.out.println("[이전글]" + preSubject);
		}
		
		BoardCommand next = (BoardCommand)dao.getReadData("bbs.nextReadData",hMap);
		int nextNum = 0;
		String nextSubject = null;
		
		if(next!=null) {
			nextNum = next.getNum();
			nextSubject = next.getSubject();
			System.out.println("[다음글]" +nextSubject);
		}
		
		/**
		 * 주소 처리
		 */
		
		String params = "pageNum=" + pageNum;
		if(!searchValue.equals("")) {
			// 검색을 한 경우에는 검색값도 함께 넘겨준다.
			searchValue = URLEncoder.encode(searchValue, "UTF-8");
			params += "&searchKey=" + searchKey + "&searchValue=" + searchValue;
		}
		
		
		/**-------------------------------
		 * article.jsp에 데이터 넘기기
		 * -------------------------------
		 */
		
		req.setAttribute("dto", dto);
		req.setAttribute("preNum", preNum);
		req.setAttribute("preSubject", preSubject);
		req.setAttribute("nextNum", nextNum);
		req.setAttribute("nextSubject", nextSubject);
		req.setAttribute("params", params);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("lineSu", lineSu);
		
		return "board/article";
	}
	
	@RequestMapping(value="/bbs/updated.action", method= {RequestMethod.GET})
	public String updateForm(HttpServletRequest req) throws Exception {
		/**-----------------------------------------------------------------
		 * 수정 기능
		 * -----------------------------------------------------------------
		 * 수정 창을 띄울 때는 get방식으로 데이터가 넘어올 것이고,
		 * 	=> updateForm
		 * 실제로 DB에 update를 처리할 때는 Post방식으로 데이터가 넘어올 것.
		 * -----------------------------------------------------------------
		 */
		
		int num = Integer.parseInt(req.getParameter("num"));
		String pageNum = req.getParameter("pageNum");
		
		BoardCommand dto = (BoardCommand)dao.getReadData("bbs.readData",num);
		
		req.setAttribute("mode", "update");
			//created.jsp에 "update" 값을 가진 mode를 넘길 것 -> created.jsp를 수정창으로 쓸 것
			//created.jsp javaScript에서 mode에 따라 "submit"버튼을 누르면 이동할 경로를 나눠둠.
		req.setAttribute("dto", dto);
		req.setAttribute("pageNum", pageNum);
		
		return "board/created";
	}
	
	@RequestMapping(value="/bbs/updated.action", method= {RequestMethod.POST})
	public String updateSubmit(BoardCommand command, HttpServletRequest req) throws Exception {
		
		// 사용자가 Form에 입력한 데이터들이 매개변수 command에 알아서 들어온다.
		// <?> created.jsp에서 hidden으로 넘겨준 num,PageNum을 따로 command에 set 안해줘도 넘어가네..?
		// ㄴ Form 안에 요소 name을 num, pageNum으로 해서 지정해줬으니까... 당연함
		dao.updateData("bbs.updateData", command);
		
		return "redirect:/bbs/list.action?pageNum=" + command.getPageNum();
		
	}
	
	@RequestMapping(value="/bbs/deleted.action", method= {RequestMethod.GET})
	public String delete(BoardCommand command, HttpServletRequest req) throws Exception {
		// 삭제할 데이터의 num이 GET방식으로 넘어온다.
		
		int num = Integer.parseInt(req.getParameter("num"));
		String pageNum = req.getParameter("pageNum");
		
		dao.deleteData("bbs.deleteData",num);
		
		return "redirect:/bbs/list.action?pageNum=" + command.getPageNum();
		
	}
	
	
}















