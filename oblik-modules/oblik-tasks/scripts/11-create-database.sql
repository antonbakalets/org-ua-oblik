CREATE DATABASE IF NOT EXISTS oblik_db CHARACTER SET = utf8 COLLATE = utf8_general_ci;

GRANT ALL ON oblik_db.* TO 'oblik_user'@'localhost' IDENTIFIED BY 'oblik_pass';

FLUSH PRIVILEGES;