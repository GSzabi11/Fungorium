#!/bin/bash

cat test15.txt | java Proto > log15.txt

if grep -q "2.0" log15.txt; then
  echo -e "\e[32m[OK]\e[0m Rovar gyorsan mozog rendben."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: rovar sebessege nem stimmel!"
  exit 1
fi