CREATE TABLE t_users (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         username VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         PRIMARY KEY (id),
                         CONSTRAINT uk_users_username UNIQUE (username)
);

CREATE TABLE t_workout (
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           title VARCHAR(255) NOT NULL,
                           note TEXT,
                           type VARCHAR(50),
                           category VARCHAR(50),
                           duration_minutes INT,
                           scheduled_date_time DATETIME,
                           is_completed TINYINT(1) NOT NULL DEFAULT 0,
                           user_id BIGINT NOT NULL,
                           PRIMARY KEY (id),
                           CONSTRAINT fk_workout_user
                               FOREIGN KEY (user_id) REFERENCES t_users(id)
);

CREATE TABLE t_exercise (
                            id BIGINT NOT NULL AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL,
                            sets INT NOT NULL,
                            reps INT NOT NULL,
                            weight DOUBLE NOT NULL,
                            workout_id BIGINT NOT NULL,
                            PRIMARY KEY (id),
                            CONSTRAINT fk_exercise_workout
                                FOREIGN KEY (workout_id) REFERENCES t_workout(id)
);
