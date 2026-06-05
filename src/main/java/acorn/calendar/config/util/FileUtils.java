package acorn.calendar.config.util;

import acorn.calendar.config.data.AcornMap;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.UUID;

@Component
public class FileUtils {

    private static final String filePath = "C:\\project\\img\\";
    public static AcornMap profileInsert(AcornMap acornMap, HttpServletRequest request) throws Exception {

        // 프로필 수정 등 타 파일들은 업로드 사이즈 및 확장자 제한 예정

        String realPath = "";
        String savePath = filePath;

        //realPath = request.getRealPath(savePath);
        String fileExtension = getRandomString();
        String savedFileName = fileExtension + ".png";
        int count = 0;

        File baseFile = new File(savePath + "acorn.png");
        File saveDir = new File(savePath);
        if (baseFile.exists()) {
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            File file2 = new File(savePath + savedFileName);

            try (FileInputStream fis = new FileInputStream(baseFile);
                 FileOutputStream fos = new FileOutputStream(file2)) {
                int input = 0;
                byte[] data = new byte[1024];

                while ((input = fis.read(data)) != -1) {
                    fos.write(data, 0, input);
                    count += input;
                }
            }
        } else {
            savedFileName = "acorn.png";
        }

        AcornMap profile = new AcornMap();

        profile.put("fType","M");
        profile.put("fTypeSeq",acornMap.getString("mSeq"));
        profile.put("fOgName","base_acorn.png");
        profile.put("fSvName", savedFileName);
        profile.put("fSize", count);
        profile.put("fMainYn","Y");
        profile.put("fDelYn","Y");

        return profile;
    }

    public static String getRandomString(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
