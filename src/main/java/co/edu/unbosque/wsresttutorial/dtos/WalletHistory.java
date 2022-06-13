package co.edu.unbosque.dtos;

import java.util.Date;

public class WalletHistory {

    private int wallet_id;
    private String username;
    private String walletType;
    private long fcoins;
    private String art;
    private Date registeredAt;

    public WalletHistory() {}

    public WalletHistory(int wallet_id, String username, String walletType, long fcoins, String art, Date registeredAt) {
        this.wallet_id = wallet_id;
        this.username = username;
        this.walletType = walletType;
        this.fcoins = fcoins;
        this.art = art;
        this.registeredAt = registeredAt;
    }

    public int getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(int wallet_id) {
        this.wallet_id = wallet_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public long getFcoins() {
        return fcoins;
    }

    public void setFcoins(long fcoins) {
        this.fcoins = fcoins;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }
}
