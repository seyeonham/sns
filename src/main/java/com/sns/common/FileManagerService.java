package com.sns.common;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManagerService {
    public final static String FILE_UPLOAD_PATH = "C:\\함세연\\6_spring_project\\s_images/";

    // input: multipart file, userLoginId(폴더명으로 사용)
    // output: imagePath(String)
    public String uploadFile(MultipartFile file, String loginId) {
        // 폴더(디렉토리) 생성
        String directoryName = loginId + "_" + System.currentTimeMillis();
        String filePath = FILE_UPLOAD_PATH + directoryName + "/";

        // 폴더 생성
        File directory = new File(filePath);
        if (directory.mkdir() == false) {
            return null; // 폴더 생성시 실패하면 이미지 경로는 null로 리턴(에러 아님)
        }

        // 파일 업로드
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace(); // 에러 메세지 출력
            return null; // 이미지 업로드 실패시 이미지 경로는 null로 리턴(에러 아님)
        }

        // 파일 업로드가 성공하면 이미지 url path 리턴
        return "/images/" + directoryName + "/" + file.getOriginalFilename();
    }
}
