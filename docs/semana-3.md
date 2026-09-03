# Semana 3 — Compose, UX y adaptación

## Implementación
- `TarjetaActividad` es un composable parametrizado y sin estado de negocio interno.
- `ListaActividades` usa `LazyColumn` y `key = { it.id }`.
- Ancho compacto: lista. Ancho ampliado (≥ 600 dp): `LazyVerticalGrid` de dos columnas.
- Estado vacío con mensaje y acción.
- Material 3 centraliza colores y tipografía.
- La tarjeta comunica estado con texto, no solo con color. La ilustración del estado vacío es decorativa/informativa y tiene descripción semántica.
- Semántica descriptiva para tarjetas, acciones e ilustración.

## Previews solicitadas
El archivo de componentes contiene datos representativos para los escenarios que deben revisarse en Preview: normal, título largo, progreso 0, progreso 100 y lista vacía. La prueba con fuente 1.5x y ancho ampliado debe comprobarse en Preview o dispositivo.

## Checklist UX
- [ ] Fuente ampliada sin recortes.
- [ ] Contraste legible.
- [ ] Objetivos táctiles utilizables.
- [ ] Orden de lectura comprensible.
- [ ] Estado comunicado con texto.
- [ ] Prueba en teléfono.
- [ ] Prueba en ancho ampliado.
- [ ] Prueba cruzada con compañero y un hallazgo documentado.
