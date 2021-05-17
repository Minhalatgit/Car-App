var express = require('express');
var Router = express.Router();
var offerController = require('../controllers/offer_controller');

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

    Router.post('/createoffer', upload.single('offer_image'), offerController.createOffer);
    Router.post('/updateoffer', upload.single('offer_image'), offerController.updateOffer);
    Router.post('/getoffers', offerController.getOffers);
    Router.post('/deleteoffer', offerController.deleteOffer);

    return Router
}

module.exports = router();