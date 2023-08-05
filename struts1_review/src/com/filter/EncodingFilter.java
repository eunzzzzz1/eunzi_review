package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class EncodingFilter implements Filter{
	
	private String charset;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String uri;
		
		if(request instanceof HttpServletRequest) {
			
			HttpServletRequest req = (HttpServletRequest)request;
			
			uri = req.getRequestURI(); // 브라우저를 통해 요청된 경로
			
			//만약 가져오는 방식이 post방식이면
			if(req.getMethod().equalsIgnoreCase("POST")) {
				
				if(uri.indexOf("abcd.do")!=-1) {
					req.setCharacterEncoding("euc-kr");
				} else {
					req.setCharacterEncoding(charset);
				}
				
			}
			
		}
		
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		charset = filterConfig.getInitParameter("charset");
		
		if(charset==null) {
			charset = "UTF-8";
		}
		
	}

}
