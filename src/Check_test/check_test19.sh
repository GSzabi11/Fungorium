#!/bin/bash

cat test19.txt | java Proto > log19.txt

if grep -q "nem tud vagni" log19.txt; then
  echo -e "\e[32m[OK]\e[0m OK: Vagast gatlo hatas nem enged fonalat vagni"
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: rovar vagast gatlo hatasa alatt vagott fonalat!"
  exit 1
fi