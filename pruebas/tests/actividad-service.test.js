import {
    describe,
    expect,
    it,
    vi
} from "vitest";

import { cambiarEstadoActividad } from "../src/actividad-service.js";

import {
    crearActividadFixture
} from "../src/fixtures/actividad.js";

describe("Pruebas del servicio de actividades", () => {

    it("cambia correctamente el estado de una actividad", async () => {
        const actividad = crearActividadFixture({
            id: 1,
            estado: "PENDIENTE"
        });

        const repository = {
            buscarPorId: vi.fn().mockResolvedValue(actividad),
            guardar: vi.fn().mockResolvedValue(undefined)
        };

        const resultado = await cambiarEstadoActividad({
            id: 1,
            nuevoEstado: "EN_PROCESO",
            repository
        });

        expect(resultado.estado).toBe("EN_PROCESO");

        expect(repository.buscarPorId)
            .toHaveBeenCalledWith(1);

        expect(repository.guardar)
            .toHaveBeenCalledTimes(1);
    });

    it("rechaza cambiar CANCELADA a COMPLETADA", async () => {
        const actividad = crearActividadFixture({
            id: 2,
            estado: "CANCELADA"
        });

        const repository = {
            buscarPorId: vi.fn().mockResolvedValue(actividad),
            guardar: vi.fn().mockResolvedValue(undefined)
        };

        await expect(
            cambiarEstadoActividad({
                id: 2,
                nuevoEstado: "COMPLETADA",
                repository
            })
        ).rejects.toThrow(
            "El cambio de estado no está permitido"
        );

        expect(repository.guardar)
            .not.toHaveBeenCalled();
    });

    it("rechaza una actividad inexistente", async () => {
        const repository = {
            buscarPorId: vi.fn().mockResolvedValue(null),
            guardar: vi.fn().mockResolvedValue(undefined)
        };

        await expect(
            cambiarEstadoActividad({
                id: 999,
                nuevoEstado: "COMPLETADA",
                repository
            })
        ).rejects.toThrow("Actividad no encontrada");

        expect(repository.guardar)
            .not.toHaveBeenCalled();
    });

});