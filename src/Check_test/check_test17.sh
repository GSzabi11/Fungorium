#!/bin/bash

cat test17.txt | java Proto > log17.txt

if grep -q "elvagta" log17.txt; then
  echo -e "\e[32m[OK]\e[0m Rovar fonal vagas rendben."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: rovar nem tudta elvagni a fonalat!"
  exit 1
fi