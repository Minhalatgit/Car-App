'user strict';
const sql = require('../connection');
// const upload = multer({dest: 'uploads/'})
// const {  } = require('../helper/validation_schema');

exports.getClientServices = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('SELECT * FROM service WHERE cat_id = ? AND is_deleted = 0', [ body.cat_id ], (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Services fetched successfully",
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

exports.getAdminServices = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('SELECT * FROM service', (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Services fetched successfully",
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

exports.createService = async (req, res) =>{

    try {
        console.log(req.file.path);
        const body = req.body;
        sql.query('INSERT INTO service ( cat_id, service_title, service_subtitle, service_image) VALUES (?,?,?,?)', [ body.cat_id, body.service_title, body.service_subtitle, req.file.path ], (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Service created successfully",
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

exports.updateService = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('Update service SET cat_id = ? , service_title = ? , service_subtitle = ? , service_image = ? , is_deleted = ? WHERE id = ?', [ body.cat_id, body.service_title, body.service_subtitle, req.file.path, body.is_deleted , body.service_id], (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Service updated successfully"
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

exports.deleteService = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('UPDATE service SET is_deleted = 1 WHERE id = ? ', [ body.service_id ], (err, result) => {
            if(!err) {
                console.log(result)
                return res.json({
                    status: true,
                    msg: "Service deleted successfully"
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