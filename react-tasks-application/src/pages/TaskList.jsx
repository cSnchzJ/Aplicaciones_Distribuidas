import React, { useSyncExternalStore } from 'react'
import {useState} from 'react'
import { Link } from 'react-router-dom';
import Menu from './Menu';
import { variables } from '../tasksVar';
import axios from 'axios';
//import fetch from 'node-fetch';


//console.log(tasks)//clg  o con ctrl espacio aparece el autocompletado

function TaskList(props) {

    const [email,setTitle] = useState("");
    const [contrasena,setTitle1] = useState("");
    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(email)
        console.log(contrasena)
        console.log(props.variables);
        console.log(variables);

        const vars = variables.find(variables => variables.id === 1);
        //console.log(props.tasks)
       // console.log(props.variables);
        //console.log(props.tasks.title)
        //props.variables.title=email;
        //console.log(props.variables.title)
        console.log(vars)
        if (vars) {
            console.log(vars.title); // Aquí se imprime el title del objeto con id=0

            // Modificar el title del objeto encontrado
            vars.title = email;
            vars.pswd= contrasena;
            console.log(`Nuevo title: ${vars.title}`); // Aquí se imprime el nuevo title del objeto
            console.log(`Nuevo title: ${vars.pswd}`); // Aquí se imprime el nuevo contrasena del objeto

            // Enviar los datos de vars al backend usando una solicitud GET
            /*const query = new URLSearchParams({
                usuario: vars.title,
                password: vars.pswd,
                // Añadir más parámetros si es necesario
            });*/

            /*const response = await fetch(`http://localhost:4000/api/auth/login?${query.toString()}`, {
                method: 'GET'
            });*/
           /* const response = await fetch(`http://localhost:4000/api/clientes}`, {
                method: 'GET'
            });*/


           /* const dat = await response.json();
            console.log('Response from backend:', dat);*/

        } else {
            console.log('No se encontró un objeto con id=0');
        }
        
        if (email == "Ame"){
            console.log("SI") 
            //document.location='/menu'  
        }
    }  

  return (
    
    <form onSubmit={handleSubmit} action="/action_page" class="was-validated">
    <div class="mb-3 mt-3">
        <label for="uname" class="form-label">Correo electrónico:</label>
        <input type="text" class="form-control" id="uname" placeholder="Correo Electrónico" name="uname" required
        onChange={(e) => setTitle(e.target.value)}
        />
        <div class="valid-feedback">Valido.</div>
        <div class="invalid-feedback">Por favor rellena esta casilla.</div>
        <div class="mb-3">
            <label for="pwd" class="form-label">Contraseña<a href="mailto:"></a>:</label>
            <input type="password" class="form-control" id="pwd" placeholder="Contraseña" name="pswd" required
            onChange={(e) => setTitle1(e.target.value)}
            />
            <div class="valid-feedback">Valido.</div>
            <div class="invalid-feedback">Por favor rellena esta casilla.</div>
        </div>
        <div class="form-check mb-3"></div>
    </div>
    <button type="submit" class="btn btn-dark" >Ingresar</button>
    </form>
    
  )
}
/*function TaskList({tasks}) {

    
    if(tasks.length === 0)
        {
            return <h1>No hay tareas aun</h1>
        }

  return (
    <div >
        {
        tasks.map((task) => (<div key={task.id} >
                <h1>{task.title}</h1>
                <p>{task.description}</p>
            </div>
            ))
        }
    </div>
  )
}*/

export default TaskList
