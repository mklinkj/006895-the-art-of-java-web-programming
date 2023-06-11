DROP TABLE IF EXISTS t_member CASCADE;

CREATE TABLE t_member
(
    id        VARCHAR(10) PRIMARY KEY,
    pwd       VARCHAR(200) NOT NULL,
    name      VARCHAR(50) NOT NULL,
    email     VARCHAR(50) NOT NULL,
    joinDate DATE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

INSERT INTO t_member (id, pwd, name, email, joinDate)
VALUES ('hong', '1212', '홍길동', 'hong@gamil.com', '2023-02-02');

INSERT INTO t_member (id, pwd, name, email, joinDate)
VALUES ('lee', '1212', '이순신', 'lee@test.com', '2023-02-03');

INSERT INTO t_member (id, pwd, name, email, joinDate)
VALUES ('kim', '1212', '김유신', 'hong@gamil.com', '2023-02-04');

INSERT INTO t_member (id, pwd, name, email, joinDate)
VALUES ('choi', '1212', '최치원', 'choi@gamil.com', '2023-02-04');
