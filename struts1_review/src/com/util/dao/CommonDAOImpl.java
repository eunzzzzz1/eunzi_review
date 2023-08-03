package com.util.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.util.sqlMap.SqlMapConfig;

public class CommonDAOImpl implements CommonDAO{
	
	private SqlMapClient sqlMap;
	
	public CommonDAOImpl() {
		
		this.sqlMap = SqlMapConfig.getSqlMapInstance();
		
	}
	
	
	public static CommonDAO getInstance() {
		return new CommonDAOImpl();
	}
	
	
	

	@Override
	public void insertData(String id, Object value) throws SQLException {
		
		
		try {
			sqlMap.startTransaction(); // 트랜젝션 시작
			sqlMap.insert(id, value); // insert
			sqlMap.commitTransaction(); // 커밋
		} catch (Exception e) {
			System.out.println("[insertData의 에러] " + e);
		} finally {
			sqlMap.endTransaction();
		}
	}

	@Override
	public int updateData(String id, Object value) throws SQLException {
		
		int result = 0;
		
		try {
			
			sqlMap.startTransaction();
			result = sqlMap.update(id, value);
			sqlMap.commitTransaction();
			
			
		} catch (Exception e) {
			System.out.println("[updateData의 에러] " + e);
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
			result = sqlMap.update(id, map);
			sqlMap.commitTransaction();
			
			
		} catch (Exception e) {
			System.out.println("[updateData의 에러] " + e);
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
			result = sqlMap.delete(id);
			sqlMap.commitTransaction();
			
			
		} catch (Exception e) {
			System.out.println("[deleteData의 에러] " + e);
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
			result = sqlMap.delete(id,value);
			sqlMap.commitTransaction();
			
			
		} catch (Exception e) {
			System.out.println("[deleteData의 에러] " + e);
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
			result = sqlMap.delete(id,map);
			sqlMap.commitTransaction();
			
			
		} catch (Exception e) {
			System.out.println("[deleteData의 에러] " + e);
		} finally {
			sqlMap.endTransaction();
		}
		
		return result;
	}

	@Override
	public int getIntData(String id) {
		
		try {
			
			return ((Integer)sqlMap.queryForObject(id)).intValue();
			
		} catch (Exception e) {
			System.out.println("[getIntData의 에러] " + e);
		} // select 문은 트랜젝션을 시작/종료 하지 않아도 된다!
		
		return 0;
		
	}

	@Override
	public int getIntData(String id, Object value) {
		
		try {
			
			return ((Integer)sqlMap.queryForObject(id, value)).intValue();
			
		} catch (Exception e) {
			System.out.println("[getIntData의 에러] " + e);
		} // select 문은 트랜젝션을 시작/종료 하지 않아도 된다!
		
		return 0;
		
	}

	@Override
	public int getIntData(String id, Map<String, Object> map) {
		
		try {
			
			return ((Integer)sqlMap.queryForObject(id, map)).intValue();
			
		} catch (Exception e) {
			System.out.println("[getIntData의 에러] " + e);
		} // select 문은 트랜젝션을 시작/종료 하지 않아도 된다!
		
		return 0;
		
	}

	@Override
	public Object getReadData(String id) {

		try {
			
			return sqlMap.queryForObject(id);
			
		} catch (Exception e) {
			System.out.println("[getReadData의 에러] " + e);
		}
		
		
		return null;
	}

	@Override
	public Object getReadData(String id, Object value) {

		try {
			
			return sqlMap.queryForObject(id,value);
			
		} catch (Exception e) {
			System.out.println("[getReadData의 에러] " + e);
		}
		
		
		return null;
	}

	@Override
	public Object getReadData(String id, Map<String, Object> map) {

		try {
			
			return sqlMap.queryForObject(id, map);
			
		} catch (Exception e) {
			System.out.println("[getReadData의 에러] " + e);
		}
		
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getListData(String id) {
		
		try {
			
			return (List<Object>)sqlMap.queryForList(id);
			
		} catch (Exception e) {
			System.out.println("[getListData의 에러] " + e);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getListData(String id, Object value) {
		
		try {
			
			return (List<Object>)sqlMap.queryForList(id, value);
			
		} catch (Exception e) {
			System.out.println("[getListData의 에러] " + e);
		}
		
		return null;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getListData(String id, Map<String, Object> map) {
		
		try {
			
			return (List<Object>)sqlMap.queryForList(id, map);
			
		} catch (Exception e) {
			System.out.println("[getListData의 에러] " + e);
		}
		
		return null;
	}
	

}
