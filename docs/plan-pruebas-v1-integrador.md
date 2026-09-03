# Plan de Pruebas v1 — Mi Formación CTMA

## 1. Objetivo
Verificar que las historias seleccionadas cumplen sus criterios de aceptación y que las reglas, navegación, estado y accesibilidad básica funcionan de forma reproducible.

## 2. Alcance
Incluye lista, búsqueda, creación, edición, detalle, validaciones, progreso/estado, navegación, conservación del formulario y fuente aumentada.

## 3. Fuera de alcance
Persistencia real en servidor, autenticación, sincronización con API y datos personales reales.

## 4. Base de prueba
Historias HU-01..HU-08, criterios CA-01..CA-08, reglas Kotlin existentes y mapa de navegación.

## 5. Estrategia
- Partición de equivalencia.
- Análisis de valores límite.
- Transición de estados.
- Casos de uso/flujos completos.
- Navegación negativa.
- Accesibilidad manual con fuente aumentada.

La guía integradora exige una suite representativa con positivos y negativos, valores límite, navegación, consistencia, duplicación, accesibilidad y regresión. fileciteturn6file0L245-L253

## 6. Ambiente
Android Studio + emulador/dispositivo autorizado. Registrar versión de build y configuración de pantalla/fuente.

## 7. Datos
Usar únicamente datos sintéticos.

## 8. Convención
- **PASS:** observado coincide con esperado.
- **FAIL:** observado no coincide con esperado.
- **BLOCKED:** no se puede ejecutar por una condición externa o precondición incumplida.

## 9. Criterios de entrada
Proyecto sincronizado, compilable, aplicación ejecutable y datos de prueba disponibles.

## 10. Criterios de salida
Casos ejecutados o justificados como BLOCKED, defectos documentados, confirmación/regresión realizada cuando aplique y evidencias organizadas.

Una prueba no ejecutada no se declara PASS; la guía explícitamente exige resultados reales y no inventados. fileciteturn6file0L300-L314
