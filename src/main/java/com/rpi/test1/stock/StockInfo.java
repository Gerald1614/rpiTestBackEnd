package com.rpi.test1.stock;


public class StockInfo {

	public StockInfo(String status, String from, String symbol, double open, double high, double low, double close,
			int volume, double afterHours, double preMarket) {
		super();
		this.status = status;
		this.from = from;
		this.symbol = symbol;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.afterHours = afterHours;
		this.preMarket = preMarket;
	}



	public String status;
    public String from;
    public String symbol;
    public double open;
    public double high;
    public double low;
    public double close;
    public int volume;
    public double afterHours;
    public double preMarket;
    


	@Override
	public String toString() {
		return "StockInfo [status=" + status + ", from=" + from + ", symbol=" + symbol + ", open=" + open + ", high="
				+ high + ", low=" + low + ", close=" + close + ", volume=" + volume + ", afterHours=" + afterHours
				+ ", preMarket=" + preMarket + "]";
	}


}
