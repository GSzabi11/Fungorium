#!/bin/bash

cat test6.txt | java Proto > log6.txt

if grep -q "T2" log6.txt; then
  echo -e "\e[32m[OK]\e[0m rovarklonozas rendben."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: nem stimmel a rovarok szama."
  exit 1
fi