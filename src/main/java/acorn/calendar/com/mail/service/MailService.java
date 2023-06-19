package acorn.calendar.com.mail.service;

import acorn.calendar.config.data.AcornMap;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mailService")
@RequiredArgsConstructor
public class MailService {

    @Autowired
    private SqlSession sqlSession;

    public void insertMailAuthHistory(AcornMap acornMap) throws Exception {
        sqlSession.insert("mapper.com.mail.insertMailAuthHistory",acornMap);
    }

    public AcornMap selectMailAuthHistory(AcornMap acornMap) throws Exception {
        return sqlSession.selectOne("mapper.com.mail.selectMailAuthHistory",acornMap);
    }

    public AcornMap selectSeq(AcornMap acornMap) throws Exception {
        return sqlSession.selectOne("mapper.com.mail.selectSeq",acornMap);
    }

    public AcornMap updatePw(AcornMap acornMap) throws Exception {
        return sqlSession.selectOne("mapper.com.mail.updatePw",acornMap);
    }

    public int selectEmailCheck(AcornMap acornMap) throws Exception {
        return sqlSession.selectOne("mapper.com.member.selectEmailCheck",acornMap);
    }
}
