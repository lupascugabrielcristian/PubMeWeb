package pubme.interfaces;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface IUserUiService {
    String saveImageToDatabase(String filename, MultipartFile image, HttpServletRequest httpRequest) throws RuntimeException, IOException;

    String saveImageToDisk(MultipartFile image, HttpServletRequest httpRequest) throws IOException;
}
