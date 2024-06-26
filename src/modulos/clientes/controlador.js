const db = require('../../DB/mysql');
const auth = require('../auth');

const TABLA = 'usuario';


module.exports = function (dbInyectada) {

    let db = dbInyectada;
    if(!db){
        db=require("../../DB/mysql");
    }

    function todos (){
        return db.todos(TABLA);
    }

    function uno(id){
        return db.uno(TABLA,id);
    }

    async function agregar(body){
        const usuario = {
            id: body.id,
            tipo_usuario: body.tipo_usuario,
            correo: body.correo,
            password: body.password,
            placas: body.placas,
            telefono: body.telefono,
            activo: body.activo
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

    function eliminar(body){
        return db.eliminar(TABLA,body);
    }

    function eliminar(body){
        return db.eliminar(TABLA,body);
    }

    return{
        todos,
        uno,
        agregar,
        eliminar
    }
    
}