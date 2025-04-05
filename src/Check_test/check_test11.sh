#!/bin/bash

cat test11.txt | java Proto > log11.txt

if grep -q "nem szomszedja" log11.txt; then
  echo -e "\e[32m[OK]\e[0m Gombafonal tovabb novesztesenel szomszedossag ellenorzese rendben."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: ugy novesztunk tovabb gombafonalat, hogy nem szomszedok a tektonok!."
  exit 1
fi