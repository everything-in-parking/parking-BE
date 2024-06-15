package com.parkingcomestrue.parking.application.member.dto;

import lombok.Getter;

@Getter
public class MemberId {

    private long id;

    private MemberId(long id) {
        this.id = id;
    }

    public static MemberId from(long id) {
        return new MemberId(id);
    }

    public boolean isGuestUser() {
        return id < 0;
    }
}
