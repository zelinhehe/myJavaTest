package IoCTest.IoC;

public class ToggleSwitch {
	
	public void Toggle(ISwitchable t) {
		t.TurnOn();
		t.TurnOff();
	}

	public static void main(String[] args) {
		Light light = new BulbLight();
		TV tv = new TV();
		
		ToggleSwitch t = new ToggleSwitch();
		t.Toggle(light);
		t.Toggle(tv);
	}
}
