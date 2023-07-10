package com.goit.org.dto;

import com.goit.org.banks.Privat;
import com.goit.org.functionalInteface.BanksUtil;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

public class PrivatService extends BankService implements BanksUtil, Serializable {
    private static final String URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";

    @Override
    public String getCurrency(String command, DecimalFormat df) {
        try {
            String response = Jsoup.connect(URL).ignoreContentType(true).get().body().text();
            List<Privat> responseDtos = convertResponseToList(response, Privat.class);
            return responseDtos
                    .stream()
                    .filter(dto -> dto.getCcy().equals(Currency.getInstance(command)))
                    .map(dto -> "ПРИВАТБАНК курси обміну для " + Currency.getInstance(command) + "\n"
                            + "Купівля = " + df.format(dto.getBuy()) + " UAH, "
                            + "Продаж = " + df.format(dto.getSale()) + " UAH")
                    .collect(Collectors.joining(""));
        } catch (IOException e) {
            return "Проблеми на сервері";
        } catch (IllegalArgumentException e) {
            return "такої валюти немає";
        }
    }
}
