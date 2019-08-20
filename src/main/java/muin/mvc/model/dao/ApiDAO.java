package muin.mvc.model.dao;



import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import muin.mvc.model.dto.MovieDTO;

/**
 * �������� DB�� ����
 * */

@Repository
public class ApiDAO {

	@Autowired
	private SqlSession session;
	
	
	public void insertAll(MovieDTO dDTO) {
		session.insert("movieApiMapper.insertall", dDTO);
	}
	        
}//Ŭ����
