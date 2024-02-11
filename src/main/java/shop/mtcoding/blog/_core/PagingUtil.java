package shop.mtcoding.blog._core;

import java.util.ArrayList;
import java.util.List;

public class PagingUtil {// 페이징

    public static boolean isFisrt(int currentPage) {
        return currentPage == 0 ? true : false;
    }

    public static boolean isLast(int currentPage, int totalCount) {
        int totalPageCount = getTotalPageCount(totalCount);
        if (totalCount != 0)
            return currentPage == totalPageCount - 1 ? true : false;
        return true;
    }
    public static int nextPage(int currentPage){
        return currentPage + 1;
    }
    public static int prevPage(int currentPage){
        return currentPage - 1;
    }

    public static int getTotalPageCount(int totalCount) {
        int remainCount = totalCount % Constant.PAGING_COUNT;
        int totalPageCount = totalCount / Constant.PAGING_COUNT;
        if (remainCount > 0) {
            totalPageCount += 1;
        }
        return totalPageCount;
    }
    public static List<Integer> getPageList(int lastPage){
        List<Integer> pageCount = new ArrayList<>();
        if (lastPage > 0 ) {
            for (int pageNum = 0; pageNum < lastPage; pageNum++) {
                pageCount.add(pageNum);
            }
        }
        return pageCount;
    }
}
