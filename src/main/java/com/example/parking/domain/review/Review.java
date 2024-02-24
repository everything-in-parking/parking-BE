package com.example.parking.domain.review;

import com.example.parking.domain.member.Member;
import com.example.parking.domain.parking.Parking;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review {

    private static final int MAX_CONTENTS_SIZE = 3;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_id", nullable = false)
    private Parking parking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private Member reviewer;

    private List<Content> contents;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST)
    private List<Image> images = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Review(Parking parking, Member reviewer, List<Content> contents, List<Image> images) {
        validate(contents);
        this.parking = parking;
        this.reviewer = reviewer;
        this.contents = contents;
        setImages(images);
    }

    private static void validate(List<Content> contents) {
        if (contents == null || contents.isEmpty() || contents.size() > MAX_CONTENTS_SIZE) {
            throw new IllegalArgumentException("리뷰 내용은 1개에서 3개까지 선택가능합니다.");
        }
    }

    private void setImages(List<Image> images) {
        for (Image image : images) {
            image.setReview(this);
        }
        this.images = images;
    }
}