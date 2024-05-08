ALTER TABLE parking
    CHANGE COLUMN point location GEOMETRY,
    CHANGE COLUMN description pay_types VARCHAR(255);
