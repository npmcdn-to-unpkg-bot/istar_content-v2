<%-- 
    Document   : event_listing
    Created on : 25 Mar, 2016, 4:38:00 PM
    Author     : Kunal Chakravertti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Event Listing</title>
    </head>
    <body>
        <h1>Event Listing</h1>
        <table border="1">
            <thead >
            <th>Number</th>
            <th>Event Type</th>
            <th>Event Date</th>
            <th>Event Hour</th>
            <th>Event Minute</th>
             <th>&nbsp;</th>
             <th>&nbsp;</th>
        </thead>
        <tbody>
            <c:set var="count" value="1" scope="page"/>
            <c:forEach items="${eventList}" var="event">
                <tr>
                    <td>
                      ${count}
                    </td>
                    <td>${event.type}</td>
                    <td><fmt:formatDate pattern="dd/MM/yyyy" value="${event.eventdate}"/>   </td>
                    <td>${event.eventhour}</td>
                    <td>${event.eventminute}</td>
                    <td><a href="event_controller?_action=foredit&event_id=${event.id}">Edit</a></td>
                    <td><a href="event_controller?_action=delete&event_id=${event.id}">Delete</a></td>
                </tr>
                <c:set var="count" value="${count +1}" scope="page"/>
            </c:forEach>
        </tbody>
        <a href="event_controller?_action=create">Create New Event</a> &nbsp;&nbsp;||&nbsp;&nbsp; <a href="event_controller?_action=findall">All Events</a> &nbsp;&nbsp;||&nbsp;&nbsp;<a href="event_controller?_action=findbydate">Event Listing for Today</a> &nbsp;&nbsp;||&nbsp;&nbsp; 
        <a href="event_controller?_action=findbyweek">Event Listing for Week</a> &nbsp;&nbsp;||&nbsp;&nbsp; <a href="event_controller?_action=findbymonth">Event Listing for Month</a>
    </table>

</body>
</html>
