#!/bin/bash

cat test16.txt | java Proto > log16.txt

if grep -q "0.5" log16.txt; then
  echo -e "\e[32m[OK]\e[0m Rovar lassan mozog rendben."
  exit 0
else
  echo -e "\e[31m[ERROR]\e[0m Hiba: rovar sebessege nem stimmel!"
  exit 1
fi