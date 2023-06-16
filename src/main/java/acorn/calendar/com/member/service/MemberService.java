package acorn.calendar.com.member.service;

import acorn.calendar.config.util.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acorn.calendar.config.data.AcornMap;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;

@Service("memberService")
@RequiredArgsConstructor
@Transactional(rollbackFor={Exception.class})
public class MemberService {

	@Autowired
	private SqlSession sqlSession;

	// java 버전으로 인해 autowired없이 private static으로 선언 불가

	//@Transactional(rollbackFor=Exception.class)

	public void insertMember(AcornMap acornMap, HttpServletRequest request) throws Exception {

		//try{
			sqlSession.insert("mapper.com.member.insertMember",acornMap);
			//throw new Exception("롤백");
			//위 쿼리문으로 acornMap에 시퀀스 뽑아옴. 회원가입 시, 프로필 추가 쿼리 사용 예정
			//sqlSession.insert("mapper.com.member.insertProfile", FileUtils.profileInsert(acornMap,request));
		//}catch(Exception e){
			//e.printStackTrace();
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			 //트랜잭션 설정 X라서 sqlSession.rollback() 불가, 일단 강제적으로 rollback 지정
			 //트랜잭션 설정 시, Aspect 설정 안되어 있어서 불가
			//e.printStackTrace();
			//sqlSession.rollback();
		//}



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
