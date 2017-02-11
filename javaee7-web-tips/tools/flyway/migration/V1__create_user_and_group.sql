CREATE TABLE "user_entity" (
    "user_id" VARCHAR(10) NOT NULL,
    "password" VARCHAR (128) NOT NULL,
    "last_name" VARCHAR (32) ,
    "first_name" VARCHAR (32) ,
    "gender" CHAR (1) ,
    "deleted_flg" CHAR(1) DEFAULT '0' NOT NULL,
    "created" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "created_by" VARCHAR(10) NOT NULL,
    "updated" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_by" VARCHAR(10),
    PRIMARY KEY ("user_id")
);

CREATE TABLE "group_entity" (
    "group_code" CHAR(8) NOT NULL,
    "group_name" VARCHAR (64) ,
    "deleted_flg" CHAR(1) DEFAULT '0' NOT NULL,
    "created" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "created_by" VARCHAR(10) NOT NULL,
    "updated" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_by" VARCHAR(10),
    PRIMARY KEY ("group_code")
);

CREATE TABLE "user_group_relation" (
    "user_id" VARCHAR(10) NOT NULL ,
    "group_code" CHAR (8) NOT NULL,
    "deleted_flg" CHAR(1) DEFAULT '0' NOT NULL,
    "created" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "created_by" VARCHAR(10) NOT NULL,
    "updated" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    "updated_by" VARCHAR(10),
    FOREIGN KEY ("user_id") REFERENCES "user_entity" ("user_id") ,
    FOREIGN KEY ("group_code") REFERENCES "group_entity" ("group_code") ,
    PRIMARY KEY ("user_id", "group_code")
);
