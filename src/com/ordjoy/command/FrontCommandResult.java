package com.ordjoy.command;

public class FrontCommandResult {

    private String page;
    private NavigationType navigationType;

    public FrontCommandResult() {
    }

    public FrontCommandResult(String page, NavigationType navigationType) {
        this.page = page;
        this.navigationType = navigationType;
    }

    /**
     * Gets page
     * @return page from {@link FrontCommandResult}
     */
    public String getPage() {
        return page;
    }

    /**
     * Gets Navigation Type
     * @return {@link NavigationType} from {@link FrontCommandResult}
     */
    public NavigationType getNavigationType() {
        return navigationType;
    }

    @Override
    public String toString() {
        return "FrontCommandResult{" +
               "page='" + page + '\'' +
               ", navigationType=" + navigationType +
               '}';
    }
}