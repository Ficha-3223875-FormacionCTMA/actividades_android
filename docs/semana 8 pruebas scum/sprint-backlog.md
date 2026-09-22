# SPRINT BACKLOG Y DEFINITION OF DONE
## Semana 8 — Pruebas de Software y SCRUM

---

# 1. Sprint Goal

Implementar y documentar una estrategia de pruebas automatizadas para
Mi Formación CTMA, verificando reglas de negocio, validaciones, cambios
de estado, autenticación y funcionamiento de los principales endpoints
de la API.

Además, generar evidencias de ejecución, cobertura, repetibilidad y
trazabilidad de las pruebas.

---

# 2. Sprint Backlog

| ID | Historia / tarea | Actividad | Evidencia | Estado |
|---|---|---|---|---|
| SB-01 | Como desarrollador quiero validar las reglas de negocio para evitar errores en las actividades. | Crear pruebas unitarias para estados y validaciones. | `actividad-status.test.js` | COMPLETADO |
| SB-02 | Como desarrollador quiero comprobar los cambios de estado de una actividad. | Crear pruebas parametrizadas de estados. | `actividad-status-parametrized.test.js` | COMPLETADO |
| SB-03 | Como desarrollador quiero verificar el servicio de actividades. | Probar actualización, errores y actividades inexistentes. | `actividad-service.test.js` | COMPLETADO |
| SB-04 | Como desarrollador quiero reutilizar datos de prueba. | Crear fixture para actividades. | `fixtures/actividad.js` | COMPLETADO |
| SB-05 | Como desarrollador quiero aislar dependencias externas durante las pruebas unitarias. | Utilizar mocks del repositorio mediante `vi.fn()`. | `actividad-service.test.js` | COMPLETADO |
| SB-06 | Como desarrollador quiero conocer qué porcentaje del código está cubierto. | Ejecutar reporte de cobertura. | `EV-02-cobertura.txt` | COMPLETADO |
| SB-07 | Como desarrollador quiero comprobar que las pruebas sean repetibles. | Ejecutar la suite tres veces sin modificar el código. | `EV-03-repetibilidad.txt` | COMPLETADO |
| SB-08 | Como desarrollador quiero verificar el funcionamiento de la API. | Automatizar pruebas GET y POST. | `pruebas_api/test_api.py` | COMPLETADO |
| SB-09 | Como desarrollador quiero verificar la seguridad básica de la API. | Comprobar acceso sin autenticación. | `test_acceso_sin_autenticacion` | COMPLETADO |
| SB-10 | Como equipo quiero relacionar requisitos, riesgos y pruebas. | Crear matriz de trazabilidad. | `matriz-trazabilidad.md` | COMPLETADO |
| SB-11 | Como equipo quiero demostrar el proceso TDD. | Documentar un ciclo Red-Green-Refactor real. | Evidencia TDD | PENDIENTE |
| SB-12 | Como equipo quiero documentar el proceso de pruebas. | Actualizar README con comandos y resultados. | `README.md` | PENDIENTE |

---

# 3. Definition of Done — DoD

Una tarea de pruebas se considera terminada cuando cumple las
siguientes condiciones:

## Código

- [x] El código de pruebas está organizado.
- [x] Las pruebas unitarias se encuentran dentro de `pruebas/tests`.
- [x] Las funciones reutilizables se encuentran dentro de `pruebas/src`.
- [x] Existe una fixture para generar datos de prueba.
- [x] Se utiliza mocking en las pruebas que lo requieren.
- [x] Las pruebas de API están dentro de `pruebas_api`.

## Pruebas unitarias

- [x] Existen más de 6 pruebas unitarias.
- [x] Existen pruebas parametrizadas.
- [x] Se prueban casos válidos.
- [x] Se prueban casos inválidos.
- [x] Se prueban errores.
- [x] La suite se ejecuta correctamente.

## Pruebas de API

- [x] Existe una prueba GET.
- [x] Existe una prueba POST.
- [x] Existe una prueba de autenticación.
- [x] Se utilizan códigos HTTP esperados.
- [x] Las respuestas JSON son verificadas.

## Cobertura

- [x] Se generó un reporte de cobertura.
- [x] Se identificaron líneas no cubiertas.
- [x] Se registró el porcentaje de cobertura.

Cobertura obtenida:

**76,36 % general**

## Repetibilidad

- [x] La suite fue ejecutada tres veces.
- [x] Las tres ejecuciones fueron exitosas.
- [x] No fue necesario modificar el código entre ejecuciones.

## Trazabilidad

- [x] Los riesgos fueron identificados.
- [x] Los casos de prueba fueron relacionados con los riesgos.
- [x] Las pruebas manuales y automatizadas fueron relacionadas.
- [x] Se creó la matriz de trazabilidad.

## Documentación

- [x] Se documentaron las pruebas unitarias.
- [x] Se documentó la cobertura.
- [x] Se documentó la repetibilidad.
- [x] Se documentaron las pruebas de API.
- [x] Se creó el Sprint Backlog.
- [x] Se definió la Definition of Done.

## Pendientes

- [ ] Realizar evidencia TDD Red-Green-Refactor.
- [ ] Finalizar README de pruebas.

---

# 4. Resultado del Sprint

Hasta el momento se encuentran completadas las siguientes actividades:

- Pruebas unitarias con Vitest.
- Pruebas parametrizadas.
- Fixture de datos.
- Mock del repositorio.
- Pruebas del servicio.
- Cobertura de código.
- Pruebas de repetibilidad.
- Pruebas automatizadas de API con pytest.
- Prueba de autenticación.
- Matriz de trazabilidad.
- Sprint Backlog.
- Definition of Done.

## Resultados obtenidos

### Vitest

18 pruebas ejecutadas.

18 pruebas aprobadas.

0 pruebas fallidas.

### Cobertura

76,36 % de cobertura general.

### API

3 pruebas ejecutadas.

3 pruebas aprobadas.

0 pruebas fallidas.

### Repetibilidad

3 ejecuciones consecutivas.

Todas aprobadas.

---

# 5. Observación

La evidencia TDD debe realizarse mediante un ciclo real:

1. RED — crear primero una prueba que falle.
2. GREEN — modificar el código para conseguir que la prueba pase.
3. REFACTOR — mejorar el código manteniendo las pruebas aprobadas.

No se debe registrar como realizada una etapa TDD si no se ejecutó realmente.