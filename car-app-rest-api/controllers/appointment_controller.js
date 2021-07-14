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
        sql.query('UPDATE appointment SET status = ?, title = ?, amount = ?, appointment_date = ? , appointment_is_deleted = ? WHERE id = ?', [ body.status, body.title, body.amount, body.appointment_date, body.is_deleted, body.appointment_id ], (err, result) => {
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
        sql.query('UPDATE appointment SET appointment_is_deleted = 1 WHERE id = ? ', [ body.appointment_id ], (err, result) => {
            if(!err) {
                console.log(result)
                return res.json({
                    status: true,
                    msg: "Appointment deleted successfully"
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
