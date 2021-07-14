'user strict';
const sql = require('../connection');

exports.getHomeData = async (req, res) =>{

    try {
        let offers, services;
        const body = req.body;
        sql.query('SELECT * FROM offer LIMIT 2', (offerError, offerResult) => {
            if(!offerError) {
                console.log('Offer result', offerResult);

                sql.query('SELECT * FROM service LIMIT 2', (serviceError, serviceResult) =>{
                    if (!serviceError) {
                        console.log('Service result', serviceResult);
                        
                        sql.query('SELECT * FROM appointment as a INNER JOIN service as s ON a.service_id = s.id INNER JOIN store as st ON a.store_id = st.id LIMIT 2', (appointmentError, appointmentResult) =>{
                            if (!appointmentError) {
                                return res.json({
                                    status: true,
                                    msg: "Home data fetched successfully",
                                    data: {
                                        offers: offerResult,
                                        services: serviceResult,
                                        appointments: appointmentResult
                                    }
                                })  
                            } else{
                                res.send(appointmentError);
                            }
                        })    
                    } else{
                        res.send(serviceError);
                    }
                })
            } else{
                res.send(offerError);
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
