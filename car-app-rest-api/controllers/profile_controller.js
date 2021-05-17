'user strict';
const sql = require('../connection');


exports.getProfile = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('SELECT * FROM user WHERE user_id = ? ', [ body.profile_id ], (err, result) => {
            if(!err) {
                console.log(result[0])
                return res.json({
                    status: true,
                    msg: "Profile fetched successfully",
                    data: {
                        user_id : result[0].user_id,
                        username : result[0].user_name,
                        email : result[0].user_email,
                        user_number : result[0].user_number,
                        gender : result[0].gender,
                        birthday : result[0].birthday,
                        user_img : result[0].user_img,
                    }
                })
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

exports.updateProfile = async (req, res) =>{

    try {
        const body = req.body;
        console.log(body);
        sql.query('UPDATE user SET user_name = ? , user_number = ? , gender = ? , birthday = ? , user_img = ? WHERE user_id = ?', [ body.user_name, body.user_number, body.gender, body.birthday, req.file.path , body.profile_id], (err, row) => {
            if(!err) {
                console.log(row)
                sql.query('SELECT * FROM user WHERE user_id = ? ', [ body.profile_id ], (err, result) => {
                    if(!err) {
                        console.log(result[0])
                        return res.json({
                            status: true,
                            msg: "Profile updated successfully",
                            data: {
                                user_id : result[0].user_id,
                                username : result[0].user_name,
                                email : result[0].user_email,
                                user_number : result[0].user_number,
                                gender : result[0].gender,
                                birthday : result[0].birthday,
                                user_img : result[0].user_img
                            }
                        })
                    } else{
                        res.send(err);
                    }
                })
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