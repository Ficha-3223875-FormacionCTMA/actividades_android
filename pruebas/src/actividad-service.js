import {
    puedeCambiarEstado
} from "./actividad-status.js";

export async function cambiarEstadoActividad({
    id,
    nuevoEstado,
    repository
}) {
    const actividad = await repository.buscarPorId(id);

    if (!actividad) {
        throw new Error("Actividad no encontrada");
    }

    const permitido = puedeCambiarEstado(
        actividad.estado,
        nuevoEstado
    );

    if (!permitido) {
        throw new Error(
            "El cambio de estado no está permitido"
        );
    }

    const actividadActualizada = {
        ...actividad,
        estado: nuevoEstado
    };

    await repository.guardar(actividadActualizada);

    return actividadActualizada;
}