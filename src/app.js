const express = require ('express');
const morgan = require('morgan');
const config = require ('./config');
const clientes = require('./modulos/clientes/rutas');
const cajones = require("./modulos/cajones/rutas");
const error = require('./red/errors');
const app = express();

//middleware
app.use(morgan('dev'));
app.use(express.json());
app.use(express.urlencoded({extended:true}));
//configuracion
app.set('port', config.app.port)

//rutas
app.use('/api/clientes', clientes)
app.use('/api/cajones', cajones)
app.use(error);

module.exports = app;