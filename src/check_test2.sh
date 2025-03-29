#!/bin/bash

cat test2.txt | java Proto > log2.txt

if grep -q "LASSITO" log2.txt; then
  echo -e "\e[32m[OK]\e[0m Teszt sikeres: Rovaron a lassito allapot beallitodott."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Teszt sikertelen: lassito allapot nem lett ballitva."
  exit 1
fi