package com.file;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.util.FileManager;
import com.util.MyUtil;
import com.util.dao.CommonDAO;

@Controller("fileTest.fileController")
public class FileController {
	
	@Resource(name="dao")
	private CommonDAO dao;
	
	@Resource(name="myUtil")
	private MyUtil myUtil;
	
	@Resource(name="fileManager")
	private FileManager fileManager;
	
	@RequestMapping(value="/fileTest/list.action", method= {RequestMethod.GET})
	public String fileList(HttpServletRequest req) throws Exception {
		
		int numPerPage = 5;
		int currentPage = 1;
		
		String pageNum = req.getParameter("pageNum"); // get방식으로 넘어올 pageNum
		
		if(pageNum!=null && !pageNum.equals("")) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		int start = (numPerPage)*(currentPage-1)+1;
		int end = (numPerPage)*(currentPage);
		
		Map<String, Object> hMap = new HashMap<String, Object>();
		
		hMap.put("start", start);
		hMap.put("end", end);
		
		
		List<Object> lists = (List<Object>)dao.getListData("fileTest.listData", hMap);
		Iterator<Object> it = lists.iterator();
		
		String cp = req.getContextPath();
		
		int listNum, n=0;
		
		int dataCount = dao.getIntValue("fileTest.dataCount");
		while(it.hasNext()) {
			
			FileCommand dto = (FileCommand)it.next();
			listNum = dataCount - (start - 1 + n);
			dto.setListNum(listNum);
			
			dto.setDownloadUrl( cp + "/fileTest/download.action?num=" + dto.getNum());
			n++;
		}
		
		
		
		int totalPage = dataCount / numPerPage + 1;
		
		if(dataCount % numPerPage == 0) {
			totalPage = totalPage - 1;
		}
		
		
		String listUrl = cp + "/fileTest/list.action";
		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		
		req.setAttribute("lists", lists);
		req.setAttribute("pageIndexList", pageIndexList);
		req.setAttribute("dataCount", dataCount);
		
		return "file/list";
	}
	
	
	
	
	@RequestMapping(value="/fileTest/upload.action", method= {RequestMethod.GET})
	public String uploadForm() throws Exception {
		
			return "file/write";

	}
	
	@RequestMapping(value="/fileTest/upload.action", method= {RequestMethod.POST})
	public String uploading(FileCommand command, MultipartHttpServletRequest req, HttpSession session) throws Exception {
		
		
		// 여기까지 넘어온 거면 mode가 upload로 채워져있다는 뜻
		
		/**----------------------------------------------------------------
		 * 순서 정리
		 * ----------------------------------------------------------------
		 * 1. 파일 업로드
		 * 2. DB 기재
		 * ----------------------------------------------------------------
		 */
		
		/**----------------------------------------------------------------
		 * 파일 업로드하기
		 * ----------------------------------------------------------------
		 * 1. 파일이 저장될 path 가져오기
		 * 2. path대로 실제 경로 생성하기
		 * 3. MultipartFile를 이용해서 req객체에 담긴 파일을 가지고 오기.
		 * 		- Form안에서 input요소의 name속성에 준 값을 매개변수로 전달
		 * 4. MultipartFile 객체에서 InputStream을 얻어오기
		 * 5. FileManage.doFileUpload를 활용해 위 객체들을 매개변수로 전달,
		 * 6. 반환값으로 newFileName 받기
		 * ----------------------------------------------------------------
		 */
		
		
		String root = session.getServletContext().getRealPath("/");
		String path = root + "pds" + File.separator + "saveSpringFile";
		
//		File file = new File(path);
//		
//		if(!file.exists()) {
//			file.mkdirs();
//			// 경로가 실제로 존재하지 않으면 만든다.
//		} 여기서는 이게 필요없다.
		
		MultipartFile mpFile = req.getFile("fileUpload");
		InputStream is = mpFile.getInputStream();
		
		String originalFileName = mpFile.getOriginalFilename();
		String newFileName = FileManager.doFileUpload(is, originalFileName, path);
		
		
		/**----------------------------------------------------------------
		 * DB에 파일 기재하기
		 * ----------------------------------------------------------------
		 * 1. originalFileName과 saveFileName을 command에 세팅해주기
		 * 2. maxNum을 DAO를 통해 구하고, num도 command에 세팅하기
		 * 		subject는 command에 딸려왔을 것
		 * 2. DAO를 이용해 DB에 기재하기
		 * ----------------------------------------------------------------
		 */
		
		command.setOriginalFileName(originalFileName);
		command.setSaveFileName(newFileName);
		
		int num = dao.getIntValue("fileTest.maxNum") + 1;
		command.setNum(num);
		
		dao.insertData("fileTest.uploadData", command);
		
		return "redirect:/fileTest/list.action";
	}
	
	@RequestMapping(value="/fileTest/download.action", method= {RequestMethod.GET})
	public void download(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {
		
		int num = Integer.parseInt(req.getParameter("num"));
		
		String root = session.getServletContext().getRealPath("/");
		String path = root + "pds" + File.separator + "saveSpringFile";
		
		// 데이터 한 개 읽어오는 쿼리 사용
		
		
		FileCommand command = (FileCommand)dao.getReadData("fileTest.readData",num);
		
		String saveFileName = command.getSaveFileName();
		String originalFileName = command.getOriginalFileName();
		
		boolean flag = FileManager.doFileDownload(resp, saveFileName, originalFileName, path);
		
		if(!flag) {
			resp.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = resp.getWriter();
			out.print("<script type='text/javascript'>");
			out.print("alert('파일 다운로드에 실패하셨습니다.')");
			out.print("history.back()");
			out.print("</script>");
		}
		
		
	}
	
	@RequestMapping(value="/fileTest/delete.action", method= {RequestMethod.GET})
	public String delete(HttpServletRequest req, HttpSession session) throws Exception {
		
		/**
		 * 1. 파일 삭제
		 * 2. DB 삭제
		 */
		
		int num = Integer.parseInt(req.getParameter("num"));
		
		//파일 삭제하기
		

		FileCommand command = (FileCommand)dao.getReadData("fileTest.readData",num);
		
		String root = session.getServletContext().getRealPath("/");
		String path = root + "pds" + File.separator + "saveSpringFile";
		
		String fileName = command.getSaveFileName();
		
		FileManager.doFileDelete(fileName, path);
		
		
		// DB 삭제하기
		dao.deleteData("fileTest.deleteData",num);
		
		
		return "redirect:/fileTest/list.action";
	}
	

}
