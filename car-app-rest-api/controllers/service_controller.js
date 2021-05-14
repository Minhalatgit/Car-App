'user strict';
const sql = require('../connection');
// const upload = multer({dest: 'uploads/'})
// const {  } = require('../helper/validation_schema');

exports.getServices = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('SELECT * FROM service WHERE cat_id = ?', [ body.cat_id ], (err, result) => {
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
        sql.query('Update service SET cat_id = ? , service_title = ? , service_subtitle = ? , service_image = ? WHERE id = ?', [ body.cat_id, body.service_title, body.service_subtitle, req.file.path , body.service_id], (err, result) => {
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
        sql.query('DELETE FROM service WHERE id = ? ', [ body.service_id ], (err, result) => {
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