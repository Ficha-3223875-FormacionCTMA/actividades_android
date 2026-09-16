Semana 3 — Matriz de Trazabilidad

1. Objetivo

La matriz de trazabilidad permite relacionar las historias de usuario, criterios de aceptación, casos de prueba, técnicas utilizadas y posibles defectos de la aplicación Mi Formación CTMA.

Su objetivo es comprobar que los requisitos definidos tengan pruebas asociadas y que los resultados puedan relacionarse con el requisito correspondiente.

---

2. Historias de usuario

ID| Historia de usuario
HU-01| Consultar información de formación.
HU-02| Consultar actividades formativas.
HU-03| Consultar próximo compromiso.

---

3. Criterios de aceptación

ID| Criterio de aceptación| Historia
CA-01| Mostrar título "Mi Formación CTMA".| HU-01
CA-02| Mostrar información relacionada con la formación.| HU-01
CA-03| Información clara y fácil de leer.| HU-01
CA-04| La pantalla funciona correctamente al abrir.| HU-01
CA-05| Mostrar sección de actividades.| HU-02
CA-06| Cada actividad tiene nombre o descripción.| HU-02
CA-07| Actividades organizadas.| HU-02
CA-08| Información no cortada ni superpuesta.| HU-02
CA-09| Mostrar tarjeta o información del próximo compromiso.| HU-03
CA-10| La información del compromiso es entendible.| HU-03
CA-11| El elemento relacionado responde correctamente.| HU-03
CA-12| La interfaz conserva una presentación organizada.| HU-03

---

4. Matriz principal de trazabilidad

Historia| Criterio| Caso de prueba| Técnica| Tipo| Resultado
HU-01| CA-01| CP-01| Partición de equivalencia| Positiva| Pendiente
HU-01| CA-02| CP-02| Partición de equivalencia| Positiva| Pendiente
HU-01| CA-03| CP-03| Valores límite| Positiva| Pendiente
HU-01| CA-04| CP-04| Caso de uso| Positiva| Pendiente
HU-02| CA-05| CP-05| Caso de uso| Positiva| Pendiente
HU-02| CA-06| CP-06| Partición de equivalencia| Positiva| Pendiente
HU-02| CA-07| CP-07| Prueba funcional| Positiva| Pendiente
HU-02| CA-08| CP-08| Valores límite| Positiva/Negativa| Pendiente
HU-03| CA-09| CP-09| Caso de uso| Positiva| Pendiente
HU-03| CA-10| CP-10| Partición de equivalencia| Positiva| Pendiente
HU-03| CA-11| CP-11| Prueba funcional| Positiva| Pendiente
HU-03| CA-12| CP-12| Prueba funcional| Positiva| Pendiente

---

5. Trazabilidad de técnicas de prueba

5.1 Partición de equivalencia

Las particiones utilizadas para las actividades son:

ID| Partición| Casos relacionados
P1| Actividad con título y descripción| CP-01, CP-02, CP-06
P2| Actividad con título largo| CP-06, CP-08
P3| Actividad con descripción vacía| CP-06, CP-10
P4| Varias actividades| CP-07

---

5.2 Valores límite

Situación| Caso relacionado| Resultado esperado
Texto corto| CP-03, CP-08| El texto se muestra correctamente.
Texto de longitud normal| CP-03, CP-08| La información permanece visible.
Texto largo| CP-03, CP-08| No debe existir corte ni superposición.

---

5.3 Tabla de decisión

La tabla de decisión se relaciona con las condiciones de existencia y presentación de las actividades.

Regla| Condiciones| Acción esperada| Caso relacionado
R1| Hay actividades + título + descripción + presentación correcta| Mostrar actividad completa| CP-06
R2| Hay actividades + título + sin descripción + presentación correcta| Mostrar actividad sin descripción| CP-06
R3| Hay actividades + información disponible + presentación incorrecta| Identificar posible defecto| CP-08
R4| No hay actividades| Mostrar estado vacío| Escenario alterno

---

5.4 Transición de estados

Estado inicial| Evento| Estado siguiente| Tipo
Pantalla inicial| Consultar actividades disponibles| Lista con actividades| Válida
Pantalla inicial| Consultar cuando no existen actividades| Estado vacío| Alterna
Estado vacío| Intentar mostrar actividades inexistentes| Estado vacío| Inválida
Lista con actividades| Mantener actividades disponibles| Lista con actividades| Inválida

---

6. Trazabilidad de defectos

Los defectos encontrados durante la ejecución deben relacionarse con el caso de prueba que los detectó.

Defecto| Caso de prueba| Criterio| Severidad| Prioridad| Estado
DEF-01| Por registrar| Por registrar| Por determinar| Por determinar| NUEVO
DEF-02| Por registrar| Por registrar| Por determinar| Por determinar| NUEVO
DEF-03| Por registrar| Por registrar| Por determinar| Por determinar| NUEVO

Los defectos anteriores corresponden a una estructura de registro. No deben considerarse defectos reales hasta que sean encontrados durante la ejecución de las pruebas.

---

7. Cobertura

La matriz permite verificar que los 12 criterios de aceptación definidos para el proyecto tengan un caso de prueba relacionado.

Elemento| Cantidad
Historias de usuario| 3
Criterios de aceptación| 12
Casos de prueba| 12
Técnicas principales| 4
Criterios con caso asociado| 12

Cobertura de criterios

Cobertura = criterios con casos asociados / criterios totales × 100

Cobertura = 12 / 12 × 100 = 100 %

La cobertura anterior corresponde a la asignación documental de casos de prueba. El resultado PASS/FAIL se determinará después de ejecutar cada caso.

---

8. Relación con los riesgos

Riesgo| Descripción| Casos relacionados
R-01| El proyecto no compila o presenta errores al iniciar.| CP-04
R-02| La interfaz puede presentarse desorganizada.| CP-07, CP-08, CP-12
R-03| Pérdida de cambios del proyecto.| Control de versiones
R-04| Una funcionalidad puede no responder correctamente.| CP-11

---

9. Estado de ejecución

La matriz se actualizará después de ejecutar las pruebas.

Caso| Resultado| Defecto relacionado
CP-01| Pendiente| —
CP-02| Pendiente| —
CP-03| Pendiente| —
CP-04| Pendiente| —
CP-05| Pendiente| —
CP-06| Pendiente| —
CP-07| Pendiente| —
CP-08| Pendiente| —
CP-09| Pendiente| —
CP-10| Pendiente| —
CP-11| Pendiente| —
CP-12| Pendiente| —

---

10. Control de cambios

Versión| Cambio realizado
v1.0| Creación de la matriz de trazabilidad.
v1.1| Asociación de historias, criterios y casos de prueba.
v1.2| Incorporación de técnicas de prueba y riesgos.
v1.3| Preparación para registrar resultados y defectos.

---

11. Conclusión

La matriz de trazabilidad permite mantener una relación clara entre las historias de usuario, los criterios de aceptación y los casos de prueba de Mi Formación CTMA.

La cobertura documental permite comprobar que los criterios definidos cuentan con una prueba asociada.

La matriz también facilita relacionar los posibles defectos con las pruebas y requisitos afectados, permitiendo realizar un seguimiento organizado durante el proceso de pruebas.