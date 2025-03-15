<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.Set" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Add Notes</title>
    <jsp:include page="/meta.jsp"/>
</head>

<body>
<jsp:include page="/header.jsp"/>

<div class="container">
    <h1>Start Taking Notes:</h1>
    <form method="post" action="/saveNote" enctype="multipart/form-data">

        <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>">

        <div class="form-group">
            <label for="note">Note Name:</label>
            <input type="text" id="noteName" name="noteName" placeholder="Enter note title" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="content">Note Content:</label>
            <textarea class="form-control" rows="15" id="content" name="content" placeholder="Write your notes here..." autofocus="1"></textarea>
        </div>

        <div class="form-group">
            <input type="file" name="image">
        </div>

        <div class="form-check">
            <label for="noteCategories">Categories:</label>
            <%
                Set<String> categories = (Set<String>) request.getAttribute("categories");
                for (String category : categories) {
            %>
                    <input class="form-check-input" type="checkbox" id="<%= category %>" name="noteCategories" value="<%= category %>">
                    <label class="form-check-label"><%= category %></label>
            <%  } %>
        </div>

        <input class="form-control btn-success" type="submit" value="Save">
    </form>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>