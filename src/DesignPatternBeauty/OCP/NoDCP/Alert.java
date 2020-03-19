package DesignPatternBeauty.OCP.NoDCP;

import DesignPatternBeauty.OCP.AlertRule;
import DesignPatternBeauty.OCP.Notification;
import DesignPatternBeauty.OCP.NotificationEmergencyLevel;

public class Alert {

    private AlertRule rule;
    private Notification notification;

    public Alert(AlertRule rule, Notification notification) {
        this.rule = rule;
        this.notification = notification;
    }

    public void check(String api, long requestCount, long errorCount, long durationOfSeconds) {
        long tps = requestCount / durationOfSeconds;
        // 当tps 大于 预警规则设定的最大值时，触发告警
        if (tps > rule.getMatchedRule(api).getMaxTps()) {
            notification.notify(NotificationEmergencyLevel.URGENCY, "...");
        }
        // 当error数 大于 预警规则设定的最大值时，触发告警
        if (errorCount > rule.getMatchedRule(api).getMaxErrorCount()) {
            notification.notify(NotificationEmergencyLevel.SEVERE, "...");
        }
    }

    /**
     * 添加功能：
     *      当每秒钟接口超时请求个数，超过某个预先设置的最大阈值时，也要触发告警发送通知。
     * 改动代码
     *      一：修改 check() 函数的入参，添加一个新的统计数据 timeoutCount，表示超时接口请求数；
     *      二：在 check() 函数中添加新的告警逻辑。
     * 这样的代码修改存在的问题：
     *      一：我们对接口进行了修改，这就意味着调用这个接口的代码都要做相应的修改。
     *      二：修改了 check() 函数，相应的单元测试都需要修改。
     */
    // 改动一：添加参数timeoutCount
    public void check(String api, long requestCount, long errorCount, long timeoutCount, long durationOfSeconds) {
        long tps = requestCount / durationOfSeconds;
        if (tps > rule.getMatchedRule(api).getMaxTps()) {
            notification.notify(NotificationEmergencyLevel.URGENCY, "...");
        }
        if (errorCount > rule.getMatchedRule(api).getMaxErrorCount()) {
            notification.notify(NotificationEmergencyLevel.SEVERE, "...");
        }
        // 改动二：添加接口超时处理逻辑
        long timeoutTps = timeoutCount / durationOfSeconds;
        if (timeoutTps > rule.getMatchedRule(api).getMaxTimeoutTps()) {
            notification.notify(NotificationEmergencyLevel.URGENCY, "...");
        }
    }
}


