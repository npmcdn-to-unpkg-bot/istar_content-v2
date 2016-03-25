<%-- 
    Document   : create_edit_event
    Created on : 25 Mar, 2016, 4:38:50 PM
    Author     : Kunal Chakravertti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html><%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>jQuery UI Datepicker - Default functionality</title>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">



        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>


        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>


        <script>
            $(function () {
                $("#datepicker").datepicker({dateFormat: 'dd/mm/yy'});
            });

        </script>
        <script type="text/javascript">
            function checkDate() {
                var flag = false;
                var todayDate = new Date();
                //need to add one to get current month as it is start with 0
                var todayMonth = todayDate.getMonth() + 1;
                var todayDay = todayDate.getDate();
                var todayYear = todayDate.getFullYear();
                var todayDateText = todayDay + "/" + todayMonth + "/" + todayYear;
                var inputDateText = document.getElementById('datepicker').value;
                var inputToDate = Date.parse(inputDateText);
                var todayToDate = Date.parse(todayDateText);
                if (inputToDate > todayToDate) {
                    flag = true;
                    alert("the input is later than today");
                } else {

                }
//                else if (inputToDate < todayToDate) {
//                    alert("the input is earlier than today");
//                }
//                else {
//                    alert("the input is same as today");
//                }
                if (flag) {

                } else {

                }

            }
        </script>
    </head>
    <body>
        <c:if test="${action == 'create'}">
            <form action="event_controller?_action=save" method="post">
                <h1>Create Event</h1>
                <p>Event Type : <input type="text"  name="event_type" /> </p>
                <p>Date: <input type="text" id="datepicker" name="event_date" />(dd/MM/yyyy) &nbsp;&nbsp;&nbsp; Hour : ${hourdrop} &nbsp;&nbsp;&nbsp; Minute : &nbsp;&nbsp;&nbsp; ${mindrop} </p>
                <p><input type="submit" value="Save" /></p>
            </form>
        </c:if>
        <c:if test="${action == 'edit'}">
            <form action="event_controller?_action=update" method="post">
                <h1>Edit Event</h1>
                <p>Event Type : <input type="text" value="${eventobject.type}" name="event_type" /> </p>
                <p>Date: <input type="text" id="datepicker" name="event_date" value="<fmt:formatDate pattern='dd/MM/yyyy' value='${eventobject.eventdate}'  />" /> (dd/MM/yyyy) &nbsp;&nbsp;&nbsp; Hour : ${hourdrop} &nbsp;&nbsp;&nbsp; Minute : &nbsp;&nbsp;&nbsp; ${mindrop} </p>
                <p><input type="button" value="Save" onclick="checkDate();"/></p>
                <input type="hidden" name="event_id" value="${eventId}"/>
            </form>
        </c:if>
    </body>
</html>
