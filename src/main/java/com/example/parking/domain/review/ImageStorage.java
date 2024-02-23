package com.example.parking.domain.review;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorage {

    List<Image> store(List<MultipartFile> files);
}
