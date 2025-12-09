-- Insert into publishers
insert into publishers (created_date, edited_date, address, contact_details, name)
values (now(), now(), 'www.bmg.com', 'This is a great service both for recording and music publishing', 'BMG'),
       (now(), now(), 'https://concord.com/',
        'They have incredible connections and know many influential people in the music industry', 'Concord');

-- Insert into genres
insert into genres (created_date, edited_date, name, description)
values (now(), now(), 'Soul', 'It is good for your soul'),
       (now(), now(), 'Jazz', 'Jazz hands and careless whispers');

-- -- Insert into albums
insert into albums (created_date, edited_date, title, release_year, publisher_id, genre_id)
values (now(), now(), 'Test', 2014, 1, 1),
       (now(), now(), 'Test2', 2018, 2, 2);

-- -- Insert into stock
insert into stock (created_date, edited_date, condition, price, album_id)
values (now(), now(), 'good', 19.95, 1),
       (now(), now(), 'excellent', 24.95, 2);

-- -- Insert into artists
insert into artists (created_date, edited_date, name, biography)
values (now(), now(), 'Henkie', 'Lekker verhaal'),
       (now(), now(), 'Pietje', 'Een nog beter verhaal');

insert into album_artist (album_id, artist_id)
values (1, 1),
       (2, 2);