#!/bin/bash

cat test7.txt | java Proto > log7.txt

if grep -q "created" log7.txt; then
  echo -e "\e[32m[OK]\e[0m Sporatermeles rendben."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: nem stimmel a spora termeles."
  exit 1
fi