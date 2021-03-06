var express = require('express');
var Router = express.Router();
var userController = require('../controllers/user_controller');

var router = function(){

    Router.post('/follow', userController.follow);
    Router.post('/unfollow', userController.unfollow);

    return Router
}

module.exports = router();