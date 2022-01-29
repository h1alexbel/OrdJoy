package com.ordjoy.command;

import com.ordjoy.command.impl.*;

public enum FrontCommandType {

    LOGIN(new LoginCommand()),
    REGISTER(new RegisterCommand()),
    LOGOUT(new LogOutCommand()),
    ADD_ADMIN(new AddAdminCommand()),
    UPDATE_USER(new EditUserInfoCommand()),
    ALL_USERS(new ShowAllUsersCommand()),
    ADD_TRACK(new AddTrackCommand()),
    EDIT_TRACK(new EditTrackCommand()),
    DELETE_TRACK(new DeleteTrackCommand()),
    ALL_TRACKS(new AllTracksCommand()),
    ADD_ALBUM(new AddAlbumCommand()),
    EDIT_ALBUM(new EditAlbumCommand()),
    DELETE_ALBUM(new DeleteAlbumCommand()),
    ALL_ALBUMS(new ShowAllAlbumsCommand()),
    ADD_MIX(new AddMixCommand()),
    EDIT_MIX(new EditMixCommand()),
    DELETE_MIX(new DeleteMixCommand()),
    ALL_MIXES(new AllMixesCommand()),
    ADD_TRACK_REVIEW(new AddTrackReviewCommand()),
    EDIT_TRACK_REVIEW(new EditTrackReviewCommand()),
    DELETE_TRACK_REVIEW(new DeleteTrackReviewCommand()),
    ALL_TRACK_REVIEWS(new AllTrackReviewsCommand()),
    ADD_MIX_REVIEW(new AddMixReviewCommand()),
    EDIT_MIX_REVIEW(new EditMixReviewCommand()),
    DELETE_MIX_REVIEW(new DeleteMixReviewCommand()),
    ALL_MIX_REVIEWS(new AllMixReviewsCommand()),
    ADD_ALBUM_REVIEW(new AddAlbumReviewCommand()),
    EDIT_ALBUM_REVIEW(new EditAlbumReviewCommand()),
    DELETE_ALBUM_REVIEW(new DeleteAlbumReviewCommand()),
    ALL_ALBUM_REVIEWS(new AllAlbumReviewsCommand()),
    MAKE_ORDER(new MakeOrderCommand()),
    EDIT_ORDER(new EditOrderCommand()),
    EDIT_ORDER_STATUS(new EditOrderStatusCommand()),
    DELETE_ORDER(new DeleteOrderCommand()),
    ALL_ORDERS(new AllOrdersCommand());

    private FrontCommand frontCommand;

    FrontCommandType(FrontCommand frontCommand) {
        this.frontCommand = frontCommand;
    }

    public FrontCommand getFrontCommand() {
        return frontCommand;
    }
}