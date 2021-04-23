'user strict';
const sql = require('../connection');
const multer = require('multer');
const path = require('path');
const { authSchema } = require('../helper/validation_schema');

const allowedFileTypes = [ '.mp4', '.mov', '.wmv', '.avi', '.jpeg', '.jpg', '.png', '.gif', '.mp3', '.wav'];

function getPostType(file_ext) {
    if (file_ext == '.mp4' || file_ext == '.mov' || file_ext == '.wmv' || file_ext == '.avi') {
        return 'video';
    } else if (file_ext == '.jpeg' || file_ext == '.jpg' || file_ext == '.png' || file_ext == '.gif') {
        return 'image';   
    } else if (file_ext == '.mp3' || file_ext == '.wav') {
        return 'audio';
    }
}

exports.createPost = async (req, res) =>{

    var file_url, post_type = 'text';

    try {

        if (req.file == undefined) {
            //post without file
            file_url = "";     
        } else {
            //post with file
            const file_ext = path.extname(req.file.originalname);

            file_url = `http://localhost:5000/post_file/${req.file.filename}`;

            if(!allowedFileTypes.includes(file_ext)){
                return res.json({
                    status: false,
                    msg: `${file_ext} File type not supported`,
                    data: []
                })
            } 

            post_type = getPostType(file_ext);
        }

        const body = req.body;
        const { user_id, post_title, post_desc } = body
        sql.query('INSERT INTO post ( user_id, post_title, post_url, post_desc, post_type ) VALUES (?,?,?,?,?)', [user_id, post_title, file_url, post_desc, post_type] , (err, result) =>{
            if (!err) {
                return res.json({
                    status: true,
                    msg: 'Post created successfully',
                    data: [{
                        id : result.insertId,
                        post_url: file_url
                    }]
                })
            } else{
                return res.send(err);
            }
        })
    } catch(e) {
        console.log('Catch an error: ', e);
        return res.json({
            status: false,
            msg: 'Something went wrong',
            data: []
        }) 
    }

}

exports.updatePost = async (req, res) =>{
    
    var file_url, post_type = 'text';
    try {

        if (req.file == undefined) {
            //post without file
            file_url = "";     
        } else {
            //post with file
            const file_ext = path.extname(req.file.originalname);

            file_url = `http://localhost:5000/post_file/${req.file.filename}`;

            if(!allowedFileTypes.includes(file_ext)){
                return res.json({
                    status: false,
                    msg: `${file_ext} File type not supported`,
                    data: []
                })
            } 

            post_type = getPostType(file_ext);
        }

        const { post_title, post_desc , post_id } = req.body;
        sql.query('UPDATE post SET post_title = ? , post_url = ?, post_desc = ?, post_type = ? WHERE post_id = ?', [ post_title, file_url, post_desc, post_type, post_id] , (err, result) =>{
            if (!err) {
                console.log(result);
                return res.json({
                    status: true,
                    msg: 'Post updated successfully',
                    data: []
                })
            } else{
                return res.send(err);
            }
        })
    } catch(e) {
        console.log('Catch an error: ', e);
        return res.json({
            status: false,
            msg: 'Something went wrong',
            data: []
        }) 
    }

}

exports.deletePost = async (req, res) =>{

    try {
        const post_id = req.body.post_id;
        console.log('Post id', post_id);
        sql.query('DELETE FROM post WHERE post_id = ?', post_id , (err, result) =>{
            if (!err) {
                return res.json({
                    status: true,
                    msg: 'Post deleted successfully',
                    data: []
                })
            } else{
                return res.send(err);
            }
        })
    } catch(e) {
        console.log('Catch an error: ', e);
        return res.json({
            status: false,
            msg: 'Something went wrong',
            data: []
        }) 
    }

}

exports.getmyfeed = async (req, res) =>{

    try {
        sql.query(`SELECT post.*, count(likes.likes_id) as 'total_likes' FROM post LEFT JOIN likes ON post.post_id = likes.post_id WHERE post.user_id = 1 GROUP BY post.post_id `, req.query.id , (err, result) =>{
            if (!err) {
                return res.json({
                    status: true,
                    msg: 'Posts fetched successfully',
                    data: result
                })
            } else{
                return res.send(err);
            }
        })
    } catch(e) {
        console.log('Catch an error: ', e);
        return res.json({
            status: false,
            msg: 'Something went wrong',
            data: []
        }) 
    }

}

exports.getfeed = async (req, res) =>{

    try {
        console.log(req.query.id)
        sql.query('SELECT post.*, following.* FROM post INNER JOIN following ON following.following_to = post.user_id WHERE following.user_id = ? ', req.query.id, (err, result) =>{
            if (!err) {
                console.log(result)
                return res.json({
                    status: true,
                    msg: 'Feed fetched successfully',
                    data: result
                })
            } else{
                return res.send(err);
            }
        })
    } catch(e) {
        console.log('Catch an error: ', e);
        return res.json({
            status: false,
            msg: 'Something went wrong',
            data: []
        }) 
    }

}

exports.like = async (req, res) =>{

    try {
        const { post_id, user_id, generated_by } = req.body;
        sql.query('INSERT INTO likes (post_id, user_id, generated_by) VALUES (?,?,?) ON DUPLICATE KEY UPDATE post_id = ? , generated_by = ?', [ post_id, user_id, generated_by, post_id, generated_by ] , (err, result) =>{
            if (!err) {
                return res.json({
                    status: true,
                    msg: 'Post liked successfully',
                    data: []
                })
            } else{
                return res.send(err);
            }
        })
    } catch(e) {
        console.log('Catch an error: ', e);
        return res.json({
            status: false,
            msg: 'Something went wrong',
            data: []
        }) 
    }

}

exports.unlike = async (req, res) =>{

    try {
        const likes_id = req.body.likes_id;
        sql.query('DELETE FROM likes WHERE likes_id = ?', [ likes_id ] , (err, result) => { 
            if (!err) {
                return res.json({
                    status: true,
                    msg: 'Post unliked successfully',
                    data: []
                })
            } else{
                return res.send(err);
            }
        })
    } catch(e) {
        console.log('Catch an error: ', e);
        return res.json({
            status: false,
            msg: 'Something went wrong',
            data: []
        }) 
    }

}

exports.createcomment = async (req, res) =>{

    try {
        const { post_id, user_id, sender_id, comment_text } = req.body;
        sql.query('INSERT INTO comment_user (user_id, sender_id, post_id) VALUES (?,?,?)', [ user_id, sender_id,  post_id] , (err, result) =>{
            if (!err) {

                console.log(result);

                sql.query('INSERT INTO comment (comment_id, comment_text) VALUES (?,?)', [ result.insertId, comment_text ], (err, comment_result) =>{
                        if (!err) {
                            return res.json({
                                status: true,
                                msg: 'Comment done successfully',
                                data: []
                            })
                        } else{
                            return res.send(err);
                        }
                })

         
            } else{
                return res.send(err);
            }
        })
    } catch(e) {
        console.log('Catch an error: ', e);
        return res.json({
            status: false,
            msg: 'Something went wrong',
            data: []
        }) 
    }

}

exports.updatecomment = async (req, res) =>{

    try {
        const { comment_id, comment_text } = req.body;
        sql.query('UPDATE comment SET comment_text = ? WHERE comment_id = ?', [ comment_text, comment_id] , (err, result) =>{
            if (!err) {

                console.log(result);

                if (!err) {
                    return res.json({
                        status: true,
                        msg: 'Comment updated successfully',
                        data: []
                    })
                } else{
                    return res.send(err);
                }
         
            } else{
                return res.send(err);
            }
        })
    } catch(e) {
        console.log('Catch an error: ', e);
        return res.json({
            status: false,
            msg: 'Something went wrong',
            data: []
        }) 
    }

}

exports.deletecomment = async (req, res) =>{

    try {
        const comment_id = req.body.comment_id;
        sql.query('DELETE FROM c WHERE comment_id = ?', comment_id , (err, result) =>{
            if (!err) {
                return res.json({
                    status: true,
                    msg: 'Comment deleted successfully',
                    data: []
                })
            } else{
                return res.send(err);
            }
        })
    } catch(e) {
        console.log('Catch an error: ', e);
        return res.json({
            status: false,
            msg: 'Something went wrong',
            data: []
        }) 
    }

}