var express = require('express');
var Router = express.Router();
var apppointmentController = require('../controllers/appointment_controller');

var router = function(){

    Router.post('/getappointments', apppointmentController.getAppointments);
    Router.post('/createappointment', apppointmentController.createAppointment);

    return Router
}

module.exports = router();