package a_ResponsibilityChain.c;

public class Test {

    /**
     * 完美解决第一版责任链模式存在的问题，增加、修改责任对象客户端调用代码都不需要再改动。
     *      问题：增加、减少责任对象，main()方法，不是还得addFilter，或者删除一行吗？
     *      答：回想一下，Servlet增加或减少Filter需要改动什么代码吗？不用，我们需要改动的只是web.xml而已。
     *         同样的道理，FilterChain里面有studyPrepareFilterList，我们完全可以把FilterChain做成一个Spring Bean，
     *         所有的Filter具体实现类也都是Spring Bean，注入studyPrepareFilterList就好了，伪代码为：
     * <bean id="filterChain" class="xxx.xxx.xxx.FilterChain">
     *     <property name="studyPrepareFilterList">
     *         <list>
     *             <ref bean="washFaceFilter" />
     *             <ref bean="washHairFilter" />
     *             <ref bean="haveBreakfastFilter" />
     *         </list>
     *     </property>
     * </bean>
     */
    public static void main(String[] args) {
        PreparationList preparationList = new PreparationList();
        preparationList.setWashFace(true);
        preparationList.setWashHair(false);
        preparationList.setHaveBreakfast(true);

        Study study = new Study();

        StudyPrepareFilter washFaceFilter = new WashFaceFilter();
        StudyPrepareFilter washHairFilter = new WashHairFilter();
        StudyPrepareFilter haveBreakfastFilter = new HaveBreakfastFilter();

        FilterChain filterChain = new FilterChain(study);
        filterChain.addFilter(washFaceFilter);
        filterChain.addFilter(washHairFilter);
        filterChain.addFilter(haveBreakfastFilter);

        filterChain.doFilter(preparationList, filterChain);
    }
}
