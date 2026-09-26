Set-Location $PSScriptRoot
python -m pip install -r requirements.txt
if ($LASTEXITCODE -ne 0) { Read-Host "No se pudieron instalar las dependencias"; exit 1 }
python -m uvicorn main:app --host 0.0.0.0 --port 8000
