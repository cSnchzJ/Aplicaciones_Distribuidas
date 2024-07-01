import React, { useState } from 'react';

import { Modal, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const MyModal = ({ show, handleClose,datosEspacio}) => (
  <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
          <Modal.Title><p>{datosEspacio?.tipo_usuario}</p></Modal.Title>
      </Modal.Header>
      <Modal.Body>
          <p>id: {datosEspacio?.id}</p>
          <p>estatus:{datosEspacio?.estatus}</p>
          <p>identificador:{datosEspacio?.identificador}</p>
        
          
      </Modal.Body>
      <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
              Close
          </Button>
      </Modal.Footer>
  </Modal>
);

function Espacios(props) {
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
        <h1 id='h1usuarios'>Espacios</h1>
        <div >{
        props.datosEspacios.map((datosEspacio) => (<div id='seccionUsuario' class="row" key={datosEspacio.id}>

                <div class="col-sm-8">

                <Button variant="info" size="lg" onClick={() => handleShow(datosEspacio)}>
                  <h1>{datosEspacio.id}</h1>
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
        {selectedUser && <MyModal show={show} handleClose={handleClose} datosEspacio={selectedUser} />}
    </div>
  )
}

export default Espacios
