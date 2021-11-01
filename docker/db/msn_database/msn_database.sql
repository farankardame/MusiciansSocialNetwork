CREATE TABLE IF NOT EXISTS Artist
(
    id                             SERIAL      NOT NULL PRIMARY KEY,
    created_date                   TIMESTAMP   NOT NULL DEFAULT now(),
    updated_date                   TIMESTAMP   NOT NULL DEFAULT now(),
    name                           TEXT,
    description                    TEXT
);

CREATE TABLE IF NOT EXISTS Band
(
    id                             SERIAL      NOT NULL PRIMARY KEY,
    created_date                   TIMESTAMP   NOT NULL DEFAULT now(),
    updated_date                   TIMESTAMP   NOT NULL DEFAULT now(),
    name                           TEXT,
    description                    TEXT
);

CREATE TABLE IF NOT EXISTS Skill
(
    id                             SERIAL      NOT NULL PRIMARY KEY,
    created_date                   TIMESTAMP   NOT NULL DEFAULT now(),
    updated_date                   TIMESTAMP   NOT NULL DEFAULT now(),
    name                           TEXT,
    description                    TEXT
);

CREATE TABLE IF NOT EXISTS Profile_Message
(
    id                             SERIAL      NOT NULL PRIMARY KEY,
    artist_id                      INT,
    band_id                      INT,
    created_date                   TIMESTAMP   NOT NULL DEFAULT now(),
    updated_date                   TIMESTAMP   NOT NULL DEFAULT now(),
    message                        TEXT,
    CONSTRAINT fk_messageartist FOREIGN KEY(artist_id) REFERENCES artist(id),
    CONSTRAINT fk_messageband FOREIGN KEY(band_id) REFERENCES band(id)
);

CREATE TABLE IF NOT EXISTS Artist_Skill
(
    id                             SERIAL      NOT NULL PRIMARY KEY,
    artist_id                     INT,
    skill_id                      INT,
    CONSTRAINT fk_artist FOREIGN KEY(artist_id) REFERENCES artist(id),
    CONSTRAINT fk_skill FOREIGN KEY(skill_id) REFERENCES skill(id)
);

CREATE TABLE IF NOT EXISTS Band_Artist
(
    id                             SERIAL      NOT NULL PRIMARY KEY,
    band_id                     INT,
    artist_id                      INT,
    CONSTRAINT fk_band FOREIGN KEY(band_id) REFERENCES band(id),
    CONSTRAINT fk_bandartist FOREIGN KEY(artist_id) REFERENCES artist(id)
);
