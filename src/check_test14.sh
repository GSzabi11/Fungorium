#!/bin/bash

cat test14.txt | java Proto > log14.txt

if grep -q "nem tud mozogni" log14.txt; then
  echo -e "\e[32m[OK]\e[0m OK: Rovar mozogna, de benult."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: rovar mozog, pedig benult!"
  exit 1
fi