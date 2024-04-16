package com.example.parking.application.favorite;

import com.example.parking.application.favorite.dto.FavoriteCreateRequest;
import com.example.parking.application.favorite.dto.FavoriteDeleteRequest;
import com.example.parking.domain.favorite.Favorite;
import com.example.parking.domain.favorite.FavoriteRepository;
import com.example.parking.support.Association;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public void createFavorite(FavoriteCreateRequest favoriteCreateRequest, Long memberId) {
        Long parkingId = favoriteCreateRequest.getParkingId();

        Favorite favorite = new Favorite(Association.from(memberId), Association.from(parkingId));
        saveFavorite(favorite);
    }

    private void saveFavorite(Favorite favorite) {
        try {
            favoriteRepository.save(favorite);
        } catch (DataIntegrityViolationException e) {
            log.warn("memberId: {}, parkingId: {} request duplicate favorite create", favorite.getMemberId(),
                    favorite.getParkingId());
        }
    }

    public void deleteFavorite(FavoriteDeleteRequest favoriteDeleteRequest, Long memberId) {
        Long parkingId = favoriteDeleteRequest.getParkingId();

        favoriteRepository.deleteByMemberIdAndParkingId(Association.from(memberId), Association.from(parkingId));
    }
}
