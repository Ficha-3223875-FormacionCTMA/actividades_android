# ACTIVIDAD DE SCRUM

## PROYECTO ENTREGASEGURA

### Diagnóstico de aprendizajes previos y organización del Sprint

**Programa:** Análisis y Desarrollo de Software (ADSO)

**Ficha:** 3223875

**Aprendiz:** Alejandra Herrera

**Instructor:** wilson castro gil

**Centro de formación:** CTMA – SENA

**Fecha:** 14 de septiembre de 2026

---

# INTRODUCCIÓN

El presente documento tiene como finalidad analizar y organizar las necesidades del proyecto EntregaSegura mediante la metodología ágil Scrum.

EntregaSegura es un sistema orientado a la gestión de entregas de medicamentos a pacientes. El proyecto contempla diferentes funcionalidades, como el registro de pacientes, el inicio de sesión, la creación de órdenes de entrega, la asignación de mensajeros y el seguimiento de los estados de las órdenes.

A partir de las necesidades expresadas en las tarjetas del diagnóstico, se construye un Product Backlog ordenado por prioridad. Posteriormente, se seleccionan tres historias de usuario para conformar un Sprint, se establece un objetivo de trabajo y se definen las actividades necesarias para su desarrollo.

Finalmente, se establece una Definition of Done, que permite determinar las condiciones de calidad y cumplimiento que deben tener las funcionalidades para considerarse terminadas.

# 1. OBJETIVOS

## 1.1 Objetivo general

Organizar las necesidades del proyecto EntregaSegura mediante Scrum, construyendo un Product Backlog, historias de usuario, Sprint Goal, Sprint Backlog y Definition of Done.

## 1.2 Objetivos específicos

* Identificar y ordenar las necesidades principales del sistema EntregaSegura.
* Formular tres historias de usuario con sus respectivos criterios de aceptación.
* Definir un objetivo para el Sprint.
* Organizar las tareas necesarias para desarrollar las historias seleccionadas.
* Establecer condiciones de calidad para considerar terminado el trabajo.

# 2. DESCRIPCIÓN DEL CASO ENTREGASEGURA

EntregaSegura es un sistema para organizar la operación de entrega de medicamentos a pacientes.

El sistema contempla diferentes actores:

| Actor               | Necesidad                                                      |
| ------------------- | -------------------------------------------------------------- |
| Administrador       | Crear, activar, desactivar usuarios y asignar roles.           |
| Coordinador         | Registrar pacientes, crear órdenes y organizar las entregas.   |
| Mensajero           | Consultar órdenes asignadas y registrar evidencias de entrega. |
| Usuario de consulta | Consultar información permitida según sus permisos.            |

El proyecto debe tener en cuenta la seguridad de la información personal, los permisos de los usuarios y el registro de los cambios realizados en las órdenes.

# 3. PRODUCT BACKLOG

## 3.1 Definición

El Product Backlog es una lista ordenada de las necesidades y funcionalidades que se desean construir para el proyecto EntregaSegura.

Las prioridades presentadas a continuación son una propuesta del equipo para organizar el trabajo inicial. La guía proporciona las necesidades, pero no establece un orden obligatorio de prioridad.

## 3.2 Tabla del Product Backlog

| ID    | Historia o necesidad           | Descripción                                                                             | Prioridad |
| ----- | ------------------------------ | --------------------------------------------------------------------------------------- | --------- |
| ES-02 | Iniciar sesión                 | Permitir el acceso de usuarios autorizados con correo o nombre de usuario y contraseña. | 1         |
| ES-01 | Registrar paciente             | Registrar la información de un paciente antes de crear una orden.                       | 2         |
| ES-03 | Crear orden de entrega         | Registrar una orden asociada a un paciente existente.                                   | 3         |
| ES-04 | Asignar mensajero              | Asignar una orden a un mensajero disponible.                                            | 4         |
| ES-05 | Consultar órdenes pendientes   | Consultar las entregas pendientes con filtros.                                          | 5         |
| ES-06 | Cambiar estado de una orden    | Actualizar el estado de una orden durante el proceso de entrega.                        | 6         |
| ES-07 | Adjuntar evidencia fotográfica | Adjuntar una fotografía como evidencia de la entrega.                                   | 7         |
| ES-08 | Registrar firma del paciente   | Capturar la firma del paciente o persona autorizada.                                    | 8         |
| ES-14 | Registrar entrega fallida      | Registrar el motivo y observaciones de una entrega no completada.                       | 9         |
| ES-09 | Consultar historial de cambios | Consultar lo ocurrido con una orden desde su creación hasta su cierre.                  | 10        |
| ES-10 | Recuperar contraseña           | Permitir recuperar el acceso mediante un correo registrado.                             | 11        |
| ES-13 | Administrar usuarios y roles   | Crear, activar, desactivar y asignar responsabilidades.                                 | 12        |
| ES-11 | Exportar reporte               | Descargar información de las entregas en Excel o PDF.                                   | 13        |
| ES-12 | Notificar al paciente          | Informar al paciente sobre el estado de su medicamento.                                 | 14        |
| ES-15 | Consultar indicadores          | Visualizar indicadores del desempeño de las entregas.                                   | 15        |

## 3.3 Justificación de la priorización

Se priorizaron inicialmente las funcionalidades de inicio de sesión, registro de pacientes y creación de órdenes porque permiten establecer una base para organizar las entregas de medicamentos.

Posteriormente, se incluyen las funcionalidades relacionadas con la asignación de mensajeros, consulta de órdenes, seguimiento y evidencias. Finalmente, se dejan para etapas posteriores los reportes, las notificaciones y los indicadores.

Esta priorización es una propuesta académica del equipo y puede cambiar según las necesidades del negocio.

# 4. HISTORIAS DE USUARIO

## 4.1 Historia de usuario ES-02 — Iniciar sesión

**ID:** ES-02

**Prioridad:** 1

**Título:** Iniciar sesión

### Historia

Como usuario autorizado, quiero ingresar a EntregaSegura con mi correo o nombre de usuario y contraseña, para acceder únicamente a las funciones que me corresponden.

### Criterios de aceptación

1. Si las credenciales son correctas, el usuario puede ingresar al sistema.
2. Si las credenciales son incorrectas, el sistema muestra un mensaje general de error.
3. Si el usuario está inactivo, no puede acceder.
4. El sistema debe respetar los permisos correspondientes al rol del usuario.

### Resultado esperado

El usuario autorizado puede ingresar al sistema y acceder únicamente a las funciones permitidas.

---

## 4.2 Historia de usuario ES-01 — Registrar paciente

**ID:** ES-01

**Prioridad:** 2

**Título:** Registrar paciente

### Historia

Como personal administrativo, quiero registrar los datos de un paciente, para tenerlo disponible antes de crear una orden de entrega de medicamentos.

### Criterios de aceptación

1. El sistema permite registrar tipo y número de documento, nombres, teléfono, dirección, municipio y contacto autorizado.
2. No se permite registrar dos pacientes con el mismo tipo y número de documento.
3. La información personal solo está disponible para usuarios autorizados.
4. El paciente queda registrado correctamente para utilizarlo en una orden de entrega.

### Resultado esperado

El personal administrativo puede registrar un paciente sin duplicar su documento y la información queda disponible para la operación autorizada.

---

## 4.3 Historia de usuario ES-03 — Crear orden de entrega

**ID:** ES-03

**Prioridad:** 3

**Título:** Crear orden de entrega

### Historia

Como coordinador, quiero registrar una orden de entrega asociada a un paciente existente, para organizar la entrega de los medicamentos al paciente correcto.

### Criterios de aceptación

1. La orden solo puede asociarse a un paciente existente.
2. La orden debe incluir dirección, ventana de entrega, observaciones y medicamentos o paquetes a entregar.
3. Toda orden nueva inicia en el estado definido por el negocio.
4. El sistema no permite crear una orden sin paciente asociado.

### Resultado esperado

El coordinador puede crear una orden completa y asociarla correctamente a un paciente existente.

# 5. SPRINT GOAL

## Objetivo del Sprint

"Crear una base funcional para que los usuarios autorizados puedan ingresar a EntregaSegura, registrar pacientes y crear órdenes de entrega de medicamentos."

## Justificación

Este objetivo permite iniciar el desarrollo del sistema con funcionalidades fundamentales para la operación de EntregaSegura.

El Sprint busca obtener una base que permita controlar el acceso, registrar pacientes y crear órdenes de entrega, dejando las demás funcionalidades para futuros incrementos.

# 6. SPRINT BACKLOG

## 6.1 Definición

El Sprint Backlog está compuesto por las historias de usuario seleccionadas para el Sprint y las tareas necesarias para cumplir el Sprint Goal.

## 6.2 Sprint 1

### Historia ES-02 — Iniciar sesión

| No. | Tarea                                              |
| --- | -------------------------------------------------- |
| 1   | Diseñar la pantalla de inicio de sesión.           |
| 2   | Crear los campos de usuario y contraseña.          |
| 3   | Validar las credenciales.                          |
| 4   | Mostrar mensajes de error.                         |
| 5   | Validar el acceso de usuarios activos e inactivos. |
| 6   | Realizar pruebas funcionales.                      |

### Historia ES-01 — Registrar paciente

| No. | Tarea                                         |
| --- | --------------------------------------------- |
| 1   | Diseñar el formulario de registro.            |
| 2   | Crear los campos de información del paciente. |
| 3   | Validar el tipo y número de documento.        |
| 4   | Evitar documentos duplicados.                 |
| 5   | Guardar la información del paciente.          |
| 6   | Verificar los permisos de acceso.             |
| 7   | Realizar pruebas funcionales.                 |

### Historia ES-03 — Crear orden de entrega

| No. | Tarea                                            |
| --- | ------------------------------------------------ |
| 1   | Diseñar el formulario de creación de órdenes.    |
| 2   | Permitir seleccionar un paciente existente.      |
| 3   | Agregar dirección y ventana de entrega.          |
| 4   | Agregar observaciones y medicamentos o paquetes. |
| 5   | Asignar el estado inicial de la orden.           |
| 6   | Validar que la orden tenga paciente asociado.    |
| 7   | Realizar pruebas funcionales.                    |

## 6.3 Resultado esperado del Sprint

Al finalizar el Sprint se espera contar con una base funcional que permita:

* Iniciar sesión con un usuario autorizado.
* Registrar pacientes.
* Crear órdenes de entrega asociadas a pacientes existentes.
* Verificar que las funcionalidades cumplan sus criterios de aceptación.

# 7. DEFINITION OF DONE

## 7.1 Definición

La Definition of Done es el conjunto de condiciones que debe cumplir una historia de usuario para considerarse terminada.

## 7.2 Condiciones de terminado

Una historia de usuario se considera terminada cuando:

1. El desarrollo de la funcionalidad está completo.
2. La funcionalidad cumple los criterios de aceptación definidos.
3. Se realizaron pruebas funcionales.
4. Se revisaron y corrigieron los errores encontrados.
5. Se verificaron los permisos de acceso cuando corresponda.
6. Se protegió la información sensible según las reglas del sistema.
7. El código fue revisado por otro integrante del equipo, cuando aplique.
8. La funcionalidad está documentada.
9. Se puede demostrar el funcionamiento de la historia.
10. El equipo considera que la historia cumple con los criterios establecidos.

## 7.3 Importancia de la Definition of Done

La Definition of Done permite que todos los integrantes del equipo tengan claridad sobre cuándo una tarea está realmente terminada.

También ayuda a mantener la calidad del producto, evitar errores y asegurar que las funcionalidades cumplan las necesidades del negocio.

# 8. CONCLUSIÓN

La actividad permitió organizar las necesidades del proyecto EntregaSegura utilizando la metodología Scrum.

Mediante el Product Backlog se identificaron y priorizaron las funcionalidades principales del sistema. Luego se formularon tres historias de usuario relacionadas con el inicio de sesión, el registro de pacientes y la creación de órdenes de entrega.

El Sprint Goal permitió establecer un objetivo común para el equipo, mientras que el Sprint Backlog organizó las tareas necesarias para desarrollar las historias seleccionadas.

Finalmente, la Definition of Done estableció las condiciones de calidad que deben cumplirse para considerar terminado el trabajo.

Scrum facilita la organización, la colaboración y el desarrollo progresivo de un producto, permitiendo entregar funcionalidades de manera ordenada y mejorar continuamente el resultado del proyecto.

# 9. REFERENCIA

SENA. *Kit de Tarjetas de Necesidades para el Diagnóstico de Scrum — EntregaSegura*. Programa ADSO. Documento suministrado para la actividad de aprendizaje.
