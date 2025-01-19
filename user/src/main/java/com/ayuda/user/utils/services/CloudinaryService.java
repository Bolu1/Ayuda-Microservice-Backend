package com.ayuda.user.utils.services;

import com.ayuda.user.config.AppSettings;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(AppSettings appSettings) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", appSettings.getCloudinaryCloudName(),
                "api_key", appSettings.getCloudinaryApiKey(),
                "api_secret", appSettings.getCloudinaryApiSecret()
        ));
    }

    public String uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "use_filename", false,
                "unique_filename", true,
                "overwrite", true
        ));
        return uploadResult.get("url").toString();
    }
}
