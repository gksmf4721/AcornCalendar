package acorn.calendar.com.letter.service;

import acorn.calendar.config.data.AcornMap;
import acorn.calendar.config.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("letterService")
@RequiredArgsConstructor
public class LetterService {

	@Autowired
	private SqlSession sqlSession;

	public List<AcornMap> selectLetterList(AcornMap acornMap) throws Exception {
		if(acornMap.getString("type").equals("all")){
			acornMap.put("categoryType","0");
			return sqlSession.selectList("mapper.com.letter.selectLAllLetterList",acornMap);
		}else if(acornMap.getString("type").equals("send")){
			return sqlSession.selectList("mapper.com.letter.selectSendLetterList",acornMap);
		}else if(acornMap.getString("type").equals("recive")){
			return sqlSession.selectList("mapper.com.letter.selectReciveLetterList",acornMap);
		}else{
			acornMap.put("categoryType","1");
			return sqlSession.selectList("mapper.com.letter.selectLAllLetterList",acornMap);
		}
	}

	public void updateTrash(List<AcornMap> list) throws Exception {
		for(int i=0 ; i<list.size() ; i++){
			if(list.get(i).get("SEND").equals("SENDER")){
				sqlSession.update("mapper.com.letter.updateSenderTrash",list.get(i));
			}else{
				sqlSession.update("mapper.com.letter.updateReciverTrash",list.get(i));
			}
		}
	}

	public String selectSeq(AcornMap acornMap) throws Exception {
		AcornMap resultMap = sqlSession.selectOne("mapper.com.letter.selectSeq",acornMap);
		return resultMap.getString("M_SEQ");
	}

	public void insertLetter(AcornMap acornMap) throws Exception {
		sqlSession.insert("mapper.com.letter.insertLetter",acornMap);
	}


}
