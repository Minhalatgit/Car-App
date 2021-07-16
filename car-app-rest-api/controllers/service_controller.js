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

        const body = JSON.parse(JSON.stringify(req.body));
        const path = req.file.path;
        console.log(path);
        sql.query('INSERT INTO service ( cat_id, service_title, service_subtitle, service_amount, service_image) VALUES (?,?,?,?,?)', [ body.cat_id, body.service_title, body.service_subtitle, body.service_amount, path ], (err, result) => {
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
        const body = JSON.parse(JSON.stringify(req.body));
        
        let query
        if (req.file) {
            let path = req.file.path
            path = path.replace("\\", "\\\\")
            console.log(path);

            query = `UPDATE service SET cat_id = '${body.cat_id}' , service_title = '${body.service_title}' , service_subtitle = '${body.service_subtitle}' , service_image = '${path}' , service_amount = '${body.service_amount}' WHERE id = '${body.service_id}'`
        } else {
            query = `UPDATE service SET cat_id = '${body.cat_id}' , service_title = '${body.service_title}' , service_subtitle = '${body.service_subtitle}' , service_amount = '${body.service_amount}' WHERE id = '${body.service_id}'`
        }

        console.log("query", query)

        sql.query(query, (err, result) => {
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
        const is_deleted = req.body.is_deleted
        console.log(body)

        let msg = ""
        if (is_deleted == "1") {
            msg = 'Service disabled successfully'
        } else{
            msg = 'Service enabled successfully'
        }

        sql.query('UPDATE service SET is_deleted = ? WHERE id = ? ', [ body.is_deleted, body.service_id ], (err, result) => {
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

exports.favoriteService = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('UPDATE service SET is_favourite = ? WHERE id = ? ', [ body.is_favourite, body.service_id ], (err, result) => {
            if(!err) {
                console.log(result)
                return res.json({
                    status: true,
                    msg: "Service favourite updated successfully"
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

exports.getService = async (req, res) =>{

    try {
        const service_id = req.body.service_id;
        
        sql.query('SELECT * from service WHERE id = ?',[ service_id ], (err, result) => {
            if(!err) {
                console.log(result)
                return res.json({
                    status: true,
                    msg: "Service fetched successfully",
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