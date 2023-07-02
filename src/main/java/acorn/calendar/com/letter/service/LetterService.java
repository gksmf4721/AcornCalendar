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
		try{
			for(int i=0 ; i<list.size() ; i++){
				if(list.get(i).get("SEND").equals("SENDER")){
					sqlSession.update("mapper.com.letter.updateSenderTrash",list.get(i));
				}else{
					sqlSession.update("mapper.com.letter.updateReciverTrash",list.get(i));
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			sqlSession.rollback();
		}
	}

	public void updateTrashDelete(List<AcornMap> list) throws Exception {
		try{
			for(int i=0 ; i<list.size() ; i++){
				if(list.get(i).get("SEND").equals("SENDER")){
					sqlSession.update("mapper.com.letter.updateSenderTrashDelete",list.get(i));
				}else{
					sqlSession.update("mapper.com.letter.updateReciverTrashDelete",list.get(i));
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			sqlSession.rollback();
		}
	}

	public void updateTrashRestore(List<AcornMap> list) throws Exception {
		try{
			for(int i=0 ; i<list.size() ; i++){
				if(list.get(i).get("SEND").equals("SENDER")){
					sqlSession.update("mapper.com.letter.updateSenderTrashRestore",list.get(i));
				}else{
					sqlSession.update("mapper.com.letter.updateReciverTrashRestore",list.get(i));
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			sqlSession.rollback();
		}
	}

	public String selectSeq(AcornMap acornMap) throws Exception {
		AcornMap resultMap = sqlSession.selectOne("mapper.com.letter.selectSeq",acornMap);
		return resultMap.getString("M_SEQ");
	}

	public String selectId(AcornMap acornMap) throws Exception {
		AcornMap resultMap = sqlSession.selectOne("mapper.com.letter.selectId",acornMap);
		return resultMap.getString("M_ID");
	}

	public void insertLetter(AcornMap acornMap) throws Exception {
		sqlSession.insert("mapper.com.letter.insertLetter",acornMap);
	}

	public void updateTrashList(List<AcornMap> deleteList) throws Exception {
		for(int i=0 ; i<deleteList.size() ; i++){
			if(deleteList.get(i).getString("SEND").equals("SENDER")){
				sqlSession.insert("mapper.com.letter.updateSenderComplateDelete",deleteList.get(i));
			}else{
				sqlSession.insert("mapper.com.letter.updateReciverComplateDelete",deleteList.get(i));
			}
		}
	}


}
