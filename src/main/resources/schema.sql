DROP TABLE IF EXISTS "dashboard";
DROP TABLE IF EXISTS "book";
DROP TABLE IF EXISTS "library_user";

CREATE TABLE "library_user" (
    "id" BIGSERIAL PRIMARY KEY,
    "full_name" VARCHAR(120) NOT NULL,
    "email" VARCHAR(120) UNIQUE NOT NULL,
    "phone" VARCHAR(30),
    "active" BOOLEAN NOT NULL DEFAULT TRUE,
    "role" VARCHAR(30) NOT NULL DEFAULT 'READER',
    CONSTRAINT "library_user_role_check"
        CHECK ("role" IN ('READER', 'STUDENT', 'ADMIN', 'LIBRARIAN', 'GUEST'))
);

CREATE TABLE "book" (
    "id" BIGSERIAL PRIMARY KEY,
    "title" VARCHAR(180) NOT NULL,
    "author" VARCHAR(120) NOT NULL,
    "isbn" VARCHAR(30) UNIQUE NOT NULL,
    "published_year" INT,
    "available" BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE "dashboard" (
    "id" BIGSERIAL PRIMARY KEY,
    "widget_name" VARCHAR(120) NOT NULL,
    "widget_type" VARCHAR(80) NOT NULL,
    "status" VARCHAR(30) NOT NULL,
    "display_order" INT NOT NULL
);
