CREATE TABLE course
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    datetime NULL,
    updated_at    datetime NULL,
    title         VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    price DOUBLE NULL,
    learners      BIGINT NULL,
    language      enum('ENGLISH',
        'HINDI',
        'SPANISH',
        'FRENCH',
        'CHINESE'),
    instructor_id BIGINT NULL,
    CONSTRAINT pk_course PRIMARY KEY (id)
);

CREATE TABLE enrollments
(
    student_id BIGINT NOT NULL,
    course_id  BIGINT NOT NULL,
    CONSTRAINT pk_enrollments PRIMARY KEY (student_id, course_id)
);

CREATE TABLE instructor
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    created_at     datetime NULL,
    updated_at     datetime NULL,
    about          VARCHAR(255) NULL,
    total_students BIGINT NULL,
    user_id        BIGINT NULL,
    CONSTRAINT pk_instructor PRIMARY KEY (id)
);

CREATE TABLE review
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    name       VARCHAR(255) NULL,
    rating     FLOAT NULL,
    comment    VARCHAR(255) NULL,
    user_id    BIGINT NULL,
    course_id  BIGINT NULL,
    CONSTRAINT pk_review PRIMARY KEY (id)
);

CREATE TABLE student
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    user_id    BIGINT NULL,
    CONSTRAINT pk_student PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE instructor
    ADD CONSTRAINT uc_instructor_user UNIQUE (user_id);

ALTER TABLE student
    ADD CONSTRAINT uc_student_user UNIQUE (user_id);

ALTER TABLE course
    ADD CONSTRAINT FK_COURSE_ON_INSTRUCTOR FOREIGN KEY (instructor_id) REFERENCES instructor (id);

ALTER TABLE instructor
    ADD CONSTRAINT FK_INSTRUCTOR_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_USER FOREIGN KEY (user_id) REFERENCES student (id);

ALTER TABLE student
    ADD CONSTRAINT FK_STUDENT_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE enrollments
    ADD CONSTRAINT fk_enr_on_course FOREIGN KEY (course_id) REFERENCES course (id);

ALTER TABLE enrollments
    ADD CONSTRAINT fk_enr_on_student FOREIGN KEY (student_id) REFERENCES student (id);