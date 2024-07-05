import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavBar from './pages/NavBar';
import Menu from './pages/Menu';
import Espacios from './pages/Espacios';
import ListaUsuarios from './pages/ListaUsuarios';
import { tasks as dataA } from './tasks'; // Importación de datos desde tasks.js
import { variables as data2 } from './tasksVar';
import { datosUsuario as data3 } from './tasksDatosUsuario';
import { datosEspacios as data4 } from './tasksEspacios';

function App() {
  const [tasks, setTasks] = useState([]);
  const [variables, setVariables] = useState([]);
  const [datosUsuario, setDatosUsuario] = useState([]);
  const [datosEspacios, setDatosEspacios] = useState([]);
  const [clientes, setClientes] = useState([]);

  useEffect(() => {
    // Setear los datos iniciales usando los arreglos importados
    setTasks(dataA);
    setVariables(data2);
    setDatosUsuario(data3);
    setDatosEspacios(data4);

    // Llamar a la API para obtener clientes
    axios.get('http://localhost:4000/api/clientes')
      .then(response => {
        console.log(response.data.body);
        setClientes(response.data.body);
      })
      .catch(error => {
        console.error('Error al obtener clientes:', error);
      });
  }, []);

  return (
    <div className=''>
      <Router>
        {/* Rutas para cada componente */}
        <Routes>
          <Route index element={<NavBar tasks={tasks} variables={variables} />} />
          <Route path="Menu" element={<Menu variables={variables} />} />
          <Route path="Espacios" element={<Espacios datosEspacios={datosEspacios} />} />
          <Route path="ListaUsuarios" element={<ListaUsuarios datosUsuario={datosUsuario} clientes={clientes} />} />
        </Routes>
      </Router>
    </div>
  )
}

export default App;


