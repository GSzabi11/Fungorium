#!/bin/bash

cat test20.txt | java Proto > log20.txt

if grep -q "atkerult" log20.txt; then
  echo -e "\e[32m[OK]\e[0m Gomba sporaz rendben"
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: valami nem stimmel a sporaszorassal!"
  exit 1
fi