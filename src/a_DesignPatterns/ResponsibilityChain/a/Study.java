package a_DesignPatterns.ResponsibilityChain.a;

public class Study {

    public void study(PreparationList preparationList) {
        if (preparationList.isWashFace()) {
            System.out.println("洗脸");
        }
        if (preparationList.isWashHair()) {
            System.out.println("洗头");
        }
        if (preparationList.isHaveBreakfast()) {
            System.out.println("吃早餐");
        }

        System.out.println("我可以去上学了！");
    }

    /**
     * 这个例子实现了我们的需求，但是不够优雅
     * 主流程是学习，但是把要准备做的事情这些动作耦合在学习中，这样有两个问题：
     *      PreparationList中增加一件事情的时候，比如增加化妆、打扫房间，必须修改study方法进行适配
     *      当这些事情的顺序需要发生变化的时候，必须修改study方法，比如先洗头再洗脸，那么7~9行的代码必须和4~6行的代码互换位置
     * 最糟糕的写法，只是为了满足功能罢了，违背开闭原则，即当我们扩展功能的时候需要去修改主流程，无法做到对修改关闭、对扩展开放。
     */
    public static void main(String[] args) {
        PreparationList preparationList = new PreparationList();
        preparationList.setWashFace(true);
        preparationList.setWashHair(true);
        preparationList.setHaveBreakfast(true);
        Study study = new Study();
        study.study(preparationList);
    }
}
