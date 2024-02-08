package shop.mtcoding.blog._core;

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

    public static int getTotalPageCount(int totalCount) {
        int remainCount = totalCount % Constant.PAGING_COUNT;
        int totalPageCount = totalCount / Constant.PAGING_COUNT;
        if (remainCount > 0) {
            totalPageCount += 1;
        }
        return totalPageCount;
    }
}
