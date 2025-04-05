#!/bin/bash

cat test12.txt | java Proto > log12.txt

if grep -q "mozog: uj hely" log12.txt; then
  echo -e "\e[32m[OK]\e[0m Rovar mozog rendben."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: rovar nem mozog!"
  exit 1
fi