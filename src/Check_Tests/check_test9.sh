#!/bin/bash

cat test9.txt | java Proto > log9.txt

if grep -q "nem szomszedja" log9.txt; then
  echo -e "\e[32m[OK]\e[0m Uj gombafonal novesztesenel szomszedossag ellenorzese rendben."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: ugy novesztunk uj gombafonalat, hogy nem szomszedok a tektonok!."
  exit 1
fi