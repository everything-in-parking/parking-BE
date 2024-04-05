package com.parkingcomestrue.favorite.api;

import com.parkingcomestrue.favorite.application.FavoriteService;
import com.parkingcomestrue.favorite.application.dto.FavoriteCreateRequest;
import com.parkingcomestrue.favorite.application.dto.FavoriteDeleteRequest;
import com.parkingcomestrue.config.argumentresolver.MemberAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "즐겨찾기 컨트롤러")
@RequiredArgsConstructor
@RestController
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "즐겨찾기 등록", description = "즐겨찾기 등록")
    @PostMapping("/favorites")
    public ResponseEntity<Void> create(@RequestBody FavoriteCreateRequest favoriteCreateRequest,
                                       @Parameter(hidden = true) @MemberAuth Long memberId) {
        favoriteService.createFavorite(favoriteCreateRequest, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "즐겨찾기 해제", description = "즐겨찾기 해제")
    @DeleteMapping("/favorites")
    public ResponseEntity<Void> delete(@RequestBody FavoriteDeleteRequest favoriteDeleteRequest,
                                       @Parameter(hidden = true) @MemberAuth Long memberId) {
        favoriteService.deleteFavorite(favoriteDeleteRequest, memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
