package IoCTest;

public class ToggleSwitch {
	
	public void LightToggle(Light light) {  // for light
		light.TurnOn();
		light.TurnOff();
	}
	
	public void TVToggle(TV tv) {  // for tv
		tv.TurnOn();
		tv.TurnOff();
	}
	
	public static void main(String[] args) {
		ToggleSwitch t = new ToggleSwitch();

		Light aLight = new ALight();
		Light bLight = new BLight();
		TV tv = new TV();

		t.LightToggle(aLight);
		t.LightToggle(bLight);
		t.TVToggle(tv);
	}
}

abstract class Light {
	public abstract void TurnOn();
	public abstract void TurnOff();
}

class ALight extends Light{
	public void TurnOn() { System.out.println("A Light On."); }
	public void TurnOff() { System.out.println("A Light Off."); }
}

class BLight extends Light{
	public void TurnOn() { System.out.println("B Light On."); }
	public void TurnOff() { System.out.println("B Light Off."); }
}

class TV {
	public void TurnOn() { System.out.println("TV On."); }
	public void TurnOff() { System.out.println("TV Off."); }
}
