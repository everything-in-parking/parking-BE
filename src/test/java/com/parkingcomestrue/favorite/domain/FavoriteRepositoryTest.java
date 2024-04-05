package com.parkingcomestrue.favorite.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.parkingcomestrue.favorite.domain.Favorite;
import com.parkingcomestrue.favorite.domain.FavoriteRepository;
import com.parkingcomestrue.support.Association;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void 멤버의_즐겨찾기_주차장을_삭제한다() {
        Long memberId = 1L;
        Long parkingId = 1L;
        Favorite favorite = new Favorite(Association.from(memberId), Association.from(parkingId));
        favoriteRepository.save(favorite);

        favoriteRepository.deleteByMemberIdAndParkingId(Association.from(memberId), Association.from(parkingId));
        assertThat(favoriteRepository.findByMemberId(Association.from(memberId))).isEmpty();
    }

    @Test
    void 멤버의_즐겨찾기_주차장을_조회한다() {
        // given
        Long memberId = 1L;
        Long memberId2 = 2L;

        Long parkingId = 1L;
        Long parkingId2 = 2L;
        Long parkingId3 = 3L;
        Long parkingId4 = 4L;

        // when
        favoriteRepository.save(new Favorite(Association.from(memberId), Association.from(parkingId)));
        favoriteRepository.save(new Favorite(Association.from(memberId), Association.from(parkingId2)));
        favoriteRepository.save(new Favorite(Association.from(memberId), Association.from(parkingId3)));

        favoriteRepository.save(new Favorite(Association.from(memberId2), Association.from(parkingId3)));
        favoriteRepository.save(new Favorite(Association.from(memberId2), Association.from(parkingId4)));

        // then
        assertThat(favoriteRepository.findByMemberId(Association.from(memberId))).hasSize(3);
    }
}
