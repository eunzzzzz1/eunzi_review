package com.file;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.util.FileManager;
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
		
		return mapping.findForward("filelist");
	}

	
	
}
