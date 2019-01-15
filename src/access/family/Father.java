package access.family;

public class Father {
	public void makeMoney() {
		FamilyQQGroup.sendGroupMsg("Father: 赚了1000万。");
	}
	
	public static void receiveMsg(String msg) {
		System.out.println("Father receive: " + msg);
	}
}
