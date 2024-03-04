package com.example.parking.domain.common;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Embeddable
public class Association<T> {

    private Long id;

    private Association(Long id) {
        this.id = id;
    }

    public static <T> Association<T> from(Long id) {
        Assert.notNull(id, "Id는 null이 될 수 없습니다.");
        return new Association<>(id);
    }
}
