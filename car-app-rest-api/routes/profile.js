var express = require('express');
var Router = express.Router();
var profileController = require('../controllers/profile_controller');

const multer = require('multer');

const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, 'uploads/');
    },
    filename: function (req, file, cb) {
        cb(null, Date.now() + file.originalname)
    }
});

const upload = multer({storage: storage, limits: {
    fileSize : 1024 * 1024 * 5
    }
});

var router = function(){

    Router.post('/updateprofile', upload.single('profile_image'), profileController.updateProfile);
    Router.post('/getprofile', profileController.getProfile);

    return Router
}

module.exports = router();