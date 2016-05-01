-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2016-04-29 12:35:41.364

-- tables
-- Table: Statistic
CREATE TABLE Statistic (
    id int NOT NULL,
    title varchar(50) NOT NULL,
    weight double(3,2) NOT NULL,
    value bigint NOT NULL,
    CONSTRAINT Statistic_pk PRIMARY KEY (id)
);

-- Table: achievement
CREATE TABLE achievement (
    id int NOT NULL,
    title varchar(50) NOT NULL,
    criteria blob NOT NULL COMMENT 'list of statistics to be satisfied',
    CONSTRAINT achievement_pk PRIMARY KEY (id)
);

-- Table: event
CREATE TABLE event (
    id int NOT NULL,
    start_time time NOT NULL,
    end_time time NOT NULL,
    source blob NOT NULL COMMENT 'attacker player object',
    destination blob NOT NULL COMMENT 'player object attacked',
    performed_action blob NOT NULL COMMENT 'statistic object being modifed because of this attack',
    CONSTRAINT event_pk PRIMARY KEY (id)
);

-- Table: game
CREATE TABLE game (
    id int NOT NULL,
    start_time time NOT NULL,
    end_time time NOT NULL,
    team_1 blob NOT NULL COMMENT 'team 1 object',
    team_2 blob NOT NULL COMMENT 'tetam 2 object',
    team_size int NOT NULL,
    events blob NOT NULL COMMENT 'list of events occured in game',
    team_1_jointimes blob NOT NULL COMMENT 'join times of team 1 players',
    team_2_jointimes blob NOT NULL COMMENT 'join times of team 2 players',
    CONSTRAINT game_pk PRIMARY KEY (id)
) COMMENT ''
COMMENT 'The game that happens between two teams';

-- Table: player
CREATE TABLE player (
    id int NOT NULL,
    name varchar(50) NOT NULL,
    team blob NOT NULL COMMENT 'team of the player',
    achievements blob NOT NULL COMMENT 'list of achievements earned by player',
    CONSTRAINT player_pk PRIMARY KEY (id)
);

-- Table: team
CREATE TABLE team (
    id int NOT NULL,
    name varchar(50) NOT NULL,
    players blob NOT NULL COMMENT 'list of team players',
    size int NOT NULL,
    CONSTRAINT team_pk PRIMARY KEY (id)
);

-- End of file.

