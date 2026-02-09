CREATE TABLE accounts (
account_id UUID PRIMARY KEY,
account_number VARCHAR(255),
balance NUMERIC(38, 2),
user_id UUID
);