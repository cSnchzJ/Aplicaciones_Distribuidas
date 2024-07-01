import {useState} from 'react';

function TaskForm() {

  return (
    
    <div >
    {
     <h1>Bienvenido Administrador</h1>
    }
    </div>
    
  )
}
/*function TaskForm() {

    const [title,setTitle] = useState("");
    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(title)
    }

  return (
    
      <form  onSubmit ={handleSubmit}>
        <input 
        placeholder="tarea" 
        onChange={(e) => setTitle(e.target.value)}
        />
        <button>
            Guardar
        </button>
      </form>
    
  )
}*/

export default TaskForm
