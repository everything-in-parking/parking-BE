ALTER TABLE parking MODIFY COLUMN location POINT NOT NULL SRID 4326;

CREATE SPATIAL INDEX parking_ix_location ON parking (location);
CREATE UNIQUE INDEX search_condition_ux_member_id ON search_condition (member_id);
CREATE UNIQUE INDEX review_ux_parking_id_reviewer_id ON review (parking_id, reviewer_id);
