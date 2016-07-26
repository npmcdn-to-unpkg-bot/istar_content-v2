<%-- 
    Document   : learning_objective
    Created on : 29 Mar, 2016, 5:35:27 PM
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
        <h1>Choose Learning Objectives</h1>
        <form action="tests_controller?_action=choosequestions" method="POST">
            <c:set var="count" value="1" scope="page"/>
            <table border="1">
                <thead>
                <th>&nbsp;</th><th>Title</th><th>&nbsp;</th>
                </thead>
                <c:forEach items="${learningObjectiveList}" var="item">
                    <c:if test="${oldLearningObjectiveList.contains(item.id)}">
                        <tr>
                            <td>${count}</td> <td>${item.title}</td><td><input type="checkbox" name="obj_${item.id}" checked="checked"/></td>
                        </tr>
                    </c:if>
                    <c:if test="${!oldLearningObjectiveList.contains(item.id)}">
                        <tr>
                            <td>${count}</td> <td>${item.title}</td><td><input type="checkbox" name="obj_${item.id}" /></td>
                        </tr>
                    </c:if>
                    <c:set var="count" value="${count +1}" scope="page"/>
                </c:forEach> 
            </table>
            <input type="submit" value="Next" />
        </form>

    </body>
</html>
