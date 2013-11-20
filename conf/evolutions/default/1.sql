# --- !Ups

CREATE TABLE User (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    fullname varchar(255) NOT NULL,
    isAdmin boolean NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO User (id,email,password,fullname,isAdmin) VALUES (1,'admin@test.com','password','Administrador',true);

# --- !Downs

DROP TABLE User;