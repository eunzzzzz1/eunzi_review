package com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;

import org.apache.struts.upload.FormFile;

public class FileManager {
	
	/**
	 * 파일을 서버에 업로드하는 메소드
	 * @param upload Struts1의 파일 관리 클래스
	 * @param path 파일을 업로드 할 서버 경로
	 * @return newFileName 새로운 파일 이름
	 */
	public static String doFileUpload(FormFile upload, String path) throws Exception {
		
		String newFileName = ""; 
		
		if(upload==null || upload.getFileName()==null || upload.getFileName().equals("")) {
			return null;
		}
		
		
		/**------------------------------------------------
		 * 순서 정리
		 * ------------------------------------------------
		 * 1. 파일 이름 정해주기
		 * 	1) formfile에서 파일 이름 가지고와서
		 *  2) 확장자 슬쩍 떼어내고
		 *  3) newFileName에는 년월일시분초나노타임 넣고
		 *  4) 다시 확장자 붙이고
		 *  
		 * 2. 파일 업로드하기
		 *  1) 파일 객체 만들어서 path 넘겨준 뒤에
		 *  2) 경로가 존재하지 않으면 경로를 만들어주기
		 *  3) 그 경로에 Struts1 방식으로 업로드하기
		 *  -----------------------------------------------
		 */
		
		
		// 원래 이름 가지고 오기
		String originalFileName = upload.getFileName();
		String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		// 새로운 파일 이름 지어주기
		newFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
		newFileName += System.nanoTime();
		newFileName += fileExt;
		
		// 파일 업로드할 경로 만들어주기
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		//업로드해주기
		String fullFileName = path + File.separator + newFileName;
		FileOutputStream fos = new FileOutputStream(fullFileName);
		
		byte[] data = upload.getFileData();
		fos.write(data);
		fos.close();
		
		return newFileName;
		
	}
	
	/**
	 * 
	 * @param resp 서버->클라이언트 이기 때문에 Response 객체에 담아 보낸다.
	 * @param saveFileName 서버에 저장된 파일의 이름
	 * @param originalFileName 원 파일명
	 * @param path 서버에서 파일이 저장된 경로
	 * @return true 파일 다운로드 성공 시 반환
	 * @return false 파일 다운로드 실패 시 반환
	 */
	public static boolean doFileDownload(HttpServletResponse resp, String saveFileName, String originalFileName, String path) {
		
		/**--------------------------------------
		 * 순서 정리하기
		 * --------------------------------------
		 * 1. 파일 경로를 정리해준다. (서버에 저장된 파일의 경로)
		 * 2. originalFileName을 정돈해준다.
		 * 3. 파일 경로대로 File 객체를 생성해준다.
		 * 	- 파일 존재하지 않으면 false로 돌려주기.
		 * 4. resp 객체를 세팅해준다. (content type과 header 설정)
		 * 5. 파일을 내보낸다.
		 * --------------------------------------
		 */

		try {
			
		
		// 파일 경로 정리하기 
		String filePath = path + File.separator + saveFileName;
		
		// 원 파일명 정돈하기
		if(originalFileName==null || originalFileName.equals("")) {
			originalFileName = saveFileName;
		}
		
		originalFileName = new String(originalFileName.getBytes("euc-kr"),"UTF-8");
		
		
		// 파일 객체 생성하기
		File file = new File(filePath);
		if(!file.exists()) {
			return false;
		}
		
		// 파일 세팅해주기
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-dispositon", "attachment;fileName=" + originalFileName);
		
		// 파일 내려주기
		BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(file));
		
		int dataReadLength;
		byte bytes[] = new byte[4096];
		
		OutputStream out = resp.getOutputStream();
		
		while((dataReadLength=bis.read(bytes))!=-1) {
			out.write(bytes,0,dataReadLength);
		}
		
		out.flush();
		out.close();
		bis.close();
		
		
		
		} catch (Exception e) {
			System.out.println("[ FileManager doFileDownload의 에러 ]" + e);
			return false;
		}
		
		
		return true;
	}


	
}
