package com.doodeec.csasdemo.Model;

import com.doodeec.csasdemo.Helper;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Dusan Doodeec Bartos on 9.11.2014.
 *
 * Account transaction object model
 */
public class Transaction extends JSONParser {

    public static final String TRANSACTIONS_KEY = "transactions";
    private static final String AMOUNT_KEY = "amount";
    private static final String VALUE_KEY = "value";
    private static final String PRECISION_KEY = "precision";
    private static final String CURRENCY_KEY = "currency";
    private static final String TYPE_KEY = "transactionType";
    private static final String DUE_DATE_KEY = "dueDate";
    private static final String VALUATION_DATE_KEY = "valuationDate";
    private static final String SENDER_NAME_KEY = "senderName";
    private static final String SENDER_KEY = "sender";
    private static final String RECEIVER_KEY = "receiver";
    private static final String CONSTANT_SYM_KEY = "constantSymbol";
    private static final String IBAN_KEY = "iban";
    private static final String DESC_KEY = "description";

    private String mType;
    private String mSenderName;
    private Date mDueDate;
    private Date mValuationDate;
    private TransactionAmount mAmount;
    private AccountEntity mSender;
    private AccountEntity mReceiver;

    public Transaction(JSONObject transactionDefinition) {
        mType = getStringForKey(transactionDefinition, TYPE_KEY);
        mSenderName = getStringForKey(transactionDefinition, SENDER_NAME_KEY);
        mDueDate = Helper.parseDateFromString(getStringForKey(transactionDefinition, DUE_DATE_KEY));
        mValuationDate = Helper.parseDateFromString(getStringForKey(transactionDefinition, VALUATION_DATE_KEY));

        mAmount = new TransactionAmount(getObjectForKey(transactionDefinition, AMOUNT_KEY));
        mSender = new AccountEntity(getObjectForKey(transactionDefinition, SENDER_KEY));
        mReceiver = new AccountEntity(getObjectForKey(transactionDefinition, RECEIVER_KEY));
    }

    public String getType() {
        return mType;
    }

    public String getSenderName() {
        return mSenderName;
    }

    public double getAmount() {
        return mAmount.mValue;
    }

    public String getCurrency() {
        return mAmount.mCurrency;
    }

    public String getSenderDescription() {
        return mSender.mDescription;
    }

    public String getSender() {
        return mSender.mIban;
    }

    public String getValuationDate() {
        return String.valueOf(android.text.format.DateFormat.format("dd. MM. yyyy", mValuationDate));
    }

    private class AccountEntity {
        protected String mConstantSymbol;
        protected String mDescription;
        protected String mIban;

        public AccountEntity(JSONObject entityDefinition) {
            if (entityDefinition.has(CONSTANT_SYM_KEY)) {
                mConstantSymbol = getStringForKey(entityDefinition, CONSTANT_SYM_KEY);
            }
            if (entityDefinition.has(DESC_KEY)) {
                mDescription = getStringForKey(entityDefinition, DESC_KEY);
            }
            if (entityDefinition.has(IBAN_KEY)) {
                mIban = getStringForKey(entityDefinition, IBAN_KEY);
            }
        }
    }

    private class TransactionAmount {
        protected double mValue;
        protected int mPrecision;
        protected String mCurrency;

        public TransactionAmount(JSONObject amountDefinition) {
            mValue = getDoubleForKey(amountDefinition, VALUE_KEY);
            mPrecision = getIntForKey(amountDefinition, PRECISION_KEY);
            mCurrency = getStringForKey(amountDefinition, CURRENCY_KEY);
        }
    }
}
