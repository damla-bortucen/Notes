<%@ page import="java.util.Map" %>
<%@ page import="ucl.ac.uk.classes.Folder" %>
<%@ page import="ucl.ac.uk.classes.Note" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Damla&#39;s Notes App</title>
    <jsp:include page="/meta.jsp"/>
</head>

<body>
<jsp:include page="/header.jsp"/>

<div class="container">
    <h1>Damla&#39;s Notes</h1>
    <form action="displayFolder" method="get">
        <input type="hidden" name="folderId" value="root"/>
        <button class="btn btn-primary btn-lg" type="submit">View Notes</button>
    </form>
    <a href="search.jsp" class="btn btn-primary btn-lg">
        <span class="glyphicon glyphicon-search"></span> Search
    </a>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>