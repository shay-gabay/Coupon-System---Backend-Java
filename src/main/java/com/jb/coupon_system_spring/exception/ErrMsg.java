package com.jb.coupon_system_spring.exception;

import lombok.Getter;

@Getter
public enum ErrMsg {

    ADD_COMPANY_ID_EXIST("Cannot add company since company's id already exist"),
    ADD_COMPANY_NAME_EXIST("Cannot add company since company's name already exist"),
    ADD_COMPANY_EMAIL_EXIST("Cannot add company since company's email already exist"),
    UPDATE_COMPANY_ID_NOT_EXIST("Cannot update company since company id not exist"),
    UPDATE_COMPANY_CANNOT_UPDATE_ID("Cannot update company's id"),
    UPDATE_COMPANY_CANNOT_UPDATE_NAME("Cannot update company's name"),
    DELETE_COMPANY_ID_NOT_EXIST("Cannot delete company since company id not exist"),
    GET_COMPANY_ID_NOT_EXIST("Cannot get company since company id not exist"),
    ADD_CUSTOMER_ID_EXIST("Cannot add customer since customer's id already exist"),
    ADD_CUSTOMER_EMAIL_EXIST("Cannot add customer since customer's email already exist"),
    UPDATE_CUSTOMER_ID_NOT_EXIST("Cannot update customer since customer id not exist"),
    UPDATE_CUSTOMER_CANNOT_UPDATE_ID("Cannot update customer's id"),
    UPDATE_CUSTOMER_EMAIL_EXIST("Cannot update customer's email since this email already exist"),
    DELETE_CUSTOMER_ID_NOT_EXIST("Cannot delete customer since customer id not exist"),
    GET_CUSTOMER_ID_NOT_EXIST("Cannot get customer since customer id not exist"),
    ADD_COUPON_TITLE_EXIST("Cannot add coupon since coupon's title already exist"),
    UPDATE_COUPONS_ID_INVALID("Cannot update coupon's id"),
    UPDATE_COUPONS_COMPANY_ID_INVALID("Cannot update coupon's company's id"),
    DELETE_COUPONS_NOT_EXIST("Cannot delete this coupon since it not exist"),
    COMPANY_COUPONS_NOT_EXIST("There's no company's coupons yet"),
    COMPANY_CATEGORY_COUPONS_NOT_EXIST("There's no company's coupons in this category"),
    COMPANY_MAX_PRICE_COUPONS_NOT_EXIST("There's no company's coupons under this price"),
    LOGIN_MANAGER("Invalid email or password"),
    LOGIN_MANAGER_INVALID_EMAIL("Invalid email"),
    LOGIN_MANAGER_INVALID_PASSWORD("Invalid password"),
    LOGIN_MANAGER_INVALID_EMAIL_AND_PASSWORD("Invalid email and password"),
    CUSTOMER_COUPON_PURCHASE_INVALID("You cannot purchase this coupon since you already purchase it before"),
    CUSTOMER_COUPON_PURCHASE_INVALID_AMOUNT_ZERO("You cannot purchase this coupon since his amount is zero"),
    CUSTOMER_COUPON_PURCHASE_INVALID_DATE_EXPIRED("You cannot purchase this coupon since his date expired"),
    CUSTOMER_COUPONS_NOT_EXIST("You don't have any coupons yet"),
    SYSTEM_COUPONS_NOT_EXIST("There's no coupons yet"),
    CUSTOMER_COUPONS_NOT_MATCH("You don't have this coupon"),
    COMPANY_COUPONS_NOT_MATCH("The company doesn't have this coupon"),
    CUSTOMER_COUPONS_BY_CATEGORY_NOT_EXIST("You don't have any coupons under this category"),
    CUSTOMER_COUPONS_BY_MAX_PRICE_NOT_EXIST("You don't have any coupons under this price"),
    CUSTOMER_ID_NOT_EXIST("Customer id not exist"),
    SECURITY_CANNOT_CREATE_ADMIN("Invalid client type for register "),
    SECURITY_EMAIL_ALREADY_EXIST("Cannot register since this email already exist in the system"),
    SECURITY_PASSWORD_ALREADY_EXIST("Cannot register since this password already exist in the system"),
    SECURITY_EMAIL_OR_PASSWORD_INCORRECT("Cannot login since email or password incorrect"),
    COMPANY_NOT_EXIST("company not exist"),
    COUPON_NOT_EXIST("coupon not exist"),
    COUPON_DATE_INVALID("Coupon start/end date is invalid"),
    CUSTOMER_NOT_EXIST("customer not exist"),
    INVALID_COUPON_ID("invalid coupon id"),
    INVALID_PRICE("invalid price ,must be grater than zero"),
    INVALID_AMOUNT("invalid amount, must be grater than zero "),
    INVALID_START_DATE("invalid start date, must be before or equal to end date"),
    TOKEN_NOT_EXISTS("token not exist"),
    TIME_OUT("Your stay on the site has expired . Please Login again ."),
    CLIENT_NOT_ALLOWED("Your client type not allowed to this ability"),

    END("");
    private String message;

    ErrMsg(String message) {
        this.message = message;
    }
}
