CREATE TABLE IF NOT EXISTS books (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    author TEXT NOT NULL,
    dateOfPublication TEXT,
    ISBN TEXT UNIQUE NOT NULL,
    language TEXT,
    category TEXT,
    publisher TEXT,
    imagePath TEXT,
    pagesNumber INTEGER,
    copiesNumber INTEGER
);
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    userName TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    fullName TEXT NOT NULL,
    role TEXT NOT NULL,
    email TEXT NOT NULL,
    phoneNumber TEXT,
    imagePath TEXT
);
CREATE TABLE IF NOT EXISTS book_registrations (
    user_id INTEGER NOT NULL,
    book_id INTEGER NOT NULL,
    isApproved BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (user_id, book_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS book_categories (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT UNIQUE NOT NULL
);
CREATE TABLE IF NOT EXISTS book_languages (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT UNIQUE NOT NULL
);