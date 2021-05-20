'user strict';
const sql = require('../connection');

exports.getAppointments = async (req, res) =>{

    try {
        const body = req.body;
        sql.query('SELECT * FROM appointment', (err, result) => {
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
        sql.query('INSERT INTO appointment (service_id, user_id, status, title, amount, appointment_date) VALUES (?,?,?,?,?,?)', [ body.service_id, body.user_id, body.status, body.title, body.amount, body.appointment_date ], (err, result) => {
            if(!err) {
                return res.json({
                    status: true,
                    msg: "Appointment created successfully",
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
