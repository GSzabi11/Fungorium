#!/bin/bash

cat test13.txt | java Proto > log13.txt

if grep -q "nincs fonal" log13.txt; then
  echo -e "\e[32m[OK]\e[0m OK: Rovar nem mozog nem letezo fonalakon."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: rovar mozog, pedig nincs fonal!"
  exit 1
fi