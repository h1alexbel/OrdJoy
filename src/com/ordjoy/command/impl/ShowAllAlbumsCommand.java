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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;
import static com.ordjoy.util.JspPageConst.SEPARATOR;

public class ShowAllAlbumsCommand implements FrontCommand {

    private static final String ALBUMS_ATTRIBUTE = "albums";
    private final AlbumService albumService = AlbumService.getInstance();
    private final AlbumFilter defaultAlbumFilter = new AlbumFilter(20, 0);

    @Override
    public FrontCommandResult process(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        try {
            List<AlbumDto> allAlbums = albumService.findAllAlbums(defaultAlbumFilter);
            httpServletRequest.setAttribute(ALBUMS_ATTRIBUTE, allAlbums);
            page = SEPARATOR + JspFormatHelper.getUserPath(ALBUMS_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.FORWARD);
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
        return frontCommandResult;
    }
}