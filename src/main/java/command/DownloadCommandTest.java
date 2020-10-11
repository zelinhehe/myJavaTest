package command;

/*
 * https://mp.weixin.qq.com/s?__biz=MzU0NjYxNDI2Mw==&mid=2247483656&idx=1&sn=c08f4d7f62b0dc048e13a6a4c26d2873&chksm=fb5bbf90cc2c368643a3d64aca795e226a10fd41e69d00d361deb651f811b83c52ab1862a8a6&mpshare=1&scene=1&srcid=1113Hxk5aGwt46UTFKBZY2C8#rd
 * 运用 命令模式 + 简单工厂模式
 * 需求：客户端通过 http下载数据。每下载一种业务相关的数据，都要增加一个对外的接口，导致接口增加频繁，客户端也要随着修改。
 * 所以，问题是 下载接口与业务数据耦合。
 * 解决方法：使用命令模式，将请求封装为一个对象，从而可用不同的请求对客户进行参数化。
 */

import java.util.HashMap;
import java.util.Map;

public class DownloadCommandTest {

    public static void main(String[] args){
        Map<String, String> params = new HashMap<>();
        params.put("userId", "ID001");
        String command = "1";

        DownloadCommand downloadCommand = CommandFactory.getDownloadCommand(Commands.getEnumByKey(command));  // 简单工厂获取命令对象
        Invoker invoker = new Invoker();  // 命令调用者
        invoker.setDownloadCommand(downloadCommand);  // 设置命令
        String result = invoker.actionDownload(params);  // 执行命令
        System.out.println(result);
    }
}

/*
 * 命令调用者
 */
class Invoker{
    private DownloadCommand downloadCommand;

    public void setDownloadCommand(DownloadCommand downloadCommand) {
        this.downloadCommand = downloadCommand;
    }

    public String actionDownload(Map<String, String> params) {
        return downloadCommand.execute(params);
    }
}

/*
 * 抽象命令接口
 */
abstract class DownloadCommand {
    public abstract String execute(Map<String, String> params);
}

/*
 * 继承抽象命令，实现命令对象，写具体业务
 */
class NullDownloadCommand extends DownloadCommand {
    @Override
    public String execute(Map<String, String> params) {
        System.out.println("Operation unsupported");
        return null;
    }
}

class UserInfoDownloadCommand extends DownloadCommand {
    @Override
    public String execute(Map<String, String> params) {
        String userId = params.get("userId");
        return "get UserInfo by UserId=" + userId;
    }
}

class UserGroupDownloadCommand extends DownloadCommand {
    @Override
    public String execute(Map<String, String> params) {
        String userId = params.get("userId");
        return "get UserGroup by UserId=" + userId;
    }
}

/*
 * 命令工厂，用于获取命令对象
 */
class CommandFactory {
    public static DownloadCommand getDownloadCommand(Commands enumByKey) {
        DownloadCommand downloadCommand;
        switch (enumByKey) {
            case download_UserInfo:
                downloadCommand = new UserInfoDownloadCommand();
                break;
            case download_UserGroup:
                downloadCommand = new UserGroupDownloadCommand();
                break;
            default:
                downloadCommand = new NullDownloadCommand();
                break;
        }
        return downloadCommand;
    }
}

/*
 * 命令集合
 */
enum Commands {
    download_Null("0", "空命令"),
    download_UserInfo("1", "用户信息"),
    download_UserGroup("2", "用户组");

    private final String key;
    private final String value;

    Commands(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Commands getEnumByKey(String key) {
        if (null == key){
            return null;
        }
        for (Commands temp : Commands.values()) {
            if (temp.getKey().equals(key)) {
                return temp;
            }
        }
        return download_Null;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static void main(String[] args) {
        Commands enumByKey = Commands.getEnumByKey("1");

        System.out.println(enumByKey);
        System.out.println(enumByKey.getKey());
        System.out.println(enumByKey.getValue());

        switch (enumByKey) {
            case download_UserInfo:
                System.out.println(download_UserInfo.getValue());
                break;
            default:
                System.out.println("input error");
                break;
        }
    }
}
