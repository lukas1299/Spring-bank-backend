CREATE TABLE credit_cards(
credit_card_id UUID PRIMARY KEY,
card_number VARCHAR(255),
card_limit NUMERIC(38, 2),
status VARCHAR(255),
account_id UUID,
FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);