const { Client } = require('pg');

const pgclient = new Client({
    host: process.env.POSTGRES_HOST,
    port: process.env.POSTGRES_PORT,
    user: 'postgres',
    password: 'postgres',
    database: 'reactlibrarydatabase'
});

pgclient.connect();

const table = [
    'DROP TABLE IF EXISTS review',
    'CREATE TABLE review (id SERIAL PRIMARY KEY , user_email varchar(45) DEFAULT NULL, date timestamp, rating numeric (3,2) DEFAULT NULL, book_id int, review_description text DEFAULT NULL)',
    'DROP TABLE IF EXISTS checkout',
    'CREATE TABLE checkout (id SERIAL PRIMARY KEY, user_email varchar(45) DEFAULT NULL,checkout_date varchar(45) DEFAULT NULL,return_date varchar(45) DEFAULT NULL,book_id int)',
    'DROP TABLE IF EXISTS messages',
    'CREATE TABLE messages (id SERIAL PRIMARY KEY , user_email varchar(45) DEFAULT NULL, title varchar(45) DEFAULT NULL,question text DEFAULT NULL, admin_email varchar(45) DEFAULT NULL, response text DEFAULT NULL, closed smallint DEFAULT 0)',
    'DROP TABLE IF EXISTS history',
    'CREATE TABLE history (id SERIAL PRIMARY KEY ,user_email varchar(45) DEFAULT NULL, checkout_date varchar(45) DEFAULT NULL, returned_date varchar(45) DEFAULT NULL, title varchar(45) DEFAULT NULL, author varchar(45) DEFAULT NULL, description text DEFAULT NULL, img bytea)'
]
const text = 'INSERT INTO student(firstname, lastname, age, address, email) VALUES($1, $2, $3, $4, $5) RETURNING *'
const values = ['Mona the', 'Octocat', 9, '88 Colin P Kelly Jr St, San Francisco, CA 94107, United States', 'octocat@github.com']


table.forEach((query) => {
    pgclient.query(query, (err, res) => {
        if (err) throw err
    });
});


/*
pgclient.query(text, values, (err, res) => {
    if (err) throw err
});

pgclient.query('SELECT * FROM student', (err, res) => {
    if (err) throw err
    console.log(err, res.rows) // Print the data in student table
    pgclient.end()
});
*/