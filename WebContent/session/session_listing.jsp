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
        <title>Session Listing</title>
    </head>
    <body>
        <h1>Session Listing</h1>
        <table border="1">
            <thead >
            <th>Number</th>
            <th>Session Type</th>
            <th>Session Content</th>
            <th>Session Date</th>
            <th>Session Hour</th>
            <th>Session Minute</th>
            <th>Review Weekly?</th>
            <th>Review Monthly?</th> 
            <th>No Of Reviews</th>
            <th>&nbsp;</th>
        </thead>
        <tbody>
            <c:set var="count" value="1" scope="page"/>
            <c:forEach items="${sessionList}" var="tblsession">
                <tr>
                    <td>
                        ${count}
                    </td>
                    <td>${tblsession.sessionType}</td>
                      <td>${tblsession.classContent}</td>
                    <td><fmt:formatDate pattern="dd/MM/yyyy" value="${tblsession.sessionDate}"/>   </td>
                    <td>${tblsession.sessionHour}</td>
                    <td>${tblsession.sessionMinute}</td>
                    <td>${tblsession.reviewWeekly}</td>
                     <td>${tblsession.reviewMonthly}</td>
                     <td>${tblsession.noReviews}</td>
                    <td><a href="session_controller?_action=foredit&session_id=${tblsession.sessionId}">Edit</a></td>
                </tr>
                <c:set var="count" value="${count +1}" scope="page"/>
            </c:forEach>
        </tbody>
        <a href="session_controller?_action=create">Create New Session</a> 
    </table>

</body>
</html>
