const express = require ('express');
const respuesta = require('../../red/respuestas')
const controlador = require('./index');


const router = express.Router();

router.post('/', agregar);

async function agregar (req, res, next){
    try{
        const items = await controlador.agregar(req.body);
        mensaje = 'Agregado con exito';
        respuesta.success(req, res, mensaje, 201);
    }catch(err){
        next(err);
    }    
};
module.exports = router;