const express = require('express');
const app =  express();

var authRoute = require('./authentication');
var serviceRoute = require('./service');

app.use('/auth', authRoute);
app.use('/service', serviceRoute);

module.exports = app;