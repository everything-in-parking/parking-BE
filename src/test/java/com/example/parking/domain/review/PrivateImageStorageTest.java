//package com.example.parking.domain.review;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.example.parking.domain.image.PrivateImageStorage;
//import java.io.File;
//import java.io.FilenameFilter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Arrays;
//import java.util.List;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.mock.web.MockMultipartFile;
//
//class PrivateImageStorageTest {
//
//    private final String directory = "src/test/resources/static/images/";
//
//    private final PrivateImageStorage imageStorage = new PrivateImageStorage(directory);
//
//    @Test
//    void 이미지를_로컬_환경에_저장한다() throws IOException {
//        //given
//        final InputStream inputStream = getClass().getResourceAsStream("/static/images/test-image.png");
//        MockMultipartFile file = new MockMultipartFile(
//                "testImage1.png",
//                "test-image.png",
//                "image/png",
//                inputStream
//        );
//        int beforeFileCount = new File(directory).list().length;
//
//        //when
//        imageStorage.store(List.of(file));
//        int afterFileCount = new File(directory).list().length;
//
//        //then
//        assertThat(afterFileCount).isEqualTo(beforeFileCount + 1);
//    }
//
//    @AfterEach
//    void 테스트후_저장된_이미지_제거() {
//        File folder = new File(directory);
//        FilenameFilter filter = (dir, name) -> !(name.equals("dummy.txt") || name.equals("test-image.png"));
//        File[] files = folder.listFiles(filter);
//        Arrays.stream(files).forEach(File::delete);
//    }
//}
