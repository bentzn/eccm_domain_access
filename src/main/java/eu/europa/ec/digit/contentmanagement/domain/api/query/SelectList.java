package eu.europa.ec.digit.contentmanagement.domain.api.query;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author bentsth
 */
public class SelectList extends AbstractQueryElement {

    private List<SelectSubList> selectSubLists;


    public SelectList(SelectSubList selectSubList) {
        checkIsNotNull("selectSubList", selectSubList);
        selectSubLists = new LinkedList<>();
        selectSubLists.add(selectSubList);
    }


    public SelectList(List<SelectSubList> selectSubLists) {
        checkIsNotNullOrEmpty("selectSubLists", selectSubLists);
        this.selectSubLists = selectSubLists;
    }


    public List<SelectSubList> getSelectSubLists() {
        return selectSubLists;
    }


    @Override
    public String toString() {
        return "SelectList [selectSubLists=" + selectSubLists + "]";
    }

    
    
}
