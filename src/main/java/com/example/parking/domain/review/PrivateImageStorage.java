package com.example.parking.domain.review;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class PrivateImageStorage implements ImageStorage {

    private final String directory;

    public PrivateImageStorage(@Value("${file.dir}") String directory) {
        this.directory = directory;
    }

    public List<Image> store(List<MultipartFile> files) {
        if (Objects.isNull(files) || files.isEmpty()) {
            return Collections.emptyList();
        }
        return files.stream()
                .map(this::store)
                .toList();
    }

    private Image store(MultipartFile file) {
        String storeFileName = createStoreFileName(file.getOriginalFilename());
        Path path = Path.of(directory, storeFileName);
        try {
            file.transferTo(path);
        } catch (IOException e) {
            log.warn("파일 저장 중에 오류가 생겼습니다. {}", e);
        }
        return new Image(path.toString());
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
