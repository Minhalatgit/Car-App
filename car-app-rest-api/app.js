const express = require('express');
const route = require('./routes/route');
const app = express();
var cors = require('cors')

app.use(cors());
app.options('*', cors())
app.use(express.static('uploads'));
app.use(express.urlencoded({extended: true})); 
app.use(express.json());

app.use('/api', route);

app.listen(3000, () => console.log('Listening to port 3000...'));