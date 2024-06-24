const app = require ('./app');

const PORT = app.get('port');
const HOST = '0.0.0.0';  // Escucha en todas las interfaces de red

app.listen(PORT, HOST, () => {
    console.log(`Servidor escuchando en http://${HOST}:${PORT}`);
});