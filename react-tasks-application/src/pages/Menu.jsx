import React from 'react'
import { Link } from 'react-router-dom'
import TaskList from './TaskList'

function Menu(props) {
  return (
    <div id='menu'>
      <Link to='/' id='salir_1'>Salir</Link>
      {props.variables.map((variable) => (
        <h1 key={variable.id}>{variable.title}</h1>
      ))}
      <p id='Ver_menu'>Ver: </p>
      <a id='botonEspacios' href="/espacios" target="_blank">Espacios</a>
      <br></br><br></br>
      <a id='botonListaUsuarios' href="/listausuarios" target="_blank">Lista de Usuarios</a>

    </div>
  )
}

export default Menu
