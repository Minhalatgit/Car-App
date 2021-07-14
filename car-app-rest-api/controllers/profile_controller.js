'user strict';
const sql = require('../connection');

exports.getProfile = async (req, res) =>{

    try {
        const body = req.body;
        console.log(body)
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

exports.updateProfile = (req, res) =>{

    try {
        const body = JSON.parse(JSON.stringify(req.body));
        
        let query
        if (req.file) {
            let path = req.file.path
            path = path.replace("\\", "\\\\")
            console.log(path);

            query = `UPDATE user SET user_name = '${body.user_name}' , user_number = '${body.user_number}' , gender = '${body.gender}' , birthday = '${body.birthday}' , user_img = '${path}' WHERE user_id = '${body.profile_id}'`
        } else {
            query = `UPDATE user SET user_name = '${body.user_name}' , user_number = '${body.user_number}' , gender = '${body.gender}' , birthday = '${body.birthday}' WHERE user_id = '${body.profile_id}'`
        }

        console.log("query", query)

        sql.query(query, (updateError, row) => {
            if(!updateError) {
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
                    } else {
                        res.send(err);
                    }
                })
            } else{
                console.log(updateError)
                res.send(updateError);
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

exports.getUsers = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('SELECT * FROM user', [ body.profile_id ], (err, result) => {
            if(!err) {
                console.log(result[0])
                return res.json({
                    status: true,
                    msg: "Users fetched successfully",
                    data: result
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