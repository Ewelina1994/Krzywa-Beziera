
public class Punkt {
	
	private double x;
	private double y;
	private double z;
	public Punkt(double x, double y)
	{
		this.x = x;
		this.y = y;
		z=1;
	}
	public double dajX() {
		return x;
	}
	public void ustawX(double x) {
		this.x = x;
	}
	public double dajY() {
		return y;
	}
	public void ustawY(double y) {
		this.y = y;
	}	
	public String toString()
	{
		return ("x: "+x + " y: "+y);
	}
	
}
