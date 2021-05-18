var express = require('express');
var Router = express.Router();
var homeController = require('../controllers/home_controller');

var router = function(){

    Router.post('/gethome', homeController.getHomeData);

    return Router
}

module.exports = router();