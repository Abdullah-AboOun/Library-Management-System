-- Seeding users
INSERT INTO users (userName, password, fullName, role, email, phoneNumber, imagePath)
VALUES ('ahmad', '123', 'Ahmad', 'ADMIN', 'ahmad@gmail.com', '123456789',
        'com/example/libraryManagementSystem/images/default.png'),
       ('ali', '123', 'Ali', 'ADMIN', 'ali@gmail.com', '123456789',
        'com/example/libraryManagementSystem/images/default.png'),
       ('sara', '123', 'Sara', 'LIBRARIAN', 'Sara@gmail.com', '123456789',
        'com/example/libraryManagementSystem/images/default.png'),
       ('nada', '123', 'Nada', 'USER', 'nada@gmail.com', '123456789',
        'com/example/libraryManagementSystem/images/default.png');

-- Seeding books
INSERT INTO books (title, author, dateOfPublication, ISBN, language, category, publisher, imagePath, pagesNumber,
                   copiesNumber)
VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', '1925', '34556545', 'English', 'Literature', 'Scribner',
        'com/example/libraryManagementSystem/images/book.png', 180, 5),
       ('To Kill a Mockingbird', 'Harper Lee', '1960', '87645632', 'English', 'Literature', 'J. B. Lippincott & Co.',
        'com/example/libraryManagementSystem/images/book.png', 281, 3),
       ('1984', 'George Orwell', '1949', '45355478', 'English', 'Science Fiction', 'Secker & Warburg',
        'com/example/libraryManagementSystem/images/book.png',
        328, 2),
       ('Pride and Prejudice', 'Jane Austen', '1813', '53453245', 'English', 'Literature', 'T. Egerton, Whitehall',
        'com/example/libraryManagementSystem/images/book.png', 279, 4),
       ('The Catcher in the Rye', 'J. D. Salinger', '1951', '75742221', 'English', 'Literature',
        'Little, Brown and Company', 'com/example/libraryManagementSystem/images/book.png', 234, 6),
       ('الأرض', 'علي الطنطاوي', '2021', '87453454', 'Arabic', 'Science', 'المكتبة العصرية',
        'com/example/libraryManagementSystem/images/book.png', 200,
        10),
       ('Der Vorleser', 'Bernhard Schlink', '1995', '23787653', 'German', 'Literature', 'Diogenes',
        'com/example/libraryManagementSystem/images/book.png',
        300, 7),
       ('L\Étranger', 'Albert Camus', '1942', '23765498', 'French', 'Literature', 'Gallimard',
        'com/example/libraryManagementSystem/images/book.png', 250,
        8),
       ('La Peste', 'Albert Camus', '1947', '23765451', 'French', 'Literature', 'Gallimard',
        'com/example/libraryManagementSystem/images/book.png', 300, 6),
        ('La Chute', 'Albert Camus', '1956', '23765452', 'French', 'Literature', 'Gallimard',
        'com/example/libraryManagementSystem/images/book.png', 200, 5);
        