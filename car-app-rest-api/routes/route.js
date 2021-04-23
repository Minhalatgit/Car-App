const express = require('express');
const app =  express();

var authRoute = require('./authentication');

app.use('/auth', authRoute);

module.exports = app;