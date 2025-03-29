#!/bin/bash

cat test4.txt | java Proto > log4.txt

if grep -q "VAGASTGATLO" log4.txt; then
  echo -e "\e[32m[OK]\e[0m Teszt sikeres: Rovaron a vagast gatlo allapot beallitodott."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Teszt sikertelen: vagast gatlo allapot nem lett ballitva."
  exit 1
fi