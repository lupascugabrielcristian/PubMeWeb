package pubme.interfaces;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface IUploadService {
    String upload(MultipartFile filePart, HttpServletRequest request, String status);
}
