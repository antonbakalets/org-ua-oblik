DROP TABLE IF EXISTS txaction;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS currency;
DROP TABLE IF EXISTS user_login;

-- -----------------------------------------------------
-- Table User_Login
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_login (
    login_id            INT UNSIGNED NOT NULL AUTO_INCREMENT,
    username            VARCHAR(100) NOT NULL,
    password            VARCHAR(100) NOT NULL,
    email               VARCHAR(100) NOT NULL,
    permit              VARCHAR(100) NOT NULL,
    activation_uuid     VARCHAR(36) NULL,
    PRIMARY KEY (login_id)
)ENGINE=InnoDB
    DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci 
    COMMENT = 'Користувачі.';

-- -----------------------------------------------------
-- Table Currency
-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS currency (
    curr_id         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    symbol          VARCHAR(10) NOT NULL ,
    by_default      BOOL NOT NULL DEFAULT FALSE,
    rate            DECIMAL(10, 5) NOT NULL,
    PRIMARY KEY (curr_id)
) ENGINE=InnoDB
    DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci 
    COMMENT = 'Валюти, якими користується користувач';

-- -----------------------------------------------------
-- Table Account
-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS account (
    acco_id         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    short_name      VARCHAR(100) NOT NULL,
    kind            VARCHAR(7) NOT NULL,
    total           DECIMAL(15, 5) NOT NULL,
    currency        INT UNSIGNED NOT NULL,
    PRIMARY KEY (acco_id),
    INDEX fk_acco_curr (currency ASC),
    CONSTRAINT fk_acco_curr
        FOREIGN KEY (currency) REFERENCES currency (curr_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB
    DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci 
    COMMENT = 'Дебетові та кредитові рахунки';

-- -----------------------------------------------------
-- Table Txaction
-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS txaction (
    txac_id         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    tx_date         DATE NOT NULL,
    debet           INT UNSIGNED NULL,
    debet_amount   DECIMAL(15, 5) NULL,
    credit          INT UNSIGNED NULL,
    credit_amount  DECIMAL(15, 5) NULL,
    tx_comment      VARCHAR(250) NULL,
    PRIMARY KEY (txac_id),
    INDEX fk_tx_debet (debet ASC),
    CONSTRAINT fk_tx_debet
        FOREIGN KEY (debet) REFERENCES account (acco_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    INDEX fk_tx_credit (credit ASC),
    CONSTRAINT fk_tx_credit
        FOREIGN KEY (credit) REFERENCES account (acco_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB
    DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci 
    COMMENT = 'Грошові транзакції.';
