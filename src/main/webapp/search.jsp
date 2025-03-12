<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="ucl.ac.uk.main.Folder" %>
<%@ page import="ucl.ac.uk.main.Note" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Search Notes and Folders</title>
    <jsp:include page="/meta.jsp"/>
</head>

<body>
<jsp:include page="/header.jsp"/>

<div class="container">
    <h1>Search Notes</h1>

    <form method="GET" action="search">
        <div class="form-group">
            <label for="searchTerm">Search Notes:</label>
            <input type="text" name="searchTerm" id="searchTerm" class="form-control">
        </div>
        <button type="submit" class="btn btn-primary">Search</button>
    </form>

    <h3>Search Results:</h3>

    <h3>Notes:</h3>
    <ul class="list-group">
        <%
            List<Note> resultNotes = (List<Note>) request.getAttribute("resultNotes");
            if (resultNotes != null && !resultNotes.isEmpty()) {
                for (Note note : resultNotes)
                {
        %>
                    <li class="list-group-item">
                        <p><a href="displayNote?noteId=<%= note.getId() %>"><%= note.getName() %></a></p>
                        <p><%= note.getDateTime() %></p>
                    </li>
        <%
                }
            } else {
        %>
            <p>No notes found matching your search criteria.</p>
        <%
            }
        %>
    </ul>

    <h3>Folders:</h3>
    <ul class="list-group">
        <%
            List<Folder> resultFolders = (List<Folder>) request.getAttribute("resultFolders");
            if (resultFolders != null && !resultFolders.isEmpty()) {
                for (Folder folder : resultFolders)
                {
        %>
                    <li class="list-group-item">
                        <p><a href="displayIndex?folderId=<%= folder.getId() %>"><%= folder.getName() %></a></p>
                    </li>
        <%
                }
            } else {
        %>
                <p>No folders found matching your search criteria.</p>
        <%
            }
        %>
    </ul>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>