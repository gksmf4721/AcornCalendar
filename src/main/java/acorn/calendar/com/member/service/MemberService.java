package acorn.calendar.com.member.service;

import acorn.calendar.config.util.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acorn.calendar.config.data.AcornMap;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.activation.DataSource;
import javax.servlet.http.HttpServletRequest;

@Service("memberService")
@RequiredArgsConstructor
public class MemberService {

	// java 버전으로 인해 autowired 없이 private static 으로 선언 불가
	@Autowired
	private SqlSession sqlSession;


	//@Transactional(rollbackFor={Exception.class}) // 기존 해당 어노테이션과 다르게 모든 Exception 을 rollback.
	public void insertMember(AcornMap acornMap, HttpServletRequest request) throws Exception {

		try{
			sqlSession.insert("mapper.com.member.insertMember",acornMap);
			sqlSession.insert("mapper.com.member.insertProfile", FileUtils.profileInsert(acornMap,request));
		}catch (Exception e){
			sqlSession.rollback();
		}

		// try-catch 를 쓰면 error 발생해도 롤백 안 됨
		// TransactionConfig 를 지우고 테스트 해보면 트랜잭션 처리는 되는 게 맞음
		// 근데 try-catch 안에 에러 발생 코드 넣고
		// sqlSession.rollback(); 해보면 또 먹네..?
		// try - catch 말고 그냥 해도 먹긴 먹음..
		// 그냥 메시지만 Manual rollback is not allowed over a Spring managed SqlSession
		// 이렇게 스프링이 수동 롤백을 지원할 수 없다고 뜨는 듯.

	}

	public int selectInputCheck(AcornMap acornMap) throws Exception {
		int result = 0;
		if(acornMap.getString("type").equals("id")) result = sqlSession.selectOne("mapper.com.member.selectIdCheck",acornMap);
		else if(acornMap.get("type").equals("nickname")) result = sqlSession.selectOne("mapper.com.member.selectNicknameCheck",acornMap);
		//else result = sqlSession.selectOne("mapper.com.member.selectEmailCheck",acornMap);
		return result;
	}

	public AcornMap selectLogin(AcornMap acornMap) throws Exception {
		return sqlSession.selectOne("mapper.com.member.selectLogin",acornMap);
	}

}
