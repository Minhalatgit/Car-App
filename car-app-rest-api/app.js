const express = require('express');
const bodyParser = require('body-parser');
const route = require('./routes/route');
const app = express();
var cors = require('cors')

app.use(cors());
app.options('*', cors())
app.use('/uploads', express.static('uploads'));
app.use(bodyParser.json({ limit: '10mb', extended: true }));
app.use(bodyParser.urlencoded({ limit: '10mb', extended: true }));

app.use('/api', route);

app.listen(5000, () => console.log('Listening to port 5000...'));