package com.doodeec.csasdemo.Model;

import com.doodeec.csasdemo.Helper;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Dusan Doodeec Bartos on 8.11.2014.
 *
 * Bank Account object model
 */
public class BankAccount extends JSONParser {

    private static final String ID_KEY = "accNumber";
    private static final String IBAN_KEY = "iban";
    private static final String NAME_KEY = "name";
    private static final String DESC_KEY = "description";
    private static final String NOTE_KEY = "note";
    private static final String CURRENCY_KEY = "currency";
    private static final String BANK_CODE_KEY = "accBankCode";
    private static final String BALANCE_KEY = "balance";
    private static final String TRANSP_FROM_KEY = "transparencyFrom";
    private static final String TRANSP_TO_KEY = "transparencyTo";
    private static final String PUBLIC_TO_KEY = "publicationTo";
    private static final String ACTUALIZATION_KEY = "actualizationDate";

    private String mId;
    private String mIban;
    private String mName;
    private String mDescription;
    private String mNote;
    private String mCurrency;
    private int mBankCode;
    private double mBalance;
    private Date mTransparentFrom;
    private Date mTransparentTo;
    private Date mPublicationTo;
    private Date mActualizationDate;

    public BankAccount(JSONObject accountDefinition) {
        mId = getStringForKey(accountDefinition, ID_KEY);
        mIban = getStringForKey(accountDefinition, IBAN_KEY);
        mName = getStringForKey(accountDefinition, NAME_KEY);
        mDescription = getStringForKey(accountDefinition, DESC_KEY);

        if (accountDefinition.has(NOTE_KEY)) {
            mNote = getStringForKey(accountDefinition, NOTE_KEY);
        }

        mCurrency = getStringForKey(accountDefinition, CURRENCY_KEY);
        mBankCode = getIntForKey(accountDefinition, BANK_CODE_KEY);
        mBalance = getDoubleForKey(accountDefinition, BALANCE_KEY);

        mTransparentFrom = Helper.parseDateFromString(getStringForKey(accountDefinition, TRANSP_FROM_KEY));
        mTransparentTo = Helper.parseDateFromString(getStringForKey(accountDefinition, TRANSP_TO_KEY));
        mPublicationTo = Helper.parseDateFromString(getStringForKey(accountDefinition, PUBLIC_TO_KEY));
        mActualizationDate = new Date(getIntForKey(accountDefinition, ACTUALIZATION_KEY));
    }

    public String getId() {
        return mId;
    }

    public String getIban() {
        return mIban;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getNote() {
        return mNote;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public int getBankCode() {
        return mBankCode;
    }

    public double getBalance() {
        return mBalance;
    }

    public Date getTransparencyFrom() {
        return mTransparentFrom;
    }

    public Date getTransparencyTo() {
        return mTransparentTo;
    }

    public Date getPublicationTo() {
        return mPublicationTo;
    }

    public Date getActualizationDate() {
        return mActualizationDate;
    }
}
