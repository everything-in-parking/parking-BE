ALTER TABLE parking
    DROP COLUMN holiday_begin_time,
    DROP COLUMN holiday_end_time,
    DROP COLUMN holiday_free_begin_time,
    DROP COLUMN holiday_free_end_time,
    DROP COLUMN saturday_begin_time,
    DROP COLUMN saturday_end_time,
    DROP COLUMN saturday_free_begin_time,
    DROP COLUMN saturday_free_end_time,
    DROP COLUMN weekday_begin_time,
    DROP COLUMN weekday_end_time,
    DROP COLUMN weekday_free_begin_time,
    DROP COLUMN weekday_free_end_time;

ALTER TABLE parking
    ADD COLUMN holiday_operating_time VARCHAR(20),
    ADD COLUMN holiday_free_operating_time VARCHAR(20),
    ADD COLUMN saturday_operating_time VARCHAR(20),
    ADD COLUMN saturday_free_operating_time VARCHAR(20),
    ADD COLUMN weekday_operating_time VARCHAR(20),
    ADD COLUMN weekday_free_operating_time VARCHAR(20);



