package com.file;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.util.FileManager;
import com.util.Pageing;
import com.util.dao.CommonDAO;
import com.util.dao.CommonDAOImpl;

public class FileAction extends DispatchAction{
	
	/**
	 * 파일 업로드 페이지 호출
	 */
	public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		
		
		return mapping.findForward("upload");
	}

	/**
	 * 파일 업로드 처리 메소드
	 */
	public ActionForward uploading(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		
		CommonDAO dao = CommonDAOImpl.getInstance();
		
		/**
		 * 순서 정리
		 * ----------------------------------------
		 * 1. 파일을 업로드 할 위치를 설정하기 String path로.
		 * 2. 매개변수로 들어온 form을 fileForm으로 다운캐스팅하고
		 * 3. 거기서 file타입의 인풋요소를 담는 변수 fileUpload를 get 해온다.
		 * 		= 얘가 FormFile 타입의 객체로 가져와질거임
		 * 4. 얘를 금방 만든 fileManager의 doFileUpload 메소드에 넣는다.
		 * 5. 그럼 파일은 업로드가 되고, 성공하면 newFileName을 나에게 돌려줄 것
		 * 6. 만약 newFileName이 null이 아니면, 파일업로드가 잘 된거니까
		 * 	  DB에도 정보를 저장하자.
		 * 		1) num을 세팅해주고
		 * 		2) saveFileName을 newFileName으로 설정
		 * 		3) originalFileName은 FormFile의 객체 upload에서 얻는다.
		 * `	4) insert 쿼리를 활용해 데이터를 넣어준다.
		 * ----------------------------------------
		 */

		
		
		
		//1. 파일을 업로드할 위치를 설정해준다.
		HttpSession session = req.getSession();
		String root = session.getServletContext().getRealPath("/");
		String path = root + "pds" + File.separator + "saveFileRe";
		
		//2. 매개변수로 들어온 form을 다운캐스팅 해주기.
		FileForm fileForm = (FileForm)form;
		
		//3. newFileName 얻기
		String newFileName = FileManager.doFileUpload(fileForm.getFileUpload(), path);
		
		//4. DB에 넣기
		if(fileForm.getSubject()==null || fileForm.getSubject().equals("") || newFileName==null) {
			
			return mapping.findForward("filelist");
			// 추가. 만일 파일 업로드를 못했다면 그냥 리스트로 돌아가도록.
			
		} else if (newFileName!=null) {
			
			int maxNum = dao.getIntData("fileRe.maxNum");
			fileForm.setNum(maxNum+1);
			fileForm.setSaveFileName(newFileName);
			fileForm.setOriginalFileName(fileForm.getFileUpload().getFileName());
			
			dao.insertData("fileRe.insertFile", fileForm);
			
		}
		
		return mapping.findForward("uploading");
	}
	
	
	public ActionForward filelist(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		
		CommonDAO dao = CommonDAOImpl.getInstance();
		/**----------------------------------------
		 * 순서 정리
		 * ----------------------------------------
		 * 1. 페이징 처리
		 * 2. 리스트 뽑기
		 * 3. 일련번호 정리하기
		 * 4. 데이터 넘기기
		 * ----------------------------------------
		 */
		
		//1. 페이징 처리하기
		Pageing pageing = new Pageing();
		
		int dataCount = dao.getIntData("fileRe.dataCount");
		int numPerPage = 3;

			// dataCount 쿼리를 사용해 총 게시글 개수를 가지고오기
		int totalPage = pageing.getPageCount(numPerPage, dataCount);
		
			// list에서 pageNum 넘겨받고, 만약 pageNum이 null이 아니면 currentPagep에 넣어주기.
		int currentPage = 1;
		String pageNum = req.getParameter("pageNum");
				// - pageIndexList에서 번호를 클릭했다면, ?pageNum=2 방식으로 주소가 변경되어 get방식으로 넘어올 것.
		
		if(pageNum!=null && !pageNum.equals("")) {
			currentPage = Integer.parseInt(pageNum);
		}
		
			// request에서 현재 url 뽑아오기
		String cp = req.getContextPath();
		String listUrl = cp + "/reFile.do?method=filelist";
		String pageIndexList = pageing.pageIndexList(currentPage, totalPage, listUrl);
	
		
		
		
		
		//2. 리스트 처리하기
		
			// 시작번호와 끝 번호 구하기
		int start = numPerPage*(currentPage-1)+1;
				// 현재 페이지가 3페이지면, 3페이지의 시작은
				// 2페이지 * numPerPage + 1
		int end = numPerPage*currentPage;
				// 현재 페이지가 3페이지면, 3페이지의 끝 게시물은
				// 3페이지 * numPerPage
		
			// 이 둘을 map에 담아준다.
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap.put("start", start);
		hMap.put("end", end);
		
		
		// 전체 게시글의 정보를 FileForm에 담아 List로 들고오기
		List<Object> lists = dao.getListData("fileRe.fileList", hMap);
		
		
		/**
		 * 3. 일련번호 정리하기
		 * ----------------------------------------------------
		 * 가장 처음 등록된 데이터의 번호는 1이 될 것
		 * 가장 나중에 등록된 데이터의 번호는 dataCount와 같을 것
		 * 
		 * dataCount - 0 (0 = start - 1)
		 * dataCount - 1 (1 = start - 1 + 1)
		 * dataCount - 2 (2 = start - 1 + 2)
		 * --------------
		 * dataCount - 3 (3 = start - 1 + 3)
		 * dataCount - 4 ...
		 * dataCount - 5
		 * -------------
		 * 
		 * => 일련번호는 dataCount - (start - 1 + n) (n은 0부터 1씩 커짐)
		 * ----------------------------------------------------
		 */
		
		int listNum = 0;
		int n = 0;
		String fileDownloadUrl = "";
		Iterator<Object> it = lists.iterator();
		while(it.hasNext()) {
			
			FileForm dto = (FileForm)it.next();
			listNum = dataCount - (start - 1 + n);
			dto.setListNum(listNum);
			n++;
			
			//다운로드 Url도 함께 설정해주자
			fileDownloadUrl = cp + "/reFile.do?method=download&num=" + dto.getNum();
			dto.setFileDownloadUrl(fileDownloadUrl);
			
		}
		
		req.setAttribute("lists", lists);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("pageIndexList", pageIndexList);
		
		
		return mapping.findForward("filelist");
	}

	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		
		/**
		 * 순서 정리
		 * -----------------------------
		 * 1. list에서 파일 명을 누르면 이동하는 링크에서 get방식으로 num을 받는다.
		 * 2. 그 num을 dao.getReadData에 넘겨 해당 num의 데이터 하나를 읽어온다.
		 * 3. FileForm객체로 받아낸다
		 * 4. FileManager.doFileDownload에 필요한 매개변수를 뽑아낸다.
		 * 		- saveFileName
		 * 		- originalFileName
		 * 		- path는 파일 업로드 시 사용했던 경로
		 * 5. return mapping을 ""로 주어 다른 곳으로 포워딩되지 않도록 한다.
		 */
		
		CommonDAO dao = CommonDAOImpl.getInstance();
		
		int num = Integer.parseInt(req.getParameter("num"));
		FileForm fileForm = (FileForm)dao.getReadData("fileRe.fileData", num);
		
		
		// 경로
		HttpSession session = req.getSession();
		String root = session.getServletContext().getRealPath("/");
		String path = root + "pds" + File.separator + "saveFileRe";
		
		// 매개변수
		String saveFileName = fileForm.getSaveFileName();
		String originalFileName = fileForm.getOriginalFileName();
		
		
		boolean flag
			= FileManager.doFileDownload(resp, saveFileName, originalFileName, path);
		
		// 만약 flag가 false면, 경고창 띄우기!
		if(!flag) {
			resp.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = resp.getWriter();
			out.print("<script type='text/javascript'>");
			out.print("alert('파일 다운로드에 실패하셨습니다.')");
			out.print("history.back()");
			out.print("</script>");
		}
		
		return mapping.findForward("");
	}
	
	
	
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		
		return mapping.findForward("");
	}
	
}
