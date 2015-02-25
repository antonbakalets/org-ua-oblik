DROP TABLE IF EXISTS user_login;
DROP TABLE IF EXISTS txaction;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS currency;

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
);

-- -----------------------------------------------------
-- Table Currency
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS currency (
    curr_id         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    symbol          VARCHAR(10) NOT NULL ,
    by_default      BOOL NOT NULL DEFAULT FALSE,
    rate            DECIMAL(10, 5) NOT NULL,
    PRIMARY KEY (curr_id)
);

-- -----------------------------------------------------
-- Table Account
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS account (
    acco_id         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    short_name      VARCHAR(100) NOT NULL,
    kind            VARCHAR(7) NOT NULL,
    total           DECIMAL(15, 5) NOT NULL,
    currency        INT UNSIGNED NOT NULL,
    PRIMARY KEY (acco_id),
    CONSTRAINT fk_acco_curr
        FOREIGN KEY (currency) REFERENCES currency (curr_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX in_acco_curr ON account(currency ASC);

-- -----------------------------------------------------
-- Table Txaction
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS txaction (
    txac_id         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    tx_date         DATE NOT NULL,
    debet           INT UNSIGNED NULL,
    debet_ammount   DECIMAL(15, 5) NULL,
    credit          INT UNSIGNED NULL,
    credit_ammount  DECIMAL(15, 5) NULL,
    tx_comment      VARCHAR(250) NULL,
    PRIMARY KEY (txac_id),
    CONSTRAINT fk_tx_debet
        FOREIGN KEY (debet) REFERENCES account (acco_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_tx_credit
        FOREIGN KEY (credit) REFERENCES account (acco_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX in_tx_debet ON txaction(debet ASC);
CREATE INDEX in_tx_credit ON txaction(credit ASC);