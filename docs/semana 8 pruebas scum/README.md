¡Perfecto! Ya casi terminamos esta parte. Ahora vamos a crear el README de pruebas, que te sirve para que el instructor vea claramente cómo ejecutar todo.

Crea este archivo dentro de:

C:\Users\herre\AndroidStudioProjects\actividades_android\pruebas\README.md

Pega todo esto:

# Pruebas automatizadas — Mi Formación CTMA

## 1. Descripción

Este proyecto contiene las pruebas automatizadas desarrolladas para
Mi Formación CTMA como parte de la Semana 8 de Pruebas de Software
y SCRUM.

Se utilizan pruebas unitarias y pruebas automatizadas de API para
verificar reglas de negocio, validaciones, cambios de estado y
autenticación.

---

# 2. Tecnologías utilizadas

- Node.js
- npm
- Vitest 3.2.7
- V8 Coverage
- Python
- pytest 9.1.1
- FastAPI TestClient
- httpx

---

# 3. Estructura

text
actividades_android/
│
├── pruebas/
│   ├── package.json
│   ├── vitest.config.js
│   │
│   ├── src/
│   │   ├── actividad-status.js
│   │   ├── actividad-service.js
│   │   └── fixtures/
│   │       └── actividad.js
│   │
│   ├── tests/
│   │   ├── actividad-status.test.js
│   │   ├── actividad-status-parametrized.test.js
│   │   └── actividad-service.test.js
│   │
│   └── README.md
│
├── pruebas_api/
│   └── test_api.py
│
└── docs/
    └── semana 8 pruebas scrum/
        ├── evidencias/
        ├── matriz-trazabilidad.md
        ├── sprint-backlog-y-dod.md
        └── evidencia-tdd.md


---

4. Instalación

Abrir PowerShell dentro de la carpeta:

pruebas

Ejecutar:

npm install


---

5. Ejecutar pruebas unitarias

Comando:

npm test

La suite contiene pruebas relacionadas con:

estados de actividades;

transiciones de estado;

validación de títulos;

validación de descripciones;

actualización de actividades;

actividades inexistentes.


Resultado obtenido durante la ejecución:

19 pruebas aprobadas
0 pruebas fallidas


---

6. Ejecutar pruebas en modo observación

Para ejecutar Vitest en modo watch:

npm run test:watch

Este modo permite ejecutar nuevamente las pruebas cuando se detectan cambios en los archivos.

Para detenerlo:

Ctrl + C


---

7. Ejecutar cobertura

Comando:

npm run coverage

Resultado registrado:

Cobertura general: 76,36 %

Cobertura por archivo

actividad-service.js

Statements: 100 %
Branches:   100 %
Functions:  100 %
Lines:      100 %

actividad-status.js

Statements: 92,59 %
Branches:   86,66 %
Functions:  100 %
Lines:      92,59 %

El reporte HTML de cobertura se genera dentro de:

pruebas/coverage/


---

8. Pruebas de API

Las pruebas de API se encuentran en:

pruebas_api/test_api.py

Estas pruebas utilizan el backend FastAPI real mediante FastAPI TestClient.


---

9. Instalar dependencias de Python

Desde la carpeta del proyecto se pueden instalar:

python -m pip install pytest httpx


---

10. Ejecutar pruebas API

Desde la raíz del proyecto:

python -m pytest pruebas_api

Resultado obtenido:

3 passed
0 failed


---

11. Casos de API automatizados

GET /api/actividades

Verifica que un usuario autenticado pueda consultar las actividades.

Resultado esperado:

HTTP 200

Resultado:

APROBADO


---

POST /api/actividades

Verifica que una actividad válida pueda ser creada.

Resultado esperado:

HTTP 201

Resultado:

APROBADO


---

GET /api/actividades sin autenticación

Verifica que el sistema rechace una solicitud sin token.

Resultado esperado:

HTTP 401

Resultado:

APROBADO


---

12. Fixture

El proyecto utiliza una fixture para crear datos de prueba reutilizables.

Archivo:

src/fixtures/actividad.js

Función:

crearActividadFixture()

Esto evita repetir manualmente los mismos datos en diferentes pruebas.


---

13. Mock / Stub

En las pruebas del servicio se utiliza:

vi.fn()

para simular las operaciones del repositorio.

Por ejemplo:

buscarPorId: vi.fn().mockResolvedValue(actividad)

y:

guardar: vi.fn().mockResolvedValue(undefined)

Esto permite probar el servicio sin depender de una base de datos real.


---

14. Pruebas parametrizadas

El proyecto utiliza pruebas parametrizadas mediante:

it.each()

Archivo:

tests/actividad-status-parametrized.test.js

Se prueban diferentes combinaciones de estados, por ejemplo:

PENDIENTE → EN_PROCESO
EN_PROCESO → COMPLETADA
PENDIENTE → CANCELADA
CANCELADA → COMPLETADA
ESTADO_INVALIDO → COMPLETADA


---

15. Repetibilidad

La suite de pruebas unitarias fue ejecutada tres veces sin modificar el código.

Resultado:

Ejecución 1: 18 pruebas aprobadas
Ejecución 2: 18 pruebas aprobadas
Ejecución 3: 18 pruebas aprobadas

Posteriormente se agregó la prueba correspondiente al ciclo TDD, por lo que la suite actual registra:

19 pruebas aprobadas


---

16. TDD

Se documentó un ciclo real:

RED → GREEN → REFACTOR

La funcionalidad utilizada fue la validación de la transición:

CANCELADA → COMPLETADA

Esta transición debe ser rechazada.

La evidencia completa se encuentra en:

docs/semana 8 pruebas scrum/evidencia-tdd.md


---

17. Documentación de Semana 8

Las evidencias se encuentran en:

docs/semana 8 pruebas scrum/

Incluyen:

pruebas unitarias;

cobertura;

repetibilidad;

pruebas API;

matriz de trazabilidad;

Sprint Backlog;

Definition of Done;

evidencia TDD.



---

18. Comandos principales

Pruebas unitarias

cd pruebas
npm test

Cobertura

cd pruebas
npm run coverage

Modo watch

cd pruebas
npm run test:watch

Pruebas API

python -m pytest pruebas_api


---

19. Resultado general

El proyecto cuenta con:

pruebas unitarias automatizadas;

pruebas parametrizadas;

fixture de datos;

mock de dependencias;

pruebas automatizadas de API;

cobertura de código;

pruebas repetibles;

matriz de trazabilidad;

Sprint Backlog;

Definition of Done;

evidencia TDD.


Los resultados registrados muestran que las pruebas automatizadas ejecutadas actualmente son exitosas.

### Después de guardarlo

Vamos a verificar que el README quede en:

```text
pruebas
└── README.md

