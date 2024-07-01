//ul>li*4>a{Extension $}
//rfce crea funcion
//imp exporta
import TaskList from './pages/TaskList'
import TaskForm from './pages/TaskForm'
import ReactDOM from "react-dom/client"
import {tasks as data} from './tasks'//tasks entre llaves porque el export no es default en tasks
import {variables as data2} from './tasksVar'
import {datosUsuario as data3} from './tasksDatosUsuario'
import {datosEspacios as data4} from './tasksEspacios'
import {BrowserRouter as Router, Routes,Route} from 'react-router-dom'
import {useState,useEffect} from 'react'
import NavBar from './pages/NavBar'
import Menu from './pages/Menu'
import Espacios from './pages/Espacios'
import ListaUsuarios from './pages/ListaUsuarios';
import axios from 'axios';



function App() {

  const [tasks,setTasks]= useState([])//crea una const tareas, que es un arreglo vacio

    useEffect(()=>{
        setTasks(data)
    },[])
    const [variables,setTasks2]= useState([])//crea una var variables, que es un arreglo vacio

    useEffect(()=>{
        setTasks2(data2)
    },[])
    var [datosUsuario,setTasks3]= useState([])//crea una var datosUsuario, que es un arreglo vacio

    useEffect(()=>{
        setTasks3(data3)
    },[])
    var [datosEspacios,setTasks4]= useState([])//crea una var datosEspacio, que es un arreglo vacio

    useEffect(()=>{
        setTasks4(data4)
    },[])

    const [clientes, setClientes] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:4000/api/clientes')
            .then(response => {
                setClientes(response.data);
            })
            .catch(error => {
                console.error('Error al obtener clientes:', error);
            });
    }, []);
  return (
    <div className=''>
      <Router>
        
        <Routes> <Route index element={<NavBar tasks={tasks} variables={variables} />} /> </Routes>
        <Routes> <Route path="Menu" element={<Menu variables={variables}/>} /> </Routes>
        <Routes> <Route path="Espacios" element={<Espacios datosEspacios={datosEspacios}/>} /> </Routes>
        <Routes> <Route path="ListaUsuarios" element={<ListaUsuarios datosUsuario={datosUsuario} clientes={clientes} />} /> </Routes>
      </Router>
    </div>
  )
}

export default App

