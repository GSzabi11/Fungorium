#!/bin/bash

cat test18.txt | java Proto > log18.txt

if grep -q "Nem letezo" log18.txt; then
  echo -e "\e[32m[OK]\e[0m OK: rovar nem tud nem letezo fonalat vagni."
  exit 0
fi

# Proto tul okos, mar a modellt sem hivja mert latja, hogy nincs GF1...
vagas_szam=$(grep -c "elvagta" log18.txt)

if [ "$vagas_szam" -eq 0 ]; then
  echo -e "\e[32m[OK]\e[0m OK: a rovar nem csinalt semmit, mivel nincs mit elvagni."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: rovar elvagta a nem letezo fonalat!"
  exit 1
fi