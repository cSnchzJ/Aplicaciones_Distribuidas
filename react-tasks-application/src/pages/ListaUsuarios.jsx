import React, { useState } from 'react';
import { datosUsuario } from '../tasksDatosUsuario'
import { Modal, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const MyModal = ({ show, handleClose,datoUsuario}) => (
  <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
          <Modal.Title><p>{datoUsuario?.correo}</p></Modal.Title>
      </Modal.Header>
      <Modal.Body>
          <p>activo: {datoUsuario?.activo}</p>
          <p>fecha_creacion:{datoUsuario?.fecha_creacion}</p>
          <p>id:{datoUsuario?.id}</p>
          
          <p>password:{datoUsuario?.password}</p>
          <p>placas:{datoUsuario?.placas}</p>
          <p>telefono:{datoUsuario?.telefono}</p>
          <p>tipo_usuario:{datoUsuario?.tipo_usuario}</p>
          
      </Modal.Body>
      <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
              Close
          </Button>
      </Modal.Footer>
  </Modal>
);

function ListaUsuarios(props) {
  //const datos = JSON.parse(clientes);
  console.log(props);
  console.log(props.clientes.body);
  //console.log(props.datosUsuario);

  const [show, setShow] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);

  const handleClose = () => setShow(false);
  const handleShow = (user) => {
    setSelectedUser(user);
    setShow(true);
  };

  return (
    <div id='listaUsuarios'>
        <h1 id='h1usuarios'>Usuarios</h1>
        <div >{
        props.clientes.body.map((datoUsuario) => (<div id='seccionUsuario' class="row" key={datoUsuario.id}>

                <div class="col-sm-8">

                <Button variant="info" size="lg" onClick={() => handleShow(datoUsuario)}>
                  <h1>{datoUsuario.correo}</h1>
                </Button>
                  
                </div>

                <div class="col-sm-4">
                  <p>botonUsuario</p>
                </div>
                <br></br>
            </div>
            
            ))
        }
        
        </div>
        {selectedUser && <MyModal show={show} handleClose={handleClose} datoUsuario={selectedUser} />}
    </div>
  )
}

export default ListaUsuarios
