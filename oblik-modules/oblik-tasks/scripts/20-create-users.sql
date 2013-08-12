--CREATE USER 'oblik_user'@'localhost' IDENTIFIED BY 'shared';--

GRANT ALL ON oblik_login.* TO 'oblik_user'@'localhost' IDENTIFIED BY 'oblik_pass';
GRANT ALL ON oblik_test.* TO 'oblik_user'@'localhost' IDENTIFIED BY 'oblik_pass';

FLUSH PRIVILEGES;
