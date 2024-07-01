import React from 'react'
import TaskList from './TaskList'
import TaskForm from './TaskForm'
import { Link } from 'react-router-dom'

function NavBar(props) {
  return (
    <main>
        <TaskForm/>
        <TaskList tasks={props.tasks} variables={props.variables} />  
    </main>
  )
}

export default NavBar
