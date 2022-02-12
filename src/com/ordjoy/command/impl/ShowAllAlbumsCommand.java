package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dao.filter.AlbumFilter;
import com.ordjoy.dto.AlbumDto;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.AlbumService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;
import static com.ordjoy.util.JspPageConst.SEPARATOR;

public class ShowAllAlbumsCommand implements FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(ShowAllAlbumsCommand.class);
    private final AlbumService albumService = AlbumService.getInstance();
    private static final String ALBUMS_ATTRIBUTE = "albums";
    private static final String OFFSET = "offset";
    private static final int DEFAULT_LIMIT = 20;
    private static final String NO_OF_PAGES = "noOfPages";

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        try {
            int offset = Integer.parseInt(httpServletRequest.getParameter(OFFSET));
            Long records = albumService.getRecords();
            AlbumFilter albumFilter = new AlbumFilter(DEFAULT_LIMIT, offset);
            List<AlbumDto> allAlbums = albumService.findAllAlbums(albumFilter);
            int noOfPages = (int) (records / DEFAULT_LIMIT);
            httpServletRequest.setAttribute(NO_OF_PAGES, noOfPages);
            httpServletRequest.setAttribute(ALBUMS_ATTRIBUTE, allAlbums);
            page = SEPARATOR + JspFormatHelper.getUserPath(ALBUMS_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.warn(LogginUtils.SHOW_ALL_WARN, e);
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
        return frontCommandResult;
    }
}