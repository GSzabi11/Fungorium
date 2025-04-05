#!/bin/bash

cat test10.txt | java Proto > log10.txt

if grep -q "tovabb" log10.txt; then
  echo -e "\e[32m[OK]\e[0m Gombafonal tovabb novesztese rendben."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: ugy novesztunk tovabb gombafonalat, hogy nem szomszedok a tektonok!."
  exit 1
fi