package access;

import access.family.*;
import access.school.*;

public class AccessTest {
	public static void main(String[] args) {
		Father father = new Father();
		Mather mather = new Mather();
		Son son = new Son();
		Teacher teacher = new Teacher();
		
		father.makeMoney();
		mather.shop();
		son.play();
		teacher.notifyTo();
	}
}
