#!/bin/bash

cat test5.txt | java Proto > log5.txt

if grep -q "klonozva! (BARNA)" log5.txt; then
  echo -e "\e[32m[OK]\e[0m rovarklonozas rendben."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: nem stimmel a rovarok szama."
  exit 1
fi