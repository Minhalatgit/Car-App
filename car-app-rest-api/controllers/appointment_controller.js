'user strict';
const sql = require('../connection');

exports.getAppointments = async (req, res) =>{

    try {
        sql.query('SELECT * FROM appointment as a INNER JOIN service as s ON a.service_id = s.id INNER JOIN store as st ON a.store_id = st.id WHERE appointment_is_deleted = 0', (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Apppointments fetched successfully",
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

exports.createAppointment = async (req, res) =>{

    try {
        const body = req.body;
        console.log(body);
        sql.query('INSERT INTO appointment (service_id, store_id, user_id, status, title, amount, appointment_date) VALUES (?,?,?,?,?,?,?)', [ body.service_id, body.store_id, body.user_id, body.status, body.title, body.amount, body.appointment_date ], (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Appointment created successfully"
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

exports.updateAppointment = async (req, res) =>{

    try {
        const body = req.body;
        console.log(body);
        sql.query('UPDATE appointment SET status = ?, title = ?, amount = ?, appointment_date = ? WHERE id = ?', [ body.status, body.title, body.amount, body.appointment_date, body.appointment_id ], (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Appointment updated successfully"
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

exports.deleteAppointment = async (req, res) =>{

    try {
        const body = req.body;
        const is_deleted = req.body.is_deleted
        console.log(body)

        let msg = ""
        if (is_deleted == "1") {
            msg = 'Appointment disabled successfully'
        } else{
            msg = 'Appointment enabled successfully'
        }

        sql.query('UPDATE appointment SET appointment_is_deleted = ? WHERE id = ? ', [ is_deleted, body.appointment_id ], (err, result) => {
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

exports.getAppointment = async (req, res) =>{

    try {
        const appointment_id = req.body.appointment_id;
        
        sql.query('SELECT * from appointment WHERE id = ?',[ appointment_id ], (err, result) => {
            if(!err) {
                console.log(result)
                return res.json({
                    status: true,
                    msg: "Appointment fetched successfully",
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
