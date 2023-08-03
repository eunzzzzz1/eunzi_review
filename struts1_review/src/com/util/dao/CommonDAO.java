package com.util.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CommonDAO {
	// iBatis 모든 경우의 수대로 메소드 선언해주기
	
	// 데이터 추가
	public void insertData(String id, Object value) throws SQLException;
	
	// 데이터 수정
	public int updateData(String id, Object value) throws SQLException;
	public int updateData(String id, Map<String, Object> map) throws SQLException;
	
	// 데이터 삭제
	public int deleteData(String id) throws SQLException;
	public int deleteData(String id, Object value) throws SQLException;
	public int deleteData(String id, Map<String, Object> map) throws SQLException;
	
	// 숫자 데이터 한 개 가져오기
	public int getIntData(String id);
	public int getIntData(String id, Object value);
	public int getIntData(String id, Map<String,Object> map);
	
	// 데이터 한 행 가져오기
	public Object getReadData(String id);
	public Object getReadData(String id, Object value);
	public Object getReadData(String id, Map<String,Object> map);
	
	
	// 데이터 전체 행 가져오기
	public List<Object> getListData(String id);
	public List<Object> getListData(String id, Object value);
	public List<Object> getListData(String id, Map<String,Object> map);
	
}
