-- --------------------------------------
-- Create tables, constraints and indexes
-- --------------------------------------

create table role
(
    name            varchar(255)                          not null,
    created_on      timestamp default CURRENT_TIMESTAMP() not null,
    created_by      bigint                                not null,
    updated_on      timestamp,
    updated_by      bigint,
    is_default_role boolean   default FALSE               not null,
    primary key (name)
);

create table role_title
(
    role_name varchar(255) not null,
    text      varchar(255) not null,
    language  varchar(10)  not null,
    primary key (role_name, language)
);

create table user
(
    id                     bigint generated by default as identity,
    created_on             timestamp default CURRENT_TIMESTAMP() not null,
    created_by             bigint                                not null,
    updated_on             timestamp,
    updated_by             bigint,
    encoded_password       varchar(255)                          not null,
    full_name              varchar(255)                          not null,
    username               varchar(255)                          not null,
    account_expires_on     timestamp,
    is_locked              boolean   default FALSE               not null,
    locked_reason          varchar(255),
    credentials_expires_on timestamp,
    primary key (id)
);

create table user_role
(
    role_name  varchar(255) not null,
    user_id    bigint       not null,
    expires_at timestamp    not null,
    primary key (role_name, user_id)
);

alter table user
    add constraint UC_user_unique_username unique (username);

alter table role_title
    add constraint FK_role_name_on_role
        foreign key (role_name)
            references role;

alter table user_role
    add constraint FK_name_on_role
        foreign key (role_name)
            references role;

alter table user_role
    add constraint FK_user_id_on_user
        foreign key (user_id)
            references user;

-- --------------------------------------
-- Add base data
-- --------------------------------------

-- Default Roles
insert into role (created_by, name, is_default_role)
values (1, 'ROLE_SYSTEM', TRUE),
       (1, 'ROLE_USER', TRUE),
       (1, 'ROLE_ADMIN', TRUE);

insert into role_title(role_name, language, text)
values ('ROLE_SYSTEM', 'DANISH', 'System-rolle (kan ikke tildeles brugere som oprettes)'),
       ('ROLE_SYSTEM', 'ENGLISH', 'System role (not applicable to users being created)'),
       ('ROLE_USER', 'DANISH', 'Standard bruger-rolle'),
       ('ROLE_USER', 'ENGLISH', 'Default user role'),
       ('ROLE_ADMIN', 'DANISH', 'Standard administrator-rolle'),
       ('ROLE_ADMIN', 'ENGLISH', 'Default administrator role');

-- Default Users
-- ... (encoded password is encoded form of "password")
insert into user (created_by, encoded_password, full_name, username)
values (1, '', 'System', 'system'),
       (1, '$2a$10$DdO0U8excIQkeOofYOZqfOaeIot8zBijDy6H..2Ta/UJhaMseklOS', 'Anders Skarby (bruger)', 'askarby'),
       (1, '$2a$10$DdO0U8excIQkeOofYOZqfOaeIot8zBijDy6H..2Ta/UJhaMseklOS', 'Anders Skarby (administrator)', 'admin');

-- Assigning users with roles
insert into user_role (role_name, user_id, expires_at)
values ('ROLE_SYSTEM', 1, PARSEDATETIME('2100-01-01 00:00:00 UTC', 'yyyy-MM-dd HH:mm:ss z', 'en', 'UTC')),
       ('ROLE_USER', 2, PARSEDATETIME('2100-01-01 00:00:00 UTC', 'yyyy-MM-dd HH:mm:ss z', 'en', 'UTC')),
       ('ROLE_USER', 3, PARSEDATETIME('2100-01-01 00:00:00 UTC', 'yyyy-MM-dd HH:mm:ss z', 'en', 'UTC')),
       ('ROLE_ADMIN', 3, PARSEDATETIME('2100-01-01 00:00:00 UTC', 'yyyy-MM-dd HH:mm:ss z', 'en', 'UTC'));


