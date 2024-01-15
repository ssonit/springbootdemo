package com.example.demo.services;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageStorageService implements IStorageService {

    private final Path storageFolder = Paths.get("uploads");

    public ImageStorageService() {
        try {
            Files.createDirectories(storageFolder);
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize Image Storage", e);
        }
    }

    public boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"png", "jpg", "jpeg", "bmp"}).contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file) {
         try {
            System.out.println("haha");
           if(file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file");
           }
           if(!isImageFile(file)) {
            throw new RuntimeException("You can only upload image file");

           }

           float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
            
           if(fileSizeInMegabytes > 5.0f) {
                throw new RuntimeException("File must be at least 5MB");
           }

           String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
           String generatedFilename = UUID.randomUUID().toString().replace("-", "");
           generatedFilename = generatedFilename + "." + fileExtension;
           Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFilename)).normalize().toAbsolutePath();

           if(!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                        throw new RuntimeException("Cannot store file outside current directory");

           }

           try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
           }

           return generatedFilename;

        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize Image Storage", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(storageFolder, 1).filter(path -> {
                return !path.equals(storageFolder) && !path.toString().contains("._");
            }).map(storageFolder::relativize);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load all files", e);
            }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else {
                throw new RuntimeException("Could not read file " + fileName);
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Could not read file " + fileName, e);
        }
    }

    @Override
    public void deleteAllFiles() {
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllFiles'");
    }

}