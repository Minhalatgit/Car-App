'user strict';
const sql = require('../connection');

exports.getClientOffers = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('SELECT * FROM offer WHERE is_deleted = 0', (err, result) => {
            if(!err) {
                console.log(result);
                return res.json({
                    status: true,
                    msg: "Offers fetched successfully",
                    data : result
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

exports.getAdminOffers = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('SELECT * FROM offer', (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Offers fetched successfully",
                    data : result
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

exports.createOffer = async (req, res) =>{

    try {
        console.log(req.file.path);
        const body = req.body;
        sql.query('INSERT INTO offer ( offer_title, offer_discount, offer_image) VALUES (?,?,?)', [ body.offer_title, body.offer_discount, req.file.path], (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Offer created successfully",
                    data: result.insertId
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

exports.updateOffer = async (req, res) =>{

    try {
        const body = req.body;
        console.log(body);
        sql.query('UPDATE offer SET offer_title = ? , offer_discount = ? , offer_image = ? , is_deleted = ? WHERE id = ?', [ body.offer_title, body.offer_discount, req.file.path, body.is_deleted , body.offer_id], (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Offer updated successfully"
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

exports.deleteOffer = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('UPDATE offer SET is_deleted = 1 WHERE id = ?', [ body.offer_id ], (err, result) => {
            if(!err) {
                console.log(result)
                return res.json({
                    status: true,
                    msg: "Offer deleted successfully"
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