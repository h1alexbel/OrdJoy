package com.ordjoy.command;

import com.ordjoy.command.impl.*;

public enum FrontCommandType {

    LOGIN(new LoginCommand()),
    REGISTER(new RegisterCommand()),
    LOGOUT(new LogOutCommand()),
    ADD_ADMIN(new AddAdminCommand()),
    ALL_USERS(new ShowAllUsersCommand()),
    ADD_TRACK(new AddTrackCommand()),
    ADD_TRACK_TO_MIX(new AddTrackToMixCommand()),
    ALL_ADMINS(new ShowAllAdminsCommand()),
    EDIT_TRACK(new EditTrackCommand()),
    DELETE_TRACK(new DeleteTrackCommand()),
    ALL_TRACKS(new ShowAllTracksCommand()),
    ADD_ALBUM(new AddAlbumCommand()),
    EDIT_ALBUM(new EditAlbumCommand()),
    DELETE_ALBUM(new DeleteAlbumCommand()),
    ALL_ALBUMS(new ShowAllAlbumsCommand()),
    ADD_MIX(new AddMixCommand()),
    EDIT_MIX(new EditMixCommand()),
    DELETE_MIX(new DeleteMixCommand()),
    ALL_MIXES(new ShowAllMixesCommand()),
    ADD_TRACK_REVIEW(new AddTrackReviewCommand()),
    ALL_TRACK_REVIEWS(new ShowAllTrackReviewsCommand()),
    ADD_MIX_REVIEW(new AddMixReviewCommand()),
    ALL_MIX_REVIEWS(new ShowAllMixReviewsCommand()),
    ADD_ALBUM_REVIEW(new AddAlbumReviewCommand()),
    ALL_ALBUM_REVIEWS(new ShowAllAlbumReviewsCommand()),
    MAKE_ORDER(new MakeOrderCommand()),
    EDIT_DISCOUNT_PERCENTAGE_LEVEL(new EditDiscountPercentageLevelCommand()),
    DO_ORDER(new RunOrderCommand()),
    DECLINE_ORDER(new DeclineOrderCommand()),
    DELETE_ADMIN(new DeleteAdminCommand()),
    DELETE_ORDER(new DeleteOrderCommand()),
    ALL_ORDERS(new ShowAllOrdersCommand()),
    SWITCH_LANG(new SwitchLangCommand()),
    INDEX_PAGE(new GotoIndexPageCommand());

    private FrontCommand frontCommand;

    FrontCommandType(FrontCommand frontCommand) {
        this.frontCommand = frontCommand;
    }

    public FrontCommand getFrontCommand() {
        return frontCommand;
    }
}