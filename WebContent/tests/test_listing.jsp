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
        <title>Tests Listing</title>
    </head>
    <body>
        <h1>Tests Listing</h1>
        <table border="1">
            <thead >
            <th>Number</th>
            <th>Test Title</th>
            <th>Test Type</th>
            <th>Test Date</th>
            <th>Test Time (HH:MM)</th>
            <th>Test Duration (HH:MM)</th>
            <th>Question Count</th>
            <th>&nbsp;</th>
        </thead>
        <tbody>
            <c:set var="count" value="1" scope="page"/>
            <c:forEach items="${assessmentList}" var="test">
                <tr>
                    <td>
                        ${count}
                    </td>
                    <td>${test.assessmentTitle}</td>
                    <td>${test.assessmentType}</td>
                    <td><fmt:formatDate pattern="dd/MM/yyyy" value="${test.assessmentDate}"/>   </td>
                    <td>${test.assessmentHour} : ${test.assessmentMinutes}</td>
                    <td>${test.assessmentDurationHours} : ${test.assessmentDurationMinutes}</td>
                    <td>${test.numberOfQuestions}</td>
                    <td><a href="tests_controller?_action=foredit&test_id=${test.id}">Edit</a></td>
                </tr>
                <c:set var="count" value="${count +1}" scope="page"/>
            </c:forEach>
        </tbody>
        <a href="tests_controller?_action=create">Create New Test</a> 
    </table>

</body>
</html>
