package com.denkiri.zalego.network;

public class EndPoints {
    private static final String ROOT_URL = "https://zalego.denkiri.com/";

    public static final String URL_REGISTER = ROOT_URL +"Api.php?apicall=signup";
    public static final String URL_LOGIN= ROOT_URL + "Api.php?apicall=login";
    public static final String URL_ADD = ROOT_URL+"Api.php?apicall=selectCourse";
    public static final String URL_SELECT = ROOT_URL+"Api.php?apicall=getcourses";
    public static final  String  PROGRAMMING =ROOT_URL+"Api.php?apicall=getprogramming";
    public static final  String  BUSINESS =ROOT_URL+"business.php";
    public static final  String  DATASCIENCE =ROOT_URL+"Api.php?apicall=getpdatascience";
    public static final  String  CYBERSECURITY =ROOT_URL+"Api.php?apicall=getpdatascience";
    public static final  String  QUALITYASSURANCE =ROOT_URL+"qualityassurance.php";
    public static final String URL_SELECT_ID = ROOT_URL+"select_id.php";
}
