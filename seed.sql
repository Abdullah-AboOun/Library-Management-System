-- Seeding users
INSERT INTO users (
                userName,
                password,
                fullName,
                role,
                email,
                phoneNumber,
                imagePath
        )
VALUES (
                'ahmad',
                '123',
                'Ahmad',
                'ADMIN',
                'ahmad@gmail.com',
                '123456789',
                'src/main/resources/com/example/libraryManagementSystem/images/default.png'
        ),
        (
                'ali',
                '123',
                'Ali',
                'ADMIN',
                'ali@gmail.com',
                '123456789',
                'src/main/resources/com/example/libraryManagementSystem/images/default.png'
        ),
        (
                'sara',
                '123',
                'Sara',
                'LIBRARIAN',
                'Sara@gmail.com',
                '123456789',
                'src/main/resources/com/example/libraryManagementSystem/images/default.png'
        ),
        (
                'nada',
                '123',
                'Nada',
                'USER',
                'nada@gmail.com',
                '123456789',
                'src/main/resources/com/example/libraryManagementSystem/images/default.png'
        );
-- Seeding books
INSERT INTO books (
                title,
                author,
                dateOfPublication,
                ISBN,
                language,
                category,
                publisher,
                imagePath,
                pagesNumber,
                copiesNumber
        )
VALUES (
                '1984',
                'George Orwell',
                '1949',
                '978-0451524935',
                'English',
                'Fiction',
                'Secker and Warburg',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                328,
                7
        ),
        (
                'The Little Prince',
                'Antoine de Saint-Exupéry',
                '1943',
                '978-0156012195',
                'French',
                'Children Literature',
                'Gallimard',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                96,
                5
        ),
        (
                'The Name of the Rose',
                'Umberto Eco',
                '1980',
                '978-0156001311',
                'Italian',
                'Mystery',
                'Bompiani',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                536,
                4
        ),
        (
                'Don Quixote',
                'Miguel de Cervantes',
                '1605',
                '978-0142437230',
                'Spanish',
                'Fiction',
                'Francisco de Robles',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                1023,
                3
        ),
        (
                'ألف ليلة وليلة',
                'Unknown',
                '900',
                '978-9772937615',
                'Arabic',
                'Fantasy',
                'دار المعارف',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                1001,
                4
        ),
        (
                'Das Parfum',
                'Patrick Süskind',
                '1985',
                '978-3257229530',
                'German',
                'Horror',
                'Diogenes',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                263,
                3
        ),
        (
                'The Silent Patient',
                'Alex Michaelides',
                '2019',
                '978-1250301697',
                'English',
                'Mystery',
                'Celadon Books',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                325,
                6
        ),
        (
                'Le Petit Nicolas',
                'René Goscinny',
                '1959',
                '978-2070364237',
                'French',
                'Children Literature',
                'Denoël',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                167,
                4
        ),
        (
                'A Brief History of Time',
                'Stephen Hawking',
                '1988',
                '978-0553380163',
                'English',
                'Non-Fiction',
                'Bantam',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                212,
                5
        ),
        (
                'The Divine Comedy',
                'Dante Alighieri',
                '1320',
                '978-0142437223',
                'Italian',
                'Poetry',
                'Penguin Classics',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                928,
                3
        ),
        (
                'Foundation',
                'Isaac Asimov',
                '1951',
                '978-0553293357',
                'English',
                'Science Fiction',
                'Gnome Press',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                255,
                6
        ),
        (
                'Romeo and Juliet',
                'William Shakespeare',
                '1597',
                '978-0743477116',
                'English',
                'Drama',
                'Simon & Schuster',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                336,
                8
        ),
        (
                'Pride and Prejudice',
                'Jane Austen',
                '1813',
                '978-0141439518',
                'English',
                'Romance',
                'Penguin Classics',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                480,
                7
        ),
        (
                'Physics for Scientists and Engineers',
                'Raymond A. Serway',
                '2018',
                '978-1337553292',
                'English',
                'Educational',
                'Cengage Learning',
                'src/main/resources/com/example/libraryManagementSystem/images/book.png',
                1484,
                4
        );
-- Keep existing category and language seeding...
-- Seed book categories
INSERT INTO book_categories (name)
VALUES ('Children Literature'),
        ('Horror'),
        ('Mystery'),
        ('Fiction'),
        ('Non-Fiction'),
        ('Fantasy'),
        ('Science Fiction'),
        ('Romance'),
        ('Drama'),
        ('Poetry'),
        ('Educational');
-- Seed book languages  
INSERT INTO book_languages (name)
VALUES ('English'),
        ('French'),
        ('Italian'),
        ('Spanish'),
        ('German'),
        ('Arabic'),
        ('Chinese'),
        ('Japanese'),
        ('Russian');