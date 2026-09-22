export function crearActividadFixture(cambios = {}) {
    return {
        id: 1,
        titulo: "Actividad de prueba",
        descripcion: "Descripción de la actividad de prueba",
        estado: "PENDIENTE",
        prioridad: "MEDIA",
        ...cambios
    };
}