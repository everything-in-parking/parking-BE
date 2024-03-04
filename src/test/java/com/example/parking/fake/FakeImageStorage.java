//package com.example.parking.fake;
//
//import com.example.parking.domain.image.Image;
//import com.example.parking.domain.image.ImageStorage;
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.web.multipart.MultipartFile;
//
//public class FakeImageStorage implements ImageStorage {
//
//    @Override
//    public List<Image> store(List<MultipartFile> files) {
//        ArrayList<Image> result = new ArrayList<>(files.size());
//        int count = 0;
//        for (MultipartFile file : files) {
//            result.add(new Image("testUrl" + count++));
//        }
//        return result;
//    }
//}
