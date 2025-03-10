<%@ page import="ucl.ac.uk.main.Note" %>

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
        <a href="displayIndex?folderId=root" class="btn btn-primary">Back to Notes List</a>

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
