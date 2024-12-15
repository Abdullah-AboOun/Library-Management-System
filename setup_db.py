import sqlite3
import os

def init_database():
    # Delete existing database if it exists
    if os.path.exists('./src/main/resources/database.db'):
#         exit() # exit the code if the database already exists
        os.remove('./src/main/resources/database.db')
    
    # Connect to database (creates if not exists)
    conn = sqlite3.connect('./src/main/resources/database.db')
    cursor = conn.cursor()
    
    # Read and execute schema
    with open('schema.sql', 'r') as schema_file:
        cursor.executescript(schema_file.read())
    
    # Read and execute seed data
    with open('seed.sql', 'r') as seed_file:
        cursor.executescript(seed_file.read())
    
    # Commit and close
    conn.commit()
    conn.close()
    print("Database initialized successfully!")

if __name__ == "__main__":
    init_database()