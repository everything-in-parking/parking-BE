-- favorite 테이블 생성
CREATE TABLE favorite (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    member_id BIGINT,
    parking_id BIGINT,
    PRIMARY KEY (id),
    UNIQUE (member_id, parking_id)
);

-- member 테이블 생성
CREATE TABLE member (
    id BIGINT NOT NULL AUTO_INCREMENT,
    deleted BOOLEAN,
    email VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    nickname VARCHAR(255),
    password VARCHAR(255),
    PRIMARY KEY (id)
);

-- member_session 테이블 생성
CREATE TABLE member_session (
    session_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    expired_at TIMESTAMP,
    member_id BIGINT,
    PRIMARY KEY (session_id)
);

-- parking 테이블 생성
CREATE TABLE parking (
     id BIGINT NOT NULL AUTO_INCREMENT,
     created_at TIMESTAMP,
     updated_at TIMESTAMP,
     base_fee INTEGER,
     base_time_unit INTEGER,
     capacity INTEGER,
     current_parking INTEGER,
     day_maximum_fee INTEGER,
     extra_fee INTEGER,
     extra_time_unit INTEGER,
     holiday_begin_time TIME,
     holiday_end_time TIME,
     holiday_free_begin_time TIME,
     holiday_free_end_time TIME,
     latitude FLOAT NOT NULL,
     longitude FLOAT NOT NULL,
     saturday_begin_time TIME,
     saturday_end_time TIME,
     saturday_free_begin_time TIME,
     saturday_free_end_time TIME,
     weekday_begin_time TIME,
     weekday_end_time TIME,
     weekday_free_begin_time TIME,
     weekday_free_end_time TIME,
     address VARCHAR(255),
     description VARCHAR(255),
     name VARCHAR(255),
     operation_type ENUM ('PUBLIC', 'PRIVATE', 'NO_INFO'),
     parking_type ENUM ('OFF_STREET', 'ON_STREET', 'MECHANICAL', 'NO_INFO'),
     tel VARCHAR(255),
     PRIMARY KEY (id)
);

-- review 테이블 생성
CREATE TABLE review (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at TIMESTAMP,
    parking_id BIGINT,
    reviewer_id BIGINT,
    contents VARCHAR(255),
    PRIMARY KEY (id)
);

-- search_condition 테이블 생성
CREATE TABLE search_condition (
    id BIGINT NOT NULL AUTO_INCREMENT,
    hours INTEGER NOT NULL,
    member_id BIGINT,
    fee_types VARCHAR(255),
    operation_types VARCHAR(255),
    parking_types VARCHAR(255),
    pay_types VARCHAR(255),
    priority ENUM ('DISTANCE', 'PRICE', 'RECOMMENDATION'),
    PRIMARY KEY (id)
);
