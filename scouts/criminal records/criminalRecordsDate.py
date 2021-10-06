#!/usr/bin/python
import requests
import json
import os
import mechanize
import re
import datetime
import http.cookiejar as cookielib
import calendar
from bs4 import BeautifulSoup

LOGIN_URL = "https://eaep.escoteiros.pt"
URL = "https://eaep.escoteiros.pt/divisao/Chefia/escoteiros"
browser = mechanize.Browser()
cookieJar = cookielib.CookieJar()
browser.set_cookiejar(cookieJar)

startDate = "{}-{}-{}".format(datetime.date.today().year, "09", "01")

page = requests.get(URL)
creds = open("creds.json")
creds = json.load(creds)
user = creds["username"]
password = creds["password"]

def days_between(d1, d2):
    d1 = datetime.datetime.strptime(d1, "%Y-%m-%d")
    d2 = datetime.datetime.strptime(d2, "%Y-%m-%d")
    return abs((d1 - d2).days)


def CriminalRecordIsValid(result):
    if re.search("Sem", result):
        return {
            "valid": False,
            "message": "Não tem registo criminal associado"
        }

    x = re.search("([0-9]{4})-([0-9]{2})-([0-9]{2})$", result)

    if (not x):
        return {
            "valid": False,
            "message": "Registo criminal inválido"
        }
    else:
        if x.group(0) >= startDate:
            return {
                "valid": True,
                "message": "Registo criminal válido"
            }
        else:
            return {
                "valid": False,
                "message": "Registo criminal anterior à data mínima de emissão"
            }

browser.open(LOGIN_URL)
browser.select_form(nr=0)
browser.form['user'] = user
browser.form['password'] = password
browser.submit()
criminalRecordStates = dict()

browser.open(URL)

adultsPage = BeautifulSoup(browser.response().read(), "html.parser")
adults = adultsPage.select("tbody tr td a")


for adult in adults:
    browser.open(adult['href'])
    adultPage = BeautifulSoup(browser.response().read(), "html.parser")
    result = adultPage.select("#page-wrapper > div.row:nth-last-child(3) pre:last-child")[0].text
    criminalRecordStates[adult.text] = CriminalRecordIsValid(result)  

with open("results.json", "w") as outfile:
    outfile.write(json.dumps(criminalRecordStates, indent=True, ensure_ascii=False))
    outfile.close()
