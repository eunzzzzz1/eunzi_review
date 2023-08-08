package com.util.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;



@Repository("dao")
public class CommonDAOImpl implements CommonDAO{
	
	/**------------------------------------------------------------------------------------
	 * Spring의 CommonDAOImpl
	 * ------------------------------------------------------------------------------------
	 * 1. Spring이 객체를 생성해주기 때문에 getInstance()로 객체를 생성해줄 필요가 없다.
	 * 		=> 즉, getInstance() 메소드가 필요없다.
	 * 
	 * 2. 객체 생성을 Spring의 어노테이션이 하기 때문에, 생성자가 따로 필요하지 않다.
	 * 
	 * 3. SqlMapClient의 객체가 아닌 SqlMapClientTemplate의 객체가 필요하다.
	 * 	  즉, private SqlMapClientTemplate sqlMapClientTemplate를 선언하고 의존성을 주입해 주어야한다.
	 * 	  이를 struts1, 2에서는 생성자를 통해 초기화 시켜주었는데,
	 * 	  Spring에서는 생성자를 쓰지 않기 때문에 다른 방법으로 의존성을 주입시켜주어야한다.
	 * 	  
	 *    * SqlMapClientTemplate 객체는
	 *    	'sqlMapClientTemplate' 라는 이름으로 applicationContext.xml에서 생성해주었다.
	 * 
	 * 		3-1 ) 메소드로 의존성 주입 시켜주기
	 * 			public void setSqlMapClientTemplate (SqlMapClientTemplate sqlMapClientTemplate) {
				this.sqlMapClientTemplate = sqlMapClientTemplate;
				}
	 * 
	 * 		3-2) 어노테이션으로 자동으로 의존성주입 시켜주기
	 * 			SqlMapClientTemplate 타입 변수에 @Autowired 를 사용하면 된다.
	 * ------------------------------------------------------------------------------------
	 */
	
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	
	@Override
	public void insertData(String id, Object value) throws SQLException {
		
		try {
			
			/**------------------------------------------------------------------------------------
			 * Spring의 트랜잭션 처리
			 * ------------------------------------------------------------------------------------
			 * 트랜잭션 처리는 applicationContext.xml의 transactionManager객체가 해주기 때문에
			 * sqlMap.startTransaction();과 sqlMap.commitTransaction();,
			 * 그리고 finally 절의 sqlMap.endTransaction();이 필요하지 않다.
			 * ------------------------------------------------------------------------------------
			 */

			sqlMapClientTemplate.insert(id,value);
			
			
		} catch (Exception e) {
			System.out.println("insertData의 에러 : " + e);
			
		}
		
	}

	@Override
	public int updateData(String id, Object value) throws SQLException {
		
		int result = 0;
		
		try {

			result = sqlMapClientTemplate.update(id,value);
			
		} catch (Exception e) {
			System.out.println("updateData의 에러 : " + e);
		}
		
		return result;
	}

	@Override
	public int updateData(String id, Map<String, Object> map) throws SQLException {
		
		int result = 0;
		
		try {
	
			result = sqlMapClientTemplate.update(id,map); 
			
		} catch (Exception e) {
			System.out.println("updateData의 에러 : " + e);
		}
		
		return result;
	}

	@Override
	public int deleteData(String id) throws SQLException {

		int result = 0;
		
		try {
			
			result = sqlMapClientTemplate.delete(id); 
			
		} catch (Exception e) {
			System.out.println("deleteData의 에러 : " + e);
		}
		
		return result;
	}

	@Override
	public int deleteData(String id, Object value) throws SQLException {

		int result = 0;
		
		try {
			
			result = sqlMapClientTemplate.delete(id,value);
			
		} catch (Exception e) {
			System.out.println("deleteData의 에러 : " + e);
		} 
		return result;
	}

	@Override
	public int deleteData(String id, Map<String, Object> map) throws SQLException {

		int result = 0;
		
		try {
			

			result = sqlMapClientTemplate.delete(id,map); 
			
		} catch (Exception e) {
			System.out.println("deleteData의 에러 : " + e);
		} 
		
		return result;
	}

	@Override
	public Object getReadData(String id) {
		//select
		
		try {
			
			return sqlMapClientTemplate.queryForObject(id);
			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return null;
		
	}

	@Override
	public Object getReadData(String id, Object value) {
		
		try {
			
			return sqlMapClientTemplate.queryForObject(id,value);
			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return null;
		
	}

	@Override
	public Object getReadData(String id, Map<String, Object> map) {
		
		try {
			
			return sqlMapClientTemplate.queryForObject(id,map);
			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return null;
		
	}

	@Override
	public int getIntValue(String id) {
		
		try {
			
			return ((Integer)sqlMapClientTemplate.queryForObject(id)).intValue();
			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return 0;
	}

	@Override
	public int getIntValue(String id, Object value) {
		
		try {
			
			return ((Integer)sqlMapClientTemplate.queryForObject(id,value)).intValue();

			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return 0;
	}

	@Override
	public int getIntValue(String id, Map<String, Object> map) {
		
		try {
			
			return ((Integer)sqlMapClientTemplate.queryForObject(id,map)).intValue();

			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getListData(String id) {
			
		try {
			
			return (List<Object>)sqlMapClientTemplate.queryForList(id);

			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return null;
		
	}

	@Override
	public List<Object> getListData(String id, Object value) {
		
		try {
			
			return (List<Object>)sqlMapClientTemplate.queryForList(id,value);

			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return null;
		
	}

	@Override
	public List<Object> getListData(String id, Map<String, Object> map) {
		
		try {
			
			return (List<Object>)sqlMapClientTemplate.queryForList(id,map);

			
		} catch (Exception e) {
			System.out.println("getReadData의 에러 : " + e);
		}
		
		return null;
		
	}

}
