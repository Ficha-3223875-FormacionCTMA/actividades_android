# Semana 3 — Interfaces declarativas con Jetpack Compose

## Implementación

- UI declarativa con Jetpack Compose y Material 3.
- Componentes reutilizables en `ui/components`.
- Pantallas separadas en `ui/screens`.
- Tema centralizado en `ui/theme`.
- `LazyColumn` con `key = { it.id }`.
- `LazyVerticalGrid` para vista amplia/cuadrícula.
- `BoxWithConstraints` usa 600.dp como umbral para adaptar la lista a dos columnas.
- Estado vacío, contenido, carga y error.
- Previews para tamaño normal, fuente al 150 % y pantalla ancha.
- Los estados de actividad se muestran con texto además de color.

## Accesibilidad y adaptación

- Los textos largos pueden crecer por escalado de fuente.
- Se conserva el orden lógico de lectura.
- Los controles principales ocupan el ancho disponible.
- La interfaz no depende exclusivamente del color para comunicar estado.
- La pantalla se prueba con configuración compacta y amplia mediante Preview.

## Evidencia recomendada

Al abrir Android Studio, ejecutar los Previews `PreviewPantallaActividades`, `PreviewPantallaActividadesFuenteGrande` y `PreviewPantallaActividadesAncha` y guardar capturas para la entrega.
