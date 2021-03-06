package be.dijlezonen.dzwelg.models;

import com.google.firebase.database.Exclude;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;

/**
 * Stelt een transactie voor, m.a.w. een aan- of verkoop of undo (het terugdraaien van één der vorigen)
 *
 */
public abstract class Transactie {



    public enum TransactieSoort {
        CREDIT, DEBIT, UNDO
    }

    private String userId;
    private String eventId;
    private String kassaverantwoordelijkeId;
    private long timestamp;
    private TransactieSoort soort;   //omdat het onmogelijk is vanuit Firebase db te weten welke subklasse het is

    @SuppressWarnings("WeakerAccess")
    public Transactie() {
        // lege constructor voor Firebase
    }

    protected Transactie(TransactieSoort soort, String userId, String eventId, String kassaverantwoordelijkeId) {
        this.soort = soort;
        this.userId = userId;
        this.eventId = eventId;
        this.kassaverantwoordelijkeId = kassaverantwoordelijkeId;
        this.timestamp = -1 * new Date().getTime(); // negatieve timestamp om antichronologisch te kunnen ordenen
    }

    public abstract String toString();


    public long getTimestamp() {
        return timestamp;
    }

    @Exclude
    public long getTimestampForKey() {
        return -1 * timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getEventId() {
        return eventId;
    }

    public TransactieSoort getSoort() {
        return soort;
    }

    public String getKassaverantwoordelijkeId() {
        return kassaverantwoordelijkeId;
    }

    public void setKassaverantwoordelijkeId(String kassaverantwoordelijkeId) {
        this.kassaverantwoordelijkeId = kassaverantwoordelijkeId;
    }

    protected String formatteerSaldo(double saldo)
    {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(Currency.getInstance("EUR"));
        return format.format(saldo);
    }
}
