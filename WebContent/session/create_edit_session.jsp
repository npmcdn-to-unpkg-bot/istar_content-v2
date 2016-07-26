<%-- 
    Document   : create_edit_event
    Created on : 25 Mar, 2016, 4:38:50 PM
    Author     : Kunal Chakravertti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title></title>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">



        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>


        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>


        <script>
            $(function () {
                $("#datepicker").datepicker({dateFormat: 'dd/mm/yy'});
            });</script>
        <script type="text/javascript">
            function checkDate(val) {
                var flag = true;
//                var todayDate = new Date();
//                //need to add one to get current month as it is start with 0
//                var todayMonth = todayDate.getMonth() + 1;
//                var todayDay = todayDate.getDate();
//                var todayYear = todayDate.getFullYear();
//                var todayDateText = todayDay + "/" + todayMonth + "/" + todayYear;
//                var inputDateText = document.getElementById('datepicker').value;
//                var inputToDate = Date.parse(inputDateText);
//
//                var todayToDate = Date.parse(todayDateText);
//                if (inputToDate > todayToDate) {
//                    flag = true;
//
//                } else {
//                    alert("the input is earlier than today");
//                }
////                else if (inputToDate < todayToDate) {
////                    
////                }
////                else {
////                    alert("the input is same as today");
////                }
                if (flag) {
                    if (val == '1') {
                        document.getElementById("myform").action = "session_controller?_action=save";
                    } else {
                        document.getElementById("myform").action = "session_controller?_action=update";
                    }
                    document.getElementById("myform").method = "post";
                    document.getElementById("myform").submit();
                }
            }
        </script>

    </head>
    <body>
        <c:if test="${action == 'create'}">
            <form id="myform">
                <h1>Create Session</h1>
                <p>Session Type : <input type="text"  name="session_type" /> </p>
                <p>Class Content : <input type="text"  name="class_content" /> </p>
                <p>Date: <input type="text" id="datepicker" name="session_date" />(dd/MM/yyyy) &nbsp;&nbsp;&nbsp; Hour : ${hourdrop} &nbsp;&nbsp;&nbsp; Minute : &nbsp;&nbsp;&nbsp; ${mindrop} </p>
                <p>Review Monthly?: <input type="checkbox"  name="review_monthly" /> </p>
                <p>Review Weekly?: <input type="checkbox"  name="review_weekly" /> </p>
                <p>How many Weeks/Month? : <input type="text"  name="no_reviews" /> </p>
                <p><input type="button" value="Save" onclick="checkDate('1');"/></p>
            </form>                  
        </c:if>
        <c:if test="${action == 'edit'}">
            <form id ="myform">
                <h1>Edit Session</h1>
                <p>Session Type : <input type="text"  name="session_type" value="${sessionobject.sessionType}"/> </p>
                <p>Class Content : <input type="text"  name="class_content" value="${sessionobject.classContent}"/> </p>
                <p>Date: <input type="text" id="datepicker"  name="session_date"  value="<fmt:formatDate pattern='dd/MM/yyyy' value='${sessionobject.sessionDate}'  />" /> (dd/MM/yyyy) &nbsp;&nbsp;&nbsp; Hour : ${hourdrop} &nbsp;&nbsp;&nbsp; Minute : &nbsp;&nbsp;&nbsp; ${mindrop} </p>
                    <c:if test="${sessionobject.reviewMonthly == 'true'}">
                    <p>Review Monthly?: <input type="checkbox"  name="review_monthly" checked="checked"/> </p>
                    </c:if>
                    <c:if test="${sessionobject.reviewMonthly == 'false'}">
                    <p>Review Monthly?: <input type="checkbox"  name="review_monthly" /> </p>
                    </c:if>
                    <c:if test="${sessionobject.reviewWeekly == 'true'}">
                    <p>Review Weekly?: <input type="checkbox"  name="review_weekly" checked="checked"/> </p>
                    </c:if>
                    <c:if test="${sessionobject.reviewWeekly == 'false'}">
                    <p>Review Weekly?: <input type="checkbox"  name="review_weekly" /> </p>
                    </c:if>


                <p>How many Weeks/Month? : <input type="text"  name="no_reviews"  value="${sessionobject.noReviews}" /> </p>
                <input type="hidden" name="session_id" value="${sessionobject.sessionId}"/>
                <p><input type="button" value="Save" onclick="checkDate('2');"/></p>
            </form>
        </c:if>
    </body>
</html>
