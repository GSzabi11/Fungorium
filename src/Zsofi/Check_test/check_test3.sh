#!/bin/bash

cat test3.txt | java Proto > log3.txt

if grep -q "GYORSITO" log3.txt; then
  echo -e "\e[32m[OK]\e[0m Teszt sikeres: Rovaron a gyorsito allapot beallitodott."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Teszt sikertelen: gyorsito allapot nem lett ballitva."
  exit 1
fi