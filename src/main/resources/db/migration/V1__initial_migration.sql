CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE categories
(
    id   TINYINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE courses
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    category_id TINYINT      NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE topics
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    message    TEXT         NOT NULL,
    created_at DATE                        DEFAULT (CURDATE()),
    status     ENUM ('UNSOLVED', 'SOLVED') DEFAULT 'UNSOLVED',
    user_id    BIGINT       NOT NULL,
    course_id  BIGINT       NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (course_id) REFERENCES courses (id)
);

CREATE TABLE answers
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    message    TEXT   NOT NULL,
    created_at DATE    DEFAULT (CURDATE()),
    solution   BOOLEAN DEFAULT FALSE,
    topic_id   BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    FOREIGN KEY (topic_id) REFERENCES topics (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);