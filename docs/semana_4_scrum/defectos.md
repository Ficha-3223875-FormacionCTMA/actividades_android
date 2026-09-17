Registro de Defectos — Semana 4

1. Información general

Proyecto: Mi Formación CTMA
Actividad: Pruebas de software — Semana 4

2. Objetivo

Registrar los defectos encontrados durante la ejecución de las pruebas y diferenciar entre comportamientos esperados y errores reales del sistema.

3. Defectos encontrados

Durante la ejecución de las pruebas no se confirmó ningún defecto funcional que requiriera ser registrado como incidente.

ID| Caso| Descripción| Resultado esperado| Resultado obtenido| Estado
DEF-001| —| No se confirmaron defectos funcionales durante la ejecución| Las funcionalidades deben responder de acuerdo con lo especificado| Comportamiento observado conforme a las pruebas realizadas| Cerrado / Sin defecto confirmado

4. Observaciones

Durante las pruebas se presentaron respuestas HTTP correspondientes a escenarios negativos. Estas respuestas no fueron consideradas defectos cuando coincidieron con el comportamiento esperado.

Por ejemplo, en el caso EV-14 — Login incorrecto, la API respondió con código 401 Unauthorized. Este resultado es correcto porque las credenciales utilizadas eran incorrectas.

También se realizaron pruebas con datos no válidos para comprobar las validaciones de la API. Estas respuestas fueron consideradas parte del comportamiento esperado de las pruebas negativas.

5. Conclusión

Con base en las pruebas ejecutadas durante la Semana 4, no se identificaron defectos funcionales confirmados que deban ser reportados como errores del sistema.

Las respuestas de error observadas durante los casos negativos fueron analizadas de acuerdo con el resultado esperado de cada prueba.