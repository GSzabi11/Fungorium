#!/bin/bash

cat test21.txt | java Proto > log21.txt

if grep -q "nincs termelt sporaja" log21.txt; then
  echo -e "\e[32m[OK]\e[0m Gomba nem letezo sporat nem szor szet"
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: nem stimmel a sporaszoras, nem letezo sporat szorunk!"
  exit 1
fi