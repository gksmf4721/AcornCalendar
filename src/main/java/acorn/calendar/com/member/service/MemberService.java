package acorn.calendar.com.member.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import acorn.calendar.config.data.AcornMap;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private static SqlSession sqlSession;
	
	public int insertMember(AcornMap acornMap) throws Exception {
		int result = sqlSession.insert("mapper.com.member.insertMember",acornMap);
		return result;
	}
}
