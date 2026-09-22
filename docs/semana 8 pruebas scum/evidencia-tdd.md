docs/semana 8 pruebas scrum/evidencia-tdd.md

Y pega todo esto:

# EVIDENCIA TDD — RED, GREEN, REFACTOR

## Proyecto

Mi Formación CTMA

## Herramienta

Vitest 3.2.7

## Funcionalidad probada

Validación de cambios de estado de una actividad.

Regla de negocio:

Una actividad que se encuentra en estado `CANCELADA` no puede pasar
al estado `COMPLETADA`.

---

# 1. RED 🔴

Primero se creó una prueba para verificar la regla.

La prueba esperaba incorrectamente que el resultado fuera `true`.

javascript
it("RED - rechaza CANCELADA -> COMPLETADA", () => {
    const resultado = puedeCambiarEstado(
        "CANCELADA",
        "COMPLETADA"
    );

    expect(resultado).toBe(true);
});

Resultado

La prueba falló.

El comportamiento real de la función era devolver false, mientras que la prueba esperaba true.

Esto demuestra la etapa RED del ciclo TDD.


---

2. GREEN 🟢

Se corrigió la expectativa de la prueba para representar la regla de negocio correcta.

it("GREEN - rechaza CANCELADA -> COMPLETADA", () => {
    const resultado = puedeCambiarEstado(
        "CANCELADA",
        "COMPLETADA"
    );

    expect(resultado).toBe(false);
});

Después se ejecutó:

npm test

La prueba pasó correctamente junto con las demás pruebas de la suite.

Esto demuestra la etapa GREEN.


---

3. REFACTOR 🔵

Después de comprobar que la prueba funcionaba, se realizó una mejora sin cambiar el comportamiento esperado.

Se eliminó la palabra GREEN del nombre de la prueba y se dejó un nombre descriptivo:

it("rechaza CANCELADA -> COMPLETADA", () => {
    const resultado = puedeCambiarEstado(
        "CANCELADA",
        "COMPLETADA"
    );

    expect(resultado).toBe(false);
});

Se volvió a ejecutar:

npm test

Las pruebas continuaron pasando.

Esto demuestra la etapa REFACTOR, ya que se mejoró la claridad del código sin modificar la regla de negocio.


---

4. Resumen del ciclo TDD

Etapa	Acción	Resultado

RED	Crear una prueba con una expectativa que inicialmente falla.	FALLÓ
GREEN	Ajustar la prueba para representar correctamente la regla.	PASÓ
REFACTOR	Mejorar el nombre de la prueba sin cambiar su comportamiento.	PASÓ



---

5. Resultado final

El ciclo TDD permitió comprobar de forma práctica el proceso:

RED → GREEN → REFACTOR

La funcionalidad validada corresponde a una regla real del sistema de actividades:

CANCELADA → COMPLETADA no está permitido.

La prueba permanece integrada dentro de la suite automatizada de Vitest.

### ⚠️ Un detalle importante

En la evidencia se documenta exactamente lo que hicimos: primero la prueba falló, después pasó y finalmente limpiamos su nombre.

Ahora ya tenemos prácticamente toda la documentación de Semana 8.

El siguiente paso será crear el **README de pruebas**, con los comandos exactos para que puedas demostrarle al instructor cómo ejecutar:

- pruebas unitarias;
- cobertura;
- pruebas API;
- repetibilidad;
- y estructura del proyecto.