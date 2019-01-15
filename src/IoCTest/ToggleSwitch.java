package IoCTest;

public class ToggleSwitch {

	private Light light = new BulbLight();
	private TV tv = new TV();
	
	public void LightToggle() {
		light.TurnOn();
		light.TurnOff();
	}
	
	public void TVToggle() {
		tv.TurnOn();
		tv.TurnOff();
	}
	
	public static void main(String[] args) {
		ToggleSwitch t = new ToggleSwitch();
		t.LightToggle();
		t.TVToggle();
	}
}
