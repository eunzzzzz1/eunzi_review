package com.util.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;
//import com.util.sqlMap.sqlMapConfig;

public class CommonDAOImpl implements CommonDAO{

	// 의존성 주입
	private SqlMapClient sqlMap; // 얘가 필수
	
	public CommonDAOImpl() {
		//this.sqlMap = sqlMapConfig.getSqlMapInstance();// sqlMap 호출
		// 객체만 생성하면 자동적으로 호출되게 된다.
	}
	
	public static CommonDAO getInstance() {
		return new CommonDAOImpl();
		// 외부에서 getInstance를 호출
		// CommonDAO dao = new CommondDAOImpl(); 이렇게 호출할 수 있게 된다.
	}
	
	
	@Override
	public void insertData(String id, Object value) throws SQLException {
		
		try {
			
			sqlMap.startTransaction();
				// 트렌젝션 시작. 
			sqlMap.insert(id,value); // 실제 인서트 문에 사람들에게 받은 id,value를 넘기고
			sqlMap.commitTransaction(); // 커밋
			//xml에서 transactionManager 부분에서 commit을 하도록 하지 않았기 때문에(false) 커밋을 해줘야한다.
			
		} catch (Exception e) {
			System.out.println("insertData의 에러 : " + e);
		} finally {
			sqlMap.endTransaction(); //트랜젝션 해줬으면 끝내줘야한다.
			//iBatis는 이런 세팅을 일일히 해줘야함
			//이렇게 한 번 만들어두고 매 프로젝트마다 복사해서 써주면 된다.
		}
		
	}

	@Override
	public int updateData(String id, Object value) throws SQLException {
		
		int result = 0;
		
		try {
			sqlMap.startTransaction();
			// 트렌젝션 시작. 
			result = sqlMap.update(id,value); // 실제 인서트 문에 사람들에게 받은 id,value를 넘기고
			//update는 반환값이 있다.
			sqlMap.commitTransaction(); // 커밋
		//xml에서 transactionManager 부분에서 commit을 하도록 하지 않았기 때문에(false) 커밋을 해줘야한다.
		} catch (Exception e) {
			System.out.println("updateData의 에러 : " + e);
		} finally {
			 sqlMap.endTransaction();
		}
		return result;
	}

	@Override
	public int updateData(String id, Map<String, Object> map) throws SQLException {
		
		int result = 0;
		
		try {
			sqlMap.startTransaction();
			// 트렌젝션 시작. 
			result = sqlMap.update(id,map); // 실제 인서트 문에 사람들에게 받은 id,value를 넘기고
			//update는 반환값이 있다.
			sqlMap.commitTransaction(); // 커밋
			//xml에서 transactionManager 부분에서 commit을 하도록 하지 않았기 때문에(false) 커밋을 해줘야한다.
		} catch (Exception e) {
			System.out.println("updateData의 에러 : " + e);
		} finally {
			 sqlMap.endTransaction();
		}
		
		return result;
	}

	@Override
	public int deleteData(String id) throws SQLException {

		int result = 0;
		
		try {
			sqlMap.startTransaction();
			// 트렌젝션 시작. 
			result = sqlMap.delete(id); // 실제 인서트 문에 사람들에게 받은 id,value를 넘기고
			//update는 반환값이 있다.
			sqlMap.commitTransaction(); // 커밋
			//xml에서 transactionManager 부분에서 commit을 하도록 하지 않았기 때문에(false) 커밋을 해줘야한다.
		} catch (Exception e) {
			System.out.println("deleteData의 에러 : " + e);
		} finally {
			 sqlMap.endTransaction();
		}
		
		return result;
	}

	@Override
	public int deleteData(String id, Object value) throws SQLException {

		int result = 0;
		
		try {
			sqlMap.startTransaction();
			// 트렌젝션 시작. 
			result = sqlMap.delete(id,value); // 실제 인서트 문에 사람들에게 받은 id,value를 넘기고
			//update는 반환값이 있다.
			sqlMap.commitTransaction(); // 커밋
			//xml에서 transactionManager 부분에서 commit을 하도록 하지 않았기 때문에(false) 커밋을 해줘야한다.
		} catch (Exception e) {
			System.out.println("deleteData의 에러 : " + e);
		} finally {
			 sqlMap.endTransaction();
		}
		
		return result;
	}

	@Override
	public int deleteData(String id, Map<String, Object> map) throws SQLException {

		int result = 0;
		
		try {
			sqlMap.startTransaction();
			// 트렌젝션 시작. 
			result = sqlMap.delete(id,map); // 실제 인서트 문에 사람들에게 받은 id,value를 넘기고
			//update는 반환값이 있다.
			sqlMap.commitTransaction(); // 커밋
			//xml에서 transactionManager 부분에서 commit을 하도록 하지 않았기 때문에(false) 커밋을 해줘야한다.
		} catch (Exception e) {
			System.out.println("deleteData의 에러 : " + e);
		} finally {
			 sqlMap.endTransaction();
		}
		
		return result;
	}

	@Override
	public Object getReadData(String id) {
		//select
		
		try {
			
			return sqlMap.queryForObject(id);
			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return null;
		
	}

	@Override
	public Object getReadData(String id, Object value) {
		
		try {
			
			return sqlMap.queryForObject(id,value);
			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return null;
		
	}

	@Override
	public Object getReadData(String id, Map<String, Object> map) {
		
		try {
			
			return sqlMap.queryForObject(id,map);
			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return null;
		
	}

	@Override
	public int getIntValue(String id) {
		
		try {
			
			return ((Integer)sqlMap.queryForObject(id)).intValue();
			// 쿼리의 int 값을 반환
			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return 0;
	}

	@Override
	public int getIntValue(String id, Object value) {
		
		try {
			
			return ((Integer)sqlMap.queryForObject(id,value)).intValue();
			// 쿼리의 int 값을 반환
			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return 0;
	}

	@Override
	public int getIntValue(String id, Map<String, Object> map) {
		
		try {
			
			return ((Integer)sqlMap.queryForObject(id,map)).intValue();
			// 쿼리의 int 값을 반환
			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getListData(String id) {
			
		try {
			
			return (List<Object>)sqlMap.queryForList(id);
			// 쿼리의 int 값을 반환
			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return null;
		
	}

	@Override
	public List<Object> getListData(String id, Object value) {
		
		try {
			
			return (List<Object>)sqlMap.queryForList(id,value);
			// 쿼리의 int 값을 반환
			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return null;
		
	}

	@Override
	public List<Object> getListData(String id, Map<String, Object> map) {
		
		try {
			
			return (List<Object>)sqlMap.queryForList(id,map);
			// 쿼리의 int 값을 반환
			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return null;
		
	}

}
