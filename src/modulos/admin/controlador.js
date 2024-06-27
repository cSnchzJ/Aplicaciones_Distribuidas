const db = require('../../DB/mysql');
const auth = require('../auth');

const TABLA = 'administrador';


module.exports = function (dbInyectada) {

    let db = dbInyectada;
    if(!db){
        db=require("../../DB/mysql");
    }
    async function agregar(body){
        const usuario = {
            id: body.id,
            correo: body.correo,
            password: body.password,
        }

        const respuesta = await db.agregar(TABLA, usuario);
        console.log('respuesta',respuesta);
        var insertId = 0;
        if(body.id == 0){
            insertId = respuesta.insertId;
        }else{
            insertId = body.id;
        }

        var respuesta2 = '';
        if(body.correo || body.password){
            respuesta2 = await auth.agregar({
                id: insertId,
                usuario: body.correo,
                password: body.password
            })
        }
        return true;
    }
    return{
        agregar
    }

}