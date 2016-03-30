<%-- 
    Document   : choose_questions
    Created on : 29 Mar, 2016, 6:17:35 PM
    Author     : Kunal Chakravertti
--%>

 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <h1>Choose Questions</h1>
        <form action="tests_controller?_action=save" method="POST">
            <c:set var="count" value="1" scope="page"/>
            <table border="1"> 
                <thead>
                <th>&nbsp;</th><th>Text</th><th>&nbsp;</th>
                </thead>
                <c:forEach items="${questionList}" var="item">
                    <tr>
                        <td>${count}</td> <td>${item.questionText}</td><td><input type="checkbox" name="question_${item.id}" /></td>
                    </tr>
                    <c:set var="count" value="${count +1}" scope="page"/>
                </c:forEach> 
            </table>
            <input type="submit" value="Save" />
        </form>

    </body>
