import { describe, expect, it } from "vitest";

import { puedeCambiarEstado } from "../src/actividad-status.js";

describe("Pruebas parametrizadas de cambios de estado", () => {

    it.each([
        ["PENDIENTE", "EN_PROCESO", true],
        ["EN_PROCESO", "COMPLETADA", true],
        ["PENDIENTE", "CANCELADA", true],
        ["CANCELADA", "COMPLETADA", false],
        ["ESTADO_INVALIDO", "COMPLETADA", false]
    ])(
        "cambiar de %s a %s debe devolver %s",
        (estadoActual, nuevoEstado, resultadoEsperado) => {

            const resultado = puedeCambiarEstado(
                estadoActual,
                nuevoEstado
            );

            expect(resultado).toBe(resultadoEsperado);
        }
    );

});