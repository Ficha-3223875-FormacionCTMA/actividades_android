export const ESTADOS_VALIDOS = [
    "PENDIENTE",
    "EN_PROCESO",
    "COMPLETADA",
    "CANCELADA"
];

export function esEstadoValido(estado) {
    return ESTADOS_VALIDOS.includes(estado);
}

export function puedeCambiarEstado(estadoActual, nuevoEstado) {
    if (
        !esEstadoValido(estadoActual) ||
        !esEstadoValido(nuevoEstado)
    ) {
        return false;
    }

    if (
        estadoActual === "CANCELADA" &&
        nuevoEstado === "COMPLETADA"
    ) {
        return false;
    }

    return true;
}

export function puedeCrearActividad(titulo, descripcion) {
    if (
        typeof titulo !== "string" ||
        typeof descripcion !== "string"
    ) {
        return false;
    }

    const tituloLimpio = titulo.trim();
    const descripcionLimpia = descripcion.trim();

    if (tituloLimpio.length < 3) {
        return false;
    }

    if (tituloLimpio.length > 80) {
        return false;
    }

    if (descripcionLimpia.length > 240) {
        return false;
    }

    return true;
}