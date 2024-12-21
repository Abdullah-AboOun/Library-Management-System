import sqlite3
import os


def init_database():
    # Delete existing database if it exists
    if os.path.exists("./src/main/resources/database.db"):
        exit("Database already exists! Please delete it first.")
        os.remove("./src/main/resources/database.db")

    # Connect to database (creates if not exists)
    conn = sqlite3.connect("./src/main/resources/database.db")
    cursor = conn.cursor()

    # Read and execute schema with UTF-8 encoding
    with open("schema.sql", "r", encoding="utf-8") as schema_file:
        cursor.executescript(schema_file.read())

    # Read and execute seed data with UTF-8 encoding
    with open("seed.sql", "r", encoding="utf-8") as seed_file:
        cursor.executescript(seed_file.read())

    # Commit and close
    conn.commit()
    conn.close()
    print("Database initialized successfully!")


if __name__ == "__main__":
    init_database()
