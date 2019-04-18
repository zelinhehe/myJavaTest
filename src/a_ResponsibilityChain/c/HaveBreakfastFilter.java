package a_ResponsibilityChain.c;

public class HaveBreakfastFilter implements StudyPrepareFilter {
    @Override
    public void doFilter(PreparationList preparationList, FilterChain filterChain) {
        if (preparationList.isHaveBreakfast()) {
            System.out.println("吃完早餐");
        }

        filterChain.doFilter(preparationList, filterChain);
    }
}
