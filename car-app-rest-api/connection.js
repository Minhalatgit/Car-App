const mysql = require('mysql');

// for local
const connection = mysql.createConnection({
    host            :   'localhost',
    user            :   'root',
    password        :   '',
    database        :   'car_app_db'
});

//for live
// const connection = mysql.createConnection({
//     host            :   'webprojectmockup',
//     user            :   'car_app_db',
//     password        :   '6(;p25WyF0ng',
//     database        :   'car_app_db'
// });

connection.connect((err) =>{
    if(err) console.log(`Database connection failed ${err}`);
    else console.log('Database connection successful');
});

module.exports = connection;