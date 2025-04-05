#!/bin/bash

cat test8.txt | java Proto > log8.txt

if grep -q "uj gombafonalat" log8.txt; then
  echo -e "\e[32m[OK]\e[0m Uj gombafonal novesztese rendben."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: nem stimmel az uj gombafonal novesztes."
  exit 1
fi