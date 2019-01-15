package IoCTest.IoC;

public class TV implements ISwitchable {
	public void TurnOn() {
		System.out.println("TV TurnOn.");
	}
	
	public void TurnOff() {
		System.out.println("TV TurnOff.");
	}
}
