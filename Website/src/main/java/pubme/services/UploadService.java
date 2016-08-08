package pubme.services;

import org.springframework.web.multipart.MultipartFile;
import pubme.interfaces.IUploadService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadService implements IUploadService {

    @Override
    public String upload(MultipartFile filePart, HttpServletRequest request, String status){

        String folderPath = "./src/main/resources/static";
        String folderName = "/UserImages/";
        String savePath = folderPath + folderName + filePart.getOriginalFilename();

        File fileSaveDir = new File(folderPath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        try {
            File fileToBeSaved = new File(savePath);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileToBeSaved));
            stream.write(filePart.getBytes());
            stream.close();
            return folderName + filePart.getOriginalFilename();

        } catch (IOException e) {
            return "Upload failed";
        }

    }
}
