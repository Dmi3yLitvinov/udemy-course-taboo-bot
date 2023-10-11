CREATE TABLE IF NOT EXISTS user_entity
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(50),
    last_name   VARCHAR(50),
    username    VARCHAR(50) UNIQUE,
    telegram_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS chat
(
    id               BIGSERIAL PRIMARY KEY,
    title            VARCHAR(100),
    telegram_chat_id BIGINT      NOT NULL UNIQUE,
    chat_bot_status  VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS game
(
    id      BIGSERIAL PRIMARY KEY,
    status  VARCHAR(30) NOT NULL,
    chat_id BIGINT      NOT NULL REFERENCES chat (id)
);

CREATE TABLE IF NOT EXISTS user_game
(
    id        BIGSERIAL   PRIMARY KEY,
    user_id   BIGINT      NOT NULL REFERENCES user_entity (id),
    game_id   BIGINT      NOT NULL REFERENCES game (id),
    game_role VARCHAR(30) NOT NULL,
    score     SMALLINT    NOT NULL DEFAULT 0,
    explained BOOLEAN     DEFAULT false NOT NULL
);

CREATE TABLE IF NOT EXISTS card
(
    id          BIGSERIAL PRIMARY KEY,
    answer      VARCHAR NOT NULL,
    taboos      VARCHAR[],
    all_taboos  VARCHAR[]
);

CREATE TABLE IF NOT EXISTS game_card
(
    id      BIGSERIAL PRIMARY KEY,
    card_id INT         NOT NULL REFERENCES card (id),
    game_id INT         NOT NULL REFERENCES game (id),
    status  VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS word
(
    id           BIGSERIAL PRIMARY KEY,
    word         VARCHAR(40) NOT NULL,
    init_form_id INT REFERENCES word (id)
);
CREATE INDEX idx_word_word ON word (word);

CREATE TABLE IF NOT EXISTS wait_room
(
    id          BIGSERIAL   PRIMARY KEY,
    hash        VARCHAR(36) NOT NULL UNIQUE,
    status      VARCHAR(30) NOT NULL,
    chat_id     BIGINT      NOT NULL REFERENCES chat (id),
    message_id  BIGINT
);

CREATE TABLE IF NOT EXISTS wait_room_user
(
    user_id      BIGINT NOT NULL REFERENCES user_entity (id),
    wait_room_id BIGINT NOT NULL REFERENCES wait_room (id),
    PRIMARY KEY (user_id, wait_room_id)
);