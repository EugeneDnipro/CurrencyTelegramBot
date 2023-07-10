package com.goit.Utils;

import com.goit.org.dto.PrivatService;
import com.goit.org.functionalInteface.BanksUtil;
import lombok.Data;

import java.io.Serializable;
import java.text.DecimalFormat;

@Data
public class UserPreferences implements Serializable {
    private BanksUtil bank;
    private DecimalFormat df;
    private boolean isTwo;
    private boolean isThree;
    private boolean isFour;
    private boolean isPrivate;
    private boolean isMono;
    private boolean isNBU;
    private boolean isUsd;
    private boolean isEur;
    private boolean isOne;
    private String currency;
    private String time;

    public UserPreferences() {
        bank = new PrivatService();
        df = new DecimalFormat("#0.00");
        isUsd = true;
        isEur = false;
        isPrivate = true;
        isMono = false;
        isNBU = false;
        isOne = true;
        isTwo = true;
        isThree = false;
        isFour = false;
        currency = "USD";
        time = "вимкнено";
    }
}
