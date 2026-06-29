-- ユーザテーブル
CREATE TABLE IF NOT EXISTS login (
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_login PRIMARY KEY (username),
    CONSTRAINT uq_login_username UNIQUE (username)
);

-- タスクテーブル
CREATE TABLE IF NOT EXISTS tasks (
    id         SERIAL        NOT NULL,
    username   VARCHAR(50)   NOT NULL,
    title      VARCHAR(255)  NOT NULL,
    content    TEXT,
    name       VARCHAR(255),
    start_date DATE,
    end_date   DATE,
    created_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_tasks PRIMARY KEY (id),
    CONSTRAINT fk_tasks_username FOREIGN KEY (username) REFERENCES login(username)
);