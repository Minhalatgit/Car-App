const express = require('express');
const app =  express();

var authRoute = require('./authentication');
var serviceRoute = require('./service');
var offerRoute = require('./offer');
var storeRoute = require('./store');
var profileRoute = require('./profile');
var homeRoute = require('./home');

app.use('/auth', authRoute);
app.use('/service', serviceRoute);
app.use('/offer', offerRoute);
app.use('/store', storeRoute);
app.use('/profile', profileRoute);
app.use('/home', homeRoute);

module.exports = app;