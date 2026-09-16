Semana 3 — Casos de Prueba

1. Objetivo

Diseñar y documentar casos de prueba para la aplicación Mi Formación CTMA, utilizando técnicas de diseño sistemático de pruebas.

Los casos permiten verificar que las actividades formativas, la información de formación y el próximo compromiso se presenten de manera correcta, organizada, legible y accesible.

---

2. Historias de usuario relacionadas

HU-01 — Consultar información de formación

Como aprendiz SENA,
quiero consultar información sobre mi formación CTMA,
para conocer contenidos importantes de mi proceso formativo.

HU-02 — Consultar actividades formativas

Como aprendiz SENA,
quiero visualizar mis actividades formativas,
para conocer las tareas y contenidos que debo revisar.

HU-03 — Consultar próximo compromiso

Como aprendiz SENA,
quiero consultar mi próximo compromiso,
para recordar qué actividad debo realizar.

---

3. Estructura de los casos de prueba

Cada caso de prueba contiene:

- ID
- Objetivo
- Historia de usuario y criterio relacionado
- Precondiciones
- Datos de prueba
- Pasos
- Resultado esperado
- Prioridad
- Técnica utilizada
- Postcondición

Los datos utilizados son sintéticos y no contienen información personal real.

---

4. Casos de prueba

CP-01 — Mostrar título de la aplicación

Referencia: HU-01 / CA-01
Objetivo: Verificar que la aplicación muestre el título "Mi Formación CTMA".

Precondición: La aplicación está instalada y puede ejecutarse.

Datos: Aplicación Mi Formación CTMA.

Pasos:

1. Abrir la aplicación.
2. Observar la pantalla principal.
3. Identificar el título de la aplicación.

Resultado esperado: Se muestra claramente el título "Mi Formación CTMA".

Prioridad: Alta
Técnica: Partición de equivalencia
Tipo: Positiva

---

CP-02 — Mostrar información de formación

Referencia: HU-01 / CA-02
Objetivo: Verificar que se muestre información relacionada con la formación CTMA.

Precondición: La aplicación se encuentra abierta.

Datos: Información de formación de prueba.

Pasos:

1. Abrir la aplicación.
2. Observar la información presentada.
3. Verificar que esté relacionada con la formación.

Resultado esperado: La información de formación se muestra correctamente.

Prioridad: Alta
Técnica: Partición de equivalencia
Tipo: Positiva

---

CP-03 — Verificar legibilidad de la información

Referencia: HU-01 / CA-03
Objetivo: Verificar que los textos de la aplicación sean claros y fáciles de leer.

Precondición: La aplicación está abierta.

Datos: Textos de la pantalla principal y actividades.

Pasos:

1. Abrir la aplicación.
2. Revisar títulos y descripciones.
3. Observar que los textos no estén superpuestos.
4. Verificar que sean legibles.

Resultado esperado: Los textos son visibles, legibles y no presentan superposición.

Prioridad: Alta
Técnica: Valores límite
Tipo: Positiva

---

CP-04 — Abrir correctamente la aplicación

Referencia: HU-01 / CA-04
Objetivo: Verificar que la aplicación pueda abrirse sin errores.

Precondición: La aplicación está instalada.

Datos: Aplicación compilada.

Pasos:

1. Ejecutar la aplicación.
2. Esperar a que cargue la pantalla inicial.
3. Observar si aparecen errores.

Resultado esperado: La aplicación inicia correctamente y muestra su interfaz.

Prioridad: Alta
Técnica: Escenario de caso de uso
Tipo: Positiva

---

CP-05 — Mostrar sección de actividades

Referencia: HU-02 / CA-05
Objetivo: Verificar que la aplicación muestre la sección de actividades formativas.

Precondición: La aplicación está abierta.

Datos: Lista de actividades de prueba.

Pasos:

1. Abrir la aplicación.
2. Acceder a la pantalla de actividades.
3. Observar la lista.

Resultado esperado: Se muestra la sección de actividades formativas.

Prioridad: Alta
Técnica: Escenario de caso de uso
Tipo: Positiva

---

CP-06 — Mostrar nombre o descripción de las actividades

Referencia: HU-02 / CA-06
Objetivo: Verificar que cada actividad muestre información identificable.

Precondición: Existen actividades cargadas.

Datos: Actividades con título y descripción.

Pasos:

1. Abrir la pantalla de actividades.
2. Revisar las tarjetas.
3. Comprobar que cada actividad tenga información identificable.

Resultado esperado: Las actividades muestran su título y, cuando existe, su descripción.

Prioridad: Alta
Técnica: Partición de equivalencia
Tipo: Positiva

---

CP-07 — Verificar organización de las actividades

Referencia: HU-02 / CA-07
Objetivo: Verificar que las actividades estén organizadas visualmente.

Precondición: Existen varias actividades.

Datos: Lista de varias actividades de prueba.

Pasos:

1. Abrir la pantalla de actividades.
2. Observar la distribución de las tarjetas.
3. Revisar que exista separación adecuada entre ellas.
4. Verificar que la información sea fácil de identificar.

Resultado esperado: Las actividades aparecen organizadas y separadas correctamente.

Prioridad: Alta
Técnica: Prueba funcional
Tipo: Positiva

---

CP-08 — Verificar que la información no aparezca cortada

Referencia: HU-02 / CA-08
Objetivo: Verificar que los textos de las actividades permanezcan visibles.

Precondición: Existen actividades con textos de diferente longitud.

Datos: Título corto, título normal y título largo.

Pasos:

1. Abrir la pantalla de actividades.
2. Revisar actividades con diferentes longitudes de texto.
3. Observar títulos y descripciones.
4. Verificar que no exista superposición o contenido cortado.

Resultado esperado: La información permanece visible, organizada y legible.

Prioridad: Alta
Técnica: Valores límite
Tipo: Positiva y negativa

---

CP-09 — Mostrar próximo compromiso

Referencia: HU-03 / CA-09
Objetivo: Verificar que se muestre la información relacionada con el próximo compromiso.

Precondición: La aplicación está abierta.

Datos: Próximo compromiso de prueba.

Pasos:

1. Abrir la aplicación.
2. Observar la pantalla principal.
3. Identificar la tarjeta o sección correspondiente al próximo compromiso.

Resultado esperado: Se muestra información del próximo compromiso de forma clara.

Prioridad: Alta
Técnica: Escenario de caso de uso
Tipo: Positiva

---

CP-10 — Verificar información entendible del compromiso

Referencia: HU-03 / CA-10
Objetivo: Verificar que la información del próximo compromiso sea comprensible.

Precondición: Existe información de compromiso de prueba.

Datos: Nombre y descripción del compromiso.

Pasos:

1. Abrir la aplicación.
2. Ubicar la información del próximo compromiso.
3. Leer el contenido.
4. Verificar que sea comprensible.

Resultado esperado: La información del compromiso se presenta de forma clara y entendible.

Prioridad: Media
Técnica: Partición de equivalencia
Tipo: Positiva

---

CP-11 — Verificar respuesta del elemento relacionado

Referencia: HU-03 / CA-11
Objetivo: Verificar que el elemento interactivo relacionado con el próximo compromiso responda correctamente, cuando se encuentre disponible en la versión evaluada.

Precondición: La aplicación está abierta y el elemento interactivo está disponible.

Datos: Elemento relacionado con el compromiso.

Pasos:

1. Abrir la aplicación.
2. Ubicar el elemento relacionado con el próximo compromiso.
3. Interactuar con el elemento.
4. Observar el comportamiento de la aplicación.

Resultado esperado: El elemento responde de acuerdo con la funcionalidad implementada y la interfaz mantiene su organización.

Prioridad: Alta
Técnica: Prueba funcional
Tipo: Positiva

Nota: Si el elemento interactivo no está implementado en la versión actual, este caso debe registrarse como pendiente de implementación y no como funcionalidad existente.

---

CP-12 — Verificar presentación organizada

Referencia: HU-03 / CA-12
Objetivo: Verificar que la interfaz conserve una presentación organizada.

Precondición: La aplicación está abierta.

Datos: Información de formación, actividades y compromiso.

Pasos:

1. Abrir la aplicación.
2. Revisar las diferentes secciones.
3. Observar títulos, tarjetas, textos y espacios.
4. Verificar que los elementos no se superpongan.

Resultado esperado: La interfaz mantiene una presentación organizada y fácil de consultar.

Prioridad: Alta
Técnica: Prueba funcional
Tipo: Positiva

---

5. Partición de equivalencia

La partición de equivalencia permite dividir los datos de entrada en grupos que se espera que tengan un comportamiento similar.

Para Mi Formación CTMA se analizarán los datos utilizados en las tarjetas de actividades, especialmente el título y la descripción.

Partición| Descripción| Ejemplo| Resultado esperado
P1| Actividad con título y descripción| "Actividad de aprendizaje" + descripción| La tarjeta muestra la información correctamente.
P2| Actividad con título largo| Título extenso| El título debe mantenerse visible sin afectar la interfaz.
P3| Actividad con descripción vacía| Descripción = null| La tarjeta debe seguir mostrando la actividad correctamente.
P4| Varias actividades| Lista con varias actividades| Las actividades deben mostrarse organizadamente.

Justificación

Estas particiones permiten comprobar diferentes tipos de información que pueden aparecer en las actividades sin necesidad de probar todas las combinaciones posibles.

Los datos utilizados son sintéticos y corresponden a información de prueba.

---

6. Análisis de valores límite

Los valores límite permiten comprobar el comportamiento de la interfaz cuando los datos se encuentran cerca de situaciones que pueden afectar su presentación.

Para Mi Formación CTMA se utilizarán como referencia las longitudes de los textos mostrados en las tarjetas de actividades.

Valor límite| Situación de prueba| Resultado esperado
Texto corto| Título o descripción breve| El contenido se muestra correctamente.
Texto de longitud normal| Texto habitual de una actividad| El contenido se muestra completo.
Texto largo| Título o descripción extensa| El contenido permanece visible y no genera superposición.

Pruebas de límite

Se prestará especial atención a las actividades que contengan títulos o descripciones extensas, ya que permiten comprobar que la interfaz se adapte correctamente y que la información no aparezca cortada.

Objetivo

Verificar que las tarjetas de actividades mantengan una presentación organizada y legible cuando contienen textos de diferentes longitudes.

---

7. Tabla de decisión

La tabla de decisión permite analizar diferentes condiciones que pueden presentarse al mostrar las actividades formativas en la aplicación Mi Formación CTMA.

Se consideran las siguientes condiciones:

- C1: ¿Existen actividades para mostrar?
- C2: ¿La actividad tiene título?
- C3: ¿La descripción está disponible?
- C4: ¿La información se presenta correctamente?

Regla| C1: Hay actividades| C2: Tiene título| C3: Tiene descripción| C4: Presentación correcta| Acción esperada
R1| Sí| Sí| Sí| Sí| Mostrar actividad con título y descripción.
R2| Sí| Sí| No| Sí| Mostrar actividad con título y continuar sin descripción.
R3| Sí| Sí| Sí| No| Identificar el problema de presentación.
R4| No| No aplica| No aplica| Sí| Mostrar el estado vacío de actividades.

Interpretación

R1: Cuando existen actividades, tienen título y descripción, y la presentación es correcta, la información debe mostrarse normalmente.

R2: Cuando una actividad no tiene descripción, debe seguir siendo posible mostrarla correctamente, ya que la descripción es opcional.

R3: Cuando la información existe pero la presentación presenta un problema, se debe identificar y registrar la situación como posible defecto.

R4: Cuando no existen actividades, la aplicación debe mostrar el estado vacío correspondiente.

---

8. Transición de estados

La transición de estados permite analizar cómo cambia la interfaz dependiendo de la situación de las actividades.

Para esta prueba se consideran estados de la interfaz y no estados de negocio que no estén implementados en la aplicación.

Estados

- E1 — Pantalla inicial: La aplicación acaba de abrirse.
- E2 — Lista con actividades: La aplicación muestra las actividades disponibles.
- E3 — Estado vacío: No existen actividades para mostrar.

Transición válida

E1 → E2

1. Abrir la aplicación.
2. Acceder a la pantalla de actividades.
3. La lista contiene actividades.
4. Se muestran las tarjetas correspondientes.

Resultado esperado: La pantalla pasa correctamente de la pantalla inicial a la lista de actividades.

Transición alternativa

E1 → E3

1. Abrir la aplicación.
2. Acceder a la pantalla de actividades.
3. La lista no contiene actividades.
4. Se muestra el estado vacío.

Resultado esperado: La aplicación informa que no existen actividades para mostrar.

Transición inválida 1

E3 → E2 sin cargar/agregar actividades

Si no existen actividades, la aplicación no debe mostrar tarjetas inexistentes.

Resultado esperado: Debe mantenerse el estado vacío hasta que existan actividades disponibles.

Transición inválida 2

E2 → E3 sin que la lista haya quedado vacía

Si existen actividades disponibles, no debe mostrarse el estado vacío.

Resultado esperado: Deben mantenerse visibles las actividades existentes.

---

9. Escenarios derivados del caso de uso

Caso de uso principal — Consultar actividades

Actor: Aprendiz SENA

Flujo principal:

1. El aprendiz abre la aplicación.
2. La aplicación muestra la interfaz.
3. El aprendiz consulta las actividades.
4. La aplicación muestra las actividades disponibles.
5. El aprendiz puede revisar la información de las tarjetas.

Resultado esperado: Las actividades se muestran correctamente y pueden ser consultadas.

---

Escenario alterno 1 — Actividad sin descripción

1. El aprendiz consulta las actividades.
2. Una actividad no contiene descripción.
3. La aplicación muestra la actividad utilizando la información disponible.

Resultado esperado: La actividad continúa siendo visible y la interfaz permanece organizada.

---

Escenario alterno 2 — No existen actividades

1. El aprendiz consulta la sección de actividades.
2. No existen actividades disponibles.
3. La aplicación muestra el estado vacío.

Resultado esperado: El usuario recibe una indicación clara de que no hay actividades para mostrar.

---

10. Resumen de cobertura

Técnica| Casos relacionados
Partición de equivalencia| CP-01, CP-02, CP-06, CP-10
Valores límite| CP-03, CP-08
Tabla de decisión| Casos derivados de R1, R2, R3 y R4
Transición de estados| E1→E2, E1→E3 y transiciones inválidas
Casos de uso| CP-04, CP-05, CP-09 y escenarios alternos

---

11. Criterios de aceptación cubiertos

Criterio| Caso de prueba
CA-01| CP-01
CA-02| CP-02
CA-03| CP-03
CA-04| CP-04
CA-05| CP-05
CA-06| CP-06
CA-07| CP-07
CA-08| CP-08
CA-09| CP-09
CA-10| CP-10
CA-11| CP-11
CA-12| CP-12

---

12. Datos de prueba

Los datos utilizados durante las pruebas son sintéticos y fueron diseñados únicamente para comprobar el funcionamiento y presentación de la aplicación.

Ejemplos:

- "Actividad de aprendizaje"
- "Actividad con título largo para comprobar la presentación de la interfaz"
- Descripción disponible.
- Descripción "null".
- Varias actividades cargadas.
- Lista sin actividades para comprobar el estado vacío.

No se utilizan datos personales reales.

---

13. Resultado general esperado

La aplicación Mi Formación CTMA debe permitir consultar la información de formación y las actividades de manera clara y organizada.

Las tarjetas deben conservar una presentación adecuada con diferentes cantidades de información y diferentes longitudes de texto.

Cuando no existan actividades, debe mostrarse el estado vacío correspondiente.

Las pruebas también permiten identificar posibles problemas de presentación, legibilidad, organización o interacción para posteriormente registrarlos en el documento de Gestión de Defectos.md.

---

14. Estado de ejecución

Los casos de prueba serán ejecutados utilizando la aplicación desarrollada en Android Studio.

ID| Resultado| Evidencia
CP-01| Pendiente de ejecución/evidencia| Por registrar
CP-02| Pendiente de ejecución/evidencia| Por registrar
CP-03| Pendiente de ejecución/evidencia| Por registrar
CP-04| Pendiente de ejecución/evidencia| Por registrar
CP-05| Pendiente de ejecución/evidencia| Por registrar
CP-06| Pendiente de ejecución/evidencia| Por registrar
CP-07| Pendiente de ejecución/evidencia| Por registrar
CP-08| Pendiente de ejecución/evidencia| Por registrar
CP-09| Pendiente de ejecución/evidencia| Por registrar
CP-10| Pendiente de ejecución/evidencia| Por registrar
CP-11| Pendiente de ejecución/evidencia| Por registrar
CP-12| Pendiente de ejecución/evidencia| Por registrar

«Los resultados PASS/FAIL y las capturas de evidencia se completarán después de realizar las pruebas sobre la versión final de la aplicación.»