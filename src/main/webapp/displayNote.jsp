<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ucl.ac.uk.classes.Note" %>
<%@ page import="java.util.Set" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Note Display</title>
    <jsp:include page="/meta.jsp"/>
</head>

<body>
<jsp:include page="/header.jsp"/>

    <div class="container">
        <%
            // Retrieve the note from the request attribute
            Note note = (Note) request.getAttribute("note");

            // Check if the note is not null
            if (note != null) {
        %>

                <h1><%= note.getName() %></h1>
                <p><strong>Date Created:</strong> <%= note.getDateTime() %></p>
                <p><strong>Content:</strong><br><%= note.getContent().replace("\n", "<br>") %></p>

        <%
                String imagePath = note.getImagePath();
                if (imagePath != null && !imagePath.isEmpty()) {
        %>
                    <img src="<%= imagePath %>">
        <%      } %>

        <p><strong>Categories:</strong></p>
        <ul class="list-group">
            <%
                Set<String> noteCategories = note.getCategories();

                if (!noteCategories.isEmpty()) {
                    for (String category : noteCategories) {
            %>
                        <li class="list-group-item">
                            <p><%= category %></p>
                        </li>
            <%      }
                } else {
            %>
                    <p>Currently this note is in no categories.</p>
            <%
                }
            %>
        </ul>

        <form action="editNote" method="post" style="display:inline;">
            <input type="hidden" name="noteId" value="<%= note.getId() %>">
            <button type="submit" class="btn btn-info">Edit</button>
        </form>

        <form action="deleteNote" method="post" style="display:inline;">
            <input type="hidden" name="noteId" value="<%= note.getId() %>">
            <button type="submit" class="btn btn-danger">Delete</button>
        </form>
        <br><br> <a href="displayIndex?folderId=<%=note.getParentId()%>" class="btn btn-primary">Back</a>
        <%
            } else {
        %>
            <p>Note not found.</p>
        <%
            }
        %>
    </div>

    <jsp:include page="/footer.jsp"/>
</body>
</html>
