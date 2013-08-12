DROP TABLE IF EXISTS Txaction;
DROP TABLE IF EXISTS Account;
DROP TABLE IF EXISTS Currency;

-- -----------------------------------------------------
-- Table Currency
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Currency (
    curr_id         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    symbol          VARCHAR(10) NOT NULL ,
    by_default      BOOL NOT NULL DEFAULT FALSE,
    rate            DECIMAL(10, 5) NOT NULL,
    PRIMARY KEY (curr_id)
);

-- -----------------------------------------------------
-- Table Account
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Account (
    acco_id         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    short_name      VARCHAR(100) NOT NULL,
    kind            VARCHAR(7) NOT NULL,
    total           DECIMAL(15, 5) NOT NULL,
    currency        INT UNSIGNED NOT NULL,
    PRIMARY KEY (acco_id),
    CONSTRAINT fk_acco_curr
        FOREIGN KEY (currency) REFERENCES Currency (curr_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX fk_acco_curr ON Account(currency ASC);

-- -----------------------------------------------------
-- Table Txaction
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Txaction (
    txac_id         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    tx_date         DATE NOT NULL,
    debet           INT UNSIGNED NULL,
    debet_ammount   DECIMAL(15, 5) NULL,
    credit          INT UNSIGNED NULL,
    credit_ammount  DECIMAL(15, 5) NULL,
    tx_comment      VARCHAR(250) NULL,
    PRIMARY KEY (txac_id),
    CONSTRAINT fk_tx_debet
        FOREIGN KEY (debet) REFERENCES Account (acco_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_tx_credit
        FOREIGN KEY (credit) REFERENCES Account (acco_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX fk_tx_debet ON Txaction(debet ASC);
CREATE INDEX fk_tx_credit ON Txaction(credit ASC);