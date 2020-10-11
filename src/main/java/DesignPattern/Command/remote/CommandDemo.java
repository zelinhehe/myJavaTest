package DesignPattern.Command.remote;

// ---- 接收者：不同的接收者做对应的具体动作 ----
class Light {
    public void on() { System.out.println("Light on");}
    public void off() { System.out.println("Light off");}
}
class Door {
    public void on() { System.out.println("Door on");}
    public void off() { System.out.println("Door off");}
}

// ---- 命令对象：包含了接收者和动作 ----
interface Command {
    void execute();
    void undo();
}
class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.on(); }
    public void undo() { light.off(); }
}
class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) { this.light = light; }
    public void execute() { light.off(); }
    public void undo() { light.on(); }
}
class DoorOnCommand implements Command {
    private Door door;

    public DoorOnCommand(Door door) { this.door = door; }
    public void execute() { door.on(); }
    public void undo() { door.off(); }
}
class DoorOffCommand implements Command {
    private Door door;

    public DoorOffCommand(Door door) { this.door = door; }
    public void execute() { door.off(); }
    public void undo() { door.on(); }
}
class NoCommand implements Command {
    public void execute() { }
    public void undo() { }
}
// ---- 调用者：参数是命令对象，调用其中的execute ----
class Invoker {
    private Command[] onCommands;  // 打开的命令组
    private Command[] offCommands;  // 关闭的命令组
    private Command undoCommand;  // 最近一次执行的命令对象，用于撤销最近一次命令执行

    public Invoker() {
        Command noCommand = new NoCommand();
        undoCommand = noCommand;
        onCommands = new Command[3];  // 数组，初始放入3个空命令
        offCommands = new Command[3];  // 数组，初始放入3个空命令
        for (int i = 0; i < onCommands.length; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }

    public void setCommand(int num, Command onCommand, Command offCommand) {
        onCommands[num] = onCommand;
        offCommands[num] = offCommand;
    }

    public void onButtonWasPressed(int num) {
        onCommands[num].execute();
        undoCommand = onCommands[num];
    }
    public void offButtonWasPressed(int num) {
        offCommands[num].execute();
        undoCommand = offCommands[num];
    }
    public void undo() {
        undoCommand.undo();
    }
}

public class CommandDemo {
    public static void main(String[] args) {
        Invoker invoker = new Invoker();  // 命令对象的调用者

        Light light = new Light();
        Door door = new Door();
        Command lightOnCommand = new LightOnCommand(light);  // 命令对象
        Command lightOffCommand = new LightOffCommand(light);
        Command doorOnCommand = new DoorOnCommand(door);
        Command doorOffCommand = new DoorOffCommand(door);

        invoker.setCommand(0, lightOnCommand, lightOffCommand);  // 设置按钮编号对应的命令对象
        invoker.setCommand(1, doorOnCommand, doorOffCommand);

        invoker.onButtonWasPressed(0);  // 通过按钮编号发送请求
        invoker.offButtonWasPressed(0);
        invoker.undo();  // 撤销最近一次的操作
        invoker.onButtonWasPressed(1);
        invoker.offButtonWasPressed(1);
    }
}
