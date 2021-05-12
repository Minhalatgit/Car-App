'user strict';
const sql = require('../connection');
const jwt = require('jsonwebtoken');
const nodemailer = require('nodemailer');
const { loginSchema, registerSchema } = require('../helper/validation_schema');

async function sendEmail(email, code) {
    let transporter = nodemailer.createTransport({
        host: 'smtp.gmail.com', // Gmail Host
        port: 587, // Port
        secure: false, // this is true as port is 465
        auth: {
            user: 'minhaj123technado@gmail.com', // generated ethereal user
            pass: 'geeks&bachelors', // generated ethereal password
        },
    });

    // send mail with defined transport object
    let info = await transporter.sendMail({
        from: '"minhaj123technado@gmail.com', // sender address
        to: email, // list of receivers
        subject: "Verification Code", // Subject line
        text: `Your verification code is ${code}`, // plain text body
    });

    console.log("Message sent: %s", info);
    console.log("Message sent: %s", info.messageId);

    return info.messageId
}

exports.register = async (req, res) =>{

    try {
        const body = req.body;

        //validating email, password and repeat password

        // try {
        //     const result = await registerSchema.validateAsync(body);
        //     console.log(result);
        //     return res.json({
        //         status: true,
        //         msg: result
        //     })
        // } catch (err) {
        //     return res.json({
        //         status: false,
        //         msg: err.message
        //     })
        // }

        // checking if user exists against this email
        sql.query('SELECT * FROM user WHERE user_email = ?', [ body.email ], (err, row) => {
            if(!err) {
                if(row.length > 0){
                   //user already exists
                    return res.json({
                        status: false,
                        msg: "User already exists"
                    })
                } else {
                   //user not exists 

                   const verificationCode = Math.floor(Math.random() * 10000)

                   sql.query('INSERT INTO user (user_email, user_password, verification_code) VALUES (?,?,?)', [body.email, body.password, verificationCode] , (err, rows) =>{
                        if(err) return res.json({
                            status: false,
                            msg: err,
                        })  
                        console.log(rows);

                        //send verification code to email of user
                        sendEmail(body.email,verificationCode).then(()=>{
                            console.log('Email sent')
                                return res.json({
                                    status: true,
                                    msg: "User registered successfully, check your email",
                                })
                        }).catch(()=>{
                            console.log('Email sending failed')
                            return res.json({
                                status: false,
                                msg: "Verification failed, try again",
                            })
                        })

                   })  
                }
                
            } else{
                res.send(err);
            }
        })
    } catch(e) {
        console.log('Catch an error: ', e)
        return res.json({
            status: false,
            msg: "Something went wrong",
        }) 
    }
};

exports.verify = async (req, res) =>{

    try {
        const body = req.body;

        // checking if user exists against this email
        sql.query('SELECT * FROM user WHERE user_email = ?', [ body.email ], (err, row) => {
            if(!err) {
                console.log(row)
                if(row.length > 0){
                   //user already exists
                   console.log('User exist in db')

                   console.log(row[0])
                   console.log(body.verification_code)

                   if(row[0].verification_code == body.verification_code){
                       console.log('Code is matched');
                       sql.query('UPDATE user SET status = ? WHERE user_email = ? ', [1, body.email] , (err, rows) =>{
                           if(err) return res.json({
                               status: false,
                               msg: err
                            })
                            return res.json({
                                status: true,
                                msg: 'Verified successfully'
                            })
                        })  
                    } else {
                       console.log('Code not matched');
                       return res.json({
                        status: false,
                        msg: 'Incorrect code'
                    })
                   }

                } else {
                   //user not exists 
                    console.log('User does not exist in db')                
                    return res.json({
                        status: false,
                        msg: 'User does not exist',
                    })
                }
                
            } else{
                res.send(err);
            }
        })
    } catch(e) {
        console.log('Catch an error: ', e)
        return res.json({
            status: false,
            msg: "Something went wrong",
        }) 
    }
};

exports.login = async (req, res) =>{

    try {
        const body = req.body;

        //validatiing email and password here
        try {
            const result = await loginSchema.validateAsync(body);
            console.log(result);
        } catch (err) {
            return res.json({
                status: false,
                msg: err.message
            })
        }

        //checking if user exists against this email
        sql.query('SELECT * FROM user WHERE user_email = ?', [ body.email ], (err, row) => {
            if(!err) {
                if(row.length > 0){

                    //user exists match its email and password
                    const id = row[0].user_id;
                    const email = row[0].user_email;
                    const username = row[0].user_name;
                    const user_number = row[0].user_number;
                    const user_img = row[0].user_img;
                    const user_address = row[0].user_address;
                    const member_type = row[0].member_type;
                    const gender = row[0].gender;
                    const birthday = row[0].birthday;
                    const status = row[0].status;
                    const password = row[0].user_password;

                    if(email == body.email && password == body.password){

                    const token = jwt.sign({ password: password }, 'todo-app-super-shared-secret');

                        sql.query('UPDATE User SET user_token = ? WHERE user_id = ?', [token, id] , (err, row) => {
                            if(!err){
                                return res.json({
                                    status: true,
                                    msg: "Login successful",
                                    data: {
                                        id,
                                        email,
                                        username,
                                        user_number,
                                        user_address,
                                        member_type,
                                        gender,
                                        birthday,
                                        user_img,
                                        status,
                                        token
                                    }
                                })
                            } else {
                                console.log(err);
                                return res.json({
                                    status: false,
                                    msg: "Token not updated"
                                })
                            }
                        })
            
                   } else {
                        return res.json({
                            status: false,
                            msg: "Bad credentials"
                        })
                   }

                } else {
                   //user not exists
                    return res.json({
                        status: false,
                        msg: "User not found"
                    })  
                }
                
            } else{
                return res.send(err);
            }
        })
    } catch(e) {
        console.log('Catch an error: ', e)
    }
};