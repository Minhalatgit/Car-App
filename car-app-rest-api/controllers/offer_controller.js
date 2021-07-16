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
        const body = JSON.parse(JSON.stringify(req.body));
        
        let query
        if (req.file) {
            let path = req.file.path
            path = path.replace("\\", "\\\\")
            console.log(path);

            query = `UPDATE offer SET offer_title = '${body.offer_title}' , offer_discount = '${body.offer_discount}' , offer_image = '${path}' WHERE id = '${body.offer_id}'`
        } else {
            query = `UPDATE offer SET offer_title = '${body.offer_title}' , offer_discount = '${body.offer_discount}' WHERE id = '${body.offer_id}'`
        }
     
        sql.query(query, (err, result) => {
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
        const is_deleted = req.body.is_deleted
        console.log(body)

        let msg = ""
        if (is_deleted == "1") {
            msg = 'Offer disabled successfully'
        } else{
            msg = 'Offer enabled successfully'
        }

        sql.query('UPDATE offer SET is_deleted = ? WHERE id = ?', [ is_deleted, body.offer_id ], (err, result) => {
            if(!err) {
                console.log(result)
                return res.json({
                    status: true,
                    msg: msg
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

exports.getOffer = async (req, res) =>{

    try {
        const offer_id = req.body.offer_id;
        
        sql.query('SELECT * from offer WHERE id = ?',[ offer_id ], (err, result) => {
            if(!err) {
                console.log(result)
                return res.json({
                    status: true,
                    msg: "Offer fetched successfully",
                    data: result[0]
                })
            } else {
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