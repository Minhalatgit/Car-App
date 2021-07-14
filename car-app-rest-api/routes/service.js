var express = require('express');
var Router = express.Router();
var serviceController = require('../controllers/service_controller');

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

    Router.post('/createservice', upload.single('service_image'), serviceController.createService);
    Router.post('/updateservice', upload.single('service_image'), serviceController.updateService);
    Router.post('/getclientservices', serviceController.getClientServices);
    Router.post('/getadminservices', serviceController.getAdminServices);
    Router.post('/deleteservice', serviceController.deleteService);
    Router.post('/favouriteservice', serviceController.favoriteService);

    return Router
}

module.exports = router();