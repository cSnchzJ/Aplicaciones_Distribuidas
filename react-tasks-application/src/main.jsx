import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import * as bootstrap from 'bootstrap'
import Alert from 'bootstrap/js/dist/alert';

// or, specify which plugins you need:
import { Tooltip, Toast, Popover } from 'bootstrap';

ReactDOM.createRoot(document.getElementById('root')).render(//lkmlk strict mode corrige lo que pase en react
  <React.StrictMode> 
    <App />
  </React.StrictMode>,
)
