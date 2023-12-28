CREATE TABLE posts (
  id INT PRIMARY KEY AUTO_INCREMENT,
  content VARCHAR(5000) NOT NULL,
  created TIMESTAMP,
  updated TIMESTAMP NULL,
  author_id INT,
  status VARCHAR(64),
  CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES writers(id) ON DELETE CASCADE
);