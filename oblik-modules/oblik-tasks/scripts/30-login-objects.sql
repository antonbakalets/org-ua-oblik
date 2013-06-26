-- -----------------------------------------------------
-- Table User_Login
-- -----------------------------------------------------
DROP TABLE IF EXISTS User_Login;

CREATE  TABLE IF NOT EXISTS User_Login (
  login_id              INT UNSIGNED NOT NULL AUTO_INCREMENT,
  username              VARCHAR(100) NOT NULL,
  password              VARCHAR(100) NOT NULL,
  email                 VARCHAR(100) NOT NULL,
  permit                VARCHAR(100) NOT NULL,
  activation_uuid       VARCHAR(36) NULL,
  PRIMARY KEY (login_id)
) DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci 
  COMMENT = 'Інформація для входу в систему';
