const db = require('../../DB/mysql')

const TABLA = 'login';

function todos (){
    return db.todos(TABLA)
}

module.exports = {
    todos,
}