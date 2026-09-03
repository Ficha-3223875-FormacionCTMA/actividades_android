# Flujo unidireccional (UDF)

```text
        ESTADO
          ↓
   FormularioActividad
          ↓
       INTERFAZ
          ↓
        EVENTO
          ↑
   callbacks del usuario
          ↑
   Contenedor de la app
          ↓
   nuevo estado / lista
          ↓
      recomposición
```

El componente visual no modifica directamente la fuente de verdad; recibe datos y emite eventos.
