package acorn.calendar.com.member.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acorn.calendar.config.data.AcornMap;
import lombok.RequiredArgsConstructor;

@Service("memberService")
@RequiredArgsConstructor
public class MemberService {

	@Autowired
	private SqlSession sqlSession;

	// java 버전으로 인해 autowired없이 private static으로 선언 불가
	
	public void insertMember(AcornMap acornMap) throws Exception {
		sqlSession.insert("mapper.com.member.insertMember",acornMap);
	}

	public int selectInputCheck(AcornMap acornMap) throws Exception {
		int result = 0;
		if(acornMap.getString("type").equals("id")) result = sqlSession.selectOne("mapper.com.member.selectIdCheck",acornMap);
		else if(acornMap.get("type").equals("nickname")) result = sqlSession.selectOne("mapper.com.member.selectNicknameCheck",acornMap);
		else result = sqlSession.selectOne("mapper.com.member.selectEmailCheck",acornMap);
		return result;
	}

	public AcornMap selectLogin(AcornMap acornMap) throws Exception {
		return sqlSession.selectOne("mapper.com.member.selectLogin",acornMap);
	}

}
