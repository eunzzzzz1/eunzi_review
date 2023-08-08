package com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.sun.org.apache.xalan.internal.xsltc.trax.OutputSettings;

@Service("fileManager")
public class FileManager {
	
	
	/**
	 * 파일 업로드 메소드
	 * @param InputStream is Spring의 파일 정보는 InputStream에 담긴다.
	 * @param String originalFileName
	 * @param path 파일을 업로드 할 경로
	 */
	//public static String doFileUpload(File upload, String originalFileName, String path) throws Exception {
	public static String doFileUpload(InputStream is, String originalFileName, String path) throws Exception {
		
		//파일이 저장될 이름
		String newFileName = "";
		
		if(is==null) {
			//사용자가 파일을 업로드하지 않고 '업로드'를 눌렀을 경우
			return null;
		}
		
//		String originalFileName = upload.getFileName(); // 업로드한 파일에서 파일 이름을 추출한다.
		
		//extention 파일 확장자만 떼어냈다가
		// 새로운 파일 이름을 지은 뒤에 그 뒤에 그대로 확장자를 붙이기 위함
		//String fileExt = originalFileName.substring(originalFileName.lastIndexOf(".")); // 파일이름의 뒤에서부터 .을 찾아 파일이름의 끝까지 담아
		
//		if(fileExt==null || fileExt.equals("")) {
//			// 어떤 파일은 확장자 없이 업로드되기도 함. (맥은 이런 구조 ㅇㅇ)
//			
//			return null;
//			// 확장자없는파일이 들어오면 멈춰
//		}
		
		
		// 새로운 파일명 생성
		newFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
				//%tY%tm%td%tH%tM%ts 연월일시분초
				//Calendar.getInstance()를 6개 쓰기 번거롭기 때문에 1$를 활용
				
		
		newFileName += System.nanoTime(); //동시에 파일이 올라간 경우를 대비하기 위해 나노타임을 더 이어서 써줌
//		newFileName += fileExt; // 파일 확장자까지 붙여준다.
		
		
		// 업로드된 파일을 path에 올려주자.
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs(); // 경로가 없으면 그 경로를 만들어
		}
		
		//경로 뒤에 파일의 이름 붙여서 올리기
		String fullFilePath = path + File.separator + newFileName;
		
		
//		//Struts의 파일업로드 처리방법을 활용하자
////		byte[] data = upload.getFileData(); // 사용자가 업로드한 파일의 데이터를 data배열변수에 담음
//		
//		FileOutputStream fos = new FileOutputStream(fullFileName);
////		fos.write(data);
//		fos.close();
//		// 이 4줄이 Struts의 파일 업로드 방식..!
//		
//		
//		//DB에 파일에 대한 정보들을 저장하기위해,
//		// 반환값을 새로운 파일이름으로 해주기. (saveFileName에 저장될 이름임)
//
//		
//		return newFileName;
		
		
		//--struts2는 이전에 했던 기존 파일업로드 방법임 
		
/*		FileInputStream fis = new FileInputStream(upload);
		FileOutputStream fos = new FileOutputStream(fullFileName);
		
		int data = 0;
		byte[] buffer = new byte[1024];
		while((data=fis.read(buffer,0,1024))!=-1) {
			fos.write(buffer,0,data);
		}
		
		fis.close();
		fos.close();
*/
		
		/**
		 * Spring은 FileCopyUtils의 copy 메소드에 InputStream과 FileOutputStream 매개변수를 전해주면
		 * 파일 업로드 처리를 알아서 해준다...
		 */
		// 
		FileCopyUtils.copy(is, new FileOutputStream(fullFilePath));

		return newFileName;

	}


	/**
	 * 서버가 클라이언트에게 파일을 내려주는 메소드 <br>
	 * 파일 다운로드 기능 만들 때 사용
	 * @param response HttpServletResponse의 객체. 서버가 클라이언트에게 파일을 리스폰스 해주는것이기 때문에 필요
	 * @param saveFileName 서버에 저장된 파일의 이름
	 * @param originalFileName 실제 파일의 이름
	 * @param path 파일이 저장되어있는 경로
	 * @return
	 */
	public static boolean doFileDownload(HttpServletResponse response, String saveFileName, String originalFileName, String path) {
		
		try {
			
			String filePath = path + File.separator + saveFileName;
			System.out.println("[ FileManager ] " + filePath);
			
			if(originalFileName==null || originalFileName.equals("")) {
				originalFileName = saveFileName;
			}
			
			// 파일 이름이 한글일 때 한글이 깨지지 않게 인코딩처리
			originalFileName = new String(originalFileName.getBytes("euc-kr"),"ISO-8859-1");
			
			File f = new File(filePath);
			
			if(!f.exists()) {
				return false;
			} // 만약에 파일이 존재하지 않으면 false를 되돌려줌
		
			// 파일이 있으면
			response.setContentType("application/octet-stream");
				// 확장자명 앞에 있는 .을 octet이라고 함
				// 클라이언트에게 파일의 종류를 알려줌
			response.setHeader("Content-disposition", "attachment;fileName=" + originalFileName);
				// 클라이언트에게 헤더를 줌
				// 클라이언트는 어떤 파일인지 알게 됨
			
			
			// 파일 내보내기
				// 서버에 있는 파일을 메모리상에 읽어들여야한다.
				// fileInputStream으로 파일을 메모리상에 읽어들인 후
				// 버퍼에 넣고
				// 내려주면 됨
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(f));
			
			OutputStream out = response.getOutputStream();
			
			int data;
			byte bytes[] = new byte[4096];
			
			// bis에 있는 내용을 읽어내고 byte에 있는 내용을 0부터 4096까지 읽는다.
			// 데이터가 없을 때까지 반복
			while((data=bis.read())!=-1) {
				out.write(bytes,0,data);
			}
				
			out.flush(); // 버퍼가 꽉 차지 않았을 때 내보내는 메소드
			out.close(); // 버퍼 사용 후 닫아주기
			bis.close();
				
		} catch (Exception e) {
			System.out.println("[ FileManager doFileDownload의 에러 ]" + e);
			return false; // 에러 났을 때도 false값 돌려주기
		}
		
		return true; // 파일 다운 다 되면 true 돌려주기
		
		
		
	}
		
	
	/**
	 * 물리적인 파일을 삭제하는 메소드
	 * @param fileName 파일의 이름
	 * @param path 파일이 위치한 경로
	 */
	public static void doFileDelete(String fileName, String path) {
		
		try {
			
			
			String filePath = path + File.separator + fileName;
			
			File f = new File(filePath);
			
			if(f.exists()) {
				f.delete(); // 물리적 파일 삭제
			}
			
			
		} catch (Exception e) {
			System.out.println("[ FileManager doFileDelete의 에러 ]" + e);
		}
		
	}
	
}
