package a_ResponsibilityChain.c;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements StudyPrepareFilter {
    private int pos = 0;
    private Study study;

    private List<StudyPrepareFilter> studyPrepareFilterList;

    public FilterChain(Study study) {
        this.study = study;
    }

    public void addFilter(StudyPrepareFilter studyPrepareFilter) {
        if (studyPrepareFilterList == null) {
            studyPrepareFilterList = new ArrayList<>();
        }

        studyPrepareFilterList.add(studyPrepareFilter);
    }

    @Override
    public void doFilter(PreparationList preparationList, FilterChain filterChain) {
        // 所有过滤器执行完毕
        if (pos == studyPrepareFilterList.size()) {
            study.study();
        } else {
            studyPrepareFilterList.get(pos++).doFilter(preparationList, filterChain);
        }
    }
}
