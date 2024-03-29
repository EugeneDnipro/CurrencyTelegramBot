package com.goit.org.dto;

import com.goit.org.banks.NBU;
import com.goit.org.functionalInteface.BanksUtil;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

public class NBUService extends BankService implements BanksUtil, Serializable {
    private static final String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    @Override
    public String getCurrency(String command, DecimalFormat df) {
        try {
            String response = Jsoup.connect(URL).ignoreContentType(true).get().body().text();
            List<NBU> responseDtos = convertResponseToList(response, NBU.class);
            return responseDtos
                    .stream()
                    .filter(dto -> dto.getCc().equals(Currency.getInstance(command)))
                    .map(dto -> "НБУ курс обміну для " + Currency.getInstance(command) + "\n"
                            + "Офіційний курс = " + df.format(dto.getRate()) + " UAH")
                    .collect(Collectors.joining());
        } catch (IOException e) {
            return "Проблеми на сервері";
        } catch (IllegalArgumentException e) {
            return "такої валюти немає";
        }
    }
}
