package com.util.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;



public interface CommonDAO {
	
	// 데이터 추가 메소드
	public void insertData(String id, Object value) throws SQLException;

	//데이터 수정 메소드
	public int updateData(String id, Object value) throws SQLException;
	public int updateData(String id, Map<String, Object> map) throws SQLException;
	
	// 데이터 삭제
	public int deleteData(String id) throws SQLException;
	public int deleteData(String id, Object value) throws SQLException;
	public int deleteData(String id, Map<String,Object> map) throws SQLException;
	
	// 한 개의 데이터 가져오기
	public Object getReadData(String id); // select기 때문에 에러처리 안해줘도 된다.
	public Object getReadData(String id, Object value); // select기 때문에 에러처리 안해줘도 된다.
	public Object getReadData(String id, Map<String, Object> map); // select기 때문에 에러처리 안해줘도 된다.
	// 다 쓰진 않지만 모든 경우의 수를 생각해 여러 개를 만들어 두는 것
	
	
	
	// 한 개의 데이터(숫자) 가져오는 메소드 ex)getMaxNum
	public int getIntValue(String id);
	public int getIntValue(String id, Object value);
	public int getIntValue(String id, Map<String, Object> map);
	
	
	// 전체 데이터 가져오는 메소드
	public List<Object> getListData(String id);
	public List<Object> getListData(String id, Object value);
	public List<Object> getListData(String id, Map<String, Object> map);
	
	
	// 앞으로 iBatis의 CommonDAO 인터페이스에서 필요한 메소드들을 골라서 Implement해 쓴다.
	// 메소드의 이름은 사용자 정의!
	
}
