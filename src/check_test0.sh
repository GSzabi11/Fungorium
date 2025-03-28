#!/bin/bash

cat test0.txt | java Proto > log0.txt

if grep -q "8" log0.txt; then
  echo -e "\e[32m[OK]\e[0m Teszt sikeres: Rovar tapanyagszintje nott."
else
  echo -e "\e[31m[ERROR]\e[0m Teszt sikertelen: nem nott meg a tapanyagszint."
fi