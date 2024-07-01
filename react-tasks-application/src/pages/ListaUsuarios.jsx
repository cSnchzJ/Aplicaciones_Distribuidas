import React, { useState } from 'react';
import { datosUsuario } from '../tasksDatosUsuario'
import { Modal, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const MyModal = ({ show, handleClose,datoUsuario}) => (
  <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
          <Modal.Title><p>{datoUsuario?.tipo_usuario}</p></Modal.Title>
      </Modal.Header>
      <Modal.Body>
          <p>id: {datoUsuario?.id}</p>
          <p>tipo_usuario:{datoUsuario?.tipo_usuario}</p>
          <p>correo:{datoUsuario?.correo}</p>
          
          <p>fecha_creacion:{datoUsuario?.fecha_creacion}</p>
          <p>placas:{datoUsuario?.placas}</p>
          <p>telefono:{datoUsuario?.telefono}</p>
          <p>Activo:{datoUsuario?.activo}</p>
          
      </Modal.Body>
      <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
              Close
          </Button>
      </Modal.Footer>
  </Modal>
);

function ListaUsuarios(props) {
  console.log(props);

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
        props.datosUsuario.map((datoUsuario) => (<div id='seccionUsuario' class="row" key={datoUsuario.id}>

                <div class="col-sm-8">

                <Button variant="info" size="lg" onClick={() => handleShow(datoUsuario)}>
                  <h1>{datoUsuario.tipo_usuario}</h1>
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
