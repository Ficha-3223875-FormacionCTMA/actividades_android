import { describe, expect, it } from "vitest";

import {
    puedeCambiarEstado,
    esEstadoValido,
    puedeCrearActividad
} from "../src/actividad-status.js";


describe("Pruebas de estados de actividades", () => {

    it("rechaza CANCELADA -> COMPLETADA", () => {
        const resultado = puedeCambiarEstado(
            "CANCELADA",
            "COMPLETADA"
        );

        expect(resultado).toBe(false);
    });

    it("permite PENDIENTE -> EN_PROCESO", () => {
        const resultado = puedeCambiarEstado(
            "PENDIENTE",
            "EN_PROCESO"
        );

        expect(resultado).toBe(true);
    });

    it("permite EN_PROCESO -> COMPLETADA", () => {
        const resultado = puedeCambiarEstado(
            "EN_PROCESO",
            "COMPLETADA"
        );

        expect(resultado).toBe(true);
    });

    it("rechaza un estado actual no válido", () => {
        const resultado = puedeCambiarEstado(
            "ESTADO_INCORRECTO",
            "COMPLETADA"
        );

        expect(resultado).toBe(false);
    });

    it("identifica correctamente un estado válido", () => {
        const resultado = esEstadoValido("PENDIENTE");

        expect(resultado).toBe(true);
    });

    it("identifica correctamente un estado no válido", () => {
        const resultado = esEstadoValido("DESCONOCIDO");

        expect(resultado).toBe(false);
    });

});


describe("Pruebas de creación de actividades", () => {

    it("permite crear una actividad con datos válidos", () => {
        const resultado = puedeCrearActividad(
            "Aprender Kotlin",
            "Estudiar los fundamentos de Kotlin"
        );

        expect(resultado).toBe(true);
    });

    it("rechaza un título demasiado corto", () => {
        const resultado = puedeCrearActividad(
            "AB",
            "Descripción válida"
        );

        expect(resultado).toBe(false);
    });

    it("rechaza un título vacío", () => {
        const resultado = puedeCrearActividad(
            "",
            "Descripción válida"
        );

        expect(resultado).toBe(false);
    });

    it("rechaza una descripción demasiado larga", () => {
        const descripcionLarga = "A".repeat(241);

        const resultado = puedeCrearActividad(
            "Actividad válida",
            descripcionLarga
        );

        expect(resultado).toBe(false);
    });


    //  esta prueba ahora espera el comportamiento correcto
    it(" rechaza CANCELADA -> COMPLETADA", () => {
        const resultado = puedeCambiarEstado(
            "CANCELADA",
            "COMPLETADA"
        );

        expect(resultado).toBe(false);
    });

});