package IoCTest.IoC;

/**
 * https://coolshell.cn/articles/9949.html
 */

public class ToggleSwitch {
	
	public void Toggle(ISwitchable t) {  // for any object implemented ISwitchable
		t.TurnOn();
		t.TurnOff();
	}

	public static void main(String[] args) {
		ToggleSwitch t = new ToggleSwitch();

		Light aLight = new ALight();
		Light bLight = new BLight();
		TV tv = new TV();

		t.Toggle(aLight);
		t.Toggle(bLight);
		t.Toggle(tv);
	}
}

interface ISwitchable {
	void TurnOn();
	void TurnOff();
}

abstract class Light implements ISwitchable{
}

class ALight extends Light {
	public void TurnOn() { System.out.println("A Light On."); }
	public void TurnOff() { System.out.println("A Light Off."); }
}

class BLight extends Light {
	public void TurnOn() { System.out.println("B Light On."); }
	public void TurnOff() { System.out.println("B Light Off."); }
}

class TV implements ISwitchable {
	public void TurnOn() { System.out.println("TV TurnOn."); }
	public void TurnOff() { System.out.println("TV TurnOff."); }
}
