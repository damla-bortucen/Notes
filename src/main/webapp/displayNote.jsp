<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ucl.ac.uk.classes.Note" %>
<%@ page import="ucl.ac.uk.classes.Image" %>
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
                <p><strong>Content:</strong><br><%= note.formatContent().replace("\n", "<br>") %></p>

        <%
                Image image = note.getImage();
                if (image != null) {
                    System.out.println(image.getImageName());
        %>
                    <img src="<%= image.getImageName() %>" width="300" alt="<%= image.getImageName() %>">
        <%      } %>

        <p><strong>Categories:</strong></p>
        <div class="container">
            <%
                Set<String> noteCategories = note.getCategories();

                if (!noteCategories.isEmpty()) {
                    for (String category : noteCategories) {
            %>
                        <span class="badge"><%= category %></span>
            <%      }
                } else {
            %>
                    <p>Currently this note is in no categories.</p>
            <%
                }
            %>
        </div><br>
        <a href="displayFolder?folderId=<%=note.getParentId()%>" class="btn btn-primary">
            <span class="glyphicon glyphicon-arrow-left"></span> Back
        </a>
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
