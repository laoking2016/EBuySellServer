package com.greyu.ysj.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

/**
 * @Description: 文件上传和删除类
 * @Author: gre_yu@163.com
 * @Date: Created in 16:48 2018/3/12.
 */
public class FileUtil {
    /**
     * 文件上传
     * @param file 要上传的文件
     * @param path 要上传的路径
     * @return 处理后文件的新名称
     * @throws IOException 异常
     */
    public static String upload(MultipartFile file, String path) throws IOException {
        String fileName = file.getOriginalFilename();
        String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        Files.copy(file.getInputStream(), Paths.get(path, newFileName));
        System.out.println(path);
        Runtime.getRuntime().exec("chmod 755 /var/www/html/cloudimg/goods/" + newFileName);
        return newFileName;
    }

    /**
     * 删除文件
     * @param path 路径
     * @param fileName 名称
     * @throws IOException 异常
     */
    public static void delete(String path, String fileName) throws IOException {
        File delete = new File(path + "\\" + fileName);
        boolean b = delete.delete();
        if (b) {
            System.out.println("删除文件");
        }
    }
    
    public static String MD5(byte[] b) throws NoSuchAlgorithmException {
    	byte[] hash = MessageDigest.getInstance("MD5").digest(b);
    	return DatatypeConverter.printHexBinary(hash);
    }
    
    public static byte[] toByteArray(InputStream is) throws IOException {
    	ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    	int nRead;
    	byte[] data = new byte[16384];

    	while ((nRead = is.read(data, 0, data.length)) != -1) {
    	  buffer.write(data, 0, nRead);
    	}

    	buffer.flush();

    	return buffer.toByteArray();
    }
}
