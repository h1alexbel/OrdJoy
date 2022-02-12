package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.AlbumService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static com.ordjoy.util.JspPageConst.*;
import static com.ordjoy.util.JspPageConst.REFERER_HEADER;

public class DeleteAlbumCommand implements FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(DeleteAlbumCommand.class);
    private final AlbumService albumService = AlbumService.getInstance();
    private static final String ALBUM_ID = "albumId";

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        Long id = Long.valueOf(httpServletRequest.getParameter(ALBUM_ID));
        try {
            boolean isDeleted = albumService.deleteAlbumById(id);
            if (isDeleted) {
                page = httpServletRequest.getHeader(REFERER_HEADER);
            } else {
                page = httpServletRequest.getContextPath() + JspFormatHelper.getAdminPath(ADMIN_MAIN_PAGE);
            }
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
            return frontCommandResult;
        } catch (ServiceException e) {
            LOGGER.warn(LogginUtils.DELETE_WARN, e);
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
    }
}