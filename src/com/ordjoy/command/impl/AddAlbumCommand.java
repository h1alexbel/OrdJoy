package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dto.AlbumDto;
import com.ordjoy.entity.Album;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.AlbumService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.util.LogginUtils;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.impl.AlbumValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static com.ordjoy.util.JspPageConst.*;

public class AddAlbumCommand implements FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(AddAlbumCommand.class);
    private final AlbumService albumService = AlbumService.getInstance();
    private final AlbumValidator albumValidator = AlbumValidator.getInstance();
    private static final String ALBUM_TITLE = "albumTitle";
    private static final String SESSION_ALBUM = "album";

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        String albumTitle = httpServletRequest.getParameter(ALBUM_TITLE);
        Album album = albumService.buildAlbum(albumTitle);
        ValidationResult validationResult = albumValidator.isValid(album);
        try {
            if (validationResult.isValid() && !albumService.isAlbumExists(albumTitle)) {
                AlbumDto albumDto = albumService.saveAlbum(album);
                page = httpServletRequest.getContextPath() + JspFormatHelper.getAdminPath(ADMIN_MAIN_PAGE);
                httpServletRequest.getSession().setAttribute(SESSION_ALBUM, albumDto);
            } else {
                page = httpServletRequest.getContextPath() + JspFormatHelper.getPublicPath(ERROR_PAGE);
            }
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
        } catch (ServiceException e) {
            LOGGER.warn(LogginUtils.ADD_WARN, e);
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
        return frontCommandResult;
    }
}