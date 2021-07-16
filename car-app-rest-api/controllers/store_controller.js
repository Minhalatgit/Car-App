'user strict';
const sql = require('../connection');

exports.getClientStores = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('SELECT * FROM store WHERE is_deleted = 0', (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Stores fetched successfully",
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

exports.getAdminStores = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('SELECT * FROM store', (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Stores fetched successfully",
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

exports.createStore = async (req, res) =>{

    try {
        console.log(req.file.path);
        const body = req.body;
        sql.query('INSERT INTO store ( store_title, store_address, store_lat, store_long, store_image) VALUES (?,?,?,?,?)', [ body.store_title, body.store_address, body.store_lat, body.store_long, req.file.path], (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Store created successfully",
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

exports.updateStore = async (req, res) =>{

    try {
        const body = JSON.parse(JSON.stringify(req.body));
        
        let query
        if (req.file) {
            let path = req.file.path
            path = path.replace("\\", "\\\\")
            console.log(path);

            query = `UPDATE store SET store_title = '${body.store_title}' , store_address = '${body.store_address}' , store_lat = '${body.store_lat}' , store_long = '${body.store_long}' , store_image = '${path}' WHERE id = '${body.store_id}'`
        } else {
            query = `UPDATE store SET store_title = '${body.store_title}' , store_address = '${body.store_address}' , store_lat = '${body.store_lat}' , store_long = '${body.store_long}' WHERE id = '${body.store_id}'`
        }
     
        sql.query(query, (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Store updated successfully"
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

exports.deleteStore = async (req, res) =>{

    try {
        const body = req.body;
        const is_deleted = req.body.is_deleted
        console.log(body)

        let msg = ""
        if (is_deleted == "1") {
            msg = 'Store disabled successfully'
        } else{
            msg = 'Store enabled successfully'
        }

        sql.query('UPDATE store SET is_deleted = ? WHERE id = ? ', [ is_deleted, body.store_id ], (err, result) => {
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

exports.getStore = async (req, res) =>{

    try {
        const store_id = req.body.store_id;
        
        sql.query('SELECT * from store WHERE id = ?',[ store_id ], (err, result) => {
            if(!err) {
                console.log(result)
                return res.json({
                    status: true,
                    msg: "Store fetched successfully",
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