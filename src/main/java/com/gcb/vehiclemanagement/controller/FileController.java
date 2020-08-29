package com.gcb.vehiclemanagement.controller;

//import com.gcb.vehiclemanagement.entity.ResultData;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//public class FileController {
//
//    @Value("${fileDirPath}")
//    private String fileDir;
//
//    @PostMapping("/api/uploadFile_new")
//    public ResultData uploadFile(@RequestParam(value = "file") MultipartFile file, Model model, HttpServletRequest request) {
//        ResultData resultData = new ResultData();
//        resultData.setStatusCode(400);
//        resultData.setStatusRes("fail");
//        if (file.isEmpty()) {
//            return resultData;
//        }
//        String fileName = file.getOriginalFilename();  // 文件名
//        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
//        String currentTime = String.valueOf(System.currentTimeMillis());
//        fileName = currentTime + suffixName; // 新文件名
//        File dest = new File(fileDir + fileName);
//        if (!dest.getParentFile().exists()) {
//            dest.getParentFile().mkdirs();
//        }
//        try {
//            file.transferTo(dest);
//            resultData.setStatusCode(200);
//            resultData.setStatusRes("success");
//            Map<String,Object> map = new HashMap<>();
//            map.put("fileName",fileName);
//            resultData.setInfo(map);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return resultData;
//    }
//
//    @PostMapping("/api/downloadFile_new")
//    public ResultData downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) {
//        ResultData resultData = new ResultData();
//        resultData.setStatusCode(400);
//        resultData.setStatusRes("fail");
//        if (fileName != null) {
//            String filePath = fileDir + fileName;
//            File file = new File(filePath);
//            if (file.exists()) {
//                response.setContentType("image/jpeg");
//                response.setCharacterEncoding("UTF-8");
//                byte[] buffer = new byte[1024];
//                FileInputStream fis = null;
//                BufferedInputStream bis = null;
//                try {
//                    fis = new FileInputStream(file);
//                    bis = new BufferedInputStream(fis);
//                    OutputStream os = response.getOutputStream();
//                    int i = bis.read(buffer);
//                    while (i != -1) {
//                        os.write(buffer, 0, i);
//                        i = bis.read(buffer);
//                    }
//                    resultData.setStatusCode(200);
//                    resultData.setStatusRes("success");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (bis != null) {
//                        try {
//                            bis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (fis != null) {
//                        try {
//                            fis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//        return resultData;
//    }
//}
