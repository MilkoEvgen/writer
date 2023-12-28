CREATE TABLE labels (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL
);

INSERT INTO labels (name) VALUES
    ('love'),
    ('family'),
    ('travel'),
    ('adventures');
