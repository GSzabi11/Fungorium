#!/bin/bash

Színes statisztikák ANSI escape kódokkal
zold='\033[0;32m'
piros='\033[0;31m'
reset='\033[0m'

osszes=0
sikeres=0

echo "Tesztfuttatás indul..."
echo

for f in check_test*.sh; do
  echo "------ ${f} ------"
  chmod +x "$f"
  if bash "$f"; then
    ((sikeres++))
  fi
  ((osszes++))
  echo
done

hibas=$((osszes - sikeres))

echo "=========================="
echo -e "${zold}Sikeres: ${sikeres}${reset} / ${piros}Hibás: ${hibas}${reset} teszt összesen: ${osszes}"