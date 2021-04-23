var express = require('express');
var Router = express.Router();
var authController = require('../controllers/auth_controller');

var router = function(){

    Router.post('/register', authController.register);
    Router.post('/login', authController.login);
    Router.post('/verify', authController.verify);

    return Router
}

module.exports = router();