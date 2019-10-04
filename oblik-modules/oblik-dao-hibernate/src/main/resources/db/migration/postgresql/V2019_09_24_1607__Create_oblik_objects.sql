-- -----------------------------------------------------
-- Table User_Login
-- -----------------------------------------------------
CREATE TABLE user_login (
    login_id            SERIAL,
    username            VARCHAR(100) NOT NULL,
    password            VARCHAR(100) NOT NULL,
    email               VARCHAR(100) NOT NULL,
    permit              VARCHAR(100) NOT NULL,
    activation_uuid     VARCHAR(36) NULL,
    PRIMARY KEY (login_id)
);

COMMENT ON TABLE user_login IS 'Users.';

-- -----------------------------------------------------
-- Table Currency
-- -----------------------------------------------------

CREATE TABLE currency (
    curr_id         SERIAL,
    symbol          VARCHAR(10) NOT NULL ,
    by_default      BOOLEAN NOT NULL DEFAULT FALSE,
    rate            DECIMAL(10, 5) NOT NULL,
    PRIMARY KEY (curr_id)
);

COMMENT ON TABLE currency IS 'Currencies used by user.';

-- -----------------------------------------------------
-- Table Account
-- -----------------------------------------------------

CREATE TABLE account (
    acco_id         SERIAL,
    short_name      VARCHAR(100) NOT NULL,
    kind            VARCHAR(7) NOT NULL,
    total           DECIMAL(15, 5) NOT NULL,
    currency        INT NOT NULL,
    PRIMARY KEY (acco_id),
    FOREIGN KEY (currency) REFERENCES currency (curr_id)
);

COMMENT ON TABLE account IS 'Debit and credit accounts.';

-- -----------------------------------------------------
-- Table Txaction
-- -----------------------------------------------------

CREATE TABLE txaction (
    txac_id         SERIAL,
    tx_date         DATE NOT NULL,
    debet           INT NULL,
    debet_amount    NUMERIC (15, 5) NULL,
    credit          INT NULL,
    credit_amount   NUMERIC (15, 5) NULL,
    tx_comment      VARCHAR(250) NULL,
    PRIMARY KEY (txac_id),
    FOREIGN KEY (debet) REFERENCES account (acco_id),
    FOREIGN KEY (credit) REFERENCES account (acco_id)
);

COMMENT ON TABLE txaction Is 'Money transactions.';
