package hcmute.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {
	public static Path saveFile(String uploadDir,MultipartFile file) throws IOException {
		Path fileNameAndPath = Paths.get(uploadDir, file.getOriginalFilename());
		Files.write(fileNameAndPath, file.getBytes());
		return fileNameAndPath;
    }
	public static Path saveUserImage(MultipartFile file, Long userId) throws IOException {
		Path fileNameAndPath = Paths.get(AppConstant.UPLOAD_USER_DIRECTORY, userId.toString()+".png");
		Files.write(fileNameAndPath, file.getBytes());
		return fileNameAndPath;
	}
	public static Path saveLaptopImage(MultipartFile file, Long laptopId) throws IOException {
		Path fileNameAndPath = Paths.get(AppConstant.UPLOAD_LAPTOP_DIRECTORY, laptopId.toString()+".png");
		Files.write(fileNameAndPath, file.getBytes());
		return fileNameAndPath;
	}
	public static Path saveCategorryImage(MultipartFile file, Long categoryId) throws IOException {
		Path fileNameAndPath = Paths.get(AppConstant.UPLOAD_CATEGORY_DIRECTORY, categoryId.toString()+".png");
		Files.write(fileNameAndPath, file.getBytes());
		return fileNameAndPath;
	}
}
