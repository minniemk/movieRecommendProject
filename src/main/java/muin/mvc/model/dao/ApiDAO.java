package muin.mvc.model.dao;

import java.util.List;
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
	
	/**
	 * api ������ ��ü ����
	 * */
	public void insertAll(MovieDTO dDTO) {
		session.insert("movieApiMapper.insertall", dDTO);
	}
	
	/**
	 * api ������ ��ü �˻�
	 * */
	public List<MovieDTO> selectAll() {
		List<MovieDTO> list =session.selectList("movieApiMapper.selectAll");
		return list;
	}
	
	/**
	 * �󼼺���
	 * */
	public MovieDTO detailMovie(int movieNo) {
		
		return session.selectOne("movieApiMapper.selectMovie", movieNo);
	}
	        
}//Ŭ����
