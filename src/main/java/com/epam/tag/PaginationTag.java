package com.epam.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.Writer;

public class PaginationTag extends SimpleTagSupport {

    private int numberOfPages;
    private static final int LINKS_PER_PAGE = 10;
    private static final int ITEMS_PER_PAGE = 5;
    private static final int SHIFT = 5;
    private static final String LINK_CLASS = "pagination-link";
    private int currentPage;
    private int totalItems;
    private String uri;

    @Override
    public void doTag() throws JspException, IOException {
        calculateNumberOfPages();
        if (numberOfPages <= 1) {
            return;
        }
        int startPage = 10 * (currentPage / LINKS_PER_PAGE) + 1;
        int endPage = Math.max(currentPage + SHIFT, 10 * (1 + currentPage / LINKS_PER_PAGE));
        if (endPage > numberOfPages) {
            endPage = numberOfPages;
        }
        buildPagination(startPage,endPage);
    }

    private void calculateNumberOfPages() {
        numberOfPages = (totalItems / ITEMS_PER_PAGE);
        if (totalItems % ITEMS_PER_PAGE > 0) {
            numberOfPages++;
        }
    }

    private void buildPagination(int startPage, int endPage) {
        Writer out = getJspContext().getOut();
        try {
            if (currentPage > 1) {
                out.write(buildPaginationItem(currentPage - 1, "Previous"));
            }
            for (int i = startPage; i <= endPage; i++) {
                out.write(buildPaginationItem(i, String.valueOf(i)));
            }
            if (currentPage < numberOfPages) {
                out.write(buildPaginationItem(currentPage + 1, "Next"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildPaginationItem(int page, String linkName) {
        String item = new String("<a class=\"%s\" href=\"%s&pageIndex=%s\">%s</a>");
        return String.format(item, LINK_CLASS, uri, page, linkName);
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
