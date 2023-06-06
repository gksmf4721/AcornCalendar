package acorn.calendar.config;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("newsService")
public class NewsService {
	
	private final SqlSession sqlSession;
	
	public List<HashMap<String,Object>> testt() throws Exception{
		
		return sqlSession.selectList("mapper.testTable"); 
	}
}
