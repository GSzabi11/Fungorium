#!/bin/bash

cat test1.txt | java Proto > log1.txt

if grep -q "BENITO" log1.txt; then
  echo -e "\e[32m[OK]\e[0m Teszt sikeres: Rovaron a benito allapot beallitodott."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Teszt sikertelen: benito allapot nem lett ballitva."
  exit 1
fi