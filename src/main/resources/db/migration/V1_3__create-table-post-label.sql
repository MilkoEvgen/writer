CREATE TABLE post_label (
  post_id INT,
  label_id INT,
  PRIMARY KEY (post_id, label_id),
  FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE ,
  FOREIGN KEY (label_id) REFERENCES labels(id) ON DELETE CASCADE
);