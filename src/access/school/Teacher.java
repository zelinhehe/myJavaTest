package access.school;

import access.family.Father;
//import access.family.FamilyQQGroup;

public class Teacher {
	public void notifyTo() {
//		FamilyQQGroup.sendGroupMsg("Teacher: 大家好，我是孩子的老师");
		Father.receiveMsg("Teacher: 你好，我是孩子的老师");
	}
}
