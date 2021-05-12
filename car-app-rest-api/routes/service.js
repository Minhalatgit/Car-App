var express = require('express');
var Router = express.Router();
var authController = require('../controllers/service_controller');

var router = function(){

    Router.post('/createservice', authController.createService);
    Router.post('/getservices', authController.getServices);
    Router.post('/deleteservice', authController.deleteService);

    return Router
}

module.exports = router();