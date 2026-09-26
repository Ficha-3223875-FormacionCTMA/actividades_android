@echo off
title Mi Formacion CTMA - API
cd /d "%~dp0"
echo.
echo ==========================================
echo   Mi Formacion CTMA - API FastAPI
echo ==========================================
echo.
echo API para el PC:       http://127.0.0.1:8000/docs
echo API para el emulador: http://10.0.2.2:8000/docs
echo.
python -m pip install -r requirements.txt
if errorlevel 1 (
  echo.
  echo No se pudieron instalar las dependencias.
  pause
  exit /b 1
)
echo.
echo Iniciando API...
python -m uvicorn main:app --host 0.0.0.0 --port 8000
pause
