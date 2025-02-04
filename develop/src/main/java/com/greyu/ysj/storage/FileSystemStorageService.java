package com.greyu.ysj.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.greyu.ysj.utils.FileUtil;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class FileSystemStorageService implements StorageService {

    //private final Path rootLocation;
    
    private String root;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.root = properties.getLocation();
    }
    
    @Override
    public void storeIcon(String folder, String filename) throws IOException {
    	
    	new File(root + "/" + folder).mkdirs();
    	Path path = Paths.get(root + "/" + folder);
    	
    	String thumbnailName = "icon_" + filename;
    	
    	try {
    		Thumbnails.of(path.resolve(filename).toString()).scale(0.2f).toFile(path.resolve(thumbnailName).toString());
    	}catch(IOException e) {
    		throw new StorageException("Failed to store file " + filename, e);
    	}
    	
    }	
    
    @Override
    public String store(MultipartFile file) throws IOException, NoSuchAlgorithmException {
    	
   	
    	SimpleDateFormat formator = new SimpleDateFormat("yyyyMM");
    	String folder = formator.format(new Date());
    	
    	new File(root + "/" + folder).mkdirs();
    	Path path = Paths.get(root + "/" + folder);
    
    	InputStream is =  file.getInputStream();
    	byte[] bytes = FileUtil.toByteArray(is);
    	
    	String md5 = FileUtil.MD5(bytes);
    	String ext = FilenameUtils.getExtension(file.getOriginalFilename());
    	
    	String filename = md5 + "." + ext;
    	String thumbnailName = "icon_" + filename;
    	
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            
            Files.copy(file.getInputStream(), path.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            Thumbnails.of(path.resolve(filename).toString()).scale(0.2f).toFile(path.resolve(thumbnailName).toString());
            
            return folder + "/" + filename;
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        /*try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }*/
    	return null;
    }

    @Override
    public Path load(String filename) {
        //return rootLocation.resolve(filename);
    	return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        //FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        /*try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }*/
    }
}
