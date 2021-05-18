var express = require('express');
var Router = express.Router();
var storeController = require('../controllers/store_controller');

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

    Router.post('/createstore', upload.single('store_image'), storeController.createStore);
    Router.post('/updatestore', upload.single('store_image'), storeController.updateStore);
    Router.post('/getclientstores', storeController.getClientStores);
    Router.post('/getadminstores', storeController.getAdminStores);
    Router.post('/deletestore', storeController.deleteStore);

    return Router
}

module.exports = router();