#!/bin/bash

cat test22.txt | java Proto > log22.txt

# Proto tul okos, mar a modellt sem hivja mert latja, hogy nincs GF1...
atkerult_szam=$(grep -c "atkerult" log22.txt)

if [ "$atkerult_szam" -eq 2 ]; then
  echo -e "\e[32m[OK]\e[0m OK: 2 spora 2 szomszedra szetszorva."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: valami nem jo a sporazassal ha tobb szomszed van!"
  exit 1
fi