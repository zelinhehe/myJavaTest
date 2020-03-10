package DesignPattern.ResponsibilityChain.b;

public class Test {

    /**
     * 完成了学习与准备工作之间的解耦，即核心的事情我们是要学习，此时无论加多少准备工作，都不需要修改study方法，只需要修改调用方即可。
     * 这种写法虽然符合开闭原则，但是两个明显的缺点对客户端并不友好：
     *      增加、减少责任链对象，需要修改客户端代码，即比如我这边想要增加一个打扫屋子的操作，那么main()方法需要改动
     *      AbstractPrepareFilter washFaceFilter = new WashFaceFilter(haveBreakfastFilter)
     *      这种调用方式不够优雅，客户端需要思考一下，到底真正调用的时候调用三个Filter中的哪个Filter
     */
    public static void main(String[] args) {
        PreparationList preparationList = new PreparationList();
        preparationList.setWashFace(true);
        preparationList.setWashHair(false);
        preparationList.setHaveBreakfast(true);

        Study study = new Study();

        AbstractPrepareFilter haveBreakfastFilter = new HaveBreakfastFilter(null);
        AbstractPrepareFilter washFaceFilter = new WashFaceFilter(haveBreakfastFilter);
        AbstractPrepareFilter washHairFilter = new WashHairFilter(washFaceFilter);

        washHairFilter.doFilter(preparationList, study);
    }
}
