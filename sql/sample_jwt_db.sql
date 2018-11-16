CREATE DATABASE sample_jwt_db;

USE sample_jwt_db;

CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(100) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));
CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));

INSERT INTO users(username,password,enabled)
VALUES ('helpdev','$2a$06$ugCPeuLP1XfHASA.eMXjwe7IPoDxbxm8BZermZG3xboNTSBOzEInu', true);

INSERT INTO users(username,password,enabled)
VALUES ('admin','$2a$06$YmXBrF8L3QYghwwUJlwWru.vbaNv7RisayZDjIamXCGk2Ex4x7Yyy', true);

INSERT INTO user_roles (username, role)
VALUES ('admin', 'ROLE_USER');

INSERT INTO user_roles (username, role)
VALUES ('admin', 'ROLE_ADMIN');

INSERT INTO user_roles (username, role)
VALUES ('helpdev', 'ROLE_USER');