<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ucl.ac.uk.classes.Folder" %>

<%
    String parentFolderId = (String) request.getAttribute("parentFolderId");
    Folder folder = (Folder) request.getAttribute("folder");
    boolean isNew = (Boolean) request.getAttribute("isNew");
%>


<html>
<head>
    <title>Add Folder</title>
    <jsp:include page="/meta.jsp"/>
</head>

<body>
<jsp:include page="/header.jsp"/>

<div class="container">
    <h2><%= isNew ? "Add New Folder" : "Edit Folder" %></h2>

    <form action="saveFolder" method="post">
        <% if (!isNew) { %>
            <input type="hidden" name="folderId" value="<%= folder.getId() %>">
        <% } %>
        <input type="hidden" name="parentFolderId" value="<%= parentFolderId %>">

        <div class="form-group">
            <label for="folderName">Folder Name:</label>
            <input type="text" id="folderName" name="folderName" value="<%= isNew ? "" : folder.getName() %>" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-primary"><%= isNew ? "Save Folder" : "Update Folder" %></button>
    </form>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>
